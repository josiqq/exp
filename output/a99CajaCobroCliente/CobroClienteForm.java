package a99CajaCobroCliente;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import utilidades.CuadroTexto2Decimales;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextField;
import utilidades.LimiteTextFieldConSQL;
import utilidades.PanelPadre;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class CobroClienteForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(25);
   private static final long serialVersionUID = 1L;
   private JDateChooser txt_fecha;
   private LabelPrincipal lblNombreMoneda;
   private LabelPrincipal lblNombreCajero;
   private LabelPrincipal lblNombreCobrador;
   private LabelPrincipal lblNombreCliente;
   private LimiteTextFieldConSQL txt_cod_cliente;
   private LimiteTextFieldConSQL txt_cod_cobrador;
   private LimiteTextFieldConSQL txt_cod_cajero;
   private LimiteTextFieldConSQL txt_cod_moneda;
   private LimiteTextArea txt_observacion;
   private LimiteTextField txt_recibo;
   private CuadroTexto2Decimales txt_total_saldo;
   private CuadroTexto2Decimales txt_total_cobrado;
   private CuadroTexto2Decimales txt_total_a_cobrar;
   private TablaDocumentoCobroCliente tabla_documento;
   private TablaDetalleCobroCliente tabla_detalle;
   List<Integer> codigosAEliminarDocumento = new ArrayList<>();
   List<Integer> codigosAEliminarDetalle = new ArrayList<>();

   private void buscarIni() {
   }

   private void deshabilitarCabecera() {
      this.txt_cod_cajero.setEnabled(false);
      this.txt_cod_cliente.setEnabled(false);
      this.txt_cod_cobrador.setEnabled(false);
      this.txt_cod_moneda.setEnabled(false);
   }

   private void inicializarObjetos() {
      this.buscarIni();
      this.txt_codigo.setValue(0);
      this.txt_cod_cajero.setValue(0);
      this.lblNombreCajero.setText("");
      this.txt_cod_cliente.setValue(0);
      this.lblNombreCliente.setText("");
      this.txt_cod_cobrador.setValue(0);
      this.lblNombreCobrador.setText("");
      this.txt_cod_moneda.setValue(0);
      this.lblNombreMoneda.setText("");
      this.txt_fecha.setDate(null);
      this.txt_observacion.setText("");
      this.txt_recibo.setValue("");
      this.txt_total_a_cobrar.setValue(0);
      this.txt_total_cobrado.setValue(0);
      this.txt_total_saldo.setValue(0);
      this.codigosAEliminarDocumento.clear();
      this.codigosAEliminarDetalle.clear();
      TablaDocumentoCobroCliente.eliminarFilas(this.tabla_documento);
      ModeloTablaCobroClienteDocumento modelo = (ModeloTablaCobroClienteDocumento)this.tabla_documento.getModel();
      modelo.addDefaultRow();
      ModeloTablaCobroClienteDetalle modelo2 = (ModeloTablaCobroClienteDetalle)this.tabla_detalle.getModel();
      modelo2.addDefaultRow();
   }

   public CobroClienteForm() {
      this.setTitle("Registro de Cobros a Clientes");
      this.setBounds(100, 100, 1272, 667);
      PanelPadre contentPane = new PanelPadre("");
      PanelPadre panel_cabecera = new PanelPadre("");
      PanelPadre panel_detalle_documentos = new PanelPadre("Documentos a Cobrar");
      PanelPadre panel_detalle_cobros = new PanelPadre("Detalles de Cobro");
      PanelPadre panel_totales = new PanelPadre("Totales");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      LabelPrincipal lblTotalCobrar = new LabelPrincipal("Total a Cobrar", "label");
      LabelPrincipal lblTotalCobrado = new LabelPrincipal("Total Cobrado", "label");
      LabelPrincipal lblSaldo = new LabelPrincipal("Saldo", "label");
      LabelPrincipal lblNroRegistro = new LabelPrincipal("Nro. Registro", "label");
      LabelPrincipal lblCliente = new LabelPrincipal("Cliente", "label");
      LabelPrincipal lblMoneda = new LabelPrincipal("Moneda", "label");
      LabelPrincipal lblCajero = new LabelPrincipal("Cajero", "label");
      LabelPrincipal lblCobrador = new LabelPrincipal("Cobrador", "label");
      LabelPrincipal lblObservacion = new LabelPrincipal("Observacion", "label");
      LabelPrincipal lblRecibo = new LabelPrincipal("Recibo", "label");
      LabelPrincipal lblFecha = new LabelPrincipal("Fecha", "label");
      this.lblNombreCajero = new LabelPrincipal(0);
      this.lblNombreMoneda = new LabelPrincipal(0);
      this.lblNombreCobrador = new LabelPrincipal(0);
      this.lblNombreCliente = new LabelPrincipal(0);
      this.txt_total_saldo = new CuadroTexto2Decimales(2);
      this.txt_total_cobrado = new CuadroTexto2Decimales(2);
      this.txt_total_a_cobrar = new CuadroTexto2Decimales(2);
      this.txt_cod_cliente = new LimiteTextFieldConSQL(999999, this.lblNombreCliente, "Clientes", this);
      this.txt_cod_moneda = new LimiteTextFieldConSQL(999999, this.lblNombreMoneda, "Monedas", this);
      this.txt_cod_cajero = new LimiteTextFieldConSQL(999999, this.lblNombreCajero, "Cajeros", this);
      this.txt_cod_cobrador = new LimiteTextFieldConSQL(999999, this.lblNombreCobrador, "Cobradores", this);
      this.txt_fecha = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_recibo = new LimiteTextField(25);
      this.txt_observacion = new LimiteTextArea(70);
      this.tabla_documento = new TablaDocumentoCobroCliente(this.codigosAEliminarDocumento, 2, 2);
      JScrollPane scrollPane_documentos = new JScrollPane();
      scrollPane_documentos.setViewportView(this.tabla_documento);
      this.tabla_detalle = new TablaDetalleCobroCliente(this.codigosAEliminarDetalle, 2, 2);
      JScrollPane scrollPane_detalle = new JScrollPane();
      scrollPane_detalle.setViewportView(this.tabla_detalle);
      GroupLayout gl_panel_totales = new GroupLayout(panel_totales);
      gl_panel_totales.setHorizontalGroup(
         gl_panel_totales.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_totales.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_totales.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblTotalCobrar, -2, 123, -2)
                        .addComponent(lblTotalCobrado, -2, 123, -2)
                        .addComponent(lblSaldo, -2, 123, -2)
                        .addGroup(
                           gl_panel_totales.createParallelGroup(Alignment.TRAILING, false)
                              .addComponent(this.txt_total_saldo, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.txt_total_cobrado, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.txt_total_a_cobrar, Alignment.LEADING, -1, 233, 32767)
                        )
                  )
                  .addContainerGap(17, 32767)
            )
      );
      gl_panel_totales.setVerticalGroup(
         gl_panel_totales.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_totales.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblTotalCobrar, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_a_cobrar, -2, 31, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalCobrado, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_cobrado, -2, 31, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblSaldo, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_saldo, -2, 31, -2)
                  .addContainerGap(204, 32767)
            )
      );
      panel_totales.setLayout(gl_panel_totales);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.TRAILING)
            .addComponent(panel_detalle_cobros, -2, 1246, -2)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panel_cabecera, -2, 960, -2)
                        .addComponent(panel_detalle_documentos, -2, 960, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_totales, -2, 270, -2)
                  .addContainerGap()
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_contentPane.createSequentialGroup()
                              .addComponent(panel_cabecera, -2, 201, -2)
                              .addGap(18)
                              .addComponent(panel_detalle_documentos, -2, 203, -2)
                        )
                        .addComponent(panel_totales, -2, 422, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle_cobros, -2, 191, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_detalle_cobros = new GroupLayout(panel_detalle_cobros);
      gl_panel_detalle_cobros.setHorizontalGroup(
         gl_panel_detalle_cobros.createParallelGroup(Alignment.TRAILING)
            .addGroup(gl_panel_detalle_cobros.createSequentialGroup().addContainerGap().addComponent(scrollPane_detalle, -1, 1216, 32767).addContainerGap())
      );
      gl_panel_detalle_cobros.setVerticalGroup(
         gl_panel_detalle_cobros.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle_cobros.createSequentialGroup().addComponent(scrollPane_detalle, -2, 157, -2).addContainerGap(11, 32767))
      );
      panel_detalle_cobros.setLayout(gl_panel_detalle_cobros);
      GroupLayout gl_panel_detalle_documentos = new GroupLayout(panel_detalle_documentos);
      gl_panel_detalle_documentos.setHorizontalGroup(
         gl_panel_detalle_documentos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_detalle_documentos.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(scrollPane_documentos, -2, 940, -2)
                  .addContainerGap(68, 32767)
            )
      );
      gl_panel_detalle_documentos.setVerticalGroup(
         gl_panel_detalle_documentos.createParallelGroup(Alignment.LEADING).addComponent(scrollPane_documentos, -1, 180, 32767)
      );
      panel_detalle_documentos.setLayout(gl_panel_detalle_documentos);
      GroupLayout gl_panel_cabecera = new GroupLayout(panel_cabecera);
      gl_panel_cabecera.setHorizontalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                              .addComponent(lblCliente, -1, -1, 32767)
                              .addComponent(lblNroRegistro, -1, 86, 32767)
                              .addComponent(lblCajero, -2, 86, -2)
                        )
                        .addComponent(lblObservacion, -2, 86, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                                          .addComponent(this.txt_cod_cliente, -1, -1, 32767)
                                          .addComponent(this.txt_codigo, -1, 66, 32767)
                                    )
                                    .addComponent(this.txt_cod_cajero, -2, 66, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                                          .addComponent(this.lblNombreCliente, -1, -1, 32767)
                                          .addGroup(
                                             gl_panel_cabecera.createSequentialGroup()
                                                .addGap(209)
                                                .addComponent(lblFecha, -2, 45, -2)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.txt_fecha, -2, 102, -2)
                                          )
                                    )
                                    .addComponent(this.lblNombreCajero, -2, 200, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                                          .addComponent(lblRecibo, -2, 50, -2)
                                          .addComponent(lblMoneda, -1, 56, 32767)
                                    )
                                    .addComponent(lblCobrador, -1, 56, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.txt_recibo, -2, 149, -2)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.TRAILING, false)
                                          .addGroup(
                                             gl_panel_cabecera.createSequentialGroup()
                                                .addComponent(this.txt_cod_cobrador, -2, 72, -2)
                                                .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                                                .addComponent(this.lblNombreCobrador, -2, 200, -2)
                                          )
                                          .addGroup(
                                             gl_panel_cabecera.createSequentialGroup()
                                                .addComponent(this.txt_cod_moneda, -2, 53, -2)
                                                .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                                                .addComponent(this.lblNombreMoneda, -2, 200, -2)
                                          )
                                    )
                              )
                              .addGap(126)
                        )
                        .addGroup(gl_panel_cabecera.createSequentialGroup().addComponent(this.txt_observacion, -2, 772, -2).addContainerGap())
                  )
            )
      );
      gl_panel_cabecera.setVerticalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblRecibo, -2, 25, -2)
                              .addComponent(this.txt_recibo, -2, 31, -2)
                        )
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       gl_panel_cabecera.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(lblNroRegistro, -2, 25, -2)
                                                .addComponent(this.txt_codigo, -2, 31, -2)
                                          )
                                          .addGap(12)
                                    )
                                    .addGroup(
                                       gl_panel_cabecera.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(lblFecha, -2, 25, -2)
                                                .addComponent(this.txt_fecha, -2, 31, -2)
                                          )
                                          .addGap(18)
                                    )
                              )
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblCliente, -2, 25, -2)
                                          .addComponent(this.txt_cod_cliente, -2, 31, -2)
                                    )
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                                          .addComponent(this.lblNombreCliente, -2, 28, -2)
                                          .addGroup(
                                             gl_panel_cabecera.createSequentialGroup()
                                                .addGroup(
                                                   gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                                      .addComponent(this.lblNombreMoneda, -2, 28, -2)
                                                      .addGroup(
                                                         gl_panel_cabecera.createSequentialGroup()
                                                            .addPreferredGap(ComponentPlacement.RELATED)
                                                            .addGroup(
                                                               gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                                                                  .addComponent(this.txt_cod_moneda, -2, 31, -2)
                                                                  .addComponent(lblMoneda, -2, 25, -2)
                                                            )
                                                      )
                                                )
                                                .addGap(3)
                                          )
                                    )
                              )
                        )
                  )
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addGap(6)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(this.txt_cod_cajero, -2, 31, -2)
                                          .addComponent(lblCajero, -2, 25, -2)
                                    )
                                    .addComponent(this.lblNombreCajero, -2, 28, -2)
                              )
                        )
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.txt_cod_cobrador, -2, 31, -2)
                                    .addComponent(this.lblNombreCobrador, -2, 28, -2)
                              )
                        )
                        .addGroup(gl_panel_cabecera.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(lblCobrador, -2, 25, -2))
                  )
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(gl_panel_cabecera.createSequentialGroup().addGap(27).addComponent(lblObservacion, -2, 25, -2))
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_observacion, -1, -1, 32767)
                        )
                  )
                  .addContainerGap()
            )
      );
      panel_cabecera.setLayout(gl_panel_cabecera);
      contentPane.setLayout(gl_contentPane);
      this.tabla_documento.getInputMap(1).put(KeyStroke.getKeyStroke(112, 0), "F1");
      this.tabla_documento
         .getActionMap()
         .put(
            "F1",
            new AbstractAction() {
               private static final long serialVersionUID = 1L;

               @Override
               public void actionPerformed(ActionEvent e) {
                  if (CobroClienteForm.this.tabla_documento.getSelectedColumn() == 0) {
                     BuscadorCtasCobrar buscador = new BuscadorCtasCobrar(
                        Integer.parseInt(CobroClienteForm.this.txt_cod_cliente.getText().trim().replace(".", "")),
                        Integer.parseInt(CobroClienteForm.this.txt_cod_moneda.getText().trim().replace(".", "")),
                        CobroClienteForm.this
                     );
                     buscador.setModal(true);
                     buscador.setVisible(true);
                     if (buscador != null) {
                        List<Integer> cod_tipo_documentos = buscador.getCod_tipo_documentos();
                        List<String> nombre_tipo_documento = buscador.getNombre_tipo_documentos();
                        List<Integer> timbrados = buscador.getTimbrados();
                        List<Integer> nroDocs = buscador.getNro_documentos();
                        List<Integer> cuotas = buscador.getCuotas();
                        List<Double> importes = buscador.getImportes();
                        List<String> monedas = buscador.getMoneda_siglas();
                        List<Integer> primarios = buscador.getPrimarios();
                        ModeloTablaCobroClienteDocumento model = (ModeloTablaCobroClienteDocumento)CobroClienteForm.this.tabla_documento.getModel();

                        for (int i = 0; i < cod_tipo_documentos.size(); i++) {
                           if (i == 0) {
                              if (CobroClienteForm.this.tabla_documento.isEditing()) {
                                 CobroClienteForm.this.tabla_documento.getCellEditor().stopCellEditing();
                              }

                              CobroClienteForm.this.tabla_documento
                                 .setValueAt(
                                    cod_tipo_documentos.get(i),
                                    CobroClienteForm.this.tabla_documento.getSelectedRow(),
                                    CobroClienteForm.this.tabla_documento.getColumn("Cod Tipo Doc").getModelIndex()
                                 );
                              CobroClienteForm.this.tabla_documento
                                 .setValueAt(
                                    nombre_tipo_documento.get(i),
                                    CobroClienteForm.this.tabla_documento.getSelectedRow(),
                                    CobroClienteForm.this.tabla_documento.getColumn("Tipo Documento").getModelIndex()
                                 );
                              CobroClienteForm.this.tabla_documento
                                 .setValueAt(
                                    timbrados.get(i),
                                    CobroClienteForm.this.tabla_documento.getSelectedRow(),
                                    CobroClienteForm.this.tabla_documento.getColumn("Timbrado").getModelIndex()
                                 );
                              CobroClienteForm.this.tabla_documento
                                 .setValueAt(
                                    nroDocs.get(i),
                                    CobroClienteForm.this.tabla_documento.getSelectedRow(),
                                    CobroClienteForm.this.tabla_documento.getColumn("Nro. Documento").getModelIndex()
                                 );
                              CobroClienteForm.this.tabla_documento
                                 .setValueAt(
                                    cuotas.get(i),
                                    CobroClienteForm.this.tabla_documento.getSelectedRow(),
                                    CobroClienteForm.this.tabla_documento.getColumn("Cuota").getModelIndex()
                                 );
                              CobroClienteForm.this.tabla_documento
                                 .setValueAt(
                                    importes.get(i),
                                    CobroClienteForm.this.tabla_documento.getSelectedRow(),
                                    CobroClienteForm.this.tabla_documento.getColumn("Importe").getModelIndex()
                                 );
                              CobroClienteForm.this.tabla_documento
                                 .setValueAt(
                                    monedas.get(i),
                                    CobroClienteForm.this.tabla_documento.getSelectedRow(),
                                    CobroClienteForm.this.tabla_documento.getColumn("Moneda").getModelIndex()
                                 );
                              CobroClienteForm.this.tabla_documento
                                 .setValueAt(
                                    primarios.get(i),
                                    CobroClienteForm.this.tabla_documento.getSelectedRow(),
                                    CobroClienteForm.this.tabla_documento.getColumn("Primario").getModelIndex()
                                 );
                              CobroClienteForm.this.tabla_documento
                                 .setValueAt(
                                    primarios.get(i),
                                    CobroClienteForm.this.tabla_documento.getSelectedRow(),
                                    CobroClienteForm.this.tabla_documento.getColumn("SW").getModelIndex()
                                 );
                           } else {
                              model.addRow(
                                 new Object[]{
                                    cod_tipo_documentos.get(i),
                                    nombre_tipo_documento.get(i),
                                    timbrados.get(i),
                                    nroDocs.get(i),
                                    cuotas.get(i),
                                    importes.get(i),
                                    monedas.get(i),
                                    primarios.get(i),
                                    primarios.get(i)
                                 }
                              );
                           }
                        }

                        model.addDefaultRow();
                        CobroClienteForm.this.tabla_documento.requestFocusInWindow();
                        int ultimaFila = CobroClienteForm.this.tabla_documento.getRowCount() - 1;
                        CobroClienteForm.this.tabla_documento
                           .changeSelection(ultimaFila, CobroClienteForm.this.tabla_documento.getColumn("Cod Tipo Doc").getModelIndex(), false, false);
                        CobroClienteForm.this.tabla_documento
                           .editCellAt(ultimaFila, CobroClienteForm.this.tabla_documento.getColumn("Cod Tipo Doc").getModelIndex());
                        Component editorComponent = CobroClienteForm.this.tabla_documento.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     }
                  }
               }
            }
         );
      this.inicializarObjetos();
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CobroClienteForm frame = new CobroClienteForm();

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
