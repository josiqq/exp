package a00Productos;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import utilidades.LimiteTextFieldString;

public class ModeloTablaCodigoBarra extends DefaultTableModel {
   private static final long serialVersionUID = 1L;

   public ModeloTablaCodigoBarra(Object[][] data, Object[] columnNames, JTable tabla) {
      super(data, columnNames);
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      String columnName = this.getColumnName(column);
      return columnName.equals("Codigo de Barras");
   }

   public void formato(JTable tabla) {
      DefaultTableCellRenderer headerRendererDerecha = new DefaultTableCellRenderer() {
         private static final long serialVersionUID = 1L;

         @Override
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            this.setHorizontalAlignment(4);
            this.setFont(this.getFont().deriveFont(1));
            this.setBackground(new Color(240, 240, 240));
            this.setForeground(Color.BLACK);
            this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            this.setOpaque(true);
            return this;
         }
      };
      tabla.getColumn("Codigo de Barras").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Codigo de Barras").setResizable(false);
      tabla.getColumn("Codigo de Barras").setMinWidth(900);
      tabla.getColumn("Codigo de Barras").setMaxWidth(900);
      tabla.getColumn("Codigo de Barras").setPreferredWidth(900);
      tabla.getColumn("ID").setResizable(false);
      tabla.getColumn("ID").setMinWidth(0);
      tabla.getColumn("ID").setMaxWidth(0);
      tabla.getColumn("ID").setPreferredWidth(0);
      LimiteTextFieldString txt_codigo_barra = new LimiteTextFieldString(25);
      tabla.getColumn("Codigo de Barras").setCellEditor(new DefaultCellEditor(txt_codigo_barra));
   }

   public void addDefaultRow() {
      this.addRow(new Object[]{"", 0});
   }

   public void deleteRow(JTable tabla) {
      if (tabla.isEditing()) {
         TableCellEditor editor = tabla.getCellEditor();
         if (editor != null) {
            editor.stopCellEditing();
         }
      }

      if (this.getRowCount() - 1 >= 0) {
         while (this.getRowCount() > 0) {
            this.removeRow(0);
         }
      }
   }
}
