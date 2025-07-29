package a5Sucursales;

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

public class SucursalesE {
   private int cod_sucursal;
   private int estado;
   private String nombre_sucursal;
   private String telefono;
   private String direccion;

   public static void cargarSucursalesTablaDialog(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Sucursales..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Sucursales..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static SucursalesE buscarSucursales2Dialog(int codigo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      SucursalesE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_sucursal,nombre_sucursal from sucursales where cod_sucursal =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Sucursal inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         SucursalesE sucursal = new SucursalesE(resultado.getInt("cod_sucursal"), resultado.getString("nombre_sucursal"));
         var8 = sucursal;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Sucursales..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Sucursales....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static int eliminarSucursales(SucursalesE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarSucursal = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarSucursal = conexion.prepareStatement("delete from sucursales where cod_sucursal =?");
         psEliminarSucursal.setInt(1, entidad.getCod_sucursal());
         psEliminarSucursal.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Sucursal.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Sucursal..", frame);
      } finally {
         new CerrarRecursos(psEliminarSucursal, frame, "Error al eliminar Sucursal..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarSucursales(SucursalesE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarSucursales = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarSucursales = conexion.prepareStatement("update sucursales set nombre_sucursal=?,estado=?,telefono=?,direccion=? where cod_sucursal = ?");
         psActualizarSucursales.setString(1, entidad.getNombre_sucursal());
         psActualizarSucursales.setInt(2, entidad.getEstado());
         psActualizarSucursales.setString(3, entidad.getTelefono());
         psActualizarSucursales.setString(4, entidad.getDireccion());
         psActualizarSucursales.setInt(5, entidad.getCod_sucursal());
         psActualizarSucursales.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Sucursal.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Sucursal..", frame);
      } finally {
         new CerrarRecursos(psActualizarSucursales, frame, "Error al actualizar Sucursal..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarSucursales(SucursalesE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarSucursales = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_sucursal) as mayor from sucursales");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarSucursales = conexion.prepareStatement("insert into sucursales (cod_sucursal,nombre_sucursal,estado,telefono,direccion) values(?,?,?,?,?)");
         psInsertarSucursales.setInt(1, siguienteRegistro);
         psInsertarSucursales.setString(2, entidad.getNombre_sucursal());
         psInsertarSucursales.setInt(3, entidad.getEstado());
         psInsertarSucursales.setString(4, entidad.getTelefono());
         psInsertarSucursales.setString(5, entidad.getDireccion());
         psInsertarSucursales.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Sucursal.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Sucursal..", frame);
      } finally {
         new CerrarRecursos(psInsertarSucursales, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Sucursal..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarSucursalesTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Sucursales..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Sucursales..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static SucursalesE buscarSucursales2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      SucursalesE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_sucursal,nombre_sucursal from sucursales where cod_sucursal =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Sucursal inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         SucursalesE sucursal = new SucursalesE(resultado.getInt("cod_sucursal"), resultado.getString("nombre_sucursal"));
         var8 = sucursal;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Sucursal..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Sucursal....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static SucursalesE buscarSucursal(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "Select cod_sucursal,nombre_sucursal,estado,telefono,direccion from sucursales where cod_sucursal =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new SucursalesE(
               resultado.getInt("cod_sucursal"),
               resultado.getString("nombre_sucursal"),
               resultado.getInt("estado"),
               resultado.getString("telefono"),
               resultado.getString("direccion")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Sucursal..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Sucursal..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public SucursalesE(int cod_sucursal, String nombre_sucursal) {
      this.cod_sucursal = cod_sucursal;
      this.nombre_sucursal = nombre_sucursal;
   }

   public SucursalesE(int cod_sucursal, String nombre_sucursal, int estado, String telefono, String direccion) {
      this.cod_sucursal = cod_sucursal;
      this.nombre_sucursal = nombre_sucursal;
      this.estado = estado;
      this.telefono = telefono;
      this.direccion = direccion;
   }

   public int getCod_sucursal() {
      return this.cod_sucursal;
   }

   public void setCod_sucursal(int cod_sucursal) {
      this.cod_sucursal = cod_sucursal;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_sucursal() {
      return this.nombre_sucursal;
   }

   public void setNombre_sucursal(String nombre_sucursal) {
      this.nombre_sucursal = nombre_sucursal;
   }

   public String getTelefono() {
      return this.telefono;
   }

   public void setTelefono(String telefono) {
      this.telefono = telefono;
   }

   public String getDireccion() {
      return this.direccion;
   }

   public void setDireccion(String direccion) {
      this.direccion = direccion;
   }
}
