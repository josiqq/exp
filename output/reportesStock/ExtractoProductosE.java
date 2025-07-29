package reportesStock;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import utilidades.LabelPrincipal;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadreReporte;

public class ExtractoProductosE {
   public static void cargarProductos(
      String cod_producto,
      int cod_deposito,
      String fecha_un_dia_antes,
      String fecha_ini,
      String fecha_fin,
      LabelPrincipal lblStockAnterior,
      LabelPrincipal lblSaldoFinal,
      JTable tabla,
      JinternalPadreReporte frame
   ) {
      ResultSet resultado = null;
      ResultSet resultado2 = null;
      PreparedStatement preparedStatementSelectStockAnterior = null;
      PreparedStatement preparedStatementSelectMovimientos = null;
      NumberFormat nf = NumberFormat.getNumberInstance();
      ModeloTablaDefecto modelo = (ModeloTablaDefecto)tabla.getModel();
      Double v_stock_anterior = 0.0;
      Double v_entrada = 0.0;
      Double v_salida = 0.0;

      try {
         if (modelo.getRowCount() - 1 >= 0) {
            for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
               modelo.removeRow(i);
            }
         }

         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         FormatoDecimalOperacionSistema formatoDecimalImporte = new FormatoDecimalOperacionSistema(2);
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelectStockAnterior = conexion.prepareStatement("select f_stock_fecha(?, ?,?) as anterior");
         preparedStatementSelectStockAnterior.setString(1, cod_producto);
         preparedStatementSelectStockAnterior.setInt(2, cod_deposito);
         preparedStatementSelectStockAnterior.setString(3, fecha_un_dia_antes);
         resultado = preparedStatementSelectStockAnterior.executeQuery();
         if (resultado.next()) {
            BigDecimal stockBD = resultado.getBigDecimal("anterior");
            if (stockBD != null) {
               v_stock_anterior = stockBD.doubleValue();
               lblStockAnterior.setText(nf.format(v_stock_anterior));
               System.out.println("Stock correcto: " + v_stock_anterior);
            }
         }

         preparedStatementSelectMovimientos = conexion.prepareStatement(
            "select\r\n      envios.fecha as fecha,\r\n      envios.hora_envio as hora,\r\n      envios.nro_registro,\r\n      'Envios de Mercaderias' as descripcion,\r\n      envios_detalle.cantidad_recibido as entrada,\r\n      0 as salida\r\nfrom\r\n  envios,envios_detalle\r\nwhere\r\n  (envios.nro_registro = envios_detalle.nro_registro)\r\n  and ((envios.cod_deposito_destino =?) or (0=?))\r\n  and (envios_detalle.cod_producto = ?)\r\n  and (envios.fecha >= ? and envios.fecha <= ?) union all\r\nselect\r\nenvios.fecha as fecha,\r\nenvios.hora_envio as hora,\r\nenvios.nro_registro,\r\n'Envios de Mercaderias' as descripcion,\r\n0 as entrada,\r\nenvios_detalle.cantidad_recibido as salida\r\nfrom envios,envios_detalle\r\nwhere\r\n(envios.nro_registro = envios_detalle.nro_registro)\r\nand ((envios.cod_deposito_origen =?) or (?=0))\r\nand (envios_detalle.cod_producto = ?)\r\nand (envios.fecha >= ? and envios.fecha <= ?) union all\r\nselect\r\najuste_stock.fecha as fecha,\r\najuste_stock.hora_ajuste as hora,\r\najuste_stock.nro_registro,\r\n'Ajuste de Stock' as descripcion,\r\ncase\r\n\t\t\twhen ajuste_stock_detalle.Cantidad > 0 then\r\n\t\t\t\t  ajuste_stock_detalle.Cantidad\r\n\t\t\telse\r\n\t\t\t     0\r\n\t\t\tend as entrada,\r\ncase\r\n\t\t\twhen ajuste_stock_detalle.Cantidad < 0 then\r\n\t\t\t\t  ajuste_stock_detalle.Cantidad * -1\r\n\t\t\telse\r\n\t\t\t     0\r\n\t\t\tend as salida\r\n from ajuste_stock, ajuste_stock_detalle\r\n where\r\n (ajuste_stock.nro_registro = ajuste_stock.nro_registro)\r\nand ((ajuste_stock.cod_deposito =?) or (?=0))\r\nand (ajuste_stock_detalle.cod_producto =?)\r\nand (ajuste_stock.fecha >= ? and ajuste_stock.fecha <=?) union all\r\nselect\r\ncompras.fecha_factura as fecha,\r\ncompras.hora_compra as hora,\r\ncompras.nro_registro,\r\n'Compras de Mercaderias' as descripcion,\r\n(compras_detalle.cantidad+compras_detalle.cantidad_bonif) as entrada,\r\n0 as salida\r\n from compras,compras_detalle\r\nwhere\r\n(compras.nro_registro = compras_detalle.nro_registro)\r\nand ((compras.cod_deposito =?) or (?=0))\r\nand (compras_detalle.cod_producto =?)\r\nand (compras.fecha_factura >=? and compras.fecha_factura <=?) union all\r\nselect\r\nventas.fecha as fecha,\r\nventas.hora_venta as hora,\r\nventas.nro_registro,\r\n'Ventas de Mercaderia' as descripcion,\r\n0 as entrada,\r\nventas_detalle.cantidad as salida\r\nfrom ventas, ventas_detalle\r\nwhere\r\n(ventas.nro_registro = ventas_detalle.nro_registro)\r\nand ((ventas.cod_deposito =?) or (?=0))\r\nand (ventas_detalle.cod_producto =?)\r\nand (ventas.fecha >= ? and ventas.fecha <= ?)\r\norder by fecha, hora"
         );
         preparedStatementSelectMovimientos.setInt(1, cod_deposito);
         preparedStatementSelectMovimientos.setInt(2, cod_deposito);
         preparedStatementSelectMovimientos.setString(3, cod_producto);
         preparedStatementSelectMovimientos.setString(4, fecha_ini);
         preparedStatementSelectMovimientos.setString(5, fecha_fin);
         preparedStatementSelectMovimientos.setInt(6, cod_deposito);
         preparedStatementSelectMovimientos.setInt(7, cod_deposito);
         preparedStatementSelectMovimientos.setString(8, cod_producto);
         preparedStatementSelectMovimientos.setString(9, fecha_ini);
         preparedStatementSelectMovimientos.setString(10, fecha_fin);
         preparedStatementSelectMovimientos.setInt(11, cod_deposito);
         preparedStatementSelectMovimientos.setInt(12, cod_deposito);
         preparedStatementSelectMovimientos.setString(13, cod_producto);
         preparedStatementSelectMovimientos.setString(14, fecha_ini);
         preparedStatementSelectMovimientos.setString(15, fecha_fin);
         preparedStatementSelectMovimientos.setInt(16, cod_deposito);
         preparedStatementSelectMovimientos.setInt(17, cod_deposito);
         preparedStatementSelectMovimientos.setString(18, cod_producto);
         preparedStatementSelectMovimientos.setString(19, fecha_ini);
         preparedStatementSelectMovimientos.setString(20, fecha_fin);
         preparedStatementSelectMovimientos.setInt(21, cod_deposito);
         preparedStatementSelectMovimientos.setInt(22, cod_deposito);
         preparedStatementSelectMovimientos.setString(23, cod_producto);
         preparedStatementSelectMovimientos.setString(24, fecha_ini);
         preparedStatementSelectMovimientos.setString(25, fecha_fin);
         resultado2 = preparedStatementSelectMovimientos.executeQuery();
         if (!resultado2.isBeforeFirst()) {
            JOptionPane.showMessageDialog(null, "No se encontraron registros.");
            lblStockAnterior.setText(nf.format(0L));
            lblSaldoFinal.setText(nf.format(0L));
         } else {
            int fila = 0;

            while (resultado2.next()) {
               modelo.addRow(new Object[tabla.getColumnCount()]);
               fila = tabla.getRowCount() - 1;
               v_entrada = resultado2.getDouble("entrada");
               v_salida = resultado2.getDouble("salida");
               Double var30 = v_stock_anterior + v_entrada;
               v_stock_anterior = var30 - v_salida;
               tabla.setValueAt(dateFormat.format(resultado2.getDate("fecha")), fila, tabla.getColumn("Fecha").getModelIndex());
               tabla.setValueAt(resultado2.getString("hora"), fila, tabla.getColumn("Hora").getModelIndex());
               tabla.setValueAt(resultado2.getString("nro_registro"), fila, tabla.getColumn("Nro. Registro").getModelIndex());
               tabla.setValueAt(resultado2.getString("descripcion"), fila, tabla.getColumn("Operacion").getModelIndex());
               tabla.setValueAt(formatoDecimalImporte.format(v_entrada), fila, tabla.getColumn("Entrada").getModelIndex());
               tabla.setValueAt(formatoDecimalImporte.format(v_salida), fila, tabla.getColumn("Salida").getModelIndex());
               tabla.setValueAt(formatoDecimalImporte.format(v_stock_anterior), fila, tabla.getColumn("Saldo").getModelIndex());
            }

            lblSaldoFinal.setText(nf.format(v_stock_anterior));
         }
      } catch (Exception var26) {
         LogErrores.errores(var26, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelectStockAnterior, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }
}
