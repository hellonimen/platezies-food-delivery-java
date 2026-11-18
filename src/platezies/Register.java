package platezies;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Register extends javax.swing.JFrame {

    public Register() {
        initComponents();
        initializeTextFieldVisibility();
        updateTextFieldVisibility();
        
        
        
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTextFieldVisibility(); 
            }   
        });      
      }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        tfname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfusername = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfemail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tfphone = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        tfpassword = new javax.swing.JPasswordField();
        btnregister = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jlcuisine = new javax.swing.JLabel();
        tfcuisine = new javax.swing.JTextField();
        jlvehicle = new javax.swing.JLabel();
        tfvehicle = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnmainmenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Register");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        tfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfnameActionPerformed(evt);
            }
        });
        jPanel1.add(tfname);
        tfname.setBounds(430, 90, 359, 22);

        jLabel3.setText("Name");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(430, 60, 359, 16);

        tfusername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfusernameActionPerformed(evt);
            }
        });
        jPanel1.add(tfusername);
        tfusername.setBounds(430, 150, 290, 22);

        jLabel4.setText("Username");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(430, 130, 359, 16);

        tfemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfemailActionPerformed(evt);
            }
        });
        jPanel1.add(tfemail);
        tfemail.setBounds(430, 210, 260, 22);

        jLabel5.setText("Email");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(430, 190, 41, 16);

        jLabel7.setText("Phone");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(430, 250, 41, 16);
        jPanel1.add(tfphone);
        tfphone.setBounds(430, 270, 190, 22);

        jLabel9.setText("Select A Role   ");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(430, 310, 86, 16);

        jComboBox1.setBackground(new java.awt.Color(102, 102, 102));
        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Customer", "Vendor", "Runner" }));
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(430, 330, 88, 22);

        jLabel8.setText("Password");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(430, 360, 56, 16);

        tfpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfpasswordActionPerformed(evt);
            }
        });
        jPanel1.add(tfpassword);
        tfpassword.setBounds(430, 380, 240, 22);

        btnregister.setBackground(new java.awt.Color(0, 102, 102));
        btnregister.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnregister.setText("Register");
        btnregister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregisterActionPerformed(evt);
            }
        });
        jPanel1.add(btnregister);
        btnregister.setBounds(430, 550, 91, 23);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Register Users");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(410, 20, 398, 32);

        jlcuisine.setText("Cuisine Type");
        jPanel1.add(jlcuisine);
        jlcuisine.setBounds(430, 420, 340, 16);
        jPanel1.add(tfcuisine);
        tfcuisine.setBounds(430, 440, 210, 22);

        jlvehicle.setText("Vehicle No.");
        jPanel1.add(jlvehicle);
        jlvehicle.setBounds(430, 480, 330, 16);
        jPanel1.add(tfvehicle);
        tfvehicle.setBounds(430, 500, 110, 22);

        jLabel1.setIcon(new javax.swing.ImageIcon("Images/platezies.jpeg"));
        jLabel1.setText("jLabel1");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 110, 380, 370);

        jLabel6.setText("copyright © Platezies All rights reserved");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(80, 490, 240, 16);

        btnmainmenu.setBackground(new java.awt.Color(255, 204, 102));
        btnmainmenu.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnmainmenu.setIcon(new javax.swing.ImageIcon("Images/home.png"));
        btnmainmenu.setText("MAIN MENU");
        btnmainmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmainmenuActionPerformed(evt);
            }
        });
        jPanel1.add(btnmainmenu);
        btnmainmenu.setBounds(665, 550, 130, 23);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnregisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregisterActionPerformed
       
    String name = tfname.getText();
    String username = tfusername.getText();
    String email = tfemail.getText();
    String phone = tfphone.getText();
    String password = new String(tfpassword.getPassword());
    String role = jComboBox1.getSelectedItem().toString();
    String cuisine = tfcuisine.getText();
    String vehicle = tfvehicle.getText();
    Double eWalletBal = 0.0;

    if (!isValidEmail(email)) {
        JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (isEmailRegistered(email)) {
        JOptionPane.showMessageDialog(this, "Email is already registered!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!isValidPhone(phone)) {
        JOptionPane.showMessageDialog(this, "Phone number must be 10 digits long!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!isValidPassword(password)) {
        JOptionPane.showMessageDialog(this,
            "Password must be at least 8 characters long and contain uppercase, lowercase, number, and special character!",
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (role.equalsIgnoreCase("Vendor") && cuisine.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Cuisine type cannot be empty for Vendors!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (role.equalsIgnoreCase("Runner") && !isValidVehicleNumber(vehicle)) {
        JOptionPane.showMessageDialog(this, "Invalid vehicle number format!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String filename = "platezies_txtfiles/" + role.toLowerCase() + ".txt";
    
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
        writer.write(name + "," + username + "," + email + "," + phone + "," + password);
        
        
        if (role.equalsIgnoreCase("Customer")) {
            writer.write("," + String.valueOf(eWalletBal));
        } else if (role.equalsIgnoreCase("Vendor")) {
            writer.write("," + cuisine);
        } else if (role.equalsIgnoreCase("Runner")) {
            writer.write("," + vehicle);
        }

        writer.newLine();
        JOptionPane.showMessageDialog(this, "User Registration is successful!");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving user data.");
        e.printStackTrace();
    }



    }//GEN-LAST:event_btnregisterActionPerformed

   private boolean isEmailRegistered(String email) {
    String[] roles = {"customer", "vendor", "runner"};
    for (String role : roles) {
        String filename = "platezies_txtfiles/" + role + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 2 && data[2].equalsIgnoreCase(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return false;
}

    private boolean isValidVehicleNumber(String vehicle) {
        return vehicle.matches("^[A-Z0-9-]{6,10}$");
    }
    
    
    
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
    
    private boolean isValidEmail(String email) {
    return email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidPhone(String phone) {
    return phone.matches("^\\d{10}$");
    }
    
    private void tfpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfpasswordActionPerformed
        if (!String.valueOf(tfpassword.getPassword()).matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            JOptionPane.showMessageDialog(this,
                                            "Password must be at least 8 characters long and contain uppercase, lowercase, number, and special character!",
                                             "Error", JOptionPane.ERROR_MESSAGE);
                                              return;
        }
    }//GEN-LAST:event_tfpasswordActionPerformed

    private void tfemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfemailActionPerformed
        if (!tfemail.getText().matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(this,
                                         "Invalid email format!",
                                         "Error", JOptionPane.ERROR_MESSAGE);
                                         return;
        }
    }//GEN-LAST:event_tfemailActionPerformed

    private void tfusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfusernameActionPerformed

    private void tfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfnameActionPerformed

    private void btnmainmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmainmenuActionPerformed
        // TODO add your handling code here:
        AdminMainMenu adminMenuFrame = new AdminMainMenu();
        adminMenuFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnmainmenuActionPerformed
private void updateTextFieldVisibility() {
        String role = jComboBox1.getSelectedItem().toString();
        
        if (role.equalsIgnoreCase("Customer")) {
            tfvehicle.setVisible(false);
            tfcuisine.setVisible(false);
            jlvehicle.setVisible(false); 
            jlcuisine.setVisible(false); 
        } else if (role.equalsIgnoreCase("Vendor")) {
            tfvehicle.setVisible(false);
            tfcuisine.setVisible(true);
            jlvehicle.setVisible(false); 
            jlcuisine.setVisible(true);
        } else if (role.equalsIgnoreCase("Runner")) {
            tfvehicle.setVisible(true);
            tfcuisine.setVisible(false);
            jlvehicle.setVisible(true); 
            jlcuisine.setVisible(false); 
        }
        
     
        jPanel1.revalidate();
        jPanel1.repaint();
    }
    
    private void initializeTextFieldVisibility() { //hide by default
        tfvehicle.setVisible(false);
        tfcuisine.setVisible(false);
        jlvehicle.setVisible(false); 
        jlcuisine.setVisible(false);
    }
   
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnmainmenu;
    private javax.swing.JButton btnregister;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JLabel jlcuisine;
    private javax.swing.JLabel jlvehicle;
    private javax.swing.JTextField tfcuisine;
    private javax.swing.JTextField tfemail;
    private javax.swing.JTextField tfname;
    private javax.swing.JPasswordField tfpassword;
    private javax.swing.JTextField tfphone;
    private javax.swing.JTextField tfusername;
    private javax.swing.JTextField tfvehicle;
    // End of variables declaration//GEN-END:variables
}
