package a3Permisos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JDialogPrincipal;

public class ListaPreciosUsuariosE {
   public static void cargarListaPrecioUsuario(String usuario, TablaListaPreciosUsuario tabla, JDialogPrincipal frame) {
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      ModeloTablaPermisos modelo = (ModeloTablaPermisos)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement(
            "select\r\nsys_usuarios_lista_precios.cod_lista,lista_precios_cab.nombre_lista,sys_usuarios_lista_precios.id\r\n from sys_usuarios_lista_precios,lista_precios_cab\r\nwhere\r\nsys_usuarios_lista_precios.cod_lista = lista_precios_cab.cod_lista and sys_usuarios_lista_precios.usuario =? "
         );
         preparedStatement.setString(1, usuario);
         rs = preparedStatement.executeQuery();

         while (rs.next()) {
            modelo.addRow(new Object[]{rs.getString("cod_lista"), rs.getString("nombre_lista"), rs.getInt("id"), rs.getInt("id")});
         }

         modelo.addDefaultRow();
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Lista de Usuario..", frame);
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar Lista de Usuario..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static int insertarListaPrecioUsuarios(String usuario, JTable tablaInsertar, JDialogPrincipal frame, List<Integer> codigosAEliminar) {
      int codigoColumn = tablaInsertar.getColumn("Código").getModelIndex();
      int descripcioncolumn = tablaInsertar.getColumn("Descripción").getModelIndex();
      int primarioColumn = tablaInsertar.getColumn("Primario").getModelIndex();
      int SWColumn = tablaInsertar.getColumn("SW").getModelIndex();
      PreparedStatement preparedStatementInsert = null;
      PreparedStatement preparedStatementDelete = null;
      PreparedStatement preparedStatementUpdate = null;
      Connection conexion = null;
      if (String.valueOf(tablaInsertar.getValueAt(0, codigoColumn)).trim().equals("")
         || tablaInsertar.getValueAt(0, codigoColumn) == null
         || String.valueOf(tablaInsertar.getValueAt(0, descripcioncolumn)).trim().equals("")
         || tablaInsertar.getValueAt(0, descripcioncolumn) == null) {
         LogErrores.errores(null, "No se puede grabar registro sin detalle", frame);
      }

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementDelete = conexion.prepareStatement("delete from sys_usuarios_lista_precios where id = ?");
         preparedStatementInsert = conexion.prepareStatement("INSERT INTO sys_usuarios_lista_precios (usuario,cod_lista) VALUES (?,?)");
         preparedStatementUpdate = conexion.prepareStatement("UPDATE sys_usuarios_lista_precios set  cod_lista=? where id =?");

         for (int id : codigosAEliminar) {
            preparedStatementDelete.setInt(1, id);
            preparedStatementDelete.addBatch();
         }

         for (int fila = 0; fila < tablaInsertar.getRowCount(); fila++) {
            if (!String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)).trim().equals("")
               && tablaInsertar.getValueAt(fila, codigoColumn) != null
               && !String.valueOf(tablaInsertar.getValueAt(fila, descripcioncolumn)).trim().equals("")
               && tablaInsertar.getValueAt(fila, descripcioncolumn) != null) {
               if (String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn)).trim().equals("0")) {
                  preparedStatementInsert.setString(1, usuario);
                  preparedStatementInsert.setInt(2, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn))));
                  preparedStatementInsert.addBatch();
               } else if (!String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))
                  .trim()
                  .equals(String.valueOf(tablaInsertar.getValueAt(fila, SWColumn)).trim())) {
                  preparedStatementUpdate.setInt(1, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn))));
                  preparedStatementUpdate.setInt(2, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))));
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
      } catch (Exception var20) {
         try {
            conexion.rollback();
         } catch (SQLException var19) {
            LogErrores.errores(var19, "Error al actualizar Lista de Usuario.", frame);
         }

         LogErrores.errores(var20, "Error al actualizar Lista de Usuario..", frame);
      } finally {
         new CerrarRecursos(preparedStatementInsert, preparedStatementUpdate, preparedStatementDelete, frame, "Error al insertar Lista de Usuario..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }
}
