package a0099ListaPrecios;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import javax.swing.JTable;

public class CopiarFilasFormatoWhatsAppNegrita {
   public static void copiarFilasSeleccionadasAlPortapapeles(JTable tabla) {
      int[] filasSeleccionadas = tabla.getSelectedRows();
      if (filasSeleccionadas.length == 0) {
         System.out.println("⚠️ No hay filas seleccionadas.");
      } else {
         StringBuilder sb = new StringBuilder();

         for (int fila : filasSeleccionadas) {
            for (int col = 0; col < tabla.getColumnCount(); col++) {
               String nombreColumna = tabla.getColumnName(col);
               Object valorCelda = tabla.getValueAt(fila, col);
               sb.append("*").append(nombreColumna).append(":* ").append(valorCelda != null ? valorCelda.toString() : "").append("\n");
            }

            sb.append("-----\n");
         }

         StringSelection seleccion = new StringSelection(sb.toString());
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(seleccion, null);
         System.out.println("✅ Copiado al portapapeles con formato enriquecido para WhatsApp.");
      }
   }
}
