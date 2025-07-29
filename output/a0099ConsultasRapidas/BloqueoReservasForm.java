package a0099ConsultasRapidas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQLReportes;
import utilidades.PanelPadre;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadreReporte;

public class BloqueoReservasForm extends JinternalPadreReporte {
   private static final long serialVersionUID = 1L;
   private PanelPadre contentPane;
   LimiteTextFieldConSQLReportes txt_cod_producto;
   private BuscadorTablaConsultas tabla;

   @Override
   public void recuperar() {
      BloqueoReservasE.buscarProductosReservadoTabla(this.txt_cod_producto.getText(), this.tabla, this);
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               BloqueoReservasForm frame = new BloqueoReservasForm();
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
   }

   private void inicializarObjetos() {
      this.txt_cod_producto.setValue(0);
   }

   public BloqueoReservasForm() {
      this.setTitle("Productos Reservados");
      this.setBounds(100, 100, 970, 628);
      this.contentPane = new PanelPadre("");
      this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(this.contentPane);
      PanelPadre panel_filtro = new PanelPadre("Filtro de Reporte");
      PanelPadre panel_detalle = new PanelPadre("Detalles");
      LabelPrincipal lblCodigoProducto = new LabelPrincipal("Producto", "label");
      LabelPrincipal lblNombreProducto = new LabelPrincipal(0);
      this.txt_cod_producto = new LimiteTextFieldConSQLReportes(999999, null, "Productos", null);
      String[] cabecera = new String[]{"Codigo", "Descripcion Producto", "CodDeposito", "Nombre Deposito", "Cantidad", "Tipo", "Sesion", "Anular"};
      ModeloTablaDefecto modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTablaConsultas(modeloTabla, "ProductosReservados", this);
      JScrollPane scrollPane = new JScrollPane(20, 30);
      scrollPane.setViewportView(this.tabla);
      this.tabla.setAutoResizeMode(0);
      scrollPane.setViewportView(this.tabla);
      JButton btnRecuperar = new JButton("Recuperar");
      JButton btnProcesar = new JButton("Procesar");
      btnProcesar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BloqueoReservasE.actualizaProductoReservados(BloqueoReservasForm.this, BloqueoReservasForm.this.tabla);
         }
      });
      GroupLayout gl_panel_filtro = new GroupLayout(panel_filtro);
      gl_panel_filtro.setHorizontalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblCodigoProducto, -2, 60, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cod_producto, -2, 45, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblNombreProducto, -2, 341, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(btnRecuperar)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(btnProcesar, -2, 79, -2)
                  .addGap(318)
            )
      );
      gl_panel_filtro.setVerticalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_filtro.createParallelGroup(Alignment.BASELINE).addComponent(btnRecuperar).addComponent(btnProcesar))
                        .addGroup(
                           gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblCodigoProducto, -2, 25, -2)
                              .addComponent(this.txt_cod_producto, -2, 25, -2)
                        )
                        .addComponent(lblNombreProducto, -2, 25, -2)
                  )
                  .addContainerGap(39, 32767)
            )
      );
      panel_filtro.setLayout(gl_panel_filtro);
      GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(panel_filtro, -2, 953, -2).addComponent(panel_detalle, -2, 953, -2)
                  )
                  .addContainerGap(88, 32767)
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_filtro, -2, 48, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -2, 494, -2)
                  .addContainerGap(33, 32767)
            )
      );
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addComponent(scrollPane, -2, 946, -2).addContainerGap(-1, 32767))
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addComponent(scrollPane, -2, 488, -2).addContainerGap(-1, 32767))
      );
      panel_detalle.setLayout(gl_panel_detalle);
      this.contentPane.setLayout(gl_contentPane);
      this.inicializarObjetos();
   }
}
