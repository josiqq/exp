package a99Paises;

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

public class PaisesE {
   private int cod_pais;
   private int estado;
   private String nombre_pais;
   private String sigla;

   public static int eliminarPaises(PaisesE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarPais = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarPais = conexion.prepareStatement("delete from paises where cod_pais =?");
         psEliminarPais.setInt(1, entidad.getCod_pais());
         psEliminarPais.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Pais.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Pais..", frame);
      } finally {
         new CerrarRecursos(psEliminarPais, frame, "Error al eliminar Pais..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarPaises(PaisesE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarPais = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarPais = conexion.prepareStatement("update paises set nombre_pais=?,estado=?,sigla=? where cod_pais =?");
         psActualizarPais.setString(1, entidad.getNombre_pais());
         psActualizarPais.setInt(2, entidad.getEstado());
         psActualizarPais.setString(3, entidad.getSigla());
         psActualizarPais.setInt(4, entidad.getCod_pais());
         psActualizarPais.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Pais.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Pais..", frame);
      } finally {
         new CerrarRecursos(psActualizarPais, frame, "Error al actualizar Pais..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarPais(PaisesE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarPais = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_pais) as mayor from paises");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarPais = conexion.prepareStatement("insert into paises (cod_pais,nombre_pais,estado,sigla) values(?,?,?,?)");
         psInsertarPais.setInt(1, siguienteRegistro);
         psInsertarPais.setString(2, entidad.getNombre_pais());
         psInsertarPais.setInt(3, entidad.getEstado());
         psInsertarPais.setString(4, entidad.getSigla());
         psInsertarPais.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Pais.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Pais..", frame);
      } finally {
         new CerrarRecursos(psInsertarPais, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Pais..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarPaisesTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Pais..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Pais..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static PaisesE buscarPaises2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      PaisesE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_pais,nombre_pais from paises where cod_pais =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Pais inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         PaisesE pais = new PaisesE(resultado.getInt("cod_pais"), resultado.getString("nombre_pais"));
         var8 = pais;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Pais..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Pais....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static PaisesE buscarPais(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_pais,nombre_pais,estado,sigla from paises where cod_pais =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new PaisesE(resultado.getInt("cod_pais"), resultado.getString("nombre_pais"), resultado.getInt("estado"), resultado.getString("sigla"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Pais..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Pais..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public PaisesE(int cod_pais, String nombre_pais) {
      this.cod_pais = cod_pais;
      this.nombre_pais = nombre_pais;
   }

   public PaisesE(int cod_pais, String nombre_pais, int estado, String sigla) {
      this.cod_pais = cod_pais;
      this.nombre_pais = nombre_pais;
      this.estado = estado;
      this.sigla = sigla;
   }

   public int getCod_pais() {
      return this.cod_pais;
   }

   public void setCod_pais(int cod_pais) {
      this.cod_pais = cod_pais;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_pais() {
      return this.nombre_pais;
   }

   public void setNombre_pais(String nombre_pais) {
      this.nombre_pais = nombre_pais;
   }

   public String getSigla() {
      return this.sigla;
   }

   public void setSigla(String sigla) {
      this.sigla = sigla;
   }
}
