package compradores;

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

public class CompradoresE {
   private int cod_comprador;
   private int estado;
   private String nombre_comprador;

   public static int eliminarCompradores(CompradoresE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMarca = conexion.prepareStatement("delete from compradores where cod_comprador =?");
         psEliminarMarca.setInt(1, entidad.getCod_comprador());
         psEliminarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Comprador.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Comprador..", frame);
      } finally {
         new CerrarRecursos(psEliminarMarca, frame, "Error al eliminar Comprador..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarCompradores(CompradoresE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarCompradores = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarCompradores = conexion.prepareStatement("update compradores set nombre_comprador=?,estado=? where cod_comprador =?");
         psActualizarCompradores.setString(1, entidad.getNombre_comprador());
         psActualizarCompradores.setInt(2, entidad.getEstado());
         psActualizarCompradores.setInt(4, entidad.getCod_comprador());
         psActualizarCompradores.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Comprador.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Comprador..", frame);
      } finally {
         new CerrarRecursos(psActualizarCompradores, frame, "Error al actualizar Comprador..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarCompradores(CompradoresE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarComprador = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_comprador) as mayor from compradores");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarComprador = conexion.prepareStatement("insert into compradores (cod_comprador,nombre_comprador,estado) values(?,?,?)");
         psInsertarComprador.setInt(1, siguienteRegistro);
         psInsertarComprador.setString(2, entidad.getNombre_comprador());
         psInsertarComprador.setInt(3, entidad.getEstado());
         psInsertarComprador.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Comprador.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Comprador..", frame);
      } finally {
         new CerrarRecursos(psInsertarComprador, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Comprador..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarCompradoresTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Compradores..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Compradores..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarCompradoresTablaDialog(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Compradores..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Compradores..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static CompradoresE buscarCompradores2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CompradoresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_comprador,nombre_comprador from compradores where cod_comprador =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Comprador inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CompradoresE comprador = new CompradoresE(resultado.getInt("cod_comprador"), resultado.getString("nombre_comprador"));
         var8 = comprador;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Compradores..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Compradores....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static CompradoresE buscarCompradores2(String usuario, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CompradoresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select sys_usuarios.cod_comprador,compradores.nombre_comprador\r\nfrom sys_usuarios,compradores\r\nwhere\r\nsys_usuarios.cod_comprador = compradores.cod_comprador\r\nand sys_usuarios.usuario =? and compradores.estado =1"
         );
         preparedStatementSelect.setString(1, usuario);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Comprador inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CompradoresE comprador = new CompradoresE(resultado.getInt("cod_comprador"), resultado.getString("nombre_comprador"));
         var8 = comprador;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Compradores..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Compradores....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static CompradoresE buscarCompradores2Dialog(int codigo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CompradoresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_comprador,nombre_comprador from compradores where cod_comprador =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Comprador inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CompradoresE comprador = new CompradoresE(resultado.getInt("cod_comprador"), resultado.getString("nombre_comprador"));
         var8 = comprador;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Compradores..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Compradores....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void buscarCompradoresF3(JinternalPadre frame, ModeloTablaDefecto modelo) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_comprador,nombre_comprador from compradores where estado =1");
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt("cod_comprador"), resultado.getString("nombre_comprador")});
         }
      } catch (Exception var9) {
         LogErrores.errores(var9, "Error al seleccionar Compradores..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Compradores....");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static CompradoresE buscarComprador(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CompradoresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_comprador,nombre_comprador,estado from compradores where cod_comprador =? and estado =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Comprador inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CompradoresE comprador = new CompradoresE(resultado.getInt("cod_comprador"), resultado.getString("nombre_comprador"), resultado.getInt("estado"));
         var8 = comprador;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Compradores..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Compradores....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public CompradoresE(int cod_comprador, String nombre_comprador, int estado) {
      this.cod_comprador = cod_comprador;
      this.nombre_comprador = nombre_comprador;
      this.estado = estado;
   }

   public CompradoresE(int cod_comprador, String nombre_comprador) {
      this.cod_comprador = cod_comprador;
      this.nombre_comprador = nombre_comprador;
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

   public String getNombre_comprador() {
      return this.nombre_comprador;
   }

   public void setNombre_comprador(String nombre_comprador) {
      this.nombre_comprador = nombre_comprador;
   }
}
