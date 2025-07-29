package utilidadesSQL;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;

public class BotonFoco extends JButton implements FocusListener {
   private static final long serialVersionUID = 1L;

   public BotonFoco() {
      this.addFocusListener(this);
   }

   @Override
   public void focusGained(FocusEvent e) {
      ((JButton)e.getComponent()).setSelected(true);
      ((JButton)e.getComponent()).setBackground(Color.yellow);
   }

   @Override
   public void focusLost(FocusEvent e) {
      ((JButton)e.getComponent()).setSelected(false);
      ((JButton)e.getComponent()).setBackground(Color.red);
   }
}
