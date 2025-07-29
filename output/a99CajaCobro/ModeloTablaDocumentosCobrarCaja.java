package a99CajaCobro;

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

public class ModeloTablaDocumentosCobrarCaja extends DefaultTableModel {
   private static final long serialVersionUID = 1L;

   public ModeloTablaDocumentosCobrarCaja(Object[][] data, Object[] columnNames) {
      super(data, columnNames);
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      return false;
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
      tabla.getColumn("Nro. Registro").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("CodTipoDoc").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Tipo Documento").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Timbrado").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Nro Documento").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Cuotas").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Nro Cuota").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("Importe").setHeaderRenderer(headerRendererDerecha);
      tabla.getColumn("CodMoneda").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("CodCliente").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Nro. Registro").setResizable(false);
      tabla.getColumn("Nro. Registro").setMinWidth(90);
      tabla.getColumn("Nro. Registro").setMaxWidth(90);
      tabla.getColumn("CodTipoDoc").setResizable(false);
      tabla.getColumn("CodTipoDoc").setMinWidth(0);
      tabla.getColumn("CodTipoDoc").setMaxWidth(0);
      tabla.getColumn("Tipo Documento").setResizable(false);
      tabla.getColumn("Tipo Documento").setMinWidth(210);
      tabla.getColumn("Tipo Documento").setMaxWidth(210);
      tabla.getColumn("Timbrado").setResizable(false);
      tabla.getColumn("Timbrado").setMinWidth(0);
      tabla.getColumn("Timbrado").setMaxWidth(0);
      tabla.getColumn("Nro Documento").setResizable(false);
      tabla.getColumn("Nro Documento").setMinWidth(140);
      tabla.getColumn("Nro Documento").setMaxWidth(140);
      tabla.getColumn("Cuotas").setResizable(false);
      tabla.getColumn("Cuotas").setMinWidth(70);
      tabla.getColumn("Cuotas").setMaxWidth(70);
      tabla.getColumn("Nro Cuota").setResizable(false);
      tabla.getColumn("Nro Cuota").setMinWidth(70);
      tabla.getColumn("Nro Cuota").setMaxWidth(70);
      tabla.getColumn("Importe").setResizable(false);
      tabla.getColumn("Importe").setMinWidth(110);
      tabla.getColumn("Importe").setMaxWidth(110);
      tabla.getColumn("CodMoneda").setResizable(false);
      tabla.getColumn("CodMoneda").setMinWidth(0);
      tabla.getColumn("CodMoneda").setMaxWidth(0);
      tabla.getColumn("Moneda").setResizable(false);
      tabla.getColumn("Moneda").setMinWidth(60);
      tabla.getColumn("Moneda").setMaxWidth(60);
      tabla.getColumn("Tipo").setResizable(false);
      tabla.getColumn("Tipo").setMinWidth(0);
      tabla.getColumn("Tipo").setMaxWidth(0);
      tabla.getColumn("CodCliente").setResizable(false);
      tabla.getColumn("CodCliente").setMinWidth(0);
      tabla.getColumn("CodCliente").setMaxWidth(0);
      FormatoDecimalSistema formatoDecimalPrecioBD = new FormatoDecimalSistema(7);
      JFormattedTextField txt_formato_decimal_precioBD = new JFormattedTextField(
         (AbstractFormatterFactory)(new DefaultFormatterFactory(formatoDecimalPrecioBD))
      );
      tabla.getColumn("Importe").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Importe").setCellRenderer(new DecimalCellRenderer(par_decimal_importe));
      tabla.getColumn("Nro. Registro").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Tipo Documento").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Nro Documento").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Cuotas").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Nro Cuota").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Importe").setCellRenderer(new PintarCeldasEditables());
      tabla.getColumn("Moneda").setCellRenderer(new PintarCeldasEditables());
   }

   public void addDefaultRow() {
      this.addRow(new Object[]{0, "", 0});
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
