package a3Permisos;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.BotonPadre;
import utilidades.CheckPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidades.LimiteTextFieldConSQLDialog;
import utilidades.PanelPadre;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JDialogPrincipal;

public class UsuariosForm extends JDialogPrincipal implements ActionListener {
   private static final long serialVersionUID = 1L;
   private LimiteTextField txt_usuario;
   private LimiteTextField txt_nombre_usuario;
   private LimiteTextFieldConSQLDialog txt_vendedor;
   private LimiteTextFieldConSQLDialog txt_cajero;
   private LimiteTextFieldConSQLDialog txt_comprador;
   private LabelPrincipal lblNombreVendedorTexto;
   private LabelPrincipal lblNombreCajeroTexto;
   private LabelPrincipal lblNombreCompradorTexto;
   private BotonPadre btnGuardar;
   private BotonPadre btnSalir;
   private String grupo;
   private String nombre_usuario;
   private CheckPadre chckActivo;
   private int SW;
   private BotonPadre btnResetearPassword;
   private LimiteTextFieldConSQLDialog txt_cod_sucursal;
   private BotonPadre btnEnvios;
   private BotonPadre btnDepositos;
   private BotonPadre btnCuentas;
   private BotonPadre btnListaPrecios;
   private LabelPrincipal lblNombreSucursalTexto;
   private BotonPadre btnCondicionVenta;
   private BotonPadre btnTipoDocumentos;

   public UsuariosForm(String grupo, String nombre_usuario, int sw) {
      this.setModal(true);
      this.setTitle("Registro de Usuarios");
      this.SW = sw;
      this.grupo = grupo;
      this.nombre_usuario = nombre_usuario;
      System.out.println("NOMBRE USUARIO: " + nombre_usuario);
      this.setBounds(100, 100, 610, 360);
      this.getContentPane().setLayout(new BorderLayout());
      PanelPadre contentPanel = new PanelPadre("");
      contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(contentPanel, "North");
      PanelPadre panel = new PanelPadre("Datos Basicos");
      PanelPadre panel_sistema = new PanelPadre("");
      PanelPadre panel_botones = new PanelPadre("");
      PanelPadre panel_datos_personales = new PanelPadre("");
      PanelPadre panel_boton_privilegio = new PanelPadre("");
      LabelPrincipal lblVendedor = new LabelPrincipal("Vendedor", "label");
      LabelPrincipal lblNombreCajero = new LabelPrincipal("Cajero", "label");
      LabelPrincipal lblComprador = new LabelPrincipal("Comprador", "label");
      LabelPrincipal lblUsuario = new LabelPrincipal("Usuario", "label");
      LabelPrincipal lblNombreUsuario = new LabelPrincipal("Nombre Usuario", "label");
      LabelPrincipal lblSucursal = new LabelPrincipal("Sucursal", "label");
      this.lblNombreSucursalTexto = new LabelPrincipal(0);
      this.lblNombreVendedorTexto = new LabelPrincipal(0);
      this.lblNombreCajeroTexto = new LabelPrincipal(0);
      this.lblNombreCompradorTexto = new LabelPrincipal(0);
      this.txt_vendedor = new LimiteTextFieldConSQLDialog(999999, this.lblNombreVendedorTexto, "Vendedores", this);
      this.txt_cajero = new LimiteTextFieldConSQLDialog(999999, this.lblNombreCajeroTexto, "Cajeros", this);
      this.txt_comprador = new LimiteTextFieldConSQLDialog(999999, this.lblNombreCompradorTexto, "Compradores", this);
      this.txt_cod_sucursal = new LimiteTextFieldConSQLDialog(999999, this.lblNombreSucursalTexto, "Sucursales", this);
      this.txt_usuario = new LimiteTextField(25);
      this.txt_nombre_usuario = new LimiteTextField(25);
      this.btnResetearPassword = new BotonPadre("<html>Resetear<br>Password</html>", "reset.png");
      this.btnResetearPassword.addActionListener(this);
      this.btnGuardar = new BotonPadre("Guardar", "guardar.png");
      this.btnGuardar.addActionListener(this);
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      this.btnSalir.addActionListener(this);
      this.btnTipoDocumentos = new BotonPadre("<html>Tipo<br>Documentos</html>");
      this.btnTipoDocumentos.addActionListener(this);
      this.btnCondicionVenta = new BotonPadre("<html>Condicion<br>Venta</html>");
      this.btnCondicionVenta.addActionListener(this);
      this.chckActivo = new CheckPadre("Activo");
      this.btnDepositos = new BotonPadre("Depositos");
      this.btnDepositos.addActionListener(this);
      this.btnCuentas = new BotonPadre("Cuentas");
      this.btnCuentas.addActionListener(this);
      this.btnListaPrecios = new BotonPadre("<html>Lista<br>Precios</html>");
      this.btnListaPrecios.addActionListener(this);
      this.btnEnvios = new BotonPadre("<html>Envios<br>Depositos</html>");
      this.btnEnvios.addActionListener(this);
      GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
      gl_contentPanel.setHorizontalGroup(
         gl_contentPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_contentPanel.createSequentialGroup().addComponent(panel, -2, 589, -2).addContainerGap(94, 32767))
      );
      gl_contentPanel.setVerticalGroup(
         gl_contentPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_contentPanel.createSequentialGroup().addComponent(panel, -2, 326, -2).addContainerGap(483, 32767))
      );
      GroupLayout gl_panel_boton_privilegio = new GroupLayout(panel_boton_privilegio);
      gl_panel_boton_privilegio.setHorizontalGroup(
         gl_panel_boton_privilegio.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_boton_privilegio.createSequentialGroup()
                  .addGroup(
                     gl_panel_boton_privilegio.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_boton_privilegio.createParallelGroup(Alignment.TRAILING, false)
                              .addComponent(this.btnListaPrecios, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.btnCuentas, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.btnEnvios, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.btnDepositos, Alignment.LEADING, -1, -1, 32767)
                        )
                        .addComponent(this.btnTipoDocumentos, -2, 85, -2)
                        .addComponent(this.btnCondicionVenta, -2, 85, -2)
                  )
                  .addGap(33)
            )
      );
      gl_panel_boton_privilegio.setVerticalGroup(
         gl_panel_boton_privilegio.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_boton_privilegio.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.btnDepositos, -2, 35, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnEnvios, -2, 35, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnCuentas, -2, 35, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnListaPrecios, -2, 35, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnTipoDocumentos, -2, 35, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnCondicionVenta, -2, 35, -2)
                  .addContainerGap(13, 32767)
            )
      );
      panel_boton_privilegio.setLayout(gl_panel_boton_privilegio);
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.LEADING).addComponent(panel_sistema, 0, 0, 32767).addComponent(panel_datos_personales, -2, 448, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_boton_privilegio, -2, 121, -2)
                  .addGap(11)
            )
            .addGroup(gl_panel.createSequentialGroup().addGap(45).addComponent(panel_botones, -2, 341, -2).addContainerGap(200, 32767))
      );
      gl_panel.setVerticalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel_boton_privilegio, Alignment.LEADING, -1, -1, 32767)
                        .addGroup(
                           Alignment.LEADING,
                           gl_panel.createSequentialGroup()
                              .addComponent(panel_datos_personales, -2, 88, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_sistema, -2, 162, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_botones, -2, 48, -2)
                  .addContainerGap(13, 32767)
            )
      );
      GroupLayout gl_panel_datos_personales = new GroupLayout(panel_datos_personales);
      gl_panel_datos_personales.setHorizontalGroup(
         gl_panel_datos_personales.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_personales.createSequentialGroup()
                  .addGroup(
                     gl_panel_datos_personales.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNombreUsuario, -2, -1, -2)
                        .addComponent(lblUsuario, -2, 89, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos_personales.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.txt_nombre_usuario, -2, 338, -2)
                        .addGroup(
                           gl_panel_datos_personales.createSequentialGroup()
                              .addComponent(this.txt_usuario, -2, 208, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.chckActivo, -2, 72, -2)
                        )
                  )
                  .addContainerGap(140, 32767)
            )
      );
      gl_panel_datos_personales.setVerticalGroup(
         gl_panel_datos_personales.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos_personales.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos_personales.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblUsuario, -2, -1, -2)
                        .addComponent(this.txt_usuario, -2, 31, -2)
                        .addComponent(this.chckActivo, -2, -1, -2)
                  )
                  .addGap(7)
                  .addGroup(
                     gl_panel_datos_personales.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNombreUsuario, -2, -1, -2)
                        .addComponent(this.txt_nombre_usuario, -2, 27, -2)
                  )
                  .addContainerGap(41, 32767)
            )
      );
      panel_datos_personales.setLayout(gl_panel_datos_personales);
      GroupLayout gl_panel_botones = new GroupLayout(panel_botones);
      gl_panel_botones.setHorizontalGroup(
         gl_panel_botones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_botones.createSequentialGroup()
                  .addComponent(this.btnResetearPassword, -2, 111, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnGuardar, -2, 104, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnSalir, -2, 92, -2)
                  .addContainerGap(226, 32767)
            )
      );
      gl_panel_botones.setVerticalGroup(
         gl_panel_botones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_botones.createSequentialGroup()
                  .addGroup(
                     gl_panel_botones.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_botones.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.btnGuardar, -2, -1, -2)
                              .addComponent(this.btnSalir, -2, -1, -2)
                        )
                        .addComponent(this.btnResetearPassword, -2, 33, -2)
                  )
                  .addContainerGap(46, 32767)
            )
      );
      panel_botones.setLayout(gl_panel_botones);
      GroupLayout gl_panel_sistema = new GroupLayout(panel_sistema);
      gl_panel_sistema.setHorizontalGroup(
         gl_panel_sistema.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_sistema.createSequentialGroup()
                  .addGroup(
                     gl_panel_sistema.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_sistema.createSequentialGroup()
                              .addComponent(lblVendedor, -2, 62, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_vendedor, -2, 37, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreVendedorTexto, -2, 234, -2)
                        )
                        .addGroup(
                           gl_panel_sistema.createSequentialGroup()
                              .addGroup(
                                 gl_panel_sistema.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       Alignment.LEADING,
                                       gl_panel_sistema.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_sistema.createParallelGroup(Alignment.LEADING)
                                                .addComponent(lblNombreCajero, -2, 62, -2)
                                                .addComponent(lblComprador, -2, -1, -2)
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addGroup(
                                             gl_panel_sistema.createParallelGroup(Alignment.LEADING)
                                                .addComponent(this.txt_cajero, -2, 35, -2)
                                                .addComponent(this.txt_comprador, -2, 35, -2)
                                          )
                                    )
                                    .addGroup(
                                       Alignment.LEADING,
                                       gl_panel_sistema.createSequentialGroup()
                                          .addComponent(lblSucursal, -2, 62, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.txt_cod_sucursal, -2, 35, -2)
                                    )
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_sistema.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.lblNombreSucursalTexto, -2, 236, -2)
                                    .addGroup(
                                       gl_panel_sistema.createParallelGroup(Alignment.LEADING)
                                          .addComponent(this.lblNombreCompradorTexto, -1, 236, 32767)
                                          .addComponent(this.lblNombreCajeroTexto, -1, -1, 32767)
                                    )
                              )
                        )
                  )
                  .addGap(142)
            )
      );
      gl_panel_sistema.setVerticalGroup(
         gl_panel_sistema.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_sistema.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_sistema.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_sistema.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblVendedor, -2, -1, -2)
                              .addComponent(this.txt_vendedor, -2, 28, -2)
                        )
                        .addComponent(this.lblNombreVendedorTexto, -2, 28, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_sistema.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_sistema.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblNombreCajero, -2, -1, -2)
                              .addComponent(this.txt_cajero, -2, 28, -2)
                        )
                        .addComponent(this.lblNombreCajeroTexto, -2, 29, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_sistema.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_sistema.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_comprador, -2, 28, -2)
                              .addComponent(lblComprador, -2, -1, -2)
                        )
                        .addComponent(this.lblNombreCompradorTexto, -2, 31, -2)
                  )
                  .addGap(8)
                  .addGroup(
                     gl_panel_sistema.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_sistema.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblSucursal, -2, 16, -2)
                              .addComponent(this.txt_cod_sucursal, -2, 28, -2)
                        )
                        .addComponent(this.lblNombreSucursalTexto, -2, 31, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_sistema.setLayout(gl_panel_sistema);
      panel.setLayout(gl_panel);
      contentPanel.setLayout(gl_contentPanel);
      if (this.SW == 1) {
         this.txt_usuario.setEnabled(false);
         UsuariosE usuario = UsuariosE.buscarDatosUsuarios(nombre_usuario, null);
         this.cargarUsuarios(usuario);
      }
   }

   public String getGrupo() {
      return this.grupo;
   }

   public void setGrupo(String grupo) {
      this.grupo = grupo;
   }

   public void cargarUsuarios(UsuariosE e) {
      this.txt_vendedor.setValue(e.getCod_vendedor());
      this.lblNombreVendedorTexto.setText(e.getNombre_vendedor());
      this.txt_comprador.setValue(e.getCod_comprador());
      this.lblNombreCompradorTexto.setText(e.getNombre_comprador());
      this.txt_cajero.setValue(e.getCod_cajero());
      this.lblNombreCajeroTexto.setText(e.getNombre_cajero());
      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_usuario.setText(e.getUsuario());
      this.txt_nombre_usuario.setText(e.getNombre_usuario());
      this.txt_cod_sucursal.setValue(e.getCod_sucursal());
      this.lblNombreSucursalTexto.setText(e.getNombre_sucursal());
   }

   public void buscarFocoBD() {
      UsuariosE usuario = UsuariosE.buscarDatosUsuarios(this.nombre_usuario, null);
      if (usuario == null) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No se pudo recuperar datos del Usuario", "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      } else {
         this.cargarUsuarios(usuario);
      }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.btnCondicionVenta) {
         CondicionVentaUsuarioForm tipoDocumento = new CondicionVentaUsuarioForm(this.nombre_usuario);
         tipoDocumento.setLocationRelativeTo(this);
         tipoDocumento.setModal(true);
         tipoDocumento.setVisible(true);
      }

      if (e.getSource() == this.btnTipoDocumentos) {
         TipoDocumentosForm tipoDocumento = new TipoDocumentosForm(this.nombre_usuario);
         tipoDocumento.setLocationRelativeTo(this);
         tipoDocumento.setModal(true);
         tipoDocumento.setVisible(true);
      }

      if (e.getSource() == this.btnDepositos) {
         DepositosUsuariosForm deposito = new DepositosUsuariosForm(this.nombre_usuario);
         deposito.setLocationRelativeTo(this);
         deposito.setModal(true);
         deposito.setVisible(true);
      }

      if (e.getSource() == this.btnListaPrecios) {
         ListaPreciosUsuariosForm listaPrecio = new ListaPreciosUsuariosForm(this.nombre_usuario);
         listaPrecio.setLocationRelativeTo(this);
         listaPrecio.setModal(true);
         listaPrecio.setVisible(true);
      }

      if (e.getSource() == this.btnEnvios) {
         DepositosEnviosUsuariosForm depositoEnvio = new DepositosEnviosUsuariosForm(this.nombre_usuario);
         depositoEnvio.setLocationRelativeTo(this);
         depositoEnvio.setModal(true);
         depositoEnvio.setVisible(true);
      }

      if (this.btnCuentas == e.getSource()) {
         CuentasUsuariosForm cuenta = new CuentasUsuariosForm(this.nombre_usuario);
         cuenta.setLocationRelativeTo(this);
         cuenta.setModal(true);
         cuenta.setVisible(true);
      }

      if (e.getSource() == this.btnResetearPassword) {
         int codigo = UsuariosE.actualizarPassword(this.txt_usuario.getText(), "123", this);
         if (codigo != 0) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Password reseteado correctamente", "correcto");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
         }
      }

      if (this.btnSalir == e.getSource()) {
         this.dispose();
      }

      if (this.btnGuardar == e.getSource()) {
         if (this.SW == 0) {
            DialogoMessagebox d = new DialogoMessagebox("Desea crear nuevo Usuario ?");
            d.setLocationRelativeTo(this);
            d.setVisible(true);
            if (d.isResultadoEncontrado()) {
               UsuariosE entidadUsuario = this.CargarEntidades(this.grupo);
               int codigo = UsuariosE.insertarUsuario(entidadUsuario, null);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Usuario Creado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
               }
            }
         } else {
            DialogoMessagebox d = new DialogoMessagebox("Desea actualizar Usuario ?");
            d.setLocationRelativeTo(this);
            d.setVisible(true);
            if (d.isResultadoEncontrado()) {
               UsuariosE entidadUsuario = this.CargarEntidades(this.grupo);
               int codigo = UsuariosE.actualizarUsuario(entidadUsuario, null);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Usuario actualizado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
               }
            }
         }
      }
   }

   private UsuariosE CargarEntidades(String usuario) {
      int estado = 0;
      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new UsuariosE(
            this.txt_usuario.getText(),
            this.txt_nombre_usuario.getText(),
            this.grupo,
            estado,
            Integer.parseInt(this.txt_vendedor.getText()),
            Integer.parseInt(this.txt_cajero.getText()),
            Integer.parseInt(this.txt_comprador.getText()),
            Integer.parseInt(this.txt_cod_sucursal.getText())
         );
      } catch (Exception var4) {
         LogErrores.errores(var4, "Error al Iniciar Recuperar Entidad de Usuarios...", this);
         return null;
      }
   }

   public static void main(String[] args) {
      UsuariosForm dialog = new UsuariosForm("", "", 0);

      try {
         dialog.setDefaultCloseOperation(2);
         dialog.setVisible(true);
      } catch (Exception var3) {
         LogErrores.errores(var3, "Error al iniciar el Formulario", dialog);
         var3.printStackTrace();
      }
   }
}
