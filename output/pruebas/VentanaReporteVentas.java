package pruebas;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class VentanaReporteVentas extends JFrame {
   private JButton btnGenerarReporte;

   public VentanaReporteVentas() {
      this.setTitle("Reporte de Ventas");
      this.setSize(400, 150);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(3);
      this.btnGenerarReporte = new JButton("Generar PDF de Ventas");
      this.btnGenerarReporte.addActionListener(this::generarReporte);
      this.add(this.btnGenerarReporte, "Center");
   }

   private void generarReporte(ActionEvent e) {
      Connection conexion = null;

      try {
         Class.forName("com.mysql.jdbc.Driver");
         conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema2025", "root", "123");
         InputStream maestro = this.getClass().getClassLoader().getResourceAsStream("reportes/Compras.jasper");
         InputStream detalle = this.getClass().getClassLoader().getResourceAsStream("reportes/Compras_detalle.jasper");
         JasperReport subReporte = (JasperReport)JRLoader.loadObject(detalle);
         Map<String, Object> parametros = new HashMap<>();
         parametros.put("SUBREPORT_OBJ", subReporte);
         parametros.put("par_nro_registro", 0);
         parametros.put("par_cod_sucursal", 0);
         parametros.put("par_cod_proveedor", 0);
         parametros.put("par_fecha_ini", "20240101");
         parametros.put("par_fecha_fin", "20300101");
         JasperPrint jasperPrint = JasperFillManager.fillReport(maestro, parametros, conexion);
         File tempPDF = File.createTempFile("reporte_ventas_", ".pdf");
         tempPDF.deleteOnExit();
         JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(tempPDF));
         if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(tempPDF);
         }
      } catch (Exception var17) {
         var17.printStackTrace();
         JOptionPane.showMessageDialog(this, "Error al generar el reporte:\n" + var17.getMessage());
      } finally {
         try {
            if (conexion != null) {
               conexion.close();
            }
         } catch (Exception var16) {
            var16.printStackTrace();
         }
      }
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new VentanaReporteVentas().setVisible(true));
   }
}
