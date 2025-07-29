package a3Permisos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaConParametro;
import utilidadesVentanas.JinternalPadrePermisos;

public class GruposE {
   private String usuario;
   private String menu;
   private String grupo;
   private int estado;
   private int ver;
   private int agregar;
   private int modificar;
   private int eliminar;

   public static int insertarGrupos(GruposE entidad, JinternalPadrePermisos frame) {
      PermisosUsuario permisos = PermisosUsuario.getInstancia();
      if (permisos.tienePermiso("GrupoUsuario", "agregar")) {
         PreparedStatement psInsertarGrupo = null;
         PreparedStatement psInsertarPermisos = null;
         Connection conexion = null;

         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            psInsertarGrupo = conexion.prepareStatement("insert into sys_grupos (grupo, estado) values (?,?)");
            psInsertarGrupo.setString(1, entidad.getGrupo());
            psInsertarGrupo.setInt(2, entidad.getEstado());
            psInsertarPermisos = conexion.prepareStatement("insert into sys_permisos select menu, ?,0,0,0,0 from sys_menus");
            psInsertarPermisos.setString(1, entidad.getGrupo());
            psInsertarGrupo.executeUpdate();
            psInsertarPermisos.executeUpdate();
            conexion.commit();
            return 1;
         } catch (SQLException var13) {
            try {
               conexion.rollback();
            } catch (SQLException var12) {
               LogErrores.errores(var12, "Error al insertar Grupo.", frame);
            }

            LogErrores.errores(var13, "Error al insertar Grupo..", frame);
         } finally {
            new CerrarRecursos(psInsertarGrupo, psInsertarPermisos, frame, "Error al insertar Grupo..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No tienes permisos para insertar !", "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         return 0;
      }
   }

   public static int eliminarGrupos(String grupo, JinternalPadrePermisos frame) {
      PermisosUsuario permisos = PermisosUsuario.getInstancia();
      if (permisos.tienePermiso("GrupoUsuario", "eliminar")) {
         PreparedStatement psEliminarGrupo = null;
         PreparedStatement psEliminarPermisos = null;
         Connection conexion = null;

         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            psEliminarPermisos = conexion.prepareStatement("delete from sys_permisos where grupo =?");
            psEliminarPermisos.setString(1, grupo);
            psEliminarGrupo = conexion.prepareStatement("delete from sys_grupos where grupo =?");
            psEliminarGrupo.setString(1, grupo);
            psEliminarPermisos.executeUpdate();
            psEliminarGrupo.executeUpdate();
            conexion.commit();
            return 1;
         } catch (SQLException var13) {
            try {
               conexion.rollback();
            } catch (SQLException var12) {
               LogErrores.errores(var12, "Error al eliminar Grupo.", frame);
            }

            LogErrores.errores(var13, "Error al eliminar Grupo..", frame);
         } finally {
            new CerrarRecursos(psEliminarGrupo, psEliminarPermisos, frame, "Error al eliminar Grupo..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No tienes permisos para eliminar !", "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         return 0;
      }
   }

   public static int actualizarGrupos(TablaConParametro tablaGrupos, JinternalPadrePermisos frame) {
      PermisosUsuario permisos = PermisosUsuario.getInstancia();
      if (permisos.tienePermiso("GrupoUsuario", "modificar")) {
         int grupo = tablaGrupos.getColumn("Nombre Grupo").getModelIndex();
         int estado = tablaGrupos.getColumn("Estado").getModelIndex();
         PreparedStatement psActualizarPermisos = null;
         Connection conexion = null;

         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            psActualizarPermisos = conexion.prepareStatement("update sys_grupos set estado=? where grupo = ?");

            for (int fila = 0; fila < tablaGrupos.getRowCount(); fila++) {
               psActualizarPermisos.setBoolean(1, (Boolean)tablaGrupos.getValueAt(fila, estado));
               psActualizarPermisos.setString(2, String.valueOf(tablaGrupos.getValueAt(fila, grupo)));
               psActualizarPermisos.addBatch();
            }

            psActualizarPermisos.executeBatch();
            conexion.commit();
            return 1;
         } catch (SQLException var14) {
            try {
               conexion.rollback();
            } catch (SQLException var13) {
               LogErrores.errores(var13, "Error al actualizar grupos.", frame);
            }

            LogErrores.errores(var14, "Error al actualizar grupos..", frame);
         } finally {
            new CerrarRecursos(psActualizarPermisos, frame, "Error al actualizar grupos..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No tienes permisos para modificar !", "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         return 0;
      }
   }

   public static void buscarPermisos(String grupo, JinternalPadrePermisos frame, ModeloTablaDefecto modelo) {
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
            "select sys_permisos.menu,sys_permisos.ver,sys_permisos.agregar,sys_permisos.modificar,sys_permisos.eliminar,\r\nsys_menus.id,sys_menus.parent\r\n from sys_permisos,sys_menus\r\nwhere\r\nsys_permisos.menu = sys_menus.menu and sys_menus.id = sys_menus.parent  and sys_permisos.grupo =? order by sys_menus.orden"
         );
         preparedStatementSelect.setString(1, grupo);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(
               new Object[]{
                  resultado.getString("menu"),
                  resultado.getBoolean("ver"),
                  resultado.getBoolean("agregar"),
                  resultado.getBoolean("modificar"),
                  resultado.getBoolean("eliminar"),
                  1
               }
            );
            if (resultado.getInt("id") == resultado.getInt("parent")) {
               preparedStatementSelect2 = conexion.prepareStatement(
                  "select sys_permisos.menu,sys_permisos.ver,sys_permisos.agregar,sys_permisos.modificar,sys_permisos.eliminar,\r\nsys_menus.id,sys_menus.parent,sys_menus.tipo\r\n from sys_permisos,sys_menus\r\nwhere\r\nsys_permisos.menu = sys_menus.menu and sys_menus.parent <> sys_menus.id   and sys_permisos.grupo =? and sys_menus.parent =? order by sys_menus.orden"
               );
               preparedStatementSelect2.setString(1, grupo);
               preparedStatementSelect2.setInt(2, resultado.getInt("parent"));
               resultado2 = preparedStatementSelect2.executeQuery();

               while (resultado2.next()) {
                  if (resultado2.getInt("tipo") == 1) {
                     modelo.addRow(
                        new Object[]{
                           resultado2.getString("menu"),
                           resultado2.getBoolean("ver"),
                           resultado2.getBoolean("agregar"),
                           resultado2.getBoolean("modificar"),
                           resultado2.getBoolean("eliminar"),
                           2
                        }
                     );
                     preparedStatementSelect3 = conexion.prepareStatement(
                        "select sys_permisos.menu,sys_permisos.ver,sys_permisos.agregar,sys_permisos.modificar,sys_permisos.eliminar,\r\nsys_menus.id,sys_menus.parent,sys_menus.tipo\r\n from sys_permisos,sys_menus\r\nwhere\r\nsys_permisos.menu = sys_menus.menu and sys_menus.parent <> sys_menus.id   and sys_permisos.grupo =? and sys_menus.parent =? order by sys_menus.orden"
                     );
                     preparedStatementSelect3.setString(1, grupo);
                     preparedStatementSelect3.setInt(2, resultado2.getInt("id"));
                     resultado3 = preparedStatementSelect3.executeQuery();

                     while (resultado3.next()) {
                        modelo.addRow(
                           new Object[]{
                              resultado3.getString("menu"),
                              resultado3.getBoolean("ver"),
                              resultado3.getBoolean("agregar"),
                              resultado3.getBoolean("modificar"),
                              resultado3.getBoolean("eliminar"),
                              0
                           }
                        );
                     }
                  } else {
                     modelo.addRow(
                        new Object[]{
                           resultado2.getString("menu"),
                           resultado2.getBoolean("ver"),
                           resultado2.getBoolean("agregar"),
                           resultado2.getBoolean("modificar"),
                           resultado2.getBoolean("eliminar"),
                           0
                        }
                     );
                  }
               }
            }
         }
      } catch (Exception var14) {
         LogErrores.errores(var14, "Error al seleccionar Grupos..", frame);
      } finally {
         new CerrarRecursos(
            preparedStatementSelect,
            preparedStatementSelect2,
            preparedStatementSelect3,
            resultado2,
            resultado,
            resultado3,
            frame,
            "Error al seleccionar Grupos...."
         );
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarGrupo(JinternalPadrePermisos frame, ModeloTablaDefecto modelo) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select grupo, estado from sys_grupos");
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getString("grupo"), resultado.getBoolean("estado")});
         }
      } catch (Exception var9) {
         LogErrores.errores(var9, "Error al seleccionar Grupos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Grupos....");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static int actualizarPermisos(String grupo, TablaConParametro tablaPermisos, JinternalPadrePermisos frame) {
      int ver = tablaPermisos.getColumn("Ver").getModelIndex();
      int insertar = tablaPermisos.getColumn("Insertar").getModelIndex();
      int modificar = tablaPermisos.getColumn("Modificar").getModelIndex();
      int eliminar = tablaPermisos.getColumn("Eliminar").getModelIndex();
      int menu = tablaPermisos.getColumn("Menu").getModelIndex();
      PreparedStatement psActualizarPermisos = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarPermisos = conexion.prepareStatement("update sys_permisos set ver=?, agregar=?,modificar=?,eliminar=? where grupo = ? and menu =?");

         for (int fila = 0; fila < tablaPermisos.getRowCount(); fila++) {
            psActualizarPermisos.setBoolean(1, (Boolean)tablaPermisos.getValueAt(fila, ver));
            psActualizarPermisos.setBoolean(2, (Boolean)tablaPermisos.getValueAt(fila, insertar));
            psActualizarPermisos.setBoolean(3, (Boolean)tablaPermisos.getValueAt(fila, modificar));
            psActualizarPermisos.setBoolean(4, (Boolean)tablaPermisos.getValueAt(fila, eliminar));
            psActualizarPermisos.setString(5, grupo);
            psActualizarPermisos.setString(6, String.valueOf(tablaPermisos.getValueAt(fila, menu)));
            psActualizarPermisos.addBatch();
         }

         psActualizarPermisos.executeBatch();
         conexion.commit();
         return 1;
      } catch (SQLException var17) {
         try {
            conexion.rollback();
         } catch (SQLException var16) {
            LogErrores.errores(var16, "Error al actualizar permisos.", frame);
         }

         LogErrores.errores(var17, "Error al actualizar permisos..", frame);
      } finally {
         new CerrarRecursos(psActualizarPermisos, frame, "Error al actualizar permisos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public GruposE(String grupo, int estado) {
      this.grupo = grupo;
      this.estado = estado;
   }

   public GruposE(String usuario, String menu, String grupo, int estado, int ver, int agregar, int modificar, int eliminar) {
      this.usuario = usuario;
      this.menu = menu;
      this.grupo = grupo;
      this.estado = estado;
      this.ver = ver;
      this.agregar = agregar;
      this.modificar = modificar;
      this.eliminar = eliminar;
   }

   public String getUsuario() {
      return this.usuario;
   }

   public void setUsuario(String usuario) {
      this.usuario = usuario;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getMenu() {
      return this.menu;
   }

   public void setMenu(String menu) {
      this.menu = menu;
   }

   public String getGrupo() {
      return this.grupo;
   }

   public void setGrupo(String grupo) {
      this.grupo = grupo;
   }

   public int getVer() {
      return this.ver;
   }

   public void setVer(int ver) {
      this.ver = ver;
   }

   public int getAgregar() {
      return this.agregar;
   }

   public void setAgregar(int agregar) {
      this.agregar = agregar;
   }

   public int getModificar() {
      return this.modificar;
   }

   public void setModificar(int modificar) {
      this.modificar = modificar;
   }

   public int getEliminar() {
      return this.eliminar;
   }

   public void setEliminar(int eliminar) {
      this.eliminar = eliminar;
   }
}
