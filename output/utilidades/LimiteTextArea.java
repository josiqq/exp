package utilidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimiteTextArea extends JTextArea {
   private static final long serialVersionUID = 1L;
   private int limiteCaracteres;

   public LimiteTextArea(int limiteCaracteres) {
      this.setFont(new Font("Roboto", 0, 12));
      this.limiteCaracteres = limiteCaracteres;
      this.setBorder(new MinimalShadowBorder());
      this.setLineWrap(true);
      this.setWrapStyleWord(true);
      this.setDocument(new LimiteTextArea.LimitedPlainDocument());
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextArea.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            LimiteTextArea.this.setBackground(Color.WHITE);
         }
      });
      this.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               e.consume();
               LimiteTextArea.this.append("\n");
            }
         }

         @Override
         public void keyReleased(KeyEvent e) {
         }
      });
   }

   private void mostrarAdvertencia(String mensaje) {
      Component componentePadre = SwingUtilities.getWindowAncestor(this);
      JOptionPane.showMessageDialog(componentePadre, mensaje, "Advertencia", 2);
   }

   private class LimitedPlainDocument extends PlainDocument {
      private static final long serialVersionUID = 1L;

      @Override
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
         if (str != null && this.getLength() + str.length() <= LimiteTextArea.this.limiteCaracteres) {
            super.insertString(offs, str, a);
         } else {
            LimiteTextArea.this.mostrarAdvertencia("Se ha alcanzado el límite de caracteres.");
         }
      }
   }
}
