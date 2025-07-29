package utilidadesTablaDetalle;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class PintarCeldasEditables2 extends DefaultTableCellRenderer {
   private static final long serialVersionUID = 1L;
   private Border editableBorder = new LineBorder(Color.BLACK);

   @Override
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      cell.setBackground(new Color(245, 245, 245));
      this.setBorder(this.editableBorder);
      if (isSelected) {
         cell.setBackground(table.getSelectionBackground());
      }

      return cell;
   }
}
