package a2MenuPrincipal;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import utilidades.CheckPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextField;
import utilidades.LimiteTextFieldConSQL;
import utilidades.LimiteTextFieldConSQLClientes;
import utilidades.PanelPadre;
import utilidades.TablaScrollPane;
import utilidades.VerificarCamposVacios;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTablaParametros;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;

public class ParametrosForm extends JinternalPadre {
   private List<String> seleccionados = new ArrayList<>();
   private BuscadorTablaParametros tabla;
   private ModeloTablaDefecto modeloTabla;
   private static final long serialVersionUID = 1L;
   private LabelPrincipal lblNroVersionTexto;
   private LabelPrincipal lblMonedaSistemaTexto;
   private LabelPrincipal lblMonedaNacionalTexto;
   private LabelPrincipal lblRUCCliente;
   private LabelPrincipal lblClienteNombre;
   private LimiteTextField txt_nombre_empresa;
   private LimiteTextField txt_ruc_empresa;
   private LimiteTextField txt_telefono_empresa;
   private LimiteTextArea txt_direccion_empresa;
   private LimiteTextFieldConSQL txt_cod_moneda_sistema;
   private LimiteTextFieldConSQL txt_cod_moneda_nacional;
   private CheckPadre chckbxCtasCobrar;
   private CheckPadre chckbxCtasPagar;
   private CheckPadre chckbxVentas;
   private CheckPadre chckbxStock;
   private CheckPadre chckbxTesoreria;
   private CheckPadre chckbxTodos;
   private CheckPadre chckbxCompras;
   private LimiteTextField txt_buscador;
   private LimiteTextFieldConSQLClientes txt_cod_cliente;

   private void actualizarParametros() {
      DialogoMessagebox d = new DialogoMessagebox("Desea actualizar Parametros ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()
         && VerificarCamposVacios.verificar(this.txt_cod_moneda_sistema, this)
         && VerificarCamposVacios.verificar(this.txt_cod_moneda_nacional, this)) {
         ParametrosDetalleE.actualizarParametros(this.tabla, this);
         ParametrosE entidadParametro = this.cargarEntidades();
         int codigo = ParametrosE.actualizarParametros(entidadParametro, this);
         if (codigo != 0) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Parametros actualizado correctamente", "correcto");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
         }
      }
   }

   public void buscarFocoBD() {
      ParametrosE parametro = ParametrosE.buscarParametrosGral(this);
      if (parametro == null) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No se pudo recuperar datos de los Parametros", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         this.cargarParametros(parametro);
      }
   }

   private ParametrosE cargarEntidades() {
      return new ParametrosE(
         this.txt_ruc_empresa.getText(),
         this.txt_nombre_empresa.getText(),
         this.txt_direccion_empresa.getText(),
         this.txt_telefono_empresa.getText(),
         Integer.parseInt(this.txt_cod_moneda_sistema.getText()),
         Integer.parseInt(this.txt_cod_moneda_nacional.getText()),
         Integer.parseInt(this.lblNroVersionTexto.getText()),
         Integer.parseInt(this.txt_cod_cliente.getText())
      );
   }

   private void cargarParametros(ParametrosE entidad) {
      this.txt_cod_moneda_nacional.setValue(entidad.getCod_moneda_nacional());
      this.lblMonedaNacionalTexto.setText(entidad.getNombreMonedaNacional());
      this.txt_cod_moneda_sistema.setValue(entidad.getCod_moneda_sistema());
      this.lblMonedaSistemaTexto.setText(entidad.getNombreMonedaSistema());
      this.txt_direccion_empresa.setText(entidad.getDireccion());
      this.txt_nombre_empresa.setValue(entidad.getNombre_empresa());
      this.txt_ruc_empresa.setValue(entidad.getRuc());
      this.txt_telefono_empresa.setValue(entidad.getTelefono());
      this.lblNroVersionTexto.setText(String.valueOf(entidad.getNro_version()));
      this.txt_cod_cliente.setValue(entidad.getCod_cliente());
      this.lblClienteNombre.setText(entidad.getNombre_cliente());
      this.lblRUCCliente.setText(entidad.getRuc_cliente());
   }

   private void agregarListener(JCheckBox checkBox) {
      checkBox.addItemListener(e -> {
         String texto = checkBox.getText();
         if (checkBox.isSelected()) {
            if (!this.seleccionados.contains(texto)) {
               this.seleccionados.add(texto);
            }
         } else {
            this.seleccionados.remove(texto);
         }
      });
   }

   public ParametrosForm() {
      this.setTitle("Parametros Generales del Sistema");
      this.setBounds(100, 100, 927, 582);
      PanelPadre panel_empresa = new PanelPadre("Parametros Empresa");
      PanelPadre panel_parametros_basicos = new PanelPadre("Parametros Basicos");
      PanelPadre panel_parametros_detalle = new PanelPadre("Parametros Detalle");
      PanelPadre panel_modulo = new PanelPadre("Modulo");
      PanelPadre panel_ventas = new PanelPadre("Ventas");
      LabelPrincipal lblNroVersion = new LabelPrincipal("Nro. Version", "label");
      LabelPrincipal lblMonedaSistema = new LabelPrincipal("Moneda Sistema", "label");
      LabelPrincipal lblMonedaNacional = new LabelPrincipal("Moneda Nacional", "label");
      LabelPrincipal lblNombreEmpresa = new LabelPrincipal("Nombre de la Empresa", "label");
      LabelPrincipal lblRuc = new LabelPrincipal("RUC EMPRESA", "label");
      LabelPrincipal lblDireccion = new LabelPrincipal("Direccion Empresa", "label");
      LabelPrincipal lblTelefono = new LabelPrincipal("Telefono Empresa", "label");
      LabelPrincipal lblCliente = new LabelPrincipal("Cliente", "label");
      this.lblClienteNombre = new LabelPrincipal(0);
      this.lblRUCCliente = new LabelPrincipal(0);
      this.lblNroVersionTexto = new LabelPrincipal(0);
      this.lblMonedaSistemaTexto = new LabelPrincipal(0);
      this.lblMonedaNacionalTexto = new LabelPrincipal(0);
      this.txt_cod_moneda_sistema = new LimiteTextFieldConSQL(999999, this.lblMonedaSistemaTexto, "Monedas", this);
      this.txt_cod_moneda_nacional = new LimiteTextFieldConSQL(999999, this.lblMonedaNacionalTexto, "Monedas", this);
      this.txt_cod_cliente = new LimiteTextFieldConSQLClientes(999999, this.lblClienteNombre, this.lblRUCCliente, this);
      this.txt_nombre_empresa = new LimiteTextField(25);
      this.txt_ruc_empresa = new LimiteTextField(25);
      this.txt_telefono_empresa = new LimiteTextField(25);
      this.txt_direccion_empresa = new LimiteTextArea(70);
      this.txt_buscador = new LimiteTextField(25);
      this.txt_buscador.setName("txt_buscador");
      this.chckbxTodos = new CheckPadre("Todos");
      this.chckbxStock = new CheckPadre("Stock");
      this.chckbxCompras = new CheckPadre("Compras");
      this.chckbxVentas = new CheckPadre("Ventas");
      this.chckbxTesoreria = new CheckPadre("Tesoreria");
      this.chckbxCtasCobrar = new CheckPadre("Ctas. Cobrar");
      this.chckbxCtasPagar = new CheckPadre("Ctas. Pagar");
      this.agregarListener(this.chckbxTodos);
      this.agregarListener(this.chckbxStock);
      this.agregarListener(this.chckbxCompras);
      this.agregarListener(this.chckbxVentas);
      this.agregarListener(this.chckbxTesoreria);
      this.agregarListener(this.chckbxCtasCobrar);
      this.agregarListener(this.chckbxCtasPagar);
      String[] cabecera = new String[]{"ID", "Nombre", "Modulo", "Estado"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTablaParametros(this.modeloTabla, "ParametrosDetalle", this);
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla);
      this.txt_buscador
         .addKeyListener(
            new KeyAdapter() {
               @Override
               public void keyPressed(KeyEvent e) {
                  if (ParametrosForm.this.modeloTabla.getRowCount() >= 0 && e.getKeyCode() == 10) {
                     if (ParametrosForm.this.chckbxTodos.isSelected()) {
                        if (ParametrosForm.this.txt_buscador.getText().equals("")) {
                           ParametrosDetalleE.cargarTabla(ParametrosForm.this.modeloTabla, "", ParametrosForm.this);
                        } else {
                           ParametrosDetalleE.cargarTabla(
                              ParametrosForm.this.modeloTabla,
                              " where  nombre_parametro like '%" + ParametrosForm.this.txt_buscador.getText().trim() + "%'",
                              ParametrosForm.this
                           );
                        }
                     } else if (!ParametrosForm.this.seleccionados.isEmpty()) {
                        String seleccionSql = "'" + String.join("', '", ParametrosForm.this.seleccionados) + "'";
                        ParametrosDetalleE.cargarTabla(
                           ParametrosForm.this.modeloTabla,
                           " where modulo in (" + seleccionSql + ") and nombre_parametro like '%" + ParametrosForm.this.txt_buscador.getText().trim() + "%'",
                           ParametrosForm.this
                        );
                     } else {
                        ParametrosDetalleE.cargarTabla(
                           ParametrosForm.this.modeloTabla,
                           " where nombre_parametro like '%" + ParametrosForm.this.txt_buscador.getText().trim() + "%'",
                           ParametrosForm.this
                        );
                     }
                  }
               }
            }
         );
      GroupLayout gl_panel_modulo = new GroupLayout(panel_modulo);
      gl_panel_modulo.setHorizontalGroup(
         gl_panel_modulo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_modulo.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_modulo.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.chckbxCtasPagar, -2, -1, -2)
                        .addComponent(this.chckbxCtasCobrar, -2, -1, -2)
                  )
                  .addGap(10)
                  .addGroup(
                     gl_panel_modulo.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_modulo.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                              .addComponent(this.chckbxTesoreria, -2, 93, -2)
                        )
                        .addGroup(
                           gl_panel_modulo.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(this.chckbxStock, -2, 93, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(
                     gl_panel_modulo.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_modulo.createSequentialGroup()
                              .addComponent(this.chckbxCompras, -2, 93, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.chckbxTodos, -2, 93, -2)
                        )
                        .addComponent(this.chckbxVentas, -2, 93, -2)
                  )
                  .addContainerGap()
            )
      );
      gl_panel_modulo.setVerticalGroup(
         gl_panel_modulo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_modulo.createSequentialGroup()
                  .addGroup(
                     gl_panel_modulo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.chckbxStock, -2, -1, -2)
                        .addComponent(this.chckbxCompras, -2, -1, -2)
                        .addComponent(this.chckbxCtasCobrar, -2, -1, -2)
                        .addComponent(this.chckbxTodos, -2, -1, -2)
                  )
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(
                     gl_panel_modulo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.chckbxTesoreria, -2, -1, -2)
                        .addComponent(this.chckbxCtasPagar, -2, -1, -2)
                        .addComponent(this.chckbxVentas, -2, -1, -2)
                  )
                  .addContainerGap(28, 32767)
            )
      );
      panel_modulo.setLayout(gl_panel_modulo);
      GroupLayout gl_panel_parametros_detalle = new GroupLayout(panel_parametros_detalle);
      gl_panel_parametros_detalle.setHorizontalGroup(
         gl_panel_parametros_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_parametros_detalle.createSequentialGroup().addContainerGap(-1, 32767).addComponent(panel_modulo, -2, 439, -2).addContainerGap())
            .addGroup(gl_panel_parametros_detalle.createSequentialGroup().addGap(18).addComponent(this.txt_buscador, -2, 255, -2).addContainerGap(186, 32767))
            .addGroup(gl_panel_parametros_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 449, 32767))
      );
      gl_panel_parametros_detalle.setVerticalGroup(
         gl_panel_parametros_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_parametros_detalle.createSequentialGroup()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addGap(7)
                  .addComponent(panel_modulo, -2, 87, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(scrollPane, -2, 353, -2)
                  .addContainerGap(13, 32767)
            )
      );
      panel_parametros_detalle.setLayout(gl_panel_parametros_detalle);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_empresa, -2, -1, -2)
                        .addComponent(panel_parametros_basicos, -2, 409, -2)
                        .addComponent(panel_ventas, -2, 409, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_parametros_detalle, -2, 476, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_parametros_detalle, -1, -1, 32767)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_empresa, -2, 186, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_parametros_basicos, -2, 107, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_ventas, -1, 196, 32767)
                        )
                  )
                  .addGap(41)
            )
      );
      GroupLayout gl_panel_ventas = new GroupLayout(panel_ventas);
      gl_panel_ventas.setHorizontalGroup(
         gl_panel_ventas.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_ventas.createSequentialGroup()
                  .addComponent(lblCliente, -2, 101, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(
                     gl_panel_ventas.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(this.lblRUCCliente, -1, -1, 32767)
                        .addGroup(
                           gl_panel_ventas.createSequentialGroup()
                              .addComponent(this.txt_cod_cliente, -2, 55, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblClienteNombre, -1, 226, 32767)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_panel_ventas.setVerticalGroup(
         gl_panel_ventas.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_ventas.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_ventas.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_ventas.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblCliente, -2, 25, -2)
                              .addComponent(this.txt_cod_cliente, -2, 25, -2)
                        )
                        .addComponent(this.lblClienteNombre, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblRUCCliente, -2, 25, -2)
                  .addContainerGap(127, 32767)
            )
      );
      panel_ventas.setLayout(gl_panel_ventas);
      GroupLayout gl_panel_parametros_basicos = new GroupLayout(panel_parametros_basicos);
      gl_panel_parametros_basicos.setHorizontalGroup(
         gl_panel_parametros_basicos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_parametros_basicos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_parametros_basicos.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNroVersion, -2, 101, -2)
                        .addComponent(lblMonedaSistema, -2, 89, -2)
                        .addComponent(lblMonedaNacional, -2, -1, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_parametros_basicos.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_parametros_basicos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_parametros_basicos.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(this.txt_cod_moneda_nacional, -2, 53, -2)
                                    .addComponent(this.txt_cod_moneda_sistema, -2, 55, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_parametros_basicos.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.lblMonedaNacionalTexto, -1, 200, 32767)
                                    .addComponent(this.lblMonedaSistemaTexto, -1, 200, 32767)
                              )
                        )
                        .addComponent(this.lblNroVersionTexto, -2, 74, -2)
                  )
                  .addContainerGap()
            )
      );
      gl_panel_parametros_basicos.setVerticalGroup(
         gl_panel_parametros_basicos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_parametros_basicos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_parametros_basicos.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNroVersion, -2, 25, -2)
                        .addComponent(this.lblNroVersionTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_parametros_basicos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_parametros_basicos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_moneda_sistema, -2, 25, -2)
                              .addComponent(lblMonedaSistema, -2, 25, -2)
                        )
                        .addComponent(this.lblMonedaSistemaTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_parametros_basicos.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_parametros_basicos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_moneda_nacional, -2, 25, -2)
                              .addComponent(lblMonedaNacional, -2, 25, -2)
                        )
                        .addComponent(this.lblMonedaNacionalTexto, -2, 25, -2)
                  )
                  .addContainerGap(122, 32767)
            )
      );
      panel_parametros_basicos.setLayout(gl_panel_parametros_basicos);
      GroupLayout gl_panel_empresa = new GroupLayout(panel_empresa);
      gl_panel_empresa.setHorizontalGroup(
         gl_panel_empresa.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_empresa.createSequentialGroup()
                  .addGroup(
                     gl_panel_empresa.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNombreEmpresa, -2, -1, -2)
                        .addComponent(lblRuc, -2, 123, -2)
                        .addComponent(lblTelefono, -2, 123, -2)
                        .addComponent(lblDireccion, -2, -1, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_empresa.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.txt_telefono_empresa, -2, 247, -2)
                        .addComponent(this.txt_ruc_empresa, -2, 168, -2)
                        .addComponent(this.txt_nombre_empresa, -2, 255, -2)
                        .addComponent(this.txt_direccion_empresa, -2, 269, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_panel_empresa.setVerticalGroup(
         gl_panel_empresa.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_empresa.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_empresa.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNombreEmpresa, -2, 25, -2)
                        .addComponent(this.txt_nombre_empresa, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_empresa.createParallelGroup(Alignment.BASELINE).addComponent(this.txt_ruc_empresa, -2, 25, -2).addComponent(lblRuc, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_empresa.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.txt_telefono_empresa, -2, 25, -2)
                        .addComponent(lblTelefono, -2, 25, -2)
                  )
                  .addGroup(
                     gl_panel_empresa.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_empresa.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_direccion_empresa, -2, 70, -2)
                        )
                        .addGroup(gl_panel_empresa.createSequentialGroup().addGap(36).addComponent(lblDireccion, -2, 25, -2))
                  )
                  .addContainerGap(35, 32767)
            )
      );
      panel_empresa.setLayout(gl_panel_empresa);
      this.getContentPane().setLayout(groupLayout);
      this.buscarFocoBD();
   }

   @Override
   public void guardar() {
      this.actualizarParametros();
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ParametrosForm frame = new ParametrosForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
            }
         }
      });
   }
}
