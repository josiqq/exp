package utilidadesTabla;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JDialog;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JDialogBuscadores;
import utilidadesVentanas.JinternalPadre;

public class BuscadorProductoE {
   public static void buscarBuscadorAjustePrecio(int cod_lista, String texto, JTable tabla, ModeloTablaDefecto modelo, JDialogBuscadores frame) {
      String sql = "";
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         String textoBuscado = "%" + texto + "%";
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         if (texto.trim().equals("")) {
            sql = "select\r\nproductos.cod_producto,productos.nombre_producto,unidades_medidas.sigla,lista_precios_det.cod_moneda,monedas.nombre_moneda,\r\nproductos.costo_producto,lista_precios_det.precio,lista_precios_det.precio_minimo\r\nfrom\r\nproductos,unidades_medidas,lista_precios_det,monedas\r\nwhere\r\nproductos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand productos.cod_producto = lista_precios_det.cod_producto\r\nand lista_precios_det.cod_moneda = monedas.cod_moneda\r\nand lista_precios_det.cod_lista =?";
            preparedStatementSelect = conexion.prepareStatement(sql);
            preparedStatementSelect.setInt(1, cod_lista);
         } else {
            sql = "select\r\nproductos.cod_producto,productos.nombre_producto,unidades_medidas.sigla,lista_precios_det.cod_moneda,monedas.nombre_moneda,\r\nproductos.costo_producto,lista_precios_det.precio,lista_precios_det.precio_minimo\r\nfrom\r\nproductos,unidades_medidas,lista_precios_det,monedas\r\nwhere\r\n(productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida)\r\nand (productos.cod_producto = lista_precios_det.cod_producto)\r\nand (lista_precios_det.cod_moneda = monedas.cod_moneda) and lista_precios_det.cod_lista =?\r\nAND (EXISTS (SELECT * FROM productos_codigo_barras             WHERE productos_codigo_barras.cod_producto = productos.cod_producto             AND productos_codigo_barras.codigo_barra LIKE ?)      OR productos.cod_producto LIKE ?      OR productos.nombre_producto LIKE ?)";
            preparedStatementSelect = conexion.prepareStatement(sql);
            preparedStatementSelect.setInt(1, cod_lista);
            preparedStatementSelect.setString(2, textoBuscado);
            preparedStatementSelect.setString(3, textoBuscado);
            preparedStatementSelect.setString(4, textoBuscado);
         }

         resultado = preparedStatementSelect.executeQuery();
         int fila = 0;
         if (resultado.next()) {
            do {
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               tabla.setValueAt(resultado.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
               tabla.setValueAt(resultado.getString("nombre_producto"), fila, tabla.getColumn("Nombre").getModelIndex());
               tabla.setValueAt(resultado.getString("sigla"), fila, tabla.getColumn("Sigla").getModelIndex());
               tabla.setValueAt(resultado.getString("cod_moneda"), fila, tabla.getColumn("CodMoneda").getModelIndex());
               tabla.setValueAt(resultado.getString("nombre_moneda"), fila, tabla.getColumn("NombreMoneda").getModelIndex());
               tabla.setValueAt(resultado.getString("costo_producto"), fila, tabla.getColumn("Costo").getModelIndex());
               tabla.setValueAt(resultado.getString("precio"), fila, tabla.getColumn("Precio").getModelIndex());
               tabla.setValueAt(resultado.getString("precio_minimo"), fila, tabla.getColumn("Precio Minimo").getModelIndex());
            } while (resultado.next());
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existen datos con el filtro establecido ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
         }
      } catch (Exception var16) {
         LogErrores.errores(var16, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void buscarBuscadorVentas(int cod_lista, String texto, JTable tabla, ModeloTablaDefecto modelo, JDialogBuscadores frame) {
      String sql = "";
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         String textoBuscado = "%" + texto + "%";
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         if (texto.trim().equals("")) {
            sql = "select\r\nproductos.cod_producto,productos.nombre_producto,unidades_medidas.sigla,lista_precios_det.cod_moneda,monedas.nombre_moneda,\r\nproductos.costo_producto,lista_precios_det.precio,lista_precios_det.precio_minimo\r\nfrom\r\nproductos,unidades_medidas,lista_precios_det,monedas\r\nwhere\r\nproductos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand productos.cod_producto = lista_precios_det.cod_producto\r\nand lista_precios_det.cod_moneda = monedas.cod_moneda\r\nand lista_precios_det.cod_lista =?";
            preparedStatementSelect = conexion.prepareStatement(sql);
            preparedStatementSelect.setInt(1, cod_lista);
         } else {
            sql = "select\r\nproductos.cod_producto,productos.nombre_producto,unidades_medidas.sigla,lista_precios_det.cod_moneda,monedas.nombre_moneda,\r\nproductos.costo_producto,lista_precios_det.precio,lista_precios_det.precio_minimo\r\nfrom\r\nproductos,unidades_medidas,lista_precios_det,monedas\r\nwhere\r\n(productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida)\r\nand (productos.cod_producto = lista_precios_det.cod_producto)\r\nand (lista_precios_det.cod_moneda = monedas.cod_moneda) and lista_precios_det.cod_lista =?\r\nAND (EXISTS (SELECT * FROM productos_codigo_barras             WHERE productos_codigo_barras.cod_producto = productos.cod_producto             AND productos_codigo_barras.codigo_barra LIKE ?)      OR productos.cod_producto LIKE ?      OR productos.nombre_producto LIKE ?)";
            preparedStatementSelect = conexion.prepareStatement(sql);
            preparedStatementSelect.setInt(1, cod_lista);
            preparedStatementSelect.setString(2, textoBuscado);
            preparedStatementSelect.setString(3, textoBuscado);
            preparedStatementSelect.setString(4, textoBuscado);
         }

         resultado = preparedStatementSelect.executeQuery();
         int fila = 0;
         if (resultado.next()) {
            do {
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               tabla.setValueAt(resultado.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
               tabla.setValueAt(resultado.getString("nombre_producto"), fila, tabla.getColumn("Nombre").getModelIndex());
               tabla.setValueAt(resultado.getString("sigla"), fila, tabla.getColumn("Sigla").getModelIndex());
               tabla.setValueAt(resultado.getString("costo_producto"), fila, tabla.getColumn("Costo").getModelIndex());
            } while (resultado.next());
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existen datos con el filtro establecido ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
         }
      } catch (Exception var16) {
         LogErrores.errores(var16, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarProductosTablaFiscal(String texto, JTable tabla, ModeloTablaDefecto modelo, JinternalPadre frame) {
      String sql = "";
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         String textoBuscado = "%" + texto + "%";
         if (texto.trim().equals("")) {
            sql = "select\r\n      productos.cod_producto,productos.nombre_producto,productos.tipo_fiscal,productos.iva_producto,porc_regimen,porcentaje_gravado,unidades_medidas.sigla\r\nfrom productos, unidades_medidas\r\nwhere\r\nproductos.cod_unidad_medida = unidades_medidas.cod_unidad_medida";
            preparedStatementSelect = conexion.prepareStatement(sql);
         } else {
            sql = "SELECT productos.cod_producto, productos.nombre_producto, productos.tipo_fiscal, productos.iva_producto, productos.porc_regimen, productos.porcentaje_gravado, unidades_medidas.sigla FROM productos, unidades_medidas WHERE productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida AND (EXISTS (SELECT * FROM productos_codigo_barras             WHERE productos_codigo_barras.cod_producto = productos.cod_producto             AND productos_codigo_barras.codigo_barra LIKE ?)      OR productos.cod_producto LIKE ?      OR productos.nombre_producto LIKE ?)";
            preparedStatementSelect = conexion.prepareStatement(sql);
            preparedStatementSelect.setString(1, textoBuscado);
            preparedStatementSelect.setString(2, textoBuscado);
            preparedStatementSelect.setString(3, textoBuscado);
         }

         resultado = preparedStatementSelect.executeQuery();
         int fila = 0;
         if (resultado.next()) {
            do {
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               tabla.setValueAt(resultado.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
               tabla.setValueAt(resultado.getString("nombre_producto"), fila, tabla.getColumn("Nombre").getModelIndex());
               tabla.setValueAt(resultado.getInt("tipo_fiscal"), fila, tabla.getColumn("TF").getModelIndex());
               tabla.setValueAt(resultado.getDouble("iva_producto"), fila, tabla.getColumn("IVA").getModelIndex());
               tabla.setValueAt(resultado.getDouble("porc_regimen"), fila, tabla.getColumn("REGIMEN").getModelIndex());
               tabla.setValueAt(resultado.getDouble("porcentaje_gravado"), fila, tabla.getColumn("GRAVADO").getModelIndex());
               tabla.setValueAt(resultado.getString("sigla"), fila, tabla.getColumn("UM").getModelIndex());
            } while (resultado.next());
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existen datos con el filtro establecido ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
         }
      } catch (Exception var15) {
         LogErrores.errores(var15, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void buscarBuscadorAjusteStock(int cod_deposito, String texto, JTable tabla, ModeloTablaDefecto modelo, JDialogBuscadores frame) {
      String sql = "";
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         String textoBuscado = "%" + texto + "%";
         if (texto.trim().equals("")) {
            sql = "select\r\nproductos.cod_producto,productos.nombre_producto,unidades_medidas.sigla,productos.costo_producto,\r\nf_stock(productos.cod_producto,?) as stock\r\nfrom productos,unidades_medidas\r\nwhere\r\nproductos.cod_unidad_medida = unidades_medidas.cod_unidad_medida";
            preparedStatementSelect = conexion.prepareStatement(sql);
            preparedStatementSelect.setInt(1, cod_deposito);
         } else {
            sql = "select\r\nproductos.cod_producto,productos.nombre_producto,unidades_medidas.sigla,productos.costo_producto,f_stock(productos.cod_producto,?) as stock\r\nfrom productos,unidades_medidas\r\nwhere\r\nproductos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nAND (EXISTS (SELECT * FROM productos_codigo_barras             WHERE productos_codigo_barras.cod_producto = productos.cod_producto             AND productos_codigo_barras.codigo_barra LIKE ?)      OR productos.cod_producto LIKE ?      OR productos.nombre_producto LIKE ?)";
            preparedStatementSelect = conexion.prepareStatement(sql);
            preparedStatementSelect.setInt(1, cod_deposito);
            preparedStatementSelect.setString(2, textoBuscado);
            preparedStatementSelect.setString(3, textoBuscado);
            preparedStatementSelect.setString(4, textoBuscado);
         }

         resultado = preparedStatementSelect.executeQuery();
         int fila = 0;
         if (resultado.next()) {
            do {
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               tabla.setValueAt(resultado.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
               tabla.setValueAt(resultado.getString("nombre_producto"), fila, tabla.getColumn("Nombre").getModelIndex());
               tabla.setValueAt(resultado.getString("sigla"), fila, tabla.getColumn("Sigla").getModelIndex());
               tabla.setValueAt(resultado.getString("costo_producto"), fila, tabla.getColumn("Costo").getModelIndex());
               tabla.setValueAt(resultado.getString("stock"), fila, tabla.getColumn("Stock").getModelIndex());
            } while (resultado.next());
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existen datos con el filtro establecido ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
         }
      } catch (Exception var16) {
         LogErrores.errores(var16, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void buscarBuscadorBasicosinCosto(String texto, JTable tabla, ModeloTablaDefecto modelo, JinternalPadre frame) {
      String sql = "";
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         String textoBuscado = "%" + texto + "%";
         if (!texto.trim().equals("")) {
            sql = "select\r\nproductos.cod_producto,productos.nombre_producto,unidades_medidas.sigla,productos.costo_producto\r\nfrom productos,unidades_medidas\r\nwhere\r\nproductos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nAND (EXISTS (SELECT * FROM productos_codigo_barras             WHERE productos_codigo_barras.cod_producto = productos.cod_producto             AND productos_codigo_barras.codigo_barra LIKE ?)      OR productos.cod_producto LIKE ?      OR productos.nombre_producto LIKE ?)";
            preparedStatementSelect = conexion.prepareStatement(sql);
            preparedStatementSelect.setString(1, textoBuscado);
            preparedStatementSelect.setString(2, textoBuscado);
            preparedStatementSelect.setString(3, textoBuscado);
         } else {
            sql = "select\r\nproductos.cod_producto,productos.nombre_producto,unidades_medidas.sigla,productos.costo_producto\r\nfrom productos,unidades_medidas\r\nwhere\r\nproductos.cod_unidad_medida = unidades_medidas.cod_unidad_medida";
            preparedStatementSelect = conexion.prepareStatement(sql);
         }

         System.out.println("SQL: " + sql);
         resultado = preparedStatementSelect.executeQuery();
         int fila = 0;
         if (resultado.next()) {
            do {
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               tabla.setValueAt(resultado.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
               tabla.setValueAt(resultado.getString("nombre_producto"), fila, tabla.getColumn("Nombre").getModelIndex());
               tabla.setValueAt(resultado.getString("sigla"), fila, tabla.getColumn("Sigla").getModelIndex());
            } while (resultado.next());
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existen datos con el filtro establecido ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
         }
      } catch (Exception var15) {
         LogErrores.errores(var15, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void buscarBuscadorBasicoconCosto(String sql, String texto, JTable tabla, ModeloTablaDefecto modelo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         if (!texto.trim().equals("")) {
            String patron = "%" + texto + "%";
            preparedStatementSelect.setString(1, patron);
            preparedStatementSelect.setString(2, patron);
            preparedStatementSelect.setString(3, patron);
         }

         resultado = preparedStatementSelect.executeQuery();
         int fila = 0;
         if (resultado.next()) {
            do {
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               tabla.setValueAt(resultado.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
               tabla.setValueAt(resultado.getString("nombre_producto"), fila, tabla.getColumn("Nombre").getModelIndex());
               tabla.setValueAt(resultado.getString("sigla"), fila, tabla.getColumn("Sigla").getModelIndex());
               tabla.setValueAt(resultado.getString("costo_producto"), fila, tabla.getColumn("Costo").getModelIndex());
            } while (resultado.next());
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existen datos con el filtro establecido ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
         }
      } catch (Exception var14) {
         LogErrores.errores(var14, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void buscarStockProducto(String cod_producto, JTable tabla_stock, ModeloTablaDefecto modelo_stock, JDialogBuscadores frame) {
      ModeloTablaDefecto.eliminarFilas(tabla_stock);
      String sql = "";
      sql = "select\r\ndepositos.nombre_deposito,stock_depositos.cantidad,stock_depositos.reservado,stock_depositos.transito\r\n from stock_depositos,depositos\r\nwhere\r\nstock_depositos.cod_deposito = depositos.cod_deposito\r\nand depositos.estado =1 and stock_depositos.cod_producto =?";
      cargarDepositoTablaStock(cod_producto, sql, modelo_stock, frame);
   }

   public static void buscarListaPrecios(String cod_producto, JTable tabla_precio, ModeloTablaDefecto modelo_precio, JDialogBuscadores frame) {
      ModeloTablaDefecto.eliminarFilas(tabla_precio);
      String sql = "";
      sql = "select\r\nlista_precios_cab.nombre_lista,monedas.nombre_moneda,lista_precios_det.precio,lista_precios_det.precio_minimo\r\n from lista_precios_cab,lista_precios_det,monedas\r\nwhere\r\nlista_precios_cab.cod_moneda = monedas.cod_moneda\r\nand lista_precios_cab.cod_lista = lista_precios_det.cod_lista\r\nand lista_precios_cab.estado =1\r\nand lista_precios_det.cod_producto =?";
      cargarPrecioProductoTabla(cod_producto, sql, modelo_precio, frame);
   }

   public static void buscarBuscadorBasicoSimple(String texto, JTable tabla, ModeloTablaDefecto modelo, JinternalPadre frame) {
      String sql = "";
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         String textoBuscado = "%" + texto + "%";
         if (!texto.trim().equals("")) {
            sql = "select\r\nproductos.cod_producto,productos.nombre_producto\r\nfrom productos\r\nwhere\r\n (EXISTS (SELECT * FROM productos_codigo_barras             WHERE productos_codigo_barras.cod_producto = productos.cod_producto             AND productos_codigo_barras.codigo_barra LIKE ?)      OR productos.cod_producto LIKE ?      OR productos.nombre_producto LIKE ?)";
            preparedStatementSelect = conexion.prepareStatement(sql);
            preparedStatementSelect.setString(1, textoBuscado);
            preparedStatementSelect.setString(2, textoBuscado);
            preparedStatementSelect.setString(3, textoBuscado);
         } else {
            sql = "select\r\nproductos.cod_producto,productos.nombre_producto\r\nfrom productos\r\n";
            preparedStatementSelect = conexion.prepareStatement(sql);
         }

         System.out.println("SQL: " + sql);
         resultado = preparedStatementSelect.executeQuery();
         int fila = 0;
         if (resultado.next()) {
            do {
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               tabla.setValueAt(resultado.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
               tabla.setValueAt(resultado.getString("nombre_producto"), fila, tabla.getColumn("Nombre").getModelIndex());
            } while (resultado.next());
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existen datos con el filtro establecido ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
         }
      } catch (Exception var15) {
         LogErrores.errores(var15, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarPrecioProductoTabla(String cod_producto, String sql, ModeloTablaDefecto modelo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(2);

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         preparedStatementSelect.setString(1, cod_producto);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(
               new Object[]{
                  resultado.getString(1),
                  resultado.getString(2),
                  formatoDecimalPrecio.format(resultado.getDouble(3)),
                  formatoDecimalPrecio.format(resultado.getDouble(4))
               }
            );
         }
      } catch (Exception var12) {
         LogErrores.errores(var12, "Error al seleccionar Lista de Precios..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Lista de Precios..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarDepositoTablaStock(String cod_producto, String sql, ModeloTablaDefecto modelo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(2);

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         preparedStatementSelect.setString(1, cod_producto);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(
               new Object[]{
                  resultado.getString(1),
                  formatoDecimalPrecio.format(resultado.getDouble(2)),
                  formatoDecimalPrecio.format(resultado.getDouble(3)),
                  formatoDecimalPrecio.format(resultado.getDouble(4))
               }
            );
         }
      } catch (Exception var12) {
         LogErrores.errores(var12, "Error al seleccionar Stock de Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Stock de Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }
}
