package pruebas;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class VentanaReporteVentas2 extends JFrame {
   private JButton btnGenerarReporte;
   private JPanel panelPDF;

   public VentanaReporteVentas2() {
      this.setTitle("Reporte de Ventas");
      this.setSize(800, 600);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(3);
      this.btnGenerarReporte = new JButton("Generar y Mostrar PDF de Ventas");
      this.btnGenerarReporte.addActionListener(this::generarReporte);
      this.panelPDF = new JPanel();
      this.panelPDF.setLayout(new BoxLayout(this.panelPDF, 1));
      JScrollPane scroll = new JScrollPane(this.panelPDF);
      this.add(this.btnGenerarReporte, "North");
      this.add(scroll, "Center");
   }

   private void generarReporte(ActionEvent e) {
   }

   private void mostrarPDFEnPanel(File archivoPDF) {
      try {
         this.panelPDF.removeAll();
         PDDocument documento = PDDocument.load(archivoPDF);
         PDFRenderer renderer = new PDFRenderer(documento);

         for (int i = 0; i < documento.getNumberOfPages(); i++) {
            BufferedImage imagen = renderer.renderImageWithDPI(i, 200.0F, ImageType.RGB);
            JLabel etiqueta = new JLabel(new ImageIcon(imagen));
            etiqueta.setAlignmentX(0.5F);
            this.panelPDF.add(Box.createVerticalStrut(10));
            this.panelPDF.add(etiqueta);
         }

         documento.close();
         this.panelPDF.revalidate();
         this.panelPDF.repaint();
      } catch (IOException var7) {
         var7.printStackTrace();
         JOptionPane.showMessageDialog(this, "Error al mostrar el PDF:\n" + var7.getMessage());
      }
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new VentanaReporteVentas2().setVisible(true));
   }
}
