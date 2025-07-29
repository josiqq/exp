package a0098DevolucionCompras;

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
import utilidadesConexion.FechaActualE;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorProducto;
import utilidadesVentanas.JinternalPadre;

public class DevolucionComprasForm extends JinternalPadre {
   List<Integer> codigosAEliminar = new ArrayList<>();
   public int SW;
   private TablaDetalleDevolucionCompras tabla;
   private static final long serialVersionUID = 1L;
   private JDateChooser txt_fecha;
   private LimiteTextFieldConSQL txt_cod_deposito;
   private LimiteTextFieldConSQL txt_cod_motivo;
   private LimiteTextFieldConSQL txt_cod_proveedor;
   private LimiteTextArea txt_observacion;
   private LabelPrincipal lblUltimoRegistroTexto;
   private LabelPrincipal lblTotalLineasTexto;
   private LabelPrincipal lblNombreDeposito;
   private LabelPrincipal lblNombreMotivo;
   private LabelPrincipal lblNombreProveedor;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         DevolucionComprasE b = DevolucionComprasE.buscarDevolucion(Integer.parseInt(this.txt_codigo.getText().trim()), this, this.tabla, 2, 2);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarDevolucionCompra(b);
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

         DevolucionComprasE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = DevolucionComprasE.insertarDevolucionProveedor(entidad, this.tabla, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = DevolucionComprasE.actualizaDevolucionCompra(entidad, this, this.tabla, this.codigosAEliminar);
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
            DevolucionComprasE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = DevolucionComprasE.eliminarDevolucion(ent, this);
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

   public void cargarDevolucionCompra(DevolucionComprasE e) {
      this.txt_cod_proveedor.setValue(e.getCod_proveedor());
      this.lblNombreProveedor.setText(e.getNombre_proveedor());
      this.txt_cod_deposito.setEnabled(false);
      this.SW = Integer.parseInt(this.txt_codigo.getText());
      this.txt_cod_deposito.setValue(e.getCod_deposito());
      this.lblNombreDeposito.setText(e.getNombre_deposito());
      this.txt_cod_motivo.setValue(e.getCod_motivo());
      this.lblNombreMotivo.setText(e.getNombre_motivo());
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

   public DevolucionComprasE CargarEntidades() {
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
         return new DevolucionComprasE(
            Integer.parseInt(this.txt_codigo.getText().trim().replace(".", "")),
            fecha,
            Integer.parseInt(this.txt_cod_proveedor.getText().trim().replace(".", "")),
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
      this.lblUltimoRegistroTexto.setText(String.valueOf(formatoLinea.format(DevolucionComprasE.ultimoRegistro(this))));
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
      this.lblNombreDeposito.setText("");
      this.txt_cod_motivo.setValue(0);
      this.lblNombreMotivo.setText("");
      this.txt_cod_proveedor.setValue(0);
      this.lblNombreProveedor.setText("");
      this.txt_cod_deposito.setEnabled(true);
      this.txt_observacion.setText("");
      this.txt_codigo.setValue(0);
      ModeloTablaDevolucionCompras modelo = (ModeloTablaDevolucionCompras)this.tabla.getModel();
      modelo.deleteRow(this.tabla);
      modelo.addDefaultRow();
      this.lblTotalLineasTexto.setText("0");
      this.txt_codigo.requestFocusInWindow();
      this.codigosAEliminar.clear();
   }

   public DevolucionComprasForm() {
      this.setTitle("Registro de Devoluciones a Proveedores");
      this.setBounds(100, 100, 904, 583);
      PanelPadre contentPane = new PanelPadre("");
      PanelPadre panel_cabecera = new PanelPadre("");
      PanelPadre panel = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      LabelPrincipal lblTotalLineas = new LabelPrincipal("Total Lineas:", "lineas");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro:", "lineas");
      LabelPrincipal lblNroRegistro = new LabelPrincipal("Nro. Registro", "label");
      LabelPrincipal lblFecha = new LabelPrincipal("Fecha", "label");
      LabelPrincipal lblProveedor = new LabelPrincipal("Proveedor", "label");
      LabelPrincipal lblDeposito = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblObservacion = new LabelPrincipal("Observacion", "label");
      LabelPrincipal lblMotivo = new LabelPrincipal("Motivo", "label");
      this.lblNombreProveedor = new LabelPrincipal(0);
      this.lblNombreDeposito = new LabelPrincipal(0);
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreMotivo = new LabelPrincipal(0);
      this.lblTotalLineasTexto = new LabelPrincipal("0", "lineas");
      this.txt_fecha = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_cod_proveedor = new LimiteTextFieldConSQL(999999, this.lblNombreProveedor, "Proveedores", this);
      this.txt_cod_deposito = new LimiteTextFieldConSQL(999999, this.lblNombreDeposito, "Depositos", this);
      this.txt_cod_motivo = new LimiteTextFieldConSQL(999999, this.lblNombreMotivo, "MotivosNC", this);
      this.txt_observacion = new LimiteTextArea(70);
      this.tabla = new TablaDetalleDevolucionCompras(this.lblTotalLineasTexto, this.codigosAEliminar, 2, 2);
      JScrollPane scrollPane = new JScrollPane(this.tabla);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_cabecera, -2, 868, -2)
                        .addComponent(panel, -2, 868, -2)
                        .addGroup(
                           gl_contentPane.createSequentialGroup()
                              .addComponent(lblTotalLineas, -2, 70, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblTotalLineasTexto, -2, 45, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_cabecera, -2, 184, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel, -2, 328, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblTotalLineas, -2, -1, -2)
                        .addComponent(this.lblTotalLineasTexto, -2, -1, -2)
                  )
                  .addContainerGap(32, 32767)
            )
      );
      GroupLayout gl_panel_cabecera = new GroupLayout(panel_cabecera);
      gl_panel_cabecera.setHorizontalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblFecha, -1, -1, 32767)
                                    .addComponent(lblNroRegistro, -1, 91, 32767)
                                    .addComponent(lblProveedor, -2, 91, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_cabecera.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 81, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblUltimoRegistro, -2, -1, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, 45, -2)
                                    )
                                    .addGroup(
                                       gl_panel_cabecera.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_cabecera.createParallelGroup(Alignment.TRAILING, false)
                                                .addGroup(
                                                   Alignment.LEADING,
                                                   gl_panel_cabecera.createSequentialGroup()
                                                      .addComponent(this.txt_fecha, -2, 119, -2)
                                                      .addGap(143)
                                                      .addComponent(lblMotivo, -1, -1, 32767)
                                                )
                                                .addGroup(
                                                   Alignment.LEADING,
                                                   gl_panel_cabecera.createSequentialGroup()
                                                      .addComponent(this.txt_cod_proveedor, -2, 41, -2)
                                                      .addPreferredGap(ComponentPlacement.RELATED)
                                                      .addComponent(this.lblNombreProveedor, -2, 200, -2)
                                                      .addGap(18)
                                                      .addComponent(lblDeposito, -2, 64, -2)
                                                )
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addGroup(
                                             gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                                .addGroup(
                                                   gl_panel_cabecera.createSequentialGroup()
                                                      .addComponent(this.txt_cod_motivo, -2, 41, -2)
                                                      .addPreferredGap(ComponentPlacement.RELATED)
                                                      .addComponent(this.lblNombreMotivo, -2, 200, -2)
                                                )
                                                .addGroup(
                                                   gl_panel_cabecera.createSequentialGroup()
                                                      .addComponent(this.txt_cod_deposito, -2, 41, -2)
                                                      .addPreferredGap(ComponentPlacement.RELATED)
                                                      .addComponent(this.lblNombreDeposito, -2, 200, -2)
                                                )
                                          )
                                    )
                              )
                        )
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addComponent(lblObservacion, -2, 91, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_observacion, -2, 599, -2)
                        )
                  )
                  .addGap(387)
            )
      );
      gl_panel_cabecera.setVerticalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addGap(10)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblNroRegistro, -2, 25, -2)
                                    .addComponent(this.txt_codigo, -2, 25, -2)
                              )
                        )
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblUltimoRegistro, -2, -1, -2)
                                    .addComponent(this.lblUltimoRegistroTexto, -2, -1, -2)
                              )
                        )
                  )
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_cabecera.createSequentialGroup().addGap(12).addComponent(lblFecha, -2, 25, -2))
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                          .addComponent(this.lblNombreMotivo, -2, 25, -2)
                                          .addGroup(
                                             gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(lblMotivo, -2, 25, -2)
                                                .addComponent(this.txt_cod_motivo, -2, 25, -2)
                                          )
                                    )
                                    .addComponent(this.txt_fecha, -2, 25, -2)
                              )
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_proveedor, -2, 25, -2)
                              .addComponent(lblProveedor, -2, 25, -2)
                        )
                        .addComponent(lblDeposito, -2, 25, -2)
                        .addComponent(this.lblNombreProveedor, -2, 25, -2)
                        .addComponent(this.txt_cod_deposito, -2, 25, -2)
                        .addComponent(this.lblNombreDeposito, -2, 25, -2)
                  )
                  .addGap(18)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblObservacion, -2, 25, -2)
                        .addComponent(this.txt_observacion, -2, 48, -2)
                  )
                  .addContainerGap(12, 32767)
            )
      );
      panel_cabecera.setLayout(gl_panel_cabecera);
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addComponent(scrollPane, -1, 856, 32767).addContainerGap())
      );
      gl_panel.setVerticalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 306, 32767).addContainerGap())
      );
      panel.setLayout(gl_panel);
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
                  if (DevolucionComprasForm.this.tabla.getSelectedColumn() == 0) {
                     BuscadorProducto buscador = new BuscadorProducto(0);
                     buscador.setLocationRelativeTo(DevolucionComprasForm.this);
                     buscador.setModal(true);
                     buscador.setVisible(true);
                     if (buscador.getCodigo() != null) {
                        int filaSeleccionada = DevolucionComprasForm.this.tabla.getSelectedRow();
                        DevolucionComprasForm.this.tabla.clearSelection();
                        DevolucionComprasForm.this.tabla.requestFocusInWindow();
                        DevolucionComprasForm.this.tabla
                           .changeSelection(filaSeleccionada, DevolucionComprasForm.this.tabla.getColumn("Cantidad").getModelIndex(), false, false);
                        DevolucionComprasForm.this.tabla.editCellAt(filaSeleccionada, DevolucionComprasForm.this.tabla.getColumn("Cantidad").getModelIndex());
                        Component editorComponent = DevolucionComprasForm.this.tabla.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }

                        DevolucionComprasForm.this.tabla
                           .setValueAt(
                              buscador.getCodigo(),
                              DevolucionComprasForm.this.tabla.getSelectedRow(),
                              DevolucionComprasForm.this.tabla.getColumn("Código").getModelIndex()
                           );
                        DevolucionComprasForm.this.tabla
                           .setValueAt(
                              buscador.getNombre(),
                              DevolucionComprasForm.this.tabla.getSelectedRow(),
                              DevolucionComprasForm.this.tabla.getColumn("Descripción").getModelIndex()
                           );
                        DevolucionComprasForm.this.tabla
                           .setValueAt(
                              buscador.getSigla(),
                              DevolucionComprasForm.this.tabla.getSelectedRow(),
                              DevolucionComprasForm.this.tabla.getColumn("UM").getModelIndex()
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
            try {
               DevolucionComprasForm frame = new DevolucionComprasForm();
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
   }
}
