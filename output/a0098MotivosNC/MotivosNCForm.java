package a0098MotivosNC;

import java.awt.EventQueue;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utilidades.CheckPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidades.PanelPadre;
import utilidades.RadioBoton;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadorTablaCargar;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class MotivosNCForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(25);
   private static final long serialVersionUID = 1L;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_buscador;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private CheckPadre chckbxActivo;
   private RadioBoton rdbtnTodos;
   private RadioBoton rdbtnCantidad;
   private RadioBoton rdbtnDescuento;
   private ModeloTablaDefecto modeloTabla;
   private BuscadorTabla tabla;
   public int SW;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         MotivosNCE b = MotivosNCE.buscarMotivoNC(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarMotivosNC(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         MotivosNCE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = MotivosNCE.insertarMotivoNC(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = MotivosNCE.actualizarMotivoNC(entidad, this);
            if (codigo != 0) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro actualizado correctamente...", "correcto");
               rs.setLocationRelativeTo(this);
               rs.setVisible(true);
               this.inicializarObjetos();
            }
         }
      }
   }

   @Override
   public void eliminar() {
      if (Integer.parseInt(this.txt_codigo.getText()) == this.SW && this.SW != 0) {
         DialogoMessagebox d = new DialogoMessagebox("Desea eliminar el registro ?");
         d.setLocationRelativeTo(this);
         d.setVisible(true);
         if (d.isResultadoEncontrado()) {
            MotivosNCE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = MotivosNCE.eliminarMotivosNC(ent, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro eliminado correctamente... ", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         }
      }
   }

   public void cargarMotivosNC(MotivosNCE e) {
      this.txt_codigo.setValue(e.getCod_motivo());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_motivo()));
      this.txt_nombre.setValue(e.getNombre_motivo());
      if (e.getTipo() == 0) {
         this.rdbtnTodos.setSelected(true);
      } else if (e.getTipo() == 1) {
         this.rdbtnCantidad.setSelected(true);
      } else if (e.getTipo() == 2) {
         this.rdbtnDescuento.setSelected(true);
      }

      if (e.getEstado() == 0) {
         this.chckbxActivo.setSelected(false);
      } else {
         this.chckbxActivo.setSelected(true);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public MotivosNCE CargarEntidades() {
      int estado = 0;
      int tipo = 0;
      if (this.rdbtnCantidad.isSelected()) {
         tipo = 1;
      } else if (this.rdbtnDescuento.isSelected()) {
         tipo = 2;
      }

      if (this.chckbxActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new MotivosNCE(Integer.parseInt(this.txt_codigo.getText()), this.txt_nombre.getText(), estado, tipo);
      } catch (NumberFormatException var4) {
         LogErrores.errores(var4, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var5) {
         LogErrores.errores(var5, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   public void buscarTabla() {
      int filaSeleccionada = this.tabla.getSelectedRow();
      if (filaSeleccionada != -1) {
         this.txt_codigo.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString());
         this.SW = Integer.parseInt(String.valueOf(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString()));
         this.txt_nombre.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nombre").getModelIndex()).toString());
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Estado").getModelIndex()).toString().equals("true")) {
            this.chckbxActivo.setSelected(true);
         } else {
            this.chckbxActivo.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().trim().equals("0")) {
            this.rdbtnTodos.setSelected(true);
         } else if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().trim().equals("1")) {
            this.rdbtnCantidad.setSelected(true);
         } else if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().trim().equals("2")) {
            this.rdbtnDescuento.setSelected(true);
         }

         this.tabla.requestFocusInWindow();
      }
   }

   private void inicializarObjetos() {
      this.SW = 0;
      this.chckbxActivo.setSelected(true);
      TablaBuscador.eliminarFilasConWhile(this.modeloTabla);
      this.txt_buscador.setValue("");
      this.txt_codigo.setValue(0);
      this.txt_nombre.setValue("");
      this.rdbtnCantidad.setSelected(false);
      this.rdbtnDescuento.setSelected(false);
      this.rdbtnTodos.setSelected(true);
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public MotivosNCForm() {
      this.setTitle("Registro de Motivos NC");
      this.setBounds(100, 100, 378, 425);
      PanelPadre contentPane = new PanelPadre("");
      PanelPadre panel_cabecera = new PanelPadre("");
      PanelPadre panel_detalle = new PanelPadre("");
      PanelPadre panel_tipo = new PanelPadre("Tipo");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("New label", "lineas");
      this.txt_buscador = new LimiteTextField(25);
      this.txt_nombre = new LimiteTextField(25);
      this.chckbxActivo = new CheckPadre("Activo");
      this.rdbtnTodos = new RadioBoton("Todos");
      this.rdbtnCantidad = new RadioBoton("Cantidad");
      this.rdbtnDescuento = new RadioBoton("Descuento");
      ButtonGroup botones = new ButtonGroup();
      botones.add(this.rdbtnCantidad);
      botones.add(this.rdbtnDescuento);
      botones.add(this.rdbtnTodos);
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "Tipo"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "MotivosNC", this);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblLineasRecuperadas, -2, 119, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                  .addContainerGap(345, 32767)
            )
            .addGroup(
               Alignment.LEADING,
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel_detalle, Alignment.LEADING, 0, 0, 32767)
                        .addComponent(panel_cabecera, Alignment.LEADING, -1, 363, 32767)
                  )
                  .addContainerGap(19, 32767)
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGap(6)
                  .addComponent(panel_cabecera, -2, 158, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -2, 183, -2)
                  .addGap(14)
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_detalle.createSequentialGroup()
                  .addGroup(
                     gl_panel_detalle.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_detalle.createSequentialGroup().addGap(26).addComponent(this.txt_buscador, -2, 255, -2))
                        .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -2, 344, -2))
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_detalle.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPane, -1, 124, 32767)
                  .addContainerGap()
            )
      );
      panel_detalle.setLayout(gl_panel_detalle);
      GroupLayout gl_panel_cabecera = new GroupLayout(panel_cabecera);
      gl_panel_cabecera.setHorizontalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel_tipo, Alignment.LEADING, 0, 0, 32767)
                        .addGroup(
                           Alignment.LEADING,
                           gl_panel_cabecera.createSequentialGroup()
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblNombre, -1, -1, 32767)
                                    .addComponent(lblCodigo, -1, 72, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_cabecera.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 41, -2)
                                          .addGap(18)
                                          .addComponent(this.chckbxActivo, -2, 93, -2)
                                    )
                                    .addComponent(this.txt_nombre, -2, 255, -2)
                              )
                        )
                  )
                  .addContainerGap(160, 32767)
            )
      );
      gl_panel_cabecera.setVerticalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addGap(16)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblCodigo, -2, 25, -2)
                        .addComponent(this.txt_codigo, -2, 25, -2)
                        .addComponent(this.chckbxActivo, -2, 16, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.BASELINE).addComponent(lblNombre, -2, 25, -2).addComponent(this.txt_nombre, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_tipo, -2, 51, -2)
                  .addContainerGap(37, 32767)
            )
      );
      GroupLayout gl_panel_tipo = new GroupLayout(panel_tipo);
      gl_panel_tipo.setHorizontalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.rdbtnTodos, -2, 103, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.rdbtnCantidad, -2, 103, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.rdbtnDescuento, -2, 103, -2)
                  .addContainerGap(125, 32767)
            )
      );
      gl_panel_tipo.setVerticalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING,
               gl_panel_tipo.createSequentialGroup()
                  .addContainerGap(12, 32767)
                  .addGroup(
                     gl_panel_tipo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.rdbtnTodos, -2, 16, -2)
                        .addComponent(this.rdbtnCantidad, -2, 16, -2)
                        .addComponent(this.rdbtnDescuento, -2, 16, -2)
                  )
                  .addContainerGap()
            )
      );
      panel_tipo.setLayout(gl_panel_tipo);
      panel_cabecera.setLayout(gl_panel_cabecera);
      contentPane.setLayout(gl_contentPane);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "MotivosNC", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = MotivosNCForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  MotivosNCForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               MotivosNCForm frame = new MotivosNCForm();
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
   }
}
