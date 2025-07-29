package a0098DevolucionVentas;

import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextField;
import utilidades.LimiteTextFieldConSQL;
import utilidades.PanelPadre;
import utilidadesVentanas.JinternalPadre;

public class DevolucionVentasForm extends JinternalPadre {
   private static final long serialVersionUID = 1L;
   private JDateChooser txt_fecha;
   private LimiteTextFieldConSQL txt_cod_deposito;
   private LimiteTextFieldConSQL txt_cod_motivo;
   private LimiteTextFieldConSQL txt_cod_proveedor;
   private LimiteTextArea txt_observacion;
   private LabelPrincipal lblUltimoRegistroTexto;
   private LabelPrincipal lblTotalLineasTexto;
   private LabelPrincipal lblNombreDeposito;
   private LabelPrincipal lblNombreMotivo;
   private LabelPrincipal lblNombreProveedor;
   private LimiteTextField txt_codigo = new LimiteTextField(25);

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               DevolucionVentasForm frame = new DevolucionVentasForm();
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
   }

   public DevolucionVentasForm() {
      this.setBounds(100, 100, 904, 612);
      PanelPadre contentPane = new PanelPadre("");
      PanelPadre panel_cabecera = new PanelPadre("");
      PanelPadre panel = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      LabelPrincipal lblTotalLineas = new LabelPrincipal("Total Lineas:", "lineas");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro:", "lineas");
      LabelPrincipal lblNroRegistro = new LabelPrincipal("Nro. Registro", "label");
      LabelPrincipal lblFecha = new LabelPrincipal("Fecha", "label");
      LabelPrincipal lblProveedor = new LabelPrincipal("Proveedor", "label");
      LabelPrincipal lblDeposito = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblObservacion = new LabelPrincipal("Observacion", "label");
      LabelPrincipal lblMotivo = new LabelPrincipal("Motivo", "label");
      this.lblNombreProveedor = new LabelPrincipal(0);
      this.lblNombreDeposito = new LabelPrincipal(0);
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreMotivo = new LabelPrincipal(0);
      this.lblTotalLineasTexto = new LabelPrincipal("0", "lineas");
      this.txt_fecha = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_cod_proveedor = new LimiteTextFieldConSQL(999999, this.lblNombreProveedor, "Proveedores", this);
      this.txt_cod_deposito = new LimiteTextFieldConSQL(999999, this.lblNombreDeposito, "Depositos", this);
      this.txt_cod_motivo = new LimiteTextFieldConSQL(999999, this.lblNombreMotivo, "MotivosNC", this);
      this.txt_observacion = new LimiteTextArea(70);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel, -1, -1, 32767)
                        .addComponent(panel_cabecera, -2, 868, -2)
                        .addGroup(
                           gl_contentPane.createSequentialGroup()
                              .addComponent(lblTotalLineas, -2, 70, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblTotalLineasTexto, -2, 45, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_cabecera, -2, 212, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel, -2, 328, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblTotalLineas).addComponent(this.lblTotalLineasTexto))
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_cabecera = new GroupLayout(panel_cabecera);
      gl_panel_cabecera.setHorizontalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblProveedor, -1, -1, 32767)
                                    .addComponent(lblFecha, -1, -1, 32767)
                                    .addComponent(lblNroRegistro, -1, 91, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                                    .addGroup(
                                       gl_panel_cabecera.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 81, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblUltimoRegistro)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, 45, -2)
                                    )
                                    .addGroup(
                                       gl_panel_cabecera.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                                .addGroup(
                                                   gl_panel_cabecera.createSequentialGroup()
                                                      .addComponent(this.txt_cod_proveedor, -2, 58, -2)
                                                      .addPreferredGap(ComponentPlacement.RELATED)
                                                      .addComponent(this.lblNombreProveedor, -2, 200, -2)
                                                )
                                                .addComponent(this.txt_fecha, -2, 119, -2)
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addGroup(
                                             gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(lblMotivo, -1, -1, 32767)
                                                .addComponent(lblDeposito, -1, -1, 32767)
                                          )
                                          .addGap(4)
                                          .addGroup(
                                             gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(this.txt_cod_motivo, -1, -1, 32767)
                                                .addComponent(this.txt_cod_deposito, -1, 58, 32767)
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addGroup(
                                             gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                                .addComponent(this.lblNombreMotivo, -2, 200, -2)
                                                .addComponent(this.lblNombreDeposito, -2, 200, -2)
                                          )
                                    )
                              )
                        )
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addComponent(lblObservacion, -2, 91, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_observacion, -1, -1, 32767)
                        )
                  )
                  .addContainerGap(181, 32767)
            )
      );
      gl_panel_cabecera.setVerticalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addGap(10)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                              .addGroup(
                                 gl_panel_cabecera.createSequentialGroup()
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblNroRegistro, -2, 25, -2)
                                          .addComponent(this.txt_codigo, -2, 31, -2)
                                          .addComponent(lblUltimoRegistro)
                                          .addComponent(this.lblUltimoRegistroTexto)
                                    )
                                    .addGap(12)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblFecha, -2, 25, -2)
                                          .addComponent(this.txt_fecha, -2, 31, -2)
                                    )
                              )
                              .addGroup(gl_panel_cabecera.createSequentialGroup().addGap(49).addComponent(lblMotivo, -2, 25, -2))
                        )
                        .addComponent(this.txt_cod_motivo, -2, 31, -2)
                        .addComponent(this.lblNombreMotivo, -2, 28, -2)
                  )
                  .addGap(18)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblProveedor, -2, 25, -2)
                        .addComponent(this.txt_cod_proveedor, -2, 31, -2)
                        .addGroup(
                           gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                              .addComponent(this.lblNombreProveedor, -2, 28, -2)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(this.lblNombreDeposito, -2, 28, -2)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblDeposito, -2, 25, -2)
                                          .addComponent(this.txt_cod_deposito, -2, 31, -2)
                                    )
                              )
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblObservacion, -2, 25, -2)
                        .addComponent(this.txt_observacion, -2, 48, -2)
                  )
                  .addContainerGap(25, 32767)
            )
      );
      panel_cabecera.setLayout(gl_panel_cabecera);
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGap(0, 779, 32767));
      gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGap(0, 301, 32767));
      panel.setLayout(gl_panel);
      contentPane.setLayout(gl_contentPane);
   }
}
