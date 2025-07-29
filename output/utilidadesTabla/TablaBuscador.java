package utilidadesTabla;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import utilidadesSQL.LogErrores;

public class TablaBuscador extends JTable {
   private static final long serialVersionUID = 1L;
   private ModeloTablaDefecto modelo;

   public static void eliminarFilasConWhile(ModeloTablaDefecto model) {
      int rowCount = model.getRowCount();

      for (int i = rowCount - 1; i >= 0; i--) {
         model.removeRow(i);
      }
   }

   public TablaBuscador(ModeloTablaDefecto modelo, String nombre) {
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      this.setName(nombre);
      this.modelo = modelo;
      this.crearTabla();
      this.setName(nombre);
      this.setSelectionMode(0);
      this.getTableHeader().setReorderingAllowed(false);
      TablaBuscador.ColorRenderer colorRenderer = new TablaBuscador.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
   }

   private void crearTabla() {
      try {
         this.setModel(this.modelo);
         DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
         modelocentrar.setHorizontalAlignment(2);
         this.setRowHeight(25);
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
         if (this.getName().equals("CodigoNom")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(180);
            this.getColumn("Codigo").setMaxWidth(180);
            this.getColumn("Codigo").setPreferredWidth(180);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(450);
            this.getColumn("Nombre").setMaxWidth(450);
            this.getColumn("Nombre").setPreferredWidth(450);
            this.getColumn("TF").setResizable(false);
            this.getColumn("TF").setMinWidth(360);
            this.getColumn("TF").setMaxWidth(360);
            this.getColumn("TF").setPreferredWidth(360);
            this.getColumn("IVA").setResizable(false);
            this.getColumn("IVA").setMinWidth(360);
            this.getColumn("IVA").setMaxWidth(360);
            this.getColumn("IVA").setPreferredWidth(360);
            this.getColumn("REGIMEN").setResizable(false);
            this.getColumn("REGIMEN").setMinWidth(360);
            this.getColumn("REGIMEN").setMaxWidth(360);
            this.getColumn("REGIMEN").setPreferredWidth(360);
            this.getColumn("UM").setResizable(false);
            this.getColumn("UM").setMinWidth(360);
            this.getColumn("UM").setMaxWidth(360);
            this.getColumn("UM").setPreferredWidth(360);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("TF").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("IVA").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("REGIMEN").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("UM").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("CodigoNom2")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(180);
            this.getColumn("Codigo").setMaxWidth(180);
            this.getColumn("Codigo").setPreferredWidth(180);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(450);
            this.getColumn("Nombre").setMaxWidth(450);
            this.getColumn("Nombre").setPreferredWidth(450);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("CodigoNom3")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(90);
            this.getColumn("Codigo").setMaxWidth(90);
            this.getColumn("Codigo").setPreferredWidth(90);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(450);
            this.getColumn("Nombre").setMaxWidth(450);
            this.getColumn("Nombre").setPreferredWidth(450);
            this.getColumn("Dias").setResizable(false);
            this.getColumn("Dias").setMinWidth(100);
            this.getColumn("Dias").setMaxWidth(100);
            this.getColumn("Dias").setPreferredWidth(100);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Dias").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("ListaPrecios")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(180);
            this.getColumn("Codigo").setMaxWidth(180);
            this.getColumn("Codigo").setPreferredWidth(180);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(450);
            this.getColumn("Nombre").setMaxWidth(450);
            this.getColumn("Nombre").setPreferredWidth(450);
            this.getColumn("CodMoneda").setResizable(false);
            this.getColumn("CodMoneda").setMinWidth(0);
            this.getColumn("CodMoneda").setMaxWidth(0);
            this.getColumn("CodMoneda").setPreferredWidth(0);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("CodMoneda").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("CodigoNom3Cliente")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(70);
            this.getColumn("Codigo").setMaxWidth(70);
            this.getColumn("Codigo").setPreferredWidth(70);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(410);
            this.getColumn("Nombre").setMaxWidth(410);
            this.getColumn("Nombre").setPreferredWidth(410);
            this.getColumn("RUC").setResizable(false);
            this.getColumn("RUC").setMinWidth(160);
            this.getColumn("RUC").setMaxWidth(160);
            this.getColumn("RUC").setPreferredWidth(160);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("RUC").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("BuscadorProducto")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(80);
            this.getColumn("Codigo").setMaxWidth(80);
            this.getColumn("Codigo").setPreferredWidth(80);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(450);
            this.getColumn("Nombre").setMaxWidth(450);
            this.getColumn("Nombre").setPreferredWidth(450);
            this.getColumn("TF").setResizable(false);
            this.getColumn("TF").setMinWidth(0);
            this.getColumn("TF").setMaxWidth(0);
            this.getColumn("TF").setPreferredWidth(0);
            this.getColumn("IVA").setResizable(false);
            this.getColumn("IVA").setMinWidth(0);
            this.getColumn("IVA").setMaxWidth(0);
            this.getColumn("IVA").setPreferredWidth(0);
            this.getColumn("REGIMEN").setResizable(false);
            this.getColumn("REGIMEN").setMinWidth(0);
            this.getColumn("REGIMEN").setMaxWidth(0);
            this.getColumn("REGIMEN").setPreferredWidth(0);
            this.getColumn("GRAVADO").setResizable(false);
            this.getColumn("GRAVADO").setMinWidth(0);
            this.getColumn("GRAVADO").setMaxWidth(0);
            this.getColumn("GRAVADO").setPreferredWidth(0);
            this.getColumn("UM").setResizable(false);
            this.getColumn("UM").setMinWidth(0);
            this.getColumn("UM").setMaxWidth(0);
            this.getColumn("UM").setPreferredWidth(0);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("TF").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("IVA").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("REGIMEN").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("UM").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("BuscadorProductoPrecio")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(80);
            this.getColumn("Codigo").setMaxWidth(80);
            this.getColumn("Codigo").setPreferredWidth(80);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(360);
            this.getColumn("Nombre").setMaxWidth(360);
            this.getColumn("Nombre").setPreferredWidth(360);
            this.getColumn("Sigla").setResizable(false);
            this.getColumn("Sigla").setMinWidth(0);
            this.getColumn("Sigla").setMaxWidth(0);
            this.getColumn("Sigla").setPreferredWidth(0);
            this.getColumn("CodMoneda").setResizable(false);
            this.getColumn("CodMoneda").setMinWidth(0);
            this.getColumn("CodMoneda").setMaxWidth(0);
            this.getColumn("CodMoneda").setPreferredWidth(0);
            this.getColumn("NombreMoneda").setResizable(false);
            this.getColumn("NombreMoneda").setMinWidth(0);
            this.getColumn("NombreMoneda").setMaxWidth(0);
            this.getColumn("NombreMoneda").setPreferredWidth(0);
            this.getColumn("Costo").setResizable(false);
            this.getColumn("Costo").setMinWidth(0);
            this.getColumn("Costo").setMaxWidth(0);
            this.getColumn("Costo").setPreferredWidth(0);
            this.getColumn("Precio").setResizable(false);
            this.getColumn("Precio").setMinWidth(0);
            this.getColumn("Precio").setMaxWidth(0);
            this.getColumn("Precio").setPreferredWidth(0);
            this.getColumn("Precio Minimo").setResizable(false);
            this.getColumn("Precio Minimo").setMinWidth(0);
            this.getColumn("Precio Minimo").setMaxWidth(0);
            this.getColumn("Precio Minimo").setPreferredWidth(0);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Sigla").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("CodMoneda").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("NombreMoneda").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Costo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Precio").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Precio Minimo").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("AjusteStock")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(180);
            this.getColumn("Codigo").setMaxWidth(180);
            this.getColumn("Codigo").setPreferredWidth(180);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(1200);
            this.getColumn("Nombre").setMaxWidth(1200);
            this.getColumn("Nombre").setPreferredWidth(1200);
            this.getColumn("Sigla").setResizable(false);
            this.getColumn("Sigla").setMinWidth(0);
            this.getColumn("Sigla").setMaxWidth(0);
            this.getColumn("Sigla").setPreferredWidth(0);
            this.getColumn("Costo").setResizable(false);
            this.getColumn("Costo").setMinWidth(0);
            this.getColumn("Costo").setMaxWidth(0);
            this.getColumn("Costo").setPreferredWidth(0);
            this.getColumn("Stock").setResizable(false);
            this.getColumn("Stock").setMinWidth(0);
            this.getColumn("Stock").setMaxWidth(0);
            this.getColumn("Stock").setPreferredWidth(0);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Sigla").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Costo").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("ListaPrecioProducto")) {
            this.getColumn("Lista de Precio").setResizable(false);
            this.getColumn("Lista de Precio").setMinWidth(140);
            this.getColumn("Lista de Precio").setMaxWidth(140);
            this.getColumn("Lista de Precio").setPreferredWidth(140);
            this.getColumn("Moneda").setResizable(false);
            this.getColumn("Moneda").setMinWidth(90);
            this.getColumn("Moneda").setMaxWidth(90);
            this.getColumn("Moneda").setPreferredWidth(90);
            this.getColumn("Precio").setResizable(false);
            this.getColumn("Precio").setMinWidth(100);
            this.getColumn("Precio").setMaxWidth(100);
            this.getColumn("Precio").setPreferredWidth(100);
            this.getColumn("Precio Minimo").setResizable(false);
            this.getColumn("Precio Minimo").setMinWidth(100);
            this.getColumn("Precio Minimo").setMaxWidth(100);
            this.getColumn("Precio Minimo").setPreferredWidth(100);
            this.getColumn("Lista de Precio").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Precio").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Precio Minimo").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("StockProducto")) {
            this.getColumn("Deposito").setResizable(false);
            this.getColumn("Deposito").setMinWidth(250);
            this.getColumn("Deposito").setMaxWidth(250);
            this.getColumn("Deposito").setPreferredWidth(250);
            this.getColumn("Cantidad").setResizable(false);
            this.getColumn("Cantidad").setMinWidth(68);
            this.getColumn("Cantidad").setMaxWidth(68);
            this.getColumn("Cantidad").setPreferredWidth(68);
            this.getColumn("Reserva").setResizable(false);
            this.getColumn("Reserva").setMinWidth(68);
            this.getColumn("Reserva").setMaxWidth(68);
            this.getColumn("Reserva").setPreferredWidth(68);
            this.getColumn("Transito").setResizable(false);
            this.getColumn("Transito").setMinWidth(68);
            this.getColumn("Transito").setMaxWidth(68);
            this.getColumn("Transito").setPreferredWidth(68);
            this.getColumn("Deposito").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Cantidad").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Reserva").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Transito").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("CodigoNom4Cliente")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(70);
            this.getColumn("Codigo").setMaxWidth(70);
            this.getColumn("Codigo").setPreferredWidth(70);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(410);
            this.getColumn("Nombre").setMaxWidth(410);
            this.getColumn("Nombre").setPreferredWidth(410);
            this.getColumn("RUC").setResizable(false);
            this.getColumn("RUC").setMinWidth(160);
            this.getColumn("RUC").setMaxWidth(160);
            this.getColumn("RUC").setPreferredWidth(160);
            this.getColumn("TipoFiscal").setResizable(false);
            this.getColumn("TipoFiscal").setMinWidth(0);
            this.getColumn("TipoFiscal").setMaxWidth(0);
            this.getColumn("TipoFiscal").setPreferredWidth(0);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("RUC").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("TipoDoc4")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(90);
            this.getColumn("Codigo").setMaxWidth(90);
            this.getColumn("Codigo").setPreferredWidth(90);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(450);
            this.getColumn("Nombre").setMaxWidth(450);
            this.getColumn("Nombre").setPreferredWidth(450);
            this.getColumn("Timbrado").setResizable(false);
            this.getColumn("Timbrado").setMinWidth(0);
            this.getColumn("Timbrado").setMaxWidth(0);
            this.getColumn("Timbrado").setPreferredWidth(0);
            this.getColumn("Numeracion").setResizable(false);
            this.getColumn("Numeracion").setMinWidth(0);
            this.getColumn("Numeracion").setMaxWidth(0);
            this.getColumn("Numeracion").setPreferredWidth(0);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Timbrado").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("CodigoNom5Proveedor")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(90);
            this.getColumn("Codigo").setMaxWidth(90);
            this.getColumn("Codigo").setPreferredWidth(90);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(450);
            this.getColumn("Nombre").setMaxWidth(450);
            this.getColumn("Nombre").setPreferredWidth(450);
            this.getColumn("RUC").setResizable(false);
            this.getColumn("RUC").setMinWidth(70);
            this.getColumn("RUC").setMaxWidth(70);
            this.getColumn("RUC").setPreferredWidth(70);
            this.getColumn("Telefono").setResizable(false);
            this.getColumn("Telefono").setMinWidth(0);
            this.getColumn("Telefono").setMaxWidth(0);
            this.getColumn("Telefono").setPreferredWidth(0);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Timbrado").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("CtasCobrar")) {
            this.getColumn("Cod Tipo Doc").setResizable(false);
            this.getColumn("Cod Tipo Doc").setMinWidth(90);
            this.getColumn("Cod Tipo Doc").setMaxWidth(90);
            this.getColumn("Cod Tipo Doc").setPreferredWidth(90);
            this.getColumn("Tipo Documento").setResizable(false);
            this.getColumn("Tipo Documento").setMinWidth(290);
            this.getColumn("Tipo Documento").setMaxWidth(290);
            this.getColumn("Tipo Documento").setPreferredWidth(290);
            this.getColumn("Timbrado").setResizable(false);
            this.getColumn("Timbrado").setMinWidth(70);
            this.getColumn("Timbrado").setMaxWidth(70);
            this.getColumn("Timbrado").setPreferredWidth(70);
            this.getColumn("Nro. Documento").setResizable(false);
            this.getColumn("Nro. Documento").setMinWidth(0);
            this.getColumn("Nro. Documento").setMaxWidth(0);
            this.getColumn("Nro. Documento").setPreferredWidth(0);
            this.getColumn("Cuotas").setResizable(false);
            this.getColumn("Cuotas").setMinWidth(40);
            this.getColumn("Cuotas").setMaxWidth(40);
            this.getColumn("Cuotas").setPreferredWidth(40);
            this.getColumn("Nro Cuota").setResizable(false);
            this.getColumn("Nro Cuota").setMinWidth(40);
            this.getColumn("Nro Cuota").setMaxWidth(40);
            this.getColumn("Nro Cuota").setPreferredWidth(40);
            this.getColumn("Importe").setResizable(false);
            this.getColumn("Importe").setMinWidth(70);
            this.getColumn("Importe").setMaxWidth(70);
            this.getColumn("Importe").setPreferredWidth(70);
            this.getColumn("CodMoneda").setResizable(false);
            this.getColumn("CodMoneda").setMinWidth(0);
            this.getColumn("CodMoneda").setMaxWidth(0);
            this.getColumn("CodMoneda").setPreferredWidth(0);
            this.getColumn("Moneda").setResizable(false);
            this.getColumn("Moneda").setMinWidth(70);
            this.getColumn("Moneda").setMaxWidth(70);
            this.getColumn("Moneda").setPreferredWidth(70);
            this.getColumn("CodCliente").setResizable(false);
            this.getColumn("CodCliente").setMinWidth(70);
            this.getColumn("CodCliente").setMaxWidth(70);
            this.getColumn("CodCliente").setPreferredWidth(70);
            this.getColumn("Primario").setResizable(false);
            this.getColumn("Primario").setMinWidth(0);
            this.getColumn("Primario").setMaxWidth(0);
            this.getColumn("Primario").setPreferredWidth(0);
            this.getColumn("SW").setResizable(false);
            this.getColumn("SW").setMinWidth(0);
            this.getColumn("SW").setMaxWidth(0);
            this.getColumn("SW").setPreferredWidth(0);
            this.getColumn("Cod Tipo Doc").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Tipo Documento").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Timbrado").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nro. Documento").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Cuotas").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nro Cuota").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Importe").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("PedidosProveedores")) {
            this.getColumn("Nro. Pedido").setResizable(false);
            this.getColumn("Nro. Pedido").setMinWidth(70);
            this.getColumn("Nro. Pedido").setMaxWidth(70);
            this.getColumn("Nro. Pedido").setPreferredWidth(70);
            this.getColumn("Fecha").setResizable(false);
            this.getColumn("Fecha").setMinWidth(80);
            this.getColumn("Fecha").setMaxWidth(80);
            this.getColumn("Fecha").setPreferredWidth(80);
            this.getColumn("CodComprador").setResizable(false);
            this.getColumn("CodComprador").setMinWidth(0);
            this.getColumn("CodComprador").setMaxWidth(0);
            this.getColumn("CodComprador").setPreferredWidth(0);
            this.getColumn("Nombre Comprador").setResizable(false);
            this.getColumn("Nombre Comprador").setMinWidth(155);
            this.getColumn("Nombre Comprador").setMaxWidth(155);
            this.getColumn("Nombre Comprador").setPreferredWidth(155);
            this.getColumn("CodProveedor").setResizable(false);
            this.getColumn("CodProveedor").setMinWidth(0);
            this.getColumn("CodProveedor").setMaxWidth(0);
            this.getColumn("CodProveedor").setPreferredWidth(0);
            this.getColumn("Nombre Proveedor").setResizable(false);
            this.getColumn("Nombre Proveedor").setMinWidth(205);
            this.getColumn("Nombre Proveedor").setMaxWidth(205);
            this.getColumn("Nombre Proveedor").setPreferredWidth(205);
            this.getColumn("Total").setResizable(false);
            this.getColumn("Total").setMinWidth(90);
            this.getColumn("Total").setMaxWidth(90);
            this.getColumn("Total").setPreferredWidth(90);
            this.getColumn("Moneda").setResizable(false);
            this.getColumn("Moneda").setMinWidth(50);
            this.getColumn("Moneda").setMaxWidth(50);
            this.getColumn("Moneda").setPreferredWidth(50);
            this.getColumn("Nro. Pedido").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Fecha").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre Comprador").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre Proveedor").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("PedidosProveedoresOC")) {
            this.getColumn("Nro. OC").setResizable(false);
            this.getColumn("Nro. OC").setMinWidth(70);
            this.getColumn("Nro. OC").setMaxWidth(70);
            this.getColumn("Nro. OC").setPreferredWidth(70);
            this.getColumn("Nro. Pedido").setResizable(false);
            this.getColumn("Nro. Pedido").setMinWidth(0);
            this.getColumn("Nro. Pedido").setMaxWidth(0);
            this.getColumn("Nro. Pedido").setPreferredWidth(0);
            this.getColumn("Fecha").setResizable(false);
            this.getColumn("Fecha").setMinWidth(80);
            this.getColumn("Fecha").setMaxWidth(80);
            this.getColumn("Fecha").setPreferredWidth(80);
            this.getColumn("CodComprador").setResizable(false);
            this.getColumn("CodComprador").setMinWidth(0);
            this.getColumn("CodComprador").setMaxWidth(0);
            this.getColumn("CodComprador").setPreferredWidth(0);
            this.getColumn("Nombre Comprador").setResizable(false);
            this.getColumn("Nombre Comprador").setMinWidth(155);
            this.getColumn("Nombre Comprador").setMaxWidth(155);
            this.getColumn("Nombre Comprador").setPreferredWidth(155);
            this.getColumn("CodProveedor").setResizable(false);
            this.getColumn("CodProveedor").setMinWidth(0);
            this.getColumn("CodProveedor").setMaxWidth(0);
            this.getColumn("CodProveedor").setPreferredWidth(0);
            this.getColumn("Nombre Proveedor").setResizable(false);
            this.getColumn("Nombre Proveedor").setMinWidth(205);
            this.getColumn("Nombre Proveedor").setMaxWidth(205);
            this.getColumn("Nombre Proveedor").setPreferredWidth(205);
            this.getColumn("Total").setResizable(false);
            this.getColumn("Total").setMinWidth(90);
            this.getColumn("Total").setMaxWidth(90);
            this.getColumn("Total").setPreferredWidth(90);
            this.getColumn("Moneda").setResizable(false);
            this.getColumn("Moneda").setMinWidth(50);
            this.getColumn("Moneda").setMaxWidth(50);
            this.getColumn("Moneda").setPreferredWidth(50);
            this.getColumn("Nro. OC").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Fecha").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre Comprador").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre Proveedor").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Total").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Moneda").setHeaderRenderer(headerRendererIzquierda);
         }
      } catch (Exception var3) {
         LogErrores.errores(var3, "Error al iniciar tabla");
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
