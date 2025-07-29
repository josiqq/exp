package a00CondicionesVentas;

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

public class CondicionesVentasE {
   private int cod_condicion;
   private int estado;
   private int tipo;
   private int cuotas;
   private int cod_lista;
   private String nombre_condicion;
   private String nombre_lista;

   public static int eliminarCondicionesVentas(CondicionesVentasE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarCondicionVta = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarCondicionVta = conexion.prepareStatement("delete from condiciones_ventas where cod_condicion =?");
         psEliminarCondicionVta.setInt(1, entidad.getCod_condicion());
         psEliminarCondicionVta.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Condicion de Venta.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Condicion de Venta..", frame);
      } finally {
         new CerrarRecursos(psEliminarCondicionVta, frame, "Error al eliminar Condicion de Venta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarCondicionVta(CondicionesVentasE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarCondicionVta = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarCondicionVta = conexion.prepareStatement(
            "update condiciones_ventas set nombre_condicion=?,estado=?,tipo=?,cuotas=?,cod_lista=? where cod_condicion=?"
         );
         psActualizarCondicionVta.setString(1, entidad.getNombre_condicion());
         psActualizarCondicionVta.setInt(2, entidad.getEstado());
         psActualizarCondicionVta.setInt(3, entidad.getTipo());
         psActualizarCondicionVta.setInt(4, entidad.getCuotas());
         psActualizarCondicionVta.setInt(5, entidad.getCod_lista());
         psActualizarCondicionVta.setInt(6, entidad.getCod_condicion());
         psActualizarCondicionVta.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Condicion de Venta.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Condicion de Venta..", frame);
      } finally {
         new CerrarRecursos(psActualizarCondicionVta, frame, "Error al actualizar Condicion de Venta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarCondicionVta(CondicionesVentasE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarMoneda = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_condicion) as mayor from condiciones_ventas");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarMoneda = conexion.prepareStatement(
            "insert into condiciones_ventas (cod_condicion,nombre_condicion,estado,tipo,cuotas,cod_lista) values(?,?,?,?,?,?)"
         );
         psInsertarMoneda.setInt(1, siguienteRegistro);
         psInsertarMoneda.setString(2, entidad.getNombre_condicion());
         psInsertarMoneda.setInt(3, entidad.getEstado());
         psInsertarMoneda.setInt(4, entidad.getTipo());
         psInsertarMoneda.setInt(5, entidad.getCuotas());
         psInsertarMoneda.setInt(6, entidad.getCod_lista());
         psInsertarMoneda.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Condicion de Venta.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Condicion de Venta..", frame);
      } finally {
         new CerrarRecursos(psInsertarMoneda, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Condicion de Venta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static CondicionesVentasE buscarCondicionVta2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CondicionesVentasE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_condicion,nombre_condicion from condiciones_ventas where cod_condicion =? and estado =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Condicion de Venta inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CondicionesVentasE condicionVta = new CondicionesVentasE(resultado.getInt("cod_condicion"), resultado.getString("nombre_condicion"));
         var8 = condicionVta;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Condicion de Venta..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Condicion de Venta....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static CondicionesVentasE buscarCondicionVta3(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CondicionesVentasE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_condicion,nombre_condicion,cuotas from condiciones_ventas where cod_condicion =? and estado =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Condicion de Venta inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CondicionesVentasE condicionVta = new CondicionesVentasE(
            resultado.getInt("cod_condicion"), resultado.getString("nombre_condicion"), resultado.getInt("cuotas")
         );
         var8 = condicionVta;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Condicion de Venta..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Condicion de Venta....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void cargarCondicionesVentasTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Condiciones de Ventas..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Condiciones de Ventas..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarCondicionesVentasTabla(String sql, ModeloTablaDefecto modelo, JinternalPadre frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Condiciones de Ventas..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Condiciones de Ventas..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static CondicionesVentasE buscarCondicionVta(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\ncondiciones_ventas.cod_condicion,condiciones_ventas.nombre_condicion,condiciones_ventas.estado,\r\ncondiciones_ventas.tipo,condiciones_ventas.cuotas,condiciones_ventas.cod_lista,lista_precios_cab.nombre_lista\r\n from condiciones_ventas,lista_precios_cab\r\nwhere\r\ncondiciones_ventas.cod_lista =lista_precios_cab.cod_lista and condiciones_ventas.cod_condicion =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new CondicionesVentasE(
               resultado.getInt("cod_condicion"),
               resultado.getString("nombre_condicion"),
               resultado.getInt("estado"),
               resultado.getInt("tipo"),
               resultado.getInt("cuotas"),
               resultado.getInt("cod_lista"),
               resultado.getString("nombre_lista")
            );
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

   public CondicionesVentasE(int cod_condicion, String nombre_condicion) {
      this.cod_condicion = cod_condicion;
      this.nombre_condicion = nombre_condicion;
   }

   public CondicionesVentasE(int cod_condicion, String nombre_condicion, int cuotas) {
      this.cod_condicion = cod_condicion;
      this.nombre_condicion = nombre_condicion;
      this.cuotas = cuotas;
   }

   public CondicionesVentasE(int cod_condicion, String nombre_condicion, int estado, int tipo, int cuotas, int cod_lista) {
      this.cod_condicion = cod_condicion;
      this.nombre_condicion = nombre_condicion;
      this.estado = estado;
      this.tipo = tipo;
      this.cuotas = cuotas;
      this.cod_lista = cod_lista;
   }

   public CondicionesVentasE(int cod_condicion, String nombre_condicion, int estado, int tipo, int cuotas, int cod_lista, String nombre_lista) {
      this.cod_condicion = cod_condicion;
      this.nombre_condicion = nombre_condicion;
      this.estado = estado;
      this.tipo = tipo;
      this.cuotas = cuotas;
      this.cod_lista = cod_lista;
      this.nombre_lista = nombre_lista;
   }

   public int getCod_condicion() {
      return this.cod_condicion;
   }

   public void setCod_condicion(int cod_condicion) {
      this.cod_condicion = cod_condicion;
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

   public int getCuotas() {
      return this.cuotas;
   }

   public void setCuotas(int cuotas) {
      this.cuotas = cuotas;
   }

   public int getCod_lista() {
      return this.cod_lista;
   }

   public void setCod_lista(int cod_lista) {
      this.cod_lista = cod_lista;
   }

   public String getNombre_condicion() {
      return this.nombre_condicion;
   }

   public void setNombre_condicion(String nombre_condicion) {
      this.nombre_condicion = nombre_condicion;
   }

   public String getNombre_lista() {
      return this.nombre_lista;
   }

   public void setNombre_lista(String nombre_lista) {
      this.nombre_lista = nombre_lista;
   }
}
