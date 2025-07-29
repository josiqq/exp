package utilidadesTabla;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadreReporte;

public class BuscadorTablaReportes extends JTable {
   private static final long serialVersionUID = 1L;
   private ModeloTablaDefecto modelo;

   public BuscadorTablaReportes(ModeloTablaDefecto modelo, String nombre, JinternalPadreReporte frame) {
      this.setName(nombre);
      this.modelo = modelo;
      this.crearTabla(frame);
      this.setName(nombre);
      this.setSelectionMode(0);
      this.getTableHeader().setReorderingAllowed(false);
      BuscadorTablaReportes.ColorRenderer colorRenderer = new BuscadorTablaReportes.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
   }

   private void crearTabla(JinternalPadreReporte frame) {
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

      try {
         this.setModel(this.modelo);
         DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
         modelocentrar.setHorizontalAlignment(2);
         this.setRowHeight(25);
         if (this.getName().equals("Productos")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(70);
            this.getColumn("Codigo").setMaxWidth(70);
            this.getColumn("Codigo").setPreferredWidth(70);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(310);
            this.getColumn("Nombre").setMaxWidth(310);
            this.getColumn("Nombre").setPreferredWidth(310);
            this.getColumn("Tipo Fiscal").setResizable(false);
            this.getColumn("Tipo Fiscal").setMinWidth(70);
            this.getColumn("Tipo Fiscal").setMaxWidth(70);
            this.getColumn("IVA").setResizable(false);
            this.getColumn("IVA").setMinWidth(30);
            this.getColumn("IVA").setMaxWidth(30);
            this.getColumn("Marca").setResizable(false);
            this.getColumn("Marca").setMinWidth(100);
            this.getColumn("Marca").setMaxWidth(100);
            this.getColumn("Unidad Medida").setResizable(false);
            this.getColumn("Unidad Medida").setMinWidth(100);
            this.getColumn("Unidad Medida").setMaxWidth(100);
            this.getColumn("Proveedor").setResizable(false);
            this.getColumn("Proveedor").setMinWidth(250);
            this.getColumn("Proveedor").setMaxWidth(250);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Tipo Fiscal").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("IVA").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Marca").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Unidad Medida").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Proveedor").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("AjusteStock")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(70);
            this.getColumn("Codigo").setMaxWidth(70);
            this.getColumn("Codigo").setPreferredWidth(70);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(310);
            this.getColumn("Nombre").setMaxWidth(310);
            this.getColumn("Nombre").setPreferredWidth(310);
            this.getColumn("Cantidad").setResizable(false);
            this.getColumn("Cantidad").setMinWidth(70);
            this.getColumn("Cantidad").setMaxWidth(70);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Cantidad").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("ListadoPrecios")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(70);
            this.getColumn("Codigo").setMaxWidth(70);
            this.getColumn("Codigo").setPreferredWidth(70);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(310);
            this.getColumn("Nombre").setMaxWidth(310);
            this.getColumn("Nombre").setPreferredWidth(310);
            this.getColumn("Moneda").setResizable(false);
            this.getColumn("Moneda").setMinWidth(70);
            this.getColumn("Moneda").setMaxWidth(70);
            this.getColumn("Precio").setResizable(false);
            this.getColumn("Precio").setMinWidth(70);
            this.getColumn("Precio").setMaxWidth(70);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Precio").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("ListadoVentas")) {
            this.getColumn("Nro. Registro").setResizable(false);
            this.getColumn("Nro. Registro").setMinWidth(90);
            this.getColumn("Nro. Registro").setMaxWidth(90);
            this.getColumn("Nro. Registro").setPreferredWidth(90);
            this.getColumn("Fecha").setResizable(false);
            this.getColumn("Fecha").setMinWidth(70);
            this.getColumn("Fecha").setMaxWidth(70);
            this.getColumn("Fecha").setPreferredWidth(70);
            this.getColumn("Deposito").setResizable(false);
            this.getColumn("Deposito").setMinWidth(210);
            this.getColumn("Deposito").setMaxWidth(210);
            this.getColumn("Cliente").setResizable(false);
            this.getColumn("Cliente").setMinWidth(390);
            this.getColumn("Cliente").setMaxWidth(390);
            this.getColumn("Total Venta").setResizable(false);
            this.getColumn("Total Venta").setMinWidth(120);
            this.getColumn("Total Venta").setMaxWidth(120);
            this.getColumn("Moneda").setResizable(false);
            this.getColumn("Moneda").setMinWidth(52);
            this.getColumn("Moneda").setMaxWidth(52);
            this.getColumn("Nro. Registro").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Fecha").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Deposito").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Cliente").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total Venta").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("ExtractoProductos")) {
            this.getColumn("Fecha").setResizable(false);
            this.getColumn("Fecha").setMinWidth(90);
            this.getColumn("Fecha").setMaxWidth(90);
            this.getColumn("Fecha").setPreferredWidth(90);
            this.getColumn("Hora").setResizable(false);
            this.getColumn("Hora").setMinWidth(70);
            this.getColumn("Hora").setMaxWidth(70);
            this.getColumn("Hora").setPreferredWidth(70);
            this.getColumn("Nro. Registro").setResizable(false);
            this.getColumn("Nro. Registro").setMinWidth(100);
            this.getColumn("Nro. Registro").setMaxWidth(100);
            this.getColumn("Operacion").setResizable(false);
            this.getColumn("Operacion").setMinWidth(180);
            this.getColumn("Operacion").setMaxWidth(180);
            this.getColumn("Entrada").setResizable(false);
            this.getColumn("Entrada").setMinWidth(100);
            this.getColumn("Entrada").setMaxWidth(100);
            this.getColumn("Salida").setResizable(false);
            this.getColumn("Salida").setMinWidth(100);
            this.getColumn("Salida").setMaxWidth(100);
            this.getColumn("Saldo").setResizable(false);
            this.getColumn("Saldo").setMinWidth(100);
            this.getColumn("Saldo").setMaxWidth(100);
            this.getColumn("Fecha").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Hora").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nro. Registro").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Operacion").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Entrada").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Salida").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Saldo").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("NotaEnvio")) {
            this.getColumn("Nro. Registro").setResizable(false);
            this.getColumn("Nro. Registro").setMinWidth(90);
            this.getColumn("Nro. Registro").setMaxWidth(90);
            this.getColumn("Nro. Registro").setPreferredWidth(90);
            this.getColumn("Fecha").setResizable(false);
            this.getColumn("Fecha").setMinWidth(120);
            this.getColumn("Fecha").setMaxWidth(120);
            this.getColumn("Fecha").setPreferredWidth(120);
            this.getColumn("Deposito Origen").setResizable(false);
            this.getColumn("Deposito Origen").setMinWidth(410);
            this.getColumn("Deposito Origen").setMaxWidth(410);
            this.getColumn("Deposito Destino").setResizable(false);
            this.getColumn("Deposito Destino").setMinWidth(410);
            this.getColumn("Deposito Destino").setMaxWidth(410);
            this.getColumn("Recibido").setResizable(false);
            this.getColumn("Recibido").setMinWidth(90);
            this.getColumn("Recibido").setMaxWidth(90);
            this.getColumn("Recibido").setPreferredWidth(90);
            this.getColumn("Nro. Registro").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Fecha").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Deposito Origen").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Deposito Destino").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Recibido").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("RankingProductos")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(120);
            this.getColumn("Codigo").setMaxWidth(120);
            this.getColumn("Codigo").setPreferredWidth(120);
            this.getColumn("Nombre Producto").setResizable(false);
            this.getColumn("Nombre Producto").setMinWidth(410);
            this.getColumn("Nombre Producto").setMaxWidth(410);
            this.getColumn("Nombre Producto").setPreferredWidth(410);
            this.getColumn("Cantidad").setResizable(false);
            this.getColumn("Cantidad").setMinWidth(90);
            this.getColumn("Cantidad").setMaxWidth(90);
            this.getColumn("Total Venta").setResizable(false);
            this.getColumn("Total Venta").setMinWidth(120);
            this.getColumn("Total Venta").setMaxWidth(120);
            this.getColumn("Total Costo").setResizable(false);
            this.getColumn("Total Costo").setMinWidth(120);
            this.getColumn("Total Costo").setMaxWidth(120);
            this.getColumn("Total Costo").setPreferredWidth(90);
            this.getColumn("Moneda").setResizable(false);
            this.getColumn("Moneda").setMinWidth(52);
            this.getColumn("Moneda").setMaxWidth(52);
            this.getColumn("Utilidad").setResizable(false);
            this.getColumn("Utilidad").setMinWidth(90);
            this.getColumn("Utilidad").setMaxWidth(90);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre Producto").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Cantidad").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total Venta").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total Costo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Utilidad").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("RankingClientes")) {
            this.getColumn("Cod. Cliente").setResizable(false);
            this.getColumn("Cod. Cliente").setMinWidth(81);
            this.getColumn("Cod. Cliente").setMaxWidth(81);
            this.getColumn("Cod. Cliente").setPreferredWidth(81);
            this.getColumn("Nombre Cliente").setResizable(false);
            this.getColumn("Nombre Cliente").setMinWidth(410);
            this.getColumn("Nombre Cliente").setMaxWidth(410);
            this.getColumn("Nombre Cliente").setPreferredWidth(410);
            this.getColumn("RUC").setResizable(false);
            this.getColumn("RUC").setMinWidth(90);
            this.getColumn("RUC").setMaxWidth(90);
            this.getColumn("Total Venta").setResizable(false);
            this.getColumn("Total Venta").setMinWidth(120);
            this.getColumn("Total Venta").setMaxWidth(120);
            this.getColumn("Total Costo").setResizable(false);
            this.getColumn("Total Costo").setMinWidth(120);
            this.getColumn("Total Costo").setMaxWidth(120);
            this.getColumn("Total Costo").setPreferredWidth(90);
            this.getColumn("Moneda").setResizable(false);
            this.getColumn("Moneda").setMinWidth(52);
            this.getColumn("Moneda").setMaxWidth(52);
            this.getColumn("Utilidad").setResizable(false);
            this.getColumn("Utilidad").setMinWidth(90);
            this.getColumn("Utilidad").setMaxWidth(90);
            this.getColumn("Cod. Cliente").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre Cliente").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("RUC").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total Venta").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total Costo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Utilidad").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("VentaxProveedor")) {
            this.getColumn("Cod. Proveedor").setResizable(false);
            this.getColumn("Cod. Proveedor").setMinWidth(90);
            this.getColumn("Cod. Proveedor").setMaxWidth(90);
            this.getColumn("Cod. Proveedor").setPreferredWidth(90);
            this.getColumn("Nombre Proveedor").setResizable(false);
            this.getColumn("Nombre Proveedor").setMinWidth(400);
            this.getColumn("Nombre Proveedor").setMaxWidth(400);
            this.getColumn("Nombre Proveedor").setPreferredWidth(400);
            this.getColumn("RUC").setResizable(false);
            this.getColumn("RUC").setMinWidth(90);
            this.getColumn("RUC").setMaxWidth(90);
            this.getColumn("Total Venta").setResizable(false);
            this.getColumn("Total Venta").setMinWidth(120);
            this.getColumn("Total Venta").setMaxWidth(120);
            this.getColumn("Total Costo").setResizable(false);
            this.getColumn("Total Costo").setMinWidth(120);
            this.getColumn("Total Costo").setMaxWidth(120);
            this.getColumn("Total Costo").setPreferredWidth(90);
            this.getColumn("Moneda").setResizable(false);
            this.getColumn("Moneda").setMinWidth(52);
            this.getColumn("Moneda").setMaxWidth(52);
            this.getColumn("Utilidad").setResizable(false);
            this.getColumn("Utilidad").setMinWidth(90);
            this.getColumn("Utilidad").setMaxWidth(90);
            this.getColumn("Cod. Proveedor").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre Proveedor").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("RUC").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total Venta").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total Costo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Utilidad").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("VentaxMarca")) {
            this.getColumn("Cod. Marca").setResizable(false);
            this.getColumn("Cod. Marca").setMinWidth(90);
            this.getColumn("Cod. Marca").setMaxWidth(90);
            this.getColumn("Cod. Marca").setPreferredWidth(90);
            this.getColumn("Nombre Marca").setResizable(false);
            this.getColumn("Nombre Marca").setMinWidth(400);
            this.getColumn("Nombre Marca").setMaxWidth(400);
            this.getColumn("Nombre Marca").setPreferredWidth(400);
            this.getColumn("Total Venta").setResizable(false);
            this.getColumn("Total Venta").setMinWidth(120);
            this.getColumn("Total Venta").setMaxWidth(120);
            this.getColumn("Total Costo").setResizable(false);
            this.getColumn("Total Costo").setMinWidth(120);
            this.getColumn("Total Costo").setMaxWidth(120);
            this.getColumn("Total Costo").setPreferredWidth(90);
            this.getColumn("Moneda").setResizable(false);
            this.getColumn("Moneda").setMinWidth(52);
            this.getColumn("Moneda").setMaxWidth(52);
            this.getColumn("Utilidad").setResizable(false);
            this.getColumn("Utilidad").setMinWidth(90);
            this.getColumn("Utilidad").setMaxWidth(90);
            this.getColumn("Cod. Marca").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre Marca").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total Venta").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total Costo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Utilidad").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("ComprasProductos")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(70);
            this.getColumn("Codigo").setMaxWidth(70);
            this.getColumn("Codigo").setPreferredWidth(70);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(310);
            this.getColumn("Nombre").setMaxWidth(310);
            this.getColumn("Nombre").setPreferredWidth(310);
            this.getColumn("Cantidad").setResizable(false);
            this.getColumn("Cantidad").setMinWidth(120);
            this.getColumn("Cantidad").setMaxWidth(120);
            this.getColumn("Cantidad Bonif").setResizable(false);
            this.getColumn("Cantidad Bonif").setMinWidth(120);
            this.getColumn("Cantidad Bonif").setMaxWidth(120);
            this.getColumn("Total").setResizable(false);
            this.getColumn("Total").setMinWidth(120);
            this.getColumn("Total").setMaxWidth(120);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Cantidad").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Cantidad Bonif").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total").setHeaderRenderer(headerRendererIzquierda);
         }
      } catch (Exception var4) {
         LogErrores.errores(var4, "Error al iniciar tabla", frame);
      }
   }

   public BuscadorTablaReportes(ModeloTablaDefecto modelo, String nombre, JinternalPadre frame) {
      this.setName(nombre);
      this.modelo = modelo;
      this.crearTabla(frame);
      this.setName(nombre);
      this.setSelectionMode(0);
      this.getTableHeader().setReorderingAllowed(false);
      BuscadorTablaReportes.ColorRenderer colorRenderer = new BuscadorTablaReportes.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
   }

   private void crearTabla(JinternalPadre frame) {
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

      try {
         this.setModel(this.modelo);
         DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
         modelocentrar.setHorizontalAlignment(2);
         this.setRowHeight(25);
         if (this.getName().equals("RecalculoStock")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(90);
            this.getColumn("Codigo").setMaxWidth(90);
            this.getColumn("Codigo").setPreferredWidth(90);
            this.getColumn("Descripcion").setResizable(false);
            this.getColumn("Descripcion").setMinWidth(410);
            this.getColumn("Descripcion").setMaxWidth(410);
            this.getColumn("Descripcion").setPreferredWidth(410);
            this.getColumn("CodDeposito").setResizable(false);
            this.getColumn("CodDeposito").setMinWidth(0);
            this.getColumn("CodDeposito").setMaxWidth(0);
            this.getColumn("Deposito").setResizable(false);
            this.getColumn("Deposito").setMinWidth(360);
            this.getColumn("Deposito").setMaxWidth(360);
            this.getColumn("Cantidad").setResizable(false);
            this.getColumn("Cantidad").setMinWidth(100);
            this.getColumn("Cantidad").setMaxWidth(100);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Descripcion").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("CodDeposito").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Deposito").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Cantidad").setHeaderRenderer(headerRendererIzquierda);
         }
      } catch (Exception var4) {
         LogErrores.errores(var4, "Error al iniciar tabla", frame);
      }
   }

   class ColorRenderer extends DefaultTableCellRenderer {
      private static final long serialVersionUID = 1L;
      private Color colorFilaPar = new Color(224, 224, 224);
      private Color colorFilaImpar = new Color(245, 245, 245);

      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         if (!isSelected) {
            c.setBackground(row % 2 == 0 ? this.colorFilaPar : this.colorFilaImpar);
         }

         return c;
      }
   }
}
