package utilidadesTablaDetalle;

import a00Productos.ProductosE;
import a00pedidosProveedores.DecimalFilter;
import a00pedidosProveedores.ModeloTablaProveedores;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;
import utilidades.LabelPrincipal;
import utilidadesOperaciones.F_Calcular_PedidosProveedores;
import utilidadesOperaciones.F_OperacionesSistema;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesTabla.BuscadordeCodigos;

public class TablaDetallePedidos extends JTable {
   private int DECIMAL_IMPORTE;
   private DecimalFilter txt_total;
   private static final long serialVersionUID = 1L;

   public TablaDetallePedidos(
      final LabelPrincipal lblTextoTotalLinea, final List<Integer> codigosAEliminar, int DECIMAL_CANTIDAD, int DECIMAL_IMPORTE, final DecimalFilter txt_total
   ) {
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      this.txt_total = txt_total;
      this.DECIMAL_IMPORTE = DECIMAL_IMPORTE;
      this.setRowHeight(25);
      TablaDetallePedidos.ColorRenderer colorRenderer = new TablaDetallePedidos.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
      String[] columnNames = new String[]{"Código", "Descripción", "TF", "IVA", "REG", "UM", "Cantidad", "Cant. Bonif", "Precio", "SubTotal", "Primario", "SW"};
      final ModeloTablaProveedores model = new ModeloTablaProveedores(null, columnNames, this);
      this.setModel(model);
      model.formato(this, DECIMAL_CANTIDAD, DECIMAL_IMPORTE);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(37, 0), "left");
      this.getActionMap().put("left", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaDetallePedidos.this.getSelectedRow();
            int selectedColumn = TablaDetallePedidos.this.getSelectedColumn();

            for (int i = selectedColumn - 1; i >= 0; i--) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetallePedidos.this.changeSelection(selectedRow, i, false, false);
                  TablaDetallePedidos.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetallePedidos.this.getEditorComponent();
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
            int selectedRow = TablaDetallePedidos.this.getSelectedRow();
            int selectedColumn = TablaDetallePedidos.this.getSelectedColumn();

            for (int i = selectedColumn + 1; i < TablaDetallePedidos.this.getColumnCount(); i++) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetallePedidos.this.changeSelection(selectedRow, i, false, false);
                  TablaDetallePedidos.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetallePedidos.this.getEditorComponent();
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
            if (TablaDetallePedidos.this.getRowCount() - 1 > 0) {
               int selectedRow = TablaDetallePedidos.this.getSelectedRow() - 1;
               TablaDetallePedidos.this.changeSelection(selectedRow, TablaDetallePedidos.this.getSelectedColumn(), false, false);
               TablaDetallePedidos.this.editCellAt(selectedRow, TablaDetallePedidos.this.getSelectedColumn());
               Component editorComponent = TablaDetallePedidos.this.getEditorComponent();
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
            if (TablaDetallePedidos.this.getRowCount() - 1 > TablaDetallePedidos.this.getSelectedRow()) {
               int selectedRow = TablaDetallePedidos.this.getSelectedRow() + 1;
               TablaDetallePedidos.this.changeSelection(selectedRow, TablaDetallePedidos.this.getSelectedColumn(), false, false);
               TablaDetallePedidos.this.editCellAt(selectedRow, TablaDetallePedidos.this.getSelectedColumn());
               Component editorComponent = TablaDetallePedidos.this.getEditorComponent();
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
                  int selectedRow = TablaDetallePedidos.this.getSelectedRow();
                  int selectedColumn = TablaDetallePedidos.this.getSelectedColumn();
                  String columnName = TablaDetallePedidos.this.getColumnName(selectedColumn);
                  if (columnName.equals("Precio") && TablaDetallePedidos.this.getRowCount() - 1 == TablaDetallePedidos.this.getSelectedRow()) {
                     if (TablaDetallePedidos.this.getValueAt(TablaDetallePedidos.this.getSelectedRow(), 1) != null
                        && !String.valueOf(TablaDetallePedidos.this.getValueAt(TablaDetallePedidos.this.getSelectedRow(), 1)).equals("")) {
                        lblTextoTotalLinea.setText(String.valueOf(TablaDetallePedidos.this.getRowCount()));
                        model.addDefaultRow();
                        TablaDetallePedidos.this.editCellAt(TablaDetallePedidos.this.getSelectedRow() + 1, 0);
                        TablaDetallePedidos.this.changeSelection(TablaDetallePedidos.this.getSelectedRow() + 1, 0, false, false);
                        Component editorComponent = TablaDetallePedidos.this.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     } else {
                        SwingUtilities.invokeLater(
                           () -> {
                              TablaDetallePedidos.this.changeSelection(
                                 TablaDetallePedidos.this.getSelectedRow(), TablaDetallePedidos.this.getColumn("Código").getModelIndex(), false, false
                              );
                              TablaDetallePedidos.this.editCellAt(
                                 TablaDetallePedidos.this.getSelectedRow(), TablaDetallePedidos.this.getColumn("Código").getModelIndex()
                              );
                              Component editorComponentx = TablaDetallePedidos.this.getEditorComponent();
                              if (editorComponentx instanceof JTextComponent) {
                                 ((JTextComponent)editorComponentx).selectAll();
                              }

                              editorComponentx.requestFocusInWindow();
                           }
                        );
                     }
                  } else if (columnName.equals("Precio") && TablaDetallePedidos.this.getSelectedRow() < TablaDetallePedidos.this.getRowCount() - 1) {
                     TablaDetallePedidos.this.editCellAt(TablaDetallePedidos.this.getSelectedRow() + 1, 0);
                     TablaDetallePedidos.this.changeSelection(TablaDetallePedidos.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaDetallePedidos.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     for (int i = selectedColumn + 1; i < TablaDetallePedidos.this.getColumnCount(); i++) {
                        if (model.isCellEditable(selectedRow, i)) {
                           TablaDetallePedidos.this.editCellAt(selectedRow, i);
                           TablaDetallePedidos.this.changeSelection(selectedRow, i, false, false);
                           Component editorComponent = TablaDetallePedidos.this.getEditorComponent();
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
                  int editingRow = TablaDetallePedidos.this.getEditingRow();
                  int editingColumn = TablaDetallePedidos.this.getEditingColumn();
                  if (editingRow != -1 && editingColumn != -1) {
                     TableCellEditor cellEditor = TablaDetallePedidos.this.getCellEditor();
                     cellEditor.stopCellEditing();
                  }

                  DialogoMessagebox d = new DialogoMessagebox("¿Estás seguro de eliminar esta fila?");
                  d.setLocationRelativeTo(TablaDetallePedidos.this);
                  d.setVisible(true);
                  if (d.isResultadoEncontrado()) {
                     int selectedRow = TablaDetallePedidos.this.getSelectedRow();
                     int primarioColumn = TablaDetallePedidos.this.getColumn("Primario").getModelIndex();
                     if (!String.valueOf(TablaDetallePedidos.this.getValueAt(TablaDetallePedidos.this.getSelectedRow(), primarioColumn)).trim().equals("0")) {
                        codigosAEliminar.add(
                           Integer.parseInt(
                              String.valueOf(TablaDetallePedidos.this.getValueAt(TablaDetallePedidos.this.getSelectedRow(), primarioColumn)).trim()
                           )
                        );
                     }

                     if (selectedRow == 0 && TablaDetallePedidos.this.getRowCount() - 1 == 0) {
                        FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
                        lblTextoTotalLinea.setText(String.valueOf(formatoLinea.format(TablaDetallePedidos.this.getRowCount() + 1)));
                        model.addDefaultRow();
                     }

                     if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel)TablaDetallePedidos.this.getModel();
                        model.removeRow(selectedRow);
                        TablaDetallePedidos.this.updateSubtotal();
                        model.fireTableDataChanged();
                        if (model.getRowCount() > 0) {
                           int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                           TablaDetallePedidos.this.changeSelection(newSelectedRow, 0, false, false);
                           TablaDetallePedidos.this.editCellAt(newSelectedRow, 0);
                        }

                        int column = TablaDetallePedidos.this.getColumn("Precio").getModelIndex();
                        BigDecimal total = BigDecimal.ZERO;

                        for (int fila = 0; fila < TablaDetallePedidos.this.getRowCount(); fila++) {
                           BigDecimal subtotal = new BigDecimal(
                              String.valueOf(TablaDetallePedidos.this.getValueAt(fila, column)).replace(".", "").replace(",", ".")
                           );
                           total = total.add(subtotal);
                        }

                        F_Calcular_PedidosProveedores.calcularTotal(TablaDetallePedidos.this, txt_total);
                     }
                  } else {
                     TablaDetallePedidos.this.changeSelection(editingRow, 0, false, false);
                     TablaDetallePedidos.this.editCellAt(editingRow, 0);
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
               for (int row = 0; row < TablaDetallePedidos.this.getRowCount(); row++) {
                  String codigo = String.valueOf(TablaDetallePedidos.this.getValueAt(row, 0));
                  if (codigo.equals(buscador.getCodigo())) {
                     TablaDetallePedidos.this.changeSelection(row, 0, false, false);
                     TablaDetallePedidos.this.editCellAt(row, 0);
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

   private void buscarBD(int row) {
      String codigo = (String)this.getValueAt(row, this.getColumn("Código").getModelIndex());
      if (!codigo.trim().equals("")) {
         ProductosE producto = ProductosE.cargarProductosPedidosProv(codigo, null);
         if (producto != null) {
            this.setValueAt(producto.getCod_producto(), row, this.getColumn("Código").getModelIndex());
            this.setValueAt(producto.getNombre_producto(), row, this.getColumn("Descripción").getModelIndex());
            if (this.getValueAt(row, this.getColumn("Primario").getModelIndex()) == null) {
               this.setValueAt(0, row, this.getColumn("Precio").getModelIndex());
            }

            this.setValueAt(producto.getTipo_fiscal(), row, this.getColumn("TF").getModelIndex());
            this.setValueAt(producto.getIva_producto(), row, this.getColumn("IVA").getModelIndex());
            this.setValueAt(producto.getPorcentaje_gravado(), row, this.getColumn("REG").getModelIndex());
            this.setValueAt(producto.getNombreUnidadMedida(), row, this.getColumn("UM").getModelIndex());
         } else {
            this.setValueAt("", row, this.getColumn("Código").getModelIndex());
            this.setValueAt("", row, this.getColumn("Descripción").getModelIndex());
            if (this.getValueAt(row, this.getColumn("Primario").getModelIndex()) == null) {
               this.setValueAt(0, row, this.getColumn("Precio").getModelIndex());
            }

            this.setValueAt(0, row, this.getColumn("TF").getModelIndex());
            this.setValueAt(0, row, this.getColumn("IVA").getModelIndex());
            this.setValueAt(0, row, this.getColumn("REG").getModelIndex());
            this.setValueAt("", row, this.getColumn("UM").getModelIndex());
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
            }
         }

         super.editingStopped(e);
         if (columnName.equals("Cantidad") || columnName.equals("Precio")) {
            this.updateSubtotal();
            F_Calcular_PedidosProveedores.calcularTotal(this, this.txt_total);
         }
      }
   }

   private void updateSubtotal() {
      int cantidadColumn = this.getColumn("Cantidad").getModelIndex();
      int precioColumn = this.getColumn("Precio").getModelIndex();
      int subtotalColumn = this.getColumn("SubTotal").getModelIndex();
      int codigoColumn = this.getColumn("Código").getModelIndex();
      int descripcioncolumn = this.getColumn("Descripción").getModelIndex();

      for (int row = 0; row < this.getRowCount(); row++) {
         if (!String.valueOf(this.getValueAt(row, codigoColumn)).trim().equals("")
            && this.getValueAt(row, codigoColumn) != null
            && !String.valueOf(this.getValueAt(row, descripcioncolumn)).trim().equals("")
            && this.getValueAt(row, descripcioncolumn) != null) {
            try {
               String v_cantidad = String.valueOf(this.getValueAt(row, cantidadColumn)).replace(".", "").replace(",", ".");
               String v_precio = String.valueOf(this.getValueAt(row, precioColumn)).replace(".", "").replace(",", ".");
               BigDecimal cantidad = new BigDecimal(v_cantidad);
               BigDecimal precio = new BigDecimal(v_precio);
               BigDecimal subtotal = F_OperacionesSistema.multiplicar(cantidad, precio);
               FormatoDecimalOperacionSistema formatoDecimal = new FormatoDecimalOperacionSistema(this.DECIMAL_IMPORTE);
               String formattedSubtotal = formatoDecimal.format(subtotal);
               this.setValueAt(formattedSubtotal, row, subtotalColumn);
            } catch (NumberFormatException var14) {
               this.setValueAt("", row, subtotalColumn);
            }
         }
      }
   }

   class ColorRenderer extends DefaultTableCellRenderer {
      private static final long serialVersionUID = 1L;
      private Color colorFilaPar = new Color(224, 224, 224);
      private Color colorFilaImpar = new Color(245, 245, 245);

      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         if (!isSelected) {
            c.setBackground(row % 2 == 0 ? this.colorFilaPar : this.colorFilaImpar);
         }

         return c;
      }
   }
}
