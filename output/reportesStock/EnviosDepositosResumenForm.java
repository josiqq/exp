package reportesStock;

import a0099NotaTransferencia.EnvioDepositoE;
import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQLJasper;
import utilidades.PanelPadre;
import utilidades.RadioBoton;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTablaReportes;
import utilidadesTabla.ExcelExporterAbrirExcel;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadreReporte;

public class EnviosDepositosResumenForm extends JinternalPadreReporte {
   private BuscadorTablaReportes tabla;
   private static final long serialVersionUID = 1L;
   private RadioBoton rdbtnTodos;
   private RadioBoton rdbtnRecibidos;
   private RadioBoton rdbtnPendientes;
   private JDateChooser txt_fecha_ini;
   private JDateChooser txt_fecha_final;
   private LimiteTextFieldConSQLJasper txt_cod_deposito_origen;
   private LimiteTextFieldConSQLJasper txt_cod_deposito_destino;

   @Override
   public void exportarExcel() {
      ExcelExporterAbrirExcel exporter = new ExcelExporterAbrirExcel();
      exporter.exportarJTableConDialogo(this.tabla);
   }

   @Override
   public void recuperar() {
      int estado = 2;
      if (this.rdbtnPendientes.isSelected()) {
         estado = 0;
      } else if (this.rdbtnRecibidos.isSelected()) {
         estado = 1;
      }

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha_ini = dateFormat.format(this.txt_fecha_ini.getDate());
      String fecha_fin = dateFormat.format(this.txt_fecha_final.getDate());
      EnvioDepositoE.buscarEnvioTablaReporte(
         fecha_ini,
         fecha_fin,
         Integer.parseInt(this.txt_cod_deposito_origen.getText()),
         Integer.parseInt(this.txt_cod_deposito_destino.getText()),
         estado,
         this.tabla,
         2,
         this
      );
   }

   private void inicializarObjetos() {
      this.txt_cod_deposito_destino.setValue(0);
      this.txt_cod_deposito_origen.setValue(0);
   }

   public EnviosDepositosResumenForm() {
      this.setTitle("Reporte de Envios de Depositos - Resumen");
      this.setBounds(100, 100, 1129, 617);
      PanelPadre contentPane = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      PanelPadre panel_detalle = new PanelPadre("Detalles");
      String[] cabecera = new String[]{"Nro. Registro", "Fecha", "Deposito Origen", "Deposito Destino", "Recibido"};
      ModeloTablaDefecto modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTablaReportes(modeloTabla, "NotaEnvio", this);
      JScrollPane scrollPane = new JScrollPane(this.tabla);
      PanelPadre panel_filtro = new PanelPadre("Filtro de Reporte");
      PanelPadre panel_opciones = new PanelPadre("");
      this.rdbtnTodos = new RadioBoton("Todos");
      this.rdbtnRecibidos = new RadioBoton("Recibidos");
      this.rdbtnPendientes = new RadioBoton("Pendientes");
      ButtonGroup button = new ButtonGroup();
      button.add(this.rdbtnPendientes);
      button.add(this.rdbtnRecibidos);
      button.add(this.rdbtnTodos);
      this.rdbtnTodos.setSelected(true);
      LabelPrincipal lblFechaIni = new LabelPrincipal("Fecha Inicio", "label");
      LabelPrincipal lblFechaFin = new LabelPrincipal("Fecha Final", "label");
      LabelPrincipal lblDepositoOrigen = new LabelPrincipal("Deposito Origen", "label");
      LabelPrincipal lblNombreDepositoOrigen = new LabelPrincipal(0);
      LabelPrincipal lblCodigoDepositoDestino = new LabelPrincipal("Deposito Destino", "label");
      LabelPrincipal lblNombreDepositoDestino = new LabelPrincipal(0);
      this.txt_fecha_ini = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_final = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_cod_deposito_origen = new LimiteTextFieldConSQLJasper(999999, lblNombreDepositoOrigen, "Depositos", null);
      this.txt_cod_deposito_destino = new LimiteTextFieldConSQLJasper(999999, lblNombreDepositoDestino, "Depositos", null);
      GroupLayout gl_panel_opciones = new GroupLayout(panel_opciones);
      gl_panel_opciones.setHorizontalGroup(
         gl_panel_opciones.createParallelGroup(Alignment.TRAILING)
            .addGap(0, 358, 32767)
            .addGroup(
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
            .addGap(0, 19, 32767)
            .addGroup(
               gl_panel_opciones.createSequentialGroup()
                  .addGroup(
                     gl_panel_opciones.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.rdbtnPendientes, -2, 16, -2)
                        .addComponent(this.rdbtnRecibidos, -2, 16, -2)
                        .addComponent(this.rdbtnTodos, -2, 16, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_opciones.setLayout(gl_panel_opciones);
      GroupLayout gl_panel_filtro = new GroupLayout(panel_filtro);
      gl_panel_filtro.setHorizontalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_opciones, -2, 337, -2)
                        .addGroup(
                           gl_panel_filtro.createSequentialGroup()
                              .addComponent(lblFechaIni, -2, 77, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_fecha_ini, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblFechaFin, -2, 77, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_fecha_final, -2, 94, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblDepositoOrigen, -2, 95, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_deposito_origen, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblNombreDepositoOrigen, -2, 200, -2)
                              .addGap(4)
                              .addComponent(lblCodigoDepositoDestino, -2, 98, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_deposito_destino, -2, 41, -2)
                              .addGap(4)
                              .addComponent(lblNombreDepositoDestino, -2, 228, -2)
                        )
                  )
                  .addGap(14)
            )
      );
      gl_panel_filtro.setVerticalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_filtro.createSequentialGroup()
                              .addGroup(
                                 gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblFechaIni, -2, 25, -2)
                                    .addComponent(this.txt_fecha_ini, -2, 25, -2)
                                    .addComponent(lblFechaFin, -2, 25, -2)
                                    .addComponent(this.txt_fecha_final, -2, 25, -2)
                                    .addGroup(
                                       gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblCodigoDepositoDestino, -2, 25, -2)
                                          .addComponent(this.txt_cod_deposito_destino, -2, 25, -2)
                                    )
                              )
                              .addPreferredGap(ComponentPlacement.RELATED, 7, 32767)
                              .addComponent(panel_opciones, -2, 19, -2)
                        )
                        .addGroup(
                           gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblDepositoOrigen, -2, 25, -2)
                              .addComponent(this.txt_cod_deposito_origen, -2, 25, -2)
                        )
                        .addComponent(lblNombreDepositoOrigen, -2, 25, -2)
                        .addComponent(lblNombreDepositoDestino, -2, 25, -2)
                  )
                  .addContainerGap()
            )
      );
      panel_filtro.setLayout(gl_panel_filtro);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_filtro, -2, 1101, -2)
                        .addComponent(panel_detalle, -1, 1234, 32767)
                  )
                  .addContainerGap()
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_filtro, -2, 64, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -1, 507, 32767)
            )
      );
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -2, 1091, -2).addContainerGap(130, 32767))
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addComponent(scrollPane, -1, 481, 32767).addContainerGap())
      );
      panel_detalle.setLayout(gl_panel_detalle);
      contentPane.setLayout(gl_contentPane);
      this.inicializarObjetos();
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            EnviosDepositosResumenForm frame = new EnviosDepositosResumenForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
               var3.printStackTrace();
            }
         }
      });
   }
}
