package a00Ventas;

import a00Cotizaciones.CotizacionesE;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import utilidades.LabelPrincipal;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTablaDetalle.ValidacionesTabla;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadreReporte;

public class VentasE {
   private int timbrado;
   private int numeracion;
   private int nro_factura;
   private int id_peticion;
   private int nro_registro;
   private int cod_cliente;
   private int cod_moneda;
   private int cod_deposito;
   private int cod_vendedor;
   private int cod_tipo_documento;
   private int cod_condicion;
   private int dias;
   private int cuotas;
   private int tipo_fiscal_cliente;
   private String fecha;
   private String nombre_cliente;
   private String ruc;
   private String direccion;
   private String telefono;
   private String observacion;
   private String usuario_alta;
   private String fecha_alta;
   private String nombre_moneda;
   private String nombre_deposito;
   private String nombre_vendedor;
   private String nombre_tipo_documento;
   private String nombre_condicion;
   private double porcentaje_descuento;
   private double importe_descuento;
   private double importe_gravado10;
   private double importe_gravado5;
   private double importe_iva5;
   private double importe_iva10;
   private double importe_exento;
   private double importe_total;
   private double valor_moneda1;
   private double valor_moneda2;
   private double valor_moneda3;
   private double valor_moneda4;

   public static void buscarVentasTablaReporte(
      String fecha_inicial,
      String fecha_final,
      String cod_producto,
      int cod_cliente,
      int cod_deposito,
      JTable tabla,
      int par_decimal_importe,
      LabelPrincipal lblTotalVenta,
      JinternalPadreReporte frame
   ) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      ModeloTablaDefecto modelo = (ModeloTablaDefecto)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\nventas.nro_registro,\r\nventas.fecha,\r\nventas.cod_deposito,\r\ndepositos.nombre_deposito,\r\nmonedas.sigla,\r\nventas.cod_cliente,\r\nventas.nombre_cliente,\r\nventas.ruc,\r\nventas.importe_total\r\n from ventas,ventas_detalle,depositos,monedas,productos\r\nwhere\r\n(ventas.cod_deposito = depositos.cod_deposito)\r\nand (ventas.cod_moneda = monedas.cod_moneda)\r\nand (ventas.nro_registro = ventas_detalle.nro_registro)\r\nand (ventas_detalle.cod_producto = productos.cod_producto) and ventas.fecha between (?) and (?)\r\nand ((productos.cod_producto =?) or (?=0))\r\nand ((ventas.cod_deposito =?) or (?=0))\r\nand ((ventas.cod_cliente = ?) or (?=0)) group by ventas.nro_registro"
         );
         preparedStatement2.setString(1, fecha_inicial);
         preparedStatement2.setString(2, fecha_final);
         preparedStatement2.setString(3, cod_producto);
         preparedStatement2.setString(4, cod_producto);
         preparedStatement2.setInt(5, cod_deposito);
         preparedStatement2.setInt(6, cod_deposito);
         preparedStatement2.setInt(7, cod_cliente);
         preparedStatement2.setInt(8, cod_cliente);
         rs2 = preparedStatement2.executeQuery();
         int fila = 0;
         double v_total_general = 0.0;
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         FormatoDecimalOperacionSistema formatoDecimalImporte = new FormatoDecimalOperacionSistema(2);
         if (tabla.getRowCount() - 1 == 0) {
            modelo.removeRow(0);
         }

         while (rs2.next()) {
            modelo.addRow(new Object[tabla.getColumnCount()]);
            fila = tabla.getRowCount() - 1;
            tabla.setValueAt(rs2.getString("nro_registro"), fila, tabla.getColumn("Nro. Registro").getModelIndex());
            tabla.setValueAt(dateFormat.format(rs2.getDate("fecha")), fila, tabla.getColumn("Fecha").getModelIndex());
            tabla.setValueAt(rs2.getInt("cod_deposito") + " - " + rs2.getString("nombre_deposito"), fila, tabla.getColumn("Deposito").getModelIndex());
            tabla.setValueAt(rs2.getInt("cod_cliente") + " - " + rs2.getString("nombre_cliente"), fila, tabla.getColumn("Cliente").getModelIndex());
            tabla.setValueAt(formatoDecimalImporte.format(rs2.getDouble("importe_total")), fila, tabla.getColumn("Total Venta").getModelIndex());
            tabla.setValueAt(rs2.getString("sigla"), fila, tabla.getColumn("Moneda").getModelIndex());
            v_total_general += rs2.getDouble("importe_total");
         }

         lblTotalVenta.setText(String.valueOf(formatoDecimalImporte.format(v_total_general)));
      } catch (Exception var22) {
         LogErrores.errores(var22, "Error al Cargar Venta..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al Cargar Venta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static int ultimoRegistro(JinternalPadre frame) {
      ResultSet rsMayor = null;
      Connection conexion = null;
      PreparedStatement preparedStatementMayor = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementMayor = conexion.prepareStatement("Select max(nro_registro) as mayor from ventas");
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

   public static int insertarVta(VentasE entidad, JTable tabla, JinternalPadre frame) {
      int codigoColumn = tabla.getColumn("Código").getModelIndex();
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      int tipoFiscalColumn = tabla.getColumn("TF").getModelIndex();
      int ivaColumn = tabla.getColumn("IVA").getModelIndex();
      int porcentajeGravadoColumn = tabla.getColumn("Gravado").getModelIndex();
      int regimenColumn = tabla.getColumn("REG").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int precioBrutoColumn = tabla.getColumn("Precio Bruto").getModelIndex();
      int descuentoColumn = tabla.getColumn("Desc.").getModelIndex();
      int precioVtaColumn = tabla.getColumn("Precio Venta").getModelIndex();
      int totalIvaColumn = tabla.getColumn("Total Iva").getModelIndex();
      int totalGravadoColumn = tabla.getColumn("Total Gravado").getModelIndex();
      int totalExentoColumn = tabla.getColumn("Total Exento").getModelIndex();
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      int costoColumn = tabla.getColumn("Costo").getModelIndex();
      int codListaPrecioColumn = tabla.getColumn("CodLista").getModelIndex();
      PreparedStatement preparedStatementInsertVta = null;
      PreparedStatement preparedStatementVtaDetalle = null;
      ResultSet generatedKeys = null;
      Connection conexion = null;
      int lastInsertId = 0;
      if (ValidacionesTabla.validarSinDetalles(tabla, frame)) {
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementInsertVta = conexion.prepareStatement(
               "insert into ventas (fecha,hora_venta,cod_moneda,cod_deposito,cod_vendedor,\r\ncod_cliente,nombre_cliente,ruc,direccion,telefono,cod_tipo_documento,\r\ncod_condicion,dias,cuotas,porcentaje_descuento,importe_descuento,observacion,\r\nimporte_gravado10,importe_gravado5,importe_iva10,importe_iva5,importe_exento,importe_total,fecha_alta,usuario_alta,timbrado,nro_factura,id_peticion) values (?,TIME(NOW()),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(), SUBSTRING_INDEX(USER(), '@', 1),?,?,?)",
               1
            );
            preparedStatementInsertVta.setString(1, entidad.getFecha());
            preparedStatementInsertVta.setInt(2, entidad.getCod_moneda());
            preparedStatementInsertVta.setInt(3, entidad.getCod_deposito());
            preparedStatementInsertVta.setInt(4, entidad.getCod_vendedor());
            preparedStatementInsertVta.setInt(5, entidad.getCod_cliente());
            preparedStatementInsertVta.setString(6, entidad.getNombre_cliente());
            preparedStatementInsertVta.setString(7, entidad.getRuc());
            preparedStatementInsertVta.setString(8, entidad.getDireccion());
            preparedStatementInsertVta.setString(9, entidad.getTelefono());
            preparedStatementInsertVta.setInt(10, entidad.getCod_tipo_documento());
            preparedStatementInsertVta.setInt(11, entidad.getCod_condicion());
            preparedStatementInsertVta.setInt(12, entidad.getDias());
            preparedStatementInsertVta.setInt(13, entidad.getCuotas());
            preparedStatementInsertVta.setDouble(14, entidad.getPorcentaje_descuento());
            preparedStatementInsertVta.setDouble(15, entidad.getImporte_descuento());
            preparedStatementInsertVta.setString(16, entidad.getObservacion());
            preparedStatementInsertVta.setDouble(17, entidad.getImporte_gravado10());
            preparedStatementInsertVta.setDouble(18, entidad.getImporte_gravado5());
            preparedStatementInsertVta.setDouble(19, entidad.getImporte_iva10());
            preparedStatementInsertVta.setDouble(20, entidad.getImporte_iva5());
            preparedStatementInsertVta.setDouble(21, entidad.getImporte_exento());
            preparedStatementInsertVta.setDouble(22, entidad.getImporte_total());
            preparedStatementInsertVta.setInt(23, entidad.getTimbrado());
            preparedStatementInsertVta.setInt(24, entidad.getNro_factura());
            preparedStatementInsertVta.setInt(25, entidad.getId_peticion());
            preparedStatementInsertVta.executeUpdate();
            generatedKeys = preparedStatementInsertVta.getGeneratedKeys();
            if (generatedKeys.next()) {
               lastInsertId = generatedKeys.getInt(1);
            }

            preparedStatementVtaDetalle = conexion.prepareStatement(
               "INSERT INTO ventas_detalle (nro_registro,cod_producto,tipo_fiscal,iva_producto,porcentaje_gravado,regimen,cantidad,precio_bruto,precio_neto,porcentaje_descuento,\r\ntotal,total_iva,total_gravado,total_exento,costo_producto,cod_lista) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
               if (!String.valueOf(tabla.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tabla.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tabla.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tabla.getValueAt(fila, descripcioncolumn) != null) {
                  double cantidad = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."));
                  double precio = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, precioVtaColumn)).replace(".", "").replace(",", "."));
                  if (!ValidacionesTabla.validarCeros(tabla, fila, cantidad, "No se puede grabar registro con cantidad 0! ", frame)) {
                     return 0;
                  }

                  if (!ValidacionesTabla.validarCeros(tabla, fila, precio, "No se puede grabar registro con precio 0! ", frame)) {
                     return 0;
                  }

                  preparedStatementVtaDetalle.setInt(1, lastInsertId);
                  preparedStatementVtaDetalle.setString(2, String.valueOf(tabla.getValueAt(fila, codigoColumn)));
                  preparedStatementVtaDetalle.setInt(3, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn))));
                  preparedStatementVtaDetalle.setDouble(4, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, ivaColumn)).replace(",", ".")));
                  preparedStatementVtaDetalle.setDouble(
                     5, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, porcentajeGravadoColumn)).replace(",", "."))
                  );
                  preparedStatementVtaDetalle.setDouble(6, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, regimenColumn)).replace(",", ".")));
                  preparedStatementVtaDetalle.setDouble(
                     7, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementVtaDetalle.setBigDecimal(
                     8, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioBrutoColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementVtaDetalle.setBigDecimal(
                     9, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioVtaColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementVtaDetalle.setDouble(
                     10, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, descuentoColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementVtaDetalle.setBigDecimal(
                     11, new BigDecimal(String.valueOf(tabla.getValueAt(fila, subTotalColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementVtaDetalle.setBigDecimal(
                     12, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalIvaColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementVtaDetalle.setBigDecimal(
                     13, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalGravadoColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementVtaDetalle.setBigDecimal(
                     14, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalExentoColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementVtaDetalle.setBigDecimal(
                     15, new BigDecimal(String.valueOf(tabla.getValueAt(fila, costoColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementVtaDetalle.setBigDecimal(
                     16, new BigDecimal(String.valueOf(tabla.getValueAt(fila, codListaPrecioColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementVtaDetalle.addBatch();
               }
            }

            preparedStatementVtaDetalle.executeBatch();
            conexion.commit();
            return 1;
         } catch (Exception var35) {
            try {
               conexion.rollback();
            } catch (SQLException var34) {
               LogErrores.errores(var34, "Error al insertar Venta.", frame);
            }

            LogErrores.errores(var35, "Error al insertar Venta..", frame);
            return 0;
         } finally {
            new CerrarRecursos(preparedStatementInsertVta, preparedStatementVtaDetalle, generatedKeys, frame, "Error al insertar Venta..");
            DatabaseConnection.getInstance().cerrarConexion();
         }
      } else {
         return 0;
      }
   }

   public static int actualizarVta(VentasE entidad, JTable tabla, List<Integer> codigosAEliminar, JinternalPadre frame) {
      int codigoColumn = tabla.getColumn("Código").getModelIndex();
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      int tipoFiscalColumn = tabla.getColumn("TF").getModelIndex();
      int ivaColumn = tabla.getColumn("IVA").getModelIndex();
      int porcentajeGravadoColumn = tabla.getColumn("Gravado").getModelIndex();
      int regimenColumn = tabla.getColumn("REG").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int precioBrutoColumn = tabla.getColumn("Precio Bruto").getModelIndex();
      int descuentoColumn = tabla.getColumn("Desc.").getModelIndex();
      int precioNetoColumn = tabla.getColumn("Precio Venta").getModelIndex();
      int totalIvaColumn = tabla.getColumn("Total Iva").getModelIndex();
      int totalGravadoColumn = tabla.getColumn("Total Gravado").getModelIndex();
      int totalExentoColumn = tabla.getColumn("Total Exento").getModelIndex();
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      int primarioColumn = tabla.getColumn("Primario").getModelIndex();
      int costoColumn = tabla.getColumn("Costo").getModelIndex();
      int codListaPrecioColumn = tabla.getColumn("CodLista").getModelIndex();
      if (ValidacionesTabla.validarSinDetalles(tabla, frame)) {
         PreparedStatement preparedStatementDeleteReserva = null;
         PreparedStatement preparedStatementUpdateVta = null;
         PreparedStatement preparedStatementInsertDetalle = null;
         PreparedStatement preparedStatementDeleteDetalle = null;
         PreparedStatement preparedStatementUpdateDetalle = null;
         Connection conexion = null;

         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementUpdateVta = conexion.prepareStatement(
               "update ventas set fecha=?,cod_moneda=?,cod_deposito=?,cod_vendedor=?,\r\ncod_cliente=?,nombre_cliente=?,ruc=?,direccion=?,telefono=?,cod_tipo_documento=?,\r\ncod_condicion=?,dias=?,cuotas=?,porcentaje_descuento=?,importe_descuento=?,observacion=?,\r\nimporte_gravado10=?,importe_gravado5=?,importe_iva10=?,importe_iva5=?,importe_exento=?,importe_total =?,timbrado=?,nro_factura=? where nro_registro =?"
            );
            preparedStatementUpdateVta.setString(1, entidad.getFecha());
            preparedStatementUpdateVta.setInt(2, entidad.getCod_moneda());
            preparedStatementUpdateVta.setInt(3, entidad.getCod_deposito());
            preparedStatementUpdateVta.setInt(4, entidad.getCod_vendedor());
            preparedStatementUpdateVta.setInt(5, entidad.getCod_cliente());
            preparedStatementUpdateVta.setString(6, entidad.getNombre_cliente());
            preparedStatementUpdateVta.setString(7, entidad.getRuc());
            preparedStatementUpdateVta.setString(8, entidad.getDireccion());
            preparedStatementUpdateVta.setString(9, entidad.getTelefono());
            preparedStatementUpdateVta.setInt(10, entidad.getCod_tipo_documento());
            preparedStatementUpdateVta.setInt(11, entidad.getCod_condicion());
            preparedStatementUpdateVta.setInt(12, entidad.getDias());
            preparedStatementUpdateVta.setInt(13, entidad.getCuotas());
            preparedStatementUpdateVta.setDouble(14, entidad.getPorcentaje_descuento());
            preparedStatementUpdateVta.setDouble(15, entidad.getImporte_descuento());
            preparedStatementUpdateVta.setString(16, entidad.getObservacion());
            preparedStatementUpdateVta.setDouble(17, entidad.getImporte_gravado10());
            preparedStatementUpdateVta.setDouble(18, entidad.getImporte_gravado5());
            preparedStatementUpdateVta.setDouble(19, entidad.getImporte_iva10());
            preparedStatementUpdateVta.setDouble(20, entidad.getImporte_iva5());
            preparedStatementUpdateVta.setDouble(21, entidad.getImporte_exento());
            preparedStatementUpdateVta.setDouble(22, entidad.getImporte_total());
            preparedStatementUpdateVta.setInt(23, entidad.getTimbrado());
            preparedStatementUpdateVta.setInt(24, entidad.getNro_factura());
            preparedStatementUpdateVta.setInt(25, entidad.getNro_registro());
            preparedStatementUpdateVta.executeUpdate();
            preparedStatementDeleteDetalle = conexion.prepareStatement("delete from ventas_detalle where id = ?");
            preparedStatementInsertDetalle = conexion.prepareStatement(
               "INSERT INTO ventas_detalle (nro_registro,cod_producto,tipo_fiscal,iva_producto,porcentaje_gravado,regimen,cantidad,precio_bruto,precio_neto,porcentaje_descuento,\r\ntotal,total_iva,total_gravado,total_exento,costo_producto,cod_lista) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );
            preparedStatementUpdateDetalle = conexion.prepareStatement(
               "update ventas_detalle set cod_producto=?,tipo_fiscal=?,iva_producto=?,porcentaje_gravado=?,regimen=?,cantidad=?,precio_bruto=?,precio_neto=?,porcentaje_descuento=?,\r\ntotal=?,total_iva=?,total_gravado=?,total_exento=?,costo_producto=?,cod_lista=? where id=?"
            );

            for (int id : codigosAEliminar) {
               preparedStatementDeleteDetalle.setInt(1, id);
               preparedStatementDeleteDetalle.addBatch();
            }

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
               if (!String.valueOf(tabla.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tabla.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tabla.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tabla.getValueAt(fila, descripcioncolumn) != null) {
                  double cantidad = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."));
                  double precio = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, precioNetoColumn)).replace(".", "").replace(",", "."));
                  if (!ValidacionesTabla.validarCeros(tabla, fila, cantidad, "No se puede grabar registro con cantidad 0! ", frame)) {
                     return 0;
                  }

                  if (!ValidacionesTabla.validarCeros(tabla, fila, precio, "No se puede grabar registro con precio 0! ", frame)) {
                     return 0;
                  }

                  if (String.valueOf(tabla.getValueAt(fila, primarioColumn)).trim().equals("0")) {
                     preparedStatementInsertDetalle.setInt(1, entidad.getNro_registro());
                     preparedStatementInsertDetalle.setString(2, String.valueOf(tabla.getValueAt(fila, codigoColumn)));
                     preparedStatementInsertDetalle.setInt(3, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn))));
                     preparedStatementInsertDetalle.setDouble(4, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, ivaColumn)).replace(",", ".")));
                     preparedStatementInsertDetalle.setDouble(
                        5, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, porcentajeGravadoColumn)).replace(",", "."))
                     );
                     preparedStatementInsertDetalle.setDouble(6, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, regimenColumn)).replace(",", ".")));
                     preparedStatementInsertDetalle.setDouble(
                        7, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsertDetalle.setBigDecimal(
                        8, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioBrutoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsertDetalle.setBigDecimal(
                        9, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioNetoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsertDetalle.setDouble(
                        10, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, descuentoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsertDetalle.setBigDecimal(
                        11, new BigDecimal(String.valueOf(tabla.getValueAt(fila, subTotalColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsertDetalle.setBigDecimal(
                        12, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalIvaColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsertDetalle.setBigDecimal(
                        13, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalGravadoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsertDetalle.setBigDecimal(
                        14, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalExentoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsertDetalle.setBigDecimal(
                        15, new BigDecimal(String.valueOf(tabla.getValueAt(fila, costoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsertDetalle.setBigDecimal(
                        16, new BigDecimal(String.valueOf(tabla.getValueAt(fila, codListaPrecioColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsertDetalle.addBatch();
                  } else {
                     preparedStatementUpdateDetalle.setString(1, String.valueOf(tabla.getValueAt(fila, codigoColumn)));
                     preparedStatementUpdateDetalle.setInt(2, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn))));
                     preparedStatementUpdateDetalle.setDouble(3, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, ivaColumn)).replace(",", ".")));
                     preparedStatementUpdateDetalle.setDouble(
                        4, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, porcentajeGravadoColumn)).replace(",", "."))
                     );
                     preparedStatementUpdateDetalle.setDouble(5, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, regimenColumn)).replace(",", ".")));
                     preparedStatementUpdateDetalle.setDouble(
                        6, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdateDetalle.setBigDecimal(
                        7, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioBrutoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdateDetalle.setBigDecimal(
                        8, new BigDecimal(String.valueOf(tabla.getValueAt(fila, precioNetoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdateDetalle.setDouble(
                        9, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, descuentoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdateDetalle.setBigDecimal(
                        10, new BigDecimal(String.valueOf(tabla.getValueAt(fila, subTotalColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdateDetalle.setBigDecimal(
                        11, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalIvaColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdateDetalle.setBigDecimal(
                        12, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalGravadoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdateDetalle.setBigDecimal(
                        13, new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalExentoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdateDetalle.setBigDecimal(
                        14, new BigDecimal(String.valueOf(tabla.getValueAt(fila, costoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdateDetalle.setBigDecimal(
                        15, new BigDecimal(String.valueOf(tabla.getValueAt(fila, codListaPrecioColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdateDetalle.setInt(16, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, primarioColumn))));
                     preparedStatementUpdateDetalle.addBatch();
                  }
               }
            }

            if (preparedStatementInsertDetalle != null) {
               preparedStatementInsertDetalle.executeBatch();
            }

            if (preparedStatementUpdateDetalle != null) {
               preparedStatementUpdateDetalle.executeBatch();
            }

            if (preparedStatementDeleteDetalle != null) {
               preparedStatementDeleteDetalle.executeBatch();
            }

            preparedStatementDeleteReserva = conexion.prepareStatement("delete from productos_reservados where sesion =?");
            preparedStatementDeleteReserva.setInt(1, entidad.getId_peticion());
            preparedStatementDeleteReserva.executeUpdate();
            conexion.commit();
            return 1;
         } catch (Exception var38) {
            try {
               conexion.rollback();
            } catch (SQLException var37) {
               LogErrores.errores(var37, "Error al actualizar Venta.", frame);
            }

            LogErrores.errores(var38, "Error al actualizar Venta..", frame);
            return 0;
         } finally {
            new CerrarRecursos(
               preparedStatementUpdateVta,
               preparedStatementInsertDetalle,
               preparedStatementUpdateDetalle,
               preparedStatementDeleteDetalle,
               preparedStatementDeleteReserva,
               "Error al actualizar Venta..",
               frame
            );
            DatabaseConnection.getInstance().cerrarConexion();
         }
      } else {
         return 0;
      }
   }

   public static int eliminarVta(VentasE entidad, JinternalPadre frame) {
      Connection conexion = null;
      PreparedStatement preparedStatementEliminarReserva = null;
      PreparedStatement preparedStatementEliminarVta = null;
      PreparedStatement preparedStatementEliminarVtaDetalle = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementEliminarVtaDetalle = conexion.prepareStatement("delete from ventas_detalle where nro_registro =? ");
         preparedStatementEliminarVtaDetalle.setInt(1, entidad.getNro_registro());
         preparedStatementEliminarVtaDetalle.executeUpdate();
         preparedStatementEliminarVta = conexion.prepareStatement("delete from ventas where nro_registro =?");
         preparedStatementEliminarVta.setInt(1, entidad.getNro_registro());
         preparedStatementEliminarVta.executeUpdate();
         preparedStatementEliminarReserva = conexion.prepareStatement("delete from productos_reservados where sesion =? ");
         preparedStatementEliminarReserva.setInt(1, entidad.getId_peticion());
         preparedStatementEliminarReserva.executeUpdate();
         conexion.commit();
         return 1;
      } catch (Exception var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            LogErrores.errores(var12, "Error al eliminar Venta.", frame);
         }

         LogErrores.errores(var13, "Error al eliminar Venta..", frame);
      } finally {
         new CerrarRecursos(preparedStatementEliminarVtaDetalle, preparedStatementEliminarVta, frame, "Error al eliminar Venta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static VentasE buscarVta(int registro, TablaDetalleVentas tabla, JinternalPadre frame) {
      ResultSet rs = null;
      ResultSet rsDetalle = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatementDetalle = null;
      Connection conexion = null;
      DecimalFormat decimalFormat16 = (DecimalFormat)NumberFormat.getNumberInstance();
      decimalFormat16.applyPattern("#,##0.0000000000000000");
      DecimalFormat decimalFormat2 = (DecimalFormat)NumberFormat.getNumberInstance();
      decimalFormat2.applyPattern("#,##0.00");
      DefaultTableModel tableModel = (DefaultTableModel)tabla.getModel();

      for (int rowCount = tableModel.getRowCount(); rowCount > 0; rowCount = tableModel.getRowCount()) {
         tableModel.removeRow(0);
      }

      DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementDetalle = conexion.prepareStatement(
            "select\r\n      ventas_detalle.cod_producto,productos.nombre_producto,ventas_detalle.tipo_fiscal,ventas_detalle.iva_producto,\r\n      ventas_detalle.porcentaje_gravado,ventas_detalle.regimen,unidades_medidas.sigla,ventas_detalle.precio_bruto,\r\n      ventas_detalle.precio_bruto,0 as precio_minimo,ventas_detalle.costo_producto,ventas_detalle.precio_neto,\r\n      ventas_detalle.cantidad,ventas_detalle.porcentaje_descuento,ventas_detalle.total_iva,\r\n      ventas_detalle.total_gravado,ventas_detalle.total_exento,\r\n      ventas_detalle.total,ventas_detalle.id,ventas_detalle.id,ventas_detalle.cod_lista\r\nfrom ventas_detalle,productos,unidades_medidas\r\nwhere\r\nventas_detalle.cod_producto = productos.cod_producto\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida and ventas_detalle.nro_registro =?"
         );
         preparedStatementDetalle.setInt(1, registro);
         rsDetalle = preparedStatementDetalle.executeQuery();

         while (rsDetalle.next()) {
            modelo.addRow(
               new Object[]{
                  rsDetalle.getString("cod_producto"),
                  rsDetalle.getString("nombre_producto"),
                  rsDetalle.getInt("tipo_fiscal"),
                  decimalFormat2.format(rsDetalle.getDouble("iva_producto")),
                  decimalFormat2.format(rsDetalle.getDouble("porcentaje_gravado")),
                  decimalFormat2.format(rsDetalle.getDouble("regimen")),
                  rsDetalle.getString("sigla"),
                  decimalFormat16.format(rsDetalle.getDouble("precio_bruto")),
                  decimalFormat16.format(rsDetalle.getDouble("precio_bruto")),
                  decimalFormat2.format(rsDetalle.getDouble("precio_minimo")),
                  decimalFormat2.format(rsDetalle.getDouble("costo_producto")),
                  decimalFormat2.format(rsDetalle.getDouble("cantidad")),
                  decimalFormat16.format(rsDetalle.getDouble("precio_neto")),
                  decimalFormat2.format(rsDetalle.getDouble("porcentaje_descuento")),
                  decimalFormat16.format(rsDetalle.getDouble("total_iva")),
                  decimalFormat16.format(rsDetalle.getDouble("total_gravado")),
                  decimalFormat16.format(rsDetalle.getDouble("total_exento")),
                  decimalFormat16.format(rsDetalle.getDouble("total")),
                  rsDetalle.getInt("id"),
                  rsDetalle.getInt("id"),
                  rsDetalle.getInt("cod_lista")
               }
            );
         }

         preparedStatement = conexion.prepareStatement(
            "select\r\nventas.nro_registro,ventas.fecha,ventas.cod_moneda,\r\nventas.cod_deposito,ventas.cod_vendedor,\r\nventas.cod_cliente,ventas.nombre_cliente,\r\nventas.ruc,ventas.direccion,ventas.telefono,\r\nventas.cod_tipo_documento,ventas.cod_condicion,ventas.dias,ventas.cuotas,\r\nventas.porcentaje_descuento,ventas.importe_descuento,ventas.observacion,\r\nventas.importe_gravado10,ventas.importe_gravado5,ventas.importe_iva10,ventas.importe_iva5,\r\nventas.importe_exento,ventas.importe_total,monedas.nombre_moneda,depositos.nombre_deposito,\r\nvendedores.nombre_vendedor,tipo_documentos.nombre_tipo_documento,condiciones_ventas.nombre_condicion,clientes.tipo_fiscal as tipo_fiscal_cliente,ventas.timbrado, ventas.nro_factura,tipo_documentos.numeracion\r\n from ventas,monedas,depositos,vendedores,tipo_documentos,condiciones_ventas,clientes\r\nwhere\r\nventas.cod_moneda =monedas.cod_moneda and clientes.cod_cliente = ventas.cod_cliente\r\nand ventas.cod_deposito = depositos.cod_deposito\r\nand ventas.cod_vendedor = vendedores.cod_vendedor\r\nand ventas.cod_tipo_documento = tipo_documentos.cod_tipo_documento\r\nand ventas.cod_condicion = condiciones_ventas.cod_condicion and ventas.nro_registro =?"
         );
         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (rs.next()) {
            return new VentasE(
               registro,
               rs.getString("fecha"),
               rs.getInt("cod_moneda"),
               rs.getInt("cod_deposito"),
               rs.getInt("cod_vendedor"),
               rs.getInt("cod_cliente"),
               rs.getString("nombre_cliente"),
               rs.getString("ruc"),
               rs.getString("direccion"),
               rs.getString("telefono"),
               rs.getInt("cod_tipo_documento"),
               rs.getInt("cod_condicion"),
               rs.getInt("dias"),
               rs.getInt("cuotas"),
               rs.getDouble("porcentaje_descuento"),
               rs.getDouble("importe_descuento"),
               rs.getString("observacion"),
               rs.getDouble("importe_gravado10"),
               rs.getDouble("importe_gravado5"),
               rs.getDouble("importe_iva10"),
               rs.getDouble("importe_iva5"),
               rs.getDouble("importe_exento"),
               rs.getDouble("importe_total"),
               rs.getString("nombre_moneda"),
               rs.getString("nombre_deposito"),
               rs.getString("nombre_vendedor"),
               rs.getString("nombre_tipo_documento"),
               rs.getString("nombre_condicion"),
               CotizacionesE.cambiarImporte(rs.getInt("cod_moneda"), 1, rs.getDouble("importe_total"), null).getCotizacion(),
               CotizacionesE.cambiarImporte(rs.getInt("cod_moneda"), 2, rs.getDouble("importe_total"), null).getCotizacion(),
               CotizacionesE.cambiarImporte(rs.getInt("cod_moneda"), 3, rs.getDouble("importe_total"), null).getCotizacion(),
               CotizacionesE.cambiarImporte(rs.getInt("cod_moneda"), 4, rs.getDouble("importe_total"), null).getCotizacion(),
               rs.getInt("tipo_fiscal_cliente"),
               rs.getInt("timbrado"),
               rs.getInt("nro_factura"),
               rs.getInt("numeracion")
            );
         }
      } catch (Exception var19) {
         LogErrores.errores(var19, "Error al seleccionar Venta..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatementDetalle, rs, rsDetalle, frame, "Error al seleccionar Venta..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static int buscarNroFactura(int numeracion, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      int var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select ifnull(max(nro_factura),0)+1 as mayor from ventas,tipo_documentos\r\nwhere ventas.cod_tipo_documento = tipo_documentos.cod_tipo_documento\r\nand tipo_documentos.numeracion=?"
         );
         preparedStatementSelect.setInt(1, numeracion);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Error al obtener Numero de Factura", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return 0;
         }

         var8 = resultado.getInt("mayor");
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Numero de Factura..", frame);
         return 0;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Numero de Factura....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public VentasE(int nro_factura) {
      this.nro_factura = nro_factura;
   }

   public VentasE(
      int nro_registro,
      String fecha,
      int cod_moneda,
      int cod_deposito,
      int cod_vendedor,
      int cod_cliente,
      String nombre_cliente,
      String ruc,
      String direccion,
      String telefono,
      int cod_tipo_documento,
      int cod_condicion,
      int dias,
      int cuotas,
      double porcentaje_descuento,
      double importe_descuento,
      String observacion,
      double importe_gravado10,
      double importe_gravado5,
      double importe_iva10,
      double importe_iva5,
      double importe_exento,
      double importe_total,
      int timbrado,
      int numeracion,
      int nro_factura,
      int id_peticion
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_moneda = cod_moneda;
      this.cod_deposito = cod_deposito;
      this.cod_vendedor = cod_vendedor;
      this.cod_cliente = cod_cliente;
      this.nombre_cliente = nombre_cliente;
      this.ruc = ruc;
      this.direccion = direccion;
      this.telefono = telefono;
      this.cod_tipo_documento = cod_tipo_documento;
      this.cod_condicion = cod_condicion;
      this.dias = dias;
      this.cuotas = cuotas;
      this.porcentaje_descuento = porcentaje_descuento;
      this.importe_descuento = importe_descuento;
      this.observacion = observacion;
      this.importe_gravado10 = importe_gravado10;
      this.importe_gravado5 = importe_gravado5;
      this.importe_iva10 = importe_iva10;
      this.importe_iva5 = importe_iva5;
      this.importe_exento = importe_exento;
      this.importe_total = importe_total;
      this.timbrado = timbrado;
      this.numeracion = numeracion;
      this.nro_factura = nro_factura;
      this.id_peticion = id_peticion;
   }

   public VentasE(
      int nro_registro,
      String fecha,
      int cod_moneda,
      int cod_deposito,
      int cod_vendedor,
      int cod_cliente,
      String nombre_cliente,
      String ruc,
      String direccion,
      String telefono,
      int cod_tipo_documento,
      int cod_condicion,
      int dias,
      int cuotas,
      double porcentaje_descuento,
      double importe_descuento,
      String observacion,
      double importe_gravado10,
      double importe_gravado5,
      double importe_iva10,
      double importe_iva5,
      double importe_exento,
      String usuario_alta,
      String fecha_alta,
      String nombre_moneda,
      String nombre_deposito,
      String nombre_vendedor,
      String nombre_tipo_documento,
      String nombre_condicion,
      int tipo_fiscal_cliente
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_moneda = cod_moneda;
      this.cod_deposito = cod_deposito;
      this.cod_vendedor = cod_vendedor;
      this.cod_cliente = cod_cliente;
      this.nombre_cliente = nombre_cliente;
      this.ruc = ruc;
      this.direccion = direccion;
      this.telefono = telefono;
      this.cod_tipo_documento = cod_tipo_documento;
      this.cod_condicion = cod_condicion;
      this.dias = dias;
      this.cuotas = cuotas;
      this.porcentaje_descuento = porcentaje_descuento;
      this.importe_descuento = importe_descuento;
      this.observacion = observacion;
      this.importe_gravado10 = importe_gravado10;
      this.importe_gravado5 = importe_gravado5;
      this.importe_iva10 = importe_iva10;
      this.importe_iva5 = importe_iva5;
      this.importe_exento = importe_exento;
      this.usuario_alta = usuario_alta;
      this.fecha_alta = fecha_alta;
      this.nombre_moneda = nombre_moneda;
      this.nombre_deposito = nombre_deposito;
      this.nombre_vendedor = nombre_vendedor;
      this.nombre_tipo_documento = nombre_tipo_documento;
      this.nombre_condicion = nombre_condicion;
      this.tipo_fiscal_cliente = tipo_fiscal_cliente;
   }

   public VentasE(
      int nro_registro,
      String fecha,
      int cod_moneda,
      int cod_deposito,
      int cod_vendedor,
      int cod_cliente,
      String nombre_cliente,
      String ruc,
      String direccion,
      String telefono,
      int cod_tipo_documento,
      int cod_condicion,
      int dias,
      int cuotas,
      double porcentaje_descuento,
      double importe_descuento,
      String observacion,
      double importe_gravado10,
      double importe_gravado5,
      double importe_iva10,
      double importe_iva5,
      double importe_exento,
      double importe_total,
      String nombre_moneda,
      String nombre_deposito,
      String nombre_vendedor,
      String nombre_tipo_documento,
      String nombre_condicion,
      double valor_moneda1,
      double valor_moneda2,
      double valor_moneda3,
      double valor_moneda4,
      int tipo_fiscal_cliente,
      int timbrado,
      int nro_factura,
      int numeracion
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_moneda = cod_moneda;
      this.cod_deposito = cod_deposito;
      this.cod_vendedor = cod_vendedor;
      this.cod_cliente = cod_cliente;
      this.nombre_cliente = nombre_cliente;
      this.ruc = ruc;
      this.direccion = direccion;
      this.telefono = telefono;
      this.cod_tipo_documento = cod_tipo_documento;
      this.cod_condicion = cod_condicion;
      this.dias = dias;
      this.cuotas = cuotas;
      this.porcentaje_descuento = porcentaje_descuento;
      this.importe_descuento = importe_descuento;
      this.observacion = observacion;
      this.importe_gravado10 = importe_gravado10;
      this.importe_gravado5 = importe_gravado5;
      this.importe_iva10 = importe_iva10;
      this.importe_iva5 = importe_iva5;
      this.importe_exento = importe_exento;
      this.importe_total = importe_total;
      this.nombre_moneda = nombre_moneda;
      this.nombre_deposito = nombre_deposito;
      this.nombre_vendedor = nombre_vendedor;
      this.nombre_tipo_documento = nombre_tipo_documento;
      this.nombre_condicion = nombre_condicion;
      this.valor_moneda1 = valor_moneda1;
      this.valor_moneda2 = valor_moneda2;
      this.valor_moneda3 = valor_moneda3;
      this.valor_moneda4 = valor_moneda4;
      this.tipo_fiscal_cliente = tipo_fiscal_cliente;
      this.timbrado = timbrado;
      this.nro_factura = nro_factura;
      this.numeracion = numeracion;
   }

   public int getNro_registro() {
      return this.nro_registro;
   }

   public void setNro_registro(int nro_registro) {
      this.nro_registro = nro_registro;
   }

   public int getCod_cliente() {
      return this.cod_cliente;
   }

   public void setCod_cliente(int cod_cliente) {
      this.cod_cliente = cod_cliente;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public void setCod_moneda(int cod_moneda) {
      this.cod_moneda = cod_moneda;
   }

   public int getCod_deposito() {
      return this.cod_deposito;
   }

   public void setCod_deposito(int cod_deposito) {
      this.cod_deposito = cod_deposito;
   }

   public int getCod_vendedor() {
      return this.cod_vendedor;
   }

   public void setCod_vendedor(int cod_vendedor) {
      this.cod_vendedor = cod_vendedor;
   }

   public int getCod_tipo_documento() {
      return this.cod_tipo_documento;
   }

   public void setCod_tipo_documento(int cod_tipo_documento) {
      this.cod_tipo_documento = cod_tipo_documento;
   }

   public int getDias() {
      return this.dias;
   }

   public void setDias(int dias) {
      this.dias = dias;
   }

   public int getCuotas() {
      return this.cuotas;
   }

   public void setCuotas(int cuotas) {
      this.cuotas = cuotas;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
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

   public String getNombre_moneda() {
      return this.nombre_moneda;
   }

   public void setNombre_moneda(String nombre_moneda) {
      this.nombre_moneda = nombre_moneda;
   }

   public String getNombre_deposito() {
      return this.nombre_deposito;
   }

   public void setNombre_deposito(String nombre_deposito) {
      this.nombre_deposito = nombre_deposito;
   }

   public String getNombre_vendedor() {
      return this.nombre_vendedor;
   }

   public void setNombre_vendedor(String nombre_vendedor) {
      this.nombre_vendedor = nombre_vendedor;
   }

   public String getNombre_tipo_documento() {
      return this.nombre_tipo_documento;
   }

   public void setNombre_tipo_documento(String nombre_tipo_documento) {
      this.nombre_tipo_documento = nombre_tipo_documento;
   }

   public double getPorcentaje_descuento() {
      return this.porcentaje_descuento;
   }

   public void setPorcentaje_descuento(double porcentaje_descuento) {
      this.porcentaje_descuento = porcentaje_descuento;
   }

   public double getImporte_descuento() {
      return this.importe_descuento;
   }

   public void setImporte_descuento(double importe_descuento) {
      this.importe_descuento = importe_descuento;
   }

   public double getImporte_exento() {
      return this.importe_exento;
   }

   public void setImporte_exento(double importe_exento) {
      this.importe_exento = importe_exento;
   }

   public int getCod_condicion() {
      return this.cod_condicion;
   }

   public void setCod_condicion(int cod_condicion) {
      this.cod_condicion = cod_condicion;
   }

   public String getNombre_condicion() {
      return this.nombre_condicion;
   }

   public void setNombre_condicion(String nombre_condicion) {
      this.nombre_condicion = nombre_condicion;
   }

   public double getImporte_gravado10() {
      return this.importe_gravado10;
   }

   public void setImporte_gravado10(double importe_gravado10) {
      this.importe_gravado10 = importe_gravado10;
   }

   public double getImporte_gravado5() {
      return this.importe_gravado5;
   }

   public void setImporte_gravado5(double importe_gravado5) {
      this.importe_gravado5 = importe_gravado5;
   }

   public double getImporte_iva5() {
      return this.importe_iva5;
   }

   public void setImporte_iva5(double importe_iva5) {
      this.importe_iva5 = importe_iva5;
   }

   public double getImporte_iva10() {
      return this.importe_iva10;
   }

   public void setImporte_iva10(double importe_iva10) {
      this.importe_iva10 = importe_iva10;
   }

   public double getValor_moneda1() {
      return this.valor_moneda1;
   }

   public void setValor_moneda1(double valor_moneda1) {
      this.valor_moneda1 = valor_moneda1;
   }

   public double getValor_moneda2() {
      return this.valor_moneda2;
   }

   public void setValor_moneda2(double valor_moneda2) {
      this.valor_moneda2 = valor_moneda2;
   }

   public double getValor_moneda3() {
      return this.valor_moneda3;
   }

   public void setValor_moneda3(double valor_moneda3) {
      this.valor_moneda3 = valor_moneda3;
   }

   public double getValor_moneda4() {
      return this.valor_moneda4;
   }

   public void setValor_moneda4(double valor_moneda4) {
      this.valor_moneda4 = valor_moneda4;
   }

   public double getImporte_total() {
      return this.importe_total;
   }

   public void setImporte_total(double importe_total) {
      this.importe_total = importe_total;
   }

   public int getTipo_fiscal_cliente() {
      return this.tipo_fiscal_cliente;
   }

   public void setTipo_fiscal_cliente(int tipo_fiscal_cliente) {
      this.tipo_fiscal_cliente = tipo_fiscal_cliente;
   }

   public int getTimbrado() {
      return this.timbrado;
   }

   public void setTimbrado(int timbrado) {
      this.timbrado = timbrado;
   }

   public int getNumeracion() {
      return this.numeracion;
   }

   public void setNumeracion(int numeracion) {
      this.numeracion = numeracion;
   }

   public int getNro_factura() {
      return this.nro_factura;
   }

   public void setNro_factura(int nro_factura) {
      this.nro_factura = nro_factura;
   }

   public int getId_peticion() {
      return this.id_peticion;
   }

   public void setId_peticion(int id_peticion) {
      this.id_peticion = id_peticion;
   }
}
