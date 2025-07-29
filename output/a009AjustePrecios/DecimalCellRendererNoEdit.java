package a009AjustePrecios;

import java.awt.Color;
import java.awt.Component;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class DecimalCellRendererNoEdit extends DefaultTableCellRenderer {
   private static final long serialVersionUID = 1L;
   private Border editableBorder;
   DecimalFormat decimalFormat;

   public DecimalCellRendererNoEdit(int decimal) {
      if (decimal == 0) {
         this.decimalFormat = new DecimalFormat("#,##0");
      } else if (decimal == 2) {
         this.decimalFormat = new DecimalFormat("#,##0.00");
      } else if (decimal == 3) {
         this.decimalFormat = new DecimalFormat("#,##0.000");
      } else if (decimal == 7) {
         this.decimalFormat = new DecimalFormat("#,##0.0000000");
      } else if (decimal == 10) {
         this.decimalFormat = new DecimalFormat("#,##0.0000000000");
      }

      this.decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);
      DecimalFormatSymbols symbols = this.decimalFormat.getDecimalFormatSymbols();
      symbols.setDecimalSeparator(',');
      symbols.setGroupingSeparator('.');
      this.decimalFormat.setDecimalFormatSymbols(symbols);
      this.setHorizontalAlignment(4);
      this.editableBorder = new LineBorder(Color.BLACK);
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      cell.setBackground(Color.LIGHT_GRAY);
      this.setBorder(this.editableBorder);
      if (isSelected) {
         cell.setBackground(table.getSelectionBackground());
      }

      return cell;
   }

   @Override
   protected void setValue(Object value) {
      if (value != null) {
         this.setText(this.decimalFormat.format(Double.parseDouble(String.valueOf(value).replace(".", "").replace(",", "."))));
      }
   }
}
