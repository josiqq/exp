package a99CajaChica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class CajaChicaE {
   private int nro_registro;
   private int cod_proveedor;
   private int cod_tipo_documento;
   private int timbrado;
   private int cod_cajero;
   private int cod_cuenta;
   private int cod_motivo;
   private int nro_planilla;
   private int cod_moneda;
   private String fecha;
   private String nombre_proveedor;
   private String ruc_proveedor;
   private String telefono_proveedor;
   private String nro_factura;
   private String direccion_proveedor;
   private String fecha_timbrado;
   private String observacion;
   private String nombre_tipo_documento;
   private String nombre_cajero;
   private String nombre_cuenta;
   private String nombre_motivo;
   private String nombre_moneda;
   private double importe_exento;
   private double importe_gravado10;
   private double importe_gravado5;
   private double importe_iva10;
   private double importe_iva5;
   private double importe_total;

   public static int eliminarCajaChica(CajaChicaE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarCajaChica = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarCajaChica = conexion.prepareStatement("delete from caja_chica where nro_registro =?");
         psEliminarCajaChica.setInt(1, entidad.getNro_registro());
         psEliminarCajaChica.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Caja Chica.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Caja Chica..", frame);
      } finally {
         new CerrarRecursos(psEliminarCajaChica, frame, "Error al eliminar Caja Chica..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarCajaChica(CajaChicaE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarCajaChica = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarCajaChica = conexion.prepareStatement(
            "update caja_chica set fecha=?,cod_proveedor=?,nombre_proveedor=?,ruc_proveedor=?,telefono_proveedor=?,direccion_proveedor=?,\r\ncod_tipo_documento=?,timbrado=?,fecha_timbrado=?,nro_factura=?,cod_motivo,cod_moneda=?,\r\nobservacion=?,importe_exento=?,importe_gravado10=?,importe_gravado5=?,importe_iva10=?,importe_iva5=?,importe_total=? where nro_registro =?"
         );
         psActualizarCajaChica.setString(1, entidad.getFecha());
         psActualizarCajaChica.setInt(2, entidad.getCod_proveedor());
         psActualizarCajaChica.setString(3, entidad.getNombre_proveedor());
         psActualizarCajaChica.setString(4, entidad.getRuc_proveedor());
         psActualizarCajaChica.setString(5, entidad.getTelefono_proveedor());
         psActualizarCajaChica.setString(6, entidad.getDireccion_proveedor());
         psActualizarCajaChica.setInt(7, entidad.getCod_tipo_documento());
         psActualizarCajaChica.setInt(8, entidad.getTimbrado());
         psActualizarCajaChica.setString(9, entidad.getFecha_timbrado());
         psActualizarCajaChica.setString(10, entidad.getNro_factura());
         psActualizarCajaChica.setInt(11, entidad.getCod_motivo());
         psActualizarCajaChica.setInt(12, entidad.getCod_moneda());
         psActualizarCajaChica.setString(13, entidad.getObservacion());
         psActualizarCajaChica.setDouble(14, entidad.getImporte_exento());
         psActualizarCajaChica.setDouble(15, entidad.getImporte_gravado10());
         psActualizarCajaChica.setDouble(16, entidad.getImporte_gravado5());
         psActualizarCajaChica.setDouble(17, entidad.getImporte_iva10());
         psActualizarCajaChica.setDouble(18, entidad.getImporte_iva5());
         psActualizarCajaChica.setDouble(19, entidad.getImporte_total());
         psActualizarCajaChica.setInt(20, entidad.getNro_registro());
         psActualizarCajaChica.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Caja Chica.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Caja Chica..", frame);
      } finally {
         new CerrarRecursos(psActualizarCajaChica, frame, "Error al actualizar Caja Chica..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarCajaChica(CajaChicaE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarCajaChica = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(nro_registro) as mayor from caja_chica");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarCajaChica = conexion.prepareStatement(
            "insert into caja_chica (nro_registro,fecha,cod_proveedor,nombre_proveedor,ruc_proveedor,telefono_proveedor,direccion_proveedor,\r\ncod_tipo_documento,timbrado,fecha_timbrado,nro_factura,\r\ncod_cajero,cod_cuenta,cod_motivo,nro_planilla,cod_moneda,\r\nobservacion,importe_exento,importe_gravado10,importe_gravado5,importe_iva10,importe_iva5,importe_total) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
         );
         psInsertarCajaChica.setInt(1, siguienteRegistro);
         psInsertarCajaChica.setString(2, entidad.getFecha());
         psInsertarCajaChica.setInt(3, entidad.getCod_proveedor());
         psInsertarCajaChica.setString(4, entidad.getNombre_proveedor());
         psInsertarCajaChica.setString(5, entidad.getRuc_proveedor());
         psInsertarCajaChica.setString(6, entidad.getTelefono_proveedor());
         psInsertarCajaChica.setString(7, entidad.getDireccion_proveedor());
         psInsertarCajaChica.setInt(8, entidad.getCod_tipo_documento());
         psInsertarCajaChica.setInt(9, entidad.getTimbrado());
         psInsertarCajaChica.setString(10, entidad.getFecha_timbrado());
         psInsertarCajaChica.setString(11, entidad.getNro_factura());
         psInsertarCajaChica.setInt(12, entidad.getCod_cajero());
         psInsertarCajaChica.setInt(13, entidad.getCod_cuenta());
         psInsertarCajaChica.setInt(14, entidad.getCod_motivo());
         psInsertarCajaChica.setInt(15, entidad.getNro_planilla());
         psInsertarCajaChica.setInt(16, entidad.getCod_moneda());
         psInsertarCajaChica.setString(17, entidad.getObservacion());
         psInsertarCajaChica.setDouble(18, entidad.getImporte_exento());
         psInsertarCajaChica.setDouble(19, entidad.getImporte_gravado10());
         psInsertarCajaChica.setDouble(20, entidad.getImporte_gravado5());
         psInsertarCajaChica.setDouble(21, entidad.getImporte_iva10());
         psInsertarCajaChica.setDouble(22, entidad.getImporte_iva5());
         psInsertarCajaChica.setDouble(23, entidad.getImporte_total());
         psInsertarCajaChica.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Caja Chica.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Caja Chica..", frame);
      } finally {
         new CerrarRecursos(psInsertarCajaChica, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Caja Chica..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static CajaChicaE buscarCajaChica(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\ncaja_chica.nro_registro,caja_chica.fecha,caja_chica.cod_proveedor,\r\ncaja_chica.nombre_proveedor,caja_chica.ruc_proveedor,caja_chica.telefono_proveedor,caja_chica.direccion_proveedor,\r\ncaja_chica.cod_tipo_documento,caja_chica.timbrado,caja_chica.fecha_timbrado,caja_chica.nro_factura,\r\ncaja_chica.cod_cajero,caja_chica.cod_cuenta,caja_chica.cod_motivo,caja_chica.nro_planilla,caja_chica.cod_moneda,\r\ncaja_chica.observacion,caja_chica.importe_exento,caja_chica.importe_gravado10,\r\ncaja_chica.importe_gravado5,caja_chica.importe_iva10,caja_chica.importe_iva5,caja_chica.importe_total,\r\ntipo_documentos.nombre_tipo_documento,cajeros.nombre_cajero,cuentas.nombre_cuenta,motivos.nombre_motivo,\r\nmonedas.nombre_moneda\r\n from caja_chica,tipo_documentos,cajeros,cuentas,motivos,monedas\r\nwhere\r\ncaja_chica.cod_tipo_documento = tipo_documentos.cod_tipo_documento\r\nand caja_chica.cod_cuenta = cuentas.cod_cuenta\r\nand caja_chica.cod_motivo = motivos.cod_motivo\r\nand caja_chica.cod_cajero = cajeros.cod_cajero\r\nand caja_chica.cod_moneda = monedas.cod_moneda and caja_chica.nro_registro =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new CajaChicaE(
               resultado.getInt("nro_registro"),
               resultado.getString("fecha"),
               resultado.getInt("cod_proveedor"),
               resultado.getString("nombre_proveedor"),
               resultado.getString("ruc_proveedor"),
               resultado.getString("telefono_proveedor"),
               resultado.getString("direccion_proveedor"),
               resultado.getInt("cod_tipo_documento"),
               resultado.getInt("timbrado"),
               resultado.getString("fecha_timbrado"),
               resultado.getString("nro_factura"),
               resultado.getInt("cod_cajero"),
               resultado.getInt("cod_cuenta"),
               resultado.getInt("cod_motivo"),
               resultado.getInt("nro_planilla"),
               resultado.getInt("cod_moneda"),
               resultado.getString("observacion"),
               resultado.getDouble("importe_exento"),
               resultado.getDouble("importe_gravado10"),
               resultado.getDouble("importe_gravado5"),
               resultado.getDouble("importe_iva10"),
               resultado.getDouble("importe_iva5"),
               resultado.getDouble("importe_total")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Caja Chica..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Caja Chica..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public CajaChicaE(
      int nro_registro,
      String fecha,
      int cod_proveedor,
      String nombre_proveedor,
      String ruc_proveedor,
      String telefono_proveedor,
      String direccion_proveedor,
      int cod_tipo_documento,
      int timbrado,
      String fecha_timbrado,
      String nro_factura,
      int cod_cajero,
      int cod_cuenta,
      int cod_motivo,
      int nro_planilla,
      int cod_moneda,
      String observacion,
      double importe_exento,
      double importe_gravado10,
      double importe_gravado5,
      double importe_iva10,
      double importe_iva5,
      double importe_total
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_proveedor = cod_proveedor;
      this.nombre_proveedor = nombre_proveedor;
      this.ruc_proveedor = ruc_proveedor;
      this.telefono_proveedor = telefono_proveedor;
      this.direccion_proveedor = direccion_proveedor;
      this.cod_tipo_documento = cod_tipo_documento;
      this.timbrado = timbrado;
      this.fecha_timbrado = fecha_timbrado;
      this.nro_factura = nro_factura;
      this.cod_cajero = cod_cajero;
      this.cod_cuenta = cod_cuenta;
      this.cod_motivo = cod_motivo;
      this.nro_planilla = nro_planilla;
      this.cod_moneda = cod_moneda;
      this.observacion = observacion;
      this.importe_exento = importe_exento;
      this.importe_gravado10 = importe_gravado10;
      this.importe_gravado5 = importe_gravado5;
      this.importe_iva10 = importe_iva10;
      this.importe_iva5 = importe_iva5;
      this.importe_total = importe_total;
   }

   public CajaChicaE(
      int nro_registro,
      String fecha,
      int cod_proveedor,
      String nombre_proveedor,
      String ruc_proveedor,
      String telefono_proveedor,
      String direccion_proveedor,
      int cod_tipo_documento,
      int timbrado,
      String fecha_timbrado,
      String nro_factura,
      int cod_cajero,
      int cod_cuenta,
      int cod_motivo,
      int nro_planilla,
      int cod_moneda,
      String observacion,
      double importe_exento,
      double importe_gravado10,
      double importe_gravado5,
      double importe_iva10,
      double importe_iva5,
      double importe_total,
      String nombre_tipo_documento,
      String nombre_cajero,
      String nombre_cuenta,
      String nombre_motivo,
      String nombre_moneda
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_proveedor = cod_proveedor;
      this.nombre_proveedor = nombre_proveedor;
      this.ruc_proveedor = ruc_proveedor;
      this.telefono_proveedor = telefono_proveedor;
      this.direccion_proveedor = direccion_proveedor;
      this.cod_tipo_documento = cod_tipo_documento;
      this.timbrado = timbrado;
      this.fecha_timbrado = fecha_timbrado;
      this.nro_factura = nro_factura;
      this.cod_cajero = cod_cajero;
      this.cod_cuenta = cod_cuenta;
      this.cod_motivo = cod_motivo;
      this.nro_planilla = nro_planilla;
      this.cod_moneda = cod_moneda;
      this.observacion = observacion;
      this.importe_exento = importe_exento;
      this.importe_gravado10 = importe_gravado10;
      this.importe_gravado5 = importe_gravado5;
      this.importe_iva10 = importe_iva10;
      this.importe_iva5 = importe_iva5;
      this.importe_total = importe_total;
      this.nombre_tipo_documento = nombre_tipo_documento;
      this.nombre_cajero = nombre_cajero;
      this.nombre_cuenta = nombre_cuenta;
      this.nombre_motivo = nombre_motivo;
      this.nombre_moneda = nombre_moneda;
   }

   public int getNro_registro() {
      return this.nro_registro;
   }

   public void setNro_registro(int nro_registro) {
      this.nro_registro = nro_registro;
   }

   public int getCod_proveedor() {
      return this.cod_proveedor;
   }

   public void setCod_proveedor(int cod_proveedor) {
      this.cod_proveedor = cod_proveedor;
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

   public String getNro_factura() {
      return this.nro_factura;
   }

   public void setNro_factura(String nro_factura) {
      this.nro_factura = nro_factura;
   }

   public int getCod_cajero() {
      return this.cod_cajero;
   }

   public void setCod_cajero(int cod_cajero) {
      this.cod_cajero = cod_cajero;
   }

   public int getCod_cuenta() {
      return this.cod_cuenta;
   }

   public void setCod_cuenta(int cod_cuenta) {
      this.cod_cuenta = cod_cuenta;
   }

   public int getCod_motivo() {
      return this.cod_motivo;
   }

   public void setCod_motivo(int cod_motivo) {
      this.cod_motivo = cod_motivo;
   }

   public int getNro_planilla() {
      return this.nro_planilla;
   }

   public void setNro_planilla(int nro_planilla) {
      this.nro_planilla = nro_planilla;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public void setCod_moneda(int cod_moneda) {
      this.cod_moneda = cod_moneda;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
   }

   public String getNombre_proveedor() {
      return this.nombre_proveedor;
   }

   public void setNombre_proveedor(String nombre_proveedor) {
      this.nombre_proveedor = nombre_proveedor;
   }

   public String getRuc_proveedor() {
      return this.ruc_proveedor;
   }

   public void setRuc_proveedor(String ruc_proveedor) {
      this.ruc_proveedor = ruc_proveedor;
   }

   public String getTelefono_proveedor() {
      return this.telefono_proveedor;
   }

   public void setTelefono_proveedor(String telefono_proveedor) {
      this.telefono_proveedor = telefono_proveedor;
   }

   public String getDireccion_proveedor() {
      return this.direccion_proveedor;
   }

   public void setDireccion_proveedor(String direccion_proveedor) {
      this.direccion_proveedor = direccion_proveedor;
   }

   public String getFecha_timbrado() {
      return this.fecha_timbrado;
   }

   public void setFecha_timbrado(String fecha_timbrado) {
      this.fecha_timbrado = fecha_timbrado;
   }

   public String getObservacion() {
      return this.observacion;
   }

   public void setObservacion(String observacion) {
      this.observacion = observacion;
   }

   public String getNombre_tipo_documento() {
      return this.nombre_tipo_documento;
   }

   public void setNombre_tipo_documento(String nombre_tipo_documento) {
      this.nombre_tipo_documento = nombre_tipo_documento;
   }

   public String getNombre_cajero() {
      return this.nombre_cajero;
   }

   public void setNombre_cajero(String nombre_cajero) {
      this.nombre_cajero = nombre_cajero;
   }

   public String getNombre_cuenta() {
      return this.nombre_cuenta;
   }

   public void setNombre_cuenta(String nombre_cuenta) {
      this.nombre_cuenta = nombre_cuenta;
   }

   public String getNombre_motivo() {
      return this.nombre_motivo;
   }

   public void setNombre_motivo(String nombre_motivo) {
      this.nombre_motivo = nombre_motivo;
   }

   public String getNombre_moneda() {
      return this.nombre_moneda;
   }

   public void setNombre_moneda(String nombre_moneda) {
      this.nombre_moneda = nombre_moneda;
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
}
