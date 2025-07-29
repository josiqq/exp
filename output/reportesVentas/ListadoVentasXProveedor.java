package reportesVentas;

import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQLReportes;
import utilidades.PanelPadre;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTablaReportes;
import utilidadesTabla.ExcelExporterAbrirExcel;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadreReporte;

public class ListadoVentasXProveedor extends JinternalPadreReporte {
   private BuscadorTablaReportes tabla;
   private static final long serialVersionUID = 1L;
   private LimiteTextFieldConSQLReportes txt_cod_proveedor;
   private LabelPrincipal lblNombreProveedor;
   private JDateChooser txt_fecha_ini;
   private JDateChooser txt_fecha_final;

   @Override
   public void exportarExcel() {
      ExcelExporterAbrirExcel exporter = new ExcelExporterAbrirExcel();
      exporter.exportarJTableConDialogo(this.tabla);
   }

   @Override
   public void recuperar() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha_ini = dateFormat.format(this.txt_fecha_ini.getDate());
      String fecha_fin = dateFormat.format(this.txt_fecha_final.getDate());
      ReportesVentasE.buscarProveedorTabla(fecha_ini, fecha_fin, Integer.parseInt(this.txt_cod_proveedor.getText()), this.tabla, 2, this);
   }

   private void inicializarObjetos() {
      this.txt_cod_proveedor.setValue(0);
      this.lblNombreProveedor.setText("");
   }

   public ListadoVentasXProveedor() {
      this.setTitle("Reporte de Ventas por Proveedor");
      this.setBounds(100, 100, 979, 617);
      PanelPadre contentPane = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      PanelPadre panel_filtro = new PanelPadre("Filtro de Reporte");
      PanelPadre panel_detalle = new PanelPadre("Detalles");
      LabelPrincipal lblCodigoProveedor = new LabelPrincipal("Proveedor", "label");
      LabelPrincipal lblFechaFin = new LabelPrincipal("Fecha Final", "label");
      LabelPrincipal lblFechaIni = new LabelPrincipal("Fecha Inicio", "label");
      this.lblNombreProveedor = new LabelPrincipal(0);
      this.txt_cod_proveedor = new LimiteTextFieldConSQLReportes(999999, this.lblNombreProveedor, "Proveedores", this);
      this.txt_fecha_ini = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_final = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      String[] cabecera = new String[]{"Cod. Proveedor", "Nombre Proveedor", "RUC", "Total Venta", "Total Costo", "Moneda", "Utilidad"};
      ModeloTablaDefecto modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTablaReportes(modeloTabla, "VentaxProveedor", this);
      JScrollPane scrollPane = new JScrollPane(this.tabla);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panel_detalle, Alignment.LEADING, -1, 953, 32767)
                        .addComponent(panel_filtro, Alignment.LEADING, -2, 953, -2)
                  )
                  .addContainerGap()
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_filtro, -2, 34, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -1, 537, 32767)
            )
      );
      GroupLayout gl_panel_filtro = new GroupLayout(panel_filtro);
      gl_panel_filtro.setHorizontalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addComponent(lblFechaIni, -2, 77, -2)
                  .addGap(4)
                  .addComponent(this.txt_fecha_ini, -2, 88, -2)
                  .addGap(4)
                  .addComponent(lblFechaFin, -2, 77, -2)
                  .addGap(4)
                  .addComponent(this.txt_fecha_final, -2, 94, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(lblCodigoProveedor, -2, 60, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cod_proveedor, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreProveedor, -2, 365, -2)
                  .addContainerGap(118, 32767)
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
                        .addComponent(this.lblNombreProveedor, -2, 25, -2)
                        .addGroup(
                           gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblCodigoProveedor, -2, 25, -2)
                              .addComponent(this.txt_cod_proveedor, -2, 25, -2)
                        )
                  )
                  .addGap(18)
            )
      );
      panel_filtro.setLayout(gl_panel_filtro);
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 940, 32767))
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
            ListadoVentasXProveedor frame = new ListadoVentasXProveedor();

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
