package a009AjusteStockMotivo;

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
import utilidadesVentanas.JinternalPadreReporte;

public class AjusteMotivosE {
   private int cod_motivo;
   private int estado;
   private int tipo;
   private String nombre_motivo;

   public static int eliminarMotivoAjuste(AjusteMotivosE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMarca = conexion.prepareStatement("delete from ajuste_stock_motivos where cod_motivo =?");
         psEliminarMarca.setInt(1, entidad.getCod_motivo());
         psEliminarMarca.executeUpdate();
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
         new CerrarRecursos(psEliminarMarca, frame, "Error al eliminar Motivo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarMotivosAjuste(AjusteMotivosE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarMonedas = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarMonedas = conexion.prepareStatement("update ajuste_stock_motivos set nombre_motivo=?,estado=?,tipo=? where cod_motivo =?");
         psActualizarMonedas.setString(1, entidad.getNombre_motivo());
         psActualizarMonedas.setInt(2, entidad.getEstado());
         psActualizarMonedas.setInt(3, entidad.getTipo());
         psActualizarMonedas.setInt(4, entidad.getCod_motivo());
         psActualizarMonedas.executeUpdate();
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
         new CerrarRecursos(psActualizarMonedas, frame, "Error al actualizar Motivo..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarMotivosAjuste(AjusteMotivosE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarMoneda = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_motivo) as mayor from ajuste_stock_motivos");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarMoneda = conexion.prepareStatement("insert into ajuste_stock_motivos (cod_motivo,nombre_motivo,estado,tipo) values(?,?,?,?)");
         psInsertarMoneda.setInt(1, siguienteRegistro);
         psInsertarMoneda.setString(2, entidad.getNombre_motivo());
         psInsertarMoneda.setInt(3, entidad.getEstado());
         psInsertarMoneda.setInt(4, entidad.getTipo());
         psInsertarMoneda.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Ajuste.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Ajuste..", frame);
      } finally {
         new CerrarRecursos(psInsertarMoneda, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Ajuste..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static AjusteMotivosE buscarMotivosAjuste2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      AjusteMotivosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_motivo,nombre_motivo from ajuste_stock_motivos where cod_motivo =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Motivo inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         AjusteMotivosE ajusteMotivo = new AjusteMotivosE(resultado.getInt("cod_motivo"), resultado.getString("nombre_motivo"));
         var8 = ajusteMotivo;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Motivos..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Motivos....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static AjusteMotivosE buscarMotivosAjuste2(int codigo, JinternalPadreReporte frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      AjusteMotivosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_motivo,nombre_motivo from ajuste_stock_motivos where cod_motivo =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Motivo inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         AjusteMotivosE ajusteMotivo = new AjusteMotivosE(resultado.getInt("cod_motivo"), resultado.getString("nombre_motivo"));
         var8 = ajusteMotivo;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Motivos..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Motivos....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void cargarMotivosAjusteTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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

   public static AjusteMotivosE buscarAjusteMotivo(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_motivo,nombre_motivo,estado,tipo from ajuste_stock_motivos where cod_motivo =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new AjusteMotivosE(
               resultado.getInt("cod_motivo"), resultado.getString("nombre_motivo"), resultado.getInt("estado"), resultado.getInt("tipo")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Motivo de Ajuste..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Motivo de Ajuste..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public AjusteMotivosE(int cod_motivo, String nombre_motivo) {
      this.cod_motivo = cod_motivo;
      this.nombre_motivo = nombre_motivo;
   }

   public AjusteMotivosE(int cod_motivo, String nombre_motivo, int estado, int tipo) {
      this.cod_motivo = cod_motivo;
      this.nombre_motivo = nombre_motivo;
      this.estado = estado;
      this.tipo = tipo;
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

   public int getTipo() {
      return this.tipo;
   }

   public void setTipo(int tipo) {
      this.tipo = tipo;
   }

   public String getNombre_motivo() {
      return this.nombre_motivo;
   }

   public void setNombre_motivo(String nombre_motivo) {
      this.nombre_motivo = nombre_motivo;
   }
}
