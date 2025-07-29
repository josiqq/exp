package a0099NotaEnvio;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.beans.Beans;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextFieldConSQL;
import utilidades.PanelPadre;
import utilidadesConexion.DatabaseConnection;
import utilidadesConexion.FechaActualE;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorProducto;
import utilidadesVentanas.JinternalPadre;

public class NotaEnvioForm extends JinternalPadre {
   List<Integer> codigosAEliminar = new ArrayList<>();
   private TablaDetalleNotaEnvio tabla;
   public int SW;
   private static final long serialVersionUID = 1L;
   private LabelPrincipal lblUltimoRegistroTexto;
   private LabelPrincipal lblTotalLineasTexto;
   private LabelPrincipal lblHoraTexto;
   private LabelPrincipal lblUsuarioAltaTexto;
   private LabelPrincipal lblNombreDepositoDestino;
   private LabelPrincipal lblNombreDepositoOrigen;
   private JDateChooser txt_fecha;
   private LimiteTextFieldConSQL txt_cod_deposito_destino;
   private LimiteTextFieldConSQL txt_cod_deposito_origen;
   private LimiteTextArea txt_observacion;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         NotaEnvioE b = NotaEnvioE.buscarNotaEnvio(Integer.parseInt(this.txt_codigo.getText().trim()), this, this.tabla, 2, 2);
         if (b != null && b.getTransferencia() != 1) {
            this.cargarNotaEnvio(b);
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         if (this.tabla.isEditing()) {
            TableCellEditor editor = this.tabla.getCellEditor();
            if (editor != null) {
               editor.addCellEditorListener(new CellEditorListener() {
                  @Override
                  public void editingStopped(ChangeEvent e) {
                     System.out.println("Edición detenida correctamente.");
                  }

                  @Override
                  public void editingCanceled(ChangeEvent e) {
                  }
               });
               editor.stopCellEditing();
            }
         }

         if (this.txt_cod_deposito_destino.getText().trim().equals(this.txt_cod_deposito_origen.getText().trim())) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Deposito Origen y Destino no pueden ser iguales !", "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.txt_cod_deposito_origen.requestFocusInWindow();
            return;
         }

         if (this.txt_cod_deposito_destino.getText().trim().equals("0") || this.txt_cod_deposito_origen.getText().trim().equals("0")) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Se tienen que cargar Deposito Origen y Deposito Destino !", "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.txt_cod_deposito_origen.requestFocusInWindow();
            return;
         }

         NotaEnvioE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = NotaEnvioE.insertarEnvios(entidad, this.tabla, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = NotaEnvioE.actualizarNotaEnvio(entidad, this, this.tabla, this.codigosAEliminar);
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
            NotaEnvioE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = NotaEnvioE.eliminarNotaEnvio(ent, this);
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

   public void cargarNotaEnvio(NotaEnvioE e) {
      this.txt_cod_deposito_destino.setEnabled(false);
      this.txt_cod_deposito_origen.setEnabled(false);
      this.SW = e.getNro_registro();
      this.lblHoraTexto.setText(e.getHora_envio());
      this.txt_cod_deposito_origen.setValue(e.getCod_deposito_origen());
      this.lblNombreDepositoOrigen.setText(e.getNombre_deposito_origen());
      this.txt_cod_deposito_destino.setValue(e.getCod_deposito_destino());
      this.lblNombreDepositoDestino.setText(e.getNombre_deposito_destino());
      this.txt_observacion.setText(e.getObservacion());
      Date v_fecha = null;

      try {
         v_fecha = new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha());
         this.txt_fecha.setDate(v_fecha);
      } catch (ParseException var4) {
         LogErrores.errores(var4, "Error al Convertir Fecha...", this);
      }

      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblTotalLineasTexto.setText(String.valueOf(formatoLinea.format(this.tabla.getRowCount())));
   }

   public NotaEnvioE CargarEntidades() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha = "";

      try {
         fecha = dateFormat.format(this.txt_fecha.getDate());
      } catch (Exception var6) {
         LogErrores.errores(var6, "Debe de cargar Fecha Inicial Vencimiento...", this);
         this.txt_fecha.requestFocusInWindow();
         return null;
      }

      try {
         return new NotaEnvioE(
            Integer.parseInt(this.txt_codigo.getText().trim().replace(".", "")),
            fecha,
            Integer.parseInt(this.txt_cod_deposito_origen.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_deposito_destino.getText().trim().replace(".", "")),
            this.txt_observacion.getText()
         );
      } catch (NumberFormatException var4) {
         LogErrores.errores(var4, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var5) {
         LogErrores.errores(var5, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   private void inicializarObjetos() {
      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblUltimoRegistroTexto.setText(String.valueOf(formatoLinea.format(NotaEnvioE.ultimoRegistro(this))));
      FechaActualE fechaActual = FechaActualE.buscarFechaactual(this);
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      String fechaActualStr = fechaActual.getFechaActual();

      try {
         Date fechaDate = dateFormat.parse(fechaActualStr);
         this.txt_fecha.setDate(fechaDate);
      } catch (ParseException var6) {
         var6.printStackTrace();
      }

      this.SW = 0;
      this.txt_cod_deposito_origen.setValue(0);
      this.lblNombreDepositoOrigen.setText("");
      this.txt_cod_deposito_destino.setValue(0);
      this.lblNombreDepositoDestino.setText("");
      this.txt_observacion.setText("");
      this.txt_codigo.setValue(0);
      ModeloTablaNotaEnvio modelo = (ModeloTablaNotaEnvio)this.tabla.getModel();
      modelo.deleteRow(this.tabla);
      modelo.addDefaultRow();
      this.lblTotalLineasTexto.setText("0");
      this.txt_codigo.requestFocusInWindow();
      this.lblUsuarioAltaTexto.setText(DatabaseConnection.getInstance().getUsuario());
      this.codigosAEliminar.clear();
      this.txt_cod_deposito_destino.setEnabled(true);
      this.txt_cod_deposito_origen.setEnabled(true);
   }

   public NotaEnvioForm() {
      this.setTitle("Registro de Nota de Envio");
      this.setBounds(100, 100, 952, 562);
      PanelPadre contentPane = new PanelPadre("");
      PanelPadre panel_cabecera = new PanelPadre("");
      PanelPadre panel_detalle = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      LabelPrincipal lblNroRegistro = new LabelPrincipal("Nro. Registro", "label");
      LabelPrincipal lblFecha = new LabelPrincipal("Fecha Envio", "label");
      LabelPrincipal lblDepositoOrigen = new LabelPrincipal("Deposito Origen", "label");
      LabelPrincipal lblDepositoDestino = new LabelPrincipal("Deposito Destino", "label");
      LabelPrincipal lblObservacion = new LabelPrincipal("Observacion", "label");
      LabelPrincipal lblUsuarioAlta = new LabelPrincipal("Usuario Alta", "label");
      LabelPrincipal lblHoraEnvio = new LabelPrincipal("Hora Envio", "label");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro:", "lineas");
      LabelPrincipal lblTotalLineas = new LabelPrincipal("Total Lineas:", "lineas");
      this.lblTotalLineasTexto = new LabelPrincipal("0", "lineas");
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblUsuarioAltaTexto = new LabelPrincipal(0);
      this.lblHoraTexto = new LabelPrincipal(0);
      this.lblNombreDepositoOrigen = new LabelPrincipal(0);
      this.lblNombreDepositoDestino = new LabelPrincipal(0);
      this.txt_fecha = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_cod_deposito_origen = new LimiteTextFieldConSQL(999999, this.lblNombreDepositoOrigen, "Depositos", this);
      this.txt_cod_deposito_destino = new LimiteTextFieldConSQL(999999, this.lblNombreDepositoDestino, "Depositos", this);
      this.txt_observacion = new LimiteTextArea(70);
      this.tabla = new TablaDetalleNotaEnvio(this.lblTotalLineasTexto, this.codigosAEliminar, 2, 2);
      JScrollPane scrollPane = new JScrollPane(this.tabla);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                              .addComponent(panel_cabecera, -1, 914, 32767)
                              .addComponent(panel_detalle, -1, -1, 32767)
                        )
                        .addGroup(
                           gl_contentPane.createSequentialGroup()
                              .addComponent(lblTotalLineas, -2, 67, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblTotalLineasTexto, -2, 45, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_cabecera, -2, 173, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -2, 319, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblTotalLineas, -2, -1, -2)
                        .addComponent(this.lblTotalLineasTexto, -2, -1, -2)
                  )
                  .addContainerGap(61, 32767)
            )
      );
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -2, 887, -2).addContainerGap(-1, 32767))
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -2, 301, -2).addContainerGap(-1, 32767))
      );
      panel_detalle.setLayout(gl_panel_detalle);
      GroupLayout gl_panel_cabecera = new GroupLayout(panel_cabecera);
      gl_panel_cabecera.setHorizontalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(lblObservacion, -1, -1, 32767)
                        .addComponent(lblDepositoOrigen, -1, -1, 32767)
                        .addComponent(lblFecha, -1, -1, 32767)
                        .addComponent(lblNroRegistro, -1, 108, 32767)
                  )
                  .addGap(19)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_cabecera.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 96, -2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(lblUltimoRegistro, -2, -1, -2)
                                          .addGap(1)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, 45, -2)
                                    )
                                    .addComponent(this.txt_fecha, -2, 126, -2)
                                    .addGroup(
                                       gl_panel_cabecera.createSequentialGroup()
                                          .addComponent(this.txt_cod_deposito_origen, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreDepositoOrigen, -2, 200, -2)
                                    )
                              )
                              .addGap(40)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblDepositoDestino, -1, 110, 32767)
                                    .addComponent(lblHoraEnvio, Alignment.TRAILING, -1, 110, 32767)
                                    .addComponent(lblUsuarioAlta, -1, 110, 32767)
                              )
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.lblHoraTexto, -2, 200, -2)
                                    .addComponent(this.lblUsuarioAltaTexto, -2, 200, -2)
                                    .addGroup(
                                       gl_panel_cabecera.createSequentialGroup()
                                          .addComponent(this.txt_cod_deposito_destino, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreDepositoDestino, -2, 200, -2)
                                    )
                              )
                              .addContainerGap(134, 32767)
                        )
                        .addGroup(gl_panel_cabecera.createSequentialGroup().addComponent(this.txt_observacion, -2, 727, -2).addContainerGap())
                  )
            )
      );
      gl_panel_cabecera.setVerticalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(lblUsuarioAlta, -2, 25, -2)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblNroRegistro, -2, 25, -2)
                                          .addComponent(this.txt_codigo, -2, 25, -2)
                                          .addComponent(lblUltimoRegistro, -2, -1, -2)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, -1, -2)
                                    )
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblHoraEnvio, -2, 25, -2)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                                          .addComponent(lblFecha, -2, 25, -2)
                                          .addComponent(this.txt_fecha, -2, 25, -2)
                                    )
                              )
                        )
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addComponent(this.lblUsuarioAltaTexto, -2, 25, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblHoraTexto, -2, 25, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                              .addComponent(lblDepositoOrigen, -2, 25, -2)
                              .addComponent(this.txt_cod_deposito_origen, -2, 25, -2)
                              .addComponent(this.lblNombreDepositoOrigen, -2, 25, -2)
                        )
                        .addComponent(lblDepositoDestino, -2, 25, -2)
                        .addGroup(
                           gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                              .addComponent(this.txt_cod_deposito_destino, -2, 25, -2)
                              .addComponent(this.lblNombreDepositoDestino, -2, 25, -2)
                        )
                  )
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_cabecera.createSequentialGroup().addGap(12).addComponent(lblObservacion, -2, 25, -2))
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(this.txt_observacion, -2, 45, -2)
                        )
                  )
                  .addGap(81)
            )
      );
      panel_cabecera.setLayout(gl_panel_cabecera);
      contentPane.setLayout(gl_contentPane);
      if (!Beans.isDesignTime()) {
         this.inicializarObjetos();
      }

      this.tabla.getInputMap(1).put(KeyStroke.getKeyStroke(112, 0), "F1");
      this.tabla
         .getActionMap()
         .put(
            "F1",
            new AbstractAction() {
               private static final long serialVersionUID = 1L;

               @Override
               public void actionPerformed(ActionEvent e) {
                  if (NotaEnvioForm.this.tabla.getSelectedColumn() == 0) {
                     BuscadorProducto buscador = new BuscadorProducto(true);
                     buscador.setLocationRelativeTo(NotaEnvioForm.this);
                     buscador.setModal(true);
                     buscador.setVisible(true);
                     if (buscador.getCodigo() != null) {
                        int filaSeleccionada = NotaEnvioForm.this.tabla.getSelectedRow();
                        NotaEnvioForm.this.tabla.clearSelection();
                        NotaEnvioForm.this.tabla.requestFocusInWindow();
                        NotaEnvioForm.this.tabla
                           .changeSelection(filaSeleccionada, NotaEnvioForm.this.tabla.getColumn("Cantidad").getModelIndex(), false, false);
                        NotaEnvioForm.this.tabla.editCellAt(filaSeleccionada, NotaEnvioForm.this.tabla.getColumn("Cantidad").getModelIndex());
                        Component editorComponent = NotaEnvioForm.this.tabla.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }

                        NotaEnvioForm.this.tabla
                           .setValueAt(
                              buscador.getCodigo(), NotaEnvioForm.this.tabla.getSelectedRow(), NotaEnvioForm.this.tabla.getColumn("Código").getModelIndex()
                           );
                        NotaEnvioForm.this.tabla
                           .setValueAt(
                              buscador.getNombre(),
                              NotaEnvioForm.this.tabla.getSelectedRow(),
                              NotaEnvioForm.this.tabla.getColumn("Descripción").getModelIndex()
                           );
                        NotaEnvioForm.this.tabla
                           .setValueAt(
                              buscador.getV_unidad_medida(),
                              NotaEnvioForm.this.tabla.getSelectedRow(),
                              NotaEnvioForm.this.tabla.getColumn("UM").getModelIndex()
                           );
                        NotaEnvioForm.this.tabla
                           .setValueAt(
                              buscador.getCosto_producto(),
                              NotaEnvioForm.this.tabla.getSelectedRow(),
                              NotaEnvioForm.this.tabla.getColumn("Costo").getModelIndex()
                           );
                        NotaEnvioForm.this.tabla
                           .setValueAt(
                              buscador.getV_tipoFiscal(),
                              NotaEnvioForm.this.tabla.getSelectedRow(),
                              NotaEnvioForm.this.tabla.getColumn("Tipo Fiscal").getModelIndex()
                           );
                        NotaEnvioForm.this.tabla
                           .setValueAt(
                              buscador.getV_iva(), NotaEnvioForm.this.tabla.getSelectedRow(), NotaEnvioForm.this.tabla.getColumn("IVA").getModelIndex()
                           );
                        NotaEnvioForm.this.tabla
                           .setValueAt(
                              buscador.getV_porcentaje_gravado(),
                              NotaEnvioForm.this.tabla.getSelectedRow(),
                              NotaEnvioForm.this.tabla.getColumn("PorcGravado").getModelIndex()
                           );
                     }
                  }
               }
            }
         );
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            NotaEnvioForm frame = new NotaEnvioForm();

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
