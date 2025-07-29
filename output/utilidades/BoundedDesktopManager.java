package utilidades;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;

public class BoundedDesktopManager extends DefaultDesktopManager {
   private static final long serialVersionUID = 1L;

   @Override
   public void beginDraggingFrame(JComponent f) {
   }

   @Override
   public void beginResizingFrame(JComponent f, int direction) {
   }

   @Override
   public void setBoundsForFrame(JComponent f, int newX, int newY, int newWidth, int newHeight) {
      boolean didResize = f.getWidth() != newWidth || f.getHeight() != newHeight;
      if (!this.inBounds((JInternalFrame)f, newX, newY, newWidth, newHeight)) {
         Container parent = f.getParent();
         Dimension parentSize = parent.getSize();
         int boundedX = (int)Math.min((double)Math.max(0, newX), parentSize.getWidth() - newWidth);
         int boundedY = (int)Math.min((double)Math.max(0, newY), parentSize.getHeight() - newHeight);
         f.setBounds(boundedX, boundedY, newWidth, newHeight);
      } else {
         f.setBounds(newX, newY, newWidth, newHeight);
      }

      if (didResize) {
         f.validate();
      }
   }

   protected boolean inBounds(JInternalFrame f, int newX, int newY, int newWidth, int newHeight) {
      if (newX >= 0 && newY >= 0) {
         return newX + newWidth > f.getDesktopPane().getWidth() ? false : newY + newHeight <= f.getDesktopPane().getHeight();
      } else {
         return false;
      }
   }
}
