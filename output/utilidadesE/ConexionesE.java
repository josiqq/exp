package utilidadesE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;

public class ConexionesE {
   public static int id_peticion(String usuario) {
      Connection conexion = null;
      PreparedStatement psActualizarPeticion = null;
      ResultSet resultado = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarPeticion = conexion.prepareStatement("insert into sys_usuarios_peticiones (usuario) values (?)", 1);
         psActualizarPeticion.setString(1, usuario);
         psActualizarPeticion.executeUpdate();
         resultado = psActualizarPeticion.getGeneratedKeys();
         int idGenerado = 0;
         if (resultado.next()) {
            idGenerado = resultado.getInt(1);
         }

         conexion.commit();
         return idGenerado;
      } catch (SQLException var12) {
         try {
            conexion.rollback();
         } catch (SQLException var11) {
            LogErrores.errores(var11, "Error al insertar Peticion.");
         }

         LogErrores.errores(var12, "Error al insertar Peticion..");
      } finally {
         new CerrarRecursos(psActualizarPeticion, resultado, "Error al insertar Peticion..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }
}
