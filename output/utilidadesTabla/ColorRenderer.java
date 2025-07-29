package utilidadesTabla;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorRenderer extends DefaultTableCellRenderer {
   private static final long serialVersionUID = 1L;
   private Color colorFilaPar = new Color(224, 224, 224);
   private Color colorFilaImpar = new Color(245, 245, 245);

   @Override
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (!isSelected) {
         c.setBackground(row % 2 == 0 ? this.colorFilaPar : this.colorFilaImpar);
      }

      return c;
   }
}
