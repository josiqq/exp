package a00Compras;

import a00Productos.ProductosE;
import a00pedidosProveedores.DecimalFilter;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;
import utilidades.LabelPrincipal;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesTabla.BuscadordeCodigos;

public class TablaComprasDetalles extends JTable {
   private DecimalFilter txt_total;
   private DecimalFilter txt_exentas;
   private DecimalFilter txt_gravada10;
   private DecimalFilter txt_iva10;
   private DecimalFilter txt_gravada5;
   private DecimalFilter txt_iva5;
   private DecimalFilter txt_total_impuesto;
   private static final long serialVersionUID = 1L;

   public TablaComprasDetalles(
      final DecimalFilter txt_total,
      final DecimalFilter txt_exentas,
      final DecimalFilter txt_gravada10,
      final DecimalFilter txt_iva10,
      final DecimalFilter txt_gravada5,
      final DecimalFilter txt_iva5,
      final DecimalFilter txt_total_impuesto,
      final LabelPrincipal lblTextoTotalLinea,
      int DECIMAL_CANTIDAD,
      int DECIMAL_IMPORTE,
      final List<Integer> codigosAEliminar
   ) {
      this.txt_total = txt_total;
      this.txt_exentas = txt_exentas;
      this.txt_gravada10 = txt_gravada10;
      this.txt_iva10 = txt_iva10;
      this.txt_gravada5 = txt_gravada5;
      this.txt_iva5 = txt_iva5;
      this.txt_total_impuesto = txt_total_impuesto;
      this.setRowHeight(25);
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      this.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
      String[] columnNames = new String[]{
         "Código",
         "Descripción",
         "TF",
         "Gravado",
         "IVA",
         "REG",
         "UM",
         "Cantidad",
         "Cant. Bonif",
         "Precio Bruto",
         "Precio Real",
         "Desc.",
         "Precio Neto",
         "Total Iva",
         "Total Gravado",
         "Total Exento",
         "SubTotal",
         "Primario",
         "SW"
      };
      final ModeloTablaCompra model = new ModeloTablaCompra(null, columnNames, this);
      this.setModel(model);
      model.formato(this, DECIMAL_CANTIDAD, DECIMAL_IMPORTE);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(37, 0), "left");
      this.getActionMap().put("left", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaComprasDetalles.this.getSelectedRow();
            int selectedColumn = TablaComprasDetalles.this.getSelectedColumn();

            for (int i = selectedColumn - 1; i >= 0; i--) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaComprasDetalles.this.changeSelection(selectedRow, i, false, false);
                  TablaComprasDetalles.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaComprasDetalles.this.getEditorComponent();
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
            int selectedRow = TablaComprasDetalles.this.getSelectedRow();
            int selectedColumn = TablaComprasDetalles.this.getSelectedColumn();

            for (int i = selectedColumn + 1; i < TablaComprasDetalles.this.getColumnCount(); i++) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaComprasDetalles.this.changeSelection(selectedRow, i, false, false);
                  TablaComprasDetalles.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaComprasDetalles.this.getEditorComponent();
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
            if (TablaComprasDetalles.this.getRowCount() - 1 > 0) {
               int selectedRow = TablaComprasDetalles.this.getSelectedRow() - 1;
               TablaComprasDetalles.this.changeSelection(selectedRow, TablaComprasDetalles.this.getSelectedColumn(), false, false);
               TablaComprasDetalles.this.editCellAt(selectedRow, TablaComprasDetalles.this.getSelectedColumn());
               Component editorComponent = TablaComprasDetalles.this.getEditorComponent();
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
            if (TablaComprasDetalles.this.getRowCount() - 1 > TablaComprasDetalles.this.getSelectedRow()) {
               int selectedRow = TablaComprasDetalles.this.getSelectedRow() + 1;
               TablaComprasDetalles.this.changeSelection(selectedRow, TablaComprasDetalles.this.getSelectedColumn(), false, false);
               TablaComprasDetalles.this.editCellAt(selectedRow, TablaComprasDetalles.this.getSelectedColumn());
               Component editorComponent = TablaComprasDetalles.this.getEditorComponent();
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
                  int selectedRow = TablaComprasDetalles.this.getSelectedRow();
                  int selectedColumn = TablaComprasDetalles.this.getSelectedColumn();
                  String columnName = TablaComprasDetalles.this.getColumnName(selectedColumn);
                  if (columnName.equals("SubTotal") && TablaComprasDetalles.this.getRowCount() - 1 == TablaComprasDetalles.this.getSelectedRow()) {
                     if (TablaComprasDetalles.this.getValueAt(TablaComprasDetalles.this.getSelectedRow(), 1) != null
                        && !String.valueOf(TablaComprasDetalles.this.getValueAt(TablaComprasDetalles.this.getSelectedRow(), 1)).equals("")) {
                        FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
                        lblTextoTotalLinea.setText(String.valueOf(formatoLinea.format(TablaComprasDetalles.this.getRowCount() + 1)));
                        model.addDefaultRow();
                        TablaComprasDetalles.this.editCellAt(TablaComprasDetalles.this.getSelectedRow() + 1, 0);
                        TablaComprasDetalles.this.changeSelection(TablaComprasDetalles.this.getSelectedRow() + 1, 0, false, false);
                        Component editorComponent = TablaComprasDetalles.this.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     } else {
                        SwingUtilities.invokeLater(
                           () -> {
                              TablaComprasDetalles.this.changeSelection(
                                 TablaComprasDetalles.this.getSelectedRow(), TablaComprasDetalles.this.getColumn("Código").getModelIndex(), false, false
                              );
                              TablaComprasDetalles.this.editCellAt(
                                 TablaComprasDetalles.this.getSelectedRow(), TablaComprasDetalles.this.getColumn("Código").getModelIndex()
                              );
                              Component editorComponentx = TablaComprasDetalles.this.getEditorComponent();
                              if (editorComponentx instanceof JTextComponent) {
                                 ((JTextComponent)editorComponentx).selectAll();
                              }

                              editorComponentx.requestFocusInWindow();
                           }
                        );
                     }
                  } else if (columnName.equals("SubTotal") && TablaComprasDetalles.this.getSelectedRow() < TablaComprasDetalles.this.getRowCount() - 1) {
                     TablaComprasDetalles.this.editCellAt(TablaComprasDetalles.this.getSelectedRow() + 1, 0);
                     TablaComprasDetalles.this.changeSelection(TablaComprasDetalles.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaComprasDetalles.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     for (int i = selectedColumn + 1; i < TablaComprasDetalles.this.getColumnCount(); i++) {
                        if (model.isCellEditable(selectedRow, i)) {
                           TablaComprasDetalles.this.editCellAt(selectedRow, i);
                           TablaComprasDetalles.this.changeSelection(selectedRow, i, false, false);
                           Component editorComponent = TablaComprasDetalles.this.getEditorComponent();
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
                  int editingRow = TablaComprasDetalles.this.getEditingRow();
                  int editingColumn = TablaComprasDetalles.this.getEditingColumn();
                  if (editingRow != -1 && editingColumn != -1) {
                     TableCellEditor cellEditor = TablaComprasDetalles.this.getCellEditor();
                     cellEditor.stopCellEditing();
                  }

                  DialogoMessagebox d = new DialogoMessagebox("¿Estás seguro de eliminar esta fila?");
                  d.setLocationRelativeTo(TablaComprasDetalles.this);
                  d.setVisible(true);
                  if (d.isResultadoEncontrado()) {
                     int selectedRow = TablaComprasDetalles.this.getSelectedRow();
                     int primarioColumn = TablaComprasDetalles.this.getColumn("Primario").getModelIndex();
                     if (!String.valueOf(TablaComprasDetalles.this.getValueAt(TablaComprasDetalles.this.getSelectedRow(), primarioColumn)).trim().equals("0")) {
                        codigosAEliminar.add(
                           Integer.parseInt(
                              String.valueOf(TablaComprasDetalles.this.getValueAt(TablaComprasDetalles.this.getSelectedRow(), primarioColumn)).trim()
                           )
                        );
                     }

                     if (selectedRow == 0 && TablaComprasDetalles.this.getRowCount() - 1 == 0) {
                        FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
                        lblTextoTotalLinea.setText(String.valueOf(formatoLinea.format(TablaComprasDetalles.this.getRowCount() + 1)));
                        model.addDefaultRow();
                     }

                     if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel)TablaComprasDetalles.this.getModel();
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        if (model.getRowCount() > 0) {
                           int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                           TablaComprasDetalles.this.changeSelection(newSelectedRow, 0, false, false);
                           TablaComprasDetalles.this.editCellAt(newSelectedRow, 0);
                        }
                     }

                     CalculosTabla.cargarImpuestos(
                        TablaComprasDetalles.this, txt_total, txt_exentas, txt_gravada10, txt_iva10, txt_gravada5, txt_iva5, txt_total_impuesto
                     );
                  } else {
                     TablaComprasDetalles.this.changeSelection(editingRow, 0, false, false);
                     TablaComprasDetalles.this.editCellAt(editingRow, 0);
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
               for (int row = 0; row < TablaComprasDetalles.this.getRowCount(); row++) {
                  String codigo = String.valueOf(TablaComprasDetalles.this.getValueAt(row, 0));
                  if (codigo.equals(buscador.getCodigo())) {
                     TablaComprasDetalles.this.changeSelection(row, 0, false, false);
                     TablaComprasDetalles.this.editCellAt(row, 0);
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
         if (columnName.equals("Código")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)
                  && !valorActual.trim().equals("")
                  && (String.valueOf(this.getValueAt(row, 1)).trim().equals("") || this.getValueAt(row, 1) == null)
               || !valorActual.trim().equals(valorAnterior)) {
               this.buscarBD(row);
               F_CalculoCompras.calcularSubTotal(row, this);
               CalculosTabla.calcularIvas(row, this);
               CalculosTabla.cargarImpuestos(
                  this, this.txt_total, this.txt_exentas, this.txt_gravada10, this.txt_iva10, this.txt_gravada5, this.txt_iva5, this.txt_total_impuesto
               );
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         if (columnName.equals("Cantidad")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior.trim()) && (!valorActual.trim().equals("1,00") || !valorAnterior.trim().equals("1"))) {
               F_CalculoCompras.calcularSubTotal(row, this);
               CalculosTabla.calcularIvas(row, this);
               CalculosTabla.cargarImpuestos(
                  this, this.txt_total, this.txt_exentas, this.txt_gravada10, this.txt_iva10, this.txt_gravada5, this.txt_iva5, this.txt_total_impuesto
               );
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         if (columnName.equals("Precio Bruto")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior.trim())) {
               F_CalculoCompras.calcularSubTotal(row, this);
               CalculosTabla.calcularIvas(row, this);
               CalculosTabla.cargarImpuestos(
                  this, this.txt_total, this.txt_exentas, this.txt_gravada10, this.txt_iva10, this.txt_gravada5, this.txt_iva5, this.txt_total_impuesto
               );
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         if (columnName.equals("Cant. Bonif")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior.trim())) {
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         if (columnName.equals("Desc.")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior.trim())) {
               F_CalculoCompras.calcularPrecioNeto(row, this);
               CalculosTabla.calcularIvas(row, this);
               CalculosTabla.cargarImpuestos(
                  this, this.txt_total, this.txt_exentas, this.txt_gravada10, this.txt_iva10, this.txt_gravada5, this.txt_iva5, this.txt_total_impuesto
               );
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         if (columnName.equals("SubTotal")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior.trim())) {
               CalculosTabla.calcularPrecioReal(row, this);
               CalculosTabla.calcularIvas(row, this);
               CalculosTabla.cargarImpuestos(
                  this, this.txt_total, this.txt_exentas, this.txt_gravada10, this.txt_iva10, this.txt_gravada5, this.txt_iva5, this.txt_total_impuesto
               );
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         super.editingStopped(e);
      }
   }

   private void buscarBD(int row) {
      String codigo = (String)this.getValueAt(row, this.getColumn("Código").getModelIndex());
      String descripcion = (String)this.getValueAt(row, this.getColumn("Descripción").getModelIndex());
      if (!codigo.trim().equals("")) {
         ProductosE producto = ProductosE.cargarProductosComprasProv(codigo, null);
         if (producto != null) {
            this.setValueAt(producto.getCod_producto(), row, this.getColumn("Código").getModelIndex());
            this.setValueAt(producto.getNombre_producto(), row, this.getColumn("Descripción").getModelIndex());
            if (this.getValueAt(row, this.getColumn("Primario").getModelIndex()) == null) {
               this.setValueAt(0, row, this.getColumn("Primario").getModelIndex());
            }

            this.setValueAt(producto.getTipo_fiscal(), row, this.getColumn("TF").getModelIndex());
            this.setValueAt(producto.getIva_producto(), row, this.getColumn("IVA").getModelIndex());
            this.setValueAt(producto.getPorc_regimen(), row, this.getColumn("REG").getModelIndex());
            this.setValueAt(producto.getNombreUnidadMedida(), row, this.getColumn("UM").getModelIndex());
            this.setValueAt(producto.getPorcentaje_gravado(), row, this.getColumn("Gravado").getModelIndex());
            if (!descripcion.trim().equals("") && descripcion != null) {
               CalculosTabla.calcularIvas(row, this);
               CalculosTabla.cargarImpuestos(
                  this, this.txt_total, this.txt_exentas, this.txt_gravada10, this.txt_iva10, this.txt_gravada5, this.txt_iva5, this.txt_total_impuesto
               );
            }
         } else {
            this.setValueAt("", row, this.getColumn("Código").getModelIndex());
            this.setValueAt("", row, this.getColumn("Descripción").getModelIndex());
            if (this.getValueAt(row, this.getColumn("Primario").getModelIndex()) == null) {
               this.setValueAt(0, row, this.getColumn("Precio Bruto").getModelIndex());
            }

            this.setValueAt(0, row, this.getColumn("TF").getModelIndex());
            this.setValueAt(0, row, this.getColumn("IVA").getModelIndex());
            this.setValueAt(0, row, this.getColumn("REG").getModelIndex());
            this.setValueAt("", row, this.getColumn("UM").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Gravado").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Primario").getModelIndex());
            this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
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
      }
   }
}
