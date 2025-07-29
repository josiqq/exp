package a99CajaCobroCliente;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;

public class TablaDetalleCobroCliente extends JTable {
   private static final long serialVersionUID = 1L;

   public TablaDetalleCobroCliente(final List<Integer> codigosAEliminar, int DECIMAL_CANTIDAD, int DECIMAL_IMPORTE) {
      this.setRowHeight(25);
      this.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
      String[] columnNames = new String[]{
         "Condicion", "Cuenta", "Tarjeta", "Nro Tarjeta", "Moneda", "Cotizacion", "Importe", "Banco", "Nro Cheque", "Nro Cuenta", "Librador", "Primario", "SW"
      };
      final ModeloTablaCobroClienteDetalle model = new ModeloTablaCobroClienteDetalle(null, columnNames);
      TablaDetalleCobroCliente.ColorRenderer colorRenderer = new TablaDetalleCobroCliente.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
      this.setModel(model);
      model.formato(this, DECIMAL_CANTIDAD, DECIMAL_IMPORTE);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(37, 0), "left");
      this.getActionMap().put("left", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaDetalleCobroCliente.this.getSelectedRow();
            int selectedColumn = TablaDetalleCobroCliente.this.getSelectedColumn();

            for (int i = selectedColumn - 1; i >= 0; i--) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleCobroCliente.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleCobroCliente.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleCobroCliente.this.getEditorComponent();
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
            int selectedRow = TablaDetalleCobroCliente.this.getSelectedRow();
            int selectedColumn = TablaDetalleCobroCliente.this.getSelectedColumn();

            for (int i = selectedColumn + 1; i < TablaDetalleCobroCliente.this.getColumnCount(); i++) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleCobroCliente.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleCobroCliente.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleCobroCliente.this.getEditorComponent();
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
            if (TablaDetalleCobroCliente.this.getRowCount() - 1 > 0) {
               int selectedRow = TablaDetalleCobroCliente.this.getSelectedRow() - 1;
               TablaDetalleCobroCliente.this.changeSelection(selectedRow, TablaDetalleCobroCliente.this.getSelectedColumn(), false, false);
               TablaDetalleCobroCliente.this.editCellAt(selectedRow, TablaDetalleCobroCliente.this.getSelectedColumn());
               Component editorComponent = TablaDetalleCobroCliente.this.getEditorComponent();
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
            if (TablaDetalleCobroCliente.this.getRowCount() - 1 > TablaDetalleCobroCliente.this.getSelectedRow()) {
               int selectedRow = TablaDetalleCobroCliente.this.getSelectedRow() + 1;
               TablaDetalleCobroCliente.this.changeSelection(selectedRow, TablaDetalleCobroCliente.this.getSelectedColumn(), false, false);
               TablaDetalleCobroCliente.this.editCellAt(selectedRow, TablaDetalleCobroCliente.this.getSelectedColumn());
               Component editorComponent = TablaDetalleCobroCliente.this.getEditorComponent();
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
                  int selectedRow = TablaDetalleCobroCliente.this.getSelectedRow();
                  int selectedColumn = TablaDetalleCobroCliente.this.getSelectedColumn();
                  String columnName = TablaDetalleCobroCliente.this.getColumnName(selectedColumn);
                  if (columnName.equals("Importe") && TablaDetalleCobroCliente.this.getRowCount() - 1 == TablaDetalleCobroCliente.this.getSelectedRow()) {
                     if (TablaDetalleCobroCliente.this.getValueAt(TablaDetalleCobroCliente.this.getSelectedRow(), 1) != null
                        && !String.valueOf(TablaDetalleCobroCliente.this.getValueAt(TablaDetalleCobroCliente.this.getSelectedRow(), 1)).equals("")) {
                        model.addDefaultRow();
                        TablaDetalleCobroCliente.this.editCellAt(TablaDetalleCobroCliente.this.getSelectedRow() + 1, 0);
                        TablaDetalleCobroCliente.this.changeSelection(TablaDetalleCobroCliente.this.getSelectedRow() + 1, 0, false, false);
                        Component editorComponent = TablaDetalleCobroCliente.this.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     } else {
                        SwingUtilities.invokeLater(
                           () -> {
                              TablaDetalleCobroCliente.this.changeSelection(
                                 TablaDetalleCobroCliente.this.getSelectedRow(),
                                 TablaDetalleCobroCliente.this.getColumn("Condicion").getModelIndex(),
                                 false,
                                 false
                              );
                              TablaDetalleCobroCliente.this.editCellAt(
                                 TablaDetalleCobroCliente.this.getSelectedRow(), TablaDetalleCobroCliente.this.getColumn("Condicion").getModelIndex()
                              );
                              Component editorComponentx = TablaDetalleCobroCliente.this.getEditorComponent();
                              if (editorComponentx instanceof JTextComponent) {
                                 ((JTextComponent)editorComponentx).selectAll();
                              }

                              editorComponentx.requestFocusInWindow();
                           }
                        );
                     }
                  } else if (columnName.equals("Importe") && TablaDetalleCobroCliente.this.getSelectedRow() < TablaDetalleCobroCliente.this.getRowCount() - 1) {
                     TablaDetalleCobroCliente.this.editCellAt(TablaDetalleCobroCliente.this.getSelectedRow() + 1, 0);
                     TablaDetalleCobroCliente.this.changeSelection(TablaDetalleCobroCliente.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaDetalleCobroCliente.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     for (int i = selectedColumn + 1; i < TablaDetalleCobroCliente.this.getColumnCount(); i++) {
                        if (model.isCellEditable(selectedRow, i)) {
                           TablaDetalleCobroCliente.this.editCellAt(selectedRow, i);
                           TablaDetalleCobroCliente.this.changeSelection(selectedRow, i, false, false);
                           Component editorComponent = TablaDetalleCobroCliente.this.getEditorComponent();
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
                  int editingRow = TablaDetalleCobroCliente.this.getEditingRow();
                  int editingColumn = TablaDetalleCobroCliente.this.getEditingColumn();
                  if (editingRow != -1 && editingColumn != -1) {
                     TableCellEditor cellEditor = TablaDetalleCobroCliente.this.getCellEditor();
                     cellEditor.stopCellEditing();
                  }

                  int option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta fila?", "Confirmar eliminación", 0);
                  if (option == 0) {
                     int selectedRow = TablaDetalleCobroCliente.this.getSelectedRow();
                     int primarioColumn = TablaDetalleCobroCliente.this.getColumn("Primario").getModelIndex();
                     if (!String.valueOf(TablaDetalleCobroCliente.this.getValueAt(TablaDetalleCobroCliente.this.getSelectedRow(), primarioColumn))
                        .trim()
                        .equals("0")) {
                        codigosAEliminar.add(
                           Integer.parseInt(
                              String.valueOf(TablaDetalleCobroCliente.this.getValueAt(TablaDetalleCobroCliente.this.getSelectedRow(), primarioColumn)).trim()
                           )
                        );
                     }

                     if (selectedRow == 0 && TablaDetalleCobroCliente.this.getRowCount() - 1 == 0) {
                        model.addDefaultRow();
                     }

                     if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel)TablaDetalleCobroCliente.this.getModel();
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        if (model.getRowCount() > 0) {
                           int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                           TablaDetalleCobroCliente.this.changeSelection(newSelectedRow, 0, false, false);
                           TablaDetalleCobroCliente.this.editCellAt(newSelectedRow, 0);
                        }
                     }
                  } else {
                     TablaDetalleCobroCliente.this.changeSelection(editingRow, 0, false, false);
                     TablaDetalleCobroCliente.this.editCellAt(editingRow, 0);
                  }
               }
            }
         );
   }

   @Override
   public void editingStopped(ChangeEvent e) {
      if (this.getRowCount() - 1 >= 0) {
         int column = this.getEditingColumn();
         int row = this.getEditingRow();
         String columnName = this.getColumnName(column);
         if (columnName.equals("Condicion")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)
                  && !valorActual.trim().equals("")
                  && (String.valueOf(this.getValueAt(row, 1)).trim().equals("") || this.getValueAt(row, 1) == null)
               || !valorActual.trim().equals(valorAnterior)) {
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         if (columnName.equals("Moneda")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)
                  && !valorActual.trim().equals("")
                  && (String.valueOf(this.getValueAt(row, 1)).trim().equals("") || this.getValueAt(row, 1) == null)
               || !valorActual.trim().equals(valorAnterior)) {
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
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
