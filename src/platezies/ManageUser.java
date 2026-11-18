package platezies;


public class ManageUser extends javax.swing.JFrame {

    public ManageUser() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jPanel1 = new javax.swing.JPanel();
        btnregister = new javax.swing.JButton();
        btnvendor = new javax.swing.JButton();
        btnrunner = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnmainmenu = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btncustomer1 = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnregister.setBackground(new java.awt.Color(0, 102, 102));
        btnregister.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnregister.setIcon(new javax.swing.ImageIcon("Images/user.png"));
        btnregister.setText("Register New Users");
        btnregister.setMaximumSize(new java.awt.Dimension(144, 38));
        btnregister.setMinimumSize(new java.awt.Dimension(144, 38));
        btnregister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregisterActionPerformed(evt);
            }
        });

        btnvendor.setBackground(new java.awt.Color(0, 102, 102));
        btnvendor.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnvendor.setIcon(new javax.swing.ImageIcon("Images/vendor.png"));
        btnvendor.setText("Vendor");
        btnvendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnvendorActionPerformed(evt);
            }
        });

        btnrunner.setBackground(new java.awt.Color(0, 102, 102));
        btnrunner.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnrunner.setIcon(new javax.swing.ImageIcon("Images/runner.png"));
        btnrunner.setText("Runner");
        btnrunner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrunnerActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MANAGE USER PROFILE");

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
        jLabel3.setText("jLabel3");

        btncustomer1.setBackground(new java.awt.Color(0, 102, 102));
        btncustomer1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btncustomer1.setIcon(new javax.swing.ImageIcon("Images/customer.png"));
        btncustomer1.setText("Customer");
        btncustomer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncustomer1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addGap(103, 103, 103))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnregister, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnrunner, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncustomer1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnvendor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(240, 240, 240))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnmainmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addGap(50, 50, 50)
                .addComponent(btnregister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(btncustomer1)
                .addGap(28, 28, 28)
                .addComponent(btnrunner)
                .addGap(33, 33, 33)
                .addComponent(btnvendor)
                .addGap(56, 56, 56)
                .addComponent(btnmainmenu)
                .addGap(28, 28, 28))
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

    private void btnregisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregisterActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register().setVisible(true);
                
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnregisterActionPerformed

    private void btnmainmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmainmenuActionPerformed
       // TODO add your handling code here:
        AdminMainMenu adminMenuFrame = new AdminMainMenu();
        adminMenuFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnmainmenuActionPerformed

    private void btnrunnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrunnerActionPerformed
        // TODO add your handling code here:
        EditRunner  editRunnerFrame = new EditRunner ();
        editRunnerFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnrunnerActionPerformed

    private void btnvendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnvendorActionPerformed
        // TODO add your handling code here:
        EditVendor  editVendorFrame = new EditVendor ();
        editVendorFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnvendorActionPerformed

    private void btncustomer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncustomer1ActionPerformed
        // TODO add your handling code here:
        EditCustomer  editCustomerFrame = new EditCustomer ();
        editCustomerFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btncustomer1ActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncustomer1;
    private javax.swing.JButton btnmainmenu;
    private javax.swing.JButton btnregister;
    private javax.swing.JButton btnrunner;
    private javax.swing.JButton btnvendor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSlider jSlider1;
    // End of variables declaration//GEN-END:variables
}
