package utilidadesTabla;

import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelExporter {
   public void exportarJTableConDialogo(final JTable table) {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Guardar archivo Excel");
      fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xls)", "xls"));
      final String finalFilePath = null;

      while (true) {
         int seleccion = fileChooser.showSaveDialog(null);
         if (seleccion != 0) {
            return;
         }

         File archivoSeleccionado = fileChooser.getSelectedFile();
         String filePath = archivoSeleccionado.getAbsolutePath();
         if (!filePath.toLowerCase().endsWith(".xls")) {
            filePath = filePath + ".xls";
         }

         File archivoDestino = new File(filePath);
         if (archivoDestino.exists()) {
            int respuesta = JOptionPane.showConfirmDialog(
               null, "El archivo ya existe:\n" + archivoDestino.getName() + "\n¿Deseás reemplazarlo?", "Confirmar reemplazo", 0, 2
            );
            if (respuesta != 0) {
               continue;
            }

            finalFilePath = filePath;
         } else {
            finalFilePath = filePath;
         }

         final JDialog progressDialog = new JDialog(null, "Exportando...", true);
         JProgressBar progressBar = new JProgressBar();
         progressBar.setIndeterminate(true);
         progressBar.setString("Exportando a Excel...");
         progressBar.setStringPainted(true);
         progressDialog.add(progressBar);
         progressDialog.setSize(300, 80);
         progressDialog.setLocationRelativeTo(null);
         SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
               ExcelExporter.this.exportarJTableAXLS(table, finalFilePath);
               return null;
            }

            @Override
            protected void done() {
               progressDialog.dispose();
               JOptionPane.showMessageDialog(null, "Exportación exitosa a:\n" + finalFilePath, "Éxito", 1);
            }
         };
         worker.execute();
         progressDialog.setVisible(true);
         return;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void exportarJTableAXLS(JTable table, String filePath) {
      try {
         Throwable e = null;
         Object var4 = null;

         try {
            Workbook workbook = new HSSFWorkbook();

            try {
               Sheet sheet = workbook.createSheet("Datos");
               Row headerRow = sheet.createRow(0);

               for (int i = 0; i < table.getColumnCount(); i++) {
                  Cell cell = headerRow.createCell(i);
                  cell.setCellValue(table.getColumnName(i));
               }

               for (int row = 0; row < table.getRowCount(); row++) {
                  Row excelRow = sheet.createRow(row + 1);

                  for (int col = 0; col < table.getColumnCount(); col++) {
                     Object value = table.getValueAt(row, col);
                     excelRow.createCell(col).setCellValue(value != null ? value.toString() : "");
                  }
               }

               Throwable var38 = null;
               Object var40 = null;

               try {
                  FileOutputStream out = new FileOutputStream(filePath);

                  try {
                     workbook.write(out);
                  } finally {
                     if (out != null) {
                        out.close();
                     }
                  }
               } catch (Throwable var33) {
                  if (var38 == null) {
                     var38 = var33;
                  } else if (var38 != var33) {
                     var38.addSuppressed(var33);
                  }

                  throw var38;
               }
            } finally {
               if (workbook != null) {
                  workbook.close();
               }
            }
         } catch (Throwable var35) {
            if (e == null) {
               e = var35;
            } else if (e != var35) {
               e.addSuppressed(var35);
            }

            throw e;
         }
      } catch (Exception var36) {
         var36.printStackTrace();
         JOptionPane.showMessageDialog(null, "Error durante la exportación:\n" + var36.getMessage(), "Error", 0);
      }
   }
}
