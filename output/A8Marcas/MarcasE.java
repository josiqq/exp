package A8Marcas;

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
import utilidadesVentanas.JinternalPadreString;

public class MarcasE {
   private int cod_marca;
   private int estado;
   private String nombre_marca;

   public static int eliminarMarcas(MarcasE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMarca = conexion.prepareStatement("delete from marcas where cod_marca =?");
         psEliminarMarca.setInt(1, entidad.getCod_marca());
         psEliminarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Marca.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Marca..", frame);
      } finally {
         new CerrarRecursos(psEliminarMarca, frame, "Error al eliminar Marca..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarMarcas(MarcasE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarMarcas = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarMarcas = conexion.prepareStatement("update marcas set nombre_marca=?,estado=? where cod_marca =?");
         psActualizarMarcas.setString(1, entidad.getNombre_marca());
         psActualizarMarcas.setInt(2, entidad.getEstado());
         psActualizarMarcas.setInt(3, entidad.getCod_marca());
         psActualizarMarcas.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Marca.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Marca..", frame);
      } finally {
         new CerrarRecursos(psActualizarMarcas, frame, "Error al actualizarMarca..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarMarcas(MarcasE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_marca) as mayor from marcas");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarMarca = conexion.prepareStatement("insert into marcas (cod_marca,nombre_marca,estado) values(?,?,?)");
         psInsertarMarca.setInt(1, siguienteRegistro);
         psInsertarMarca.setString(2, entidad.getNombre_marca());
         psInsertarMarca.setInt(3, entidad.getEstado());
         psInsertarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Marca.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Marca..", frame);
      } finally {
         new CerrarRecursos(psInsertarMarca, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Marca..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarMarcasTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Marca..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static MarcasE buscarMarca2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      MarcasE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_marca,nombre_marca from marcas where cod_marca =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Marca inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         MarcasE marca = new MarcasE(resultado.getInt("cod_marca"), resultado.getString("nombre_marca"));
         var8 = marca;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Marca..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Marca....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static MarcasE buscarMarca2(int codigo, JinternalPadreReporte frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      MarcasE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_marca,nombre_marca from marcas where cod_marca =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Marca inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         MarcasE marca = new MarcasE(resultado.getInt("cod_marca"), resultado.getString("nombre_marca"));
         var8 = marca;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Marca..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Marca....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static MarcasE buscarMarca2(int codigo, JinternalPadreString frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      MarcasE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_marca,nombre_marca from marcas where cod_marca =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Marca inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         MarcasE marca = new MarcasE(resultado.getInt("cod_marca"), resultado.getString("nombre_marca"));
         var8 = marca;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Marca..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Marca....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static MarcasE buscarMarca(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_marca,nombre_marca ,estadofrom marcas where cod_marca =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new MarcasE(resultado.getInt("cod_marca"), resultado.getString("nombre_marca"), resultado.getInt("estado"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Marca..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Marca..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public MarcasE(int cod_marca, String nombre_marca) {
      this.cod_marca = cod_marca;
      this.nombre_marca = nombre_marca;
   }

   public MarcasE(int cod_marca, String nombre_marca, int estado) {
      this.cod_marca = cod_marca;
      this.nombre_marca = nombre_marca;
      this.estado = estado;
   }

   public int getCod_marca() {
      return this.cod_marca;
   }

   public void setCod_marca(int cod_marca) {
      this.cod_marca = cod_marca;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_marca() {
      return this.nombre_marca;
   }

   public void setNombre_marca(String nombre_marca) {
      this.nombre_marca = nombre_marca;
   }
}
