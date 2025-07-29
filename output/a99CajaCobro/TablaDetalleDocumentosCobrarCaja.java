package a99CajaCobro;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class TablaDetalleDocumentosCobrarCaja extends JTable {
   private static final long serialVersionUID = 1L;

   public TablaDetalleDocumentosCobrarCaja(int DECIMAL_CANTIDAD, int DECIMAL_IMPORTE) {
      this.setRowHeight(25);
      this.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
      String[] columnNames = new String[]{
         "Nro. Registro",
         "CodTipoDoc",
         "Tipo Documento",
         "Timbrado",
         "Nro Documento",
         "Cuotas",
         "Nro Cuota",
         "Importe",
         "CodMoneda",
         "Moneda",
         "CodCliente",
         "Tipo"
      };
      final ModeloTablaDocumentosCobrarCaja model = new ModeloTablaDocumentosCobrarCaja(null, columnNames);
      TablaDetalleDocumentosCobrarCaja.ColorRenderer colorRenderer = new TablaDetalleDocumentosCobrarCaja.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
      this.setModel(model);
      model.formato(this, DECIMAL_CANTIDAD, DECIMAL_IMPORTE);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(119, 0), "deleteRow");
      this.getActionMap().put("deleteRow", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int editingRow = TablaDetalleDocumentosCobrarCaja.this.getEditingRow();
            int editingColumn = TablaDetalleDocumentosCobrarCaja.this.getEditingColumn();
            if (editingRow != -1 && editingColumn != -1) {
               TableCellEditor cellEditor = TablaDetalleDocumentosCobrarCaja.this.getCellEditor();
               cellEditor.stopCellEditing();
            }

            int option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta fila?", "Confirmar eliminación", 0);
            if (option == 0) {
               int selectedRow = TablaDetalleDocumentosCobrarCaja.this.getSelectedRow();
               int primarioColumn = TablaDetalleDocumentosCobrarCaja.this.getColumn("Primario").getModelIndex();
               if (selectedRow == 0 && TablaDetalleDocumentosCobrarCaja.this.getRowCount() - 1 == 0) {
                  model.addDefaultRow();
               }

               if (selectedRow != -1) {
                  DefaultTableModel modelx = (DefaultTableModel)TablaDetalleDocumentosCobrarCaja.this.getModel();
                  modelx.removeRow(selectedRow);
                  modelx.fireTableDataChanged();
                  if (modelx.getRowCount() > 0) {
                     int newSelectedRow = Math.min(selectedRow, modelx.getRowCount() - 1);
                     TablaDetalleDocumentosCobrarCaja.this.changeSelection(newSelectedRow, 0, false, false);
                     TablaDetalleDocumentosCobrarCaja.this.editCellAt(newSelectedRow, 0);
                  }
               }
            } else {
               TablaDetalleDocumentosCobrarCaja.this.changeSelection(editingRow, 0, false, false);
               TablaDetalleDocumentosCobrarCaja.this.editCellAt(editingRow, 0);
            }
         }
      });
   }

   public static void eliminarFilasConWhile(ModeloTablaDocumentosCobrarCaja model) {
      int rowCount = model.getRowCount();

      for (int i = rowCount - 1; i >= 0; i--) {
         model.removeRow(i);
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
