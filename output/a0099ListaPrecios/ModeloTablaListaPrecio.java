package a0099ListaPrecios;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class ModeloTablaListaPrecio extends DefaultTableModel {
   private static final long serialVersionUID = 1L;

   public ModeloTablaListaPrecio(Object[][] data, Object[] columnNames) {
      super(data, columnNames);
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      String columnName = this.getColumnName(column);
      return columnName.equals("Código") || columnName.equals("IVA");
   }

   @Override
   public Class<?> getColumnClass(int column) {
      String columnName = this.getColumnName(column);
      return columnName.equals("IVA") ? Boolean.class : Object.class;
   }

   public void formato(JTable tabla, int par_decimal_cantidad, int par_decimal_importe) {
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
      tabla.getColumn("Código").setResizable(false);
      tabla.getColumn("Código").setMinWidth(110);
      tabla.getColumn("Código").setMaxWidth(110);
      tabla.getColumn("Descripción").setResizable(false);
      tabla.getColumn("Descripción").setMinWidth(415);
      tabla.getColumn("Descripción").setMaxWidth(415);
      tabla.getColumn("UM").setResizable(false);
      tabla.getColumn("UM").setMinWidth(70);
      tabla.getColumn("UM").setMaxWidth(70);
      tabla.getColumn("IVA").setResizable(false);
      tabla.getColumn("IVA").setMinWidth(0);
      tabla.getColumn("IVA").setMaxWidth(0);
      tabla.getColumn("Precio Minimo").setResizable(false);
      tabla.getColumn("Precio Minimo").setMinWidth(120);
      tabla.getColumn("Precio Minimo").setMaxWidth(120);
      tabla.getColumn("Precio").setResizable(false);
      tabla.getColumn("Precio").setMinWidth(120);
      tabla.getColumn("Precio").setMaxWidth(120);
      tabla.getColumn("Moneda").setResizable(false);
      tabla.getColumn("Moneda").setMinWidth(0);
      tabla.getColumn("Moneda").setMaxWidth(0);
      tabla.getColumn("Primario").setResizable(false);
      tabla.getColumn("Primario").setMinWidth(0);
      tabla.getColumn("Primario").setMaxWidth(0);
      tabla.getColumn("SW").setResizable(false);
      tabla.getColumn("SW").setMinWidth(0);
      tabla.getColumn("SW").setMaxWidth(0);
      tabla.getColumn("Código").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Descripción").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("UM").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Precio Minimo").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Precio").setHeaderRenderer(headerRendererDerecha);
      NumberFormatter precioFormatter = new NumberFormatter(new DecimalFormat("##,##0.00"));
      precioFormatter.setValueClass(Double.class);
      precioFormatter.setAllowsInvalid(false);
      JFormattedTextField cantidadField = new JFormattedTextField((AbstractFormatterFactory)(new DefaultFormatterFactory(precioFormatter)));
      NumberFormatter subtotal = new NumberFormatter(new DecimalFormat("##,##0.00"));
      subtotal.setValueClass(Double.class);
      subtotal.setAllowsInvalid(false);
      tabla.getColumn("Precio").setCellEditor(new DefaultCellEditor(cantidadField));
      tabla.getColumn("Precio Minimo").setCellEditor(new DefaultCellEditor(cantidadField));
   }

   public void addDefaultRow() {
      this.addRow(new Object[]{"", "", "", "", true, 0, 0, 0, 0});
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
