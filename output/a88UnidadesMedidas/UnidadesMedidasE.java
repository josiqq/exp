package a88UnidadesMedidas;

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
import utilidadesVentanas.JinternalPadreString;

public class UnidadesMedidasE {
   private int cod_unidad_medida;
   private int estado;
   private String nombre_unidad_medida;
   private String sigla;

   public static int eliminarUnidadesMedidas(UnidadesMedidasE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarSucursal = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarSucursal = conexion.prepareStatement("delete from unidades_medidas where cod_unidad_medida =?");
         psEliminarSucursal.setInt(1, entidad.getCod_unidad_medida());
         psEliminarSucursal.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Unidad de Medida.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Unidad de Medida..", frame);
      } finally {
         new CerrarRecursos(psEliminarSucursal, frame, "Error al eliminar Unidad de Medida..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarUnidadesMedidas(UnidadesMedidasE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarSucursales = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarSucursales = conexion.prepareStatement("update unidades_medidas set nombre_unidad_medida=?,estado=?,sigla=? where cod_unidad_medida =?");
         psActualizarSucursales.setString(1, entidad.getNombre_unidad_medida());
         psActualizarSucursales.setInt(2, entidad.getEstado());
         psActualizarSucursales.setString(3, entidad.getSigla());
         psActualizarSucursales.setInt(4, entidad.getCod_unidad_medida());
         psActualizarSucursales.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Unidad de Medida.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Unidad de Medida..", frame);
      } finally {
         new CerrarRecursos(psActualizarSucursales, frame, "Error al actualizar Unidad de Medida..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarUnidadesMedidas(UnidadesMedidasE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarUnidadesMedida = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_unidad_medida) as mayor from unidades_medidas");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarUnidadesMedida = conexion.prepareStatement(
            "insert into unidades_medidas (cod_unidad_medida,nombre_unidad_medida,estado,sigla) values(?,?,?,?)"
         );
         psInsertarUnidadesMedida.setInt(1, siguienteRegistro);
         psInsertarUnidadesMedida.setString(2, entidad.getNombre_unidad_medida());
         psInsertarUnidadesMedida.setInt(3, entidad.getEstado());
         psInsertarUnidadesMedida.setString(4, entidad.getSigla());
         psInsertarUnidadesMedida.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Unidad de Medida.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Unidad de Medida..", frame);
      } finally {
         new CerrarRecursos(psInsertarUnidadesMedida, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Unidad de Medida..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarUnidadesMedidasTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Unidad de Medida..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Unidad de Medida..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static UnidadesMedidasE buscarUnidadesMedida2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      UnidadesMedidasE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_unidad_medida,nombre_unidad_medida from unidades_medidas where cod_unidad_medida =? and estado =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Unidad de Medida inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         UnidadesMedidasE unidadMedida = new UnidadesMedidasE(resultado.getInt("cod_unidad_medida"), resultado.getString("nombre_unidad_medida"));
         var8 = unidadMedida;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Unidad de Medida..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Unidad de Medida....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static UnidadesMedidasE buscarUnidadesMedida2(int codigo, JinternalPadreString frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      UnidadesMedidasE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_unidad_medida,nombre_unidad_medida from unidades_medidas where cod_unidad_medida =? and estado =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Unidad de Medida inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         UnidadesMedidasE unidadMedida = new UnidadesMedidasE(resultado.getInt("cod_unidad_medida"), resultado.getString("nombre_unidad_medida"));
         var8 = unidadMedida;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Unidad de Medida..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Unidad de Medida....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static UnidadesMedidasE buscarUnidadMedida(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "Select cod_unidad_medida,nombre_unidad_medida,estado,sigla from unidades_medidas where cod_unidad_medida =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new UnidadesMedidasE(
               resultado.getInt("cod_unidad_medida"), resultado.getString("nombre_unidad_medida"), resultado.getInt("estado"), resultado.getString("sigla")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Unidad de Medida..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Unidad de Medida..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public UnidadesMedidasE(int cod_unidad_medida, String nombre_unidad_medida) {
      this.cod_unidad_medida = cod_unidad_medida;
      this.nombre_unidad_medida = nombre_unidad_medida;
   }

   public UnidadesMedidasE(int cod_unidad_medida, String nombre_unidad_medida, int estado, String sigla) {
      this.cod_unidad_medida = cod_unidad_medida;
      this.nombre_unidad_medida = nombre_unidad_medida;
      this.estado = estado;
      this.sigla = sigla;
   }

   public int getCod_unidad_medida() {
      return this.cod_unidad_medida;
   }

   public void setCod_unidad_medida(int cod_unidad_medida) {
      this.cod_unidad_medida = cod_unidad_medida;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_unidad_medida() {
      return this.nombre_unidad_medida;
   }

   public void setNombre_unidad_medida(String nombre_unidad_medida) {
      this.nombre_unidad_medida = nombre_unidad_medida;
   }

   public String getSigla() {
      return this.sigla;
   }

   public void setSigla(String sigla) {
      this.sigla = sigla;
   }
}
