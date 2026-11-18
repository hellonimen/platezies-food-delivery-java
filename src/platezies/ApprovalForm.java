package platezies;

import platezies.AdminMainMenu;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class ApprovalForm extends javax.swing.JFrame {

    private String selectedName = "";
    private String selectedAmount = "";
    private String selectedStatus = "";
    
    /**
     * Creates new form ApprovalForm
     */
    public ApprovalForm() {
        initComponents();
        loadRequests(); 
        addEventListeners();
    }

    private void addEventListeners() {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow != -1) {
                    selectedName = jTable1.getValueAt(selectedRow, 0).toString();
                    selectedAmount = jTable1.getValueAt(selectedRow, 1).toString();
                    selectedStatus = jTable1.getValueAt(selectedRow, 2).toString();
                }
            }
        });

        btnaccept.addActionListener(e -> processApproval());
        btndecline.addActionListener(e -> processRejection());
        btnnotification.addActionListener(e -> sendNotification());
        btninvoice.addActionListener(e -> generateInvoice());
    }
    
    private void loadRequests() {
    String filePath = Paths.get("platezies_txtfiles/topup_requests.txt").toAbsolutePath().toString();
    System.out.println("Loading file from: " + filePath);

    File file = new File(filePath);
    if (!file.exists()) {
        JOptionPane.showMessageDialog(this, "File not found: " + filePath, "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Clear existing table data

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("Read line: " + line); // Debugging line

            // CSV parsing (Email, Amount, Status)
            String[] parts = line.split(",");
            if (parts.length == 3) {
                String email = parts[0].trim();
                String amount = parts[1].trim();
                String status = parts[2].trim();

                System.out.println("Parsed -> Email: " + email + ", Amount: " + amount + ", Status: " + status);
                model.addRow(new Object[]{email, amount, status}); // Add row
            } else {
                System.err.println("Invalid line format: " + line);
            }
        }

        // Force refresh
        SwingUtilities.invokeLater(() -> model.fireTableDataChanged());

    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading requests!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}



    
    private void processApproval() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Do you want to approve this amount?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            jTable1.setValueAt("Approved", selectedRow, 2);
            updateDatabase(selectedName, selectedAmount, "Approved");
        }
    }
    
    private void processRejection() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Do you want to decline this amount?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            jTable1.setValueAt("Declined", selectedRow, 2);
            updateDatabase(selectedName, selectedAmount, "Declined");
        }
    }
    
    private void updateDatabase(String name, String amount, String status) {
        String filePath = Paths.get("platezies_txtfiles/topup_requests.txt").toAbsolutePath().toString();
        File file = new File(filePath);
        
        StringBuilder fileContent = new StringBuilder();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Name: " + name) && line.contains("Amount: RM " + amount)) {
                    fileContent.append("Name: ").append(name).append(", Amount: RM ").append(amount).append(", Status: ").append(status);
                    updated = true;
                } else {
                    fileContent.append(line);
                }
                fileContent.append(System.lineSeparator());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                                          "Error updating the database!", 
                                          "Error", 
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!updated) {
            fileContent.append("Name: ").append(name).append(", Amount: RM ").append(amount).append(", Status: ").append(status);
            fileContent.append(System.lineSeparator());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContent.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                                          "Error saving data!",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendNotification() {
        JOptionPane.showMessageDialog(this, 
                                     "Notification sent to TopUpRequest.java!", 
                                     "Notification", 
                                     JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void generateInvoice() {
    int selectedRow = jTable1.getSelectedRow();
    if (selectedRow != -1) {
        String name = jTable1.getValueAt(selectedRow, 0).toString();
        String amount = jTable1.getValueAt(selectedRow, 1).toString();
        String status = jTable1.getValueAt(selectedRow, 2).toString();

        // Create Invoice Frame
        JFrame invoiceFrame = new JFrame("Invoice");
        invoiceFrame.setSize(400, 300);
        invoiceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        invoiceFrame.setLayout(new BoxLayout(invoiceFrame.getContentPane(), BoxLayout.Y_AXIS));

        // Add Invoice Title
        JLabel titleLabel = new JLabel("INVOICE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Invoice Details
        JLabel nameLabel = new JLabel("Customer: " + name);
        JLabel amountLabel = new JLabel("Amount: RM " + amount);
        JLabel statusLabel = new JLabel("Status: " + status);
        
        // Styling Labels
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        
        // Panel for Centering Content
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(nameLabel);
        panel.add(amountLabel);
        panel.add(statusLabel);
        panel.add(Box.createVerticalStrut(20));
        

        invoiceFrame.add(panel);
        invoiceFrame.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Please select a request to generate an invoice.", "Error", JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnaccept = new javax.swing.JButton();
        btndecline = new javax.swing.JButton();
        btnnotification = new javax.swing.JButton();
        btninvoice = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnmainmenu = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Amount", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Approval Dimension :");

        btnaccept.setIcon(new javax.swing.ImageIcon("Images/accept.png"));
        btnaccept.setText("Accept");

        btndecline.setIcon(new javax.swing.ImageIcon("Images/decline.png"));
        btndecline.setText("Decline");

        btnnotification.setIcon(new javax.swing.ImageIcon("Images/notification.png"));
        btnnotification.setText("Notification");

        btninvoice.setText("Generate Invoice");

        jLabel2.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon("Images/top-up.png"));
        jLabel2.setText("Customer Top-Up Approval");

        btnmainmenu.setBackground(new java.awt.Color(255, 204, 102));
        btnmainmenu.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnmainmenu.setIcon(new javax.swing.ImageIcon("Images/home.png"));
        btnmainmenu.setText("MAIN MENU");
        btnmainmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmainmenuActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon("Images/logo small.jpg"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 134, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnmainmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnnotification)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(btnaccept)
                                            .addGap(18, 18, 18)
                                            .addComponent(btndecline)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btninvoice))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(32, 32, 32)))
                .addComponent(btnnotification)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btninvoice)
                        .addGap(97, 97, 97)
                        .addComponent(btnmainmenu))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnaccept)
                            .addComponent(btndecline))))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnmainmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmainmenuActionPerformed
        // TODO add your handling code here:
        AdminMainMenu adminMenuFrame = new AdminMainMenu();
        adminMenuFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnmainmenuActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnaccept;
    private javax.swing.JButton btndecline;
    private javax.swing.JButton btninvoice;
    private javax.swing.JButton btnmainmenu;
    private javax.swing.JButton btnnotification;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
