package utilidadesVentanas;

import a1Login.LoginForm;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import utilidades.PanelPadre;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;

public class JInternalPadreReporteJasper extends JInternalFrame {
   protected PDDocument documento;
   protected PDFRenderer renderer;
   protected int totalPaginas;
   private static final long serialVersionUID = 1L;
   protected JasperPrint jasperPrint;
   public File archivoDestino;
   protected float zoomFactor = 0.7F;
   protected File archivoPDFTemporal;
   protected JPanel panelPDFGlobal;

   public JInternalPadreReporteJasper() {
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setFrameIcon(iconoVentana);
      this.getContentPane().setBackground(new Color(210, 210, 210));
      this.addEscapeKey();
      this.addInternalFrameListener(new InternalFrameAdapter() {
         @Override
         public void internalFrameClosing(InternalFrameEvent e) {
            JInternalPadreReporteJasper.this.confirmarSalida();
         }
      });
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

   protected void aumentarZoom(PanelPadre panelPaginas) {
      this.zoomFactor += 0.1F;
      this.cargarTodasLasPaginas(panelPaginas);
   }

   protected void disminuirZoom(PanelPadre panelPaginas) {
      this.zoomFactor = Math.max(0.1F, this.zoomFactor - 0.1F);
      this.cargarTodasLasPaginas(panelPaginas);
   }

   protected void cargarTodasLasPaginas(PanelPadre panelPaginas) {
      try {
         float baseDPI = 150.0F;
         float currentDPI = baseDPI * this.zoomFactor;
         panelPaginas.removeAll();

         for (int i = 0; i < this.totalPaginas; i++) {
            final BufferedImage imagen = this.renderer.renderImageWithDPI(i, currentDPI);
            JPanel panelPagina = new JPanel() {
               private static final long serialVersionUID = 1L;

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
            panelPaginas.add(panelPagina);
            panelPaginas.add(Box.createVerticalStrut(15));
         }

         panelPaginas.revalidate();
         panelPaginas.repaint();
      } catch (IOException var7) {
         var7.printStackTrace();
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Error cargando páginas: " + var7.getMessage(), "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      }
   }

   public void recuperar() {
      DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe evento para esta ventana", "error");
      rs.setLocationRelativeTo(this);
      rs.setVisible(true);
   }

   private void addEscapeKey() {
      KeyStroke escape = KeyStroke.getKeyStroke(27, 0, false);
      Action escapeAction = new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            JInternalPadreReporteJasper.this.confirmarSalida();
         }
      };
      this.getRootPane().getInputMap(2).put(escape, "ESCAPE");
      this.getRootPane().getActionMap().put("ESCAPE", escapeAction);
   }

   private void confirmarSalida() {
      DialogoMessagebox d = new DialogoMessagebox("Desea cerrar la ventana ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         this.dispose();
      }
   }

   public void exportarPDF() {
      DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe evento para esta ventana", "error");
      rs.setLocationRelativeTo(this);
      rs.setVisible(true);
   }

   public void exportarExcel() {
      DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe evento para esta ventana", "error");
      rs.setLocationRelativeTo(this);
      rs.setVisible(true);
   }

   public void imprimirPDF() {
      DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe evento para esta ventana", "error");
      rs.setLocationRelativeTo(this);
      rs.setVisible(true);
   }

   protected void generarReporte(JPanel panel, String archivoCab, String archivoDet, Map<String, Object> parametros, String archivoTMP) {
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         InputStream maestro = this.getClass().getClassLoader().getResourceAsStream(archivoCab);
         this.jasperPrint = JasperFillManager.fillReport(maestro, parametros, conexion);
         if (this.jasperPrint.getPages().isEmpty()) {
            this.jasperPrint = null;
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No hay datos para mostrar en el reporte con los filtros seleccionados.", "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            return;
         }

         this.archivoPDFTemporal = File.createTempFile(archivoTMP, ".pdf");
         this.archivoPDFTemporal.deleteOnExit();
         JasperExportManager.exportReportToPdfStream(this.jasperPrint, new FileOutputStream(this.archivoPDFTemporal));
         this.panelPDFGlobal = panel;
         this.mostrarPDFEnPanel(this.panelPDFGlobal, this.archivoPDFTemporal, this.zoomFactor);
      } catch (Exception var10) {
         var10.printStackTrace();
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Error al generar el reporte:\n" + var10.getMessage(), "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      }
   }

   public void mostrarPDFEnPanel(JPanel panelDestino, File archivoPDF, float zoomFactor) {
      try {
         panelDestino.removeAll();
         panelDestino.setLayout(new BoxLayout(panelDestino, 1));
         panelDestino.setBackground(Color.WHITE);
         PDDocument documento = PDDocument.load(archivoPDF);
         PDFRenderer renderer = new PDFRenderer(documento);
         float dpi = 150.0F * zoomFactor;

         for (int i = 0; i < documento.getNumberOfPages(); i++) {
            final BufferedImage imagen = renderer.renderImageWithDPI(i, dpi, ImageType.RGB);
            JPanel panelPagina = new JPanel() {
               private static final long serialVersionUID = 1L;

               @Override
               protected void paintComponent(Graphics g) {
                  super.paintComponent(g);
                  Graphics2D g2d = (Graphics2D)g;
                  g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                  g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                  g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                  g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                  g2d.drawImage(imagen, 0, 0, this);
               }
            };
            panelPagina.setPreferredSize(new Dimension(imagen.getWidth(), imagen.getHeight()));
            panelPagina.setBackground(Color.WHITE);
            panelPagina.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelDestino.add(Box.createVerticalStrut(10));
            panelDestino.add(panelPagina);
         }

         documento.close();
         panelDestino.revalidate();
         panelDestino.repaint();
      } catch (IOException var10) {
         var10.printStackTrace();
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Error al mostrar el PDF: ", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      }
   }
}
