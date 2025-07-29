package a3Permisos;

import a00CondicionesVentas.CondicionesVentasE;
import java.awt.Color;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;
import utilidadesSQL.DialogoMessagebox;

public class TablaCondicionVentaUsuario extends JTable {
   private static final long serialVersionUID = 1L;

   public TablaCondicionVentaUsuario(final List<Integer> codigosAEliminar) {
      this.setRowHeight(25);
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      this.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
      String[] columnNames = new String[]{"Código", "Descripción", "SW2", "Primario", "SW"};
      final ModeloTablaPermisos model = new ModeloTablaPermisos(null, columnNames);
      TablaCondicionVentaUsuario.ColorRenderer colorRenderer = new TablaCondicionVentaUsuario.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
      this.setModel(model);
      model.formato(this, "Defecto");
      this.getInputMap(1).put(KeyStroke.getKeyStroke(10, 0), "enter");
      this.getActionMap()
         .put(
            "enter",
            new AbstractAction() {
               private static final long serialVersionUID = 1L;

               @Override
               public void actionPerformed(ActionEvent e) {
                  int selectedRow = TablaCondicionVentaUsuario.this.getSelectedRow();
                  int selectedColumn = TablaCondicionVentaUsuario.this.getSelectedColumn();
                  String columnName = TablaCondicionVentaUsuario.this.getColumnName(selectedColumn);
                  if (columnName.equals("SW2") && TablaCondicionVentaUsuario.this.getRowCount() - 1 == TablaCondicionVentaUsuario.this.getSelectedRow()) {
                     if (TablaCondicionVentaUsuario.this.getValueAt(TablaCondicionVentaUsuario.this.getSelectedRow(), 1) != null
                        && !String.valueOf(TablaCondicionVentaUsuario.this.getValueAt(TablaCondicionVentaUsuario.this.getSelectedRow(), 1)).equals("")) {
                        model.addDefaultRow();
                        TablaCondicionVentaUsuario.this.editCellAt(TablaCondicionVentaUsuario.this.getSelectedRow() + 1, 0);
                        TablaCondicionVentaUsuario.this.changeSelection(TablaCondicionVentaUsuario.this.getSelectedRow() + 1, 0, false, false);
                        Component editorComponent = TablaCondicionVentaUsuario.this.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     } else {
                        SwingUtilities.invokeLater(
                           () -> {
                              TablaCondicionVentaUsuario.this.changeSelection(
                                 TablaCondicionVentaUsuario.this.getSelectedRow(),
                                 TablaCondicionVentaUsuario.this.getColumn("Código").getModelIndex(),
                                 false,
                                 false
                              );
                              TablaCondicionVentaUsuario.this.editCellAt(
                                 TablaCondicionVentaUsuario.this.getSelectedRow(), TablaCondicionVentaUsuario.this.getColumn("Código").getModelIndex()
                              );
                              Component editorComponentx = TablaCondicionVentaUsuario.this.getEditorComponent();
                              if (editorComponentx instanceof JTextComponent) {
                                 ((JTextComponent)editorComponentx).selectAll();
                              }

                              editorComponentx.requestFocusInWindow();
                           }
                        );
                     }
                  } else if (columnName.equals("SW2") && TablaCondicionVentaUsuario.this.getSelectedRow() < TablaCondicionVentaUsuario.this.getRowCount() - 1) {
                     TablaCondicionVentaUsuario.this.editCellAt(TablaCondicionVentaUsuario.this.getSelectedRow() + 1, 0);
                     TablaCondicionVentaUsuario.this.changeSelection(TablaCondicionVentaUsuario.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaCondicionVentaUsuario.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     for (int i = selectedColumn + 1; i < TablaCondicionVentaUsuario.this.getColumnCount(); i++) {
                        if (model.isCellEditable(selectedRow, i)) {
                           TablaCondicionVentaUsuario.this.editCellAt(selectedRow, i);
                           TablaCondicionVentaUsuario.this.changeSelection(selectedRow, i, false, false);
                           Component editorComponent = TablaCondicionVentaUsuario.this.getEditorComponent();
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
                  System.out.println("PROBANDO");
                  int editingRow = TablaCondicionVentaUsuario.this.getEditingRow();
                  int editingColumn = TablaCondicionVentaUsuario.this.getEditingColumn();
                  if (editingRow != -1 && editingColumn != -1) {
                     TableCellEditor cellEditor = TablaCondicionVentaUsuario.this.getCellEditor();
                     cellEditor.stopCellEditing();
                  }

                  DialogoMessagebox d = new DialogoMessagebox("¿Estás seguro de eliminar esta fila?");
                  d.setLocationRelativeTo(TablaCondicionVentaUsuario.this);
                  d.setVisible(true);
                  if (d.isResultadoEncontrado()) {
                     int selectedRow = TablaCondicionVentaUsuario.this.getSelectedRow();
                     int primarioColumn = TablaCondicionVentaUsuario.this.getColumn("Primario").getModelIndex();
                     if (!String.valueOf(TablaCondicionVentaUsuario.this.getValueAt(TablaCondicionVentaUsuario.this.getSelectedRow(), primarioColumn))
                        .trim()
                        .equals("0")) {
                        codigosAEliminar.add(
                           Integer.parseInt(
                              String.valueOf(TablaCondicionVentaUsuario.this.getValueAt(TablaCondicionVentaUsuario.this.getSelectedRow(), primarioColumn))
                                 .trim()
                           )
                        );
                     }

                     if (selectedRow == 0 && TablaCondicionVentaUsuario.this.getRowCount() - 1 == 0) {
                        model.addDefaultRow();
                     }

                     if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel)TablaCondicionVentaUsuario.this.getModel();
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        if (model.getRowCount() > 0) {
                           int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                           TablaCondicionVentaUsuario.this.changeSelection(newSelectedRow, 0, false, false);
                           TablaCondicionVentaUsuario.this.editCellAt(newSelectedRow, 0);
                        }
                     }
                  } else {
                     TablaCondicionVentaUsuario.this.changeSelection(editingRow, 0, false, false);
                     TablaCondicionVentaUsuario.this.editCellAt(editingRow, 0);
                  }
               }
            }
         );
   }

   private void buscarBD(String codigo, int row) {
      if (!codigo.trim().equals("")) {
         CondicionesVentasE condicionVta = CondicionesVentasE.buscarCondicionVta2(Integer.parseInt(codigo), null);
         if (condicionVta != null) {
            this.setValueAt(condicionVta.getNombre_condicion(), row, this.getColumn("Descripción").getModelIndex());
         } else {
            this.setValueAt("", row, this.getColumn("Descripción").getModelIndex());
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
               String codigo = (String)this.getValueAt(row, this.getColumn("Código").getModelIndex());
               this.buscarBD(codigo, row);
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
