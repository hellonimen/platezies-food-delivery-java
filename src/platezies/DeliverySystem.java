package platezies;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.border.*;
import java.util.List; 

public class DeliverySystem extends JFrame {
    private JList<String> taskList;
    private DefaultListModel<String> taskModel;
    private List<String[]> allTasks;
    private String currentRunner;
    private JTextArea taskDetails;
    

    private static final Font MAIN_FONT = new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 36);
    private static final Font SUB_FONT = new Font("Tw Cen MT Condensed", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Impact", Font.PLAIN, 14);
    private static final double DELIVERY_FEE = 5.0;

    public DeliverySystem(String runnerId) {
        this.currentRunner = runnerId;
        setTitle("Delivery System - " + runnerId);
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // 主面板设置
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // 左侧任务面板
        JPanel leftPanel = createLeftPanel();
        
        // 右侧详情面板
        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
        
        // 初始化数据
        taskModel = new DefaultListModel<>();
        allTasks = new ArrayList<>();
        loadTasks();
        
        setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            "Available Tasks",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            MAIN_FONT
        ));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(500, 0));

        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);
        taskList.setFont(SUB_FONT);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.addListSelectionListener(e -> showTaskDetails(taskList.getSelectedIndex()));
        
        JScrollPane scrollPane = new JScrollPane(taskList);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        // 任务详情区域
        taskDetails = new JTextArea();
        taskDetails.setEditable(false);
        taskDetails.setFont(SUB_FONT);
        taskDetails.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane detailsScrollPane = new JScrollPane(taskDetails);
        detailsScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            "Task Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            MAIN_FONT
        ));

        // 按钮面板
        JPanel buttonPanel = createButtonPanel();

        panel.add(detailsScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(new EmptyBorder(10, 0, 10, 0));
        panel.setBackground(Color.WHITE);

        JButton acceptButton = createStyledButton("Accept Task", new Color(76, 175, 80));
        JButton declineButton = createStyledButton("Decline Task", new Color(244, 67, 54));
        JButton backButton = createStyledButton("Back to Main", new Color(170, 170, 170));

        acceptButton.addActionListener(e -> acceptTask(taskList.getSelectedIndex()));
        declineButton.addActionListener(e -> declineTask(taskList.getSelectedIndex()));
        backButton.addActionListener(e -> dispose());

        panel.add(acceptButton);
        panel.add(declineButton);
        panel.add(backButton);
        
        return panel;
    }

    private void loadTasks() {
        taskModel.clear();
        allTasks.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/tasks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                
                // 检查是否为当前runner的pending任务
                if (data.length >= 5 
                    && data[3].equalsIgnoreCase("pending")
                    && data[4].equals(currentRunner)) {
                    
                    // 检查是否为delivery类型订单
                    if (isDeliveryOrder(data[0])) {
                        allTasks.add(data);
                        String taskDesc = String.format("%s | Address: %s | Delivery Fee: RM%.2f", 
                            data[0], data[1], DELIVERY_FEE);
                        taskModel.addElement(taskDesc);
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
            loadTaskDetails(task[0]);
        }
    }

    private void loadTaskDetails(String orderId) {
        StringBuilder details = new StringBuilder();
        details.append("🚚 Order Details:\n\n");
        details.append("Order ID: ").append(orderId).append("\n\n");
        details.append("Items:\n");
        
        double totalFoodCost = 0.0;
        
        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/OrderItem.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 8 && data[1].equals(orderId)) {
                    String itemName = data[4];
                    double price = Double.parseDouble(data[7]);
                    details.append(String.format("- %s: RM%.2f\n", itemName, price));
                    totalFoodCost += price;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        details.append("\nFood Total: RM").append(String.format("%.2f", totalFoodCost));
        details.append("\nDelivery Fee: RM").append(String.format("%.2f", DELIVERY_FEE));
        details.append("\nRunner: ").append(currentRunner);
        
        taskDetails.setText(details.toString());
    }

    private void acceptTask(int selectedIndex) {
        if (selectedIndex != -1) {
            String[] task = allTasks.get(selectedIndex);
            task[3] = "Delivering";  // 更新状态
            task[2] = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());  // 更新时间
            saveTasksToFile();
            addDeliveryRevenue(task[0]);  // 记录收入
            taskModel.remove(selectedIndex);
            taskDetails.setText("");
            JOptionPane.showMessageDialog(this, "Task accepted: " + task[0]);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task first!");
        }
    }

    private void declineTask(int selectedIndex) {
        if (selectedIndex != -1) {
            String[] task = allTasks.get(selectedIndex);
            task[3] = "Declined";
            task[2] = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
            task[4] = findNextAvailableRunner(task[4]);
            saveTasksToFile();
            taskModel.remove(selectedIndex);
            taskDetails.setText("");
            JOptionPane.showMessageDialog(this, "Task declined: " + task[0]);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task first!");
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
                currentRunner, orderId, DELIVERY_FEE, date));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTasksToFile() {
        try (FileWriter writer = new FileWriter("platezies_txtfiles/tasks.txt")) {
            for (String[] task : allTasks) {
                writer.write(String.join(",", task) + "\n");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks: " + e.getMessage());
        }
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setFocusPainted(false);
        return button;
    }

    
}