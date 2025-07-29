package a3Permisos;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JPasswordField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import utilidades.BotonPadre;
import utilidades.LabelPrincipal;
import utilidades.PanelPadre;
import utilidadesConexion.DatabaseConnection;
import utilidadesVentanas.JinternalPadre;

public class ActualizarPasswordForm extends JinternalPadre implements ActionListener {
   private static final long serialVersionUID = 1L;
   private BotonPadre btnGrabar;
   private BotonPadre btnSalir;
   private JPasswordField txt_confirmacion_password;
   private JPasswordField txt_nuevo_password;
   private JPasswordField txt_password_actual;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               ActualizarPasswordForm frame = new ActualizarPasswordForm();
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
   }

   public ActualizarPasswordForm() {
      this.setTitle("Actualizar Contraseña");
      this.setBounds(100, 100, 483, 363);
      PanelPadre panel = new PanelPadre("");
      LabelPrincipal lblPasswordActual = new LabelPrincipal("Contraseña Actual", "label");
      LabelPrincipal lblNuevoPassword = new LabelPrincipal("Nueva Contraseña", "label");
      LabelPrincipal lblRepetirPassword = new LabelPrincipal("Repetir Contraseña", "label");
      this.txt_password_actual = new JPasswordField(25);
      this.txt_nuevo_password = new JPasswordField(25);
      this.txt_confirmacion_password = new JPasswordField(25);
      this.btnGrabar = new BotonPadre("Actualizar", "reset.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, -2, 448, -2).addContainerGap(-1, 32767))
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, -2, 314, -2).addContainerGap(-1, 32767))
      );
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel.createSequentialGroup()
                              .addGap(51)
                              .addComponent(this.btnGrabar, -2, 148, -2)
                              .addGap(26)
                              .addComponent(this.btnSalir, -2, 148, -2)
                        )
                        .addGroup(
                           gl_panel.createSequentialGroup()
                              .addComponent(lblPasswordActual, -2, 123, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_password_actual, -2, 255, -2)
                        )
                        .addGroup(
                           gl_panel.createSequentialGroup()
                              .addComponent(lblNuevoPassword, -2, 123, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_nuevo_password, -2, 255, -2)
                        )
                        .addGroup(
                           gl_panel.createSequentialGroup()
                              .addComponent(lblRepetirPassword, -2, 123, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_confirmacion_password, -2, 255, -2)
                        )
                  )
                  .addContainerGap(66, 32767)
            )
      );
      gl_panel.setVerticalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addGap(16)
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblPasswordActual, -2, 25, -2)
                        .addComponent(this.txt_password_actual, -2, 31, -2)
                  )
                  .addGap(12)
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNuevoPassword, -2, 25, -2)
                        .addComponent(this.txt_nuevo_password, -2, 31, -2)
                  )
                  .addGap(12)
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblRepetirPassword, -2, 25, -2)
                        .addComponent(this.txt_confirmacion_password, -2, 31, -2)
                  )
                  .addGap(18)
                  .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(this.btnGrabar, -2, 45, -2).addComponent(this.btnSalir, -2, 45, -2))
                  .addContainerGap(118, 32767)
            )
      );
      panel.setLayout(gl_panel);
      this.getContentPane().setLayout(groupLayout);
      this.btnGrabar.addActionListener(this);
      this.btnSalir.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.btnGrabar) {
         DatabaseConnection db = DatabaseConnection.getInstance();
         String passwordActual = new String(this.txt_password_actual.getPassword());
         String nuevoPassword = new String(this.txt_nuevo_password.getPassword());
         String confirmacionPassword = new String(this.txt_nuevo_password.getPassword());
         if (DatabaseConnection.probarConexion(DatabaseConnection.getInstance().getUsuario(), passwordActual) && nuevoPassword.equals(confirmacionPassword)) {
            UsuariosE.actualizarPassword(DatabaseConnection.getInstance().getUsuario(), nuevoPassword, this);
         }
      }

      if (e.getSource() == this.btnSalir) {
         this.dispose();
      }
   }
}
