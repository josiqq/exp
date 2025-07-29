package utilidadesOperaciones;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class F_OperacionesSistema {
   public static BigDecimal multiplicar(BigDecimal valor1, BigDecimal valor2) {
      return valor1.multiply(valor2).setScale(10, RoundingMode.HALF_EVEN);
   }

   public static BigDecimal dividir(BigDecimal valor1, BigDecimal valor2) {
      return valor1.divide(valor2, 10, RoundingMode.HALF_EVEN);
   }

   public static BigDecimal restar(BigDecimal valor1, BigDecimal valor2) {
      return valor1.subtract(valor2).setScale(10, RoundingMode.HALF_EVEN);
   }

   public static BigDecimal sumar(BigDecimal valor1, BigDecimal valor2) {
      return valor1.add(valor2).setScale(10, RoundingMode.HALF_EVEN);
   }
}
