package a009AjusteStock;

import a00Productos.ProductosE;
import a2MenuPrincipal.ParametrosDetalleE;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
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
import utilidades.LimiteTextFieldConSQL;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesTabla.BuscadordeCodigos;

public class TablaDetalleAjusteStock extends JTable {
   private int PAR_REPETIR_CODIGO;
   private LimiteTextFieldConSQL txt_cod_deposito;
   private static final long serialVersionUID = 1L;

   public TablaDetalleAjusteStock(
      LimiteTextFieldConSQL txt_cod_deposito,
      final LabelPrincipal lblTextoTotalLinea,
      final List<Integer> codigosAEliminar,
      int DECIMAL_CANTIDAD,
      int DECIMAL_IMPORTE
   ) {
      this.txt_cod_deposito = txt_cod_deposito;
      this.PAR_REPETIR_CODIGO = ParametrosDetalleE.buscarParametros(1, null);
      this.setRowHeight(25);
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      TablaDetalleAjusteStock.ColorRenderer colorRenderer = new TablaDetalleAjusteStock.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
      String[] columnNames = new String[]{"Código", "Descripción", "UM", "Stock", "Cantidad", "Costo", "Primario", "SW"};
      final ModeloTablaAjusteStock model = new ModeloTablaAjusteStock(null, columnNames, this);
      this.setModel(model);
      model.formato(this, DECIMAL_CANTIDAD, DECIMAL_IMPORTE);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(37, 0), "left");
      this.getActionMap().put("left", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaDetalleAjusteStock.this.getSelectedRow();
            int selectedColumn = TablaDetalleAjusteStock.this.getSelectedColumn();

            for (int i = selectedColumn - 1; i >= 0; i--) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleAjusteStock.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleAjusteStock.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleAjusteStock.this.getEditorComponent();
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
            int selectedRow = TablaDetalleAjusteStock.this.getSelectedRow();
            int selectedColumn = TablaDetalleAjusteStock.this.getSelectedColumn();

            for (int i = selectedColumn + 1; i < TablaDetalleAjusteStock.this.getColumnCount(); i++) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleAjusteStock.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleAjusteStock.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleAjusteStock.this.getEditorComponent();
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
            if (TablaDetalleAjusteStock.this.getRowCount() - 1 > 0) {
               int selectedRow = TablaDetalleAjusteStock.this.getSelectedRow() - 1;
               TablaDetalleAjusteStock.this.changeSelection(selectedRow, TablaDetalleAjusteStock.this.getSelectedColumn(), false, false);
               TablaDetalleAjusteStock.this.editCellAt(selectedRow, TablaDetalleAjusteStock.this.getSelectedColumn());
               Component editorComponent = TablaDetalleAjusteStock.this.getEditorComponent();
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
            if (TablaDetalleAjusteStock.this.getRowCount() - 1 > TablaDetalleAjusteStock.this.getSelectedRow()) {
               int selectedRow = TablaDetalleAjusteStock.this.getSelectedRow() + 1;
               TablaDetalleAjusteStock.this.changeSelection(selectedRow, TablaDetalleAjusteStock.this.getSelectedColumn(), false, false);
               TablaDetalleAjusteStock.this.editCellAt(selectedRow, TablaDetalleAjusteStock.this.getSelectedColumn());
               Component editorComponent = TablaDetalleAjusteStock.this.getEditorComponent();
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
                  int selectedRow = TablaDetalleAjusteStock.this.getSelectedRow();
                  int selectedColumn = TablaDetalleAjusteStock.this.getSelectedColumn();
                  String columnName = TablaDetalleAjusteStock.this.getColumnName(selectedColumn);
                  if (columnName.equals("Cantidad") && TablaDetalleAjusteStock.this.getRowCount() - 1 == TablaDetalleAjusteStock.this.getSelectedRow()) {
                     if (TablaDetalleAjusteStock.this.getValueAt(TablaDetalleAjusteStock.this.getSelectedRow(), 1) != null
                        && !String.valueOf(TablaDetalleAjusteStock.this.getValueAt(TablaDetalleAjusteStock.this.getSelectedRow(), 1)).equals("")) {
                        lblTextoTotalLinea.setText(String.valueOf(TablaDetalleAjusteStock.this.getRowCount()));
                        model.addDefaultRow();
                        TablaDetalleAjusteStock.this.editCellAt(TablaDetalleAjusteStock.this.getSelectedRow() + 1, 0);
                        TablaDetalleAjusteStock.this.changeSelection(TablaDetalleAjusteStock.this.getSelectedRow() + 1, 0, false, false);
                        Component editorComponent = TablaDetalleAjusteStock.this.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     } else {
                        SwingUtilities.invokeLater(
                           () -> {
                              TablaDetalleAjusteStock.this.changeSelection(
                                 TablaDetalleAjusteStock.this.getSelectedRow(), TablaDetalleAjusteStock.this.getColumn("Código").getModelIndex(), false, false
                              );
                              TablaDetalleAjusteStock.this.editCellAt(
                                 TablaDetalleAjusteStock.this.getSelectedRow(), TablaDetalleAjusteStock.this.getColumn("Código").getModelIndex()
                              );
                              Component editorComponentx = TablaDetalleAjusteStock.this.getEditorComponent();
                              if (editorComponentx instanceof JTextComponent) {
                                 ((JTextComponent)editorComponentx).selectAll();
                              }

                              editorComponentx.requestFocusInWindow();
                           }
                        );
                     }
                  } else if (columnName.equals("Cantidad") && TablaDetalleAjusteStock.this.getSelectedRow() < TablaDetalleAjusteStock.this.getRowCount() - 1) {
                     TablaDetalleAjusteStock.this.editCellAt(TablaDetalleAjusteStock.this.getSelectedRow() + 1, 0);
                     TablaDetalleAjusteStock.this.changeSelection(TablaDetalleAjusteStock.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaDetalleAjusteStock.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     for (int i = selectedColumn + 1; i < TablaDetalleAjusteStock.this.getColumnCount(); i++) {
                        if (model.isCellEditable(selectedRow, i)) {
                           TablaDetalleAjusteStock.this.editCellAt(selectedRow, i);
                           TablaDetalleAjusteStock.this.changeSelection(selectedRow, i, false, false);
                           Component editorComponent = TablaDetalleAjusteStock.this.getEditorComponent();
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
                  int editingRow = TablaDetalleAjusteStock.this.getEditingRow();
                  int editingColumn = TablaDetalleAjusteStock.this.getEditingColumn();
                  if (editingRow != -1 && editingColumn != -1) {
                     TableCellEditor cellEditor = TablaDetalleAjusteStock.this.getCellEditor();
                     cellEditor.stopCellEditing();
                  }

                  DialogoMessagebox d = new DialogoMessagebox("¿Estás seguro de eliminar esta fila?");
                  d.setLocationRelativeTo(TablaDetalleAjusteStock.this);
                  d.setVisible(true);
                  if (d.isResultadoEncontrado()) {
                     int selectedRow = TablaDetalleAjusteStock.this.getSelectedRow();
                     int primarioColumn = TablaDetalleAjusteStock.this.getColumn("Primario").getModelIndex();
                     if (!String.valueOf(TablaDetalleAjusteStock.this.getValueAt(TablaDetalleAjusteStock.this.getSelectedRow(), primarioColumn))
                        .trim()
                        .equals("0")) {
                        codigosAEliminar.add(
                           Integer.parseInt(
                              String.valueOf(TablaDetalleAjusteStock.this.getValueAt(TablaDetalleAjusteStock.this.getSelectedRow(), primarioColumn)).trim()
                           )
                        );
                     }

                     if (selectedRow == 0 && TablaDetalleAjusteStock.this.getRowCount() - 1 == 0) {
                        FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
                        lblTextoTotalLinea.setText(String.valueOf(formatoLinea.format(TablaDetalleAjusteStock.this.getRowCount() + 1)));
                        model.addDefaultRow();
                     }

                     if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel)TablaDetalleAjusteStock.this.getModel();
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        if (model.getRowCount() > 0) {
                           int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                           TablaDetalleAjusteStock.this.changeSelection(newSelectedRow, 0, false, false);
                           TablaDetalleAjusteStock.this.editCellAt(newSelectedRow, 0);
                        }
                     }
                  } else {
                     TablaDetalleAjusteStock.this.changeSelection(editingRow, 0, false, false);
                     TablaDetalleAjusteStock.this.editCellAt(editingRow, 0);
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
               for (int row = 0; row < TablaDetalleAjusteStock.this.getRowCount(); row++) {
                  String codigo = String.valueOf(TablaDetalleAjusteStock.this.getValueAt(row, 0));
                  if (codigo.equals(buscador.getCodigo())) {
                     TablaDetalleAjusteStock.this.changeSelection(row, 0, false, false);
                     TablaDetalleAjusteStock.this.editCellAt(row, 0);
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
               this.changeSelection(fila, this.getColumn("Cantidad").getModelIndex(), false, false);
               this.editCellAt(fila, this.getColumn("Cantidad").getModelIndex());
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

   private void buscarBD(String codigo, int row) {
      if (!codigo.trim().equals("")) {
         int cod_deposito = Integer.parseInt(this.txt_cod_deposito.getText().replace(".", ""));
         if (cod_deposito == 0) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Se tiene que cargar un deposito !", "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.txt_cod_deposito.requestFocusInWindow();
            return;
         }

         this.txt_cod_deposito.setEnabled(false);
         if (this.getRowCount() == 1) {
            this.txt_cod_deposito.setEnabled(false);
         }

         ProductosE producto = ProductosE.cargarProductosAjusteStock(codigo, Integer.parseInt(this.txt_cod_deposito.getText().replace(".", "")), null);
         FormatoDecimalOperacionSistema formato = new FormatoDecimalOperacionSistema(2);
         if (producto != null) {
            this.setValueAt(producto.getCod_producto(), row, this.getColumn("Código").getModelIndex());
            this.setValueAt(producto.getNombre_producto(), row, this.getColumn("Descripción").getModelIndex());
            this.setValueAt(producto.getNombreUnidadMedida(), row, this.getColumn("UM").getModelIndex());
            this.setValueAt(formato.format(producto.getStock()), row, this.getColumn("Stock").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Cantidad").getModelIndex());
            this.setValueAt(formato.format(producto.getCosto_producto()), row, this.getColumn("Costo").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Primario").getModelIndex());
            this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
         } else {
            this.setValueAt("", row, this.getColumn("Código").getModelIndex());
            this.setValueAt("", row, this.getColumn("Descripción").getModelIndex());
            this.setValueAt("", row, this.getColumn("UM").getModelIndex());
            this.setValueAt(formato.format(0L), row, this.getColumn("Stock").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Cantidad").getModelIndex());
            this.setValueAt(formato.format(0L), row, this.getColumn("Costo").getModelIndex());
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
               this.buscarParametros(row);
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
