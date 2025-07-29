package a00Ventas;

import a00Cotizaciones.CotizacionesE;
import a00Productos.ProductosE;
import a00pedidosProveedores.DecimalFilter;
import a2MenuPrincipal.ParametrosDetalleE;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQL;
import utilidades.LimiteTextFieldConSQLClientes;
import utilidades.LimiteTextFieldConSQLCondicionVta;
import utilidades.NumerosTextField;
import utilidadesE.StockDepositosE;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadordeCodigos;
import utilidadesVtas.CalculosTabla;
import utilidadesVtas.F_Calcular_Vtas;

public class TablaDetalleVentas extends JTable {
   private DecimalFilter txt_exentas;
   private DecimalFilter txt_gravada10;
   private DecimalFilter txt_iva10;
   private DecimalFilter txt_gravada5;
   private DecimalFilter txt_iva5;
   private DecimalFilter txt_total_impuesto;
   private LimiteTextFieldConSQLClientes txt_cliente;
   private LimiteTextFieldConSQL txt_moneda;
   private JDateChooser txt_fecha;
   private NumerosTextField txt_tipo_fiscal_cliente;
   private LimiteTextFieldConSQLCondicionVta txt_condicion;
   private DecimalFilter txt_moneda1;
   private DecimalFilter txt_moneda2;
   private DecimalFilter txt_moneda3;
   private DecimalFilter txt_moneda4;
   private int PAR_REPETIR_CODIGO = ParametrosDetalleE.buscarParametros(2, null);
   private int COD_DEPOSITO;
   private int ID_PETICION;
   private static final long serialVersionUID = 1L;

   public TablaDetalleVentas(
      final List<Integer> codigosAEliminar,
      JDateChooser txt_fecha,
      final DecimalFilter txt_exentas,
      final DecimalFilter txt_gravada10,
      final DecimalFilter txt_iva10,
      final DecimalFilter txt_gravada5,
      final DecimalFilter txt_iva5,
      final DecimalFilter txt_total_impuesto,
      LimiteTextFieldConSQLClientes txt_cliente,
      final LimiteTextFieldConSQL txt_moneda,
      final NumerosTextField txt_tipo_fiscal_cliente,
      LimiteTextFieldConSQLCondicionVta txt_condicion,
      final LabelPrincipal lblTextoTotalLinea,
      int DECIMAL_CANTIDAD,
      int DECIMAL_IMPORTE,
      final DecimalFilter txt_moneda1,
      final DecimalFilter txt_moneda2,
      final DecimalFilter txt_moneda3,
      final DecimalFilter txt_moneda4,
      int COD_DEPOSITO,
      final int ID_PETICION
   ) {
      this.COD_DEPOSITO = COD_DEPOSITO;
      this.ID_PETICION = ID_PETICION;
      this.txt_fecha = txt_fecha;
      this.txt_moneda = txt_moneda;
      this.txt_cliente = txt_cliente;
      this.txt_condicion = txt_condicion;
      this.txt_tipo_fiscal_cliente = txt_tipo_fiscal_cliente;
      this.txt_moneda1 = txt_moneda1;
      this.txt_moneda2 = txt_moneda2;
      this.txt_moneda3 = txt_moneda3;
      this.txt_moneda4 = txt_moneda4;
      this.txt_exentas = txt_exentas;
      this.txt_gravada10 = txt_gravada10;
      this.txt_iva10 = txt_iva10;
      this.txt_gravada5 = txt_gravada5;
      this.txt_iva5 = txt_iva5;
      this.txt_total_impuesto = txt_total_impuesto;
      this.setRowHeight(25);
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      String[] columnNames = new String[]{
         "Código",
         "Descripción",
         "TF",
         "IVA",
         "Gravado",
         "REG",
         "UM",
         "Precio Lista",
         "Precio Bruto",
         "Precio Minimo",
         "Costo",
         "Cantidad",
         "Precio Venta",
         "Desc.",
         "Total Iva",
         "Total Gravado",
         "Total Exento",
         "SubTotal",
         "Primario",
         "SW",
         "CodLista"
      };
      final ModeloVentasCliente model = new ModeloVentasCliente(null, columnNames);
      this.setModel(model);
      model.formato(this, DECIMAL_CANTIDAD, DECIMAL_IMPORTE);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(37, 0), "left");
      this.getActionMap().put("left", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaDetalleVentas.this.getSelectedRow();
            int selectedColumn = TablaDetalleVentas.this.getSelectedColumn();

            for (int i = selectedColumn - 1; i >= 0; i--) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleVentas.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleVentas.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleVentas.this.getEditorComponent();
                  if (editorComponent instanceof JTextComponent) {
                     ((JTextComponent)editorComponent).selectAll();
                  }
                  break;
               }
            }
         }
      });
      this.getInputMap(1).put(KeyStroke.getKeyStroke(39, 0), "right");
      this.getActionMap().put("right", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaDetalleVentas.this.getSelectedRow();
            int selectedColumn = TablaDetalleVentas.this.getSelectedColumn();

            for (int i = selectedColumn + 1; i < TablaDetalleVentas.this.getColumnCount(); i++) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleVentas.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleVentas.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleVentas.this.getEditorComponent();
                  if (editorComponent instanceof JTextComponent) {
                     ((JTextComponent)editorComponent).selectAll();
                  }
                  break;
               }
            }
         }
      });
      this.getInputMap(1).put(KeyStroke.getKeyStroke(38, 0), "up");
      this.getActionMap().put("up", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            if (TablaDetalleVentas.this.getRowCount() - 1 > 0) {
               int selectedRow = TablaDetalleVentas.this.getSelectedRow() - 1;
               TablaDetalleVentas.this.changeSelection(selectedRow, TablaDetalleVentas.this.getSelectedColumn(), false, false);
               TablaDetalleVentas.this.editCellAt(selectedRow, TablaDetalleVentas.this.getSelectedColumn());
               Component editorComponent = TablaDetalleVentas.this.getEditorComponent();
               if (editorComponent instanceof JTextComponent) {
                  ((JTextComponent)editorComponent).selectAll();
               }
            }
         }
      });
      this.getInputMap(1).put(KeyStroke.getKeyStroke(40, 0), "down");
      this.getActionMap().put("down", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            if (TablaDetalleVentas.this.getRowCount() - 1 > TablaDetalleVentas.this.getSelectedRow()) {
               int selectedRow = TablaDetalleVentas.this.getSelectedRow() + 1;
               TablaDetalleVentas.this.changeSelection(selectedRow, TablaDetalleVentas.this.getSelectedColumn(), false, false);
               TablaDetalleVentas.this.editCellAt(selectedRow, TablaDetalleVentas.this.getSelectedColumn());
               Component editorComponent = TablaDetalleVentas.this.getEditorComponent();
               if (editorComponent instanceof JTextComponent) {
                  ((JTextComponent)editorComponent).selectAll();
               }
            }
         }
      });
      this.getInputMap(1).put(KeyStroke.getKeyStroke(10, 0), "enter");
      this.getActionMap()
         .put(
            "enter",
            new AbstractAction() {
               private static final long serialVersionUID = 1L;

               @Override
               public void actionPerformed(ActionEvent e) {
                  int selectedRow = TablaDetalleVentas.this.getSelectedRow();
                  int selectedColumn = TablaDetalleVentas.this.getSelectedColumn();
                  String columnName = TablaDetalleVentas.this.getColumnName(selectedColumn);
                  if (columnName.equals("Desc.") && TablaDetalleVentas.this.getRowCount() - 1 == TablaDetalleVentas.this.getSelectedRow()) {
                     if (TablaDetalleVentas.this.getValueAt(TablaDetalleVentas.this.getSelectedRow(), 1) != null
                        && !String.valueOf(TablaDetalleVentas.this.getValueAt(TablaDetalleVentas.this.getSelectedRow(), 1)).equals("")) {
                        lblTextoTotalLinea.setText(String.valueOf(TablaDetalleVentas.this.getRowCount()));
                        model.addDefaultRow();
                        TablaDetalleVentas.this.editCellAt(TablaDetalleVentas.this.getSelectedRow() + 1, 0);
                        TablaDetalleVentas.this.changeSelection(TablaDetalleVentas.this.getSelectedRow() + 1, 0, false, false);
                        Component editorComponent = TablaDetalleVentas.this.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     } else {
                        SwingUtilities.invokeLater(
                           () -> {
                              TablaDetalleVentas.this.changeSelection(
                                 TablaDetalleVentas.this.getSelectedRow(), TablaDetalleVentas.this.getColumn("Código").getModelIndex(), false, false
                              );
                              TablaDetalleVentas.this.editCellAt(
                                 TablaDetalleVentas.this.getSelectedRow(), TablaDetalleVentas.this.getColumn("Código").getModelIndex()
                              );
                              Component editorComponentx = TablaDetalleVentas.this.getEditorComponent();
                              if (editorComponentx instanceof JTextComponent) {
                                 ((JTextComponent)editorComponentx).selectAll();
                              }

                              editorComponentx.requestFocusInWindow();
                           }
                        );
                     }
                  } else if (columnName.equals("Desc.") && TablaDetalleVentas.this.getSelectedRow() < TablaDetalleVentas.this.getRowCount() - 1) {
                     TablaDetalleVentas.this.editCellAt(TablaDetalleVentas.this.getSelectedRow() + 1, 0);
                     TablaDetalleVentas.this.changeSelection(TablaDetalleVentas.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaDetalleVentas.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     for (int i = selectedColumn + 1; i < TablaDetalleVentas.this.getColumnCount(); i++) {
                        if (model.isCellEditable(selectedRow, i)) {
                           TablaDetalleVentas.this.editCellAt(selectedRow, i);
                           TablaDetalleVentas.this.changeSelection(selectedRow, i, false, false);
                           Component editorComponent = TablaDetalleVentas.this.getEditorComponent();
                           if (editorComponent instanceof JTextComponent) {
                              ((JTextComponent)editorComponent).selectAll();
                           }
                           break;
                        }
                     }
                  }
               }
            }
         );
      this.getInputMap(1).put(KeyStroke.getKeyStroke(119, 0), "deleteRow");
      this.getActionMap()
         .put(
            "deleteRow",
            new AbstractAction() {
               private static final long serialVersionUID = 1L;

               @Override
               public void actionPerformed(ActionEvent e) {
                  int editingRow = TablaDetalleVentas.this.getEditingRow();
                  int editingColumn = TablaDetalleVentas.this.getEditingColumn();
                  if (editingRow != -1 && editingColumn != -1) {
                     TableCellEditor cellEditor = TablaDetalleVentas.this.getCellEditor();
                     cellEditor.stopCellEditing();
                  }

                  DialogoMessagebox d = new DialogoMessagebox("¿Estás seguro de eliminar esta fila?");
                  d.setLocationRelativeTo(TablaDetalleVentas.this);
                  d.setVisible(true);
                  if (d.isResultadoEncontrado()) {
                     int selectedRow = TablaDetalleVentas.this.getSelectedRow();
                     int primarioColumn = TablaDetalleVentas.this.getColumn("Primario").getModelIndex();
                     if (!String.valueOf(TablaDetalleVentas.this.getValueAt(TablaDetalleVentas.this.getSelectedRow(), primarioColumn)).trim().equals("0")) {
                        codigosAEliminar.add(
                           Integer.parseInt(String.valueOf(TablaDetalleVentas.this.getValueAt(TablaDetalleVentas.this.getSelectedRow(), primarioColumn)).trim())
                        );
                     } else {
                        String codigo = (String)TablaDetalleVentas.this.getValueAt(selectedRow, TablaDetalleVentas.this.getColumn("Código").getModelIndex());
                        StockDepositosE.eliminarReserva(codigo, ID_PETICION);
                     }

                     if (selectedRow == 0 && TablaDetalleVentas.this.getRowCount() - 1 == 0) {
                        FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
                        lblTextoTotalLinea.setText(String.valueOf(formatoLinea.format(TablaDetalleVentas.this.getRowCount() + 1)));
                        model.addDefaultRow();
                     }

                     if (selectedRow != -1) {
                        ModeloVentasCliente model = (ModeloVentasCliente)TablaDetalleVentas.this.getModel();
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        if (model.getRowCount() > 0) {
                           int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                           TablaDetalleVentas.this.changeSelection(newSelectedRow, 0, false, false);
                           TablaDetalleVentas.this.editCellAt(newSelectedRow, 0);
                        }

                        CalculosTabla.cargarImpuestos(
                           TablaDetalleVentas.this,
                           txt_exentas,
                           txt_gravada10,
                           txt_iva10,
                           txt_gravada5,
                           txt_iva5,
                           txt_total_impuesto,
                           Integer.parseInt(txt_tipo_fiscal_cliente.getText())
                        );
                        Number valor = (Number)txt_total_impuesto.getValue();
                        double totalImpuesto = valor.doubleValue();
                        txt_moneda1.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(txt_moneda.getText()), 1, totalImpuesto, null).getCotizacion());
                        txt_moneda2.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(txt_moneda.getText()), 2, totalImpuesto, null).getCotizacion());
                        txt_moneda3.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(txt_moneda.getText()), 3, totalImpuesto, null).getCotizacion());
                        txt_moneda4.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(txt_moneda.getText()), 4, totalImpuesto, null).getCotizacion());
                     }
                  } else {
                     TablaDetalleVentas.this.changeSelection(editingRow, 0, false, false);
                     TablaDetalleVentas.this.editCellAt(editingRow, 0);
                  }
               }
            }
         );
      this.getInputMap(1).put(KeyStroke.getKeyStroke(115, 0), "search");
      this.getActionMap().put("search", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadordeCodigos buscador = new BuscadordeCodigos();
            buscador.setModal(true);
            buscador.setVisible(true);
            if (buscador.getCodigo() != null) {
               for (int row = 0; row < TablaDetalleVentas.this.getRowCount(); row++) {
                  String codigo = String.valueOf(TablaDetalleVentas.this.getValueAt(row, 0));
                  if (codigo.equals(buscador.getCodigo())) {
                     TablaDetalleVentas.this.changeSelection(row, 0, false, false);
                     TablaDetalleVentas.this.editCellAt(row, 0);
                     return;
                  }
               }

               DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe producto con codigo: " + buscador.getCodigo(), "error");
               rs.setLocationRelativeTo(buscador);
               rs.setVisible(true);
            }
         }
      });
   }

   @Override
   public void editingStopped(ChangeEvent e) {
      if (this.getRowCount() - 1 >= 0) {
         int column = this.getEditingColumn();
         int row = this.getEditingRow();
         String columnName = this.getColumnName(column);
         if (columnName.equals("Cantidad")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)
                  && !valorActual.trim().equals("")
                  && (
                     String.valueOf(this.getValueAt(row, this.getColumn("Descripción").getModelIndex())).trim().equals("")
                        || this.getValueAt(row, this.getColumn("Descripción").getModelIndex()) == null
                  )
               || !valorActual.trim().equals(valorAnterior)) {
               F_Calcular_Vtas.calcularDescuento(row, this);
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
               CalculosTabla.calcularIvas(row, this, Integer.parseInt(this.txt_tipo_fiscal_cliente.getText()));
               CalculosTabla.cargarImpuestos(
                  this,
                  this.txt_exentas,
                  this.txt_gravada10,
                  this.txt_iva10,
                  this.txt_gravada5,
                  this.txt_iva5,
                  this.txt_total_impuesto,
                  Integer.parseInt(this.txt_tipo_fiscal_cliente.getText())
               );
               Number valor = (Number)this.txt_total_impuesto.getValue();
               double totalImpuesto = valor.doubleValue();
               this.txt_moneda1.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_moneda.getText()), 1, totalImpuesto, null).getCotizacion());
               this.txt_moneda2.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_moneda.getText()), 2, totalImpuesto, null).getCotizacion());
               this.txt_moneda3.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_moneda.getText()), 3, totalImpuesto, null).getCotizacion());
               this.txt_moneda4.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_moneda.getText()), 4, totalImpuesto, null).getCotizacion());
            }

            String codigo = (String)this.getValueAt(row, this.getColumn("Código").getModelIndex());
            Double cantidad_actual = Double.parseDouble(valorActual.replace(".", "").replace(",", "."));
            Double cantidad_anterior = Double.parseDouble(valorAnterior.replace(".", "").replace(",", "."));
            StockDepositosE.insertarReserva(codigo, this.COD_DEPOSITO, cantidad_actual - cantidad_anterior, this.ID_PETICION);
         }

         if (columnName.equals("Código")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)
                  && !valorActual.trim().equals("")
                  && (String.valueOf(this.getValueAt(row, 1)).trim().equals("") || this.getValueAt(row, 1) == null)
               || !valorActual.trim().equals(valorAnterior)) {
               StockDepositosE.eliminarReserva(valorAnterior, this.ID_PETICION);
               this.buscarParametros(row);
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         if (columnName.equals("Precio Venta")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)
                  && !valorActual.trim().equals("")
                  && (String.valueOf(this.getValueAt(row, 1)).trim().equals("") || this.getValueAt(row, 1) == null)
               || !valorActual.trim().equals(valorAnterior)) {
               F_Calcular_Vtas.calcularSubTotal(row, this);
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
               CalculosTabla.calcularIvas(row, this, Integer.parseInt(this.txt_tipo_fiscal_cliente.getText()));
               CalculosTabla.cargarImpuestos(
                  this,
                  this.txt_exentas,
                  this.txt_gravada10,
                  this.txt_iva10,
                  this.txt_gravada5,
                  this.txt_iva5,
                  this.txt_total_impuesto,
                  Integer.parseInt(this.txt_tipo_fiscal_cliente.getText())
               );
            }
         }

         if (columnName.equals("Desc.")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)
                  && !valorActual.trim().equals("")
                  && (
                     String.valueOf(this.getValueAt(row, this.getColumn("Descripción").getModelIndex())).trim().equals("")
                        || this.getValueAt(row, this.getColumn("Descripción").getModelIndex()) == null
                  )
               || !valorActual.trim().equals(valorAnterior)) {
               F_Calcular_Vtas.calcularDescuento(row, this);
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
               CalculosTabla.calcularIvas(row, this, Integer.parseInt(this.txt_tipo_fiscal_cliente.getText()));
               CalculosTabla.cargarImpuestos(
                  this,
                  this.txt_exentas,
                  this.txt_gravada10,
                  this.txt_iva10,
                  this.txt_gravada5,
                  this.txt_iva5,
                  this.txt_total_impuesto,
                  Integer.parseInt(this.txt_tipo_fiscal_cliente.getText())
               );
               Number valor = (Number)this.txt_total_impuesto.getValue();
               double totalImpuesto = valor.doubleValue();
               this.txt_moneda1.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_moneda.getText()), 1, totalImpuesto, null).getCotizacion());
               this.txt_moneda2.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_moneda.getText()), 2, totalImpuesto, null).getCotizacion());
               this.txt_moneda3.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_moneda.getText()), 3, totalImpuesto, null).getCotizacion());
               this.txt_moneda4.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_moneda.getText()), 4, totalImpuesto, null).getCotizacion());
            }
         }

         super.editingStopped(e);
      }
   }

   private void buscarBD(String codigo, int row) {
      if (!codigo.trim().equals("")) {
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         String fecha = "";

         try {
            fecha = dateFormat.format(this.txt_fecha.getDate());
         } catch (Exception var9) {
            LogErrores.errores(var9, "Debe de cargar Fecha de Venta...");
         }

         ProductosE producto = ProductosE.cargarProductosPedidosVta(
            codigo,
            Integer.parseInt(String.valueOf(this.txt_cliente.getText())),
            Integer.parseInt(String.valueOf(this.txt_moneda.getText())),
            fecha,
            null,
            Integer.parseInt(String.valueOf(this.txt_condicion.getValue()))
         );
         if (producto != null) {
            this.setValueAt(producto.getNombre_producto(), row, this.getColumn("Descripción").getModelIndex());
            if (this.getValueAt(row, this.getColumn("Primario").getModelIndex()) == null) {
               this.setValueAt(0, row, this.getColumn("Primario").getModelIndex());
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }

            FormatoDecimalOperacionSistema formatoDecimalImporte = new FormatoDecimalOperacionSistema(7);
            this.setValueAt(producto.getTipo_fiscal(), row, this.getColumn("TF").getModelIndex());
            this.setValueAt(producto.getIva_producto(), row, this.getColumn("IVA").getModelIndex());
            this.setValueAt(producto.getPorcentaje_gravado(), row, this.getColumn("Gravado").getModelIndex());
            this.setValueAt(producto.getPorc_regimen(), row, this.getColumn("REG").getModelIndex());
            this.setValueAt(producto.getNombreUnidadMedida(), row, this.getColumn("UM").getModelIndex());
            this.setValueAt(producto.getCosto_producto(), row, this.getColumn("Costo").getModelIndex());
            this.setValueAt(producto.getCod_lista(), row, this.getColumn("CodLista").getModelIndex());
            this.setValueAt(formatoDecimalImporte.format(producto.getPrecio_lista()), row, this.getColumn("Precio Lista").getModelIndex());
            this.setValueAt(formatoDecimalImporte.format(producto.getPrecio()), row, this.getColumn("Precio Bruto").getModelIndex());
            this.setValueAt(formatoDecimalImporte.format(producto.getPrecio_minimo()), row, this.getColumn("Precio Minimo").getModelIndex());
            this.setValueAt(formatoDecimalImporte.format(producto.getPrecio()), row, this.getColumn("Precio Venta").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Desc.").getModelIndex());
            String cantidad = String.valueOf(this.getValueAt(row, this.getColumn("Cantidad").getModelIndex()));
            Double cantidad_actual = Double.parseDouble(cantidad.replace(".", "").replace(",", "."));
            StockDepositosE.insertarReserva(codigo, this.COD_DEPOSITO, cantidad_actual, this.ID_PETICION);
         } else {
            this.setValueAt("", row, this.getColumn("Descripción").getModelIndex());
            if (this.getValueAt(row, this.getColumn("Primario").getModelIndex()) == null) {
               this.setValueAt(0, row, this.getColumn("Precio Venta").getModelIndex());
            }

            this.setValueAt(0, row, this.getColumn("TF").getModelIndex());
            this.setValueAt(0, row, this.getColumn("IVA").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Gravado").getModelIndex());
            this.setValueAt(0, row, this.getColumn("REG").getModelIndex());
            this.setValueAt(0, row, this.getColumn("UM").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Costo").getModelIndex());
            this.setValueAt(0, row, this.getColumn("CodLista").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Precio Lista").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Precio Bruto").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Precio Minimo").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Precio Venta").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Desc.").getModelIndex());
            SwingUtilities.invokeLater(() -> {
               this.changeSelection(row, this.getColumn("Código").getModelIndex(), false, false);
               this.editCellAt(row, this.getColumn("Código").getModelIndex());
               Component editorComponent = this.getEditorComponent();
               if (editorComponent instanceof JTextComponent) {
                  ((JTextComponent)editorComponent).selectAll();
               }

               editorComponent.requestFocusInWindow();
            });
         }

         F_Calcular_Vtas.calcularDescuento(row, this);
         CalculosTabla.calcularIvas(row, this, Integer.parseInt(this.txt_tipo_fiscal_cliente.getText()));
         CalculosTabla.cargarImpuestos(
            this,
            this.txt_exentas,
            this.txt_gravada10,
            this.txt_iva10,
            this.txt_gravada5,
            this.txt_iva5,
            this.txt_total_impuesto,
            Integer.parseInt(this.txt_tipo_fiscal_cliente.getText())
         );
      }
   }

   private void buscarParametros(int row) {
      String codigo = (String)this.getValueAt(row, this.getColumn("Código").getModelIndex());
      if (this.PAR_REPETIR_CODIGO == 0) {
         if (this.buscarCodigoNoRepetido(codigo)) {
            this.buscarBD(codigo, row);
         }
      } else {
         this.buscarBD(codigo, row);
      }
   }

   private boolean buscarCodigoNoRepetido(String par_codigo) {
      for (int row = 0; row < this.getRowCount(); row++) {
         String codigo = String.valueOf(this.getValueAt(row, 0));
         if (codigo.trim().equals(par_codigo.trim()) && this.getRowCount() > 1 && row != this.getSelectedRow()) {
            this.clearSelection();
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Ya existe el codigo: " + codigo, "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            int fila = row;
            SwingUtilities.invokeLater(() -> {
               this.changeSelection(fila, this.getColumn("Código").getModelIndex(), false, false);
               this.editCellAt(fila, this.getColumn("Código").getModelIndex());
               Component editorComponent = this.getEditorComponent();
               if (editorComponent instanceof JTextComponent) {
                  ((JTextComponent)editorComponent).selectAll();
               }

               editorComponent.requestFocusInWindow();
            });
            return false;
         }
      }

      return true;
   }
}
