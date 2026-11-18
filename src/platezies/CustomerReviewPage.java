/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package platezies;

/**
 *
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CustomerReviewPage {

    public CustomerReviewPage(Runner runner) {
    }
    private static List<CustomerReview> reviews = new ArrayList<>();

    public static void createAndShowGUI(Vendor vendor) {
    String FILE = Paths.get("platezies_txtfiles/reviews.txt").toAbsolutePath().toString();
    loadReviewsFromFile(FILE);

    JFrame frame = new JFrame("Customer Reviews");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(800, 400);

    String[] columnNames = {"Review ID", "Order ID", "Order Item", "Cuisine", "Username", "Star Rating", "Comment","Review Date"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    JTable table = new JTable(model);
    table.setAutoCreateRowSorter(true); // Enable sorting
    JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane, BorderLayout.CENTER);

    // Populate Table with Reviews for the Vendor's Cuisine
    for (CustomerReview review : reviews) {
        if (review.getCuisine().equalsIgnoreCase(vendor.getCuisine())) {
            model.addRow(new Object[]{review.getReviewId(), review.getOrderId(), review.getOrderItem(),
                    review.getCuisine(), review.getUsername(), review.getStarRating(), review.getComment(), review.getreviewDate()});
        }
    }

    // Calculate and display the average rating
    double avgRating = calculateAverageRating(vendor);
    JLabel avgLabel = new JLabel("Average Rating: " + String.format("%.2f", avgRating));
    avgLabel.setFont(new Font("Impact", Font.PLAIN, 14));

    JButton exitButton = new JButton("Exit");
    exitButton.setFont(new Font("Impact", Font.PLAIN, 14));
    exitButton.addActionListener(e -> {
        JOptionPane.showMessageDialog(frame, "Returning to home screen...");
        SwingUtilities.invokeLater(() -> new vendorHome(vendor).setVisible(true));
        frame.dispose();
    });

    // Panel for exit button and average rating
    JPanel controlPanel = new JPanel(new BorderLayout());
    controlPanel.add(avgLabel, BorderLayout.WEST);
    controlPanel.add(exitButton, BorderLayout.EAST);
    frame.add(controlPanel, BorderLayout.SOUTH);

    frame.setVisible(true);
}

    private static void loadReviewsFromFile(String filename) {
        reviews.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {
                    reviews.add(new CustomerReview(data[0], data[1], data[2], data[3], data[4],
                            Integer.parseInt(data[5]), data[6], data[7]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static double calculateAverageRating(Vendor vendor) {
    if (reviews.isEmpty()) {
        return 0.0; // Return 0 if no reviews are available
    }

    double totalStars = 0;
    int count = 0;

    for (CustomerReview review : reviews) {
        if (review.getCuisine().equalsIgnoreCase(vendor.getCuisine())) { // Only count reviews matching the user's cuisine
            totalStars += review.getStarRating();
            count++;
        }
    }

    return count > 0 ? totalStars / count : 0.0; // Avoid division by zero
}


}
