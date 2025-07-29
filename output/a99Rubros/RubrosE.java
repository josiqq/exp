package a99Rubros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;

public class RubrosE {
   private int cod_rubro;
   private int estado;
   private String nombre_rubro;

   public static int eliminarRubros(RubrosE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarRubro = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarRubro = conexion.prepareStatement("delete from rubros where cod_rubro =?");
         psEliminarRubro.setInt(1, entidad.getCod_rubro());
         psEliminarRubro.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Rubro.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Rubro..", frame);
      } finally {
         new CerrarRecursos(psEliminarRubro, frame, "Error al eliminar Rubro..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarRubros(RubrosE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarRubro = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarRubro = conexion.prepareStatement("update rubros set nombre_rubro=?,estado=? where cod_rubro =?");
         psActualizarRubro.setString(1, entidad.getNombre_rubro());
         psActualizarRubro.setInt(2, entidad.getEstado());
         psActualizarRubro.setInt(3, entidad.getCod_rubro());
         psActualizarRubro.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Rubro.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Rubro..", frame);
      } finally {
         new CerrarRecursos(psActualizarRubro, frame, "Error al actualizar Rubro..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarRubro(RubrosE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarRubro = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_rubro) as mayor from rubros");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarRubro = conexion.prepareStatement("insert into rubros (cod_rubro,nombre_rubro,estado) values(?,?,?)");
         psInsertarRubro.setInt(1, siguienteRegistro);
         psInsertarRubro.setString(2, entidad.getNombre_rubro());
         psInsertarRubro.setInt(3, entidad.getEstado());
         psInsertarRubro.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Rubro.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Rubro..", frame);
      } finally {
         new CerrarRecursos(psInsertarRubro, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Rubro..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarRubrosTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt(1), resultado.getString(2)});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Rubro..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Rubro..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static RubrosE buscarRubros2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      RubrosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_rubro,nombre_rubro from rubros where cod_rubro =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Rubro inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         RubrosE rubro = new RubrosE(resultado.getInt("cod_rubro"), resultado.getString("nombre_rubro"));
         var8 = rubro;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Rubro..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Rubro....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static RubrosE buscarRubro(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_rubro,nombre_rubro,estado from rubros where cod_rubro =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new RubrosE(resultado.getInt("cod_rubro"), resultado.getString("nombre_rubro"), resultado.getInt("estado"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Rubro..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Rubro..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public RubrosE(int cod_rubro, String nombre_rubro) {
      this.cod_rubro = cod_rubro;
      this.nombre_rubro = nombre_rubro;
   }

   public RubrosE(int cod_rubro, String nombre_rubro, int estado) {
      this.cod_rubro = cod_rubro;
      this.nombre_rubro = nombre_rubro;
      this.estado = estado;
   }

   public int getCod_rubro() {
      return this.cod_rubro;
   }

   public void setCod_rubro(int cod_rubro) {
      this.cod_rubro = cod_rubro;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_rubro() {
      return this.nombre_rubro;
   }

   public void setNombre_rubro(String nombre_rubro) {
      this.nombre_rubro = nombre_rubro;
   }
}
