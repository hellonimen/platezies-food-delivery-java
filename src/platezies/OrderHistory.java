



package platezies;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class OrderHistory {
    private static java.util.List<Order> orders = new java.util.ArrayList<>();
    private static String currentCuisine;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static void createAndShowGUI(Vendor user) {
        OrderHistory.currentCuisine = user.getCuisine();
        String FILE_PATH = ("platezies_txtfiles/OrderItem.txt");
        String filePath = Paths.get(FILE_PATH).toAbsolutePath().toString();
         
        loadOrdersFromFile(filePath);

        JFrame frame = new JFrame("Order Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);

        String[] columnNames = {"Order Item ID", "Order ID", "Username", "Cuisine", "Item",  "Type", "Cost", "Price", "Status", "Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Populate Table with Completed Orders
        for (Order order : orders) {
            if (order.cuisine.equals(currentCuisine) && order.status.equals("completed")) {
                model.addRow(new Object[]{order.orderItemId, order.orderId, order.customerName, order.cuisine, order.orderItem,  order.orderType, order.cost, order.price, order.status, order.orderDate});
            }
        }
        
        // Create Dropdown for Filtering
        JPanel controlPanel = new JPanel();
        String[] filterOptions = {"Daily", "Monthly", "Quarterly","Annually"};
        JComboBox<String> filterDropdown = new JComboBox<>(filterOptions);
        filterDropdown.setFont(new java.awt.Font("Impact", 0, 14));
        JLabel revenueLabel = new JLabel("Revenue: $0.00");
        revenueLabel.setFont(new java.awt.Font("Impact", 0, 14));
        JLabel profitLabel = new JLabel("Profit: $0.00");
        profitLabel.setFont(new java.awt.Font("Impact", 0, 14));
        JButton calculateButton = new JButton("Calculate Revenue");
        calculateButton.setFont(new java.awt.Font("Impact", 0, 14));
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new java.awt.Font("Impact", 0, 14));

        controlPanel.add(new JLabel("Filter by:"));
        controlPanel.add(filterDropdown);
        controlPanel.add(calculateButton);
        controlPanel.add(revenueLabel);
        controlPanel.add(profitLabel);
        controlPanel.add(exitButton, BorderLayout.EAST);        
        // Add a new JLabel for Profit


// Add the profit label to the control panel


// Modify the calculateButton action listener to update profit as well
calculateButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedFilter = (String) filterDropdown.getSelectedItem();
        double[] revenueAndProfit = calculateRevenueAndProfit(selectedFilter, model);
        revenueLabel.setText(String.format("Revenue: $%.2f", revenueAndProfit[0]));
        profitLabel.setText(String.format("Profit: $%.2f", revenueAndProfit[1]));
    }
});

        frame.add(controlPanel, BorderLayout.SOUTH);

        
        

        // Exit Button Action
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Returning to home screen...");
            SwingUtilities.invokeLater(() -> new vendorHome(user).setVisible(true));
            frame.dispose();
        });

        frame.setVisible(true);
    }

    private static void loadOrdersFromFile(String filename) {
        orders.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 10) {
                    orders.add(new Order(data[0], data[1], data[2], data[3], data[4], data[5], 
                                         Double.parseDouble(data[6]), Double.parseDouble(data[7]), data[8], data[9]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Modify the calculateRevenue method to pass the model


// Add the missing matchesFilter method
private static boolean matchesFilter(Order order, String filterType, Date currentDate) {
    try {
        Date orderDate = dateFormat.parse(order.orderDate);
        switch (filterType) {
            case "Daily":
                return isSameDay(orderDate, currentDate);
            case "Monthly":
                return isSameMonth(orderDate, currentDate);
            case "Quarterly":
                return isSameQuarter(orderDate, currentDate);
            case "Annually":
                return isSameYear(orderDate, currentDate);
            default:
                return false;
        }
    } catch (ParseException e) {
        e.printStackTrace();
        return false;
    }
}


    private static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dayFormat.format(date1).equals(dayFormat.format(date2));
    }

    private static boolean isSameMonth(Date date1, Date date2) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM-yyyy");
        return monthFormat.format(date1).equals(monthFormat.format(date2));
    }

    private static boolean isSameQuarter(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        int quarter1 = (cal1.get(Calendar.MONTH) / 3) + 1;
        int quarter2 = (cal2.get(Calendar.MONTH) / 3) + 1;

        return year1 == year2 && quarter1 == quarter2;
    }
    
    private static boolean isSameYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    private static double[] calculateRevenueAndProfit(String filterType, DefaultTableModel model) {
    Date currentDate = new Date();
    double totalRevenue = 0.0;
    double totalCost = 0.0;

    model.setRowCount(0); // Clear existing rows

    for (Order order : orders) {
        if (matchesFilter(order, filterType, currentDate) && order.status.equals("completed") && order.cuisine.equals(currentCuisine)) {
            model.addRow(new Object[]{order.orderItemId, order.orderId, order.customerName, order.cuisine, 
                                      order.orderItem, order.orderType, order.cost, order.price, 
                                      order.status, order.orderDate});
            try {
                Date orderDate = dateFormat.parse(order.orderDate);
                if (filterType.equals("Daily") && isSameDay(orderDate, currentDate)) {
                    totalRevenue += order.price;
                    totalCost += order.cost;
                } else if (filterType.equals("Monthly") && isSameMonth(orderDate, currentDate)) {
                    totalRevenue += order.price;
                    totalCost += order.cost;
                } else if (filterType.equals("Quarterly") && isSameQuarter(orderDate, currentDate)) {
                    totalRevenue += order.price;
                    totalCost += order.cost;
                } else if (filterType.equals("Annually") && isSameYear(orderDate, currentDate)) {
                    totalRevenue += order.price;
                    totalCost += order.cost;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    double totalProfit = totalRevenue - totalCost;
    return new double[]{totalRevenue, totalProfit};
}

}
