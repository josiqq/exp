package utilidadesTablaDetalle;

import java.text.DecimalFormat;
import javax.swing.text.NumberFormatter;

public class FormatoDecimalSistema extends NumberFormatter {
   private static final long serialVersionUID = 1L;

   public FormatoDecimalSistema(int decimal) {
      DecimalFormat decimalFormat = null;
      if (decimal == 0) {
         decimalFormat = new DecimalFormat("#,##0");
      } else if (decimal == 2) {
         decimalFormat = new DecimalFormat("#,##0.00");
      } else if (decimal == 3) {
         decimalFormat = new DecimalFormat("#,##0.000");
      } else if (decimal == 7) {
         decimalFormat = new DecimalFormat("#,##0.0000000");
      }

      this.setFormat(decimalFormat);
      this.setValueClass(Double.class);
      this.setAllowsInvalid(false);
   }

   public FormatoDecimalSistema(int decimal, int negativo) {
      DecimalFormat decimalFormat = null;
      if (decimal == 0) {
         decimalFormat = new DecimalFormat("#,##0");
      } else if (decimal == 2) {
         decimalFormat = new DecimalFormat("#,##0.00");
      } else if (decimal == 3) {
         decimalFormat = new DecimalFormat("#,##0.000");
      } else if (decimal == 7) {
         decimalFormat = new DecimalFormat("#,##0.0000000");
      }

      this.setFormat(decimalFormat);
      this.setValueClass(Double.class);
      this.setMinimum(null);
      this.setAllowsInvalid(true);
      this.setCommitsOnValidEdit(true);
   }
}
