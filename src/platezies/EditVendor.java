package platezies;


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


public class EditVendor extends javax.swing.JFrame {

    public EditVendor() {
        initComponents();
        loadVendorData();
        addTableMouseListener();
 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btncreate = new javax.swing.JButton();
        btnclear = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfname = new javax.swing.JTextField();
        tfusername = new javax.swing.JTextField();
        tfemail = new javax.swing.JTextField();
        tfphone = new javax.swing.JTextField();
        tfpassword = new javax.swing.JTextField();
        tfcuisine = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btndelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVendor = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("Images/vendor.png"));
        jLabel1.setText("List of Vendors");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Vendor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 14))); // NOI18N
        jPanel2.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setText("Name");

        btncreate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btncreate.setIcon(new javax.swing.ImageIcon("Images/add.png"));
        btncreate.setText("Create");
        btncreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncreateActionPerformed(evt);
            }
        });

        btnclear.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btnclear.setIcon(new javax.swing.ImageIcon("Images/clear.png"));
        btnclear.setText("Clear");
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Phone");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("Email");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("Password");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setText("Username");

        tfemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfemailActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel9.setText("Cuisine Type");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btncreate, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(btnclear, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfusername, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfname, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfemail, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfphone, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(tfpassword)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(tfcuisine)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addComponent(tfphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfcuisine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncreate)
                    .addComponent(btnclear))
                .addContainerGap())
        );

        btndelete.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btndelete.setIcon(new javax.swing.ImageIcon("Images/delete.png"));
        btndelete.setText("Delete");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        tableVendor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Name", "Username", "Email", "Phone ", "Password", "Cuisine Type"
            }
        ));
        jScrollPane1.setViewportView(tableVendor);

        jButton4.setBackground(new java.awt.Color(255, 204, 102));
        jButton4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon("Images/home.png"));
        jButton4.setText("MAIN MENU");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnupdate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btnupdate.setIcon(new javax.swing.ImageIcon("Images/update.png"));
        btnupdate.setText("Update");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon("Images/logo small.jpg"));
        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnupdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndelete)
                        .addGap(44, 44, 44))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfemailActionPerformed

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
                                      "Vendor created successfully!", 
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

    private void loadVendorData() {
    DefaultTableModel model = (DefaultTableModel) tableVendor.getModel();
    
    model.setRowCount(0);

    try (BufferedReader reader = new BufferedReader(new FileReader("platezies_txtfiles/vendor.txt"))) {
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
    
    
    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
            int row = tableVendor.getSelectedRow();

            if (row < 0){
                    JOptionPane.showMessageDialog(this,
                                                   "No row is selected! Please select one row",
                                                   "Select row",
                                                   JOptionPane.ERROR_MESSAGE);
            } else {
                DefaultTableModel model = (DefaultTableModel) tableVendor.getModel();
                model.removeRow(row);
                try {
                    saveUpdatedDataToFile();
                } catch (IOException ex) {
                    Logger.getLogger(EditVendor.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(this, 
                                              "Vendor deleted successfully!", 
                                              "Success",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        // TODO add your handling code here:
        tfname.setText("");
        tfusername.setText("");
        tfemail.setText("");
        tfphone.setText("");  
        tfcuisine.setText("");
        tfpassword.setText("");
    }//GEN-LAST:event_btnclearActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
                                                 
    int row = tableVendor.getSelectedRow();
    
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
    DefaultTableModel model = (DefaultTableModel) tableVendor.getModel();
    model.setValueAt(tfname.getText().trim(), row, 0);
    model.setValueAt(username, row, 1);
    model.setValueAt(email, row, 2);
    model.setValueAt(tfphone.getText().trim(), row, 3);
    model.setValueAt(tfpassword.getText().trim(), row, 4);
    model.setValueAt(tfcuisine.getText().trim(), row, 5);

    // Save the updated table data to file
    try {
        saveUpdatedDataToFile();
        JOptionPane.showMessageDialog(this, 
                                      "Vendor updated successfully!", 
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

    private void saveUpdatedDataToFile() throws IOException {
    String filePath = Paths.get("platezies_txtfiles/vendor.txt").toAbsolutePath().toString();
    File vendorFile = new File(filePath);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(vendorFile))) {
        DefaultTableModel model = (DefaultTableModel) tableVendor.getModel();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            writer.write(model.getValueAt(i, 0) + "," + // Name
                         model.getValueAt(i, 1) + "," + // Username
                         model.getValueAt(i, 2) + "," + // Email
                         model.getValueAt(i, 3) + "," + // Phone
                         model.getValueAt(i, 4) + "," + // Password
                         model.getValueAt(i, 5));      // Cuisine
            writer.newLine();
        }
    }
}

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        AdminMainMenu adminMenuFrame = new AdminMainMenu();
        adminMenuFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

private boolean validateInputs() {
    String name = tfname.getText().trim();
    String username = tfusername.getText().trim();
    String email = tfemail.getText().trim();
    String phone = tfphone.getText().trim();
    String password = tfpassword.getText().trim();
    String cuisine = tfcuisine.getText().trim();

    // Check for empty fields
    if (name.isEmpty() || username.isEmpty() || email.isEmpty() || 
        phone.isEmpty() || password.isEmpty() || cuisine.isEmpty()) {
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
    DefaultTableModel model = (DefaultTableModel) tableVendor.getModel();
    
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

    String filePath = Paths.get("platezies_txtfiles/vendor.txt").toAbsolutePath().toString();
    File vendorFile = new File(filePath);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(vendorFile, true))) {
        writer.write(tfname.getText().trim() + "," +
                     tfusername.getText().trim() + "," +
                     tfemail.getText().trim() + "," +
                     tfphone.getText().trim() + "," +
                     tfpassword.getText().trim() + "," +
                     tfcuisine.getText().trim());
        writer.newLine();
    }

    DefaultTableModel model = (DefaultTableModel) tableVendor.getModel();
    model.addRow(new Object[]{ tfname.getText().trim(), 
                               tfusername.getText().trim(),
                               tfemail.getText().trim(), 
                               tfphone.getText().trim(), 
                               tfpassword.getText().trim(), 
                               tfcuisine.getText().trim() });
}


    
  private void addTableMouseListener() {
        tableVendor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = tableVendor.getSelectedRow();
                if (row >= 0) {
                    tfname.setText((String) tableVendor.getValueAt(row, 0));
                    tfusername.setText((String) tableVendor.getValueAt(row, 1));
                    tfemail.setText((String) tableVendor.getValueAt(row, 2));
                    tfphone.setText((String) tableVendor.getValueAt(row, 3));
                    tfpassword.setText((String) tableVendor.getValueAt(row, 4));
                    tfcuisine.setText((String) tableVendor.getValueAt(row, 5));
                }
            }
        });
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btncreate;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnupdate;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableVendor;
    private javax.swing.JTextField tfcuisine;
    private javax.swing.JTextField tfemail;
    private javax.swing.JTextField tfname;
    private javax.swing.JTextField tfpassword;
    private javax.swing.JTextField tfphone;
    private javax.swing.JTextField tfusername;
    // End of variables declaration//GEN-END:variables
}
