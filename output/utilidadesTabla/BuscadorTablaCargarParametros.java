package utilidadesTabla;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utilidades.LimiteTextField;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class BuscadorTablaCargarParametros {
   private ModeloTablaDefecto modelo;
   private LimiteTextField txt_buscador;

   public BuscadorTablaCargarParametros(final LimiteTextField txt_buscador, final ModeloTablaDefecto modelo, final String modulo, final JinternalPadre frame) {
      this.txt_buscador = txt_buscador;
      this.txt_buscador.setName("txt_buscador");
      this.modelo = modelo;
      this.txt_buscador
         .addKeyListener(
            new KeyListener() {
               @Override
               public void keyTyped(KeyEvent e) {
               }

               @Override
               public void keyReleased(KeyEvent e) {
               }

               @Override
               public void keyPressed(KeyEvent e) {
                  if (modelo.getRowCount() >= 0 && e.getKeyCode() == 10) {
                     if (txt_buscador.getText().trim().equals("")) {
                        BuscadorTablaCargarParametros.this.cargarTabla("", frame);
                     } else if (modulo.trim().equals("Todos")) {
                        BuscadorTablaCargarParametros.this.cargarTabla(" where nombre_parametro like '%" + txt_buscador.getText().trim() + "%'", frame);
                     } else if (modulo.trim().equals("Stock")) {
                        BuscadorTablaCargarParametros.this.cargarTabla(
                           " where modulo = Stock and nombre_parametro like '%" + txt_buscador.getText().trim() + "%'", frame
                        );
                     }
                  }
               }
            }
         );
   }

   private void cargarTabla(String condicion, JinternalPadre frame) {
      this.modelo.setRowCount(0);
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("Select id_parametro,nombre_parametro,estado,modulo from sys_parametros_detalle " + condicion);
         rs = preparedStatement.executeQuery();

         while (rs.next()) {
            this.modelo.addRow(new Object[]{rs.getInt("id_parametro"), rs.getString("nombre_parametro"), rs.getString("modulo"), rs.getBoolean("estado")});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al cargar Parametros ", frame);
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al cargar Parametros ");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }
}
