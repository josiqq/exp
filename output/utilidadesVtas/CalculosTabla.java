package utilidadesVtas;

import a00pedidosProveedores.DecimalFilter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JTable;

public class CalculosTabla {
   public static void calcularIvas(int row, JTable tabla, int tipo_fiscal_cliente) {
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
      if (Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn))) != 0
         && (Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn))) != 2 || tipo_fiscal_cliente != 0)) {
         if (Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn))) == 1
            || Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn))) == 2 && tipo_fiscal_cliente == 2) {
            BigDecimal totalIva = BigDecimal.ZERO;
            BigDecimal totalGravado = BigDecimal.ZERO;
            BigDecimal totalExenta = F_calcular_impuestosVta.calcularGravado(
               subTotal,
               Double.parseDouble(v_iva),
               Double.parseDouble(v_regimen),
               new BigDecimal(String.valueOf(tabla.getValueAt(row, gravadoColumn)).replace(",", ".")),
               Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn)).replace(".", "").replace(",", ".")),
               tipo_fiscal_cliente
            );
            DecimalFormat formaro16Decimales = new DecimalFormat("#,##0.0000000000000000000000");
            String formatoTotalExenta = formaro16Decimales.format(totalExenta);
            String formatoTotalIva = formaro16Decimales.format(totalIva);
            String formatoTotalGravado = formaro16Decimales.format(totalGravado);
            tabla.setValueAt(formatoTotalIva, row, totalIvaColumn);
            tabla.setValueAt(formatoTotalGravado, row, totalGravadoColumn);
            tabla.setValueAt(formatoTotalExenta, row, totalExentoColumn);
         }
      } else {
         BigDecimal totalExento = BigDecimal.ZERO;
         BigDecimal totalIva = F_calcular_impuestosVta.calcularIVA(
            subTotal,
            Double.parseDouble(v_iva),
            Double.parseDouble(v_regimen),
            Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn)).replace(".", "").replace(",", ".")),
            tipo_fiscal_cliente
         );
         BigDecimal totalGravado = F_calcular_impuestosVta.calcularGravado(
            subTotal,
            Double.parseDouble(v_iva),
            Double.parseDouble(v_regimen),
            new BigDecimal(String.valueOf(tabla.getValueAt(row, gravadoColumn)).replace(",", ".")),
            Integer.parseInt(String.valueOf(tabla.getValueAt(row, tipoFiscalColumn)).replace(".", "").replace(",", ".")),
            tipo_fiscal_cliente
         );
         DecimalFormat formaro10Decimales = new DecimalFormat("#,##0.0000000000000000");
         String formatoTotalGravado = formaro10Decimales.format(totalGravado);
         String formatoTotalIva = formaro10Decimales.format(totalIva);
         String formatoTotalExento = formaro10Decimales.format(totalExento);
         tabla.setValueAt(formatoTotalExento, row, totalExentoColumn);
         tabla.setValueAt(formatoTotalIva, row, totalIvaColumn);
         tabla.setValueAt(formatoTotalGravado, row, totalGravadoColumn);
      }
   }

   public static void cargarImpuestos(
      JTable tabla,
      DecimalFilter txt_exentas,
      DecimalFilter txt_gravada10,
      DecimalFilter txt_iva10,
      DecimalFilter txt_gravada5,
      DecimalFilter txt_iva5,
      DecimalFilter txt_total_impuesto,
      int par_tipo_fiscal_cliente
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
      System.out.println("POLLO191211: " + par_tipo_fiscal_cliente);
      int var25 = 0;

      for (int fila = 0; fila < tabla.getRowCount(); fila++) {
         if (String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn)).trim().equals("0") && var25 == 0
            || String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn)).trim().equals("2") && var25 == 0
            || String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn)).trim().equals("0") && var25 == 2) {
            if (String.valueOf(tabla.getValueAt(fila, ivaColumn)).trim().equals("10.0")
               || String.valueOf(tabla.getValueAt(fila, ivaColumn)).trim().equals("10,00")) {
               subtotalGravada10 = new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalGravadoColumn)).replace(".", "").replace(",", "."));
               subtotalGravada05 = BigDecimal.ZERO;
               subtotaliva10 = new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalIvaColumn)).replace(".", "").replace(",", "."));
               subtotaliva5 = BigDecimal.ZERO;
               totalGravado10 = totalGravado10.add(subtotalGravada10).setScale(16, RoundingMode.DOWN);
               totalGravado05 = totalGravado05.add(subtotalGravada05).setScale(16, RoundingMode.DOWN);
               totalIva10 = totalIva10.add(subtotaliva10).setScale(16, RoundingMode.DOWN);
               totalIva5 = totalIva5.add(subtotaliva5).setScale(16, RoundingMode.DOWN);
            } else if (String.valueOf(tabla.getValueAt(fila, ivaColumn)).trim().equals("5.0")
               || String.valueOf(tabla.getValueAt(fila, ivaColumn)).trim().equals("5,00")) {
               subtotalGravada05 = new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalGravadoColumn)).replace(".", "").replace(",", "."));
               subtotalGravada10 = BigDecimal.ZERO;
               subtotaliva5 = new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalIvaColumn)).replace(".", "").replace(",", "."));
               subtotaliva10 = BigDecimal.ZERO;
               totalGravado10 = totalGravado10.add(subtotalGravada10).setScale(16, RoundingMode.DOWN);
               totalGravado05 = totalGravado05.add(subtotalGravada05).setScale(16, RoundingMode.DOWN);
               totalIva10 = totalIva10.add(subtotaliva10).setScale(16, RoundingMode.DOWN);
               totalIva5 = totalIva5.add(subtotaliva5).setScale(16, RoundingMode.DOWN);
            }
         } else if (!String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn)).trim().equals("1")
            && !String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn)).trim().equals("2")) {
            String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn)).trim().equals("3");
         } else {
            subtotalExentas = new BigDecimal(String.valueOf(tabla.getValueAt(fila, totalExentoColumn)).replace(".", "").replace(",", "."));
            totalExentas = totalExentas.add(subtotalExentas).setScale(16, RoundingMode.DOWN);
         }
      }

      totalGeneral = totalGeneral.add(totalExentas).setScale(16, RoundingMode.DOWN);
      totalGeneral = totalGeneral.add(totalGravado10).setScale(16, RoundingMode.DOWN);
      totalGeneral = totalGeneral.add(totalIva10).setScale(16, RoundingMode.DOWN);
      totalGeneral = totalGeneral.add(totalGravado05).setScale(16, RoundingMode.DOWN);
      totalGeneral = totalGeneral.add(totalIva5).setScale(16, RoundingMode.DOWN);
      txt_exentas.setValue(totalExentas);
      txt_gravada10.setValue(totalGravado10);
      txt_iva10.setValue(totalIva10);
      txt_gravada5.setValue(totalGravado05);
      txt_iva5.setValue(totalIva5);
      txt_total_impuesto.setValue(totalGeneral);
   }
}
