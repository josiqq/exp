package utilidadesRecalculos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import utilidades.LabelPrincipal;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;

public class RecalculoStockE {
   public static int actualizarStock(JTable tabla, JProgressBar progressBar, LabelPrincipal lblLineaProcesadas, JinternalPadre frame) {
      NumberFormat nf = NumberFormat.getNumberInstance();
      progressBar.setMinimum(0);
      progressBar.setValue(0);
      progressBar.setMaximum(tabla.getRowCount());
      progressBar.setStringPainted(true);
      lblLineaProcesadas.setText("0");
      int codigoProductoColumn = tabla.getColumn("Codigo").getModelIndex();
      int codigoDepositoColumn = tabla.getColumn("CodDeposito").getModelIndex();
      PreparedStatement preparedStatementinsert = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementinsert = conexion.prepareStatement(
            "update stock_depositos set cantidad =`f_stock_reprocesar`(cod_producto, cod_deposito)\r\nwhere stock_depositos.cod_deposito = ? and stock_depositos.cod_producto=?"
         );

         for (int fila = 0; fila < tabla.getRowCount(); fila++) {
            preparedStatementinsert.setInt(1, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, codigoDepositoColumn))));
            preparedStatementinsert.setString(2, String.valueOf(tabla.getValueAt(fila, codigoProductoColumn)));
            preparedStatementinsert.addBatch();
            progressBar.setValue(fila + 1);
            lblLineaProcesadas.setText(String.valueOf(nf.format((long)(fila + 1))));
         }

         preparedStatementinsert.executeBatch();
         conexion.commit();
         progressBar.setValue(tabla.getRowCount());
         return 1;
      } catch (Exception var16) {
         try {
            conexion.rollback();
         } catch (SQLException var15) {
            LogErrores.errores(var15, "Error al insertar Codigo de Barra.", frame);
         }

         LogErrores.errores(var16, "Error al insertar Codigo de Barra..", frame);
      } finally {
         new CerrarRecursos(preparedStatementinsert, frame, "Error al insertar Codigo de Barra..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarProductos(String cod_producto, int cod_deposito, ModeloTablaDefecto modelo, LabelPrincipal lineasRecuperadas, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;
      NumberFormat nf = NumberFormat.getNumberInstance();
      int v_cantidad_registros = 0;

      try {
         if (modelo.getRowCount() - 1 >= 0) {
            for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
               modelo.removeRow(i);
            }
         }

         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\nproductos.cod_producto,\r\nproductos.nombre_producto,\r\nstock_depositos.cod_deposito,\r\ndepositos.nombre_deposito,\r\nstock_depositos.cantidad\r\nfrom productos,stock_depositos,depositos\r\nwhere (productos.estado =1)\r\nand (productos.cod_producto = stock_depositos.cod_producto)\r\nand (stock_depositos.cod_deposito = depositos.cod_deposito)\r\nand (depositos.estado =1)\r\nand ((productos.cod_producto = ?) or (? = '0'))\r\nand ((stock_depositos.cod_deposito = ?) or (?=0))\r\norder by productos.nombre_producto,depositos.cod_deposito"
         );
         preparedStatementSelect.setString(1, cod_producto);
         preparedStatementSelect.setString(2, cod_producto);
         preparedStatementSelect.setInt(3, cod_deposito);
         preparedStatementSelect.setInt(4, cod_deposito);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            v_cantidad_registros++;
            modelo.addRow(
               new Object[]{
                  resultado.getString(1),
                  resultado.getString(2),
                  resultado.getInt(3),
                  resultado.getString(3) + " - " + resultado.getString(4),
                  resultado.getDouble(5)
               }
            );
         }

         lineasRecuperadas.setText(String.valueOf(nf.format((long)v_cantidad_registros)));
      } catch (Exception var14) {
         LogErrores.errores(var14, "Error al seleccionar Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }
}
