package reportesVentas;

import a00Ventas.VentasE;
import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.SimpleDateFormat;
import javax.swing.GroupLayout;
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

public class ListadoVentasResumenForm extends JinternalPadreReporte {
   private BuscadorTablaReportes tabla;
   private static final long serialVersionUID = 1L;
   private LimiteTextFieldConSQLProductos txt_cod_producto;
   private LimiteTextFieldConSQLReportes txt_cod_deposito;
   private LimiteTextFieldConSQLReportes txt_cod_cliente;
   private LabelPrincipal lblNombreDeposito;
   private LabelPrincipal lblNombreCliente;
   private LabelPrincipal lblNombreProducto;
   private LabelPrincipal lblTotalGeneralTexto;
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
      VentasE.buscarVentasTablaReporte(
         fecha_ini,
         fecha_fin,
         this.txt_cod_producto.getText(),
         Integer.parseInt(this.txt_cod_cliente.getText().trim().replace(".", "")),
         Integer.parseInt(this.txt_cod_deposito.getText().trim().replace(".", "")),
         this.tabla,
         2,
         this.lblTotalGeneralTexto,
         this
      );
   }

   private void inicializarObjetos() {
      this.txt_cod_deposito.setValue(0);
      this.lblNombreDeposito.setText("");
      this.txt_cod_producto.setText("0");
      this.lblNombreProducto.setText("");
      this.txt_cod_cliente.setValue(0);
      this.lblNombreCliente.setText("");
   }

   public ListadoVentasResumenForm() {
      this.setTitle("Reporte de Ventas Resumen");
      this.setBounds(100, 100, 979, 617);
      PanelPadre contentPane = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      PanelPadre panel_filtro = new PanelPadre("Filtro de Reporte");
      PanelPadre panel_detalle = new PanelPadre("Detalles");
      LabelPrincipal lblCodigoCliente = new LabelPrincipal("Cliente", "label");
      LabelPrincipal lblCodigoProducto = new LabelPrincipal("Producto", "label");
      LabelPrincipal lblDeposito = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblFechaIni = new LabelPrincipal("Fecha Inicio", "label");
      LabelPrincipal lblFechaFin = new LabelPrincipal("Fecha Final", "label");
      LabelPrincipal lblTotalGeneral = new LabelPrincipal("Total Venta:", "lineas");
      lblTotalGeneral.setFont(new Font("Tahoma", 1, 16));
      lblTotalGeneral.setHorizontalAlignment(4);
      this.lblTotalGeneralTexto = new LabelPrincipal("0", "lineas");
      this.lblTotalGeneralTexto.setFont(new Font("Tahoma", 1, 16));
      this.lblNombreCliente = new LabelPrincipal(0);
      this.lblNombreProducto = new LabelPrincipal(0);
      this.lblNombreDeposito = new LabelPrincipal(0);
      this.txt_cod_cliente = new LimiteTextFieldConSQLReportes(999999, this.lblNombreCliente, "Clientes", this);
      this.txt_cod_producto = new LimiteTextFieldConSQLProductos(this.lblNombreProducto, this);
      this.txt_cod_deposito = new LimiteTextFieldConSQLReportes(999999, this.lblNombreDeposito, "Marcas", this);
      this.txt_fecha_ini = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_final = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      String[] cabecera = new String[]{"Nro. Registro", "Fecha", "Deposito", "Cliente", "Total Venta", "Moneda"};
      ModeloTablaDefecto modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTablaReportes(modeloTabla, "ListadoVentas", this);
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
                  .addComponent(panel_filtro, -2, 65, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -1, 506, 32767)
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
                        .addComponent(lblDeposito, -1, -1, 32767)
                        .addComponent(lblFechaIni, -1, 77, 32767)
                  )
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_filtro.createSequentialGroup()
                              .addGap(4)
                              .addComponent(this.txt_fecha_ini, -2, 97, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblFechaFin, -2, 77, -2)
                              .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                              .addComponent(this.txt_fecha_final, -2, 94, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(lblCodigoProducto, -2, 60, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_producto, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                        )
                        .addGroup(
                           gl_panel_filtro.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_deposito, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreDeposito, -2, 200, -2)
                              .addPreferredGap(ComponentPlacement.RELATED, 30, 32767)
                              .addComponent(lblCodigoCliente, -2, 60, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_cliente, -2, 41, -2)
                              .addGap(10)
                        )
                  )
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(this.lblNombreCliente, -2, 365, -2)
                        .addComponent(this.lblNombreProducto, -2, 459, -2)
                  )
                  .addContainerGap()
            )
      );
      gl_panel_filtro.setVerticalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblFechaIni, -2, 25, -2)
                        .addComponent(lblFechaFin, -2, 25, -2)
                        .addComponent(this.txt_fecha_final, -2, 25, -2)
                        .addGroup(
                           gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblCodigoProducto, -2, 25, -2)
                              .addComponent(this.txt_cod_producto, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreProducto, -2, 25, -2)
                        .addComponent(this.txt_fecha_ini, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblDeposito, -2, 25, -2)
                              .addComponent(this.txt_cod_deposito, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreDeposito, -2, 25, -2)
                        .addComponent(this.lblNombreCliente, -2, 25, -2)
                        .addGroup(
                           gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_cliente, -2, 25, -2)
                              .addComponent(lblCodigoCliente, -2, 25, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_filtro.setLayout(gl_panel_filtro);
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_detalle.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_detalle.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           Alignment.TRAILING,
                           gl_panel_detalle.createSequentialGroup()
                              .addComponent(lblTotalGeneral, -2, 129, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblTotalGeneralTexto, -2, 129, -2)
                              .addGap(19)
                        )
                        .addComponent(scrollPane, -1, 940, 32767)
                  )
            )
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING,
               gl_panel_detalle.createSequentialGroup()
                  .addComponent(scrollPane, -1, 477, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_detalle.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblTotalGeneral, -2, -1, -2)
                        .addComponent(this.lblTotalGeneralTexto, -2, 20, -2)
                  )
            )
      );
      panel_detalle.setLayout(gl_panel_detalle);
      contentPane.setLayout(gl_contentPane);
      this.inicializarObjetos();
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ListadoVentasResumenForm frame = new ListadoVentasResumenForm();

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
