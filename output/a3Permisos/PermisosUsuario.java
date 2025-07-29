package a3Permisos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;

public class PermisosUsuario {
   private static PermisosUsuario instancia;
   private static Map<String, Permiso> permisos;

   private PermisosUsuario() {
      permisos = new HashMap<>();
   }

   public static PermisosUsuario getInstancia() {
      if (instancia == null) {
         instancia = new PermisosUsuario();
      }

      return instancia;
   }

   public void cargarPermisosDesdeBD(String usuario, JFrame frame) {
      permisos.clear();
      ResultSet resultado = null;
      ResultSet resultado2 = null;
      ResultSet resultado3 = null;
      PreparedStatement preparedStatementSelect = null;
      PreparedStatement preparedStatementSelect2 = null;
      PreparedStatement preparedStatementSelect3 = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\nsys_permisos.menu,sys_permisos.grupo,sys_permisos.ver,\r\nsys_permisos.agregar,sys_permisos.modificar,sys_permisos.eliminar,\r\nsys_menus.id,sys_menus.parent\r\nfrom sys_permisos,sys_usuarios,sys_menus\r\nwhere\r\nsys_usuarios.grupo = sys_permisos.grupo\r\nand sys_permisos.menu = sys_menus.menu\r\nand sys_menus.parent = sys_menus.id and sys_permisos.ver = 1 and sys_usuarios.usuario = ?"
         );
         preparedStatementSelect.setString(1, usuario);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            String menu = resultado.getString("menu");
            Permiso permiso = new Permiso(
               resultado.getBoolean("ver"), resultado.getBoolean("agregar"), resultado.getBoolean("modificar"), resultado.getBoolean("eliminar")
            );
            permisos.put(menu, permiso);
            preparedStatementSelect2 = conexion.prepareStatement(
               "select\r\nsys_permisos.menu,sys_permisos.grupo,sys_permisos.ver,\r\nsys_permisos.agregar,sys_permisos.modificar,sys_permisos.eliminar,\r\nsys_menus.id,sys_menus.parent,sys_menus.tipo\r\nfrom sys_permisos,sys_usuarios,sys_menus\r\nwhere\r\nsys_usuarios.grupo = sys_permisos.grupo\r\nand sys_permisos.menu = sys_menus.menu and sys_menus.parent =? and sys_usuarios.usuario = ?"
            );
            preparedStatementSelect2.setInt(1, resultado.getInt("parent"));
            preparedStatementSelect2.setString(2, usuario);
            resultado2 = preparedStatementSelect2.executeQuery();

            while (resultado2.next()) {
               menu = resultado2.getString("menu");
               permiso = new Permiso(
                  resultado2.getBoolean("ver"), resultado2.getBoolean("agregar"), resultado2.getBoolean("modificar"), resultado2.getBoolean("eliminar")
               );
               permisos.put(menu, permiso);
               if (resultado2.getInt("tipo") == 1) {
                  preparedStatementSelect3 = conexion.prepareStatement(
                     "select\r\nsys_permisos.menu,sys_permisos.grupo,sys_permisos.ver,\r\nsys_permisos.agregar,sys_permisos.modificar,sys_permisos.eliminar,\r\nsys_menus.id,sys_menus.parent,sys_menus.tipo\r\nfrom sys_permisos,sys_usuarios,sys_menus\r\nwhere\r\nsys_usuarios.grupo = sys_permisos.grupo \r\nand sys_permisos.menu = sys_menus.menu and sys_menus.parent =? and sys_usuarios.usuario = ?"
                  );
                  preparedStatementSelect3.setInt(1, resultado2.getInt("id"));
                  preparedStatementSelect3.setString(2, usuario);
                  resultado3 = preparedStatementSelect3.executeQuery();

                  while (resultado3.next()) {
                     menu = resultado3.getString("menu");
                     permiso = new Permiso(
                        resultado3.getBoolean("ver"), resultado3.getBoolean("agregar"), resultado3.getBoolean("modificar"), resultado3.getBoolean("eliminar")
                     );
                     permisos.put(menu, permiso);
                  }
               }
            }
         }
      } catch (Exception var16) {
         LogErrores.errores(var16, "Error al seleccionar Permisos para el Usuario..", frame);
      } finally {
         new CerrarRecursos(
            preparedStatementSelect,
            preparedStatementSelect2,
            preparedStatementSelect3,
            resultado,
            resultado2,
            resultado3,
            "Error al seleccionar Permisos para el Usuario..",
            frame
         );
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public Map<String, Permiso> getPermisos() {
      return permisos;
   }

   public boolean tienePermiso(String menu, String accion) {
      Permiso permiso = permisos.get(menu);
      if (permiso == null) {
         return false;
      } else {
         String var4;
         switch ((var4 = accion.toLowerCase()).hashCode()) {
            case -1901048086:
               if (var4.equals("modificar")) {
                  return permiso.isModificar();
               }
               break;
            case -1049261217:
               if (var4.equals("agregar")) {
                  return permiso.isAgregar();
               }
               break;
            case 116643:
               if (var4.equals("ver")) {
                  return permiso.isVer();
               }
               break;
            case 106295969:
               if (var4.equals("eliminar")) {
                  return permiso.isEliminar();
               }
         }

         return false;
      }
   }
}
