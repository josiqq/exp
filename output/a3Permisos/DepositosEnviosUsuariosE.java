package a3Permisos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JDialogPrincipal;

public class DepositosEnviosUsuariosE {
   public static void cargarDepositoEnvioUsuario(String usuario, TablaDepositoEnviosUsuarios tabla, JDialogPrincipal frame) {
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      ModeloTablaPermisos modelo = (ModeloTablaPermisos)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement(
            "select\r\nusuario,cod_deposito_origen,\r\n(select depositos.nombre_deposito from depositos where\r\ndepositos.cod_deposito = sys_usuarios_depositos_envios.cod_deposito_origen) as nombre_deposito_origen,\r\ncod_deposito_destino,\r\n(select depositos.nombre_deposito from depositos where\r\ndepositos.cod_deposito = sys_usuarios_depositos_envios.cod_deposito_destino) as nombre_deposito_destino,\r\nrecibe,sys_usuarios_depositos_envios.id\r\n from sys_usuarios_depositos_envios where sys_usuarios_depositos_envios.usuario =? "
         );
         preparedStatement.setString(1, usuario);
         rs = preparedStatement.executeQuery();

         while (rs.next()) {
            modelo.addRow(
               new Object[]{
                  rs.getString("cod_deposito_origen"),
                  rs.getString("nombre_deposito_origen"),
                  rs.getString("cod_deposito_destino"),
                  rs.getString("nombre_deposito_destino"),
                  rs.getBoolean("recibe"),
                  rs.getInt("id"),
                  0
               }
            );
         }

         modelo.addDefaultRow2();
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Deposito del Usuario..", frame);
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar Deposito del Usuario..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static int insertarDepositosEnviosUsuarios(String usuario, JTable tablaInsertar, JDialogPrincipal frame, List<Integer> codigosAEliminar) {
      int codOrigenColumn = tablaInsertar.getColumn("Código").getModelIndex();
      int descripcioncolumn = tablaInsertar.getColumn("Descripción").getModelIndex();
      int codDestinoColumn = tablaInsertar.getColumn("Codigo").getModelIndex();
      int recibeColumn = tablaInsertar.getColumn("Recibe").getModelIndex();
      int primarioColumn = tablaInsertar.getColumn("Primario").getModelIndex();
      int SWColumn = tablaInsertar.getColumn("SW").getModelIndex();
      int cod_deposito_origen = 0;
      int cod_deposito_destino = 0;
      PreparedStatement preparedStatementInsert = null;
      PreparedStatement preparedStatementDelete = null;
      PreparedStatement preparedStatementUpdate = null;
      int recibe = 0;
      Connection conexion = null;
      if (String.valueOf(tablaInsertar.getValueAt(0, codOrigenColumn)).trim().equals("")
         || tablaInsertar.getValueAt(0, codOrigenColumn) == null
         || String.valueOf(tablaInsertar.getValueAt(0, descripcioncolumn)).trim().equals("")
         || tablaInsertar.getValueAt(0, descripcioncolumn) == null) {
         LogErrores.errores(null, "No se puede grabar registro sin detalle", frame);
      }

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementDelete = conexion.prepareStatement("delete from sys_usuarios_depositos_envios where id = ?");
         preparedStatementInsert = conexion.prepareStatement(
            "INSERT INTO sys_usuarios_depositos_envios (usuario,cod_deposito_origen,cod_deposito_destino,recibe) VALUES (?,?,?,?)"
         );
         preparedStatementUpdate = conexion.prepareStatement(
            "UPDATE sys_usuarios_depositos_envios set  cod_deposito_origen=?,cod_deposito_destino =?,recibe =? where id =?"
         );

         for (int id : codigosAEliminar) {
            preparedStatementDelete.setInt(1, id);
            preparedStatementDelete.addBatch();
         }

         for (int fila = 0; fila < tablaInsertar.getRowCount(); fila++) {
            if (!String.valueOf(tablaInsertar.getValueAt(fila, codOrigenColumn)).trim().equals("")
               && tablaInsertar.getValueAt(fila, codOrigenColumn) != null
               && !String.valueOf(tablaInsertar.getValueAt(fila, descripcioncolumn)).trim().equals("")
               && tablaInsertar.getValueAt(fila, descripcioncolumn) != null) {
               cod_deposito_origen = Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, codOrigenColumn)));
               cod_deposito_destino = Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, codDestinoColumn)));
               if (cod_deposito_destino == cod_deposito_origen) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Deposito Origen no puede ser igual a Destino", "error");
                  rs.setLocationRelativeTo(frame);
                  rs.setVisible(true);
               } else if (String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn)).trim().equals("0")) {
                  preparedStatementInsert.setString(1, usuario);
                  preparedStatementInsert.setInt(2, cod_deposito_origen);
                  preparedStatementInsert.setInt(3, cod_deposito_destino);
                  preparedStatementInsert.setBoolean(4, (Boolean)tablaInsertar.getValueAt(fila, recibeColumn));
                  preparedStatementInsert.addBatch();
               } else if (!String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))
                  .trim()
                  .equals(String.valueOf(tablaInsertar.getValueAt(fila, SWColumn)).trim())) {
                  preparedStatementUpdate.setInt(1, cod_deposito_origen);
                  preparedStatementUpdate.setInt(2, cod_deposito_destino);
                  preparedStatementUpdate.setBoolean(3, (Boolean)tablaInsertar.getValueAt(fila, recibeColumn));
                  preparedStatementUpdate.setInt(4, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))));
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
      } catch (Exception var25) {
         try {
            conexion.rollback();
         } catch (SQLException var24) {
            LogErrores.errores(var24, "Error al actualizar Deposito de Usuarios.", frame);
         }

         LogErrores.errores(var25, "Error al actualizar Deposito de Usuarios..", frame);
      } finally {
         new CerrarRecursos(preparedStatementInsert, preparedStatementUpdate, preparedStatementDelete, frame, "Error al insertar Deposito de Usuarios..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }
}
