package reportesVentas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadreReporte;

public class ReportesVentasE {
   public static void buscarMarcasTabla(
      String fecha_inicio, String fecha_fin, int cod_marca, JTable tabla, int par_decimal_importe, JinternalPadreReporte frame
   ) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      ModeloTablaDefecto modelo = (ModeloTablaDefecto)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\nmarcas.cod_marca,\r\nmarcas.nombre_marca,\r\nsum(ventas.importe_total) as total_venta,\r\nsum(ventas_detalle.costo_producto) as total_costo,\r\nmonedas.sigla\r\n from ventas,ventas_detalle,monedas,marcas,productos\r\nwhere\r\n(ventas.nro_registro = ventas_detalle.nro_registro) and (ventas.cod_moneda = monedas.cod_moneda) and (ventas_detalle.cod_producto = productos.cod_producto) and (productos.cod_marca = marcas.cod_marca) and ((marcas.cod_marca = ?) or (?=0))  and ventas.fecha between (?) and (?) group by marcas.cod_marca,ventas.cod_moneda\r\norder by total desc"
         );
         preparedStatement2.setInt(1, cod_marca);
         preparedStatement2.setInt(2, cod_marca);
         preparedStatement2.setString(3, fecha_inicio);
         preparedStatement2.setString(4, fecha_fin);
         rs2 = preparedStatement2.executeQuery();
         int fila = 0;
         double v_total_costo = 0.0;
         double v_total_venta = 0.0;
         double v_total_utilidad = 0.0;
         FormatoDecimalOperacionSistema formato = new FormatoDecimalOperacionSistema(2);
         if (rs2.next()) {
            do {
               v_total_costo = rs2.getDouble("total_costo");
               v_total_venta = rs2.getDouble("total_venta");
               v_total_utilidad = v_total_venta - v_total_costo;
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               tabla.setValueAt(rs2.getString("cod_marca"), fila, tabla.getColumn("Cod. Marca").getModelIndex());
               tabla.setValueAt(rs2.getString("nombre_marca"), fila, tabla.getColumn("Nombre Marca").getModelIndex());
               tabla.setValueAt(formato.format(v_total_venta), fila, tabla.getColumn("Total Venta").getModelIndex());
               tabla.setValueAt(formato.format(v_total_costo), fila, tabla.getColumn("Total Costo").getModelIndex());
               tabla.setValueAt(rs2.getString("sigla"), fila, tabla.getColumn("Moneda").getModelIndex());
               tabla.setValueAt(formato.format(v_total_utilidad), fila, tabla.getColumn("Utilidad").getModelIndex());
            } while (rs2.next());
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existen datos con el filtro establecido ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
         }
      } catch (Exception var23) {
         LogErrores.errores(var23, "Error al Cargar Producto..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al Cargar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void buscarProveedorTabla(
      String fecha_inicio, String fecha_fin, int cod_proveedor, JTable tabla, int par_decimal_importe, JinternalPadreReporte frame
   ) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      ModeloTablaDefecto modelo = (ModeloTablaDefecto)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\nproveedores.cod_proveedor,\r\nproveedores.nombre_proveedor,\r\nproveedores.ruc,\r\nsum(ventas.importe_total) as total_venta,\r\nsum(ventas_detalle.costo_producto) as total_costo,\r\nmonedas.sigla\r\n from ventas,ventas_detalle,monedas,proveedores,productos\r\nwhere\r\n(ventas.nro_registro = ventas_detalle.nro_registro) and (ventas.cod_moneda = monedas.cod_moneda) and (ventas_detalle.cod_producto = productos.cod_producto) and (productos.cod_proveedor = proveedores.cod_proveedor) and ((proveedores.cod_proveedor = ?) or (?=0))  and ventas.fecha between (?) and (?) group by proveedores.ruc,ventas.cod_moneda\r\norder by total desc"
         );
         preparedStatement2.setInt(1, cod_proveedor);
         preparedStatement2.setInt(2, cod_proveedor);
         preparedStatement2.setString(3, fecha_inicio);
         preparedStatement2.setString(4, fecha_fin);
         rs2 = preparedStatement2.executeQuery();
         int fila = 0;
         double v_total_costo = 0.0;
         double v_total_venta = 0.0;
         double v_total_utilidad = 0.0;
         FormatoDecimalOperacionSistema formato = new FormatoDecimalOperacionSistema(2);
         if (rs2.next()) {
            do {
               v_total_costo = rs2.getDouble("total_costo");
               v_total_venta = rs2.getDouble("total_venta");
               v_total_utilidad = v_total_venta - v_total_costo;
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               tabla.setValueAt(rs2.getString("cod_proveedor"), fila, tabla.getColumn("Cod. Proveedor").getModelIndex());
               tabla.setValueAt(rs2.getString("nombre_proveedor"), fila, tabla.getColumn("Nombre Proveedor").getModelIndex());
               tabla.setValueAt(rs2.getString("ruc"), fila, tabla.getColumn("RUC").getModelIndex());
               tabla.setValueAt(formato.format(v_total_venta), fila, tabla.getColumn("Total Venta").getModelIndex());
               tabla.setValueAt(formato.format(v_total_costo), fila, tabla.getColumn("Total Costo").getModelIndex());
               tabla.setValueAt(rs2.getString("sigla"), fila, tabla.getColumn("Moneda").getModelIndex());
               tabla.setValueAt(formato.format(v_total_utilidad), fila, tabla.getColumn("Utilidad").getModelIndex());
            } while (rs2.next());
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existen datos con el filtro establecido ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
         }
      } catch (Exception var23) {
         LogErrores.errores(var23, "Error al Cargar Producto..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al Cargar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void buscarClientesRankingTabla(
      String fecha_inicio, String fecha_fin, int cod_cliente, JTable tabla, int par_decimal_importe, JinternalPadreReporte frame
   ) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      ModeloTablaDefecto modelo = (ModeloTablaDefecto)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\nventas.cod_cliente,\r\nventas.nombre_cliente,\r\nventas.ruc,\r\nsum(ventas.importe_total) as total_venta,\r\nsum(ventas_detalle.costo_producto) as total_costo,\r\nmonedas.sigla\r\n from ventas,ventas_detalle,monedas\r\nwhere\r\nventas.nro_registro = ventas_detalle.nro_registro\r\nand ventas.cod_moneda = monedas.cod_moneda and ventas.fecha between (?) and (?) and ((ventas.cod_cliente = ?) or (?=0)) group by ventas.ruc,ventas.cod_moneda\r\norder by total desc"
         );
         preparedStatement2.setString(1, fecha_inicio);
         preparedStatement2.setString(2, fecha_fin);
         preparedStatement2.setInt(3, cod_cliente);
         preparedStatement2.setInt(4, cod_cliente);
         rs2 = preparedStatement2.executeQuery();
         int fila = 0;
         double v_total_costo = 0.0;
         double v_total_venta = 0.0;
         double v_total_utilidad = 0.0;
         FormatoDecimalOperacionSistema formato = new FormatoDecimalOperacionSistema(2);
         if (rs2.next()) {
            do {
               v_total_costo = rs2.getDouble("total_costo");
               v_total_venta = rs2.getDouble("total_venta");
               v_total_utilidad = v_total_venta - v_total_costo;
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               tabla.setValueAt(rs2.getString("cod_cliente"), fila, tabla.getColumn("Cod. Cliente").getModelIndex());
               tabla.setValueAt(rs2.getString("nombre_cliente"), fila, tabla.getColumn("Nombre Cliente").getModelIndex());
               tabla.setValueAt(rs2.getString("ruc"), fila, tabla.getColumn("RUC").getModelIndex());
               tabla.setValueAt(formato.format(v_total_venta), fila, tabla.getColumn("Total Venta").getModelIndex());
               tabla.setValueAt(formato.format(v_total_costo), fila, tabla.getColumn("Total Costo").getModelIndex());
               tabla.setValueAt(rs2.getString("sigla"), fila, tabla.getColumn("Moneda").getModelIndex());
               tabla.setValueAt(formato.format(v_total_utilidad), fila, tabla.getColumn("Utilidad").getModelIndex());
            } while (rs2.next());
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existen datos con el filtro establecido ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
         }
      } catch (Exception var23) {
         LogErrores.errores(var23, "Error al Cargar Producto..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al Cargar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void buscarProductosRankingTabla(
      String fecha_inicio,
      String fecha_fin,
      int cod_proveedor,
      int cod_marca,
      int cod_seccion,
      int cod_sub_seccion,
      int cod_grupo,
      JTable tabla,
      int par_decimal_importe,
      JinternalPadreReporte frame
   ) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      ModeloTablaDefecto modelo = (ModeloTablaDefecto)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\nventas_detalle.cod_producto,\r\nproductos.nombre_producto,\r\nmonedas.sigla,\r\nsum(ventas_detalle.costo_producto) as costo_vta,\r\nsum(ventas_detalle.cantidad) as cantidad_venta,\r\nsum(ventas_detalle.total) as total_vta\r\nfrom\r\nventas_detalle,ventas,productos,monedas\r\nwhere\r\n(ventas.nro_registro = ventas_detalle.nro_registro)\r\nand (ventas_detalle.cod_producto = productos.cod_producto)\r\nand (ventas.cod_moneda = monedas.cod_moneda)\r\nand (ventas.fecha between (?) and (?))\r\nand ((productos.cod_marca =?)or(?=0))\r\nand ((productos.cod_proveedor = ?) or (?=0))\r\nand ((productos.cod_seccion =? ) or (?=0))\r\nand ((productos.cod_sub_seccion =? ) or (?=0))\r\nand ((productos.cod_grupo =? ) or (?=0))\r\ngroup by ventas_detalle.cod_producto,ventas.cod_moneda\r\norder by cantidad_venta desc"
         );
         preparedStatement2.setString(1, fecha_inicio);
         preparedStatement2.setString(2, fecha_fin);
         preparedStatement2.setInt(3, cod_marca);
         preparedStatement2.setInt(4, cod_marca);
         preparedStatement2.setInt(5, cod_proveedor);
         preparedStatement2.setInt(6, cod_proveedor);
         preparedStatement2.setInt(7, cod_seccion);
         preparedStatement2.setInt(8, cod_seccion);
         preparedStatement2.setInt(9, cod_sub_seccion);
         preparedStatement2.setInt(10, cod_sub_seccion);
         preparedStatement2.setInt(11, cod_grupo);
         preparedStatement2.setInt(12, cod_grupo);
         rs2 = preparedStatement2.executeQuery();
         int fila = 0;
         double v_total_costo = 0.0;
         double v_total_venta = 0.0;
         double v_total_utilidad = 0.0;
         FormatoDecimalOperacionSistema formato = new FormatoDecimalOperacionSistema(2);
         if (rs2.next()) {
            do {
               v_total_costo = rs2.getDouble("costo_vta");
               v_total_venta = rs2.getDouble("total_vta");
               v_total_utilidad = v_total_venta - v_total_costo;
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               tabla.setValueAt(rs2.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
               tabla.setValueAt(rs2.getString("nombre_producto"), fila, tabla.getColumn("Nombre Producto").getModelIndex());
               tabla.setValueAt(formato.format(rs2.getDouble("cantidad_venta")), fila, tabla.getColumn("Cantidad").getModelIndex());
               tabla.setValueAt(formato.format(v_total_venta), fila, tabla.getColumn("Total Venta").getModelIndex());
               tabla.setValueAt(formato.format(v_total_costo), fila, tabla.getColumn("Total Costo").getModelIndex());
               tabla.setValueAt(rs2.getString("sigla"), fila, tabla.getColumn("Moneda").getModelIndex());
               tabla.setValueAt(formato.format(v_total_utilidad), fila, tabla.getColumn("Utilidad").getModelIndex());
            } while (rs2.next());
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existen datos con el filtro establecido ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
         }
      } catch (Exception var27) {
         LogErrores.errores(var27, "Error al Cargar Producto..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al Cargar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }
}
