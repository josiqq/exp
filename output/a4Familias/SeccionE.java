package a4Familias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;

public class SeccionE {
   private int cod_seccion;
   private String nombre_seccion;

   public static int eliminarSeccion(int cod_seccion, JinternalPadre frame) {
      PreparedStatement psEliminarSeccion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarSeccion = conexion.prepareStatement("delete from familia_seccion where cod_seccion =?");
         psEliminarSeccion.setInt(1, cod_seccion);
         psEliminarSeccion.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Seccion.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Seccion..", frame);
      } finally {
         new CerrarRecursos(psEliminarSeccion, frame, "Error al eliminar Seccion..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarSeccion(SeccionE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarSeccion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarSeccion = conexion.prepareStatement("update familia_seccion set nombre_seccion=? where cod_seccion = ?");
         psActualizarSeccion.setString(1, entidad.getNombre_seccion());
         psActualizarSeccion.setInt(2, entidad.getCod_seccion());
         psActualizarSeccion.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar secciones.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar secciones..", frame);
      } finally {
         new CerrarRecursos(psActualizarSeccion, frame, "Error al actualizar secciones..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarSeccion(JinternalPadre frame, ModeloTablaDefecto modelo) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_seccion,nombre_seccion from familia_seccion");
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt("cod_seccion"), resultado.getString("nombre_seccion")});
         }
      } catch (Exception var9) {
         LogErrores.errores(var9, "Error al seleccionar Seccion..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Seccion....");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarSeccion(JDialog frame, ModeloTablaDefecto modelo) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_seccion,nombre_seccion from familia_seccion");
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt("cod_seccion"), resultado.getString("nombre_seccion")});
         }
      } catch (Exception var9) {
         LogErrores.errores(var9, "Error al seleccionar Seccion..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Seccion....");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static int insertarSecciones(SeccionE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarSeccion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_seccion) as mayor from familia_seccion");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarSeccion = conexion.prepareStatement("insert into familia_seccion (cod_seccion,nombre_seccion) values(?,?)", 1);
         psInsertarSeccion.setInt(1, siguienteRegistro);
         psInsertarSeccion.setString(2, entidad.getNombre_seccion());
         psInsertarSeccion.executeUpdate();
         conexion.commit();
         entidad.setCod_seccion(siguienteRegistro);
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Seccion.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Seccion..", frame);
      } finally {
         new CerrarRecursos(psInsertarSeccion, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Seccion..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public SeccionE(int cod_seccion, String nombre_seccion) {
      this.cod_seccion = cod_seccion;
      this.nombre_seccion = nombre_seccion;
   }

   public int getCod_seccion() {
      return this.cod_seccion;
   }

   public void setCod_seccion(int cod_seccion) {
      this.cod_seccion = cod_seccion;
   }

   public String getNombre_seccion() {
      return this.nombre_seccion;
   }

   public void setNombre_seccion(String nombre_seccion) {
      this.nombre_seccion = nombre_seccion;
   }
}
