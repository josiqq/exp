package a009AjustePrecios;

import a00Productos.ProductosE;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQL;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesTabla.BuscadordeCodigos;

public class TablaAjustePreciosDetalles extends JTable {
   private LimiteTextFieldConSQL txt_lista;
   private int DECIMAL_IMPORTE;
   private static final long serialVersionUID = 1L;

   public TablaAjustePreciosDetalles(LimiteTextFieldConSQL txt_lista, final LabelPrincipal lblTextoTotalLinea, int par_decimal_importe) {
      this.setRowHeight(25);
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      this.DECIMAL_IMPORTE = par_decimal_importe;
      this.txt_lista = txt_lista;
      this.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
      String[] columnNames = new String[]{
         "Código",
         "Descripción",
         "UM",
         "Lista de Precio",
         "CodMon",
         "Moneda",
         "Costo",
         "Precio Anterior",
         "Margen Precio",
         "Margen Costo",
         "Precio Minimo",
         "Precio Nuevo",
         "Primario",
         "Nro Lista",
         "SW"
      };
      final ModeloTablaAjustePrecios model = new ModeloTablaAjustePrecios(null, columnNames);
      TablaAjustePreciosDetalles.ColorRenderer colorRenderer = new TablaAjustePreciosDetalles.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
      this.setModel(model);
      model.formato(this, par_decimal_importe);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(37, 0), "left");
      this.getActionMap().put("left", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaAjustePreciosDetalles.this.getSelectedRow();
            int selectedColumn = TablaAjustePreciosDetalles.this.getSelectedColumn();

            for (int i = selectedColumn - 1; i >= 0; i--) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaAjustePreciosDetalles.this.changeSelection(selectedRow, i, false, false);
                  TablaAjustePreciosDetalles.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaAjustePreciosDetalles.this.getEditorComponent();
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
            int selectedRow = TablaAjustePreciosDetalles.this.getSelectedRow();
            int selectedColumn = TablaAjustePreciosDetalles.this.getSelectedColumn();

            for (int i = selectedColumn + 1; i < TablaAjustePreciosDetalles.this.getColumnCount(); i++) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaAjustePreciosDetalles.this.changeSelection(selectedRow, i, false, false);
                  TablaAjustePreciosDetalles.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaAjustePreciosDetalles.this.getEditorComponent();
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
            if (TablaAjustePreciosDetalles.this.getRowCount() - 1 > 0) {
               int selectedRow = TablaAjustePreciosDetalles.this.getSelectedRow() - 1;
               TablaAjustePreciosDetalles.this.changeSelection(selectedRow, TablaAjustePreciosDetalles.this.getSelectedColumn(), false, false);
               TablaAjustePreciosDetalles.this.editCellAt(selectedRow, TablaAjustePreciosDetalles.this.getSelectedColumn());
               Component editorComponent = TablaAjustePreciosDetalles.this.getEditorComponent();
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
            if (TablaAjustePreciosDetalles.this.getRowCount() - 1 > TablaAjustePreciosDetalles.this.getSelectedRow()) {
               int selectedRow = TablaAjustePreciosDetalles.this.getSelectedRow() + 1;
               TablaAjustePreciosDetalles.this.changeSelection(selectedRow, TablaAjustePreciosDetalles.this.getSelectedColumn(), false, false);
               TablaAjustePreciosDetalles.this.editCellAt(selectedRow, TablaAjustePreciosDetalles.this.getSelectedColumn());
               Component editorComponent = TablaAjustePreciosDetalles.this.getEditorComponent();
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
                  int selectedRow = TablaAjustePreciosDetalles.this.getSelectedRow();
                  int selectedColumn = TablaAjustePreciosDetalles.this.getSelectedColumn();
                  String columnName = TablaAjustePreciosDetalles.this.getColumnName(selectedColumn);
                  if (columnName.equals("Precio Nuevo")
                     && TablaAjustePreciosDetalles.this.getRowCount() - 1 == TablaAjustePreciosDetalles.this.getSelectedRow()) {
                     if (TablaAjustePreciosDetalles.this.getValueAt(TablaAjustePreciosDetalles.this.getSelectedRow(), 1) != null
                        && !String.valueOf(TablaAjustePreciosDetalles.this.getValueAt(TablaAjustePreciosDetalles.this.getSelectedRow(), 1)).equals("")) {
                        lblTextoTotalLinea.setText(String.valueOf(TablaAjustePreciosDetalles.this.getRowCount()));
                        model.addDefaultRow();
                        TablaAjustePreciosDetalles.this.editCellAt(TablaAjustePreciosDetalles.this.getSelectedRow() + 1, 0);
                        TablaAjustePreciosDetalles.this.changeSelection(TablaAjustePreciosDetalles.this.getSelectedRow() + 1, 0, false, false);
                        Component editorComponent = TablaAjustePreciosDetalles.this.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     } else {
                        SwingUtilities.invokeLater(
                           () -> {
                              TablaAjustePreciosDetalles.this.changeSelection(
                                 TablaAjustePreciosDetalles.this.getSelectedRow(),
                                 TablaAjustePreciosDetalles.this.getColumn("Código").getModelIndex(),
                                 false,
                                 false
                              );
                              TablaAjustePreciosDetalles.this.editCellAt(
                                 TablaAjustePreciosDetalles.this.getSelectedRow(), TablaAjustePreciosDetalles.this.getColumn("Código").getModelIndex()
                              );
                              Component editorComponentx = TablaAjustePreciosDetalles.this.getEditorComponent();
                              if (editorComponentx instanceof JTextComponent) {
                                 ((JTextComponent)editorComponentx).selectAll();
                              }

                              editorComponentx.requestFocusInWindow();
                           }
                        );
                     }
                  } else if (columnName.equals("Precio Nuevo")
                     && TablaAjustePreciosDetalles.this.getSelectedRow() < TablaAjustePreciosDetalles.this.getRowCount() - 1) {
                     TablaAjustePreciosDetalles.this.editCellAt(TablaAjustePreciosDetalles.this.getSelectedRow() + 1, 0);
                     TablaAjustePreciosDetalles.this.changeSelection(TablaAjustePreciosDetalles.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaAjustePreciosDetalles.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     for (int i = selectedColumn + 1; i < TablaAjustePreciosDetalles.this.getColumnCount(); i++) {
                        if (model.isCellEditable(selectedRow, i)) {
                           TablaAjustePreciosDetalles.this.editCellAt(selectedRow, i);
                           TablaAjustePreciosDetalles.this.changeSelection(selectedRow, i, false, false);
                           Component editorComponent = TablaAjustePreciosDetalles.this.getEditorComponent();
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
                  int editingRow = TablaAjustePreciosDetalles.this.getEditingRow();
                  int editingColumn = TablaAjustePreciosDetalles.this.getEditingColumn();
                  if (editingRow != -1 && editingColumn != -1) {
                     TableCellEditor cellEditor = TablaAjustePreciosDetalles.this.getCellEditor();
                     cellEditor.stopCellEditing();
                  }

                  DialogoMessagebox d = new DialogoMessagebox("¿Estás seguro de eliminar esta fila?");
                  d.setLocationRelativeTo(TablaAjustePreciosDetalles.this);
                  d.setVisible(true);
                  if (d.isResultadoEncontrado()) {
                     int selectedRow = TablaAjustePreciosDetalles.this.getSelectedRow();
                     int primarioColumn = TablaAjustePreciosDetalles.this.getColumn("Primario").getModelIndex();
                     String.valueOf(TablaAjustePreciosDetalles.this.getValueAt(TablaAjustePreciosDetalles.this.getSelectedRow(), primarioColumn))
                        .trim()
                        .equals("0");
                     if (selectedRow == 0 && TablaAjustePreciosDetalles.this.getRowCount() - 1 == 0) {
                        lblTextoTotalLinea.setText(String.valueOf(TablaAjustePreciosDetalles.this.getRowCount()));
                        model.addDefaultRow();
                     }

                     if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel)TablaAjustePreciosDetalles.this.getModel();
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        if (model.getRowCount() > 0) {
                           int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                           TablaAjustePreciosDetalles.this.changeSelection(newSelectedRow, 0, false, false);
                           TablaAjustePreciosDetalles.this.editCellAt(newSelectedRow, 0);
                        }

                        int column = 8;
                        BigDecimal total = BigDecimal.ZERO;

                        for (int fila = 0; fila < TablaAjustePreciosDetalles.this.getRowCount(); fila++) {
                           BigDecimal subtotal = new BigDecimal(
                              String.valueOf(TablaAjustePreciosDetalles.this.getValueAt(fila, column)).replace(".", "").replace(",", ".")
                           );
                           total = total.add(subtotal);
                        }
                     }
                  } else {
                     TablaAjustePreciosDetalles.this.changeSelection(editingRow, 0, false, false);
                     TablaAjustePreciosDetalles.this.editCellAt(editingRow, 0);
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
               for (int row = 0; row < TablaAjustePreciosDetalles.this.getRowCount(); row++) {
                  String codigo = String.valueOf(TablaAjustePreciosDetalles.this.getValueAt(row, 0));
                  if (codigo.equals(buscador.getCodigo())) {
                     TablaAjustePreciosDetalles.this.changeSelection(row, 0, false, false);
                     TablaAjustePreciosDetalles.this.editCellAt(row, 0);
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
         ProductosE producto = ProductosE.buscarProductoAjustePrecio(codigo, Integer.parseInt(this.txt_lista.getText()), null);
         if (producto != null) {
            FormatoDecimalOperacionSistema formatoDecimalImporte = new FormatoDecimalOperacionSistema(10);
            this.setValueAt(producto.getCod_producto(), row, this.getColumn("Código").getModelIndex());
            this.setValueAt(producto.getNombre_producto(), row, this.getColumn("Descripción").getModelIndex());
            this.setValueAt(producto.getNombreUnidadMedida(), row, this.getColumn("UM").getModelIndex());
            this.setValueAt(producto.getNombreListaPrecio(), row, this.getColumn("Lista de Precio").getModelIndex());
            this.setValueAt(producto.getCod_moneda_costo(), row, this.getColumn("CodMon").getModelIndex());
            this.setValueAt(producto.getNombreMoneda(), row, this.getColumn("Moneda").getModelIndex());
            this.setValueAt(formatoDecimalImporte.format(producto.getCosto_producto()), row, this.getColumn("Costo").getModelIndex());
            this.setValueAt(formatoDecimalImporte.format(producto.getPrecio()), row, this.getColumn("Precio Anterior").getModelIndex());
            this.setValueAt(formatoDecimalImporte.format(producto.getPrecio()), row, this.getColumn("Precio Nuevo").getModelIndex());
            this.setValueAt(formatoDecimalImporte.format(producto.getPrecio_minimo()), row, this.getColumn("Precio Minimo").getModelIndex());
            this.setValueAt(this.txt_lista.getValue(), row, this.getColumn("Nro Lista").getModelIndex());
            this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            this.setValueAt(formatoDecimalImporte.format(producto.getPrecio()), row, this.getColumn("Precio Nuevo").getModelIndex());
         } else {
            this.setValueAt("", row, this.getColumn("Código").getModelIndex());
            this.setValueAt("", row, this.getColumn("Descripción").getModelIndex());
            this.setValueAt("", row, this.getColumn("UM").getModelIndex());
            this.setValueAt("", row, this.getColumn("Lista de Precio").getModelIndex());
            this.setValueAt(0, row, this.getColumn("CodMon").getModelIndex());
            this.setValueAt("", row, this.getColumn("Moneda").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Costo").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Precio Anterior").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Precio Nuevo").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Precio Minimo").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Nro Lista").getModelIndex());
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
            if (!valorActual.trim().equals(valorAnterior)) {
               this.buscarBD(row);
            }
         }

         if (columnName.equals("Margen Costo")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior.trim()) && (!valorActual.trim().equals("0,00") || !valorAnterior.trim().equals("0"))) {
               CalculoTabla.calcularMargenCosto(row, this, this.DECIMAL_IMPORTE);
            }
         }

         if (columnName.equals("Margen Precio")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior.trim()) && (!valorActual.trim().equals("0,00") || !valorAnterior.trim().equals("0"))) {
               CalculoTabla.calcularMargenPrecio(row, this, this.DECIMAL_IMPORTE);
            }
         }

         if (columnName.equals("Precio Minimo")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)) {
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         if (columnName.equals("Precio Nuevo")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)) {
               this.setValueAt(0, row, this.getColumn("Margen Costo").getModelIndex());
               this.setValueAt(0, row, this.getColumn("Margen Precio").getModelIndex());
            }
         }

         super.editingStopped(e);
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
