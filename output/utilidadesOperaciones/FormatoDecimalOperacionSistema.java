package utilidadesOperaciones;

import java.text.DecimalFormat;

public class FormatoDecimalOperacionSistema extends DecimalFormat {
   private static final long serialVersionUID = 1L;

   public FormatoDecimalOperacionSistema(int decimal) {
      this.applyPattern(switch (decimal) {
         case 0 -> "#,##0";
         default -> "#,##0.00";
         case 2 -> "#,##0.00";
         case 3 -> "##,##0.000";
         case 7 -> "##,##0.0000000";
         case 10 -> "##,##0.0000000000";
      });
   }
}
