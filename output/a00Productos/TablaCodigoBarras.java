package a00Productos;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;
import utilidadesSQL.DialogoMessagebox;

public class TablaCodigoBarras extends JTable {
   private static final long serialVersionUID = 1L;
   private ModeloTablaCodigoBarra model;

   public TablaCodigoBarras(final List<String> codigosAEliminar) {
      this.setRowHeight(25);
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      String[] columnNames = new String[]{"Codigo de Barras", "ID"};
      this.model = new ModeloTablaCodigoBarra(null, columnNames, this);
      this.setModel(this.model);
      this.model.formato(this);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(10, 0), "enter");
      this.getActionMap()
         .put(
            "enter",
            new AbstractAction() {
               private static final long serialVersionUID = 1L;

               @Override
               public void actionPerformed(ActionEvent e) {
                  if (TablaCodigoBarras.this.getValueAt(TablaCodigoBarras.this.getSelectedRow(), 1) != null
                     && !String.valueOf(TablaCodigoBarras.this.getValueAt(TablaCodigoBarras.this.getSelectedRow(), 1)).equals("")) {
                     TablaCodigoBarras.this.model.addDefaultRow();
                     TablaCodigoBarras.this.editCellAt(TablaCodigoBarras.this.getSelectedRow() + 1, 0);
                     TablaCodigoBarras.this.changeSelection(TablaCodigoBarras.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaCodigoBarras.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     SwingUtilities.invokeLater(
                        () -> {
                           TablaCodigoBarras.this.changeSelection(
                              TablaCodigoBarras.this.getSelectedRow(), TablaCodigoBarras.this.getColumn("Codigo de Barras").getModelIndex(), false, false
                           );
                           TablaCodigoBarras.this.editCellAt(
                              TablaCodigoBarras.this.getSelectedRow(), TablaCodigoBarras.this.getColumn("Codigo de Barras").getModelIndex()
                           );
                           Component editorComponentx = TablaCodigoBarras.this.getEditorComponent();
                           if (editorComponentx instanceof JTextComponent) {
                              ((JTextComponent)editorComponentx).selectAll();
                           }

                           editorComponentx.requestFocusInWindow();
                        }
                     );
                  }
               }
            }
         );
      this.getInputMap(1).put(KeyStroke.getKeyStroke(119, 0), "deleteRow");
      this.getActionMap().put("deleteRow", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int editingRow = TablaCodigoBarras.this.getEditingRow();
            int editingColumn = TablaCodigoBarras.this.getEditingColumn();
            if (editingRow != -1 && editingColumn != -1) {
               TableCellEditor cellEditor = TablaCodigoBarras.this.getCellEditor();
               cellEditor.stopCellEditing();
            }

            DialogoMessagebox d = new DialogoMessagebox("¿Estás seguro de eliminar esta fila?");
            d.setLocationRelativeTo(TablaCodigoBarras.this);
            d.setVisible(true);
            if (d.isResultadoEncontrado()) {
               int selectedRow = TablaCodigoBarras.this.getSelectedRow();
               int primarioColumn = TablaCodigoBarras.this.getColumn("ID").getModelIndex();
               if (!String.valueOf(TablaCodigoBarras.this.getValueAt(TablaCodigoBarras.this.getSelectedRow(), primarioColumn)).trim().equals("0")) {
                  codigosAEliminar.add(String.valueOf(TablaCodigoBarras.this.getValueAt(TablaCodigoBarras.this.getSelectedRow(), primarioColumn)).trim());
               }

               if (selectedRow == 0 && TablaCodigoBarras.this.getRowCount() - 1 == 0) {
                  TablaCodigoBarras.this.model.addDefaultRow();
               }

               if (selectedRow != -1) {
                  DefaultTableModel model = (DefaultTableModel)TablaCodigoBarras.this.getModel();
                  model.removeRow(selectedRow);
                  model.fireTableDataChanged();
                  if (model.getRowCount() > 0) {
                     int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                     TablaCodigoBarras.this.changeSelection(newSelectedRow, 0, false, false);
                     TablaCodigoBarras.this.editCellAt(newSelectedRow, 0);
                  }
               }
            } else {
               TablaCodigoBarras.this.changeSelection(editingRow, 0, false, false);
               TablaCodigoBarras.this.editCellAt(editingRow, 0);
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
         if (columnName.equals("Codigo de Barra")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)
                  && !valorActual.trim().equals("")
                  && (String.valueOf(this.getValueAt(row, 1)).trim().equals("") || this.getValueAt(row, 1) == null)
               || !valorActual.trim().equals(valorAnterior)) {
               this.model.addDefaultRow();
            }
         }

         super.editingStopped(e);
      }
   }
}
