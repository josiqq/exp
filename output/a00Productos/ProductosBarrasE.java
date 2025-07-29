package a00Productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadreString;

public class ProductosBarrasE {
   private String cod_producto;
   private String codigo_barra;

   public static int insertarCodigoBarras(String cod_producto, JTable tabla, List<String> codigosAEliminar, JinternalPadreString frame) {
      int codigoBarraColumn = tabla.getColumn("Codigo de Barras").getModelIndex();
      int SWcolumn = tabla.getColumn("ID").getModelIndex();
      PreparedStatement preparedStatementinsert = null;
      PreparedStatement preparedStatementDelete = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementinsert = conexion.prepareStatement("INSERT INTO productos_codigo_barras (cod_producto,codigo_barra) VALUES (?,?)");
         preparedStatementDelete = conexion.prepareStatement("delete from productos_codigo_barras where codigo_barra =?");

         for (String id : codigosAEliminar) {
            preparedStatementDelete.setString(1, id);
            preparedStatementDelete.addBatch();
         }

         for (int fila = 0; fila < tabla.getRowCount(); fila++) {
            if (!String.valueOf(tabla.getValueAt(fila, codigoBarraColumn)).trim().equals("")
               && tabla.getValueAt(fila, codigoBarraColumn) != null
               && String.valueOf(tabla.getValueAt(fila, SWcolumn)).trim().equals("0")) {
               preparedStatementinsert.setString(1, cod_producto);
               preparedStatementinsert.setString(2, String.valueOf(tabla.getValueAt(fila, codigoBarraColumn)));
               preparedStatementinsert.addBatch();
            }
         }

         preparedStatementDelete.executeBatch();
         preparedStatementinsert.executeBatch();
         conexion.commit();
         return 1;
      } catch (Exception var17) {
         try {
            conexion.rollback();
         } catch (SQLException var16) {
            LogErrores.errores(var16, "Error al insertar Codigo de Barra.", frame);
         }

         LogErrores.errores(var17, "Error al insertar Codigo de Barra..", frame);
      } finally {
         new CerrarRecursos(preparedStatementinsert, preparedStatementDelete, frame, "Error al insertar Codigo de Barra..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarCodigoBarra(String cod_producto, JinternalPadreString productosForm, TablaCodigoBarras tabla) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      ModeloTablaCodigoBarra modelo = (ModeloTablaCodigoBarra)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("Select cod_producto,codigo_barra from productos_codigo_barras where cod_producto =? ");
         preparedStatement.setString(1, cod_producto);
         rs2 = preparedStatement.executeQuery();

         while (rs2.next()) {
            modelo.addRow(new Object[]{rs2.getString("codigo_barra"), rs2.getString("codigo_barra")});
         }

         modelo.addDefaultRow();
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Codigo de Barra..", productosForm);
      } finally {
         new CerrarRecursos(preparedStatement, rs2, productosForm, "Error al seleccionar Codigo de Barra..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public ProductosBarrasE(String cod_producto, String codigo_barra) {
      this.cod_producto = cod_producto;
      this.codigo_barra = codigo_barra;
   }

   public String getCod_producto() {
      return this.cod_producto;
   }

   public void setCod_producto(String cod_producto) {
      this.cod_producto = cod_producto;
   }

   public String getCodigo_barra() {
      return this.codigo_barra;
   }

   public void setCodigo_barra(String codigo_barra) {
      this.codigo_barra = codigo_barra;
   }
}
