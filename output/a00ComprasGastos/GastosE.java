package a00ComprasGastos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class GastosE {
   private int nro_registro;
   private int cod_sucursal;
   private int cod_comprador;
   private int cod_moneda;
   private int cod_tipo_documento;
   private int timbrado;
   private int cod_plazo;
   private int cuotas;
   private int dias;
   private int cod_proveedor;
   private int cod_motivo;
   private String fecha_proceso;
   private String fecha_factura;
   private String vencimiento_timbrado;
   private String nro_factura;
   private String ruc_proveedor;
   private String nombre_sucursal;
   private String nombre_comprador;
   private String nombre_moneda;
   private String nombre_tipo_documento;
   private String nombre_plazo;
   private String nombre_proveedor;
   private String nombre_motivo;
   private double importe_exento;
   private double importe_gravado10;
   private double importe_gravado5;
   private double importe_iva10;
   private double importe_iva5;
   private double importe_total;

   public static int ultimoRegistro(JinternalPadre frame) {
      ResultSet rsMayor = null;
      Connection conexion = null;
      PreparedStatement preparedStatementMayor = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementMayor = conexion.prepareStatement("Select max(nro_registro) as mayor from gastos_proveedores");
         rsMayor = preparedStatementMayor.executeQuery();
         if (rsMayor.next()) {
            return rsMayor.getInt("mayor");
         }
      } catch (Exception var9) {
         LogErrores.errores(var9, "Error al seleccionar ultimo registro.", frame);
         return 0;
      } finally {
         new CerrarRecursos(preparedStatementMayor, rsMayor, frame, "Error al recuperar ultimo registro..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int eliminarGastoProveedor(GastosE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarGastoProveedor = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarGastoProveedor = conexion.prepareStatement("delete from gastos_proveedores where nro_registro =?");
         psEliminarGastoProveedor.setInt(1, entidad.getNro_registro());
         psEliminarGastoProveedor.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Gasto.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Gasto..", frame);
      } finally {
         new CerrarRecursos(psEliminarGastoProveedor, frame, "Error al eliminar Gasto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarGastoProveedor(GastosE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarGastoProveedor = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarGastoProveedor = conexion.prepareStatement(
            "update gastos_proveedores set fecha_proceso=?,fecha_factura=?,cod_sucursal=?,cod_comprador=?,cod_moneda=?,cod_tipo_documento=?,timbrado=?,nro_factura=?,\r\nvencimiento_timbrado=?,cod_plazo=?,cuotas=?,dias=?,cod_proveedor=?,cod_motivo=?,importe_exento=?,\r\nimporte_gravado10=?,importe_gravado5=?,importe_iva10=?,importe_iva5=?,importe_total=? where nro_registro=?"
         );
         psActualizarGastoProveedor.setString(1, entidad.getFecha_proceso());
         psActualizarGastoProveedor.setString(2, entidad.getFecha_factura());
         psActualizarGastoProveedor.setInt(3, entidad.getCod_sucursal());
         psActualizarGastoProveedor.setInt(4, entidad.getCod_comprador());
         psActualizarGastoProveedor.setInt(5, entidad.getCod_moneda());
         psActualizarGastoProveedor.setInt(6, entidad.getCod_tipo_documento());
         psActualizarGastoProveedor.setInt(7, entidad.getTimbrado());
         psActualizarGastoProveedor.setString(8, entidad.getNro_factura());
         psActualizarGastoProveedor.setString(9, entidad.getVencimiento_timbrado());
         psActualizarGastoProveedor.setInt(10, entidad.getCod_plazo());
         psActualizarGastoProveedor.setInt(11, entidad.getCuotas());
         psActualizarGastoProveedor.setInt(12, entidad.getDias());
         psActualizarGastoProveedor.setInt(13, entidad.getCod_proveedor());
         psActualizarGastoProveedor.setDouble(14, entidad.getCod_motivo());
         psActualizarGastoProveedor.setDouble(15, entidad.getImporte_exento());
         psActualizarGastoProveedor.setDouble(16, entidad.getImporte_gravado10());
         psActualizarGastoProveedor.setDouble(17, entidad.getImporte_gravado5());
         psActualizarGastoProveedor.setDouble(18, entidad.getImporte_iva10());
         psActualizarGastoProveedor.setDouble(19, entidad.getImporte_iva5());
         psActualizarGastoProveedor.setDouble(20, entidad.getImporte_total());
         psActualizarGastoProveedor.setInt(21, entidad.getNro_registro());
         psActualizarGastoProveedor.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Gasto.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Gasto..", frame);
      } finally {
         new CerrarRecursos(psActualizarGastoProveedor, frame, "Error al actualizar Gasto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarGasto(GastosE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarCajaChica = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(nro_registro) as mayor from gastos_proveedores");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarCajaChica = conexion.prepareStatement(
            "insert into gastos_proveedores (nro_registro,fecha_proceso,fecha_factura,cod_sucursal,cod_comprador,cod_moneda,cod_tipo_documento,timbrado,nro_factura,\r\nvencimiento_timbrado,cod_plazo,cuotas,dias,cod_proveedor,cod_motivo,importe_exento,\r\nimporte_gravado10,importe_gravado5,importe_iva10,importe_iva5,importe_total)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
         );
         psInsertarCajaChica.setInt(1, siguienteRegistro);
         psInsertarCajaChica.setString(2, entidad.getFecha_proceso());
         psInsertarCajaChica.setString(3, entidad.getFecha_factura());
         psInsertarCajaChica.setInt(4, entidad.getCod_sucursal());
         psInsertarCajaChica.setInt(5, entidad.getCod_comprador());
         psInsertarCajaChica.setInt(6, entidad.getCod_moneda());
         psInsertarCajaChica.setInt(7, entidad.getCod_tipo_documento());
         psInsertarCajaChica.setInt(8, entidad.getTimbrado());
         psInsertarCajaChica.setString(9, entidad.getNro_factura());
         psInsertarCajaChica.setString(10, entidad.getVencimiento_timbrado());
         psInsertarCajaChica.setInt(11, entidad.getCod_plazo());
         psInsertarCajaChica.setInt(12, entidad.getCuotas());
         psInsertarCajaChica.setInt(13, entidad.getDias());
         psInsertarCajaChica.setInt(14, entidad.getCod_proveedor());
         psInsertarCajaChica.setInt(15, entidad.getCod_motivo());
         psInsertarCajaChica.setDouble(16, entidad.getImporte_exento());
         psInsertarCajaChica.setDouble(17, entidad.getImporte_gravado10());
         psInsertarCajaChica.setDouble(18, entidad.getImporte_gravado5());
         psInsertarCajaChica.setDouble(19, entidad.getImporte_iva10());
         psInsertarCajaChica.setDouble(20, entidad.getImporte_iva5());
         psInsertarCajaChica.setDouble(21, entidad.getImporte_total());
         System.out.println("POLLO GEI: " + entidad.getImporte_iva10());
         psInsertarCajaChica.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Gasto.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Gasto..", frame);
      } finally {
         new CerrarRecursos(psInsertarCajaChica, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Gasto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static GastosE buscarGasto(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\ngastos_proveedores.nro_registro,gastos_proveedores.fecha_proceso,gastos_proveedores.fecha_factura,\r\ngastos_proveedores.cod_sucursal,gastos_proveedores.cod_comprador,gastos_proveedores.cod_moneda,\r\ngastos_proveedores.cod_tipo_documento,gastos_proveedores.timbrado,gastos_proveedores.nro_factura,\r\ngastos_proveedores.vencimiento_timbrado,gastos_proveedores.cod_plazo,gastos_proveedores.cuotas,gastos_proveedores.dias,\r\ngastos_proveedores.cod_proveedor,gastos_proveedores.cod_motivo,gastos_proveedores.importe_exento,\r\ngastos_proveedores.importe_gravado10,gastos_proveedores.importe_gravado5,gastos_proveedores.importe_iva10,\r\ngastos_proveedores.importe_iva5,gastos_proveedores.importe_total,\r\nsucursales.nombre_sucursal,compradores.nombre_comprador,monedas.nombre_moneda,tipo_documentos.nombre_tipo_documento,\r\nplazos.nombre_plazo,proveedores.nombre_proveedor,motivos.nombre_motivo,proveedores.ruc as ruc_proveedor\r\n from gastos_proveedores,sucursales,compradores,monedas,tipo_documentos,plazos,proveedores,motivos\r\nwhere\r\ngastos_proveedores.cod_sucursal = sucursales.cod_sucursal\r\nand gastos_proveedores.cod_comprador = compradores.cod_comprador\r\nand gastos_proveedores.cod_moneda = monedas.cod_moneda\r\nand gastos_proveedores.cod_tipo_documento = tipo_documentos.cod_tipo_documento\r\nand gastos_proveedores.cod_plazo = plazos.cod_plazo\r\nand gastos_proveedores.cod_proveedor = proveedores.cod_proveedor\r\nand gastos_proveedores.cod_motivo = motivos.cod_motivo and gastos_proveedores.nro_registro =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new GastosE(
               resultado.getInt("nro_registro"),
               resultado.getString("fecha_proceso"),
               resultado.getString("fecha_factura"),
               resultado.getInt("cod_sucursal"),
               resultado.getInt("cod_comprador"),
               resultado.getInt("cod_moneda"),
               resultado.getInt("cod_tipo_documento"),
               resultado.getInt("timbrado"),
               resultado.getString("nro_factura"),
               resultado.getString("vencimiento_timbrado"),
               resultado.getInt("cod_plazo"),
               resultado.getInt("cuotas"),
               resultado.getInt("dias"),
               resultado.getInt("cod_proveedor"),
               resultado.getInt("cod_motivo"),
               resultado.getDouble("importe_exento"),
               resultado.getDouble("importe_gravado10"),
               resultado.getDouble("importe_gravado5"),
               resultado.getDouble("importe_iva10"),
               resultado.getDouble("importe_iva5"),
               resultado.getDouble("importe_total"),
               resultado.getString("nombre_sucursal"),
               resultado.getString("nombre_comprador"),
               resultado.getString("nombre_moneda"),
               resultado.getString("nombre_tipo_documento"),
               resultado.getString("nombre_plazo"),
               resultado.getString("nombre_proveedor"),
               resultado.getString("nombre_motivo"),
               resultado.getString("ruc_proveedor")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Gasto..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Gasto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public GastosE(
      int nro_registro,
      String fecha_proceso,
      String fecha_factura,
      int cod_sucursal,
      int cod_comprador,
      int cod_moneda,
      int cod_tipo_documento,
      int timbrado,
      String nro_factura,
      String vencimiento_timbrado,
      int cod_plazo,
      int cuotas,
      int dias,
      int cod_proveedor,
      int cod_motivo,
      double importe_exento,
      double importe_gravado10,
      double importe_gravado5,
      double importe_iva10,
      double importe_iva5,
      double importe_total,
      String nombre_sucursal,
      String nombre_comprador,
      String nombre_moneda,
      String nombre_tipo_documento,
      String nombre_plazo,
      String nombre_proveedor,
      String nombre_motivo,
      String ruc_proveedor
   ) {
      this.nro_registro = nro_registro;
      this.fecha_proceso = fecha_proceso;
      this.fecha_factura = fecha_factura;
      this.cod_sucursal = cod_sucursal;
      this.cod_comprador = cod_comprador;
      this.cod_moneda = cod_moneda;
      this.cod_tipo_documento = cod_tipo_documento;
      this.timbrado = timbrado;
      this.nro_factura = nro_factura;
      this.vencimiento_timbrado = vencimiento_timbrado;
      this.cod_plazo = cod_plazo;
      this.cuotas = cuotas;
      this.dias = dias;
      this.cod_proveedor = cod_proveedor;
      this.cod_motivo = cod_motivo;
      this.importe_exento = importe_exento;
      this.importe_gravado10 = importe_gravado10;
      this.importe_gravado5 = importe_gravado5;
      this.importe_iva10 = importe_iva10;
      this.importe_iva5 = importe_iva5;
      this.importe_total = importe_total;
      this.nombre_sucursal = nombre_sucursal;
      this.nombre_comprador = nombre_comprador;
      this.nombre_moneda = nombre_moneda;
      this.nombre_tipo_documento = nombre_tipo_documento;
      this.nombre_plazo = nombre_plazo;
      this.nombre_proveedor = nombre_proveedor;
      this.nombre_motivo = nombre_motivo;
      this.ruc_proveedor = ruc_proveedor;
   }

   public GastosE(
      int nro_registro,
      String fecha_proceso,
      String fecha_factura,
      int cod_sucursal,
      int cod_comprador,
      int cod_moneda,
      int cod_tipo_documento,
      int timbrado,
      String nro_factura,
      String vencimiento_timbrado,
      int cod_plazo,
      int cuotas,
      int dias,
      int cod_proveedor,
      int cod_motivo,
      double importe_exento,
      double importe_gravado10,
      double importe_gravado5,
      double importe_iva10,
      double importe_iva5,
      double importe_total
   ) {
      this.nro_registro = nro_registro;
      this.fecha_proceso = fecha_proceso;
      this.fecha_factura = fecha_factura;
      this.cod_sucursal = cod_sucursal;
      this.cod_comprador = cod_comprador;
      this.cod_moneda = cod_moneda;
      this.cod_tipo_documento = cod_tipo_documento;
      this.timbrado = timbrado;
      this.nro_factura = nro_factura;
      this.vencimiento_timbrado = vencimiento_timbrado;
      this.cod_plazo = cod_plazo;
      this.cuotas = cuotas;
      this.dias = dias;
      this.cod_proveedor = cod_proveedor;
      this.cod_motivo = cod_motivo;
      this.importe_exento = importe_exento;
      this.importe_gravado10 = importe_gravado10;
      this.importe_gravado5 = importe_gravado5;
      this.importe_iva10 = importe_iva10;
      this.importe_iva5 = importe_iva5;
      this.importe_total = importe_total;
   }

   public int getNro_registro() {
      return this.nro_registro;
   }

   public void setNro_registro(int nro_registro) {
      this.nro_registro = nro_registro;
   }

   public int getCod_sucursal() {
      return this.cod_sucursal;
   }

   public void setCod_sucursal(int cod_sucursal) {
      this.cod_sucursal = cod_sucursal;
   }

   public int getCod_comprador() {
      return this.cod_comprador;
   }

   public void setCod_comprador(int cod_comprador) {
      this.cod_comprador = cod_comprador;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public void setCod_moneda(int cod_moneda) {
      this.cod_moneda = cod_moneda;
   }

   public int getCod_tipo_documento() {
      return this.cod_tipo_documento;
   }

   public void setCod_tipo_documento(int cod_tipo_documento) {
      this.cod_tipo_documento = cod_tipo_documento;
   }

   public int getTimbrado() {
      return this.timbrado;
   }

   public void setTimbrado(int timbrado) {
      this.timbrado = timbrado;
   }

   public int getCod_plazo() {
      return this.cod_plazo;
   }

   public void setCod_plazo(int cod_plazo) {
      this.cod_plazo = cod_plazo;
   }

   public int getCuotas() {
      return this.cuotas;
   }

   public void setCuotas(int cuotas) {
      this.cuotas = cuotas;
   }

   public int getDias() {
      return this.dias;
   }

   public void setDias(int dias) {
      this.dias = dias;
   }

   public int getCod_proveedor() {
      return this.cod_proveedor;
   }

   public void setCod_proveedor(int cod_proveedor) {
      this.cod_proveedor = cod_proveedor;
   }

   public int getCod_motivo() {
      return this.cod_motivo;
   }

   public void setCod_motivo(int cod_motivo) {
      this.cod_motivo = cod_motivo;
   }

   public String getFecha_proceso() {
      return this.fecha_proceso;
   }

   public void setFecha_proceso(String fecha_proceso) {
      this.fecha_proceso = fecha_proceso;
   }

   public String getFecha_factura() {
      return this.fecha_factura;
   }

   public void setFecha_factura(String fecha_factura) {
      this.fecha_factura = fecha_factura;
   }

   public String getVencimiento_timbrado() {
      return this.vencimiento_timbrado;
   }

   public void setVencimiento_timbrado(String vencimiento_timbrado) {
      this.vencimiento_timbrado = vencimiento_timbrado;
   }

   public String getNro_factura() {
      return this.nro_factura;
   }

   public void setNro_factura(String nro_factura) {
      this.nro_factura = nro_factura;
   }

   public String getNombre_sucursal() {
      return this.nombre_sucursal;
   }

   public void setNombre_sucursal(String nombre_sucursal) {
      this.nombre_sucursal = nombre_sucursal;
   }

   public String getNombre_comprador() {
      return this.nombre_comprador;
   }

   public void setNombre_comprador(String nombre_comprador) {
      this.nombre_comprador = nombre_comprador;
   }

   public String getNombre_moneda() {
      return this.nombre_moneda;
   }

   public void setNombre_moneda(String nombre_moneda) {
      this.nombre_moneda = nombre_moneda;
   }

   public String getNombre_tipo_documento() {
      return this.nombre_tipo_documento;
   }

   public void setNombre_tipo_documento(String nombre_tipo_documento) {
      this.nombre_tipo_documento = nombre_tipo_documento;
   }

   public String getNombre_plazo() {
      return this.nombre_plazo;
   }

   public void setNombre_plazo(String nombre_plazo) {
      this.nombre_plazo = nombre_plazo;
   }

   public String getNombre_proveedor() {
      return this.nombre_proveedor;
   }

   public void setNombre_proveedor(String nombre_proveedor) {
      this.nombre_proveedor = nombre_proveedor;
   }

   public String getNombre_motivo() {
      return this.nombre_motivo;
   }

   public void setNombre_motivo(String nombre_motivo) {
      this.nombre_motivo = nombre_motivo;
   }

   public double getImporte_exento() {
      return this.importe_exento;
   }

   public void setImporte_exento(double importe_exento) {
      this.importe_exento = importe_exento;
   }

   public double getImporte_gravado10() {
      return this.importe_gravado10;
   }

   public void setImporte_gravado10(double importe_gravado10) {
      this.importe_gravado10 = importe_gravado10;
   }

   public double getImporte_gravado5() {
      return this.importe_gravado5;
   }

   public void setImporte_gravado5(double importe_gravado5) {
      this.importe_gravado5 = importe_gravado5;
   }

   public double getImporte_iva10() {
      return this.importe_iva10;
   }

   public void setImporte_iva10(double importe_iva10) {
      this.importe_iva10 = importe_iva10;
   }

   public double getImporte_iva5() {
      return this.importe_iva5;
   }

   public void setImporte_iva5(double importe_iva5) {
      this.importe_iva5 = importe_iva5;
   }

   public double getImporte_total() {
      return this.importe_total;
   }

   public void setImporte_total(double importe_total) {
      this.importe_total = importe_total;
   }

   public String getRuc_proveedor() {
      return this.ruc_proveedor;
   }

   public void setRuc_proveedor(String ruc_proveedor) {
      this.ruc_proveedor = ruc_proveedor;
   }
}
