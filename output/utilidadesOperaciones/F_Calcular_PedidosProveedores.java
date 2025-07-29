package utilidadesOperaciones;

import a00pedidosProveedores.DecimalFilter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JTable;

public class F_Calcular_PedidosProveedores {
   public static void calcularTotal(JTable tabla, DecimalFilter txt_total) {
      BigDecimal subTotal = BigDecimal.ZERO;
      BigDecimal totalGeneral = BigDecimal.ZERO;
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();

      for (int fila = 0; fila < tabla.getRowCount(); fila++) {
         subTotal = new BigDecimal(String.valueOf(tabla.getValueAt(fila, subTotalColumn)).replace(".", "").replace(",", "."));
         totalGeneral = totalGeneral.add(subTotal).setScale(16, RoundingMode.DOWN);
      }

      txt_total.setValue(totalGeneral);
   }

   public static void calcularSubTotal(int row, JTable tabla) {
      int precioColumn = tabla.getColumn("Precio").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      String v_cantidadString = String.valueOf(tabla.getValueAt(row, cantidadColumn)).replace(".", "").replace(",", ".");
      String v_precioVentaString = String.valueOf(tabla.getValueAt(row, precioColumn)).replace(".", "").replace(",", ".");
      BigDecimal v_cantidad = new BigDecimal(v_cantidadString);
      BigDecimal v_precioVenta = new BigDecimal(v_precioVentaString);
      BigDecimal v_subTotal = BigDecimal.ZERO;
      v_subTotal = v_cantidad.multiply(v_precioVenta).setScale(16, RoundingMode.DOWN);
      DecimalFormat formaro10Decimales = new DecimalFormat("#,##0.0000000000000000");
      String formatoPrecioSubTotal = formaro10Decimales.format(v_subTotal);
      tabla.setValueAt(formatoPrecioSubTotal, row, subTotalColumn);
   }
}
