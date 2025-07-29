package a1Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;

public class LoginEntidad {
   private String usuario;
   private int estado;
   private String grupo;

   public static LoginEntidad buscarUsuario() {
      ResultSet resultado = null;
      PreparedStatement preparedStatement = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("select estado, grupo from sys_usuarios where usuario = ?");
         preparedStatement.setString(1, "root");
         resultado = preparedStatement.executeQuery();
         if (resultado.next()) {
            return new LoginEntidad(db.getUsuario(), resultado.getInt("estado"), resultado.getString("grupo"));
         }
      } catch (Exception var9) {
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, resultado, "Error al obtener consulta de usuario...");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public LoginEntidad(String usuario, int estado, String grupo) {
      this.usuario = usuario;
      this.estado = estado;
      this.grupo = grupo;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getGrupo() {
      return this.grupo;
   }

   public void setGrupo(String grupo) {
      this.grupo = grupo;
   }

   public String getUsuario() {
      return this.usuario;
   }

   public void setUsuario(String usuario) {
      this.usuario = usuario;
   }
}
