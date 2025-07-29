package a0099VentasLugares;

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

public class VentasLugaresE {
   private int cod_lugar;
   private int estado;
   private String nombre_lugar;

   public static int eliminarLugarVta(VentasLugaresE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMarca = conexion.prepareStatement("delete from ventas_lugares where cod_lugar =?");
         psEliminarMarca.setInt(1, entidad.getCod_lugar());
         psEliminarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Lugar de Venta.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Lugar de Venta..", frame);
      } finally {
         new CerrarRecursos(psEliminarMarca, frame, "Error al eliminar Lugar de Venta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarLugarVta(VentasLugaresE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarMarcas = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarMarcas = conexion.prepareStatement("update ventas_lugares set nombre_lugar=?,estado=? where cod_lugar =?");
         psActualizarMarcas.setString(1, entidad.getNombre_lugar());
         psActualizarMarcas.setInt(2, entidad.getEstado());
         psActualizarMarcas.setInt(3, entidad.getCod_lugar());
         psActualizarMarcas.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Lugar de Venta.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Lugar de Venta..", frame);
      } finally {
         new CerrarRecursos(psActualizarMarcas, frame, "Error al actualizar Lugar de Venta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarLugarVta(VentasLugaresE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_lugar) as mayor from ventas_lugares");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarMarca = conexion.prepareStatement("insert into ventas_lugares (cod_lugar,nombre_lugar,estado) values(?,?,?)");
         psInsertarMarca.setInt(1, siguienteRegistro);
         psInsertarMarca.setString(2, entidad.getNombre_lugar());
         psInsertarMarca.setInt(3, entidad.getEstado());
         psInsertarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Lugar de Venta.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Lugar de Venta..", frame);
      } finally {
         new CerrarRecursos(psInsertarMarca, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Lugar de Venta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarLugaresVtasTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Lugar de Venta..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Lugar de Venta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static VentasLugaresE buscarLugarVta2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      VentasLugaresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_lugar,nombre_lugar from ventas_lugares where cod_lugar =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Lugar de Venta inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         VentasLugaresE lugarVta = new VentasLugaresE(resultado.getInt("cod_lugar"), resultado.getString("nombre_lugar"));
         var8 = lugarVta;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Lugar de Venta..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Lugar de Venta....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static VentasLugaresE buscarLugarVta2(int codigo, JinternalPadreString frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      VentasLugaresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_lugar,nombre_lugar from ventas_lugares where cod_lugar =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Lugar de Venta inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         VentasLugaresE marca = new VentasLugaresE(resultado.getInt("cod_lugar"), resultado.getString("nombre_lugar"));
         var8 = marca;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Lugar de Venta..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Lugar de Venta....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static VentasLugaresE buscarLugarVta(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_lugar,nombre_lugar,estado from ventas_lugares where cod_lugar =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new VentasLugaresE(resultado.getInt("cod_lugar"), resultado.getString("nombre_lugar"), resultado.getInt("estado"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Lugar de Venta..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Lugar de Venta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public VentasLugaresE(int cod_lugar, String nombre_lugar) {
      this.cod_lugar = cod_lugar;
      this.nombre_lugar = nombre_lugar;
   }

   public VentasLugaresE(int cod_lugar, String nombre_lugar, int estado) {
      this.cod_lugar = cod_lugar;
      this.nombre_lugar = nombre_lugar;
      this.estado = estado;
   }

   public int getCod_lugar() {
      return this.cod_lugar;
   }

   public void setCod_lugar(int cod_lugar) {
      this.cod_lugar = cod_lugar;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_lugar() {
      return this.nombre_lugar;
   }

   public void setNombre_lugar(String nombre_lugar) {
      this.nombre_lugar = nombre_lugar;
   }
}
