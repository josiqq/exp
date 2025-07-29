package a00Plazos;

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

public class PlazosE {
   private int cod_plazo;
   private int estado;
   private int dias;
   private String nombre_plazo;

   public static int eliminarPlazos(PlazosE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarPlazo = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarPlazo = conexion.prepareStatement("delete from plazos where cod_plazo =?");
         psEliminarPlazo.setInt(1, entidad.getCod_plazo());
         psEliminarPlazo.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Plazo.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Plazo..", frame);
      } finally {
         new CerrarRecursos(psEliminarPlazo, frame, "Error al eliminar Plazo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarPlazos(PlazosE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarPplazos = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarPplazos = conexion.prepareStatement("update plazos set nombre_plazo=?,estado=?,dias=? where cod_plazo =?");
         psActualizarPplazos.setString(1, entidad.getNombre_plazo());
         psActualizarPplazos.setInt(2, entidad.getEstado());
         psActualizarPplazos.setInt(3, entidad.getDias());
         psActualizarPplazos.setInt(4, entidad.getCod_plazo());
         psActualizarPplazos.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Plazo.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Plazo..", frame);
      } finally {
         new CerrarRecursos(psActualizarPplazos, frame, "Error al actualizar Plazo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarPlazos(PlazosE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarPlazo = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_plazo) as mayor from plazos");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarPlazo = conexion.prepareStatement("insert into plazos (cod_plazo,nombre_plazo,estado,dias) values(?,?,?,?)");
         psInsertarPlazo.setInt(1, siguienteRegistro);
         psInsertarPlazo.setString(2, entidad.getNombre_plazo());
         psInsertarPlazo.setInt(3, entidad.getEstado());
         psInsertarPlazo.setInt(4, entidad.getDias());
         psInsertarPlazo.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Plazo.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Plazo..", frame);
      } finally {
         new CerrarRecursos(psInsertarPlazo, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Plazo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static PlazosE buscarPlazos2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      PlazosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_plazo,nombre_plazo from plazos where cod_plazo =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Plazo inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         PlazosE plazo = new PlazosE(resultado.getInt("cod_plazo"), resultado.getString("nombre_plazo"));
         var8 = plazo;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Plazos..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Plazos....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void cargarPlazoTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Plazos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Plazos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarPlazoTabla3(String sql, ModeloTablaDefecto modelo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt(1), resultado.getString(2), resultado.getInt(3)});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Plazos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Plazos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static PlazosE buscarPlazo(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_plazo,nombre_plazo,estado,dias from plazos where cod_plazo =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new PlazosE(resultado.getInt("cod_plazo"), resultado.getString("nombre_plazo"), resultado.getInt("estado"), resultado.getInt("dias"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Plazo..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Plazo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static PlazosE buscarPlazo3(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_plazo,nombre_plazo,dias from plazos where cod_plazo =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new PlazosE(resultado.getInt("cod_plazo"), resultado.getString("nombre_plazo"), resultado.getInt("dias"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Plazo..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Plazo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public PlazosE(int cod_plazo, String nombre_plazo, int dias) {
      this.cod_plazo = cod_plazo;
      this.nombre_plazo = nombre_plazo;
      this.dias = dias;
   }

   public PlazosE(int cod_plazo, String nombre_plazo) {
      this.cod_plazo = cod_plazo;
      this.nombre_plazo = nombre_plazo;
   }

   public PlazosE(int cod_plazo, String nombre_plazo, int estado, int dias) {
      this.cod_plazo = cod_plazo;
      this.nombre_plazo = nombre_plazo;
      this.estado = estado;
      this.dias = dias;
   }

   public int getCod_plazo() {
      return this.cod_plazo;
   }

   public void setCod_plazo(int cod_plazo) {
      this.cod_plazo = cod_plazo;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public int getDias() {
      return this.dias;
   }

   public void setDias(int dias) {
      this.dias = dias;
   }

   public String getNombre_plazo() {
      return this.nombre_plazo;
   }

   public void setNombre_plazo(String nombre_plazo) {
      this.nombre_plazo = nombre_plazo;
   }
}
