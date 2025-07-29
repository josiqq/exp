package a0098NotaCreditoCompra;

import a00Compras.ComprasE;
import a00pedidosProveedores.DecimalFilter;
import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import java.awt.Font;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.BotonPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextField;
import utilidades.LimiteTextFieldConSQL;
import utilidades.NumerosTextField;
import utilidades.PanelPadre;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class NotaCreditoCompraForm extends JinternalPadre {
   LimiteTextField txt_nro_registro = new LimiteTextField(25);
   private Integer SW;
   private List<Integer> codigosAEliminar = new ArrayList<>();
   private TablaDetalleComprasNC tabla;
   private static final long serialVersionUID = 1L;
   private JDateChooser txt_fecha_documento;
   private JDateChooser txt_fecha_proceso;
   private LimiteTextField txt_nro_documento;
   private LimiteTextField txt_nro_factura;
   private LimiteTextFieldConSQL txt_cod_motivo;
   private LimiteTextFieldConSQL txt_cod_deposito;
   private LimiteTextFieldConSQL txt_cod_proveedor;
   private LimiteTextFieldConSQL txt_cod_tipo_documento_nc;
   private LimiteTextFieldConSQL txt_cod_tipo_documento_compra;
   private NumerosTextField txt_timbrado;
   private LabelPrincipal lblUltimoRegistroTexto;
   private LabelPrincipal lblNombreMotivo;
   private LabelPrincipal lblNombreDeposito;
   private LabelPrincipal lblNombreProveedor;
   private LabelPrincipal lblNombreMoneda;
   private LabelPrincipal lblTipoDocumentoCompra;
   private LabelPrincipal lblNombreTipoDocumentoNC;
   private LimiteTextFieldConSQL txt_cod_moneda;
   private LimiteTextArea txt_obsevacion;
   private DecimalFilter txt_total_impuesto;
   private DecimalFilter txt_iva5;
   private DecimalFilter txt_iva10;
   private DecimalFilter txt_exentas;
   private DecimalFilter txt_gravado5;
   private DecimalFilter txt_gravado10;
   private DecimalFilter txt_total_general;
   private BotonPadre btnBuscarCompras;
   private LabelPrincipal lblLineasTotalRecuperadasTexto;

   private void inicializarObjetos() {
      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblUltimoRegistroTexto.setText(String.valueOf(formatoLinea.format(ComprasE.ultimoRegistro(this))));
      this.SW = 0;
      ModeloTablaComprasNC modelo = (ModeloTablaComprasNC)this.tabla.getModel();
      modelo.deleteRow(this.tabla);
      modelo.addDefaultRow();
      this.txt_cod_deposito.setValue(0);
      this.lblNombreDeposito.setText("");
      this.txt_cod_moneda.setValue(0);
      this.lblNombreMoneda.setText("");
      this.txt_cod_motivo.setValue(0);
      this.lblNombreMotivo.setText("");
      this.txt_cod_proveedor.setValue(0);
      this.lblNombreProveedor.setText("");
      this.txt_cod_tipo_documento_compra.setValue(0);
      this.lblTipoDocumentoCompra.setText("");
      this.txt_cod_tipo_documento_nc.setValue(0);
      this.lblNombreTipoDocumentoNC.setText("");
      this.txt_exentas.setValue(0);
      this.txt_fecha_documento.setDate(null);
      this.txt_fecha_proceso.setDate(null);
      this.txt_gravado10.setValue(0);
      this.txt_gravado5.setValue(0);
      this.txt_iva10.setValue(0);
      this.txt_iva5.setValue(0);
      this.txt_nro_documento.setValue("");
      this.txt_nro_factura.setValue("");
      this.txt_nro_registro.setValue(0);
      this.txt_obsevacion.setText("");
      this.txt_timbrado.setValue(0);
      this.txt_total_impuesto.setValue(0);
   }

   public NotaCreditoCompraForm() {
      this.setTitle("Registro de Nota de Credito Compras");
      this.setBounds(100, 100, 1174, 549);
      PanelPadre contentPane = new PanelPadre("");
      PanelPadre panel_cabecera = new PanelPadre("");
      PanelPadre panel_titulo = new PanelPadre("");
      PanelPadre panel_detalle = new PanelPadre("Detalle de Productos");
      PanelPadre panel_compra = new PanelPadre("Detalle Factura Compra");
      PanelPadre panel_nc = new PanelPadre("Detalle NC");
      PanelPadre panel_impuestos = new PanelPadre("Totales");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      LabelPrincipal lblGravada5 = new LabelPrincipal("Gravada 5%", "label");
      LabelPrincipal lblGravada10 = new LabelPrincipal("Gravada 10%", "label");
      LabelPrincipal lblExentas = new LabelPrincipal("Exentas", "label");
      LabelPrincipal lblIva10 = new LabelPrincipal("IVA 10%", "label");
      LabelPrincipal lblIva5 = new LabelPrincipal("IVA 5%", "label");
      LabelPrincipal lblTotal = new LabelPrincipal("Total", "label");
      LabelPrincipal lblTipoDocumentoVta_1 = new LabelPrincipal("Tipo Documento", "label");
      LabelPrincipal lblFechaProceso = new LabelPrincipal("Fecha Proceso", "label");
      LabelPrincipal lblObservacion = new LabelPrincipal("Observacion", "label");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro", "lineas");
      LabelPrincipal lblMotivo = new LabelPrincipal("Motivo", "label");
      LabelPrincipal lblMoneda = new LabelPrincipal("Moneda", "label");
      LabelPrincipal lblNroFactura = new LabelPrincipal("Nro. Factura Vta.", "label");
      LabelPrincipal lblTipoDocumentoVta = new LabelPrincipal("Tipo Documento", "label");
      LabelPrincipal lblProveedor = new LabelPrincipal("Proveedor", "label");
      LabelPrincipal lblDeposito = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblNroRegistro = new LabelPrincipal("Nro. Registro", "label");
      LabelPrincipal lblTimbrado = new LabelPrincipal("Timbrado", "label");
      LabelPrincipal lblFechaDocumento = new LabelPrincipal("Fecha Doc", "label");
      LabelPrincipal lblNroDocumento = new LabelPrincipal("Nro. Documento", "label");
      LabelPrincipal lblTextoLineaRecuperadas = new LabelPrincipal("Total Lineas:", "lineas");
      this.lblLineasTotalRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreDeposito = new LabelPrincipal(0);
      this.lblNombreTipoDocumentoNC = new LabelPrincipal(0);
      this.lblNombreMotivo = new LabelPrincipal(0);
      this.lblNombreMoneda = new LabelPrincipal(0);
      this.lblTipoDocumentoCompra = new LabelPrincipal(0);
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreProveedor = new LabelPrincipal(0);
      this.txt_fecha_documento = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_proceso = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_timbrado = new NumerosTextField(99999999L);
      this.txt_cod_tipo_documento_nc = new LimiteTextFieldConSQL(999999, null, "Monedas", null);
      this.txt_cod_motivo = new LimiteTextFieldConSQL(999999, this.lblNombreMotivo, "MotivosNC", this);
      this.txt_cod_deposito = new LimiteTextFieldConSQL(999999, this.lblNombreDeposito, "Depositos", this);
      this.txt_cod_tipo_documento_compra = new LimiteTextFieldConSQL(999999, this.lblTipoDocumentoCompra, "Monedas", this);
      this.txt_cod_moneda = new LimiteTextFieldConSQL(999999, this.lblNombreMoneda, "Monedas", this);
      this.txt_cod_proveedor = new LimiteTextFieldConSQL(999999, this.lblNombreProveedor, "Proveedores", this);
      this.txt_nro_documento = new LimiteTextField(25);
      this.txt_nro_factura = new LimiteTextField(25);
      this.txt_obsevacion = new LimiteTextArea(70);
      this.txt_gravado10 = new DecimalFilter(2);
      this.txt_gravado10.setFont(new Font("Arial", 0, 14));
      this.txt_gravado10.setEditable(false);
      this.txt_gravado5 = new DecimalFilter(2);
      this.txt_gravado5.setFont(new Font("Arial", 0, 14));
      this.txt_gravado5.setEditable(false);
      this.txt_exentas = new DecimalFilter(2);
      this.txt_exentas.setFont(new Font("Arial", 0, 14));
      this.txt_exentas.setEditable(false);
      this.txt_iva10 = new DecimalFilter(2);
      this.txt_iva10.setFont(new Font("Arial", 0, 14));
      this.txt_iva10.setEditable(false);
      this.txt_iva5 = new DecimalFilter(2);
      this.txt_iva5.setFont(new Font("Arial", 0, 14));
      this.txt_iva5.setEditable(false);
      this.txt_total_impuesto = new DecimalFilter(2);
      this.txt_total_impuesto.setFont(new Font("Arial", 0, 21));
      this.txt_total_impuesto.setEditable(false);
      this.txt_total_general = new DecimalFilter(2);
      this.txt_total_general.setEditable(false);
      this.btnBuscarCompras = new BotonPadre("", "buscar.png");
      this.tabla = new TablaDetalleComprasNC(
         this.txt_total_general,
         this.txt_exentas,
         this.txt_gravado10,
         this.txt_iva10,
         this.txt_gravado5,
         this.txt_iva5,
         this.txt_total_impuesto,
         this.lblLineasTotalRecuperadasTexto,
         2,
         2,
         this.codigosAEliminar
      );
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout gl_panel_impuestos = new GroupLayout(panel_impuestos);
      gl_panel_impuestos.setHorizontalGroup(
         gl_panel_impuestos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_impuestos.createSequentialGroup()
                  .addGroup(
                     gl_panel_impuestos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_impuestos.createSequentialGroup()
                              .addComponent(lblGravada10, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_gravado10, -2, 120, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblGravada5, -2, 71, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_gravado5, -2, 120, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblExentas, -2, 45, -2)
                              .addGap(2)
                              .addComponent(this.txt_exentas, -2, 120, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblIva10, -2, 51, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_iva10, -2, 120, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblIva5, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_iva5, -2, 120, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblTotal, -2, 32, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_total_impuesto, -2, 158, -2)
                        )
                        .addGroup(
                           gl_panel_impuestos.createSequentialGroup()
                              .addComponent(lblTextoLineaRecuperadas, -2, 89, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasTotalRecuperadasTexto, -2, 69, -2)
                        )
                  )
                  .addContainerGap(39, 32767)
            )
      );
      gl_panel_impuestos.setVerticalGroup(
         gl_panel_impuestos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_impuestos.createSequentialGroup()
                  .addGroup(
                     gl_panel_impuestos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_impuestos.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 gl_panel_impuestos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_impuestos.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblGravada10, -2, 25, -2)
                                          .addComponent(lblGravada5, -2, 25, -2)
                                    )
                                    .addGroup(
                                       gl_panel_impuestos.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblExentas, -2, 25, -2)
                                          .addComponent(lblIva10, -2, 25, -2)
                                    )
                              )
                        )
                        .addComponent(this.txt_gravado10, -2, 25, -2)
                        .addComponent(this.txt_gravado5, -2, 25, -2)
                        .addComponent(this.txt_exentas, -2, 25, -2)
                        .addGroup(
                           gl_panel_impuestos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_iva10, -2, 25, -2)
                              .addComponent(lblIva5, -2, 25, -2)
                              .addComponent(this.txt_iva5, -2, 25, -2)
                              .addComponent(lblTotal, -2, 25, -2)
                              .addComponent(this.txt_total_impuesto, -2, 25, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_impuestos.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblTextoLineaRecuperadas, -2, 17, -2)
                        .addComponent(this.lblLineasTotalRecuperadasTexto, -2, 17, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_impuestos.setLayout(gl_panel_impuestos);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_contentPane.createSequentialGroup().addComponent(panel_cabecera, 0, 0, 32767).addGap(127))
            .addGroup(gl_contentPane.createSequentialGroup().addComponent(panel_detalle, -2, 1156, -2).addContainerGap())
            .addGroup(gl_contentPane.createSequentialGroup().addComponent(panel_impuestos, -2, 1156, -2).addContainerGap())
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_cabecera, -2, 190, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -2, 238, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_impuestos, -1, 69, 32767)
            )
      );
      GroupLayout gl_panel_nc = new GroupLayout(panel_nc);
      gl_panel_nc.setHorizontalGroup(
         gl_panel_nc.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_nc.createSequentialGroup()
                  .addComponent(lblTipoDocumentoVta_1, -2, 100, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.txt_cod_tipo_documento_nc, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreTipoDocumentoNC, -2, 200, -2)
                  .addGap(35)
                  .addComponent(lblNroDocumento, -2, 95, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_nro_documento, -2, 129, -2)
                  .addGap(26)
                  .addComponent(lblFechaDocumento, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.txt_fecha_documento, -2, 96, -2)
                  .addGap(18)
                  .addComponent(lblTimbrado, -2, 63, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_timbrado, -2, 151, -2)
                  .addContainerGap(116, 32767)
            )
      );
      gl_panel_nc.setVerticalGroup(
         gl_panel_nc.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_nc.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_nc.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_nc.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblNroDocumento, -2, 25, -2)
                              .addComponent(this.txt_nro_documento, -2, 25, -2)
                              .addComponent(lblFechaDocumento, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreTipoDocumentoNC, -2, 25, -2)
                        .addGroup(
                           gl_panel_nc.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblTipoDocumentoVta_1, -2, 25, -2)
                              .addComponent(this.txt_cod_tipo_documento_nc, -2, 25, -2)
                        )
                        .addGroup(
                           gl_panel_nc.createParallelGroup(Alignment.LEADING)
                              .addComponent(this.txt_fecha_documento, -2, 25, -2)
                              .addGroup(
                                 gl_panel_nc.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblTimbrado, -2, 25, -2)
                                    .addComponent(this.txt_timbrado, -2, 25, -2)
                              )
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_nc.setLayout(gl_panel_nc);
      GroupLayout gl_panel_cabecera = new GroupLayout(panel_cabecera);
      gl_panel_cabecera.setHorizontalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel_nc, Alignment.LEADING, 0, 0, 32767)
                        .addComponent(panel_compra, Alignment.LEADING, 0, 0, 32767)
                        .addComponent(panel_titulo, Alignment.LEADING, -2, 1132, -2)
                  )
                  .addGap(21)
            )
      );
      gl_panel_cabecera.setVerticalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_titulo, -2, 72, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_compra, -2, 37, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_nc, -2, -1, -2)
                  .addContainerGap(28, 32767)
            )
      );
      GroupLayout gl_panel_titulo = new GroupLayout(panel_titulo);
      gl_panel_titulo.setHorizontalGroup(
         gl_panel_titulo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_titulo.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_titulo.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(lblFechaProceso, Alignment.LEADING, -1, -1, 32767)
                        .addComponent(lblNroRegistro, Alignment.LEADING, -1, 94, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_titulo.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_titulo.createSequentialGroup()
                              .addComponent(this.txt_nro_registro, -2, 72, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblUltimoRegistro, -2, 72, -2)
                        )
                        .addComponent(this.txt_fecha_proceso, -2, 96, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblUltimoRegistroTexto, -2, 45, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(gl_panel_titulo.createParallelGroup(Alignment.LEADING).addComponent(lblProveedor, -2, 64, -2).addComponent(lblDeposito, -2, 63, -2))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_titulo.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(this.txt_cod_proveedor, -1, -1, 32767)
                        .addComponent(this.txt_cod_deposito, -2, 41, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_titulo.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(this.lblNombreProveedor, -1, -1, 32767)
                        .addComponent(this.lblNombreDeposito, -1, 245, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_titulo.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_titulo.createSequentialGroup()
                              .addComponent(lblMotivo, -2, 74, -2)
                              .addGap(18)
                              .addComponent(this.txt_cod_motivo, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreMotivo, -2, 200, -2)
                        )
                        .addGroup(
                           gl_panel_titulo.createSequentialGroup()
                              .addComponent(lblObservacion, -2, 78, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.txt_obsevacion, -2, 234, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_total_general, -2, 72, -2)
                        )
                  )
                  .addGap(28)
            )
      );
      gl_panel_titulo.setVerticalGroup(
         gl_panel_titulo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_titulo.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_titulo.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_titulo.createParallelGroup(Alignment.LEADING)
                              .addGroup(
                                 gl_panel_titulo.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblNroRegistro, -2, 25, -2)
                                    .addComponent(this.txt_nro_registro, -2, 25, -2)
                                    .addComponent(lblUltimoRegistro, -2, 25, -2)
                                    .addComponent(this.lblUltimoRegistroTexto, -2, 25, -2)
                                    .addComponent(lblDeposito, -2, 25, -2)
                              )
                              .addComponent(this.txt_cod_deposito, -2, 25, -2)
                              .addComponent(this.lblNombreDeposito, -2, 25, -2)
                        )
                        .addGroup(
                           gl_panel_titulo.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_motivo, -2, 25, -2)
                              .addComponent(lblMotivo, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreMotivo, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_titulo.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_titulo.createSequentialGroup()
                              .addGroup(
                                 gl_panel_titulo.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       gl_panel_titulo.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblFechaProceso, -2, 25, -2)
                                          .addComponent(this.txt_fecha_proceso, -2, 25, -2)
                                    )
                                    .addGroup(
                                       gl_panel_titulo.createParallelGroup(Alignment.LEADING)
                                          .addGroup(
                                             gl_panel_titulo.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(lblObservacion, -2, 25, -2)
                                                .addComponent(this.txt_obsevacion, -2, 25, -2)
                                                .addComponent(this.txt_total_general, -2, 25, -2)
                                          )
                                          .addComponent(lblProveedor, -2, 25, -2)
                                    )
                              )
                              .addGap(29)
                        )
                        .addGroup(
                           gl_panel_titulo.createSequentialGroup()
                              .addGroup(
                                 gl_panel_titulo.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(this.txt_cod_proveedor, -2, 25, -2)
                                    .addComponent(this.lblNombreProveedor, -2, 25, -2)
                              )
                              .addGap(34)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_titulo.setLayout(gl_panel_titulo);
      GroupLayout gl_panel_compra = new GroupLayout(panel_compra);
      gl_panel_compra.setHorizontalGroup(
         gl_panel_compra.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_compra.createSequentialGroup()
                  .addComponent(lblTipoDocumentoVta, -2, 98, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.txt_cod_tipo_documento_compra, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblTipoDocumentoCompra, -2, 200, -2)
                  .addGap(37)
                  .addComponent(lblNroFactura, -2, 88, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.txt_nro_factura, -2, 129, -2)
                  .addGap(27)
                  .addComponent(lblMoneda, -2, 54, -2)
                  .addGap(12)
                  .addComponent(this.txt_cod_moneda, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreMoneda, -2, 200, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnBuscarCompras, -2, 52, -2)
                  .addContainerGap(118, 32767)
            )
      );
      gl_panel_compra.setVerticalGroup(
         gl_panel_compra.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_compra.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_compra.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.btnBuscarCompras, -2, 25, -2)
                        .addComponent(this.lblNombreMoneda, -2, 25, -2)
                        .addGroup(
                           gl_panel_compra.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblNroFactura, -2, 25, -2)
                              .addComponent(this.txt_nro_factura, -2, 25, -2)
                        )
                        .addGroup(
                           gl_panel_compra.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblMoneda, -2, 25, -2)
                              .addComponent(this.txt_cod_moneda, -2, 25, -2)
                        )
                        .addGroup(
                           gl_panel_compra.createParallelGroup(Alignment.LEADING)
                              .addComponent(this.lblTipoDocumentoCompra, -2, 25, -2)
                              .addGroup(
                                 gl_panel_compra.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblTipoDocumentoVta, -2, 25, -2)
                                    .addComponent(this.txt_cod_tipo_documento_compra, -2, 25, -2)
                              )
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_compra.setLayout(gl_panel_compra);
      panel_cabecera.setLayout(gl_panel_cabecera);
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addComponent(scrollPane, -1, 1155, 32767).addGap(4))
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.TRAILING)
            .addGroup(Alignment.LEADING, gl_panel_detalle.createSequentialGroup().addComponent(scrollPane, -1, 269, 32767).addGap(2))
      );
      panel_detalle.setLayout(gl_panel_detalle);
      contentPane.setLayout(gl_contentPane);
      if (!Beans.isDesignTime()) {
         this.inicializarObjetos();
      }
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            NotaCreditoCompraForm frame = new NotaCreditoCompraForm();

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
