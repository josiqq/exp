package a00TipoDocumentos;

import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utilidades.CheckPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidades.NumerosTextField;
import utilidades.PanelPadre;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadorTablaCargar;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class TipoDocumentosForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(25);
   private static final long serialVersionUID = 1L;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_archivo_impresion;
   private LimiteTextField txt_buscador;
   private LimiteTextField txt_punto_expedicion;
   private NumerosTextField txt_secuencia;
   private NumerosTextField txt_timbrado;
   private CheckPadre chckActivo;
   private CheckPadre chckActivoVta;
   private CheckPadre chckActivoCompra;
   private CheckPadre chckContado;
   private CheckPadre chckCredito;
   private CheckPadre chckNC;
   private CheckPadre chckPreVenta;
   private ModeloTablaDefecto modeloTabla;
   private BuscadorTabla tabla;
   public int SW;
   private JDateChooser txt_fecha_inicio;
   private JDateChooser txt_fecha_vencimiento;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         TipoDocumentosE b = TipoDocumentosE.buscarTipoDocumento(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarTipoDocumentos(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         TipoDocumentosE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = TipoDocumentosE.insertarTipoDocumentos(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = TipoDocumentosE.actualizarTipoDoc(entidad, this);
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
            TipoDocumentosE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = TipoDocumentosE.eliminarTipoDocumentos(ent, this);
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

   public void cargarTipoDocumentos(TipoDocumentosE e) {
      this.txt_codigo.setValue(e.getCod_tipo_documento());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_tipo_documento()));
      this.txt_nombre.setValue(e.getNombre_tipo_documento());
      this.txt_archivo_impresion.setValue(e.getArchivo_impresion());
      this.txt_secuencia.setValue(e.getNumeracion());
      Date v_fecha_inicio = null;
      Date v_fecha_vencimiento = null;

      try {
         v_fecha_inicio = new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha_inicio_vigencia());
         this.txt_fecha_inicio.setDate(v_fecha_inicio);
         v_fecha_vencimiento = new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha_vencimiento());
         this.txt_fecha_vencimiento.setDate(v_fecha_vencimiento);
      } catch (ParseException var5) {
         LogErrores.errores(var5, "Error al Convertir Fecha...", this);
      }

      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      if (e.getCompras() == 1) {
         this.chckActivoCompra.setSelected(true);
      } else {
         this.chckActivoCompra.setSelected(false);
      }

      if (e.getVentas() == 1) {
         this.chckActivoVta.setSelected(true);
      } else {
         this.chckActivoVta.setSelected(false);
      }

      if (e.getNc() == 1) {
         this.chckNC.setSelected(true);
      } else {
         this.chckNC.setSelected(false);
      }

      if (e.getPreventa() == 1) {
         this.chckPreVenta.setSelected(true);
      } else {
         this.chckPreVenta.setSelected(false);
      }

      if (e.getTipo() == 0) {
         this.chckContado.setSelected(true);
         this.chckCredito.setSelected(false);
      } else {
         this.chckContado.setSelected(false);
         this.chckCredito.setSelected(true);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public TipoDocumentosE CargarEntidades() {
      int tipo = 0;
      int nc = 0;
      int activo_vtas = 0;
      int activo_compras = 0;
      int pre_venta = 0;
      if (this.chckPreVenta.isSelected()) {
         pre_venta = 1;
      }

      if (this.chckActivoVta.isSelected()) {
         activo_vtas = 1;
      }

      if (this.chckActivoCompra.isSelected()) {
         activo_compras = 1;
      }

      if (this.chckNC.isSelected()) {
         nc = 1;
      }

      if (this.chckCredito.isSelected()) {
         tipo = 1;
      }

      int estado = 0;
      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha_inicio = null;
      String fecha_vencimiento = null;

      try {
         fecha_inicio = dateFormat.format(this.txt_fecha_inicio.getDate());
      } catch (Exception var14) {
         LogErrores.errores(var14, "Debe de cargar Fecha Inicial Vencimiento...", this);
         this.txt_fecha_inicio.requestFocusInWindow();
         return null;
      }

      try {
         fecha_vencimiento = dateFormat.format(this.txt_fecha_vencimiento.getDate());
      } catch (Exception var13) {
         LogErrores.errores(var13, "Debe de cargar Fecha Vencimiento...", this);
         this.txt_fecha_vencimiento.requestFocusInWindow();
         return null;
      }

      try {
         return new TipoDocumentosE(
            Integer.parseInt(this.txt_codigo.getText()),
            this.txt_nombre.getText(),
            estado,
            tipo,
            nc,
            activo_vtas,
            activo_compras,
            pre_venta,
            this.txt_archivo_impresion.getText(),
            Integer.parseInt(this.txt_secuencia.getText()),
            Integer.parseInt(this.txt_timbrado.getText()),
            fecha_inicio,
            fecha_vencimiento,
            this.txt_punto_expedicion.getText()
         );
      } catch (NumberFormatException var11) {
         LogErrores.errores(var11, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var12) {
         LogErrores.errores(var12, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   public void buscarTabla() {
      int filaSeleccionada = this.tabla.getSelectedRow();
      if (filaSeleccionada != -1) {
         this.txt_codigo.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString());
         this.SW = Integer.parseInt(String.valueOf(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString()));
         this.txt_nombre.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nombre").getModelIndex()).toString());
         this.txt_archivo_impresion.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Archivo").getModelIndex()).toString());
         this.txt_secuencia.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Secuencia").getModelIndex()).toString());
         this.txt_secuencia.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Timbrado").getModelIndex()).toString());
         Date v_fecha_inicio = null;
         Date v_fecha_vencimiento = null;

         try {
            v_fecha_inicio = new SimpleDateFormat("yyyy-MM-dd")
               .parse(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("FechaInicio").getModelIndex()).toString());
            this.txt_fecha_inicio.setDate(v_fecha_inicio);
            v_fecha_vencimiento = new SimpleDateFormat("yyyy-MM-dd")
               .parse(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("FechaVenc").getModelIndex()).toString());
            this.txt_fecha_vencimiento.setDate(v_fecha_vencimiento);
         } catch (ParseException var5) {
            LogErrores.errores(var5, "Error al Convertir Fecha...", this);
         }

         this.txt_punto_expedicion.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Expedicion").getModelIndex()).toString());
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().equals("0")) {
            this.chckContado.setSelected(true);
            this.chckCredito.setSelected(false);
         } else {
            this.chckContado.setSelected(false);
            this.chckCredito.setSelected(true);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NC").getModelIndex()).toString().equals("1")) {
            this.chckNC.setSelected(true);
         } else {
            this.chckNC.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Ventas").getModelIndex()).toString().equals("1")) {
            this.chckActivoVta.setSelected(true);
         } else {
            this.chckActivoVta.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Compras").getModelIndex()).toString().equals("1")) {
            this.chckActivoCompra.setSelected(true);
         } else {
            this.chckActivoCompra.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("PreVenta").getModelIndex()).toString().equals("1")) {
            this.chckPreVenta.setSelected(true);
         } else {
            this.chckPreVenta.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Estado").getModelIndex()).toString().equals("true")) {
            this.chckActivo.setSelected(true);
         } else {
            this.chckActivo.setSelected(false);
         }

         this.tabla.requestFocusInWindow();
      }
   }

   private void inicializarObjetos() {
      this.SW = 0;
      this.chckActivo.setSelected(true);
      this.chckActivoCompra.setSelected(false);
      this.chckActivoVta.setSelected(false);
      this.chckContado.setSelected(false);
      this.chckCredito.setSelected(false);
      this.chckNC.setSelected(false);
      this.chckPreVenta.setSelected(false);
      TablaBuscador.eliminarFilasConWhile(this.modeloTabla);
      this.txt_punto_expedicion.setValue("");
      this.txt_timbrado.setValue(0);
      this.txt_buscador.setValue("");
      this.txt_codigo.setValue(0);
      this.txt_nombre.setValue("");
      this.txt_secuencia.setValue(0);
      this.txt_archivo_impresion.setValue("");
      this.txt_fecha_inicio.setDate(null);
      this.txt_fecha_vencimiento.setDate(null);
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public TipoDocumentosForm() {
      this.setTitle("Registro de Tipo de Documentos");
      this.setBounds(100, 100, 625, 499);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("Buscador de Registros");
      PanelPadre panel_activaciones = new PanelPadre("Activaciones");
      PanelPadre panel_tipo = new PanelPadre("Tipo");
      PanelPadre panel_timbrado = new PanelPadre("Datos del Timbrado");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblSecuencia = new LabelPrincipal("Secuencia", "label");
      LabelPrincipal lblArchivoImpresion = new LabelPrincipal("Archivo de Impresion", "label");
      LabelPrincipal lblTimbrador = new LabelPrincipal("Timbrado", "label");
      LabelPrincipal lblInicioTimbrado = new LabelPrincipal("Inicio", "label");
      LabelPrincipal lblFechaVencimiento = new LabelPrincipal("Fecha Vencimiento", "label");
      LabelPrincipal lblPuntoExpedicion = new LabelPrincipal("Punto Expedicion", "label");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.txt_nombre = new LimiteTextField(25);
      this.txt_archivo_impresion = new LimiteTextField(25);
      this.txt_buscador = new LimiteTextField(25);
      this.txt_secuencia = new NumerosTextField(999L);
      this.txt_timbrado = new NumerosTextField(999999999L);
      this.txt_punto_expedicion = new LimiteTextField(25);
      this.txt_fecha_inicio = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_vencimiento = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.chckContado = new CheckPadre("Contado");
      this.chckCredito = new CheckPadre("Credito");
      this.chckNC = new CheckPadre("Nota de Credito");
      this.chckPreVenta = new CheckPadre("Pre Venta");
      this.chckActivo = new CheckPadre("Activo");
      this.chckActivoVta = new CheckPadre("Activo para Venta");
      this.chckActivoCompra = new CheckPadre("Activo para Compra");
      ButtonGroup grupoTipo = new ButtonGroup();
      grupoTipo.add(this.chckContado);
      grupoTipo.add(this.chckCredito);
      ButtonGroup grupoTipo2 = new ButtonGroup();
      grupoTipo2.add(this.chckNC);
      grupoTipo2.add(this.chckPreVenta);
      String[] cabecera = new String[]{
         "Codigo",
         "Nombre",
         "Estado",
         "Tipo",
         "NC",
         "Ventas",
         "Compras",
         "PreVenta",
         "Archivo",
         "Secuencia",
         "Timbrado",
         "FechaInicio",
         "FechaVenc",
         "Expedicion"
      };
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "TipoDocumentos", this);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(panel_datos, -1, -1, 32767)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, 120, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                        )
                        .addComponent(panel_buscador, -1, -1, 32767)
                  )
                  .addContainerGap()
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, 231, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -2, 196, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
                  .addContainerGap(29, 32767)
            )
      );
      GroupLayout gl_panel_timbrado = new GroupLayout(panel_timbrado);
      gl_panel_timbrado.setHorizontalGroup(
         gl_panel_timbrado.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_timbrado.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_timbrado.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblTimbrador, -2, 53, -2)
                        .addComponent(lblInicioTimbrado, -2, 53, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_timbrado.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_timbrado.createSequentialGroup()
                              .addComponent(this.txt_timbrado, -2, 116, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblPuntoExpedicion, -2, 110, -2)
                        )
                        .addGroup(
                           gl_panel_timbrado.createSequentialGroup()
                              .addComponent(this.txt_fecha_inicio, -2, 116, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblFechaVencimiento, -2, 110, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_timbrado.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.txt_fecha_vencimiento, -2, 116, -2)
                        .addComponent(this.txt_punto_expedicion, -2, 116, -2)
                  )
                  .addContainerGap(158, 32767)
            )
      );
      gl_panel_timbrado.setVerticalGroup(
         gl_panel_timbrado.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_timbrado.createSequentialGroup()
                  .addGroup(
                     gl_panel_timbrado.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_timbrado.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_timbrado, -2, 25, -2)
                              .addComponent(lblTimbrador, -2, 25, -2)
                        )
                        .addGroup(
                           gl_panel_timbrado.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_punto_expedicion, -2, 25, -2)
                              .addComponent(lblPuntoExpedicion, -2, 25, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_timbrado.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.txt_fecha_inicio, -2, 25, -2)
                        .addComponent(this.txt_fecha_vencimiento, -2, 25, -2)
                        .addComponent(lblInicioTimbrado, -2, 25, -2)
                        .addComponent(lblFechaVencimiento, -2, 25, -2)
                  )
                  .addContainerGap(26, 32767)
            )
      );
      panel_timbrado.setLayout(gl_panel_timbrado);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblSecuencia, -1, -1, 32767)
                                    .addComponent(lblNombre, -1, -1, 32767)
                                    .addComponent(lblCodigo, -1, -1, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(gl_panel_datos.createSequentialGroup().addComponent(this.txt_codigo, -2, 63, -2).addGap(287))
                                    .addGroup(gl_panel_datos.createSequentialGroup().addComponent(this.txt_nombre, -2, 249, -2).addGap(101))
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_secuencia, -2, 63, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblArchivoImpresion, -1, 130, 32767)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.txt_archivo_impresion, -2, 168, -2)
                                    )
                              )
                        )
                        .addGroup(gl_panel_datos.createSequentialGroup().addComponent(panel_activaciones, -2, 388, -2).addGap(40))
                  )
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(panel_tipo, -2, -1, -2)
                  .addGap(8)
            )
            .addGroup(gl_panel_datos.createSequentialGroup().addComponent(panel_timbrado, -2, 578, -2).addContainerGap(-1, 32767))
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGap(20)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblCodigo, -2, 25, -2)
                                    .addComponent(this.txt_codigo, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblNombre, -2, 25, -2)
                                    .addComponent(this.txt_nombre, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblSecuencia, -2, 25, -2)
                                    .addComponent(this.txt_secuencia, -2, 25, -2)
                                    .addComponent(lblArchivoImpresion, -2, 25, -2)
                                    .addComponent(this.txt_archivo_impresion, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_activaciones, -2, 32, -2)
                        )
                        .addGroup(gl_panel_datos.createSequentialGroup().addContainerGap().addComponent(panel_tipo, 0, 0, 32767))
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_timbrado, -2, 67, -2)
                  .addGap(31)
            )
      );
      GroupLayout gl_panel_tipo = new GroupLayout(panel_tipo);
      gl_panel_tipo.setHorizontalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_tipo.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_tipo.createParallelGroup(Alignment.LEADING, false)
                              .addComponent(this.chckNC, -1, -1, 32767)
                              .addComponent(this.chckCredito, -1, 127, 32767)
                              .addComponent(this.chckPreVenta, -1, -1, 32767)
                        )
                        .addComponent(this.chckContado, -2, 127, -2)
                  )
                  .addContainerGap()
            )
      );
      gl_panel_tipo.setVerticalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo.createSequentialGroup()
                  .addGap(16)
                  .addComponent(this.chckContado, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.chckCredito, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.chckNC, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.chckPreVenta, -2, 16, -2)
                  .addContainerGap(89, 32767)
            )
      );
      panel_tipo.setLayout(gl_panel_tipo);
      GroupLayout gl_panel_activaciones = new GroupLayout(panel_activaciones);
      gl_panel_activaciones.setHorizontalGroup(
         gl_panel_activaciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_activaciones.createSequentialGroup()
                  .addComponent(this.chckActivo, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.chckActivoVta, -1, -1, 32767)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.chckActivoCompra, -2, 178, -2)
                  .addGap(173)
            )
      );
      gl_panel_activaciones.setVerticalGroup(
         gl_panel_activaciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_activaciones.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_activaciones.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.chckActivo, -2, 16, -2)
                        .addComponent(this.chckActivoVta, -2, 16, -2)
                        .addComponent(this.chckActivoCompra, -2, 16, -2)
                  )
                  .addContainerGap(58, 32767)
            )
      );
      panel_activaciones.setLayout(gl_panel_activaciones);
      panel_datos.setLayout(gl_panel_datos);
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, -1, 560, 32767)
                        .addGroup(gl_panel_buscador.createSequentialGroup().addGap(116).addComponent(this.txt_buscador, -2, 403, -2))
                  )
                  .addContainerGap()
            )
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPane, -1, 104, 32767)
                  .addContainerGap()
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "TipoDocumentos", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = TipoDocumentosForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  TipoDocumentosForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            TipoDocumentosForm frame = new TipoDocumentosForm();

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
