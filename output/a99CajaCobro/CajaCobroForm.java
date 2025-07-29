package a99CajaCobro;

import a00Cotizaciones.CotizacionesE;
import a00pedidosProveedores.DecimalFilter;
import a99CajaCobroCliente.BuscadorCtasCobrar;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import utilidades.BotonPadre;
import utilidades.LabelImagenes;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidades.PanelPadre;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class CajaCobroForm extends JinternalPadre {
   private final JPanel panel_detalle = new JPanel();
   private final List<LineaPago> lineas = new ArrayList<>();
   private JComboBox<CondicionesE> comboTipoPago;
   private JComboBox<MonedasE> comboMonedas_efectivo;
   private JComboBox<MonedasE> comboMonedaVuelto;
   private JComboBox<MonedasE> comboMonedas_tarjeta;
   private JComboBox<MonedasE> comboMonedas_cheque;
   private JComboBox<TarjetasE> comboTarjetas;
   private JComboBox<BancosE> comboBancos;
   private static final long serialVersionUID = 1L;
   private LimiteTextField txt_registro_vta;
   private TablaDetalleDocumentosCobrarCaja tabla_documentos;
   List<Integer> codigosAEliminar = new ArrayList<>();
   private BotonPadre btnCancelar;
   private BotonPadre btnBuscarCredito;
   private BotonPadre btnBuscarTicket;
   private DecimalFilter txt_total_cobrar;
   private DecimalFilter txt_total_cobrado;
   private DecimalFilter txt_total_saldo;
   private DecimalFilter txt_total_moneda1;
   private DecimalFilter txt_total_moneda2;
   private DecimalFilter txt_total_moneda3;
   private DecimalFilter txt_total_moneda4;
   private int COD_CLIENTE;
   private int COD_MONEDA;

   public CajaCobroForm() {
      this.setTitle("Registro de Cobros Caja");
      this.setBounds(100, 100, 1149, 593);
      PanelPadre panel_detalles_registro = new PanelPadre("Detalles de Documentos a Cobrar");
      PanelPadre panel_totales_cobrar = new PanelPadre("Totales a Pagar en Monedas");
      PanelPadre panel = new PanelPadre("Totales");
      PanelPadre panel_botones = new PanelPadre("");
      LabelPrincipal lblDigiteRegistro = new LabelPrincipal("Digite el Numero de Registro", "label");
      LabelPrincipal lblTotalCobrar = new LabelPrincipal("Total a Cobrar", "label");
      LabelPrincipal lblTotalCobrado = new LabelPrincipal("Total Cobrado", "label");
      LabelPrincipal lblSaldo = new LabelPrincipal("Saldo a Cobrar", "label");
      LabelImagenes lblNombreMoneda1 = new LabelImagenes("moneda1");
      LabelImagenes lblNombreMoneda2 = new LabelImagenes("moneda2");
      LabelImagenes lblNombreMoneda3 = new LabelImagenes("moneda3");
      LabelImagenes lblNombreMoneda4 = new LabelImagenes("moneda4");
      this.txt_registro_vta = new LimiteTextField(25);
      this.txt_registro_vta.setName("txt_buscador");
      this.txt_registro_vta
         .addKeyListener(
            new KeyAdapter() {
               @Override
               public void keyPressed(KeyEvent e) {
                  if (e.getKeyCode() == 10) {
                     ModeloTablaDocumentosCobrarCaja modelo = (ModeloTablaDocumentosCobrarCaja)CajaCobroForm.this.tabla_documentos.getModel();
                     VentaTicketE entidad = VentaTicketE.cargarVentaETabla(
                        CajaCobroForm.this.txt_registro_vta, modelo, 2, CajaCobroForm.this.COD_MONEDA, CajaCobroForm.this.COD_CLIENTE, CajaCobroForm.this
                     );
                     if (entidad != null) {
                        if (CajaCobroForm.this.COD_MONEDA == 0) {
                           CajaCobroForm.this.COD_MONEDA = entidad.getCod_moneda();
                           CajaCobroForm.this.COD_CLIENTE = entidad.getCod_cliente();
                        }

                        Number valor_a_cobrar = (Number)CajaCobroForm.this.txt_total_cobrar.getValue();
                        Number valor_saldo = (Number)CajaCobroForm.this.txt_total_saldo.getValue();
                        Number valor_cobrado = (Number)CajaCobroForm.this.txt_total_cobrado.getValue();
                        double totalCobrar = valor_a_cobrar.doubleValue();
                        double totalCobrado = valor_cobrado.doubleValue();
                        double totalSaldo = valor_saldo.doubleValue();
                        totalCobrar += entidad.getImporte();
                        totalSaldo = totalCobrar - totalCobrado;
                        CajaCobroForm.this.txt_total_moneda1
                           .setValue(CotizacionesE.cambiarImporte(CajaCobroForm.this.COD_MONEDA, 1, totalSaldo, CajaCobroForm.this).getCotizacion());
                        CajaCobroForm.this.txt_total_moneda2
                           .setValue(CotizacionesE.cambiarImporte(CajaCobroForm.this.COD_MONEDA, 2, totalSaldo, CajaCobroForm.this).getCotizacion());
                        CajaCobroForm.this.txt_total_moneda3
                           .setValue(CotizacionesE.cambiarImporte(CajaCobroForm.this.COD_MONEDA, 3, totalSaldo, CajaCobroForm.this).getCotizacion());
                        CajaCobroForm.this.txt_total_moneda4
                           .setValue(CotizacionesE.cambiarImporte(CajaCobroForm.this.COD_MONEDA, 4, totalSaldo, CajaCobroForm.this).getCotizacion());
                     }
                  }
               }
            }
         );
      this.txt_total_moneda1 = new DecimalFilter(2);
      this.txt_total_moneda1.setFont(new Font("Arial", 0, 21));
      this.txt_total_moneda1.setEditable(false);
      this.txt_total_moneda2 = new DecimalFilter(2);
      this.txt_total_moneda2.setFont(new Font("Arial", 0, 21));
      this.txt_total_moneda2.setEditable(false);
      this.txt_total_moneda3 = new DecimalFilter(2);
      this.txt_total_moneda3.setFont(new Font("Arial", 0, 21));
      this.txt_total_moneda3.setEditable(false);
      this.txt_total_moneda4 = new DecimalFilter(2);
      this.txt_total_moneda4.setFont(new Font("Arial", 0, 21));
      this.txt_total_moneda4.setEditable(false);
      this.txt_total_cobrar = new DecimalFilter(2);
      this.txt_total_cobrar.setEditable(false);
      this.txt_total_cobrado = new DecimalFilter(2);
      this.txt_total_cobrado.setEditable(false);
      this.txt_total_saldo = new DecimalFilter(2);
      this.txt_total_saldo.setEditable(false);
      this.btnBuscarTicket = new BotonPadre("Ticket", "buscar.png");
      this.btnBuscarTicket.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            CajaCobroForm.this.buscarTickets();
         }
      });
      this.btnBuscarCredito = new BotonPadre("Credito", "buscar.png");
      this.btnBuscarCredito.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            CajaCobroForm.this.buscarCreditos();
         }
      });
      this.btnCancelar = new BotonPadre("Cancelar", "salir.png");
      this.btnCancelar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            CajaCobroForm.this.inicializarObjetos();
         }
      });
      this.tabla_documentos = new TablaDetalleDocumentosCobrarCaja(2, 2);
      JScrollPane scrollPaneDocumentosCobrar = new JScrollPane();
      scrollPaneDocumentosCobrar.setViewportView(this.tabla_documentos);
      BotonPadre btnAgregarLinea = new BotonPadre("Cancelar", "salir.png");
      btnAgregarLinea.addActionListener(e -> this.agregarLinea());
      BotonPadre btnMostrar = new BotonPadre("Cancelar", "salir.png");
      btnMostrar.addActionListener(e -> this.mostrarDatos());
      GroupLayout gl_panel_totales_cobrar = new GroupLayout(panel_totales_cobrar);
      gl_panel_totales_cobrar.setHorizontalGroup(
         gl_panel_totales_cobrar.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_totales_cobrar.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_totales_cobrar.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_totales_cobrar.createSequentialGroup()
                              .addGroup(
                                 gl_panel_totales_cobrar.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       gl_panel_totales_cobrar.createParallelGroup(Alignment.LEADING)
                                          .addComponent(lblNombreMoneda3, -2, 98, -2)
                                          .addComponent(lblNombreMoneda4, -2, 98, -2)
                                          .addComponent(lblNombreMoneda1, -2, 98, -2)
                                    )
                                    .addComponent(lblNombreMoneda2, -2, 98, -2)
                              )
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addGroup(
                                 gl_panel_totales_cobrar.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(this.txt_total_moneda4, -2, 146, -2)
                                    .addComponent(this.txt_total_moneda3, -2, 146, -2)
                                    .addComponent(this.txt_total_moneda2, -2, 146, -2)
                                    .addComponent(this.txt_total_moneda1, -2, 146, -2)
                              )
                        )
                        .addGroup(
                           gl_panel_totales_cobrar.createSequentialGroup()
                              .addComponent(btnAgregarLinea, -2, 100, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(btnMostrar, -2, 100, -2)
                        )
                  )
                  .addContainerGap(15, 32767)
            )
      );
      gl_panel_totales_cobrar.setVerticalGroup(
         gl_panel_totales_cobrar.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_totales_cobrar.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_totales_cobrar.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.txt_total_moneda1, -2, 43, -2)
                        .addComponent(lblNombreMoneda1, -2, 43, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_totales_cobrar.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.txt_total_moneda2, -2, 43, -2)
                        .addComponent(lblNombreMoneda2, -2, 43, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_totales_cobrar.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNombreMoneda3, -2, 43, -2)
                        .addComponent(this.txt_total_moneda3, -2, 43, -2)
                  )
                  .addGap(14)
                  .addGroup(
                     gl_panel_totales_cobrar.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNombreMoneda4, -2, 43, -2)
                        .addComponent(this.txt_total_moneda4, -2, 43, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                  .addGroup(
                     gl_panel_totales_cobrar.createParallelGroup(Alignment.LEADING)
                        .addComponent(btnAgregarLinea, -2, 26, -2)
                        .addComponent(btnMostrar, -2, 26, -2)
                  )
                  .addContainerGap()
            )
      );
      panel_totales_cobrar.setLayout(gl_panel_totales_cobrar);
      PanelPadre panel_opciones = new PanelPadre("Totales");
      GroupLayout gl_panel_opciones = new GroupLayout(panel_opciones);
      gl_panel_opciones.setHorizontalGroup(gl_panel_opciones.createParallelGroup(Alignment.LEADING).addGap(0, 1066, 32767));
      gl_panel_opciones.setVerticalGroup(gl_panel_opciones.createParallelGroup(Alignment.LEADING).addGap(0, 48, 32767));
      panel_opciones.setLayout(gl_panel_opciones);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup().addComponent(this.panel_detalle, -2, 1121, -2).addContainerGap())
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       Alignment.LEADING,
                                       groupLayout.createSequentialGroup()
                                          .addComponent(panel_detalles_registro, -2, 831, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(panel_totales_cobrar, -2, 282, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                    )
                                    .addComponent(panel, -1, 1108, 32767)
                                    .addComponent(panel_opciones, -1, 1108, 32767)
                              )
                              .addGap(141)
                        )
                  )
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_totales_cobrar, -2, 248, -2)
                        .addComponent(panel_detalles_registro, -2, 248, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.panel_detalle, -2, 186, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel, -2, 48, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_opciones, -2, 48, -2)
                  .addContainerGap()
            )
      );
      this.panel_detalle.setLayout(new BoxLayout(this.panel_detalle, 1));
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addComponent(lblTotalCobrar, -2, 87, -2)
                  .addGap(18)
                  .addComponent(this.txt_total_cobrar, -2, 168, -2)
                  .addGap(43)
                  .addComponent(lblTotalCobrado, -2, 101, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_cobrado, -2, 168, -2)
                  .addGap(18)
                  .addComponent(lblSaldo, -2, 101, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_saldo, -2, 168, -2)
                  .addGap(109)
            )
      );
      gl_panel.setVerticalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblTotalCobrar, -2, 21, -2)
                        .addComponent(this.txt_total_cobrar, -2, 33, -2)
                        .addComponent(lblTotalCobrado, -2, 21, -2)
                        .addComponent(this.txt_total_cobrado, -2, 33, -2)
                        .addComponent(lblSaldo, -2, 21, -2)
                        .addComponent(this.txt_total_saldo, -2, 33, -2)
                  )
                  .addContainerGap(93, 32767)
            )
      );
      panel.setLayout(gl_panel);
      GroupLayout gl_panel_detalles_registro = new GroupLayout(panel_detalles_registro);
      gl_panel_detalles_registro.setHorizontalGroup(
         gl_panel_detalles_registro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_detalles_registro.createSequentialGroup()
                  .addGap(3)
                  .addGroup(
                     gl_panel_detalles_registro.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPaneDocumentosCobrar, -2, 761, -2)
                        .addGroup(
                           gl_panel_detalles_registro.createSequentialGroup()
                              .addComponent(lblDigiteRegistro, -2, 160, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_registro_vta, -2, 132, -2)
                        )
                  )
                  .addGap(11)
            )
            .addGroup(
               Alignment.TRAILING,
               gl_panel_detalles_registro.createSequentialGroup().addContainerGap(436, 32767).addComponent(panel_botones, -2, 329, -2).addContainerGap()
            )
      );
      gl_panel_detalles_registro.setVerticalGroup(
         gl_panel_detalles_registro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_detalles_registro.createSequentialGroup()
                  .addGroup(
                     gl_panel_detalles_registro.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_detalles_registro.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 gl_panel_detalles_registro.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(this.txt_registro_vta, -2, 31, -2)
                                    .addComponent(lblDigiteRegistro, -2, 25, -2)
                              )
                        )
                        .addComponent(panel_botones, -2, 49, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPaneDocumentosCobrar, -2, 180, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_botones = new GroupLayout(panel_botones);
      gl_panel_botones.setHorizontalGroup(
         gl_panel_botones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_botones.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.btnBuscarTicket, -2, 92, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnBuscarCredito, -2, 96, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnCancelar, -2, 100, -2)
                  .addContainerGap(146, 32767)
            )
      );
      gl_panel_botones.setVerticalGroup(
         gl_panel_botones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_botones.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_botones.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.btnBuscarTicket, -2, 27, -2)
                        .addComponent(this.btnBuscarCredito, -2, 27, -2)
                        .addComponent(this.btnCancelar, -2, 26, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_botones.setLayout(gl_panel_botones);
      panel_detalles_registro.setLayout(gl_panel_detalles_registro);
      this.getContentPane().setLayout(groupLayout);
      if (!Beans.isDesignTime()) {
         this.inicializarObjetos();
         this.agregarLinea();
      }
   }

   private void inicializarObjetos() {
      this.txt_registro_vta.setValue("");
      this.txt_total_cobrado.setValue(0);
      this.txt_total_cobrar.setValue(0);
      this.txt_total_saldo.setValue(0);
      this.txt_total_moneda1.setValue(0);
      this.txt_total_moneda2.setValue(0);
      this.txt_total_moneda3.setValue(0);
      this.txt_total_moneda4.setValue(0);
      ModeloTablaDocumentosCobrarCaja modelo = (ModeloTablaDocumentosCobrarCaja)this.tabla_documentos.getModel();
      TablaDetalleDocumentosCobrarCaja.eliminarFilasConWhile(modelo);
      this.COD_CLIENTE = 0;
      this.COD_MONEDA = 0;
      this.txt_registro_vta.requestFocusInWindow();
   }

   private void buscarCreditos() {
      BuscadorCtasCobrar buscador = new BuscadorCtasCobrar(this.COD_CLIENTE, this.COD_MONEDA, this);
      buscador.setModal(true);
      buscador.setVisible(true);
      if (buscador != null) {
         List<Integer> cod_tipo_documentos = buscador.getCod_tipo_documentos();
         List<String> nombre_tipo_documento = buscador.getNombre_tipo_documentos();
         List<Integer> timbrados = buscador.getTimbrados();
         List<Integer> nroDocs = buscador.getNro_documentos();
         List<Integer> cuotas = buscador.getCuotas();
         List<Integer> nro_cuotas = buscador.getNro_cuotas();
         List<Double> importes = buscador.getImportes();
         List<Integer> cod_moneda = buscador.getCod_monedas();
         List<String> monedas = buscador.getMoneda_siglas();
         List<Integer> cod_cliente = buscador.getCod_clientes();
         List<Integer> primarios = buscador.getPrimarios();
         ModeloTablaDocumentosCobrarCaja model = (ModeloTablaDocumentosCobrarCaja)this.tabla_documentos.getModel();
         FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(2);

         for (int i = 0; i < cod_tipo_documentos.size(); i++) {
            model.addRow(
               new Object[]{
                  0,
                  cod_tipo_documentos.get(i),
                  nombre_tipo_documento.get(i),
                  timbrados.get(i),
                  nroDocs.get(i),
                  nro_cuotas.get(i),
                  cuotas.get(i),
                  formatoDecimalPrecio.format(importes.get(i)),
                  cod_moneda.get(i),
                  monedas.get(i),
                  cod_cliente.get(i),
                  primarios.get(i)
               }
            );
            if (this.COD_MONEDA == 0) {
               this.COD_MONEDA = cod_moneda.get(i);
               this.COD_CLIENTE = cod_cliente.get(i);
            }

            Number valor_a_cobrar = (Number)this.txt_total_cobrar.getValue();
            Number valor_saldo = (Number)this.txt_total_saldo.getValue();
            Number valor_cobrado = (Number)this.txt_total_cobrado.getValue();
            double totalCobrar = valor_a_cobrar.doubleValue();
            double totalCobrado = valor_cobrado.doubleValue();
            double totalSaldo = valor_saldo.doubleValue();
            totalCobrar += importes.get(i);
            totalSaldo = totalCobrar - totalCobrado;
            this.txt_total_moneda1.setValue(CotizacionesE.cambiarImporte(this.COD_MONEDA, 1, totalSaldo, this).getCotizacion());
            this.txt_total_moneda2.setValue(CotizacionesE.cambiarImporte(this.COD_MONEDA, 2, totalSaldo, this).getCotizacion());
            this.txt_total_moneda3.setValue(CotizacionesE.cambiarImporte(this.COD_MONEDA, 3, totalSaldo, this).getCotizacion());
            this.txt_total_moneda4.setValue(CotizacionesE.cambiarImporte(this.COD_MONEDA, 4, totalSaldo, this).getCotizacion());
         }

         this.txt_codigo.requestFocusInWindow();
      }
   }

   private void buscarTickets() {
      BuscadorCtasCobrar buscador = new BuscadorCtasCobrar(this.COD_CLIENTE, this.COD_MONEDA, "", this);
      buscador.setModal(true);
      buscador.setVisible(true);
      if (buscador != null) {
         List<Integer> cod_tipo_documentos = buscador.getCod_tipo_documentos();
         List<String> nombre_tipo_documento = buscador.getNombre_tipo_documentos();
         List<Integer> timbrados = buscador.getTimbrados();
         List<Integer> nroDocs = buscador.getNro_documentos();
         List<Integer> cuotas = buscador.getCuotas();
         List<Integer> nro_cuotas = buscador.getNro_cuotas();
         List<Double> importes = buscador.getImportes();
         List<Integer> cod_moneda = buscador.getCod_monedas();
         List<String> monedas = buscador.getMoneda_siglas();
         List<Integer> cod_cliente = buscador.getCod_clientes();
         List<Integer> primarios = buscador.getPrimarios();
         ModeloTablaDocumentosCobrarCaja model = (ModeloTablaDocumentosCobrarCaja)this.tabla_documentos.getModel();
         FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(2);

         for (int i = 0; i < cod_tipo_documentos.size(); i++) {
            model.addRow(
               new Object[]{
                  0,
                  cod_tipo_documentos.get(i),
                  nombre_tipo_documento.get(i),
                  timbrados.get(i),
                  nroDocs.get(i),
                  nro_cuotas.get(i),
                  cuotas.get(i),
                  formatoDecimalPrecio.format(importes.get(i)),
                  cod_moneda.get(i),
                  monedas.get(i),
                  cod_cliente.get(i),
                  primarios.get(i)
               }
            );
            if (this.COD_MONEDA == 0) {
               this.COD_MONEDA = cod_moneda.get(i);
               this.COD_CLIENTE = cod_cliente.get(i);
            }

            Number valor_a_cobrar = (Number)this.txt_total_cobrar.getValue();
            Number valor_saldo = (Number)this.txt_total_saldo.getValue();
            Number valor_cobrado = (Number)this.txt_total_cobrado.getValue();
            double totalCobrar = valor_a_cobrar.doubleValue();
            double totalCobrado = valor_cobrado.doubleValue();
            double totalSaldo = valor_saldo.doubleValue();
            totalCobrar += importes.get(i);
            totalSaldo = totalCobrar - totalCobrado;
            this.txt_total_moneda1.setValue(CotizacionesE.cambiarImporte(this.COD_MONEDA, 1, totalSaldo, this).getCotizacion());
            this.txt_total_moneda2.setValue(CotizacionesE.cambiarImporte(this.COD_MONEDA, 2, totalSaldo, this).getCotizacion());
            this.txt_total_moneda3.setValue(CotizacionesE.cambiarImporte(this.COD_MONEDA, 3, totalSaldo, this).getCotizacion());
            this.txt_total_moneda4.setValue(CotizacionesE.cambiarImporte(this.COD_MONEDA, 4, totalSaldo, this).getCotizacion());
         }

         this.txt_codigo.requestFocusInWindow();
      }
   }

   public void agregarLinea() {
      LineaPago linea = new LineaPago();
      PanelLineaPago[] ui = new PanelLineaPago[1];
      this.comboTipoPago = new JComboBox<>(
         new CondicionesE[]{new CondicionesE(1, "Efectivo", 0), new CondicionesE(2, "Tarjeta", 1), new CondicionesE(3, "Cheque", 2)}
      );
      this.comboMonedas_efectivo = new JComboBox<>(new MonedasE[]{new MonedasE(1, "Guaranies"), new MonedasE(2, "Dolares"), new MonedasE(3, "Reales")});
      this.comboMonedaVuelto = new JComboBox<>(new MonedasE[]{new MonedasE(1, "Guaranies"), new MonedasE(2, "Dolares"), new MonedasE(3, "Reales")});
      this.comboMonedas_tarjeta = new JComboBox<>(new MonedasE[]{new MonedasE(1, "Guaranies"), new MonedasE(2, "Dolares"), new MonedasE(3, "Reales")});
      this.comboTarjetas = new JComboBox<>(new TarjetasE[]{new TarjetasE(1, "Visa"), new TarjetasE(2, "Mastercard"), new TarjetasE(3, "American Express")});
      this.comboMonedas_cheque = new JComboBox<>(new MonedasE[]{new MonedasE(1, "Guaranies"), new MonedasE(2, "Dolares"), new MonedasE(3, "Reales")});
      this.comboBancos = new JComboBox<>(new BancosE[]{new BancosE(1, "Sudameris"), new BancosE(2, "UENO"), new BancosE(3, "Familiar")});
      ui[0] = new PanelLineaPago(
         linea,
         () -> this.eliminarLinea(linea, ui[0]),
         this.comboTipoPago,
         this.comboMonedas_efectivo,
         this.comboMonedaVuelto,
         this.comboMonedas_tarjeta,
         this.comboTarjetas,
         this.comboMonedas_cheque,
         this.comboBancos
      );
      this.lineas.add(linea);
      this.panel_detalle.add(ui[0]);
      this.panel_detalle.revalidate();
      this.panel_detalle.repaint();
   }

   private void eliminarLinea(LineaPago linea, PanelLineaPago ui) {
      this.lineas.remove(linea);
      this.panel_detalle.remove(ui);
      this.panel_detalle.revalidate();
      this.panel_detalle.repaint();
   }

   private void mostrarDatos() {
      System.out.println("---- DATOS CAPTURADOS ----");
      int idx = 1;

      for (LineaPago l : this.lineas) {
         System.out.println("COD CONDICION: " + l.cod_condicion_pago + " cod_tipo_pago: " + l.cod_tipo_pago + " cod_moneda_cobro" + l.cod_moneda_cobro);
         System.out.println("cotizacion: " + l.cotizacion + " importeACobrar: " + l.importeACobrar + " importeCobrado " + l.importeCobrado);
         System.out.println("cod_monedaVuelto " + l.cod_monedaVuelto + " importeVuelto " + l.importeVuelto + " cotizacionVuelto " + l.cotizacionVuelto);
         System.out.println("cod_tarjeta " + l.cod_tarjeta + " baucher" + l.baucher + " cod_banco " + l.cod_banco + " nroCuenta " + l.nroCuenta);
         System.out
            .println("nroCheque " + l.nroCheque + " fechaEmision " + l.fechaEmision + " fechaVencimiento " + l.fechaVencimiento + " librador " + l.librador);
      }
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CajaCobroForm frame = new CajaCobroForm();

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
