package a99CajaChica;

import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.CuadroTexto2Decimales;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextField;
import utilidades.LimiteTextFieldConSQL;
import utilidades.LimiteTextFieldConSQLProveedores;
import utilidades.NumerosTextField;
import utilidades.PanelPadre;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class CajaChicaForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(25);
   private int SW;
   private int COD_CAJERO;
   private static final long serialVersionUID = 1L;
   private JDateChooser txt_vencimiento_timbrado;
   private JDateChooser txt_fecha;
   private LabelPrincipal lblNombreTipoDocumento;
   private LabelPrincipal lblNumeroPlanilla;
   private LabelPrincipal lblNombreCajeroTexto;
   private LabelPrincipal lblNombreCuenta;
   private LabelPrincipal lblNombreMoneda;
   private LabelPrincipal lblNombreMotivo;
   private LimiteTextFieldConSQLProveedores txt_cod_proveedor;
   private LimiteTextField txt_nro_factura;
   private LimiteTextField txt_direccion;
   private LimiteTextField txt_telefono;
   private LimiteTextField txt_ruc;
   private LimiteTextField txt_nombre_proveedor;
   private NumerosTextField txt_numero_timbrado;
   private LimiteTextFieldConSQL txt_cod_tipo_documento;
   private LimiteTextFieldConSQL txt_cod_moneda;
   private LimiteTextFieldConSQL txt_cod_motivo;
   private LimiteTextFieldConSQL txt_cuenta;
   private LimiteTextArea txt_observacion;
   private CuadroTexto2Decimales txt_total_general;
   private CuadroTexto2Decimales txt_total_iva5;
   private CuadroTexto2Decimales txt_total_iva10;
   private CuadroTexto2Decimales txt_total_gravado5;
   private CuadroTexto2Decimales txt_total_gravado10;
   private CuadroTexto2Decimales txt_total_exento;

   private void buscarIni() {
      this.COD_CAJERO = 1;
      this.lblNumeroPlanilla.setText("1");
      this.lblNombreCajeroTexto.setText("");
   }

   private void inicializarObjetos() {
      this.buscarIni();
      this.SW = 0;
      this.txt_codigo.setValue(0);
      this.txt_cod_moneda.setValue(0);
      this.lblNombreMoneda.setText("");
      this.txt_cod_motivo.setValue(0);
      this.lblNombreMotivo.setText("");
      this.txt_cod_proveedor.setValue(0);
      this.txt_nombre_proveedor.setValue("");
      this.txt_ruc.setValue("");
      this.txt_telefono.setValue("");
      this.txt_direccion.setValue("");
      this.txt_cod_tipo_documento.setValue(0);
      this.lblNombreTipoDocumento.setText("");
      this.txt_cuenta.setValue(0);
      this.lblNombreCuenta.setText("");
      this.txt_fecha.setDate(null);
      this.txt_vencimiento_timbrado.setDate(null);
      this.txt_nro_factura.setValue("");
      this.txt_numero_timbrado.setValue(0);
      this.txt_observacion.setText("");
   }

   public CajaChicaForm() {
      this.setTitle("Registro de Caja Chica");
      this.setBounds(100, 100, 1133, 480);
      PanelPadre contentPane = new PanelPadre("");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      PanelPadre panel_datos_cuenta = new PanelPadre("");
      PanelPadre panel_proveedor = new PanelPadre("");
      PanelPadre panel_tipo_documentos = new PanelPadre("");
      PanelPadre panel_otros = new PanelPadre("");
      PanelPadre panel_totales = new PanelPadre("Totales");
      LabelPrincipal lblObservacion = new LabelPrincipal("Observacion", "label");
      LabelPrincipal lblTipoDocumento = new LabelPrincipal("Tipo Documento", "label");
      LabelPrincipal lblVencimientoTimbrado = new LabelPrincipal("Vencimiento", "label");
      LabelPrincipal lblTimbrado = new LabelPrincipal("Timbrado", "label");
      LabelPrincipal lblCodProveedor = new LabelPrincipal("Cod. Proveedor", "label");
      LabelPrincipal lblNombreProveedor = new LabelPrincipal("Nombre Proveedor", "label");
      LabelPrincipal lblRUC = new LabelPrincipal("RUC", "label");
      LabelPrincipal lblTelefono = new LabelPrincipal("Telefono", "label");
      LabelPrincipal lblDireccion = new LabelPrincipal("Direccion", "label");
      LabelPrincipal lblRegistro = new LabelPrincipal("Nro. de Registro", "label");
      LabelPrincipal lblCajero = new LabelPrincipal("Cajero", "label");
      LabelPrincipal lblNroPlanilla = new LabelPrincipal("Nro. Planilla", "label");
      LabelPrincipal lblNroCuenta = new LabelPrincipal("Cuenta", "label");
      LabelPrincipal lblCodMoneda = new LabelPrincipal("Moneda", "label");
      LabelPrincipal lblFecha = new LabelPrincipal("Fecha", "label");
      LabelPrincipal lblNroFactura = new LabelPrincipal("Nro. Factura", "label");
      LabelPrincipal lblMotivo = new LabelPrincipal("Motivo", "label");
      LabelPrincipal lblTotalExento = new LabelPrincipal("Total Exento", "label");
      LabelPrincipal lblTotalGravado10 = new LabelPrincipal("Total Gravado 10%", "label");
      LabelPrincipal lblTotalGravado5 = new LabelPrincipal("Total Gravado 5%", "label");
      LabelPrincipal lblTotalIva10 = new LabelPrincipal("Total IVA 10%", "label");
      LabelPrincipal lblTotalIva5 = new LabelPrincipal("Total IVA 5%", "label");
      LabelPrincipal lblTotalGeneral = new LabelPrincipal("Total General", "label");
      this.lblNombreMotivo = new LabelPrincipal(0);
      this.lblNombreTipoDocumento = new LabelPrincipal(0);
      this.lblNombreCajeroTexto = new LabelPrincipal(0);
      this.lblNombreCuenta = new LabelPrincipal(0);
      this.lblNombreMoneda = new LabelPrincipal(0);
      this.lblNumeroPlanilla = new LabelPrincipal(0);
      this.txt_fecha = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_vencimiento_timbrado = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_nro_factura = new LimiteTextField(25);
      this.txt_nombre_proveedor = new LimiteTextField(25);
      this.txt_ruc = new LimiteTextField(25);
      this.txt_telefono = new LimiteTextField(25);
      this.txt_cod_motivo = new LimiteTextFieldConSQL(999999, this.lblNombreMotivo, "Motivos", this);
      this.txt_cod_tipo_documento = new LimiteTextFieldConSQL(999999, this.lblNombreTipoDocumento, "TipoDocumentos", this);
      this.txt_cuenta = new LimiteTextFieldConSQL(999999, this.lblNombreCuenta, "Cuentas", this);
      this.txt_cod_moneda = new LimiteTextFieldConSQL(999999, this.lblNombreMoneda, "Monedas", this);
      this.txt_cod_proveedor = new LimiteTextFieldConSQLProveedores(
         999999, this.txt_nombre_proveedor, this.txt_ruc, this.txt_telefono, this.txt_direccion, this
      );
      this.txt_numero_timbrado = new NumerosTextField(99999999L);
      this.txt_observacion = new LimiteTextArea(70);
      this.txt_direccion = new LimiteTextField(25);
      this.txt_total_general = new CuadroTexto2Decimales(2);
      this.txt_total_iva5 = new CuadroTexto2Decimales(2);
      this.txt_total_iva10 = new CuadroTexto2Decimales(2);
      this.txt_total_gravado5 = new CuadroTexto2Decimales(2);
      this.txt_total_gravado10 = new CuadroTexto2Decimales(2);
      this.txt_total_exento = new CuadroTexto2Decimales(2);
      GroupLayout gl_panel_totales = new GroupLayout(panel_totales);
      gl_panel_totales.setHorizontalGroup(
         gl_panel_totales.createParallelGroup(Alignment.TRAILING)
            .addGap(0, 270, 32767)
            .addGroup(
               gl_panel_totales.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_totales.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblTotalExento, -2, 123, -2)
                        .addComponent(lblTotalGravado10, -2, 123, -2)
                        .addComponent(lblTotalGravado5, -2, 123, -2)
                        .addComponent(lblTotalIva10, -2, 123, -2)
                        .addComponent(lblTotalIva5, -2, 123, -2)
                        .addComponent(lblTotalGeneral, -2, 123, -2)
                        .addGroup(
                           gl_panel_totales.createParallelGroup(Alignment.TRAILING, false)
                              .addComponent(this.txt_total_general, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.txt_total_iva5, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.txt_total_iva10, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.txt_total_gravado5, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.txt_total_gravado10, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.txt_total_exento, Alignment.LEADING, -1, 233, 32767)
                        )
                  )
                  .addContainerGap(17, 32767)
            )
      );
      gl_panel_totales.setVerticalGroup(
         gl_panel_totales.createParallelGroup(Alignment.LEADING)
            .addGap(0, 435, 32767)
            .addGroup(
               gl_panel_totales.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblTotalExento, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_exento, -2, 31, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalGravado10, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_gravado10, -2, 31, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalGravado5, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_gravado5, -2, 31, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalIva10, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_iva10, -2, 31, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalIva5, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_iva5, -2, 31, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalGeneral, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_general, -2, 31, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      panel_totales.setLayout(gl_panel_totales);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_contentPane.createSequentialGroup()
                              .addComponent(panel_tipo_documentos, -2, 390, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_otros, -2, 441, -2)
                        )
                        .addComponent(panel_proveedor, 0, 0, 32767)
                        .addComponent(panel_datos_cuenta, -2, 837, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_totales, -2, 270, -2)
                  .addContainerGap(42, 32767)
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_contentPane.createSequentialGroup()
                              .addComponent(panel_datos_cuenta, -2, 105, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_proveedor, -2, 103, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(panel_otros, -1, -1, 32767)
                                    .addComponent(panel_tipo_documentos, -2, 159, 32767)
                              )
                        )
                        .addComponent(panel_totales, -2, 435, -2)
                  )
                  .addContainerGap(110, 32767)
            )
      );
      GroupLayout gl_panel_otros = new GroupLayout(panel_otros);
      gl_panel_otros.setHorizontalGroup(
         gl_panel_otros.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_otros.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_otros.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(lblMotivo, Alignment.TRAILING, -1, -1, 32767)
                        .addComponent(lblObservacion, Alignment.TRAILING, -1, 78, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_otros.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_otros.createSequentialGroup()
                              .addComponent(this.txt_cod_motivo, -2, 55, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreMotivo, -2, 264, -2)
                        )
                        .addComponent(this.txt_observacion, 0, 0, 32767)
                  )
                  .addContainerGap(189, 32767)
            )
      );
      gl_panel_otros.setVerticalGroup(
         gl_panel_otros.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_otros.createSequentialGroup()
                  .addGap(14)
                  .addGroup(
                     gl_panel_otros.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_otros.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblMotivo, -2, 25, -2)
                              .addComponent(this.txt_cod_motivo, -2, 32, -2)
                        )
                        .addComponent(this.lblNombreMotivo, -2, 28, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_otros.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblObservacion, -2, 25, -2)
                        .addComponent(this.txt_observacion, -2, 63, -2)
                  )
                  .addContainerGap(41, 32767)
            )
      );
      panel_otros.setLayout(gl_panel_otros);
      GroupLayout gl_panel_tipo_documentos = new GroupLayout(panel_tipo_documentos);
      gl_panel_tipo_documentos.setHorizontalGroup(
         gl_panel_tipo_documentos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo_documentos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_tipo_documentos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_tipo_documentos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_tipo_documentos.createParallelGroup(Alignment.TRAILING, false)
                                    .addComponent(lblTimbrado, Alignment.LEADING, -1, -1, 32767)
                                    .addComponent(lblNroFactura, Alignment.LEADING, -1, -1, 32767)
                                    .addComponent(lblTipoDocumento, Alignment.LEADING, -1, -1, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_tipo_documentos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_tipo_documentos.createSequentialGroup()
                                          .addComponent(this.txt_cod_tipo_documento, -2, 55, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreTipoDocumento, -2, 200, -2)
                                    )
                                    .addGroup(
                                       gl_panel_tipo_documentos.createParallelGroup(Alignment.TRAILING, false)
                                          .addComponent(this.txt_numero_timbrado, Alignment.LEADING, -1, -1, 32767)
                                          .addComponent(this.txt_nro_factura, Alignment.LEADING, -1, 157, 32767)
                                    )
                              )
                        )
                        .addGroup(
                           gl_panel_tipo_documentos.createSequentialGroup()
                              .addComponent(lblVencimientoTimbrado, -2, 79, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.txt_vencimiento_timbrado, -2, 157, -2)
                        )
                  )
                  .addContainerGap(365, 32767)
            )
      );
      gl_panel_tipo_documentos.setVerticalGroup(
         gl_panel_tipo_documentos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo_documentos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_tipo_documentos.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_tipo_documentos.createParallelGroup(Alignment.LEADING)
                              .addComponent(this.txt_cod_tipo_documento, -2, 32, -2)
                              .addComponent(this.lblNombreTipoDocumento, -2, 28, -2)
                        )
                        .addComponent(lblTipoDocumento, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_tipo_documentos.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNroFactura, -2, 25, -2)
                        .addComponent(this.txt_nro_factura, -2, 31, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_tipo_documentos.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblTimbrado, -2, 25, -2)
                        .addComponent(this.txt_numero_timbrado, -2, 32, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_tipo_documentos.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblVencimientoTimbrado, -2, 25, -2)
                        .addComponent(this.txt_vencimiento_timbrado, -2, 31, -2)
                  )
                  .addContainerGap(11, 32767)
            )
      );
      panel_tipo_documentos.setLayout(gl_panel_tipo_documentos);
      GroupLayout gl_panel_proveedor = new GroupLayout(panel_proveedor);
      gl_panel_proveedor.setHorizontalGroup(
         gl_panel_proveedor.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_proveedor.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_proveedor.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_proveedor.createSequentialGroup()
                              .addComponent(lblCodProveedor, -2, 123, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_proveedor, -2, 55, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblNombreProveedor, -2, 123, -2)
                        )
                        .addGroup(
                           gl_panel_proveedor.createSequentialGroup()
                              .addComponent(lblTelefono, -2, 123, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_telefono, -2, 157, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_proveedor.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_proveedor.createSequentialGroup()
                              .addComponent(this.txt_nombre_proveedor, -2, 307, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblRUC, -2, 60, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_ruc, -2, 108, -2)
                        )
                        .addGroup(
                           gl_panel_proveedor.createSequentialGroup()
                              .addComponent(lblDireccion, -2, 71, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_direccion, -2, 307, -2)
                        )
                  )
                  .addContainerGap(260, 32767)
            )
      );
      gl_panel_proveedor.setVerticalGroup(
         gl_panel_proveedor.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_proveedor.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_proveedor.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_proveedor.createParallelGroup(Alignment.BASELINE).addComponent(lblRUC, -2, 25, -2).addComponent(this.txt_ruc, -2, 31, -2)
                        )
                        .addComponent(this.txt_nombre_proveedor, -2, 31, -2)
                        .addComponent(this.txt_cod_proveedor, -2, 32, -2)
                        .addComponent(lblCodProveedor, -2, 25, -2)
                        .addComponent(lblNombreProveedor, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_proveedor.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_proveedor.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblTelefono, -2, 25, -2)
                              .addComponent(this.txt_telefono, -2, 31, -2)
                        )
                        .addGroup(
                           gl_panel_proveedor.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblDireccion, -2, 25, -2)
                              .addComponent(this.txt_direccion, -2, 31, -2)
                        )
                  )
                  .addContainerGap(99, 32767)
            )
      );
      panel_proveedor.setLayout(gl_panel_proveedor);
      GroupLayout gl_panel_datos_cuenta = new GroupLayout(panel_datos_cuenta);
      gl_panel_datos_cuenta.setHorizontalGroup(
         gl_panel_datos_cuenta.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_cuenta.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_cuenta.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(lblNroCuenta, -1, -1, 32767)
                        .addComponent(lblRegistro, -1, -1, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_cuenta.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_datos_cuenta.createSequentialGroup()
                              .addComponent(this.txt_codigo, -2, 67, -2)
                              .addGap(18)
                              .addComponent(lblFecha, -2, 46, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_fecha, -2, 108, -2)
                              .addGap(18)
                              .addComponent(lblCajero, -1, -1, 32767)
                        )
                        .addGroup(
                           gl_panel_datos_cuenta.createSequentialGroup()
                              .addComponent(this.txt_cuenta, -2, 55, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreCuenta, -2, 200, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblCodMoneda, -2, 68, -2)
                        )
                  )
                  .addGroup(
                     gl_panel_datos_cuenta.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_cuenta.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_moneda, -2, 55, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreMoneda, -2, 200, -2)
                        )
                        .addGroup(
                           gl_panel_datos_cuenta.createSequentialGroup()
                              .addGap(6)
                              .addComponent(this.lblNombreCajeroTexto, -2, 200, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblNroPlanilla, -2, 74, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNumeroPlanilla, -2, 99, -2)
                        )
                  )
                  .addContainerGap(111, 32767)
            )
      );
      gl_panel_datos_cuenta.setVerticalGroup(
         gl_panel_datos_cuenta.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_cuenta.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_cuenta.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_datos_cuenta.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblRegistro, -2, 25, -2)
                              .addComponent(this.txt_codigo, -2, 31, -2)
                              .addComponent(lblFecha, -2, 25, -2)
                        )
                        .addComponent(this.txt_fecha, -2, 31, -2)
                        .addComponent(lblCajero, -2, 25, -2)
                        .addGroup(
                           gl_panel_datos_cuenta.createParallelGroup(Alignment.LEADING)
                              .addComponent(lblNroPlanilla, -2, 25, -2)
                              .addComponent(this.lblNombreCajeroTexto, -2, 28, -2)
                              .addComponent(this.lblNumeroPlanilla, -2, 28, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_cuenta.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_datos_cuenta.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblCodMoneda, -2, 25, -2)
                              .addComponent(this.txt_cod_moneda, -2, 32, -2)
                        )
                        .addComponent(lblNroCuenta, -2, 25, -2)
                        .addComponent(this.txt_cuenta, -2, 32, -2)
                        .addComponent(this.lblNombreCuenta, -2, 28, -2)
                        .addComponent(this.lblNombreMoneda, -2, 28, -2)
                  )
                  .addContainerGap(24, 32767)
            )
      );
      panel_datos_cuenta.setLayout(gl_panel_datos_cuenta);
      contentPane.setLayout(gl_contentPane);
      this.inicializarObjetos();
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CajaChicaForm frame = new CajaChicaForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
               var3.printStackTrace();
            }
         }
      });
   }

   public int getCOD_CAJERO() {
      return this.COD_CAJERO;
   }

   public int getSW() {
      return this.SW;
   }
}
