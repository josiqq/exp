package utilidades;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class BarraMenu extends JMenuBar {
   private static final long serialVersionUID = 1L;

   public BarraMenu(JMenu... menus) {
      this.setFondoERP();

      for (JMenu menu : menus) {
         this.add(menu);
      }
   }

   public void agregarMenu(JMenu menu) {
      this.add(menu);
   }

   private void setFondoERP() {
      this.setBackground(new Color(245, 245, 245));
      this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      this.setFont(new Font("Roboto", 0, 13));
   }
}
