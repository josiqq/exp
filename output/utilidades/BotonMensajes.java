package utilidades;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BotonMensajes extends JButton {
   private static final long serialVersionUID = 1L;

   public BotonMensajes(String texto, String imagen) {
      URL imageUrl = this.getClass().getResource("/imagenes/" + imagen);
      if (imageUrl != null) {
         ImageIcon iconoVentana = new ImageIcon(imageUrl);
         Image iconImage = iconoVentana.getImage();
         Image scaledIcon = iconImage.getScaledInstance(14, 14, 4);
         ImageIcon scaledIconoVentana = new ImageIcon(scaledIcon);
         this.setIcon(scaledIconoVentana);
      }

      this.setText(texto);
      final Color colorOriginal = new Color(230, 230, 230);
      final Color colorHover = new Color(205, 205, 205);
      this.setFocusPainted(false);
      this.setBorderPainted(false);
      this.setBackground(colorOriginal);
      this.setForeground(Color.BLACK);
      this.setFont(new Font("Roboto", 0, 12));
      this.setCursor(new Cursor(12));
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusLost(FocusEvent e) {
            BotonMensajes.this.setBackground(colorOriginal);
         }

         @Override
         public void focusGained(FocusEvent e) {
            BotonMensajes.this.setBackground(colorHover);
         }
      });
   }

   public BotonMensajes(String texto) {
      this.setText(texto);
      final Color colorOriginal = new Color(230, 230, 230);
      final Color colorHover = new Color(205, 205, 205);
      this.setFocusPainted(false);
      this.setBorderPainted(false);
      this.setBackground(colorOriginal);
      this.setForeground(Color.BLACK);
      this.setFont(new Font("Roboto", 0, 12));
      this.setCursor(new Cursor(12));
      this.setPreferredSize(new Dimension(60, 35));
      this.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusLost(FocusEvent e) {
            BotonMensajes.this.setBackground(colorOriginal);
         }

         @Override
         public void focusGained(FocusEvent e) {
            BotonMensajes.this.setBackground(colorHover);
         }
      });
   }
}
