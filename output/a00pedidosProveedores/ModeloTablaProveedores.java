package a00pedidosProveedores;

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

public class ModeloTablaProveedores extends DefaultTableModel {
   private static final long serialVersionUID = 1L;

   public ModeloTablaProveedores(Object[][] data, Object[] columnNames, JTable tabla) {
      super(data, columnNames);
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      String columnName = this.getColumnName(column);
      return columnName.equals("Código") || columnName.equals("Cantidad") || columnName.equals("Precio") || columnName.equals("Cant. Bonif");
   }

   public void formato(JTable tabla, int par_decimal_cantidad, int par_decimal_importe) {
      DefaultTableCellRenderer headerRendererCentro = new DefaultTableCellRenderer() {
         private static final long serialVersionUID = 1L;

         @Override
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            this.setHorizontalAlignment(0);
            this.setFont(this.getFont().deriveFont(1));
            this.setBackground(new Color(240, 240, 240));
            this.setForeground(Color.BLACK);
            this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            this.setOpaque(true);
            return this;
         }
      };
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
      tabla.getColumn("Código").setMinWidth(120);
      tabla.getColumn("Código").setMaxWidth(120);
      tabla.getColumn("Código").setPreferredWidth(120);
      tabla.getColumn("Descripción").setResizable(false);
      tabla.getColumn("Descripción").setMinWidth(560);
      tabla.getColumn("Descripción").setMaxWidth(560);
      tabla.getColumn("Descripción").setPreferredWidth(560);
      tabla.getColumn("TF").setResizable(false);
      tabla.getColumn("TF").setMinWidth(0);
      tabla.getColumn("TF").setMaxWidth(0);
      tabla.getColumn("TF").setPreferredWidth(0);
      tabla.getColumn("IVA").setResizable(false);
      tabla.getColumn("IVA").setMinWidth(0);
      tabla.getColumn("IVA").setMaxWidth(0);
      tabla.getColumn("IVA").setPreferredWidth(0);
      tabla.getColumn("REG").setResizable(false);
      tabla.getColumn("REG").setMinWidth(0);
      tabla.getColumn("REG").setMaxWidth(0);
      tabla.getColumn("REG").setPreferredWidth(0);
      tabla.getColumn("UM").setResizable(false);
      tabla.getColumn("UM").setMinWidth(50);
      tabla.getColumn("UM").setMaxWidth(50);
      tabla.getColumn("UM").setPreferredWidth(50);
      tabla.getColumn("Cantidad").setResizable(false);
      tabla.getColumn("Cantidad").setMinWidth(70);
      tabla.getColumn("Cantidad").setMaxWidth(70);
      tabla.getColumn("Cantidad").setPreferredWidth(70);
      tabla.getColumn("Cant. Bonif").setResizable(false);
      tabla.getColumn("Cant. Bonif").setMinWidth(70);
      tabla.getColumn("Cant. Bonif").setMaxWidth(70);
      tabla.getColumn("Cant. Bonif").setPreferredWidth(70);
      tabla.getColumn("Precio").setResizable(false);
      tabla.getColumn("Precio").setMinWidth(120);
      tabla.getColumn("Precio").setMaxWidth(120);
      tabla.getColumn("Precio").setPreferredWidth(120);
      tabla.getColumn("SubTotal").setResizable(false);
      tabla.getColumn("SubTotal").setMinWidth(120);
      tabla.getColumn("SubTotal").setMaxWidth(120);
      tabla.getColumn("SubTotal").setPreferredWidth(120);
      tabla.getColumn("Primario").setResizable(false);
      tabla.getColumn("Primario").setMinWidth(0);
      tabla.getColumn("Primario").setMaxWidth(0);
      tabla.getColumn("Primario").setPreferredWidth(0);
      tabla.getColumn("Cantidad").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Cant. Bonif").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Precio").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("SubTotal").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("UM").setHeaderRenderer(headerRendererCentro);
      tabla.getColumn("Código").setHeaderRenderer(headerRendererCentro);
      tabla.getColumn("Descripción").setHeaderRenderer(headerRendererCentro);
      FormatoDecimalSistema formatoDecimalCantidad = new FormatoDecimalSistema(par_decimal_cantidad);
      JFormattedTextField txt_formato_decimal_cantidad = new JFormattedTextField(
         (AbstractFormatterFactory)(new DefaultFormatterFactory(formatoDecimalCantidad))
      );
      FormatoDecimalSistema formatoDecimalPrecio = new FormatoDecimalSistema(par_decimal_importe);
      JFormattedTextField txt_formato_decimal_precio = new JFormattedTextField((AbstractFormatterFactory)(new DefaultFormatterFactory(formatoDecimalPrecio)));
      tabla.getColumn("Cantidad").setCellEditor(new DefaultCellEditor(txt_formato_decimal_cantidad));
      tabla.getColumn("Cant. Bonif").setCellEditor(new DefaultCellEditor(txt_formato_decimal_cantidad));
      tabla.getColumn("Precio").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precio));
      tabla.getColumn("SubTotal").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precio));
      tabla.getColumn("SubTotal").setCellRenderer(new DecimalCellRenderer(par_decimal_importe));
      tabla.getColumn("Descripción").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("UM").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("SubTotal").setCellRenderer(new PintarCeldasEditables());
   }

   public void addDefaultRow() {
      this.addRow(new Object[]{"", "", 0, 0, 0, "", 0, 0, 0, 0, 0, 0});
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
