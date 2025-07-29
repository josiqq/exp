package utilidades;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

public class TablaScrollPane extends JScrollPane {
   private static final long serialVersionUID = 1L;

   public TablaScrollPane(JTable tabla) {
      super(tabla);
      this.setOpaque(false);
      this.getViewport().setOpaque(false);
      this.setBorder(new LineBorder(Color.GRAY));
      this.setHorizontalScrollBarPolicy(30);
      this.setVerticalScrollBarPolicy(20);
   }
}
