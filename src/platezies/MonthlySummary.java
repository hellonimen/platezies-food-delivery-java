package platezies;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonthlySummary {
    String ORDER_FILE = Paths.get("platezies_txtfiles/OrderItem.txt").toAbsolutePath().toString();
    String REVIEW_FILE = Paths.get("platezies_txtfiles/reviews.txt").toAbsolutePath().toString();
    String REVENUE_FILE = Paths.get("platezies_txtfiles/revenue.txt").toAbsolutePath().toString();
    
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private String currentCuisine;

    public MonthlySummary(String currentCuisine) {
        this.currentCuisine = currentCuisine;
    }

    public void createAndShowGUI(Vendor vendor) {
        this.currentCuisine = vendor.getCuisine();

        JFrame frame = new JFrame("Monthly Summary");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel(new BorderLayout());

        // Dropdowns
        JPanel filterPanel = new JPanel();
        JLabel monthLabel = new JLabel("Select Month:");
        JComboBox<String> monthDropdown = new JComboBox<>(new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"});
        JLabel yearLabel = new JLabel("Select Year:");
        JComboBox<String> yearDropdown = new JComboBox<>(new String[]{"2024", "2025", "2026"});
        JButton calculateButton = new JButton("Calculate");
        filterPanel.add(monthLabel);
        filterPanel.add(monthDropdown);
        filterPanel.add(yearLabel);
        filterPanel.add(yearDropdown);
        filterPanel.add(calculateButton);

        // Table setup
        String[] columnNames = {"Revenue", "Profit", "Avg Rating", "Month", "Year"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons
        JButton publishButton = new JButton("Publish");
        JButton exitButton = new JButton("Exit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(publishButton);
        buttonPanel.add(exitButton);

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);

        // Calculate button action
        calculateButton.addActionListener((ActionEvent e) -> {
            String selectedMonth = (String) monthDropdown.getSelectedItem();
            String selectedYear = (String) yearDropdown.getSelectedItem();

            double[] revenueProfit = calculateRevenueAndProfit(selectedMonth, selectedYear);
            double avgRating = 0;
            try {
                avgRating = calculateAverageRating(selectedMonth, selectedYear);
            } catch (ParseException ex) {
                Logger.getLogger(MonthlySummary.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Clear table before adding new data
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{
                    String.format("$%.2f", revenueProfit[0]),
                    String.format("$%.2f", revenueProfit[1]),
                    String.format("%.1f", avgRating),
                    selectedMonth,
                    selectedYear
            });
        });

        // Publish button action
        publishButton.addActionListener((ActionEvent e) -> {
            if (tableModel.getRowCount() > 0) {
                String selectedMonth = (String) monthDropdown.getSelectedItem();
                String selectedYear = (String) yearDropdown.getSelectedItem();

                double revenue = Double.parseDouble(tableModel.getValueAt(0, 0).toString().replace("$", ""));
                double profit = Double.parseDouble(tableModel.getValueAt(0, 1).toString().replace("$", ""));
                double avgRating = Double.parseDouble(tableModel.getValueAt(0, 2).toString());

                if (!isAlreadyPublished(vendor.getUserEmail(), selectedMonth, selectedYear)) {
                    publishRevenue(vendor, revenue, profit, avgRating, selectedMonth, selectedYear);
                    JOptionPane.showMessageDialog(frame, "Revenue report published successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Revenue report for this month/year is already published.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please calculate revenue before publishing.");
            }
        });

        // Exit button action
        exitButton.addActionListener((ActionEvent e) -> {
            frame.dispose();
            SwingUtilities.invokeLater(() -> new vendorHome(vendor).setVisible(true));
        });

        frame.setVisible(true);
    }

    private double[] calculateRevenueAndProfit(String month, String year) {
        double totalRevenue = 0.0;
        double totalCost = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[3].equals(currentCuisine) && data.length == 10 && data[8].equals("completed")) {
                    Date orderDate = dateFormat.parse(data[9]);
                    if (matchesMonthYear(orderDate, month, year)) {
                        totalRevenue += Double.parseDouble(data[7]);
                        totalCost += Double.parseDouble(data[6]);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return new double[]{totalRevenue, totalRevenue - totalCost};
    }

    private double calculateAverageRating(String month, String year) throws ParseException {
        int totalRating = 0;
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(REVIEW_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {
                    Date reviewDate = dateFormat.parse(data[7]);
                    String cuisine = data[3];

                    if (matchesMonthYear(reviewDate, month, year) && cuisine.equalsIgnoreCase(currentCuisine)) {
                        int rating = Integer.parseInt(data[5]);
                        totalRating += rating;
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count > 0 ? (double) totalRating / count : 0.0;
    }

    private boolean isAlreadyPublished(String userEmail, String month, String year) {
        try (BufferedReader reader = new BufferedReader(new FileReader(REVENUE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8 && data[0].equals(userEmail) && data[6].equals(month) && data[7].equals(year)) {
                    return true; // Record already exists
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void publishRevenue(Vendor user, double revenue, double profit, double avgRating, String month, String year) {
        try (FileWriter writer = new FileWriter(REVENUE_FILE, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            bufferedWriter.write(String.format("%s,%s,%s,%.2f,%.2f,%.1f,%s,%s%n",
                    user.getUserEmail(), user.getUsername(), user.getPhone(), revenue, profit, avgRating, month, year));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean matchesMonthYear(Date date, String month, String year) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        return monthFormat.format(date).equals(month) && yearFormat.format(date).equals(year);
    }
}
