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
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import utilidades.Combo;
import utilidades.ComboEntidad;
import utilidadesTablaDetalle.DecimalCellRenderer;
import utilidadesTablaDetalle.FormatoDecimalSistema;
import utilidadesTablaDetalle.PintarCeldasEditables2;

public class ModeloTablaCobroClienteDetalle extends DefaultTableModel {
   private static final long serialVersionUID = 1L;
   Combo comboCondicion;
   Combo comboCuenta;
   Combo comboMoneda;
   Combo comboBanco;
   Combo comboTarjeta;

   public ModeloTablaCobroClienteDetalle(Object[][] data, Object[] columnNames) {
      super(data, columnNames);
   }

   @Override
   public Class<?> getColumnClass(int column) {
      String columnName = this.getColumnName(column);
      return columnName.equals("Importe") ? Double.class : Object.class;
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      return true;
   }

   public void formato(JTable tabla, int par_decimal_cantidad, int par_decimal_importe) {
      new DefaultTableCellRenderer() {
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
      tabla.getColumn("Cuenta").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Tarjeta").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Nro Tarjeta").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Cotizacion").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Importe").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Nro Cheque").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Banco").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Nro Cuenta").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Librador").setHeaderRenderer(headerRendererIzquierda);
      tabla.getColumn("Condicion").setResizable(false);
      tabla.getColumn("Condicion").setMinWidth(120);
      tabla.getColumn("Condicion").setMaxWidth(120);
      tabla.getColumn("Cuenta").setResizable(false);
      tabla.getColumn("Cuenta").setMinWidth(175);
      tabla.getColumn("Cuenta").setMaxWidth(175);
      tabla.getColumn("Tarjeta").setResizable(false);
      tabla.getColumn("Tarjeta").setMinWidth(120);
      tabla.getColumn("Tarjeta").setMaxWidth(120);
      tabla.getColumn("Nro Tarjeta").setResizable(false);
      tabla.getColumn("Nro Tarjeta").setMinWidth(105);
      tabla.getColumn("Nro Tarjeta").setMaxWidth(105);
      tabla.getColumn("Moneda").setResizable(false);
      tabla.getColumn("Moneda").setMinWidth(80);
      tabla.getColumn("Moneda").setMaxWidth(80);
      tabla.getColumn("Cotizacion").setResizable(false);
      tabla.getColumn("Cotizacion").setMinWidth(80);
      tabla.getColumn("Cotizacion").setMaxWidth(80);
      tabla.getColumn("Importe").setResizable(false);
      tabla.getColumn("Importe").setMinWidth(80);
      tabla.getColumn("Importe").setMaxWidth(80);
      tabla.getColumn("Nro Cheque").setResizable(false);
      tabla.getColumn("Nro Cheque").setMinWidth(80);
      tabla.getColumn("Nro Cheque").setMaxWidth(80);
      tabla.getColumn("Banco").setResizable(false);
      tabla.getColumn("Banco").setMinWidth(140);
      tabla.getColumn("Banco").setMaxWidth(140);
      tabla.getColumn("Nro Cuenta").setResizable(false);
      tabla.getColumn("Nro Cuenta").setMinWidth(80);
      tabla.getColumn("Nro Cuenta").setMaxWidth(80);
      tabla.getColumn("Librador").setResizable(false);
      tabla.getColumn("Librador").setMinWidth(160);
      tabla.getColumn("Librador").setMaxWidth(160);
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
      tabla.getColumn("Cotizacion").setCellEditor(new DefaultCellEditor(txt_formato_decimal_precioBD));
      tabla.getColumn("Cotizacion").setCellRenderer(new DecimalCellRenderer(par_decimal_importe));
      tabla.getColumn("Condicion").setCellRenderer(new PintarCeldasEditables2());
      tabla.getColumn("Cuenta").setCellRenderer(new PintarCeldasEditables2());
      tabla.getColumn("Tarjeta").setCellRenderer(new PintarCeldasEditables2());
      tabla.getColumn("Moneda").setCellRenderer(new PintarCeldasEditables2());
      tabla.getColumn("Banco").setCellRenderer(new PintarCeldasEditables2());
      this.comboCondicion = new Combo("CondicionesPago", null);
      this.comboCuenta = new Combo("Cuentas", null);
      this.comboMoneda = new Combo("Monedas", null);
      this.comboBanco = new Combo("Bancos", null);
      this.comboTarjeta = new Combo("Tarjetas", null);
      TableColumn columnaCondicion = tabla.getColumnModel().getColumn(tabla.getColumnModel().getColumnIndex("Condicion"));
      columnaCondicion.setCellEditor(new DefaultCellEditor(this.comboCondicion));
      columnaCondicion = tabla.getColumnModel().getColumn(tabla.getColumnModel().getColumnIndex("Cuenta"));
      columnaCondicion.setCellEditor(new DefaultCellEditor(this.comboCuenta));
      columnaCondicion = tabla.getColumnModel().getColumn(tabla.getColumnModel().getColumnIndex("Tarjeta"));
      columnaCondicion.setCellEditor(new DefaultCellEditor(this.comboTarjeta));
      columnaCondicion = tabla.getColumnModel().getColumn(tabla.getColumnModel().getColumnIndex("Banco"));
      columnaCondicion.setCellEditor(new DefaultCellEditor(this.comboBanco));
      columnaCondicion = tabla.getColumnModel().getColumn(tabla.getColumnModel().getColumnIndex("Moneda"));
      columnaCondicion.setCellEditor(new DefaultCellEditor(this.comboMoneda));
   }

   public void addDefaultRow() {
      ComboEntidad condicionDefault = (ComboEntidad)this.comboCondicion.getItemAt(0);
      ComboEntidad cuentaDefault = (ComboEntidad)this.comboCuenta.getItemAt(0);
      ComboEntidad monedaDefault = (ComboEntidad)this.comboMoneda.getItemAt(0);
      ComboEntidad bancoDefault = (ComboEntidad)this.comboBanco.getItemAt(0);
      ComboEntidad tarjetaDefault = (ComboEntidad)this.comboTarjeta.getItemAt(0);
      this.addRow(new Object[]{condicionDefault, cuentaDefault, tarjetaDefault, 0, monedaDefault, 0, 0, bancoDefault, 0, 0, "", 0, 0});
      String[] var10000 = new String[]{
         "Condicion", "Cuenta", "Tarjeta", "Nro Tarjeta", "Moneda", "Cotizacion", "Importe", "Banco", "Nro Cheque", "Nro Cuenta", "Librador", "Primario", "SW"
      };
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
