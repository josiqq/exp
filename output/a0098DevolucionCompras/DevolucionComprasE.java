package a0098DevolucionCompras;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTablaDetalle.ValidacionesTabla;
import utilidadesVentanas.JinternalPadre;

public class DevolucionComprasE {
   private int nro_registro;
   private int cod_proveedor;
   private int cod_deposito;
   private int cod_motivo;
   private String fecha;
   private String observacion;
   private String usuario_alta;
   private String fecha_alta;
   private String nombre_proveedor;
   private String nombre_deposito;
   private String nombre_motivo;

   public static int insertarDevolucionProveedor(DevolucionComprasE entidad, JTable tabla, JinternalPadre frame) {
      int codigoColumn = tabla.getColumn("Código").getModelIndex();
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      if (ValidacionesTabla.validarSinDetalles(tabla, frame)) {
         int lastInsertId = 0;
         PreparedStatement preparedStatement1 = null;
         PreparedStatement preparedStatement2 = null;
         ResultSet generatedKeys = null;
         Connection conexion = null;

         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatement1 = conexion.prepareStatement(
               "INSERT INTO devoluciones_proveedores (fecha,cod_proveedor,cod_deposito,cod_motivo,observacion,fecha_alta,usuario_alta) VALUES (?,?,?,?,?,NOW(), SUBSTRING_INDEX(USER(), '@', 1))",
               1
            );
            preparedStatement1.setString(1, entidad.getFecha());
            preparedStatement1.setInt(2, entidad.getCod_proveedor());
            preparedStatement1.setInt(3, entidad.getCod_deposito());
            preparedStatement1.setInt(4, entidad.getCod_motivo());
            preparedStatement1.setString(5, entidad.getObservacion());
            preparedStatement1.executeUpdate();
            generatedKeys = preparedStatement1.getGeneratedKeys();
            if (generatedKeys.next()) {
               lastInsertId = generatedKeys.getInt(1);
            }

            preparedStatement2 = conexion.prepareStatement("INSERT INTO devoluciones_proveedores_det (nro_registro,cod_producto,cantidad) VALUES (?,?,?)");

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
               if (!String.valueOf(tabla.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tabla.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tabla.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tabla.getValueAt(fila, descripcioncolumn) != null) {
                  double cantidad = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."));
                  if (!ValidacionesTabla.validarCeros(tabla, fila, cantidad, "No se puede grabar registro con cantidad 0! ", frame)) {
                     return 0;
                  }

                  preparedStatement2.setInt(1, lastInsertId);
                  preparedStatement2.setString(2, String.valueOf(tabla.getValueAt(fila, codigoColumn)));
                  preparedStatement2.setDouble(3, cantidad);
                  preparedStatement2.addBatch();
               }
            }

            preparedStatement2.executeBatch();
            conexion.commit();
            return 1;
         } catch (Exception var20) {
            try {
               conexion.rollback();
            } catch (SQLException var19) {
               LogErrores.errores(var19, "Error al insertar Devolucion.", frame);
            }

            LogErrores.errores(var20, "Error al insertar Devolucion..", frame);
         } finally {
            new CerrarRecursos(preparedStatement1, preparedStatement2, generatedKeys, frame, "Error al insertar Devolucion..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static int actualizaDevolucionCompra(DevolucionComprasE entidad, JinternalPadre frame, JTable tablaInsertar, List<Integer> codigosAEliminar) {
      int codigoColumn = tablaInsertar.getColumn("Código").getModelIndex();
      int descripcioncolumn = tablaInsertar.getColumn("Descripción").getModelIndex();
      int cantidadColumn = tablaInsertar.getColumn("Cantidad").getModelIndex();
      int primarioColumn = tablaInsertar.getColumn("Primario").getModelIndex();
      int SWColumn = tablaInsertar.getColumn("SW").getModelIndex();
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatementInsert = null;
      PreparedStatement preparedStatementDelete = null;
      PreparedStatement preparedStatementUpdate = null;
      Connection conexion = null;
      int registro = entidad.getNro_registro();
      if (ValidacionesTabla.validarSinDetalles(tablaInsertar, frame)) {
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementDelete = conexion.prepareStatement("delete from devoluciones_proveedores_det where id = ?");
            preparedStatementInsert = conexion.prepareStatement("INSERT INTO devoluciones_proveedores_det (nro_registro,cod_producto,cantidad) values (?,?,?)");
            preparedStatementUpdate = conexion.prepareStatement("UPDATE devoluciones_proveedores_det set cod_producto=?,cantidad=? where id =?");
            preparedStatement1 = conexion.prepareStatement(
               "Update devoluciones_proveedores set fecha= ?,cod_proveedor= ?,cod_deposito= ?,cod_motivo =?,observacion=? where nro_registro = ?"
            );
            preparedStatement1.setString(1, entidad.getFecha());
            preparedStatement1.setInt(2, entidad.getCod_proveedor());
            preparedStatement1.setInt(3, entidad.getCod_deposito());
            preparedStatement1.setInt(4, entidad.getCod_motivo());
            preparedStatement1.setString(5, entidad.getObservacion());
            preparedStatement1.setInt(6, registro);
            preparedStatement1.executeUpdate();

            for (int id : codigosAEliminar) {
               preparedStatementDelete.setInt(1, id);
               preparedStatementDelete.addBatch();
            }

            for (int fila = 0; fila < tablaInsertar.getRowCount(); fila++) {
               if (!String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tablaInsertar.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tablaInsertar.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tablaInsertar.getValueAt(fila, descripcioncolumn) != null) {
                  double cantidad = Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."));
                  if (!ValidacionesTabla.validarCeros(tablaInsertar, fila, cantidad, "No se puede grabar registro con cantidad 0! ", frame)) {
                     return 0;
                  }

                  if (String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn)).trim().equals("0")) {
                     preparedStatementInsert.setInt(1, registro);
                     preparedStatementInsert.setString(2, String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)));
                     preparedStatementInsert.setDouble(3, cantidad);
                     preparedStatementInsert.addBatch();
                  } else if (!String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))
                     .trim()
                     .equals(String.valueOf(tablaInsertar.getValueAt(fila, SWColumn)).trim())) {
                     preparedStatementUpdate.setString(1, String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)));
                     preparedStatementUpdate.setDouble(2, cantidad);
                     preparedStatementUpdate.setInt(3, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))));
                     preparedStatementUpdate.addBatch();
                  }
               }
            }

            if (preparedStatementInsert != null) {
               preparedStatementInsert.executeBatch();
            }

            if (preparedStatementUpdate != null) {
               preparedStatementUpdate.executeBatch();
            }

            if (preparedStatementDelete != null) {
               preparedStatementDelete.executeBatch();
            }

            conexion.commit();
            return 1;
         } catch (Exception var24) {
            try {
               conexion.rollback();
            } catch (SQLException var23) {
               LogErrores.errores(var23, "Error al insertar Devolucion.", frame);
            }

            LogErrores.errores(var24, "Error al insertar Devolucion..", frame);
         } finally {
            new CerrarRecursos(
               preparedStatementUpdate, preparedStatement1, preparedStatementInsert, preparedStatementDelete, frame, "Error al insertar Devolucion.."
            );
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static int eliminarDevolucion(DevolucionComprasE entidad, JinternalPadre frame) {
      int registro = entidad.getNro_registro();
      PreparedStatement preparedStatement2 = null;
      PreparedStatement preparedStatement1 = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement("delete from devoluciones_proveedores_det where nro_registro =? ");
         preparedStatement2.setInt(1, registro);
         preparedStatement2.executeUpdate();
         preparedStatement1 = conexion.prepareStatement("delete from devoluciones_proveedores where nro_registro =?");
         preparedStatement1.setInt(1, registro);
         preparedStatement1.executeUpdate();
         conexion.commit();
         return 1;
      } catch (Exception var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            LogErrores.errores(var12, "Error al eliminar Devolucion.", frame);
         }

         LogErrores.errores(var13, "Error al eliminar Devolucion..", frame);
      } finally {
         new CerrarRecursos(preparedStatement1, preparedStatement2, frame, "Error al eliminar Devolucion..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static DevolucionComprasE buscarDevolucion(
      int registro, JinternalPadre frame, TablaDetalleDevolucionCompras tabla, int par_decimal_cantidad, int par_decimal_importe
   ) {
      ResultSet rs = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalCantidad = new FormatoDecimalOperacionSistema(par_decimal_cantidad);
      new FormatoDecimalOperacionSistema(par_decimal_importe);
      ModeloTablaDevolucionCompras modelo = (ModeloTablaDevolucionCompras)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\ndevoluciones_proveedores_det.nro_registro,devoluciones_proveedores_det.cod_producto,productos.nombre_producto,cantidad,unidades_medidas.sigla,\r\ndevoluciones_proveedores_det.id\r\n from devoluciones_proveedores_det,productos,unidades_medidas\r\nwhere\r\ndevoluciones_proveedores_det.cod_producto = productos.cod_producto\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida and devoluciones_proveedores_det.nro_registro =?"
         );
         preparedStatement2.setInt(1, registro);
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            modelo.addRow(
               new Object[]{
                  rs2.getString("cod_producto"),
                  rs2.getString("nombre_producto"),
                  rs2.getString("sigla"),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad")),
                  rs2.getInt("id"),
                  0
               }
            );
         }

         modelo.addDefaultRow();
         preparedStatement = conexion.prepareStatement(
            "select\r\ndevoluciones_proveedores.nro_registro, devoluciones_proveedores.fecha,devoluciones_proveedores.cod_proveedor,\r\ndevoluciones_proveedores.cod_deposito,devoluciones_proveedores.cod_motivo,devoluciones_proveedores.observacion,\r\nproveedores.nombre_proveedor,depositos.nombre_deposito,motivos_nc.nombre_motivo\r\nfrom devoluciones_proveedores,proveedores,depositos,motivos_nc\r\nwhere\r\ndevoluciones_proveedores.cod_proveedor = proveedores.cod_proveedor\r\nand devoluciones_proveedores.cod_deposito = depositos.cod_deposito\r\nand devoluciones_proveedores.cod_motivo = motivos_nc.cod_motivo and devoluciones_proveedores.nro_registro =?"
         );
         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (rs.next()) {
            return new DevolucionComprasE(
               rs.getInt("nro_registro"),
               rs.getString("fecha"),
               rs.getInt("cod_proveedor"),
               rs.getInt("cod_deposito"),
               rs.getInt("cod_motivo"),
               rs.getString("observacion"),
               rs.getString("nombre_proveedor"),
               rs.getString("nombre_deposito"),
               rs.getString("nombre_motivo")
            );
         }
      } catch (Exception var19) {
         LogErrores.errores(var19, "Error al seleccionar Devolucion..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatement2, rs, rs2, frame, "Error al seleccionar Devolucion..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static int ultimoRegistro(JinternalPadre frame) {
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      int var7;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("Select max(nro_registro) as mayor from devoluciones_proveedores");
         rs = preparedStatement.executeQuery();
         if (!rs.next()) {
            return 0;
         }

         int siguienteRegistro = rs.getInt("mayor");
         var7 = siguienteRegistro;
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Devolucion..", frame);
         return 0;
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar Devolucion..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var7;
   }

   public DevolucionComprasE(
      int nro_registro,
      String fecha,
      int cod_proveedor,
      int cod_deposito,
      int cod_motivo,
      String observacion,
      String nombre_proveedor,
      String nombre_deposito,
      String nombre_motivo
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_proveedor = cod_proveedor;
      this.cod_deposito = cod_deposito;
      this.cod_motivo = cod_motivo;
      this.observacion = observacion;
      this.nombre_proveedor = nombre_proveedor;
      this.nombre_deposito = nombre_deposito;
      this.nombre_motivo = nombre_motivo;
   }

   public DevolucionComprasE(int nro_registro, String fecha, int cod_proveedor, int cod_deposito, int cod_motivo, String observacion) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_proveedor = cod_proveedor;
      this.cod_deposito = cod_deposito;
      this.cod_motivo = cod_motivo;
      this.observacion = observacion;
   }

   public int getNro_registro() {
      return this.nro_registro;
   }

   public void setNro_registro(int nro_registro) {
      this.nro_registro = nro_registro;
   }

   public int getCod_proveedor() {
      return this.cod_proveedor;
   }

   public void setCod_proveedor(int cod_proveedor) {
      this.cod_proveedor = cod_proveedor;
   }

   public int getCod_deposito() {
      return this.cod_deposito;
   }

   public void setCod_deposito(int cod_deposito) {
      this.cod_deposito = cod_deposito;
   }

   public int getCod_motivo() {
      return this.cod_motivo;
   }

   public void setCod_motivo(int cod_motivo) {
      this.cod_motivo = cod_motivo;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
   }

   public String getObservacion() {
      return this.observacion;
   }

   public void setObservacion(String observacion) {
      this.observacion = observacion;
   }

   public String getUsuario_alta() {
      return this.usuario_alta;
   }

   public void setUsuario_alta(String usuario_alta) {
      this.usuario_alta = usuario_alta;
   }

   public String getFecha_alta() {
      return this.fecha_alta;
   }

   public void setFecha_alta(String fecha_alta) {
      this.fecha_alta = fecha_alta;
   }

   public String getNombre_proveedor() {
      return this.nombre_proveedor;
   }

   public void setNombre_proveedor(String nombre_proveedor) {
      this.nombre_proveedor = nombre_proveedor;
   }

   public String getNombre_motivo() {
      return this.nombre_motivo;
   }

   public void setNombre_motivo(String nombre_motivo) {
      this.nombre_motivo = nombre_motivo;
   }

   public String getNombre_deposito() {
      return this.nombre_deposito;
   }

   public void setNombre_deposito(String nombre_deposito) {
      this.nombre_deposito = nombre_deposito;
   }
}
