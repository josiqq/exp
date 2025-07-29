package a6Depositos;

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
import utilidadesVentanas.JinternalPadreReporte;

public class DepositosE {
   private int cod_deposito;
   private int estado;
   private int cod_sucursal;
   private int pos;
   private int stock_negativo;
   private String nombre_deposito;
   private String telefono;
   private String direccion;
   private String encargado;
   private String nombre_sucursal;

   public static int eliminarDepositos(DepositosE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarSucursal = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarSucursal = conexion.prepareStatement("delete from depositos where cod_deposito =?");
         psEliminarSucursal.setInt(1, entidad.getCod_deposito());
         psEliminarSucursal.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Deposito.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Deposito..", frame);
      } finally {
         new CerrarRecursos(psEliminarSucursal, frame, "Error al eliminar Deposito..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarDepositos(DepositosE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarSucursales = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarSucursales = conexion.prepareStatement(
            "update depositos set nombre_deposito=?,estado=?,telefono=?,direccion=?,encargado=?,\r\ncod_sucursal=?,pos=?,stock_negativo=? where cod_deposito =?"
         );
         psActualizarSucursales.setString(1, entidad.getNombre_deposito());
         psActualizarSucursales.setInt(2, entidad.getEstado());
         psActualizarSucursales.setString(3, entidad.getTelefono());
         psActualizarSucursales.setString(4, entidad.getDireccion());
         psActualizarSucursales.setString(5, entidad.getEncargado());
         psActualizarSucursales.setInt(6, entidad.getCod_sucursal());
         psActualizarSucursales.setInt(7, entidad.getPos());
         psActualizarSucursales.setInt(8, entidad.getStock_negativo());
         psActualizarSucursales.setInt(9, entidad.getCod_deposito());
         psActualizarSucursales.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Deposito.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Deposito..", frame);
      } finally {
         new CerrarRecursos(psActualizarSucursales, frame, "Error al actualizar Deposito..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarDepositos(DepositosE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarSucursales = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_deposito) as mayor from depositos");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarSucursales = conexion.prepareStatement(
            "insert into depositos (cod_deposito,nombre_deposito,estado,telefono,direccion,encargado,\r\ncod_sucursal,pos,stock_negativo) values(?,?,?,?,?,?,?,?,?)"
         );
         psInsertarSucursales.setInt(1, siguienteRegistro);
         psInsertarSucursales.setString(2, entidad.getNombre_deposito());
         psInsertarSucursales.setInt(3, entidad.getEstado());
         psInsertarSucursales.setString(4, entidad.getTelefono());
         psInsertarSucursales.setString(5, entidad.getDireccion());
         psInsertarSucursales.setString(6, entidad.getEncargado());
         psInsertarSucursales.setInt(7, entidad.getCod_sucursal());
         psInsertarSucursales.setInt(8, entidad.getPos());
         psInsertarSucursales.setInt(9, entidad.getStock_negativo());
         psInsertarSucursales.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Deposito.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Deposito..", frame);
      } finally {
         new CerrarRecursos(psInsertarSucursales, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Deposito..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static DepositosE buscarDeposito(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\ndepositos.cod_deposito,depositos.nombre_deposito,depositos.estado,depositos.telefono,depositos.direccion,depositos.encargado,\r\ndepositos.cod_sucursal,depositos.pos,depositos.stock_negativo,sucursales.nombre_sucursal\r\n from depositos,sucursales\r\nwhere\r\ndepositos.cod_sucursal = sucursales.cod_sucursal and depositos.cod_deposito =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new DepositosE(
               resultado.getInt("cod_deposito"),
               resultado.getString("nombre_deposito"),
               resultado.getInt("estado"),
               resultado.getString("telefono"),
               resultado.getString("direccion"),
               resultado.getString("encargado"),
               resultado.getInt("cod_sucursal"),
               resultado.getInt("pos"),
               resultado.getInt("stock_negativo"),
               resultado.getString("nombre_sucursal")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Sucursal..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Sucursal..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static DepositosE buscarDeposito2(int codigo, JinternalPadreReporte frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select depositos.cod_deposito,depositos.nombre_deposito from depositos where  depositos.cod_deposito =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new DepositosE(resultado.getInt("cod_deposito"), resultado.getString("nombre_deposito"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Deposito..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Deposito..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static DepositosE buscarDeposito2(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select depositos.cod_deposito,depositos.nombre_deposito from depositos where  depositos.cod_deposito =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new DepositosE(resultado.getInt("cod_deposito"), resultado.getString("nombre_deposito"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Deposito..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Deposito..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static void cargarDepositoTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Depositos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Depositos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public DepositosE(int cod_deposito, String nombre_deposito) {
      this.cod_deposito = cod_deposito;
      this.nombre_deposito = nombre_deposito;
   }

   public DepositosE(
      int cod_deposito, String nombre_deposito, int estado, String telefono, String direccion, String encargado, int cod_sucursal, int pos, int stock_negativo
   ) {
      this.cod_deposito = cod_deposito;
      this.nombre_deposito = nombre_deposito;
      this.estado = estado;
      this.telefono = telefono;
      this.direccion = direccion;
      this.encargado = encargado;
      this.cod_sucursal = cod_sucursal;
      this.pos = pos;
      this.stock_negativo = stock_negativo;
   }

   public DepositosE(
      int cod_deposito,
      String nombre_deposito,
      int estado,
      String telefono,
      String direccion,
      String encargado,
      int cod_sucursal,
      int pos,
      int stock_negativo,
      String nombre_sucursal
   ) {
      this.cod_deposito = cod_deposito;
      this.nombre_deposito = nombre_deposito;
      this.estado = estado;
      this.telefono = telefono;
      this.direccion = direccion;
      this.encargado = encargado;
      this.cod_sucursal = cod_sucursal;
      this.pos = pos;
      this.stock_negativo = stock_negativo;
      this.nombre_sucursal = nombre_sucursal;
   }

   public int getCod_deposito() {
      return this.cod_deposito;
   }

   public void setCod_deposito(int cod_deposito) {
      this.cod_deposito = cod_deposito;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public int getCod_sucursal() {
      return this.cod_sucursal;
   }

   public void setCod_sucursal(int cod_sucursal) {
      this.cod_sucursal = cod_sucursal;
   }

   public int getPos() {
      return this.pos;
   }

   public void setPos(int pos) {
      this.pos = pos;
   }

   public int getStock_negativo() {
      return this.stock_negativo;
   }

   public void setStock_negativo(int stock_negativo) {
      this.stock_negativo = stock_negativo;
   }

   public String getNombre_deposito() {
      return this.nombre_deposito;
   }

   public void setNombre_deposito(String nombre_deposito) {
      this.nombre_deposito = nombre_deposito;
   }

   public String getTelefono() {
      return this.telefono;
   }

   public void setTelefono(String telefono) {
      this.telefono = telefono;
   }

   public String getDireccion() {
      return this.direccion;
   }

   public void setDireccion(String direccion) {
      this.direccion = direccion;
   }

   public String getEncargado() {
      return this.encargado;
   }

   public void setEncargado(String encargado) {
      this.encargado = encargado;
   }

   public String getNombre_sucursal() {
      return this.nombre_sucursal;
   }

   public void setNombre_sucursal(String nombre_sucursal) {
      this.nombre_sucursal = nombre_sucursal;
   }
}
