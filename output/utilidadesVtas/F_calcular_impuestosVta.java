package utilidadesVtas;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class F_calcular_impuestosVta {
   public static BigDecimal calcularGravado(
      BigDecimal par_importe, double par_iva, double par_regimen, BigDecimal par_porcentaje_gravado, int par_tipo_fiscal, int par_tipo_fiscal_cliente
   ) {
      BigDecimal v_valor_gravado = BigDecimal.ZERO;
      if (par_tipo_fiscal != 1 && (par_tipo_fiscal != 2 || par_tipo_fiscal_cliente != 2)) {
         BigDecimal v_iva_imponible = par_porcentaje_gravado.divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP);
         BigDecimal v_importe_imponible = par_importe.multiply(v_iva_imponible);
         BigDecimal ar_iva_decimal = BigDecimal.valueOf(par_iva);
         BigDecimal uno_mas_iva = ar_iva_decimal.divide(BigDecimal.valueOf(100L)).add(BigDecimal.ONE);
         v_valor_gravado = v_importe_imponible.divide(uno_mas_iva, 16, RoundingMode.DOWN);
      } else {
         v_valor_gravado = par_importe;
      }

      return v_valor_gravado;
   }

   public static BigDecimal calcularIVA(BigDecimal par_importe, double par_iva, double par_regimen, int par_tipo_fiscal, int par_tipo_fiscal_cliente) {
      BigDecimal v_valor_iva = BigDecimal.ZERO;
      if (par_tipo_fiscal_cliente == 0 || par_tipo_fiscal == 2 && par_tipo_fiscal_cliente == 0 || par_tipo_fiscal == 0) {
         BigDecimal ar_iva_decimal = BigDecimal.valueOf(par_iva);
         BigDecimal uno_mas_iva = ar_iva_decimal.divide(BigDecimal.valueOf(100L)).add(BigDecimal.ONE);
         v_valor_iva = par_importe.divide(uno_mas_iva, 16, RoundingMode.DOWN);
         v_valor_iva = par_importe.subtract(v_valor_iva);
      }

      return v_valor_iva;
   }
}
