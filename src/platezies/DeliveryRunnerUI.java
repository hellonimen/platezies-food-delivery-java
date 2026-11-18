package platezies;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.border.TitledBorder;
import javax.imageio.ImageIO;

public class DeliveryRunnerUI extends JFrame {
    
    private static final Font MAIN_FONT = new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 36);
    private static final Font SUB_FONT = new Font("Tw Cen MT Condensed", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Impact", Font.PLAIN, 14);
    private static final double DELIVERY_FEE = 5.0;
    
    private static final Color TEAL_COLOR = new Color(0, 128, 128);
    private static final Color LIGHT_TEAL = new Color(0, 150, 150);
    private static final Color SEMI_TEAL = new Color(0, 128, 128, 160);
    private static final Color TEXT_COLOR = Color.BLACK;
    
    private final Runner runner;
    private String currentRunner;
    private DefaultListModel<String> taskModel;
    private List<String[]> allTasks;
    private JTextArea detailsArea;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JToggleButton onlineStatusButton;
    private JLabel completedOrdersLabel;
    private JLabel totalEarningsLabel;
    private boolean isOnline = true;
    private Timer autoRefreshTimer;

    public DeliveryRunnerUI(Runner runner) {
        this.runner = runner;
        currentRunner = runner.getUsername();
        taskModel = new DefaultListModel<>();
        allTasks = new ArrayList<>();
        
        setTitle("Delivery Runner Dashboard - " + runner);
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1400, 800));
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 0));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(Color.WHITE);

        setupMenuBar();
        JPanel statusPanel = createStatusPanel();
        mainPanel.add(statusPanel, BorderLayout.NORTH);

        setupLeftPanel(mainPanel);
        setupRightPanel(mainPanel);

        add(mainPanel);

        loadTasksFromFile("platezies_txtfiles/tasks.txt");
        setupFileMonitoring();
        startAutoRefresh();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupFileMonitoring() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get(".");
            dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            new Thread(() -> {
                while (true) {
                    try {
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            String fileName = event.context().toString();
                            if (fileName.equals("platezies_txtfiles/tasks.txt") || 
                                fileName.equals("platezies_txtfiles/delivery_revenue.txt")) {
                                SwingUtilities.invokeLater(this::refreshData);
                            }
                        }
                        key.reset();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startAutoRefresh() {
        if (autoRefreshTimer != null) {
            autoRefreshTimer.stop();
        }
        autoRefreshTimer = new Timer(2000, e -> refreshData());
        autoRefreshTimer.start();
    }

    public void refreshData() {
        loadTasksFromFile("platezies_txtfiles/tasks.txt");
        updateDashboardData();
        revalidate();
        repaint();
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(TEAL_COLOR);
        
        String[] menus = {"Manage Tasks", "Task History", "Customer Reviews", "Revenue Dashboard"};
        
        for (String menu : menus) {
            JMenu m = new JMenu(menu);
            m.setFont(SUB_FONT.deriveFont(24f));
            m.setForeground(TEXT_COLOR);
            m.setBackground(TEAL_COLOR);
            setupMenuListeners(m, menu);
            menuBar.add(m);
        }
        
        setJMenuBar(menuBar);
    }

    private void setupMenuListeners(JMenu m, String menu) {
        m.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                m.setForeground(Color.WHITE);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                m.setForeground(TEXT_COLOR);
            }
            
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                switch (menu) {
                    case "Manage Tasks":
                        new TaskManagementPage(runner);
                        break;
                    case "Task History":
                        new TaskHistoryPage(runner);
                        break;
                    case "Customer Reviews":
                        new customer_reviewpage(runner);
                        break;
                    case "Revenue Dashboard":
                        new RevenueDashboardPage(runner);
                        break;
                }
            }
        });
    }

    private void setupLeftPanel(JPanel mainPanel) {
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(TEAL_COLOR, 2),
            "Active Tasks (0)",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            MAIN_FONT,
            TEAL_COLOR
        ));
        leftPanel.setBackground(new Color(240, 255, 255));

        JList<String> taskList = new JList<>(taskModel);
        taskList.setFont(SUB_FONT);
        taskList.setForeground(TEXT_COLOR);
        taskList.setBackground(new Color(240, 255, 255));
        taskList.setCellRenderer(new TaskListRenderer());
        taskList.addListSelectionListener(e -> showTaskDetails(taskList.getSelectedIndex()));
        
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBackground(new Color(240, 255, 255));
        scrollPane.getViewport().setBackground(new Color(240, 255, 255));
        
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.WEST);
    }

    private void setupRightPanel(JPanel mainPanel) {
        rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(new Color(240, 255, 255));

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(SUB_FONT);
        detailsArea.setForeground(TEXT_COLOR);
        detailsArea.setBackground(new Color(240, 255, 255));
        detailsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEAL_COLOR, 2),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        detailsScroll.setBackground(new Color(240, 255, 255));
        detailsScroll.getViewport().setBackground(new Color(240, 255, 255));

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 15, 15));
        buttonPanel.setBackground(new Color(240, 255, 255));
        buttonPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        JButton acceptBtn = createActionButton("Accept Task", TEAL_COLOR);
        JButton declineBtn = createActionButton("Decline Task", new Color(220, 53, 69));

        acceptBtn.addActionListener(e -> acceptTask(((JList<String>)((JScrollPane)leftPanel.getComponent(0)).getViewport().getView()).getSelectedIndex()));
        declineBtn.addActionListener(e -> declineTask(((JList<String>)((JScrollPane)leftPanel.getComponent(0)).getViewport().getView()).getSelectedIndex()));

        buttonPanel.add(acceptBtn);
        buttonPanel.add(declineBtn);

        rightPanel.add(detailsScroll, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(rightPanel, BorderLayout.CENTER);
    }

    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        statusPanel.setBackground(SEMI_TEAL);

        onlineStatusButton = new JToggleButton("Online", true) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isSelected()) {
                    g2d.setColor(TEAL_COLOR);
                } else {
                    g2d.setColor(new Color(158, 158, 158));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                FontMetrics fm = g2d.getFontMetrics();
                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), 
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
            }
        };

        onlineStatusButton.setFont(BUTTON_FONT);
        onlineStatusButton.setPreferredSize(new Dimension(100, 40));
        onlineStatusButton.setBorderPainted(false);
        onlineStatusButton.setContentAreaFilled(false);
        onlineStatusButton.setFocusPainted(false);
        onlineStatusButton.addActionListener(e -> toggleOnlineStatus());

        completedOrdersLabel = new JLabel("Completed Orders: 0");
        totalEarningsLabel = new JLabel("Today's Earnings: RM0.00");
        completedOrdersLabel.setFont(SUB_FONT);
        totalEarningsLabel.setFont(SUB_FONT);
        completedOrdersLabel.setForeground(TEXT_COLOR);
        totalEarningsLabel.setForeground(TEXT_COLOR);

        statusPanel.add(onlineStatusButton);
        statusPanel.add(completedOrdersLabel);
        statusPanel.add(totalEarningsLabel);

        return statusPanel;
    }

    private void toggleOnlineStatus() {
        isOnline = onlineStatusButton.isSelected();
        onlineStatusButton.setText(isOnline ? "Online" : "Offline");
        updateOnlineStatus();
        loadTasksFromFile("platezies_txtfiles/tasks.txt");
    }

    private void updateOnlineStatus() {
        try (FileWriter writer = new FileWriter("platezies_txtfiles/runner_status.txt", true)) {
            String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            writer.write(String.format("%s,%s,%s\n", 
                runner, 
                isOnline ? "online" : "offline",
                timestamp));
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateAllOpenWindows();
    }

    private void loadTasksFromFile(String filename) {
        taskModel.clear();
        allTasks.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                
                if (data.length >= 5 
                    && data[3].equalsIgnoreCase("pending")
                    && data[4].equals(currentRunner)
                    && isOnline) {
                    
                    if (isDeliveryOrder(data[0])) {
                        allTasks.add(data);
                        String taskDesc = String.format("%s | Address: %s | Delivery Fee: RM%.2f", 
                            data[0], data[1], DELIVERY_FEE);
                        taskModel.addElement(taskDesc);
                    }
                }
            }
            updateActiveTasks();
            updateDashboardData();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading tasks: " + e.getMessage());
        }
    }

    private boolean isDeliveryOrder(String orderId) {
        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/OrderItem.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6 && data[1].equals(orderId) && "delivery".equals(data[5])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showTaskDetails(int selectedIndex) {
        if (selectedIndex != -1) {
            String[] task = allTasks.get(selectedIndex);
            detailsArea.setText(formatTaskDetails(task));
        }
    }

       private String formatTaskDetails(String[] task) {
        StringBuilder details = new StringBuilder();
        details.append("🚚 Order Details:\n\n");
        details.append("ID: ").append(task[0]).append("\n");
        details.append("Address: ").append(task[1]).append("\n\n");
        
        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/OrderItem.txt"))) {
            String line;
            details.append("Items:\n");
            double totalFoodCost = 0.0;
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 8 && data[1].equals(task[0])) {
                    String itemName = data[4];
                    double price = Double.parseDouble(data[7]);
                    details.append(String.format("  - %s: RM%.2f\n", itemName, price));
                    totalFoodCost += price;
                }
            }
            
            details.append("\nFood Total: RM").append(String.format("%.2f", totalFoodCost));
            details.append("\nDelivery Fee: RM").append(String.format("%.2f", DELIVERY_FEE));
            details.append("\nAssigned Runner: ").append(task[4]);
            
        } catch (IOException e) {
            e.printStackTrace();
            details.append("\nError loading order details");
        }

        return details.toString();
    }

    private void updateDashboardData() {
        int completedOrders = 0;
        double totalEarnings = 0.0;
        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/delivery_revenue.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length >= 5 
                    && data[0].equals(currentRunner)
                    && data[3].startsWith(today)
                    && data[4].equals("delivery")) {
                    completedOrders++;
                    totalEarnings += Double.parseDouble(data[2]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        completedOrdersLabel.setText("Completed Orders: " + completedOrders);
        totalEarningsLabel.setText("Today's Earnings: RM" + String.format("%.2f", totalEarnings));
    }

    private void updateAllOpenWindows() {
        for (Window window : Window.getWindows()) {
            if (window instanceof TaskManagementPage) {
                ((TaskManagementPage)window).refreshData();
            }
            if (window instanceof RevenueDashboardPage) {
                ((RevenueDashboardPage)window).refreshData();
            }
            if (window instanceof TaskHistoryPage) {
                ((TaskHistoryPage)window).refreshData();
            }
        }
    }
    
    private void acceptTask(int selectedIndex) {
        if (selectedIndex != -1) {
            String[] task = allTasks.get(selectedIndex);
            task[3] = "Delivering";  // 更改状态为Delivering
            task[2] = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            saveTasksToFile();
            
            // 记录收入
            addDeliveryRevenue(task[0]);
            
            taskModel.remove(selectedIndex);
            updateActiveTasks();
            updateAllOpenWindows();
            JOptionPane.showMessageDialog(this, "Task accepted: " + task[0]);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task first!");
        }
    }

private void declineTask(int selectedIndex) {
    if (selectedIndex != -1) {
        String[] task = allTasks.get(selectedIndex);
        task[3] = "Declined";
        task[2] = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        task[4] = findNextAvailableRunner(task[4]);
        saveTasksToFile();
        
        // 添加扣除记录
        addDeductionRecord(task[0]);
        
        taskModel.remove(selectedIndex);
        updateAllOpenWindows();
        JOptionPane.showMessageDialog(this, "Task declined: " + task[0]);
    } else {
        JOptionPane.showMessageDialog(this, "Please select a task first!");
    }
}

// 新增扣除记录的方法
private void addDeductionRecord(String orderId) {
    try (FileWriter writer = new FileWriter("platezies_txtfiles/delivery_revenue.txt", true)) {
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        writer.write(String.format("%s,%s,%.2f,%s,deduction\n",
            runner, orderId, 2.0, date));
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private String findNextAvailableRunner(String currentRunner) {
        String[] runners = {"runner1", "runner2", "runner3"};
        for (String runner : runners) {
            if (!runner.equals(currentRunner)) {
                return runner;
            }
        }
        return "none";
    }

    private void addDeliveryRevenue(String orderId) {
        try (FileWriter writer = new FileWriter("platezies_txtfiles/delivery_revenue.txt", true)) {
            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            writer.write(String.format("%s,%s,%.2f,%s,delivery\n",
                runner, orderId, DELIVERY_FEE, date));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTasksToFile() {
        try {
            // 首先读取所有任务
            List<String> allLines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("platezies_txtfiles/tasks.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    allLines.add(line);
                }
            }

            // 更新已修改的任务
            for (int i = 0; i < allLines.size(); i++) {
                String[] data = allLines.get(i).split(",");
                if (data.length >= 5) {
                    for (String[] task : allTasks) {
                        if (task[0].equals(data[0])) {
                            // 确保按照正确的顺序写入数据：orderId,address,date,status,runnerId
                            allLines.set(i, String.format("%s,%s,%s,%s,%s",
                                task[0], task[1], task[2], task[3], task[4]));
                            break;
                        }
                    }
                }
            }

            // 写回文件
            try (FileWriter writer = new FileWriter("platezies_txtfiles/tasks.txt")) {
                for (String line : allLines) {
                    writer.write(line + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving tasks: " + e.getMessage());
        }
    }

    private void updateActiveTasks() {
        String title = String.format("Active Tasks (%d)", taskModel.size());
        ((TitledBorder)leftPanel.getBorder()).setTitle(title);
        leftPanel.repaint();
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2d.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(color.brighter());
                } else {
                    g2d.setColor(color);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                FontMetrics fm = g2d.getFontMetrics();
                g2d.setColor(Color.WHITE);
                g2d.drawString(text, 
                    (getWidth() - fm.stringWidth(text)) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
            }
        };
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 40));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    static class TaskListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                     boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
            label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, TEAL_COLOR),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            if (isSelected) {
                label.setBackground(LIGHT_TEAL);
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(new Color(240, 255, 255));
                label.setForeground(TEXT_COLOR);
            }
            return label;
        }
    }

    @Override
    public void dispose() {
        if (autoRefreshTimer != null) {
            autoRefreshTimer.stop();
        }
        super.dispose();
    }

    
}