package a0098NotaCreditoCompra;

import a00Compras.ComprasE;
import a00Compras.ModeloTablaCompra;
import a00Compras.TablaComprasDetalles;
import java.math.BigDecimal;
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

public class NotaCreditoCompraE {
   private int nro_registro;
   private int cod_comprador;
   private int cod_deposito;
   private int cod_moneda;
   private int cod_proveedor;
   private int cod_tipo_documento_compra;
   private int timbrado_nc;
   private int nro_devolucion;
   private int cod_motivo;
   private int cod_tipo_documento_nc;
   private int timbrado_compra;
   private String fecha;
   private String fecha_documento;
   private String hora_nc;
   private String nro_documento_nc;
   private String vencimiento_timbrado;
   private String nro_factura_compra;
   private String usuario_alta;
   private String fecha_alta;
   private double total_exenta;
   private double total_gravada10;
   private double total_gravada5;
   private double total_iva10;
   private double total_iva5;
   private double total;

   public static int insertarComprasNC(NotaCreditoCompraE var0, JTable var1, JinternalPadre var2) {
      throw new Error(
         "Unresolved compilation problems: \n\tThe method getFecha_llegada() is undefined for the type NotaCreditoCompraE\n\tThe method getNro_pedido() is undefined for the type NotaCreditoCompraE\n\tThe method setBigDecimal(int, BigDecimal) in the type PreparedStatement is not applicable for the arguments (int, double)\n\tThe method setBigDecimal(int, BigDecimal) in the type PreparedStatement is not applicable for the arguments (int, double)\n\tThe method getTotal_gravado5() is undefined for the type NotaCreditoCompraE\n\tThe method setBigDecimal(int, BigDecimal) in the type PreparedStatement is not applicable for the arguments (int, double)\n\tThe method setBigDecimal(int, BigDecimal) in the type PreparedStatement is not applicable for the arguments (int, double)\n\tThe method getDescuento() is undefined for the type NotaCreditoCompraE\n\tThe method getCotizacion() is undefined for the type NotaCreditoCompraE\n\tThe method getCod_tipo_documento() is undefined for the type NotaCreditoCompraE\n\tThe method getCod_plazo() is undefined for the type NotaCreditoCompraE\n\tThe method getCuotas() is undefined for the type NotaCreditoCompraE\n\tThe method getDias() is undefined for the type NotaCreditoCompraE\n\tThe method getNro_factura() is undefined for the type NotaCreditoCompraE\n\tThe method getFecha_factura() is undefined for the type NotaCreditoCompraE\n\tThe method getTimbrado() is undefined for the type NotaCreditoCompraE\n"
      );
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

   public NotaCreditoCompraE(
      int nro_registro,
      String fecha,
      int cod_comprador,
      int cod_deposito,
      int cod_moneda,
      int cod_proveedor,
      int cod_tipo_documento_compra,
      String nro_factura_compra,
      int timbrado_compra,
      int cod_tipo_documento_nc,
      String nro_documento_nc,
      int timbrado_nc,
      String vencimiento_timbrado,
      String fecha_documento,
      int cod_motivo,
      String hora_nc,
      double total_exenta,
      double total_gravada10,
      double total_gravada5,
      double total_iva10,
      double total_iva5,
      double total,
      int nro_devolucion,
      String usuario_alta,
      String fecha_alta
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_comprador = cod_comprador;
      this.cod_deposito = cod_deposito;
      this.cod_moneda = cod_moneda;
      this.cod_proveedor = cod_proveedor;
      this.cod_tipo_documento_compra = cod_tipo_documento_compra;
      this.nro_factura_compra = nro_factura_compra;
      this.timbrado_compra = timbrado_compra;
      this.cod_tipo_documento_nc = cod_tipo_documento_nc;
      this.nro_documento_nc = nro_documento_nc;
      this.timbrado_nc = timbrado_nc;
      this.vencimiento_timbrado = vencimiento_timbrado;
      this.fecha_documento = fecha_documento;
      this.cod_motivo = cod_motivo;
      this.hora_nc = hora_nc;
      this.nro_devolucion = nro_devolucion;
      this.total_exenta = total_exenta;
      this.total_gravada10 = total_gravada10;
      this.total_gravada5 = total_gravada5;
      this.total_iva10 = total_iva10;
      this.total_iva5 = total_iva5;
      this.total = total;
      this.usuario_alta = usuario_alta;
      this.fecha_alta = fecha_alta;
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

   public int getCod_proveedor() {
      return this.cod_proveedor;
   }

   public void setCod_proveedor(int cod_proveedor) {
      this.cod_proveedor = cod_proveedor;
   }

   public int getCod_tipo_documento_compra() {
      return this.cod_tipo_documento_compra;
   }

   public void setCod_tipo_documento_compra(int cod_tipo_documento_compra) {
      this.cod_tipo_documento_compra = cod_tipo_documento_compra;
   }

   public String getNro_factura_compra() {
      return this.nro_factura_compra;
   }

   public void setNro_factura_compra(String nro_factura_compra) {
      this.nro_factura_compra = nro_factura_compra;
   }

   public int getNro_devolucion() {
      return this.nro_devolucion;
   }

   public void setNro_devolucion(int nro_devolucion) {
      this.nro_devolucion = nro_devolucion;
   }

   public int getCod_motivo() {
      return this.cod_motivo;
   }

   public void setCod_motivo(int cod_motivo) {
      this.cod_motivo = cod_motivo;
   }

   public int getCod_tipo_documento_nc() {
      return this.cod_tipo_documento_nc;
   }

   public void setCod_tipo_documento_nc(int cod_tipo_documento_nc) {
      this.cod_tipo_documento_nc = cod_tipo_documento_nc;
   }

   public int getTimbrado_compra() {
      return this.timbrado_compra;
   }

   public void setTimbrado_compra(int timbrado_compra) {
      this.timbrado_compra = timbrado_compra;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
   }

   public String getFecha_documento() {
      return this.fecha_documento;
   }

   public void setFecha_documento(String fecha_documento) {
      this.fecha_documento = fecha_documento;
   }

   public String getHora_nc() {
      return this.hora_nc;
   }

   public void setHora_nc(String hora_nc) {
      this.hora_nc = hora_nc;
   }

   public String getNro_documento_nc() {
      return this.nro_documento_nc;
   }

   public void setNro_documento_nc(String nro_documento_nc) {
      this.nro_documento_nc = nro_documento_nc;
   }

   public String getVencimiento_timbrado() {
      return this.vencimiento_timbrado;
   }

   public void setVencimiento_timbrado(String vencimiento_timbrado) {
      this.vencimiento_timbrado = vencimiento_timbrado;
   }

   public int getTimbrado_nc() {
      return this.timbrado_nc;
   }

   public void setTimbrado_nc(int timbrado_nc) {
      this.timbrado_nc = timbrado_nc;
   }

   public double getTotal_exenta() {
      return this.total_exenta;
   }

   public void setTotal_exenta(double total_exenta) {
      this.total_exenta = total_exenta;
   }

   public double getTotal_gravada10() {
      return this.total_gravada10;
   }

   public void setTotal_gravada10(double total_gravada10) {
      this.total_gravada10 = total_gravada10;
   }

   public double getTotal_gravada5() {
      return this.total_gravada5;
   }

   public void setTotal_gravada5(double total_gravada5) {
      this.total_gravada5 = total_gravada5;
   }

   public double getTotal_iva10() {
      return this.total_iva10;
   }

   public void setTotal_iva10(double total_iva10) {
      this.total_iva10 = total_iva10;
   }

   public double getTotal_iva5() {
      return this.total_iva5;
   }

   public void setTotal_iva5(double total_iva5) {
      this.total_iva5 = total_iva5;
   }

   public double getTotal() {
      return this.total;
   }

   public void setTotal(double total) {
      this.total = total;
   }
}
