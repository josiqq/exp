package a00Bancos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import utilidades.ComboEntidad;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;

public class BancosE {
   private int cod_banco;
   private int estado;
   private String nombre_banco;
   private String direccion;
   private String telefono;

   public static int eliminarBancos(BancosE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarBanco = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarBanco = conexion.prepareStatement("delete from bancos where cod_banco =?");
         psEliminarBanco.setInt(1, entidad.getCod_banco());
         psEliminarBanco.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Banco.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Banco..", frame);
      } finally {
         new CerrarRecursos(psEliminarBanco, frame, "Error al eliminar Banco..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarBancos(BancosE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarBancos = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarBancos = conexion.prepareStatement("update bancos set nombre_banco=?,estado=?,direccion=?,telefono=? where cod_banco =?");
         psActualizarBancos.setString(1, entidad.getNombre_banco());
         psActualizarBancos.setInt(2, entidad.getEstado());
         psActualizarBancos.setString(3, entidad.getDireccion());
         psActualizarBancos.setString(4, entidad.getTelefono());
         psActualizarBancos.setInt(5, entidad.getCod_banco());
         psActualizarBancos.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Banco.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Banco..", frame);
      } finally {
         new CerrarRecursos(psActualizarBancos, frame, "Error al actualizar Banco..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarBancos(BancosE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarBanco = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_banco) as mayor from bancos");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarBanco = conexion.prepareStatement("insert into bancos (cod_banco,nombre_banco,estado,direccion,telefono) values(?,?,?,?,?)");
         psInsertarBanco.setInt(1, siguienteRegistro);
         psInsertarBanco.setString(2, entidad.getNombre_banco());
         psInsertarBanco.setInt(3, entidad.getEstado());
         psInsertarBanco.setString(4, entidad.getDireccion());
         psInsertarBanco.setString(5, entidad.getTelefono());
         psInsertarBanco.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Banco.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Banco..", frame);
      } finally {
         new CerrarRecursos(psInsertarBanco, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Banco..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static BancosE buscarBancos2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      BancosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_banco,nombre_banco from bancos where cod_banco =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Moneda inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         BancosE banco = new BancosE(resultado.getInt("cod_banco"), resultado.getString("nombre_banco"));
         var8 = banco;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Bancos..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Bancos....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void cargarBancosTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Bancos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Bancos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static List<ComboEntidad> obtenerBancos(JinternalPadre frame) {
      List<ComboEntidad> lista = new ArrayList<>();
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;
      int id = 0;
      String nombre = "";

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_banco,nombre_banco from bancos where estado =1");
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            id = resultado.getInt("cod_banco");
            nombre = resultado.getString("nombre_banco");
            lista.add(new ComboEntidad(id, nombre));
         }

         return lista;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Bancos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Bancos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static BancosE buscarBanco(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_banco,nombre_banco,estado,direccion,telefono from bancos where cod_banco =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new BancosE(
               resultado.getInt("cod_banco"),
               resultado.getString("nombre_banco"),
               resultado.getInt("estado"),
               resultado.getString("direccion"),
               resultado.getString("telefono")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Banco..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Banco..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public BancosE(int cod_banco, String nombre_banco, int estado, String direccion, String telefono) {
      this.cod_banco = cod_banco;
      this.nombre_banco = nombre_banco;
      this.estado = estado;
      this.direccion = direccion;
      this.telefono = telefono;
   }

   public BancosE(int cod_banco, String nombre_banco) {
      this.cod_banco = cod_banco;
      this.nombre_banco = nombre_banco;
   }

   public int getCod_banco() {
      return this.cod_banco;
   }

   public void setCod_banco(int cod_banco) {
      this.cod_banco = cod_banco;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_banco() {
      return this.nombre_banco;
   }

   public void setNombre_banco(String nombre_banco) {
      this.nombre_banco = nombre_banco;
   }

   public String getDireccion() {
      return this.direccion;
   }

   public void setDireccion(String direccion) {
      this.direccion = direccion;
   }

   public String getTelefono() {
      return this.telefono;
   }

   public void setTelefono(String telefono) {
      this.telefono = telefono;
   }
}
