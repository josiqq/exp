package a0099Tarjetas;

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
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;

public class TarjetasE {
   private int cod_tarjeta;
   private int estado;
   private int tipo;
   private int cod_proveedor;
   private String nombre_tarjeta;
   private String nombre_proveedor;

   public static int eliminarTarjeta(TarjetasE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMarca = conexion.prepareStatement("delete from tarjetas where cod_tarjeta =?");
         psEliminarMarca.setInt(1, entidad.getCod_tarjeta());
         psEliminarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Tarjeta.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Tarjeta..", frame);
      } finally {
         new CerrarRecursos(psEliminarMarca, frame, "Error al eliminar Tarjeta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarTarjeta(TarjetasE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarMonedas = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarMonedas = conexion.prepareStatement("update tarjetas set nombre_tarjeta=?,estado=?,tipo=?,cod_proveedor =? where cod_tarjeta =?");
         psActualizarMonedas.setString(1, entidad.getNombre_tarjeta());
         psActualizarMonedas.setInt(2, entidad.getEstado());
         psActualizarMonedas.setInt(3, entidad.getTipo());
         psActualizarMonedas.setInt(4, entidad.getCod_proveedor());
         psActualizarMonedas.setInt(5, entidad.getCod_tarjeta());
         psActualizarMonedas.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Tarjeta.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Tarjeta..", frame);
      } finally {
         new CerrarRecursos(psActualizarMonedas, frame, "Error al actualizar Tarjeta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarTarjetas(TarjetasE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarMoneda = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_tarjeta) as mayor from tarjetas");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarMoneda = conexion.prepareStatement("insert into tarjetas (cod_tarjeta,nombre_tarjeta,estado,tipo,cod_proveedor) values(?,?,?,?,?)");
         psInsertarMoneda.setInt(1, siguienteRegistro);
         psInsertarMoneda.setString(2, entidad.getNombre_tarjeta());
         psInsertarMoneda.setInt(3, entidad.getEstado());
         psInsertarMoneda.setInt(4, entidad.getTipo());
         psInsertarMoneda.setInt(5, entidad.getCod_proveedor());
         psInsertarMoneda.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Tarjeta.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Tarjeta..", frame);
      } finally {
         new CerrarRecursos(psInsertarMoneda, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Tarjeta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarTarjetasTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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

   public static List<ComboEntidad> obtenerTarjetas(JinternalPadre frame) {
      List<ComboEntidad> lista = new ArrayList<>();
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;
      int id = 0;
      String nombre = "";

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_tarjeta,nombre_tarjeta from tarjetas where estado =1");
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            id = resultado.getInt("cod_tarjeta");
            nombre = resultado.getString("nombre_tarjeta");
            lista.add(new ComboEntidad(id, nombre));
         }

         return lista;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Tarjetas..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Tarjetas..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static TarjetasE buscarTarjeta(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\ntarjetas.cod_tarjeta,tarjetas.nombre_tarjeta,tarjetas.estado,tarjetas.tipo,\r\ntarjetas.cod_proveedor,proveedores.nombre_proveedor\r\n from tarjetas,proveedores\r\nwhere\r\ntarjetas.cod_proveedor = proveedores.cod_proveedor and tarjetas.cod_tarjeta=?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new TarjetasE(
               resultado.getInt("cod_tarjeta"),
               resultado.getString("nombre_tarjeta"),
               resultado.getInt("estado"),
               resultado.getInt("tipo"),
               resultado.getInt("cod_proveedor"),
               resultado.getString("nombre_proveedor")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Tarjeta..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Tarjeta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public TarjetasE(int cod_tarjeta, String nombre_tarjeta, int estado, int tipo, int cod_proveedor, String nombre_proveedor) {
      this.cod_tarjeta = cod_tarjeta;
      this.nombre_tarjeta = nombre_tarjeta;
      this.estado = estado;
      this.tipo = tipo;
      this.cod_proveedor = cod_proveedor;
      this.nombre_proveedor = nombre_proveedor;
   }

   public TarjetasE(int cod_tarjeta, String nombre_tarjeta, int estado, int tipo, int cod_proveedor) {
      this.cod_tarjeta = cod_tarjeta;
      this.nombre_tarjeta = nombre_tarjeta;
      this.estado = estado;
      this.tipo = tipo;
      this.cod_proveedor = cod_proveedor;
   }

   public int getCod_tarjeta() {
      return this.cod_tarjeta;
   }

   public void setCod_tarjeta(int cod_tarjeta) {
      this.cod_tarjeta = cod_tarjeta;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public int getTipo() {
      return this.tipo;
   }

   public void setTipo(int tipo) {
      this.tipo = tipo;
   }

   public int getCod_proveedor() {
      return this.cod_proveedor;
   }

   public void setCod_proveedor(int cod_proveedor) {
      this.cod_proveedor = cod_proveedor;
   }

   public String getNombre_tarjeta() {
      return this.nombre_tarjeta;
   }

   public void setNombre_tarjeta(String nombre_tarjeta) {
      this.nombre_tarjeta = nombre_tarjeta;
   }

   public String getNombre_proveedor() {
      return this.nombre_proveedor;
   }

   public void setNombre_proveedor(String nombre_proveedor) {
      this.nombre_proveedor = nombre_proveedor;
   }
}
