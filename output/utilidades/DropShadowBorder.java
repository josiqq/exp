package utilidades;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.border.AbstractBorder;

public class DropShadowBorder extends AbstractBorder {
   private static final long serialVersionUID = 1L;
   private final int shadowSize = 6;
   private final float opacity = 0.3F;
   private final Color shadowColor = Color.BLACK;
   private BufferedImage shadow;

   public DropShadowBorder() {
      this.generateShadow();
   }

   private void generateShadow() {
      int size = 12;
      this.shadow = new BufferedImage(size, size, 2);
      Graphics2D g2 = this.shadow.createGraphics();
      g2.setComposite(AlphaComposite.SrcOver.derive(0.3F));
      g2.setColor(this.shadowColor);
      g2.fillRoundRect(6, 6, 6, 6, 8, 8);
      g2.dispose();
   }

   @Override
   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Graphics2D g2 = (Graphics2D)g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      int sx = x + width - 6;
      int sy = y + height - 6;
      g2.drawImage(this.shadow, sx - 6, sy - 6, null);
      g2.dispose();
   }

   @Override
   public Insets getBorderInsets(Component c) {
      return new Insets(4, 4, 6, 6);
   }

   @Override
   public Insets getBorderInsets(Component c, Insets insets) {
      insets.set(4, 4, 6, 6);
      return insets;
   }
}
