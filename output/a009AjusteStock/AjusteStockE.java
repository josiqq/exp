package a009AjusteStock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTablaDetalle.ValidacionesTabla;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadreReporte;

public class AjusteStockE {
   private int nro_registro;
   private int cod_deposito;
   private int cod_motivo;
   private String fecha;
   private String hora_ajuste;
   private String usuario_alta;
   private String fecha_alta;
   private String nombre_deposito;
   private String nombre_motivo;
   private String observacion;

   public static int buscarAjusteReporteDetalle(int nro_registro, ModeloTablaDefecto modelo, JinternalPadreReporte frame) {
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement(
            "select\r\najuste_stock_detalle.cod_producto,productos.nombre_producto,ajuste_stock_detalle.cantidad\r\n from ajuste_stock_detalle,productos\r\nwhere\r\najuste_stock_detalle.cod_producto = productos.cod_producto\r\n and ajuste_stock_detalle.nro_registro =?"
         );
         preparedStatement.setInt(1, nro_registro);
         rs = preparedStatement.executeQuery();

         while (rs.next()) {
            modelo.addRow(new Object[]{rs.getString("cod_producto"), rs.getString("nombre_producto"), rs.getDouble("cantidad")});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Ajuste de Stock..", frame);
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar Ajuste de Stock..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void buscarAjusteReporte(
      DefaultListModel<Map<String, Object>> modeloLista, int cod_deposito, int cod_motivo, String fecha_ini, String fecha_fin, JinternalPadreReporte frame
   ) {
      modeloLista.clear();
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;
      SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement(
            "select\r\najuste_stock.nro_registro,\r\najuste_stock.fecha,\r\najuste_stock.cod_deposito,\r\ndepositos.nombre_deposito,\r\najuste_stock.cod_motivo,\r\najuste_stock_motivos.nombre_motivo,\r\najuste_stock.hora_ajuste,\r\najuste_stock.usuario_alta\r\n from ajuste_stock,depositos,ajuste_stock_motivos\r\nwhere\r\najuste_stock.cod_deposito = depositos.cod_deposito\r\nand ajuste_stock.cod_motivo = ajuste_stock_motivos.cod_motivo\r\nand ((ajuste_stock.cod_motivo = ?) or (? = 0))\r\nand ((ajuste_stock.cod_deposito = ?) or (? = 0))\r\nand ((ajuste_stock.fecha >= ?) or (? is null))\r\nand ((ajuste_stock.fecha <= ?) or (? is null))"
         );
         preparedStatement.setInt(1, cod_motivo);
         preparedStatement.setInt(2, cod_motivo);
         preparedStatement.setInt(3, cod_deposito);
         preparedStatement.setInt(4, cod_deposito);
         preparedStatement.setString(5, fecha_ini);
         preparedStatement.setString(6, fecha_ini);
         preparedStatement.setString(7, fecha_fin);
         preparedStatement.setString(8, fecha_fin);
         rs = preparedStatement.executeQuery();

         while (rs.next()) {
            Map<String, Object> map = new HashMap<>();
            map.put("nro_registro", rs.getInt("nro_registro"));
            map.put("fecha", formatoFecha.format(rs.getDate("fecha")));
            map.put("hora", rs.getString("hora_ajuste"));
            map.put("usuario", rs.getString("usuario_alta"));
            map.put("deposito", rs.getString("nombre_deposito"));
            map.put("motivo", rs.getString("nombre_motivo"));
            modeloLista.addElement(map);
         }
      } catch (Exception var15) {
         LogErrores.errores(var15, "Error al seleccionar Ajuste de Stock..", frame);
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar Ajuste de Stock..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static int insertarAjusteStock(AjusteStockE entidad, JTable tabla, JinternalPadre frame) {
      int codigoColumn = tabla.getColumn("Código").getModelIndex();
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int stockColumn = tabla.getColumn("Stock").getModelIndex();
      int costoColumn = tabla.getColumn("Costo").getModelIndex();
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
               "INSERT INTO ajuste_stock (fecha,cod_deposito,cod_motivo,observacion,hora_ajuste,fecha_alta,usuario_alta) VALUES (?,?,?,?, curtime(),NOW(), SUBSTRING_INDEX(USER(), '@', 1))",
               1
            );
            preparedStatement1.setString(1, entidad.getFecha());
            preparedStatement1.setInt(2, entidad.getCod_deposito());
            preparedStatement1.setInt(3, entidad.getCod_motivo());
            preparedStatement1.setString(4, entidad.getObservacion());
            preparedStatement1.executeUpdate();
            generatedKeys = preparedStatement1.getGeneratedKeys();
            if (generatedKeys.next()) {
               lastInsertId = generatedKeys.getInt(1);
            }

            preparedStatement2 = conexion.prepareStatement(
               "INSERT INTO ajuste_stock_detalle (nro_registro,cod_producto,cantidad_stock,cantidad,costo,fecha_alta,usuario_alta) VALUES (?,?,?,?,?,NOW(), SUBSTRING_INDEX(USER(), '@', 1))"
            );

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
                  preparedStatement2.setDouble(3, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, stockColumn)).replace(".", "").replace(",", ".")));
                  preparedStatement2.setDouble(4, cantidad);
                  preparedStatement2.setDouble(5, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, costoColumn)).replace(".", "").replace(",", ".")));
                  preparedStatement2.addBatch();
               }
            }

            preparedStatement2.executeBatch();
            conexion.commit();
            return 1;
         } catch (Exception var22) {
            try {
               conexion.rollback();
            } catch (SQLException var21) {
               LogErrores.errores(var21, "Error al insertar Ajuste de Stock.", frame);
            }

            LogErrores.errores(var22, "Error al insertar Ajuste de Stock..", frame);
         } finally {
            new CerrarRecursos(preparedStatement1, preparedStatement2, generatedKeys, frame, "Error al insertar Ajuste de Stock..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static int actualizarAjusteStock(AjusteStockE entidad, JinternalPadre frame, JTable tablaInsertar, List<Integer> codigosAEliminar) {
      int codigoColumn = tablaInsertar.getColumn("Código").getModelIndex();
      int descripcioncolumn = tablaInsertar.getColumn("Descripción").getModelIndex();
      int cantidadColumn = tablaInsertar.getColumn("Cantidad").getModelIndex();
      int stockColumn = tablaInsertar.getColumn("Stock").getModelIndex();
      int costoColumn = tablaInsertar.getColumn("Costo").getModelIndex();
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
            preparedStatementDelete = conexion.prepareStatement("delete from ajuste_stock_detalle where id = ?");
            preparedStatementInsert = conexion.prepareStatement(
               "INSERT INTO ajuste_stock_detalle (nro_registro,cod_producto,cantidad_stock,cantidad,costo,fecha_alta,usuario_alta) VALUES (?,?,?,?,?,NOW(), SUBSTRING_INDEX(USER(), '@', 1))"
            );
            preparedStatementUpdate = conexion.prepareStatement(
               "UPDATE ajuste_stock_detalle set cod_producto=?,cantidad_stock=?,cantidad=?,costo=? where id =?"
            );
            preparedStatement1 = conexion.prepareStatement(
               "Update ajuste_stock set fecha= ?,cod_deposito= ?,cod_motivo= ?,observacion=? where nro_registro = ?"
            );
            preparedStatement1.setString(1, entidad.getFecha());
            preparedStatement1.setInt(2, entidad.getCod_deposito());
            preparedStatement1.setInt(3, entidad.getCod_motivo());
            preparedStatement1.setString(4, entidad.getObservacion());
            preparedStatement1.setInt(5, registro);
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
                     preparedStatementInsert.setDouble(
                        3, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, stockColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.setDouble(4, cantidad);
                     preparedStatementInsert.setDouble(
                        5, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, costoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.addBatch();
                  } else if (!String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))
                     .trim()
                     .equals(String.valueOf(tablaInsertar.getValueAt(fila, SWColumn)).trim())) {
                     preparedStatementUpdate.setString(1, String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)));
                     preparedStatementUpdate.setDouble(
                        2, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, stockColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setDouble(3, cantidad);
                     preparedStatementUpdate.setDouble(
                        4, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, costoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setInt(5, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))));
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
         } catch (Exception var26) {
            try {
               conexion.rollback();
            } catch (SQLException var25) {
               LogErrores.errores(var25, "Error al insertar Ajuste Stock.", frame);
            }

            LogErrores.errores(var26, "Error al insertar Ajuste Stock..", frame);
         } finally {
            new CerrarRecursos(
               preparedStatementUpdate, preparedStatement1, preparedStatementInsert, preparedStatementDelete, frame, "Error al insertar Ajuste Stock.."
            );
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static int eliminarAjusteStock(AjusteStockE entidad, JinternalPadre frame) {
      int registro = entidad.getNro_registro();
      PreparedStatement preparedStatement2 = null;
      PreparedStatement preparedStatement1 = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement("delete from ajuste_stock_detalle where nro_registro =? ");
         preparedStatement2.setInt(1, registro);
         preparedStatement2.executeUpdate();
         preparedStatement1 = conexion.prepareStatement("delete from ajuste_stock where nro_registro =?");
         preparedStatement1.setInt(1, registro);
         preparedStatement1.executeUpdate();
         conexion.commit();
         return 1;
      } catch (Exception var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            LogErrores.errores(var12, "Error al eliminar Ajuste de Stock.", frame);
         }

         LogErrores.errores(var13, "Error al eliminar Ajuste de Stock..", frame);
      } finally {
         new CerrarRecursos(preparedStatement1, preparedStatement2, frame, "Error al eliminar Ajuste de Stock..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static AjusteStockE buscarAjusteStock(
      int registro, JinternalPadre frame, TablaDetalleAjusteStock tabla, int par_decimal_cantidad, int par_decimal_importe
   ) {
      ResultSet rs = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalCantidad = new FormatoDecimalOperacionSistema(par_decimal_cantidad);
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      ModeloTablaAjusteStock modelo = (ModeloTablaAjusteStock)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\najuste_stock_detalle.cod_producto,productos.nombre_producto,unidades_medidas.sigla,\r\najuste_stock_detalle.cantidad,ajuste_stock_detalle.cantidad_stock,ajuste_stock_detalle.costo,ajuste_stock_detalle.id\r\n from ajuste_stock_detalle,productos,unidades_medidas\r\nwhere\r\najuste_stock_detalle.cod_producto = productos.cod_producto\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida and ajuste_stock_detalle.nro_registro =?"
         );
         preparedStatement2.setInt(1, registro);
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            modelo.addRow(
               new Object[]{
                  rs2.getString("cod_producto"),
                  rs2.getString("nombre_producto"),
                  rs2.getString("sigla"),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad_stock")),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad")),
                  formatoDecimalPrecio.format(rs2.getDouble("costo")),
                  rs2.getInt("id"),
                  0
               }
            );
         }

         modelo.addDefaultRow();
         preparedStatement = conexion.prepareStatement(
            "select\r\najuste_stock.nro_registro,ajuste_stock.fecha,ajuste_stock.cod_deposito,\r\najuste_stock.cod_motivo,ajuste_stock.hora_ajuste,ajuste_stock.usuario_alta,ajuste_stock.fecha_alta,\r\ndepositos.nombre_deposito,ajuste_stock_motivos.nombre_motivo,ajuste_stock.observacion\r\n from ajuste_stock,depositos,ajuste_stock_motivos\r\nwhere\r\najuste_stock.cod_deposito = depositos.cod_deposito\r\nand ajuste_stock.cod_motivo = ajuste_stock_motivos.cod_motivo and ajuste_stock.nro_registro =?"
         );
         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (rs.next()) {
            return new AjusteStockE(
               rs.getInt("nro_registro"),
               rs.getString("fecha"),
               rs.getInt("cod_deposito"),
               rs.getInt("cod_motivo"),
               rs.getString("hora_ajuste"),
               rs.getString("usuario_alta"),
               rs.getString("fecha_alta"),
               rs.getString("nombre_deposito"),
               rs.getString("nombre_motivo"),
               rs.getString("nombre_motivo")
            );
         }
      } catch (Exception var19) {
         LogErrores.errores(var19, "Error al seleccionar Ajuste de Stock..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatement2, rs, rs2, frame, "Error al seleccionar Ajuste de Stock..");
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
         preparedStatement = conexion.prepareStatement("Select max(nro_registro) as mayor from ajuste_stock");
         rs = preparedStatement.executeQuery();
         if (!rs.next()) {
            return 0;
         }

         int siguienteRegistro = rs.getInt("mayor");
         var7 = siguienteRegistro;
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Ajuste de Stock..", frame);
         return 0;
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar Ajuste de Stock..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var7;
   }

   public AjusteStockE(
      int nro_registro, String fecha, int cod_deposito, int cod_motivo, String hora_ajuste, String usuario_alta, String fecha_alta, String observacion
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_deposito = cod_deposito;
      this.cod_motivo = cod_motivo;
      this.hora_ajuste = hora_ajuste;
      this.usuario_alta = usuario_alta;
      this.fecha_alta = fecha_alta;
      this.observacion = observacion;
   }

   public AjusteStockE(
      int nro_registro,
      String fecha,
      int cod_deposito,
      int cod_motivo,
      String hora_ajuste,
      String usuario_alta,
      String fecha_alta,
      String nombre_deposito,
      String nombre_motivo,
      String observacion
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_deposito = cod_deposito;
      this.cod_motivo = cod_motivo;
      this.hora_ajuste = hora_ajuste;
      this.usuario_alta = usuario_alta;
      this.fecha_alta = fecha_alta;
      this.nombre_deposito = nombre_deposito;
      this.nombre_motivo = nombre_motivo;
      this.observacion = observacion;
   }

   public AjusteStockE(int nro_registro, String fecha, int cod_deposito, int cod_motivo, String observacion) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
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

   public String getHora_ajuste() {
      return this.hora_ajuste;
   }

   public void setHora_ajuste(String hora_ajuste) {
      this.hora_ajuste = hora_ajuste;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
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

   public String getNombre_deposito() {
      return this.nombre_deposito;
   }

   public void setNombre_deposito(String nombre_deposito) {
      this.nombre_deposito = nombre_deposito;
   }

   public String getNombre_motivo() {
      return this.nombre_motivo;
   }

   public void setNombre_motivo(String nombre_motivo) {
      this.nombre_motivo = nombre_motivo;
   }

   public String getObservacion() {
      return this.observacion;
   }

   public void setObservacion(String observacion) {
      this.observacion = observacion;
   }
}
