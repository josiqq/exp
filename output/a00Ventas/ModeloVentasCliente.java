package a00Ventas;

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
import utilidadesTablaDetalle.PintarCeldasEditables2;

public class ModeloVentasCliente extends DefaultTableModel {
   private static final long serialVersionUID = 1L;

   public ModeloVentasCliente(Object[][] data, Object[] columnNames) {
      super(data, columnNames);
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      String columnName = this.getColumnName(column);
      return columnName.equals("Código") || columnName.equals("Cantidad") || columnName.equals("Precio Venta") || columnName.equals("Desc.");
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
      tabla.getColumn("Código").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Descripción").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("TF").setHeaderRenderer(headerRendererCentro);
      tabla.getColumn("UM").setHeaderRenderer(headerRendererCentro);
      tabla.getColumn("IVA").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Precio Bruto").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Precio Venta").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Cantidad").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Desc.").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("SubTotal").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Gravado").setResizable(false);
      tabla.getColumn("Gravado").setMinWidth(0);
      tabla.getColumn("Gravado").setMaxWidth(0);
      tabla.getColumn("IVA").setResizable(false);
      tabla.getColumn("IVA").setMinWidth(0);
      tabla.getColumn("IVA").setMaxWidth(0);
      tabla.getColumn("TF").setResizable(false);
      tabla.getColumn("TF").setMinWidth(0);
      tabla.getColumn("TF").setMaxWidth(0);
      tabla.getColumn("REG").setResizable(false);
      tabla.getColumn("REG").setMinWidth(0);
      tabla.getColumn("REG").setMaxWidth(0);
      tabla.getColumn("Precio Lista").setResizable(false);
      tabla.getColumn("Precio Lista").setMinWidth(0);
      tabla.getColumn("Precio Lista").setMaxWidth(0);
      tabla.getColumn("Precio Minimo").setResizable(false);
      tabla.getColumn("Precio Minimo").setMinWidth(0);
      tabla.getColumn("Precio Minimo").setMaxWidth(0);
      tabla.getColumn("Costo").setResizable(false);
      tabla.getColumn("Costo").setMinWidth(0);
      tabla.getColumn("Costo").setMaxWidth(0);
      tabla.getColumn("Total Iva").setResizable(false);
      tabla.getColumn("Total Iva").setMinWidth(0);
      tabla.getColumn("Total Iva").setMaxWidth(0);
      tabla.getColumn("Total Gravado").setResizable(false);
      tabla.getColumn("Total Gravado").setMinWidth(0);
      tabla.getColumn("Total Gravado").setMaxWidth(0);
      tabla.getColumn("Total Exento").setResizable(false);
      tabla.getColumn("Total Exento").setMinWidth(0);
      tabla.getColumn("Total Exento").setMaxWidth(0);
      tabla.getColumn("Primario").setResizable(false);
      tabla.getColumn("Primario").setMinWidth(0);
      tabla.getColumn("Primario").setMaxWidth(0);
      tabla.getColumn("SW").setResizable(false);
      tabla.getColumn("SW").setMinWidth(0);
      tabla.getColumn("SW").setMaxWidth(0);
      tabla.getColumn("CodLista").setResizable(false);
      tabla.getColumn("CodLista").setMinWidth(0);
      tabla.getColumn("CodLista").setMaxWidth(0);
      tabla.getColumn("Código").setResizable(false);
      tabla.getColumn("Código").setMinWidth(120);
      tabla.getColumn("Código").setMaxWidth(120);
      tabla.getColumn("Código").setPreferredWidth(120);
      tabla.getColumn("Descripción").setResizable(false);
      tabla.getColumn("Descripción").setMinWidth(650);
      tabla.getColumn("Descripción").setMaxWidth(650);
      tabla.getColumn("Descripción").setPreferredWidth(650);
      tabla.getColumn("UM").setResizable(false);
      tabla.getColumn("UM").setMinWidth(50);
      tabla.getColumn("UM").setMaxWidth(50);
      tabla.getColumn("UM").setPreferredWidth(50);
      tabla.getColumn("Cantidad").setResizable(false);
      tabla.getColumn("Cantidad").setMinWidth(70);
      tabla.getColumn("Cantidad").setMaxWidth(70);
      tabla.getColumn("Cantidad").setPreferredWidth(70);
      tabla.getColumn("Desc.").setResizable(false);
      tabla.getColumn("Desc.").setMinWidth(40);
      tabla.getColumn("Desc.").setMaxWidth(40);
      tabla.getColumn("Desc.").setPreferredWidth(40);
      tabla.getColumn("Precio Bruto").setResizable(false);
      tabla.getColumn("Precio Bruto").setMinWidth(0);
      tabla.getColumn("Precio Bruto").setMaxWidth(0);
      tabla.getColumn("Precio Bruto").setPreferredWidth(0);
      tabla.getColumn("Precio Venta").setResizable(false);
      tabla.getColumn("Precio Venta").setMinWidth(120);
      tabla.getColumn("Precio Venta").setMaxWidth(120);
      tabla.getColumn("Precio Venta").setPreferredWidth(120);
      tabla.getColumn("SubTotal").setResizable(false);
      tabla.getColumn("SubTotal").setMinWidth(120);
      tabla.getColumn("SubTotal").setMaxWidth(120);
      tabla.getColumn("SubTotal").setPreferredWidth(120);
      FormatoDecimalSistema formatoDecimalCantidad = new FormatoDecimalSistema(par_decimal_cantidad);
      NumerosTextFieldTabla txt_formato_decimal_cantidad = new NumerosTextFieldTabla();
      txt_formato_decimal_cantidad.setFormatterFactory(new DefaultFormatterFactory(formatoDecimalCantidad));
      FormatoDecimalSistema formatoDecimalDescuento = new FormatoDecimalSistema(2);
      JFormattedTextField txt_formato_decimal_descuento = new JFormattedTextField(
         (AbstractFormatterFactory)(new DefaultFormatterFactory(formatoDecimalDescuento))
      );
      FormatoDecimalSistema formatoDecimalPrecioBD = new FormatoDecimalSistema(2);
      JFormattedTextField txt_formato_decimal_precioBD = new JFormattedTextField(
         (AbstractFormatterFactory)(new DefaultFormatterFactory(formatoDecimalPrecioBD))
      );
      tabla.getColumn("Precio Venta").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Cantidad").setCellEditor(new DefaultCellEditor(txt_formato_decimal_cantidad));
      tabla.getColumn("Desc.").setCellEditor(new DefaultCellEditor(txt_formato_decimal_descuento));
      tabla.getColumn("Cantidad").setCellRenderer(new DecimalCellRenderer(par_decimal_cantidad));
      tabla.getColumn("Desc.").setCellRenderer(new DecimalCellRenderer(par_decimal_cantidad));
      tabla.getColumn("SubTotal").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Precio Venta").setCellRenderer(new DecimalCellRenderer(par_decimal_importe));
      tabla.getColumn("SubTotal").setCellRenderer(new DecimalCellRenderer(par_decimal_importe));
      tabla.getColumn("Código").setCellRenderer(new PintarCeldasEditables2());
      tabla.getColumn("Descripción").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("UM").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("IVA").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("REG").setCellRenderer(new PintarCeldasEditables());
   }

   public void addDefaultRow() {
      this.addRow(new Object[]{"", "", 0, 0, 0, 0, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
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
