package utilidades;

import a00PedidosClientes.PedidosClientesForm;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LabelImagenes extends JLabel {
   private static final long serialVersionUID = 1L;

   public LabelImagenes(String imagen) {
      ImageIcon iconoOriginal = new ImageIcon(PedidosClientesForm.class.getResource("/imagenes/" + imagen + ".png"));
      Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(90, 39, 4);
      ImageIcon iconoMoneda = new ImageIcon(imagenEscalada);
      this.setIcon(iconoMoneda);
      this.setFont(new Font("Roboto", 0, 12));
      this.setForeground(new Color(33, 37, 41));
      this.setIconTextGap(10);
      this.setHorizontalAlignment(2);
      this.setVerticalAlignment(0);
      this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
   }
}
