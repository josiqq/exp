package a3Permisos;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;
import utilidades.BotonPadre;
import utilidades.PanelPadre;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JDialogPrincipal;

public class DepositosEnviosUsuariosForm extends JDialogPrincipal implements ActionListener {
   List<Integer> codigosAEliminar = new ArrayList<>();
   private TablaDepositoEnviosUsuarios tabla;
   private BotonPadre btnSalir;
   private BotonPadre btnGuardar;
   private String nombre_usuario;
   private static final long serialVersionUID = 1L;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            DepositosEnviosUsuariosForm frame = new DepositosEnviosUsuariosForm("");

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               var3.printStackTrace();
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
            }
         }
      });
   }

   public DepositosEnviosUsuariosForm(String nombre_usuario) {
      this.nombre_usuario = nombre_usuario;
      this.setTitle("Depositos Habilitados para Envios");
      this.setBounds(100, 100, 710, 419);
      PanelPadre contentPane = new PanelPadre("");
      PanelPadre panel_botones = new PanelPadre("");
      PanelPadre panel_cabecera = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      this.btnGuardar = new BotonPadre("Guardar", "guardar.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      this.tabla = new TablaDepositoEnviosUsuarios(this.codigosAEliminar);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      this.btnGuardar.addActionListener(this);
      this.btnSalir.addActionListener(this);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING, gl_contentPane.createSequentialGroup().addContainerGap(162, 32767).addComponent(panel_botones, -2, 415, -2).addGap(109)
            )
            .addGroup(gl_contentPane.createSequentialGroup().addComponent(panel_cabecera, -1, 676, 32767).addContainerGap())
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING,
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_cabecera, -1, 306, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_botones, -2, 50, -2)
                  .addContainerGap()
            )
      );
      GroupLayout gl_panel_botones = new GroupLayout(panel_botones);
      gl_panel_botones.setHorizontalGroup(
         gl_panel_botones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_botones.createSequentialGroup()
                  .addGap(88)
                  .addComponent(this.btnGuardar)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnSalir)
                  .addContainerGap(266, 32767)
            )
      );
      gl_panel_botones.setVerticalGroup(
         gl_panel_botones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_botones.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(gl_panel_botones.createParallelGroup(Alignment.BASELINE).addComponent(this.btnGuardar).addComponent(this.btnSalir))
                  .addContainerGap(19, 32767)
            )
      );
      panel_botones.setLayout(gl_panel_botones);
      GroupLayout gl_panel_cabecera = new GroupLayout(panel_cabecera);
      gl_panel_cabecera.setHorizontalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_cabecera.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 653, 32767).addContainerGap())
      );
      gl_panel_cabecera.setVerticalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_cabecera.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 283, 32767).addContainerGap())
      );
      panel_cabecera.setLayout(gl_panel_cabecera);
      contentPane.setLayout(gl_contentPane);
      if (nombre_usuario != null) {
         DepositosEnviosUsuariosE.cargarDepositoEnvioUsuario(nombre_usuario, this.tabla, null);
      } else {
         ModeloTablaPermisos modelo = (ModeloTablaPermisos)this.tabla.getModel();
         modelo.addDefaultRow2();
      }
   }

   private void guardar() {
      int insertar = DepositosEnviosUsuariosE.insertarDepositosEnviosUsuarios(this.nombre_usuario, this.tabla, this, this.codigosAEliminar);
      if (insertar != 0) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.btnGuardar) {
         if (this.tabla.isEditing()) {
            TableCellEditor editor = this.tabla.getCellEditor();
            if (editor != null) {
               editor.stopCellEditing();
            }
         }

         this.guardar();
      }

      if (this.btnSalir == e.getSource()) {
         this.dispose();
      }
   }
}
