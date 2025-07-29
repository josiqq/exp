package a9Proveedores;

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
import utilidadesVentanas.JinternalPadreReporte;
import utilidadesVentanas.JinternalPadreString;

public class ProveedoresE {
   private int cod_proveedor;
   private int estado;
   private int cod_rubro;
   private int cod_pais;
   private int cod_ciudad;
   private String nombre_proveedor;
   private String ruc;
   private String telefono;
   private String direccion;
   private String nombre_rubro;
   private String nombre_pais;
   private String nombre_ciudad;

   public static int eliminarProveedores(ProveedoresE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMarca = conexion.prepareStatement("delete from proveedores where cod_proveedor =?");
         psEliminarMarca.setInt(1, entidad.getCod_proveedor());
         psEliminarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Proveedor.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Proveedor..", frame);
      } finally {
         new CerrarRecursos(psEliminarMarca, frame, "Error al eliminar Proveedor..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarProveedores(ProveedoresE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarProveedores = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarProveedores = conexion.prepareStatement(
            "update proveedores set nombre_proveedor= ?,estado= ?,ruc= ?,telefono= ?,direccion= ?,cod_rubro=?,cod_pais=?,cod_ciudad=? where cod_proveedor = ?"
         );
         psActualizarProveedores.setString(1, entidad.getNombre_proveedor());
         psActualizarProveedores.setInt(2, entidad.getEstado());
         psActualizarProveedores.setString(3, entidad.getRuc());
         psActualizarProveedores.setString(4, entidad.getTelefono());
         psActualizarProveedores.setString(5, entidad.getDireccion());
         psActualizarProveedores.setInt(6, entidad.getCod_rubro());
         psActualizarProveedores.setInt(7, entidad.getCod_pais());
         psActualizarProveedores.setInt(8, entidad.getCod_ciudad());
         psActualizarProveedores.setInt(9, entidad.getCod_proveedor());
         psActualizarProveedores.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Proveedor.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Proveedor..", frame);
      } finally {
         new CerrarRecursos(psActualizarProveedores, frame, "Error al actualizar Proveedor..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarProveedores(ProveedoresE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarProveedor = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_proveedor) as mayor from proveedores");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarProveedor = conexion.prepareStatement(
            "insert into proveedores (cod_proveedor,nombre_proveedor,estado,ruc,telefono,direccion,cod_rubro,cod_pais,cod_ciudad) values(?,?,?,?,?,?,?,?,?)"
         );
         psInsertarProveedor.setInt(1, siguienteRegistro);
         psInsertarProveedor.setString(2, entidad.getNombre_proveedor());
         psInsertarProveedor.setInt(3, entidad.getEstado());
         psInsertarProveedor.setString(4, entidad.getRuc());
         psInsertarProveedor.setString(5, entidad.getTelefono());
         psInsertarProveedor.setString(6, entidad.getDireccion());
         psInsertarProveedor.setInt(7, entidad.getCod_rubro());
         psInsertarProveedor.setInt(8, entidad.getCod_pais());
         psInsertarProveedor.setInt(9, entidad.getCod_ciudad());
         psInsertarProveedor.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Proveedor.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Proveedor..", frame);
      } finally {
         new CerrarRecursos(psInsertarProveedor, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Proveedor..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarProveedoresTabla(String sql, ModeloTablaDefecto modelo, JinternalPadre frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Proveedor..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Proveedor..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarProveedoresTabla2(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Proveedor..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Proveedor..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static ProveedoresE buscarProveedores3(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      ProveedoresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_proveedor,nombre_proveedor,ruc from proveedores where cod_proveedor =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Proveedor inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         ProveedoresE proveedor = new ProveedoresE(resultado.getInt("cod_proveedor"), resultado.getString("nombre_proveedor"), resultado.getString("ruc"));
         var8 = proveedor;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Proveedor..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Proveedor....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static ProveedoresE buscarProveedor(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "Select cod_proveedor,nombre_proveedor,proveedores.estado,ruc,telefono,direccion,proveedores.cod_rubro,proveedores.cod_pais,proveedores.cod_ciudad,\r\nrubros.nombre_rubro,paises.nombre_pais,ciudades.nombre_ciudad  from proveedores,rubros,paises,ciudades where\r\nproveedores.cod_rubro = rubros.cod_rubro and proveedores.cod_pais = paises.cod_pais and proveedores.cod_ciudad = ciudades.cod_ciudad\r\nand proveedores.cod_proveedor =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new ProveedoresE(
               resultado.getInt("cod_proveedor"),
               resultado.getString("nombre_proveedor"),
               resultado.getInt("estado"),
               resultado.getString("ruc"),
               resultado.getString("telefono"),
               resultado.getString("direccion"),
               resultado.getInt("cod_rubro"),
               resultado.getInt("cod_pais"),
               resultado.getInt("cod_ciudad"),
               resultado.getString("nombre_rubro"),
               resultado.getString("nombre_pais"),
               resultado.getString("nombre_ciudad")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Proveedor..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Proveedor..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static ProveedoresE buscarProveedores2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      ProveedoresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_proveedor,nombre_proveedor from proveedores where cod_proveedor =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Proveedor inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         ProveedoresE proveedor = new ProveedoresE(resultado.getInt("cod_proveedor"), resultado.getString("nombre_proveedor"));
         var8 = proveedor;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Proveedor..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Proveedor....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static ProveedoresE buscarProveedores2(int codigo, JinternalPadreReporte frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      ProveedoresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_proveedor,nombre_proveedor from proveedores where cod_proveedor =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Proveedor inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         ProveedoresE proveedor = new ProveedoresE(resultado.getInt("cod_proveedor"), resultado.getString("nombre_proveedor"));
         var8 = proveedor;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Proveedor..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Proveedor....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static ProveedoresE buscarProveedores2(int codigo, JinternalPadreString frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      ProveedoresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_proveedor,nombre_proveedor from proveedores where cod_proveedor =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Proveedor inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         ProveedoresE proveedor = new ProveedoresE(resultado.getInt("cod_proveedor"), resultado.getString("nombre_proveedor"));
         var8 = proveedor;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Proveedor..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Proveedor....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static ProveedoresE buscarProveedores5(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      ProveedoresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_proveedor,nombre_proveedor,ruc,telefono,direccion from proveedores where cod_proveedor =? and estado =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Proveedor inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         ProveedoresE proveedor = new ProveedoresE(
            resultado.getInt("cod_proveedor"),
            resultado.getString("nombre_proveedor"),
            resultado.getString("ruc"),
            resultado.getString("telefono"),
            resultado.getString("direccion")
         );
         var8 = proveedor;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Proveedor..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Proveedor....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public ProveedoresE(int cod_proveedor, String nombre_proveedor, String ruc, String telefono, String direccion) {
      this.cod_proveedor = cod_proveedor;
      this.nombre_proveedor = nombre_proveedor;
      this.ruc = ruc;
      this.telefono = telefono;
      this.direccion = direccion;
   }

   public ProveedoresE(int cod_proveedor, String nombre_proveedor) {
      this.cod_proveedor = cod_proveedor;
      this.nombre_proveedor = nombre_proveedor;
   }

   public ProveedoresE(int cod_proveedor, String nombre_proveedor, String ruc) {
      this.cod_proveedor = cod_proveedor;
      this.nombre_proveedor = nombre_proveedor;
      this.ruc = ruc;
   }

   public ProveedoresE(
      int cod_proveedor, String nombre_proveedor, int estado, String ruc, String telefono, String direccion, int cod_rubro, int cod_pais, int cod_ciudad
   ) {
      this.cod_proveedor = cod_proveedor;
      this.nombre_proveedor = nombre_proveedor;
      this.estado = estado;
      this.ruc = ruc;
      this.telefono = telefono;
      this.direccion = direccion;
      this.cod_rubro = cod_rubro;
      this.cod_pais = cod_pais;
      this.cod_ciudad = cod_ciudad;
   }

   public ProveedoresE(
      int cod_proveedor,
      String nombre_proveedor,
      int estado,
      String ruc,
      String telefono,
      String direccion,
      int cod_rubro,
      int cod_pais,
      int cod_ciudad,
      String nombre_rubro,
      String nombre_pais,
      String nombre_ciudad
   ) {
      this.cod_proveedor = cod_proveedor;
      this.nombre_proveedor = nombre_proveedor;
      this.estado = estado;
      this.ruc = ruc;
      this.telefono = telefono;
      this.direccion = direccion;
      this.cod_rubro = cod_rubro;
      this.cod_pais = cod_pais;
      this.cod_ciudad = cod_ciudad;
      this.nombre_rubro = nombre_rubro;
      this.nombre_pais = nombre_pais;
      this.nombre_ciudad = nombre_ciudad;
   }

   public int getCod_proveedor() {
      return this.cod_proveedor;
   }

   public void setCod_proveedor(int cod_proveedor) {
      this.cod_proveedor = cod_proveedor;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_proveedor() {
      return this.nombre_proveedor;
   }

   public void setNombre_proveedor(String nombre_proveedor) {
      this.nombre_proveedor = nombre_proveedor;
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

   public int getCod_rubro() {
      return this.cod_rubro;
   }

   public void setCod_rubro(int cod_rubro) {
      this.cod_rubro = cod_rubro;
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

   public String getNombre_rubro() {
      return this.nombre_rubro;
   }

   public void setNombre_rubro(String nombre_rubro) {
      this.nombre_rubro = nombre_rubro;
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
}
