package utilidades;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

public class LimiteTextFieldString extends JTextField {
   private static final long serialVersionUID = 1L;

   public LimiteTextFieldString(final int limite) {
      this.setFont(new Font("Roboto", 0, 12));
      ((AbstractDocument)this.getDocument()).setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            int newLength = currentText.length() - length + text.length();
            if (newLength <= limite) {
               super.replace(fb, offset, length, text, attrs);
            }
         }
      });
      this.addFocusListener(new FocusAdapter() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldString.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            LimiteTextFieldString.this.setBackground(Color.WHITE);
         }
      });
      this.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 27) {
               LimiteTextFieldString.this.setText("");
            }
         }
      });
   }
}
