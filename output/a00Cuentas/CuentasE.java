package a00Cuentas;

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

public class CuentasE {
   private int cod_cuenta;
   private int estado;
   private int cod_banco;
   private int tipo;
   private int cod_sucursal;
   private int cod_moneda;
   private String nombre_cuenta;
   private String nombre_sucursal;
   private String nombre_moneda;

   public static int eliminarCuentas(CuentasE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarCuenta = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarCuenta = conexion.prepareStatement("delete from cuentas where cod_cuenta =?");
         psEliminarCuenta.setInt(1, entidad.getCod_cuenta());
         psEliminarCuenta.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Cuenta.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Cuenta..", frame);
      } finally {
         new CerrarRecursos(psEliminarCuenta, frame, "Error al eliminar Cuenta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarCuentas(CuentasE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarCuentas = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarCuentas = conexion.prepareStatement(
            "update cuentas set nombre_cuenta=?,estado=?,cod_banco=?,tipo=?,cod_sucursal=?,cod_moneda=? where cod_cuenta =?"
         );
         psActualizarCuentas.setString(1, entidad.getNombre_cuenta());
         psActualizarCuentas.setInt(2, entidad.getEstado());
         psActualizarCuentas.setInt(3, entidad.getCod_banco());
         psActualizarCuentas.setInt(4, entidad.getTipo());
         psActualizarCuentas.setInt(5, entidad.getCod_sucursal());
         psActualizarCuentas.setInt(6, entidad.getCod_cuenta());
         psActualizarCuentas.setInt(7, entidad.getCod_moneda());
         psActualizarCuentas.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Cuenta.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Cuenta..", frame);
      } finally {
         new CerrarRecursos(psActualizarCuentas, frame, "Error al actualizar Cuenta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarCuentas(CuentasE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarCuenta = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_cuenta) as mayor from cuentas");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarCuenta = conexion.prepareStatement(
            "insert into cuentas (cod_cuenta,nombre_cuenta,estado,cod_banco,tipo,cod_sucursal,cod_moneda) values(?,?,?,?,?,?,?)"
         );
         psInsertarCuenta.setInt(1, siguienteRegistro);
         psInsertarCuenta.setString(2, entidad.getNombre_cuenta());
         psInsertarCuenta.setInt(3, entidad.getEstado());
         psInsertarCuenta.setInt(4, entidad.getCod_banco());
         psInsertarCuenta.setInt(5, entidad.getTipo());
         psInsertarCuenta.setInt(6, entidad.getCod_sucursal());
         psInsertarCuenta.setInt(7, entidad.getCod_moneda());
         psInsertarCuenta.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Cuenta.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Cuenta..", frame);
      } finally {
         new CerrarRecursos(psInsertarCuenta, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Cuenta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static CuentasE buscarCuentas2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CuentasE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_cuenta,nombre_cuenta from cuentas where cod_cuenta =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Cuenta inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CuentasE moneda = new CuentasE(resultado.getInt("cod_cuenta"), resultado.getString("nombre_cuenta"));
         var8 = moneda;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cuentas..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cuentas....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static CuentasE buscarCuentas1(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      CuentasE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select nombre_cuenta from cuentas where cod_cuenta =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Cuenta inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         CuentasE moneda = new CuentasE(resultado.getString("nombre_cuenta"));
         var8 = moneda;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cuentas..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cuentas....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void cargarCuentasTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Cuentas..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cuentas..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarCuentasTabla(String sql, ModeloTablaDefecto modelo, JinternalPadre frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Cuentas..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cuentas..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static List<ComboEntidad> obtenerCuentas(JinternalPadre frame) {
      List<ComboEntidad> lista = new ArrayList<>();
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;
      int id = 0;
      String nombre = "";

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_cuenta,nombre_cuenta from cuentas where estado =1");
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            id = resultado.getInt("cod_cuenta");
            nombre = resultado.getString("nombre_cuenta");
            lista.add(new ComboEntidad(id, nombre));
         }

         return lista;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cuentas..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cuentas..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static CuentasE buscarCuenta(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\ncuentas.cod_cuenta,cuentas.nombre_cuenta,cuentas.estado,cuentas.cod_banco,cuentas.tipo,cuentas.cod_sucursal,sucursales.nombre_sucursal,cuentas.cod_moneda,monedas.nombre_moneda\r\n from cuentas,sucursales,monedas\r\nwhere\r\ncuentas.cod_sucursal = sucursales.cod_sucursal and cuentas.cod_moneda = monedas.cod_moneda and cuentas.cod_cuenta =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new CuentasE(
               resultado.getInt("cod_cuenta"),
               resultado.getString("nombre_cuenta"),
               resultado.getInt("estado"),
               resultado.getInt("cod_banco"),
               resultado.getInt("tipo"),
               resultado.getInt("cod_sucursal"),
               resultado.getString("nombre_sucursal"),
               resultado.getInt("cod_moneda"),
               resultado.getString("nombre_moneda")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cuenta..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cuenta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public CuentasE(
      int cod_cuenta, String nombre_cuenta, int estado, int cod_banco, int tipo, int cod_sucursal, String nombre_sucursal, int cod_moneda, String nombre_moneda
   ) {
      this.cod_cuenta = cod_cuenta;
      this.nombre_cuenta = nombre_cuenta;
      this.estado = estado;
      this.cod_banco = cod_banco;
      this.tipo = tipo;
      this.cod_sucursal = cod_sucursal;
      this.nombre_sucursal = nombre_sucursal;
      this.cod_moneda = cod_moneda;
      this.nombre_moneda = nombre_moneda;
   }

   public CuentasE(int cod_cuenta, String nombre_cuenta) {
      this.cod_cuenta = cod_cuenta;
      this.nombre_cuenta = nombre_cuenta;
   }

   public CuentasE(String nombre_cuenta) {
      this.nombre_cuenta = nombre_cuenta;
   }

   public CuentasE(int cod_cuenta, String nombre_cuenta, int estado, int cod_banco, int tipo, int cod_sucursal, int cod_moneda) {
      this.cod_cuenta = cod_cuenta;
      this.nombre_cuenta = nombre_cuenta;
      this.estado = estado;
      this.cod_banco = cod_banco;
      this.tipo = tipo;
      this.cod_sucursal = cod_sucursal;
      this.cod_moneda = cod_moneda;
   }

   public int getCod_cuenta() {
      return this.cod_cuenta;
   }

   public void setCod_cuenta(int cod_cuenta) {
      this.cod_cuenta = cod_cuenta;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public int getCod_banco() {
      return this.cod_banco;
   }

   public void setCod_banco(int cod_banco) {
      this.cod_banco = cod_banco;
   }

   public int getTipo() {
      return this.tipo;
   }

   public void setTipo(int tipo) {
      this.tipo = tipo;
   }

   public int getCod_sucursal() {
      return this.cod_sucursal;
   }

   public void setCod_sucursal(int cod_sucursal) {
      this.cod_sucursal = cod_sucursal;
   }

   public String getNombre_cuenta() {
      return this.nombre_cuenta;
   }

   public void setNombre_cuenta(String nombre_cuenta) {
      this.nombre_cuenta = nombre_cuenta;
   }

   public String getNombre_sucursal() {
      return this.nombre_sucursal;
   }

   public void setNombre_sucursal(String nombre_sucursal) {
      this.nombre_sucursal = nombre_sucursal;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public void setCod_moneda(int cod_moneda) {
      this.cod_moneda = cod_moneda;
   }

   public String getNombre_moneda() {
      return this.nombre_moneda;
   }

   public void setNombre_moneda(String nombre_moneda) {
      this.nombre_moneda = nombre_moneda;
   }
}
