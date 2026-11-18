package platezies;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableCellRenderer;

public class MenuApp {
    private DefaultTableModel tableModel;
    private JTable menuTable;
    String MENU_FILE = Paths.get("platezies_txtfiles/menu.txt").toAbsolutePath().toString();
    String IMAGE_FOLDER = Paths.get("Images").toAbsolutePath().toString();


    private static final Map<String, String> cuisineToUserMap = new HashMap<>();
    private final String currentUser;
    private final String currentCuisine;

    public MenuApp(Vendor vendor) {
        this.currentUser = vendor.getUserEmail();
        this.currentCuisine = vendor.getCuisine();
        loadCuisineMapping();

        JFrame frame = new JFrame("Menu Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Cuisine", "Item Name", "Cost", "Price", "Image"}, 0);
        menuTable = new JTable(tableModel);
        menuTable.setRowHeight(60);
        
        menuTable.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer());
        
        loadMenuFromFile();
        
        
                   
        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(e -> addMenuItem());
        addButton.setFont(new java.awt.Font("Impact", 0, 14));
        
        JButton editButton = new JButton("Edit Item");
        editButton.addActionListener(e -> editMenuItem());
        editButton.setFont(new java.awt.Font("Impact", 0, 14));
        
        JButton deleteButton = new JButton("Delete Item");
        deleteButton.addActionListener(e -> deleteMenuItem());
        deleteButton.setFont(new java.awt.Font("Impact", 0, 14));

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new java.awt.Font("Impact", 0, 14));
        
        exitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vendorHome(vendor).setVisible(true);  // Open vendor home
            }
        });
        ((JFrame) SwingUtilities.getWindowAncestor(exitButton)).dispose(); // Close current frame
    }
});
            

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);

        frame.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private HashMap<String, String> cuisineMap = new HashMap<>();

    private void loadCuisineMapping() {
    String Vendorfile = Paths.get("platezies_txtfiles/vendor.txt").toAbsolutePath().toString();  
    File file = new File(Vendorfile);
    if (file.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) { // Ensure full user details exist
                    String userId = parts[2].trim();
                    String cuisine = parts[5].trim(); // Cuisine is at index 5
                    cuisineMap.put(userId, cuisine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



   private void addMenuItem() {
    String cuisine = cuisineMap.getOrDefault(currentUser, "Unknown"); // Get vendor's cuisine

    String name = JOptionPane.showInputDialog("Enter item name:");
    if (name == null || name.trim().isEmpty()) return;
    String costStr = JOptionPane.showInputDialog("Enter cost:");
    if (costStr == null || costStr.trim().isEmpty()) return;
    String priceStr = JOptionPane.showInputDialog("Enter price:");
    if (priceStr == null || priceStr.trim().isEmpty()) return;

    double cost;
    try {
       cost = Double.parseDouble(costStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid cost. Please enter a numeric value.");
        return;
    }
    
    double price;
    try {
        price = Double.parseDouble(priceStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid price. Please enter a numeric value.");
        return;
    }

    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(null);
    String newImagePath = "null"; // Default

    if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        newImagePath = "Images" + "\\" + selectedFile.getName();
        try {
            Files.copy(selectedFile.toPath(), new File(newImagePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save new menu item
    saveMenuToFile(cuisine, name, cost, price, newImagePath);

    // Update table
    tableModel.addRow(new Object[]{cuisine, name, String.format("$%.2f", cost),String.format("$%.2f", price), newImagePath});
}



    private void editMenuItem() {
    int selectedRow = menuTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Please select an item to edit.");
        return;
    }

    String cuisine = tableModel.getValueAt(selectedRow, 0).toString();
    String vendorCuisine = cuisineMap.getOrDefault(currentUser, "Unknown");

    // Vendors can only edit their own cuisine items
    if (!cuisine.equalsIgnoreCase(vendorCuisine)) {
        JOptionPane.showMessageDialog(null, "You can only edit items from your assigned cuisine.");
        return;
    }

    String name = JOptionPane.showInputDialog("Enter new item name:", tableModel.getValueAt(selectedRow, 1));
    if (name == null || name.trim().isEmpty()) return;
    
    String costStr = JOptionPane.showInputDialog("Enter new price:", tableModel.getValueAt(selectedRow, 2).toString().replace("$", ""));
    if (costStr == null || costStr.trim().isEmpty()) return;
    
    String priceStr = JOptionPane.showInputDialog("Enter new price:", tableModel.getValueAt(selectedRow, 3).toString().replace("$", ""));
    if (priceStr == null || priceStr.trim().isEmpty()) return;
    
    double cost;
    try {
        cost = Double.parseDouble(costStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid cost. Please enter a numeric value.");
        return;
    }
    
    double price;
    try {
        price = Double.parseDouble(priceStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid price. Please enter a numeric value.");
        return;
    }

    JFileChooser fileChooser = new JFileChooser();
int returnValue = fileChooser.showOpenDialog(null);
String newImagePath = tableModel.getValueAt(selectedRow, 4).toString();

if (returnValue == JFileChooser.APPROVE_OPTION) {
    File selectedFile = fileChooser.getSelectedFile();
    String newImageFileName = selectedFile.getName(); // Only filename
    newImagePath = "Images" + File.separator + newImageFileName; // Relative path

    try {
        Files.copy(selectedFile.toPath(), Paths.get(newImagePath), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }
}


    // Update table values
    tableModel.setValueAt(name, selectedRow, 1);
    tableModel.setValueAt(String.format("$%.2f", cost), selectedRow, 2);
    tableModel.setValueAt(String.format("$%.2f", price), selectedRow, 3);
    tableModel.setValueAt(newImagePath, selectedRow, 4);

    // Save updated menu
    deleteUserDataFromFile();
    appendUserDataToFile();
}





    
    private void loadMenuFromFile() {
    File file = new File(MENU_FILE);
    if (file.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String userCuisine = cuisineMap.getOrDefault(currentUser, "Unknown");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String cuisine = parts[0].trim();
                    String name = parts[1].trim();
                    double cost = Double.parseDouble(parts[2].trim());
                    double price = Double.parseDouble(parts[3].trim());
                    String imagePath = (parts.length > 4) ? parts[4].trim() : "null";

                    // Only load menu items that match the vendor's cuisine
                    if (cuisine.equalsIgnoreCase(userCuisine)) {
                        tableModel.addRow(new Object[]{
                            cuisine, name, String.format("$%.2f", cost), String.format("$%.2f", price), imagePath
                        });
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



    private void deleteMenuItem() {
    int selectedRow = menuTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Please select an item to delete.");
        return;
    }

    String cuisine = tableModel.getValueAt(selectedRow, 0).toString();
    
    // Get the vendor's assigned cuisine from user mapping
    String vendorCuisine = cuisineMap.getOrDefault(currentUser, "Unknown");
    
    // Vendors can only delete items from their own cuisine
    if (!cuisine.equalsIgnoreCase(vendorCuisine)) {
        JOptionPane.showMessageDialog(null, "You can only delete items from your assigned cuisine.");
        return;
    }

    // Remove item from table
    tableModel.removeRow(selectedRow);

    // Update the file after deletion
    deleteUserDataFromFile();
    appendUserDataToFile();
}


    private void deleteUserDataFromFile() {
    File file = new File(MENU_FILE);
    if (!file.exists()) {
        return;
    }

    File tempFile = new File(MENU_FILE + ".tmp");
    try (BufferedReader reader = new BufferedReader(new FileReader(file));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

        String line;
        String userCuisine = cuisineMap.getOrDefault(currentUser, "Unknown"); // Get vendor's cuisine

        while ((line = reader.readLine()) != null) {
            MenuItem menuItem = MenuItem.fromString(line);
            if (menuItem != null && !menuItem.getCuisine().equals(userCuisine)) { 
                writer.write(menuItem.toString());
                writer.newLine();
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    if (!file.delete()) {
        System.err.println("Failed to delete original menu file.");
        return;
    }
    if (!tempFile.renameTo(file)) {
        System.err.println("Failed to rename temporary menu file.");
    }
}


private void appendUserDataToFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(MENU_FILE, true))) {
        String userCuisine = cuisineMap.getOrDefault(currentUser, "Unknown"); // Get vendor's cuisine

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String cuisine = tableModel.getValueAt(i, 0).toString();
            if (cuisine.equals(userCuisine)) {  // Ensure only this vendor's items are saved
                String name = tableModel.getValueAt(i, 1).toString();
                double cost = Double.parseDouble(tableModel.getValueAt(i, 2).toString().replace("$", ""));
                double price = Double.parseDouble(tableModel.getValueAt(i, 3).toString().replace("$", ""));
                String imagePath = tableModel.getValueAt(i, 4).toString();

                // Ensure MenuItem matches new structure
                MenuItem menuItem = new MenuItem(userCuisine, name, cost, price, imagePath);
                writer.write(menuItem.toString());
                writer.newLine();
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void saveMenuToFile(String cuisine, String name, double cost,double price, String imagePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(MENU_FILE, true))) {
        writer.write(cuisine + "," + name + "," + cost + "," + price + "," + imagePath);
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

  
    

    // Custom renderer for displaying images in the JTable
    private static class ImageRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        if (value != null && value.toString().trim().length() > 0 && !value.toString().equalsIgnoreCase("null")) {
            File imgFile = new File(value.toString());
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(value.toString());
                Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
            } else {
                label.setText("Image not found");
            }
        } else {
            label.setText("No image");
        }
        return label;
    }
}

}
