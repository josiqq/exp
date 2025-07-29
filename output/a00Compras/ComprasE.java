package a00Compras;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.F_OperacionesSistema;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTablaDetalle.ValidacionesTabla;
import utilidadesVentanas.JinternalPadre;

public class ComprasE {
   private int nro_registro;
   private int cod_comprador;
   private int cod_deposito;
   private int cod_moneda;
   private int nro_pedido;
   private int cod_proveedor;
   private int cod_tipo_documento;
   private int cod_plazo;
   private int cuotas;
   private int dias;
   private int timbrado;
   private String usuario_alta;
   private String fecha_alta;
   private String fecha_llegada;
   private String nro_factura;
   private String vencimiento_timbrado;
   private String fecha_factura;
   private String fecha_pedido;
   private String nombre_comprador;
   private String nombre_deposito;
   private String nombre_moneda;
   private String nombre_plazo;
   private String nombre_proveedor;
   private String nombre_tipo_documento;
   private double total;
   private double descuento;
   private double cotizacion;
   private BigDecimal total_iva10;
   private BigDecimal total_iva5;
   private BigDecimal total_exenta;
   private BigDecimal total_gravada10;
   private BigDecimal total_gravado5;

   public static int ultimoRegistro(JinternalPadre frame) {
      ResultSet rsMayor = null;
      Connection conexion = null;
      PreparedStatement preparedStatementMayor = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementMayor = conexion.prepareStatement("Select max(nro_registro) as mayor from compras");
         rsMayor = preparedStatementMayor.executeQuery();
         if (rsMayor.next()) {
            return rsMayor.getInt("mayor");
         }
      } catch (Exception var9) {
         LogErrores.errores(var9, "Error al seleccionar ultimo registro.", frame);
         return 0;
      } finally {
         new CerrarRecursos(preparedStatementMayor, rsMayor, frame, "Error al recuperar ultimo registro..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarCompras(ComprasE entidad, JTable tabla, JinternalPadre frame) {
      Connection conexion = null;
      int codigoColumn = tabla.getColumn("Código").getModelIndex();
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      int tipoFiscalColumn = tabla.getColumn("TF").getModelIndex();
      int ivaColumn = tabla.getColumn("IVA").getModelIndex();
      int regimenColumn = tabla.getColumn("REG").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int bonificacionColum = tabla.getColumn("Cant. Bonif").getModelIndex();
      int precioRealColumn = tabla.getColumn("Precio Real").getModelIndex();
      int descuentoColumn = tabla.getColumn("Desc.").getModelIndex();
      int precioNetoColumn = tabla.getColumn("Precio Neto").getModelIndex();
      int totalIvaColumn = tabla.getColumn("Total Iva").getModelIndex();
      int totalGravadoColumn = tabla.getColumn("Total Gravado").getModelIndex();
      int totalExentoColumn = tabla.getColumn("Total Exento").getModelIndex();
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      int gravadoColumn = tabla.getColumn("Gravado").getModelIndex();
      int lastInsertId = 0;
      PreparedStatement preparedStatementInsertCompra = null;
      PreparedStatement preparedStatementComprasDetalle = null;
      ResultSet generatedKeys = null;
      if (ValidacionesTabla.validarSinDetalles(tabla, frame)) {
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementInsertCompra = conexion.prepareStatement(
               "INSERT INTO compras (fecha_llegada,cod_comprador,cod_deposito,\r\ncod_moneda,nro_pedido,total_exenta,total_gravada10,total_gravado5,\r\ntotal_iva10,total_iva5,total,descuento,cotizacion,cod_proveedor,cod_tipo_documento,cod_plazo,cuotas,dias,nro_factura,fecha_factura,timbrado,vencimiento_timbrado,\r\nfecha_alta,usuario_alta,hora_compra) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(), SUBSTRING_INDEX(USER(), '@', 1),time(now()))",
               1
            );
            preparedStatementInsertCompra.setString(1, entidad.getFecha_llegada());
            preparedStatementInsertCompra.setInt(2, entidad.getCod_comprador());
            preparedStatementInsertCompra.setInt(3, entidad.getCod_deposito());
            preparedStatementInsertCompra.setInt(4, entidad.getCod_moneda());
            preparedStatementInsertCompra.setInt(5, entidad.getNro_pedido());
            preparedStatementInsertCompra.setBigDecimal(6, entidad.getTotal_exenta());
            preparedStatementInsertCompra.setBigDecimal(7, entidad.getTotal_gravada10());
            preparedStatementInsertCompra.setBigDecimal(8, entidad.getTotal_gravado5());
            preparedStatementInsertCompra.setBigDecimal(9, entidad.getTotal_iva10());
            preparedStatementInsertCompra.setBigDecimal(10, entidad.getTotal_iva5());
            preparedStatementInsertCompra.setDouble(11, entidad.getTotal());
            preparedStatementInsertCompra.setDouble(12, entidad.getDescuento());
            preparedStatementInsertCompra.setDouble(13, entidad.getCotizacion());
            preparedStatementInsertCompra.setInt(14, entidad.getCod_proveedor());
            preparedStatementInsertCompra.setInt(15, entidad.getCod_tipo_documento());
            preparedStatementInsertCompra.setInt(16, entidad.getCod_plazo());
            preparedStatementInsertCompra.setInt(17, entidad.getCuotas());
            preparedStatementInsertCompra.setInt(18, entidad.getDias());
            preparedStatementInsertCompra.setString(19, entidad.getNro_factura());
            preparedStatementInsertCompra.setString(20, entidad.getFecha_factura());
            preparedStatementInsertCompra.setInt(21, entidad.getTimbrado());
            preparedStatementInsertCompra.setString(22, entidad.getVencimiento_timbrado());
            preparedStatementInsertCompra.executeUpdate();
            generatedKeys = preparedStatementInsertCompra.getGeneratedKeys();
            if (generatedKeys.next()) {
               lastInsertId = generatedKeys.getInt(1);
            }

            preparedStatementComprasDetalle = conexion.prepareStatement(
               "INSERT INTO compras_detalle (nro_registro,cod_producto,tipo_fiscal,iva,porc_gravado,regimen,cantidad,cantidad_bonif,precio_bruto,precio_neto,\r\ndescuento,total,total_impuesto,total_gravado,total_exento) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
               if (!String.valueOf(tabla.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tabla.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tabla.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tabla.getValueAt(fila, descripcioncolumn) != null) {
                  double cantidad = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."));
                  double precio = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, precioNetoColumn)).replace(".", "").replace(",", "."));
                  if (ValidacionesTabla.validarCeros(tabla, fila, cantidad, "No se puede grabar registro con cantidad 0! ", frame)
                     && ValidacionesTabla.validarCeros(tabla, fila, precio, "No se puede grabar registro con precio 0! ", frame)) {
                     preparedStatementComprasDetalle.setInt(1, lastInsertId);
                     preparedStatementComprasDetalle.setString(2, String.valueOf(tabla.getValueAt(fila, codigoColumn)));
                     preparedStatementComprasDetalle.setInt(3, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn))));
                     preparedStatementComprasDetalle.setDouble(4, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, ivaColumn)).replace(",", ".")));
                     preparedStatementComprasDetalle.setDouble(5, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, gravadoColumn)).replace(",", ".")));
                     preparedStatementComprasDetalle.setDouble(6, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, regimenColumn)).replace(",", ".")));
                     preparedStatementComprasDetalle.setDouble(7, cantidad);
                     preparedStatementComprasDetalle.setDouble(
                        8, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, bonificacionColum)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementComprasDetalle.setBigDecimal(
                        9, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioRealColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementComprasDetalle.setBigDecimal(
                        10, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioNetoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementComprasDetalle.setDouble(
                        11, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, descuentoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementComprasDetalle.setBigDecimal(
                        12, new BigDecimal(String.valueOf(tabla.getValueAt(fila, subTotalColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementComprasDetalle.setBigDecimal(
                        13, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalIvaColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementComprasDetalle.setBigDecimal(
                        14, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalGravadoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementComprasDetalle.setBigDecimal(
                        15, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalExentoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementComprasDetalle.addBatch();
                  }
               }
            }

            preparedStatementComprasDetalle.executeBatch();
            conexion.commit();
            return 1;
         } catch (Exception var34) {
            try {
               conexion.rollback();
            } catch (SQLException var33) {
               LogErrores.errores(var33, "Error al insertar Compra.", frame);
            }

            LogErrores.errores(var34, "Error al insertar Compra..", frame);
         } finally {
            new CerrarRecursos(preparedStatementInsertCompra, preparedStatementComprasDetalle, generatedKeys, frame, "Error al insertar Compra..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static int actualizarCompras(ComprasE entidad, JTable tabla, JinternalPadre frame, List<Integer> codigosAEliminar) {
      int codigoColumn = tabla.getColumn("Código").getModelIndex();
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      int tipoFiscalColumn = tabla.getColumn("TF").getModelIndex();
      int ivaColumn = tabla.getColumn("IVA").getModelIndex();
      int SWColumn = tabla.getColumn("SW").getModelIndex();
      int regimenColumn = tabla.getColumn("REG").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int bonificacionColum = tabla.getColumn("Cant. Bonif").getModelIndex();
      int precioRealColumn = tabla.getColumn("Precio Real").getModelIndex();
      int descuentoColumn = tabla.getColumn("Desc.").getModelIndex();
      int precioNetoColumn = tabla.getColumn("Precio Neto").getModelIndex();
      int totalIvaColumn = tabla.getColumn("Total Iva").getModelIndex();
      int totalGravadoColumn = tabla.getColumn("Total Gravado").getModelIndex();
      int totalExentoColumn = tabla.getColumn("Total Exento").getModelIndex();
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      int primarioColumn = tabla.getColumn("Primario").getModelIndex();
      int gravadoColumn = tabla.getColumn("Gravado").getModelIndex();
      PreparedStatement preparedStatementUpdateCompra = null;
      PreparedStatement preparedStatementInsert = null;
      PreparedStatement preparedStatementDelete = null;
      PreparedStatement preparedStatementUpdate = null;
      Connection conexion = null;
      if (ValidacionesTabla.validarSinDetalles(tabla, frame)) {
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementUpdateCompra = conexion.prepareStatement(
               "update compras set fecha_llegada=?,cod_comprador=?,cod_deposito=?,\r\ncod_moneda=?,nro_pedido=?,total_exenta=?,total_gravada10=?,total_gravado5=?,\r\ntotal_iva10=?,total_iva5=?,total=?,descuento=?,cotizacion=?,cod_proveedor=?,cod_tipo_documento=?,cod_plazo=?,cuotas=?,dias=?,nro_factura=?, fecha_factura=?,timbrado=?, vencimiento_timbrado=? where nro_registro = ?"
            );
            preparedStatementUpdateCompra.setString(1, entidad.getFecha_llegada());
            preparedStatementUpdateCompra.setInt(2, entidad.getCod_comprador());
            preparedStatementUpdateCompra.setInt(3, entidad.getCod_deposito());
            preparedStatementUpdateCompra.setInt(4, entidad.getCod_moneda());
            preparedStatementUpdateCompra.setInt(5, entidad.getNro_pedido());
            preparedStatementUpdateCompra.setBigDecimal(6, entidad.getTotal_exenta());
            preparedStatementUpdateCompra.setBigDecimal(7, entidad.getTotal_gravada10());
            preparedStatementUpdateCompra.setBigDecimal(8, entidad.getTotal_gravado5());
            preparedStatementUpdateCompra.setBigDecimal(9, entidad.getTotal_iva10());
            preparedStatementUpdateCompra.setBigDecimal(10, entidad.getTotal_iva5());
            preparedStatementUpdateCompra.setDouble(11, entidad.getTotal());
            preparedStatementUpdateCompra.setDouble(12, entidad.getDescuento());
            preparedStatementUpdateCompra.setDouble(13, entidad.getCotizacion());
            preparedStatementUpdateCompra.setInt(14, entidad.getCod_proveedor());
            preparedStatementUpdateCompra.setInt(15, entidad.getCod_tipo_documento());
            preparedStatementUpdateCompra.setInt(16, entidad.getCod_plazo());
            preparedStatementUpdateCompra.setInt(17, entidad.getCuotas());
            preparedStatementUpdateCompra.setInt(18, entidad.getDias());
            preparedStatementUpdateCompra.setString(19, entidad.getFecha_factura());
            preparedStatementUpdateCompra.setString(20, entidad.getFecha_factura());
            preparedStatementUpdateCompra.setInt(21, entidad.getTimbrado());
            preparedStatementUpdateCompra.setString(22, entidad.getVencimiento_timbrado());
            preparedStatementUpdateCompra.setInt(23, entidad.getNro_registro());
            preparedStatementUpdateCompra.executeUpdate();
            preparedStatementDelete = conexion.prepareStatement("delete from compras_detalle where id = ?");
            preparedStatementInsert = conexion.prepareStatement(
               "INSERT INTO compras_detalle (nro_registro,cod_producto,tipo_fiscal,iva,porc_gravado,regimen,cantidad,cantidad_bonif,precio_bruto,precio_neto,\r\ndescuento,total,total_impuesto,total_gravado,total_exento) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );
            preparedStatementUpdate = conexion.prepareStatement(
               "update compras_detalle set cod_producto=?,tipo_fiscal=?,iva=?,regimen=?,porc_gravado=?,cantidad=?,cantidad_bonif=?,precio_bruto=?,\r\nprecio_neto=?,descuento=?,total=?,total_impuesto=?,total_gravado=?,total_exento=? where id =?"
            );

            for (int id : codigosAEliminar) {
               preparedStatementDelete.setInt(1, id);
               preparedStatementDelete.addBatch();
            }

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
               if (!String.valueOf(tabla.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tabla.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tabla.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tabla.getValueAt(fila, descripcioncolumn) != null) {
                  double cantidad = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."));
                  double precio = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, precioNetoColumn)).replace(".", "").replace(",", "."));
                  if (ValidacionesTabla.validarCeros(tabla, fila, cantidad, "No se puede grabar registro con cantidad 0! ", frame)
                     && ValidacionesTabla.validarCeros(tabla, fila, precio, "No se puede grabar registro con precio 0! ", frame)) {
                     if (String.valueOf(tabla.getValueAt(fila, primarioColumn)).trim().equals("0")) {
                        preparedStatementInsert.setInt(1, entidad.getNro_registro());
                        preparedStatementInsert.setString(2, String.valueOf(tabla.getValueAt(fila, codigoColumn)));
                        preparedStatementInsert.setInt(3, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn))));
                        preparedStatementInsert.setDouble(4, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, ivaColumn)).replace(",", ".")));
                        preparedStatementInsert.setDouble(5, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, gravadoColumn)).replace(",", ".")));
                        preparedStatementInsert.setDouble(6, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, regimenColumn)).replace(",", ".")));
                        preparedStatementInsert.setDouble(
                           7, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementInsert.setDouble(
                           8, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, bonificacionColum)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementInsert.setBigDecimal(
                           9, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioRealColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementInsert.setBigDecimal(
                           10, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioNetoColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementInsert.setDouble(
                           11, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, descuentoColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementInsert.setBigDecimal(
                           12, new BigDecimal(String.valueOf(tabla.getValueAt(fila, subTotalColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementInsert.setBigDecimal(
                           13, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalIvaColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementInsert.setBigDecimal(
                           14, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalGravadoColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementInsert.setBigDecimal(
                           15, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalExentoColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementInsert.addBatch();
                     } else if (!String.valueOf(tabla.getValueAt(fila, primarioColumn)).trim().equals(String.valueOf(tabla.getValueAt(fila, SWColumn)).trim())) {
                        preparedStatementUpdate.setString(1, String.valueOf(tabla.getValueAt(fila, codigoColumn)));
                        preparedStatementUpdate.setInt(2, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn))));
                        preparedStatementUpdate.setDouble(3, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, ivaColumn)).replace(",", ".")));
                        preparedStatementUpdate.setDouble(4, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, regimenColumn)).replace(",", ".")));
                        preparedStatementUpdate.setDouble(5, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, gravadoColumn)).replace(",", ".")));
                        preparedStatementUpdate.setDouble(
                           6, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementUpdate.setDouble(
                           7, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, bonificacionColum)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementUpdate.setBigDecimal(
                           8, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioRealColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementUpdate.setBigDecimal(
                           9, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioNetoColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementUpdate.setDouble(
                           10, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, descuentoColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementUpdate.setBigDecimal(
                           11, new BigDecimal(String.valueOf(tabla.getValueAt(fila, subTotalColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementUpdate.setBigDecimal(
                           12, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalIvaColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementUpdate.setBigDecimal(
                           13, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalGravadoColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementUpdate.setBigDecimal(
                           14, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalExentoColumn)).replace(".", "").replace(",", "."))
                        );
                        preparedStatementUpdate.setInt(15, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, primarioColumn))));
                        preparedStatementUpdate.addBatch();
                     }
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
         } catch (Exception var37) {
            try {
               conexion.rollback();
            } catch (SQLException var36) {
               LogErrores.errores(var36, "Error al actualizar Compra.", frame);
            }

            LogErrores.errores(var37, "Error al actualizar Compra..", frame);
         } finally {
            new CerrarRecursos(
               preparedStatementUpdateCompra, preparedStatementInsert, preparedStatementUpdate, preparedStatementDelete, frame, "Error al actualizar Compra.."
            );
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static int eliminarCompras(ComprasE entidad, JinternalPadre frame) {
      int registro = entidad.getNro_registro();
      PreparedStatement preparedStatementEliminarCompraDetalle = null;
      PreparedStatement preparedStatementEliminarCompra = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementEliminarCompraDetalle = conexion.prepareStatement("delete from compras_detalle where nro_registro =? ");
         preparedStatementEliminarCompraDetalle.setInt(1, registro);
         preparedStatementEliminarCompraDetalle.executeUpdate();
         preparedStatementEliminarCompra = conexion.prepareStatement("delete from compras where nro_registro =?");
         preparedStatementEliminarCompra.setInt(1, registro);
         preparedStatementEliminarCompra.executeUpdate();
         conexion.commit();
         return 1;
      } catch (Exception var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            LogErrores.errores(var12, "Error al eliminar Compras.", frame);
         }

         LogErrores.errores(var13, "Error al eliminar Compras..", frame);
      } finally {
         new CerrarRecursos(preparedStatementEliminarCompraDetalle, preparedStatementEliminarCompra, frame, "Error al eliminar Compra..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static ComprasE buscarCompra(int registro, TablaComprasDetalles tabla, int par_decimal_cantidad, JinternalPadre frame) {
      Connection conexion = null;
      ResultSet rs = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatement2 = null;
      ModeloTablaCompra modelo = (ModeloTablaCompra)tabla.getModel();
      modelo.deleteRow(tabla);
      FormatoDecimalOperacionSistema formatoDecimalCantidad = new FormatoDecimalOperacionSistema(par_decimal_cantidad);
      FormatoDecimalOperacionSistema formatoDecimalPrecio7 = new FormatoDecimalOperacionSistema(7);
      FormatoDecimalOperacionSistema formatoDecimal2 = new FormatoDecimalOperacionSistema(2);

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\n      compras_detalle.cod_producto,productos.nombre_producto,productos.tipo_fiscal,productos.iva_producto,productos.porcentaje_gravado,\r\n      productos.porc_regimen,unidades_medidas.sigla,compras_detalle.cantidad,compras_detalle.cantidad_bonif,compras_detalle.precio_bruto,\r\n      compras_detalle.descuento,compras_detalle.precio_neto,compras_detalle.total_impuesto,compras_detalle.total_gravado,compras_detalle.total_exento,\r\n      compras_detalle.total,compras_detalle.id\r\nfrom compras_detalle,productos,unidades_medidas\r\nwhere\r\ncompras_detalle.cod_producto = productos.cod_producto\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand compras_detalle.nro_registro =?"
         );
         preparedStatement2.setInt(1, registro);
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            modelo.addRow(
               new Object[]{
                  rs2.getString("cod_producto"),
                  rs2.getString("nombre_producto"),
                  rs2.getInt("tipo_fiscal"),
                  formatoDecimal2.format(rs2.getDouble("porcentaje_gravado")),
                  formatoDecimal2.format(rs2.getDouble("iva_producto")),
                  formatoDecimal2.format(rs2.getDouble("porc_regimen")),
                  rs2.getString("sigla"),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad")),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad_bonif")),
                  formatoDecimalPrecio7.format(rs2.getDouble("precio_bruto")),
                  formatoDecimalPrecio7.format(rs2.getDouble("precio_bruto")),
                  formatoDecimal2.format(rs2.getDouble("descuento")),
                  formatoDecimalPrecio7.format(rs2.getDouble("precio_neto")),
                  formatoDecimalPrecio7.format(rs2.getDouble("total_impuesto")),
                  formatoDecimalPrecio7.format(rs2.getDouble("total_gravado")),
                  formatoDecimalPrecio7.format(rs2.getDouble("total_exento")),
                  formatoDecimalPrecio7.format(rs2.getDouble("total")),
                  rs2.getInt("id"),
                  rs2.getInt("id")
               }
            );
         }

         modelo.addDefaultRow();
         preparedStatement = conexion.prepareStatement(
            "select\r\ncompras.nro_registro,compras.fecha_llegada,compras.cod_comprador,compras.cod_deposito,\r\ncompras.cod_moneda,compras.nro_pedido,compras.total_exenta,compras.total_gravada10,compras.total_gravado5,\r\ncompras.total_iva10,compras.total_iva5,compras.total,compras.descuento,compras.cotizacion,\r\ncompras.cod_proveedor,compras.cod_tipo_documento,compras.cod_plazo,compras.cuotas,compras.dias,\r\ncompras.usuario_alta,compras.fecha_alta,\r\ncompradores.nombre_comprador,depositos.nombre_deposito,monedas.nombre_moneda,plazos.nombre_plazo,proveedores.nombre_proveedor,\r\ntipo_documentos.nombre_tipo_documento, (select pedidos_proveedores.fecha from pedidos_proveedores where pedidos_proveedores.nro_registro = compras.nro_pedido) as fecha_pedido,\r\n compras.nro_factura,compras.fecha_factura,compras.timbrado,compras.vencimiento_timbrado from compras,compradores,depositos,monedas,plazos,proveedores,tipo_documentos\r\nwhere\r\ncompras.cod_comprador = compradores.cod_comprador\r\nand compras.cod_deposito = depositos.cod_deposito\r\nand compras.cod_moneda = monedas.cod_moneda\r\nand compras.cod_plazo = plazos.cod_plazo\r\nand compras.cod_proveedor = proveedores.cod_proveedor\r\nand compras.cod_tipo_documento = tipo_documentos.cod_tipo_documento and compras.nro_registro =?"
         );
         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (rs.next()) {
            return new ComprasE(
               registro,
               rs.getString("fecha_llegada"),
               rs.getInt("cod_comprador"),
               rs.getInt("cod_deposito"),
               rs.getInt("cod_moneda"),
               rs.getInt("nro_pedido"),
               rs.getBigDecimal("total_exenta"),
               rs.getBigDecimal("total_gravada10"),
               rs.getBigDecimal("total_gravado5"),
               rs.getBigDecimal("total_iva10"),
               rs.getBigDecimal("total_iva5"),
               rs.getDouble("total"),
               rs.getDouble("descuento"),
               rs.getDouble("cotizacion"),
               rs.getInt("cod_proveedor"),
               rs.getInt("cod_tipo_documento"),
               rs.getInt("cod_plazo"),
               rs.getInt("cuotas"),
               rs.getInt("dias"),
               rs.getString("usuario_alta"),
               rs.getString("fecha_alta"),
               rs.getString("nombre_comprador"),
               rs.getString("nombre_deposito"),
               rs.getString("nombre_moneda"),
               rs.getString("nombre_plazo"),
               rs.getString("nombre_proveedor"),
               rs.getString("nombre_tipo_documento"),
               rs.getString("fecha_pedido"),
               rs.getString("nro_factura"),
               rs.getString("fecha_factura"),
               rs.getInt("timbrado"),
               rs.getString("vencimiento_timbrado")
            );
         }
      } catch (Exception var21) {
         try {
            conexion.rollback();
         } catch (SQLException var20) {
            LogErrores.errores(var20, "Error al seleccionar Compras.", frame);
         }

         LogErrores.errores(var21, "Error al seleccionar Compras..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatement2, rs, rs2, frame, "Error al seleccionar Compra..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static ComprasE buscarPedido(
      int registro, int oc, TablaComprasDetalles tabla, int par_decimal_cantidad, int par_decimal_importe, JinternalPadre frame
   ) {
      ResultSet rs = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalCantidad = new FormatoDecimalOperacionSistema(par_decimal_cantidad);
      FormatoDecimalOperacionSistema formatoDecimalPrecio7 = new FormatoDecimalOperacionSistema(7);
      FormatoDecimalOperacionSistema formatoDecimal2 = new FormatoDecimalOperacionSistema(2);
      ModeloTablaCompra modelo = (ModeloTablaCompra)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      ComprasE var20;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         if (oc == 0) {
            preparedStatement = conexion.prepareStatement(
               "select\r\npedidos_proveedores.nro_registro,pedidos_proveedores.cod_moneda,\r\npedidos_proveedores.cod_proveedor,pedidos_proveedores.cod_tipo_doc,pedidos_proveedores.cod_plazo,\r\npedidos_proveedores.fecha,\r\nmonedas.nombre_moneda,proveedores.nombre_proveedor,tipo_documentos.nombre_tipo_documento,plazos.nombre_plazo,\r\nplazos.dias\r\n from pedidos_proveedores,monedas,proveedores,tipo_documentos,plazos\r\nwhere\r\npedidos_proveedores.cod_moneda = monedas.cod_moneda\r\nand pedidos_proveedores.cod_proveedor = proveedores.cod_proveedor\r\nand pedidos_proveedores.cod_tipo_doc = tipo_documentos.cod_tipo_documento\r\nand pedidos_proveedores.cod_plazo = plazos.cod_plazo  and not exists\r\n(\r\nselect * from orden_compra where orden_compra.nro_pedido = pedidos_proveedores.nro_registro) and pedidos_proveedores.nro_registro =?"
            );
         } else {
            preparedStatement = conexion.prepareStatement(
               "select\r\npedidos_proveedores.nro_registro,pedidos_proveedores.cod_moneda,\r\npedidos_proveedores.cod_proveedor,pedidos_proveedores.cod_tipo_doc,pedidos_proveedores.cod_plazo,\r\npedidos_proveedores.fecha,\r\nmonedas.nombre_moneda,proveedores.nombre_proveedor,tipo_documentos.nombre_tipo_documento,plazos.nombre_plazo,\r\nplazos.dias\r\n from pedidos_proveedores,monedas,proveedores,tipo_documentos,plazos\r\nwhere\r\npedidos_proveedores.cod_moneda = monedas.cod_moneda\r\nand pedidos_proveedores.cod_proveedor = proveedores.cod_proveedor\r\nand pedidos_proveedores.cod_tipo_doc = tipo_documentos.cod_tipo_documento\r\nand pedidos_proveedores.cod_plazo = plazos.cod_plazo  and  pedidos_proveedores.nro_registro =?"
            );
         }

         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (!rs.next()) {
            return null;
         }

         ComprasE entidad = new ComprasE(
            registro,
            rs.getInt("cod_proveedor"),
            rs.getInt("cod_tipo_doc"),
            rs.getInt("cod_plazo"),
            rs.getString("fecha"),
            rs.getString("nombre_moneda"),
            rs.getString("nombre_proveedor"),
            rs.getString("nombre_tipo_documento"),
            rs.getString("nombre_plazo"),
            rs.getInt("dias"),
            rs.getInt("cod_moneda")
         );
         preparedStatement2 = conexion.prepareStatement(
            "select\r\npedidos_proveedores_det.cod_producto,productos.nombre_producto,productos.tipo_fiscal,\r\nproductos.iva_producto,productos.porcentaje_gravado,\r\nproductos.porc_regimen,unidades_medidas.sigla,\r\npedidos_proveedores_det.cantidad,pedidos_proveedores_det.cantidad_bonif,pedidos_proveedores_det.precio,pedidos_proveedores_det.id\r\n from pedidos_proveedores_det,productos,unidades_medidas\r\nwhere\r\npedidos_proveedores_det.cod_producto = productos.cod_producto\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand pedidos_proveedores_det.nro_registro =?"
         );
         preparedStatement2.setInt(1, registro);
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            BigDecimal v_precio = new BigDecimal(rs2.getDouble("precio"));
            BigDecimal v_cantidad = new BigDecimal(rs2.getDouble("cantidad"));
            modelo.addRow(
               new Object[]{
                  rs2.getString("cod_producto"),
                  rs2.getString("nombre_producto"),
                  rs2.getInt("tipo_fiscal"),
                  formatoDecimal2.format(rs2.getDouble("porcentaje_gravado")),
                  formatoDecimal2.format(rs2.getDouble("iva_producto")),
                  formatoDecimal2.format(rs2.getDouble("porc_regimen")),
                  rs2.getString("sigla"),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad")),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad_bonif")),
                  formatoDecimalPrecio7.format(rs2.getDouble("precio")),
                  formatoDecimalPrecio7.format(rs2.getDouble("precio")),
                  0,
                  formatoDecimalPrecio7.format(rs2.getDouble("precio")),
                  0,
                  0,
                  0,
                  formatoDecimalPrecio7.format(F_OperacionesSistema.multiplicar(v_cantidad, v_precio)),
                  0
               }
            );
            CalculosTabla.calcularIvas(modelo.getRowCount() - 1, tabla);
         }

         modelo.addDefaultRow();
         var20 = entidad;
      } catch (Exception var25) {
         try {
            conexion.rollback();
         } catch (SQLException var24) {
            LogErrores.errores(var24, "Error al seleccionar Pedido.", frame);
         }

         LogErrores.errores(var25, "Error al seleccionar Pedido..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatement2, rs, rs2, frame, "Error al seleccionar Pedido..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var20;
   }

   public ComprasE(
      int nro_pedido,
      int cod_proveedor,
      int cod_tipo_documento,
      int cod_plazo,
      String fecha_pedido,
      String nombre_moneda,
      String nombre_proveedor,
      String nombre_tipo_documento,
      String nombre_plazo,
      int dias,
      int cod_moneda
   ) {
      this.nro_pedido = nro_pedido;
      this.cod_proveedor = cod_proveedor;
      this.cod_tipo_documento = cod_tipo_documento;
      this.cod_plazo = cod_plazo;
      this.fecha_pedido = fecha_pedido;
      this.nombre_moneda = nombre_moneda;
      this.nombre_proveedor = nombre_proveedor;
      this.nombre_tipo_documento = nombre_tipo_documento;
      this.nombre_plazo = nombre_plazo;
      this.dias = dias;
      this.cod_moneda = cod_moneda;
   }

   public ComprasE(
      int cod_moneda,
      int cod_proveedor,
      int cod_tipo_documento,
      int cod_plazo,
      String fecha_pedido,
      String nombre_moneda,
      String nombre_proveedor,
      String nombre_tipo_documento,
      String nombre_plazo,
      int dias
   ) {
      this.cod_moneda = cod_moneda;
      this.cod_proveedor = cod_proveedor;
      this.cod_tipo_documento = cod_tipo_documento;
      this.cod_plazo = cod_plazo;
      this.fecha_pedido = fecha_pedido;
      this.nombre_moneda = nombre_moneda;
      this.nombre_proveedor = nombre_proveedor;
      this.nombre_tipo_documento = nombre_tipo_documento;
      this.nombre_plazo = nombre_plazo;
      this.dias = dias;
   }

   public ComprasE(
      int nro_registro,
      String fecha_llegada,
      int cod_comprador,
      int cod_deposito,
      int cod_moneda,
      int nro_pedido,
      BigDecimal total_exenta,
      BigDecimal total_gravada10,
      BigDecimal total_gravado5,
      BigDecimal total_iva10,
      BigDecimal total_iva5,
      double total,
      double descuento,
      double cotizacion,
      int cod_proveedor,
      int cod_tipo_documento,
      int cod_plazo,
      int cuotas,
      int dias,
      String nro_factura,
      String vencimiento_timbrado,
      String fecha_factura,
      int timbrado
   ) {
      this.nro_registro = nro_registro;
      this.fecha_llegada = fecha_llegada;
      this.cod_comprador = cod_comprador;
      this.cod_deposito = cod_deposito;
      this.cod_moneda = cod_moneda;
      this.nro_pedido = nro_pedido;
      this.total_exenta = total_exenta;
      this.total_gravada10 = total_gravada10;
      this.total_gravado5 = total_gravado5;
      this.total_iva10 = total_iva10;
      this.total_iva5 = total_iva5;
      this.total = total;
      this.descuento = descuento;
      this.cotizacion = cotizacion;
      this.cod_proveedor = cod_proveedor;
      this.cod_tipo_documento = cod_tipo_documento;
      this.cod_plazo = cod_plazo;
      this.cuotas = cuotas;
      this.dias = dias;
      this.nro_factura = nro_factura;
      this.vencimiento_timbrado = vencimiento_timbrado;
      this.fecha_factura = fecha_factura;
      this.timbrado = timbrado;
   }

   public ComprasE(
      int nro_registro,
      String fecha_llegada,
      int cod_comprador,
      int cod_deposito,
      int cod_moneda,
      int nro_pedido,
      BigDecimal total_exenta,
      BigDecimal total_gravada10,
      BigDecimal total_gravado5,
      BigDecimal total_iva10,
      BigDecimal total_iva5,
      double total,
      double descuento,
      double cotizacion,
      int cod_proveedor,
      int cod_tipo_documento,
      int cod_plazo,
      int cuotas,
      int dias,
      String usuario_alta,
      String fecha_alta,
      String nombre_comprador,
      String nombre_deposito,
      String nombre_moneda,
      String nombre_plazo,
      String nombre_proveedor,
      String nombre_tipo_documento,
      String fecha_pedido,
      String nro_factura,
      String fecha_factura,
      int timbrado,
      String vencimiento_timbrado
   ) {
      this.nro_registro = nro_registro;
      this.fecha_llegada = fecha_llegada;
      this.cod_comprador = cod_comprador;
      this.cod_deposito = cod_deposito;
      this.cod_moneda = cod_moneda;
      this.nro_pedido = nro_pedido;
      this.total_exenta = total_exenta;
      this.total_gravada10 = total_gravada10;
      this.total_gravado5 = total_gravado5;
      this.total_iva10 = total_iva10;
      this.total_iva5 = total_iva5;
      this.total = total;
      this.descuento = descuento;
      this.cotizacion = cotizacion;
      this.usuario_alta = usuario_alta;
      this.fecha_alta = fecha_alta;
      this.nombre_comprador = nombre_comprador;
      this.nombre_deposito = nombre_deposito;
      this.nombre_moneda = nombre_moneda;
      this.cod_proveedor = cod_proveedor;
      this.cod_tipo_documento = cod_tipo_documento;
      this.cod_plazo = cod_plazo;
      this.cuotas = cuotas;
      this.dias = dias;
      this.nombre_plazo = nombre_plazo;
      this.nombre_proveedor = nombre_proveedor;
      this.nombre_tipo_documento = nombre_tipo_documento;
      this.fecha_pedido = fecha_pedido;
      this.nro_factura = nro_factura;
      this.fecha_factura = fecha_factura;
      this.timbrado = timbrado;
      this.vencimiento_timbrado = vencimiento_timbrado;
   }

   public int getNro_registro() {
      return this.nro_registro;
   }

   public void setNro_registro(int nro_registro) {
      this.nro_registro = nro_registro;
   }

   public int getCod_comprador() {
      return this.cod_comprador;
   }

   public void setCod_comprador(int cod_comprador) {
      this.cod_comprador = cod_comprador;
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

   public int getNro_pedido() {
      return this.nro_pedido;
   }

   public void setNro_pedido(int nro_pedido) {
      this.nro_pedido = nro_pedido;
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

   public String getFecha_llegada() {
      return this.fecha_llegada;
   }

   public void setFecha_llegada(String fecha_llegada) {
      this.fecha_llegada = fecha_llegada;
   }

   public String getNombre_comprador() {
      return this.nombre_comprador;
   }

   public void setNombre_comprador(String nombre_comprador) {
      this.nombre_comprador = nombre_comprador;
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

   public double getTotal() {
      return this.total;
   }

   public void setTotal(double total) {
      this.total = total;
   }

   public double getDescuento() {
      return this.descuento;
   }

   public void setDescuento(double descuento) {
      this.descuento = descuento;
   }

   public double getCotizacion() {
      return this.cotizacion;
   }

   public void setCotizacion(double cotizacion) {
      this.cotizacion = cotizacion;
   }

   public BigDecimal getTotal_iva10() {
      return this.total_iva10;
   }

   public void setTotal_iva10(BigDecimal total_iva10) {
      this.total_iva10 = total_iva10;
   }

   public BigDecimal getTotal_iva5() {
      return this.total_iva5;
   }

   public void setTotal_iva5(BigDecimal total_iva5) {
      this.total_iva5 = total_iva5;
   }

   public BigDecimal getTotal_exenta() {
      return this.total_exenta;
   }

   public void setTotal_exenta(BigDecimal total_exenta) {
      this.total_exenta = total_exenta;
   }

   public BigDecimal getTotal_gravada10() {
      return this.total_gravada10;
   }

   public void setTotal_gravada10(BigDecimal total_gravada10) {
      this.total_gravada10 = total_gravada10;
   }

   public BigDecimal getTotal_gravado5() {
      return this.total_gravado5;
   }

   public void setTotal_gravado5(BigDecimal total_gravado5) {
      this.total_gravado5 = total_gravado5;
   }

   public int getCod_proveedor() {
      return this.cod_proveedor;
   }

   public void setCod_proveedor(int cod_proveedor) {
      this.cod_proveedor = cod_proveedor;
   }

   public int getCod_tipo_documento() {
      return this.cod_tipo_documento;
   }

   public void setCod_tipo_documento(int cod_tipo_documento) {
      this.cod_tipo_documento = cod_tipo_documento;
   }

   public int getCod_plazo() {
      return this.cod_plazo;
   }

   public void setCod_plazo(int cod_plazo) {
      this.cod_plazo = cod_plazo;
   }

   public int getCuotas() {
      return this.cuotas;
   }

   public void setCuotas(int cuotas) {
      this.cuotas = cuotas;
   }

   public int getDias() {
      return this.dias;
   }

   public void setDias(int dias) {
      this.dias = dias;
   }

   public String getNombre_plazo() {
      return this.nombre_plazo;
   }

   public void setNombre_plazo(String nombre_plazo) {
      this.nombre_plazo = nombre_plazo;
   }

   public String getNombre_proveedor() {
      return this.nombre_proveedor;
   }

   public void setNombre_proveedor(String nombre_proveedor) {
      this.nombre_proveedor = nombre_proveedor;
   }

   public String getNombre_tipo_documento() {
      return this.nombre_tipo_documento;
   }

   public void setNombre_tipo_documento(String nombre_tipo_documento) {
      this.nombre_tipo_documento = nombre_tipo_documento;
   }

   public int getTimbrado() {
      return this.timbrado;
   }

   public void setTimbrado(int timbrado) {
      this.timbrado = timbrado;
   }

   public String getNro_factura() {
      return this.nro_factura;
   }

   public void setNro_factura(String nro_factura) {
      this.nro_factura = nro_factura;
   }

   public String getVencimiento_timbrado() {
      return this.vencimiento_timbrado;
   }

   public void setVencimiento_timbrado(String vencimiento_timbrado) {
      this.vencimiento_timbrado = vencimiento_timbrado;
   }

   public String getFecha_factura() {
      return this.fecha_factura;
   }

   public void setFecha_factura(String fecha_factura) {
      this.fecha_factura = fecha_factura;
   }

   public String getFecha_pedido() {
      return this.fecha_pedido;
   }

   public void setFecha_pedido(String fecha_pedido) {
      this.fecha_pedido = fecha_pedido;
   }
}
