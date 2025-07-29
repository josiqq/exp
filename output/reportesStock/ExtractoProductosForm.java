package reportesStock;

import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

public class ExtractoProductosForm extends JinternalPadreReporte {
   private BuscadorTablaReportes tabla;
   private static final long serialVersionUID = 1L;
   private LimiteTextFieldConSQLProductos txt_cod_producto;
   private LimiteTextFieldConSQLReportes txt_cod_deposito;
   private LabelPrincipal lblNombreDeposito;
   private LabelPrincipal lblNombreProducto;
   private JDateChooser txt_fecha_ini;
   private JDateChooser txt_fecha_fin;
   private LabelPrincipal lblSaldoAnteriorTexto;
   private LabelPrincipal lblSaldoFinalTexto;

   @Override
   public void exportarExcel() {
      ExcelExporterAbrirExcel exporter = new ExcelExporterAbrirExcel();
      exporter.exportarJTableConDialogo(this.tabla);
   }

   @Override
   public void recuperar() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha_ini = dateFormat.format(this.txt_fecha_ini.getDate());
      String fecha_fin = dateFormat.format(this.txt_fecha_fin.getDate());
      Date fechaOriginal = this.txt_fecha_ini.getDate();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(fechaOriginal);
      calendar.add(5, -1);
      String fecha_un_dia_antes = dateFormat.format(calendar.getTime());
      ExtractoProductosE.cargarProductos(
         this.txt_cod_producto.getText(),
         Integer.parseInt(this.txt_cod_deposito.getText().trim().replace(".", "")),
         fecha_un_dia_antes,
         fecha_ini,
         fecha_fin,
         this.lblSaldoAnteriorTexto,
         this.lblSaldoFinalTexto,
         this.tabla,
         this
      );
   }

   private void inicializarObjetos() {
      this.txt_cod_deposito.setValue(0);
      this.lblNombreDeposito.setText("");
      this.txt_cod_producto.setText("0");
      this.lblNombreProducto.setText("");
   }

   public ExtractoProductosForm() {
      this.setTitle("Extracto de Productos");
      this.setBounds(100, 100, 1042, 617);
      PanelPadre contentPane = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      PanelPadre panel_filtro = new PanelPadre("Filtro de Reporte");
      PanelPadre panel_detalle = new PanelPadre("Detalles");
      LabelPrincipal lblCodigoProducto = new LabelPrincipal("Producto", "label");
      LabelPrincipal lblDeposito = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblFechaIni = new LabelPrincipal("Fecha Inicio", "label");
      this.lblNombreProducto = new LabelPrincipal(0);
      this.lblNombreDeposito = new LabelPrincipal(0);
      LabelPrincipal lblFechaFin = new LabelPrincipal("Fecha Fin", "label");
      LabelPrincipal lblSaldoAnterior = new LabelPrincipal("Saldo Anterior:", "lineas");
      lblSaldoAnterior.setFont(new Font("Tahoma", 1, 16));
      this.lblSaldoAnteriorTexto = new LabelPrincipal("0", "lineas");
      this.lblSaldoAnteriorTexto.setFont(new Font("Tahoma", 1, 16));
      LabelPrincipal lblSaldoFinal = new LabelPrincipal("Saldo Final:", "lineas");
      lblSaldoFinal.setHorizontalAlignment(4);
      lblSaldoFinal.setFont(new Font("Tahoma", 1, 16));
      this.lblSaldoFinalTexto = new LabelPrincipal("0", "lineas");
      this.lblSaldoFinalTexto.setFont(new Font("Tahoma", 1, 16));
      this.txt_cod_producto = new LimiteTextFieldConSQLProductos(this.lblNombreProducto, this);
      this.txt_cod_deposito = new LimiteTextFieldConSQLReportes(999999, this.lblNombreDeposito, "Marcas", this);
      this.txt_fecha_ini = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_fin = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      String[] cabecera = new String[]{"Fecha", "Hora", "Nro. Registro", "Operacion", "Entrada", "Salida", "Saldo"};
      ModeloTablaDefecto modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTablaReportes(modeloTabla, "ExtractoProductos", this);
      JScrollPane scrollPane = new JScrollPane(this.tabla);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(panel_detalle, -1, -1, 32767).addComponent(panel_filtro, -2, 1006, -2)
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
                  .addComponent(panel_detalle, -2, 505, -2)
                  .addContainerGap(42, 32767)
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
                              .addComponent(lblFechaFin, -2, 67, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_fecha_fin, -2, 97, -2)
                        )
                        .addGroup(
                           gl_panel_filtro.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_deposito, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreDeposito, -2, 200, -2)
                        )
                  )
                  .addGap(42)
                  .addComponent(lblCodigoProducto, -2, 60, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cod_producto, -2, 45, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreProducto, -2, 459, -2)
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
                        .addComponent(lblCodigoProducto, -2, 25, -2)
                        .addComponent(this.txt_fecha_ini, -2, 25, -2)
                        .addComponent(lblFechaFin, -2, 25, -2)
                        .addComponent(this.txt_fecha_fin, -2, 25, -2)
                        .addComponent(this.txt_cod_producto, -2, 25, -2)
                        .addComponent(this.lblNombreProducto, -2, 25, -2)
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
                  .addComponent(scrollPane, -2, 745, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_detalle.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_detalle.createSequentialGroup()
                              .addComponent(lblSaldoAnterior, -2, 129, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblSaldoAnteriorTexto, -2, 99, -2)
                        )
                        .addGroup(
                           gl_panel_detalle.createSequentialGroup()
                              .addComponent(lblSaldoFinal, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblSaldoFinalTexto, -2, 99, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_detalle.createSequentialGroup()
                  .addGap(4)
                  .addGroup(
                     gl_panel_detalle.createParallelGroup(Alignment.BASELINE, false)
                        .addComponent(scrollPane, -2, 489, -2)
                        .addGroup(
                           gl_panel_detalle.createSequentialGroup()
                              .addGroup(
                                 gl_panel_detalle.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblSaldoAnterior, -2, -1, -2)
                                    .addComponent(this.lblSaldoAnteriorTexto, -2, -1, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                              .addGroup(
                                 gl_panel_detalle.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblSaldoFinal, -2, 20, -2)
                                    .addComponent(this.lblSaldoFinalTexto, -2, 20, -2)
                              )
                              .addGap(5)
                        )
                  )
                  .addGap(51)
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
            ExtractoProductosForm frame = new ExtractoProductosForm();

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
