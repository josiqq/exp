package a00Ventas;

import a00Cotizaciones.CotizacionesE;
import a00Productos.ProductosE;
import a00pedidosProveedores.DecimalFilter;
import a2MenuPrincipal.ParametrosDetalleE;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.Beans;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.text.JTextComponent;
import utilidades.CuadroTexto2Decimales;
import utilidades.LabelImagenes;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextFieldConSQL;
import utilidades.LimiteTextFieldConSQLClientes;
import utilidades.LimiteTextFieldConSQLCondicionVta;
import utilidades.LimiteTextFieldTipoDoc;
import utilidades.NumerosTextField;
import utilidades.PanelPadre;
import utilidades.VerificarCamposVacios;
import utilidadesConexion.DatabaseConnection;
import utilidadesConexion.FechaActualE;
import utilidadesE.ConexionesE;
import utilidadesE.StockDepositosE;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorProducto;
import utilidadesTabla.BuscadordeCodigos;
import utilidadesVentanas.JinternalPadre;
import utilidadesVtas.CalculosTabla;
import utilidadesVtas.F_Calcular_Vtas;

public class VentasForm extends JinternalPadre {
   private int COD_VENDEDOR;
   private int COD_DEPOSITO;
   private int COD_MONEDA;
   private int COD_CLIENTE;
   private int COD_TIPO_DOCUMENTO;
   private int COD_CONDICION;
   private int CUOTAS;
   private int TIPO_FISCAL_CLIENTE;
   private int ID_PETICION;
   private String NOMBRE_VENDEDOR;
   private String NOMBRE_DEPOSITO;
   private String NOMBRE_MONEDA;
   private String NOMBRE_CLIENTE;
   private String NOMBRE_TIPO_DOCUMENTO;
   private String NOMBRE_CONDICION;
   private String RUC;
   private String DIRECCION;
   private String TELEFONO;
   protected TablaDetalleVentas tabla;
   private static final long serialVersionUID = 1L;
   private JDateChooser txt_fecha;
   private LimiteTextFieldTipoDoc txt_cod_tipo_documento;
   private LimiteTextFieldConSQL txt_cod_deposito;
   private LimiteTextFieldConSQL txt_cod_vendedor;
   private LimiteTextFieldConSQL txt_cod_moneda;
   private LimiteTextFieldConSQLClientes txt_cod_cliente;
   private LabelPrincipal lblNombreTipoDocumentoTexto;
   private LabelPrincipal lblNombreCondicion;
   private LabelPrincipal lblNombreClienteTexto;
   private LabelPrincipal lblRUCTexto;
   private LabelPrincipal lblNombreVendedorTexto;
   private LabelPrincipal lblNombreDepositoTexto;
   private LabelPrincipal lblNombreMonedaTexto;
   private LabelPrincipal lblTotalLineasTexto;
   private LabelPrincipal lblNumeroFacturaTexto;
   private LimiteTextFieldConSQLCondicionVta txt_cod_condicion;
   private NumerosTextField txt_cuotas;
   private NumerosTextField txt_dias;
   private CuadroTexto2Decimales txt_valor_descuento;
   private CuadroTexto2Decimales txt_porcentaje_descuento;
   private LimiteTextArea txt_observacion;
   private DecimalFilter txt_total_moneda1;
   private DecimalFilter txt_total_moneda2;
   private DecimalFilter txt_total_moneda3;
   private DecimalFilter txt_total_moneda4;
   private String direccion_cliente;
   private String telefono_cliente;
   private DecimalFilter txt_gravado10;
   private DecimalFilter txt_gravado5;
   private DecimalFilter txt_exentas;
   private DecimalFilter txt_iva10;
   private DecimalFilter txt_iva5;
   private DecimalFilter txt_total_impuestos;
   private int SW;
   private NumerosTextField txt_tipo_fiscal_cliente;
   List<Integer> codigosAEliminar = new ArrayList<>();
   private LabelPrincipal lblTimbrado;
   private LabelPrincipal lblNumeracion;
   private LabelPrincipal lblUltimoRegistroTexto;
   private int MODIFICA_FECHA;

   public void focoDescuentoCabeceraPorcentaje() {
      String v_descuento = String.valueOf(this.txt_porcentaje_descuento.getText()).replace(",", ".");

      try {
         DecimalFormat formato2Decimales = new DecimalFormat("#,##0.00");
         String v_final = formato2Decimales.format(Double.parseDouble(v_descuento));
         int descColumn = this.tabla.getColumn("Desc.").getModelIndex();

         for (int i = 0; i <= this.tabla.getRowCount() - 1; i++) {
            this.tabla.setValueAt(v_final, i, descColumn);
            F_Calcular_Vtas.calcularDescuento(i, this.tabla);
            CalculosTabla.calcularIvas(i, this.tabla, Integer.parseInt(this.txt_tipo_fiscal_cliente.getText()));
            CalculosTabla.cargarImpuestos(
               this.tabla,
               this.txt_exentas,
               this.txt_gravado10,
               this.txt_iva10,
               this.txt_gravado5,
               this.txt_iva5,
               this.txt_total_impuestos,
               Integer.parseInt(this.txt_tipo_fiscal_cliente.getText())
            );
         }

         this.txt_cuotas.setValue(1);
         this.txt_dias.setValue(1);
         Number valor = (Number)this.txt_total_impuestos.getValue();
         double totalImpuesto = valor.doubleValue();
         this.txt_total_moneda1.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_cod_moneda.getText()), 1, totalImpuesto, this).getCotizacion());
         this.txt_total_moneda2.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_cod_moneda.getText()), 2, totalImpuesto, this).getCotizacion());
         this.txt_total_moneda3.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_cod_moneda.getText()), 3, totalImpuesto, this).getCotizacion());
         this.txt_total_moneda4.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_cod_moneda.getText()), 4, totalImpuesto, this).getCotizacion());
      } catch (Exception var8) {
         var8.printStackTrace();
         LogErrores.errores(var8, "Error al formatear Decimal...", this);
      }
   }

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         VentasE b = VentasE.buscarVta(Integer.parseInt(this.txt_codigo.getText().trim()), this.tabla, this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarVta(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         int nro_factura = 0;
         if (this.lblNumeroFacturaTexto.getText().trim().equals("0")) {
            nro_factura = VentasE.buscarNroFactura(Integer.parseInt(this.lblNumeracion.getText().replace(".", "")), this);
         } else {
            nro_factura = Integer.parseInt(this.lblNumeroFacturaTexto.getText());
         }

         BuscadordeCodigos buscador = new BuscadordeCodigos(nro_factura);
         buscador.setModal(true);
         buscador.setVisible(true);
         if (buscador.getCodigo() != null && !buscador.getCodigo().trim().isEmpty()) {
            this.lblNumeroFacturaTexto.setText(buscador.getCodigo());
            VentasE entidad = this.CargarEntidades();
            if (this.SW == 0) {
               if (entidad != null) {
                  int codigo = VentasE.insertarVta(entidad, this.tabla, this);
                  if (codigo != 0) {
                     DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                     rs.setLocationRelativeTo(this);
                     rs.setVisible(true);
                     this.inicializarObjetos();
                  }
               }
            } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
               int codigo = VentasE.actualizarVta(entidad, this.tabla, this.codigosAEliminar, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro actualizado correctamente...", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
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
            VentasE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = VentasE.eliminarVta(ent, this);
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

   public void cargarVta(VentasE e) {
      this.txt_codigo.setValue(e.getNro_registro());
      this.SW = Integer.parseInt(String.valueOf(e.getNro_registro()));
      Date v_fecha = null;

      try {
         v_fecha = new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha());
         this.txt_fecha.setDate(v_fecha);
      } catch (ParseException var4) {
         LogErrores.errores(var4, "Error al Convertir Fecha...", this);
      }

      this.txt_cod_cliente.setValue(e.getCod_cliente());
      this.lblNombreClienteTexto.setText(e.getNombre_cliente());
      this.lblRUCTexto.setText(e.getRuc());
      this.telefono_cliente = e.getTelefono();
      this.direccion_cliente = e.getDireccion();
      this.txt_cod_condicion.setValue(e.getCod_condicion());
      this.lblNombreCondicion.setText(e.getNombre_condicion());
      this.txt_cod_deposito.setValue(e.getCod_deposito());
      this.lblNombreDepositoTexto.setText(e.getNombre_deposito());
      this.txt_cod_moneda.setValue(e.getCod_moneda());
      this.lblNombreMonedaTexto.setText(e.getNombre_moneda());
      this.txt_cod_tipo_documento.setValue(e.getCod_tipo_documento());
      this.lblNombreTipoDocumentoTexto.setText(e.getNombre_tipo_documento());
      this.txt_cod_vendedor.setValue(e.getCod_vendedor());
      this.lblNombreVendedorTexto.setText(e.getNombre_vendedor());
      this.txt_cuotas.setValue(e.getCuotas());
      this.txt_dias.setValue(e.getDias());
      this.txt_exentas.setValue(e.getImporte_exento());
      this.txt_gravado10.setValue(e.getImporte_gravado10());
      this.txt_gravado5.setValue(e.getImporte_gravado5());
      this.txt_iva10.setValue(e.getImporte_iva10());
      this.txt_iva5.setValue(e.getImporte_iva5());
      this.txt_total_impuestos
         .setValue(e.getImporte_exento() + e.getImporte_gravado10() + e.getImporte_gravado5() + e.getImporte_iva10() + e.getImporte_iva5());
      this.txt_observacion.setText(e.getObservacion());
      this.txt_porcentaje_descuento.setValue(e.getPorcentaje_descuento());
      this.txt_valor_descuento.setValue(e.getImporte_descuento());
      this.txt_total_moneda1.setValue(e.getValor_moneda1());
      this.txt_total_moneda2.setValue(e.getValor_moneda2());
      this.txt_total_moneda3.setValue(e.getValor_moneda3());
      this.txt_total_moneda4.setValue(e.getValor_moneda4());
      this.txt_tipo_fiscal_cliente.setValue(e.getTipo_fiscal_cliente());
      this.lblNumeroFacturaTexto.setText(String.valueOf(e.getNro_factura()));
      this.lblTimbrado.setText(String.valueOf(e.getTimbrado()));
      this.lblNumeracion.setText(String.valueOf(e.getNumeracion()));
   }

   public VentasE CargarEntidades() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha = "";
      double v_total_gravado10 = 0.0;
      double v_total_gravado5 = 0.0;
      double v_total_iva5 = 0.0;
      double v_total_iva10 = 0.0;
      double v_total_exento = 0.0;
      double v_total = 0.0;
      Number valor = (Number)this.txt_gravado10.getValue();
      v_total_gravado10 = valor.doubleValue();
      valor = (Number)this.txt_gravado5.getValue();
      v_total_gravado5 = valor.doubleValue();
      valor = (Number)this.txt_iva10.getValue();
      v_total_iva10 = valor.doubleValue();
      valor = (Number)this.txt_iva5.getValue();
      v_total_iva5 = valor.doubleValue();
      valor = (Number)this.txt_exentas.getValue();
      v_total_exento = valor.doubleValue();
      valor = (Number)this.txt_total_impuestos.getValue();
      v_total = valor.doubleValue();

      try {
         fecha = dateFormat.format(this.txt_fecha.getDate());
      } catch (Exception var19) {
         LogErrores.errores(var19, "Debe de cargar Fecha Inicial Vencimiento...", this);
         this.txt_fecha.requestFocusInWindow();
         return null;
      }

      try {
         return new VentasE(
            Integer.parseInt(this.txt_codigo.getText().trim().replace(".", "")),
            fecha,
            Integer.parseInt(this.txt_cod_moneda.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_deposito.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_vendedor.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_cliente.getText().trim().replace(".", "")),
            this.lblNombreClienteTexto.getText(),
            this.lblRUCTexto.getText(),
            this.direccion_cliente,
            this.telefono_cliente,
            Integer.parseInt(this.txt_cod_tipo_documento.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_condicion.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_dias.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cuotas.getText().trim().replace(".", "")),
            Double.parseDouble(String.valueOf(this.txt_porcentaje_descuento.getText()).replace(".", "").replace(",", ".")),
            Double.parseDouble(String.valueOf(this.txt_valor_descuento.getText()).replace(".", "").replace(",", ".")),
            this.txt_observacion.getText(),
            v_total_gravado10,
            v_total_gravado5,
            v_total_iva10,
            v_total_iva5,
            v_total_exento,
            v_total,
            Integer.parseInt(this.lblTimbrado.getText()),
            Integer.parseInt(this.lblNumeracion.getText()),
            Integer.parseInt(this.lblNumeroFacturaTexto.getText()),
            this.ID_PETICION
         );
      } catch (NumberFormatException var17) {
         LogErrores.errores(var17, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var18) {
         LogErrores.errores(var18, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   private void inicializarObjetos() {
      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblUltimoRegistroTexto.setText(String.valueOf(formatoLinea.format(VentasE.ultimoRegistro(this))));
      ModeloVentasCliente modelo = (ModeloVentasCliente)this.tabla.getModel();
      modelo.deleteRow(this.tabla);
      FechaActualE fechaActual = FechaActualE.buscarFechaactual(this);
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      String fechaActualStr = fechaActual.getFechaActual();

      try {
         Date fechaDate = dateFormat.parse(fechaActualStr);
         this.txt_fecha.setDate(fechaDate);
      } catch (ParseException var7) {
         var7.printStackTrace();
      }

      this.lblNumeracion.setText("0");
      this.lblTimbrado.setText("0");
      this.lblNumeroFacturaTexto.setText("0");
      this.SW = 0;
      this.txt_cod_cliente.setValue(this.COD_CLIENTE);
      this.lblNombreClienteTexto.setText(this.NOMBRE_CLIENTE);
      this.lblRUCTexto.setText(this.RUC);
      this.direccion_cliente = this.DIRECCION;
      this.telefono_cliente = this.TELEFONO;
      this.txt_cod_deposito.setValue(this.COD_DEPOSITO);
      this.lblNombreDepositoTexto.setText(this.NOMBRE_DEPOSITO);
      this.txt_cod_moneda.setValue(this.COD_MONEDA);
      this.lblNombreMonedaTexto.setText(this.NOMBRE_MONEDA);
      this.txt_cod_condicion.setValue(this.COD_CONDICION);
      this.lblNombreCondicion.setText(this.NOMBRE_CONDICION);
      this.txt_cod_tipo_documento.setValue(this.COD_TIPO_DOCUMENTO);
      this.lblNombreTipoDocumentoTexto.setText(this.NOMBRE_TIPO_DOCUMENTO);
      this.txt_cod_vendedor.setValue(this.COD_VENDEDOR);
      this.lblNombreVendedorTexto.setText(this.NOMBRE_VENDEDOR);
      this.txt_cuotas.setValue(this.CUOTAS);
      this.txt_tipo_fiscal_cliente.setValue(this.TIPO_FISCAL_CLIENTE);
      this.txt_dias.setValue(0);
      this.txt_codigo.setValue(0);
      this.txt_observacion.setText("");
      this.txt_porcentaje_descuento.setValue(0);
      this.txt_valor_descuento.setValue(0);
      this.txt_total_moneda4.setValue(0);
      this.txt_total_moneda1.setValue(0);
      this.txt_total_moneda2.setValue(0);
      this.txt_total_moneda3.setValue(0);
      this.txt_exentas.setValue(0);
      this.txt_gravado10.setValue(0);
      this.txt_gravado5.setValue(0);
      this.txt_iva10.setValue(0);
      this.txt_iva5.setValue(0);
      this.txt_total_impuestos.setValue(0);
      this.codigosAEliminar.clear();
      this.habilitaCabecera();
   }

   private void cargarParametros() {
      DatabaseConnection db = DatabaseConnection.getInstance();
      VentasParametrosE parametro = VentasParametrosE.buscarParametros(db.getUsuario(), this);
      this.ID_PETICION = ConexionesE.id_peticion(db.getUsuario());
      this.COD_CLIENTE = parametro.getCod_cliente();
      this.NOMBRE_CLIENTE = parametro.getNombre_cliente();
      this.RUC = parametro.getRuc();
      this.TELEFONO = parametro.getTelefono();
      this.DIRECCION = parametro.getDireccion();
      this.COD_CONDICION = parametro.getCod_condicion();
      this.NOMBRE_CONDICION = parametro.getNombre_condicion();
      this.CUOTAS = parametro.getCuotas();
      this.COD_DEPOSITO = parametro.getCod_deposito();
      this.NOMBRE_DEPOSITO = parametro.getNombre_deposito();
      this.COD_MONEDA = parametro.getCod_moneda();
      this.NOMBRE_MONEDA = parametro.getNombre_moneda();
      this.COD_TIPO_DOCUMENTO = parametro.getCod_tipo_documento();
      this.NOMBRE_TIPO_DOCUMENTO = parametro.getNombre_tipo_documento();
      this.COD_VENDEDOR = parametro.getCod_vendedor();
      this.NOMBRE_VENDEDOR = parametro.getNombre_vendedor();
      this.TIPO_FISCAL_CLIENTE = parametro.getTipo_fiscal();
   }

   public VentasForm() {
      this.setTitle("Registro de Ventas");
      this.setBounds(100, 100, 1196, 613);
      if (!Beans.isDesignTime()) {
         this.cargarParametros();
      }

      PanelPadre panel_datos = new PanelPadre("Datos Basicos");
      PanelPadre panel_detalles = new PanelPadre("Detalles del Pedido");
      PanelPadre panel_totales = new PanelPadre("Totales");
      PanelPadre panel_clientes = new PanelPadre("Datos del Cliente");
      PanelPadre panel_datos_basicos = new PanelPadre("Datos");
      PanelPadre panel_condiciones = new PanelPadre("Condiciones");
      PanelPadre panel_descuento = new PanelPadre("Descuento");
      PanelPadre panel_descuento_1 = new PanelPadre("");
      PanelPadre panel_impuestos = new PanelPadre("");
      LabelPrincipal lblFecha = new LabelPrincipal("Fecha", "label");
      LabelPrincipal lblDeposito = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblObservacion = new LabelPrincipal("Observacion", "label");
      LabelPrincipal lblPorcentajeDescuento = new LabelPrincipal("% Descuento", "label");
      LabelPrincipal lblImporteDescuento = new LabelPrincipal("Importe Descuento", "label");
      LabelPrincipal lblTipoDocumento = new LabelPrincipal("Tipo Documento", "label");
      LabelPrincipal lblCondicion = new LabelPrincipal("Condicion", "label");
      LabelPrincipal lblDias = new LabelPrincipal("Dias", "label");
      LabelPrincipal lblCliente = new LabelPrincipal("Cliente", "label");
      LabelPrincipal lblCuotas = new LabelPrincipal("Cuotas", "label");
      LabelPrincipal lblVendedor = new LabelPrincipal("Vendedor", "label");
      LabelPrincipal lblNombreMoneda = new LabelPrincipal("Moneda", "label");
      LabelPrincipal lblNroRegistro = new LabelPrincipal("Nro. Registro", "label");
      LabelPrincipal lblImpuesto10 = new LabelPrincipal("IVA 10%", "label");
      LabelPrincipal lblGravado10 = new LabelPrincipal("Gravado 10%", "label");
      LabelPrincipal lblGravado5 = new LabelPrincipal("Gravado 5%", "label");
      LabelPrincipal lblExento = new LabelPrincipal("Exentas", "label");
      LabelPrincipal lblIva5 = new LabelPrincipal("IVA 5%", "label");
      LabelPrincipal lblTotal = new LabelPrincipal("Total", "label");
      LabelPrincipal lblNumeroFactura = new LabelPrincipal("Nro. de Factura:", "lineas");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro:", "lineas");
      LabelPrincipal lblTotalLinea = new LabelPrincipal("Total Lineas:", "lineas");
      LabelImagenes lblNombreMoneda1 = new LabelImagenes("moneda1");
      LabelImagenes lblNombreMoneda2 = new LabelImagenes("moneda2");
      LabelImagenes lblNombreMoneda3 = new LabelImagenes("moneda3");
      LabelImagenes lblNombreMoneda4 = new LabelImagenes("moneda4");
      this.lblTotalLineasTexto = new LabelPrincipal("0", "lineas");
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblTimbrado = new LabelPrincipal("Total", "label");
      this.lblNumeracion = new LabelPrincipal("Total", "label");
      this.lblNumeroFacturaTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreMonedaTexto = new LabelPrincipal(0);
      this.lblNombreDepositoTexto = new LabelPrincipal(0);
      this.lblNombreVendedorTexto = new LabelPrincipal(0);
      this.lblNombreTipoDocumentoTexto = new LabelPrincipal(0);
      this.lblNombreCondicion = new LabelPrincipal(0);
      this.lblNombreClienteTexto = new LabelPrincipal(0);
      this.lblRUCTexto = new LabelPrincipal(0);
      this.txt_total_moneda1 = new DecimalFilter(2);
      this.txt_total_moneda1.setEditable(false);
      this.txt_total_moneda1.setFont(new Font("Arial", 0, 21));
      this.txt_total_moneda2 = new DecimalFilter(2);
      this.txt_total_moneda2.setEditable(false);
      this.txt_total_moneda2.setFont(new Font("Arial", 0, 21));
      this.txt_total_moneda3 = new DecimalFilter(2);
      this.txt_total_moneda3.setEditable(false);
      this.txt_total_moneda3.setFont(new Font("Arial", 0, 21));
      this.txt_total_moneda4 = new DecimalFilter(2);
      this.txt_total_moneda4.setEditable(false);
      this.txt_total_moneda4.setFont(new Font("Arial", 0, 21));
      this.txt_fecha = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_porcentaje_descuento = new CuadroTexto2Decimales(2);
      this.txt_valor_descuento = new CuadroTexto2Decimales(2);
      this.txt_valor_descuento.setVisible(false);
      lblImporteDescuento.setVisible(false);
      this.txt_dias = new NumerosTextField(999L);
      this.txt_cuotas = new NumerosTextField(99L);
      this.txt_observacion = new LimiteTextArea(70);
      this.txt_total_impuestos = new DecimalFilter(2);
      this.txt_tipo_fiscal_cliente = new NumerosTextField(9L);
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
      this.txt_total_impuestos.setEditable(false);
      this.txt_total_impuestos.setFont(new Font("Arial", 0, 14));
      this.txt_cod_moneda = new LimiteTextFieldConSQL(999999, this.lblNombreMonedaTexto, "Monedas", this);
      this.txt_cod_moneda.setEnabled(false);
      this.txt_cod_deposito = new LimiteTextFieldConSQL(999999, this.lblNombreDepositoTexto, "Depositos", this);
      this.txt_cod_deposito.setEnabled(false);
      this.txt_cod_vendedor = new LimiteTextFieldConSQL(999999, this.lblNombreVendedorTexto, "Vendedores", this);
      this.txt_cod_vendedor.setEnabled(false);
      this.txt_cod_tipo_documento = new LimiteTextFieldTipoDoc(999999, this.lblNombreTipoDocumentoTexto, this.lblTimbrado, this.lblNumeracion, this);
      this.txt_cod_condicion = new LimiteTextFieldConSQLCondicionVta(999999, this.lblNombreCondicion, this.txt_dias, this);
      this.txt_cod_cliente = new LimiteTextFieldConSQLClientes(
         999999, this.lblNombreClienteTexto, this.lblRUCTexto, this.telefono_cliente, this.direccion_cliente, this.txt_tipo_fiscal_cliente, this
      );
      this.tabla = new TablaDetalleVentas(
         this.codigosAEliminar,
         this.txt_fecha,
         this.txt_exentas,
         this.txt_gravado10,
         this.txt_iva10,
         this.txt_gravado5,
         this.txt_iva5,
         this.txt_total_impuestos,
         this.txt_cod_cliente,
         this.txt_cod_moneda,
         this.txt_tipo_fiscal_cliente,
         this.txt_cod_condicion,
         this.lblTotalLineasTexto,
         2,
         2,
         this.txt_total_moneda1,
         this.txt_total_moneda2,
         this.txt_total_moneda3,
         this.txt_total_moneda4,
         this.COD_DEPOSITO,
         this.ID_PETICION
      );
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addContainerGap()
                              .addComponent(panel_datos, -2, 883, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       Alignment.LEADING,
                                       groupLayout.createSequentialGroup()
                                          .addComponent(lblNumeroFactura, -2, 117, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNumeroFacturaTexto, -2, 86, -2)
                                    )
                                    .addComponent(panel_totales, Alignment.LEADING, -2, 282, -2)
                              )
                        )
                        .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel_detalles, -2, 1178, -2))
                  )
                  .addContainerGap(-1, 32767)
            )
            .addGroup(
               Alignment.TRAILING, groupLayout.createSequentialGroup().addContainerGap().addComponent(panel_impuestos, -2, 1179, -2).addContainerGap(-1, 32767)
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
                              .addComponent(panel_totales, -2, 189, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblNumeroFactura, -2, 15, -2)
                                    .addComponent(this.lblNumeroFacturaTexto, -2, 15, -2)
                              )
                        )
                        .addComponent(panel_datos, -2, 224, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalles, -2, 299, -2)
                  .addGap(9)
                  .addComponent(panel_impuestos, -2, 45, -2)
                  .addGap(0, 0, 32767)
            )
      );
      GroupLayout gl_panel_impuestos = new GroupLayout(panel_impuestos);
      gl_panel_impuestos.setHorizontalGroup(
         gl_panel_impuestos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_impuestos.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblGravado10, -2, 77, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_gravado10, -2, 117, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblGravado5, -2, 71, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_gravado5, -2, 117, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblExento, -2, 46, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_exentas, -2, 117, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblImpuesto10, -2, 51, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_iva10, -2, 117, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblIva5, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_iva5, -2, 117, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotal, -2, 38, -2)
                  .addGap(2)
                  .addComponent(this.txt_total_impuestos, -2, 117, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblTotalLinea, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblTotalLineasTexto, -2, 31, -2)
                  .addGap(277)
            )
      );
      gl_panel_impuestos.setVerticalGroup(
         gl_panel_impuestos.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_impuestos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_impuestos.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblGravado10, -2, 25, -2)
                        .addComponent(this.txt_gravado10, -2, 25, -2)
                        .addComponent(lblGravado5, -2, 25, -2)
                        .addComponent(this.txt_gravado5, -2, 25, -2)
                        .addComponent(lblExento, -2, 25, -2)
                        .addComponent(this.txt_exentas, -2, 25, -2)
                        .addComponent(lblImpuesto10, -2, 25, -2)
                        .addComponent(this.txt_iva10, -2, 25, -2)
                        .addComponent(lblIva5, -2, 25, -2)
                        .addComponent(this.txt_iva5, -2, 25, -2)
                        .addComponent(lblTotal, -2, 25, -2)
                        .addComponent(this.txt_total_impuestos, -2, 25, -2)
                        .addComponent(lblTotalLinea, -2, -1, -2)
                        .addComponent(this.lblTotalLineasTexto, -2, -1, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_impuestos.setLayout(gl_panel_impuestos);
      GroupLayout gl_panel_totales = new GroupLayout(panel_totales);
      gl_panel_totales.setHorizontalGroup(
         gl_panel_totales.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_totales.createSequentialGroup()
                  .addGroup(
                     gl_panel_totales.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNombreMoneda1, Alignment.LEADING, -2, 98, -2)
                        .addComponent(lblNombreMoneda2, Alignment.LEADING, -2, 98, -2)
                        .addGroup(
                           gl_panel_totales.createSequentialGroup()
                              .addGroup(
                                 gl_panel_totales.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblNombreMoneda3, -2, 98, -2)
                                    .addGroup(gl_panel_totales.createSequentialGroup().addGap(4).addComponent(lblNombreMoneda4, -2, 98, -2))
                              )
                              .addGroup(
                                 gl_panel_totales.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_totales.createSequentialGroup()
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addGroup(
                                             gl_panel_totales.createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(this.txt_total_moneda3, Alignment.LEADING, -1, -1, 32767)
                                                .addComponent(this.txt_total_moneda2, Alignment.LEADING, -1, -1, 32767)
                                                .addComponent(this.txt_total_moneda1, Alignment.LEADING, -1, 161, 32767)
                                          )
                                    )
                                    .addGroup(
                                       Alignment.TRAILING,
                                       gl_panel_totales.createSequentialGroup()
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.txt_total_moneda4, -2, 161, -2)
                                    )
                              )
                              .addPreferredGap(ComponentPlacement.RELATED, 7, 32767)
                        )
                  )
                  .addContainerGap()
            )
      );
      gl_panel_totales.setVerticalGroup(
         gl_panel_totales.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_totales.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_totales.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNombreMoneda1, -2, 39, -2)
                        .addComponent(this.txt_total_moneda1, -2, 39, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_totales.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNombreMoneda2, -2, 39, -2)
                        .addComponent(this.txt_total_moneda2, -2, 39, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_totales.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNombreMoneda3, -2, 39, -2)
                        .addComponent(this.txt_total_moneda3, -2, 39, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_totales.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNombreMoneda4, -2, 39, -2)
                        .addComponent(this.txt_total_moneda4, -2, 39, -2)
                  )
                  .addContainerGap(29, 32767)
            )
      );
      panel_totales.setLayout(gl_panel_totales);
      GroupLayout gl_panel_detalles = new GroupLayout(panel_detalles);
      gl_panel_detalles.setHorizontalGroup(
         gl_panel_detalles.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalles.createSequentialGroup().addComponent(scrollPane, -2, 1176, -2).addContainerGap(254, 32767))
      );
      gl_panel_detalles.setVerticalGroup(
         gl_panel_detalles.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalles.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 276, 32767))
      );
      panel_detalles.setLayout(gl_panel_detalles);
      GroupLayout gl_panel_datos_basicos = new GroupLayout(panel_datos_basicos);
      gl_panel_datos_basicos.setHorizontalGroup(
         gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_basicos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_basicos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblNombreMoneda, -1, -1, 32767)
                                    .addComponent(lblFecha, -1, -1, 32767)
                                    .addComponent(lblNroRegistro, -2, 77, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING, false)
                                    .addGroup(
                                       gl_panel_datos_basicos.createSequentialGroup()
                                          .addComponent(this.txt_cod_moneda, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreMonedaTexto, -2, 200, -2)
                                    )
                                    .addComponent(this.txt_fecha, -2, -1, -2)
                                    .addGroup(
                                       gl_panel_datos_basicos.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 64, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblUltimoRegistro, -1, -1, 32767)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, 70, -2)
                                          .addGap(9)
                                    )
                              )
                        )
                        .addGroup(
                           gl_panel_datos_basicos.createSequentialGroup()
                              .addComponent(lblDeposito, -1, 77, 32767)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_deposito, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreDepositoTexto, -2, 200, -2)
                        )
                        .addGroup(
                           gl_panel_datos_basicos.createSequentialGroup()
                              .addComponent(lblVendedor, -1, 77, 32767)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_vendedor, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreVendedorTexto, -2, 200, -2)
                        )
                  )
                  .addGap(18)
            )
      );
      gl_panel_datos_basicos.setVerticalGroup(
         gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_basicos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.TRAILING, false)
                        .addGroup(
                           gl_panel_datos_basicos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos_basicos.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(this.txt_codigo, -2, 25, -2)
                                    .addComponent(lblNroRegistro, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                        )
                        .addGroup(
                           gl_panel_datos_basicos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos_basicos.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblUltimoRegistro, -2, 15, -2)
                                    .addComponent(this.lblUltimoRegistroTexto, -2, 15, -2)
                              )
                              .addGap(14)
                        )
                  )
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.BASELINE).addComponent(lblFecha, -2, 25, -2).addComponent(this.txt_fecha, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_basicos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblNombreMoneda, -2, 25, -2)
                              .addComponent(this.txt_cod_moneda, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreMonedaTexto, -2, 25, -2)
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
                        .addComponent(this.lblNombreVendedorTexto, -2, 25, -2)
                        .addGroup(
                           gl_panel_datos_basicos.createParallelGroup(Alignment.TRAILING)
                              .addComponent(this.txt_cod_vendedor, -2, 25, -2)
                              .addComponent(lblVendedor, -2, 25, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_datos_basicos.setLayout(gl_panel_datos_basicos);
      GroupLayout gl_panel_descuento_1 = new GroupLayout(panel_descuento_1);
      gl_panel_descuento_1.setHorizontalGroup(
         gl_panel_descuento_1.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_descuento_1.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblObservacion, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_observacion, -1, 395, 32767)
                  .addContainerGap()
            )
      );
      gl_panel_descuento_1.setVerticalGroup(
         gl_panel_descuento_1.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_descuento_1.createSequentialGroup()
                  .addGroup(
                     gl_panel_descuento_1.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(this.txt_observacion, Alignment.LEADING, -1, -1, 32767)
                        .addGroup(Alignment.LEADING, gl_panel_descuento_1.createSequentialGroup().addContainerGap().addComponent(lblObservacion, -2, 25, -2))
                  )
                  .addContainerGap(25, 32767)
            )
      );
      panel_descuento_1.setLayout(gl_panel_descuento_1);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(panel_descuento, 0, 0, 32767)
                        .addComponent(panel_datos_basicos, -1, -1, 32767)
                  )
                  .addGap(18)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_descuento_1, -2, 484, -2)
                        .addComponent(panel_condiciones, -2, 484, 32767)
                        .addComponent(panel_clientes, -1, 484, 32767)
                  )
                  .addGap(31)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(panel_datos_basicos, -2, 171, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_descuento, -2, 30, -2)
                        )
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(panel_clientes, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_condiciones, -2, 72, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_descuento_1, -2, 48, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      GroupLayout gl_panel_descuento = new GroupLayout(panel_descuento);
      gl_panel_descuento.setHorizontalGroup(
         gl_panel_descuento.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_descuento.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblPorcentajeDescuento, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_porcentaje_descuento, -2, 50, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblImporteDescuento, -2, 115, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_valor_descuento, -2, 90, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      gl_panel_descuento.setVerticalGroup(
         gl_panel_descuento.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_descuento.createSequentialGroup()
                  .addGroup(
                     gl_panel_descuento.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.txt_porcentaje_descuento, -2, 25, -2)
                        .addComponent(lblImporteDescuento, -2, 25, -2)
                        .addComponent(lblPorcentajeDescuento, -2, 25, -2)
                        .addComponent(this.txt_valor_descuento, -2, 25, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_descuento.setLayout(gl_panel_descuento);
      GroupLayout gl_panel_condiciones = new GroupLayout(panel_condiciones);
      gl_panel_condiciones.setHorizontalGroup(
         gl_panel_condiciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_condiciones.createSequentialGroup()
                  .addGroup(
                     gl_panel_condiciones.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_condiciones.createSequentialGroup()
                              .addGap(4)
                              .addComponent(lblCondicion, -2, 65, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.txt_cod_condicion, -2, 47, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreCondicion, -2, 126, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblDias, -2, 26, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_dias, -2, 43, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblCuotas, -2, 46, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cuotas, -2, 43, -2)
                        )
                        .addGroup(
                           gl_panel_condiciones.createSequentialGroup()
                              .addContainerGap()
                              .addComponent(lblTipoDocumento, -2, 95, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_tipo_documento, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreTipoDocumentoTexto, -2, 274, -2)
                        )
                  )
                  .addGap(127)
            )
      );
      gl_panel_condiciones.setVerticalGroup(
         gl_panel_condiciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_condiciones.createSequentialGroup()
                  .addGroup(
                     gl_panel_condiciones.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_condiciones.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_tipo_documento, -2, 25, -2)
                              .addComponent(lblTipoDocumento, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreTipoDocumentoTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_condiciones.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.lblNombreCondicion, -2, 25, -2)
                        .addGroup(
                           gl_panel_condiciones.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_condicion, -2, 25, -2)
                              .addComponent(lblCondicion, -2, 25, -2)
                        )
                        .addGroup(
                           gl_panel_condiciones.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblDias, -2, 25, -2)
                              .addComponent(this.txt_dias, -2, 25, -2)
                              .addComponent(lblCuotas, -2, 25, -2)
                              .addComponent(this.txt_cuotas, -2, 25, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_condiciones.setLayout(gl_panel_condiciones);
      GroupLayout gl_panel_clientes = new GroupLayout(panel_clientes);
      gl_panel_clientes.setHorizontalGroup(
         gl_panel_clientes.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_clientes.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_clientes.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_clientes.createSequentialGroup()
                              .addComponent(lblCliente, -2, 48, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_cliente, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreClienteTexto, -1, 310, 32767)
                        )
                        .addComponent(this.lblRUCTexto, -2, 200, -2)
                  )
                  .addContainerGap()
            )
      );
      gl_panel_clientes.setVerticalGroup(
         gl_panel_clientes.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_clientes.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_clientes.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblCliente, -2, 25, -2)
                        .addComponent(this.lblNombreClienteTexto, -2, 25, -2)
                        .addComponent(this.txt_cod_cliente, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblRUCTexto, -2, 25, -2)
                  .addContainerGap(12, 32767)
            )
      );
      panel_clientes.setLayout(gl_panel_clientes);
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      this.txt_total_impuestos.addPropertyChangeListener("value", evt -> {
         Number valor = (Number)this.txt_total_impuestos.getValue();
         double totalImpuesto = valor.doubleValue();
         this.txt_total_moneda1.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_cod_moneda.getText()), 1, totalImpuesto, null).getCotizacion());
         this.txt_total_moneda2.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_cod_moneda.getText()), 2, totalImpuesto, null).getCotizacion());
         this.txt_total_moneda3.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_cod_moneda.getText()), 3, totalImpuesto, null).getCotizacion());
         this.txt_total_moneda4.setValue(CotizacionesE.cambiarImporte(Integer.parseInt(this.txt_cod_moneda.getText()), 4, totalImpuesto, null).getCotizacion());
      });
      this.txt_porcentaje_descuento.addFocusListener(new FocusListener() {
         @Override
         public void focusLost(FocusEvent e) {
            VentasForm.this.focoDescuentoCabeceraPorcentaje();
         }

         @Override
         public void focusGained(FocusEvent e) {
         }
      });
      this.tabla.getInputMap(1).put(KeyStroke.getKeyStroke(112, 0), "F1");
      this.tabla
         .getActionMap()
         .put(
            "F1",
            new AbstractAction() {
               private static final long serialVersionUID = 1L;

               @Override
               public void actionPerformed(ActionEvent e) {
                  if (VentasForm.this.tabla.getSelectedColumn() == 0) {
                     BuscadorProducto buscador = new BuscadorProducto(0.0);
                     buscador.setLocationRelativeTo(VentasForm.this);
                     buscador.setModal(true);
                     buscador.setVisible(true);
                     if (buscador.getCodigo() != null) {
                        int filaSeleccionada = VentasForm.this.tabla.getSelectedRow();
                        VentasForm.this.tabla.clearSelection();
                        VentasForm.this.tabla.requestFocusInWindow();
                        VentasForm.this.tabla.changeSelection(filaSeleccionada, VentasForm.this.tabla.getColumn("Cantidad").getModelIndex(), false, false);
                        VentasForm.this.tabla.editCellAt(filaSeleccionada, VentasForm.this.tabla.getColumn("Cantidad").getModelIndex());
                        Component editorComponent = VentasForm.this.tabla.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String fecha = "";

                        try {
                           fecha = dateFormat.format(VentasForm.this.txt_fecha.getDate());
                        } catch (Exception var12) {
                           LogErrores.errores(var12, "Debe de cargar Fecha de Venta...");
                        }

                        ProductosE producto = ProductosE.cargarProductosPedidosVta(
                           buscador.getCodigo(),
                           Integer.parseInt(String.valueOf(VentasForm.this.txt_cod_cliente.getText())),
                           Integer.parseInt(String.valueOf(VentasForm.this.txt_cod_moneda.getText())),
                           fecha,
                           VentasForm.this,
                           Integer.parseInt(String.valueOf(VentasForm.this.txt_cod_condicion.getValue()))
                        );
                        if (producto.getNombre_producto() != null) {
                           FormatoDecimalOperacionSistema formatoDecimalImporte = new FormatoDecimalOperacionSistema(7);
                           VentasForm.this.tabla
                              .setValueAt(
                                 buscador.getCodigo(), VentasForm.this.tabla.getSelectedRow(), VentasForm.this.tabla.getColumn("Código").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 producto.getNombre_producto(),
                                 VentasForm.this.tabla.getSelectedRow(),
                                 VentasForm.this.tabla.getColumn("Descripción").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 producto.getTipo_fiscal(), VentasForm.this.tabla.getSelectedRow(), VentasForm.this.tabla.getColumn("TF").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 producto.getIva_producto(), VentasForm.this.tabla.getSelectedRow(), VentasForm.this.tabla.getColumn("IVA").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 producto.getPorcentaje_gravado(),
                                 VentasForm.this.tabla.getSelectedRow(),
                                 VentasForm.this.tabla.getColumn("Gravado").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 producto.getPorc_regimen(), VentasForm.this.tabla.getSelectedRow(), VentasForm.this.tabla.getColumn("REG").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 producto.getNombreUnidadMedida(),
                                 VentasForm.this.tabla.getSelectedRow(),
                                 VentasForm.this.tabla.getColumn("UM").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 formatoDecimalImporte.format(producto.getCosto_producto()),
                                 VentasForm.this.tabla.getSelectedRow(),
                                 VentasForm.this.tabla.getColumn("Costo").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 producto.getCod_lista(), VentasForm.this.tabla.getSelectedRow(), VentasForm.this.tabla.getColumn("CodLista").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 formatoDecimalImporte.format(producto.getPrecio_lista()),
                                 VentasForm.this.tabla.getSelectedRow(),
                                 VentasForm.this.tabla.getColumn("Precio Lista").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 formatoDecimalImporte.format(producto.getPrecio()),
                                 VentasForm.this.tabla.getSelectedRow(),
                                 VentasForm.this.tabla.getColumn("Precio Bruto").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 formatoDecimalImporte.format(producto.getPrecio_minimo()),
                                 VentasForm.this.tabla.getSelectedRow(),
                                 VentasForm.this.tabla.getColumn("Precio Minimo").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(
                                 formatoDecimalImporte.format(producto.getPrecio()),
                                 VentasForm.this.tabla.getSelectedRow(),
                                 VentasForm.this.tabla.getColumn("Precio Venta").getModelIndex()
                              );
                           VentasForm.this.tabla
                              .setValueAt(0, VentasForm.this.tabla.getSelectedRow(), VentasForm.this.tabla.getColumn("Desc.").getModelIndex());
                           F_Calcular_Vtas.calcularDescuento(VentasForm.this.tabla.getSelectedRow(), VentasForm.this.tabla);
                           CalculosTabla.calcularIvas(
                              VentasForm.this.tabla.getSelectedRow(),
                              VentasForm.this.tabla,
                              Integer.parseInt(VentasForm.this.txt_tipo_fiscal_cliente.getText())
                           );
                           CalculosTabla.cargarImpuestos(
                              VentasForm.this.tabla,
                              VentasForm.this.txt_exentas,
                              VentasForm.this.txt_gravado10,
                              VentasForm.this.txt_iva10,
                              VentasForm.this.txt_gravado5,
                              VentasForm.this.txt_iva5,
                              VentasForm.this.txt_total_impuestos,
                              Integer.parseInt(VentasForm.this.txt_tipo_fiscal_cliente.getText())
                           );
                           Number valor = (Number)VentasForm.this.txt_total_impuestos.getValue();
                           double totalImpuesto = valor.doubleValue();
                           VentasForm.this.txt_total_moneda1
                              .setValue(
                                 CotizacionesE.cambiarImporte(Integer.parseInt(VentasForm.this.txt_cod_moneda.getText()), 1, totalImpuesto, null)
                                    .getCotizacion()
                              );
                           VentasForm.this.txt_total_moneda2
                              .setValue(
                                 CotizacionesE.cambiarImporte(Integer.parseInt(VentasForm.this.txt_cod_moneda.getText()), 2, totalImpuesto, null)
                                    .getCotizacion()
                              );
                           VentasForm.this.txt_total_moneda3
                              .setValue(
                                 CotizacionesE.cambiarImporte(Integer.parseInt(VentasForm.this.txt_cod_moneda.getText()), 3, totalImpuesto, null)
                                    .getCotizacion()
                              );
                           VentasForm.this.txt_total_moneda4
                              .setValue(
                                 CotizacionesE.cambiarImporte(Integer.parseInt(VentasForm.this.txt_cod_moneda.getText()), 4, totalImpuesto, null)
                                    .getCotizacion()
                              );
                        }
                     }
                  }
               }
            }
         );
      this.txt_cod_condicion
         .addFocusListener(
            new FocusListener() {
               @Override
               public void focusLost(FocusEvent e) {
                  if (VerificarCamposVacios.verificar(VentasForm.this.txt_cod_deposito, VentasForm.this)
                     && VerificarCamposVacios.verificar(VentasForm.this.txt_cod_moneda, VentasForm.this)
                     && VerificarCamposVacios.verificar(VentasForm.this.txt_cod_cliente, VentasForm.this)) {
                     VentasForm.this.deshabilitarCabecera();
                     ModeloVentasCliente modelo = (ModeloVentasCliente)VentasForm.this.tabla.getModel();
                     modelo.addDefaultRow();
                  }
               }

               @Override
               public void focusGained(FocusEvent e) {
               }
            }
         );
      if (!Beans.isDesignTime()) {
         this.inicializarObjetos();
      }

      this.setDefaultCloseOperation(2);
      this.addInternalFrameListener(new InternalFrameAdapter() {
         @Override
         public void internalFrameClosed(InternalFrameEvent e) {
            System.out.println("internalFrameClosed fue llamado");
            StockDepositosE.eliminarTodoReserva(VentasForm.this.ID_PETICION);
         }
      });
      this.MODIFICA_FECHA = ParametrosDetalleE.buscarParametros(4, null);
      if (this.MODIFICA_FECHA == 1) {
         this.txt_fecha.setEnabled(true);
      } else {
         this.txt_fecha.setEnabled(false);
      }
   }

   private void deshabilitarCabecera() {
      this.txt_cod_cliente.setEnabled(false);
      this.txt_cod_condicion.setEnabled(false);
      this.txt_cod_deposito.setEnabled(false);
      this.txt_cod_moneda.setEnabled(false);
      this.txt_cod_tipo_documento.setEnabled(false);
      this.txt_cod_vendedor.setEnabled(false);
      this.txt_dias.setEnabled(false);
      this.txt_cuotas.setEnabled(false);
   }

   private void habilitaCabecera() {
      this.txt_cod_cliente.setEnabled(true);
      this.txt_cod_condicion.setEnabled(true);
      this.txt_cod_deposito.setEnabled(true);
      this.txt_cod_moneda.setEnabled(true);
      this.txt_cod_tipo_documento.setEnabled(true);
      this.txt_cod_vendedor.setEnabled(true);
      this.txt_dias.setEnabled(true);
      this.txt_cuotas.setEnabled(true);
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            VentasForm frame = new VentasForm();

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
