package platezies;


import javax.swing.JOptionPane;


public class AdminMainMenu extends javax.swing.JFrame {

    public AdminMainMenu() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnmanageuser = new javax.swing.JButton();
        btntopup = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnlogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnmanageuser.setBackground(new java.awt.Color(0, 102, 102));
        btnmanageuser.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnmanageuser.setIcon(new javax.swing.ImageIcon("Images/manageuser.png"));
        btnmanageuser.setText("Manage User Profile");
        btnmanageuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanageuserActionPerformed(evt);
            }
        });

        btntopup.setBackground(new java.awt.Color(0, 102, 102));
        btntopup.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btntopup.setIcon(new javax.swing.ImageIcon("Images/top-up.png"));
        btntopup.setText("Top-Up Approval");
        btntopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntopupActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ADMIN MAIN MENU");

        jLabel2.setIcon(new javax.swing.ImageIcon("Images/logo small.jpg"));
        jLabel2.setText("jLabel2");

        btnlogout.setBackground(new java.awt.Color(0, 0, 0));
        btnlogout.setIcon(new javax.swing.ImageIcon("Images/check-out (1).png"));
        btnlogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(170, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnlogout)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btntopup, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnmanageuser, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(241, 241, 241))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnlogout)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)))
                .addGap(72, 72, 72)
                .addComponent(btnmanageuser)
                .addGap(46, 46, 46)
                .addComponent(btntopup)
                .addContainerGap(152, Short.MAX_VALUE))
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

    private void btnmanageuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmanageuserActionPerformed
        // TODO add your handling code here:
        ManageUser  manageUserFrame = new ManageUser ();
        manageUserFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnmanageuserActionPerformed

    private void btntopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntopupActionPerformed
        // TODO add your handling code here:
        ApprovalForm approvalFormFrame = new ApprovalForm();
        approvalFormFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btntopupActionPerformed

    private void btnlogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlogoutActionPerformed
        // TODO add your handling code here:
        Login loginFrame = new Login(); 
        loginFrame.setVisible(true); 
        this.dispose(); // Close
        
        JOptionPane.showMessageDialog(this, "Successfully logged out.", 
                                      "Logout",
                                      JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnlogoutActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlogout;
    private javax.swing.JButton btnmanageuser;
    private javax.swing.JButton btntopup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
