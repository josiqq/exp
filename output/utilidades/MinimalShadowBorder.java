package utilidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.AbstractBorder;

public class MinimalShadowBorder extends AbstractBorder {
   private static final long serialVersionUID = 1L;
   private final int shadowSize = 2;
   private final Color shadowColor = new Color(0, 0, 0, 60);

   @Override
   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Graphics2D g2 = (Graphics2D)g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(this.shadowColor);
      g2.fillRect(x + 2, y + height - 2, width - 2, 2);
      g2.fillRect(x + width - 2, y + 2, 2, height - 2);
      g2.dispose();
   }

   @Override
   public Insets getBorderInsets(Component c) {
      return new Insets(1, 1, 2, 2);
   }

   @Override
   public Insets getBorderInsets(Component c, Insets insets) {
      insets.set(1, 1, 2, 2);
      return insets;
   }
}
