package a3Permisos;

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

public class UsuariosE {
   private int cod_vendedor;
   private int cod_cajero;
   private int cod_comprador;
   private int estado;
   private int cod_sucursal;
   private String nombre_vendedor;
   private String nombre_cajero;
   private String nombre_comprador;
   private String nombre_sucursal;
   private String usuario;
   private String nombre_usuario;
   private String grupo;

   public static int actualizarPassword(String usuario, String nuevoPassword, JDialog frame) {
      PreparedStatement psActualizarPassword = null;
      PreparedStatement psEjecutarFlushPrivileges = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         String sql = "ALTER USER `" + usuario + "`@'%' IDENTIFIED BY ?";
         psActualizarPassword = conexion.prepareStatement(sql);
         psActualizarPassword.setString(1, nuevoPassword);
         psActualizarPassword.executeUpdate();
         psActualizarPassword.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            var12.printStackTrace();
            LogErrores.errores(var12, "Error al actualizar Contraseña.", frame);
         }

         var13.printStackTrace();
         LogErrores.errores(var13, "Error al actualizar Contraseña..", frame);
      } finally {
         new CerrarRecursos(psActualizarPassword, psEjecutarFlushPrivileges, frame, "Error al actualizar Contraseña..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarPassword(String usuario, String nuevoPassword, JinternalPadre frame) {
      PreparedStatement psActualizarPassword = null;
      PreparedStatement psEjecutarFlushPrivileges = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         String sql = "ALTER USER `" + usuario + "`@'localhost' IDENTIFIED BY ?";
         psActualizarPassword = conexion.prepareStatement(sql);
         psActualizarPassword.setString(1, nuevoPassword);
         String sentenciaSQL = "FLUSH PRIVILEGES";
         psEjecutarFlushPrivileges = conexion.prepareStatement(sentenciaSQL);
         psActualizarPassword.executeUpdate();
         psEjecutarFlushPrivileges.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            var13.printStackTrace();
            LogErrores.errores(var13, "Error al actualizar Contraseña.", frame);
         }

         var14.printStackTrace();
         LogErrores.errores(var14, "Error al actualizar Contraseña..", frame);
      } finally {
         new CerrarRecursos(psActualizarPassword, psEjecutarFlushPrivileges, frame, "Error al actualizar Contraseña..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int eliminarUsuario(String usuario, JinternalPadrePermisos frame) {
      PreparedStatement psEliminarUsuario = null;
      PreparedStatement psEliminarUsuarioBD = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarUsuario = conexion.prepareStatement("delete from sys_usuarios where usuario = ?");
         psEliminarUsuario.setString(1, usuario);
         String sentenciaSQL = "DROP USER '" + usuario + "'@'%'";
         psEliminarUsuarioBD = conexion.prepareStatement(sentenciaSQL);
         psEliminarUsuarioBD.executeUpdate();
         psEliminarUsuario.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var12) {
         try {
            conexion.rollback();
         } catch (SQLException var11) {
            var11.printStackTrace();
            LogErrores.errores(var11, "Error al eliminar Usuario.", frame);
         }

         var12.printStackTrace();
         LogErrores.errores(var12, "Error al eliminar Usuario..", frame);
      } finally {
         new CerrarRecursos(psEliminarUsuario, psEliminarUsuarioBD, frame, "Error al eliminar Usuario..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarUsuario(UsuariosE entidad, JinternalPadrePermisos frame) {
      PreparedStatement psActualizarUsuario = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarUsuario = conexion.prepareStatement(
            "update sys_usuarios set  nombre_usuario=?,estado=?,cod_vendedor=?,cod_cajero=?,cod_comprador=?,cod_sucursal =? where usuario =?"
         );
         psActualizarUsuario.setString(1, entidad.getNombre_usuario());
         psActualizarUsuario.setInt(2, entidad.getEstado());
         psActualizarUsuario.setInt(3, entidad.getCod_vendedor());
         psActualizarUsuario.setInt(4, entidad.getCod_cajero());
         psActualizarUsuario.setInt(5, entidad.getCod_comprador());
         psActualizarUsuario.setInt(6, entidad.getCod_sucursal());
         psActualizarUsuario.setString(7, entidad.getUsuario());
         psActualizarUsuario.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            var10.printStackTrace();
            LogErrores.errores(var10, "Error al actualizar Usuario.", frame);
         }

         var11.printStackTrace();
         LogErrores.errores(var11, "Error al actualizar Usuario..", frame);
      } finally {
         new CerrarRecursos(psActualizarUsuario, frame, "Error al actualizar Usuario..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarUsuario(UsuariosE entidad, JinternalPadrePermisos frame) {
      PreparedStatement psInsertarUsuario = null;
      PreparedStatement psCrearUsuario = null;
      PreparedStatement psDarPermisos = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psInsertarUsuario = conexion.prepareStatement(
            "insert into sys_usuarios (usuario,nombre_usuario,estado,grupo,cod_vendedor,cod_cajero,cod_comprador,cod_sucursal) values (?,?,?,?,?,?,?,?)"
         );
         psInsertarUsuario.setString(1, entidad.getUsuario());
         psInsertarUsuario.setString(2, entidad.getNombre_usuario());
         psInsertarUsuario.setInt(3, entidad.getEstado());
         psInsertarUsuario.setString(4, entidad.getGrupo());
         psInsertarUsuario.setInt(5, entidad.getCod_vendedor());
         psInsertarUsuario.setInt(6, entidad.getCod_cajero());
         psInsertarUsuario.setInt(7, entidad.getCod_comprador());
         psInsertarUsuario.setInt(8, entidad.getCod_sucursal());
         String usuario = entidad.getUsuario();
         String sentenciaSQL = "CREATE USER '" + usuario + "'@'%' IDENTIFIED BY '123'";
         psCrearUsuario = conexion.prepareStatement(sentenciaSQL);
         String nombreUsuario = entidad.getUsuario().replace("`", "``");
         String sentenciaSQL2 = "GRANT ALL PRIVILEGES ON *.* TO `" + nombreUsuario + "`@'%'";
         psDarPermisos = conexion.prepareStatement(sentenciaSQL2);
         psInsertarUsuario.executeUpdate();
         psCrearUsuario.executeUpdate();
         psDarPermisos.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var16) {
         try {
            conexion.rollback();
         } catch (SQLException var15) {
            var15.printStackTrace();
            LogErrores.errores(var15, "Error al insertar Usuario.", frame);
         }

         var16.printStackTrace();
         LogErrores.errores(var16, "Error al insertar Usuario..", frame);
      } finally {
         new CerrarRecursos(psInsertarUsuario, psCrearUsuario, psDarPermisos, frame, "Error al insertar Usuario..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static UsuariosE buscarDatosUsuarios(String usuario, JinternalPadrePermisos frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\nsys_usuarios.usuario,sys_usuarios.nombre_usuario,sys_usuarios.estado,sys_usuarios.cod_vendedor,\r\nifnull((select nombre_vendedor from vendedores where vendedores.cod_vendedor = sys_usuarios.cod_vendedor),'SIN DEFINIR') as nombre_vendedor,\r\nsys_usuarios.cod_cajero,\r\nifnull((select nombre_cajero from cajeros where cajeros.cod_cajero = sys_usuarios.cod_cajero),'SIN DEFINIR') as nombre_cajero,\r\nsys_usuarios.cod_comprador,sys_usuarios.cod_sucursal,\r\nifnull((select nombre_comprador from compradores where compradores.cod_comprador = sys_usuarios.cod_comprador),'SIN DEFINIR') as nombre_comprador, ifnull((select nombre_sucursal from sucursales where sucursales.cod_sucursal = sys_usuarios.cod_sucursal),'SIN DEFINIR') as nombre_sucursal  from sys_usuarios where usuario =?"
         );
         preparedStatementSelect.setString(1, usuario);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new UsuariosE(
               resultado.getString("usuario"),
               resultado.getString("nombre_usuario"),
               resultado.getInt("estado"),
               resultado.getInt("cod_vendedor"),
               resultado.getString("nombre_vendedor"),
               resultado.getInt("cod_cajero"),
               resultado.getString("nombre_cajero"),
               resultado.getInt("cod_comprador"),
               resultado.getString("nombre_comprador"),
               resultado.getInt("cod_sucursal"),
               resultado.getString("nombre_sucursal")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Usuarios..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Usuarios....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static void cargarUsuarios(String grupo, JinternalPadrePermisos frame, ModeloTablaDefecto modelo) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select usuario,estado from sys_usuarios where grupo =?");
         preparedStatementSelect.setString(1, grupo);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getString("usuario"), resultado.getBoolean("estado")});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Usuarios..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Usuarios....");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public UsuariosE(
      String usuario,
      String nombre_usuario,
      int estado,
      int cod_vendedor,
      String nombre_vendedor,
      int cod_cajero,
      String nombre_cajero,
      int cod_comprador,
      String nombre_comprador,
      int cod_sucursal,
      String nombre_sucursal
   ) {
      this.usuario = usuario;
      this.nombre_usuario = nombre_usuario;
      this.estado = estado;
      this.cod_vendedor = cod_vendedor;
      this.cod_cajero = cod_cajero;
      this.cod_comprador = cod_comprador;
      this.nombre_vendedor = nombre_vendedor;
      this.nombre_cajero = nombre_cajero;
      this.nombre_comprador = nombre_comprador;
      this.cod_sucursal = cod_sucursal;
      this.nombre_sucursal = nombre_sucursal;
   }

   public UsuariosE(String usuario, String nombre_usuario, String grupo, int estado, int cod_vendedor, int cod_cajero, int cod_comprador, int cod_sucursal) {
      this.usuario = usuario;
      this.nombre_usuario = nombre_usuario;
      this.grupo = grupo;
      this.estado = estado;
      this.cod_vendedor = cod_vendedor;
      this.cod_cajero = cod_cajero;
      this.cod_comprador = cod_comprador;
      this.cod_sucursal = cod_sucursal;
   }

   public int getCod_vendedor() {
      return this.cod_vendedor;
   }

   public void setCod_vendedor(int cod_vendedor) {
      this.cod_vendedor = cod_vendedor;
   }

   public int getCod_cajero() {
      return this.cod_cajero;
   }

   public void setCod_cajero(int cod_cajero) {
      this.cod_cajero = cod_cajero;
   }

   public int getCod_comprador() {
      return this.cod_comprador;
   }

   public void setCod_comprador(int cod_comprador) {
      this.cod_comprador = cod_comprador;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getUsuario() {
      return this.usuario;
   }

   public void setUsuario(String usuario) {
      this.usuario = usuario;
   }

   public String getNombre_usuario() {
      return this.nombre_usuario;
   }

   public void setNombre_usuario(String nombre_usuario) {
      this.nombre_usuario = nombre_usuario;
   }

   public String getGrupo() {
      return this.grupo;
   }

   public void setGrupo(String grupo) {
      this.grupo = grupo;
   }

   public String getNombre_vendedor() {
      return this.nombre_vendedor;
   }

   public void setNombre_vendedor(String nombre_vendedor) {
      this.nombre_vendedor = nombre_vendedor;
   }

   public String getNombre_cajero() {
      return this.nombre_cajero;
   }

   public void setNombre_cajero(String nombre_cajero) {
      this.nombre_cajero = nombre_cajero;
   }

   public String getNombre_comprador() {
      return this.nombre_comprador;
   }

   public void setNombre_comprador(String nombre_comprador) {
      this.nombre_comprador = nombre_comprador;
   }

   public int getCod_sucursal() {
      return this.cod_sucursal;
   }

   public void setCod_sucursal(int cod_sucursal) {
      this.cod_sucursal = cod_sucursal;
   }

   public String getNombre_sucursal() {
      return this.nombre_sucursal;
   }

   public void setNombre_sucursal(String nombre_sucursal) {
      this.nombre_sucursal = nombre_sucursal;
   }
}
