package a0099Motivos;

import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utilidades.CheckPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidades.PanelPadre;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadorTablaCargar;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class MotivosForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(25);
   private static final long serialVersionUID = 1L;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_buscador;
   private CheckPadre chckCajaChica;
   private CheckPadre chckGastos;
   private CheckPadre chckActivo;
   private CheckPadre chckMovimientoCuenta;
   private LabelPrincipal lblLineasRecuperadasTexto;
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
         MotivosE b = MotivosE.buscarMotivo(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarMotivos(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         MotivosE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = MotivosE.insertarMotivo(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = MotivosE.actualizarMotivo(entidad, this);
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
            MotivosE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = MotivosE.eliminarMotivos(ent, this);
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

   public void cargarMotivos(MotivosE e) {
      this.txt_codigo.setValue(e.getCod_motivo());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_motivo()));
      this.txt_nombre.setValue(e.getNombre_motivo());
      if (e.getTesoreria() == 1) {
         this.chckMovimientoCuenta.setSelected(true);
      } else {
         this.chckMovimientoCuenta.setSelected(false);
      }

      if (e.getCajachica() == 1) {
         this.chckCajaChica.setSelected(true);
      } else {
         this.chckCajaChica.setSelected(false);
      }

      if (e.getGastos() == 1) {
         this.chckGastos.setSelected(true);
      } else {
         this.chckGastos.setSelected(false);
      }

      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public MotivosE CargarEntidades() {
      int estado = 0;
      int tesoreria = 0;
      int gastos = 0;
      int cajaChica = 0;
      if (this.chckMovimientoCuenta.isSelected()) {
         tesoreria = 1;
      }

      if (this.chckGastos.isSelected()) {
         gastos = 1;
      }

      if (this.chckCajaChica.isSelected()) {
         cajaChica = 1;
      }

      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new MotivosE(Integer.parseInt(this.txt_codigo.getText()), this.txt_nombre.getText(), estado, tesoreria, gastos, cajaChica);
      } catch (NumberFormatException var6) {
         LogErrores.errores(var6, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var7) {
         LogErrores.errores(var7, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   public void buscarTabla() {
      int filaSeleccionada = this.tabla.getSelectedRow();
      if (filaSeleccionada != -1) {
         this.txt_codigo.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString());
         this.SW = Integer.parseInt(String.valueOf(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString()));
         this.txt_nombre.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nombre").getModelIndex()).toString());
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tesoreria").getModelIndex()).toString().trim().equals("1")) {
            this.chckMovimientoCuenta.setSelected(true);
         } else {
            this.chckMovimientoCuenta.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Gastos").getModelIndex()).toString().trim().equals("1")) {
            this.chckGastos.setSelected(true);
         } else {
            this.chckGastos.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CajaChica").getModelIndex()).toString().trim().equals("1")) {
            this.chckCajaChica.setSelected(true);
         } else {
            this.chckCajaChica.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Estado").getModelIndex()).toString().equals("true")) {
            this.chckActivo.setSelected(true);
         } else {
            this.chckActivo.setSelected(false);
         }

         this.tabla.requestFocusInWindow();
      }
   }

   private void inicializarObjetos() {
      this.SW = 0;
      this.chckActivo.setSelected(true);
      TablaBuscador.eliminarFilasConWhile(this.modeloTabla);
      this.txt_buscador.setValue("");
      this.txt_codigo.setValue(0);
      this.txt_nombre.setValue("");
      this.chckCajaChica.setSelected(false);
      this.chckGastos.setSelected(false);
      this.chckMovimientoCuenta.setSelected(false);
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public MotivosForm() {
      this.setTitle("Registro de Motivos");
      this.setBounds(100, 100, 416, 421);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("");
      PanelPadre panel_tipos = new PanelPadre("");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.txt_buscador = new LimiteTextField(25);
      this.txt_nombre = new LimiteTextField(25);
      this.chckActivo = new CheckPadre("Activo");
      this.chckMovimientoCuenta = new CheckPadre("Movimientos Cuentas");
      this.chckGastos = new CheckPadre("Gastos Proveedores");
      this.chckCajaChica = new CheckPadre("Caja Chica");
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "Tesoreria", "Gastos", "CajaChica"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Motivos", this);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addContainerGap()
                              .addComponent(lblLineasRecuperadas, -2, 120, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                        )
                        .addComponent(panel_datos, -2, 405, -2)
                        .addComponent(panel_buscador, -2, 405, -2)
                  )
                  .addContainerGap()
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, 133, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -2, 217, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, 14, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_buscador.createSequentialGroup().addGap(47).addComponent(this.txt_buscador, -2, 255, -2))
                        .addGroup(gl_panel_buscador.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 383, 32767))
                  )
                  .addContainerGap()
            )
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPane, -2, 162, -2)
                  .addContainerGap(53, 32767)
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_tipos, -2, 382, -2)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING).addComponent(lblNombre, -2, 51, -2).addComponent(lblCodigo, -2, -1, -2)
                              )
                              .addGap(18)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 67, -2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(this.chckActivo, -2, 93, -2)
                                    )
                                    .addComponent(this.txt_nombre, -2, 255, -2)
                              )
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblCodigo, -2, 25, -2)
                        .addComponent(this.txt_codigo, -2, 25, -2)
                        .addComponent(this.chckActivo, -2, 16, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblNombre, -2, 25, -2).addComponent(this.txt_nombre, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_tipos, -2, 58, -2)
            )
      );
      GroupLayout gl_panel_tipos = new GroupLayout(panel_tipos);
      gl_panel_tipos.setHorizontalGroup(
         gl_panel_tipos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipos.createSequentialGroup()
                  .addGap(14)
                  .addGroup(
                     gl_panel_tipos.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.chckCajaChica, -2, 93, -2)
                        .addGroup(
                           gl_panel_tipos.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.chckMovimientoCuenta, -2, 161, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.chckGastos, -2, 171, -2)
                        )
                  )
                  .addContainerGap(33, 32767)
            )
      );
      gl_panel_tipos.setVerticalGroup(
         gl_panel_tipos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_tipos.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.chckMovimientoCuenta, -2, 16, -2)
                        .addComponent(this.chckGastos, -2, 16, -2)
                  )
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.chckCajaChica, -2, 16, -2)
                  .addContainerGap(55, 32767)
            )
      );
      panel_tipos.setLayout(gl_panel_tipos);
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Motivos", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = MotivosForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  MotivosForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            MotivosForm frame = new MotivosForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
               var3.printStackTrace();
            }
         }
      });
   }
}
