package a0099Motivos;

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

public class MotivosE {
   private int cod_motivo;
   private int estado;
   private int tesoreria;
   private int gastos;
   private int cajachica;
   private String nombre_motivo;

   public static int eliminarMotivos(MotivosE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMotivo = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMotivo = conexion.prepareStatement("delete from motivos where cod_motivo =?");
         psEliminarMotivo.setInt(1, entidad.getCod_motivo());
         psEliminarMotivo.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Motivo.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Motivo..", frame);
      } finally {
         new CerrarRecursos(psEliminarMotivo, frame, "Error al eliminar Motivo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarMotivo(MotivosE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarMotivo = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarMotivo = conexion.prepareStatement("update motivos set nombre_motivo=?,estado=?,tesoreria=?,gastos=?,cajachica=? where cod_motivo =?");
         psActualizarMotivo.setString(1, entidad.getNombre_motivo());
         psActualizarMotivo.setInt(2, entidad.getEstado());
         psActualizarMotivo.setInt(3, entidad.getTesoreria());
         psActualizarMotivo.setInt(4, entidad.getGastos());
         psActualizarMotivo.setInt(5, entidad.getCajachica());
         psActualizarMotivo.setInt(6, entidad.getCod_motivo());
         psActualizarMotivo.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Motivo.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Motivo..", frame);
      } finally {
         new CerrarRecursos(psActualizarMotivo, frame, "Error al actualizar Motivo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarMotivo(MotivosE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarMotivo = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_motivo) as mayor from motivos");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarMotivo = conexion.prepareStatement("insert into motivos (cod_motivo,nombre_motivo,estado,tesoreria,gastos,cajachica) values(?,?,?,?,?,?)");
         psInsertarMotivo.setInt(1, siguienteRegistro);
         psInsertarMotivo.setString(2, entidad.getNombre_motivo());
         psInsertarMotivo.setInt(3, entidad.getEstado());
         psInsertarMotivo.setInt(4, entidad.getTesoreria());
         psInsertarMotivo.setInt(5, entidad.getGastos());
         psInsertarMotivo.setInt(6, entidad.getCajachica());
         psInsertarMotivo.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Motivo.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Motivo..", frame);
      } finally {
         new CerrarRecursos(psInsertarMotivo, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Motivo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarMotivosTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Motivos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Motivos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static MotivosE buscarMotivo(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "Select cod_motivo,nombre_motivo,estado,tesoreria,gastos,cajachica from motivos where cod_motivo =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new MotivosE(
               resultado.getInt("cod_motivo"),
               resultado.getString("nombre_motivo"),
               resultado.getInt("estado"),
               resultado.getInt("tesoreria"),
               resultado.getInt("gastos"),
               resultado.getInt("cajachica")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Motivo..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Motivo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static MotivosE buscarMotivo2(int codigo, String sql, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new MotivosE(resultado.getInt("cod_motivo"), resultado.getString("nombre_motivo"));
         }
      } catch (Exception var12) {
         LogErrores.errores(var12, "Error al seleccionar Motivo..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Motivo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public MotivosE(int cod_motivo, String nombre_motivo) {
      this.cod_motivo = cod_motivo;
      this.nombre_motivo = nombre_motivo;
   }

   public MotivosE(int cod_motivo, String nombre_motivo, int estado, int tesoreria, int gastos, int cajachica) {
      this.cod_motivo = cod_motivo;
      this.nombre_motivo = nombre_motivo;
      this.estado = estado;
      this.tesoreria = tesoreria;
      this.gastos = gastos;
      this.cajachica = cajachica;
   }

   public int getCod_motivo() {
      return this.cod_motivo;
   }

   public void setCod_motivo(int cod_motivo) {
      this.cod_motivo = cod_motivo;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public int getTesoreria() {
      return this.tesoreria;
   }

   public void setTesoreria(int tesoreria) {
      this.tesoreria = tesoreria;
   }

   public int getGastos() {
      return this.gastos;
   }

   public void setGastos(int gastos) {
      this.gastos = gastos;
   }

   public int getCajachica() {
      return this.cajachica;
   }

   public void setCajachica(int cajachica) {
      this.cajachica = cajachica;
   }

   public String getNombre_motivo() {
      return this.nombre_motivo;
   }

   public void setNombre_motivo(String nombre_motivo) {
      this.nombre_motivo = nombre_motivo;
   }
}
