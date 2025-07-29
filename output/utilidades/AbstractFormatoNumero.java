package utilidades;

import java.awt.Toolkit;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

public class AbstractFormatoNumero {
   public static AbstractFormatter createFormatter(final int maxLength) {
      NumberFormat format = NumberFormat.getIntegerInstance();
      format.setGroupingUsed(false);
      DefaultFormatter formatter = new DefaultFormatter() {
         private static final long serialVersionUID = 1L;

         @Override
         protected DocumentFilter getDocumentFilter() {
            return new DocumentFilter() {
               @Override
               public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                  if (text.matches("\\d*")) {
                     String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                     int newLength = currentText.length() - length + text.length();
                     if (newLength <= maxLength) {
                        super.replace(fb, offset, length, text, attrs);
                     } else {
                        Toolkit.getDefaultToolkit().beep();
                     }
                  }
               }

               @Override
               public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                  this.replace(fb, offset, 0, string, attr);
               }
            };
         }

         @Override
         public Object stringToValue(String text) throws ParseException {
            if (text.isEmpty()) {
               return null;
            } else if (!text.matches("\\d+")) {
               throw new ParseException("Solo números positivos", 0);
            } else {
               return Integer.parseInt(text);
            }
         }
      };
      formatter.setOverwriteMode(false);
      formatter.setCommitsOnValidEdit(true);
      return formatter;
   }
}
