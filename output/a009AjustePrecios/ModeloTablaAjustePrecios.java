package a009AjustePrecios;

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

public class ModeloTablaAjustePrecios extends DefaultTableModel {
   private static final long serialVersionUID = 1L;

   public ModeloTablaAjustePrecios(Object[][] data, Object[] columnNames) {
      super(data, columnNames);
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      String columnName = this.getColumnName(column);
      return columnName.equals("Código") || columnName.equals("Precio Nuevo");
   }

   public void formato(JTable tabla, int par_decimal_importe) {
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
      tabla.getColumn("Descripción").setResizable(false);
      tabla.getColumn("Descripción").setMinWidth(630);
      tabla.getColumn("Descripción").setMaxWidth(630);
      tabla.getColumn("UM").setResizable(false);
      tabla.getColumn("UM").setMinWidth(30);
      tabla.getColumn("UM").setMaxWidth(30);
      tabla.getColumn("Nro Lista").setResizable(false);
      tabla.getColumn("Nro Lista").setMinWidth(0);
      tabla.getColumn("Nro Lista").setMaxWidth(0);
      tabla.getColumn("Lista de Precio").setResizable(false);
      tabla.getColumn("Lista de Precio").setMinWidth(0);
      tabla.getColumn("Lista de Precio").setMaxWidth(0);
      tabla.getColumn("CodMon").setResizable(false);
      tabla.getColumn("CodMon").setMinWidth(0);
      tabla.getColumn("CodMon").setMaxWidth(0);
      tabla.getColumn("Moneda").setResizable(false);
      tabla.getColumn("Moneda").setMinWidth(0);
      tabla.getColumn("Moneda").setMaxWidth(0);
      tabla.getColumn("Costo").setResizable(false);
      tabla.getColumn("Costo").setMinWidth(120);
      tabla.getColumn("Costo").setMaxWidth(120);
      tabla.getColumn("Precio Anterior").setResizable(false);
      tabla.getColumn("Precio Anterior").setMinWidth(120);
      tabla.getColumn("Precio Anterior").setMaxWidth(120);
      tabla.getColumn("Margen Precio").setResizable(false);
      tabla.getColumn("Margen Precio").setMinWidth(0);
      tabla.getColumn("Margen Precio").setMaxWidth(0);
      tabla.getColumn("Margen Costo").setResizable(false);
      tabla.getColumn("Margen Costo").setMinWidth(0);
      tabla.getColumn("Margen Costo").setMaxWidth(0);
      tabla.getColumn("Precio Minimo").setResizable(false);
      tabla.getColumn("Precio Minimo").setMinWidth(0);
      tabla.getColumn("Precio Minimo").setMaxWidth(0);
      tabla.getColumn("Precio Nuevo").setResizable(false);
      tabla.getColumn("Precio Nuevo").setMinWidth(120);
      tabla.getColumn("Precio Nuevo").setMaxWidth(120);
      tabla.getColumn("Primario").setResizable(false);
      tabla.getColumn("Primario").setMinWidth(0);
      tabla.getColumn("Primario").setMaxWidth(0);
      tabla.getColumn("SW").setResizable(false);
      tabla.getColumn("SW").setMinWidth(0);
      tabla.getColumn("SW").setMaxWidth(0);
      FormatoDecimalSistema formatoDecimalImporte = new FormatoDecimalSistema(par_decimal_importe);
      JFormattedTextField txt_formato_decimal_importe = new JFormattedTextField((AbstractFormatterFactory)(new DefaultFormatterFactory(formatoDecimalImporte)));
      FormatoDecimalSistema formatoDecimalMargen = new FormatoDecimalSistema(2);
      JFormattedTextField txt_formato_decimal_margen = new JFormattedTextField((AbstractFormatterFactory)(new DefaultFormatterFactory(formatoDecimalMargen)));
      tabla.getColumn("Precio Nuevo").setCellEditor(new DefaultCellEditor(txt_formato_decimal_importe));
      tabla.getColumn("Margen Costo").setCellEditor(new DefaultCellEditor(txt_formato_decimal_margen));
      tabla.getColumn("Margen Precio").setCellEditor(new DefaultCellEditor(txt_formato_decimal_margen));
      tabla.getColumn("Precio Minimo").setCellEditor(new DefaultCellEditor(txt_formato_decimal_importe));
      tabla.getColumn("Precio Nuevo").setCellRenderer(new DecimalCellRenderer(par_decimal_importe));
      tabla.getColumn("Margen Costo").setCellRenderer(new DecimalCellRenderer(2));
      tabla.getColumn("Margen Precio").setCellRenderer(new DecimalCellRenderer(2));
      tabla.getColumn("Precio Minimo").setCellRenderer(new DecimalCellRenderer(par_decimal_importe));
      tabla.getColumn("Precio Anterior").setCellRenderer(new DecimalCellRendererNoEdit(par_decimal_importe));
      tabla.getColumn("Costo").setCellRenderer(new DecimalCellRendererNoEdit(par_decimal_importe));
      tabla.getColumn("Descripción").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("UM").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Moneda").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Lista de Precio").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Código").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Descripción").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("UM").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Precio Nuevo").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Precio Minimo").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Costo").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Precio Anterior").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Margen Precio").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Margen Costo").setHeaderRenderer(headerRendererDerecha);
   }

   public void addDefaultRow() {
      this.addRow(new Object[]{"", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
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
