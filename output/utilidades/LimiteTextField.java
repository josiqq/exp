package utilidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

public class LimiteTextField extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private int limite;

   public LimiteTextField(int limite) {
      this.setFont(new Font("Roboto", 0, 12));
      this.limite = limite;
      this.setBorder(new MinimalShadowBorder());
      this.setFormatter(new LimiteTextField.LimiteFormatter());
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextField.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            LimiteTextField.this.setBackground(Color.WHITE);
         }
      });
      this.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 27) {
               LimiteTextField.this.setValue("");
            }
         }

         @Override
         public void keyReleased(KeyEvent e) {
         }
      });
   }

   private class LimiteFormatter extends DefaultFormatter {
      private static final long serialVersionUID = 1L;

      @Override
      public Object stringToValue(String text) throws ParseException {
         if (text.length() > LimiteTextField.this.limite) {
            throw new ParseException("Límite alcanzado", LimiteTextField.this.limite);
         } else {
            return super.stringToValue(text);
         }
      }

      @Override
      public String valueToString(Object value) throws ParseException {
         return super.valueToString(value);
      }

      @Override
      protected DocumentFilter getDocumentFilter() {
         return new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
               String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
               int newLength = currentText.length() - length + text.length();
               if (newLength > LimiteTextField.this.limite && length <= 0) {
                  LimiteFormatter.this.showLimitAlert();
               } else {
                  super.replace(fb, offset, length, text, attrs);
               }
            }
         };
      }

      @Override
      public void install(JFormattedTextField ftf) {
         super.install(ftf);
         ftf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               LimiteFormatter.this.checkLimit();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               LimiteFormatter.this.checkLimit();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               LimiteFormatter.this.checkLimit();
            }
         });
      }

      private void checkLimit() {
         String text = this.getFormattedTextField().getText();
         if (text.length() > LimiteTextField.this.limite) {
            String truncatedText = text.substring(0, LimiteTextField.this.limite);
            SwingUtilities.invokeLater(() -> this.getFormattedTextField().setText(truncatedText));
            this.showLimitAlert();
         }
      }

      private void showLimitAlert() {
         SwingUtilities.invokeLater(() -> {
            Component parentComponent = LimiteTextField.this;
            Point location = parentComponent.getLocationOnScreen();
            location.translate(parentComponent.getWidth() / 2, parentComponent.getHeight() / 2);
            JOptionPane.showMessageDialog(parentComponent, "Límite alcanzado", "Alerta", 2);
         });
      }
   }
}
