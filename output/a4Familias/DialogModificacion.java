package a4Familias;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.BotonPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidades.PanelPadre;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JDialogPrincipal;

public class DialogModificacion extends JDialogPrincipal implements ActionListener {
   int codigoFamilia;
   private static final long serialVersionUID = 1L;
   private final JPanel contentPanel = new JPanel();
   private LimiteTextField txt_usuario;
   private BotonPadre btnGuardar;
   private BotonPadre btnSalir;
   private LabelPrincipal lblNombreFamilia;

   public DialogModificacion(int codigoFamilia, String nombreFamilia, String familia, int fila) {
      this.setTitle("Modificacion de " + familia);
      this.setBounds(100, 100, 406, 180);
      this.getContentPane().setLayout(new BorderLayout());
      this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(this.contentPanel, "North");
      PanelPadre panel_1 = new PanelPadre("");
      this.lblNombreFamilia = new LabelPrincipal("Familia", "label");
      this.txt_usuario = new LimiteTextField(25);
      this.btnGuardar = new BotonPadre("Guardar", "guardar.gif");
      this.btnGuardar.addActionListener(this);
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      this.btnSalir.addActionListener(this);
      GroupLayout gl_contentPanel = new GroupLayout(this.contentPanel);
      gl_contentPanel.setHorizontalGroup(
         gl_contentPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_contentPanel.createSequentialGroup().addComponent(panel_1, -2, 385, -2).addContainerGap(298, 32767))
      );
      gl_contentPanel.setVerticalGroup(
         gl_contentPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addComponent(panel_1, -2, 120, -2).addContainerGap(614, 32767))
      );
      GroupLayout gl_panel_1 = new GroupLayout(panel_1);
      gl_panel_1.setHorizontalGroup(
         gl_panel_1.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_1.createSequentialGroup()
                  .addComponent(this.lblNombreFamilia, -2, 89, -2)
                  .addGroup(
                     gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_1.createSequentialGroup()
                              .addGap(35)
                              .addComponent(this.btnGuardar, -2, -1, -2)
                              .addGap(18)
                              .addComponent(this.btnSalir, -2, -1, -2)
                        )
                        .addGroup(gl_panel_1.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(this.txt_usuario, -2, 275, -2))
                  )
                  .addContainerGap(101, 32767)
            )
      );
      gl_panel_1.setVerticalGroup(
         gl_panel_1.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_1.createSequentialGroup()
                  .addGap(27)
                  .addGroup(
                     gl_panel_1.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.lblNombreFamilia, -2, -1, -2)
                        .addComponent(this.txt_usuario, -2, 31, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED, 27, 32767)
                  .addGroup(
                     gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(this.btnGuardar, -2, -1, -2).addComponent(this.btnSalir, -2, -1, -2)
                  )
            )
      );
      panel_1.setLayout(gl_panel_1);
      this.contentPanel.setLayout(gl_contentPanel);
      this.lblNombreFamilia.setText(familia);
      this.txt_usuario.setText(nombreFamilia);
      this.codigoFamilia = codigoFamilia;
   }

   public LimiteTextField getTxt_usuario() {
      return this.txt_usuario;
   }

   public void setTxt_usuario(LimiteTextField txt_usuario) {
      this.txt_usuario = txt_usuario;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (this.btnSalir == e.getSource()) {
         this.dispose();
      }

      if (this.btnGuardar == e.getSource()) {
         DialogoMessagebox d = new DialogoMessagebox("Desea actualizar " + this.lblNombreFamilia.getText() + "?");
         d.setLocationRelativeTo(this);
         d.setVisible(true);
         if (d.isResultadoEncontrado()) {
            int codigo = 0;
            if (this.lblNombreFamilia.getText().trim().equals("Seccion")) {
               SeccionE entidadUsuario = this.CargarEntidadesSeccion();
               codigo = SeccionE.actualizarSeccion(entidadUsuario, null);
            } else if (this.lblNombreFamilia.getText().trim().equals("Sub Seccion")) {
               SubSeccionE entidadUsuario = this.CargarEntidadesSubSeccion();
               codigo = SubSeccionE.actualizarsubSeccion(entidadUsuario, null);
            } else if (this.lblNombreFamilia.getText().trim().equals("Grupo")) {
               GrupoE entidadUsuario = this.CargarEntidadesGrupo();
               codigo = GrupoE.actualizarGrupo(entidadUsuario, null);
            }

            if (codigo != 0) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion(this.lblNombreFamilia.getText() + " actualizado correctamente", "correcto");
               rs.setLocationRelativeTo(this);
               rs.setVisible(true);
               this.dispose();
            }
         }
      }
   }

   private GrupoE CargarEntidadesGrupo() {
      return new GrupoE(this.codigoFamilia, this.txt_usuario.getText(), 0);
   }

   private SeccionE CargarEntidadesSeccion() {
      return new SeccionE(this.codigoFamilia, this.txt_usuario.getText());
   }

   private SubSeccionE CargarEntidadesSubSeccion() {
      return new SubSeccionE(this.codigoFamilia, this.txt_usuario.getText(), 0);
   }

   public static void main(String[] args) {
      DialogModificacion dialog = new DialogModificacion(0, "", "Seccion", 0);

      try {
         dialog.setDefaultCloseOperation(2);
         dialog.setVisible(true);
      } catch (Exception var3) {
         LogErrores.errores(var3, "Error al iniciar el Formulario", dialog);
         var3.printStackTrace();
      }
   }
}
