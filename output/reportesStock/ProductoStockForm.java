package reportesStock;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.CheckPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQLProductos;
import utilidades.LimiteTextFieldConSQLReportes;
import utilidades.PanelPadre;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ExcelExporterAbrirExcel;
import utilidadesVentanas.JinternalPadreReporte;

public class ProductoStockForm extends JinternalPadreReporte {
   private JTable tabla;
   private int COD_SECCION;
   private int COD_SUB_SECCION;
   private int COD_GRUPO;
   private static final long serialVersionUID = 1L;
   private LimiteTextFieldConSQLReportes txt_cod_marca;
   private LimiteTextFieldConSQLReportes txt_cod_deposito;
   private LimiteTextFieldConSQLProductos txt_cod_producto;
   private LabelPrincipal lblNombreMarca;
   private LabelPrincipal lblNombreDeposito;
   private LabelPrincipal lblNombreProducto;
   private CheckPadre chckbxSinStock;
   private CheckPadre chckbxConCosto;

   @Override
   public void exportarExcel() {
      ExcelExporterAbrirExcel exporter = new ExcelExporterAbrirExcel();
      exporter.exportarJTableConDialogo(this.tabla);
   }

   @Override
   public void recuperar() {
      int con_costo = 0;
      int sin_stock = 0;
      if (this.chckbxSinStock.isSelected()) {
         sin_stock = 1;
      }

      if (this.chckbxConCosto.isSelected()) {
         con_costo = 1;
      }

      ProductoStockE.buscarProductoStock(
         sin_stock,
         con_costo,
         this.txt_cod_producto.getText(),
         Integer.parseInt(this.txt_cod_deposito.getText().trim().replace(".", "")),
         Integer.parseInt(this.txt_cod_marca.getText().trim().replace(".", "")),
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
      this.txt_cod_producto.setText("0");
      this.lblNombreProducto.setText("");
      this.txt_cod_deposito.setValue(0);
      this.lblNombreDeposito.setText("");
   }

   public ProductoStockForm() {
      this.setTitle("Reporte de Stock de Productos");
      this.setBounds(100, 100, 979, 617);
      PanelPadre contentPane = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      PanelPadre panel_filtro = new PanelPadre("Filtro de Reporte");
      PanelPadre panel_detalle = new PanelPadre("Detalles");
      LabelPrincipal lblCodigoDeposito = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblCodigoProducto = new LabelPrincipal("Producto", "label");
      LabelPrincipal lblMarca = new LabelPrincipal("Marca", "label");
      this.lblNombreDeposito = new LabelPrincipal(0);
      this.lblNombreProducto = new LabelPrincipal(0);
      this.lblNombreMarca = new LabelPrincipal(0);
      this.txt_cod_deposito = new LimiteTextFieldConSQLReportes(999999, this.lblNombreDeposito, "Depositos", this);
      this.txt_cod_producto = new LimiteTextFieldConSQLProductos(this.lblNombreProducto, this);
      this.txt_cod_marca = new LimiteTextFieldConSQLReportes(999999, this.lblNombreMarca, "Marcas", this);
      JButton btnFiltrarFamilias = new JButton("Filtrar Familias");
      this.tabla = new JTable();
      JScrollPane scrollPane = new JScrollPane(20, 30);
      scrollPane.setViewportView(this.tabla);
      this.tabla.setAutoResizeMode(0);
      scrollPane.setViewportView(this.tabla);
      this.chckbxSinStock = new CheckPadre("Sin Stock");
      this.chckbxConCosto = new CheckPadre("Con Costo");
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
                  .addContainerGap()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_filtro.createSequentialGroup()
                              .addComponent(lblCodigoProducto, -2, 60, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_producto, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreProducto, -2, 341, -2)
                              .addGap(26)
                              .addComponent(this.chckbxSinStock, -2, 93, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.chckbxConCosto, -2, 93, -2)
                        )
                        .addGroup(
                           gl_panel_filtro.createSequentialGroup()
                              .addComponent(lblMarca, -2, 60, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_marca, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreMarca, -2, 200, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblCodigoDeposito, -2, 60, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_deposito, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreDeposito, -2, 365, -2)
                        )
                  )
                  .addGap(40)
                  .addComponent(btnFiltrarFamilias)
                  .addContainerGap()
            )
      );
      gl_panel_filtro.setVerticalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_filtro.createSequentialGroup().addGap(24).addComponent(btnFiltrarFamilias))
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
                                    .addComponent(this.chckbxSinStock, -2, 24, -2)
                                    .addComponent(this.chckbxConCosto, -2, 24, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.lblNombreDeposito, -2, 25, -2)
                                    .addGroup(
                                       gl_panel_filtro.createParallelGroup(Alignment.TRAILING)
                                          .addGroup(
                                             gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(lblMarca, -2, 25, -2)
                                                .addComponent(this.txt_cod_marca, -2, 25, -2)
                                          )
                                          .addGroup(
                                             gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(lblCodigoDeposito, -2, 25, -2)
                                                .addComponent(this.txt_cod_deposito, -2, 25, -2)
                                          )
                                    )
                                    .addComponent(this.lblNombreMarca, -2, 25, -2)
                              )
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_filtro.setLayout(gl_panel_filtro);
      panel_detalle.setLayout(new BorderLayout());
      panel_detalle.add(scrollPane, "Center");
      contentPane.setLayout(gl_contentPane);
      this.inicializarObjetos();
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ProductoStockForm frame = new ProductoStockForm();

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
