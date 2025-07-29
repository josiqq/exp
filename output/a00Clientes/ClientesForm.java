package a00Clientes;

import java.awt.EventQueue;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utilidades.CheckPadre;
import utilidades.Combo;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextField;
import utilidades.LimiteTextFieldConSQL;
import utilidades.PanelPadre;
import utilidades.RadioBoton;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadorTablaCargar;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class ClientesForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(70);
   private static final long serialVersionUID = 1L;
   private LimiteTextField txt_buscador;
   private LimiteTextField txt_telefono;
   private LimiteTextField txt_ruc;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private LabelPrincipal lblNombreCategoria;
   private LabelPrincipal lblNombrePais;
   private LabelPrincipal lblNombreCiudad;
   private LimiteTextFieldConSQL txt_cod_categoria;
   private LimiteTextFieldConSQL txt_cod_ciudad;
   private LimiteTextFieldConSQL txt_cod_pais;
   private Combo combo_tipo_fiscal;
   private CheckPadre chckActivo;
   private LimiteTextArea txt_nombre_cliente;
   private LimiteTextArea txt_direccion;
   private LimiteTextField txt_email;
   private ModeloTablaDefecto modeloTabla;
   private BuscadorTabla tabla;
   public int SW;
   private RadioBoton rdbtnListaPrecio;
   private RadioBoton rdbtnCondicionVenta;
   private LimiteTextFieldConSQL txt_cod_lista_precio;
   private LimiteTextFieldConSQL txt_cod_condicion_venta;
   private LabelPrincipal lblNombreListaPrecioTexto;
   private LabelPrincipal lblNombreCondicionTexto;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         ClientesE b = ClientesE.buscarCliente(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarClientes(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         ClientesE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = ClientesE.insertarClientes(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = ClientesE.actualizarClientes(entidad, this);
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
            ClientesE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = ClientesE.eliminarClientes(ent, this);
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

   public void cargarClientes(ClientesE e) {
      this.txt_codigo.setValue(e.getCod_cliente());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_cliente()));
      this.txt_nombre_cliente.setText(e.getNombre_cliente());
      this.txt_cod_categoria.setValue(e.getCod_categoria());
      this.lblNombreCategoria.setText(e.getNombre_categoria());
      this.txt_cod_ciudad.setValue(e.getCod_ciudad());
      this.lblNombreCiudad.setText(e.getNombre_ciudad());
      this.txt_cod_pais.setValue(e.getCod_pais());
      this.lblNombrePais.setText(e.getNombre_pais());
      this.txt_email.setValue(e.getEmail());
      this.txt_ruc.setValue(e.getRuc());
      this.txt_telefono.setValue(e.getTelefono());
      this.combo_tipo_fiscal.setSelectedIndex(e.getTipo_fiscal());
      this.txt_cod_condicion_venta.setValue(e.getCod_condicion_vta());
      this.lblNombreCondicionTexto.setText(e.getNombre_condicion_vta());
      this.txt_cod_lista_precio.setValue(e.getCod_lista());
      this.lblNombreListaPrecioTexto.setText(e.getNombre_lista());
      if (e.getTipo_precio() == 0) {
         this.rdbtnListaPrecio.setSelected(true);
         this.rdbtnCondicionVenta.setSelected(false);
      } else if (e.getTipo_precio() == 1) {
         this.rdbtnListaPrecio.setSelected(false);
         this.rdbtnCondicionVenta.setSelected(true);
      }

      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_nombre_cliente.requestFocusInWindow();
   }

   public ClientesE CargarEntidades() {
      int tipo_precio = 0;
      if (this.rdbtnListaPrecio.isSelected()) {
         tipo_precio = 0;
      } else if (this.rdbtnCondicionVenta.isSelected()) {
         tipo_precio = 1;
      }

      int tipo_fiscal = this.combo_tipo_fiscal.getSelectedIndex();
      int estado = 0;
      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new ClientesE(
            Integer.parseInt(this.txt_codigo.getText()),
            this.txt_nombre_cliente.getText(),
            this.txt_ruc.getText(),
            estado,
            tipo_fiscal,
            this.txt_telefono.getText(),
            this.txt_direccion.getText(),
            this.txt_email.getText(),
            Integer.parseInt(this.txt_cod_categoria.getText()),
            Integer.parseInt(this.txt_cod_pais.getText()),
            Integer.parseInt(this.txt_cod_ciudad.getText()),
            Integer.parseInt(this.txt_cod_condicion_venta.getText()),
            Integer.parseInt(this.txt_cod_lista_precio.getText()),
            tipo_precio
         );
      } catch (NumberFormatException var5) {
         LogErrores.errores(var5, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var6) {
         LogErrores.errores(var6, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   public void buscarTabla() {
      int filaSeleccionada = this.tabla.getSelectedRow();
      if (filaSeleccionada != -1) {
         this.txt_cod_lista_precio.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodListaPrecio").getModelIndex()).toString());
         this.lblNombreListaPrecioTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreListaPrecio").getModelIndex()).toString());
         this.txt_cod_condicion_venta.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodCondicionVta").getModelIndex()).toString());
         this.lblNombreCondicionTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreCondicionVta").getModelIndex()).toString());
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("TipoPrecio").getModelIndex()).toString().equals("0")) {
            this.rdbtnListaPrecio.setSelected(true);
            this.rdbtnCondicionVenta.setSelected(false);
         } else if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("TipoPrecio").getModelIndex()).toString().equals("0")) {
            this.rdbtnListaPrecio.setSelected(false);
            this.rdbtnCondicionVenta.setSelected(true);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("TF").getModelIndex()).toString().equals("0")) {
            this.combo_tipo_fiscal.setSelectedIndex(0);
         } else if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("TF").getModelIndex()).toString().equals("0")) {
            this.combo_tipo_fiscal.setSelectedIndex(1);
         } else if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("TF").getModelIndex()).toString().equals("0")) {
            this.combo_tipo_fiscal.setSelectedIndex(2);
         }

         this.txt_codigo.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString());
         this.SW = Integer.parseInt(String.valueOf(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString()));
         this.txt_nombre_cliente.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nombre").getModelIndex()).toString());
         this.txt_ruc.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("RUC").getModelIndex()).toString());
         this.txt_telefono.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Telefono").getModelIndex()).toString());
         this.txt_direccion.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Direccion").getModelIndex()).toString());
         this.txt_email.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Email").getModelIndex()).toString());
         this.txt_cod_categoria.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodCategoria").getModelIndex()).toString());
         this.lblNombreCategoria.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreCategoria").getModelIndex()).toString());
         this.txt_cod_pais.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodPais").getModelIndex()).toString());
         this.lblNombrePais.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombrePais").getModelIndex()).toString());
         this.txt_cod_ciudad.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodCiudad").getModelIndex()).toString());
         this.lblNombreCiudad.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreCiudad").getModelIndex()).toString());
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
      this.combo_tipo_fiscal.setSelectedIndex(0);
      TablaBuscador.eliminarFilasConWhile(this.modeloTabla);
      this.txt_buscador.setValue("");
      this.txt_codigo.setValue(0);
      this.txt_nombre_cliente.setText("");
      this.txt_cod_categoria.setValue(0);
      this.lblNombreCategoria.setText("");
      this.txt_cod_ciudad.setValue(0);
      this.lblNombreCiudad.setText("");
      this.txt_cod_pais.setValue(0);
      this.lblNombrePais.setText("");
      this.txt_direccion.setText("");
      this.txt_email.setValue("");
      this.txt_ruc.setValue("");
      this.txt_telefono.setValue("");
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre_cliente.requestFocusInWindow();
      this.rdbtnListaPrecio.setSelected(false);
      this.rdbtnCondicionVenta.setSelected(false);
      this.txt_cod_condicion_venta.setValue(0);
      this.lblNombreCondicionTexto.setText("");
      this.txt_cod_lista_precio.setValue(0);
      this.lblNombreListaPrecioTexto.setText("");
   }

   public ClientesForm() {
      this.setTitle("Registro de Clientes");
      this.setBounds(100, 100, 829, 575);
      PanelPadre panel_datos = new PanelPadre("Datos Personales");
      PanelPadre panel_condiciones_ventas = new PanelPadre("Condiciones Ventas");
      PanelPadre panel_buscador = new PanelPadre("");
      PanelPadre panel = new PanelPadre("Definiciones");
      PanelPadre panel_tipo_precio = new PanelPadre("Tipo de Precio");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblRuc = new LabelPrincipal("RUC", "label");
      LabelPrincipal lblTelefono = new LabelPrincipal("Telefono", "label");
      LabelPrincipal lblDireccion = new LabelPrincipal("Dir.", "label");
      LabelPrincipal lblEmail = new LabelPrincipal("Email", "label");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      LabelPrincipal lblCondicionVta = new LabelPrincipal("Condicion Venta", "label");
      LabelPrincipal lblListaPrecio = new LabelPrincipal("Lista de Precio", "label");
      LabelPrincipal lblTipoFiscal = new LabelPrincipal("Tipo Fiscal", "label");
      LabelPrincipal lblCiudad = new LabelPrincipal("Ciudad", "label");
      LabelPrincipal lblPais = new LabelPrincipal("Pais", "label");
      LabelPrincipal lblCategoria = new LabelPrincipal("Categoria", "label");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreListaPrecioTexto = new LabelPrincipal(0);
      this.lblNombreCondicionTexto = new LabelPrincipal(0);
      this.lblNombreCategoria = new LabelPrincipal(0);
      this.lblNombreCiudad = new LabelPrincipal(0);
      this.lblNombrePais = new LabelPrincipal(0);
      this.txt_nombre_cliente = new LimiteTextArea(70);
      this.txt_ruc = new LimiteTextField(25);
      this.txt_telefono = new LimiteTextField(25);
      this.txt_email = new LimiteTextField(70);
      this.txt_buscador = new LimiteTextField(70);
      this.txt_direccion = new LimiteTextArea(70);
      this.txt_cod_lista_precio = new LimiteTextFieldConSQL(999999, this.lblNombreListaPrecioTexto, "ListaPrecios", this);
      this.txt_cod_condicion_venta = new LimiteTextFieldConSQL(999999, this.lblNombreCondicionTexto, "CondicionVtas", this);
      this.txt_cod_categoria = new LimiteTextFieldConSQL(999999, this.lblNombreCategoria, "CategoriasCli", this);
      this.txt_cod_pais = new LimiteTextFieldConSQL(999999, this.lblNombrePais, "Paises", this);
      this.txt_cod_ciudad = new LimiteTextFieldConSQL(999999, this.lblNombreCiudad, "Ciudades", this);
      this.chckActivo = new CheckPadre("Activo");
      this.combo_tipo_fiscal = new Combo(1);
      String[] cabecera = new String[]{
         "Codigo",
         "Nombre",
         "RUC",
         "Estado",
         "TF",
         "Telefono",
         "Direccion",
         "Email",
         "CodCategoria",
         "CodPais",
         "CodCiudad",
         "NombreCategoria",
         "NombrePais",
         "NombreCiudad",
         "CodListaPrecio",
         "NombreListaPrecio",
         "CodCondicionVta",
         "NombreCondicionVta",
         "TipoPrecio"
      };
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Clientes", this);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      this.rdbtnListaPrecio = new RadioBoton("Lista de Precio");
      this.rdbtnCondicionVenta = new RadioBoton("Condicion de Venta");
      ButtonGroup grupo = new ButtonGroup();
      grupo.add(this.rdbtnListaPrecio);
      grupo.add(this.rdbtnCondicionVenta);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, 120, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                        )
                        .addComponent(panel_condiciones_ventas, 0, 0, 32767)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_datos, -2, 458, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel, -2, 346, -2)
                        )
                        .addComponent(panel_buscador, 0, 0, 32767)
                  )
                  .addGap(16)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel_datos, -2, 231, -2).addComponent(panel, -2, 231, -2))
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(panel_condiciones_ventas, -2, 47, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -2, 214, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                  )
                  .addGap(127)
            )
      );
      GroupLayout gl_panel_tipo_precio = new GroupLayout(panel_tipo_precio);
      gl_panel_tipo_precio.setHorizontalGroup(
         gl_panel_tipo_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo_precio.createSequentialGroup()
                  .addComponent(this.rdbtnListaPrecio, -2, 125, -2)
                  .addGap(18)
                  .addComponent(this.rdbtnCondicionVenta, -2, 153, -2)
                  .addContainerGap(22, 32767)
            )
      );
      gl_panel_tipo_precio.setVerticalGroup(
         gl_panel_tipo_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo_precio.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_tipo_precio.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.rdbtnListaPrecio, -2, 18, -2)
                        .addComponent(this.rdbtnCondicionVenta, -2, 18, -2)
                  )
                  .addContainerGap(19, 32767)
            )
      );
      panel_tipo_precio.setLayout(gl_panel_tipo_precio);
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel_tipo_precio, Alignment.LEADING, -1, -1, 32767)
                        .addGroup(
                           Alignment.LEADING,
                           gl_panel.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 gl_panel.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblTipoFiscal, -1, 70, 32767)
                                    .addComponent(lblCategoria, Alignment.TRAILING, -2, 70, -2)
                                    .addComponent(lblPais, -2, 70, -2)
                                    .addComponent(lblCiudad, -2, 70, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.combo_tipo_fiscal, -2, 120, -2)
                                    .addGroup(
                                       gl_panel.createSequentialGroup()
                                          .addComponent(this.txt_cod_categoria, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreCategoria, -2, 200, -2)
                                    )
                                    .addGroup(
                                       gl_panel.createSequentialGroup()
                                          .addGroup(
                                             gl_panel.createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(this.txt_cod_ciudad, Alignment.LEADING, -2, 41, -2)
                                                .addComponent(this.txt_cod_pais, Alignment.LEADING, -2, 41, -2)
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addGroup(
                                             gl_panel.createParallelGroup(Alignment.LEADING)
                                                .addComponent(this.lblNombreCiudad, -2, 200, -2)
                                                .addComponent(this.lblNombrePais, -2, 200, -2)
                                          )
                                    )
                              )
                        )
                  )
                  .addContainerGap(34, 32767)
            )
      );
      gl_panel.setVerticalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblTipoFiscal, -2, 25, -2).addComponent(this.combo_tipo_fiscal, -2, 25, -2)
                  )
                  .addGap(8)
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.lblNombreCategoria, -2, 25, -2)
                        .addGroup(
                           gl_panel.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_categoria, -2, 25, -2)
                              .addComponent(lblCategoria, -2, 25, -2)
                        )
                  )
                  .addGap(5)
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel.createParallelGroup(Alignment.LEADING)
                              .addComponent(this.txt_cod_pais, -2, 25, -2)
                              .addComponent(this.lblNombrePais, -2, 25, -2)
                        )
                        .addComponent(lblPais, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.lblNombreCiudad, -2, 25, -2)
                        .addGroup(
                           gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(this.txt_cod_ciudad, -2, 25, -2).addComponent(lblCiudad, -2, 25, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_tipo_precio, -2, 35, -2)
                  .addContainerGap(57, 32767)
            )
      );
      panel.setLayout(gl_panel);
      GroupLayout gl_panel_condiciones_ventas = new GroupLayout(panel_condiciones_ventas);
      gl_panel_condiciones_ventas.setHorizontalGroup(
         gl_panel_condiciones_ventas.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_condiciones_ventas.createSequentialGroup()
                  .addComponent(lblListaPrecio, -2, 88, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cod_lista_precio, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreListaPrecioTexto, -2, 200, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblCondicionVta, -2, 92, -2)
                  .addGap(10)
                  .addComponent(this.txt_cod_condicion_venta, -2, 41, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreCondicionTexto, -2, 200, -2)
                  .addContainerGap(227, 32767)
            )
      );
      gl_panel_condiciones_ventas.setVerticalGroup(
         gl_panel_condiciones_ventas.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_condiciones_ventas.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_condiciones_ventas.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.lblNombreCondicionTexto, -2, 25, -2)
                        .addGroup(
                           gl_panel_condiciones_ventas.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_condicion_venta, -2, 25, -2)
                              .addComponent(lblCondicionVta)
                        )
                        .addComponent(this.lblNombreListaPrecioTexto, -2, 25, -2)
                        .addGroup(
                           gl_panel_condiciones_ventas.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_lista_precio, -2, 25, -2)
                              .addComponent(lblListaPrecio)
                        )
                  )
                  .addContainerGap(45, 32767)
            )
      );
      panel_condiciones_ventas.setLayout(gl_panel_condiciones_ventas);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                          .addComponent(lblNombre, -2, 45, -2)
                                          .addComponent(lblCodigo, -2, 45, -2)
                                    )
                                    .addComponent(lblDireccion, -2, 45, -2)
                                    .addComponent(lblRuc, -2, 45, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 54, -2)
                                          .addGap(18)
                                          .addComponent(this.chckActivo, -2, 93, -2)
                                    )
                                    .addGroup(
                                       Alignment.TRAILING,
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_ruc, -2, 166, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblTelefono, -2, 61, -2)
                                          .addGap(10)
                                          .addComponent(this.txt_telefono, -1, -1, 32767)
                                    )
                                    .addComponent(this.txt_nombre_cliente, -1, 390, 32767)
                                    .addComponent(this.txt_direccion, 0, 0, 32767)
                              )
                        )
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(lblEmail, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_email, -1, -1, 32767)
                        )
                  )
                  .addGap(584)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblCodigo, -2, -1, -2)
                        .addComponent(this.txt_codigo, -2, 25, -2)
                        .addComponent(this.chckActivo, -2, 18, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNombre, -2, 18, -2)
                        .addComponent(this.txt_nombre_cliente, -2, 56, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.txt_telefono, -2, 25, -2)
                        .addComponent(lblTelefono, -2, 25, -2)
                        .addComponent(this.txt_ruc, -2, 25, -2)
                        .addComponent(lblRuc, -2, 25, -2)
                  )
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_datos.createSequentialGroup().addGap(26).addComponent(lblDireccion, -2, 25, -2))
                        .addGroup(gl_panel_datos.createSequentialGroup().addGap(6).addComponent(this.txt_direccion, -2, 56, -2))
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblEmail, -2, 25, -2).addComponent(this.txt_email, -2, 25, -2))
                  .addContainerGap(-1, 32767)
            )
      );
      panel_datos.setLayout(gl_panel_datos);
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_buscador.createSequentialGroup().addGap(101).addComponent(this.txt_buscador, -2, 371, -2))
                        .addGroup(gl_panel_buscador.createSequentialGroup().addContainerGap().addComponent(scrollPane, -2, 792, -2))
                  )
                  .addContainerGap(35, 32767)
            )
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(scrollPane, -2, 159, -2)
                  .addContainerGap(17, 32767)
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Clientes", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = ClientesForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  ClientesForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ClientesForm frame = new ClientesForm();

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
