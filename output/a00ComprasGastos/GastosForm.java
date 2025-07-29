package a00ComprasGastos;

import com.toedter.calendar.JDateChooser;
import compradores.CompradoresE;
import java.awt.EventQueue;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.Beans;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.CuadroTexto2Decimales;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidades.LimiteTextFieldConSQL;
import utilidades.LimiteTextFieldConSQLPlazo;
import utilidades.LimiteTextFieldConSQLProveedores;
import utilidades.NumerosTextField;
import utilidades.PanelPadre;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class GastosForm extends JinternalPadre {
   private int SW;
   private int COD_COMPRADOR;
   private String NOMBRE_COMPRADOR;
   private static final long serialVersionUID = 1L;
   private LabelPrincipal lblNombrePlazo;
   private LabelPrincipal lblNombreTipoDocumento;
   private LabelPrincipal lblNombreMoneda;
   private LabelPrincipal lblNombreMotivo;
   private LabelPrincipal lblNombreSucursal;
   private LabelPrincipal lblNombreComprador;
   private LabelPrincipal lblNombreProveedor;
   private LabelPrincipal lblRUCProveedor;
   private LimiteTextFieldConSQL txt_cod_tipo_documento;
   private LimiteTextFieldConSQL txt_cod_moneda;
   private LimiteTextFieldConSQL txt_cod_motivo;
   private LimiteTextFieldConSQL txt_cod_sucursal;
   private LimiteTextFieldConSQL txt_cod_comprador;
   private LimiteTextFieldConSQLProveedores txt_cod_proveedor;
   private LimiteTextField txt_nro_factura;
   private JDateChooser txt_fecha_factura;
   private JDateChooser txt_fecha_proceso;
   private JDateChooser txt_fecha_vencimiento_timbrado;
   private LimiteTextFieldConSQLPlazo txt_cod_plazo;
   private NumerosTextField txt_nro_timbrado;
   private NumerosTextField txt_cuotas;
   private NumerosTextField txt_dias;
   private CuadroTexto2Decimales txt_total_gravado5;
   private CuadroTexto2Decimales txt_total_gravado10;
   private CuadroTexto2Decimales txt_total_exento;
   private CuadroTexto2Decimales txt_total_iva5;
   private CuadroTexto2Decimales txt_total_iva10;
   private CuadroTexto2Decimales txt_total_general;
   private LabelPrincipal lblUltimoRegistroTexto;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         GastosE b = GastosE.buscarGasto(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarGastos(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         GastosE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = GastosE.insertarGasto(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = GastosE.actualizarGastoProveedor(entidad, this);
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
            GastosE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = GastosE.eliminarGastoProveedor(ent, this);
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

   public void cargarGastos(GastosE e) {
      this.txt_codigo.setValue(e.getNro_registro());
      this.SW = Integer.parseInt(String.valueOf(e.getNro_registro()));
      this.txt_cod_comprador.setValue(e.getCod_comprador());
      this.lblNombreComprador.setText(e.getNombre_comprador());
      this.txt_cod_moneda.setValue(e.getCod_moneda());
      this.lblNombreMoneda.setText(e.getNombre_moneda());
      this.txt_cod_motivo.setValue(e.getCod_motivo());
      this.lblNombreMotivo.setText(e.getNombre_motivo());
      this.txt_cod_plazo.setValue(e.getCod_plazo());
      this.lblNombrePlazo.setText(e.getNombre_plazo());
      this.txt_cod_proveedor.setValue(e.getCod_proveedor());
      this.lblNombreProveedor.setText(e.getNombre_proveedor());
      this.lblRUCProveedor.setText(e.getRuc_proveedor());
      this.txt_cod_sucursal.setValue(e.getCod_sucursal());
      this.lblNombreSucursal.setText(e.getNombre_sucursal());
      this.txt_cod_tipo_documento.setValue(e.getCod_tipo_documento());
      this.lblNombreTipoDocumento.setText(e.getNombre_tipo_documento());
      this.txt_cuotas.setValue(e.getCuotas());
      this.txt_dias.setValue(e.getDias());
      this.txt_nro_factura.setValue(e.getNro_factura());
      this.txt_nro_timbrado.setValue(e.getTimbrado());
      this.txt_total_exento.setValue(e.getImporte_exento());
      this.txt_total_gravado10.setValue(e.getImporte_gravado10());
      this.txt_total_gravado5.setValue(e.getImporte_gravado5());
      this.txt_total_iva10.setValue(e.getImporte_iva10());
      this.txt_total_iva5.setValue(e.getImporte_iva5());
      this.txt_total_general.setValue(e.getImporte_total());
      Date v_fecha = null;

      try {
         v_fecha = new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha_factura());
         this.txt_fecha_factura.setDate(v_fecha);
         v_fecha = new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha_proceso());
         this.txt_fecha_proceso.setDate(v_fecha);
         v_fecha = new SimpleDateFormat("yyyy-MM-dd").parse(e.getVencimiento_timbrado());
         this.txt_fecha_vencimiento_timbrado.setDate(v_fecha);
      } catch (ParseException var4) {
         LogErrores.errores(var4, "Error al Convertir Fecha...", this);
      }
   }

   public GastosE CargarEntidades() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha_proceso = "";
      String fecha_factura = "";
      String fecha_VencimientoTimbrado = "";

      try {
         fecha_VencimientoTimbrado = dateFormat.format(this.txt_fecha_vencimiento_timbrado.getDate());
      } catch (Exception var15) {
         LogErrores.errores(var15, "Debe de cargar Fecha Vencimiento Timbrado...", this);
         this.txt_fecha_vencimiento_timbrado.requestFocusInWindow();
         return null;
      }

      try {
         fecha_factura = dateFormat.format(this.txt_fecha_factura.getDate());
      } catch (Exception var14) {
         LogErrores.errores(var14, "Debe de cargar Fecha Factura...", this);
         this.txt_fecha_factura.requestFocusInWindow();
         return null;
      }

      try {
         fecha_proceso = dateFormat.format(this.txt_fecha_proceso.getDate());
      } catch (Exception var13) {
         LogErrores.errores(var13, "Debe de cargar Fecha Proceso...", this);
         this.txt_fecha_proceso.requestFocusInWindow();
         return null;
      }

      BigDecimal valor = this.txt_total_exento.getValorComoBigDecimal();
      double v_exento = valor.doubleValue();
      double v_total = valor.doubleValue();

      try {
         return new GastosE(
            Integer.parseInt(this.txt_codigo.getText().trim().replace(".", "")),
            fecha_proceso,
            fecha_factura,
            Integer.parseInt(this.txt_cod_sucursal.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_comprador.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_moneda.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_tipo_documento.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_nro_timbrado.getText().trim().replace(".", "")),
            this.txt_nro_factura.getText(),
            fecha_VencimientoTimbrado,
            Integer.parseInt(this.txt_cod_plazo.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cuotas.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_dias.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_proveedor.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_motivo.getText().trim().replace(".", "")),
            Double.parseDouble(this.txt_total_exento.getText().replace(".", "").replace(",", ".")),
            Double.parseDouble(this.txt_total_gravado10.getText().replace(".", "").replace(",", ".")),
            Double.parseDouble(this.txt_total_gravado5.getText().replace(".", "").replace(",", ".")),
            Double.parseDouble(this.txt_total_iva10.getText().replace(".", "").replace(",", ".")),
            Double.parseDouble(this.txt_total_iva5.getText().replace(".", "").replace(",", ".")),
            Double.parseDouble(this.txt_total_general.getText().replace(".", "").replace(",", "."))
         );
      } catch (NumberFormatException var11) {
         LogErrores.errores(var11, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var12) {
         LogErrores.errores(var12, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   private void inicializarObjetos() {
      this.SW = 0;
      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblUltimoRegistroTexto.setText(String.valueOf(formatoLinea.format(GastosE.ultimoRegistro(this))));
      this.txt_codigo.setValue(0);
      this.txt_cod_comprador.setValue(this.COD_COMPRADOR);
      this.lblNombreComprador.setText(this.NOMBRE_COMPRADOR);
      this.txt_cod_moneda.setValue(0);
      this.lblNombreMoneda.setText("");
      this.txt_cod_motivo.setValue(0);
      this.lblNombreMotivo.setText("");
      this.txt_cod_plazo.setValue(0);
      this.lblNombrePlazo.setText("");
      this.txt_dias.setValue(0);
      this.txt_cuotas.setValue(0);
      this.txt_cod_proveedor.setValue(0);
      this.lblNombreProveedor.setText("");
      this.lblRUCProveedor.setText("");
      this.txt_cod_sucursal.setValue(0);
      this.lblNombreSucursal.setText("");
      this.txt_cod_tipo_documento.setValue(0);
      this.lblNombreTipoDocumento.setText("");
      this.txt_fecha_factura.setDate(null);
      this.txt_fecha_proceso.setDate(null);
      this.txt_fecha_vencimiento_timbrado.setDate(null);
      this.txt_nro_timbrado.setValue(0);
      this.txt_nro_factura.setValue("");
      this.txt_total_exento.setValue(0);
      this.txt_total_general.setValue(0);
      this.txt_total_gravado10.setValue(0);
      this.txt_total_gravado5.setValue(0);
      this.txt_total_iva10.setValue(0);
      this.txt_total_iva5.setValue(0);
   }

   public GastosForm() {
      this.setTitle("Registro de Compras Gastos");
      this.setBounds(100, 100, 917, 388);
      PanelPadre contentPane = new PanelPadre("");
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_datos_factura = new PanelPadre("Detales de Factura");
      PanelPadre panel_condiciones = new PanelPadre("Condiciones");
      PanelPadre panel_totales = new PanelPadre("Totales");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      LabelPrincipal lblTotalExento = new LabelPrincipal("Total Exento", "label");
      LabelPrincipal lblTotalGravado10 = new LabelPrincipal("Total Gravado 10%", "label");
      LabelPrincipal lblTotalGravado5 = new LabelPrincipal("Total Gravado 5%", "label");
      LabelPrincipal lblTotalIva10 = new LabelPrincipal("Total IVA 10%", "label");
      LabelPrincipal lblTotalIva5 = new LabelPrincipal("Total IVA 5%", "label");
      LabelPrincipal lblTotalGeneral = new LabelPrincipal("Total General", "label");
      LabelPrincipal lblPlazo = new LabelPrincipal("Plazo", "label");
      LabelPrincipal lblCuotas = new LabelPrincipal("Cuotas", "label");
      LabelPrincipal lblTipoDocumento = new LabelPrincipal("Tipo Documento", "label");
      LabelPrincipal lblTimbrado = new LabelPrincipal("Timbrado", "label");
      LabelPrincipal lblFechaFactura = new LabelPrincipal("Fecha Factura", "label");
      LabelPrincipal lblNroRegistro = new LabelPrincipal("Nro. Registro", "label");
      LabelPrincipal lblFechaProceso = new LabelPrincipal("Fecha Proceso", "label");
      LabelPrincipal lblComprador = new LabelPrincipal("Comprador", "label");
      LabelPrincipal lblMotivo = new LabelPrincipal("Motivo", "label");
      LabelPrincipal lblProveedor = new LabelPrincipal("Proveedor", "label");
      LabelPrincipal lblMoneda = new LabelPrincipal("Moneda", "label");
      LabelPrincipal lblSucursal = new LabelPrincipal("Sucursal", "label");
      LabelPrincipal lblVencimientoTimbrado = new LabelPrincipal("Vencimiento", "label");
      LabelPrincipal lblNroFactura = new LabelPrincipal("Nro. Factura", "label");
      LabelPrincipal lblDias = new LabelPrincipal("Dias", "label");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro:", "lineas");
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblNombrePlazo = new LabelPrincipal(0);
      this.lblNombreTipoDocumento = new LabelPrincipal(0);
      this.lblNombreComprador = new LabelPrincipal(0);
      this.lblNombreMotivo = new LabelPrincipal(0);
      this.lblNombreProveedor = new LabelPrincipal(0);
      this.lblNombreMoneda = new LabelPrincipal(0);
      this.lblRUCProveedor = new LabelPrincipal(0);
      this.lblNombreSucursal = new LabelPrincipal(0);
      this.txt_total_exento = new CuadroTexto2Decimales(2);
      this.txt_total_gravado10 = new CuadroTexto2Decimales(2);
      this.txt_total_gravado5 = new CuadroTexto2Decimales(2);
      this.txt_total_iva10 = new CuadroTexto2Decimales(2);
      this.txt_total_iva10.setEditable(false);
      this.txt_total_iva5 = new CuadroTexto2Decimales(2);
      this.txt_total_iva5.setEditable(false);
      this.txt_total_general = new CuadroTexto2Decimales(2);
      this.txt_total_general.setEditable(false);
      this.txt_nro_factura = new LimiteTextField(25);
      this.txt_dias = new NumerosTextField(999L);
      this.txt_cuotas = new NumerosTextField(99L);
      this.txt_nro_timbrado = new NumerosTextField(99999999L);
      this.txt_fecha_vencimiento_timbrado = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_factura = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_proceso = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_cod_comprador = new LimiteTextFieldConSQL(999999, this.lblNombreComprador, "Compradores", this);
      this.txt_cod_comprador.setEnabled(false);
      this.txt_cod_sucursal = new LimiteTextFieldConSQL(999999, this.lblNombreSucursal, "Sucursales", this);
      this.txt_cod_motivo = new LimiteTextFieldConSQL(999999, this.lblNombreMotivo, "MotivoGasto", this);
      this.txt_cod_moneda = new LimiteTextFieldConSQL(999999, this.lblNombreMoneda, "Monedas", this);
      this.txt_cod_plazo = new LimiteTextFieldConSQLPlazo(999999, this.lblNombrePlazo, this.txt_dias, this);
      this.txt_cod_proveedor = new LimiteTextFieldConSQLProveedores(99999, this.lblNombreProveedor, this.lblRUCProveedor, this);
      this.txt_cod_tipo_documento = new LimiteTextFieldConSQL(999999, this.lblNombreTipoDocumento, "TipoDocumentos", this);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(panel_condiciones, -1, -1, 32767)
                        .addComponent(panel_datos_factura, -1, -1, 32767)
                        .addComponent(panel_datos, -2, 724, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_totales, -2, 150, -2)
                  .addGap(36)
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panel_totales, -2, -1, -2)
                        .addGroup(
                           gl_contentPane.createSequentialGroup()
                              .addComponent(panel_datos, -2, 148, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_datos_factura, -2, 121, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_condiciones, -2, 52, -2)
                        )
                  )
                  .addContainerGap(12, 32767)
            )
      );
      GroupLayout gl_panel_totales = new GroupLayout(panel_totales);
      gl_panel_totales.setHorizontalGroup(
         gl_panel_totales.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_totales.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_totales.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_totales.createSequentialGroup().addComponent(lblTotalExento, -2, 123, -2).addContainerGap(127, 32767))
                        .addGroup(gl_panel_totales.createSequentialGroup().addComponent(lblTotalGravado10, -2, 123, -2).addContainerGap(127, 32767))
                        .addGroup(gl_panel_totales.createSequentialGroup().addComponent(lblTotalGravado5, -2, 123, -2).addContainerGap(127, 32767))
                        .addGroup(gl_panel_totales.createSequentialGroup().addComponent(lblTotalIva10, -2, 123, -2).addContainerGap(127, 32767))
                        .addGroup(gl_panel_totales.createSequentialGroup().addComponent(lblTotalIva5, -2, 123, -2).addContainerGap(127, 32767))
                        .addGroup(gl_panel_totales.createSequentialGroup().addComponent(lblTotalGeneral, -2, 123, -2).addContainerGap(127, 32767))
                        .addGroup(
                           gl_panel_totales.createSequentialGroup()
                              .addGroup(
                                 gl_panel_totales.createParallelGroup(Alignment.TRAILING, false)
                                    .addComponent(this.txt_total_general, Alignment.LEADING, -2, 124, -2)
                                    .addComponent(this.txt_total_iva5, Alignment.LEADING, -2, 124, -2)
                                    .addComponent(this.txt_total_iva10, Alignment.LEADING, -2, 124, -2)
                                    .addComponent(this.txt_total_gravado5, Alignment.LEADING, -2, 124, -2)
                                    .addComponent(this.txt_total_gravado10, Alignment.LEADING, -2, 124, -2)
                                    .addComponent(this.txt_total_exento, Alignment.LEADING, -2, 124, -2)
                              )
                              .addContainerGap(22, 32767)
                        )
                  )
            )
      );
      gl_panel_totales.setVerticalGroup(
         gl_panel_totales.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_totales.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblTotalExento, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_exento, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalGravado10, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_gravado10, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalGravado5, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_gravado5, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalIva10, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_iva10, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalIva5, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_iva5, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalGeneral, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_general, -2, 25, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      panel_totales.setLayout(gl_panel_totales);
      GroupLayout gl_panel_condiciones = new GroupLayout(panel_condiciones);
      gl_panel_condiciones.setHorizontalGroup(
         gl_panel_condiciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_condiciones.createSequentialGroup()
                  .addGap(13)
                  .addComponent(lblPlazo, -2, 92, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cod_plazo, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombrePlazo, -2, 200, -2)
                  .addGap(37)
                  .addComponent(lblDias, -2, 49, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_dias, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(lblCuotas, -2, 49, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cuotas, -2, 41, -2)
                  .addContainerGap(89, 32767)
            )
      );
      gl_panel_condiciones.setVerticalGroup(
         gl_panel_condiciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_condiciones.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_condiciones.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_condiciones.createParallelGroup(Alignment.TRAILING)
                              .addComponent(this.txt_cod_plazo, -2, 25, -2)
                              .addComponent(this.lblNombrePlazo, -2, 25, -2)
                        )
                        .addComponent(lblPlazo, -2, 25, -2)
                        .addGroup(
                           gl_panel_condiciones.createParallelGroup(Alignment.TRAILING)
                              .addComponent(lblCuotas, -2, 25, -2)
                              .addGroup(
                                 gl_panel_condiciones.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblDias, -2, 25, -2)
                                    .addComponent(this.txt_dias, -2, 25, -2)
                              )
                              .addComponent(this.txt_cuotas, -2, 25, -2)
                        )
                  )
                  .addContainerGap(14, 32767)
            )
      );
      panel_condiciones.setLayout(gl_panel_condiciones);
      GroupLayout gl_panel_datos_factura = new GroupLayout(panel_datos_factura);
      gl_panel_datos_factura.setHorizontalGroup(
         gl_panel_datos_factura.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_factura.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_factura.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(lblFechaFactura, -1, -1, 32767)
                        .addComponent(lblTimbrado, -1, -1, 32767)
                        .addComponent(lblTipoDocumento, -1, 97, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_factura.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_factura.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos_factura.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos_factura.createSequentialGroup()
                                          .addComponent(this.txt_cod_tipo_documento, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreTipoDocumento, -2, 200, -2)
                                    )
                                    .addComponent(this.txt_nro_timbrado, -2, 120, -2)
                              )
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addGroup(
                                 gl_panel_datos_factura.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblVencimientoTimbrado, -1, -1, 32767)
                                    .addComponent(lblNroFactura, -1, 80, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos_factura.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.txt_fecha_vencimiento_timbrado, -2, 96, -2)
                                    .addComponent(this.txt_nro_factura, -2, 148, -2)
                              )
                        )
                        .addComponent(this.txt_fecha_factura, -2, 96, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_panel_datos_factura.setVerticalGroup(
         gl_panel_datos_factura.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_factura.createSequentialGroup()
                  .addGap(11)
                  .addGroup(
                     gl_panel_datos_factura.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_datos_factura.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblNroFactura, -2, 25, -2)
                              .addComponent(this.txt_nro_factura, -2, 25, -2)
                        )
                        .addGroup(
                           gl_panel_datos_factura.createParallelGroup(Alignment.LEADING)
                              .addComponent(this.lblNombreTipoDocumento, -2, 25, -2)
                              .addGroup(
                                 gl_panel_datos_factura.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblTipoDocumento, -2, 25, -2)
                                    .addComponent(this.txt_cod_tipo_documento, -2, 25, -2)
                              )
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_factura.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_datos_factura.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblTimbrado, -2, 25, -2)
                              .addComponent(this.txt_nro_timbrado, -2, 25, -2)
                        )
                        .addComponent(lblVencimientoTimbrado, -2, 25, -2)
                        .addComponent(this.txt_fecha_vencimiento_timbrado, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_factura.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblFechaFactura, -2, 25, -2)
                        .addComponent(this.txt_fecha_factura, -2, 25, -2)
                  )
                  .addContainerGap(14, 32767)
            )
      );
      panel_datos_factura.setLayout(gl_panel_datos_factura);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblProveedor, -1, -1, 32767)
                                    .addComponent(lblFechaProceso, -1, -1, 32767)
                                    .addComponent(lblNroRegistro, -1, 82, 32767)
                              )
                              .addGap(6)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(this.txt_codigo, -1, -1, 32767)
                                                .addComponent(this.txt_fecha_proceso, -1, 96, 32767)
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblUltimoRegistro, -2, 70, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, 70, -2)
                                          .addGap(46)
                                          .addGroup(
                                             gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                                .addComponent(lblComprador, -2, 71, -2)
                                                .addComponent(lblSucursal, -2, 71, -2)
                                          )
                                    )
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_cod_proveedor, -2, 55, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblRUCProveedor, -2, 200, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED, 33, 32767)
                                          .addComponent(lblMotivo, -2, 71, -2)
                                    )
                              )
                        )
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(this.lblNombreProveedor, -2, 362, -2)
                              .addGap(18, 18, 32767)
                              .addComponent(lblMoneda, -2, 71, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.txt_cod_comprador, -2, 41, -2)
                        .addComponent(this.txt_cod_sucursal, -2, 41, -2)
                        .addComponent(this.txt_cod_motivo, -2, 41, -2)
                        .addComponent(this.txt_cod_moneda, -2, 41, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.lblNombreSucursal, -2, 200, -2)
                        .addComponent(this.lblNombreComprador, -2, 200, -2)
                        .addComponent(this.lblNombreMotivo, -2, 200, -2)
                        .addComponent(this.lblNombreMoneda, -2, 200, -2)
                  )
                  .addGap(71)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblComprador, -2, 25, -2)
                                          .addComponent(this.txt_cod_comprador, -2, 25, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblNroRegistro, -2, 25, -2)
                                          .addComponent(this.txt_codigo, -2, 25, -2)
                                    )
                                    .addComponent(this.lblNombreComprador, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblSucursal, -2, 25, -2)
                                          .addComponent(this.txt_cod_sucursal, -2, 25, -2)
                                    )
                                    .addComponent(lblFechaProceso, -2, 25, -2)
                                    .addComponent(this.txt_fecha_proceso, -2, 25, -2)
                                    .addComponent(this.lblNombreSucursal, -2, 25, -2)
                              )
                        )
                        .addComponent(this.lblUltimoRegistroTexto, -2, 15, -2)
                        .addComponent(lblUltimoRegistro, -2, 15, -2)
                  )
                  .addGap(10)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblMotivo, -2, 25, -2)
                              .addComponent(this.txt_cod_motivo, -2, 25, -2)
                        )
                        .addComponent(lblProveedor, -2, 25, -2)
                        .addComponent(this.txt_cod_proveedor, -2, 25, -2)
                        .addComponent(this.lblRUCProveedor, -2, 25, -2)
                        .addComponent(this.lblNombreMotivo, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblMoneda, -2, 25, -2)
                              .addComponent(this.txt_cod_moneda, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreMoneda, -2, 25, -2)
                        .addComponent(this.lblNombreProveedor, -2, 25, -2)
                  )
                  .addContainerGap(25, 32767)
            )
      );
      panel_datos.setLayout(gl_panel_datos);
      contentPane.setLayout(gl_contentPane);
      if (!Beans.isDesignTime()) {
         CompradoresE comprador = CompradoresE.buscarCompradores2(DatabaseConnection.getInstance().getUsuario(), this);
         this.COD_COMPRADOR = comprador.getCod_comprador();
         this.NOMBRE_COMPRADOR = comprador.getNombre_comprador();
         this.inicializarObjetos();
      }

      this.txt_total_gravado10.addPropertyChangeListener("value", evt -> {});
      this.txt_total_gravado10
         .addFocusListener(
            new FocusAdapter() {
               @Override
               public void focusLost(FocusEvent e) {
                  GastosCalculoImpuestos.calcularImpuestos(
                     GastosForm.this.txt_total_exento,
                     GastosForm.this.txt_total_gravado10,
                     GastosForm.this.txt_total_gravado5,
                     GastosForm.this.txt_total_iva10,
                     GastosForm.this.txt_total_iva5,
                     GastosForm.this.txt_total_general
                  );
               }
            }
         );
      this.txt_total_gravado5
         .addFocusListener(
            new FocusAdapter() {
               @Override
               public void focusLost(FocusEvent e) {
                  GastosCalculoImpuestos.calcularImpuestos(
                     GastosForm.this.txt_total_exento,
                     GastosForm.this.txt_total_gravado10,
                     GastosForm.this.txt_total_gravado5,
                     GastosForm.this.txt_total_iva10,
                     GastosForm.this.txt_total_iva5,
                     GastosForm.this.txt_total_general
                  );
               }
            }
         );
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            GastosForm frame = new GastosForm();

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
