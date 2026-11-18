/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package platezies;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

;

/**
 *
 */
public class manageNotifsVendor extends javax.swing.JFrame {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private List<Notification> allNotifications = new ArrayList<>();
    private Vendor user;
    private DefaultTableModel model = new DefaultTableModel();
    String filePath = Paths.get(FILE_PATH).toAbsolutePath().toString();
    private static final String FILE_PATH = "platezies_txtfiles/notifications.txt";
    private final String[] columnNames = {"To", "Title","Date", "Time"};
    private int selectedRow = -1;
    /**
     * Creates new form manageNotifsVendor
     * @param user
     */
    public manageNotifsVendor(Vendor user) {
        this.user = user;
        model.setColumnIdentifiers(columnNames);
        initComponents();
        loadAppointmentsFromFile();
        jTable1.setDefaultEditor(Object.class, null); // Disable cell editing
    }
    
    private void loadAppointmentsFromFile() {
    allNotifications.clear();
    model.setRowCount(0); // Clear the table before adding new data

    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
        String line;
        while ((line = br.readLine()) != null) {
            Notification notification = Notification.fromCsv(line);
            if (notification.getRecipient().equals(user.getUsername())) {
                allNotifications.add(notification);
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading notifications.", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    filterNotifications(); // Apply default filter
}

    private void filterNotifications() {
    String filterOption = jComboBox1.getSelectedItem().toString();
    model.setRowCount(0); // Clear the table before applying the filter

    LocalDate today = LocalDate.now();
    LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
    LocalDate endOfWeek = today.with(java.time.DayOfWeek.SUNDAY);

    for (Notification notification : allNotifications) {
        try {
            LocalDate notifDate = LocalDate.parse(notification.getDate(), DATE_FORMATTER);

            boolean shouldDisplay = switch (filterOption) {
                case "Today" -> notifDate.isEqual(today);
                case "This Week" -> !notifDate.isBefore(startOfWeek) && !notifDate.isAfter(endOfWeek);
                case "This Month" -> notifDate.getMonth() == today.getMonth() && notifDate.getYear() == today.getYear();
                case "This Year" -> notifDate.getYear() == today.getYear();
                default -> true;
            };

            if (shouldDisplay) {
                model.addRow(new Object[]{
                    notification.getRecipient(),
                    notification.getTitle(),
                    notification.getDate(),
                    notification.getTime()
                });
            }
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + notification.getDate());
        }
    }
}




    
    private void updateFileContent() {
    List<String> allLines = new ArrayList<>();
    
    // Step 1: Read existing file contents
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
        String line;
        while ((line = br.readLine()) != null) {
            Notification notification = Notification.fromCsv(line);
            // Keep only notifications that do NOT belong to the current user
            if (notification != null && !notification.getRecipient().equals(user.getUsername())) {
                allLines.add(line);
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading file.", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    
    // Step 2: Append updated notifications for the current user
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) { // Overwrite file
        // Write back old notifications (excluding the current user's)
        for (String line : allLines) {
            bw.write(line);
            bw.newLine();
        }
        // Write updated notifications for the current user
        for (int i = 0; i < model.getRowCount(); i++) {
            Notification notification = new Notification(
                model.getValueAt(i, 0).toString(),
                model.getValueAt(i, 1).toString(),
                model.getValueAt(i, 2).toString(),
                model.getValueAt(i, 3).toString()
            );
            bw.write(notification.toCsv());
            bw.newLine();
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error writing to file.", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}


    private void deleteSelectedNotification() {
    int selectedRow = jTable1.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        return;
    }

    int confirmation = JOptionPane.showConfirmDialog(
        this, "Are you sure you want to delete this notification?", "Confirm Deletion",
        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
    );

    if (confirmation == JOptionPane.YES_OPTION) {
        model.removeRow(selectedRow);
        updateFileContent(); // Ensure only necessary lines are updated
        JOptionPane.showMessageDialog(this, "Notification deleted successfully.");
    }
}


    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(model);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 375, 275));

        jButton1.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jButton1.setText("Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, -1, -1));

        jButton2.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, -1, -1));

        jComboBox1.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Today", "This Week", "This Month", "This Year" }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, -1, -1));

        jButton3.setText("Home");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, -1, -1));

        jButton4.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jButton4.setText("Filter");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon("Images/Platezies logo.jpg"));
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon("Images/Platezies background.png"));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                new vendorHome(user).setVisible(true);
                            }
                        });
                        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        deleteSelectedNotification();
    
    
    
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        // TODO add your handling code here:
        selectedRow = jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1MouseReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        filterNotifications();
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
