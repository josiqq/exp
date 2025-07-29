package a99CategoriaCliente;

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

public class CategoriaClienteE {
   private int cod_categoria;
   private int estado;
   private String nombre_categoria;

   public static int eliminarCategoriaCliente(CategoriaClienteE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMarca = conexion.prepareStatement("delete from clientes_categoria where cod_categoria =?");
         psEliminarMarca.setInt(1, entidad.getCod_categoria());
         psEliminarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Categoria de Cliente.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Categoria de Cliente..", frame);
      } finally {
         new CerrarRecursos(psEliminarMarca, frame, "Error al eliminar Categoria de Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarCategoriaCliente(CategoriaClienteE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarCategoria = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarCategoria = conexion.prepareStatement("update clientes_categoria set nombre_categoria=?,estado=? where cod_categoria =?");
         psActualizarCategoria.setString(1, entidad.getNombre_categoria());
         psActualizarCategoria.setInt(2, entidad.getEstado());
         psActualizarCategoria.setInt(3, entidad.getCod_categoria());
         psActualizarCategoria.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Categoria de Cliente.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Categoria de Cliente..", frame);
      } finally {
         new CerrarRecursos(psActualizarCategoria, frame, "Error al actualizar Categoria de Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarCategoriaCliente(CategoriaClienteE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_categoria) as mayor from clientes_categoria");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarMarca = conexion.prepareStatement("insert into clientes_categoria (cod_categoria,nombre_categoria,estado) values(?,?,?)");
         psInsertarMarca.setInt(1, siguienteRegistro);
         psInsertarMarca.setString(2, entidad.getNombre_categoria());
         psInsertarMarca.setInt(3, entidad.getEstado());
         psInsertarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Categoria de Cliente.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Categoria de Cliente..", frame);
      } finally {
         new CerrarRecursos(psInsertarMarca, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Categoria de Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarCategoriaClienteTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Marca..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Categoria de Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static CategoriaClienteE buscarCategoriaCliente2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CategoriaClienteE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_categoria,nombre_categoria from clientes_categoria where cod_categoria =? and estado =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Categoria inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CategoriaClienteE categoria = new CategoriaClienteE(resultado.getInt("cod_categoria"), resultado.getString("nombre_categoria"));
         var8 = categoria;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Categoria de Cliente..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Categoria de Cliente....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static CategoriaClienteE buscarCategoria(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_categoria,nombre_categoria,estado from clientes_categoria where cod_categoria =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new CategoriaClienteE(resultado.getInt("cod_categoria"), resultado.getString("nombre_categoria"), resultado.getInt("estado"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Categoria de Cliente..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Categoria de Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public CategoriaClienteE(int cod_categoria, String nombre_categoria) {
      this.cod_categoria = cod_categoria;
      this.nombre_categoria = nombre_categoria;
   }

   public CategoriaClienteE(int cod_categoria, String nombre_categoria, int estado) {
      this.cod_categoria = cod_categoria;
      this.nombre_categoria = nombre_categoria;
      this.estado = estado;
   }

   public int getCod_categoria() {
      return this.cod_categoria;
   }

   public void setCod_categoria(int cod_categoria) {
      this.cod_categoria = cod_categoria;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_categoria() {
      return this.nombre_categoria;
   }

   public void setNombre_categoria(String nombre_categoria) {
      this.nombre_categoria = nombre_categoria;
   }
}
