package platezies;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 */



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class OrderManagement {
    private static List<Order> orders = new ArrayList<>();
    private static String currentCuisine;
    private static DefaultTableModel model;

    public static void createAndShowGUI(Vendor user) {
        OrderManagement.currentCuisine = user.getCuisine();
        String FILE_PATH = ("platezies_txtfiles/OrderItem.txt");
        String filePath = Paths.get(FILE_PATH).toAbsolutePath().toString();
         
        loadOrdersFromFile(filePath);
        
        JFrame frame = new JFrame("Order Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        String[] columnNames = {"Order Item ID", "Order ID", "Username",  "Cuisine", "Item", "Type", "Cost","Price", "Status", "Date"};
        model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        refreshTableModel();

        JPanel controlPanel = new JPanel();
        JButton acceptButton = new JButton("Accept Order");
        acceptButton.setFont(new java.awt.Font("Impact", 0, 14));
        
        JButton rejectButton = new JButton("Reject Order");
        rejectButton.setFont(new java.awt.Font("Impact", 0, 14));
        
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new java.awt.Font("Impact", 0, 14));
        
        controlPanel.add(acceptButton);
        controlPanel.add(rejectButton);
        
        controlPanel.add(exitButton);
        frame.add(controlPanel, BorderLayout.SOUTH);

        acceptButton.addActionListener(e -> updateOrderStatus(table, "preparing"));
        rejectButton.addActionListener(e -> updateOrderStatus(table, "rejected"));

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Returning to home screen...");
                java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                new vendorHome(user).setVisible(true);
                            }
                        });
                      
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    private static void updateOrderStatus(JTable table, String newStatus) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow != -1) {
        String orderItemId = model.getValueAt(selectedRow, 0).toString(); // Order Item ID
        String orderId = model.getValueAt(selectedRow, 1).toString();     // Order ID
        String orderItem = model.getValueAt(selectedRow, 4).toString();   // Item Name
        boolean updated = false;
        String assignedRunner = "";

        for (Order order : orders) {
            if (order.orderItemId.equals(orderItemId)) {
                order.updateStatus(newStatus);
                sendNotification(order.customerName, "Order Item '" + orderItem + "' " + newStatus, getCurrentDate(), getCurrentTime());
                updated = true;
                break;
            }
        }

        if (updated) {
            String orderItemFile = Paths.get("platezies_txtfiles/OrderItem.txt").toAbsolutePath().toString();
    
    
            saveOrdersToFile(orderItemFile);

            // **Check if the order is a delivery & notify the rider**
            if (newStatus.equalsIgnoreCase("rejected")) {
                assignedRunner = getAssignedRunner(orderId);
                if (!assignedRunner.isEmpty()) {
                    sendNotification(assignedRunner, "Order Item '" + orderItem + "' for Order " + orderId + " has been rejected.", getCurrentDate(), getCurrentTime());
                }
                updateOrderStatusIfAllRejected(orderId);
            }

            refreshTableModel();
            JOptionPane.showMessageDialog(null, "Order item '" + orderItem + "' updated to " + newStatus + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Selected order item not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Please select an order item to " + newStatus + ".", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private static String getAssignedRunner(String orderId) {
    
    String taskFile = Paths.get("platezies_txtfiles/tasks.txt").toAbsolutePath().toString();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] taskDetails = line.split(",");
            if (taskDetails.length >= 5 && taskDetails[0].equals(orderId)) { 
                return taskDetails[4];  // Return assigned runner's username
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return ""; // Return empty if no runner is assigned
}







    
    private static void refreshTableModel() {
    model.setRowCount(0);  // Clear the table model
    for (Order order : orders) {
        if (order.cuisine.equals(currentCuisine) && order.status.equals("pending")) {
            model.addRow(new Object[]{
                order.orderItemId, order.orderId, order.customerName, order.cuisine, 
                order.orderItem, order.orderType, order.cost, order.price, order.status, order.orderDate
            });
        }
    }
    System.out.println("Total orders in table: " + model.getRowCount()); // Debug: print row count
}


    
     private static void sendNotification(String recipient, String title, String date, String time) {
        String FilePath = Paths.get("platezies_txtfiles/notifications.txt").toAbsolutePath().toString();
    
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FilePath, true))) {
            writer.write(recipient + "," + title + "," + date + "," + time);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    private static String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(new Date());
    }
    
    private static void loadOrdersFromFile(String filename) {
    orders.clear(); // Clear the existing list to avoid duplicates
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            System.out.println("Line has " + data.length + " fields: " + line);  // Debugging line count
            if (data.length == 10) {  // Ensure we have 9 fields
                orders.add(new Order(data[0], data[1], data[2], data[3], data[4], data[5], 
                                      Double.parseDouble(data[6]), Double.parseDouble(data[7]), data[8], data[9]));
                System.out.println("Loaded order: " + data[1]); // Debug: print order ID
            } else {
                System.out.println("Skipped invalid line: " + line);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

}


    
    private static void saveOrdersToFile(String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        
        System.out.println("Saving to file: " + filename);
        
        for (Order order : orders) {
            // Debug: Check if orderItemId is correctly included
            System.out.println("Saving order with Item ID: " + order.orderItemId);

            // Make sure all the fields are written correctly
            writer.write(order.orderItemId + "," + order.orderId + "," + order.customerName + "," + order.cuisine + "," +
                         order.orderItem + "," + order.orderType + "," + order.cost + "," + order.price + "," +
                         order.status + "," + order.orderDate);
            writer.newLine();  // Ensure each order is on a new line
        }
        
        System.out.println("File successfully written.");
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    
    private static void updateOrderStatusIfAllRejected(String orderId) {
    String orderItemFile = Paths.get("platezies_txtfiles/OrderItem.txt").toAbsolutePath().toString();
    String orderFile = Paths.get("platezies_txtfiles/Orders.txt").toAbsolutePath().toString();
    String taskFile = Paths.get("platezies_txtfiles/tasks.txt").toAbsolutePath().toString();
    

    boolean allRejected = true;
    String orderType = "";
    String assignedRunner = "";
    List<String> orderLines = new ArrayList<>();
    List<String> taskLines = new ArrayList<>();

    // **Step 1: Check if all items in OrderItem.txt are rejected**
    try (BufferedReader reader = new BufferedReader(new FileReader(orderItemFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 9 && data[1].equals(orderId)) {  // Match Order ID
                if (!data[8].equalsIgnoreCase("rejected")) {  // Status is NOT "rejected"
                    allRejected = false;
                    break;
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    if (allRejected) {
        // **Step 2: Read and update OrderS.txt (Cancel the order)**
        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7 && data[0].equals(orderId)) {
                    data[5] = "cancelled";  // Update status to "cancelled"
                    orderType = data[2];   // Get order type (e.g., "delivery")
                    line = String.join(",", data);
                }
                orderLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // **Step 3: Write updated data back to OrderS.txt**
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(orderFile))) {
            for (String orderLine : orderLines) {
                writer.write(orderLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // **Step 4: Cancel the corresponding task in tasks.txt**
        try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskDetails = line.split(",");
                if (taskDetails.length >= 5 && taskDetails[0].equals(orderId)) { 
                    taskDetails[3] = "cancelled";  // Update task status to "cancelled"
                    assignedRunner = taskDetails[4]; // Get assigned runner
                    line = String.join(",", taskDetails);
                }
                taskLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // **Step 5: Write updated tasks back to tasks.txt**
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(taskFile))) {
            for (String taskLine : taskLines) {
                writer.write(taskLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // **Step 6: Notify the runner if it's a delivery order**
        if (orderType.equalsIgnoreCase("delivery") && !assignedRunner.isEmpty()) {
            sendNotification(assignedRunner, "Order " + orderId + " has been cancelled.", getCurrentDate(), getCurrentTime());
        }
    }
}




}
