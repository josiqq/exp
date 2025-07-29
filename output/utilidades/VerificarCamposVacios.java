package utilidades;

import utilidadesSQL.DialogResultadoOperacion;
import utilidadesVentanas.JinternalPadre;

public class VerificarCamposVacios {
   public static boolean verificar(LimiteTextFieldConSQL txt_campo, JinternalPadre frame) {
      if (!txt_campo.getText().trim().equals("0") && !txt_campo.getText().trim().equals("") && txt_campo.getValue() != null) {
         return true;
      } else {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debe de tener un valor " + txt_campo.getText(), "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         txt_campo.requestFocusInWindow();
         return false;
      }
   }

   public static boolean verificar(LimiteTextFieldConSQLPlazo txt_campo, JinternalPadre frame) {
      if (!txt_campo.getText().trim().equals("0") && !txt_campo.getText().trim().equals("") && txt_campo.getValue() != null) {
         return true;
      } else {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debe de tener un valor " + txt_campo.getText(), "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         txt_campo.requestFocusInWindow();
         return false;
      }
   }

   public static boolean verificar(LimiteTextFieldConSQLClientes txt_campo, JinternalPadre frame) {
      if (!txt_campo.getText().trim().equals("0") && !txt_campo.getText().trim().equals("") && txt_campo.getValue() != null) {
         return true;
      } else {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Debe de tener un valor " + txt_campo.getText(), "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         txt_campo.requestFocusInWindow();
         return false;
      }
   }

   public static boolean verificarIguales(LimiteTextFieldConSQL txt_campo1, LimiteTextFieldConSQL txt_campo2, JinternalPadre frame) {
      if (txt_campo1.getText().trim().equals(txt_campo2.getText().trim())) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No deben de tener el mismo valor !", "error");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         txt_campo1.requestFocusInWindow();
         return false;
      } else {
         return true;
      }
   }
}
