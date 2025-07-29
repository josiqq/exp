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

public class GrupoE {
   private int cod_grupo;
   private int cod_sub_seccion;
   private String nombre_grupo;

   public static int eliminarGrupo(int cod_grupo, JinternalPadre frame) {
      PreparedStatement psEliminarSeccion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarSeccion = conexion.prepareStatement("delete from familia_grupo where cod_grupo =?");
         psEliminarSeccion.setInt(1, cod_grupo);
         psEliminarSeccion.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Grupo.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Grupo..", frame);
      } finally {
         new CerrarRecursos(psEliminarSeccion, frame, "Error al eliminar Grupo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarGrupo(GrupoE entidad, JinternalPadrePermisos frame) {
      PreparedStatement psActualizarSeccion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarSeccion = conexion.prepareStatement("update familia_grupo set nombre_grupo=? where cod_grupo = ?");
         psActualizarSeccion.setString(1, entidad.getNombre_grupo());
         psActualizarSeccion.setInt(2, entidad.getCod_grupo());
         psActualizarSeccion.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Grupo.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Grupo..", frame);
      } finally {
         new CerrarRecursos(psActualizarSeccion, frame, "Error al actualizar Grupo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarGrupos(GrupoE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarSeccion = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_grupo) as mayor from familia_grupo");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarSeccion = conexion.prepareStatement("insert into familia_grupo (cod_grupo,nombre_grupo,cod_sub_seccion) values(?,?,?)");
         psInsertarSeccion.setInt(1, siguienteRegistro);
         psInsertarSeccion.setString(2, entidad.getNombre_grupo());
         psInsertarSeccion.setInt(3, entidad.getCod_sub_seccion());
         psInsertarSeccion.executeUpdate();
         conexion.commit();
         entidad.setCod_grupo(siguienteRegistro);
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Grupo.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Grupo..", frame);
      } finally {
         new CerrarRecursos(psInsertarSeccion, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Grupo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarGrupo(int cod_sub_seccion, ModeloTablaDefecto modelo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_grupo,nombre_grupo from familia_grupo where cod_sub_seccion =?");
         preparedStatementSelect.setInt(1, cod_sub_seccion);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt("cod_grupo"), resultado.getString("nombre_grupo")});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Grupo..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Grupo....");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarGrupo(int cod_sub_seccion, ModeloTablaDefecto modelo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_grupo,nombre_grupo from familia_grupo where cod_sub_seccion =?");
         preparedStatementSelect.setInt(1, cod_sub_seccion);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt("cod_grupo"), resultado.getString("nombre_grupo")});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Grupo..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Grupo....");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public GrupoE(int cod_grupo, String nombre_grupo, int cod_sub_seccion) {
      this.cod_grupo = cod_grupo;
      this.nombre_grupo = nombre_grupo;
      this.cod_sub_seccion = cod_sub_seccion;
   }

   public int getCod_grupo() {
      return this.cod_grupo;
   }

   public void setCod_grupo(int cod_grupo) {
      this.cod_grupo = cod_grupo;
   }

   public int getCod_sub_seccion() {
      return this.cod_sub_seccion;
   }

   public void setCod_sub_seccion(int cod_sub_seccion) {
      this.cod_sub_seccion = cod_sub_seccion;
   }

   public String getNombre_grupo() {
      return this.nombre_grupo;
   }

   public void setNombre_grupo(String nombre_grupo) {
      this.nombre_grupo = nombre_grupo;
   }
}
