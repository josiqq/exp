package a009MovimientosCuentas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class MovimientoCuentasE {
   private int nro_registro;
   private int cod_cajero;
   private int nro_planilla;
   private int cod_cuenta_origen;
   private int cod_cuenta_destino;
   private int nro_cheque;
   private int nro_boleta;
   private int cod_moneda_origen;
   private int cod_moneda_destino;
   private int cod_motivo;
   private String fecha;
   private String hora_movimiento;
   private String observacion;
   private String nombre_cuenta_origen;
   private String nombre_cuenta_destino;
   private String nombre_moneda_origen;
   private String nombre_moneda_destino;
   private String nombre_cajero;
   private String nombre_motivo;
   private double importe;
   private double cotizacion;

   public static int ultimoRegistro(JinternalPadre frame) {
      ResultSet rsMayor = null;
      Connection conexion = null;
      PreparedStatement preparedStatementMayor = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementMayor = conexion.prepareStatement("Select max(nro_registro) as mayor from cuentas_movimientos");
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

   public static int eliminarMovimientoCuenta(MovimientoCuentasE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMovimientoCuenta = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMovimientoCuenta = conexion.prepareStatement("delete from cuentas_movimientos where nro_registro =?");
         psEliminarMovimientoCuenta.setInt(1, entidad.getNro_registro());
         psEliminarMovimientoCuenta.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Movimiento de Cuenta.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Movimiento de Cuenta..", frame);
      } finally {
         new CerrarRecursos(psEliminarMovimientoCuenta, frame, "Error al eliminar Movimiento de Cuenta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarMovimientoCuenta(MovimientoCuentasE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarMovimientoCuenta = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarMovimientoCuenta = conexion.prepareStatement(
            "update cuentas_movimientos set fecha=?,cod_cuenta_origen=?,cod_cuenta_destino=?,nro_cheque=?,nro_boleta=?,\r\nobservacion=?,importe=?,cod_moneda_origen=?,cod_moneda_destino=?,cotizacion=? where nro_registro =?"
         );
         psActualizarMovimientoCuenta.setString(1, entidad.getFecha());
         psActualizarMovimientoCuenta.setInt(2, entidad.getCod_cuenta_origen());
         psActualizarMovimientoCuenta.setInt(3, entidad.getCod_cuenta_destino());
         psActualizarMovimientoCuenta.setInt(4, entidad.getNro_cheque());
         psActualizarMovimientoCuenta.setInt(5, entidad.getNro_boleta());
         psActualizarMovimientoCuenta.setString(6, entidad.getObservacion());
         psActualizarMovimientoCuenta.setDouble(7, entidad.getImporte());
         psActualizarMovimientoCuenta.setInt(8, entidad.getCod_moneda_origen());
         psActualizarMovimientoCuenta.setInt(9, entidad.getCod_moneda_destino());
         psActualizarMovimientoCuenta.setDouble(10, entidad.getCotizacion());
         psActualizarMovimientoCuenta.setInt(11, entidad.getNro_registro());
         psActualizarMovimientoCuenta.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Movimiento de Cuenta.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Movimiento de Cuenta..", frame);
      } finally {
         new CerrarRecursos(psActualizarMovimientoCuenta, frame, "Error al actualizar Movimiento de Cuenta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarMovimientoCuentas(MovimientoCuentasE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarMovimientoCuenta = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(nro_registro) as mayor from cuentas_movimientos");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarMovimientoCuenta = conexion.prepareStatement(
            "insert into cuentas_movimientos (nro_registro,fecha,hora_movimiento,cod_cajero,nro_planilla,\r\ncod_cuenta_origen,cod_cuenta_destino,nro_cheque,nro_boleta,\r\nobservacion,importe,cod_moneda_origen,cod_moneda_destino,cotizacion,cod_motivo) values(?,?,curtime(),?,?,?,?,?,?,?,?,?,?,?,?)"
         );
         psInsertarMovimientoCuenta.setInt(1, siguienteRegistro);
         psInsertarMovimientoCuenta.setString(2, entidad.getFecha());
         psInsertarMovimientoCuenta.setInt(3, entidad.getCod_cajero());
         psInsertarMovimientoCuenta.setInt(4, entidad.getNro_planilla());
         psInsertarMovimientoCuenta.setInt(5, entidad.getCod_cuenta_origen());
         psInsertarMovimientoCuenta.setInt(6, entidad.getCod_cuenta_destino());
         psInsertarMovimientoCuenta.setInt(7, entidad.getNro_cheque());
         psInsertarMovimientoCuenta.setInt(8, entidad.getNro_boleta());
         psInsertarMovimientoCuenta.setString(9, entidad.getObservacion());
         psInsertarMovimientoCuenta.setDouble(10, entidad.getImporte());
         psInsertarMovimientoCuenta.setInt(11, entidad.getCod_moneda_origen());
         psInsertarMovimientoCuenta.setInt(12, entidad.getCod_moneda_destino());
         psInsertarMovimientoCuenta.setDouble(13, entidad.getCotizacion());
         psInsertarMovimientoCuenta.setInt(14, entidad.getCod_motivo());
         psInsertarMovimientoCuenta.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Movimiento de Cuenta.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Movimiento de Cuenta..", frame);
      } finally {
         new CerrarRecursos(psInsertarMovimientoCuenta, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Movimiento de Cuenta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static MovimientoCuentasE buscarMovimiento(int registro, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\ncuentas_movimientos.nro_registro,cuentas_movimientos.fecha,cuentas_movimientos.hora_movimiento,\r\ncuentas_movimientos.cod_cajero,cuentas_movimientos.nro_planilla,cuentas_movimientos.cod_cuenta_origen,\r\ncuentas_movimientos.cod_cuenta_destino,cuentas_movimientos.nro_cheque,cuentas_movimientos.nro_boleta,\r\ncuentas_movimientos.observacion,cuentas_movimientos.importe,cuentas_movimientos.cod_moneda_origen,\r\ncuentas_movimientos.cod_moneda_destino,cuentas_movimientos.cotizacion,cuentas_movimientos.cod_motivo,motivos.nombre_motivo,\r\ncajeros.nombre_cajero,cuentas.nombre_cuenta as nombre_cuenta_origen,\r\n(select cuentas.nombre_cuenta from cuentas where cuentas.cod_cuenta = cuentas_movimientos.cod_cuenta_destino ) as nombre_cuenta_destino,\r\n(select monedas.nombre_moneda from monedas where monedas.cod_moneda = cuentas_movimientos.cod_moneda_destino) as nombre_moneda_destino,\r\nmonedas.nombre_moneda as nombre_moneda_origen\r\n from cuentas_movimientos,cajeros,cuentas,monedas,motivos\r\nwhere\r\ncuentas_movimientos.cod_cajero = cajeros.cod_cajero\r\nand cuentas_movimientos.cod_cuenta_origen = cuentas.cod_cuenta\r\nand cuentas_movimientos.cod_motivo = motivos.cod_motivo\r\nand cuentas_movimientos.cod_moneda_origen = monedas.cod_moneda and cuentas_movimientos.nro_registro =?"
         );
         preparedStatementSelect.setInt(1, registro);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new MovimientoCuentasE(
               resultado.getInt("nro_registro"),
               resultado.getString("fecha"),
               resultado.getString("hora_movimiento"),
               resultado.getInt("cod_cajero"),
               resultado.getInt("nro_planilla"),
               resultado.getInt("cod_cuenta_origen"),
               resultado.getInt("cod_cuenta_destino"),
               resultado.getInt("nro_boleta"),
               resultado.getInt("nro_cheque"),
               resultado.getString("observacion"),
               resultado.getDouble("importe"),
               resultado.getInt("cod_moneda_origen"),
               resultado.getInt("cod_moneda_destino"),
               resultado.getDouble("cotizacion"),
               resultado.getString("nombre_cajero"),
               resultado.getString("nombre_cuenta_origen"),
               resultado.getString("nombre_cuenta_destino"),
               resultado.getString("nombre_moneda_origen"),
               resultado.getString("nombre_moneda_destino"),
               resultado.getInt("cod_motivo"),
               resultado.getString("nombre_motivo")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Movimiento de Cuenta..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Movimiento de Cuenta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public MovimientoCuentasE(
      int nro_registro,
      String fecha,
      String hora_movimiento,
      int cod_cajero,
      int nro_planilla,
      int cod_cuenta_origen,
      int cod_cuenta_destino,
      int nro_cheque,
      int nro_boleta,
      String observacion,
      double importe,
      int cod_moneda_origen,
      int cod_moneda_destino,
      double cotizacion,
      String nombre_cajero,
      String nombre_cuenta_origen,
      String nombre_cuenta_destino,
      String nombre_moneda_origen,
      String nombre_moneda_destino,
      int cod_motivo,
      String nombre_motivo
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.hora_movimiento = hora_movimiento;
      this.cod_cajero = cod_cajero;
      this.nro_planilla = nro_planilla;
      this.cod_cuenta_origen = cod_cuenta_origen;
      this.cod_cuenta_destino = cod_cuenta_destino;
      this.nro_cheque = nro_cheque;
      this.nro_boleta = nro_boleta;
      this.observacion = observacion;
      this.importe = importe;
      this.cod_moneda_origen = cod_moneda_origen;
      this.cod_moneda_destino = cod_moneda_destino;
      this.cotizacion = cotizacion;
      this.nombre_cajero = nombre_cajero;
      this.nombre_cuenta_origen = nombre_cuenta_origen;
      this.nombre_cuenta_destino = nombre_cuenta_destino;
      this.nombre_moneda_origen = nombre_moneda_origen;
      this.nombre_moneda_destino = nombre_moneda_destino;
      this.cod_motivo = cod_motivo;
      this.nombre_motivo = nombre_motivo;
   }

   public MovimientoCuentasE(
      int nro_registro,
      String fecha,
      int cod_cajero,
      int nro_planilla,
      int cod_cuenta_origen,
      int cod_cuenta_destino,
      int nro_cheque,
      int nro_boleta,
      String observacion,
      double importe,
      int cod_moneda_origen,
      int cod_moneda_destino,
      double cotizacion,
      int cod_motivo
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_cajero = cod_cajero;
      this.nro_planilla = nro_planilla;
      this.cod_cuenta_origen = cod_cuenta_origen;
      this.cod_cuenta_destino = cod_cuenta_destino;
      this.nro_cheque = nro_cheque;
      this.nro_boleta = nro_boleta;
      this.observacion = observacion;
      this.importe = importe;
      this.cod_moneda_origen = cod_moneda_origen;
      this.cod_moneda_destino = cod_moneda_destino;
      this.cotizacion = cotizacion;
      this.cod_motivo = cod_motivo;
   }

   public MovimientoCuentasE(
      int nro_registro,
      String fecha,
      String hora_movimiento,
      int cod_cajero,
      int nro_planilla,
      int cod_cuenta_origen,
      int cod_cuenta_destino,
      int nro_cheque,
      int nro_boleta,
      String observacion,
      double importe,
      int cod_moneda_origen,
      int cod_moneda_destino,
      double cotizacion,
      int cod_motivo
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.hora_movimiento = hora_movimiento;
      this.cod_cajero = cod_cajero;
      this.nro_planilla = nro_planilla;
      this.cod_cuenta_origen = cod_cuenta_origen;
      this.cod_cuenta_destino = cod_cuenta_destino;
      this.nro_cheque = nro_cheque;
      this.nro_boleta = nro_boleta;
      this.observacion = observacion;
      this.importe = importe;
      this.cod_moneda_origen = cod_moneda_origen;
      this.cod_moneda_destino = cod_moneda_destino;
      this.cotizacion = cotizacion;
      this.cod_motivo = cod_motivo;
   }

   public int getNro_registro() {
      return this.nro_registro;
   }

   public void setNro_registro(int nro_registro) {
      this.nro_registro = nro_registro;
   }

   public int getCod_cajero() {
      return this.cod_cajero;
   }

   public void setCod_cajero(int cod_cajero) {
      this.cod_cajero = cod_cajero;
   }

   public int getNro_planilla() {
      return this.nro_planilla;
   }

   public void setNro_planilla(int nro_planilla) {
      this.nro_planilla = nro_planilla;
   }

   public int getCod_cuenta_origen() {
      return this.cod_cuenta_origen;
   }

   public void setCod_cuenta_origen(int cod_cuenta_origen) {
      this.cod_cuenta_origen = cod_cuenta_origen;
   }

   public int getCod_cuenta_destino() {
      return this.cod_cuenta_destino;
   }

   public void setCod_cuenta_destino(int cod_cuenta_destino) {
      this.cod_cuenta_destino = cod_cuenta_destino;
   }

   public int getNro_cheque() {
      return this.nro_cheque;
   }

   public void setNro_cheque(int nro_cheque) {
      this.nro_cheque = nro_cheque;
   }

   public int getNro_boleta() {
      return this.nro_boleta;
   }

   public void setNro_boleta(int nro_boleta) {
      this.nro_boleta = nro_boleta;
   }

   public int getCod_moneda_origen() {
      return this.cod_moneda_origen;
   }

   public void setCod_moneda_origen(int cod_moneda_origen) {
      this.cod_moneda_origen = cod_moneda_origen;
   }

   public int getCod_moneda_destino() {
      return this.cod_moneda_destino;
   }

   public void setCod_moneda_destino(int cod_moneda_destino) {
      this.cod_moneda_destino = cod_moneda_destino;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
   }

   public String getHora_movimiento() {
      return this.hora_movimiento;
   }

   public void setHora_movimiento(String hora_movimiento) {
      this.hora_movimiento = hora_movimiento;
   }

   public String getObservacion() {
      return this.observacion;
   }

   public void setObservacion(String observacion) {
      this.observacion = observacion;
   }

   public String getNombre_cuenta_origen() {
      return this.nombre_cuenta_origen;
   }

   public void setNombre_cuenta_origen(String nombre_cuenta_origen) {
      this.nombre_cuenta_origen = nombre_cuenta_origen;
   }

   public String getNombre_cuenta_destino() {
      return this.nombre_cuenta_destino;
   }

   public void setNombre_cuenta_destino(String nombre_cuenta_destino) {
      this.nombre_cuenta_destino = nombre_cuenta_destino;
   }

   public String getNombre_moneda_origen() {
      return this.nombre_moneda_origen;
   }

   public void setNombre_moneda_origen(String nombre_moneda_origen) {
      this.nombre_moneda_origen = nombre_moneda_origen;
   }

   public String getNombre_moneda_destino() {
      return this.nombre_moneda_destino;
   }

   public void setNombre_moneda_destino(String nombre_moneda_destino) {
      this.nombre_moneda_destino = nombre_moneda_destino;
   }

   public double getImporte() {
      return this.importe;
   }

   public void setImporte(double importe) {
      this.importe = importe;
   }

   public double getCotizacion() {
      return this.cotizacion;
   }

   public void setCotizacion(double cotizacion) {
      this.cotizacion = cotizacion;
   }

   public String getNombre_cajero() {
      return this.nombre_cajero;
   }

   public void setNombre_cajero(String nombre_cajero) {
      this.nombre_cajero = nombre_cajero;
   }

   public int getCod_motivo() {
      return this.cod_motivo;
   }

   public void setCod_motivo(int cod_motivo) {
      this.cod_motivo = cod_motivo;
   }

   public String getNombre_motivo() {
      return this.nombre_motivo;
   }

   public void setNombre_motivo(String nombre_motivo) {
      this.nombre_motivo = nombre_motivo;
   }
}
