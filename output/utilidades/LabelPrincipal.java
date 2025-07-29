package utilidades;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class LabelPrincipal extends JLabel {
   private static final long serialVersionUID = 1L;

   public LabelPrincipal(String titulo, String nombre) {
      this.setFont(new Font("Roboto", 0, 12));
      if (nombre.trim().equals("label")) {
         this.setText(titulo);
      } else if (nombre.trim().equals("titulo")) {
         this.setText(titulo);
         this.setForeground(Color.BLUE);
      } else if (nombre.trim().equals("lineas") || nombre.trim().equals("ultRegistro")) {
         this.setText(titulo);
         this.setForeground(Color.RED);
         this.setHorizontalAlignment(2);
      } else if (nombre.trim().equals("totalTexto")) {
         this.setText(titulo);
         this.setForeground(Color.RED);
         this.setHorizontalAlignment(4);
         this.setFont(new Font("Dialog", 1, 36));
      }
   }

   public LabelPrincipal(int justificacion) {
      this.setFont(new Font("Roboto", 0, 12));
      this.setOpaque(true);
      this.setBackground(new Color(200, 220, 200));
      this.setBorder(new ShadowBorder());
      if (justificacion == 0) {
         this.setHorizontalAlignment(2);
      } else if (justificacion == 1) {
         this.setHorizontalAlignment(0);
      }
   }

   public LabelPrincipal(int justificacion, int fuenteTamaño) {
      this.setFont(new Font("Roboto", 0, fuenteTamaño));
      this.setBorder(new LineBorder(new Color(0, 0, 0)));
      if (justificacion == 0) {
         this.setHorizontalAlignment(2);
      } else if (justificacion == 1) {
         this.setHorizontalAlignment(0);
      }
   }
}
