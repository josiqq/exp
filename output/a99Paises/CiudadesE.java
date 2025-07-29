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

public class CiudadesE {
   private int cod_ciudad;
   private int estado;
   private int cod_pais;
   private String nombre_ciudad;
   private String nombre_pais;

   public static int eliminarCiudades(CiudadesE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarCiudad = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarCiudad = conexion.prepareStatement("delete from ciudades where cod_ciudad =?");
         psEliminarCiudad.setInt(1, entidad.getCod_ciudad());
         psEliminarCiudad.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Ciudad.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Ciudad..", frame);
      } finally {
         new CerrarRecursos(psEliminarCiudad, frame, "Error al eliminar Ciudad..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarCiudades(CiudadesE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarCiudad = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarCiudad = conexion.prepareStatement("update ciudades set nombre_ciudad=?,estado=?,cod_pais=? where cod_ciudad =?");
         psActualizarCiudad.setString(1, entidad.getNombre_ciudad());
         psActualizarCiudad.setInt(2, entidad.getEstado());
         psActualizarCiudad.setInt(3, entidad.getCod_pais());
         psActualizarCiudad.setInt(4, entidad.getCod_ciudad());
         psActualizarCiudad.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Ciudad.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Ciudad..", frame);
      } finally {
         new CerrarRecursos(psActualizarCiudad, frame, "Error al actualizar Ciudad..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarCiudad(CiudadesE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarCiudad = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_ciudad) as mayor from ciudades");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarCiudad = conexion.prepareStatement("insert into ciudades (  cod_ciudad,nombre_ciudad,estado,cod_pais) values(?,?,?,?)");
         psInsertarCiudad.setInt(1, siguienteRegistro);
         psInsertarCiudad.setString(2, entidad.getNombre_ciudad());
         psInsertarCiudad.setInt(3, entidad.getEstado());
         psInsertarCiudad.setInt(4, entidad.getCod_pais());
         psInsertarCiudad.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Ciudad.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Ciudad..", frame);
      } finally {
         new CerrarRecursos(psInsertarCiudad, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Ciudad..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarCiudadesTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Ciudad..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Ciudad..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static CiudadesE buscarCiudades2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CiudadesE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_ciudad,nombre_ciudad from ciudades where cod_ciudad =? and cod_pais and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Ciudad inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CiudadesE ciudad = new CiudadesE(resultado.getInt("cod_ciudad"), resultado.getString("nombre_ciudad"));
         var8 = ciudad;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Ciudad..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Ciudad....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static CiudadesE buscarCiudad(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "Select ciudades.cod_ciudad,ciudades.nombre_ciudad,ciudades.estado,ciudades.cod_pais,paises.nombre_pais from ciudades,paises where ciudades.cod_pais = paises.cod_pais and ciudades.cod_ciudad =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new CiudadesE(
               resultado.getInt("cod_ciudad"),
               resultado.getString("nombre_ciudad"),
               resultado.getInt("estado"),
               resultado.getInt("cod_pais"),
               resultado.getString("nombre_ciudad")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Ciudad..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Ciudad..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public CiudadesE(int cod_ciudad, String nombre_ciudad) {
      this.cod_ciudad = cod_ciudad;
      this.nombre_ciudad = nombre_ciudad;
   }

   public CiudadesE(int cod_ciudad, String nombre_ciudad, int estado, int cod_pais, String nombre_pais) {
      this.cod_ciudad = cod_ciudad;
      this.nombre_ciudad = nombre_ciudad;
      this.estado = estado;
      this.cod_pais = cod_pais;
      this.nombre_pais = nombre_pais;
   }

   public CiudadesE(int cod_ciudad, String nombre_ciudad, int estado, int cod_pais) {
      this.cod_ciudad = cod_ciudad;
      this.nombre_ciudad = nombre_ciudad;
      this.estado = estado;
      this.cod_pais = cod_pais;
   }

   public String getNombre_pais() {
      return this.nombre_pais;
   }

   public void setNombre_pais(String nombre_pais) {
      this.nombre_pais = nombre_pais;
   }

   public int getCod_ciudad() {
      return this.cod_ciudad;
   }

   public void setCod_ciudad(int cod_ciudad) {
      this.cod_ciudad = cod_ciudad;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public int getCod_pais() {
      return this.cod_pais;
   }

   public void setCod_pais(int cod_pais) {
      this.cod_pais = cod_pais;
   }

   public String getNombre_ciudad() {
      return this.nombre_ciudad;
   }

   public void setNombre_ciudad(String nombre_ciudad) {
      this.nombre_ciudad = nombre_ciudad;
   }
}
