package utilidadesRecalculos;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQLJasper;
import utilidades.LimiteTextFieldConSQLReportes;
import utilidades.PanelPadre;
import utilidadesTabla.BuscadorTablaReportes;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;

public class StockForm extends JinternalPadre {
   private static final long serialVersionUID = 1L;
   private PanelPadre contentPane;
   private LimiteTextFieldConSQLJasper txt_cod_deposito;
   private LabelPrincipal lblNombreDeposito;
   private LabelPrincipal lblNombreProducto;
   private JButton btnProcesar;
   private JButton btnRecuperar;
   private LimiteTextFieldConSQLReportes txt_cod_producto;
   private BuscadorTablaReportes tabla;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               StockForm frame = new StockForm();
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
   }

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   private void inicializarObjetos() {
      this.txt_cod_deposito.setValue(0);
      this.lblNombreDeposito.setText("");
      this.txt_cod_producto.setText("0");
      this.lblNombreProducto.setText("");
   }

   public StockForm() {
      this.setBounds(100, 100, 1007, 544);
      this.contentPane = new PanelPadre("");
      this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(this.contentPane);
      PanelPadre panel_filtro = new PanelPadre("");
      PanelPadre panel_detalle = new PanelPadre("");
      PanelPadre panel_filtro_1 = new PanelPadre("");
      LabelPrincipal lblCodigoDeposito = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblProducto = new LabelPrincipal("Producto", "label");
      LabelPrincipal lbllineaRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      final LabelPrincipal lblLineaRecupera = new LabelPrincipal("", "lineas");
      lblLineaRecupera.setText("0");
      LabelPrincipal lbllineaProcesadas = new LabelPrincipal("Lineas Procesadas:", "lineas");
      lbllineaProcesadas.setForeground(Color.BLUE);
      final LabelPrincipal lblLineaProcesadas = new LabelPrincipal("0", "lineas");
      lblLineaProcesadas.setForeground(Color.BLUE);
      this.lblNombreProducto = new LabelPrincipal(0);
      this.lblNombreDeposito = new LabelPrincipal(0);
      this.txt_cod_deposito = new LimiteTextFieldConSQLJasper(999999, this.lblNombreDeposito, "Depositos", null);
      this.txt_cod_producto = new LimiteTextFieldConSQLReportes(999999, this.lblNombreProducto, "Productos", null);
      final JProgressBar progressBar = new JProgressBar();
      String[] cabecera = new String[]{"Codigo", "Descripcion", "CodDeposito", "Deposito", "Cantidad"};
      final ModeloTablaDefecto modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTablaReportes(modeloTabla, "RecalculoStock", this);
      JScrollPane scrollPane = new JScrollPane(this.tabla);
      this.btnRecuperar = new JButton("Recuperar");
      this.btnRecuperar
         .addActionListener(
            new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                  SwingWorker<?, ?> worker = new SwingWorker<Object, Object>() {
                     @Override
                     protected Object doInBackground() throws Exception {
                        progressBar.setValue(0);
                        lblLineaProcesadas.setText("0");
                        RecalculoStockE.cargarProductos(
                           StockForm.this.txt_cod_producto.getText(),
                           Integer.parseInt(StockForm.this.txt_cod_deposito.getText().replace(".", "")),
                           modeloTabla,
                           lblLineaRecupera,
                           StockForm.this
                        );
                        return null;
                     }
                  };
                  worker.execute();
               }
            }
         );
      this.btnProcesar = new JButton("Procesar");
      this.btnProcesar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            SwingWorker<?, ?> worker = new SwingWorker<Object, Object>() {
               @Override
               protected Object doInBackground() throws Exception {
                  progressBar.setValue(0);
                  lblLineaProcesadas.setText("0");
                  RecalculoStockE.actualizarStock(StockForm.this.tabla, progressBar, lblLineaProcesadas, StockForm.this);
                  return null;
               }
            };
            worker.execute();
         }
      });
      GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(panel_filtro, -1, 981, 32767)
                        .addComponent(panel_detalle, -1, -1, 32767)
                        .addComponent(panel_filtro_1, -1, -1, 32767)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_filtro, -2, 50, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -1, 384, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_filtro_1, -2, 58, -2)
            )
      );
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 859, 32767).addContainerGap())
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 286, 32767).addContainerGap())
      );
      panel_detalle.setLayout(gl_panel_detalle);
      GroupLayout gl_panel_filtro_1 = new GroupLayout(panel_filtro_1);
      gl_panel_filtro_1.setHorizontalGroup(
         gl_panel_filtro_1.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro_1.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_filtro_1.createParallelGroup(Alignment.LEADING)
                        .addComponent(progressBar, -1, 958, 32767)
                        .addGroup(
                           gl_panel_filtro_1.createSequentialGroup()
                              .addComponent(lbllineaRecuperadas, -2, 130, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblLineaRecupera, -2, 47, -2)
                              .addGap(44)
                              .addComponent(lbllineaProcesadas, -2, 122, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblLineaProcesadas, -2, 62, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      gl_panel_filtro_1.setVerticalGroup(
         gl_panel_filtro_1.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_filtro_1.createSequentialGroup()
                  .addComponent(progressBar, -2, 30, -2)
                  .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                  .addGroup(
                     gl_panel_filtro_1.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lbllineaRecuperadas, -2, 17, -2)
                        .addComponent(lblLineaRecupera, -2, 17, -2)
                        .addComponent(lbllineaProcesadas, -2, 17, -2)
                        .addComponent(lblLineaProcesadas, -2, 17, -2)
                  )
                  .addGap(82)
            )
      );
      panel_filtro_1.setLayout(gl_panel_filtro_1);
      GroupLayout gl_panel_filtro = new GroupLayout(panel_filtro);
      gl_panel_filtro.setHorizontalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addComponent(lblProducto, -2, 60, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cod_producto, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreProducto, -2, 298, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblCodigoDeposito, -2, 60, -2)
                  .addGap(4)
                  .addComponent(this.txt_cod_deposito, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreDeposito, -2, 236, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.btnRecuperar, -2, 101, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnProcesar, -2, 101, -2)
                  .addGap(2)
            )
      );
      gl_panel_filtro.setVerticalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addGap(10)
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_panel_filtro.createParallelGroup(Alignment.BASELINE).addComponent(this.btnRecuperar).addComponent(this.btnProcesar))
                        .addGroup(
                           gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                              .addGroup(
                                 gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblProducto, -2, 25, -2)
                                    .addComponent(this.txt_cod_producto, -2, 25, -2)
                              )
                              .addComponent(lblCodigoDeposito, -2, 25, -2)
                              .addComponent(this.txt_cod_deposito, -2, 25, -2)
                              .addComponent(this.lblNombreDeposito, -2, 25, -2)
                              .addComponent(this.lblNombreProducto, -2, 25, -2)
                        )
                  )
                  .addContainerGap(55, 32767)
            )
      );
      panel_filtro.setLayout(gl_panel_filtro);
      this.contentPane.setLayout(gl_contentPane);
      this.inicializarObjetos();
   }
}
