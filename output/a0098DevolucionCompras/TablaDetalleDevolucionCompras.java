package a0098DevolucionCompras;

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
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesTabla.BuscadordeCodigos;

public class TablaDetalleDevolucionCompras extends JTable {
   private int PAR_REPETIR_CODIGO = ParametrosDetalleE.buscarParametros(1, null);
   private static final long serialVersionUID = 1L;

   public TablaDetalleDevolucionCompras(
      final LabelPrincipal lblTextoTotalLinea, final List<Integer> codigosAEliminar, int DECIMAL_CANTIDAD, int DECIMAL_IMPORTE
   ) {
      this.setRowHeight(25);
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      TablaDetalleDevolucionCompras.ColorRenderer colorRenderer = new TablaDetalleDevolucionCompras.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
      String[] columnNames = new String[]{"Código", "Descripción", "UM", "Cantidad", "Primario", "SW"};
      final ModeloTablaDevolucionCompras model = new ModeloTablaDevolucionCompras(null, columnNames, this);
      this.setModel(model);
      model.formato(this, DECIMAL_CANTIDAD, DECIMAL_IMPORTE);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(37, 0), "left");
      this.getActionMap().put("left", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaDetalleDevolucionCompras.this.getSelectedRow();
            int selectedColumn = TablaDetalleDevolucionCompras.this.getSelectedColumn();

            for (int i = selectedColumn - 1; i >= 0; i--) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleDevolucionCompras.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleDevolucionCompras.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleDevolucionCompras.this.getEditorComponent();
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
            int selectedRow = TablaDetalleDevolucionCompras.this.getSelectedRow();
            int selectedColumn = TablaDetalleDevolucionCompras.this.getSelectedColumn();

            for (int i = selectedColumn + 1; i < TablaDetalleDevolucionCompras.this.getColumnCount(); i++) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleDevolucionCompras.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleDevolucionCompras.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleDevolucionCompras.this.getEditorComponent();
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
            if (TablaDetalleDevolucionCompras.this.getRowCount() - 1 > 0) {
               int selectedRow = TablaDetalleDevolucionCompras.this.getSelectedRow() - 1;
               TablaDetalleDevolucionCompras.this.changeSelection(selectedRow, TablaDetalleDevolucionCompras.this.getSelectedColumn(), false, false);
               TablaDetalleDevolucionCompras.this.editCellAt(selectedRow, TablaDetalleDevolucionCompras.this.getSelectedColumn());
               Component editorComponent = TablaDetalleDevolucionCompras.this.getEditorComponent();
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
            if (TablaDetalleDevolucionCompras.this.getRowCount() - 1 > TablaDetalleDevolucionCompras.this.getSelectedRow()) {
               int selectedRow = TablaDetalleDevolucionCompras.this.getSelectedRow() + 1;
               TablaDetalleDevolucionCompras.this.changeSelection(selectedRow, TablaDetalleDevolucionCompras.this.getSelectedColumn(), false, false);
               TablaDetalleDevolucionCompras.this.editCellAt(selectedRow, TablaDetalleDevolucionCompras.this.getSelectedColumn());
               Component editorComponent = TablaDetalleDevolucionCompras.this.getEditorComponent();
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
                  int selectedRow = TablaDetalleDevolucionCompras.this.getSelectedRow();
                  int selectedColumn = TablaDetalleDevolucionCompras.this.getSelectedColumn();
                  String columnName = TablaDetalleDevolucionCompras.this.getColumnName(selectedColumn);
                  if (columnName.equals("Cantidad")
                     && TablaDetalleDevolucionCompras.this.getRowCount() - 1 == TablaDetalleDevolucionCompras.this.getSelectedRow()) {
                     if (TablaDetalleDevolucionCompras.this.getValueAt(TablaDetalleDevolucionCompras.this.getSelectedRow(), 1) != null
                        && !String.valueOf(TablaDetalleDevolucionCompras.this.getValueAt(TablaDetalleDevolucionCompras.this.getSelectedRow(), 1)).equals("")) {
                        lblTextoTotalLinea.setText(String.valueOf(TablaDetalleDevolucionCompras.this.getRowCount()));
                        model.addDefaultRow();
                        TablaDetalleDevolucionCompras.this.editCellAt(TablaDetalleDevolucionCompras.this.getSelectedRow() + 1, 0);
                        TablaDetalleDevolucionCompras.this.changeSelection(TablaDetalleDevolucionCompras.this.getSelectedRow() + 1, 0, false, false);
                        Component editorComponent = TablaDetalleDevolucionCompras.this.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     } else {
                        SwingUtilities.invokeLater(
                           () -> {
                              TablaDetalleDevolucionCompras.this.changeSelection(
                                 TablaDetalleDevolucionCompras.this.getSelectedRow(),
                                 TablaDetalleDevolucionCompras.this.getColumn("Código").getModelIndex(),
                                 false,
                                 false
                              );
                              TablaDetalleDevolucionCompras.this.editCellAt(
                                 TablaDetalleDevolucionCompras.this.getSelectedRow(), TablaDetalleDevolucionCompras.this.getColumn("Código").getModelIndex()
                              );
                              Component editorComponentx = TablaDetalleDevolucionCompras.this.getEditorComponent();
                              if (editorComponentx instanceof JTextComponent) {
                                 ((JTextComponent)editorComponentx).selectAll();
                              }

                              editorComponentx.requestFocusInWindow();
                           }
                        );
                     }
                  } else if (columnName.equals("Cantidad")
                     && TablaDetalleDevolucionCompras.this.getSelectedRow() < TablaDetalleDevolucionCompras.this.getRowCount() - 1) {
                     TablaDetalleDevolucionCompras.this.editCellAt(TablaDetalleDevolucionCompras.this.getSelectedRow() + 1, 0);
                     TablaDetalleDevolucionCompras.this.changeSelection(TablaDetalleDevolucionCompras.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaDetalleDevolucionCompras.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     for (int i = selectedColumn + 1; i < TablaDetalleDevolucionCompras.this.getColumnCount(); i++) {
                        if (model.isCellEditable(selectedRow, i)) {
                           TablaDetalleDevolucionCompras.this.editCellAt(selectedRow, i);
                           TablaDetalleDevolucionCompras.this.changeSelection(selectedRow, i, false, false);
                           Component editorComponent = TablaDetalleDevolucionCompras.this.getEditorComponent();
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
                  int editingRow = TablaDetalleDevolucionCompras.this.getEditingRow();
                  int editingColumn = TablaDetalleDevolucionCompras.this.getEditingColumn();
                  if (editingRow != -1 && editingColumn != -1) {
                     TableCellEditor cellEditor = TablaDetalleDevolucionCompras.this.getCellEditor();
                     cellEditor.stopCellEditing();
                  }

                  DialogoMessagebox d = new DialogoMessagebox("¿Estás seguro de eliminar esta fila?");
                  d.setLocationRelativeTo(TablaDetalleDevolucionCompras.this);
                  d.setVisible(true);
                  if (d.isResultadoEncontrado()) {
                     int selectedRow = TablaDetalleDevolucionCompras.this.getSelectedRow();
                     int primarioColumn = TablaDetalleDevolucionCompras.this.getColumn("Primario").getModelIndex();
                     if (!String.valueOf(TablaDetalleDevolucionCompras.this.getValueAt(TablaDetalleDevolucionCompras.this.getSelectedRow(), primarioColumn))
                        .trim()
                        .equals("0")) {
                        codigosAEliminar.add(
                           Integer.parseInt(
                              String.valueOf(TablaDetalleDevolucionCompras.this.getValueAt(TablaDetalleDevolucionCompras.this.getSelectedRow(), primarioColumn))
                                 .trim()
                           )
                        );
                     }

                     if (selectedRow == 0 && TablaDetalleDevolucionCompras.this.getRowCount() - 1 == 0) {
                        FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
                        lblTextoTotalLinea.setText(String.valueOf(formatoLinea.format(TablaDetalleDevolucionCompras.this.getRowCount() + 1)));
                        model.addDefaultRow();
                     }

                     if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel)TablaDetalleDevolucionCompras.this.getModel();
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        if (model.getRowCount() > 0) {
                           int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                           TablaDetalleDevolucionCompras.this.changeSelection(newSelectedRow, 0, false, false);
                           TablaDetalleDevolucionCompras.this.editCellAt(newSelectedRow, 0);
                        }
                     }
                  } else {
                     TablaDetalleDevolucionCompras.this.changeSelection(editingRow, 0, false, false);
                     TablaDetalleDevolucionCompras.this.editCellAt(editingRow, 0);
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
               for (int row = 0; row < TablaDetalleDevolucionCompras.this.getRowCount(); row++) {
                  String codigo = String.valueOf(TablaDetalleDevolucionCompras.this.getValueAt(row, 0));
                  if (codigo.equals(buscador.getCodigo())) {
                     TablaDetalleDevolucionCompras.this.changeSelection(row, 0, false, false);
                     TablaDetalleDevolucionCompras.this.editCellAt(row, 0);
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
         ProductosE producto = ProductosE.buscarProducto3(codigo, null);
         if (producto != null) {
            this.setValueAt(producto.getCod_producto(), row, this.getColumn("Código").getModelIndex());
            this.setValueAt(producto.getNombre_producto(), row, this.getColumn("Descripción").getModelIndex());
            this.setValueAt(producto.getNombreUnidadMedida(), row, this.getColumn("UM").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Cantidad").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Primario").getModelIndex());
            this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
         } else {
            this.setValueAt("", row, this.getColumn("Código").getModelIndex());
            this.setValueAt("", row, this.getColumn("Descripción").getModelIndex());
            this.setValueAt("", row, this.getColumn("UM").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Cantidad").getModelIndex());
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
