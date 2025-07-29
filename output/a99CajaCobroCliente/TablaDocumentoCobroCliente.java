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

public class TablaDocumentoCobroCliente extends JTable {
   private static final long serialVersionUID = 1L;

   public TablaDocumentoCobroCliente(final List<Integer> codigosAEliminar, int DECIMAL_CANTIDAD, int DECIMAL_IMPORTE) {
      this.setRowHeight(25);
      this.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
      String[] columnNames = new String[]{"Cod Tipo Doc", "Tipo Documento", "Timbrado", "Nro. Documento", "Cuota", "Importe", "Moneda", "Primario", "SW"};
      final ModeloTablaCobroClienteDocumento model = new ModeloTablaCobroClienteDocumento(null, columnNames);
      TablaDocumentoCobroCliente.ColorRenderer colorRenderer = new TablaDocumentoCobroCliente.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
      this.setModel(model);
      model.formato(this, DECIMAL_CANTIDAD, DECIMAL_IMPORTE);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(37, 0), "left");
      this.getActionMap().put("left", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaDocumentoCobroCliente.this.getSelectedRow();
            int selectedColumn = TablaDocumentoCobroCliente.this.getSelectedColumn();

            for (int i = selectedColumn - 1; i >= 0; i--) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDocumentoCobroCliente.this.changeSelection(selectedRow, i, false, false);
                  TablaDocumentoCobroCliente.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDocumentoCobroCliente.this.getEditorComponent();
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
            int selectedRow = TablaDocumentoCobroCliente.this.getSelectedRow();
            int selectedColumn = TablaDocumentoCobroCliente.this.getSelectedColumn();

            for (int i = selectedColumn + 1; i < TablaDocumentoCobroCliente.this.getColumnCount(); i++) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDocumentoCobroCliente.this.changeSelection(selectedRow, i, false, false);
                  TablaDocumentoCobroCliente.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDocumentoCobroCliente.this.getEditorComponent();
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
            if (TablaDocumentoCobroCliente.this.getRowCount() - 1 > 0) {
               int selectedRow = TablaDocumentoCobroCliente.this.getSelectedRow() - 1;
               TablaDocumentoCobroCliente.this.changeSelection(selectedRow, TablaDocumentoCobroCliente.this.getSelectedColumn(), false, false);
               TablaDocumentoCobroCliente.this.editCellAt(selectedRow, TablaDocumentoCobroCliente.this.getSelectedColumn());
               Component editorComponent = TablaDocumentoCobroCliente.this.getEditorComponent();
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
            if (TablaDocumentoCobroCliente.this.getRowCount() - 1 > TablaDocumentoCobroCliente.this.getSelectedRow()) {
               int selectedRow = TablaDocumentoCobroCliente.this.getSelectedRow() + 1;
               TablaDocumentoCobroCliente.this.changeSelection(selectedRow, TablaDocumentoCobroCliente.this.getSelectedColumn(), false, false);
               TablaDocumentoCobroCliente.this.editCellAt(selectedRow, TablaDocumentoCobroCliente.this.getSelectedColumn());
               Component editorComponent = TablaDocumentoCobroCliente.this.getEditorComponent();
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
                  int selectedRow = TablaDocumentoCobroCliente.this.getSelectedRow();
                  int selectedColumn = TablaDocumentoCobroCliente.this.getSelectedColumn();
                  String columnName = TablaDocumentoCobroCliente.this.getColumnName(selectedColumn);
                  if (columnName.equals("Importe") && TablaDocumentoCobroCliente.this.getRowCount() - 1 == TablaDocumentoCobroCliente.this.getSelectedRow()) {
                     if (TablaDocumentoCobroCliente.this.getValueAt(TablaDocumentoCobroCliente.this.getSelectedRow(), 1) != null
                        && !String.valueOf(TablaDocumentoCobroCliente.this.getValueAt(TablaDocumentoCobroCliente.this.getSelectedRow(), 1)).equals("")) {
                        model.addDefaultRow();
                        TablaDocumentoCobroCliente.this.editCellAt(TablaDocumentoCobroCliente.this.getSelectedRow() + 1, 0);
                        TablaDocumentoCobroCliente.this.changeSelection(TablaDocumentoCobroCliente.this.getSelectedRow() + 1, 0, false, false);
                        Component editorComponent = TablaDocumentoCobroCliente.this.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     } else {
                        SwingUtilities.invokeLater(
                           () -> {
                              TablaDocumentoCobroCliente.this.changeSelection(
                                 TablaDocumentoCobroCliente.this.getSelectedRow(),
                                 TablaDocumentoCobroCliente.this.getColumn("Cod Tipo Doc").getModelIndex(),
                                 false,
                                 false
                              );
                              TablaDocumentoCobroCliente.this.editCellAt(
                                 TablaDocumentoCobroCliente.this.getSelectedRow(), TablaDocumentoCobroCliente.this.getColumn("Cod Tipo Doc").getModelIndex()
                              );
                              Component editorComponentx = TablaDocumentoCobroCliente.this.getEditorComponent();
                              if (editorComponentx instanceof JTextComponent) {
                                 ((JTextComponent)editorComponentx).selectAll();
                              }

                              editorComponentx.requestFocusInWindow();
                           }
                        );
                     }
                  } else if (columnName.equals("Importe")
                     && TablaDocumentoCobroCliente.this.getSelectedRow() < TablaDocumentoCobroCliente.this.getRowCount() - 1) {
                     TablaDocumentoCobroCliente.this.editCellAt(TablaDocumentoCobroCliente.this.getSelectedRow() + 1, 0);
                     TablaDocumentoCobroCliente.this.changeSelection(TablaDocumentoCobroCliente.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaDocumentoCobroCliente.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     for (int i = selectedColumn + 1; i < TablaDocumentoCobroCliente.this.getColumnCount(); i++) {
                        if (model.isCellEditable(selectedRow, i)) {
                           TablaDocumentoCobroCliente.this.editCellAt(selectedRow, i);
                           TablaDocumentoCobroCliente.this.changeSelection(selectedRow, i, false, false);
                           Component editorComponent = TablaDocumentoCobroCliente.this.getEditorComponent();
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
                  int editingRow = TablaDocumentoCobroCliente.this.getEditingRow();
                  int editingColumn = TablaDocumentoCobroCliente.this.getEditingColumn();
                  if (editingRow != -1 && editingColumn != -1) {
                     TableCellEditor cellEditor = TablaDocumentoCobroCliente.this.getCellEditor();
                     cellEditor.stopCellEditing();
                  }

                  int option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta fila?", "Confirmar eliminación", 0);
                  if (option == 0) {
                     int selectedRow = TablaDocumentoCobroCliente.this.getSelectedRow();
                     int primarioColumn = TablaDocumentoCobroCliente.this.getColumn("Primario").getModelIndex();
                     if (!String.valueOf(TablaDocumentoCobroCliente.this.getValueAt(TablaDocumentoCobroCliente.this.getSelectedRow(), primarioColumn))
                        .trim()
                        .equals("0")) {
                        codigosAEliminar.add(
                           Integer.parseInt(
                              String.valueOf(TablaDocumentoCobroCliente.this.getValueAt(TablaDocumentoCobroCliente.this.getSelectedRow(), primarioColumn))
                                 .trim()
                           )
                        );
                     }

                     if (selectedRow == 0 && TablaDocumentoCobroCliente.this.getRowCount() - 1 == 0) {
                        model.addDefaultRow();
                     }

                     if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel)TablaDocumentoCobroCliente.this.getModel();
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        if (model.getRowCount() > 0) {
                           int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                           TablaDocumentoCobroCliente.this.changeSelection(newSelectedRow, 0, false, false);
                           TablaDocumentoCobroCliente.this.editCellAt(newSelectedRow, 0);
                        }
                     }
                  } else {
                     TablaDocumentoCobroCliente.this.changeSelection(editingRow, 0, false, false);
                     TablaDocumentoCobroCliente.this.editCellAt(editingRow, 0);
                  }
               }
            }
         );
   }

   @Override
   public void editingStopped(ChangeEvent e) {
      if (this.getRowCount() - 1 >= 0) {
         super.editingStopped(e);
      }
   }

   public static void eliminarFilas(JTable tabla) {
      DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
      tabla.clearSelection();
      if (modelo.getRowCount() - 1 >= 0) {
         while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
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
