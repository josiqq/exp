package a009AjustePrecios;

import a00Productos.ProductosE;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;
import utilidades.CuadroTexto2Decimales;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextField;
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

public class AjustePreciosForm extends JinternalPadre {
   private static final long serialVersionUID = 1L;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private CuadroTexto2Decimales txt_margen_precio;
   private CuadroTexto2Decimales txt_margen_costo;
   private LimiteTextArea txt_observacion;
   private LabelPrincipal lblListaPrecioTexto;
   private LabelPrincipal lblUsuarioAltaTexto;
   private LabelPrincipal lblFechaAltaTexto;
   private LimiteTextFieldConSQL txt_cod_lista;
   public int SW;
   List<Integer> codigosAEliminar = new ArrayList<>();
   private TablaAjustePreciosDetalles tabla;
   private LimiteTextField txt_cod_producto;
   private LabelPrincipal lblUltimoRegistroTexto;
   private LabelPrincipal lblNombreProducto;

   private void buscarListasPrecios() {
      ProductosE producto = ProductosE.buscarProductos2(this.txt_cod_producto.getText(), this);
      if (producto != null) {
         this.txt_cod_lista.setEnabled(false);
         this.lblNombreProducto.setText(producto.getNombre_producto());
         AjustePreciosE.buscarAjustePrecioTabla(producto.getCod_producto(), this.tabla, 2, this);
         this.lblNombreProducto.setText("");
         this.txt_cod_producto.setValue("");
      }
   }

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         AjustePreciosE b = AjustePreciosE.buscarAjustePrecio(Integer.parseInt(this.txt_codigo.getText().trim()), this.tabla, 2, this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarAjustePrecios(b);
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
                  }

                  @Override
                  public void editingCanceled(ChangeEvent e) {
                  }
               });
               editor.stopCellEditing();
            }
         }

         AjustePreciosE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = AjustePreciosE.ajustePrecios(entidad, this.tabla, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = AjustePreciosE.actualizarAjusteprecio(entidad, this.tabla, this);
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
            AjustePreciosE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = AjustePreciosE.eliminarAjustePrecio(ent, this);
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

   public void cargarAjustePrecios(AjustePreciosE e) {
      this.txt_codigo.setValue(e.getNro_registro());
      this.SW = Integer.parseInt(String.valueOf(e.getNro_registro()));
      this.txt_cod_lista.setValue(e.getCod_lista_precio());
      this.lblListaPrecioTexto.setText(e.getNombre_lista());
      this.txt_margen_costo.setValue(e.getMargen_costo());
      this.txt_margen_precio.setValue(e.getMargen_precio());
      this.txt_observacion.setText(e.getObservacion());
      this.lblFechaAltaTexto.setText(e.getFecha());
      this.lblUsuarioAltaTexto.setText(e.getUsuario_alta());
      this.txt_cod_lista.requestFocusInWindow();
      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblLineasRecuperadasTexto.setText(String.valueOf(formatoLinea.format(this.tabla.getRowCount())));
   }

   public AjustePreciosE CargarEntidades() {
      try {
         return new AjustePreciosE(
            Integer.parseInt(String.valueOf(this.txt_codigo.getText()).replace(".", "").replace(",", ".")),
            Integer.parseInt(String.valueOf(this.txt_cod_lista.getText()).replace(".", "").replace(",", ".")),
            Double.parseDouble(String.valueOf(this.txt_margen_costo.getText()).replace(".", "").replace(",", ".")),
            Double.parseDouble(String.valueOf(this.txt_margen_precio.getText()).replace(".", "").replace(",", ".")),
            this.txt_observacion.getText()
         );
      } catch (NumberFormatException var2) {
         LogErrores.errores(var2, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var3) {
         LogErrores.errores(var3, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   private void inicializarObjetos() {
      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblUltimoRegistroTexto.setText(String.valueOf(formatoLinea.format(AjustePreciosE.ultimoRegistro(this))));
      this.SW = 0;
      this.txt_codigo.setValue(0);
      ModeloTablaAjustePrecios modelo = (ModeloTablaAjustePrecios)this.tabla.getModel();
      modelo.deleteRow(this.tabla);
      modelo.addDefaultRow();
      this.txt_cod_lista.setEnabled(true);
      this.txt_cod_lista.setValue(0);
      this.lblListaPrecioTexto.setText("");
      this.txt_margen_costo.setValue(0);
      this.txt_margen_precio.setValue(0);
      this.txt_observacion.setText("");
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_cod_lista.requestFocusInWindow();
      FechaActualE fechaActual = FechaActualE.buscarFechaactual(this);
      this.lblFechaAltaTexto.setText(fechaActual.getFechaActual());
      this.lblUsuarioAltaTexto.setText("");
      this.codigosAEliminar.clear();
      this.lblUsuarioAltaTexto.setText(DatabaseConnection.getInstance().getUsuario());
      this.lblNombreProducto.setText("");
      this.txt_cod_producto.setValue("");
   }

   public AjustePreciosForm() {
      this.setTitle("Registro de Ajuste de Precios");
      this.setBounds(100, 100, 1161, 610);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel = new PanelPadre("Detalles de Ajuste");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Total Lineas", "lineas");
      LabelPrincipal lblNroAjuste = new LabelPrincipal("Nro. Ajuste", "label");
      LabelPrincipal lblListaPrecio = new LabelPrincipal("Lista Precio", "label");
      LabelPrincipal lblUsuarioAlta = new LabelPrincipal("Usuario Alta", "label");
      LabelPrincipal lblFechaAlta = new LabelPrincipal("Fecha Alta", "label");
      LabelPrincipal lblMargenCosto = new LabelPrincipal("Margen Costo", "label");
      LabelPrincipal lblMargenPrecio = new LabelPrincipal("Margen Precio", "label");
      LabelPrincipal lblObservacion = new LabelPrincipal("Observacion", "label");
      LabelPrincipal lblCodigo = new LabelPrincipal("Producto", "label");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro:", "lineas");
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblUsuarioAltaTexto = new LabelPrincipal(0);
      this.lblFechaAltaTexto = new LabelPrincipal(0);
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblListaPrecioTexto = new LabelPrincipal(0);
      this.lblNombreProducto = new LabelPrincipal(0);
      this.txt_margen_costo = new CuadroTexto2Decimales(2);
      this.txt_margen_precio = new CuadroTexto2Decimales(2);
      this.txt_cod_lista = new LimiteTextFieldConSQL(999999, this.lblListaPrecioTexto, "ListaPrecios", this);
      this.txt_cod_producto = new LimiteTextField(9);
      this.txt_cod_producto.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            AjustePreciosForm.this.buscarListasPrecios();
         }
      });
      this.txt_observacion = new LimiteTextArea(70);
      this.tabla = new TablaAjustePreciosDetalles(this.txt_cod_lista, this.lblLineasRecuperadasTexto, 2);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panel, Alignment.LEADING, -1, 1125, 32767)
                        .addComponent(panel_datos, Alignment.LEADING, -1, 1125, 32767)
                        .addGroup(
                           Alignment.LEADING,
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, 85, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 85, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, 123, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel, -1, 410, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblLineasRecuperadas, -2, 15, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, 15, -2)
                  )
                  .addContainerGap()
            )
      );
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, -1, 1038, 32767));
      gl_panel.setVerticalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 322, 32767).addContainerGap())
      );
      panel.setLayout(gl_panel);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                              .addComponent(lblNroAjuste, -2, 85, -2)
                              .addComponent(lblListaPrecio, -2, 85, -2)
                        )
                        .addComponent(lblCodigo, -2, 85, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.txt_cod_producto, -2, 55, -2)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.TRAILING, false)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 55, -2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(lblUltimoRegistro, -2, -1, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, 85, -2)
                                          .addGap(52)
                                          .addComponent(lblMargenCosto, -2, 85, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_cod_lista, -2, 55, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblListaPrecioTexto, -2, 200, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED, 16, 32767)
                                          .addComponent(lblMargenPrecio, -2, 85, -2)
                                    )
                                    .addGroup(gl_panel_datos.createSequentialGroup().addGap(59).addComponent(this.lblNombreProducto, -1, -1, 32767))
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_margen_precio, -2, 55, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblObservacion, -2, 82, -2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(this.txt_observacion, -2, 476, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_margen_costo, -2, 55, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblFechaAlta, -2, 85, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblFechaAltaTexto, -2, 200, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED, 8, 32767)
                                          .addComponent(lblUsuarioAlta, -2, 85, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblUsuarioAltaTexto, -2, 178, -2)
                                    )
                              )
                        )
                  )
                  .addContainerGap()
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(this.lblUltimoRegistroTexto, -2, 25, -2)
                                                .addComponent(lblUltimoRegistro, -2, 15, -2)
                                          )
                                          .addGap(18)
                                          .addComponent(this.lblListaPrecioTexto, -2, 25, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                                .addComponent(lblNroAjuste, -2, 25, -2)
                                                .addGroup(
                                                   gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                                      .addComponent(this.txt_codigo, -2, 25, -2)
                                                      .addGroup(
                                                         gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                                            .addComponent(lblUsuarioAlta, -2, 25, -2)
                                                            .addComponent(lblMargenCosto, -2, 25, -2)
                                                            .addComponent(this.txt_margen_costo, -2, 25, -2)
                                                            .addComponent(this.lblUsuarioAltaTexto, -2, 25, -2)
                                                      )
                                                )
                                          )
                                          .addGroup(
                                             gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                                .addGroup(
                                                   gl_panel_datos.createSequentialGroup()
                                                      .addGap(6)
                                                      .addGroup(
                                                         gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                                            .addComponent(this.txt_cod_lista, -2, 25, -2)
                                                            .addGroup(gl_panel_datos.createSequentialGroup().addComponent(lblListaPrecio, -2, 25, -2).addGap(8))
                                                      )
                                                )
                                                .addGroup(
                                                   gl_panel_datos.createSequentialGroup()
                                                      .addPreferredGap(ComponentPlacement.RELATED)
                                                      .addGroup(
                                                         gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                                            .addComponent(this.txt_margen_precio, -2, 25, -2)
                                                            .addComponent(lblMargenPrecio, -2, 25, -2)
                                                            .addComponent(lblObservacion, -2, 25, -2)
                                                            .addComponent(this.txt_observacion, -2, 25, -2)
                                                      )
                                                )
                                          )
                                    )
                              )
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(lblCodigo, -2, 25, -2)
                                    .addComponent(this.txt_cod_producto, -2, 25, -2)
                                    .addComponent(this.lblNombreProducto, -2, 25, -2)
                              )
                        )
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblFechaAlta, -2, 25, -2)
                              .addComponent(this.lblFechaAltaTexto, -2, 25, -2)
                        )
                  )
                  .addContainerGap(24, 32767)
            )
      );
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
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
                  if (AjustePreciosForm.this.tabla.getSelectedColumn() == 0) {
                     BuscadorProducto buscador = new BuscadorProducto(Integer.parseInt(AjustePreciosForm.this.txt_cod_lista.getValue().toString()));
                     buscador.setLocationRelativeTo(AjustePreciosForm.this);
                     buscador.setModal(true);
                     buscador.setVisible(true);
                     if (buscador.isSeleccionado()) {
                        FormatoDecimalOperacionSistema formatoDecimalImporte = new FormatoDecimalOperacionSistema(10);
                        int filaSeleccionada = AjustePreciosForm.this.tabla.getSelectedRow();
                        AjustePreciosForm.this.tabla.clearSelection();
                        AjustePreciosForm.this.tabla.requestFocusInWindow();
                        AjustePreciosForm.this.tabla
                           .changeSelection(filaSeleccionada, AjustePreciosForm.this.tabla.getColumn("Margen Precio").getModelIndex(), false, false);
                        AjustePreciosForm.this.tabla.editCellAt(filaSeleccionada, AjustePreciosForm.this.tabla.getColumn("Margen Precio").getModelIndex());
                        Component editorComponent = AjustePreciosForm.this.tabla.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }

                        AjustePreciosForm.this.tabla
                           .setValueAt(
                              buscador.getCodigo(),
                              AjustePreciosForm.this.tabla.getSelectedRow(),
                              AjustePreciosForm.this.tabla.getColumn("Código").getModelIndex()
                           );
                        AjustePreciosForm.this.tabla
                           .setValueAt(
                              buscador.getNombre(),
                              AjustePreciosForm.this.tabla.getSelectedRow(),
                              AjustePreciosForm.this.tabla.getColumn("Descripción").getModelIndex()
                           );
                        AjustePreciosForm.this.tabla
                           .setValueAt(
                              buscador.getSigla(), AjustePreciosForm.this.tabla.getSelectedRow(), AjustePreciosForm.this.tabla.getColumn("UM").getModelIndex()
                           );
                        AjustePreciosForm.this.tabla
                           .setValueAt(
                              buscador.getCod_moneda(),
                              AjustePreciosForm.this.tabla.getSelectedRow(),
                              AjustePreciosForm.this.tabla.getColumn("CodMon").getModelIndex()
                           );
                        AjustePreciosForm.this.tabla
                           .setValueAt(
                              buscador.getNombre_moneda(),
                              AjustePreciosForm.this.tabla.getSelectedRow(),
                              AjustePreciosForm.this.tabla.getColumn("Moneda").getModelIndex()
                           );
                        AjustePreciosForm.this.tabla
                           .setValueAt(
                              formatoDecimalImporte.format(buscador.getCosto_producto()),
                              AjustePreciosForm.this.tabla.getSelectedRow(),
                              AjustePreciosForm.this.tabla.getColumn("Costo").getModelIndex()
                           );
                        AjustePreciosForm.this.tabla
                           .setValueAt(
                              formatoDecimalImporte.format(buscador.getPrecio()),
                              AjustePreciosForm.this.tabla.getSelectedRow(),
                              AjustePreciosForm.this.tabla.getColumn("Precio Anterior").getModelIndex()
                           );
                        AjustePreciosForm.this.tabla
                           .setValueAt(
                              formatoDecimalImporte.format(buscador.getPrecio()),
                              AjustePreciosForm.this.tabla.getSelectedRow(),
                              AjustePreciosForm.this.tabla.getColumn("Precio Nuevo").getModelIndex()
                           );
                        AjustePreciosForm.this.tabla
                           .setValueAt(
                              formatoDecimalImporte.format(buscador.getPrecio_minimo()),
                              AjustePreciosForm.this.tabla.getSelectedRow(),
                              AjustePreciosForm.this.tabla.getColumn("Precio Minimo").getModelIndex()
                           );
                        AjustePreciosForm.this.tabla
                           .setValueAt(
                              AjustePreciosForm.this.txt_cod_lista.getValue(),
                              AjustePreciosForm.this.tabla.getSelectedRow(),
                              AjustePreciosForm.this.tabla.getColumn("Nro Lista").getModelIndex()
                           );
                        AjustePreciosForm.this.tabla
                           .setValueAt(0, AjustePreciosForm.this.tabla.getSelectedRow(), AjustePreciosForm.this.tabla.getColumn("SW").getModelIndex());
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
            AjustePreciosForm frame = new AjustePreciosForm();

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
