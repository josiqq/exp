package a0099ConsultasRapidas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadreReporte;

public class BloqueoReservasE {
   public static void buscarProductosReservadoTabla(String codigo, JTable tabla, JinternalPadreReporte frame) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      ModeloTablaDefecto modelo = (ModeloTablaDefecto)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select \r\n\t\tproductos_reservados.cod_producto,\r\n\t\tproductos.nombre_producto,\r\n\t\tproductos_reservados.cod_deposito,\r\n\t\tdepositos.nombre_deposito,\r\n\t\tproductos_reservados.cantidad,\r\n\t\tproductos_reservados.tipo,\r\n\t\tproductos_reservados.sesion\r\nfrom\r\n\tproductos_reservados,productos,depositos\r\nwhere\r\n(productos_reservados.cod_producto = productos.cod_producto)\r\nand (productos_reservados.cod_deposito = depositos.cod_deposito)\r\nand ((productos_reservados.cod_producto =?) or (0=?))"
         );
         preparedStatement2.setString(1, codigo);
         preparedStatement2.setString(2, codigo);
         rs2 = preparedStatement2.executeQuery();
         int fila = 0;
         if (tabla.getRowCount() - 1 == 0) {
            modelo.removeRow(0);
         }

         while (rs2.next()) {
            modelo.addRow(new Object[tabla.getColumnCount()]);
            fila = tabla.getRowCount() - 1;
            tabla.setValueAt(rs2.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
            tabla.setValueAt(rs2.getString("nombre_producto"), fila, tabla.getColumn("Descripcion Producto").getModelIndex());
            tabla.setValueAt(rs2.getInt("cod_deposito"), fila, tabla.getColumn("CodDeposito").getModelIndex());
            tabla.setValueAt(rs2.getString("nombre_deposito"), fila, tabla.getColumn("Nombre Deposito").getModelIndex());
            tabla.setValueAt(rs2.getDouble("cantidad"), fila, tabla.getColumn("Cantidad").getModelIndex());
            tabla.setValueAt(rs2.getString("tipo"), fila, tabla.getColumn("Tipo").getModelIndex());
            tabla.setValueAt(rs2.getString("sesion"), fila, tabla.getColumn("Sesion").getModelIndex());
            tabla.setValueAt(false, fila, tabla.getColumn("Anular").getModelIndex());
         }
      } catch (Exception var12) {
         LogErrores.errores(var12, "Error al Cargar Producto..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al Cargar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static int actualizaProductoReservados(JinternalPadreReporte frame, JTable tablaInsertar) {
      int codigoColumn = tablaInsertar.getColumn("Codigo").getModelIndex();
      int anularColumn = tablaInsertar.getColumn("Anular").getModelIndex();
      int tipocolumn = tablaInsertar.getColumn("Tipo").getModelIndex();
      int sesioncolumn = tablaInsertar.getColumn("Sesion").getModelIndex();
      PreparedStatement preparedStatementDelete = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementDelete = conexion.prepareStatement("delete from productos_reservados where sesion=? and tipo =?");

         for (int fila = 0; fila < tablaInsertar.getRowCount(); fila++) {
            if (!String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)).trim().equals("")
               && tablaInsertar.getValueAt(fila, codigoColumn) != null
               && (Boolean)tablaInsertar.getValueAt(fila, anularColumn)) {
               preparedStatementDelete.setString(1, String.valueOf(tablaInsertar.getValueAt(fila, sesioncolumn)));
               preparedStatementDelete.setString(2, String.valueOf(tablaInsertar.getValueAt(fila, tipocolumn)));
               preparedStatementDelete.addBatch();
            }
         }

         if (preparedStatementDelete != null) {
            preparedStatementDelete.executeBatch();
         }

         conexion.commit();
         return 1;
      } catch (Exception var15) {
         try {
            conexion.rollback();
         } catch (SQLException var14) {
            LogErrores.errores(var14, "Error al actualizar Productos Reservados.", frame);
         }

         LogErrores.errores(var15, "Error al actualizar Productos Reservados..", frame);
      } finally {
         new CerrarRecursos(preparedStatementDelete, frame, "Error al actualizar Productos Reservados..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }
}
