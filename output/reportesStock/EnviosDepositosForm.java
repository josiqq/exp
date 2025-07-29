package reportesStock;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import utilidades.BuscarIni;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQLJasper;
import utilidades.PanelPadre;
import utilidades.RadioBoton;
import utilidades.SelectorArchivoPDF;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JInternalPadreReporteJasper;

public class EnviosDepositosForm extends JInternalPadreReporteJasper {
   private PanelPadre panelPaginas;
   private static final long serialVersionUID = 1L;
   private JDateChooser txt_fecha_ini;
   private LimiteTextFieldConSQLJasper txt_cod_deposito_origen;
   private LimiteTextFieldConSQLJasper txt_cod_deposito_destino;
   private LabelPrincipal lblNombreDepositoOrigen;
   private LabelPrincipal lblNombreDepositoDestino;
   private JDateChooser txt_fecha_final;
   private RadioBoton rdbtnTodos;
   private RadioBoton rdbtnRecibidos;
   private RadioBoton rdbtnPendientes;

   private void inicializarObjetos() {
      this.txt_cod_deposito_origen.setValue(0);
      this.lblNombreDepositoOrigen.setText("");
      this.txt_cod_deposito_destino.setValue(0);
      this.lblNombreDepositoDestino.setText("");
   }

   public EnviosDepositosForm() {
      this.setTitle("Reporte de Envio de Depositos");
      this.setBounds(100, 100, 1252, 617);
      PanelPadre contentPane = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      this.panelPaginas = new PanelPadre("");
      PanelPadre panel_filtro = new PanelPadre("Filtro de Reporte");
      PanelPadre panel_zoom = new PanelPadre("");
      PanelPadre panel_opciones = new PanelPadre("");
      LabelPrincipal lblCodigoDepositoDestino = new LabelPrincipal("Deposito Destino", "label");
      LabelPrincipal lblFechaIni = new LabelPrincipal("Fecha Inicio", "label");
      LabelPrincipal lblDepositoOrigen = new LabelPrincipal("Deposito Origen", "label");
      LabelPrincipal lblFechaFin = new LabelPrincipal("Fecha Final", "label");
      this.lblNombreDepositoDestino = new LabelPrincipal(0);
      this.lblNombreDepositoOrigen = new LabelPrincipal(0);
      this.txt_cod_deposito_destino = new LimiteTextFieldConSQLJasper(999999, this.lblNombreDepositoDestino, "Depositos", this);
      this.txt_fecha_ini = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_final = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_cod_deposito_origen = new LimiteTextFieldConSQLJasper(999999, this.lblNombreDepositoOrigen, "MotivosAjuste", this);
      this.rdbtnTodos = new RadioBoton("Todos");
      this.rdbtnRecibidos = new RadioBoton("Recibidos");
      this.rdbtnPendientes = new RadioBoton("Pendientes");
      ButtonGroup button = new ButtonGroup();
      button.add(this.rdbtnPendientes);
      button.add(this.rdbtnRecibidos);
      button.add(this.rdbtnTodos);
      this.rdbtnTodos.setSelected(true);
      JButton btnAumentar = new JButton("+");
      btnAumentar.addActionListener(e -> this.aumentarZoom(this.panelPaginas));
      JButton btnDisminuir = new JButton("-");
      btnDisminuir.addActionListener(e -> this.disminuirZoom(this.panelPaginas));
      this.panelPaginas.setLayout(new BoxLayout(this.panelPaginas, 1));
      this.panelPaginas.setBackground(Color.white);
      JScrollPane scrollPane = new JScrollPane(this.panelPaginas);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                        .addGroup(gl_contentPane.createSequentialGroup().addGap(10).addComponent(scrollPane))
                        .addComponent(panel_filtro, Alignment.LEADING, -2, 1234, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_filtro, -2, 64, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPane, -2, 507, -2)
            )
      );
      GroupLayout gl_panel_opciones = new GroupLayout(panel_opciones);
      gl_panel_opciones.setHorizontalGroup(
         gl_panel_opciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING,
               gl_panel_opciones.createSequentialGroup()
                  .addContainerGap(38, 32767)
                  .addComponent(this.rdbtnTodos, -2, 86, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.rdbtnRecibidos, -2, 109, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.rdbtnPendientes, -2, 112, -2)
                  .addContainerGap()
            )
      );
      gl_panel_opciones.setVerticalGroup(
         gl_panel_opciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_opciones.createSequentialGroup()
                  .addGroup(
                     gl_panel_opciones.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.rdbtnPendientes, -2, 16, -2)
                        .addComponent(this.rdbtnRecibidos, -2, 16, -2)
                        .addComponent(this.rdbtnTodos, -2, 16, -2)
                  )
                  .addContainerGap(10, 32767)
            )
      );
      panel_opciones.setLayout(gl_panel_opciones);
      GroupLayout gl_panel_filtro = new GroupLayout(panel_filtro);
      gl_panel_filtro.setHorizontalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, gl_panel_filtro.createSequentialGroup().addGap(473).addComponent(panel_opciones, -1, -1, 32767))
                        .addGroup(
                           Alignment.LEADING,
                           gl_panel_filtro.createSequentialGroup()
                              .addContainerGap()
                              .addComponent(lblFechaIni, -2, 77, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_fecha_ini, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblFechaFin, -2, 77, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_fecha_final, -2, 94, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_zoom, -2, 99, -2)
                              .addGap(18)
                              .addComponent(lblDepositoOrigen, -2, 101, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_deposito_origen, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreDepositoOrigen, -2, 200, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblCodigoDepositoDestino, -2, 110, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cod_deposito_destino, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreDepositoDestino, -2, 228, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      gl_panel_filtro.setVerticalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblFechaIni, -2, 25, -2)
                        .addComponent(this.txt_fecha_ini, -2, 25, -2)
                        .addComponent(lblFechaFin, -2, 25, -2)
                        .addComponent(this.txt_fecha_final, -2, 25, -2)
                        .addComponent(panel_zoom, -2, 29, -2)
                        .addGroup(
                           gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblCodigoDepositoDestino, -2, 25, -2)
                              .addComponent(this.txt_cod_deposito_destino, -2, 25, -2)
                        )
                        .addComponent(lblDepositoOrigen, -2, 25, -2)
                        .addComponent(this.txt_cod_deposito_origen, -2, 25, -2)
                        .addComponent(this.lblNombreDepositoOrigen, -2, 25, -2)
                        .addComponent(this.lblNombreDepositoDestino, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                  .addComponent(panel_opciones, -2, 19, -2)
                  .addContainerGap()
            )
      );
      GroupLayout gl_panel_zoom = new GroupLayout(panel_zoom);
      gl_panel_zoom.setHorizontalGroup(
         gl_panel_zoom.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_zoom.createSequentialGroup()
                  .addGap(12)
                  .addComponent(btnAumentar, -2, 35, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(btnDisminuir, -2, 29, 32767)
                  .addContainerGap()
            )
      );
      gl_panel_zoom.setVerticalGroup(
         gl_panel_zoom.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_zoom.createSequentialGroup()
                  .addGroup(gl_panel_zoom.createParallelGroup(Alignment.BASELINE).addComponent(btnAumentar).addComponent(btnDisminuir))
                  .addContainerGap(-1, 32767)
            )
      );
      panel_zoom.setLayout(gl_panel_zoom);
      panel_filtro.setLayout(gl_panel_filtro);
      contentPane.setLayout(gl_contentPane);
      this.inicializarObjetos();
   }

   @Override
   public void exportarExcel() {
      if (this.jasperPrint == null) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Primero debe generar el reporte.", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         SelectorArchivoPDF fileChooser = new SelectorArchivoPDF(1);

         while (true) {
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection != 0) {
               return;
            }

            final File archivoDestino = fileChooser.getSelectedFile();
            if (!archivoDestino.getName().toLowerCase().endsWith(".xlsx")) {
               archivoDestino = new File(archivoDestino.getAbsolutePath() + ".xlsx");
            }

            if (archivoDestino.exists()) {
               DialogoMessagebox d = new DialogoMessagebox("El archivo ya existe.\n¿Desea reemplazarlo?");
               d.setLocationRelativeTo(this);
               d.setVisible(true);
               if (!d.isResultadoEncontrado()) {
                  continue;
               }
            }

            final EnviosDepositosForm.DialogoProgreso dialogo = new EnviosDepositosForm.DialogoProgreso(null, "Exportando a Excel...");
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
               protected Void doInBackground() {
                  try {
                     JRXlsxExporter exporter = new JRXlsxExporter();
                     exporter.setExporterInput(new SimpleExporterInput(EnviosDepositosForm.this.jasperPrint));
                     exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(archivoDestino));
                     SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
                     config.setDetectCellType(true);
                     config.setCollapseRowSpan(false);
                     config.setWhitePageBackground(false);
                     config.setIgnoreGraphics(false);
                     exporter.setConfiguration(config);
                     exporter.exportReport();
                  } catch (Exception var3) {
                     DialogResultadoOperacion rs = new DialogResultadoOperacion("Error al exportar Excel ", "error");
                     rs.setLocationRelativeTo(EnviosDepositosForm.this);
                     rs.setVisible(true);
                     var3.printStackTrace();
                  }

                  return null;
               }

               @Override
               protected void done() {
                  dialogo.dispose();
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Excel guardado Correctamente", "correcto");
                  rs.setLocationRelativeTo(EnviosDepositosForm.this);
                  rs.setVisible(true);
               }
            };
            worker.execute();
            dialogo.setVisible(true);
            return;
         }
      }
   }

   @Override
   public void exportarPDF() {
      if (this.jasperPrint == null) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Primero debe de generar el reporte", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         SelectorArchivoPDF fileChooser = new SelectorArchivoPDF(0);

         while (true) {
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection != 0) {
               return;
            }

            final File archivoDestino = fileChooser.getSelectedFile();
            if (!archivoDestino.getName().toLowerCase().endsWith(".pdf")) {
               archivoDestino = new File(archivoDestino.getAbsolutePath() + ".pdf");
            }

            if (archivoDestino.exists()) {
               DialogoMessagebox d = new DialogoMessagebox("El archivo ya existe.\n¿Desea reemplazarlo?");
               d.setLocationRelativeTo(this);
               d.setVisible(true);
               if (!d.isResultadoEncontrado()) {
                  continue;
               }
            }

            final EnviosDepositosForm.DialogoProgreso dialogo = new EnviosDepositosForm.DialogoProgreso(null, "Exportando a PDF");
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
               protected Void doInBackground() {
                  try {
                     JasperExportManager.exportReportToPdfFile(EnviosDepositosForm.this.jasperPrint, archivoDestino.getAbsolutePath());
                  } catch (Exception var3) {
                     DialogResultadoOperacion rs = new DialogResultadoOperacion("Error al exportar PDF ", "error");
                     rs.setLocationRelativeTo(EnviosDepositosForm.this);
                     rs.setVisible(true);
                     var3.printStackTrace();
                  }

                  return null;
               }

               @Override
               protected void done() {
                  dialogo.dispose();
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("PDF guardado correctamente", "correcto");
                  rs.setLocationRelativeTo(EnviosDepositosForm.this);
                  rs.setVisible(true);
               }
            };
            worker.execute();
            dialogo.setVisible(true);
            return;
         }
      }
   }

   @Override
   public void imprimirPDF() {
      final String nombreImpresora = BuscarIni.buscar("impresora.reportes");
      if (this.jasperPrint == null) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("El reporte aun no fue generado", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         final EnviosDepositosForm.DialogoProgreso dialogo = new EnviosDepositosForm.DialogoProgreso(null, "Imprimiendo Reporte...");
         SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
               try {
                  PrintService[] servicios = PrintServiceLookup.lookupPrintServices(null, null);
                  PrintService impresoraDestino = null;

                  for (PrintService ps : servicios) {
                     if (ps.getName().equalsIgnoreCase(nombreImpresora)) {
                        impresoraDestino = ps;
                        break;
                     }
                  }

                  if (impresoraDestino == null) {
                     DialogResultadoOperacion rs = new DialogResultadoOperacion("Impresora no encontrada !" + nombreImpresora, "error");
                     rs.setLocationRelativeTo(EnviosDepositosForm.this);
                     rs.setVisible(true);
                     return null;
                  }

                  JRPrintServiceExporter exporter = new JRPrintServiceExporter();
                  exporter.setExporterInput(new SimpleExporterInput(EnviosDepositosForm.this.jasperPrint));
                  SimplePrintServiceExporterConfiguration config = new SimplePrintServiceExporterConfiguration();
                  config.setPrintService(impresoraDestino);
                  config.setDisplayPageDialog(false);
                  config.setDisplayPrintDialog(false);
                  config.setPrintServiceAttributeSet(impresoraDestino.getAttributes());
                  exporter.setConfiguration(config);
                  exporter.exportReport();
               } catch (Exception var7) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Error al imprimir", "error");
                  rs.setLocationRelativeTo(EnviosDepositosForm.this);
                  rs.setVisible(true);
               }

               return null;
            }

            @Override
            protected void done() {
               dialogo.dispose();
            }
         };
         worker.execute();
         dialogo.setVisible(true);
      }
   }

   @Override
   public void recuperar() {
      final EnviosDepositosForm.DialogoProgreso dialogo = new EnviosDepositosForm.DialogoProgreso(null, "Generando Reporte...");
      SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
         protected Void doInBackground() {
            try {
               int v_estado = 0;
               if (EnviosDepositosForm.this.rdbtnTodos.isSelected()) {
                  v_estado = 0;
               } else if (EnviosDepositosForm.this.rdbtnPendientes.isSelected()) {
                  v_estado = 1;
               } else if (EnviosDepositosForm.this.rdbtnRecibidos.isSelected()) {
                  v_estado = 2;
               }

               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
               String fecha_ini = dateFormat.format(EnviosDepositosForm.this.txt_fecha_ini.getDate());
               String fecha_fin = dateFormat.format(EnviosDepositosForm.this.txt_fecha_final.getDate());
               Map<String, Object> parametros = new HashMap<>();
               parametros.put("par_nro_registro", 0);
               parametros.put("par_cod_deposito_origen", Integer.parseInt(EnviosDepositosForm.this.txt_cod_deposito_origen.getText().trim().replace(".", "")));
               parametros.put("par_cod_deposito_destino", Integer.parseInt(EnviosDepositosForm.this.txt_cod_deposito_destino.getText().trim().replace(".", "")));
               parametros.put("par_estado", v_estado);
               parametros.put("par_fecha_ini", fecha_ini);
               parametros.put("par_fecha_fin", fecha_fin);
               EnviosDepositosForm.this.generarReporte(EnviosDepositosForm.this.panelPaginas, "reportes/Envios.jasper", "", parametros, "envios");
            } catch (Exception var6) {
               LogErrores.errores(var6, "No se pudo generar reporte...");
            }

            return null;
         }

         @Override
         protected void done() {
            dialogo.dispose();
         }
      };
      worker.execute();
      dialogo.setVisible(true);
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            EnviosDepositosForm frame = new EnviosDepositosForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
               var3.printStackTrace();
            }
         }
      });
   }

   class DialogoProgreso extends JDialog {
      private static final long serialVersionUID = 1L;

      public DialogoProgreso(Frame owner, String mensaje) {
         super(owner, "Procesando...", true);
         JProgressBar barra = new JProgressBar();
         barra.setIndeterminate(true);
         barra.setString(mensaje);
         barra.setStringPainted(true);
         this.getContentPane().add(barra);
         this.setSize(300, 80);
         this.setLocationRelativeTo(owner);
      }
   }
}
