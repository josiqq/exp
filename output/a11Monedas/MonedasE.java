package a11Monedas;

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

public class MonedasE {
   private int cod_moneda;
   private int estado;
   private String nombre_moneda;
   private String sigla;

   public static int eliminarMonedas(MonedasE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMarca = conexion.prepareStatement("delete from monedas where cod_moneda =?");
         psEliminarMarca.setInt(1, entidad.getCod_moneda());
         psEliminarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Moneda.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Moneda..", frame);
      } finally {
         new CerrarRecursos(psEliminarMarca, frame, "Error al eliminar Moneda..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarMonedas(MonedasE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarMonedas = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarMonedas = conexion.prepareStatement("update monedas set nombre_moneda=?,estado=?,sigla=? where cod_moneda =?");
         psActualizarMonedas.setString(1, entidad.getNombre_moneda());
         psActualizarMonedas.setInt(2, entidad.getEstado());
         psActualizarMonedas.setString(3, entidad.getSigla());
         psActualizarMonedas.setInt(4, entidad.getCod_moneda());
         psActualizarMonedas.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Moneda.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Moneda..", frame);
      } finally {
         new CerrarRecursos(psActualizarMonedas, frame, "Error al actualizar Moneda..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarMonedas(MonedasE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarMoneda = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_moneda) as mayor from monedas");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarMoneda = conexion.prepareStatement("insert into monedas (cod_moneda,nombre_moneda,sigla,estado) values(?,?,?,?)");
         psInsertarMoneda.setInt(1, siguienteRegistro);
         psInsertarMoneda.setString(2, entidad.getNombre_moneda());
         psInsertarMoneda.setInt(3, entidad.getEstado());
         psInsertarMoneda.setInt(4, entidad.getCod_moneda());
         psInsertarMoneda.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Moneda.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Moneda..", frame);
      } finally {
         new CerrarRecursos(psInsertarMoneda, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Moneda..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static MonedasE buscarMonedas2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      MonedasE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_moneda,nombre_moneda from monedas where cod_moneda =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Moneda inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         MonedasE moneda = new MonedasE(resultado.getInt("cod_moneda"), resultado.getString("nombre_moneda"));
         var8 = moneda;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Monedas..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Monedas....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static MonedasE buscarMonedas2(int codigo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      MonedasE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_moneda,nombre_moneda from monedas where cod_moneda =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Moneda inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         MonedasE moneda = new MonedasE(resultado.getInt("cod_moneda"), resultado.getString("nombre_moneda"));
         var8 = moneda;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Monedas..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Monedas....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void cargarMonedasTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Monedas..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Monedas..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static List<ComboEntidad> obtenerMonedas(JinternalPadre frame) {
      List<ComboEntidad> lista = new ArrayList<>();
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;
      int id = 0;
      String nombre = "";

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_moneda,nombre_moneda from monedas where estado =1");
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            id = resultado.getInt("cod_moneda");
            nombre = resultado.getString("nombre_moneda");
            lista.add(new ComboEntidad(id, nombre));
         }

         return lista;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Monedas..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Monedas..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static MonedasE buscarMoneda(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_moneda,nombre_moneda,sigla,estado from monedas where cod_moneda =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new MonedasE(resultado.getInt("cod_moneda"), resultado.getString("nombre_moneda"), resultado.getInt("estado"), resultado.getString("sigla"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Moneda..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Moneda..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public MonedasE(int cod_moneda, String nombre_moneda) {
      this.cod_moneda = cod_moneda;
      this.nombre_moneda = nombre_moneda;
   }

   public MonedasE(int cod_moneda, String nombre_moneda, int estado, String sigla) {
      this.cod_moneda = cod_moneda;
      this.nombre_moneda = nombre_moneda;
      this.estado = estado;
      this.sigla = sigla;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public void setCod_moneda(int cod_moneda) {
      this.cod_moneda = cod_moneda;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_moneda() {
      return this.nombre_moneda;
   }

   public void setNombre_moneda(String nombre_moneda) {
      this.nombre_moneda = nombre_moneda;
   }

   public String getSigla() {
      return this.sigla;
   }

   public void setSigla(String sigla) {
      this.sigla = sigla;
   }
}
