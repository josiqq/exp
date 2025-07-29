package a00Cotizaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class CotizacionesE {
   private String fecha;
   private String nombre_moneda_origen;
   private String nombre_moneda_destino;
   private int cod_moneda_origen;
   private int cod_moneda_destino;
   private int operacion;
   private double cotizacion;

   public static int eliminarCotizaciones(CotizacionesE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarCotizacion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarCotizacion = conexion.prepareStatement("delete from cotizaciones where cod_moneda_origen =? and cod_moneda_destino =? and fecha =?");
         psEliminarCotizacion.setInt(1, entidad.getCod_moneda_origen());
         psEliminarCotizacion.setInt(2, entidad.getCod_moneda_destino());
         psEliminarCotizacion.setString(3, entidad.getFecha());
         psEliminarCotizacion.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Cotizacion.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Cotizacion..", frame);
      } finally {
         new CerrarRecursos(psEliminarCotizacion, frame, "Error al eliminar Cotizacion..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarCotizaciones(CotizacionesE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarCotizacion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarCotizacion = conexion.prepareStatement(
            "update cotizaciones set operacion=?, cotizacion =? where cod_moneda_origen =? and cod_moneda_destino =? and fecha =?"
         );
         psActualizarCotizacion.setInt(1, entidad.getOperacion());
         psActualizarCotizacion.setDouble(2, entidad.getCotizacion());
         psActualizarCotizacion.setInt(3, entidad.getCod_moneda_origen());
         psActualizarCotizacion.setInt(4, entidad.getCod_moneda_destino());
         psActualizarCotizacion.setString(5, entidad.getFecha());
         psActualizarCotizacion.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Cotizacion.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Cotizacion..", frame);
      } finally {
         new CerrarRecursos(psActualizarCotizacion, frame, "Error al actualizar Cotizacion..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarCotizaciones(CotizacionesE entidad, JinternalPadre frame) {
      PreparedStatement psInsertarCotizacion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psInsertarCotizacion = conexion.prepareStatement(
            "insert into cotizaciones (fecha,cod_moneda_origen,cod_moneda_destino,cotizacion,operacion,fecha_alta,usuario_alta) values(?,?,?,?,?,now(),SUBSTRING_INDEX(USER(), '@', 1))"
         );
         psInsertarCotizacion.setString(1, entidad.getFecha());
         psInsertarCotizacion.setInt(2, entidad.getCod_moneda_origen());
         psInsertarCotizacion.setInt(3, entidad.getCod_moneda_destino());
         psInsertarCotizacion.setDouble(4, entidad.getCotizacion());
         psInsertarCotizacion.setInt(5, entidad.getOperacion());
         psInsertarCotizacion.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al insertar Cotizacion.", frame);
         }

         LogErrores.errores(var11, "Error al insertar Cotizacion..", frame);
      } finally {
         new CerrarRecursos(psInsertarCotizacion, frame, "Error al insertar Cotizacion..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static CotizacionesE buscarCotizacionGral(String fecha, int cod_moneda_origen, int cod_moneda_destino, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CotizacionesE var10;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cotizacion from cotizaciones where fecha=? and cod_moneda_origen =? and cod_moneda_destino =?"
         );
         preparedStatementSelect.setString(1, fecha);
         preparedStatementSelect.setInt(2, cod_moneda_origen);
         preparedStatementSelect.setInt(3, cod_moneda_destino);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Cotizacion", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CotizacionesE cotizacion = new CotizacionesE(resultado.getDouble("cotizacion"));
         var10 = cotizacion;
      } catch (Exception var13) {
         LogErrores.errores(var13, "Error al seleccionar Cotizacion..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cotizacion....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var10;
   }

   public static CotizacionesE buscarCotizacionHoy(int cod_moneda_origen, int cod_moneda_destino, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;
      if (cod_moneda_destino == cod_moneda_origen) {
         return new CotizacionesE(1.0);
      } else {
         CotizacionesE var9;
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            Connection conexion = db.abrirConexion();
            preparedStatementSelect = conexion.prepareStatement(
               "select cotizacion from cotizaciones where fecha=curdate() and cod_moneda_origen =? and cod_moneda_destino =?"
            );
            preparedStatementSelect.setInt(1, cod_moneda_origen);
            preparedStatementSelect.setInt(2, cod_moneda_destino);
            resultado = preparedStatementSelect.executeQuery();
            if (!resultado.next()) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Cotizacion", "error");
               rs.setLocationRelativeTo(frame);
               rs.setVisible(true);
               return null;
            }

            CotizacionesE cotizacion = new CotizacionesE(resultado.getDouble("cotizacion"));
            var9 = cotizacion;
         } catch (Exception var12) {
            LogErrores.errores(var12, "Error al seleccionar Cotizacion..", frame);
            return null;
         } finally {
            new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cotizacion....");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return var9;
      }
   }

   public static CotizacionesE buscarCotizacion2(String fecha, int cod_moneda_origen, int cod_moneda_destino, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CotizacionesE var10;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cotizacion,operacion from cotizaciones where fecha=? and cod_moneda_origen =? and cod_moneda_destino =?"
         );
         preparedStatementSelect.setString(1, fecha);
         preparedStatementSelect.setInt(2, cod_moneda_origen);
         preparedStatementSelect.setInt(3, cod_moneda_destino);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Cotizacion", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CotizacionesE cotizacion = new CotizacionesE(resultado.getDouble("cotizacion"), resultado.getInt("operacion"));
         var10 = cotizacion;
      } catch (Exception var13) {
         LogErrores.errores(var13, "Error al seleccionar Cotizacion..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cotizacion....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var10;
   }

   public static CotizacionesE cambiarImporte(int cod_moneda_origen, int cod_moneda_destino, double importe, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CotizacionesE var11;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select f_cambiar_moneda(curdate(), ?, ?, ?) as cotizacion");
         preparedStatementSelect.setInt(1, cod_moneda_origen);
         preparedStatementSelect.setInt(2, cod_moneda_destino);
         preparedStatementSelect.setDouble(3, importe);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe Cotizacion", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CotizacionesE cotizacion = new CotizacionesE(resultado.getDouble("cotizacion"));
         var11 = cotizacion;
      } catch (Exception var14) {
         LogErrores.errores(var14, "Error al seleccionar Cotizacion..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cotizacion....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var11;
   }

   public CotizacionesE(double cotizacion, int operacion) {
      this.cotizacion = cotizacion;
      this.operacion = operacion;
   }

   public CotizacionesE(double cotizacion) {
      this.cotizacion = cotizacion;
   }

   public CotizacionesE(
      String fecha, int cod_moneda_origen, int cod_moneda_destino, double cotizacion, int operacion, String nombre_moneda_origen, String nombre_moneda_destino
   ) {
      this.fecha = fecha;
      this.cod_moneda_origen = cod_moneda_origen;
      this.cod_moneda_destino = cod_moneda_destino;
      this.cotizacion = cotizacion;
      this.operacion = operacion;
      this.nombre_moneda_destino = nombre_moneda_destino;
      this.nombre_moneda_origen = nombre_moneda_origen;
   }

   public CotizacionesE(String fecha, int cod_moneda_origen, int cod_moneda_destino, double cotizacion, int operacion) {
      this.fecha = fecha;
      this.cod_moneda_origen = cod_moneda_origen;
      this.cod_moneda_destino = cod_moneda_destino;
      this.cotizacion = cotizacion;
      this.operacion = operacion;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
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

   public double getCotizacion() {
      return this.cotizacion;
   }

   public void setCotizacion(double cotizacion) {
      this.cotizacion = cotizacion;
   }

   public int getOperacion() {
      return this.operacion;
   }

   public void setOperacion(int operacion) {
      this.operacion = operacion;
   }
}
