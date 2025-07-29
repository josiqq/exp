package utilidadesTablaDetalle;

import javax.swing.JTable;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadreString;

public class ValidacionesTabla {
   public static boolean validarSinDetalles(JTable tabla, JinternalPadre frame) {
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      if (tabla.getRowCount() - 1 >= 0
         && !String.valueOf(tabla.getValueAt(0, descripcioncolumn)).trim().equals("")
         && tabla.getValueAt(0, descripcioncolumn) != null) {
         return true;
      } else {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No se puede grabar registro sin detalles!", "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         tabla.requestFocusInWindow();
         tabla.changeSelection(0, 0, false, false);
         tabla.editCellAt(0, 0);
         return false;
      }
   }

   public static boolean validarCeros(JTable tabla, int fila, double valor, String mensaje, JinternalPadre frame) {
      double EPSILON = 1.0E-9;
      if (Math.abs(valor) > 1.0E-9) {
         return true;
      } else {
         DialogResultadoOperacion resultado = new DialogResultadoOperacion(mensaje, "error");
         resultado.setLocationRelativeTo(frame);
         resultado.setVisible(true);
         tabla.requestFocusInWindow();
         tabla.changeSelection(fila, 0, false, false);
         tabla.editCellAt(fila, 0);
         return false;
      }
   }

   public static boolean validarSinDetalles(JTable tabla, JinternalPadreString frame) {
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      if (tabla.getRowCount() - 1 >= 0
         && !String.valueOf(tabla.getValueAt(0, descripcioncolumn)).trim().equals("")
         && tabla.getValueAt(0, descripcioncolumn) != null) {
         return true;
      } else {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No se puede grabar registro sin detalles!", "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         tabla.requestFocusInWindow();
         tabla.changeSelection(0, 0, false, false);
         tabla.editCellAt(0, 0);
         return false;
      }
   }

   public static boolean validarSinDetalles(JTable tabla, int barra, JinternalPadreString frame) {
      int descripcioncolumn = tabla.getColumn("Codigo de Barras").getModelIndex();
      if (tabla.getRowCount() - 1 >= 0
         && !String.valueOf(tabla.getValueAt(0, descripcioncolumn)).trim().equals("")
         && tabla.getValueAt(0, descripcioncolumn) != null) {
         return true;
      } else {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No se puede grabar registro sin detalles!", "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         tabla.requestFocusInWindow();
         tabla.changeSelection(0, 0, false, false);
         tabla.editCellAt(0, 0);
         return false;
      }
   }
}
