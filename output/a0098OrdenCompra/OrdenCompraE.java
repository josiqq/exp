package a0098OrdenCompra;

import a00pedidosProveedores.ModeloTablaProveedores;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.F_OperacionesSistema;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTablaDetalle.TablaDetallePedidos;
import utilidadesVentanas.JinternalPadre;

public class OrdenCompraE {
   private int nro_registro;
   private int cod_comprador;
   private int cod_sucursal;
   private int cod_proveedor;
   private int cod_moneda;
   private int cod_tipo_doc;
   private int cod_plazo;
   private int nro_pedido;
   private int dias;
   private int impreso;
   private int estado;
   private String usuario_alta;
   private String fecha_alta;
   private String observacion;
   private String fecha;
   private double total;
   private String nombre_comprador;
   private String nombre_sucursal;
   private String nombre_proveedor;
   private String nombre_moneda;
   private String nombre_tipo_doc;
   private String nombre_plazo;

   public static int ultimoRegistro(JinternalPadre frame) {
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      int var7;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("Select max(nro_registro) as mayor from orden_compra");
         rs = preparedStatement.executeQuery();
         if (!rs.next()) {
            return 0;
         }

         int siguienteRegistro = rs.getInt("mayor");
         var7 = siguienteRegistro;
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Ultimo Registro..", frame);
         return 0;
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar Ultimo Registro..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var7;
   }

   public static double buscarDetallePedido(
      int registro,
      int cod_proveedor,
      int cod_moneda,
      int cod_sucursal,
      JinternalPadre frame,
      TablaDetallePedidos tabla,
      int par_decimal_cantidad,
      int par_decimal_importe
   ) {
      ResultSet rs1 = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalCantidad = new FormatoDecimalOperacionSistema(par_decimal_cantidad);
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      FormatoDecimalOperacionSistema formatoDecimal2 = new FormatoDecimalOperacionSistema(2);
      ModeloTablaProveedores modelo = (ModeloTablaProveedores)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      double var21;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\npedidos_proveedores_det.cod_producto,productos.nombre_producto,pedidos_proveedores_det.tipo_fiscal,\r\npedidos_proveedores_det.iva_producto,pedidos_proveedores_det.porc_gravado,unidades_medidas.sigla,\r\npedidos_proveedores_det.cantidad,pedidos_proveedores_det.cantidad_bonif,pedidos_proveedores_det.precio,\r\npedidos_proveedores_det.id\r\nfrom pedidos_proveedores_det,pedidos_proveedores,productos,unidades_medidas\r\nwhere\r\npedidos_proveedores_det.cod_producto = productos.cod_producto\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand pedidos_proveedores.nro_registro = pedidos_proveedores_det.nro_registro\r\nand pedidos_proveedores.cod_proveedor = ?\r\nand pedidos_proveedores.cod_moneda = ?\r\nand pedidos_proveedores.cod_sucursal = ? and pedidos_proveedores_det.nro_registro =?  and not exists\r\n(\r\nselect * from orden_compra where orden_compra.nro_pedido = pedidos_proveedores.nro_registro)"
         );
         preparedStatement2.setInt(1, cod_proveedor);
         preparedStatement2.setInt(2, cod_moneda);
         preparedStatement2.setInt(3, cod_sucursal);
         preparedStatement2.setInt(4, registro);
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            BigDecimal v_precio = new BigDecimal(rs2.getDouble("precio"));
            BigDecimal v_cantidad = new BigDecimal(rs2.getDouble("cantidad"));
            modelo.addRow(
               new Object[]{
                  rs2.getString("cod_producto"),
                  rs2.getString("nombre_producto"),
                  rs2.getInt("tipo_fiscal"),
                  formatoDecimal2.format(rs2.getDouble("iva_producto")),
                  formatoDecimal2.format(rs2.getDouble("porc_gravado")),
                  rs2.getString("sigla"),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad")),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad_bonif")),
                  formatoDecimalPrecio.format(rs2.getDouble("precio")),
                  formatoDecimalPrecio.format(F_OperacionesSistema.multiplicar(v_cantidad, v_precio)),
                  rs2.getInt("id")
               }
            );
         }

         if (modelo.getRowCount() <= 0) {
            return 0.0;
         }

         preparedStatement1 = conexion.prepareStatement(
            "select\r\npedidos_proveedores.total\r\nfrom\r\npedidos_proveedores\r\nwhere\r\npedidos_proveedores.nro_registro =?"
         );
         preparedStatement1.setInt(1, registro);
         rs1 = preparedStatement1.executeQuery();
         if (!rs1.next()) {
            return 0.0;
         }

         var21 = rs1.getDouble("total");
      } catch (Exception var25) {
         LogErrores.errores(var25, "Error al seleccionar Detalle..", frame);
         return 0.0;
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al seleccionar Detalle..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var21;
   }

   public static OrdenCompraE buscarOrdenCompra(
      int registro, JinternalPadre frame, TablaDetallePedidos tabla, int par_decimal_cantidad, int par_decimal_importe
   ) {
      ResultSet rs = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalCantidad = new FormatoDecimalOperacionSistema(par_decimal_cantidad);
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      FormatoDecimalOperacionSistema formatoDecimal2 = new FormatoDecimalOperacionSistema(2);
      ModeloTablaProveedores modelo = (ModeloTablaProveedores)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      OrdenCompraE var19;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement(
            "select\r\norden_compra.nro_registro,orden_compra.fecha,orden_compra.cod_comprador,\r\norden_compra.cod_sucursal,orden_compra.cod_proveedor,orden_compra.cod_moneda,\r\norden_compra.cod_tipo_doc,orden_compra.cod_plazo,orden_compra.nro_pedido,\r\norden_compra.dias,orden_compra.total,orden_compra.impreso,\r\norden_compra.estado,orden_compra.observacion,orden_compra.usuario_alta,orden_compra.fecha_alta,\r\ncompradores.nombre_comprador,sucursales.nombre_sucursal,proveedores.nombre_proveedor,\r\nmonedas.nombre_moneda,tipo_documentos.nombre_tipo_documento,plazos.nombre_plazo\r\nfrom\r\norden_compra,compradores,sucursales,proveedores,monedas,tipo_documentos,plazos\r\nwhere\r\norden_compra.cod_comprador = compradores.cod_comprador\r\nand orden_compra.cod_sucursal = sucursales.cod_sucursal\r\nand orden_compra.cod_proveedor = proveedores.cod_proveedor\r\nand orden_compra.cod_moneda = monedas.cod_moneda\r\nand orden_compra.cod_tipo_doc = tipo_documentos.cod_tipo_documento\r\nand orden_compra.cod_plazo = plazos.cod_plazo and orden_compra.nro_registro =?"
         );
         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (!rs.next()) {
            return null;
         }

         OrdenCompraE entidad = new OrdenCompraE(
            rs.getInt("nro_registro"),
            rs.getString("fecha"),
            rs.getInt("cod_comprador"),
            rs.getInt("cod_sucursal"),
            rs.getInt("cod_proveedor"),
            rs.getInt("cod_moneda"),
            rs.getInt("cod_tipo_doc"),
            rs.getInt("cod_plazo"),
            rs.getInt("nro_pedido"),
            rs.getInt("dias"),
            rs.getDouble("total"),
            rs.getInt("impreso"),
            rs.getInt("estado"),
            rs.getString("observacion"),
            rs.getString("nombre_comprador"),
            rs.getString("nombre_sucursal"),
            rs.getString("nombre_proveedor"),
            rs.getString("nombre_moneda"),
            rs.getString("nombre_tipo_documento"),
            rs.getString("nombre_plazo")
         );
         preparedStatement2 = conexion.prepareStatement(
            "select\r\npedidos_proveedores_det.cod_producto,productos.nombre_producto,pedidos_proveedores_det.tipo_fiscal,\r\npedidos_proveedores_det.iva_producto,pedidos_proveedores_det.porc_gravado,unidades_medidas.sigla,\r\npedidos_proveedores_det.cantidad,pedidos_proveedores_det.cantidad_bonif,pedidos_proveedores_det.precio,\r\npedidos_proveedores_det.id\r\n from pedidos_proveedores_det,productos,unidades_medidas\r\nwhere\r\npedidos_proveedores_det.cod_producto = productos.cod_producto\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida and pedidos_proveedores_det.nro_registro =?"
         );
         preparedStatement2.setInt(1, rs.getInt("nro_pedido"));
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            BigDecimal v_precio = new BigDecimal(rs2.getDouble("precio"));
            BigDecimal v_cantidad = new BigDecimal(rs2.getDouble("cantidad"));
            modelo.addRow(
               new Object[]{
                  rs2.getString("cod_producto"),
                  rs2.getString("nombre_producto"),
                  rs2.getInt("tipo_fiscal"),
                  formatoDecimal2.format(rs2.getDouble("iva_producto")),
                  formatoDecimal2.format(rs2.getDouble("porc_gravado")),
                  rs2.getString("sigla"),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad")),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad_bonif")),
                  formatoDecimalPrecio.format(rs2.getDouble("precio")),
                  formatoDecimalPrecio.format(F_OperacionesSistema.multiplicar(v_cantidad, v_precio)),
                  rs2.getInt("id")
               }
            );
         }

         modelo.addDefaultRow();
         var19 = entidad;
      } catch (Exception var22) {
         LogErrores.errores(var22, "Error al seleccionar Orden de Compra..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatement2, rs, rs2, frame, "Error al seleccionar Orden de Compra..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var19;
   }

   public static int eliminarOrdenCompra(OrdenCompraE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarOC = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarOC = conexion.prepareStatement("delete from orden_compra where nro_registro =?");
         psEliminarOC.setInt(1, entidad.getNro_registro());
         psEliminarOC.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Orden de Compra.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Orden de Compra..", frame);
      } finally {
         new CerrarRecursos(psEliminarOC, frame, "Error al eliminar Orden de Compra..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarOC(OrdenCompraE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarOC = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarOC = conexion.prepareStatement(
            "update orden_compra set fecha=?,cod_comprador=?,cod_sucursal=?,cod_proveedor=?,cod_moneda=?,cod_tipo_doc=?,cod_plazo=?,nro_pedido=?,\r\ndias=?,total=?,impreso=?,estado=?,observacion=? where nro_registro =?"
         );
         psActualizarOC.setString(1, entidad.getFecha());
         psActualizarOC.setInt(2, entidad.getCod_comprador());
         psActualizarOC.setInt(3, entidad.getCod_sucursal());
         psActualizarOC.setInt(4, entidad.getCod_proveedor());
         psActualizarOC.setInt(5, entidad.getCod_moneda());
         psActualizarOC.setInt(6, entidad.getCod_tipo_doc());
         psActualizarOC.setInt(6, entidad.getCod_plazo());
         psActualizarOC.setInt(6, entidad.getNro_pedido());
         psActualizarOC.setInt(6, entidad.getDias());
         psActualizarOC.setDouble(6, entidad.getTotal());
         psActualizarOC.setInt(6, entidad.getImpreso());
         psActualizarOC.setInt(6, entidad.getEstado());
         psActualizarOC.setString(6, entidad.getObservacion());
         psActualizarOC.setInt(6, entidad.getNro_registro());
         psActualizarOC.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Orden de Compra.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Orden de Compra..", frame);
      } finally {
         new CerrarRecursos(psActualizarOC, frame, "Error al actualizar Orden de Compra..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarOrdenCompra(OrdenCompraE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarOC = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(nro_registro) as mayor from orden_compra");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarOC = conexion.prepareStatement(
            "insert into orden_compra (nro_registro,fecha,cod_comprador,cod_sucursal,cod_proveedor,cod_moneda,cod_tipo_doc,cod_plazo,nro_pedido,\r\ndias,total,impreso,estado,observacion,usuario_alta,fecha_alta) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?, SUBSTRING_INDEX(USER(), '@', 1),now())"
         );
         psInsertarOC.setInt(1, siguienteRegistro);
         psInsertarOC.setString(2, entidad.getFecha());
         psInsertarOC.setInt(3, entidad.getCod_comprador());
         psInsertarOC.setInt(4, entidad.getCod_sucursal());
         psInsertarOC.setInt(5, entidad.getCod_proveedor());
         psInsertarOC.setInt(6, entidad.getCod_moneda());
         psInsertarOC.setInt(7, entidad.getCod_tipo_doc());
         psInsertarOC.setInt(8, entidad.getCod_plazo());
         psInsertarOC.setInt(9, entidad.getNro_pedido());
         psInsertarOC.setInt(10, entidad.getDias());
         psInsertarOC.setDouble(11, entidad.getTotal());
         psInsertarOC.setInt(12, entidad.getImpreso());
         psInsertarOC.setInt(13, entidad.getEstado());
         psInsertarOC.setString(14, entidad.getObservacion());
         psInsertarOC.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Orden de Compra.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Orden de Compra..", frame);
      } finally {
         new CerrarRecursos(psInsertarOC, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Orden de Compra..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public OrdenCompraE(int nro_pedido) {
      this.nro_pedido = nro_pedido;
   }

   public OrdenCompraE(
      int nro_registro,
      String fecha,
      int cod_comprador,
      int cod_sucursal,
      int cod_proveedor,
      int cod_moneda,
      int cod_tipo_doc,
      int cod_plazo,
      int nro_pedido,
      int dias,
      double total,
      int impreso,
      int estado,
      String observacion,
      String nombre_comprador,
      String nombre_sucursal,
      String nombre_proveedor,
      String nombre_moneda,
      String nombre_tipo_doc,
      String nombre_plazo
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_comprador = cod_comprador;
      this.cod_sucursal = cod_sucursal;
      this.cod_proveedor = cod_proveedor;
      this.cod_moneda = cod_moneda;
      this.cod_tipo_doc = cod_tipo_doc;
      this.cod_plazo = cod_plazo;
      this.nro_pedido = nro_pedido;
      this.dias = dias;
      this.total = total;
      this.impreso = impreso;
      this.estado = estado;
      this.observacion = observacion;
      this.nombre_comprador = nombre_comprador;
      this.nombre_sucursal = nombre_sucursal;
      this.nombre_proveedor = nombre_proveedor;
      this.nombre_moneda = nombre_moneda;
      this.nombre_tipo_doc = nombre_tipo_doc;
   }

   public OrdenCompraE(
      int nro_registro,
      String fecha,
      int cod_comprador,
      int cod_sucursal,
      int cod_proveedor,
      int cod_moneda,
      int cod_tipo_doc,
      int cod_plazo,
      int nro_pedido,
      int dias,
      double total,
      int impreso,
      int estado,
      String observacion
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_comprador = cod_comprador;
      this.cod_sucursal = cod_sucursal;
      this.cod_proveedor = cod_proveedor;
      this.cod_moneda = cod_moneda;
      this.cod_tipo_doc = cod_tipo_doc;
      this.cod_plazo = cod_plazo;
      this.nro_pedido = nro_pedido;
      this.dias = dias;
      this.total = total;
      this.impreso = impreso;
      this.estado = estado;
      this.observacion = observacion;
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

   public int getCod_sucursal() {
      return this.cod_sucursal;
   }

   public void setCod_sucursal(int cod_sucursal) {
      this.cod_sucursal = cod_sucursal;
   }

   public int getCod_proveedor() {
      return this.cod_proveedor;
   }

   public void setCod_proveedor(int cod_proveedor) {
      this.cod_proveedor = cod_proveedor;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public void setCod_moneda(int cod_moneda) {
      this.cod_moneda = cod_moneda;
   }

   public int getCod_tipo_doc() {
      return this.cod_tipo_doc;
   }

   public void setCod_tipo_doc(int cod_tipo_doc) {
      this.cod_tipo_doc = cod_tipo_doc;
   }

   public int getCod_plazo() {
      return this.cod_plazo;
   }

   public void setCod_plazo(int cod_plazo) {
      this.cod_plazo = cod_plazo;
   }

   public int getNro_pedido() {
      return this.nro_pedido;
   }

   public void setNro_pedido(int nro_pedido) {
      this.nro_pedido = nro_pedido;
   }

   public int getDias() {
      return this.dias;
   }

   public void setDias(int dias) {
      this.dias = dias;
   }

   public int getImpreso() {
      return this.impreso;
   }

   public void setImpreso(int impreso) {
      this.impreso = impreso;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getUsuario_alta() {
      return this.usuario_alta;
   }

   public void setUsuario_alta(String usuario_alta) {
      this.usuario_alta = usuario_alta;
   }

   public String getFecha_alta() {
      return this.fecha_alta;
   }

   public void setFecha_alta(String fecha_alta) {
      this.fecha_alta = fecha_alta;
   }

   public String getObservacion() {
      return this.observacion;
   }

   public void setObservacion(String observacion) {
      this.observacion = observacion;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
   }

   public double getTotal() {
      return this.total;
   }

   public void setTotal(double total) {
      this.total = total;
   }

   public String getNombre_comprador() {
      return this.nombre_comprador;
   }

   public void setNombre_comprador(String nombre_comprador) {
      this.nombre_comprador = nombre_comprador;
   }

   public String getNombre_sucursal() {
      return this.nombre_sucursal;
   }

   public void setNombre_sucursal(String nombre_sucursal) {
      this.nombre_sucursal = nombre_sucursal;
   }

   public String getNombre_proveedor() {
      return this.nombre_proveedor;
   }

   public void setNombre_proveedor(String nombre_proveedor) {
      this.nombre_proveedor = nombre_proveedor;
   }

   public String getNombre_moneda() {
      return this.nombre_moneda;
   }

   public void setNombre_moneda(String nombre_moneda) {
      this.nombre_moneda = nombre_moneda;
   }

   public String getNombre_tipo_doc() {
      return this.nombre_tipo_doc;
   }

   public void setNombre_tipo_doc(String nombre_tipo_doc) {
      this.nombre_tipo_doc = nombre_tipo_doc;
   }

   public String getNombre_plazo() {
      return this.nombre_plazo;
   }

   public void setNombre_plazo(String nombre_plazo) {
      this.nombre_plazo = nombre_plazo;
   }
}
