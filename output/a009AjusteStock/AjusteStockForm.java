package a009AjusteStock;

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

public class AjusteStockForm extends JinternalPadre {
   private static final long serialVersionUID = 1L;
   private JDateChooser txt_fecha;
   private String USUARIO;
   private LimiteTextFieldConSQL txt_cod_motivo;
   private LimiteTextFieldConSQL txt_cod_deposito;
   private LabelPrincipal lblTotalLineasTexto;
   private LabelPrincipal lblUsuarioAltaTexto;
   private LabelPrincipal lblHoraAjusteTexto;
   private LabelPrincipal lblNombreMotivoTexto;
   private LabelPrincipal lblNombreDepositoTexto;
   private LimiteTextArea txt_observacion;
   List<Integer> codigosAEliminar = new ArrayList<>();
   public int SW;
   private TablaDetalleAjusteStock tabla;
   private LabelPrincipal lblUltimoRegistroTexto;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         AjusteStockE b = AjusteStockE.buscarAjusteStock(Integer.parseInt(this.txt_codigo.getText().trim()), this, this.tabla, 2, 2);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarAjusteStock(b);
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
               editor.stopCellEditing();
            }
         }

         AjusteStockE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = AjusteStockE.insertarAjusteStock(entidad, this.tabla, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = AjusteStockE.actualizarAjusteStock(entidad, this, this.tabla, this.codigosAEliminar);
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
            AjusteStockE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = AjusteStockE.eliminarAjusteStock(ent, this);
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

   public void cargarAjusteStock(AjusteStockE e) {
      this.txt_cod_deposito.setEnabled(false);
      this.SW = Integer.parseInt(this.txt_codigo.getText());
      this.lblHoraAjusteTexto.setText(e.getHora_ajuste());
      this.txt_cod_deposito.setValue(e.getCod_deposito());
      this.lblNombreDepositoTexto.setText(e.getNombre_deposito());
      this.txt_cod_motivo.setValue(e.getCod_motivo());
      this.lblNombreMotivoTexto.setText(e.getNombre_motivo());
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

   public AjusteStockE CargarEntidades() {
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
         return new AjusteStockE(
            Integer.parseInt(this.txt_codigo.getText().trim().replace(".", "")),
            fecha,
            Integer.parseInt(this.txt_cod_deposito.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_motivo.getText().trim().replace(".", "")),
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
      this.lblUltimoRegistroTexto.setText(String.valueOf(formatoLinea.format(AjusteStockE.ultimoRegistro(this))));
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
      this.txt_cod_deposito.setValue(0);
      this.lblNombreDepositoTexto.setText("");
      this.txt_cod_motivo.setValue(0);
      this.lblNombreMotivoTexto.setText("");
      this.txt_cod_deposito.setEnabled(true);
      this.txt_observacion.setText("");
      this.txt_codigo.setValue(0);
      ModeloTablaAjusteStock modelo = (ModeloTablaAjusteStock)this.tabla.getModel();
      modelo.deleteRow(this.tabla);
      modelo.addDefaultRow();
      this.lblTotalLineasTexto.setText("0");
      this.lblUsuarioAltaTexto.setText(this.USUARIO);
      this.txt_codigo.requestFocusInWindow();
      this.codigosAEliminar.clear();
      this.habilitarCabecera();
   }

   private void deshabilitarCabecera() {
      this.txt_cod_deposito.setEnabled(false);
   }

   private void habilitarCabecera() {
      this.txt_cod_deposito.setEnabled(true);
   }

   public AjusteStockForm() {
      this.setTitle("Registro de Ajuste de Stock");
      this.setBounds(100, 100, 929, 588);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("Detalles de Ajuste de Stock");
      LabelPrincipal lblLineaRecuperadas = new LabelPrincipal("Total Lineas:", "lineas");
      LabelPrincipal lblNroRegistro = new LabelPrincipal("Nro. Registro", "label");
      LabelPrincipal lblFechaAjuste = new LabelPrincipal("Fecha", "label");
      LabelPrincipal lblDeposito = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblMotivo = new LabelPrincipal("Motivo", "label");
      LabelPrincipal lblHoraAjuste = new LabelPrincipal("Hora Ajuste", "label");
      LabelPrincipal lblUsuarioAlta = new LabelPrincipal("Usuario Alta", "label");
      LabelPrincipal lblObservacion = new LabelPrincipal("Observacion", "label");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro:", "lineas");
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblTotalLineasTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreDepositoTexto = new LabelPrincipal(0);
      this.lblNombreMotivoTexto = new LabelPrincipal(0);
      this.lblHoraAjusteTexto = new LabelPrincipal(0);
      this.lblUsuarioAltaTexto = new LabelPrincipal(0);
      this.txt_fecha = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_cod_deposito = new LimiteTextFieldConSQL(999999, this.lblNombreDepositoTexto, "Depositos", this);
      this.txt_cod_motivo = new LimiteTextFieldConSQL(999999, this.lblNombreMotivoTexto, "MotivosAjuste", this);
      this.txt_observacion = new LimiteTextArea(70);
      this.tabla = new TablaDetalleAjusteStock(this.txt_cod_deposito, this.lblTotalLineasTexto, this.codigosAEliminar, 2, 2);
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
                        .addComponent(panel_buscador, -2, -1, -2)
                        .addComponent(panel_datos, -2, 895, -2)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineaRecuperadas, -2, 66, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.lblTotalLineasTexto, -2, 45, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -1, 319, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineaRecuperadas, -2, -1, -2)
                        .addComponent(this.lblTotalLineasTexto, -2, -1, -2)
                  )
                  .addGap(6)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_buscador.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 875, 32767).addContainerGap())
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_buscador.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 181, 32767).addContainerGap())
      );
      panel_buscador.setLayout(gl_panel_buscador);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(lblObservacion, -2, 75, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.txt_observacion, -1, -1, 32767)
                        )
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblDeposito, -1, 83, 32767)
                                    .addComponent(lblFechaAjuste, -1, 83, 32767)
                                    .addComponent(lblNroRegistro, -1, 83, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_cod_deposito, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreDepositoTexto, -2, 200, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_datos.createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(this.txt_fecha, Alignment.LEADING, -1, -1, 32767)
                                                .addComponent(this.txt_codigo, Alignment.LEADING, -1, 109, 32767)
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblUltimoRegistro, -2, -1, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, 45, -2)
                                    )
                              )
                              .addGap(36)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblMotivo, -1, -1, 32767)
                                    .addComponent(lblUsuarioAlta, -1, -1, 32767)
                                    .addComponent(lblHoraAjuste, -1, 77, 32767)
                              )
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addGap(4)
                                          .addComponent(this.txt_cod_motivo, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreMotivoTexto, -2, 200, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblHoraAjusteTexto, -2, 200, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblUsuarioAltaTexto, -2, 200, -2)
                                    )
                              )
                        )
                  )
                  .addContainerGap(161, 32767)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblNroRegistro, -2, 25, -2)
                                          .addComponent(this.txt_codigo, -2, 25, -2)
                                          .addComponent(lblHoraAjuste, -2, 25, -2)
                                    )
                                    .addComponent(this.lblHoraAjusteTexto, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblFechaAjuste, -2, 25, -2)
                                    .addGroup(
                                       gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(this.txt_fecha, -2, 25, -2)
                                          .addComponent(lblUsuarioAlta, -2, 25, -2)
                                    )
                                    .addComponent(this.lblUsuarioAltaTexto, -2, 25, -2)
                              )
                        )
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGap(18)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.lblUltimoRegistroTexto, -2, 15, -2)
                                    .addComponent(lblUltimoRegistro, -2, 15, -2)
                              )
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblDeposito, -2, 25, -2)
                              .addComponent(this.txt_cod_deposito, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreDepositoTexto, -2, 25, -2)
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblMotivo, -2, 25, -2)
                              .addComponent(this.txt_cod_motivo, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreMotivoTexto, -2, 25, -2)
                  )
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_datos.createSequentialGroup().addGap(18).addComponent(lblObservacion, -2, 25, -2))
                        .addGroup(gl_panel_datos.createSequentialGroup().addGap(4).addComponent(this.txt_observacion, -2, 74, -2))
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      if (!Beans.isDesignTime()) {
         this.USUARIO = DatabaseConnection.getInstance().getUsuario();
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
                  if (AjusteStockForm.this.tabla.getSelectedColumn() == 0) {
                     BuscadorProducto buscador = new BuscadorProducto("AjusteStock", AjusteStockForm.this.txt_cod_deposito);
                     buscador.setLocationRelativeTo(AjusteStockForm.this);
                     buscador.setModal(true);
                     buscador.setVisible(true);
                     if (buscador.getCodigo() != null) {
                        FormatoDecimalOperacionSistema formato = new FormatoDecimalOperacionSistema(2);
                        int filaSeleccionada = AjusteStockForm.this.tabla.getSelectedRow();
                        AjusteStockForm.this.tabla.clearSelection();
                        AjusteStockForm.this.tabla.requestFocusInWindow();
                        AjusteStockForm.this.tabla
                           .changeSelection(filaSeleccionada, AjusteStockForm.this.tabla.getColumn("Cantidad").getModelIndex(), false, false);
                        AjusteStockForm.this.tabla.editCellAt(filaSeleccionada, AjusteStockForm.this.tabla.getColumn("Cantidad").getModelIndex());
                        Component editorComponent = AjusteStockForm.this.tabla.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }

                        AjusteStockForm.this.tabla
                           .setValueAt(
                              buscador.getCodigo(), AjusteStockForm.this.tabla.getSelectedRow(), AjusteStockForm.this.tabla.getColumn("Código").getModelIndex()
                           );
                        AjusteStockForm.this.tabla
                           .setValueAt(
                              buscador.getNombre(),
                              AjusteStockForm.this.tabla.getSelectedRow(),
                              AjusteStockForm.this.tabla.getColumn("Descripción").getModelIndex()
                           );
                        AjusteStockForm.this.tabla
                           .setValueAt(
                              buscador.getSigla(), AjusteStockForm.this.tabla.getSelectedRow(), AjusteStockForm.this.tabla.getColumn("UM").getModelIndex()
                           );
                        AjusteStockForm.this.tabla
                           .setValueAt(
                              buscador.getCosto_producto(),
                              AjusteStockForm.this.tabla.getSelectedRow(),
                              AjusteStockForm.this.tabla.getColumn("Costo").getModelIndex()
                           );
                        AjusteStockForm.this.tabla
                           .setValueAt(
                              formato.format(buscador.getStock()),
                              AjusteStockForm.this.tabla.getSelectedRow(),
                              AjusteStockForm.this.tabla.getColumn("Stock").getModelIndex()
                           );
                        AjusteStockForm.this.tabla
                           .setValueAt(0, AjusteStockForm.this.tabla.getSelectedRow(), AjusteStockForm.this.tabla.getColumn("SW").getModelIndex());
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
            AjusteStockForm frame = new AjusteStockForm();

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
