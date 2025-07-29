package a00Compras;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class F_calcular_impuestos {
   public static BigDecimal calcularGravado(BigDecimal ar_importe, double ar_iva, double ar_regimen, BigDecimal ar_porcentaje_gravado, int ar_tipo_fiscal) {
      BigDecimal v_valor_gravado = BigDecimal.ZERO;
      if (ar_tipo_fiscal == 2) {
         BigDecimal ar_regimen_decimal = BigDecimal.valueOf(ar_regimen);
         BigDecimal uno_mas_gravado = ar_regimen_decimal.divide(BigDecimal.valueOf(100L)).add(BigDecimal.ONE);
         v_valor_gravado = ar_importe.divide(uno_mas_gravado, 7, RoundingMode.HALF_EVEN);
      } else if (ar_tipo_fiscal == 0) {
         BigDecimal v_iva_imponible = ar_porcentaje_gravado.divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_EVEN);
         BigDecimal v_importe_imponible = ar_importe.multiply(v_iva_imponible);
         BigDecimal ar_iva_decimal = BigDecimal.valueOf(ar_iva);
         BigDecimal uno_mas_iva = ar_iva_decimal.divide(BigDecimal.valueOf(100L)).add(BigDecimal.ONE);
         v_valor_gravado = v_importe_imponible.divide(uno_mas_iva, 7, RoundingMode.HALF_EVEN);
      } else if (ar_tipo_fiscal == 1) {
         v_valor_gravado = ar_importe;
      }

      return v_valor_gravado;
   }

   public static BigDecimal calcularIVA(BigDecimal ar_importe, double ar_iva, double ar_regimen, int ar_tipo_fiscal) {
      BigDecimal v_valor_iva = BigDecimal.ZERO;
      if (ar_tipo_fiscal == 0) {
         BigDecimal ar_iva_decimal = BigDecimal.valueOf(ar_iva);
         BigDecimal uno_mas_iva = ar_iva_decimal.divide(BigDecimal.valueOf(100L)).add(BigDecimal.ONE);
         v_valor_iva = ar_importe.divide(uno_mas_iva, 7, RoundingMode.HALF_EVEN);
         v_valor_iva = ar_importe.subtract(v_valor_iva);
      }

      return v_valor_iva;
   }
}
