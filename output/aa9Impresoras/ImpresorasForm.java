package aa9Impresoras;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Beans;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import utilidades.BotonPadre;
import utilidades.BuscarIni;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidades.PanelPadre;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class ImpresorasForm extends JinternalPadre {
   private JComboBox<String> impresoraComboBox;
   private static final long serialVersionUID = 1L;
   private LimiteTextField txt_impresora_cheques;
   private LimiteTextField txt_impresora_cobros;
   private LimiteTextField txt_impresora_pagos;
   private LimiteTextField txt_impresora_ventas;
   private LimiteTextField txt_impresora_compras;
   private BotonPadre btnSalir;
   private BotonPadre btnGuardar;
   private BotonPadre btnSeleccionarImpresoraVenta;
   private BotonPadre btnSeleccionarImpresoraCheque;
   private BotonPadre btnSeleccionarImpresoraCobro;
   private BotonPadre btnSeleccionarImpresoraPago;
   private BotonPadre btnSeleccionarImpresoraCompra;
   private LimiteTextField txt_impresora_reportes;
   private BotonPadre btnSeleccionarImpresoraReporte;

   private void mostrarDialogoSeleccionarImpresora(LimiteTextField cuadroTexto) {
      PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
      String[] impresoras = new String[printServices.length];

      for (int i = 0; i < printServices.length; i++) {
         impresoras[i] = printServices[i].getName();
      }

      this.impresoraComboBox = new JComboBox<>(impresoras);
      int resultado = JOptionPane.showConfirmDialog(this, this.impresoraComboBox, "Seleccionar Impresora", 2);
      if (resultado == 0) {
         String impresoraSeleccionada = (String)this.impresoraComboBox.getSelectedItem();
         cuadroTexto.setValue(impresoraSeleccionada);
      }
   }

   public void guardarConfiguracion() {
      String impresoraCompra = this.txt_impresora_compras.getText();
      String impresoraVenta = this.txt_impresora_ventas.getText();
      String impresoraCobro = this.txt_impresora_cobros.getText();
      String impresoraPago = this.txt_impresora_pagos.getText();
      String impresoraCheques = this.txt_impresora_cheques.getText();
      String impresoraReportes = this.txt_impresora_reportes.getText();

      try {
         BuscarIni.guardar("impresora.compras", impresoraCompra);
         BuscarIni.guardar("impresora.ventas", impresoraVenta);
         BuscarIni.guardar("impresora.cobros", impresoraCobro);
         BuscarIni.guardar("impresora.pagos", impresoraPago);
         BuscarIni.guardar("impresora.cheques", impresoraCheques);
         BuscarIni.guardar("impresora.reportes", impresoraReportes);
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Configuración guardada correctamente", "correcto");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } catch (Exception var9) {
         DialogResultadoOperacion rsx = new DialogResultadoOperacion("Error al guardar la configuración: " + var9.getMessage(), "error");
         rsx.setLocationRelativeTo(this);
         rsx.setVisible(true);
      }
   }

   private void cargarConfiguracion() {
      try {
         this.txt_impresora_compras.setText(BuscarIni.buscar("impresora.compras"));
         this.txt_impresora_ventas.setText(BuscarIni.buscar("impresora.ventas"));
         this.txt_impresora_cobros.setText(BuscarIni.buscar("impresora.cobros"));
         this.txt_impresora_pagos.setText(BuscarIni.buscar("impresora.pagos"));
         this.txt_impresora_cheques.setText(BuscarIni.buscar("impresora.cheques"));
         this.txt_impresora_reportes.setText(BuscarIni.buscar("impresora.reportes"));
      } catch (Exception var3) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Error al cargar la configuración: " + var3.getMessage(), "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      }
   }

   public ImpresorasForm() {
      this.setTitle("Configurar Impresoras");
      this.setBounds(100, 100, 587, 420);
      PanelPadre panel = new PanelPadre("Configuracion de Impresoras");
      LabelPrincipal lblImpresoraCompra = new LabelPrincipal("Impresora para Compras", "label");
      LabelPrincipal lblImpresoraVenta = new LabelPrincipal("Impresora para Ventas", "label");
      LabelPrincipal lblImpresoraPagos = new LabelPrincipal("Impresora para Pagos", "label");
      LabelPrincipal lblImpresoraCobro = new LabelPrincipal("Impresora para Cobros", "label");
      LabelPrincipal lblImpresoraCheque = new LabelPrincipal("Impresora para Cheques", "label");
      LabelPrincipal lblImpresoraReportes = new LabelPrincipal("Impresora para Reportes", "label");
      this.txt_impresora_compras = new LimiteTextField(25);
      this.txt_impresora_compras.setEditable(false);
      this.txt_impresora_ventas = new LimiteTextField(25);
      this.txt_impresora_ventas.setEditable(false);
      this.txt_impresora_pagos = new LimiteTextField(25);
      this.txt_impresora_pagos.setEditable(false);
      this.txt_impresora_cobros = new LimiteTextField(25);
      this.txt_impresora_cobros.setEditable(false);
      this.txt_impresora_cheques = new LimiteTextField(25);
      this.txt_impresora_cheques.setEditable(false);
      this.txt_impresora_reportes = new LimiteTextField(25);
      this.txt_impresora_reportes.setEditable(false);
      this.btnSeleccionarImpresoraCompra = new BotonPadre("Seleccionar", "buscar.png");
      this.btnSeleccionarImpresoraVenta = new BotonPadre("Seleccionar", "buscar.png");
      this.btnSeleccionarImpresoraPago = new BotonPadre("Seleccionar", "buscar.png");
      this.btnSeleccionarImpresoraCobro = new BotonPadre("Seleccionar", "buscar.png");
      this.btnSeleccionarImpresoraCheque = new BotonPadre("Seleccionar", "buscar.png");
      this.btnSeleccionarImpresoraReporte = new BotonPadre("Seleccionar", "buscar.png");
      this.btnGuardar = new BotonPadre("Guardar", "guardar.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, -1, 551, 32767).addContainerGap())
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, -2, 364, -2).addContainerGap(16, 32767))
      );
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 gl_panel.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblImpresoraCheque, -2, 136, 32767)
                                    .addComponent(lblImpresoraCobro, -1, -1, 32767)
                                    .addComponent(lblImpresoraPagos, -1, -1, 32767)
                                    .addComponent(lblImpresoraVenta, -1, -1, 32767)
                                    .addComponent(lblImpresoraCompra, -1, -1, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel.createSequentialGroup()
                                          .addComponent(this.txt_impresora_pagos, -2, 255, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.btnSeleccionarImpresoraPago, -2, -1, -2)
                                    )
                                    .addGroup(
                                       gl_panel.createSequentialGroup()
                                          .addComponent(this.txt_impresora_ventas, -2, 255, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.btnSeleccionarImpresoraVenta, -2, -1, -2)
                                    )
                                    .addGroup(
                                       gl_panel.createSequentialGroup()
                                          .addComponent(this.txt_impresora_compras, -2, 255, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.btnSeleccionarImpresoraCompra, -2, -1, -2)
                                    )
                                    .addGroup(
                                       gl_panel.createSequentialGroup()
                                          .addComponent(this.txt_impresora_cobros, -2, 255, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.btnSeleccionarImpresoraCobro, -2, -1, -2)
                                    )
                                    .addGroup(
                                       gl_panel.createSequentialGroup()
                                          .addComponent(this.txt_impresora_cheques, -2, 255, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.btnSeleccionarImpresoraCheque, -2, -1, -2)
                                    )
                              )
                        )
                        .addGroup(
                           gl_panel.createSequentialGroup()
                              .addGap(93)
                              .addComponent(this.btnGuardar, -2, 120, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 120, -2)
                        )
                        .addGroup(
                           gl_panel.createSequentialGroup()
                              .addContainerGap()
                              .addComponent(lblImpresoraReportes, -2, 136, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_impresora_reportes, -2, 255, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSeleccionarImpresoraReporte, -2, 120, -2)
                        )
                  )
                  .addContainerGap(17, 32767)
            )
      );
      gl_panel.setVerticalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblImpresoraCompra, -2, 25, -2)
                        .addComponent(this.txt_impresora_compras, -2, 31, -2)
                        .addComponent(this.btnSeleccionarImpresoraCompra, -2, -1, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblImpresoraVenta, -2, 25, -2)
                        .addComponent(this.txt_impresora_ventas, -2, 31, -2)
                        .addComponent(this.btnSeleccionarImpresoraVenta, -2, -1, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblImpresoraPagos, -2, 25, -2)
                        .addComponent(this.txt_impresora_pagos, -2, 31, -2)
                        .addComponent(this.btnSeleccionarImpresoraPago, -2, -1, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblImpresoraCobro, -2, 25, -2)
                        .addComponent(this.txt_impresora_cobros, -2, 31, -2)
                        .addComponent(this.btnSeleccionarImpresoraCobro, -2, -1, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblImpresoraCheque, -2, 25, -2)
                        .addComponent(this.txt_impresora_cheques, -2, 31, -2)
                        .addComponent(this.btnSeleccionarImpresoraCheque, -2, -1, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblImpresoraReportes, -2, 25, -2)
                              .addComponent(this.txt_impresora_reportes, -2, 31, -2)
                        )
                        .addComponent(this.btnSeleccionarImpresoraReporte, -2, 35, -2)
                  )
                  .addGap(21)
                  .addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(this.btnGuardar, -2, 35, -2).addComponent(this.btnSalir, -2, 35, -2))
                  .addContainerGap(77, 32767)
            )
      );
      panel.setLayout(gl_panel);
      this.getContentPane().setLayout(groupLayout);
      this.btnSeleccionarImpresoraCompra.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ImpresorasForm.this.mostrarDialogoSeleccionarImpresora(ImpresorasForm.this.txt_impresora_compras);
         }
      });
      this.btnSeleccionarImpresoraVenta.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ImpresorasForm.this.mostrarDialogoSeleccionarImpresora(ImpresorasForm.this.txt_impresora_ventas);
         }
      });
      this.btnSeleccionarImpresoraPago.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ImpresorasForm.this.mostrarDialogoSeleccionarImpresora(ImpresorasForm.this.txt_impresora_pagos);
         }
      });
      this.btnSeleccionarImpresoraCobro.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ImpresorasForm.this.mostrarDialogoSeleccionarImpresora(ImpresorasForm.this.txt_impresora_cobros);
         }
      });
      this.btnSeleccionarImpresoraCheque.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ImpresorasForm.this.mostrarDialogoSeleccionarImpresora(ImpresorasForm.this.txt_impresora_cheques);
         }
      });
      this.btnSeleccionarImpresoraReporte.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ImpresorasForm.this.mostrarDialogoSeleccionarImpresora(ImpresorasForm.this.txt_impresora_reportes);
         }
      });
      this.btnGuardar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ImpresorasForm.this.guardarConfiguracion();
         }
      });
      this.btnSalir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ImpresorasForm.this.dispose();
         }
      });
      if (!Beans.isDesignTime()) {
         this.cargarConfiguracion();
      }
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ImpresorasForm frame = new ImpresorasForm();

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
