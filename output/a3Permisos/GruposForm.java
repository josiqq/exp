package a3Permisos;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import utilidadesTabla.BuscadordeCodigos;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaConParametro;
import utilidadesVentanas.JinternalPadrePermisos;

public class GruposForm extends JinternalPadrePermisos implements ActionListener, MouseListener {
   private static final long serialVersionUID = 1L;
   private ModeloTablaDefecto modeloGrupo;
   private ModeloTablaDefecto modeloPermisos;
   private ModeloTablaDefecto modeloUsuarios;
   private TablaConParametro tablaGrupo;
   private TablaConParametro tablaPermisos;
   private TablaConParametro tablaUsuarios;
   private BotonPadre btnNuevoGrupo;
   private BotonPadre btnGrabarPermiso;
   private BotonPadre btnEliminarGrupo;
   private BotonPadre btnActualizarGrupo;
   private BotonPadre btnNuevoUsuario;
   private BotonPadre btnModificarUsuario;
   private BotonPadre btnEliminarUsuario;

   public GruposForm() {
      this.getContentPane().setBackground(new Color(200, 200, 200));
      this.setTitle("Permisos del Sistema");
      this.setBounds(100, 100, 1082, 540);
      PanelPadre panel_botones = new PanelPadre("");
      String[] cabeceraGrupo = new String[]{"Nombre Grupo", "Estado"};
      String[] cabeceraPermiso = new String[]{"Menu", "Ver", "Insertar", "Modificar", "Eliminar", "Tipo"};
      String[] cabeceraUsuario = new String[]{"Nombre Usuario", "Estado"};
      this.modeloPermisos = new ModeloTablaDefecto(cabeceraPermiso);
      this.modeloGrupo = new ModeloTablaDefecto(cabeceraGrupo);
      this.modeloUsuarios = new ModeloTablaDefecto(cabeceraUsuario);
      this.tablaGrupo = new TablaConParametro(this.modeloGrupo, "GrupoUsuario", this);
      this.tablaPermisos = new TablaConParametro(this.modeloPermisos, "Permisos", this);
      this.tablaUsuarios = new TablaConParametro(this.modeloUsuarios, "Usuario", this);
      JScrollPane scrollPanelGrupo = new JScrollPane();
      scrollPanelGrupo.setViewportView(this.tablaGrupo);
      JScrollPane scrollPanelPermiso = new JScrollPane();
      scrollPanelPermiso.setViewportView(this.tablaPermisos);
      JScrollPane scrollPanelUsuario = new JScrollPane();
      scrollPanelUsuario.setViewportView(this.tablaUsuarios);
      this.btnNuevoGrupo = new BotonPadre("<html>Nuevo<br>Grupo</html>", "nuevoGrupo.png");
      this.btnNuevoGrupo.addActionListener(this);
      this.btnActualizarGrupo = new BotonPadre("<html>Actualizar<br>Grupo</html>", "updateGrupo.png");
      this.btnActualizarGrupo.addActionListener(this);
      this.btnEliminarGrupo = new BotonPadre("<html>Eliminar<br>Grupo</html>", "eliminarGrupo.png");
      this.btnEliminarGrupo.addActionListener(this);
      this.btnGrabarPermiso = new BotonPadre("<html>Guardar<br>Permiso</html>", "guardar.png");
      this.btnGrabarPermiso.addActionListener(this);
      this.btnNuevoUsuario = new BotonPadre("<html>Nuevo<br>Usuario</html>", "nuevoUsuario.png");
      this.btnNuevoUsuario.addActionListener(this);
      this.btnModificarUsuario = new BotonPadre("<html>Modificar<br>Usuario</html>", "guardar.gif");
      this.btnModificarUsuario.addActionListener(this);
      this.btnEliminarUsuario = new BotonPadre("<html>Eliminar<br>Usuario</html>", "eliminarUsuario.png");
      this.btnEliminarUsuario.addActionListener(this);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(scrollPanelGrupo, -2, 229, -2)
                              .addGap(6)
                              .addComponent(scrollPanelUsuario, -2, 285, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(scrollPanelPermiso, -2, 543, -2)
                        )
                        .addGroup(groupLayout.createSequentialGroup().addGap(115).addComponent(panel_botones, -2, 804, -2))
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           groupLayout.createParallelGroup(Alignment.BASELINE)
                              .addComponent(scrollPanelGrupo, -2, 424, -2)
                              .addComponent(scrollPanelUsuario, -2, 424, -2)
                        )
                        .addComponent(scrollPanelPermiso, -2, 424, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_botones, -2, 64, -2)
                  .addGap(222)
            )
      );
      GroupLayout gl_panel_botones = new GroupLayout(panel_botones);
      gl_panel_botones.setHorizontalGroup(
         gl_panel_botones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_botones.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.btnNuevoGrupo, -2, 105, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnActualizarGrupo, -2, 105, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnEliminarGrupo, -2, 105, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnGrabarPermiso, -2, 105, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnNuevoUsuario, -2, 105, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnModificarUsuario, -2, 105, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnEliminarUsuario, -2, 105, -2)
                  .addContainerGap(413, 32767)
            )
      );
      gl_panel_botones.setVerticalGroup(
         gl_panel_botones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_botones.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_botones.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_botones.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.btnNuevoGrupo, -2, 45, -2)
                              .addComponent(this.btnActualizarGrupo, -2, 40, -2)
                              .addComponent(this.btnEliminarGrupo, -2, 45, -2)
                        )
                        .addGroup(
                           gl_panel_botones.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.btnGrabarPermiso, -2, 41, -2)
                              .addComponent(this.btnNuevoUsuario, -2, 44, -2)
                              .addComponent(this.btnModificarUsuario, -2, 41, -2)
                              .addComponent(this.btnEliminarUsuario, -2, 45, -2)
                        )
                  )
                  .addContainerGap(20, 32767)
            )
      );
      panel_botones.setLayout(gl_panel_botones);
      this.getContentPane().setLayout(groupLayout);
      ListSelectionModel modeloSeleccion = this.tablaGrupo.getSelectionModel();
      modeloSeleccion.addListSelectionListener(
         new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
               if (!e.getValueIsAdjusting()) {
                  int filaSeleccionada = GruposForm.this.tablaGrupo.getSelectedRow();
                  if (filaSeleccionada != -1) {
                     TablaConParametro.eliminarFilasConWhile(GruposForm.this.modeloPermisos);
                     TablaConParametro.eliminarFilasConWhile(GruposForm.this.modeloUsuarios);
                     GruposE.buscarPermisos(
                        String.valueOf(
                           GruposForm.this.tablaGrupo
                              .getValueAt(GruposForm.this.tablaGrupo.getSelectedRow(), GruposForm.this.tablaGrupo.getColumn("Nombre Grupo").getModelIndex())
                        ),
                        GruposForm.this,
                        GruposForm.this.modeloPermisos
                     );
                     UsuariosE.cargarUsuarios(
                        String.valueOf(
                           GruposForm.this.tablaGrupo
                              .getValueAt(GruposForm.this.tablaGrupo.getSelectedRow(), GruposForm.this.tablaGrupo.getColumn("Nombre Grupo").getModelIndex())
                        ),
                        GruposForm.this,
                        GruposForm.this.modeloUsuarios
                     );
                  }
               }
            }
         }
      );
      GruposE.cargarGrupo(this, this.modeloGrupo);
      this.tablaGrupo.addMouseListener(this);
   }

   private GruposE CargarEntidades(String usuario) {
      try {
         return new GruposE(usuario, 1);
      } catch (Exception var3) {
         LogErrores.errores(var3, "Error al Iniciar Recuperar Entidad de Marcas...", this);
         return null;
      }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.btnEliminarUsuario) {
         DialogoMessagebox d = new DialogoMessagebox("Desea eliminar Usuario ?");
         d.setLocationRelativeTo(this);
         d.setVisible(true);
         if (d.isResultadoEncontrado()) {
            int filaSeleccionada = this.tablaUsuarios.getSelectedRow();
            if (filaSeleccionada == -1) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar un Usuario", "error");
               rs.setLocationRelativeTo(this);
               rs.setVisible(true);
            } else {
               String usuarioNombre = String.valueOf(
                  this.tablaUsuarios.getValueAt(filaSeleccionada, this.tablaUsuarios.getColumn("Nombre Usuario").getModelIndex())
               );
               this.tablaUsuarios.clearSelection();
               int codigo = UsuariosE.eliminarUsuario(usuarioNombre, null);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Usuario eliminado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
               }
            }
         }
      }

      if (e.getSource() == this.btnModificarUsuario) {
         int filaSeleccionada = this.tablaUsuarios.getSelectedRow();
         if (filaSeleccionada == -1) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar un Usuario", "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
         } else {
            String usuarioNombre = String.valueOf(
               this.tablaUsuarios.getValueAt(filaSeleccionada, this.tablaUsuarios.getColumn("Nombre Usuario").getModelIndex())
            );
            filaSeleccionada = this.tablaGrupo.getSelectedRow();
            String grupo = String.valueOf(this.tablaGrupo.getValueAt(filaSeleccionada, this.tablaGrupo.getColumn("Nombre Grupo").getModelIndex()));
            this.tablaUsuarios.clearSelection();
            UsuariosForm usuario = new UsuariosForm(grupo, usuarioNombre, 1);
            usuario.setLocationRelativeTo(this);
            usuario.setVisible(true);
         }
      }

      if (e.getSource() == this.btnNuevoUsuario) {
         int filaSeleccionada = this.tablaGrupo.getSelectedRow();
         if (filaSeleccionada == -1) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar un Grupo", "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
         } else {
            String grupo = String.valueOf(this.tablaGrupo.getValueAt(filaSeleccionada, this.tablaGrupo.getColumn("Nombre Grupo").getModelIndex()));
            this.tablaGrupo.clearSelection();
            UsuariosForm usuario = new UsuariosForm(grupo, "", 0);
            usuario.setLocationRelativeTo(this);
            usuario.setVisible(true);
         }
      }

      if (e.getSource() == this.btnEliminarGrupo) {
         int filaSeleccionada = this.tablaGrupo.getSelectedRow();
         if (filaSeleccionada == -1) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar un Grupo", "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
         } else {
            String grupo = String.valueOf(this.tablaGrupo.getValueAt(filaSeleccionada, this.tablaGrupo.getColumn("Nombre Grupo").getModelIndex()));
            this.tablaGrupo.clearSelection();
            DialogoMessagebox d = new DialogoMessagebox("Desea eliminar Grupo ?");
            d.setLocationRelativeTo(this);
            d.setVisible(true);
            if (d.isResultadoEncontrado()) {
               int codigo = GruposE.eliminarGrupos(grupo, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Grupo eliminado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
               }
            }
         }
      }

      if (e.getSource() == this.btnActualizarGrupo) {
         int filaSeleccionada = this.tablaGrupo.getSelectedRow();
         if (filaSeleccionada == -1) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar un Grupo", "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
         } else {
            DialogoMessagebox d = new DialogoMessagebox("Desea actualizar Grupos ?");
            d.setLocationRelativeTo(this);
            d.setVisible(true);
            if (d.isResultadoEncontrado()) {
               int codigo = GruposE.actualizarGrupos(this.tablaGrupo, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Grupos actualizados correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
               }
            }
         }
      }

      if (e.getSource() == this.btnGrabarPermiso) {
         int filaSeleccionada = this.tablaGrupo.getSelectedRow();
         if (filaSeleccionada == -1) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Debes de Seleccionar un Grupo", "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
         } else {
            DialogoMessagebox d = new DialogoMessagebox("Desea actualizar Permisos ?");
            d.setLocationRelativeTo(this);
            d.setVisible(true);
            if (d.isResultadoEncontrado()) {
               int codigo = GruposE.actualizarPermisos(
                  String.valueOf(this.tablaGrupo.getValueAt(filaSeleccionada, this.tablaGrupo.getColumn("Nombre Grupo").getModelIndex())),
                  this.tablaPermisos,
                  this
               );
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Permisos actualizado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
               }
            }
         }
      }

      if (e.getSource() == this.btnNuevoGrupo) {
         BuscadordeCodigos buscador = new BuscadordeCodigos("Grupo");
         buscador.setModal(true);
         buscador.setVisible(true);
         if (buscador.getCodigo() != null && !buscador.getCodigo().trim().isEmpty()) {
            GruposE entidadGrupo = this.CargarEntidades(buscador.getCodigo());
            if (entidadGrupo != null) {
               int codigo = GruposE.insertarGrupos(entidadGrupo, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Grupo insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.modeloGrupo.addRow(new Object[]{buscador.getCodigo(), false});
               }
            }
         }
      }
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      e.getSource();
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            GruposForm frame = new GruposForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
               var3.printStackTrace();
            }
         }
      });
   }

   @Override
   public void mousePressed(MouseEvent e) {
   }

   @Override
   public void mouseReleased(MouseEvent e) {
   }

   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }
}
