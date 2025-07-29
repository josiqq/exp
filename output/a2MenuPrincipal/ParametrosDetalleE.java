package a2MenuPrincipal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;

public class ParametrosDetalleE {
   private int id;
   private int estado;
   private String nombre_menu;
   private String fecha_alta;

   public static int actualizarParametros(JTable tabla, JinternalPadre frame) {
      int idColumn = tabla.getColumn("ID").getModelIndex();
      int estadocolumn = tabla.getColumn("Estado").getModelIndex();
      Connection conexion = null;
      PreparedStatement preparedStatement2 = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement("update sys_parametros_detalle set estado = ? where id_parametro=?");

         for (int fila = 0; fila < tabla.getRowCount(); fila++) {
            boolean estado = (Boolean)tabla.getValueAt(fila, estadocolumn);
            preparedStatement2.setInt(1, estado ? 1 : 0);
            preparedStatement2.setInt(2, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, idColumn))));
            preparedStatement2.addBatch();
         }

         preparedStatement2.executeBatch();
         conexion.commit();
         return 1;
      } catch (Exception var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al actualizar Parametros.", frame);
         }

         LogErrores.errores(var14, "Error al actualizar Parametros..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, frame, "Error al actualizar Parametros..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static void cargarTabla(ModeloTablaDefecto modeloTabla, String condicion, JinternalPadre frame) {
      modeloTabla.setRowCount(0);
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("Select id_parametro,nombre_parametro,estado,modulo from sys_parametros_detalle " + condicion);
         rs = preparedStatement.executeQuery();

         while (rs.next()) {
            modeloTabla.addRow(new Object[]{rs.getInt("id_parametro"), rs.getString("nombre_parametro"), rs.getString("modulo"), rs.getBoolean("estado")});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al cargar Parametros ", frame);
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al cargar Parametros ");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static int buscarParametros(int id_parametro, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      int var7;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement("select estado from sys_parametros_detalle where id_parametro =?");
         preparedStatementSelect.setInt(1, id_parametro);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            return 0;
         }

         var7 = resultado.getInt("estado");
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Parametro..", frame);
         return 0;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Parametro....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var7;
   }

   public ParametrosDetalleE(int estado) {
      this.estado = estado;
   }

   public ParametrosDetalleE(int id, int estado, String nombre_menu) {
      this.id = id;
      this.nombre_menu = nombre_menu;
      this.estado = estado;
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getNombre_menu() {
      return this.nombre_menu;
   }

   public void setNombre_menu(String nombre_menu) {
      this.nombre_menu = nombre_menu;
   }

   public String getFecha_alta() {
      return this.fecha_alta;
   }

   public void setFecha_alta(String fecha_alta) {
      this.fecha_alta = fecha_alta;
   }
}
