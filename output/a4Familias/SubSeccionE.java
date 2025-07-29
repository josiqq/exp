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
import utilidadesVentanas.JinternalPadrePermisos;

public class SubSeccionE {
   private int cod_sub_secion;
   private int cod_seccion;
   private String nombre_sub_seccion;

   public static int eliminarSubSeccion(int cod_sub_seccion, JinternalPadre frame) {
      PreparedStatement psEliminarSeccion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarSeccion = conexion.prepareStatement("delete from familia_sub_seccion where cod_sub_seccion =?");
         psEliminarSeccion.setInt(1, cod_sub_seccion);
         psEliminarSeccion.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Sub Seccion.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Sub Seccion..", frame);
      } finally {
         new CerrarRecursos(psEliminarSeccion, frame, "Error al eliminar Sub Seccion..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarsubSeccion(SubSeccionE entidad, JinternalPadrePermisos frame) {
      PreparedStatement psActualizarSeccion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarSeccion = conexion.prepareStatement("update familia_sub_seccion set nombre_sub_seccion=? where cod_sub_seccion = ?");
         psActualizarSeccion.setString(1, entidad.getNombre_sub_seccion());
         psActualizarSeccion.setInt(2, entidad.getCod_sub_secion());
         psActualizarSeccion.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar sub secciones.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar sub secciones..", frame);
      } finally {
         new CerrarRecursos(psActualizarSeccion, frame, "Error al actualizar sub secciones..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarSubSeccion(int cod_seccion, ModeloTablaDefecto modelo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_sub_seccion,nombre_sub_seccion from familia_sub_seccion where cod_seccion =?");
         preparedStatementSelect.setInt(1, cod_seccion);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt("cod_sub_seccion"), resultado.getString("nombre_sub_seccion")});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Sub Seccion..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Sub Seccion....");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarSubSeccion(int cod_seccion, ModeloTablaDefecto modelo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_sub_seccion,nombre_sub_seccion from familia_sub_seccion where cod_seccion =?");
         preparedStatementSelect.setInt(1, cod_seccion);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt("cod_sub_seccion"), resultado.getString("nombre_sub_seccion")});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Sub Seccion..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Sub Seccion....");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static int insertarSubSecciones(SubSeccionE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarSeccion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_sub_seccion) as mayor from familia_sub_seccion");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarSeccion = conexion.prepareStatement("insert into familia_sub_seccion (cod_sub_seccion,nombre_sub_seccion,cod_seccion) values(?,?,?)");
         psInsertarSeccion.setInt(1, siguienteRegistro);
         psInsertarSeccion.setString(2, entidad.getNombre_sub_seccion());
         psInsertarSeccion.setInt(3, entidad.getCod_seccion());
         psInsertarSeccion.executeUpdate();
         conexion.commit();
         entidad.setCod_seccion(siguienteRegistro);
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Sub Seccion.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Sub Seccion..", frame);
      } finally {
         new CerrarRecursos(psInsertarSeccion, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Sub Seccion..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public SubSeccionE(int cod_sub_seccion, String nombre_sub_seccion, int cod_seccion) {
      this.cod_sub_secion = cod_sub_seccion;
      this.nombre_sub_seccion = nombre_sub_seccion;
      this.cod_seccion = cod_seccion;
   }

   public int getCod_sub_secion() {
      return this.cod_sub_secion;
   }

   public void setCod_sub_secion(int cod_sub_secion) {
      this.cod_sub_secion = cod_sub_secion;
   }

   public int getCod_seccion() {
      return this.cod_seccion;
   }

   public void setCod_seccion(int cod_seccion) {
      this.cod_seccion = cod_seccion;
   }

   public String getNombre_sub_seccion() {
      return this.nombre_sub_seccion;
   }

   public void setNombre_sub_seccion(String nombre_sub_seccion) {
      this.nombre_sub_seccion = nombre_sub_seccion;
   }
}
