package a0099Cobradores;

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

public class CobradoresE {
   private int cod_cobrador;
   private int estado;
   private String nombre_cobrador;
   private String direccion;
   private String telefono;

   public static int eliminarCobradores(CobradoresE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMarca = conexion.prepareStatement("delete from cobradores where cod_cobrador =?");
         psEliminarMarca.setInt(1, entidad.getCod_cobrador());
         psEliminarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Cobrador.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Cobrador..", frame);
      } finally {
         new CerrarRecursos(psEliminarMarca, frame, "Error al eliminar Cobrador..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarCobrador(CobradoresE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarMonedas = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarMonedas = conexion.prepareStatement("update cobradores set nombre_cobrador=?,estado=?,direccion=?,telefono=? where cod_cobrador =?");
         psActualizarMonedas.setString(1, entidad.getNombre_cobrador());
         psActualizarMonedas.setInt(2, entidad.getEstado());
         psActualizarMonedas.setString(3, entidad.getDireccion());
         psActualizarMonedas.setString(4, entidad.getTelefono());
         psActualizarMonedas.setInt(5, entidad.getCod_cobrador());
         psActualizarMonedas.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Cobrador.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Cobrador..", frame);
      } finally {
         new CerrarRecursos(psActualizarMonedas, frame, "Error al actualizar Cobrador..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarCobradores(CobradoresE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarMoneda = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_cobrador) as mayor from cobradores");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarMoneda = conexion.prepareStatement("insert into cobradores (cod_cobrador,nombre_cobrador,estado,direccion,telefono) values(?,?,?,?,?)");
         psInsertarMoneda.setInt(1, siguienteRegistro);
         psInsertarMoneda.setString(2, entidad.getNombre_cobrador());
         psInsertarMoneda.setInt(3, entidad.getEstado());
         psInsertarMoneda.setString(4, entidad.getDireccion());
         psInsertarMoneda.setString(5, entidad.getTelefono());
         psInsertarMoneda.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Cobrador.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Cobrador..", frame);
      } finally {
         new CerrarRecursos(psInsertarMoneda, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Cobrador..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarCobradoresTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Cobradores..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cobradores..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static CobradoresE buscarCobrador(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "Select cod_cobrador,nombre_cobrador,estado,direccion,telefono from cobradores where cod_cobrador =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new CobradoresE(
               resultado.getInt("cod_cobrador"),
               resultado.getString("nombre_cobrador"),
               resultado.getInt("estado"),
               resultado.getString("direccion"),
               resultado.getString("telefono")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cobrador..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cobrador..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public CobradoresE(int cod_cobrador, String nombre_cobrador, int estado, String direccion, String telefono) {
      this.cod_cobrador = cod_cobrador;
      this.nombre_cobrador = nombre_cobrador;
      this.estado = estado;
      this.direccion = direccion;
      this.telefono = telefono;
   }

   public int getCod_cobrador() {
      return this.cod_cobrador;
   }

   public void setCod_cobrador(int cod_cobrador) {
      this.cod_cobrador = cod_cobrador;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_cobrador() {
      return this.nombre_cobrador;
   }

   public void setNombre_cobrador(String nombre_cobrador) {
      this.nombre_cobrador = nombre_cobrador;
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
