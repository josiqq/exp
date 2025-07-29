package a2MenuPrincipal;

import a3Permisos.PermisosUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class ParametrosE {
   private String ruc;
   private String nombre_empresa;
   private String direccion;
   private String telefono;
   private String nombreMonedaSistema;
   private String nombreMonedaNacional;
   private int cod_moneda_sistema;
   private int cod_moneda_nacional;
   private int nro_version;
   private int cod_cliente;
   private String ruc_cliente;
   private String nombre_cliente;

   public static int actualizarParametros(ParametrosE entidad, JinternalPadre frame) {
      PermisosUsuario permisos = PermisosUsuario.getInstancia();
      if (permisos.tienePermiso("Parametros", "modificar")) {
         PreparedStatement psActualizarParametros = null;
         Connection conexion = null;

         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            psActualizarParametros = conexion.prepareStatement(
               "update sys_parametros set ruc=?,nombre_empresa=?,direccion=?,telefono=?,cod_moneda_sistema=?,cod_moneda_nacional=?,nro_version=?,cod_cliente =?"
            );
            psActualizarParametros.setString(1, entidad.getRuc());
            psActualizarParametros.setString(2, entidad.getNombre_empresa());
            psActualizarParametros.setString(3, entidad.getDireccion());
            psActualizarParametros.setString(4, entidad.getTelefono());
            psActualizarParametros.setInt(5, entidad.getCod_moneda_sistema());
            psActualizarParametros.setInt(6, entidad.getCod_moneda_nacional());
            psActualizarParametros.setInt(7, entidad.getNro_version());
            psActualizarParametros.setInt(8, entidad.getCod_cliente());
            psActualizarParametros.executeUpdate();
            conexion.commit();
            return 1;
         } catch (SQLException var12) {
            try {
               conexion.rollback();
            } catch (SQLException var11) {
               LogErrores.errores(var11, "Error al actualizar Parametros.", frame);
            }

            LogErrores.errores(var12, "Error al actualizar Parametros..", frame);
         } finally {
            new CerrarRecursos(psActualizarParametros, frame, "Error al actualizar Parametros..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No tienes permisos para modificar Parametros", "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         return 0;
      }
   }

   public static ParametrosE buscarParametrosGral(JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select sys_parametros.ruc,sys_parametros.nombre_empresa,sys_parametros.direccion,\r\nsys_parametros.telefono,sys_parametros.cod_moneda_sistema,sys_parametros.cod_moneda_nacional,\r\nsys_parametros.nro_version,\r\n(select monedas.nombre_moneda from monedas where monedas.cod_moneda =cod_moneda_sistema ) as monedaSistema,\r\n(select monedas.nombre_moneda from monedas where monedas.cod_moneda = cod_moneda_nacional) as monedaNacional,\r\nsys_parametros.cod_cliente,clientes.nombre_cliente,clientes.ruc as ruc_cliente\r\nfrom sys_parametros,clientes\r\nwhere\r\nsys_parametros.cod_cliente = clientes.cod_cliente"
         );
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new ParametrosE(
               resultado.getString("ruc"),
               resultado.getString("nombre_empresa"),
               resultado.getString("direccion"),
               resultado.getString("telefono"),
               resultado.getInt("cod_moneda_sistema"),
               resultado.getInt("cod_moneda_nacional"),
               resultado.getInt("nro_version"),
               resultado.getString("monedaSistema"),
               resultado.getString("monedaNacional"),
               resultado.getInt("cod_cliente"),
               resultado.getString("nombre_cliente"),
               resultado.getString("ruc_cliente")
            );
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Parametros Generales..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Parametros Generales....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static ParametrosE buscarParametros2(JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select nombre_empresa,nro_version  from sys_parametros");
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new ParametrosE(resultado.getString("nombre_empresa"), resultado.getInt("nro_version"));
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Parametros Generales..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Parametros Generales....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public ParametrosE(String nombre_empresa, int nro_version) {
      this.nombre_empresa = nombre_empresa;
      this.nro_version = nro_version;
   }

   public ParametrosE(
      String ruc, String nombre_empresa, String direccion, String telefono, int cod_moneda_sistema, int cod_moneda_nacional, int nro_version, int cod_cliente
   ) {
      this.ruc = ruc;
      this.nombre_empresa = nombre_empresa;
      this.direccion = direccion;
      this.telefono = telefono;
      this.cod_moneda_sistema = cod_moneda_sistema;
      this.cod_moneda_nacional = cod_moneda_nacional;
      this.nro_version = nro_version;
      this.cod_cliente = cod_cliente;
   }

   public ParametrosE(
      String ruc,
      String nombre_empresa,
      String direccion,
      String telefono,
      int cod_moneda_sistema,
      int cod_moneda_nacional,
      int nro_version,
      String nombreMonedaSistema,
      String nombreMonedaNacional,
      int cod_cliente,
      String nombre_cliente,
      String ruc_cliente
   ) {
      this.ruc = ruc;
      this.nombre_empresa = nombre_empresa;
      this.direccion = direccion;
      this.telefono = telefono;
      this.cod_moneda_sistema = cod_moneda_sistema;
      this.cod_moneda_nacional = cod_moneda_nacional;
      this.nro_version = nro_version;
      this.nombreMonedaSistema = nombreMonedaSistema;
      this.nombreMonedaNacional = nombreMonedaNacional;
      this.cod_cliente = cod_cliente;
      this.nombre_cliente = nombre_cliente;
      this.ruc_cliente = ruc_cliente;
   }

   public String getRuc() {
      return this.ruc;
   }

   public void setRuc(String ruc) {
      this.ruc = ruc;
   }

   public String getNombre_empresa() {
      return this.nombre_empresa;
   }

   public void setNombre_empresa(String nombre_empresa) {
      this.nombre_empresa = nombre_empresa;
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

   public int getCod_moneda_sistema() {
      return this.cod_moneda_sistema;
   }

   public void setCod_moneda_sistema(int cod_moneda_sistema) {
      this.cod_moneda_sistema = cod_moneda_sistema;
   }

   public int getCod_moneda_nacional() {
      return this.cod_moneda_nacional;
   }

   public void setCod_moneda_nacional(int cod_moneda_nacional) {
      this.cod_moneda_nacional = cod_moneda_nacional;
   }

   public int getNro_version() {
      return this.nro_version;
   }

   public void setNro_version(int nro_version) {
      this.nro_version = nro_version;
   }

   public String getNombreMonedaSistema() {
      return this.nombreMonedaSistema;
   }

   public void setNombreMonedaSistema(String nombreMonedaSistema) {
      this.nombreMonedaSistema = nombreMonedaSistema;
   }

   public String getNombreMonedaNacional() {
      return this.nombreMonedaNacional;
   }

   public void setNombreMonedaNacional(String nombreMonedaNacional) {
      this.nombreMonedaNacional = nombreMonedaNacional;
   }

   public int getCod_cliente() {
      return this.cod_cliente;
   }

   public void setCod_cliente(int cod_cliente) {
      this.cod_cliente = cod_cliente;
   }

   public String getRuc_cliente() {
      return this.ruc_cliente;
   }

   public void setRuc_cliente(String ruc_cliente) {
      this.ruc_cliente = ruc_cliente;
   }

   public String getNombre_cliente() {
      return this.nombre_cliente;
   }

   public void setNombre_cliente(String nombre_cliente) {
      this.nombre_cliente = nombre_cliente;
   }
}
