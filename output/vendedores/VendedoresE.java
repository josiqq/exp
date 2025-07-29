package vendedores;

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

public class VendedoresE {
   private double descuento_maximo;
   private int cod_vendedor;
   private int estado;
   private int cod_lugar_venta;
   private int supervisor;
   private int realiza_descuento;
   private String nombre_vendedor;
   private String clave;
   private String nombreLugar;

   public static int eliminarVendedores(VendedoresE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarMarca = conexion.prepareStatement("delete from vendedores where cod_vendedor =?");
         psEliminarMarca.setInt(1, entidad.getCod_vendedor());
         psEliminarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Vendedor.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Vendedor..", frame);
      } finally {
         new CerrarRecursos(psEliminarMarca, frame, "Error al eliminar Vendedor..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarVendedores(VendedoresE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarMarcas = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarMarcas = conexion.prepareStatement(
            "update vendedores set nombre_vendedor=?,estado=?,clave=?,cod_lugar_venta=?,supervisor=?,realiza_descuento=?,descuento_maximo=?  where cod_vendedor =?"
         );
         psActualizarMarcas.setString(1, entidad.getNombre_vendedor());
         psActualizarMarcas.setInt(2, entidad.getEstado());
         psActualizarMarcas.setString(3, entidad.getClave());
         psActualizarMarcas.setInt(4, entidad.getCod_lugar_venta());
         psActualizarMarcas.setInt(5, entidad.getSupervisor());
         psActualizarMarcas.setInt(6, entidad.getRealiza_descuento());
         psActualizarMarcas.setDouble(7, entidad.getDescuento_maximo());
         psActualizarMarcas.setInt(8, entidad.getCod_lugar_venta());
         psActualizarMarcas.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Vendedor.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Vendedor..", frame);
      } finally {
         new CerrarRecursos(psActualizarMarcas, frame, "Error al actualizar Vendedor..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarVendedores(VendedoresE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarMarca = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_vendedor) as mayor from vendedores");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarMarca = conexion.prepareStatement(
            "insert into vendedores (cod_vendedor,nombre_vendedor,estado,clave,cod_lugar_venta,supervisor,realiza_descuento,descuento_maximo) values(?,?,?,?,?,?,?,?)"
         );
         psInsertarMarca.setInt(1, siguienteRegistro);
         psInsertarMarca.setString(2, entidad.getNombre_vendedor());
         psInsertarMarca.setInt(3, entidad.getEstado());
         psInsertarMarca.setString(4, entidad.getClave());
         psInsertarMarca.setInt(5, entidad.getCod_lugar_venta());
         psInsertarMarca.setInt(6, entidad.getSupervisor());
         psInsertarMarca.setInt(7, entidad.getRealiza_descuento());
         psInsertarMarca.setDouble(8, entidad.getDescuento_maximo());
         psInsertarMarca.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Vendedor.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Vendedor..", frame);
      } finally {
         new CerrarRecursos(psInsertarMarca, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Vendedor..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarVendedoresTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Vendedores..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Vendedores..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarVendedoresTablaDialog(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Vendedores..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Vendedores..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static VendedoresE buscarVendedores2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      VendedoresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_vendedor,nombre_vendedor from vendedores where cod_vendedor =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Vendedor inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         VendedoresE vendedor = new VendedoresE(resultado.getInt("cod_vendedor"), resultado.getString("nombre_vendedor"));
         var8 = vendedor;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Vendedores..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Vendedores....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static VendedoresE buscarVendedores2Dialog(int codigo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      VendedoresE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_vendedor,nombre_vendedor from vendedores where cod_vendedor =? and estado =1");
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Vendedor inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         VendedoresE vendedor = new VendedoresE(resultado.getInt("cod_vendedor"), resultado.getString("nombre_vendedor"));
         var8 = vendedor;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Vendedores..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Vendedores....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void buscarVendedoresF3(JinternalPadre frame, ModeloTablaDefecto modelo) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select cod_vendedor,nombre_vendedor from compradores where estado =1");
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt("cod_vendedor"), resultado.getString("nombre_vendedor")});
         }
      } catch (Exception var9) {
         LogErrores.errores(var9, "Error al seleccionar Vendedores..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Vendedores....");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static VendedoresE buscarVendedor(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "Select vendedores.cod_vendedor,vendedores.nombre_vendedor,vendedores.estado,vendedores.clave,\r\nvendedores.cod_lugar_venta,vendedores.supervisor,vendedores.realiza_descuento,vendedores.descuento_maximo from vendedores,lugares_ventas,lugares_ventas.nombre_lugar where\r\nvendedores.cod_lugar_venta = lugares_ventas.cod_lugar and vendedores.cod_vendedor =?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new VendedoresE(
               resultado.getInt("cod_vendedor"),
               resultado.getString("nombre_vendedor"),
               resultado.getInt("estado"),
               resultado.getInt("cod_lugar_venta"),
               resultado.getInt("supervisor"),
               resultado.getInt("realiza_descuento"),
               resultado.getDouble("descuento_maximo"),
               resultado.getString("clave"),
               resultado.getString("nombreLugar")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Vendedor..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Vendedor..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public VendedoresE(int cod_vendedor, String nombre_vendedor) {
      this.cod_vendedor = cod_vendedor;
      this.nombre_vendedor = nombre_vendedor;
   }

   public VendedoresE(
      int cod_vendedor, String nombre_vendedor, int estado, int cod_lugar_venta, int supervisor, int realiza_descuento, double descuento_maximo, String clave
   ) {
      this.cod_vendedor = cod_vendedor;
      this.nombre_vendedor = nombre_vendedor;
      this.estado = estado;
      this.cod_lugar_venta = cod_lugar_venta;
      this.supervisor = supervisor;
      this.realiza_descuento = realiza_descuento;
      this.descuento_maximo = descuento_maximo;
      this.clave = clave;
   }

   public VendedoresE(
      int cod_vendedor,
      String nombre_vendedor,
      int estado,
      int cod_lugar_venta,
      int supervisor,
      int realiza_descuento,
      double descuento_maximo,
      String clave,
      String nombreLugar
   ) {
      this.cod_vendedor = cod_vendedor;
      this.nombre_vendedor = nombre_vendedor;
      this.estado = estado;
      this.cod_lugar_venta = cod_lugar_venta;
      this.supervisor = supervisor;
      this.realiza_descuento = realiza_descuento;
      this.descuento_maximo = descuento_maximo;
      this.clave = clave;
      this.nombreLugar = nombreLugar;
   }

   public int getCod_vendedor() {
      return this.cod_vendedor;
   }

   public void setCod_vendedor(int cod_vendedor) {
      this.cod_vendedor = cod_vendedor;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_vendedor() {
      return this.nombre_vendedor;
   }

   public void setNombre_vendedor(String nombre_vendedor) {
      this.nombre_vendedor = nombre_vendedor;
   }

   public double getDescuento_maximo() {
      return this.descuento_maximo;
   }

   public void setDescuento_maximo(double descuento_maximo) {
      this.descuento_maximo = descuento_maximo;
   }

   public int getCod_lugar_venta() {
      return this.cod_lugar_venta;
   }

   public void setCod_lugar_venta(int cod_lugar_venta) {
      this.cod_lugar_venta = cod_lugar_venta;
   }

   public int getSupervisor() {
      return this.supervisor;
   }

   public void setSupervisor(int supervisor) {
      this.supervisor = supervisor;
   }

   public int getRealiza_descuento() {
      return this.realiza_descuento;
   }

   public void setRealiza_descuento(int realiza_descuento) {
      this.realiza_descuento = realiza_descuento;
   }

   public String getClave() {
      return this.clave;
   }

   public void setClave(String clave) {
      this.clave = clave;
   }

   public String getNombreLugar() {
      return this.nombreLugar;
   }

   public void setNombreLugar(String nombreLugar) {
      this.nombreLugar = nombreLugar;
   }
}
