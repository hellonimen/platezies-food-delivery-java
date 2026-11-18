package platezies;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.Timer;

public class TaskManagementPage extends JFrame {
    private final Runner runner;
    private static final Font MAIN_FONT = new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 36);
    private static final Font SUB_FONT = new Font("Tw Cen MT Condensed", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Impact", Font.PLAIN, 14);
    
    private static final Color TEAL_COLOR = new Color(0, 128, 128);
    private static final Color LIGHT_TEAL = new Color(0, 150, 150);
    private static final Color SEMI_TEAL = new Color(0, 128, 128, 160);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final double DELIVERY_FEE = 5.0;
    private TaskItem currentlySelectedTask = null;

    private JTabbedPane taskTabs;
    private DefaultListModel<TaskItem> allTasksModel;
    private DefaultListModel<TaskItem> pendingTasksModel;
    private DefaultListModel<TaskItem> deliveringTasksModel;
    private DefaultListModel<TaskItem> completedTasksModel;
    private JTextArea taskDetails;
    private String currentRunner;
    private List<TaskItem> allTasks;
    private Timer autoRefreshTimer;

    public TaskManagementPage(Runner runner) {
        this.runner = runner;
        currentRunner = runner.getUsername();
        System.out.println(currentRunner);
        setTitle("Task Management - " + runner);
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1400, 800));

        initializeModels();
        setupUI();
        loadTasks();
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
                            if (fileName.equals("platezies_txtfiles/tasks.txt")) {
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
        autoRefreshTimer = new Timer(8000, e -> refreshData());
        autoRefreshTimer.start();
    }

    public void refreshData() {
        // Store the currently selected task before reloading
        if (taskTabs.getSelectedComponent() != null) {
            JPanel currentPanel = (JPanel) taskTabs.getSelectedComponent();
            JScrollPane scrollPane = (JScrollPane) currentPanel.getComponent(0);
            JList<TaskItem> currentList = (JList<TaskItem>) scrollPane.getViewport().getView();
            currentlySelectedTask = currentList.getSelectedValue();
        }

        loadTasks();

        // Restore selection after reloading
        if (currentlySelectedTask != null) {
            for (int i = 0; i < taskTabs.getTabCount(); i++) {
                JPanel panel = (JPanel) taskTabs.getComponentAt(i);
                JScrollPane scrollPane = (JScrollPane) panel.getComponent(0);
                JList<TaskItem> list = (JList<TaskItem>) scrollPane.getViewport().getView();
                
                // Find and select the task in the current tab
                for (int j = 0; j < list.getModel().getSize(); j++) {
                    TaskItem task = list.getModel().getElementAt(j);
                    if (task.orderId.equals(currentlySelectedTask.orderId)) {
                        list.setSelectedIndex(j);
                        list.ensureIndexIsVisible(j);
                        showTaskDetails(task);
                        break;
                    }
                }
            }
        }

        revalidate();
        repaint();
    }

    private void initializeModels() {
        allTasksModel = new DefaultListModel<>();
        pendingTasksModel = new DefaultListModel<>();
        deliveringTasksModel = new DefaultListModel<>();
        completedTasksModel = new DefaultListModel<>();
        allTasks = new ArrayList<>();
    }

private void setupUI() {
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setDividerLocation(600);
    splitPane.setBackground(Color.WHITE);

    taskTabs = new JTabbedPane();
    taskTabs.setFont(SUB_FONT);
    taskTabs.setBackground(Color.WHITE);
    taskTabs.setForeground(TEXT_COLOR);

    // Create panels with modified list selection
    taskTabs.addTab("All Tasks", createTaskPanel(allTasksModel, true));
    taskTabs.addTab("Pending", createTaskPanel(pendingTasksModel, true));
    taskTabs.addTab("Delivering", createTaskPanel(deliveringTasksModel, true));
    taskTabs.addTab("Completed", createTaskPanel(completedTasksModel, true));

    JPanel rightPanel = createDetailsPanel();

    splitPane.setLeftComponent(taskTabs);
    splitPane.setRightComponent(rightPanel);

    JPanel buttonPanel = createButtonPanel();

    setLayout(new BorderLayout());
    add(splitPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
}

private JPanel createTaskPanel(DefaultListModel<TaskItem> model, boolean multiSelect) {
    JList<TaskItem> list = new JList<>(model);
    list.setFont(SUB_FONT);
    list.setBackground(Color.WHITE);
    list.setForeground(TEXT_COLOR);
    
    // Set selection mode to prevent auto-deselection
    if (multiSelect) {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    list.setCellRenderer(new TaskItemRenderer());
    
    // Modify the list selection listener
    list.addListSelectionListener(e -> {
        // Only process when selection is complete
        if (!e.getValueIsAdjusting()) {
            TaskItem selected = list.getSelectedValue();
            if (selected != null) {
                showTaskDetails(selected);
            }
        }
    });

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.WHITE);
    panel.add(new JScrollPane(list), BorderLayout.CENTER);
    return panel;
}

    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(TEAL_COLOR),
            "Task Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            SUB_FONT,
            TEAL_COLOR
        ));

        taskDetails = new JTextArea();
        taskDetails.setEditable(false);
        taskDetails.setFont(SUB_FONT);
        taskDetails.setBackground(Color.WHITE);
        taskDetails.setForeground(TEXT_COLOR);
        taskDetails.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(taskDetails);
        scrollPane.setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.WHITE);
        
        JButton updateStatusBtn = createStyledButton("Update Status", TEAL_COLOR);
        JButton batchProcessBtn = createStyledButton("Batch Process", TEAL_COLOR);
        JButton backButton = createStyledButton("Back to Main", new Color(170, 170, 170));

        updateStatusBtn.addActionListener(e -> showUpdateStatusDialog());
        batchProcessBtn.addActionListener(e -> showBatchProcessDialog());
        backButton.addActionListener(e -> dispose());

        panel.add(updateStatusBtn);
        panel.add(batchProcessBtn);
        panel.add(backButton);

        return panel;
    }

private void loadTasks() {
        allTasksModel.clear();
        pendingTasksModel.clear();
        deliveringTasksModel.clear();
        completedTasksModel.clear();
        allTasks.clear();

        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/tasks.txt"))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("[")) {
                    continue;
                }
                
                String[] data = line.split(",");
                if (data.length >= 5) {
                    if (isDeliveryOrder(data[0])) {
                        TaskItem task = new TaskItem(data);
                        allTasks.add(task);
                        allTasksModel.addElement(task);
                        
                        if (task.runnerId.equals(currentRunner)) {
                            switch (task.status.toLowerCase()) {
                                case "pending":
                                    pendingTasksModel.addElement(task);
                                    break;
                                case "delivering":
                                    deliveringTasksModel.addElement(task);
                                    break;
                                case "completed":
                                    completedTasksModel.addElement(task);
                                    break;
                            }
                        }
                    }
                }
            }
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
                if (data.length >= 6 && data[1].equals(orderId)) {
                    return "delivery".equals(data[5]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateTaskModels(TaskItem task) {
        allTasksModel.addElement(task);
        
        if (task.runnerId.equals(currentRunner)) {
            switch (task.status.toLowerCase()) {
                case "pending":
                    pendingTasksModel.addElement(task);
                    break;
                case "delivering":
                    deliveringTasksModel.addElement(task);
                    break;
                case "completed":
                    completedTasksModel.addElement(task);
                    break;
            }
        }
    }

    private void showTaskDetails(TaskItem task) {
        if (task != null) {
            taskDetails.setText(task.getDetailedInfo());
        }
    }
private void showUpdateStatusDialog() {
    String[] statuses = {"Pending", "Delivering", "Completed"};
    String selectedStatus = (String) JOptionPane.showInputDialog(
        this,
        "Select new status:",
        "Update Status",
        JOptionPane.QUESTION_MESSAGE,
        null,
        statuses,
        statuses[0]
    );

    if (selectedStatus != null) {
        // Get the currently selected tab's list
        int selectedTabIndex = taskTabs.getSelectedIndex();
        JPanel currentPanel = (JPanel) taskTabs.getComponentAt(selectedTabIndex);
        JScrollPane scrollPane = (JScrollPane) currentPanel.getComponent(0);
        JList<TaskItem> currentList = (JList<TaskItem>) scrollPane.getViewport().getView();
        
        TaskItem selectedTask = currentList.getSelectedValue();
        
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Please select a task first!");
            return;
        }
        
        updateTaskStatus(selectedTask, selectedStatus);
    }
}

    private void showBatchProcessDialog() {
        BatchProcessDialog dialog = new BatchProcessDialog(this);
        dialog.setVisible(true);
    }

    private void updateTaskStatus(TaskItem task, String newStatus) {
        if (!task.runnerId.equals(currentRunner)) {
            JOptionPane.showMessageDialog(this, "You can only update your own tasks!");
            return;
        }

        String oldStatus = task.status;
        task.status = newStatus;
        task.date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

        saveTasksToFile();
        refreshData();
        updateAllOpenWindows();
        
        // 如果是从Delivering变为Completed，添加收入记录
        if (oldStatus.equalsIgnoreCase("delivering") && newStatus.equalsIgnoreCase("completed")) {
            addDeliveryRevenue(task.orderId);
        }
    }

    private void addDeliveryRevenue(String orderId) {
        try (FileWriter writer = new FileWriter("platezies_txtfiles/delivery_revenue.txt", true)) {
            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            writer.write(String.format("%s,%s,%.2f,%s,delivery\n",
                currentRunner, orderId, DELIVERY_FEE, date));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateAllOpenWindows() {
        for (Window window : Window.getWindows()) {
            if (window instanceof DeliveryRunnerUI) {
                ((DeliveryRunnerUI)window).refreshData();
            }
            if (window instanceof RevenueDashboardPage) {
                ((RevenueDashboardPage)window).refreshData();
            }
            if (window instanceof TaskHistoryPage) {
                ((TaskHistoryPage)window).refreshData();
            }
        }
    }

 private void saveTasksToFile() {
    try {
        List<String> allLines = new ArrayList<>();
        boolean foundTask = false;
        
        // 读取所有现有任务
        try (BufferedReader reader = new BufferedReader(new FileReader("platezies_txtfiles/tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("[")) continue;
                
                String[] data = line.split(",");
                boolean lineUpdated = false;
                
                // 检查是否需要更新这一行
                for (TaskItem task : allTasks) {
                    if (data[0].equals(task.orderId)) {
                        allLines.add(String.format("%s,%s,%s,%s,%s",
                            task.orderId,
                            task.address,
                            task.date,
                            task.status,
                            task.runnerId));
                        lineUpdated = true;
                        foundTask = true;
                        break;
                    }
                }
                
                // 如果这行不需要更新，保持原样
                if (!lineUpdated) {
                    allLines.add(line);
                }
            }
        }

        // 如果没有找到任务，可能需要添加新任务
        if (!foundTask) {
            for (TaskItem task : allTasks) {
                allLines.add(String.format("%s,%s,%s,%s,%s",
                    task.orderId,
                    task.address,
                    task.date,
                    task.status,
                    task.runnerId));
            }
        }

        // 写回文件
        try (FileWriter writer = new FileWriter("platezies_txtfiles/tasks.txt")) {
            // 写入头部
            writer.write("[orderId],[address],[date],[status],[runnerId]\n");
            
            // 写入所有任务
            for (String line : allLines) {
                writer.write(line + "\n");
            }
        }
        
        // 更新完成后刷新数据
        refreshData();
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving tasks: " + e.getMessage());
    }
}

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(color);
        button.setForeground(TEXT_COLOR);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(TEXT_COLOR);
            }
        });

        return button;
    }

    class TaskItem {
        String orderId;
        String address;
        String status;
        String date;
        String runnerId;
        List<OrderItemDetails> orderDetails;

        public TaskItem(String[] data) {
            this.orderId = data[0];
            this.address = data[1];
            this.date = data[2];
            this.status = data[3];
            this.runnerId = data[4];
            this.orderDetails = new ArrayList<>();
            loadOrderDetails();
        }
         private void loadOrderDetails() {
            try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/OrderItem.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 8 && data[1].equals(this.orderId)) {
                        orderDetails.add(new OrderItemDetails(data));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getDetailedInfo() {
            StringBuilder details = new StringBuilder();
            details.append("Order ID: ").append(orderId).append("\n");
            details.append("Status: ").append(status).append("\n");
            details.append("Date: ").append(date).append("\n");
            details.append("Address: ").append(address).append("\n");
            details.append("Runner: ").append(runnerId).append("\n\n");
            
            details.append("Order Items:\n");
            double total = 0;
            for (OrderItemDetails item : orderDetails) {
                details.append(String.format("  - %s: RM%.2f\n", 
                    item.itemName, item.price));
                total += item.price;
            }
            details.append("\nFood Total: RM").append(String.format("%.2f", total));
            details.append("\nDelivery Fee: RM").append(String.format("%.2f", DELIVERY_FEE));
            
            return details.toString();
        }

        @Override
        public String toString() {
            return String.format("Order: %s | Status: %s | Date: %s",
                orderId, status, date);
        }
    }

    class OrderItemDetails {
        String itemName;
        double price;

        public OrderItemDetails(String[] data) {
            this.itemName = data[4];
            this.price = Double.parseDouble(data[7]);
        }
    }

    class BatchProcessDialog extends JDialog {
        private JList<TaskItem> taskList;
        private JComboBox<String> statusCombo;

        public BatchProcessDialog(JFrame parent) {
            super(parent, "Batch Process Tasks", true);
            setSize(500, 400);
            setLocationRelativeTo(parent);
            
            setLayout(new BorderLayout(10, 10));
            
            // 创建任务列表
            DefaultListModel<TaskItem> listModel = new DefaultListModel<>();
            for (TaskItem task : allTasks) {
                if (task.runnerId.equals(currentRunner)) {
                    listModel.addElement(task);
                }
            }
            
            taskList = new JList<>(listModel);
            taskList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            taskList.setFont(SUB_FONT);
            taskList.setBackground(Color.WHITE);
            taskList.setForeground(TEXT_COLOR);
            
            // 状态选择下拉框面板
            JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            controlPanel.setBackground(Color.WHITE);
            JLabel statusLabel = new JLabel("New Status:");
            statusLabel.setFont(SUB_FONT);
            statusLabel.setForeground(TEAL_COLOR);
            controlPanel.add(statusLabel);
            
            String[] statuses = {"pending", "Delivering", "Completed"};
            statusCombo = new JComboBox<>(statuses);
            statusCombo.setFont(SUB_FONT);
            statusCombo.setBackground(Color.WHITE);
            statusCombo.setForeground(TEAL_COLOR);
            controlPanel.add(statusCombo);
            
            // 按钮面板
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(Color.WHITE);
            
            JButton processButton = createStyledButton("Process", TEAL_COLOR);
            JButton cancelButton = createStyledButton("Cancel", new Color(170, 170, 170));
            
            processButton.addActionListener(e -> {
                processBatch();
            });
            
            cancelButton.addActionListener(e -> dispose());
            
            buttonPanel.add(processButton);
            buttonPanel.add(cancelButton);
            
            // 添加所有组件到对话框
            add(new JScrollPane(taskList), BorderLayout.CENTER);
            add(controlPanel, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.SOUTH);
            
            // 设置对话框边框
            getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, TEAL_COLOR));
        }

private void processBatch() {
    List<TaskItem> selectedTasks = taskList.getSelectedValuesList();
    String newStatus = (String) statusCombo.getSelectedItem();
    
    if (!selectedTasks.isEmpty()) {
        for (TaskItem task : selectedTasks) {
            String oldStatus = task.status;
            task.status = newStatus;
            task.date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            
            // 如果从Delivering变为Completed，添加收入记录
            if (oldStatus.equalsIgnoreCase("delivering") && 
                newStatus.equalsIgnoreCase("completed")) {
                addDeliveryRevenue(task.orderId);
            }
        }
        
        // 保存更改到文件
        saveTasksToFile();
        
        // 更新所有窗口
        updateAllOpenWindows();
        
        dispose();
        JOptionPane.showMessageDialog(TaskManagementPage.this, 
            "Selected tasks have been updated successfully!");
    } else {
        JOptionPane.showMessageDialog(this, "Please select at least one task!");
    }
}

class TaskItemRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                 boolean isSelected, boolean cellHasFocus) {
        // Ensure the value is a TaskItem
        if (value instanceof TaskItem) {
            TaskItem task = (TaskItem) value;
            JLabel label = (JLabel) super.getListCellRendererComponent(
                list, task.toString(), index, isSelected, cellHasFocus);
            
            label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, TEAL_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            
            if (isSelected) {
                label.setBackground(LIGHT_TEAL);
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(TEXT_COLOR);
            }
            
            return label;
        }
        
        // Fallback to default rendering
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
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
}