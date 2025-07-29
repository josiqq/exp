package utilidadesE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;

public class StockDepositosE {
   public static int eliminarTodoReserva(int sesion) {
      PreparedStatement psActualizarReserva = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarReserva = conexion.prepareStatement("delete from productos_reservados where sesion=?");
         psActualizarReserva.setInt(1, sesion);
         psActualizarReserva.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var10) {
         try {
            conexion.rollback();
         } catch (SQLException var9) {
            LogErrores.errores(var9, "Error al eliminar Reserva.");
         }

         LogErrores.errores(var10, "Error al eliminar Reserva..");
      } finally {
         new CerrarRecursos(psActualizarReserva, "Error al eliminar Reserva..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int eliminarReserva(String cod_producto, int sesion) {
      PreparedStatement psActualizarReserva = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarReserva = conexion.prepareStatement("delete from productos_reservados where cod_producto=? and sesion=?");
         psActualizarReserva.setString(1, cod_producto);
         psActualizarReserva.setInt(2, sesion);
         psActualizarReserva.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Reserva.");
         }

         LogErrores.errores(var11, "Error al eliminar Reserva..");
      } finally {
         new CerrarRecursos(psActualizarReserva, "Error al eliminar Reserva..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarReserva(String cod_producto, int cod_deposito, double cantidad, int sesion) {
      PreparedStatement psActualizarReserva = null;
      PreparedStatement psVerificarReserva = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psVerificarReserva = conexion.prepareStatement(
            "select count(*) as cantidad from productos_reservados where sesion = ? and cod_producto =? and cod_deposito =? limit 1"
         );
         psVerificarReserva.setInt(1, sesion);
         psVerificarReserva.setString(2, cod_producto);
         psVerificarReserva.setInt(3, cod_deposito);
         resultado = psVerificarReserva.executeQuery();
         if (resultado.next()) {
            if (resultado.getInt("cantidad") > 0) {
               psActualizarReserva = conexion.prepareStatement(
                  "update productos_reservados set cantidad=cantidad + ? where cod_producto =? and sesion =? and cod_deposito =?"
               );
               psActualizarReserva.setDouble(1, cantidad);
               psActualizarReserva.setString(2, cod_producto);
               psActualizarReserva.setInt(3, sesion);
               psActualizarReserva.setInt(4, cod_deposito);
            } else {
               psActualizarReserva = conexion.prepareStatement(
                  "insert into productos_reservados (cod_producto,cod_deposito,cantidad,tipo,sesion) values (?,?,?,?,?)"
               );
               psActualizarReserva.setString(1, cod_producto);
               psActualizarReserva.setInt(2, cod_deposito);
               psActualizarReserva.setDouble(3, cantidad);
               psActualizarReserva.setString(4, "Ventas");
               psActualizarReserva.setInt(5, sesion);
            }

            psActualizarReserva.executeUpdate();
         }

         conexion.commit();
         return 1;
      } catch (SQLException var16) {
         try {
            conexion.rollback();
         } catch (SQLException var15) {
            LogErrores.errores(var15, "Error al actualizar Reserva.");
         }

         LogErrores.errores(var16, "Error al actualizar Reserva..");
      } finally {
         new CerrarRecursos(psActualizarReserva, "Error al actualizar Reserva..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }
}
