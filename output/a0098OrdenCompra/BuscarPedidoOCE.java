package a0098OrdenCompra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class BuscarPedidoOCE {
   private int nro_registro;
   private int cod_comprador;
   private int cod_proveedor;
   private String fecha;
   private String nombre_comprador;
   private String nombre_proveedor;
   private String moneda;
   private double total;

   public static void buscarOC(int cod_proveedor, JinternalPadre frame, JTable tabla) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(2);
      DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
      deleteRow(tabla, modelo);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\norden_compra.nro_pedido,\r\norden_compra.nro_registro,\r\norden_compra.fecha,\r\norden_compra.cod_comprador,\r\ncompradores.nombre_comprador,\r\norden_compra.cod_proveedor,\r\nproveedores.nombre_proveedor,\r\norden_compra.total,monedas.sigla\r\n from orden_compra,compradores,proveedores,monedas\r\nwhere\r\norden_compra.cod_comprador = compradores.cod_comprador\r\nand orden_compra.cod_proveedor = proveedores.cod_proveedor\r\nand orden_compra.cod_moneda = monedas.cod_moneda\r\nand orden_compra.cod_proveedor = ?"
         );
         preparedStatement2.setInt(1, cod_proveedor);
         rs2 = preparedStatement2.executeQuery();
         SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

         while (rs2.next()) {
            modelo.addRow(
               new Object[]{
                  rs2.getInt("nro_registro"),
                  rs2.getInt("nro_pedido"),
                  formatoFecha.format(rs2.getDate("fecha")),
                  rs2.getInt("cod_comprador"),
                  rs2.getString("nombre_comprador"),
                  rs2.getInt("cod_proveedor"),
                  rs2.getString("nombre_proveedor"),
                  formatoDecimalPrecio.format(rs2.getDouble("total")),
                  rs2.getString("sigla")
               }
            );
         }
      } catch (Exception var13) {
         LogErrores.errores(var13, "Error al seleccionar Pedido a Proveedores..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al seleccionar Pedido..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void buscarPedido(int cod_proveedor, JinternalPadre frame, JTable tabla) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      new FormatoDecimalOperacionSistema(2);
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(2);
      new FormatoDecimalOperacionSistema(2);
      DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
      deleteRow(tabla, modelo);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\npedidos_proveedores.nro_registro,pedidos_proveedores.fecha,pedidos_proveedores.cod_comprador,compradores.nombre_comprador,\r\npedidos_proveedores.cod_proveedor, proveedores.nombre_proveedor,pedidos_proveedores.total,monedas.sigla\r\n from pedidos_proveedores,compradores,proveedores,monedas\r\nwhere\r\npedidos_proveedores.cod_comprador = compradores.cod_comprador\r\nand pedidos_proveedores.cod_proveedor = proveedores.cod_proveedor\r\nand pedidos_proveedores.cod_moneda = monedas.cod_moneda and pedidos_proveedores.cod_proveedor = ? \r\nand not exists\r\n(\r\nselect * from orden_compra where orden_compra.nro_pedido = pedidos_proveedores.nro_registro\r\n)"
         );
         preparedStatement2.setInt(1, cod_proveedor);
         rs2 = preparedStatement2.executeQuery();
         SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

         while (rs2.next()) {
            modelo.addRow(
               new Object[]{
                  rs2.getInt("nro_registro"),
                  formatoFecha.format(rs2.getDate("fecha")),
                  rs2.getInt("cod_comprador"),
                  rs2.getString("nombre_comprador"),
                  rs2.getInt("cod_proveedor"),
                  rs2.getString("nombre_proveedor"),
                  formatoDecimalPrecio.format(rs2.getDouble("total")),
                  rs2.getString("sigla")
               }
            );
         }
      } catch (Exception var15) {
         LogErrores.errores(var15, "Error al seleccionar Pedido a Proveedores..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al seleccionar Pedido..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   private static void deleteRow(JTable tabla, DefaultTableModel modelo) {
      if (tabla.isEditing()) {
         TableCellEditor editor = tabla.getCellEditor();
         if (editor != null) {
            editor.stopCellEditing();
         }
      }

      if (tabla.getRowCount() - 1 >= 0) {
         while (tabla.getRowCount() > 0) {
            modelo.removeRow(0);
         }
      }
   }

   public static void buscarPedido(int cod_proveedor, int cod_moneda, int cod_sucursal, JinternalPadre frame, JTable tabla) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      new FormatoDecimalOperacionSistema(2);
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(2);
      new FormatoDecimalOperacionSistema(2);
      DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
      deleteRow(tabla, modelo);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\npedidos_proveedores.nro_registro,pedidos_proveedores.fecha,pedidos_proveedores.cod_comprador,compradores.nombre_comprador,\r\npedidos_proveedores.cod_proveedor, proveedores.nombre_proveedor,pedidos_proveedores.total,monedas.sigla\r\n from pedidos_proveedores,compradores,proveedores,monedas\r\nwhere\r\npedidos_proveedores.cod_comprador = compradores.cod_comprador\r\nand pedidos_proveedores.cod_proveedor = proveedores.cod_proveedor\r\nand pedidos_proveedores.cod_moneda = monedas.cod_moneda and pedidos_proveedores.cod_proveedor = ? \r\nand pedidos_proveedores.cod_moneda = ? and pedidos_proveedores.cod_sucursal = ? and not exists\r\n(\r\nselect * from orden_compra where orden_compra.nro_pedido = pedidos_proveedores.nro_registro\r\n)"
         );
         preparedStatement2.setInt(1, cod_proveedor);
         preparedStatement2.setInt(2, cod_moneda);
         preparedStatement2.setInt(3, cod_sucursal);
         rs2 = preparedStatement2.executeQuery();
         SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

         while (rs2.next()) {
            modelo.addRow(
               new Object[]{
                  rs2.getInt("nro_registro"),
                  formatoFecha.format(rs2.getDate("fecha")),
                  rs2.getInt("cod_comprador"),
                  rs2.getString("nombre_comprador"),
                  rs2.getInt("cod_proveedor"),
                  rs2.getString("nombre_proveedor"),
                  formatoDecimalPrecio.format(rs2.getDouble("total")),
                  rs2.getString("sigla")
               }
            );
         }
      } catch (Exception var17) {
         LogErrores.errores(var17, "Error al seleccionar Pedido a Proveedores..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al seleccionar Pedido..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public BuscarPedidoOCE(
      int nro_registro, String fecha, int cod_comprador, String nombre_comprador, int cod_proveedor, String nombre_proveedor, double total, String moneda
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_comprador = cod_comprador;
      this.nombre_comprador = nombre_comprador;
      this.cod_proveedor = cod_proveedor;
      this.nombre_proveedor = nombre_proveedor;
      this.total = total;
      this.moneda = moneda;
   }

   public int getNro_registro() {
      return this.nro_registro;
   }

   public void setNro_registro(int nro_registro) {
      this.nro_registro = nro_registro;
   }

   public int getCod_comprador() {
      return this.cod_comprador;
   }

   public void setCod_comprador(int cod_comprador) {
      this.cod_comprador = cod_comprador;
   }

   public int getCod_proveedor() {
      return this.cod_proveedor;
   }

   public void setCod_proveedor(int cod_proveedor) {
      this.cod_proveedor = cod_proveedor;
   }

   public double getTotal() {
      return this.total;
   }

   public void setTotal(double total) {
      this.total = total;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
   }

   public String getNombre_comprador() {
      return this.nombre_comprador;
   }

   public void setNombre_comprador(String nombre_comprador) {
      this.nombre_comprador = nombre_comprador;
   }

   public String getNombre_proveediro() {
      return this.nombre_proveedor;
   }

   public void setNombre_proveediro(String nombre_proveedor) {
      this.nombre_proveedor = nombre_proveedor;
   }

   public String getMoneda() {
      return this.moneda;
   }

   public void setMoneda(String moneda) {
      this.moneda = moneda;
   }
}
