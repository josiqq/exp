package utilidades;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class BarraJMenu extends JMenu {
   private static final long serialVersionUID = 1L;

   public BarraJMenu(String titulo, List<JMenuItem> items) {
      super(titulo);
      this.setEstiloERP();

      for (JMenuItem item : items) {
         this.add(item);
      }
   }

   public BarraJMenu(String titulo, JMenuItem... items) {
      super(titulo);
      this.setEstiloERP();

      for (JMenuItem item : items) {
         this.add(item);
      }
   }

   private void setEstiloERP() {
      this.setFont(new Font("Roboto", 0, 13));
      this.setForeground(new Color(33, 37, 41));
   }

   public void agregarItem(JMenuItem item) {
      this.add(item);
   }
}
