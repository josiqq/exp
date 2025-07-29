package utilidades;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class BotonPadreJasper extends JButton {
   private static final long serialVersionUID = 1L;

   public BotonPadreJasper(String nombre, String imagen, final Color colorOriginal) {
      final Color colorHover = new Color(88, 214, 141);
      this.setFocusPainted(false);
      this.setBorderPainted(false);
      this.setBackground(colorOriginal);
      this.setForeground(Color.white);
      this.setFont(new Font("Roboto", 1, 18));
      this.setText(nombre);
      this.setVerticalTextPosition(1);
      this.setHorizontalTextPosition(0);
      this.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent evt) {
            BotonPadreJasper.this.setBackground(colorHover);
         }

         @Override
         public void mouseExited(MouseEvent evt) {
            BotonPadreJasper.this.setBackground(colorOriginal);
         }
      });
   }
}
