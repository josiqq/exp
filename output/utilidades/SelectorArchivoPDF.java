package utilidades;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SelectorArchivoPDF extends JFileChooser {
   private static final long serialVersionUID = 1L;

   public SelectorArchivoPDF(int tipo) {
      UIManager.put("FileChooser.saveButtonText", "Guardar");
      UIManager.put("FileChooser.cancelButtonText", "Cancelar");
      UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
      UIManager.put("FileChooser.filesOfTypeLabelText", "Tipo de archivo");
      UIManager.put("FileChooser.saveDialogTitleText", "Guardar como");
      UIManager.put("FileChooser.openDialogTitleText", "Abrir");
      UIManager.put("FileChooser.lookInLabelText", "Buscar en");
      UIManager.put("FileChooser.upFolderToolTipText", "Subir un nivel");
      UIManager.put("FileChooser.homeFolderToolTipText", "Escritorio");
      UIManager.put("FileChooser.newFolderToolTipText", "Nueva carpeta");
      if (tipo == 0) {
         this.setDialogTitle("Guardar reporte como PDF");
         this.setFileFilter(new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf"));
         this.setSelectedFile(new File("reporte_generado.pdf"));
      } else if (tipo == 1) {
         this.setDialogTitle("Guardar reporte como Excel");
         this.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));
         this.setSelectedFile(new File("reporte_generado.xlsx"));
      }
   }
}
