package utilidadesConexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class FechaActualE {
   private String fechaActual;

   public static FechaActualE buscarFechaactual(JinternalPadre frame) {
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("Select curdate() as fecha_actual");
         rs = preparedStatement.executeQuery();
         if (rs.next()) {
            Date fechaActual = rs.getDate("fecha_actual");
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = formato.format(fechaActual);
            return new FechaActualE(fechaFormateada);
         }
      } catch (Exception var15) {
         try {
            conexion.rollback();
         } catch (SQLException var14) {
            LogErrores.errores(var14, "Error al obtener Fecha del Servidor.", frame);
         }

         LogErrores.errores(var15, "Error al obtener Fecha del Servidor..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, frame, "Error al obtener Fecha del Servidor..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public FechaActualE(String fechaActual) {
      this.fechaActual = fechaActual;
   }

   public String getFechaActual() {
      return this.fechaActual;
   }

   public void setFechaActual(String fechaActual) {
      this.fechaActual = fechaActual;
   }
}
