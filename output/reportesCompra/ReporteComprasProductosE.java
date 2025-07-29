package reportesCompra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadreReporte;

public class ReporteComprasProductosE {
   public static void buscarProductosTablaReporte(
      String fecha_ini,
      String fecha_fin,
      String cod_producto,
      int cod_proveedor,
      int cod_marca,
      int cod_seccion,
      int cod_sub_seccion,
      int cod_grupo,
      JTable tabla,
      JinternalPadreReporte frame
   ) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      ModeloTablaDefecto modelo = (ModeloTablaDefecto)tabla.getModel();
      modelo.deleteRow(tabla);
      FormatoDecimalOperacionSistema formato = new FormatoDecimalOperacionSistema(2);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\ncompras_detalle.cod_producto,\r\nproductos.nombre_producto,\r\nsum(compras_detalle.cantidad) as cantidad,\r\nsum(compras_detalle.cantidad_bonif) as cantidadBonif\r\nfrom compras, compras_detalle,productos\r\nwhere\r\n(compras.nro_registro = compras_detalle.nro_registro)\r\nand (compras_detalle.cod_producto = productos.cod_producto)\r\nand ((compras_detalle.cod_producto =?) or (?=0))\r\nand (compras.fecha_factura >= ? and compras.fecha_factura <= ?)\r\nand ((productos.cod_marca = ?) or (?=0))\r\nand ((productos.cod_proveedor = ?) or (?=0))\r\nand ((productos.cod_seccion = ?) or (?=0))\r\nand ((productos.cod_sub_seccion = ?) or (?=0))\r\nand ((productos.cod_grupo = ?) or (?=0))\r\ngroup by\r\ncompras_detalle.cod_producto\r\norder by productos.nombre_producto"
         );
         preparedStatement2.setString(1, cod_producto);
         preparedStatement2.setString(2, cod_producto);
         preparedStatement2.setString(3, fecha_ini);
         preparedStatement2.setString(4, fecha_fin);
         preparedStatement2.setInt(5, cod_marca);
         preparedStatement2.setInt(6, cod_marca);
         preparedStatement2.setInt(7, cod_proveedor);
         preparedStatement2.setInt(8, cod_proveedor);
         preparedStatement2.setInt(9, cod_seccion);
         preparedStatement2.setInt(10, cod_seccion);
         preparedStatement2.setInt(11, cod_sub_seccion);
         preparedStatement2.setInt(12, cod_sub_seccion);
         preparedStatement2.setInt(13, cod_grupo);
         preparedStatement2.setInt(14, cod_grupo);
         rs2 = preparedStatement2.executeQuery();
         int fila = 0;
         double v_cantidad = 0.0;
         double v_cantidad_bonificacion = 0.0;
         double v_total = 0.0;
         if (tabla.getRowCount() - 1 == 0) {
            modelo.removeRow(0);
         }

         while (rs2.next()) {
            modelo.addRow(new Object[tabla.getColumnCount()]);
            fila = tabla.getRowCount() - 1;
            v_cantidad = rs2.getDouble("cantidad");
            v_cantidad_bonificacion = rs2.getDouble("cantidadBonif");
            v_total = v_cantidad + v_cantidad_bonificacion;
            tabla.setValueAt(rs2.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
            tabla.setValueAt(rs2.getString("nombre_producto"), fila, tabla.getColumn("Nombre").getModelIndex());
            tabla.setValueAt(formato.format(v_cantidad), fila, tabla.getColumn("Cantidad").getModelIndex());
            tabla.setValueAt(formato.format(v_cantidad_bonificacion), fila, tabla.getColumn("Cantidad Bonif").getModelIndex());
            tabla.setValueAt(formato.format(v_total), fila, tabla.getColumn("Total").getModelIndex());
         }

         if (String.valueOf(tabla.getValueAt(fila, tabla.getColumn("Nombre").getModelIndex())).trim().equals("")
            || tabla.getValueAt(fila, tabla.getColumn("Nombre").getModelIndex()) == null) {
            modelo.removeRow(fila);
         }
      } catch (Exception var26) {
         LogErrores.errores(var26, "Error al Cargar Producto..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al Cargar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }
}
