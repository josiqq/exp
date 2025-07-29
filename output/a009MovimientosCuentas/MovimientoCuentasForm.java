package a009MovimientosCuentas;

import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import utilidades.CuadroTexto2Decimales;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextFieldConSQL;
import utilidades.NumerosTextField;
import utilidades.PanelPadre;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class MovimientoCuentasForm extends JinternalPadre {
   private int COD_CAJERO;
   public int SW;
   private static final long serialVersionUID = 1L;
   private JDateChooser txt_fecha;
   private LabelPrincipal lblNombreCuentaDestino;
   private LabelPrincipal lblNombreCuentaOrigen;
   private LabelPrincipal lblNombreMotivo;
   private LabelPrincipal lblMonedaDestinoTexto;
   private LabelPrincipal lblMonedaOrigenTexto;
   private LabelPrincipal lblNumeroPlanillaTexto;
   private LabelPrincipal lblNombreCajeroTexto;
   private LimiteTextFieldConSQL txt_cod_motivo;
   private LimiteTextFieldConSQL txt_cod_moneda_origen;
   private LimiteTextFieldConSQL txt_cod_moneda_destino;
   private LimiteTextFieldConSQL txt_cod_cuenta_destino;
   private LimiteTextFieldConSQL txt_cod_cuenta_origen;
   private NumerosTextField txt_nro_cheque;
   private NumerosTextField txt_nro_boleta;
   private CuadroTexto2Decimales txt_cotizacion;
   private CuadroTexto2Decimales txt_importe;
   private LimiteTextArea txt_observacion;
   private LabelPrincipal lblUltimoRegistroTexto;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         MovimientoCuentasE b = MovimientoCuentasE.buscarMovimiento(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarMovimientoCuenta(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         MovimientoCuentasE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = MovimientoCuentasE.insertarMovimientoCuentas(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = MovimientoCuentasE.actualizarMovimientoCuenta(entidad, this);
            if (codigo != 0) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro actualizado correctamente...", "correcto");
               rs.setLocationRelativeTo(this);
               rs.setVisible(true);
               this.inicializarObjetos();
            }
         }
      }
   }

   @Override
   public void eliminar() {
      if (Integer.parseInt(this.txt_codigo.getText()) == this.SW && this.SW != 0) {
         DialogoMessagebox d = new DialogoMessagebox("Desea eliminar el registro ?");
         d.setLocationRelativeTo(this);
         d.setVisible(true);
         if (d.isResultadoEncontrado()) {
            MovimientoCuentasE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = MovimientoCuentasE.eliminarMovimientoCuenta(ent, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro eliminado correctamente... ", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         }
      }
   }

   public void cargarMovimientoCuenta(MovimientoCuentasE e) {
      this.SW = e.getNro_registro();
      this.txt_cod_cuenta_destino.setValue(e.getCod_cuenta_destino());
      this.lblNombreCuentaDestino.setText(e.getNombre_cuenta_destino());
      this.txt_cod_cuenta_origen.setValue(e.getCod_cuenta_origen());
      this.lblNombreCuentaOrigen.setText(e.getNombre_cuenta_origen());
      this.txt_cod_moneda_destino.setValue(e.getCod_moneda_destino());
      this.lblMonedaDestinoTexto.setText(e.getNombre_moneda_destino());
      this.txt_cod_moneda_origen.setValue(e.getCod_moneda_origen());
      this.lblMonedaOrigenTexto.setText(e.getNombre_moneda_origen());
      this.txt_cod_motivo.setValue(e.getCod_motivo());
      this.lblNombreMotivo.setText(e.getNombre_motivo());
      this.txt_importe.setValue(e.getImporte());
      this.txt_cotizacion.setValue(e.getCotizacion());
      this.txt_nro_boleta.setValue(e.getNro_boleta());
      this.txt_nro_cheque.setValue(e.getNro_cheque());
      this.txt_observacion.setText(e.getObservacion());
      Date v_fecha = null;

      try {
         v_fecha = new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha());
         this.txt_fecha.setDate(v_fecha);
      } catch (ParseException var4) {
         LogErrores.errores(var4, "Error al Convertir Fecha...", this);
      }
   }

   public MovimientoCuentasE CargarEntidades() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha = "";

      try {
         fecha = dateFormat.format(this.txt_fecha.getDate());
      } catch (Exception var11) {
         LogErrores.errores(var11, "Debe de cargar Fecha Inicial Vencimiento...", this);
         this.txt_fecha.requestFocusInWindow();
         return null;
      }

      try {
         Number valor_importe = (Number)this.txt_importe.getValue();
         Number valor_cotizacion = (Number)this.txt_cotizacion.getValue();
         double totalImporte = valor_importe.doubleValue();
         double totalCotizacion = valor_cotizacion.doubleValue();
         return new MovimientoCuentasE(
            Integer.parseInt(this.txt_codigo.getText().trim().replace(".", "")),
            fecha,
            this.COD_CAJERO,
            Integer.parseInt(this.lblNumeroPlanillaTexto.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_cuenta_origen.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_cuenta_destino.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_nro_cheque.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_nro_boleta.getText().trim().replace(".", "")),
            this.txt_observacion.getText(),
            totalImporte,
            Integer.parseInt(this.txt_cod_moneda_origen.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_moneda_destino.getText().trim().replace(".", "")),
            totalCotizacion,
            Integer.parseInt(this.txt_cod_motivo.getText().trim().replace(".", ""))
         );
      } catch (NumberFormatException var9) {
         LogErrores.errores(var9, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   private void buscarIni() {
      this.COD_CAJERO = 1;
      this.lblNumeroPlanillaTexto.setText("1");
   }

   private void inicializarObjetos() {
      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblUltimoRegistroTexto.setText(String.valueOf(formatoLinea.format(MovimientoCuentasE.ultimoRegistro(this))));
      this.buscarIni();
      this.SW = 0;
      this.txt_codigo.setValue(0);
      this.txt_cod_cuenta_destino.setValue(0);
      this.lblNombreCuentaDestino.setText("");
      this.txt_cod_cuenta_origen.setValue(0);
      this.lblNombreCuentaOrigen.setText("");
      this.txt_cod_moneda_destino.setValue(0);
      this.lblMonedaDestinoTexto.setText("");
      this.txt_cod_moneda_origen.setValue(0);
      this.lblMonedaOrigenTexto.setText("");
      this.txt_cod_motivo.setValue(0);
      this.lblNombreMotivo.setText("");
      this.txt_cotizacion.setValue(0);
      this.txt_fecha.setDate(null);
      this.txt_importe.setValue(0);
      this.txt_nro_boleta.setValue(0);
      this.txt_nro_cheque.setValue(0);
      this.txt_observacion.setText("");
   }

   public MovimientoCuentasForm() {
      this.setTitle("Registro de Movimiento de Cuentas");
      this.setBounds(100, 100, 823, 458);
      PanelPadre panel_cabecera = new PanelPadre("");
      PanelPadre panel_detalle_cuentas = new PanelPadre("");
      PanelPadre panel_detalles_observacion = new PanelPadre("Observacion");
      LabelPrincipal lblCuentaOrigen = new LabelPrincipal("Cuenta Origen", "label");
      LabelPrincipal lblCuentaDestino = new LabelPrincipal("Cuenta Destino", "label");
      LabelPrincipal lblNroBoleta = new LabelPrincipal("Nro. Boleta", "label");
      LabelPrincipal lblMonedaOrigen = new LabelPrincipal("Moneda Origen", "label");
      LabelPrincipal lblMonedaDestino = new LabelPrincipal("Moneda Destino", "label");
      LabelPrincipal lblNroCheque = new LabelPrincipal("Nro. Cheque", "label");
      LabelPrincipal lblNroRegistro = new LabelPrincipal("Nro. Registro", "label");
      LabelPrincipal lblFecha = new LabelPrincipal("Fecha", "label");
      LabelPrincipal lblNroPlanilla = new LabelPrincipal("Nro. Planilla", "label");
      LabelPrincipal lblNombreCajero = new LabelPrincipal("Nombre Cajero", "label");
      LabelPrincipal lblMotivo = new LabelPrincipal("Motivo", "label");
      LabelPrincipal lblImporte = new LabelPrincipal("Importe", "label");
      LabelPrincipal lblCotizacion = new LabelPrincipal("Cotizacion", "label");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro:", "lineas");
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblMonedaOrigenTexto = new LabelPrincipal(0);
      this.lblNumeroPlanillaTexto = new LabelPrincipal(0);
      this.lblNombreMotivo = new LabelPrincipal(0);
      this.lblNombreCajeroTexto = new LabelPrincipal(0);
      this.lblMonedaDestinoTexto = new LabelPrincipal(0);
      this.lblNombreCuentaOrigen = new LabelPrincipal(0);
      this.lblNombreCuentaDestino = new LabelPrincipal(0);
      this.txt_fecha = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_observacion = new LimiteTextArea(70);
      this.txt_cod_cuenta_origen = new LimiteTextFieldConSQL(999999, this.lblNombreCuentaOrigen, "CuentaTesoreria", this);
      this.txt_cod_cuenta_destino = new LimiteTextFieldConSQL(999999, this.lblNombreCuentaDestino, "CuentaTesoreria", this);
      this.txt_cod_motivo = new LimiteTextFieldConSQL(999999, this.lblNombreMotivo, "MotivoCuenta", this);
      this.txt_cod_moneda_origen = new LimiteTextFieldConSQL(999999, this.lblMonedaOrigenTexto, "Monedas", this);
      this.txt_cod_moneda_destino = new LimiteTextFieldConSQL(999999, this.lblMonedaDestinoTexto, "Monedas", this);
      this.txt_nro_boleta = new NumerosTextField(99999999L);
      this.txt_nro_cheque = new NumerosTextField(99999999L);
      this.txt_importe = new CuadroTexto2Decimales(2);
      this.txt_cotizacion = new CuadroTexto2Decimales(2);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING,
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panel_detalles_observacion, Alignment.LEADING, -1, 799, 32767)
                        .addComponent(panel_detalle_cuentas, Alignment.LEADING, -1, 799, 32767)
                        .addComponent(panel_cabecera, Alignment.LEADING, -1, 799, 32767)
                  )
                  .addContainerGap()
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_cabecera, -2, 118, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle_cuentas, -2, 195, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalles_observacion, -2, 83, -2)
                  .addContainerGap(130, 32767)
            )
      );
      GroupLayout gl_panel_detalles_observacion = new GroupLayout(panel_detalles_observacion);
      gl_panel_detalles_observacion.setHorizontalGroup(
         gl_panel_detalles_observacion.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalles_observacion.createSequentialGroup().addComponent(this.txt_observacion, -1, 777, 32767).addContainerGap())
      );
      gl_panel_detalles_observacion.setVerticalGroup(
         gl_panel_detalles_observacion.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalles_observacion.createSequentialGroup().addComponent(this.txt_observacion, -2, 47, -2).addContainerGap(26, 32767))
      );
      panel_detalles_observacion.setLayout(gl_panel_detalles_observacion);
      GroupLayout gl_panel_detalle_cuentas = new GroupLayout(panel_detalle_cuentas);
      gl_panel_detalle_cuentas.setHorizontalGroup(
         gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_detalle_cuentas.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_detalle_cuentas.createSequentialGroup()
                              .addGroup(
                                 gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblNroBoleta, -1, -1, 32767)
                                    .addComponent(lblCuentaDestino, -1, -1, 32767)
                                    .addComponent(lblCuentaOrigen, -1, 88, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING, false)
                                    .addGroup(
                                       gl_panel_detalle_cuentas.createSequentialGroup()
                                          .addComponent(this.txt_cod_cuenta_origen, -2, 55, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreCuentaOrigen, -2, 200, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblMonedaOrigen, -2, 92, -2)
                                          .addGap(4)
                                          .addComponent(this.txt_cod_moneda_origen, -2, 55, -2)
                                          .addGap(4)
                                          .addComponent(this.lblMonedaOrigenTexto, -2, 200, -2)
                                    )
                                    .addGroup(
                                       gl_panel_detalle_cuentas.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING)
                                                .addGroup(
                                                   gl_panel_detalle_cuentas.createSequentialGroup()
                                                      .addComponent(this.txt_cod_cuenta_destino, -2, 55, -2)
                                                      .addPreferredGap(ComponentPlacement.RELATED)
                                                      .addComponent(this.lblNombreCuentaDestino, -2, 200, -2)
                                                )
                                                .addComponent(this.txt_nro_boleta, -2, 189, -2)
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addGroup(
                                             gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(lblMonedaDestino, -1, -1, 32767)
                                                .addComponent(lblNroCheque, -1, 93, 32767)
                                                .addComponent(lblCotizacion, -2, 93, -2)
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addGroup(
                                             gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING)
                                                .addGroup(
                                                   gl_panel_detalle_cuentas.createSequentialGroup()
                                                      .addComponent(this.txt_cod_moneda_destino, -2, 55, -2)
                                                      .addPreferredGap(ComponentPlacement.RELATED)
                                                      .addComponent(this.lblMonedaDestinoTexto, -2, 200, -2)
                                                )
                                                .addComponent(this.txt_nro_cheque, -2, 183, -2)
                                                .addComponent(this.txt_cotizacion, -2, 121, -2)
                                          )
                                    )
                              )
                        )
                        .addGroup(
                           gl_panel_detalle_cuentas.createSequentialGroup()
                              .addComponent(lblImporte, -2, 88, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_importe, -2, 121, -2)
                        )
                  )
                  .addContainerGap(74, 32767)
            )
      );
      gl_panel_detalle_cuentas.setVerticalGroup(
         gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_detalle_cuentas.createSequentialGroup()
                  .addGap(17)
                  .addGroup(
                     gl_panel_detalle_cuentas.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.lblMonedaOrigenTexto, -2, 28, -2)
                        .addGroup(
                           gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING)
                              .addGroup(
                                 gl_panel_detalle_cuentas.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblCuentaOrigen, -2, 25, -2)
                                    .addComponent(this.txt_cod_cuenta_origen, -2, 32, -2)
                              )
                              .addGroup(
                                 gl_panel_detalle_cuentas.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(lblMonedaOrigen, -2, 25, -2)
                                    .addComponent(this.lblNombreCuentaOrigen, -2, 28, -2)
                                    .addComponent(this.txt_cod_moneda_origen, -2, 32, -2)
                              )
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_detalle_cuentas.createSequentialGroup()
                              .addGroup(
                                 gl_panel_detalle_cuentas.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblCuentaDestino, -2, 25, -2)
                                    .addComponent(this.txt_cod_cuenta_destino, -2, 32, -2)
                              )
                              .addGap(13)
                              .addGroup(
                                 gl_panel_detalle_cuentas.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblNroBoleta, -2, 25, -2)
                                    .addComponent(this.txt_nro_boleta, -2, 32, -2)
                              )
                        )
                        .addGroup(
                           gl_panel_detalle_cuentas.createSequentialGroup()
                              .addGroup(
                                 gl_panel_detalle_cuentas.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(lblMonedaDestino, -2, 25, -2)
                                    .addComponent(this.lblNombreCuentaDestino, -2, 28, -2)
                                    .addComponent(this.txt_cod_moneda_destino, -2, 32, -2)
                                    .addComponent(this.lblMonedaDestinoTexto, -2, 28, -2)
                              )
                              .addGroup(
                                 gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING)
                                    .addGroup(gl_panel_detalle_cuentas.createSequentialGroup().addGap(18).addComponent(lblNroCheque, -2, 25, -2))
                                    .addGroup(
                                       gl_panel_detalle_cuentas.createSequentialGroup()
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.txt_nro_cheque, -2, 32, -2)
                                    )
                              )
                        )
                  )
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(
                     gl_panel_detalle_cuentas.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_detalle_cuentas.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblImporte, -2, 25, -2)
                              .addComponent(this.txt_importe, -2, 31, -2)
                        )
                        .addGroup(
                           gl_panel_detalle_cuentas.createParallelGroup(Alignment.TRAILING)
                              .addComponent(this.txt_cotizacion, -2, 31, -2)
                              .addComponent(lblCotizacion, -2, 25, -2)
                        )
                  )
                  .addContainerGap(20, 32767)
            )
      );
      panel_detalle_cuentas.setLayout(gl_panel_detalle_cuentas);
      GroupLayout gl_panel_cabecera = new GroupLayout(panel_cabecera);
      gl_panel_cabecera.setHorizontalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addComponent(lblNroRegistro, -2, 85, -2)
                              .addGap(2)
                              .addComponent(this.txt_codigo, -2, 72, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblUltimoRegistro, -2, 70, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblUltimoRegistroTexto, -2, 70, -2)
                              .addPreferredGap(ComponentPlacement.RELATED, 12, 32767)
                              .addComponent(lblNroPlanilla, -2, 87, -2)
                        )
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addComponent(lblNombreCajero, -2, 83, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreCajeroTexto, -2, 200, -2)
                              .addGap(31)
                              .addComponent(lblMotivo, -2, 88, -2)
                              .addGap(0, 0, 32767)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addComponent(this.lblNumeroPlanillaTexto, -2, 200, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblFecha, -2, 40, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_fecha, -2, 121, -2)
                        )
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addComponent(this.txt_cod_motivo, -2, 55, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreMotivo, -2, 200, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      gl_panel_cabecera.setVerticalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.lblNumeroPlanillaTexto, -2, 28, -2)
                        .addGroup(
                           gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_fecha, -2, 31, -2)
                              .addComponent(lblFecha, -2, 25, -2)
                        )
                        .addGroup(
                           gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblNroRegistro, -2, 25, -2)
                              .addComponent(this.txt_codigo, -2, 31, -2)
                              .addComponent(lblNroPlanilla, -2, 25, -2)
                              .addComponent(lblUltimoRegistro, -2, 15, -2)
                              .addComponent(this.lblUltimoRegistroTexto, -2, 15, -2)
                        )
                  )
                  .addGap(13)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNombreCajero, -2, 25, -2)
                        .addComponent(this.lblNombreCajeroTexto, -2, 28, -2)
                        .addGroup(
                           gl_panel_cabecera.createParallelGroup(Alignment.TRAILING)
                              .addComponent(this.lblNombreMotivo, -2, 28, -2)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblMotivo, -2, 25, -2)
                                    .addComponent(this.txt_cod_motivo, -2, 32, -2)
                              )
                        )
                  )
                  .addContainerGap(30, 32767)
            )
      );
      panel_cabecera.setLayout(gl_panel_cabecera);
      this.getContentPane().setLayout(groupLayout);
      this.inicializarObjetos();
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            MovimientoCuentasForm frame = new MovimientoCuentasForm();

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
