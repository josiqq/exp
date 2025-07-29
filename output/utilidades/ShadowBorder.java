package utilidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.AbstractBorder;

public class ShadowBorder extends AbstractBorder {
   private static final long serialVersionUID = 1L;
   private final int shadowSize = 4;
   private final Color shadowColor = new Color(0, 0, 0, 80);

   @Override
   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Graphics2D g2d = (Graphics2D)g.create();
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setColor(this.shadowColor);
      g2d.fillRect(x + 4, y + height - 4, width - 4, 4);
      g2d.fillRect(x + width - 4, y + 4, 4, height - 4);
      g2d.dispose();
   }

   @Override
   public Insets getBorderInsets(Component c) {
      return new Insets(0, 0, 4, 4);
   }

   @Override
   public Insets getBorderInsets(Component c, Insets insets) {
      insets.set(0, 0, 4, 4);
      return insets;
   }
}
