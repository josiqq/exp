package a0098OrdenCompra;

import a00pedidosProveedores.DecimalFilter;
import a00pedidosProveedores.ModeloTablaProveedores;
import com.toedter.calendar.JDateChooser;
import compradores.CompradoresE;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.Beans;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import utilidades.BotonPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextFieldConSQL;
import utilidades.NumerosTextField;
import utilidades.PanelPadre;
import utilidadesConexion.DatabaseConnection;
import utilidadesConexion.FechaActualE;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTablaDetalle.TablaDetallePedidos;
import utilidadesVentanas.JinternalPadre;

public class OrdenCompraForm extends JinternalPadre {
   private DecimalFilter txt_codigo = new DecimalFilter();
   private static final long serialVersionUID = 1L;
   private LimiteTextFieldConSQL txt_cod_plazo;
   private LimiteTextFieldConSQL txt_cod_tipo_documento;
   private LimiteTextFieldConSQL txt_cod_moneda;
   private LimiteTextFieldConSQL txt_cod_proveedor;
   private LimiteTextFieldConSQL txt_cod_sucursal;
   private LabelPrincipal lblNombrePlazoTexto;
   private LabelPrincipal lblNombreCompradorTexto;
   private LabelPrincipal lblNombreUsuarioTexto;
   private LabelPrincipal lblNombreTipoDocumentoTexto;
   private LabelPrincipal lblNombreMonedaTexto;
   private LabelPrincipal lblNombreProveedorTexto;
   private LabelPrincipal lblNombreSucursalTexto;
   private LabelPrincipal lblLineasTotalRecuperadas;
   private LabelPrincipal lblUltimoRegistroTexto;
   private JDateChooser txt_fecha;
   private DecimalFilter txt_total;
   private TablaDetallePedidos tabla;
   private int COD_COMPRADOR;
   private String NOMBRE_COMPRADOR;
   public int SW;
   private BotonPadre btnSeleccionarPedidos;
   private NumerosTextField txt_nro_pedido;
   private NumerosTextField txt_dias;
   private LimiteTextArea txt_observacion;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         OrdenCompraE b = OrdenCompraE.buscarOrdenCompra(Integer.parseInt(this.txt_codigo.getText().trim()), this, this.tabla, 2, 2);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarOC(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         if (this.tabla.isEditing()) {
            TableCellEditor editor = this.tabla.getCellEditor();
            if (editor != null) {
               editor.addCellEditorListener(new CellEditorListener() {
                  @Override
                  public void editingStopped(ChangeEvent e) {
                  }

                  @Override
                  public void editingCanceled(ChangeEvent e) {
                  }
               });
               editor.stopCellEditing();
            }
         }

         OrdenCompraE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = OrdenCompraE.insertarOrdenCompra(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = OrdenCompraE.actualizarOC(entidad, this);
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
            OrdenCompraE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = OrdenCompraE.eliminarOrdenCompra(ent, this);
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

   public void cargarOC(OrdenCompraE e) {
      this.txt_cod_sucursal.requestFocusInWindow();
      this.SW = Integer.parseInt(this.txt_codigo.getText());
      this.txt_cod_moneda.setValue(e.getCod_moneda());
      this.lblNombreMonedaTexto.setText(e.getNombre_moneda());
      this.txt_cod_plazo.setValue(e.getCod_plazo());
      this.lblNombrePlazoTexto.setText(e.getNombre_plazo());
      this.txt_cod_proveedor.setValue(e.getCod_proveedor());
      this.lblNombreProveedorTexto.setText(e.getNombre_proveedor());
      this.txt_cod_sucursal.setValue(e.getCod_sucursal());
      this.lblNombreSucursalTexto.setText(e.getNombre_sucursal());
      this.txt_cod_tipo_documento.setValue(e.getCod_tipo_doc());
      this.lblNombreTipoDocumentoTexto.setText(e.getNombre_tipo_doc());
      this.lblNombreUsuarioTexto.setText(e.getUsuario_alta());
      this.lblNombreCompradorTexto.setText(e.getNombre_comprador());
      this.txt_total.setValue(e.getTotal());
      Date v_fecha = null;

      try {
         v_fecha = new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha());
         this.txt_fecha.setDate(v_fecha);
      } catch (ParseException var4) {
         LogErrores.errores(var4, "Error al Convertir Fecha...", this);
      }

      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblLineasTotalRecuperadas.setText(String.valueOf(formatoLinea.format(this.tabla.getRowCount())));
   }

   public OrdenCompraE CargarEntidades() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha = "";

      try {
         fecha = dateFormat.format(this.txt_fecha.getDate());
      } catch (Exception var6) {
         LogErrores.errores(var6, "Debe de cargar Fecha...", this);
         this.txt_fecha.requestFocusInWindow();
         return null;
      }

      try {
         return new OrdenCompraE(
            Integer.parseInt(this.txt_codigo.getText().trim().replace(".", "")),
            fecha,
            this.COD_COMPRADOR,
            Integer.parseInt(this.txt_cod_sucursal.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_proveedor.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_moneda.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_tipo_documento.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_plazo.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_nro_pedido.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_dias.getText().trim().replace(".", "")),
            Double.parseDouble(this.txt_total.getValue().toString()),
            0,
            0,
            this.txt_observacion.getText()
         );
      } catch (NumberFormatException var4) {
         LogErrores.errores(var4, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var5) {
         LogErrores.errores(var5, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   private void inicializarObjetos() {
      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblUltimoRegistroTexto.setText(String.valueOf(formatoLinea.format(OrdenCompraE.ultimoRegistro(this))));
      FechaActualE fechaActual = FechaActualE.buscarFechaactual(this);
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      String fechaActualStr = fechaActual.getFechaActual();

      try {
         Date fechaDate = dateFormat.parse(fechaActualStr);
         this.txt_fecha.setDate(fechaDate);
      } catch (ParseException var6) {
         var6.printStackTrace();
      }

      this.SW = 0;
      this.txt_cod_moneda.setValue(0);
      this.lblNombreMonedaTexto.setText("");
      this.txt_cod_plazo.setValue(0);
      this.lblNombrePlazoTexto.setText("");
      this.txt_cod_proveedor.setValue(0);
      this.lblNombreProveedorTexto.setText("");
      this.txt_cod_sucursal.setValue(0);
      this.lblNombreSucursalTexto.setText("");
      this.txt_cod_tipo_documento.setValue(0);
      this.lblNombreTipoDocumentoTexto.setText("");
      this.txt_codigo.setValue(0);
      this.txt_total.setValue(0);
      ModeloTablaProveedores modelo = (ModeloTablaProveedores)this.tabla.getModel();
      modelo.deleteRow(this.tabla);
      modelo.addDefaultRow();
      this.lblLineasTotalRecuperadas.setText("0");
      this.txt_codigo.requestFocusInWindow();
      this.lblNombreUsuarioTexto.setText(DatabaseConnection.getInstance().getUsuario());
      this.lblNombreCompradorTexto.setText(this.NOMBRE_COMPRADOR);
      this.txt_nro_pedido.setValue(0);
      this.txt_dias.setValue(0);
   }

   public OrdenCompraForm() {
      this.setTitle("Registro de Orden de Compra");
      this.setBounds(100, 100, 1143, 584);
      PanelPadre panel_cabecera = new PanelPadre("");
      PanelPadre panel_detalles = new PanelPadre("Detalles del Pedido");
      PanelPadre panel = new PanelPadre("");
      PanelPadre panel_datos_basicos = new PanelPadre("Datos Basicos");
      PanelPadre panel_datos_proveedores = new PanelPadre("Datos del Proveedor");
      LabelPrincipal lblProveedor = new LabelPrincipal("Proveedor", "label");
      LabelPrincipal lblTipoDocumento = new LabelPrincipal("Tipo Documento", "label");
      LabelPrincipal lblTextoTotalLinea = new LabelPrincipal("Total:", "totalTexto");
      lblTextoTotalLinea.setFont(new Font("Dialog", 1, 18));
      LabelPrincipal lblTextoLineaRecuperadas = new LabelPrincipal("Total Lineas:", "lineas");
      this.lblLineasTotalRecuperadas = new LabelPrincipal("0", "lineas");
      LabelPrincipal lblMoneda = new LabelPrincipal("Moneda", "label");
      LabelPrincipal lblSucursal = new LabelPrincipal("Sucursal", "label");
      LabelPrincipal lblNroOC = new LabelPrincipal("Nro. OC", "label");
      LabelPrincipal lblPlazo = new LabelPrincipal("Plazo", "label");
      LabelPrincipal usuarioAlta = new LabelPrincipal("Usuario Alta", "label");
      LabelPrincipal lblComprador = new LabelPrincipal("Comprador", "label");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro:", "lineas");
      LabelPrincipal lblDias = new LabelPrincipal("Dias", "label");
      LabelPrincipal lblObservacion = new LabelPrincipal("Observacion", "label");
      LabelPrincipal lblNroPedido = new LabelPrincipal("Nro. Pedido", "label");
      LabelPrincipal lblFecha = new LabelPrincipal("Fecha", "label");
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreUsuarioTexto = new LabelPrincipal(0);
      this.lblNombreProveedorTexto = new LabelPrincipal(0);
      this.lblNombreTipoDocumentoTexto = new LabelPrincipal(0);
      this.lblNombrePlazoTexto = new LabelPrincipal(0);
      this.lblNombreCompradorTexto = new LabelPrincipal(0);
      this.lblNombreMonedaTexto = new LabelPrincipal(0);
      this.lblNombreSucursalTexto = new LabelPrincipal(0);
      this.txt_cod_proveedor = new LimiteTextFieldConSQL(999999, this.lblNombreProveedorTexto, "Proveedores", this);
      this.txt_cod_tipo_documento = new LimiteTextFieldConSQL(999999, this.lblNombreTipoDocumentoTexto, "TipoDocumentos", this);
      this.txt_cod_plazo = new LimiteTextFieldConSQL(999999, this.lblNombrePlazoTexto, "Plazos", this);
      this.txt_cod_sucursal = new LimiteTextFieldConSQL(999999, this.lblNombreSucursalTexto, "Sucursales", this);
      this.txt_cod_moneda = new LimiteTextFieldConSQL(999999, this.lblNombreMonedaTexto, "Monedas", this);
      this.txt_dias = new NumerosTextField(999999L);
      this.txt_observacion = new LimiteTextArea(70);
      this.txt_total = new DecimalFilter(2);
      this.txt_total.setEditable(false);
      this.txt_total.setFont(new Font("Arial", 1, 18));
      this.tabla = new TablaDetallePedidos(this.lblLineasTotalRecuperadas, null, 2, 2, this.txt_total);
      this.txt_fecha = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_nro_pedido = new NumerosTextField(9999L);
      this.txt_nro_pedido
         .addFocusListener(
            new FocusAdapter() {
               @Override
               public void focusLost(FocusEvent e) {
                  OrdenCompraForm.this.txt_nro_pedido.setBackground(Color.WHITE);
                  OrdenCompraForm.this.txt_total
                     .setValue(
                        OrdenCompraE.buscarDetallePedido(
                           Integer.parseInt(OrdenCompraForm.this.txt_nro_pedido.getText().trim().replace(".", "")),
                           Integer.parseInt(OrdenCompraForm.this.txt_cod_proveedor.getText().trim().replace(".", "")),
                           Integer.parseInt(OrdenCompraForm.this.txt_cod_moneda.getText().trim().replace(".", "")),
                           Integer.parseInt(OrdenCompraForm.this.txt_cod_sucursal.getText().trim().replace(".", "")),
                           OrdenCompraForm.this,
                           OrdenCompraForm.this.tabla,
                           2,
                           2
                        )
                     );
               }
            }
         );
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      this.txt_cod_proveedor
         .addFocusListener(
            new FocusAdapter() {
               @Override
               public void focusLost(FocusEvent e) {
                  OrdenCompraForm.this.txt_nro_pedido.setBackground(Color.WHITE);
                  OrdenCompraForm.this.txt_total
                     .setValue(
                        OrdenCompraE.buscarDetallePedido(
                           Integer.parseInt(OrdenCompraForm.this.txt_nro_pedido.getText().trim().replace(".", "")),
                           Integer.parseInt(OrdenCompraForm.this.txt_cod_proveedor.getText().trim().replace(".", "")),
                           Integer.parseInt(OrdenCompraForm.this.txt_cod_moneda.getText().trim().replace(".", "")),
                           Integer.parseInt(OrdenCompraForm.this.txt_cod_sucursal.getText().trim().replace(".", "")),
                           OrdenCompraForm.this,
                           OrdenCompraForm.this.tabla,
                           2,
                           2
                        )
                     );
               }
            }
         );
      this.btnSeleccionarPedidos = new BotonPadre("", "buscar.png");
      this.btnSeleccionarPedidos
         .addActionListener(
            new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                  BuscarPedidoOCForm buscador = new BuscarPedidoOCForm(
                     OrdenCompraForm.this,
                     Integer.parseInt(OrdenCompraForm.this.txt_cod_proveedor.getText().trim().replace(".", "")),
                     Integer.parseInt(OrdenCompraForm.this.txt_cod_moneda.getText().trim().replace(".", "")),
                     Integer.parseInt(OrdenCompraForm.this.txt_cod_sucursal.getText().trim().replace(".", ""))
                  );
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     OrdenCompraForm.this.txt_nro_pedido.setValue(buscador.getNro_pedido());
                     OrdenCompraForm.this.txt_nro_pedido.requestFocusInWindow();
                     SwingUtilities.invokeLater(() -> OrdenCompraForm.this.txt_cod_proveedor.requestFocusInWindow());
                  }
               }
            }
         );
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(
         gl_panel.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblTextoLineaRecuperadas, -2, 66, -2)
                  .addGap(6)
                  .addComponent(this.lblLineasTotalRecuperadas, -2, 69, -2)
                  .addPreferredGap(ComponentPlacement.RELATED, 529, 32767)
                  .addComponent(lblTextoTotalLinea, -2, 186, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_total, -2, 247, -2)
                  .addGap(25)
            )
      );
      gl_panel.setVerticalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel.createParallelGroup(Alignment.LEADING)
                              .addComponent(lblTextoLineaRecuperadas, -2, 17, -2)
                              .addComponent(this.lblLineasTotalRecuperadas, -2, 17, -2)
                        )
                        .addGroup(
                           gl_panel.createParallelGroup(Alignment.TRAILING, false)
                              .addComponent(this.txt_total, -1, -1, 32767)
                              .addComponent(lblTextoTotalLinea, -1, 39, 32767)
                        )
                  )
                  .addGap(22)
            )
      );
      panel.setLayout(gl_panel);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup().addComponent(panel_cabecera, 0, 0, 32767).addGap(36))
                        .addGroup(groupLayout.createSequentialGroup().addComponent(panel_detalles, -2, 1118, 32767).addContainerGap())
                        .addGroup(groupLayout.createSequentialGroup().addComponent(panel, -2, -1, -2).addContainerGap(36, 32767))
                  )
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               Alignment.LEADING,
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_cabecera, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalles, -2, 263, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel, -2, 61, -2)
                  .addContainerGap(37, 32767)
            )
      );
      GroupLayout gl_panel_detalles = new GroupLayout(panel_detalles);
      gl_panel_detalles.setHorizontalGroup(
         gl_panel_detalles.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalles.createSequentialGroup().addComponent(scrollPane, -2, 1118, -2).addContainerGap(-1, 32767))
      );
      gl_panel_detalles.setVerticalGroup(
         gl_panel_detalles.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalles.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 230, 32767))
      );
      panel_detalles.setLayout(gl_panel_detalles);
      GroupLayout gl_panel_cabecera = new GroupLayout(panel_cabecera);
      gl_panel_cabecera.setHorizontalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos_basicos, -2, 399, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_datos_proveedores, -2, 664, -2)
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addComponent(lblObservacion, -2, 74, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_observacion, -2, 565, -2)
                        )
                  )
                  .addContainerGap(36, 32767)
            )
      );
      gl_panel_cabecera.setVerticalGroup(
         gl_panel_cabecera.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_cabecera.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                        .addComponent(panel_datos_basicos, -2, 177, -2)
                        .addGroup(
                           gl_panel_cabecera.createSequentialGroup()
                              .addComponent(panel_datos_proveedores, -2, -1, -2)
                              .addGap(5)
                              .addGroup(
                                 gl_panel_cabecera.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblObservacion, -2, 25, -2)
                                    .addComponent(this.txt_observacion, -2, 44, -2)
                              )
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_datos_proveedores = new GroupLayout(panel_datos_proveedores);
      gl_panel_datos_proveedores.setHorizontalGroup(
         gl_panel_datos_proveedores.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_proveedores.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_proveedores.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(gl_panel_datos_proveedores.createSequentialGroup().addGap(3).addComponent(lblPlazo, -1, -1, 32767))
                        .addComponent(lblProveedor, -1, -1, 32767)
                        .addComponent(lblTipoDocumento, -1, 99, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_proveedores.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_datos_proveedores.createSequentialGroup()
                              .addComponent(this.txt_cod_plazo, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombrePlazoTexto, -2, 200, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblDias, -1, -1, 32767)
                        )
                        .addGroup(
                           gl_panel_datos_proveedores.createSequentialGroup()
                              .addComponent(this.txt_cod_tipo_documento, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreTipoDocumentoTexto, -2, 200, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblComprador, -1, -1, 32767)
                        )
                        .addGroup(
                           gl_panel_datos_proveedores.createSequentialGroup()
                              .addComponent(this.txt_cod_proveedor, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreProveedorTexto, -2, 200, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(usuarioAlta, -2, -1, -2)
                        )
                  )
                  .addGroup(
                     gl_panel_datos_proveedores.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_proveedores.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                              .addGroup(
                                 gl_panel_datos_proveedores.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.lblNombreUsuarioTexto, Alignment.TRAILING, -2, 200, -2)
                                    .addComponent(this.lblNombreCompradorTexto, Alignment.TRAILING, -2, 200, -2)
                              )
                              .addContainerGap()
                        )
                        .addGroup(
                           gl_panel_datos_proveedores.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_dias, -2, 41, -2)
                              .addContainerGap()
                        )
                  )
            )
      );
      gl_panel_datos_proveedores.setVerticalGroup(
         gl_panel_datos_proveedores.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_proveedores.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_proveedores.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_datos_proveedores.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblProveedor, -2, 25, -2)
                              .addComponent(this.txt_cod_proveedor, -2, 25, -2)
                        )
                        .addGroup(
                           gl_panel_datos_proveedores.createParallelGroup(Alignment.LEADING)
                              .addComponent(usuarioAlta, -2, 25, -2)
                              .addComponent(this.lblNombreProveedorTexto, -2, 25, -2)
                              .addComponent(this.lblNombreUsuarioTexto, -2, 25, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_proveedores.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_proveedores.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos_proveedores.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos_proveedores.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblTipoDocumento, -2, 25, -2)
                                          .addComponent(this.txt_cod_tipo_documento, -2, 25, -2)
                                    )
                                    .addComponent(this.lblNombreTipoDocumentoTexto, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos_proveedores.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos_proveedores.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblPlazo, -2, 25, -2)
                                          .addComponent(this.txt_cod_plazo, -2, 25, -2)
                                    )
                                    .addGroup(gl_panel_datos_proveedores.createSequentialGroup().addGap(4).addComponent(this.lblNombrePlazoTexto, -2, 25, -2))
                              )
                        )
                        .addGroup(
                           gl_panel_datos_proveedores.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos_proveedores.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.lblNombreCompradorTexto, -2, 25, -2)
                                    .addComponent(lblComprador, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos_proveedores.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(lblDias, -2, 25, -2)
                                    .addComponent(this.txt_dias, -2, 25, -2)
                              )
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_datos_proveedores.setLayout(gl_panel_datos_proveedores);
      GroupLayout gl_panel_datos_basicos = new GroupLayout(panel_datos_basicos);
      gl_panel_datos_basicos.setHorizontalGroup(
         gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_basicos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_datos_basicos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblMoneda, -1, -1, 32767)
                                    .addComponent(lblSucursal, -1, -1, 32767)
                                    .addComponent(lblNroOC, -1, 74, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING, false)
                                    .addGroup(
                                       gl_panel_datos_basicos.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 71, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblUltimoRegistro, -2, 70, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, 70, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos_basicos.createSequentialGroup()
                                          .addComponent(this.txt_cod_sucursal, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreSucursalTexto, -2, 233, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos_basicos.createSequentialGroup()
                                          .addComponent(this.txt_cod_moneda, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreMonedaTexto, -2, 233, -2)
                                    )
                              )
                              .addGap(29)
                        )
                        .addGroup(
                           gl_panel_datos_basicos.createSequentialGroup()
                              .addComponent(lblFecha, -2, 69, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.txt_fecha, -2, 119, -2)
                        )
                        .addGroup(
                           gl_panel_datos_basicos.createSequentialGroup()
                              .addComponent(lblNroPedido, -2, 74, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_nro_pedido, -2, 80, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSeleccionarPedidos, -2, 80, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_panel_datos_basicos.setVerticalGroup(
         gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_basicos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNroOC, -2, 25, -2)
                        .addComponent(this.txt_codigo, -2, 25, -2)
                        .addComponent(lblUltimoRegistro, -2, 15, -2)
                        .addComponent(this.lblUltimoRegistroTexto, -2, 15, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos_basicos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblSucursal, -2, 25, -2)
                              .addComponent(this.txt_cod_sucursal, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreSucursalTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblMoneda, -2, 25, -2)
                        .addComponent(this.txt_cod_moneda, -2, 25, -2)
                        .addComponent(this.lblNombreMonedaTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.LEADING).addComponent(lblFecha, -2, 25, -2).addComponent(this.txt_fecha, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_basicos.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(this.btnSeleccionarPedidos, 0, 0, 32767)
                        .addGroup(
                           gl_panel_datos_basicos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblNroPedido, -2, 25, -2)
                              .addComponent(this.txt_nro_pedido, -1, 25, 32767)
                        )
                  )
                  .addContainerGap(56, 32767)
            )
      );
      panel_datos_basicos.setLayout(gl_panel_datos_basicos);
      panel_cabecera.setLayout(gl_panel_cabecera);
      this.getContentPane().setLayout(groupLayout);
      if (!Beans.isDesignTime()) {
         CompradoresE comprador = CompradoresE.buscarCompradores2(DatabaseConnection.getInstance().getUsuario(), this);
         this.COD_COMPRADOR = comprador.getCod_comprador();
         this.NOMBRE_COMPRADOR = comprador.getNombre_comprador();
         this.inicializarObjetos();
      }
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            OrdenCompraForm frame = new OrdenCompraForm();

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
