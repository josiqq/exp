package utilidadesVtas;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JTable;

public class F_Calcular_Vtas {
   public static void incremetarImportes(BigDecimal par_incremento, int row, JTable tabla) {
      int precioVentaColumn = tabla.getColumn("Precio Venta").getModelIndex();
      int precioListaColumn = tabla.getColumn("Precio Lista").getModelIndex();
      int descuentoColumn = tabla.getColumn("Desc.").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      DecimalFormat formaro10Decimales = new DecimalFormat("#,##0.0000000000000000");
      String v_precioVentaOriginalString = String.valueOf(tabla.getValueAt(row, precioListaColumn)).replace(".", "").replace(",", ".");
      BigDecimal v_precioVentaOriginal = new BigDecimal(v_precioVentaOriginalString);
      v_precioVentaOriginal = v_precioVentaOriginal.multiply(par_incremento);
      String formatoPrecioSubTotal = formaro10Decimales.format(v_precioVentaOriginal);
      tabla.setValueAt(formatoPrecioSubTotal, row, precioVentaColumn);
      String v_cantidadString = String.valueOf(tabla.getValueAt(row, cantidadColumn)).replace(".", "").replace(",", ".");
      String v_descuentoString = String.valueOf(tabla.getValueAt(row, descuentoColumn)).replace(".", "").replace(",", ".");
      String v_precioVentaString = String.valueOf(tabla.getValueAt(row, precioVentaColumn)).replace(".", "").replace(",", ".");
      BigDecimal v_cantidad = new BigDecimal(v_cantidadString);
      BigDecimal v_precioVenta = new BigDecimal(v_precioVentaString);
      BigDecimal v_subTotal = BigDecimal.ZERO;
      v_subTotal = v_cantidad.multiply(v_precioVenta).setScale(16, RoundingMode.DOWN);
      formatoPrecioSubTotal = formaro10Decimales.format(v_subTotal);
      if (v_descuentoString.trim().equals("0") || v_descuentoString.trim().equals("0.00") || v_descuentoString.trim().equals("0,00")) {
         tabla.setValueAt(formatoPrecioSubTotal, row, subTotalColumn);
      }
   }

   public static void calcularSubTotal(int row, JTable tabla) {
      int precioVentaColumn = tabla.getColumn("Precio Venta").getModelIndex();
      int descuentoColumn = tabla.getColumn("Desc.").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      String v_cantidadString = String.valueOf(tabla.getValueAt(row, cantidadColumn)).replace(".", "").replace(",", ".");
      String v_descuentoString = String.valueOf(tabla.getValueAt(row, descuentoColumn)).replace(".", "").replace(",", ".");
      String v_precioVentaString = String.valueOf(tabla.getValueAt(row, precioVentaColumn)).replace(".", "").replace(",", ".");
      BigDecimal v_cantidad = new BigDecimal(v_cantidadString);
      BigDecimal v_precioVenta = new BigDecimal(v_precioVentaString);
      BigDecimal v_subTotal = BigDecimal.ZERO;
      v_subTotal = v_cantidad.multiply(v_precioVenta).setScale(16, RoundingMode.DOWN);
      DecimalFormat formaro10Decimales = new DecimalFormat("#,##0.0000000000000000");
      String formatoPrecioSubTotal = formaro10Decimales.format(v_subTotal);
      if (v_descuentoString.trim().equals("0") || v_descuentoString.trim().equals("0.00") || v_descuentoString.trim().equals("0,00")) {
         tabla.setValueAt(formatoPrecioSubTotal, row, subTotalColumn);
      }
   }

   public static void calcularDescuento(int row, JTable tabla) {
      int precioVtaColumn = tabla.getColumn("Precio Venta").getModelIndex();
      int precioListaColumn = tabla.getColumn("Precio Lista").getModelIndex();
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int descuentoColumn = tabla.getColumn("Desc.").getModelIndex();
      String v_precioListaString = String.valueOf(tabla.getValueAt(row, precioListaColumn)).replace(".", "").replace(",", ".");
      String v_cantidadString = String.valueOf(tabla.getValueAt(row, cantidadColumn)).replace(".", "").replace(",", ".");
      String v_descuentoString = String.valueOf(tabla.getValueAt(row, descuentoColumn)).replace(".", "").replace(",", ".");
      BigDecimal v_cantidad = new BigDecimal(v_cantidadString);
      BigDecimal v_descuento = new BigDecimal(v_descuentoString);
      BigDecimal v_precioLista = new BigDecimal(v_precioListaString);
      BigDecimal v_precioVta = BigDecimal.ZERO;
      BigDecimal v_subTotal = BigDecimal.ZERO;
      v_precioVta = v_precioLista.multiply(v_descuento).setScale(16, RoundingMode.DOWN);
      v_precioVta = v_precioVta.divide(new BigDecimal(100)).setScale(16, RoundingMode.DOWN);
      v_precioVta = v_precioLista.subtract(v_precioVta);
      v_subTotal = v_cantidad.multiply(v_precioVta).setScale(16, RoundingMode.DOWN);
      DecimalFormat formaro10Decimales = new DecimalFormat("#,##0.0000000000000000");
      String formatoPrecioSubTotal = formaro10Decimales.format(v_subTotal);
      String formatoPrecioVta = formaro10Decimales.format(v_precioVta);
      if (!v_descuentoString.trim().equals("0") && !v_descuentoString.trim().equals("0.00") && !v_descuentoString.trim().equals("0,00")) {
         tabla.setValueAt(formatoPrecioVta, row, precioVtaColumn);
         tabla.setValueAt(formatoPrecioSubTotal, row, subTotalColumn);
      } else {
         int precioBrutoColumn = tabla.getColumn("Precio Bruto").getModelIndex();
         String v_precioBrutoString = String.valueOf(tabla.getValueAt(row, precioBrutoColumn)).replace(".", "").replace(",", ".");
         BigDecimal v_precioBruto = new BigDecimal(v_precioBrutoString);
         v_subTotal = v_cantidad.multiply(v_precioBruto).setScale(16, RoundingMode.DOWN);
         formatoPrecioVta = formaro10Decimales.format(v_precioBruto);
         formatoPrecioSubTotal = formaro10Decimales.format(v_subTotal);
         tabla.setValueAt(formatoPrecioVta, row, precioVtaColumn);
         tabla.setValueAt(formatoPrecioSubTotal, row, subTotalColumn);
      }
   }
}
