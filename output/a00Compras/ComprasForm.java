package a00Compras;

import a0098OrdenCompra.BuscarPedidoOCForm;
import a00pedidosProveedores.DecimalFilter;
import com.toedter.calendar.JDateChooser;
import compradores.CompradoresE;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.JTextComponent;
import utilidades.BotonPadre;
import utilidades.CuadroTexto2Decimales;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidades.LimiteTextFieldConSQL;
import utilidades.LimiteTextFieldConSQLPlazo;
import utilidades.NumerosTextField;
import utilidades.PanelPadre;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorProducto;
import utilidadesVentanas.JinternalPadre;

public class ComprasForm extends JinternalPadre {
   List<Integer> codigosAEliminar = new ArrayList<>();
   private static final long serialVersionUID = 1L;
   private LimiteTextField txt_nro_factura;
   private LabelPrincipal lblNombreCompradorTexto;
   private LabelPrincipal lblNombreUsuarioAltaTexto;
   private LabelPrincipal lblLineasTotalRecuperadasTexto;
   private LabelPrincipal lblNombrePlazo;
   private LabelPrincipal lblNombreTipoDocTexto;
   private LabelPrincipal lblNombreProveedorTexto;
   private LabelPrincipal lblNombreMonedaTexto;
   private LabelPrincipal lblNombreDepositoTexto;
   private NumerosTextField txt_nro_oc;
   private NumerosTextField txt_timbrado;
   private NumerosTextField txt_cuotas;
   private NumerosTextField txt_nro_pedido;
   private NumerosTextField txt_dias;
   private LimiteTextFieldConSQL txt_cod_comprador;
   private LimiteTextFieldConSQL txt_cod_tipo_doc;
   private LimiteTextFieldConSQL txt_cod_proveedor;
   private LimiteTextFieldConSQL txt_cod_moneda;
   private LimiteTextFieldConSQL txt_cod_deposito;
   private JDateChooser txt_fecha_vencimiento;
   private JDateChooser txt_fecha_factura;
   private JDateChooser txt_fecha;
   private DecimalFilter txt_gravado10;
   private DecimalFilter txt_gravado5;
   private DecimalFilter txt_exentas;
   private DecimalFilter txt_iva10;
   private DecimalFilter txt_iva5;
   private DecimalFilter txt_total_impuesto;
   private DecimalFilter txt_total_general;
   private CuadroTexto2Decimales txt_descuento;
   private CuadroTexto2Decimales txt_cotizacion;
   private Integer SW;
   private TablaComprasDetalles tabla;
   private LimiteTextFieldConSQLPlazo txt_cod_plazo;
   private int COD_COMPRADOR;
   private String NOMBRE_COMPRADOR;
   private LabelPrincipal lblUltimoRegistroTexto;
   private BotonPadre btnSeleccionarOC;
   private BotonPadre btnSeleccionarPedidos;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   public void buscarPedido() {
      if (!this.txt_nro_pedido.getText().trim().equals("0")) {
         ComprasE b = ComprasE.buscarPedido(
            Integer.parseInt(this.txt_nro_pedido.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_nro_oc.getText().trim().replace(".", "")),
            this.tabla,
            2,
            2,
            this
         );
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
         } else {
            this.cargarPedido(b);
         }
      }
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         ComprasE b = ComprasE.buscarCompra(Integer.parseInt(this.txt_codigo.getText().trim()), this.tabla, 2, this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarCompras(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         ComprasE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = ComprasE.insertarCompras(entidad, this.tabla, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = ComprasE.actualizarCompras(entidad, this.tabla, this, this.codigosAEliminar);
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
            ComprasE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = ComprasE.eliminarCompras(ent, this);
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

   public void cargarPedido(ComprasE e) {
      this.txt_cod_proveedor.setValue(e.getCod_proveedor());
      this.lblNombreProveedorTexto.setText(e.getNombre_proveedor());
      this.txt_cod_tipo_doc.setValue(e.getCod_tipo_documento());
      this.lblNombreTipoDocTexto.setText(e.getNombre_tipo_documento());
      this.txt_cod_plazo.setValue(e.getCod_plazo());
      this.lblNombrePlazo.setText(e.getNombre_plazo());
      this.txt_dias.setText(String.valueOf(e.getDias()));
      this.txt_cod_moneda.setValue(e.getCod_moneda());
      this.lblNombreMonedaTexto.setText(e.getNombre_moneda());
      CalculosTabla.cargarImpuestos(
         this.tabla, this.txt_total_general, this.txt_exentas, this.txt_gravado10, this.txt_iva10, this.txt_gravado5, this.txt_iva5, this.txt_total_impuesto
      );
      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblLineasTotalRecuperadasTexto.setText(String.valueOf(formatoLinea.format(this.tabla.getRowCount())));
      this.txt_nro_oc.setEnabled(false);
      this.txt_nro_pedido.setEnabled(false);
      this.btnSeleccionarOC.setEnabled(false);
      this.btnSeleccionarPedidos.setEnabled(false);
      this.txt_cod_proveedor.setEnabled(false);
      this.txt_cod_moneda.setEnabled(false);
   }

   public void cargarCompras(ComprasE e) {
      this.txt_codigo.setValue(e.getNro_registro());
      this.SW = Integer.parseInt(String.valueOf(e.getNro_registro()));
      this.txt_cod_comprador.setValue(e.getCod_comprador());
      this.lblNombreCompradorTexto.setText(e.getNombre_comprador());
      this.txt_cod_deposito.setValue(e.getCod_deposito());
      this.lblNombreDepositoTexto.setText(e.getNombre_deposito());
      this.txt_cod_moneda.setValue(e.getCod_moneda());
      this.lblNombreMonedaTexto.setText(e.getNombre_moneda());
      this.txt_cod_plazo.setValue(e.getCod_plazo());
      this.lblNombrePlazo.setText(e.getNombre_plazo());
      this.txt_dias.setText(String.valueOf(e.getDias()));
      this.txt_cod_proveedor.setValue(e.getCod_proveedor());
      this.lblNombreProveedorTexto.setText(e.getNombre_proveedor());
      this.txt_cod_tipo_doc.setValue(e.getCod_tipo_documento());
      this.lblNombreTipoDocTexto.setText(e.getNombre_tipo_documento());
      this.txt_cotizacion.setValue(e.getCotizacion());
      this.txt_cuotas.setValue(e.getCuotas());
      this.txt_descuento.setValue(e.getDescuento());
      this.txt_exentas.setValue(e.getTotal_exenta());
      this.txt_total_general.setValue(e.getTotal());
      this.txt_iva5.setValue(e.getTotal_iva5());
      this.txt_iva10.setValue(e.getTotal_iva10());
      this.txt_gravado10.setValue(e.getTotal_gravada10());
      this.txt_gravado5.setValue(e.getTotal_gravado5());
      this.txt_total_general.setValue(e.getTotal());
      this.txt_total_impuesto.setValue(e.getTotal());
      this.txt_timbrado.setValue(e.getTimbrado());
      this.txt_nro_pedido.setValue(e.getNro_pedido());
      this.txt_nro_factura.setValue(e.getNro_factura());
      Date v_fecha = null;

      try {
         v_fecha = new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha_llegada());
         this.txt_fecha.setDate(v_fecha);
         v_fecha = new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha_factura());
         this.txt_fecha_factura.setDate(v_fecha);
         v_fecha = new SimpleDateFormat("yyyy-MM-dd").parse(e.getVencimiento_timbrado());
         this.txt_fecha_vencimiento.setDate(v_fecha);
         this.lblNombreUsuarioAltaTexto.setText(e.getUsuario_alta());
      } catch (ParseException var4) {
         LogErrores.errores(var4, "Error al Convertir Fecha...", this);
      }
   }

   public ComprasE CargarEntidades() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha_llegada = "";
      String fecha_factura = "";
      String fecha_VencimientoTimbrado = "";

      try {
         fecha_llegada = dateFormat.format(this.txt_fecha.getDate());
         fecha_factura = dateFormat.format(this.txt_fecha_factura.getDate());
         fecha_VencimientoTimbrado = dateFormat.format(this.txt_fecha_vencimiento.getDate());
      } catch (Exception var8) {
         LogErrores.errores(var8, "Debe de cargar Fecha Inicial Vencimiento...", this);
         this.txt_fecha.requestFocusInWindow();
         return null;
      }

      try {
         return new ComprasE(
            Integer.parseInt(this.txt_codigo.getText().trim().replace(".", "")),
            fecha_llegada,
            Integer.parseInt(this.txt_cod_comprador.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_deposito.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_moneda.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_nro_pedido.getText().trim().replace(".", "")),
            new BigDecimal(this.txt_exentas.getValue().toString()),
            new BigDecimal(this.txt_gravado10.getValue().toString()),
            new BigDecimal(this.txt_gravado5.getValue().toString()),
            new BigDecimal(this.txt_iva10.getValue().toString()),
            new BigDecimal(this.txt_iva5.getValue().toString()),
            Double.parseDouble(this.txt_total_general.getValue().toString()),
            Double.parseDouble(this.txt_descuento.getText().replace(".", "").replace(",", ".")),
            Double.parseDouble(this.txt_cotizacion.getText().replace(".", "").replace(",", ".")),
            Integer.parseInt(this.txt_cod_proveedor.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_tipo_doc.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_plazo.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cuotas.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_dias.getText().trim().replace(".", "")),
            this.txt_nro_factura.getText(),
            fecha_VencimientoTimbrado,
            fecha_factura,
            Integer.parseInt(this.txt_timbrado.getText().trim().replace(".", ""))
         );
      } catch (NumberFormatException var6) {
         LogErrores.errores(var6, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var7) {
         LogErrores.errores(var7, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   private void inicializarObjetos() {
      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblUltimoRegistroTexto.setText(String.valueOf(formatoLinea.format(ComprasE.ultimoRegistro(this))));
      this.SW = 0;
      ModeloTablaCompra modelo = (ModeloTablaCompra)this.tabla.getModel();
      modelo.deleteRow(this.tabla);
      modelo.addDefaultRow();
      this.txt_dias.setValue(0);
      this.txt_cod_comprador.setValue(this.COD_COMPRADOR);
      this.lblNombreCompradorTexto.setText(this.NOMBRE_COMPRADOR);
      this.txt_cod_deposito.setValue(0);
      this.lblNombreDepositoTexto.setText("");
      this.txt_cod_moneda.setValue(0);
      this.lblNombreMonedaTexto.setText("");
      this.txt_cod_plazo.setValue(0);
      this.lblNombrePlazo.setText("");
      this.txt_cod_proveedor.setValue(0);
      this.lblNombreProveedorTexto.setText("");
      this.txt_cod_tipo_doc.setValue(0);
      this.lblNombreTipoDocTexto.setText("");
      this.txt_codigo.setValue(0);
      this.txt_cotizacion.setValue(0);
      this.txt_cuotas.setValue(1);
      this.txt_descuento.setValue(0);
      this.txt_exentas.setValue(0);
      this.txt_gravado10.setValue(0);
      this.txt_gravado5.setValue(0);
      this.txt_iva10.setValue(0);
      this.txt_iva5.setValue(0);
      this.txt_nro_factura.setValue("");
      this.txt_nro_pedido.setValue(0);
      this.txt_timbrado.setValue(0);
      this.txt_total_general.setValue(0);
      this.txt_total_impuesto.setValue(0);
      this.txt_nro_oc.setValue(0);
      Calendar currentDate = Calendar.getInstance();
      this.txt_fecha.setDate(currentDate.getTime());
      this.txt_fecha_factura.setDate(null);
      this.txt_fecha_vencimiento.setDate(null);
      this.codigosAEliminar.clear();
      this.lblLineasTotalRecuperadasTexto.setText("0");
      this.txt_nro_oc.setEnabled(true);
      this.txt_nro_pedido.setEnabled(true);
      this.btnSeleccionarOC.setEnabled(true);
      this.btnSeleccionarPedidos.setEnabled(true);
      this.txt_cod_proveedor.setEnabled(true);
      this.txt_cod_moneda.setEnabled(true);
   }

   public ComprasForm() {
      this.setTitle("Registro de Compras a Proveedores");
      this.setBounds(100, 100, 1161, 592);
      PanelPadre panel_datos_basicos = new PanelPadre("Datos Basicos");
      PanelPadre panel_proveedores = new PanelPadre("Proveedores");
      PanelPadre panel_detalles = new PanelPadre("Detalles de Compras");
      PanelPadre panel_impuestos = new PanelPadre("Totales");
      PanelPadre panel_totales = new PanelPadre("");
      PanelPadre panel_timbrado = new PanelPadre("Timbrado");
      PanelPadre pane_pedido = new PanelPadre("Detalles del Pedido");
      LabelPrincipal lblNroRegistro = new LabelPrincipal("Nro. Registro", "label");
      LabelPrincipal lblDeposito = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblMoneda = new LabelPrincipal("Moneda", "label");
      LabelPrincipal lblNroFactura = new LabelPrincipal("Nro. Factura", "label");
      LabelPrincipal lblFechaFactura = new LabelPrincipal("Fecha Fact.", "label");
      LabelPrincipal lblTimbrado = new LabelPrincipal("Timbrado", "label");
      LabelPrincipal lblVencimiento = new LabelPrincipal("Vencimiento", "label");
      LabelPrincipal lblTextoTotalLinea = new LabelPrincipal("Total:", "totalTexto");
      lblTextoTotalLinea.setFont(new Font("Dialog", 1, 30));
      LabelPrincipal lblTextoLineaRecuperadas = new LabelPrincipal("Total Lineas:", "lineas");
      LabelPrincipal lblGravada10 = new LabelPrincipal("Gravada 10%", "label");
      LabelPrincipal lblProveedor = new LabelPrincipal("Proveedor", "label");
      LabelPrincipal lblTipoDoc = new LabelPrincipal("Tipo Doc", "label");
      LabelPrincipal lblFecha = new LabelPrincipal("Fecha", "label");
      LabelPrincipal lblTotal = new LabelPrincipal("Total", "label");
      LabelPrincipal lblGravada5 = new LabelPrincipal("Gravada 5%", "label");
      LabelPrincipal lblExentas = new LabelPrincipal("Exentas", "label");
      LabelPrincipal lblIva10 = new LabelPrincipal("IVA 10%", "label");
      LabelPrincipal lblIva5 = new LabelPrincipal("IVA 5%", "label");
      LabelPrincipal lblUsuarioAlta = new LabelPrincipal("Usuario Alta", "label");
      LabelPrincipal lblDias = new LabelPrincipal("Dias", "label");
      LabelPrincipal lblPlazo = new LabelPrincipal("Plazo", "label");
      LabelPrincipal lblCuotas = new LabelPrincipal("Cuotas", "label");
      LabelPrincipal lblDescuento = new LabelPrincipal("Descuento", "label");
      LabelPrincipal lblCotizacion = new LabelPrincipal("Cotizacion", "label");
      LabelPrincipal lblComprador = new LabelPrincipal("Comprador", "label");
      LabelPrincipal lblNroOC = new LabelPrincipal("Nro. OC", "label");
      LabelPrincipal lblNroPedido = new LabelPrincipal("Nro. Pedido", "label");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro:", "lineas");
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreUsuarioAltaTexto = new LabelPrincipal(0);
      this.lblNombreProveedorTexto = new LabelPrincipal(0);
      this.lblNombreTipoDocTexto = new LabelPrincipal(0);
      this.lblNombreDepositoTexto = new LabelPrincipal(0);
      this.lblNombreMonedaTexto = new LabelPrincipal(0);
      this.lblLineasTotalRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreCompradorTexto = new LabelPrincipal(0);
      this.lblNombrePlazo = new LabelPrincipal(0);
      this.txt_fecha_factura = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_vencimiento = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_nro_pedido = new NumerosTextField(999L);
      this.txt_timbrado = new NumerosTextField(99999999L);
      this.txt_cuotas = new NumerosTextField(999L);
      this.txt_dias = new NumerosTextField(99L);
      this.txt_cod_proveedor = new LimiteTextFieldConSQL(999999, this.lblNombreProveedorTexto, "Proveedores", this);
      this.txt_cod_tipo_doc = new LimiteTextFieldConSQL(999999, this.lblNombreTipoDocTexto, "TipoDocumentos", this);
      this.txt_cod_deposito = new LimiteTextFieldConSQL(999999, this.lblNombreDepositoTexto, "Depositos", this);
      this.txt_cod_moneda = new LimiteTextFieldConSQL(999999, this.lblNombreMonedaTexto, "Monedas", this);
      this.txt_cod_plazo = new LimiteTextFieldConSQLPlazo(999999, this.lblNombrePlazo, this.txt_dias, this);
      this.txt_cod_comprador = new LimiteTextFieldConSQL(999999, this.lblNombreCompradorTexto, "Compradores", this);
      this.txt_cod_comprador.setEnabled(false);
      this.txt_nro_factura = new LimiteTextField(25);
      this.txt_nro_oc = new NumerosTextField(9999L);
      this.txt_descuento = new CuadroTexto2Decimales(2);
      this.txt_cotizacion = new CuadroTexto2Decimales(2);
      this.txt_total_general = new DecimalFilter(2);
      this.txt_total_general.setEditable(false);
      this.txt_total_general.setFont(new Font("Arial", 0, 21));
      this.txt_gravado10 = new DecimalFilter(2);
      this.txt_gravado10.setEditable(false);
      this.txt_gravado10.setFont(new Font("Arial", 0, 14));
      this.txt_gravado5 = new DecimalFilter(2);
      this.txt_gravado5.setEditable(false);
      this.txt_gravado5.setFont(new Font("Arial", 0, 14));
      this.txt_exentas = new DecimalFilter(2);
      this.txt_exentas.setEditable(false);
      this.txt_exentas.setFont(new Font("Arial", 0, 14));
      this.txt_iva10 = new DecimalFilter(2);
      this.txt_iva10.setEditable(false);
      this.txt_iva10.setFont(new Font("Arial", 0, 14));
      this.txt_iva5 = new DecimalFilter(2);
      this.txt_iva5.setEditable(false);
      this.txt_iva5.setFont(new Font("Arial", 0, 14));
      this.txt_total_impuesto = new DecimalFilter(2);
      this.txt_total_impuesto.setEditable(false);
      this.txt_total_impuesto.setFont(new Font("Arial", 0, 21));
      this.tabla = new TablaComprasDetalles(
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
      this.btnSeleccionarPedidos = new BotonPadre("", "buscar.png");
      this.btnSeleccionarPedidos
         .addActionListener(
            new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                  BuscarPedidoOCForm buscador = new BuscarPedidoOCForm(
                     Integer.parseInt(ComprasForm.this.txt_cod_proveedor.getText().trim().replace(".", "")), ComprasForm.this
                  );
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     ComprasForm.this.txt_nro_pedido.setValue(buscador.getNro_pedido());
                     ComprasForm.this.txt_nro_pedido.requestFocusInWindow();
                     SwingUtilities.invokeLater(() -> ComprasForm.this.txt_cod_proveedor.requestFocusInWindow());
                  }
               }
            }
         );
      this.btnSeleccionarOC = new BotonPadre("", "buscar.png");
      this.btnSeleccionarOC
         .addActionListener(
            new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                  BuscarPedidoOCForm buscador = new BuscarPedidoOCForm(
                     Integer.parseInt(ComprasForm.this.txt_cod_proveedor.getText().trim().replace(".", "")), 1, ComprasForm.this
                  );
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     ComprasForm.this.txt_nro_oc.setValue(buscador.getNro_oc());
                     ComprasForm.this.txt_nro_pedido.setValue(buscador.getNro_pedido());
                     ComprasForm.this.txt_nro_oc.requestFocusInWindow();
                     SwingUtilities.invokeLater(() -> ComprasForm.this.txt_nro_factura.requestFocusInWindow());
                  }
               }
            }
         );
      this.txt_nro_pedido.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            ComprasForm.this.buscarPedido();
         }
      });
      this.txt_nro_oc.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            ComprasForm.this.buscarPedido();
         }
      });
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_impuestos, 0, 0, 32767)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_datos_basicos, -2, 413, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(panel_timbrado, 0, 0, 32767)
                                    .addComponent(panel_proveedores, -2, 716, 32767)
                              )
                        )
                        .addComponent(panel_detalles, -2, 1135, -2)
                        .addComponent(panel_totales, 0, 0, 32767)
                  )
                  .addContainerGap()
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addContainerGap()
                              .addComponent(panel_timbrado, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_proveedores, -2, -1, -2)
                        )
                        .addComponent(panel_datos_basicos, 0, 0, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalles, -2, 284, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_impuestos, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_totales, -2, 40, -2)
                  .addGap(21)
            )
      );
      GroupLayout gl_panel_timbrado = new GroupLayout(panel_timbrado);
      gl_panel_timbrado.setHorizontalGroup(
         gl_panel_timbrado.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_timbrado.createSequentialGroup()
                  .addComponent(lblNroFactura, -2, 66, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_nro_factura, -2, 114, -2)
                  .addGap(11)
                  .addComponent(lblFechaFactura, -2, 75, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_fecha_factura, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTimbrado, -2, 57, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_timbrado, -2, 78, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblVencimiento, -2, 73, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_fecha_vencimiento, -2, -1, -2)
                  .addContainerGap(235, 32767)
            )
      );
      gl_panel_timbrado.setVerticalGroup(
         gl_panel_timbrado.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_timbrado.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_timbrado.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.txt_fecha_factura, -2, 25, -2)
                        .addComponent(this.txt_fecha_vencimiento, -2, 25, -2)
                        .addGroup(
                           gl_panel_timbrado.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblFechaFactura, -2, 25, -2)
                              .addComponent(lblTimbrado, -2, 25, -2)
                              .addComponent(lblVencimiento, -2, 25, -2)
                              .addComponent(lblNroFactura, -2, 25, -2)
                              .addComponent(this.txt_nro_factura, -2, 25, -2)
                              .addComponent(this.txt_timbrado, -2, 25, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_timbrado.setLayout(gl_panel_timbrado);
      GroupLayout gl_panel_totales = new GroupLayout(panel_totales);
      gl_panel_totales.setHorizontalGroup(
         gl_panel_totales.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_totales.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblTextoLineaRecuperadas, -2, 89, -2)
                  .addGap(6)
                  .addComponent(this.lblLineasTotalRecuperadasTexto, -2, 69, -2)
                  .addPreferredGap(ComponentPlacement.RELATED, 627, 32767)
                  .addComponent(lblTextoTotalLinea, -2, 127, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_general, -2, 190, -2)
                  .addContainerGap()
            )
      );
      gl_panel_totales.setVerticalGroup(
         gl_panel_totales.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_totales.createSequentialGroup()
                  .addGroup(
                     gl_panel_totales.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_totales.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 gl_panel_totales.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblTextoLineaRecuperadas, -2, 17, -2)
                                    .addComponent(this.lblLineasTotalRecuperadasTexto, -2, 17, -2)
                              )
                        )
                        .addGroup(
                           gl_panel_totales.createParallelGroup(Alignment.TRAILING)
                              .addComponent(lblTextoTotalLinea, -2, 33, -2)
                              .addComponent(this.txt_total_general, -2, 33, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_totales.setLayout(gl_panel_totales);
      GroupLayout gl_panel_impuestos = new GroupLayout(panel_impuestos);
      gl_panel_impuestos.setHorizontalGroup(
         gl_panel_impuestos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_impuestos.createSequentialGroup()
                  .addComponent(lblGravada10, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_gravado10, -2, 117, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblGravada5, -2, 71, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_gravado5, -2, 117, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblExentas, -2, 45, -2)
                  .addGap(2)
                  .addComponent(this.txt_exentas, -2, 117, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblIva10, -2, 51, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_iva10, -2, 117, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(lblIva5, -2, 45, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_iva5, -2, 117, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotal, -2, 32, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total_impuesto, -2, 158, -2)
                  .addContainerGap(20, 32767)
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
                                          .addComponent(lblGravada10, -2, 21, -2)
                                          .addComponent(lblGravada5, -2, 21, -2)
                                    )
                                    .addGroup(
                                       gl_panel_impuestos.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblExentas, -2, 21, -2)
                                          .addComponent(lblIva10, -2, 21, -2)
                                          .addComponent(lblIva5, -2, 21, -2)
                                          .addComponent(lblTotal, -2, 21, -2)
                                    )
                              )
                        )
                        .addComponent(this.txt_gravado10, -2, 32, -2)
                        .addComponent(this.txt_gravado5, -2, 32, -2)
                        .addComponent(this.txt_exentas, -2, 32, -2)
                        .addComponent(this.txt_iva10, -2, 32, -2)
                        .addComponent(this.txt_iva5, -2, 32, -2)
                        .addComponent(this.txt_total_impuesto, -2, 32, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_impuestos.setLayout(gl_panel_impuestos);
      GroupLayout gl_panel_detalles = new GroupLayout(panel_detalles);
      gl_panel_detalles.setHorizontalGroup(gl_panel_detalles.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, -1, 1148, 32767));
      gl_panel_detalles.setVerticalGroup(
         gl_panel_detalles.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalles.createSequentialGroup().addComponent(scrollPane, -1, 271, 32767).addContainerGap())
      );
      panel_detalles.setLayout(gl_panel_detalles);
      GroupLayout gl_panel_proveedores = new GroupLayout(panel_proveedores);
      gl_panel_proveedores.setHorizontalGroup(
         gl_panel_proveedores.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_proveedores.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_proveedores.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblPlazo, -2, 52, -2)
                        .addComponent(lblTipoDoc, -2, 58, -2)
                        .addComponent(lblProveedor, -2, 58, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_proveedores.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_proveedores.createSequentialGroup()
                              .addGroup(
                                 gl_panel_proveedores.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(this.txt_cod_plazo, -2, 36, -2)
                                    .addComponent(this.txt_cod_tipo_doc, -2, 36, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_proveedores.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_proveedores.createSequentialGroup()
                                          .addComponent(this.lblNombrePlazo, -2, 120, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblDias, -2, 32, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.txt_dias, -2, 36, -2)
                                    )
                                    .addComponent(this.lblNombreTipoDocTexto, -2, 186, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_proveedores.createParallelGroup(Alignment.LEADING, false)
                                    .addGroup(
                                       gl_panel_proveedores.createSequentialGroup()
                                          .addGap(4)
                                          .addComponent(lblCuotas, -2, 45, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.txt_cuotas, -2, 36, -2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(lblDescuento, -2, 69, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.txt_descuento, -2, 45, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblCotizacion, -2, 66, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.txt_cotizacion, -2, 61, -2)
                                    )
                                    .addGroup(
                                       gl_panel_proveedores.createSequentialGroup()
                                          .addGap(6)
                                          .addComponent(lblFecha, -2, 45, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.txt_fecha, -2, -1, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblUsuarioAlta, -2, 73, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreUsuarioAltaTexto, -2, 120, -2)
                                    )
                              )
                        )
                        .addGroup(
                           gl_panel_proveedores.createSequentialGroup()
                              .addComponent(this.txt_cod_proveedor, -2, 52, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreProveedorTexto, -2, 577, -2)
                        )
                  )
                  .addGap(179)
            )
      );
      gl_panel_proveedores.setVerticalGroup(
         gl_panel_proveedores.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_proveedores.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_proveedores.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_proveedores.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_proveedor, -2, 25, -2)
                              .addComponent(lblProveedor, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreProveedorTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_proveedores.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_proveedores.createSequentialGroup()
                              .addGroup(
                                 gl_panel_proveedores.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_proveedores.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(this.txt_cod_tipo_doc, -2, 25, -2)
                                          .addComponent(lblTipoDoc, -2, 25, -2)
                                    )
                                    .addComponent(this.lblNombreTipoDocTexto, -2, 25, -2)
                                    .addComponent(this.txt_fecha, -2, 25, -2)
                                    .addComponent(lblUsuarioAlta, -2, 25, -2)
                                    .addComponent(this.lblNombreUsuarioAltaTexto, -2, 25, -2)
                              )
                              .addGap(10)
                              .addGroup(
                                 gl_panel_proveedores.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_proveedores.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(this.txt_cod_plazo, -2, 25, -2)
                                          .addComponent(lblPlazo, -2, 25, -2)
                                    )
                                    .addGroup(
                                       gl_panel_proveedores.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblDias, -2, 25, -2)
                                          .addComponent(this.txt_dias, -2, 25, -2)
                                          .addComponent(lblCuotas, -2, 25, -2)
                                          .addComponent(this.txt_cuotas, -2, 25, -2)
                                          .addComponent(lblDescuento, -2, 25, -2)
                                          .addComponent(this.txt_descuento, -2, 25, -2)
                                          .addComponent(lblCotizacion, -2, 25, -2)
                                          .addComponent(this.txt_cotizacion, -2, 25, -2)
                                    )
                                    .addComponent(this.lblNombrePlazo, -2, 25, -2)
                              )
                        )
                        .addComponent(lblFecha, -2, 25, -2)
                  )
                  .addContainerGap()
            )
      );
      panel_proveedores.setLayout(gl_panel_proveedores);
      GroupLayout gl_pane_pedido = new GroupLayout(pane_pedido);
      gl_pane_pedido.setHorizontalGroup(
         gl_pane_pedido.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_pane_pedido.createSequentialGroup()
                  .addComponent(lblNroPedido, -2, 68, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_nro_pedido, -2, 55, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnSeleccionarPedidos, -2, 52, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblNroOC, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_nro_oc, -2, 59, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnSeleccionarOC, -2, 52, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      gl_pane_pedido.setVerticalGroup(
         gl_pane_pedido.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_pane_pedido.createSequentialGroup()
                  .addContainerGap(-1, 32767)
                  .addGroup(
                     gl_pane_pedido.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.btnSeleccionarOC, -2, 25, -2)
                        .addGroup(
                           gl_pane_pedido.createParallelGroup(Alignment.BASELINE).addComponent(lblNroOC, -2, 25, -2).addComponent(this.txt_nro_oc, -2, 25, -2)
                        )
                        .addGroup(
                           gl_pane_pedido.createParallelGroup(Alignment.LEADING)
                              .addComponent(this.btnSeleccionarPedidos, -2, 25, -2)
                              .addGroup(
                                 gl_pane_pedido.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblNroPedido, -2, 25, -2)
                                    .addComponent(this.txt_nro_pedido, -2, 25, -2)
                              )
                        )
                  )
                  .addContainerGap()
            )
      );
      pane_pedido.setLayout(gl_pane_pedido);
      GroupLayout gl_panel_datos_basicos = new GroupLayout(panel_datos_basicos);
      gl_panel_datos_basicos.setHorizontalGroup(
         gl_panel_datos_basicos.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_datos_basicos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_basicos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING, false)
                                          .addComponent(lblMoneda, -2, 72, -2)
                                          .addComponent(lblNroRegistro, -1, 81, 32767)
                                          .addComponent(lblDeposito, -1, -1, 32767)
                                    )
                                    .addComponent(lblComprador, -2, 72, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos_basicos.createSequentialGroup()
                                          .addComponent(this.txt_cod_comprador, -2, 36, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreCompradorTexto, -2, 200, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos_basicos.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 70, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblUltimoRegistro, -2, 70, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, 70, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos_basicos.createSequentialGroup()
                                          .addComponent(this.txt_cod_deposito, -2, 36, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreDepositoTexto, -2, 200, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos_basicos.createSequentialGroup()
                                          .addComponent(this.txt_cod_moneda, -2, 36, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreMonedaTexto, -2, 200, -2)
                                    )
                              )
                        )
                        .addComponent(pane_pedido, -2, -1, -2)
                  )
                  .addContainerGap(34, 32767)
            )
      );
      gl_panel_datos_basicos.setVerticalGroup(
         gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_basicos.createSequentialGroup()
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_basicos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblNroRegistro, -2, 25, -2)
                              .addComponent(this.txt_codigo, -2, 25, -2)
                        )
                        .addGroup(
                           gl_panel_datos_basicos.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.lblUltimoRegistroTexto, -2, 15, -2)
                                    .addComponent(lblUltimoRegistro, -2, 15, -2)
                              )
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblDeposito, -2, 25, -2)
                        .addComponent(this.txt_cod_deposito, -2, 25, -2)
                        .addComponent(this.lblNombreDepositoTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_basicos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblMoneda, -2, 25, -2)
                              .addComponent(this.txt_cod_moneda, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreMonedaTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.txt_cod_comprador, -2, 25, -2)
                        .addComponent(this.lblNombreCompradorTexto, -2, 25, -2)
                        .addComponent(lblComprador, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(pane_pedido, -2, -1, -2)
                  .addContainerGap(75, 32767)
            )
      );
      panel_datos_basicos.setLayout(gl_panel_datos_basicos);
      this.getContentPane().setLayout(groupLayout);
      if (!Beans.isDesignTime()) {
         CompradoresE comprador = CompradoresE.buscarCompradores2(DatabaseConnection.getInstance().getUsuario(), this);
         this.COD_COMPRADOR = comprador.getCod_comprador();
         this.NOMBRE_COMPRADOR = comprador.getNombre_comprador();
         this.inicializarObjetos();
      }

      this.tabla.getInputMap(1).put(KeyStroke.getKeyStroke(112, 0), "F1");
      this.tabla
         .getActionMap()
         .put(
            "F1",
            new AbstractAction() {
               private static final long serialVersionUID = 1L;

               @Override
               public void actionPerformed(ActionEvent e) {
                  if (ComprasForm.this.tabla.getSelectedColumn() == 0) {
                     BuscadorProducto buscador = new BuscadorProducto(true);
                     buscador.setLocationRelativeTo(ComprasForm.this);
                     buscador.setModal(true);
                     buscador.setVisible(true);
                     if (buscador.getCodigo() != null) {
                        int filaSeleccionada = ComprasForm.this.tabla.getSelectedRow();
                        ComprasForm.this.tabla.clearSelection();
                        ComprasForm.this.tabla.requestFocusInWindow();
                        ComprasForm.this.tabla.changeSelection(filaSeleccionada, ComprasForm.this.tabla.getColumn("Cantidad").getModelIndex(), false, false);
                        ComprasForm.this.tabla.editCellAt(filaSeleccionada, ComprasForm.this.tabla.getColumn("Cantidad").getModelIndex());
                        Component editorComponent = ComprasForm.this.tabla.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }

                        ComprasForm.this.tabla
                           .setValueAt(
                              buscador.getCodigo(), ComprasForm.this.tabla.getSelectedRow(), ComprasForm.this.tabla.getColumn("Código").getModelIndex()
                           );
                        ComprasForm.this.tabla
                           .setValueAt(
                              buscador.getNombre(), ComprasForm.this.tabla.getSelectedRow(), ComprasForm.this.tabla.getColumn("Descripción").getModelIndex()
                           );
                        ComprasForm.this.tabla
                           .setValueAt(
                              buscador.getV_tipoFiscal(), ComprasForm.this.tabla.getSelectedRow(), ComprasForm.this.tabla.getColumn("TF").getModelIndex()
                           );
                        ComprasForm.this.tabla
                           .setValueAt(buscador.getV_iva(), ComprasForm.this.tabla.getSelectedRow(), ComprasForm.this.tabla.getColumn("IVA").getModelIndex());
                        ComprasForm.this.tabla
                           .setValueAt(
                              buscador.getV_regimen(), ComprasForm.this.tabla.getSelectedRow(), ComprasForm.this.tabla.getColumn("REG").getModelIndex()
                           );
                        ComprasForm.this.tabla
                           .setValueAt(
                              buscador.getV_unidad_medida(), ComprasForm.this.tabla.getSelectedRow(), ComprasForm.this.tabla.getColumn("UM").getModelIndex()
                           );
                     }
                  }
               }
            }
         );
      this.txt_total_impuesto.addPropertyChangeListener("value", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            ComprasForm.this.txt_total_general.setValue(ComprasForm.this.txt_total_impuesto.getValue());
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ComprasForm frame = new ComprasForm();

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
