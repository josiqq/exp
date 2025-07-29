package a3Permisos;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class ModeloTablaPermisos extends DefaultTableModel {
   private static final long serialVersionUID = 1L;

   public ModeloTablaPermisos(Object[][] data, Object[] columnNames) {
      super(data, columnNames);
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      String columnName = this.getColumnName(column);
      return columnName.equals("Código") || columnName.equals("Codigo") || columnName.equals("SW2") || columnName.equals("Recibe");
   }

   @Override
   public Class<?> getColumnClass(int columnIndex) {
      String columnName = this.getColumnName(columnIndex);
      return "Recibe".equals(columnName) ? Boolean.class : Object.class;
   }

   public void formato(JTable tabla, String nombre) {
      DefaultTableCellRenderer headerRendererIzquierda = new DefaultTableCellRenderer() {
         private static final long serialVersionUID = 1L;

         @Override
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            this.setHorizontalAlignment(2);
            this.setFont(this.getFont().deriveFont(1));
            this.setBackground(new Color(240, 240, 240));
            this.setForeground(Color.BLACK);
            this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            this.setOpaque(true);
            return this;
         }
      };
      if (nombre.trim().equals("Defecto")) {
         tabla.getColumn("Código").setResizable(false);
         tabla.getColumn("Código").setMinWidth(57);
         tabla.getColumn("Código").setMaxWidth(57);
         tabla.getColumn("Descripción").setResizable(false);
         tabla.getColumn("Descripción").setMinWidth(415);
         tabla.getColumn("Descripción").setMaxWidth(415);
         tabla.getColumn("Primario").setResizable(false);
         tabla.getColumn("Primario").setMinWidth(0);
         tabla.getColumn("Primario").setMaxWidth(0);
         tabla.getColumn("SW").setResizable(false);
         tabla.getColumn("SW").setMinWidth(0);
         tabla.getColumn("SW").setMaxWidth(0);
         tabla.getColumn("Código").setHeaderRenderer(headerRendererIzquierda);
         tabla.getColumn("Descripción").setHeaderRenderer(headerRendererIzquierda);
      } else if (nombre.trim().equals("DepositosEnvios")) {
         tabla.getColumn("Código").setResizable(false);
         tabla.getColumn("Código").setMinWidth(57);
         tabla.getColumn("Código").setMaxWidth(57);
         tabla.getColumn("Descripción").setResizable(false);
         tabla.getColumn("Descripción").setMinWidth(247);
         tabla.getColumn("Descripción").setMaxWidth(247);
         tabla.getColumn("Codigo").setResizable(false);
         tabla.getColumn("Codigo").setMinWidth(57);
         tabla.getColumn("Codigo").setMaxWidth(57);
         tabla.getColumn("Descripcion").setResizable(false);
         tabla.getColumn("Descripcion").setMinWidth(247);
         tabla.getColumn("Descripcion").setMaxWidth(247);
         tabla.getColumn("Recibe").setResizable(false);
         tabla.getColumn("Recibe").setMinWidth(48);
         tabla.getColumn("Recibe").setMaxWidth(48);
         tabla.getColumn("Primario").setResizable(false);
         tabla.getColumn("Primario").setMinWidth(0);
         tabla.getColumn("Primario").setMaxWidth(0);
         tabla.getColumn("SW").setResizable(false);
         tabla.getColumn("SW").setMinWidth(0);
         tabla.getColumn("SW").setMaxWidth(0);
         tabla.getColumn("Código").setHeaderRenderer(headerRendererIzquierda);
         tabla.getColumn("Descripción").setHeaderRenderer(headerRendererIzquierda);
         tabla.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
         tabla.getColumn("Descripcion").setHeaderRenderer(headerRendererIzquierda);
         tabla.getColumn("Recibe").setHeaderRenderer(headerRendererIzquierda);
      }
   }

   public void addDefaultRow2() {
      this.addRow(new Object[]{"", "", "", "", false, 0, 0});
   }

   public void addDefaultRow() {
      this.addRow(new Object[]{"", "", 0, 0});
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
