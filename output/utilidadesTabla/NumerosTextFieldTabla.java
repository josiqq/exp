package utilidadesTabla;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JFormattedTextField;
import javax.swing.SwingUtilities;

public class NumerosTextFieldTabla extends JFormattedTextField {
   private static final long serialVersionUID = 1L;

   public NumerosTextFieldTabla() {
      this.setHorizontalAlignment(4);
      this.addFocusListener(new FocusAdapter() {
         @Override
         public void focusGained(FocusEvent e) {
            SwingUtilities.invokeLater(() -> {
               NumerosTextFieldTabla.this.setBackground(Color.red);
               NumerosTextFieldTabla.this.setCaretPosition(NumerosTextFieldTabla.this.getText().length());
            });
         }
      });
   }
}
