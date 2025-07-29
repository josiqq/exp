package a00ComprasGastos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import utilidades.CuadroTexto2Decimales;

public class GastosCalculoImpuestos {
   public static void calcularImpuestos(
      CuadroTexto2Decimales txt_exento,
      CuadroTexto2Decimales txt_gravado10,
      CuadroTexto2Decimales txt_gravado5,
      CuadroTexto2Decimales txt_total_iva10,
      CuadroTexto2Decimales txt_total_iva5,
      CuadroTexto2Decimales txt_total_general
   ) {
      BigDecimal v_totalGravado10 = txt_gravado10.getValorComoBigDecimal();
      BigDecimal v_totalIva10 = v_totalGravado10.divide(new BigDecimal(11), 10, RoundingMode.HALF_UP);
      txt_total_iva10.setValue(v_totalIva10);
      v_totalGravado10 = v_totalGravado10.subtract(v_totalIva10).setScale(10, RoundingMode.HALF_UP);
      txt_gravado10.setValue(v_totalGravado10);
      BigDecimal v_totalGravado05 = txt_gravado5.getValorComoBigDecimal();
      BigDecimal v_totalIva5 = v_totalGravado05.divide(new BigDecimal(21), 10, RoundingMode.HALF_UP);
      txt_total_iva5.setValue(v_totalIva5);
      v_totalGravado05 = v_totalGravado05.subtract(v_totalIva5).setScale(10, RoundingMode.HALF_UP);
      txt_gravado5.setValue(v_totalGravado05);
      BigDecimal v_totalExentas = txt_exento.getValorComoBigDecimal();
      BigDecimal totalGeneral = v_totalGravado10.add(v_totalIva10)
         .add(v_totalGravado05)
         .add(v_totalIva5)
         .add(v_totalExentas)
         .setScale(10, RoundingMode.HALF_UP);
      txt_total_general.setValue(totalGeneral);
   }
}
