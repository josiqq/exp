package a00Cuentas;

import java.awt.EventQueue;
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
import utilidades.LimiteTextFieldConSQL;
import utilidades.PanelPadre;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadorTablaCargar;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class CuentasForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(10);
   private static final long serialVersionUID = 1L;
   private CheckPadre chckCuentaCorriente;
   private CheckPadre chckActivo;
   private CheckPadre chckCaja;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_buscador;
   private LimiteTextFieldConSQL txt_cod_moneda;
   private LimiteTextFieldConSQL txt_cod_banco;
   private LimiteTextFieldConSQL txt_cod_sucursal;
   private LabelPrincipal lblNombreMonedaTexto;
   private LabelPrincipal lblNombreBancoTexto;
   private LabelPrincipal lblNombreSucursalTexto;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private ModeloTablaDefecto modeloTabla;
   private BuscadorTabla tabla;
   public int SW;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         CuentasE b = CuentasE.buscarCuenta(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarCuentas(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         CuentasE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = CuentasE.insertarCuentas(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = CuentasE.actualizarCuentas(entidad, this);
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
            CuentasE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = CuentasE.eliminarCuentas(ent, this);
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

   public void cargarCuentas(CuentasE e) {
      this.txt_codigo.setValue(e.getCod_cuenta());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_cuenta()));
      this.txt_nombre.setValue(e.getNombre_cuenta());
      this.txt_cod_banco.setValue(e.getCod_banco());
      this.lblNombreBancoTexto.setText(e.getNombre_cuenta());
      this.txt_cod_sucursal.setValue(e.getCod_sucursal());
      this.lblNombreSucursalTexto.setText(e.getNombre_sucursal());
      this.txt_cod_moneda.setValue(e.getCod_moneda());
      this.lblNombreMonedaTexto.setText(e.getNombre_moneda());
      if (e.getTipo() == 0) {
         this.chckCaja.setSelected(true);
         this.chckCuentaCorriente.setSelected(false);
      } else {
         this.chckCaja.setSelected(false);
         this.chckCuentaCorriente.setSelected(true);
      }

      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public CuentasE CargarEntidades() {
      int tipo = 0;
      int estado = 0;
      if (this.chckCaja.isSelected()) {
         tipo = 0;
      } else if (this.chckCuentaCorriente.isSelected()) {
         tipo = 1;
      }

      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new CuentasE(
            Integer.parseInt(this.txt_codigo.getText()),
            this.txt_nombre.getText(),
            estado,
            Integer.parseInt(this.txt_cod_banco.getText()),
            tipo,
            Integer.parseInt(this.txt_cod_sucursal.getText()),
            Integer.parseInt(this.txt_cod_moneda.getText())
         );
      } catch (NumberFormatException var4) {
         LogErrores.errores(var4, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var5) {
         LogErrores.errores(var5, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   public void buscarTabla() {
      int filaSeleccionada = this.tabla.getSelectedRow();
      if (filaSeleccionada != -1) {
         this.txt_codigo.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString());
         this.SW = Integer.parseInt(String.valueOf(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString()));
         this.txt_nombre.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nombre").getModelIndex()).toString());
         this.txt_cod_banco.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodBanco").getModelIndex()).toString());
         this.lblNombreBancoTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreBanco").getModelIndex()).toString());
         this.txt_cod_sucursal.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodSucursal").getModelIndex()).toString());
         this.lblNombreSucursalTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreSucursal").getModelIndex()).toString());
         this.txt_cod_moneda.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodMoneda").getModelIndex()).toString());
         this.lblNombreMonedaTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreMoneda").getModelIndex()).toString());
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().equals("0")) {
            this.chckCaja.setSelected(true);
            this.chckCuentaCorriente.setSelected(false);
         } else {
            this.chckCaja.setSelected(false);
            this.chckCuentaCorriente.setSelected(true);
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
      TablaBuscador.eliminarFilasConWhile(this.modeloTabla);
      this.chckCaja.setSelected(false);
      this.chckCuentaCorriente.setSelected(false);
      this.txt_buscador.setValue("");
      this.txt_codigo.setValue(0);
      this.txt_nombre.setValue("");
      this.txt_cod_banco.setValue(0);
      this.txt_cod_sucursal.setValue(0);
      this.txt_cod_moneda.setValue(0);
      this.lblNombreBancoTexto.setText("");
      this.lblNombreSucursalTexto.setText("");
      this.lblNombreMonedaTexto.setText("");
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public CuentasForm() {
      this.setTitle("Registro de Cuentas");
      this.setBounds(100, 100, 497, 468);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("Buscador de Registro");
      PanelPadre panel_tipo = new PanelPadre("Tipo");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblBanco = new LabelPrincipal("Banco", "label");
      LabelPrincipal lblSucursal = new LabelPrincipal("Sucursal", "label");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombreMoneda = new LabelPrincipal("Moneda", "label");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreBancoTexto = new LabelPrincipal(0);
      this.lblNombreSucursalTexto = new LabelPrincipal(0);
      this.lblNombreMonedaTexto = new LabelPrincipal(0);
      this.lblNombreMonedaTexto.setText("");
      this.chckActivo = new CheckPadre("Activo");
      this.chckCaja = new CheckPadre("Caja");
      this.chckCuentaCorriente = new CheckPadre("Cuenta Corriente");
      this.txt_nombre = new LimiteTextField(25);
      this.txt_buscador = new LimiteTextField(25);
      this.txt_cod_banco = new LimiteTextFieldConSQL(999999, this.lblNombreBancoTexto, "Bancos", this);
      this.txt_cod_sucursal = new LimiteTextFieldConSQL(999999, this.lblNombreSucursalTexto, "Sucursales", this);
      this.txt_cod_moneda = new LimiteTextFieldConSQL(999999, this.lblNombreMonedaTexto, "Monedas", this);
      String[] cabecera = new String[]{
         "Codigo", "Nombre", "Estado", "CodBanco", "Tipo", "CodSucursal", "NombreBanco", "NombreSucursal", "CodMoneda", "NombreMoneda"
      };
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Cuentas", this);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addContainerGap(338, 32767)
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                    .addComponent(panel_buscador, Alignment.LEADING, 0, 0, 32767)
                                    .addComponent(panel_datos, Alignment.LEADING, -2, 476, 32767)
                              )
                              .addContainerGap()
                        )
                  )
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, 191, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(panel_buscador, -2, 201, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblCodigo, -2, 45, -2)
                        .addComponent(lblNombre, -2, 45, -2)
                        .addComponent(lblBanco, -2, 45, -2)
                        .addComponent(lblSucursal, -2, 59, -2)
                        .addComponent(lblNombreMoneda, -2, 59, -2)
                  )
                  .addGap(18)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(this.txt_codigo, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.chckActivo, -2, 93, -2)
                        )
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_cod_banco, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreBancoTexto, -2, 200, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                                .addComponent(this.txt_cod_moneda, -2, 41, -2)
                                                .addComponent(this.txt_cod_sucursal, -2, 41, -2)
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addGroup(
                                             gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                                .addComponent(this.lblNombreMonedaTexto, -2, 200, -2)
                                                .addComponent(this.lblNombreSucursalTexto, -2, 200, -2)
                                          )
                                    )
                              )
                        )
                        .addComponent(this.txt_nombre, -2, 249, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_tipo, -2, 129, -2)
                  .addContainerGap(18, 32767)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panel_tipo, -2, 113, -2)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblCodigo, -2, -1, -2)
                                    .addComponent(this.txt_codigo, -2, 25, -2)
                                    .addComponent(this.chckActivo, -2, 16, -2)
                              )
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblNombre, -2, 25, -2)
                                    .addComponent(this.txt_nombre, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                    .addGroup(
                                       gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                          .addComponent(this.lblNombreBancoTexto, -2, 25, -2)
                                          .addComponent(this.txt_cod_banco, -2, 25, -2)
                                    )
                                    .addComponent(lblBanco, -2, -1, -2)
                              )
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                                          .addComponent(this.txt_cod_sucursal, -2, 25, -2)
                                          .addComponent(this.lblNombreSucursalTexto, -2, 25, -2)
                                    )
                                    .addComponent(lblSucursal, -2, 25, -2)
                              )
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.lblNombreMonedaTexto, -2, 25, -2)
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_moneda, -2, 25, -2)
                              .addComponent(lblNombreMoneda, -2, 25, -2)
                        )
                  )
                  .addContainerGap(49, 32767)
            )
      );
      GroupLayout gl_panel_tipo = new GroupLayout(panel_tipo);
      gl_panel_tipo.setHorizontalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_tipo.createSequentialGroup().addComponent(this.chckCaja, -2, 93, -2).addGap(33))
            .addGroup(gl_panel_tipo.createSequentialGroup().addComponent(this.chckCuentaCorriente, -1, 123, 32767).addContainerGap())
      );
      gl_panel_tipo.setVerticalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.chckCaja, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.chckCuentaCorriente, -2, 16, -2)
                  .addContainerGap(72, 32767)
            )
      );
      panel_tipo.setLayout(gl_panel_tipo);
      panel_datos.setLayout(gl_panel_datos);
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_buscador.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 470, 32767))
                        .addGroup(gl_panel_buscador.createSequentialGroup().addGap(89).addComponent(this.txt_buscador, -2, 318, -2))
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
                  .addComponent(scrollPane, -1, 105, 32767)
                  .addContainerGap()
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Cuentas", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = CuentasForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  CuentasForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CuentasForm frame = new CuentasForm();

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
