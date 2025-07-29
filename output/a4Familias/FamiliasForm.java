package a4Familias;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utilidades.BotonPadre;
import utilidades.PanelPadre;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadordeCodigos;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class FamiliasForm extends JinternalPadre implements ActionListener {
   private ModeloTablaDefecto modeloSeccion;
   private ModeloTablaDefecto modeloSubSeccion;
   private ModeloTablaDefecto modeloGrupo;
   private BuscadorTabla tabla_seccion;
   private BuscadorTabla tabla_sub_seccion;
   private BuscadorTabla tabla_grupo;
   private static final long serialVersionUID = 1L;
   private BotonPadre btnNuevaSeccion;
   private BotonPadre btnModificarSeccion;
   private BotonPadre btnEliminarSeccion;
   private BotonPadre btnNuevaSubSeccion;
   private BotonPadre btnModificarSubSeccion;
   private BotonPadre btnEliminarSubSeccion;
   private BotonPadre btnNuevoGrupo;
   private BotonPadre btnModificarGrupo;
   private BotonPadre btnEliminarGrupo;

   private GrupoE CargarEntidadesGrupo(int cod_sub_seccion, String nueva_sub_seccion) {
      try {
         return new GrupoE(0, nueva_sub_seccion, cod_sub_seccion);
      } catch (Exception var4) {
         LogErrores.errores(var4, "Error al Iniciar Recuperar Entidad de Grupo...", this);
         return null;
      }
   }

   private SubSeccionE CargarEntidadesSubSeccion(int cod_seccion, String nueva_sub_seccion) {
      try {
         return new SubSeccionE(0, nueva_sub_seccion, cod_seccion);
      } catch (Exception var4) {
         LogErrores.errores(var4, "Error al Iniciar Recuperar Entidad de Sub Seccion...", this);
         return null;
      }
   }

   private SeccionE CargarEntidadesSeccion(int cod_seccion, String nueva_seccion) {
      try {
         return new SeccionE(cod_seccion, nueva_seccion);
      } catch (Exception var4) {
         LogErrores.errores(var4, "Error al Iniciar Recuperar Entidad de Seccion...", this);
         return null;
      }
   }

   public FamiliasForm() {
      this.setTitle("Registro de Familias de Productos");
      this.setBounds(100, 100, 969, 502);
      PanelPadre panel_seccion = new PanelPadre("Seccion");
      PanelPadre panel_sub_seccion = new PanelPadre("Sub Seccion");
      PanelPadre panel_grupo = new PanelPadre("Grupo");
      this.btnNuevaSeccion = new BotonPadre("Nuevo", "nuevo.png");
      this.btnModificarSeccion = new BotonPadre("Modificar", "modificar.png");
      this.btnEliminarSeccion = new BotonPadre("Eliminar", "eliminar.png");
      this.btnNuevaSubSeccion = new BotonPadre("Nuevo", "nuevo.png");
      this.btnModificarSubSeccion = new BotonPadre("Modificar", "modificar.png");
      this.btnEliminarSubSeccion = new BotonPadre("Eliminar", "eliminar.png");
      this.btnNuevoGrupo = new BotonPadre("Nuevo", "nuevo.png");
      this.btnModificarGrupo = new BotonPadre("Modificar", "modificar.png");
      this.btnEliminarGrupo = new BotonPadre("Eliminar", "eliminar.png");
      String[] cabecera = new String[]{"Codigo", "Nombre"};
      this.modeloSeccion = new ModeloTablaDefecto(cabecera);
      this.modeloSubSeccion = new ModeloTablaDefecto(cabecera);
      this.modeloGrupo = new ModeloTablaDefecto(cabecera);
      this.tabla_seccion = new BuscadorTabla(this.modeloSeccion, "Familias", this);
      this.tabla_sub_seccion = new BuscadorTabla(this.modeloSubSeccion, "Familias", this);
      this.tabla_grupo = new BuscadorTabla(this.modeloGrupo, "Familias", this);
      JScrollPane scrollPaneGrupo = new JScrollPane();
      scrollPaneGrupo.setViewportView(this.tabla_grupo);
      JScrollPane scrollPaneSubSeccion = new JScrollPane();
      scrollPaneSubSeccion.setViewportView(this.tabla_sub_seccion);
      JScrollPane scrollPaneSeccion = new JScrollPane();
      scrollPaneSeccion.setViewportView(this.tabla_seccion);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(this.btnNuevaSeccion, -2, 90, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnModificarSeccion, -2, 106, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnEliminarSeccion, -2, 106, -2)
                        )
                        .addComponent(panel_seccion, -1, -1, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(this.btnNuevaSubSeccion, -2, 90, -2)
                              .addGap(2)
                              .addComponent(this.btnModificarSubSeccion, -2, 106, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnEliminarSubSeccion, -2, 106, -2)
                        )
                        .addComponent(panel_sub_seccion, -1, -1, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(this.btnNuevoGrupo, -2, 90, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnModificarGrupo, -2, 106, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnEliminarGrupo, -2, 106, -2)
                        )
                        .addComponent(panel_grupo, -1, -1, 32767)
                  )
                  .addContainerGap(232, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_grupo, -1, 417, 32767)
                        .addComponent(panel_sub_seccion, Alignment.TRAILING, -1, 417, 32767)
                        .addComponent(panel_seccion, Alignment.TRAILING, -2, 417, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.btnNuevaSeccion, -2, -1, -2)
                        .addComponent(this.btnModificarSeccion, -2, -1, -2)
                        .addComponent(this.btnEliminarSeccion, -2, -1, -2)
                        .addComponent(this.btnNuevaSubSeccion, -2, -1, -2)
                        .addComponent(this.btnModificarSubSeccion, -2, -1, -2)
                        .addComponent(this.btnEliminarSubSeccion, -2, -1, -2)
                        .addComponent(this.btnNuevoGrupo, -2, -1, -2)
                        .addComponent(this.btnModificarGrupo, -2, -1, -2)
                        .addComponent(this.btnEliminarGrupo, -2, -1, -2)
                  )
                  .addGap(11)
            )
      );
      GroupLayout gl_panel_grupo = new GroupLayout(panel_grupo);
      gl_panel_grupo.setHorizontalGroup(
         gl_panel_grupo.createParallelGroup(Alignment.TRAILING)
            .addGroup(Alignment.LEADING, gl_panel_grupo.createSequentialGroup().addContainerGap().addComponent(scrollPaneGrupo, -1, 301, 32767))
      );
      gl_panel_grupo.setVerticalGroup(
         gl_panel_grupo.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_grupo.createSequentialGroup().addComponent(scrollPaneGrupo, -1, 404, 32767).addContainerGap())
      );
      panel_grupo.setLayout(gl_panel_grupo);
      GroupLayout gl_panel_sub_seccion = new GroupLayout(panel_sub_seccion);
      gl_panel_sub_seccion.setHorizontalGroup(gl_panel_sub_seccion.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneSubSeccion, -1, 302, 32767));
      gl_panel_sub_seccion.setVerticalGroup(
         gl_panel_sub_seccion.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_sub_seccion.createSequentialGroup().addComponent(scrollPaneSubSeccion, -1, 379, 32767).addContainerGap())
      );
      panel_sub_seccion.setLayout(gl_panel_sub_seccion);
      GroupLayout gl_panel_seccion = new GroupLayout(panel_seccion);
      gl_panel_seccion.setHorizontalGroup(gl_panel_seccion.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneSeccion, -1, 303, 32767));
      gl_panel_seccion.setVerticalGroup(
         gl_panel_seccion.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_seccion.createSequentialGroup().addComponent(scrollPaneSeccion, -1, 379, 32767).addContainerGap())
      );
      panel_seccion.setLayout(gl_panel_seccion);
      this.getContentPane().setLayout(groupLayout);
      ListSelectionModel modeloSeleccion = this.tabla_seccion.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = FamiliasForm.this.tabla_seccion.getSelectedRow();
               if (filaSeleccionada != -1) {
                  FamiliasForm.this.buscarSubSeccion();
               }
            }
         }
      });
      ListSelectionModel modeloSeleccion2 = this.tabla_sub_seccion.getSelectionModel();
      modeloSeleccion2.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = FamiliasForm.this.tabla_sub_seccion.getSelectedRow();
               if (filaSeleccionada != -1) {
                  FamiliasForm.this.buscarGrupo();
               }
            }
         }
      });
      this.btnNuevaSeccion.addActionListener(this);
      this.btnModificarSeccion.addActionListener(this);
      this.btnEliminarSeccion.addActionListener(this);
      SeccionE.cargarSeccion(this, this.modeloSeccion);
      this.btnNuevaSubSeccion.addActionListener(this);
      this.btnModificarSubSeccion.addActionListener(this);
      this.btnEliminarSubSeccion.addActionListener(this);
      this.btnNuevoGrupo.addActionListener(this);
      this.btnModificarGrupo.addActionListener(this);
      this.btnEliminarGrupo.addActionListener(this);
   }

   private void modificarSubSeccion() {
      int filaSeleccionada = this.tabla_sub_seccion.getSelectedRow();
      if (filaSeleccionada == -1) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar una Sub Seccion", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         int v_codigo = Integer.parseInt(
            String.valueOf(this.tabla_sub_seccion.getValueAt(filaSeleccionada, this.tabla_sub_seccion.getColumn("Codigo").getModelIndex()))
         );
         String v_seccion = String.valueOf(this.tabla_sub_seccion.getValueAt(filaSeleccionada, this.tabla_sub_seccion.getColumn("Nombre").getModelIndex()));
         DialogModificacion ventana = new DialogModificacion(v_codigo, v_seccion, "Sub Seccion", filaSeleccionada);
         ventana.setLocationRelativeTo(this);
         ventana.setModal(true);
         ventana.setVisible(true);
         this.tabla_sub_seccion.setValueAt(ventana.getTxt_usuario().getText(), filaSeleccionada, 1);
      }
   }

   private void agregarNuevaSubSeccion() {
      int filaSeleccionada = this.tabla_seccion.getSelectedRow();
      if (filaSeleccionada == -1) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar una Seccion", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         int v_codigo = Integer.parseInt(
            String.valueOf(this.tabla_seccion.getValueAt(filaSeleccionada, this.tabla_seccion.getColumn("Codigo").getModelIndex()))
         );
         BuscadordeCodigos buscador = new BuscadordeCodigos("Sub Seccion");
         buscador.setModal(true);
         buscador.setVisible(true);
         if (buscador.getCodigo() != null && !buscador.getCodigo().trim().isEmpty()) {
            SubSeccionE entidadSeccion = this.CargarEntidadesSubSeccion(v_codigo, buscador.getCodigo());
            if (entidadSeccion != null) {
               int codigo = SubSeccionE.insertarSubSecciones(entidadSeccion, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Sub Seccion insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.modeloSubSeccion.addRow(new Object[]{entidadSeccion.getCod_seccion(), buscador.getCodigo()});
               }
            }
         }
      }
   }

   private void eliminarGrupo() {
      int filaSeleccionada = this.tabla_grupo.getSelectedRow();
      if (filaSeleccionada == -1) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar un Grupo", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         int v_codigo = Integer.parseInt(String.valueOf(this.tabla_grupo.getValueAt(filaSeleccionada, this.tabla_grupo.getColumn("Codigo").getModelIndex())));
         DialogoMessagebox d = new DialogoMessagebox("Desea eliminar el registro ?");
         d.setLocationRelativeTo(this);
         d.setVisible(true);
         if (d.isResultadoEncontrado()) {
            int codigo = GrupoE.eliminarGrupo(v_codigo, this);
            if (codigo != 0) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro eliminado correctamente... ", "correcto");
               rs.setLocationRelativeTo(this);
               rs.setVisible(true);
               this.modeloGrupo.removeRow(filaSeleccionada);
            }
         }
      }
   }

   private void modificarGrupo() {
      int filaSeleccionada = this.tabla_grupo.getSelectedRow();
      if (filaSeleccionada == -1) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar un Grupo", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         int v_codigo = Integer.parseInt(String.valueOf(this.tabla_grupo.getValueAt(filaSeleccionada, this.tabla_grupo.getColumn("Codigo").getModelIndex())));
         String v_seccion = String.valueOf(this.tabla_grupo.getValueAt(filaSeleccionada, this.tabla_grupo.getColumn("Nombre").getModelIndex()));
         DialogModificacion ventana = new DialogModificacion(v_codigo, v_seccion, "Grupo", filaSeleccionada);
         ventana.setLocationRelativeTo(this);
         ventana.setModal(true);
         ventana.setVisible(true);
         this.tabla_grupo.setValueAt(ventana.getTxt_usuario().getText(), filaSeleccionada, 1);
      }
   }

   private void agregarNuevoGrupo() {
      int filaSeleccionada = this.tabla_sub_seccion.getSelectedRow();
      if (filaSeleccionada == -1) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar una Sub Seccion", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         int v_codigo = Integer.parseInt(
            String.valueOf(this.tabla_sub_seccion.getValueAt(filaSeleccionada, this.tabla_sub_seccion.getColumn("Codigo").getModelIndex()))
         );
         BuscadordeCodigos buscador = new BuscadordeCodigos("Grupo");
         buscador.setModal(true);
         buscador.setVisible(true);
         if (buscador.getCodigo() != null && !buscador.getCodigo().trim().isEmpty()) {
            GrupoE entidadSeccion = this.CargarEntidadesGrupo(v_codigo, buscador.getCodigo());
            if (entidadSeccion != null) {
               int codigo = GrupoE.insertarGrupos(entidadSeccion, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Grupo insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.modeloGrupo.addRow(new Object[]{entidadSeccion.getCod_grupo(), buscador.getCodigo()});
               }
            }
         }
      }
   }

   private void buscarGrupo() {
      int filaSeleccionada = this.tabla_sub_seccion.getSelectedRow();
      if (filaSeleccionada == -1) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar una Sub Seccion", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         int v_codigo = Integer.parseInt(
            String.valueOf(this.tabla_sub_seccion.getValueAt(filaSeleccionada, this.tabla_sub_seccion.getColumn("Codigo").getModelIndex()))
         );
         TablaBuscador.eliminarFilasConWhile(this.modeloGrupo);
         GrupoE.cargarGrupo(v_codigo, this.modeloGrupo, this);
      }
   }

   private void buscarSubSeccion() {
      int filaSeleccionada = this.tabla_seccion.getSelectedRow();
      if (filaSeleccionada == -1) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar una Seccion", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         int v_codigo = Integer.parseInt(
            String.valueOf(this.tabla_seccion.getValueAt(filaSeleccionada, this.tabla_seccion.getColumn("Codigo").getModelIndex()))
         );
         TablaBuscador.eliminarFilasConWhile(this.modeloSubSeccion);
         TablaBuscador.eliminarFilasConWhile(this.modeloGrupo);
         SubSeccionE.cargarSubSeccion(v_codigo, this.modeloSubSeccion, this);
      }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.btnNuevaSeccion) {
         this.agregarSeccion();
      }

      if (e.getSource() == this.btnModificarSeccion) {
         this.modificarSeccion();
      }

      if (e.getSource() == this.btnEliminarSeccion) {
         this.eliminarSeccion();
      }

      if (e.getSource() == this.btnNuevaSubSeccion) {
         this.agregarNuevaSubSeccion();
      }

      if (e.getSource() == this.btnModificarSubSeccion) {
         this.modificarSubSeccion();
      }

      if (e.getSource() == this.btnEliminarSubSeccion) {
         this.eliminarSubSeccion();
      }

      if (e.getSource() == this.btnEliminarGrupo) {
         this.eliminarGrupo();
      }

      if (e.getSource() == this.btnModificarGrupo) {
         this.modificarGrupo();
      }

      if (e.getSource() == this.btnNuevoGrupo) {
         this.agregarNuevoGrupo();
      }
   }

   private void eliminarSubSeccion() {
      int filaSeleccionada = this.tabla_sub_seccion.getSelectedRow();
      if (filaSeleccionada == -1) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar una Sub Seccion", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         int v_codigo = Integer.parseInt(
            String.valueOf(this.tabla_sub_seccion.getValueAt(filaSeleccionada, this.tabla_sub_seccion.getColumn("Codigo").getModelIndex()))
         );
         DialogoMessagebox d = new DialogoMessagebox("Desea eliminar el registro ?");
         d.setLocationRelativeTo(this);
         d.setVisible(true);
         if (d.isResultadoEncontrado()) {
            int codigo = SubSeccionE.eliminarSubSeccion(v_codigo, this);
            if (codigo != 0) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro eliminado correctamente... ", "correcto");
               rs.setLocationRelativeTo(this);
               rs.setVisible(true);
               this.modeloSubSeccion.removeRow(filaSeleccionada);
            }
         }
      }
   }

   private void eliminarSeccion() {
      int filaSeleccionada = this.tabla_seccion.getSelectedRow();
      if (filaSeleccionada == -1) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar una Seccion", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         int v_codigo = Integer.parseInt(
            String.valueOf(this.tabla_seccion.getValueAt(filaSeleccionada, this.tabla_seccion.getColumn("Codigo").getModelIndex()))
         );
         DialogoMessagebox d = new DialogoMessagebox("Desea eliminar el registro ?");
         d.setLocationRelativeTo(this);
         d.setVisible(true);
         if (d.isResultadoEncontrado()) {
            int codigo = SeccionE.eliminarSeccion(v_codigo, this);
            if (codigo != 0) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro eliminado correctamente... ", "correcto");
               rs.setLocationRelativeTo(this);
               rs.setVisible(true);
               this.modeloSeccion.removeRow(filaSeleccionada);
            }
         }
      }
   }

   private void modificarSeccion() {
      int filaSeleccionada = this.tabla_seccion.getSelectedRow();
      if (filaSeleccionada == -1) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar una Seccion", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         int v_codigo = Integer.parseInt(
            String.valueOf(this.tabla_seccion.getValueAt(filaSeleccionada, this.tabla_seccion.getColumn("Codigo").getModelIndex()))
         );
         String v_seccion = String.valueOf(this.tabla_seccion.getValueAt(filaSeleccionada, this.tabla_seccion.getColumn("Nombre").getModelIndex()));
         DialogModificacion ventana = new DialogModificacion(v_codigo, v_seccion, "Seccion", filaSeleccionada);
         ventana.setLocationRelativeTo(this);
         ventana.setModal(true);
         ventana.setVisible(true);
         this.tabla_seccion.setValueAt(ventana.getTxt_usuario().getText(), filaSeleccionada, 1);
      }
   }

   private void agregarSeccion() {
      BuscadordeCodigos buscador = new BuscadordeCodigos("Seccion");
      buscador.setModal(true);
      buscador.setVisible(true);
      if (buscador.getCodigo() != null && !buscador.getCodigo().trim().isEmpty()) {
         SeccionE entidadSeccion = this.CargarEntidadesSeccion(0, buscador.getCodigo());
         if (entidadSeccion != null) {
            int codigo = SeccionE.insertarSecciones(entidadSeccion, this);
            if (codigo != 0) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("Seccion insertado correctamente", "correcto");
               rs.setLocationRelativeTo(this);
               rs.setVisible(true);
               this.modeloSeccion.addRow(new Object[]{entidadSeccion.getCod_seccion(), buscador.getCodigo()});
            }
         }
      }
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         FamiliasForm frame = new FamiliasForm();

         @Override
         public void run() {
            try {
               this.frame.setVisible(true);
            } catch (Exception var2) {
               LogErrores.errores(var2, "Error al iniciar el Formulario", this.frame);
               var2.printStackTrace();
            }
         }
      });
   }
}
