package reportesStock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class VisorPDFMultipaginaScroll extends JFrame {
   private PDDocument documento;
   private PDFRenderer renderer;
   private int totalPaginas;
   private JPanel panelPaginas;
   private JScrollPane scrollPane;

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public VisorPDFMultipaginaScroll() throws IOException {
      super("Visor PDF Multipágina Scroll");

      try {
         InputStream reporteMaestroStream = this.getClass().getResourceAsStream("/reportesStock/AjusteStock.jasper");
         if (reporteMaestroStream == null) {
            throw new RuntimeException("No se encontró AjusteStock.jasper en /reportesStock");
         }

         Map<String, Object> parametros = new HashMap<>();
         parametros.put("nro_registro", 0);
         String subreportDir = this.getClass().getResource("/reportesStock/").getPath();
         parametros.put("SUBREPORT_DIR", subreportDir);
         parametros.put("par_fecha_ini", "20200101");
         parametros.put("par_fecha_fin", "20260101");
         parametros.put("par_motivo", 0);
         parametros.put("par_deposito", 0);
         new JREmptyDataSource();
         String url = "jdbc:mysql://localhost:3306/sistema2025";
         String usuario = "root";
         String password = "123";
         JasperPrint jasperPrint = null;

         try {
            Throwable rutaPaquete = null;
            Object archivoPDF = null;

            try {
               Connection conn = DriverManager.getConnection(url, usuario, password);

               try {
                  jasperPrint = JasperFillManager.fillReport(reporteMaestroStream, parametros, conn);
               } finally {
                  if (conn != null) {
                     conn.close();
                  }
               }
            } catch (Throwable var21) {
               if (rutaPaquete == null) {
                  rutaPaquete = var21;
               } else if (rutaPaquete != var21) {
                  rutaPaquete.addSuppressed(var21);
               }

               throw rutaPaquete;
            }
         } catch (SQLException var22) {
            var22.printStackTrace();
         }

         String rutaPaquete = this.getClass().getResource("/reportesStock/").getPath();
         String archivoPDF = rutaPaquete + "ajuste.pdf";
         JasperExportManager.exportReportToPdfFile(jasperPrint, archivoPDF);
         System.out.println("PDF generado en: " + archivoPDF);
      } catch (JRException var23) {
         var23.printStackTrace();
         System.err.println("Error generando PDF: " + var23.getMessage());
      }

      InputStream pdfStream = this.getClass().getResourceAsStream("/reportesStock/ajuste.pdf");
      if (pdfStream == null) {
         throw new IOException("No se encontró el recurso PDF: /reportesStock/ajuste.pdf");
      } else {
         this.documento = PDDocument.load(pdfStream);
         this.renderer = new PDFRenderer(this.documento);
         this.totalPaginas = this.documento.getNumberOfPages();
         this.initComponents();
         this.cargarTodasLasPaginas();
         this.setDefaultCloseOperation(3);
         this.setSize(800, 600);
         this.setLocationRelativeTo(null);
      }
   }

   private void initComponents() {
      this.panelPaginas = new JPanel();
      this.panelPaginas.setLayout(new BoxLayout(this.panelPaginas, 1));
      this.panelPaginas.setBackground(Color.DARK_GRAY);
      this.scrollPane = new JScrollPane(this.panelPaginas);
      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(this.scrollPane, "Center");
   }

   private void cargarTodasLasPaginas() {
      try {
         for (int i = 0; i < this.totalPaginas; i++) {
            final BufferedImage imagen = this.renderer.renderImageWithDPI(i, 200.0F);
            JPanel panelPagina = new JPanel() {
               @Override
               protected void paintComponent(Graphics g) {
                  super.paintComponent(g);
                  Graphics2D g2d = (Graphics2D)g;
                  g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                  g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                  g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                  g2d.drawImage(imagen, 0, 0, this);
               }
            };
            panelPagina.setPreferredSize(new Dimension(imagen.getWidth(), imagen.getHeight()));
            panelPagina.setMaximumSize(new Dimension(imagen.getWidth(), imagen.getHeight()));
            panelPagina.setBackground(Color.WHITE);
            panelPagina.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.panelPaginas.add(panelPagina);
            this.panelPaginas.add(Box.createVerticalStrut(15));
         }

         this.panelPaginas.revalidate();
         this.panelPaginas.repaint();
      } catch (IOException var4) {
         var4.printStackTrace();
         JOptionPane.showMessageDialog(this, "Error cargando páginas: " + var4.getMessage(), "Error", 0);
      }
   }

   public void cerrarDocumento() {
      try {
         if (this.documento != null) {
            this.documento.close();
         }
      } catch (IOException var2) {
         var2.printStackTrace();
      }
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         try {
            VisorPDFMultipaginaScroll visor = new VisorPDFMultipaginaScroll();
            visor.setVisible(true);
         } catch (IOException var1) {
            var1.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo abrir el PDF: " + var1.getMessage(), "Error", 0);
         }
      });
   }
}
