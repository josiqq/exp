package reportesTabla;

import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;

public class ListadoPreciosForm extends JFrame {
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private LimiteTextField txt_cod_lista;
   private LabelPrincipal lblNombreListaPrecio;
   private LimiteTextField txt_cod_marca;
   private LabelPrincipal lblNombreMarca;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               ListadoPreciosForm frame = new ListadoPreciosForm();
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
   }

   public ListadoPreciosForm() {
      this.setDefaultCloseOperation(3);
      this.setBounds(100, 100, 886, 645);
      this.contentPane = new JPanel();
      this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(this.contentPane);
      JPanel panel_filtros = new JPanel();
      JPanel panel_detalle = new JPanel();
      GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(panel_detalle, -2, 854, -2).addComponent(panel_filtros, -2, 859, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_filtros, -2, 60, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -1, 522, 32767)
            )
      );
      LabelPrincipal lblListaPrecio = new LabelPrincipal("Lista de Precio", "label");
      this.txt_cod_lista = new LimiteTextField(25);
      this.lblNombreListaPrecio = new LabelPrincipal(0);
      LabelPrincipal lblCodMarca = new LabelPrincipal("Marca", "label");
      this.txt_cod_marca = new LimiteTextField(25);
      this.lblNombreMarca = new LabelPrincipal(0);
      GroupLayout gl_panel_filtros = new GroupLayout(panel_filtros);
      gl_panel_filtros.setHorizontalGroup(
         gl_panel_filtros.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtros.createSequentialGroup()
                  .addComponent(lblListaPrecio, -2, 93, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cod_lista, -2, 47, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreListaPrecio, -2, 200, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblCodMarca, -2, 48, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cod_marca, -2, 47, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreMarca, -2, 200, -2)
                  .addContainerGap(202, 32767)
            )
      );
      gl_panel_filtros.setVerticalGroup(
         gl_panel_filtros.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtros.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_filtros.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblCodMarca, -2, 25, -2)
                        .addComponent(this.lblNombreListaPrecio, -2, 28, -2)
                        .addComponent(this.txt_cod_lista, -2, 33, -2)
                        .addComponent(lblListaPrecio, -2, 25, -2)
                        .addGroup(
                           gl_panel_filtros.createParallelGroup(Alignment.LEADING)
                              .addComponent(this.lblNombreMarca, -2, 28, -2)
                              .addComponent(this.txt_cod_marca, -2, 33, -2)
                        )
                  )
                  .addContainerGap(94, 32767)
            )
      );
      panel_filtros.setLayout(gl_panel_filtros);
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(gl_panel_detalle.createParallelGroup(Alignment.LEADING).addGap(0, 854, 32767));
      gl_panel_detalle.setVerticalGroup(gl_panel_detalle.createParallelGroup(Alignment.LEADING).addGap(0, 450, 32767));
      panel_detalle.setLayout(gl_panel_detalle);
      this.contentPane.setLayout(gl_contentPane);
   }
}
