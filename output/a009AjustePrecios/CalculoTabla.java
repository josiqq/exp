package a009AjustePrecios;

import java.math.BigDecimal;
import javax.swing.JTable;
import utilidadesOperaciones.F_OperacionesSistema;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;

public class CalculoTabla {
   public static void calcularMargenCosto(int row, JTable tabla, int per_decimal_importe) {
      int margenCostoColumn = tabla.getColumn("Margen Costo").getModelIndex();
      int costoColumn = tabla.getColumn("Costo").getModelIndex();
      int precioNuevoColumn = tabla.getColumn("Precio Nuevo").getModelIndex();
      String v_costoString = String.valueOf(tabla.getValueAt(row, costoColumn)).replace(".", "").replace(",", ".");
      String v_margenCostoString = String.valueOf(tabla.getValueAt(row, margenCostoColumn)).replace(".", "").replace(",", ".");
      BigDecimal v_costo = new BigDecimal(v_costoString);
      BigDecimal v_margenCosto = new BigDecimal(v_margenCostoString);
      BigDecimal v_precioNuevo = BigDecimal.ZERO;
      v_precioNuevo = F_OperacionesSistema.multiplicar(v_costo, v_margenCosto);
      v_precioNuevo = F_OperacionesSistema.dividir(v_precioNuevo, new BigDecimal(100));
      v_precioNuevo = F_OperacionesSistema.sumar(v_precioNuevo, v_costo);
      FormatoDecimalOperacionSistema formatoDecimal10 = new FormatoDecimalOperacionSistema(per_decimal_importe);
      String formatoPrecioNuevo = formatoDecimal10.format(v_precioNuevo);
      tabla.setValueAt(formatoPrecioNuevo, row, precioNuevoColumn);
      tabla.setValueAt(0, row, tabla.getColumn("Margen Precio").getModelIndex());
   }

   public static void calcularMargenPrecio(int row, JTable tabla, int per_decimal_importe) {
      int margenPrecioColumn = tabla.getColumn("Margen Precio").getModelIndex();
      int precioActualColumn = tabla.getColumn("Precio Anterior").getModelIndex();
      int precioNuevoColumn = tabla.getColumn("Precio Nuevo").getModelIndex();
      String v_precioActualString = String.valueOf(tabla.getValueAt(row, precioActualColumn)).replace(".", "").replace(",", ".");
      String v_margenPrecioString = String.valueOf(tabla.getValueAt(row, margenPrecioColumn)).replace(".", "").replace(",", ".");
      BigDecimal v_precioActual = new BigDecimal(v_precioActualString);
      BigDecimal v_margenPrecio = new BigDecimal(v_margenPrecioString);
      BigDecimal v_precioNuevo = BigDecimal.ZERO;
      v_precioNuevo = F_OperacionesSistema.multiplicar(v_precioActual, v_margenPrecio);
      v_precioNuevo = F_OperacionesSistema.dividir(v_precioNuevo, new BigDecimal(100));
      v_precioNuevo = F_OperacionesSistema.sumar(v_precioNuevo, v_precioActual);
      FormatoDecimalOperacionSistema formatoDecimal10 = new FormatoDecimalOperacionSistema(per_decimal_importe);
      String formatoPrecioNuevo = formatoDecimal10.format(v_precioNuevo);
      tabla.setValueAt(formatoPrecioNuevo, row, precioNuevoColumn);
      tabla.setValueAt(0, row, tabla.getColumn("Margen Costo").getModelIndex());
   }
}
