package a99CajaApertura;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.DefaultFormatterFactory;
import utilidadesTablaDetalle.DecimalCellRenderer;
import utilidadesTablaDetalle.FormatoDecimalSistema;
import utilidadesTablaDetalle.PintarCeldasEditables;

public class ModeloTablaAperturaCaja extends DefaultTableModel {
   private static final long serialVersionUID = 1L;

   public ModeloTablaAperturaCaja(Object[][] data, Object[] columnNames) {
      super(data, columnNames);
   }

   @Override
   public Class<?> getColumnClass(int column) {
      String columnName = this.getColumnName(column);
      return columnName.equals("Importe") ? Double.class : Object.class;
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      String columnName = this.getColumnName(column);
      return columnName.equals("Condicion") || columnName.equals("Moneda") || columnName.equals("Importe");
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
      tabla.getColumn("Condicion").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Nombre Condicion").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Nombre Moneda").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Importe").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Condicion").setResizable(false);
      tabla.getColumn("Condicion").setMinWidth(60);
      tabla.getColumn("Condicion").setMaxWidth(60);
      tabla.getColumn("Nombre Condicion").setResizable(false);
      tabla.getColumn("Nombre Condicion").setMinWidth(175);
      tabla.getColumn("Nombre Condicion").setMaxWidth(175);
      tabla.getColumn("Moneda").setResizable(false);
      tabla.getColumn("Moneda").setMinWidth(60);
      tabla.getColumn("Moneda").setMaxWidth(60);
      tabla.getColumn("Nombre Moneda").setResizable(false);
      tabla.getColumn("Nombre Moneda").setMinWidth(105);
      tabla.getColumn("Nombre Moneda").setMaxWidth(105);
      tabla.getColumn("Importe").setResizable(false);
      tabla.getColumn("Importe").setMinWidth(80);
      tabla.getColumn("Importe").setMaxWidth(80);
      tabla.getColumn("Primario").setResizable(false);
      tabla.getColumn("Primario").setMinWidth(0);
      tabla.getColumn("Primario").setMaxWidth(0);
      tabla.getColumn("SW").setResizable(false);
      tabla.getColumn("SW").setMinWidth(0);
      tabla.getColumn("SW").setMaxWidth(0);
      FormatoDecimalSistema formatoDecimalPrecioBD = new FormatoDecimalSistema(7);
      JFormattedTextField txt_formato_decimal_precioBD = new JFormattedTextField(
         (AbstractFormatterFactory)(new DefaultFormatterFactory(formatoDecimalPrecioBD))
      );
      tabla.getColumn("Importe").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Importe").setCellRenderer(new DecimalCellRenderer(par_decimal_importe));
      tabla.getColumn("Nombre Condicion").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Nombre Moneda").setCellRenderer(new PintarCeldasEditables());
   }

   public void addDefaultRow() {
      this.addRow(new Object[]{0, "", 0, "", 0, 0, 0});
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
