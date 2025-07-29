package utilidades;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.NumberFormatter;

public class NumerosTextField extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private long limite;

   public NumerosTextField(long limite) {
      super((AbstractFormatter)createNumberFormatter(limite));
      this.setFont(new Font("Roboto", 0, 12));
      this.setLimite(limite);
      this.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            NumerosTextField.this.processDigitDeletion();
         }
      });
   }

   private static NumberFormatter createNumberFormatter(long limite) {
      NumberFormat format = DecimalFormat.getInstance();
      NumberFormatter formatter = new NumberFormatter(format);
      formatter.setValueClass(Long.class);
      formatter.setMinimum(0L);
      formatter.setMaximum(limite);
      formatter.setAllowsInvalid(false);
      formatter.setCommitsOnValidEdit(true);
      return formatter;
   }

   private void processDigitDeletion() {
      if (this.getText().isEmpty()) {
         this.setValue(0L);
      }
   }

   public long getLimite() {
      return this.limite;
   }

   public void setLimite(long limite) {
      this.limite = limite;
   }
}
