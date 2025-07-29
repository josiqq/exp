package reportesVentas;

import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQLReportes;
import utilidades.PanelPadre;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorFamilia;
import utilidadesTabla.BuscadorTablaReportes;
import utilidadesTabla.ExcelExporterAbrirExcel;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadreReporte;

public class ListadoRankingProductosForm extends JinternalPadreReporte {
   private BuscadorTablaReportes tabla;
   private int COD_SECCION;
   private int COD_SUB_SECCION;
   private int COD_GRUPO;
   private static final long serialVersionUID = 1L;
   private LimiteTextFieldConSQLReportes txt_cod_marca;
   private LimiteTextFieldConSQLReportes txt_cod_proveedor;
   private LabelPrincipal lblNombreMarca;
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
      ReportesVentasE.buscarProductosRankingTabla(
         fecha_ini,
         fecha_fin,
         Integer.parseInt(this.txt_cod_proveedor.getText()),
         Integer.parseInt(this.txt_cod_marca.getText()),
         this.COD_SECCION,
         this.COD_SUB_SECCION,
         this.COD_GRUPO,
         this.tabla,
         2,
         this
      );
   }

   private void inicializarObjetos() {
      this.COD_SECCION = 0;
      this.COD_SUB_SECCION = 0;
      this.COD_GRUPO = 0;
      this.txt_cod_marca.setValue(0);
      this.lblNombreMarca.setText("");
      this.txt_cod_proveedor.setValue(0);
      this.lblNombreProveedor.setText("");
   }

   private void filtrarFamilia() {
      BuscadorFamilia buscador = new BuscadorFamilia("");
      buscador.setLocationRelativeTo(null);
      buscador.setModal(true);
      buscador.setVisible(true);
      if (buscador.isSeleccionado()) {
         this.COD_SECCION = buscador.getCod_seccion();
         this.COD_SUB_SECCION = buscador.getCod_sub_seccion();
         this.COD_GRUPO = buscador.getCod_grupo();
      } else {
         this.COD_SECCION = 0;
         this.COD_SUB_SECCION = 0;
         this.COD_GRUPO = 0;
      }
   }

   public ListadoRankingProductosForm() {
      this.setTitle("Reporte de Ranking de Productos Vendidos");
      this.setBounds(100, 100, 1050, 617);
      PanelPadre contentPane = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      PanelPadre panel_filtro = new PanelPadre("Filtro de Reporte");
      PanelPadre panel_detalle = new PanelPadre("Detalles");
      LabelPrincipal lblCodigoProveedor = new LabelPrincipal("Proveedor", "label");
      LabelPrincipal lblMarca = new LabelPrincipal("Marca", "label");
      LabelPrincipal lblFechaFin = new LabelPrincipal("Fecha Final", "label");
      LabelPrincipal lblFechaIni = new LabelPrincipal("Fecha Inicio", "label");
      this.lblNombreProveedor = new LabelPrincipal(0);
      this.lblNombreMarca = new LabelPrincipal(0);
      this.txt_cod_proveedor = new LimiteTextFieldConSQLReportes(999999, this.lblNombreProveedor, "Proveedores", this);
      this.txt_cod_marca = new LimiteTextFieldConSQLReportes(999999, this.lblNombreMarca, "Marcas", this);
      this.txt_fecha_ini = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_final = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      JButton btnFiltrarFamilias = new JButton("Filtrar Familias");
      btnFiltrarFamilias.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ListadoRankingProductosForm.this.filtrarFamilia();
         }
      });
      String[] cabecera = new String[]{"Codigo", "Nombre Producto", "Cantidad", "Total Venta", "Total Costo", "Moneda", "Utilidad"};
      ModeloTablaDefecto modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTablaReportes(modeloTabla, "RankingProductos", this);
      JScrollPane scrollPane = new JScrollPane(this.tabla);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING,
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panel_filtro, Alignment.LEADING, -1, 1014, 32767)
                        .addComponent(panel_detalle, -1, 953, 32767)
                  )
                  .addContainerGap()
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_filtro, -2, 77, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -1, 494, 32767)
            )
      );
      GroupLayout gl_panel_filtro = new GroupLayout(panel_filtro);
      gl_panel_filtro.setHorizontalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_filtro.createSequentialGroup()
                              .addContainerGap()
                              .addComponent(lblMarca, -2, 60, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_marca, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreMarca, -2, 200, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblCodigoProveedor, -2, 60, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_proveedor, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreProveedor, -2, 365, -2)
                        )
                        .addGroup(
                           gl_panel_filtro.createSequentialGroup()
                              .addComponent(lblFechaIni, -2, 77, -2)
                              .addGap(4)
                              .addComponent(this.txt_fecha_ini, -2, 88, -2)
                              .addGap(4)
                              .addComponent(lblFechaFin, -2, 77, -2)
                              .addGap(4)
                              .addComponent(this.txt_fecha_final, -2, 94, -2)
                              .addGap(33)
                              .addComponent(btnFiltrarFamilias)
                        )
                  )
                  .addContainerGap(292, 32767)
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
                        .addComponent(btnFiltrarFamilias)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.lblNombreProveedor, -2, 25, -2)
                        .addGroup(
                           gl_panel_filtro.createParallelGroup(Alignment.TRAILING)
                              .addGroup(
                                 gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblMarca, -2, 25, -2)
                                    .addComponent(this.txt_cod_marca, -2, 25, -2)
                              )
                              .addGroup(
                                 gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblCodigoProveedor, -2, 25, -2)
                                    .addComponent(this.txt_cod_proveedor, -2, 25, -2)
                              )
                        )
                        .addComponent(this.lblNombreMarca, -2, 25, -2)
                  )
            )
      );
      panel_filtro.setLayout(gl_panel_filtro);
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -2, 1021, -2).addContainerGap(-1, 32767))
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
            ListadoRankingProductosForm frame = new ListadoRankingProductosForm();

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
