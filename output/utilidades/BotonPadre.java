package utilidades;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BotonPadre extends JButton {
   private static final long serialVersionUID = 1L;

   public BotonPadre(String nombre) {
      this.setText(nombre);
      this.setFont(new Font("Roboto", 0, 12));
   }

   public BotonPadre(String nombre, char nemo) {
      if (!nombre.trim().equals("") && nemo != ' ') {
         this.setFont(new Font("Roboto", 0, 12));
         nombre.equals("cancelar");
      } else {
         this.setText(nombre);
      }

      if (nombre.trim().equals("Ok")) {
         this.setFont(new Font("Roboto", 0, 12));
      }
   }

   public BotonPadre(String nombre, String imagen) {
      URL imageUrl = this.getClass().getResource("/imagenes/" + imagen);
      ImageIcon iconoGuardar = null;
      if (imageUrl != null) {
         iconoGuardar = new ImageIcon(imageUrl);
         this.setText(nombre);
         this.setIcon(iconoGuardar);
      }

      final Color colorOriginal = new Color(213, 245, 227);
      final Color colorHover = new Color(88, 214, 141);
      this.setFocusPainted(false);
      this.setBorderPainted(false);
      this.setBackground(colorOriginal);
      this.setForeground(Color.BLACK);
      this.setFont(new Font("Roboto", 1, 9));
      this.setCursor(new Cursor(12));
      this.setPreferredSize(new Dimension(120, 35));
      this.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent evt) {
            BotonPadre.this.setBackground(colorHover);
         }

         @Override
         public void mouseExited(MouseEvent evt) {
            BotonPadre.this.setBackground(colorOriginal);
         }
      });
      this.setIcon(iconoGuardar);
   }
}
