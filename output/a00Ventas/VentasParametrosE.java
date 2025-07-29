package a00Ventas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class VentasParametrosE {
   private int cod_vendedor;
   private int cod_deposito;
   private int cod_moneda;
   private int cod_cliente;
   private int cod_tipo_documento;
   private int cod_condicion;
   private int cuotas;
   private int tipo_fiscal;
   private String nombre_vendedor;
   private String nombre_deposito;
   private String nombre_moneda;
   private String nombre_cliente;
   private String nombre_tipo_documento;
   private String nombre_condicion;
   private String ruc;
   private String telefono;
   private String direccion;

   public static VentasParametrosE buscarParametros(String usuario, JinternalPadre frame) {
      ResultSet resultadoVendedor = null;
      ResultSet resultadoParametros = null;
      ResultSet resultadoDeposito = null;
      ResultSet resultadoTipoDocumento = null;
      ResultSet resultadoCondicionVenta = null;
      PreparedStatement preparedStatementSelectVendedor = null;
      PreparedStatement preparedStatementSelectDeposito = null;
      PreparedStatement preparedStatementSelectParametros = null;
      PreparedStatement preparedStatementSelectTipoDocumentos = null;
      PreparedStatement preparedStatementSelectCondicionVenta = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelectVendedor = conexion.prepareStatement(
            "select\r\nsys_usuarios.cod_vendedor,\r\nvendedores.nombre_vendedor\r\n from sys_usuarios,vendedores\r\nwhere\r\nsys_usuarios.cod_vendedor = vendedores.cod_vendedor\r\nand sys_usuarios.usuario = ?"
         );
         preparedStatementSelectVendedor.setString(1, usuario);
         resultadoVendedor = preparedStatementSelectVendedor.executeQuery();
         int cod_vendedor;
         String nombre_vendedor;
         if (resultadoVendedor.next()) {
            cod_vendedor = resultadoVendedor.getInt("cod_vendedor");
            nombre_vendedor = resultadoVendedor.getString("nombre_vendedor");
         } else {
            cod_vendedor = 0;
            nombre_vendedor = "";
         }

         preparedStatementSelectDeposito = conexion.prepareStatement(
            "select\r\nsys_usuarios_depositos.cod_deposito,\r\ndepositos.nombre_deposito\r\n from sys_usuarios_depositos,depositos\r\nwhere\r\nsys_usuarios_depositos.cod_deposito = depositos.cod_deposito and sys_usuarios_depositos.usuario =?\r\n order by cod_deposito limit 1"
         );
         preparedStatementSelectDeposito.setString(1, usuario);
         resultadoDeposito = preparedStatementSelectDeposito.executeQuery();
         int cod_deposito;
         String nombre_deposito;
         if (resultadoDeposito.next()) {
            cod_deposito = resultadoDeposito.getInt("cod_deposito");
            nombre_deposito = resultadoDeposito.getString("nombre_deposito");
         } else {
            cod_deposito = 0;
            nombre_deposito = "";
         }

         preparedStatementSelectParametros = conexion.prepareStatement(
            "select\r\nsys_parametros.cod_cliente,\r\nclientes.nombre_cliente,\r\nclientes.ruc,clientes.tipo_fiscal,\r\nclientes.direccion,\r\nclientes.telefono,clientes.tipo_fiscal,\r\nsys_parametros.cod_moneda_sistema,\r\nmonedas.nombre_moneda\r\nfrom sys_parametros,clientes,monedas\r\nwhere\r\nsys_parametros.cod_cliente = clientes.cod_cliente\r\nand sys_parametros.cod_moneda_sistema = monedas.cod_moneda"
         );
         resultadoParametros = preparedStatementSelectParametros.executeQuery();
         int cod_moneda;
         int cod_cliente;
         int tipo_fiscal;
         String nombre_moneda;
         String nombre_cliente;
         String ruc;
         String direccion;
         String telefono;
         if (resultadoParametros.next()) {
            cod_cliente = resultadoParametros.getInt("cod_cliente");
            nombre_cliente = resultadoParametros.getString("nombre_cliente");
            tipo_fiscal = resultadoParametros.getInt("tipo_fiscal");
            ruc = resultadoParametros.getString("ruc");
            telefono = resultadoParametros.getString("telefono");
            direccion = resultadoParametros.getString("direccion");
            cod_moneda = resultadoParametros.getInt("cod_moneda_sistema");
            nombre_moneda = resultadoParametros.getString("nombre_moneda");
         } else {
            cod_cliente = 0;
            nombre_cliente = "";
            tipo_fiscal = 0;
            ruc = "";
            telefono = "";
            direccion = "";
            cod_moneda = 0;
            nombre_moneda = "";
         }

         preparedStatementSelectTipoDocumentos = conexion.prepareStatement(
            "select\r\nsys_usuarios_tipo_documentos.cod_tipo_documento,\r\ntipo_documentos.nombre_tipo_documento\r\n from sys_usuarios_tipo_documentos,tipo_documentos\r\nwhere\r\nsys_usuarios_tipo_documentos.cod_tipo_documento = tipo_documentos.cod_tipo_documento\r\nand tipo_documentos.ventas =1 and sys_usuarios_tipo_documentos.usuario = ?\r\norder by sys_usuarios_tipo_documentos.cod_tipo_documento limit 1"
         );
         preparedStatementSelectTipoDocumentos.setString(1, usuario);
         resultadoTipoDocumento = preparedStatementSelectTipoDocumentos.executeQuery();
         int cod_tipo_documento;
         String nombre_tipo_documento;
         if (resultadoTipoDocumento.next()) {
            cod_tipo_documento = resultadoTipoDocumento.getInt("cod_tipo_documento");
            nombre_tipo_documento = resultadoTipoDocumento.getString("nombre_tipo_documento");
         } else {
            cod_tipo_documento = 0;
            nombre_tipo_documento = "";
         }

         preparedStatementSelectCondicionVenta = conexion.prepareStatement(
            "select\r\nsys_usuarios_condicion_venta.cod_condicion_venta,\r\ncondiciones_ventas.nombre_condicion,\r\ncondiciones_ventas.cuotas\r\n from sys_usuarios_condicion_venta,condiciones_ventas\r\nwhere sys_usuarios_condicion_venta.cod_condicion_venta = condiciones_ventas.cod_condicion\r\nand sys_usuarios_condicion_venta.usuario = ?\r\norder by sys_usuarios_condicion_venta.cod_condicion_venta limit 1"
         );
         preparedStatementSelectCondicionVenta.setString(1, usuario);
         resultadoCondicionVenta = preparedStatementSelectCondicionVenta.executeQuery();
         int cod_condicion;
         int cuotas;
         String nombre_condicion;
         if (resultadoCondicionVenta.next()) {
            cod_condicion = resultadoCondicionVenta.getInt("cod_condicion_venta");
            nombre_condicion = resultadoCondicionVenta.getString("nombre_condicion");
            cuotas = resultadoCondicionVenta.getInt("cuotas");
         } else {
            cod_condicion = 0;
            nombre_condicion = "";
            cuotas = 0;
         }

         return new VentasParametrosE(
            cod_vendedor,
            nombre_vendedor,
            cod_deposito,
            nombre_deposito,
            cod_moneda,
            nombre_moneda,
            cod_cliente,
            nombre_cliente,
            ruc,
            telefono,
            direccion,
            cod_tipo_documento,
            nombre_tipo_documento,
            cod_condicion,
            nombre_condicion,
            cuotas,
            tipo_fiscal
         );
      } catch (Exception var35) {
         LogErrores.errores(var35, "Error al seleccionar Parametros Ventas..", frame);
      } finally {
         new CerrarRecursos(
            resultadoVendedor,
            resultadoParametros,
            resultadoDeposito,
            resultadoTipoDocumento,
            resultadoCondicionVenta,
            preparedStatementSelectVendedor,
            preparedStatementSelectDeposito,
            preparedStatementSelectParametros,
            preparedStatementSelectTipoDocumentos,
            preparedStatementSelectCondicionVenta,
            frame,
            "Error al seleccionar Parametros Ventas...."
         );
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public VentasParametrosE(
      int cod_vendedor,
      String nombre_vendedor,
      int cod_deposito,
      String nombre_deposito,
      int cod_moneda,
      String nombre_moneda,
      int cod_cliente,
      String nombre_cliente,
      String ruc,
      String telefono,
      String direccion,
      int cod_tipo_documento,
      String nombre_tipo_documento,
      int cod_condicion,
      String nombre_condicion,
      int cuotas,
      int tipo_fiscal
   ) {
      this.cod_vendedor = cod_vendedor;
      this.nombre_vendedor = nombre_vendedor;
      this.cod_deposito = cod_deposito;
      this.nombre_deposito = nombre_deposito;
      this.cod_moneda = cod_moneda;
      this.nombre_moneda = nombre_moneda;
      this.cod_cliente = cod_cliente;
      this.nombre_cliente = nombre_cliente;
      this.ruc = ruc;
      this.telefono = telefono;
      this.direccion = direccion;
      this.cod_tipo_documento = cod_tipo_documento;
      this.nombre_tipo_documento = nombre_tipo_documento;
      this.cod_condicion = cod_condicion;
      this.nombre_condicion = nombre_condicion;
      this.cuotas = cuotas;
      this.tipo_fiscal = tipo_fiscal;
   }

   public int getCod_vendedor() {
      return this.cod_vendedor;
   }

   public void setCod_vendedor(int cod_vendedor) {
      this.cod_vendedor = cod_vendedor;
   }

   public int getCod_deposito() {
      return this.cod_deposito;
   }

   public void setCod_deposito(int cod_deposito) {
      this.cod_deposito = cod_deposito;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public void setCod_moneda(int cod_moneda) {
      this.cod_moneda = cod_moneda;
   }

   public int getCod_cliente() {
      return this.cod_cliente;
   }

   public void setCod_cliente(int cod_cliente) {
      this.cod_cliente = cod_cliente;
   }

   public int getCod_tipo_documento() {
      return this.cod_tipo_documento;
   }

   public void setCod_tipo_documento(int cod_tipo_documento) {
      this.cod_tipo_documento = cod_tipo_documento;
   }

   public int getCod_condicion() {
      return this.cod_condicion;
   }

   public void setCod_condicion(int cod_condicion) {
      this.cod_condicion = cod_condicion;
   }

   public String getNombre_vendedor() {
      return this.nombre_vendedor;
   }

   public void setNombre_vendedor(String nombre_vendedor) {
      this.nombre_vendedor = nombre_vendedor;
   }

   public String getNombre_deposito() {
      return this.nombre_deposito;
   }

   public void setNombre_deposito(String nombre_deposito) {
      this.nombre_deposito = nombre_deposito;
   }

   public String getNombre_moneda() {
      return this.nombre_moneda;
   }

   public void setNombre_moneda(String nombre_moneda) {
      this.nombre_moneda = nombre_moneda;
   }

   public String getNombre_cliente() {
      return this.nombre_cliente;
   }

   public void setNombre_cliente(String nombre_cliente) {
      this.nombre_cliente = nombre_cliente;
   }

   public String getNombre_tipo_documento() {
      return this.nombre_tipo_documento;
   }

   public void setNombre_tipo_documento(String nombre_tipo_documento) {
      this.nombre_tipo_documento = nombre_tipo_documento;
   }

   public String getNombre_condicion() {
      return this.nombre_condicion;
   }

   public void setNombre_condicion(String nombre_condicion) {
      this.nombre_condicion = nombre_condicion;
   }

   public int getCuotas() {
      return this.cuotas;
   }

   public void setCuotas(int cuotas) {
      this.cuotas = cuotas;
   }

   public String getRuc() {
      return this.ruc;
   }

   public void setRuc(String ruc) {
      this.ruc = ruc;
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

   public int getTipo_fiscal() {
      return this.tipo_fiscal;
   }

   public void setTipo_fiscal(int tipo_fiscal) {
      this.tipo_fiscal = tipo_fiscal;
   }
}
