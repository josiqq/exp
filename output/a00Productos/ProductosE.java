package a00Productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import utilidades.LabelPrincipal;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadreReporte;
import utilidadesVentanas.JinternalPadreString;

public class ProductosE {
   private int cod_lista;
   private int stock_negativo;
   private double precio;
   private double stock;
   private String nombreListaPrecio;
   private String cod_producto;
   private String nombre_producto;
   private String nombreSeccion;
   private String nombreSubSeccion;
   private String nombreGrupo;
   private String nombreUnidadMedida;
   private String nombreMarca;
   private String nombreProveedor;
   private String nombreMoneda;
   private int cod_unidad_medida;
   private int cod_marca;
   private int cod_proveedor;
   private int estado;
   private int cod_moneda_costo;
   private int activo_venta;
   private int activo_compra;
   private int controla_stock;
   private int perecedero;
   private int produccion;
   private int calculo_costo;
   private int tipo_fiscal;
   private int cod_seccion;
   private int cod_sub_seccion;
   private int cod_grupo;
   private double costo_producto;
   private double iva_producto;
   private double porcentaje_gravado;
   private double porc_regimen;
   private double precio_minimo;
   private double precio_lista;

   public static int eliminarProductos(ProductosE entidad, JinternalPadreString frame) {
      PreparedStatement psEliminarProducto = null;
      PreparedStatement psEliminarProductoCodigoBarra = null;
      PreparedStatement psEliminarProductoListaPrecio = null;
      PreparedStatement psEliminarProductoStock = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarProductoCodigoBarra = conexion.prepareStatement("delete from productos_codigo_barras where cod_producto =?");
         psEliminarProductoCodigoBarra.setString(1, entidad.getCod_producto());
         psEliminarProductoListaPrecio = conexion.prepareStatement("delete from lista_precios_det where cod_producto =?");
         psEliminarProductoListaPrecio.setString(1, entidad.getCod_producto());
         psEliminarProductoStock = conexion.prepareStatement("delete from stock_depositos where cod_producto =?");
         psEliminarProductoStock.setString(1, entidad.getCod_producto());
         psEliminarProducto = conexion.prepareStatement("delete from productos where cod_producto =?");
         psEliminarProducto.setString(1, entidad.getCod_producto());
         psEliminarProductoCodigoBarra.executeUpdate();
         psEliminarProductoListaPrecio.executeUpdate();
         psEliminarProductoStock.executeUpdate();
         psEliminarProducto.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al eliminar Producto.", frame);
         }

         LogErrores.errores(var14, "Error al eliminar Producto..", frame);
      } finally {
         new CerrarRecursos(psEliminarProducto, frame, "Error al eliminar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarProductos(ProductosE entidad, JinternalPadreString frame) {
      PreparedStatement psActualizarProductos = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarProductos = conexion.prepareStatement(
            "update productos set nombre_producto=?,estado=?,cod_unidad_medida=?,cod_marca=?,cod_proveedor=?,activo_venta=?,activo_compra=?,controla_stock=?,perecedero=?,produccion=?,calculo_costo=?,\r\ntipo_fiscal=?,iva_producto=?,porcentaje_gravado=?,porc_regimen=?,cod_seccion=?,cod_sub_seccion=?,cod_grupo=?,stock_negativo =? where cod_producto =?"
         );
         psActualizarProductos.setString(1, entidad.getNombre_producto());
         psActualizarProductos.setInt(2, entidad.getEstado());
         psActualizarProductos.setInt(3, entidad.getCod_unidad_medida());
         psActualizarProductos.setInt(4, entidad.getCod_marca());
         psActualizarProductos.setInt(5, entidad.getCod_proveedor());
         psActualizarProductos.setInt(6, entidad.getActivo_venta());
         psActualizarProductos.setInt(7, entidad.getActivo_compra());
         psActualizarProductos.setInt(8, entidad.getControla_stock());
         psActualizarProductos.setInt(9, entidad.getPerecedero());
         psActualizarProductos.setInt(10, entidad.getProduccion());
         psActualizarProductos.setInt(11, entidad.getCalculo_costo());
         psActualizarProductos.setInt(12, entidad.getTipo_fiscal());
         psActualizarProductos.setDouble(13, entidad.getIva_producto());
         psActualizarProductos.setDouble(14, entidad.getPorcentaje_gravado());
         psActualizarProductos.setDouble(15, entidad.getPorc_regimen());
         psActualizarProductos.setInt(16, entidad.getCod_seccion());
         psActualizarProductos.setInt(17, entidad.getCod_sub_seccion());
         psActualizarProductos.setInt(18, entidad.getCod_grupo());
         psActualizarProductos.setInt(19, entidad.getStock_negativo());
         psActualizarProductos.setString(20, entidad.getCod_producto());
         psActualizarProductos.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Producto.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Producto..", frame);
      } finally {
         new CerrarRecursos(psActualizarProductos, frame, "Error al actualizar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarProductos(ProductosE entidad, JinternalPadreString frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarProducto = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select MAX(CAST(cod_producto AS UNSIGNED)) + 1 as mayor from productos");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarProducto = conexion.prepareStatement(
            "insert into productos (cod_producto,nombre_producto,estado,cod_unidad_medida,cod_marca,cod_proveedor,\r\nactivo_venta,activo_compra,controla_stock,perecedero,produccion,calculo_costo,\r\ntipo_fiscal,iva_producto,porcentaje_gravado,porc_regimen,cod_seccion,cod_sub_seccion,cod_grupo,stock_negativo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
         );
         psInsertarProducto.setInt(1, siguienteRegistro);
         psInsertarProducto.setString(2, entidad.getNombre_producto());
         psInsertarProducto.setInt(3, entidad.getEstado());
         psInsertarProducto.setInt(4, entidad.getCod_unidad_medida());
         psInsertarProducto.setInt(5, entidad.getCod_marca());
         psInsertarProducto.setInt(6, entidad.getCod_proveedor());
         psInsertarProducto.setInt(7, entidad.getActivo_venta());
         psInsertarProducto.setInt(8, entidad.getActivo_compra());
         psInsertarProducto.setInt(9, entidad.getControla_stock());
         psInsertarProducto.setInt(10, entidad.getPerecedero());
         psInsertarProducto.setInt(11, entidad.getProduccion());
         psInsertarProducto.setInt(12, entidad.getCalculo_costo());
         psInsertarProducto.setInt(13, entidad.getTipo_fiscal());
         psInsertarProducto.setDouble(14, entidad.getIva_producto());
         psInsertarProducto.setDouble(15, entidad.getPorcentaje_gravado());
         psInsertarProducto.setDouble(16, entidad.getPorc_regimen());
         psInsertarProducto.setInt(17, entidad.getCod_seccion());
         psInsertarProducto.setInt(18, entidad.getCod_sub_seccion());
         psInsertarProducto.setInt(19, entidad.getCod_grupo());
         psInsertarProducto.setInt(20, entidad.getStock_negativo());
         psInsertarProducto.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Producto.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Producto..", frame);
      } finally {
         new CerrarRecursos(psInsertarProducto, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static ProductosE buscarProducto(String codigo, JinternalPadreString frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\nproductos.cod_producto,productos.nombre_producto,productos.estado,productos.cod_unidad_medida,productos.cod_marca,\r\nproductos.cod_proveedor,productos.costo_producto,productos.cod_moneda_costo,productos.activo_venta,productos.activo_compra,\r\nproductos.controla_stock,productos.perecedero,productos.produccion,productos.calculo_costo,productos.tipo_fiscal,\r\nproductos.iva_producto,productos.porcentaje_gravado,productos.porc_regimen,productos.cod_seccion,productos.cod_sub_seccion,productos.cod_grupo,\r\nfamilia_seccion.nombre_seccion,familia_sub_seccion.nombre_sub_seccion,familia_grupo.nombre_grupo,productos.stock_negativo,\r\nunidades_medidas.nombre_unidad_medida,marcas.nombre_marca,proveedores.nombre_proveedor,monedas.nombre_moneda\r\n from productos,familia_seccion,familia_sub_seccion,familia_grupo,unidades_medidas,marcas,proveedores,monedas\r\nwhere\r\nproductos.cod_seccion = familia_seccion.cod_seccion\r\nand productos.cod_sub_seccion = familia_sub_seccion.cod_sub_seccion\r\nand productos.cod_grupo = familia_grupo.cod_grupo\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand productos.cod_marca = marcas.cod_marca\r\nand productos.cod_proveedor = proveedores.cod_proveedor\r\nand productos.cod_moneda_costo = monedas.cod_moneda and productos.cod_producto =?"
         );
         preparedStatementSelect.setString(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new ProductosE(
               resultado.getString("cod_producto"),
               resultado.getString("nombre_producto"),
               resultado.getInt("estado"),
               resultado.getInt("cod_unidad_medida"),
               resultado.getInt("cod_marca"),
               resultado.getInt("cod_proveedor"),
               resultado.getDouble("costo_producto"),
               resultado.getInt("cod_moneda_costo"),
               resultado.getInt("activo_venta"),
               resultado.getInt("activo_compra"),
               resultado.getInt("controla_stock"),
               resultado.getInt("perecedero"),
               resultado.getInt("produccion"),
               resultado.getInt("calculo_costo"),
               resultado.getInt("tipo_fiscal"),
               resultado.getDouble("iva_producto"),
               resultado.getDouble("porcentaje_gravado"),
               resultado.getDouble("porc_regimen"),
               resultado.getInt("cod_seccion"),
               resultado.getInt("cod_sub_seccion"),
               resultado.getInt("cod_grupo"),
               resultado.getString("nombre_seccion"),
               resultado.getString("nombre_sub_seccion"),
               resultado.getString("nombre_grupo"),
               resultado.getString("nombre_unidad_medida"),
               resultado.getString("nombre_marca"),
               resultado.getString("nombre_proveedor"),
               resultado.getString("nombre_moneda"),
               resultado.getInt("stock_negativo")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Producto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, "Error al seleccionar Producto..", frame);
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static void cargarProductosTabla2(String sql, ModeloTablaDefecto modelo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getString(1), resultado.getString(2)});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static ProductosE cargarProductosPedidosProv(String codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      ProductosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement(
            "select\r\nproductos.cod_producto,productos.nombre_producto,productos.tipo_fiscal,productos.iva_producto,productos.porcentaje_gravado,\r\n(select unidades_medidas.sigla from unidades_medidas where unidades_medidas.cod_unidad_medida =1) as siglaUnidadMedida\r\nfrom productos\r\nwhere\r\n((cod_producto =?) or (productos.cod_producto = (select productos_codigo_barras.cod_producto from productos_codigo_barras where\r\nproductos_codigo_barras.cod_producto = productos.cod_producto\r\nand productos_codigo_barras.codigo_barra =?))) limit 1"
         );
         preparedStatement.setString(1, codigo);
         preparedStatement.setString(2, codigo);
         resultado = preparedStatement.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Producto con codigo: ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         var8 = new ProductosE(
            resultado.getString("cod_producto"),
            resultado.getString("nombre_producto"),
            resultado.getInt("tipo_fiscal"),
            resultado.getDouble("iva_producto"),
            resultado.getDouble("porcentaje_gravado"),
            resultado.getString("siglaUnidadMedida")
         );
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Producto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, resultado, frame, "Error al seleccionar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static ProductosE cargarProductosAjusteStock(String codigo, int cod_deposito, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      ProductosE var9;
      try {
         System.out.println("COD DEPOSITO: " + cod_deposito);
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement(
            "select\r\nproductos.cod_producto,productos.nombre_producto,productos.costo_producto,\r\n(select unidades_medidas.sigla from unidades_medidas where unidades_medidas.cod_unidad_medida =productos.cod_unidad_medida) as siglaUnidadMedida,\r\nifnull((select cantidad from stock_depositos where stock_depositos.cod_producto = productos.cod_producto\r\nand stock_depositos.cod_deposito = ?),0) as stock\r\nfrom productos\r\nwhere\r\n( productos.estado = 1)\r\nand ((cod_producto =?) or (productos.cod_producto = (select productos_codigo_barras.cod_producto from productos_codigo_barras where\r\nproductos_codigo_barras.cod_producto = productos.cod_producto\r\nand productos_codigo_barras.codigo_barra =?))) limit 1"
         );
         preparedStatement.setInt(1, cod_deposito);
         preparedStatement.setString(2, codigo);
         preparedStatement.setString(3, codigo);
         resultado = preparedStatement.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Producto con codigo: ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         var9 = new ProductosE(
            resultado.getString("cod_producto"),
            resultado.getString("nombre_producto"),
            resultado.getDouble("costo_producto"),
            resultado.getString("siglaUnidadMedida"),
            resultado.getDouble("stock")
         );
      } catch (Exception var12) {
         LogErrores.errores(var12, "Error al seleccionar Producto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, resultado, frame, "Error al seleccionar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var9;
   }

   public static void cargarProductosTablaLista(String sql, int cod_lista, String texto, ModeloTablaDefecto modelo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         preparedStatementSelect.setInt(1, cod_lista);
         if (!texto.trim().equals("")) {
            String patron = "%" + texto + "%";
            preparedStatementSelect.setString(2, patron);
            preparedStatementSelect.setString(3, patron);
            preparedStatementSelect.setString(4, patron);
         }

         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(
               new Object[]{
                  resultado.getString(1),
                  resultado.getString(2),
                  resultado.getString(3),
                  resultado.getInt(4),
                  resultado.getString(5),
                  resultado.getDouble(6),
                  resultado.getDouble(7),
                  resultado.getDouble(8)
               }
            );
         }
      } catch (Exception var13) {
         LogErrores.errores(var13, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarProductosTablaAjustePrecio(
      String sql, DefaultTableModel modelo, int listaPrecio, JinternalPadre frame, LabelPrincipal lblLineasRecuperadas
   ) {
      ResultSet resultado = null;
      PreparedStatement preparedStatement = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement(
            "SELECT productos_descripcion.pro_nombre,(select unidades_medidas.und_sigla from unidades_medidas\r\n\t        \t\twhere unidades_medidas.und_codigo = productos.pro_unidad_medida) as unidad_medida,\r\n(select monedas.mon_nombre from monedas where monedas.mon_codigo = ?) as nomMoneda,productos_costos.pro_costo, ifnull((select lista_precios_det.lis_precio from lista_precios_det where lista_precios_det.lis_producto = productos.pro_codigo and lista_precios_det.lis_codigo =?),0) as precio FROM productos_descripcion,productos,productos_costos\r\nwhere productos.pro_codigo = productos_descripcion.pro_codigo and productos.pro_codigo = productos_costos.pro_codigo and productos.pro_codigo = ?"
         );
         preparedStatement.setInt(1, listaPrecio);
         preparedStatement.setInt(2, listaPrecio);
         resultado = preparedStatement.executeQuery();

         while (resultado.next()) {
            String v_codigo = resultado.getString("cod_producto");
            String v_descripcion = resultado.getString("nombre_producto");
            String v_unidad_medida = resultado.getString("sigla");
            String v_nomMoneda = resultado.getString("nombre_moneda");
            double v_costo = resultado.getDouble("costo_producto");
            double v_precio = resultado.getDouble("precio_lista");
            modelo.addRow(new Object[]{v_codigo, v_descripcion, v_unidad_medida, v_nomMoneda, v_costo, v_precio});
         }

         lblLineasRecuperadas.setText(String.valueOf(modelo.getRowCount()));
      } catch (Exception var20) {
         LogErrores.errores(var20, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatement, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static ProductosE cargarProductosComprasProv(String codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatement = null;

      ProductosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement(
            "select\r\nproductos.cod_producto,nombre_producto,tipo_fiscal,iva_producto,porcentaje_gravado,porc_regimen,unidades_medidas.sigla\r\nfrom productos,unidades_medidas\r\nwhere\r\n(productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida)\r\nand ((productos.cod_producto =?)or (productos.cod_producto = (select productos_codigo_barras.cod_producto from productos_codigo_barras where\r\nproductos_codigo_barras.cod_producto = productos.cod_producto\r\nand productos_codigo_barras.codigo_barra =?))) limit 1"
         );
         preparedStatement.setString(1, codigo);
         preparedStatement.setString(2, codigo);
         resultado = preparedStatement.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Producto con codigo: ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         var8 = new ProductosE(
            resultado.getString("cod_producto"),
            resultado.getString("nombre_producto"),
            resultado.getInt("tipo_fiscal"),
            resultado.getDouble("iva_producto"),
            resultado.getDouble("porcentaje_gravado"),
            resultado.getDouble("porc_regimen"),
            resultado.getString("sigla")
         );
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Producto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static ProductosE buscarProducto3(String codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      ProductosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_producto,nombre_producto,unidades_medidas.sigla from productos,unidades_medidas\r\nwhere (productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida) and ((cod_producto =?)\r\nor (cod_producto = (select productos_codigo_barras.cod_producto from productos_codigo_barras where\r\nproductos_codigo_barras.cod_producto = productos.cod_producto\r\nand productos_codigo_barras.codigo_barra =?)))"
         );
         preparedStatementSelect.setString(1, codigo);
         preparedStatementSelect.setString(2, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Producto con codigo: ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         ProductosE producto = new ProductosE(resultado.getString("cod_producto"), resultado.getString("nombre_producto"), resultado.getString("sigla"));
         var8 = producto;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Producto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static ProductosE buscarProducto2(String codigo, JinternalPadreReporte frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      ProductosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select nombre_producto from productos where cod_producto =?");
         preparedStatementSelect.setString(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Producto con codigo: ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         ProductosE producto = new ProductosE(codigo, resultado.getString("nombre_producto"));
         var8 = producto;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Producto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static ProductosE buscarProductoAjustePrecio(String codigo, int nroLista, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      ProductosE var9;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement(
            "select\r\nproductos.cod_producto,productos.nombre_producto,unidades_medidas.sigla,lista_precios_det.cod_moneda,monedas.nombre_moneda,\r\nproductos.costo_producto,lista_precios_det.precio,lista_precios_det.precio_minimo,\r\nlista_precios_cab.nombre_lista\r\nfrom\r\nproductos,unidades_medidas,lista_precios_det,monedas,lista_precios_cab\r\nwhere\r\n(productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida)\r\nand (productos.cod_producto = lista_precios_det.cod_producto)\r\nand (lista_precios_det.cod_lista = lista_precios_cab.cod_lista)\r\nand (lista_precios_det.cod_moneda = monedas.cod_moneda)\r\nand (lista_precios_det.cod_lista =?) and ((lista_precios_det.cod_producto =?)or (lista_precios_det.cod_producto = (select productos_codigo_barras.cod_producto from productos_codigo_barras where\r\nproductos_codigo_barras.cod_producto = productos.cod_producto\r\nand productos_codigo_barras.codigo_barra =?))) limit 1"
         );
         preparedStatement.setInt(1, nroLista);
         preparedStatement.setString(2, codigo);
         preparedStatement.setString(3, codigo);
         resultado = preparedStatement.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Producto con codigo: ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         ProductosE producto = new ProductosE(
            resultado.getString("cod_producto"),
            resultado.getString("nombre_producto"),
            resultado.getString("sigla"),
            resultado.getInt("cod_moneda"),
            resultado.getString("nombre_moneda"),
            resultado.getDouble("costo_producto"),
            resultado.getDouble("precio"),
            resultado.getDouble("precio_minimo"),
            resultado.getString("nombre_lista")
         );
         var9 = producto;
      } catch (Exception var12) {
         LogErrores.errores(var12, "Error al seleccionar Producto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, resultado, frame, "Error al seleccionar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var9;
   }

   public static ProductosE buscarProductos2(String codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      ProductosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("Select cod_producto,nombre_producto from productos where cod_producto =? and estado =1");
         preparedStatement.setString(1, codigo);
         resultado = preparedStatement.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Producto con codigo: ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         ProductosE producto = new ProductosE(codigo, resultado.getString("nombre_producto"));
         var8 = producto;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Producto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, resultado, frame, "Error al seleccionar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static ProductosE buscarProductos2(String codigo, JinternalPadreReporte frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      ProductosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("Select cod_producto,nombre_producto from productos where cod_producto =? and estado =1");
         preparedStatement.setString(1, codigo);
         resultado = preparedStatement.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Producto con codigo: ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         ProductosE producto = new ProductosE(codigo, resultado.getString("nombre_producto"));
         var8 = producto;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Producto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, resultado, frame, "Error al seleccionar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static ProductosE cargarProductosPedidosVta(String codigo, int cod_cliente, int cod_moneda, String fecha, JinternalPadre frame, int condicion) {
      ResultSet resultado = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("call proc_precio_vta(?,?,?);");
         preparedStatement.setInt(1, cod_cliente);
         preparedStatement.setInt(2, cod_moneda);
         preparedStatement.setString(3, codigo);
         resultado = preparedStatement.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Producto con codigo: ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         if (resultado.getDouble("precio_lista") != 0.0) {
            return new ProductosE(
               codigo,
               resultado.getString("nombre_producto"),
               resultado.getInt("tipo_fiscal"),
               resultado.getDouble("iva_producto"),
               resultado.getDouble("porcentaje_gravado"),
               resultado.getDouble("porc_regimen"),
               resultado.getString("sigla"),
               resultado.getInt("cod_lista"),
               resultado.getDouble("precio_lista"),
               resultado.getDouble("precio_cliente"),
               resultado.getDouble("precio_minimo"),
               resultado.getDouble("costo_producto")
            );
         }

         DialogResultadoOperacion rs = new DialogResultadoOperacion("Producto no tiene precio en esta Lista de Precio! ", "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
      } catch (Exception var15) {
         LogErrores.errores(var15, "Error al seleccionar Producto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, resultado, frame, "Error al seleccionar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static void buscarProductosTablaReporte(
      String cod_producto,
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
            "select\r\nproductos.cod_producto,productos.nombre_producto,\r\nproductos.tipo_fiscal,productos.iva_producto,\r\nmarcas.nombre_marca,unidades_medidas.nombre_unidad_medida,\r\nproveedores.nombre_proveedor\r\n from productos,marcas,unidades_medidas,proveedores\r\nwhere\r\nproductos.cod_marca =marcas.cod_marca\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand productos.cod_proveedor = proveedores.cod_proveedor\r\nand ((productos.cod_producto = ?) or (? =0))\r\nand ((productos.cod_marca = ?) or (? =0))\r\nand ((productos.cod_proveedor = ?) or (? =0))\r\nand ((productos.cod_seccion = ?) or (? =0))\r\nand ((productos.cod_sub_seccion = ?) or (? =0))\r\nand ((productos.cod_grupo = ?) or (? =0))"
         );
         preparedStatement2.setString(1, cod_producto);
         preparedStatement2.setString(2, cod_producto);
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
         if (tabla.getRowCount() - 1 == 0) {
            modelo.removeRow(0);
         }

         while (rs2.next()) {
            modelo.addRow(new Object[tabla.getColumnCount()]);
            fila = tabla.getRowCount() - 1;
            tabla.setValueAt(rs2.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
            tabla.setValueAt(rs2.getString("nombre_producto"), fila, tabla.getColumn("Nombre").getModelIndex());
            tabla.setValueAt(rs2.getDouble("tipo_fiscal"), fila, tabla.getColumn("Tipo Fiscal").getModelIndex());
            tabla.setValueAt(rs2.getDouble("iva_producto"), fila, tabla.getColumn("IVA").getModelIndex());
            tabla.setValueAt(rs2.getString("nombre_marca"), fila, tabla.getColumn("Marca").getModelIndex());
            tabla.setValueAt(rs2.getString("nombre_unidad_medida"), fila, tabla.getColumn("Unidad Medida").getModelIndex());
            tabla.setValueAt(rs2.getString("nombre_proveedor"), fila, tabla.getColumn("Proveedor").getModelIndex());
         }

         if (String.valueOf(tabla.getValueAt(fila, tabla.getColumn("Nombre").getModelIndex())).trim().equals("")
            || tabla.getValueAt(fila, tabla.getColumn("Nombre").getModelIndex()) == null) {
            modelo.removeRow(fila);
         }
      } catch (Exception var18) {
         LogErrores.errores(var18, "Error al Cargar Producto..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al Cargar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static ProductosE buscarProductosImpuestos(String codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatement = null;

      ProductosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement(
            "select\r\nnombre_producto,tipo_fiscal,iva_producto,porcentaje_gravado,porc_regimen,unidades_medidas.sigla\r\nfrom productos,unidades_medidas\r\nwhere\r\n(productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida) and ((productos.cod_producto = ?)or (select cod_producto from productos_codigo_barras where codigo_barra=?)) limit 1"
         );
         preparedStatement.setString(1, codigo);
         preparedStatement.setString(2, codigo);
         resultado = preparedStatement.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Producto con codigo: ", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         var8 = new ProductosE(
            codigo,
            resultado.getString("nombre_producto"),
            resultado.getInt("tipo_fiscal"),
            resultado.getDouble("iva_producto"),
            resultado.getDouble("porcentaje_gravado"),
            resultado.getDouble("porc_regimen"),
            resultado.getString("sigla")
         );
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Producto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public ProductosE(
      String cod_producto,
      String nombre_producto,
      int tipo_fiscal,
      double iva_producto,
      double porcentaje_gravado,
      double porc_regimen,
      String nombreUnidadMedida,
      int cod_lista,
      double precio_lista,
      double precio,
      double precio_minimo,
      double costo_producto
   ) {
      this.cod_producto = cod_producto;
      this.nombre_producto = nombre_producto;
      this.tipo_fiscal = tipo_fiscal;
      this.iva_producto = iva_producto;
      this.porcentaje_gravado = porcentaje_gravado;
      this.porc_regimen = porc_regimen;
      this.nombreUnidadMedida = nombreUnidadMedida;
      this.cod_lista = cod_lista;
      this.precio_lista = precio_lista;
      this.precio = precio;
      this.precio_minimo = precio_minimo;
      this.costo_producto = costo_producto;
   }

   public ProductosE(String nombre_producto, String nombreUnidadMedida, int cod_moneda, String nombreMoneda, double costo_producto, double precio) {
      this.nombre_producto = nombre_producto;
      this.nombreUnidadMedida = nombreUnidadMedida;
      this.cod_moneda_costo = cod_moneda;
      this.nombreMoneda = nombreMoneda;
      this.costo_producto = costo_producto;
      this.precio = precio;
   }

   public ProductosE(String cod_producto, String nombre_producto) {
      this.cod_producto = cod_producto;
      this.nombre_producto = nombre_producto;
   }

   public ProductosE(
      String cod_producto,
      String nombre_producto,
      String nombreUnidadMedida,
      int cod_moneda,
      String nombreMoneda,
      double costo_producto,
      double precio,
      double precio_minimo,
      String nombreListaPrecio
   ) {
      this.cod_producto = cod_producto;
      this.nombre_producto = nombre_producto;
      this.nombreUnidadMedida = nombreUnidadMedida;
      this.cod_moneda_costo = cod_moneda;
      this.nombreMoneda = nombreMoneda;
      this.costo_producto = costo_producto;
      this.precio = precio;
      this.precio_minimo = precio_minimo;
      this.nombreListaPrecio = nombreListaPrecio;
   }

   public ProductosE(String cod_producto, String nombre_producto, double costo_producto, String nombreUnidadMedida, double stock) {
      this.cod_producto = cod_producto;
      this.nombre_producto = nombre_producto;
      this.nombreUnidadMedida = nombreUnidadMedida;
      this.costo_producto = costo_producto;
      this.stock = stock;
   }

   public ProductosE(String cod_producto, String nombre_producto, String nombreUnidadMedida) {
      this.cod_producto = cod_producto;
      this.nombre_producto = nombre_producto;
      this.nombreUnidadMedida = nombreUnidadMedida;
   }

   public ProductosE(
      String cod_producto,
      String nombre_producto,
      int tipo_fiscal,
      double iva_producto,
      double porcentaje_gravado,
      double porc_regimen,
      String nombreUnidadMedida
   ) {
      this.cod_producto = cod_producto;
      this.nombre_producto = nombre_producto;
      this.tipo_fiscal = tipo_fiscal;
      this.iva_producto = iva_producto;
      this.porcentaje_gravado = porcentaje_gravado;
      this.porc_regimen = porc_regimen;
      this.nombreUnidadMedida = nombreUnidadMedida;
   }

   public ProductosE(String cod_producto, String nombre_producto, int tipo_fiscal, double iva_producto, double porcentaje_gravado, String nombreUnidadMedida) {
      this.cod_producto = cod_producto;
      this.nombre_producto = nombre_producto;
      this.tipo_fiscal = tipo_fiscal;
      this.iva_producto = iva_producto;
      this.porcentaje_gravado = porcentaje_gravado;
      this.nombreUnidadMedida = nombreUnidadMedida;
   }

   public ProductosE(
      String cod_producto,
      String nombre_producto,
      int estado,
      int cod_unidad_medida,
      int cod_marca,
      int cod_proveedor,
      int activo_venta,
      int activo_compra,
      int controla_stock,
      int perecedero,
      int produccion,
      int calculo_costo,
      int tipo_fiscal,
      double iva_producto,
      double porcentaje_gravado,
      double porc_regimen,
      int cod_seccion,
      int cod_sub_seccion,
      int cod_grupo,
      int stock_negativo
   ) {
      this.cod_producto = cod_producto;
      this.nombre_producto = nombre_producto;
      this.estado = estado;
      this.cod_unidad_medida = cod_unidad_medida;
      this.cod_marca = cod_marca;
      this.cod_proveedor = cod_proveedor;
      this.activo_venta = activo_venta;
      this.activo_compra = activo_compra;
      this.controla_stock = controla_stock;
      this.perecedero = perecedero;
      this.produccion = produccion;
      this.calculo_costo = calculo_costo;
      this.tipo_fiscal = tipo_fiscal;
      this.iva_producto = iva_producto;
      this.porcentaje_gravado = porcentaje_gravado;
      this.porc_regimen = porc_regimen;
      this.cod_seccion = cod_seccion;
      this.cod_sub_seccion = cod_sub_seccion;
      this.cod_grupo = cod_grupo;
      this.stock_negativo = stock_negativo;
   }

   public ProductosE(
      String cod_producto,
      String nombre_producto,
      int estado,
      int cod_unidad_medida,
      int cod_marca,
      int cod_proveedor,
      double costo_producto,
      int cod_moneda_costo,
      int activo_venta,
      int activo_compra,
      int controla_stock,
      int perecedero,
      int produccion,
      int calculo_costo,
      int tipo_fiscal,
      double iva_producto,
      double porcentaje_gravado,
      double porc_regimen,
      int cod_seccion,
      int cod_sub_seccion,
      int cod_grupo,
      String nombreSeccion,
      String nombreSubSeccion,
      String nombreGrupo,
      String nombreUnidadMedida,
      String nombreMarca,
      String nombreProveedor,
      String nombreMoneda,
      int stock_negativo
   ) {
      this.cod_producto = cod_producto;
      this.nombre_producto = nombre_producto;
      this.estado = estado;
      this.cod_unidad_medida = cod_unidad_medida;
      this.cod_marca = cod_marca;
      this.cod_proveedor = cod_proveedor;
      this.costo_producto = costo_producto;
      this.cod_moneda_costo = cod_moneda_costo;
      this.activo_venta = activo_venta;
      this.activo_compra = activo_compra;
      this.controla_stock = controla_stock;
      this.perecedero = perecedero;
      this.produccion = produccion;
      this.calculo_costo = calculo_costo;
      this.tipo_fiscal = tipo_fiscal;
      this.iva_producto = iva_producto;
      this.porcentaje_gravado = porcentaje_gravado;
      this.porc_regimen = porc_regimen;
      this.cod_seccion = cod_seccion;
      this.cod_sub_seccion = cod_sub_seccion;
      this.cod_grupo = cod_grupo;
      this.nombreSeccion = nombreSeccion;
      this.nombreSubSeccion = nombreSubSeccion;
      this.nombreGrupo = nombreGrupo;
      this.nombreUnidadMedida = nombreUnidadMedida;
      this.nombreMarca = nombreMarca;
      this.nombreProveedor = nombreProveedor;
      this.nombreMoneda = nombreMoneda;
      this.stock_negativo = stock_negativo;
   }

   public String getCod_producto() {
      return this.cod_producto;
   }

   public void setCod_producto(String cod_producto) {
      this.cod_producto = cod_producto;
   }

   public String getNombre_producto() {
      return this.nombre_producto;
   }

   public void setNombre_producto(String nombre_producto) {
      this.nombre_producto = nombre_producto;
   }

   public String getNombreSeccion() {
      return this.nombreSeccion;
   }

   public void setNombreSeccion(String nombreSeccion) {
      this.nombreSeccion = nombreSeccion;
   }

   public String getNombreSubSeccion() {
      return this.nombreSubSeccion;
   }

   public void setNombreSubSeccion(String nombreSubSeccion) {
      this.nombreSubSeccion = nombreSubSeccion;
   }

   public String getNombreGrupo() {
      return this.nombreGrupo;
   }

   public void setNombreGrupo(String nombreGrupo) {
      this.nombreGrupo = nombreGrupo;
   }

   public String getNombreUnidadMedida() {
      return this.nombreUnidadMedida;
   }

   public void setNombreUnidadMedida(String nombreUnidadMedida) {
      this.nombreUnidadMedida = nombreUnidadMedida;
   }

   public String getNombreMarca() {
      return this.nombreMarca;
   }

   public void setNombreMarca(String nombreMarca) {
      this.nombreMarca = nombreMarca;
   }

   public String getNombreProveedor() {
      return this.nombreProveedor;
   }

   public void setNombreProveedor(String nombreProveedor) {
      this.nombreProveedor = nombreProveedor;
   }

   public int getCod_unidad_medida() {
      return this.cod_unidad_medida;
   }

   public void setCod_unidad_medida(int cod_unidad_medida) {
      this.cod_unidad_medida = cod_unidad_medida;
   }

   public int getCod_marca() {
      return this.cod_marca;
   }

   public void setCod_marca(int cod_marca) {
      this.cod_marca = cod_marca;
   }

   public int getCod_proveedor() {
      return this.cod_proveedor;
   }

   public void setCod_proveedor(int cod_proveedor) {
      this.cod_proveedor = cod_proveedor;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public int getCod_moneda_costo() {
      return this.cod_moneda_costo;
   }

   public void setCod_moneda_costo(int cod_moneda_costo) {
      this.cod_moneda_costo = cod_moneda_costo;
   }

   public int getActivo_venta() {
      return this.activo_venta;
   }

   public void setActivo_venta(int activo_venta) {
      this.activo_venta = activo_venta;
   }

   public int getActivo_compra() {
      return this.activo_compra;
   }

   public void setActivo_compra(int activo_compra) {
      this.activo_compra = activo_compra;
   }

   public int getControla_stock() {
      return this.controla_stock;
   }

   public void setControla_stock(int controla_stock) {
      this.controla_stock = controla_stock;
   }

   public int getPerecedero() {
      return this.perecedero;
   }

   public void setPerecedero(int perecedero) {
      this.perecedero = perecedero;
   }

   public int getProduccion() {
      return this.produccion;
   }

   public void setProduccion(int produccion) {
      this.produccion = produccion;
   }

   public int getCalculo_costo() {
      return this.calculo_costo;
   }

   public void setCalculo_costo(int calculo_costo) {
      this.calculo_costo = calculo_costo;
   }

   public int getTipo_fiscal() {
      return this.tipo_fiscal;
   }

   public void setTipo_fiscal(int tipo_fiscal) {
      this.tipo_fiscal = tipo_fiscal;
   }

   public int getCod_seccion() {
      return this.cod_seccion;
   }

   public void setCod_seccion(int cod_seccion) {
      this.cod_seccion = cod_seccion;
   }

   public int getCod_sub_seccion() {
      return this.cod_sub_seccion;
   }

   public void setCod_sub_seccion(int cod_sub_seccion) {
      this.cod_sub_seccion = cod_sub_seccion;
   }

   public int getCod_grupo() {
      return this.cod_grupo;
   }

   public void setCod_grupo(int cod_grupo) {
      this.cod_grupo = cod_grupo;
   }

   public double getCosto_producto() {
      return this.costo_producto;
   }

   public void setCosto_producto(double costo_producto) {
      this.costo_producto = costo_producto;
   }

   public double getIva_producto() {
      return this.iva_producto;
   }

   public void setIva_producto(double iva_producto) {
      this.iva_producto = iva_producto;
   }

   public double getPorcentaje_gravado() {
      return this.porcentaje_gravado;
   }

   public void setPorcentaje_gravado(double porcentaje_gravado) {
      this.porcentaje_gravado = porcentaje_gravado;
   }

   public String getNombreMoneda() {
      return this.nombreMoneda;
   }

   public void setNombreMoneda(String nombreMoneda) {
      this.nombreMoneda = nombreMoneda;
   }

   public double getPorc_regimen() {
      return this.porc_regimen;
   }

   public void setPorc_regimen(double porc_regimen) {
      this.porc_regimen = porc_regimen;
   }

   public int getCod_lista() {
      return this.cod_lista;
   }

   public void setCod_lista(int cod_lista) {
      this.cod_lista = cod_lista;
   }

   public double getPrecio() {
      return this.precio;
   }

   public void setPrecio(double precio) {
      this.precio = precio;
   }

   public double getPrecio_minimo() {
      return this.precio_minimo;
   }

   public void setPrecio_minimo(double precio_minimo) {
      this.precio_minimo = precio_minimo;
   }

   public double getPrecio_lista() {
      return this.precio_lista;
   }

   public void setPrecio_lista(double precio_lista) {
      this.precio_lista = precio_lista;
   }

   public String getNombreListaPrecio() {
      return this.nombreListaPrecio;
   }

   public void setNombreListaPrecio(String nombreListaPrecio) {
      this.nombreListaPrecio = nombreListaPrecio;
   }

   public double getStock() {
      return this.stock;
   }

   public void setStock(double stock) {
      this.stock = stock;
   }

   public int getStock_negativo() {
      return this.stock_negativo;
   }

   public void setStock_negativo(int stock_negativo) {
      this.stock_negativo = stock_negativo;
   }
}
