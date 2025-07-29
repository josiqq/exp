package a00Clientes;

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

public class ClientesE {
   private int cod_cliente;
   private int estado;
   private int tipo_fiscal;
   private int cod_categoria;
   private int cod_pais;
   private int cod_ciudad;
   private int cod_condicion_vta;
   private int cod_lista;
   private int tipo_precio;
   private String nombre_cliente;
   private String ruc;
   private String telefono;
   private String direccion;
   private String email;
   private String nombre_categoria;
   private String nombre_pais;
   private String nombre_ciudad;
   private String nombre_lista;
   private String nombre_condicion_vta;

   public static int eliminarClientes(ClientesE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarCliente = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarCliente = conexion.prepareStatement("delete from clientes where cod_cliente =?");
         psEliminarCliente.setInt(1, entidad.getCod_cliente());
         psEliminarCliente.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Cliente.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Cliente..", frame);
      } finally {
         new CerrarRecursos(psEliminarCliente, frame, "Error al eliminar Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarClientes(ClientesE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarClientes = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarClientes = conexion.prepareStatement(
            "update clientes set nombre_cliente=?,ruc=?,estado=?,tipo_fiscal=?,telefono=?,direccion=?,\r\nemail=?,cod_categoria=?,cod_pais=?,cod_ciudad=?,tipo_precio=?,cod_lista=?,cod_condicion_vta=? where cod_cliente =?"
         );
         psActualizarClientes.setString(1, entidad.getNombre_cliente());
         psActualizarClientes.setString(2, entidad.getRuc());
         psActualizarClientes.setInt(3, entidad.getEstado());
         psActualizarClientes.setInt(4, entidad.getTipo_fiscal());
         psActualizarClientes.setString(5, entidad.getTelefono());
         psActualizarClientes.setString(6, entidad.getDireccion());
         psActualizarClientes.setString(7, entidad.getEmail());
         psActualizarClientes.setInt(8, entidad.getCod_categoria());
         psActualizarClientes.setInt(9, entidad.getCod_pais());
         psActualizarClientes.setInt(10, entidad.getCod_ciudad());
         psActualizarClientes.setInt(11, entidad.getTipo_precio());
         psActualizarClientes.setInt(12, entidad.getCod_lista());
         psActualizarClientes.setInt(13, entidad.getCod_condicion_vta());
         psActualizarClientes.setInt(14, entidad.getCod_cliente());
         psActualizarClientes.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Cliente.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Cliente..", frame);
      } finally {
         new CerrarRecursos(psActualizarClientes, frame, "Error al actualizar Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarClientes(ClientesE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarCliente = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_cliente) as mayor from clientes");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarCliente = conexion.prepareStatement(
            "insert into clientes (cod_cliente,nombre_cliente,ruc,estado,tipo_fiscal,telefono,direccion,email,cod_categoria,cod_pais,cod_ciudad,tipo_precio,cod_lista,cod_condicion_vta) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
         );
         psInsertarCliente.setInt(1, siguienteRegistro);
         psInsertarCliente.setString(2, entidad.getNombre_cliente());
         psInsertarCliente.setString(3, entidad.getRuc());
         psInsertarCliente.setInt(4, entidad.getEstado());
         psInsertarCliente.setInt(5, entidad.getTipo_fiscal());
         psInsertarCliente.setString(6, entidad.getTelefono());
         psInsertarCliente.setString(7, entidad.getDireccion());
         psInsertarCliente.setString(8, entidad.getEmail());
         psInsertarCliente.setInt(9, entidad.getCod_categoria());
         psInsertarCliente.setInt(10, entidad.getCod_pais());
         psInsertarCliente.setInt(11, entidad.getCod_ciudad());
         psInsertarCliente.setInt(12, entidad.getTipo_precio());
         psInsertarCliente.setInt(13, entidad.getCod_lista());
         psInsertarCliente.setInt(14, entidad.getCod_condicion_vta());
         psInsertarCliente.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Cliente.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Cliente..", frame);
      } finally {
         new CerrarRecursos(psInsertarCliente, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static ClientesE buscarClientes3(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      ClientesE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_cliente,nombre_cliente,ruc from clientes where cod_cliente =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Cliente inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         ClientesE cliente = new ClientesE(resultado.getInt("cod_cliente"), resultado.getString("nombre_cliente"), resultado.getString("ruc"));
         var8 = cliente;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Clientes..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Clientes....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void cargarClientesTabla4(String sql, ModeloTablaDefecto modelo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt(1), resultado.getString(2), resultado.getString(3), resultado.getInt(4)});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Clientes..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Clientes..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarClientesTabla2(String sql, ModeloTablaDefecto modelo, JinternalPadre frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Clientes..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Clientes..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarClientesTabla(String sql, ModeloTablaDefecto modelo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt(1), resultado.getString(2), resultado.getString(3)});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Clientes..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Clientes..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static ClientesE buscarCliente(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\nclientes.cod_cliente,clientes.nombre_cliente,clientes.ruc,clientes.estado,\r\nclientes.tipo_fiscal,clientes.telefono,clientes.direccion,\r\nclientes.email,clientes.cod_categoria,clientes.cod_pais,clientes.cod_ciudad,\r\npaises.nombre_pais,ciudades.nombre_ciudad,clientes.cod_condicion_vta,clientes.cod_lista,clientes.tipo_precio,\r\n(select lista_precios_cab.nombre_lista from lista_precios_cab where lista_precios_cab.cod_lista = clientes.cod_lista) as nombre_lista,\r\nclientes_categoria.nombre_categoria,\r\n(select condiciones_ventas.nombre_condicion from condiciones_ventas where condiciones_ventas.cod_condicion = clientes.cod_condicion_vta) as nombre_condicion_vta\r\nfrom clientes,paises,ciudades,clientes_categoria\r\nwhere\r\nclientes.cod_ciudad = ciudades.cod_ciudad and clientes.cod_categoria = clientes_categoria.cod_categoria\r\nand clientes.cod_pais = paises.cod_pais and clientes.cod_cliente =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new ClientesE(
               resultado.getInt("cod_cliente"),
               resultado.getString("nombre_cliente"),
               resultado.getString("ruc"),
               resultado.getInt("estado"),
               resultado.getInt("tipo_fiscal"),
               resultado.getString("telefono"),
               resultado.getString("direccion"),
               resultado.getString("email"),
               resultado.getInt("cod_categoria"),
               resultado.getInt("cod_pais"),
               resultado.getInt("cod_ciudad"),
               resultado.getString("nombre_categoria"),
               resultado.getString("nombre_pais"),
               resultado.getString("nombre_ciudad"),
               resultado.getInt("cod_condicion_vta"),
               resultado.getInt("cod_lista"),
               resultado.getInt("tipo_precio"),
               resultado.getString("nombre_lista"),
               resultado.getString("nombre_ciudad")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cliente..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static ClientesE buscarCliente2(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\nclientes.cod_cliente,clientes.nombre_cliente from clientes where clientes.cod_cliente =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new ClientesE(resultado.getInt("cod_cliente"), resultado.getString("nombre_cliente"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cliente..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static ClientesE buscarCliente2(int codigo, JDialog frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\nclientes.cod_cliente,clientes.nombre_cliente from clientes where clientes.cod_cliente =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new ClientesE(resultado.getInt("cod_cliente"), resultado.getString("nombre_cliente"));
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cliente..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static ClientesE buscarCliente4(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\nclientes.cod_cliente,clientes.nombre_cliente,clientes.telefono,clientes.direccion from clientes where clientes.cod_cliente =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new ClientesE(
               resultado.getInt("cod_cliente"), resultado.getString("nombre_cliente"), resultado.getString("telefono"), resultado.getString("direccion")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cliente..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static ClientesE buscarCliente5(int cod_cliente, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\nclientes.cod_cliente,clientes.nombre_cliente,clientes.telefono,clientes.direccion,clientes.tipo_fiscal from clientes where clientes.cod_cliente =?"
         );
         preparedStatementSelect.setInt(1, cod_cliente);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new ClientesE(
               resultado.getInt("cod_cliente"),
               resultado.getString("nombre_cliente"),
               resultado.getString("telefono"),
               resultado.getString("direccion"),
               resultado.getInt("tipo_fiscal")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Cliente..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Cliente..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public ClientesE(int cod_cliente, String nombre_cliente, String telefono, String direccion, int tipo_fiscal) {
      this.cod_cliente = cod_cliente;
      this.nombre_cliente = nombre_cliente;
      this.telefono = telefono;
      this.direccion = direccion;
      this.tipo_fiscal = tipo_fiscal;
   }

   public ClientesE(int cod_cliente, String nombre_cliente, String telefono, String direccion) {
      this.cod_cliente = cod_cliente;
      this.nombre_cliente = nombre_cliente;
      this.telefono = telefono;
      this.direccion = direccion;
   }

   public ClientesE(int cod_cliente, String nombre_cliente) {
      this.cod_cliente = cod_cliente;
      this.nombre_cliente = nombre_cliente;
   }

   public ClientesE(int cod_cliente, String nombre_cliente, String ruc) {
      this.cod_cliente = cod_cliente;
      this.nombre_cliente = nombre_cliente;
      this.ruc = ruc;
   }

   public ClientesE(
      int cod_cliente,
      String nombre_cliente,
      String ruc,
      int estado,
      int tipo_fiscal,
      String telefono,
      String direccion,
      String email,
      int cod_categoria,
      int cod_pais,
      int cod_ciudad,
      int cod_condicion_vta,
      int cod_lista,
      int tipo_precio
   ) {
      this.cod_cliente = cod_cliente;
      this.nombre_cliente = nombre_cliente;
      this.ruc = ruc;
      this.estado = estado;
      this.tipo_fiscal = tipo_fiscal;
      this.telefono = telefono;
      this.direccion = direccion;
      this.email = email;
      this.cod_categoria = cod_categoria;
      this.cod_pais = cod_pais;
      this.cod_ciudad = cod_ciudad;
      this.cod_condicion_vta = cod_condicion_vta;
      this.cod_lista = cod_lista;
      this.tipo_precio = tipo_precio;
   }

   public ClientesE(
      int cod_cliente,
      String nombre_cliente,
      String ruc,
      int estado,
      int tipo_fiscal,
      String telefono,
      String direccion,
      String email,
      int cod_categoria,
      int cod_pais,
      int cod_ciudad,
      String nombre_categoria,
      String nombre_pais,
      String nombre_ciudad,
      int cod_condicion_vta,
      int cod_lista,
      int tipo_precio,
      String nombre_lista,
      String nombre_condicion_vta
   ) {
      this.cod_cliente = cod_cliente;
      this.nombre_cliente = nombre_cliente;
      this.ruc = ruc;
      this.estado = estado;
      this.tipo_fiscal = tipo_fiscal;
      this.telefono = telefono;
      this.direccion = direccion;
      this.email = email;
      this.cod_categoria = cod_categoria;
      this.cod_pais = cod_pais;
      this.cod_ciudad = cod_ciudad;
      this.nombre_categoria = nombre_categoria;
      this.nombre_pais = nombre_pais;
      this.nombre_ciudad = nombre_ciudad;
      this.cod_condicion_vta = cod_condicion_vta;
      this.cod_lista = cod_lista;
      this.tipo_precio = tipo_precio;
      this.nombre_lista = nombre_lista;
      this.nombre_condicion_vta = nombre_condicion_vta;
   }

   public int getCod_cliente() {
      return this.cod_cliente;
   }

   public void setCod_cliente(int cod_cliente) {
      this.cod_cliente = cod_cliente;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public int getTipo_fiscal() {
      return this.tipo_fiscal;
   }

   public void setTipo_fiscal(int tipo_fiscal) {
      this.tipo_fiscal = tipo_fiscal;
   }

   public int getCod_categoria() {
      return this.cod_categoria;
   }

   public void setCod_categoria(int cod_categoria) {
      this.cod_categoria = cod_categoria;
   }

   public int getCod_pais() {
      return this.cod_pais;
   }

   public void setCod_pais(int cod_pais) {
      this.cod_pais = cod_pais;
   }

   public int getCod_ciudad() {
      return this.cod_ciudad;
   }

   public void setCod_ciudad(int cod_ciudad) {
      this.cod_ciudad = cod_ciudad;
   }

   public String getNombre_cliente() {
      return this.nombre_cliente;
   }

   public void setNombre_cliente(String nombre_cliente) {
      this.nombre_cliente = nombre_cliente;
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

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getNombre_categoria() {
      return this.nombre_categoria;
   }

   public void setNombre_categoria(String nombre_categoria) {
      this.nombre_categoria = nombre_categoria;
   }

   public String getNombre_pais() {
      return this.nombre_pais;
   }

   public void setNombre_pais(String nombre_pais) {
      this.nombre_pais = nombre_pais;
   }

   public String getNombre_ciudad() {
      return this.nombre_ciudad;
   }

   public void setNombre_ciudad(String nombre_ciudad) {
      this.nombre_ciudad = nombre_ciudad;
   }

   public int getCod_condicion_vta() {
      return this.cod_condicion_vta;
   }

   public void setCod_condicion_vta(int cod_condicion_vta) {
      this.cod_condicion_vta = cod_condicion_vta;
   }

   public int getCod_lista() {
      return this.cod_lista;
   }

   public void setCod_lista(int cod_lista) {
      this.cod_lista = cod_lista;
   }

   public int getTipo_precio() {
      return this.tipo_precio;
   }

   public void setTipo_precio(int tipo_precio) {
      this.tipo_precio = tipo_precio;
   }

   public String getNombre_lista() {
      return this.nombre_lista;
   }

   public void setNombre_lista(String nombre_lista) {
      this.nombre_lista = nombre_lista;
   }

   public String getNombre_condicion_vta() {
      return this.nombre_condicion_vta;
   }

   public void setNombre_condicion_vta(String nombre_condicion_vta) {
      this.nombre_condicion_vta = nombre_condicion_vta;
   }
}
