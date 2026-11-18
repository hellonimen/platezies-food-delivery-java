package platezies;


import platezies.AdminMainMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EditCustomer extends javax.swing.JFrame {

    public EditCustomer() {
        initComponents();
        loadCustomerData();
        addTableMouseListener();
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfusername = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfemail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfphone = new javax.swing.JTextField();
        btncreate = new javax.swing.JButton();
        btnclear = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        tfpassword = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tfwalletbal = new javax.swing.JTextField();
        btnmain = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCustomer = new javax.swing.JTable();
        btnupdate = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("Images/user.png"));
        jLabel1.setText("List of Customers");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Customer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 14)))); // NOI18N
        jPanel2.setOpaque(false);

        jLabel2.setText("Name");

        jLabel3.setText("Username");

        jLabel4.setText("Email");

        tfemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfemailActionPerformed(evt);
            }
        });

        jLabel5.setText("Phone");

        tfphone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfphoneActionPerformed(evt);
            }
        });

        btncreate.setIcon(new javax.swing.ImageIcon("Images/add.png"));
        btncreate.setText("Create");
        btncreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncreateActionPerformed(evt);
            }
        });

        btnclear.setIcon(new javax.swing.ImageIcon("Images/clear.png"));
        btnclear.setText("Clear");
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        jLabel6.setText("Password");

        tfpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfpasswordActionPerformed(evt);
            }
        });

        jLabel8.setText("eWallet Balance");

        tfwalletbal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfwalletbalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tfname)
            .addComponent(tfemail)
            .addComponent(tfusername)
            .addComponent(tfphone)
            .addComponent(tfpassword)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btncreate, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                        .addGap(19, 19, 19)
                        .addComponent(btnclear, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(tfwalletbal)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfwalletbal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncreate)
                    .addComponent(btnclear))
                .addGap(30, 30, 30))
        );

        btnmain.setBackground(new java.awt.Color(255, 204, 102));
        btnmain.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnmain.setIcon(new javax.swing.ImageIcon("Images/home.png"));
        btnmain.setText("MAIN MENU");
        btnmain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmainActionPerformed(evt);
            }
        });

        btndelete.setIcon(new javax.swing.ImageIcon("Images/delete.png"));
        btndelete.setText("Delete");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        tableCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Username", "Email", "Phone", "Password","eWallet Balance"
            }
        ));
        jScrollPane1.setViewportView(tableCustomer);

        btnupdate.setIcon(new javax.swing.ImageIcon("Images/update.png"));
        btnupdate.setText("Update");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon("Images/logo small.jpg"));
        jLabel7.setText("jLabel7");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(58, 58, 58))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(131, 131, 131)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnmain, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnupdate)
                        .addGap(6, 6, 6)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnmain))
                    .addComponent(btndelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfphoneActionPerformed
    private void loadCustomerData() {
        DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
        model.setRowCount(0);

        try (BufferedReader reader = new BufferedReader(new FileReader("platezies_txtfiles/customer.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                                          "Error loading data from file!", 
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        // TODO add your handling code here:
        tfname.setText("");
        tfusername.setText("");
        tfemail.setText("");
        tfphone.setText(""); 
        tfpassword.setText("");
        tfwalletbal.setText("");
    }//GEN-LAST:event_btnclearActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
         int row = tableCustomer.getSelectedRow();

            if (row < 0){
                    JOptionPane.showMessageDialog(this,
                                                   "No row is selected! Please select one row",
                                                   "Select row",
                                                   JOptionPane.ERROR_MESSAGE);
            } else {
                DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
                model.removeRow(row);
                try {
                    saveUpdatedDataToFile();
                } catch (IOException ex) {
                    Logger.getLogger(EditVendor.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(this, 
                                              "Customer deleted successfully!", 
                                              "Success",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btncreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncreateActionPerformed
        // TODO add your handling code here:
        if (!validateInputs()) {
        return; // Stop execution if validation fails
    }

    String username = tfusername.getText().trim();
    String email = tfemail.getText().trim();

    // Check for duplicate user (pass -1 for new entries)
    if (isDuplicateUser(username, email, -1)) {  
        JOptionPane.showMessageDialog(this, 
                                      "User with this username or email already exists!",
                                      "Duplicate Entry",
                                      JOptionPane.ERROR_MESSAGE);
        return; // Stop execution if user already exists
    }

    try {
        // Save data if validation and duplicate check pass
        saveDataToFile();
        JOptionPane.showMessageDialog(this, 
                                      "Customer created successfully!", 
                                      "Success",
                                      JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, 
                                      "Error saving data! Please try again.",
                                      "File Error",
                                      JOptionPane.ERROR_MESSAGE);
        Logger.getLogger(EditVendor.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
 

    }//GEN-LAST:event_btncreateActionPerformed

    private void tfpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfpasswordActionPerformed

    private void tfemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfemailActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        int row = tableCustomer.getSelectedRow();
    
    if (row < 0) {
        JOptionPane.showMessageDialog(this,
                                      "No row is selected! Please select one row", 
                                      "Select row",
                                      JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validate inputs
    if (!validateInputs()) {
        return; // Stop execution if validation fails
    }

    String username = tfusername.getText().trim();
    String email = tfemail.getText().trim();

    // Check for duplicate user (excluding the current row)
    if (isDuplicateUser(username, email, row)) {
        JOptionPane.showMessageDialog(this, 
                                      "User with this username or email already exists!",
                                      "Duplicate Entry",
                                      JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Update table model
    DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
    model.setValueAt(tfname.getText().trim(), row, 0);
    model.setValueAt(username, row, 1);
    model.setValueAt(email, row, 2);
    model.setValueAt(tfphone.getText().trim(), row, 3);
    model.setValueAt(tfpassword.getText().trim(), row, 4);
    model.setValueAt(tfwalletbal.getText().trim(), row, 5);

    // Save the updated table data to file
    try {
        saveUpdatedDataToFile();
        JOptionPane.showMessageDialog(this, 
                                      "Runner updated successfully!", 
                                      "Success",
                                      JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, 
                                      "Error saving data! Please try again.",
                                      "File Error",
                                      JOptionPane.ERROR_MESSAGE);
        Logger.getLogger(EditVendor.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnmainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmainActionPerformed
        // TODO add your handling code here:
        AdminMainMenu adminMenuFrame = new AdminMainMenu();
        adminMenuFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnmainActionPerformed

    private void tfwalletbalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfwalletbalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfwalletbalActionPerformed
    
    private void saveUpdatedDataToFile() throws IOException {
    String filePath = Paths.get("platezies_txtfiles/customer.txt").toAbsolutePath().toString();
    File vendorFile = new File(filePath);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(vendorFile))) {
        DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            writer.write(model.getValueAt(i, 0) + "," + // Name
                         model.getValueAt(i, 1) + "," + // Username
                         model.getValueAt(i, 2) + "," + // Email
                         model.getValueAt(i, 3) + "," + // Phone
                         model.getValueAt(i, 4) + "," + //Password
                         model.getValueAt(i, 5)); //eWalletBal
            writer.newLine();
        }
    }
}
    
    private boolean validateInputs() {
    String name = tfname.getText().trim();
    String username = tfusername.getText().trim();
    String email = tfemail.getText().trim();
    String phone = tfphone.getText().trim();
    String password = tfpassword.getText().trim();
    String walletBalanceStr = tfwalletbal.getText().trim(); // Assuming this is the text field for eWallet

    // Check for empty fields
    if (name.isEmpty() || username.isEmpty() || email.isEmpty() || 
        phone.isEmpty() || password.isEmpty() || walletBalanceStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
                                      "All fields are required!", 
                                      "Input Error", 
                                      JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Validate email format
    if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
        JOptionPane.showMessageDialog(this, 
                                      "Invalid email! Please include '@' and a valid domain.",
                                      "Input Error",
                                      JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Validate phone (only digits)
    if (!phone.matches("^\\d+$")) {
        JOptionPane.showMessageDialog(this, 
                                      "Invalid phone number! Only digits are allowed.",
                                      "Input Error",
                                      JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Validate eWallet balance (must be a valid double with up to 2 decimal places)
    if (!walletBalanceStr.matches("^\\d+(\\.\\d{1,2})?$")) {
        JOptionPane.showMessageDialog(this, 
                                      "Invalid eWallet balance! Must be a number with up to 2 decimal places.", 
                                      "Input Error", 
                                      JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Convert balance to double
    double walletBalance;
    try {
        walletBalance = Double.parseDouble(walletBalanceStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
                                      "Invalid eWallet balance! Please enter a valid number.", 
                                      "Input Error", 
                                      JOptionPane.ERROR_MESSAGE);
        return false;
    }

    if (walletBalance < 0) {
        JOptionPane.showMessageDialog(this, 
                                      "eWallet balance cannot be negative.", 
                                      "Input Error", 
                                      JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Validate password security
    if (password.length() < 6) {
        JOptionPane.showMessageDialog(this, 
                                      "Password must be at least 6 characters long.", 
                                      "Input Error", 
                                      JOptionPane.ERROR_MESSAGE);
        return false;
    }
    if (!password.matches(".*[A-Z].*")) {
        JOptionPane.showMessageDialog(this, 
                                      "Password must contain at least one uppercase letter.", 
                                      "Input Error", 
                                      JOptionPane.ERROR_MESSAGE);
        return false;
    }
    if (!password.matches(".*[a-z].*")) {
        JOptionPane.showMessageDialog(this, 
                                      "Password must contain at least one lowercase letter.", 
                                      "Input Error", 
                                      JOptionPane.ERROR_MESSAGE);
        return false;
    }
    if (!password.matches(".*\\d.*")) {
        JOptionPane.showMessageDialog(this, 
                                      "Password must contain at least one digit.", 
                                      "Input Error", 
                                      JOptionPane.ERROR_MESSAGE);
        return false;
    }
    if (!password.matches(".*[!@#$%^&*].*")) {
        JOptionPane.showMessageDialog(this, 
                                      "Password must contain at least one special character (e.g. !@#$%^&*).", 
                                      "Input Error", 
                                      JOptionPane.ERROR_MESSAGE);
        return false;
    }

    return true; // Validation passed
}

    
    private boolean isDuplicateUser(String username, String email, int currentRow) {
        DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            if (i == currentRow) continue; // Skip the current row being updated

            String existingUsername = model.getValueAt(i, 1).toString().trim();
            String existingEmail = model.getValueAt(i, 2).toString().trim();

            if (existingUsername.equalsIgnoreCase(username) || existingEmail.equalsIgnoreCase(email)) {
                return true; // Duplicate found
            }
        }
        return false;
    }
    
    private void saveDataToFile() throws IOException {

    String filePath = Paths.get("platezies_txtfiles/customer.txt").toAbsolutePath().toString();
    File vendorFile = new File(filePath);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(vendorFile, true))) {
        writer.write(tfname.getText().trim() + "," +
                     tfusername.getText().trim() + "," +
                     tfemail.getText().trim() + "," +
                     tfphone.getText().trim() + "," +
                     tfpassword.getText().trim() + "," +
                     tfwalletbal.getText().trim());
        writer.newLine();
    }

    DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
        model.addRow(new Object[]{ tfname.getText().trim(), 
                                   tfusername.getText().trim(),
                                   tfemail.getText().trim(), 
                                   tfphone.getText().trim(),
                                   tfpassword.getText().trim(),
                                   tfwalletbal.getText().trim()});
    }
    
    
    private void addTableMouseListener() {
        tableCustomer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = tableCustomer.getSelectedRow();
                if (row >= 0) {
                    tfname.setText((String) tableCustomer.getValueAt(row, 0));
                    tfusername.setText((String) tableCustomer.getValueAt(row, 1));
                    tfemail.setText((String) tableCustomer.getValueAt(row, 2));
                    tfphone.setText((String) tableCustomer.getValueAt(row, 3));
                    tfpassword.setText((String) tableCustomer.getValueAt(row, 4));
                }
            }
        });
    }
    
    
    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btncreate;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnmain;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableCustomer;
    private javax.swing.JTextField tfemail;
    private javax.swing.JTextField tfname;
    private javax.swing.JTextField tfpassword;
    private javax.swing.JTextField tfphone;
    private javax.swing.JTextField tfusername;
    private javax.swing.JTextField tfwalletbal;
    // End of variables declaration//GEN-END:variables
}
