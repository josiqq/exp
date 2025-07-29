package reportesCompra;

import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQLProductos;
import utilidades.LimiteTextFieldConSQLReportes;
import utilidades.PanelPadre;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTablaReportes;
import utilidadesTabla.ExcelExporterAbrirExcel;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadreReporte;

public class ReporteComprasGastosForm extends JinternalPadreReporte {
   private BuscadorTablaReportes tabla;
   private LimiteTextFieldConSQLProductos txt_cod_producto;
   private int COD_SECCION;
   private int COD_SUB_SECCION;
   private int COD_GRUPO;
   private static final long serialVersionUID = 1L;
   private LimiteTextFieldConSQLReportes txt_cod_marca;
   private LimiteTextFieldConSQLReportes txt_cod_proveedor;
   private LabelPrincipal lblNombreMarca;
   private LabelPrincipal lblNombreProveedor;
   private LabelPrincipal lblNombreProducto;
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
      ReporteComprasProductosE.buscarProductosTablaReporte(
         fecha_ini,
         fecha_fin,
         this.txt_cod_producto.getText(),
         Integer.parseInt(this.txt_cod_proveedor.getText().trim().replace(".", "")),
         Integer.parseInt(this.txt_cod_marca.getText().trim().replace(".", "")),
         this.COD_SECCION,
         this.COD_SUB_SECCION,
         this.COD_GRUPO,
         this.tabla,
         this
      );
   }

   private void inicializarObjetos() {
      this.COD_SECCION = 0;
      this.COD_SUB_SECCION = 0;
      this.COD_GRUPO = 0;
      this.txt_cod_marca.setValue(0);
      this.lblNombreMarca.setText("");
      this.txt_cod_producto.setText("0");
      this.lblNombreProducto.setText("");
      this.txt_cod_proveedor.setValue(0);
      this.lblNombreProveedor.setText("");
   }

   public ReporteComprasGastosForm() {
      this.setTitle("Reporte de Compras por Productos");
      this.setBounds(100, 100, 840, 617);
      PanelPadre contentPane = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      PanelPadre panel_filtro = new PanelPadre("Filtro de Reporte");
      PanelPadre panel_detalle = new PanelPadre("Detalles");
      LabelPrincipal lblCodigoProveedor = new LabelPrincipal("Proveedor", "label");
      LabelPrincipal lblCodigoProducto = new LabelPrincipal("Producto", "label");
      LabelPrincipal lblMarca = new LabelPrincipal("Marca", "label");
      LabelPrincipal lblFechaIni = new LabelPrincipal("Fecha Inicio", "label");
      LabelPrincipal lblFechaFin = new LabelPrincipal("Fecha Final", "label");
      this.lblNombreProveedor = new LabelPrincipal(0);
      this.lblNombreProducto = new LabelPrincipal(0);
      this.lblNombreMarca = new LabelPrincipal(0);
      this.txt_cod_proveedor = new LimiteTextFieldConSQLReportes(999999, this.lblNombreProveedor, "Proveedores", this);
      this.txt_cod_producto = new LimiteTextFieldConSQLProductos(this.lblNombreProducto, this);
      this.txt_cod_marca = new LimiteTextFieldConSQLReportes(999999, this.lblNombreMarca, "Marcas", this);
      this.txt_fecha_ini = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_final = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      JButton btnFiltrarFamilias = new JButton("Filtrar Familias");
      String[] cabecera = new String[]{"Codigo", "Nombre", "Cantidad", "Cantidad Bonif", "Total"};
      ModeloTablaDefecto modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTablaReportes(modeloTabla, "ComprasProductos", this);
      JScrollPane scrollPane = new JScrollPane(this.tabla);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel_detalle, Alignment.LEADING, 0, 0, 32767)
                        .addComponent(panel_filtro, Alignment.LEADING, -1, 822, 32767)
                  )
                  .addGap(131)
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_filtro, -2, 105, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -2, 472, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_filtro = new GroupLayout(panel_filtro);
      gl_panel_filtro.setHorizontalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_filtro.createSequentialGroup()
                              .addComponent(lblCodigoProducto, -2, 60, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_producto, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreProducto, -1, -1, 32767)
                        )
                        .addGroup(
                           gl_panel_filtro.createSequentialGroup()
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
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_fecha_ini, -2, 98, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblFechaFin, -2, 77, -2)
                              .addGap(4)
                              .addComponent(this.txt_fecha_final, -2, 94, -2)
                              .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                              .addComponent(btnFiltrarFamilias)
                        )
                  )
                  .addContainerGap(151, 32767)
            )
      );
      gl_panel_filtro.setVerticalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblCodigoProducto, -2, 25, -2)
                              .addComponent(this.txt_cod_producto, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreProducto, -2, 25, -2)
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
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblFechaIni, -2, 25, -2)
                        .addComponent(lblFechaFin, -2, 25, -2)
                        .addComponent(this.txt_fecha_final, -2, 25, -2)
                        .addComponent(this.txt_fecha_ini, -2, 25, -2)
                        .addComponent(btnFiltrarFamilias)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_filtro.setLayout(gl_panel_filtro);
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addComponent(scrollPane, -2, 747, -2).addContainerGap(203, 32767))
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -2, 449, -2).addContainerGap(-1, 32767))
      );
      panel_detalle.setLayout(gl_panel_detalle);
      contentPane.setLayout(gl_contentPane);
      this.inicializarObjetos();
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ReporteComprasGastosForm frame = new ReporteComprasGastosForm();

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
