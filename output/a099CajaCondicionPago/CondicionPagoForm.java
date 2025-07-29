package a099CajaCondicionPago;

import java.awt.EventQueue;
import javax.swing.ButtonGroup;
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
import utilidades.RadioBoton;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadorTablaCargar;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class CondicionPagoForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(25);
   private static final long serialVersionUID = 1L;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private CheckPadre chckActivo;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_buscador;
   private RadioBoton rdbtnTransferencia;
   private RadioBoton rdbtnDeposito;
   private RadioBoton rdbtnCheque;
   private RadioBoton rdbtnTarjeta;
   private RadioBoton rdbtnEfectivo;
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
         CondicionPagoE b = CondicionPagoE.buscarCondicionPago(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarCondicionesPagos(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         CondicionPagoE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = CondicionPagoE.insertarCondicionPago(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = CondicionPagoE.actualizarCondicionesPagos(entidad, this);
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
            CondicionPagoE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = CondicionPagoE.eliminarCondicionPago(ent, this);
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

   public void cargarCondicionesPagos(CondicionPagoE e) {
      this.txt_codigo.setValue(e.getCod_condicion());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_condicion()));
      this.txt_nombre.setValue(e.getNombre_condicion());
      if (e.getTipo() == 0) {
         this.rdbtnEfectivo.setSelected(true);
      } else if (e.getTipo() == 1) {
         this.rdbtnTarjeta.setSelected(true);
      } else if (e.getTipo() == 2) {
         this.rdbtnCheque.setSelected(true);
      } else if (e.getTipo() == 3) {
         this.rdbtnTransferencia.setSelected(true);
      } else if (e.getTipo() == 4) {
         this.rdbtnDeposito.setSelected(true);
      }

      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public CondicionPagoE CargarEntidades() {
      int estado = 0;
      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      int tipo = 0;
      if (this.rdbtnEfectivo.isSelected()) {
         tipo = 0;
      } else if (this.rdbtnTarjeta.isSelected()) {
         tipo = 1;
      } else if (this.rdbtnCheque.isSelected()) {
         tipo = 2;
      } else if (this.rdbtnTransferencia.isSelected()) {
         tipo = 3;
      } else if (this.rdbtnDeposito.isSelected()) {
         tipo = 4;
      }

      try {
         return new CondicionPagoE(Integer.parseInt(this.txt_codigo.getText()), this.txt_nombre.getText(), estado, tipo);
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
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().equals("0")) {
            this.rdbtnEfectivo.setSelected(true);
         } else if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().equals("1")) {
            this.rdbtnTarjeta.setSelected(true);
         } else if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().equals("2")) {
            this.rdbtnCheque.setSelected(true);
         } else if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().equals("3")) {
            this.rdbtnTransferencia.setSelected(true);
         } else if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().equals("4")) {
            this.rdbtnDeposito.setSelected(true);
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
      this.rdbtnEfectivo.setSelected(true);
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public CondicionPagoForm() {
      this.setTitle("Registro de Condicion de Pago");
      this.setBounds(100, 100, 392, 432);
      PanelPadre panel_datos = new PanelPadre("Datos Basicos");
      PanelPadre panel_tipo = new PanelPadre("Tipo");
      PanelPadre panel_buscador = new PanelPadre("Buscador");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.txt_nombre = new LimiteTextField(25);
      this.txt_buscador = new LimiteTextField(25);
      this.chckActivo = new CheckPadre("Activo");
      this.rdbtnEfectivo = new RadioBoton("Efectivo");
      this.rdbtnTarjeta = new RadioBoton("Tarjeta");
      this.rdbtnCheque = new RadioBoton("Cheque");
      this.rdbtnTransferencia = new RadioBoton("Transferencia");
      this.rdbtnDeposito = new RadioBoton("Deposito");
      ButtonGroup radioOpciones = new ButtonGroup();
      radioOpciones.add(this.rdbtnCheque);
      radioOpciones.add(this.rdbtnDeposito);
      radioOpciones.add(this.rdbtnEfectivo);
      radioOpciones.add(this.rdbtnTarjeta);
      radioOpciones.add(this.rdbtnTransferencia);
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "Tipo"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "CondicionesPagos", this);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_buscador, -1, 372, 32767)
                        .addComponent(panel_datos, -1, 380, 32767)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               Alignment.LEADING,
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, 155, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -2, 206, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
                  .addContainerGap(36, 32767)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_buscador.createSequentialGroup().addGap(88).addComponent(this.txt_buscador, -2, 255, -2))
                        .addGroup(gl_panel_buscador.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 357, 32767))
                  )
                  .addContainerGap()
            )
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPane, -1, 162, 32767)
                  .addContainerGap()
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblCodigo, -1, -1, 32767)
                                    .addComponent(lblNombre, -1, 69, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(this.chckActivo, -2, 93, -2)
                                    )
                                    .addComponent(this.txt_nombre, -2, 255, -2)
                              )
                        )
                        .addComponent(panel_tipo, -2, 359, -2)
                  )
                  .addContainerGap(18, 32767)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(this.txt_codigo, -2, 25, -2)
                                    .addComponent(this.chckActivo, -2, 16, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_nombre, -2, 25, -2)
                        )
                        .addGroup(gl_panel_datos.createSequentialGroup().addComponent(lblCodigo, -2, 25, -2).addGap(12).addComponent(lblNombre, -2, 25, -2))
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_tipo, -2, 55, -2)
                  .addContainerGap(92, 32767)
            )
      );
      GroupLayout gl_panel_tipo = new GroupLayout(panel_tipo);
      gl_panel_tipo.setHorizontalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_tipo.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.rdbtnTransferencia, -2, -1, -2)
                        .addComponent(this.rdbtnEfectivo, -2, 82, -2)
                  )
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(
                     gl_panel_tipo.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_tipo.createSequentialGroup()
                              .addComponent(this.rdbtnTarjeta, -2, 82, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.rdbtnCheque, -2, 82, -2)
                        )
                        .addComponent(this.rdbtnDeposito, -2, 93, -2)
                  )
                  .addContainerGap(74, 32767)
            )
      );
      gl_panel_tipo.setVerticalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_tipo.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_tipo.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.rdbtnCheque, -2, 16, -2)
                              .addComponent(this.rdbtnTarjeta, -2, 16, -2)
                        )
                        .addGroup(
                           gl_panel_tipo.createSequentialGroup()
                              .addComponent(this.rdbtnEfectivo, -2, 16, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addGroup(
                                 gl_panel_tipo.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(this.rdbtnTransferencia, -2, 16, -2)
                                    .addComponent(this.rdbtnDeposito, -2, 16, -2)
                              )
                        )
                  )
                  .addContainerGap(12, 32767)
            )
      );
      panel_tipo.setLayout(gl_panel_tipo);
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "CondicionesPagos", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = CondicionPagoForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  CondicionPagoForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CondicionPagoForm frame = new CondicionPagoForm();

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
