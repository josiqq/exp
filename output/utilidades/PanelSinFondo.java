package utilidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelSinFondo extends JPanel {
   private static final long serialVersionUID = 1L;

   public PanelSinFondo() {
      this.setOpaque(false);
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D)g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Color sombraColor = new Color(0, 0, 0, 40);
      g2d.setColor(sombraColor);
      g2d.fillRoundRect(10, 10, this.getWidth() - 20, this.getHeight() - 20, 10, 10);
      g2d.setColor(new Color(200, 200, 200));
      g2d.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
   }
}
