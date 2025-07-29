package a00Compras;

import a00pedidosProveedores.DecimalFilter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JTable;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;

public class CalculosTabla {
   public static void calcularIvas(int row, JTable tabla) {
      int totalIvaColumn = tabla.getColumn("Total Iva").getModelIndex();
      int totalGravadoColumn = tabla.getColumn("Total Gravado").getModelIndex();
      int ivaColumn = tabla.getColumn("IVA").getModelIndex();
      int gravadoColumn = tabla.getColumn("Gravado").getModelIndex();
      int regimenColumn = tabla.getColumn("REG").getModelIndex();
      int tipoFiscalColumn = tabla.getColumn("TF").getModelIndex();
      int totalExentoColumn = tabla.getColumn("Total Exento").getModelIndex();
      int subTotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      String v_sub_total = String.valueOf(tabla.getValueAt(row, subTotalColumn)).replace(".", "").replace(",", ".");
      String v_iva = String.valueOf(tabla.getValueAt(row, ivaColumn)).replace(",", ".");
      String v_regimen = String.valueOf(tabla.getValueAt(row, regimenColumn)).replace(".", "").replace(",", ".");
      BigDecimal subTotal = new BigDecimal(v_sub_total);
      if (Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn))) == 0) {
         BigDecimal totalExento = BigDecimal.ZERO;
         BigDecimal totalIva = F_calcular_impuestos.calcularIVA(
            subTotal,
            Double.parseDouble(v_iva),
            Double.parseDouble(v_regimen),
            Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn)).replace(".", "").replace(",", "."))
         );
         BigDecimal totalGravado = F_calcular_impuestos.calcularGravado(
            subTotal,
            Double.parseDouble(v_iva),
            Double.parseDouble(v_regimen),
            new BigDecimal(String.valueOf(tabla.getValueAt(row, gravadoColumn)).replace(",", ".")),
            Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn)).replace(".", "").replace(",", "."))
         );
         FormatoDecimalOperacionSistema formatoDecimal7 = new FormatoDecimalOperacionSistema(7);
         String formatoTotalGravado = formatoDecimal7.format(totalGravado);
         String formatoTotalIva = formatoDecimal7.format(totalIva);
         String formatoTotalExento = formatoDecimal7.format(totalExento);
         tabla.setValueAt(formatoTotalExento, row, totalExentoColumn);
         tabla.setValueAt(formatoTotalIva, row, totalIvaColumn);
         tabla.setValueAt(formatoTotalGravado, row, totalGravadoColumn);
      } else if (Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn))) == 1) {
         BigDecimal totalIva = BigDecimal.ZERO;
         BigDecimal totalGravado = BigDecimal.ZERO;
         BigDecimal totalExenta = F_calcular_impuestos.calcularGravado(
            subTotal,
            Double.parseDouble(v_iva),
            Double.parseDouble(v_regimen),
            new BigDecimal(String.valueOf(tabla.getValueAt(row, gravadoColumn)).replace(",", ".")),
            Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn)).replace(".", "").replace(",", "."))
         );
         FormatoDecimalOperacionSistema formatoDecimal7 = new FormatoDecimalOperacionSistema(7);
         String formatoTotalExenta = formatoDecimal7.format(totalExenta);
         String formatoTotalIva = formatoDecimal7.format(totalIva);
         String formatoTotalGravado = formatoDecimal7.format(totalGravado);
         tabla.setValueAt(formatoTotalIva, row, totalIvaColumn);
         tabla.setValueAt(formatoTotalGravado, row, totalGravadoColumn);
         tabla.setValueAt(formatoTotalExenta, row, totalExentoColumn);
      } else if (Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn))) == 2) {
         BigDecimal totalIva = BigDecimal.ZERO;
         BigDecimal totalGravadoFinal = BigDecimal.ZERO;
         BigDecimal porcentajeGravada = BigDecimal.ZERO;
         BigDecimal totalExento = BigDecimal.ZERO;
         BigDecimal totalGravadoInicial = F_calcular_impuestos.calcularGravado(
            subTotal,
            Double.parseDouble(v_iva),
            Double.parseDouble(v_regimen),
            new BigDecimal(String.valueOf(tabla.getValueAt(row, gravadoColumn)).replace(",", ".")),
            Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn)).replace(".", "").replace(",", "."))
         );
         String v_regimenString = String.valueOf(Double.parseDouble(v_regimen) / Double.parseDouble(v_iva));
         porcentajeGravada = new BigDecimal(1).subtract(new BigDecimal(v_regimenString)).setScale(2, RoundingMode.HALF_EVEN);
         totalExento = totalGravadoInicial.multiply(porcentajeGravada).setScale(7, RoundingMode.HALF_EVEN);
         porcentajeGravada = new BigDecimal(1).subtract(porcentajeGravada).setScale(2, RoundingMode.HALF_EVEN);
         totalGravadoFinal = totalGravadoInicial.multiply(porcentajeGravada).setScale(7, RoundingMode.HALF_EVEN);
         porcentajeGravada = new BigDecimal(v_iva).divide(new BigDecimal(100)).setScale(7, RoundingMode.HALF_EVEN);
         totalIva = totalGravadoFinal.multiply(porcentajeGravada).setScale(7, RoundingMode.HALF_EVEN);
         FormatoDecimalOperacionSistema formatoDecimal7 = new FormatoDecimalOperacionSistema(7);
         String formatoTotalGravado = formatoDecimal7.format(totalGravadoFinal);
         String formatoTotalIva = formatoDecimal7.format(totalIva);
         String formatoTotalExenta = formatoDecimal7.format(totalExento);
         tabla.setValueAt(formatoTotalGravado, row, totalGravadoColumn);
         tabla.setValueAt(formatoTotalExenta, row, totalExentoColumn);
         tabla.setValueAt(formatoTotalIva, row, totalIvaColumn);
      }
   }

   public static void calcularPrecioReal(int row, JTable tabla) {
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int precioBrutoColumn = tabla.getColumn("Precio Bruto").getModelIndex();
      int precioRealColumn = tabla.getColumn("Precio Real").getModelIndex();
      int subtotalColumn = tabla.getColumn("SubTotal").getModelIndex();
      int precioNetoColumn = tabla.getColumn("Precio Neto").getModelIndex();
      String v_precioNetoString = String.valueOf(tabla.getValueAt(row, precioNetoColumn)).replace(".", "").replace(",", ".");
      String v_precioRealString = String.valueOf(tabla.getValueAt(row, precioRealColumn)).replace(".", "").replace(",", ".");
      String v_sub_totalString = String.valueOf(tabla.getValueAt(row, subtotalColumn)).replace(".", "").replace(",", ".");
      String v_cantidadString = String.valueOf(tabla.getValueAt(row, cantidadColumn)).replace(".", "").replace(",", ".");
      BigDecimal precioNeto = new BigDecimal(v_precioNetoString);
      BigDecimal precioReal = new BigDecimal(v_precioRealString);
      BigDecimal subTotal = new BigDecimal(v_sub_totalString);
      BigDecimal cantidad = new BigDecimal(v_cantidadString);
      BigDecimal precioNetoReal = BigDecimal.ZERO;
      precioNetoReal = subTotal.divide(cantidad, 7, RoundingMode.HALF_EVEN);
      if (precioNeto.compareTo(BigDecimal.ZERO) != 0
         && precioReal.compareTo(BigDecimal.ZERO) != 0
         && subTotal.compareTo(BigDecimal.ZERO) != 0
         && precioNetoReal.compareTo(BigDecimal.ZERO) != 0) {
         precioReal = precioReal.divide(precioNeto, 7, RoundingMode.HALF_EVEN);
         precioReal = precioReal.multiply(precioNetoReal).setScale(7, RoundingMode.HALF_EVEN);
         FormatoDecimalOperacionSistema formatoDecimal7 = new FormatoDecimalOperacionSistema(7);
         String formatoPrecioNetoReal = formatoDecimal7.format(precioNetoReal);
         String formatoPrecioReal = formatoDecimal7.format(precioReal);
         tabla.setValueAt(formatoPrecioNetoReal, row, precioNetoColumn);
         tabla.setValueAt(formatoPrecioReal, row, precioRealColumn);
         tabla.setValueAt(formatoPrecioReal, row, precioBrutoColumn);
      } else {
         FormatoDecimalOperacionSistema formatoDecimal7 = new FormatoDecimalOperacionSistema(7);
         String formatoPrecioNetoReal = formatoDecimal7.format(precioNetoReal);
         tabla.setValueAt(formatoPrecioNetoReal, row, precioNetoColumn);
         tabla.setValueAt(formatoPrecioNetoReal, row, precioRealColumn);
         tabla.setValueAt(formatoPrecioNetoReal, row, precioBrutoColumn);
      }
   }

   public static void cargarImpuestos(
      JTable tabla,
      DecimalFilter txt_total,
      DecimalFilter txt_exentas,
      DecimalFilter txt_gravada10,
      DecimalFilter txt_iva10,
      DecimalFilter txt_gravada5,
      DecimalFilter txt_iva5,
      DecimalFilter txt_total_impuesto
   ) {
      BigDecimal subtotalGravada10 = BigDecimal.ZERO;
      BigDecimal subtotalGravada05 = BigDecimal.ZERO;
      BigDecimal totalGravado10 = BigDecimal.ZERO;
      BigDecimal totalGravado05 = BigDecimal.ZERO;
      BigDecimal subtotaliva10 = BigDecimal.ZERO;
      BigDecimal totalIva10 = BigDecimal.ZERO;
      BigDecimal subtotaliva5 = BigDecimal.ZERO;
      BigDecimal totalIva5 = BigDecimal.ZERO;
      BigDecimal subtotalExentas = BigDecimal.ZERO;
      BigDecimal totalExentas = BigDecimal.ZERO;
      BigDecimal totalGeneral = BigDecimal.ZERO;
      int tipoFiscalColumn = tabla.getColumn("TF").getModelIndex();
      int totalGravadoColumn = tabla.getColumn("Total Gravado").getModelIndex();
      int ivaColumn = tabla.getColumn("IVA").getModelIndex();
      int totalExentoColumn = tabla.getColumn("Total Exento").getModelIndex();
      int totalIvaColumn = tabla.getColumn("Total Iva").getModelIndex();

      for (int fila = 0; fila < tabla.getRowCount(); fila++) {
         if (String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn)).trim().equals("0")) {
            if (String.valueOf(tabla.getValueAt(fila, ivaColumn)).trim().equals("10.0")
               || String.valueOf(tabla.getValueAt(fila, ivaColumn)).trim().equals("10,00")) {
               subtotalGravada10 = new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalGravadoColumn)).replace(".", "").replace(",", "."));
               subtotalGravada05 = BigDecimal.ZERO;
               subtotaliva10 = new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalIvaColumn)).replace(".", "").replace(",", "."));
               subtotaliva5 = BigDecimal.ZERO;
               totalGravado10 = totalGravado10.add(subtotalGravada10).setScale(7, RoundingMode.HALF_EVEN);
               totalGravado05 = totalGravado05.add(subtotalGravada05).setScale(7, RoundingMode.HALF_EVEN);
               totalIva10 = totalIva10.add(subtotaliva10).setScale(7, RoundingMode.HALF_EVEN);
               totalIva5 = totalIva5.add(subtotaliva5).setScale(7, RoundingMode.HALF_EVEN);
            } else if (String.valueOf(tabla.getValueAt(fila, ivaColumn)).trim().equals("5.0")
               || String.valueOf(tabla.getValueAt(fila, ivaColumn)).trim().equals("5,00")) {
               subtotalGravada05 = new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalGravadoColumn)).replace(".", "").replace(",", "."));
               subtotalGravada10 = BigDecimal.ZERO;
               subtotaliva5 = new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalIvaColumn)).replace(".", "").replace(",", "."));
               subtotaliva10 = BigDecimal.ZERO;
               totalGravado10 = totalGravado10.add(subtotalGravada10).setScale(7, RoundingMode.HALF_EVEN);
               totalGravado05 = totalGravado05.add(subtotalGravada05).setScale(7, RoundingMode.HALF_EVEN);
               totalIva10 = totalIva10.add(subtotaliva10).setScale(7, RoundingMode.HALF_EVEN);
               totalIva5 = totalIva5.add(subtotaliva5).setScale(7, RoundingMode.HALF_EVEN);
            }
         } else if (String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn)).trim().equals("1")) {
            subtotalExentas = new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalExentoColumn)).replace(".", "").replace(",", "."));
            totalExentas = totalExentas.add(subtotalExentas).setScale(7, RoundingMode.HALF_EVEN);
         } else {
            String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn)).trim().equals("3");
         }
      }

      totalGeneral = totalGeneral.add(totalExentas).setScale(7, RoundingMode.HALF_EVEN);
      totalGeneral = totalGeneral.add(totalGravado10).setScale(7, RoundingMode.HALF_EVEN);
      totalGeneral = totalGeneral.add(totalIva10).setScale(7, RoundingMode.HALF_EVEN);
      totalGeneral = totalGeneral.add(totalGravado05).setScale(7, RoundingMode.HALF_EVEN);
      totalGeneral = totalGeneral.add(totalIva5).setScale(7, RoundingMode.HALF_EVEN);
      txt_exentas.setValue(totalExentas);
      txt_gravada10.setValue(totalGravado10);
      txt_iva10.setValue(totalIva10);
      txt_gravada5.setValue(totalGravado05);
      txt_iva5.setValue(totalIva5);
      txt_total_impuesto.setValue(totalGeneral);
   }
}
