package cajeros;

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

public class CajerosE {
   private int cod_cajero;
   private int estado;
   private int tesorero;
   private String nombre_cajero;

   public static int eliminarCajeros(CajerosE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMarca = conexion.prepareStatement("delete from cajeros where cod_cajero =?");
         psEliminarMarca.setInt(1, entidad.getCod_cajero());
         psEliminarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Cajero.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Cajero..", frame);
      } finally {
         new CerrarRecursos(psEliminarMarca, frame, "Error al eliminar Cajero..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarCajeros(CajerosE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarMarcas = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarMarcas = conexion.prepareStatement("update cajeros set nombre_cajero=?,estado=?,tesorero=? where cod_cajero =?");
         psActualizarMarcas.setString(1, entidad.getNombre_cajero());
         psActualizarMarcas.setInt(2, entidad.getEstado());
         psActualizarMarcas.setInt(3, entidad.getTesorero());
         psActualizarMarcas.setInt(4, entidad.getCod_cajero());
         psActualizarMarcas.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Cajero.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Cajero..", frame);
      } finally {
         new CerrarRecursos(psActualizarMarcas, frame, "Error al actualizar Cajero..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarCajeros(CajerosE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarCajero = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_cajero) as mayor from cajeros");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarCajero = conexion.prepareStatement("insert into cajeros (cod_cajero,nombre_cajero,estado,tesorero) values(?,?,?,?)");
         psInsertarCajero.setInt(1, siguienteRegistro);
         psInsertarCajero.setString(2, entidad.getNombre_cajero());
         psInsertarCajero.setInt(3, entidad.getEstado());
         psInsertarCajero.setInt(4, entidad.getTesorero());
         psInsertarCajero.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Cajero.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Cajero..", frame);
      } finally {
         new CerrarRecursos(psInsertarCajero, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Cajero..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarCajerosTablaDialog(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Cajeros..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cajeros..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarCajerosTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Cajeros..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cajeros..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static CajerosE buscarCajeros2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CajerosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_cajero,nombre_cajero from cajeros where cod_cajero =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Cajero inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CajerosE cajero = new CajerosE(resultado.getInt("cod_cajero"), resultado.getString("nombre_cajero"));
         var8 = cajero;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cajeros..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cajeros....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static CajerosE buscarCajeros2(String usuario, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CajerosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cajeros.cod_cajero,cajeros.nombre_cajero from sys_usuarios,cajeros where sys_usuarios.cod_cajero = cajeros.cod_cajero and cajeros.estado =1 and sys_usuarios.usuario =?"
         );
         preparedStatementSelect.setString(1, usuario);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Cajero inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CajerosE cajero = new CajerosE(resultado.getInt("cod_cajero"), resultado.getString("nombre_cajero"));
         var8 = cajero;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cajeros..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cajeros....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static CajerosE buscarCajeros2Dialog(int codigo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CajerosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_cajero,nombre_cajero from cajeros where cod_cajero =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Cajero inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CajerosE cajero = new CajerosE(resultado.getInt("cod_cajero"), resultado.getString("nombre_cajero"));
         var8 = cajero;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cajeros..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cajeros....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void buscarCajerosF3(JinternalPadre frame, ModeloTablaDefecto modelo) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_cajero,nombre_cajero from cajeros where estado =1");
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt("cod_cajero"), resultado.getString("nombre_cajero")});
         }
      } catch (Exception var9) {
         LogErrores.errores(var9, "Error al seleccionar Cajeros..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cajeros....");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static CajerosE buscarCajero(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_cajero,nombre_cajero,estado,tesorero from cajeros where cod_cajero =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new CajerosE(resultado.getInt("cod_cajero"), resultado.getString("nombre_cajero"), resultado.getInt("estado"), resultado.getInt("tesorero"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cajero..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cajero..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public CajerosE(String nombre_cajero) {
      this.nombre_cajero = nombre_cajero;
   }

   public CajerosE(int cod_cajero, String nombre_cajero) {
      this.cod_cajero = cod_cajero;
      this.nombre_cajero = nombre_cajero;
   }

   public CajerosE(int cod_cajero, String nombre_cajero, int estado, int tesorero) {
      this.cod_cajero = cod_cajero;
      this.nombre_cajero = nombre_cajero;
      this.estado = estado;
      this.tesorero = tesorero;
   }

   public int getCod_cajero() {
      return this.cod_cajero;
   }

   public void setCod_cajero(int cod_cajero) {
      this.cod_cajero = cod_cajero;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_cajero() {
      return this.nombre_cajero;
   }

   public void setNombre_cajero(String nombre_cajero) {
      this.nombre_cajero = nombre_cajero;
   }

   public int getTesorero() {
      return this.tesorero;
   }

   public void setTesorero(int tesorero) {
      this.tesorero = tesorero;
   }
}
