package utilidades;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JRadioButton;

public class RadioBoton extends JRadioButton {
   private static final long serialVersionUID = 1L;

   public RadioBoton(String texto) {
      Font fuente = new Font("Roboto", 0, 14);
      Color colorTexto = new Color(33, 37, 41);
      this.setText(texto);
      this.setFont(fuente);
      this.setForeground(colorTexto);
      this.setBackground(new Color(245, 245, 245));
      this.setFocusPainted(false);
      this.setCursor(new Cursor(12));
      this.setOpaque(false);
      this.setContentAreaFilled(false);
      this.setBorderPainted(false);
   }
}
