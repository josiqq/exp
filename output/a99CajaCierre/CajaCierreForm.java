package a99CajaCierre;

import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidades.PanelPadre;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class CajaCierreForm extends JinternalPadre {
   private static final long serialVersionUID = 1L;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CajaCierreForm frame = new CajaCierreForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
               var3.printStackTrace();
            }
         }
      });
   }

   public CajaCierreForm() {
      this.setTitle("Registro de Cierre de Caja");
      this.setBounds(100, 100, 1099, 580);
      PanelPadre panel_datos_planilla = new PanelPadre("Datos Planilla");
      LabelPrincipal lblCajero = new LabelPrincipal("Cajero", "label");
      LabelPrincipal lblFechaPlanilla = new LabelPrincipal("Fecha de Planilla", "label");
      LabelPrincipal lblNombreCaja = new LabelPrincipal("Caja", "label");
      LabelPrincipal lblNroPlanilla = new LabelPrincipal("Nro. Planilla", "label");
      LimiteTextField txt_cod_cajero = new LimiteTextField(25);
      LabelPrincipal lblNombreCajeroTexto = new LabelPrincipal(0);
      LimiteTextField txt_nro_planilla = new LimiteTextField(25);
      LimiteTextField txt_fecha_planilla = new LimiteTextField(25);
      LimiteTextField txt_cod_caja = new LimiteTextField(25);
      LabelPrincipal lblNombreCajaTexto = new LabelPrincipal(0);
      GroupLayout gl_panel_datos_planilla = new GroupLayout(panel_datos_planilla);
      gl_panel_datos_planilla.setHorizontalGroup(
         gl_panel_datos_planilla.createParallelGroup(Alignment.LEADING)
            .addGap(0, 484, 32767)
            .addGroup(
               gl_panel_datos_planilla.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_planilla.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_planilla.createParallelGroup(Alignment.TRAILING, false)
                              .addComponent(lblCajero, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(lblFechaPlanilla, Alignment.LEADING, -1, -1, 32767)
                        )
                        .addGroup(
                           gl_panel_datos_planilla.createParallelGroup(Alignment.TRAILING, false)
                              .addComponent(lblNombreCaja, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(lblNroPlanilla, Alignment.LEADING, -1, 96, 32767)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_planilla.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_planilla.createSequentialGroup()
                              .addComponent(txt_cod_cajero, -2, 69, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblNombreCajeroTexto, -2, 200, -2)
                        )
                        .addComponent(txt_nro_planilla, -2, 168, -2)
                        .addComponent(txt_fecha_planilla, -2, 168, -2)
                        .addGroup(
                           gl_panel_datos_planilla.createSequentialGroup()
                              .addComponent(txt_cod_caja, -2, 75, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblNombreCajaTexto, -2, 200, -2)
                        )
                  )
                  .addGap(101)
            )
      );
      gl_panel_datos_planilla.setVerticalGroup(
         gl_panel_datos_planilla.createParallelGroup(Alignment.LEADING)
            .addGap(0, 178, 32767)
            .addGroup(
               gl_panel_datos_planilla.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_planilla.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNroPlanilla, -2, 25, -2)
                        .addComponent(txt_nro_planilla, -2, 33, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_planilla.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_datos_planilla.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblNombreCaja, -2, 25, -2)
                              .addComponent(txt_cod_caja, -2, 33, -2)
                        )
                        .addComponent(lblNombreCajaTexto, -2, 28, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_planilla.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblFechaPlanilla, -2, 25, -2)
                        .addComponent(txt_fecha_planilla, -2, 33, -2)
                  )
                  .addGap(13)
                  .addGroup(
                     gl_panel_datos_planilla.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblCajero, -2, 25, -2)
                        .addComponent(txt_cod_cajero, -2, 33, -2)
                        .addComponent(lblNombreCajeroTexto, -2, 28, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_datos_planilla.setLayout(gl_panel_datos_planilla);
      PanelPadre panel_cobros = new PanelPadre("Detalles de Cobros");
      JScrollPane scrollPane_detalle_cobro = new JScrollPane();
      GroupLayout gl_panel_cobros = new GroupLayout(panel_cobros);
      gl_panel_cobros.setHorizontalGroup(
         gl_panel_cobros.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_cobros.createSequentialGroup().addContainerGap().addComponent(scrollPane_detalle_cobro, -2, 461, -2).addContainerGap(-1, 32767))
      );
      gl_panel_cobros.setVerticalGroup(
         gl_panel_cobros.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_cobros.createSequentialGroup().addContainerGap().addComponent(scrollPane_detalle_cobro, -2, 140, -2).addContainerGap(-1, 32767))
      );
      panel_cobros.setLayout(gl_panel_cobros);
      PanelPadre panel_apertura_caja = new PanelPadre("Detalles de Apertura de Planilla");
      JScrollPane scrollPane_detalle_apertura = new JScrollPane();
      GroupLayout gl_panel_apertura_caja = new GroupLayout(panel_apertura_caja);
      gl_panel_apertura_caja.setHorizontalGroup(
         gl_panel_apertura_caja.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_apertura_caja.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(scrollPane_detalle_apertura, -2, 453, -2)
                  .addContainerGap(11, 32767)
            )
      );
      gl_panel_apertura_caja.setVerticalGroup(
         gl_panel_apertura_caja.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_apertura_caja.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(scrollPane_detalle_apertura, -2, 134, -2)
                  .addContainerGap(11, 32767)
            )
      );
      panel_apertura_caja.setLayout(gl_panel_apertura_caja);
      PanelPadre pane_rendicion_caja = new PanelPadre("Detalles de Rendicion");
      JScrollPane scrollPane_detalle_rendicion = new JScrollPane();
      GroupLayout gl_pane_rendicion_caja = new GroupLayout(pane_rendicion_caja);
      gl_pane_rendicion_caja.setHorizontalGroup(
         gl_pane_rendicion_caja.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_pane_rendicion_caja.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(scrollPane_detalle_rendicion, -2, 454, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      gl_pane_rendicion_caja.setVerticalGroup(
         gl_pane_rendicion_caja.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_pane_rendicion_caja.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(scrollPane_detalle_rendicion, -2, 131, -2)
                  .addContainerGap(14, 32767)
            )
      );
      pane_rendicion_caja.setLayout(gl_pane_rendicion_caja);
      PanelPadre panel_movimientos = new PanelPadre("Detalles de Movimiento de Caja");
      JScrollPane scrollPane_detalle_movimientos = new JScrollPane();
      GroupLayout gl_panel_movimientos = new GroupLayout(panel_movimientos);
      gl_panel_movimientos.setHorizontalGroup(
         gl_panel_movimientos.createParallelGroup(Alignment.LEADING)
            .addGap(0, 484, 32767)
            .addGroup(
               gl_panel_movimientos.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(scrollPane_detalle_movimientos, -2, 453, -2)
                  .addContainerGap(11, 32767)
            )
      );
      gl_panel_movimientos.setVerticalGroup(
         gl_panel_movimientos.createParallelGroup(Alignment.LEADING)
            .addGap(0, 178, 32767)
            .addGroup(
               gl_panel_movimientos.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(scrollPane_detalle_movimientos, -2, 134, -2)
                  .addContainerGap(11, 32767)
            )
      );
      panel_movimientos.setLayout(gl_panel_movimientos);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_datos_planilla, -2, 484, -2)
                        .addComponent(panel_apertura_caja, -2, 484, -2)
                        .addComponent(panel_movimientos, -2, 484, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel_cobros, -2, 484, -2).addComponent(pane_rendicion_caja, -2, 484, -2)
                  )
                  .addContainerGap(99, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addContainerGap()
                              .addComponent(panel_datos_planilla, -2, 178, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_apertura_caja, -2, 178, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_movimientos, -2, 178, -2)
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addGap(18)
                              .addComponent(panel_cobros, -2, 178, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(pane_rendicion_caja, -2, 178, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      this.getContentPane().setLayout(groupLayout);
   }
}
