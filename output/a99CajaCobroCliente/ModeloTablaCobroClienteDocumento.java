package a99CajaCobroCliente;

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

public class ModeloTablaCobroClienteDocumento extends DefaultTableModel {
   private static final long serialVersionUID = 1L;

   public ModeloTablaCobroClienteDocumento(Object[][] data, Object[] columnNames) {
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
      return columnName.equals("Importe") || columnName.equals("Cod Tipo Doc");
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
      tabla.getColumn("Cod Tipo Doc").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Tipo Documento").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Timbrado").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Nro. Documento").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Cuota").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Importe").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Moneda").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Cod Tipo Doc").setResizable(false);
      tabla.getColumn("Cod Tipo Doc").setMinWidth(90);
      tabla.getColumn("Cod Tipo Doc").setMaxWidth(90);
      tabla.getColumn("Tipo Documento").setResizable(false);
      tabla.getColumn("Tipo Documento").setMinWidth(410);
      tabla.getColumn("Tipo Documento").setMaxWidth(410);
      tabla.getColumn("Timbrado").setResizable(false);
      tabla.getColumn("Timbrado").setMinWidth(120);
      tabla.getColumn("Timbrado").setMaxWidth(120);
      tabla.getColumn("Nro. Documento").setResizable(false);
      tabla.getColumn("Nro. Documento").setMinWidth(120);
      tabla.getColumn("Nro. Documento").setMaxWidth(120);
      tabla.getColumn("Cuota").setResizable(false);
      tabla.getColumn("Cuota").setMinWidth(40);
      tabla.getColumn("Cuota").setMaxWidth(40);
      tabla.getColumn("Importe").setResizable(false);
      tabla.getColumn("Importe").setMinWidth(160);
      tabla.getColumn("Importe").setMaxWidth(160);
      tabla.getColumn("Moneda").setResizable(false);
      tabla.getColumn("Moneda").setMinWidth(50);
      tabla.getColumn("Moneda").setMaxWidth(50);
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
      tabla.getColumn("Tipo Documento").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Timbrado").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Nro. Documento").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Cuota").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Moneda").setCellRenderer(new PintarCeldasEditables());
   }

   public void addDefaultRow() {
      this.addRow(new Object[]{"", "", 0, 0, 0, 0, "", 0, 0});
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
