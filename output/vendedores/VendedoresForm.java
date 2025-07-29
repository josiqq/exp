package vendedores;

import java.awt.EventQueue;
import java.text.DecimalFormat;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utilidades.CheckPadre;
import utilidades.CuadroTexto2Decimales;
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

public class VendedoresForm extends JinternalPadre {
   private JFormattedTextField txt_codigo = new JFormattedTextField();
   private static final long serialVersionUID = 1L;
   private CheckPadre chckActivo;
   private CheckPadre chckSupervisor;
   private CheckPadre chckRealizaDesc;
   private LabelPrincipal lblNombreLugarVenta;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private CuadroTexto2Decimales txt_max_descuento;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_password;
   private LimiteTextField txt_buscador;
   private LimiteTextFieldConSQL txt_cod_lugar_venta;
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
         VendedoresE b = VendedoresE.buscarVendedor(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarVendedores(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         VendedoresE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = VendedoresE.insertarVendedores(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = VendedoresE.actualizarVendedores(entidad, this);
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
            VendedoresE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = VendedoresE.eliminarVendedores(ent, this);
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

   public void cargarVendedores(VendedoresE e) {
      this.txt_codigo.setValue(e.getCod_vendedor());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_vendedor()));
      this.txt_nombre.setValue(e.getNombre_vendedor());
      this.txt_cod_lugar_venta.setValue(e.getCod_lugar_venta());
      this.lblNombreLugarVenta.setText(e.getNombreLugar());
      this.txt_max_descuento.setValue(e.getDescuento_maximo());
      this.txt_password.setValue(e.getClave());
      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      if (e.getRealiza_descuento() == 1) {
         this.chckRealizaDesc.setSelected(true);
      } else {
         this.chckRealizaDesc.setSelected(false);
      }

      if (e.getSupervisor() == 1) {
         this.chckSupervisor.setSelected(true);
      } else {
         this.chckSupervisor.setSelected(false);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public VendedoresE CargarEntidades() {
      int estado = 0;
      int realizaDesc = 0;
      int supervisor = 0;
      if (this.chckSupervisor.isSelected()) {
         supervisor = 1;
      }

      if (this.chckRealizaDesc.isSelected()) {
         realizaDesc = 1;
      }

      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new VendedoresE(
            Integer.parseInt(this.txt_codigo.getText()),
            this.txt_nombre.getText(),
            estado,
            Integer.parseInt(this.txt_cod_lugar_venta.getText()),
            supervisor,
            realizaDesc,
            Double.parseDouble(this.txt_max_descuento.getText().replace(".", "").replace(",", ".")),
            this.txt_password.getText()
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
         this.txt_codigo.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString());
         this.SW = Integer.parseInt(String.valueOf(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString()));
         this.txt_nombre.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nombre").getModelIndex()).toString());
         this.txt_cod_lugar_venta.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodLugarVta").getModelIndex()).toString());
         this.lblNombreLugarVenta.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreLugarVta").getModelIndex()).toString());
         this.txt_password.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Password").getModelIndex()).toString());
         DecimalFormat df = new DecimalFormat("#,##0.00");
         this.txt_max_descuento
            .setText(
               df.format(
                  Double.parseDouble(String.valueOf(this.tabla.getValueAt(this.tabla.getSelectedRow(), this.tabla.getColumn("DescMaximo").getModelIndex())))
               )
            );
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Estado").getModelIndex()).toString().equals("true")) {
            this.chckActivo.setSelected(true);
         } else {
            this.chckActivo.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Supervisor").getModelIndex()).toString().equals("true")) {
            this.chckSupervisor.setSelected(true);
         } else {
            this.chckSupervisor.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("RealizaDesc").getModelIndex()).toString().equals("true")) {
            this.chckRealizaDesc.setSelected(true);
         } else {
            this.chckRealizaDesc.setSelected(false);
         }

         this.tabla.requestFocusInWindow();
      }
   }

   private void inicializarObjetos() {
      this.SW = 0;
      this.chckActivo.setSelected(true);
      this.chckRealizaDesc.setSelected(false);
      this.chckSupervisor.setSelected(false);
      this.txt_max_descuento.setValue(0);
      this.txt_cod_lugar_venta.setValue(0);
      this.lblNombreLugarVenta.setText("");
      this.txt_password.setValue("");
      TablaBuscador.eliminarFilasConWhile(this.modeloTabla);
      this.txt_buscador.setValue("");
      this.txt_codigo.setValue(0);
      this.txt_nombre.setValue("");
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public VendedoresForm() {
      this.setTitle("Registro de Vendedores");
      this.setBounds(100, 100, 535, 432);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("Buscador de Registro");
      PanelPadre panel_definiciones = new PanelPadre("");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblPassword = new LabelPrincipal("Contraseña", "label");
      LabelPrincipal lblLugarVenta = new LabelPrincipal("Lugar Venta", "label");
      LabelPrincipal lblMaxDescuento = new LabelPrincipal("Max Desc.", "label");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreLugarVenta = new LabelPrincipal(0);
      this.txt_nombre = new LimiteTextField(30);
      this.txt_buscador = new LimiteTextField(30);
      this.txt_max_descuento = new CuadroTexto2Decimales(2);
      this.txt_password = new LimiteTextField(12);
      this.txt_cod_lugar_venta = new LimiteTextFieldConSQL(999999, this.lblNombreLugarVenta, "LugarVenta", this);
      this.chckActivo = new CheckPadre("Activo");
      this.chckSupervisor = new CheckPadre("Supervisor");
      this.chckRealizaDesc = new CheckPadre("Realiza Desc.");
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "Supervisor", "RealizaDesc", "DescMaximo", "Password", "CodLugarVta", "NombreLugarVta"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Vendedores", this);
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
                        .addComponent(panel_buscador, -2, 513, -2)
                        .addComponent(panel_datos, -2, 513, -2)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, 111, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, 146, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -2, 217, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
                  .addContainerGap(32, 32767)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_buscador.createSequentialGroup().addGap(64).addComponent(this.txt_buscador, -2, 271, -2))
                        .addComponent(scrollPane, -1, 536, 32767)
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
                  .addComponent(scrollPane, -1, 145, 32767)
                  .addContainerGap()
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING, false)
                        .addGroup(gl_panel_datos.createSequentialGroup().addContainerGap(-1, 32767).addComponent(lblLugarVenta, -2, 62, -2))
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.TRAILING, false)
                              .addComponent(lblPassword, Alignment.LEADING, -2, 62, 32767)
                              .addComponent(lblNombre, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(lblCodigo, Alignment.LEADING, -1, -1, 32767)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(this.txt_nombre, -2, 283, -2)
                        .addComponent(this.txt_codigo, -2, 41, -2)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(this.txt_password, -2, 90, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblMaxDescuento, -2, 64, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_max_descuento, -2, 49, -2)
                        )
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(this.txt_cod_lugar_venta, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreLugarVenta, -1, -1, 32767)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_definiciones, -2, 138, -2)
                  .addGap(7)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel_definiciones, Alignment.LEADING, -1, -1, 32767)
                        .addGroup(
                           Alignment.LEADING,
                           gl_panel_datos.createSequentialGroup()
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
                                    .addComponent(lblPassword, -2, 25, -2)
                                    .addComponent(this.txt_password, -2, 25, -2)
                                    .addComponent(lblMaxDescuento, -2, 25, -2)
                                    .addComponent(this.txt_max_descuento, -2, 25, -2)
                              )
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_lugar_venta, -2, 25, -2)
                              .addComponent(lblLugarVenta, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreLugarVenta, -2, 25, -2)
                  )
                  .addGap(35)
            )
      );
      GroupLayout gl_panel_definiciones = new GroupLayout(panel_definiciones);
      gl_panel_definiciones.setHorizontalGroup(
         gl_panel_definiciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_definiciones.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_definiciones.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_definiciones.createSequentialGroup()
                              .addGroup(
                                 gl_panel_definiciones.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.chckSupervisor, -2, -1, -2)
                                    .addComponent(this.chckRealizaDesc, -2, -1, -2)
                              )
                              .addGap(218)
                        )
                        .addComponent(this.chckActivo, Alignment.LEADING, -2, 93, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_panel_definiciones.setVerticalGroup(
         gl_panel_definiciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_definiciones.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.chckActivo, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.chckSupervisor, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.chckRealizaDesc, -2, 16, -2)
                  .addContainerGap(29, 32767)
            )
      );
      panel_definiciones.setLayout(gl_panel_definiciones);
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Vendedores", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = VendedoresForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  VendedoresForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            VendedoresForm frame = new VendedoresForm();

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
