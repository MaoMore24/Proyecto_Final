
package Vista;


public class frmSistema extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmSistema.class.getName());

    public frmSistema() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btnInicio = new javax.swing.JButton();
        btnPacientes = new javax.swing.JButton();
        btnCitas = new javax.swing.JButton();
        btnMedicos = new javax.swing.JButton();
        btnExpedientes = new javax.swing.JButton();
        btnAgenda = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestión Hospitalaria");

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 204, 255));
        jLabel1.setText("  Panel de Control");
        jLabel1.setOpaque(true);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/exit.png"))); // NOI18N

        jToolBar1.setBackground(new java.awt.Color(0, 102, 153));
        jToolBar1.setBorder(null);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        jToolBar1.setToolTipText("");
        jToolBar1.setName(""); // NOI18N

        btnInicio.setBackground(new java.awt.Color(0, 102, 153));
        btnInicio.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnInicio.setForeground(new java.awt.Color(255, 255, 255));
        btnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/home (2).png"))); // NOI18N
        btnInicio.setText("Inicio");
        btnInicio.setFocusable(false);
        btnInicio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnInicio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnInicio.setIconTextGap(8);
        btnInicio.setMargin(new java.awt.Insets(6, 14, 3, 14));
        btnInicio.setMaximumSize(new java.awt.Dimension(135, 60));
        btnInicio.setMinimumSize(new java.awt.Dimension(60, 53));
        btnInicio.setPreferredSize(new java.awt.Dimension(60, 53));
        btnInicio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });
        jToolBar1.add(btnInicio);

        btnPacientes.setBackground(new java.awt.Color(0, 102, 153));
        btnPacientes.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnPacientes.setForeground(new java.awt.Color(255, 255, 255));
        btnPacientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/user (1).png"))); // NOI18N
        btnPacientes.setText("Pacientes");
        btnPacientes.setFocusable(false);
        btnPacientes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPacientes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnPacientes.setIconTextGap(8);
        btnPacientes.setMargin(new java.awt.Insets(6, 14, 3, 14));
        btnPacientes.setMaximumSize(new java.awt.Dimension(135, 60));
        btnPacientes.setMinimumSize(new java.awt.Dimension(60, 53));
        btnPacientes.setPreferredSize(new java.awt.Dimension(60, 53));
        btnPacientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPacientesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPacientes);

        btnCitas.setBackground(new java.awt.Color(0, 102, 153));
        btnCitas.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnCitas.setForeground(new java.awt.Color(255, 255, 255));
        btnCitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/calendar.png"))); // NOI18N
        btnCitas.setText("Citas");
        btnCitas.setFocusable(false);
        btnCitas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCitas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCitas.setIconTextGap(8);
        btnCitas.setMargin(new java.awt.Insets(6, 14, 3, 14));
        btnCitas.setMaximumSize(new java.awt.Dimension(135, 60));
        btnCitas.setMinimumSize(new java.awt.Dimension(60, 53));
        btnCitas.setPreferredSize(new java.awt.Dimension(60, 53));
        btnCitas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCitasActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCitas);

        btnMedicos.setBackground(new java.awt.Color(0, 102, 153));
        btnMedicos.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnMedicos.setForeground(new java.awt.Color(255, 255, 255));
        btnMedicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/doctor (3).png"))); // NOI18N
        btnMedicos.setText("Médicos");
        btnMedicos.setFocusable(false);
        btnMedicos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMedicos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnMedicos.setIconTextGap(8);
        btnMedicos.setMargin(new java.awt.Insets(6, 14, 3, 14));
        btnMedicos.setMaximumSize(new java.awt.Dimension(135, 60));
        btnMedicos.setMinimumSize(new java.awt.Dimension(60, 53));
        btnMedicos.setPreferredSize(new java.awt.Dimension(60, 53));
        btnMedicos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMedicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMedicosActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMedicos);

        btnExpedientes.setBackground(new java.awt.Color(0, 102, 153));
        btnExpedientes.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnExpedientes.setForeground(new java.awt.Color(255, 255, 255));
        btnExpedientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/open-folder.png"))); // NOI18N
        btnExpedientes.setText("Expedientes");
        btnExpedientes.setFocusable(false);
        btnExpedientes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnExpedientes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnExpedientes.setIconTextGap(8);
        btnExpedientes.setMargin(new java.awt.Insets(6, 14, 3, 14));
        btnExpedientes.setMaximumSize(new java.awt.Dimension(135, 60));
        btnExpedientes.setMinimumSize(new java.awt.Dimension(60, 53));
        btnExpedientes.setPreferredSize(new java.awt.Dimension(60, 53));
        btnExpedientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExpedientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpedientesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnExpedientes);

        btnAgenda.setBackground(new java.awt.Color(0, 102, 153));
        btnAgenda.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnAgenda.setForeground(new java.awt.Color(255, 255, 255));
        btnAgenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/agenda (2).png"))); // NOI18N
        btnAgenda.setText("Mi agenda");
        btnAgenda.setFocusable(false);
        btnAgenda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgenda.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAgenda.setIconTextGap(8);
        btnAgenda.setMargin(new java.awt.Insets(6, 14, 3, 14));
        btnAgenda.setMaximumSize(new java.awt.Dimension(135, 60));
        btnAgenda.setMinimumSize(new java.awt.Dimension(60, 53));
        btnAgenda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAgenda);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/logohospital.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel2)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
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

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPacientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPacientesActionPerformed

    private void btnCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCitasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCitasActionPerformed

    private void btnMedicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMedicosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMedicosActionPerformed

    private void btnExpedientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpedientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExpedientesActionPerformed

    private void btnAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgendaActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new frmSistema().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAgenda;
    public javax.swing.JButton btnCitas;
    public javax.swing.JButton btnExpedientes;
    public javax.swing.JButton btnInicio;
    public javax.swing.JButton btnMedicos;
    public javax.swing.JButton btnPacientes;
    public javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
