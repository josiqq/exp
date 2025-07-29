package a00Compras;

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
import utilidadesTabla.NumerosTextFieldTabla;
import utilidadesTablaDetalle.DecimalCellRenderer;
import utilidadesTablaDetalle.FormatoDecimalSistema;
import utilidadesTablaDetalle.PintarCeldasEditables;

public class ModeloTablaCompra extends DefaultTableModel {
   private static final long serialVersionUID = 1L;

   public ModeloTablaCompra(Object[][] data, Object[] columnNames, JTable tabla) {
      super(data, columnNames);
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      String columnName = this.getColumnName(column);
      return columnName.equals("Código")
         || columnName.equals("Cantidad")
         || columnName.equals("Precio Bruto")
         || columnName.equals("Cant. Bonif")
         || columnName.equals("Desc.")
         || columnName.equals("SubTotal");
   }

   public void formato(JTable tabla, int par_decimal_cantidad, int par_decimal_importe) {
      tabla.getColumn("Código").setResizable(false);
      tabla.getColumn("Código").setMinWidth(100);
      tabla.getColumn("Código").setMaxWidth(100);
      tabla.getColumn("Código").setPreferredWidth(100);
      tabla.getColumn("Descripción").setResizable(false);
      tabla.getColumn("Descripción").setMinWidth(455);
      tabla.getColumn("Descripción").setMaxWidth(455);
      tabla.getColumn("Descripción").setPreferredWidth(455);
      tabla.getColumn("TF").setResizable(false);
      tabla.getColumn("TF").setMinWidth(0);
      tabla.getColumn("TF").setMaxWidth(0);
      tabla.getColumn("TF").setPreferredWidth(0);
      tabla.getColumn("IVA").setResizable(false);
      tabla.getColumn("IVA").setMinWidth(45);
      tabla.getColumn("IVA").setMaxWidth(45);
      tabla.getColumn("IVA").setPreferredWidth(45);
      tabla.getColumn("Gravado").setResizable(false);
      tabla.getColumn("Gravado").setMinWidth(0);
      tabla.getColumn("Gravado").setMaxWidth(0);
      tabla.getColumn("Gravado").setPreferredWidth(0);
      tabla.getColumn("REG").setResizable(false);
      tabla.getColumn("REG").setMinWidth(35);
      tabla.getColumn("REG").setMaxWidth(35);
      tabla.getColumn("REG").setPreferredWidth(35);
      tabla.getColumn("UM").setResizable(false);
      tabla.getColumn("UM").setMinWidth(35);
      tabla.getColumn("UM").setMaxWidth(35);
      tabla.getColumn("UM").setPreferredWidth(35);
      tabla.getColumn("Cantidad").setResizable(false);
      tabla.getColumn("Cantidad").setMinWidth(70);
      tabla.getColumn("Cantidad").setMaxWidth(70);
      tabla.getColumn("Cantidad").setPreferredWidth(70);
      tabla.getColumn("Cant. Bonif").setResizable(false);
      tabla.getColumn("Cant. Bonif").setMinWidth(70);
      tabla.getColumn("Cant. Bonif").setMaxWidth(70);
      tabla.getColumn("Cant. Bonif").setPreferredWidth(70);
      tabla.getColumn("Precio Bruto").setResizable(false);
      tabla.getColumn("Precio Bruto").setMinWidth(90);
      tabla.getColumn("Precio Bruto").setMaxWidth(90);
      tabla.getColumn("Precio Bruto").setPreferredWidth(90);
      tabla.getColumn("Precio Real").setResizable(false);
      tabla.getColumn("Precio Real").setMinWidth(0);
      tabla.getColumn("Precio Real").setMaxWidth(0);
      tabla.getColumn("Precio Real").setPreferredWidth(0);
      tabla.getColumn("Desc.").setResizable(false);
      tabla.getColumn("Desc.").setMinWidth(45);
      tabla.getColumn("Desc.").setMaxWidth(45);
      tabla.getColumn("Desc.").setPreferredWidth(45);
      tabla.getColumn("Precio Neto").setResizable(false);
      tabla.getColumn("Precio Neto").setMinWidth(90);
      tabla.getColumn("Precio Neto").setMaxWidth(90);
      tabla.getColumn("Precio Neto").setPreferredWidth(90);
      tabla.getColumn("Total Iva").setResizable(false);
      tabla.getColumn("Total Iva").setMinWidth(0);
      tabla.getColumn("Total Iva").setMaxWidth(0);
      tabla.getColumn("Total Iva").setPreferredWidth(0);
      tabla.getColumn("Total Gravado").setResizable(false);
      tabla.getColumn("Total Gravado").setMinWidth(0);
      tabla.getColumn("Total Gravado").setMaxWidth(0);
      tabla.getColumn("Total Gravado").setPreferredWidth(0);
      tabla.getColumn("Total Exento").setResizable(false);
      tabla.getColumn("Total Exento").setMinWidth(0);
      tabla.getColumn("Total Exento").setMaxWidth(0);
      tabla.getColumn("Total Exento").setPreferredWidth(0);
      tabla.getColumn("SubTotal").setResizable(false);
      tabla.getColumn("SubTotal").setMinWidth(100);
      tabla.getColumn("SubTotal").setMaxWidth(100);
      tabla.getColumn("SubTotal").setPreferredWidth(60);
      tabla.getColumn("Primario").setResizable(false);
      tabla.getColumn("Primario").setMinWidth(0);
      tabla.getColumn("Primario").setMaxWidth(0);
      tabla.getColumn("Primario").setPreferredWidth(0);
      tabla.getColumn("SW").setResizable(false);
      tabla.getColumn("SW").setMinWidth(0);
      tabla.getColumn("SW").setMaxWidth(0);
      tabla.getColumn("SW").setPreferredWidth(0);
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
      tabla.getColumn("Precio Bruto").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Desc.").setHeaderRenderer(headerRendererCentro);
      tabla.getColumn("Precio Neto").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("SubTotal").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Código").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Descripción").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("IVA").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("UM").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("REG").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Cantidad").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Cant. Bonif").setHeaderRenderer(headerRendererIzquierda);
      FormatoDecimalSistema formatoDecimalCantidad = new FormatoDecimalSistema(par_decimal_cantidad);
      NumerosTextFieldTabla txt_formato_decimal_cantidad = new NumerosTextFieldTabla();
      txt_formato_decimal_cantidad.setFormatterFactory(new DefaultFormatterFactory(formatoDecimalCantidad));
      FormatoDecimalSistema formatoDecimalDescuento = new FormatoDecimalSistema(2);
      JFormattedTextField txt_formato_decimal_descuento = new JFormattedTextField(
         (AbstractFormatterFactory)(new DefaultFormatterFactory(formatoDecimalDescuento))
      );
      FormatoDecimalSistema formatoDecimalPrecioBD = new FormatoDecimalSistema(7);
      JFormattedTextField txt_formato_decimal_precioBD = new JFormattedTextField(
         (AbstractFormatterFactory)(new DefaultFormatterFactory(formatoDecimalPrecioBD))
      );
      tabla.getColumn("Precio Bruto").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Cant. Bonif").setCellEditor(new DefaultCellEditor(txt_formato_decimal_cantidad));
      tabla.getColumn("Cantidad").setCellEditor(new DefaultCellEditor(txt_formato_decimal_cantidad));
      tabla.getColumn("Desc.").setCellEditor(new DefaultCellEditor(txt_formato_decimal_descuento));
      tabla.getColumn("SubTotal").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Precio Neto").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Precio Real").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Total Iva").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Total Gravado").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Total Exento").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Precio Bruto").setCellRenderer(new DecimalCellRenderer(par_decimal_importe));
      tabla.getColumn("SubTotal").setCellRenderer(new DecimalCellRenderer(par_decimal_importe));
      tabla.getColumn("Precio Neto").setCellRenderer(new DecimalCellRenderer(par_decimal_importe));
      tabla.getColumn("Precio Real").setCellRenderer(new DecimalCellRenderer(7));
      tabla.getColumn("Total Exento").setCellRenderer(new DecimalCellRenderer(7));
      tabla.getColumn("Total Iva").setCellRenderer(new DecimalCellRenderer(7));
      tabla.getColumn("Total Gravado").setCellRenderer(new DecimalCellRenderer(7));
      tabla.getColumn("Descripción").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("UM").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("IVA").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("REG").setCellRenderer(new PintarCeldasEditables());
   }

   public void addDefaultRow() {
      this.addRow(new Object[]{"", "", "0", "0", "0", "0", "", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
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
