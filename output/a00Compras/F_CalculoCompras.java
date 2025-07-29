package a00Compras;

import java.math.BigDecimal;
import javax.swing.JTable;
import utilidadesOperaciones.F_OperacionesSistema;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;

public class F_CalculoCompras {
   public static void calcularSubTotal(int row, JTable tabla) {
      int precioBrutoColumn = tabla.getColumn("Precio Bruto").getModelIndex();
      int precioRealColumn = tabla.getColumn("Precio Real").getModelIndex();
      int precioNetoColumn = tabla.getColumn("Precio Neto").getModelIndex();
      int descuentoColumn = tabla.getColumn("Desc.").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      String v_cantidadString = String.valueOf(tabla.getValueAt(row, cantidadColumn)).replace(".", "").replace(",", ".");
      String v_descuentoString = String.valueOf(tabla.getValueAt(row, descuentoColumn)).replace(".", "").replace(",", ".");
      String v_precioBrutoString = String.valueOf(tabla.getValueAt(row, precioBrutoColumn)).replace(".", "").replace(",", ".");
      BigDecimal v_cantidad = new BigDecimal(v_cantidadString);
      BigDecimal v_precioBruto = new BigDecimal(v_precioBrutoString);
      BigDecimal v_subTotal = BigDecimal.ZERO;
      v_subTotal = F_OperacionesSistema.multiplicar(v_cantidad, v_precioBruto);
      FormatoDecimalOperacionSistema formatoDecimal7 = new FormatoDecimalOperacionSistema(7);
      String formatoPrecioSubTotal = formatoDecimal7.format(v_subTotal);
      String formatoPrecioBruto = formatoDecimal7.format(v_precioBruto);
      if (v_descuentoString.trim().equals("0")) {
         tabla.setValueAt(formatoPrecioBruto, row, precioNetoColumn);
         tabla.setValueAt(formatoPrecioBruto, row, precioRealColumn);
         tabla.setValueAt(formatoPrecioSubTotal, row, subTotalColumn);
      } else {
         tabla.setValueAt(formatoPrecioBruto, row, precioRealColumn);
         calcularPrecioNeto(row, tabla);
      }
   }

   public static void calcularPrecioNeto(int row, JTable tabla) {
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int precioBrutoColumn = tabla.getColumn("Precio Bruto").getModelIndex();
      int precioNetoColumn = tabla.getColumn("Precio Neto").getModelIndex();
      int descuentoColumn = tabla.getColumn("Desc.").getModelIndex();
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      String v_precioBrutoString = String.valueOf(tabla.getValueAt(row, precioBrutoColumn)).replace(".", "").replace(",", ".");
      String v_descuentoString = String.valueOf(tabla.getValueAt(row, descuentoColumn)).replace(".", "").replace(",", ".");
      String v_cantidadString = String.valueOf(tabla.getValueAt(row, cantidadColumn)).replace(".", "").replace(",", ".");
      BigDecimal v_precioBruto = new BigDecimal(v_precioBrutoString);
      BigDecimal v_Descuento = new BigDecimal(v_descuentoString);
      BigDecimal v_cantidad = new BigDecimal(v_cantidadString);
      BigDecimal v_resultadoNeto = BigDecimal.ZERO;
      BigDecimal v_resultadoSubTotal = BigDecimal.ZERO;
      v_resultadoNeto = F_OperacionesSistema.multiplicar(v_precioBruto, v_Descuento);
      v_resultadoNeto = F_OperacionesSistema.dividir(v_resultadoNeto, new BigDecimal(100));
      v_resultadoNeto = F_OperacionesSistema.restar(v_precioBruto, v_resultadoNeto);
      FormatoDecimalOperacionSistema formatoDecimal7 = new FormatoDecimalOperacionSistema(7);
      v_resultadoSubTotal = F_OperacionesSistema.multiplicar(v_resultadoNeto, v_cantidad);
      String formatoPrecioNeto = formatoDecimal7.format(v_resultadoNeto);
      String formatoSubTotal = formatoDecimal7.format(v_resultadoSubTotal);
      tabla.setValueAt(formatoPrecioNeto, row, precioNetoColumn);
      tabla.setValueAt(formatoSubTotal, row, subTotalColumn);
   }
}
