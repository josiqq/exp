package a099CajaCondicionPago;

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

public class CondicionPagoE {
   private int cod_condicion;
   private int estado;
   private int tipo;
   private String nombre_condicion;

   public static int eliminarCondicionPago(CondicionPagoE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarCondicionPago = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarCondicionPago = conexion.prepareStatement("delete from condiciones_pagos where cod_condicion =?");
         psEliminarCondicionPago.setInt(1, entidad.getCod_condicion());
         psEliminarCondicionPago.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Condicion de Pago.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Condicion de Pago..", frame);
      } finally {
         new CerrarRecursos(psEliminarCondicionPago, frame, "Error al eliminar Condicion de Pago..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarCondicionesPagos(CondicionPagoE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarCondicionPago = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarCondicionPago = conexion.prepareStatement("update condiciones_pagos set nombre_condicion=?,estado=?,tipo=? where cod_condicion =?");
         psActualizarCondicionPago.setString(1, entidad.getNombre_condicion());
         psActualizarCondicionPago.setInt(2, entidad.getEstado());
         psActualizarCondicionPago.setInt(3, entidad.getTipo());
         psActualizarCondicionPago.setInt(4, entidad.getCod_condicion());
         psActualizarCondicionPago.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Condicion de Pago.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Condicion de Pago..", frame);
      } finally {
         new CerrarRecursos(psActualizarCondicionPago, frame, "Error al actualizar Condicion de Pago..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarCondicionPago(CondicionPagoE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarCondicionPago = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_condicion) as mayor from condiciones_pagos");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarCondicionPago = conexion.prepareStatement("insert into condiciones_pagos (cod_condicion,nombre_condicion,estado,tipo) values(?,?,?,?)");
         psInsertarCondicionPago.setInt(1, siguienteRegistro);
         psInsertarCondicionPago.setString(2, entidad.getNombre_condicion());
         psInsertarCondicionPago.setInt(3, entidad.getEstado());
         psInsertarCondicionPago.setInt(4, entidad.getTipo());
         psInsertarCondicionPago.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Condicion de Pago.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Condicion de Pago..", frame);
      } finally {
         new CerrarRecursos(psInsertarCondicionPago, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Condicion de Pago..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static CondicionPagoE buscarCondicionPago2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CondicionPagoE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_condicion,nombre_condicion from condiciones_pagos where cod_condicion =? and estado =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Condicion de Pago inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CondicionPagoE condicionPago = new CondicionPagoE(resultado.getInt("cod_condicion"), resultado.getString("nombre_condicion"));
         var8 = condicionPago;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Condicion de Pago..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Condicion de Pago....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static List<ComboEntidad> obtenerCondiciones(JinternalPadre frame) {
      List<ComboEntidad> lista = new ArrayList<>();
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;
      int tipo = 0;
      String nombre = "";

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_condicion,nombre_condicion,tipo from condiciones_pagos where estado =1");
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            int id = resultado.getInt("cod_condicion");
            nombre = resultado.getString("nombre_condicion");
            tipo = resultado.getInt("tipo");
            lista.add(new ComboEntidad(id, nombre, tipo));
         }

         return lista;
      } catch (Exception var12) {
         LogErrores.errores(var12, "Error al seleccionar Condicion de Pago..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Condicion de Pago..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static void cargarCondicionesPagoTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Condiciones de Pagos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Condiciones de Pagos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static CondicionPagoE buscarCondicionPago(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("Select cod_condicion,nombre_condicion,estado,tipo from condiciones_pagos where cod_condicion =?");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new CondicionPagoE(
               resultado.getInt("cod_condicion"), resultado.getString("nombre_condicion"), resultado.getInt("estado"), resultado.getInt("tipo")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Condicion de Pago..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Condicion de Pago..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public CondicionPagoE(int cod_condicion, String nombre_condicion) {
      this.cod_condicion = cod_condicion;
      this.nombre_condicion = nombre_condicion;
   }

   public CondicionPagoE(int cod_condicion, String nombre_condicion, int estado, int tipo) {
      this.cod_condicion = cod_condicion;
      this.nombre_condicion = nombre_condicion;
      this.estado = estado;
      this.tipo = tipo;
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

   public String getNombre_condicion() {
      return this.nombre_condicion;
   }

   public void setNombre_condicion(String nombre_condicion) {
      this.nombre_condicion = nombre_condicion;
   }
}
