package a6Depositos;

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
import utilidades.LimiteTextArea;
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

public class DepositosForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(10);
   private static final long serialVersionUID = 1L;
   private CheckPadre chckStockNegativo;
   private CheckPadre chckPOS;
   private CheckPadre chckActivo;
   private LabelPrincipal lblNombreSucursal;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private LimiteTextFieldConSQL txt_cod_sucursal;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_encargado;
   private LimiteTextField txt_telefono;
   private LimiteTextField txt_buscador;
   private LimiteTextArea txt_direccion;
   private ModeloTablaDefecto modeloTabla;
   private BuscadorTabla tabla;
   private int SW;

   public void buscarTabla() {
      int filaSeleccionada = this.tabla.getSelectedRow();
      if (filaSeleccionada != -1) {
         this.txt_codigo.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString());
         this.SW = Integer.parseInt(String.valueOf(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString()));
         this.txt_nombre.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nombre").getModelIndex()).toString());
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Estado").getModelIndex()).toString().equals("true")) {
            this.chckActivo.setSelected(true);
         } else {
            this.chckActivo.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Pos").getModelIndex()).toString().equals("true")) {
            this.chckPOS.setSelected(true);
         } else {
            this.chckPOS.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Negativo").getModelIndex()).toString().equals("true")) {
            this.chckStockNegativo.setSelected(true);
         } else {
            this.chckStockNegativo.setSelected(false);
         }

         this.txt_direccion.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Direccion").getModelIndex()).toString());
         this.txt_telefono.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Telefono").getModelIndex()).toString());
         this.txt_encargado.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Encargado").getModelIndex()).toString());
         this.txt_cod_sucursal.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodSucursal").getModelIndex()).toString());
         this.lblNombreSucursal.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NomSucursal").getModelIndex()).toString());
         this.tabla.requestFocusInWindow();
      }
   }

   public DepositosE CargarEntidades() {
      int estado = 0;
      int pos = 0;
      int stock_negativo = 0;
      if (this.chckStockNegativo.isSelected()) {
         stock_negativo = 1;
      }

      if (this.chckPOS.isSelected()) {
         pos = 1;
      }

      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new DepositosE(
            Integer.parseInt(this.txt_codigo.getText()),
            this.txt_nombre.getText(),
            estado,
            this.txt_telefono.getText(),
            this.txt_direccion.getText(),
            this.txt_encargado.getText(),
            Integer.parseInt(this.txt_cod_sucursal.getText()),
            pos,
            stock_negativo
         );
      } catch (NumberFormatException var5) {
         LogErrores.errores(var5, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var6) {
         LogErrores.errores(var6, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   public void cargarDepositos(DepositosE e) {
      this.txt_codigo.setValue(e.getCod_deposito());
      this.SW = e.getCod_deposito();
      this.txt_cod_sucursal.setValue(e.getCod_sucursal());
      this.lblNombreSucursal.setText(e.getNombre_sucursal());
      this.txt_direccion.setText(e.getDireccion());
      this.txt_encargado.setValue(e.getEncargado());
      this.txt_nombre.setValue(e.getNombre_deposito());
      this.txt_telefono.setValue(e.getTelefono());
      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      if (e.getPos() == 1) {
         this.chckPOS.setSelected(true);
      } else {
         this.chckPOS.setSelected(false);
      }

      if (e.getStock_negativo() == 1) {
         this.chckStockNegativo.setSelected(true);
      } else {
         this.chckStockNegativo.setSelected(false);
      }
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         DepositosE b = DepositosE.buscarDeposito(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarDepositos(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         DepositosE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = DepositosE.insertarDepositos(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = DepositosE.actualizarDepositos(entidad, this);
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
            DepositosE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = DepositosE.eliminarDepositos(ent, this);
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

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   private void inicializarObjetos() {
      this.chckActivo.setSelected(true);
      this.chckPOS.setSelected(false);
      this.chckStockNegativo.setSelected(false);
      this.txt_buscador.setValue("");
      this.txt_cod_sucursal.setValue(0);
      this.txt_codigo.setValue(0);
      this.txt_direccion.setText("");
      this.txt_encargado.setValue("");
      this.txt_nombre.setValue("");
      this.txt_telefono.setValue("");
      this.SW = 0;
      this.lblLineasRecuperadasTexto.setText("0");
      this.lblNombreSucursal.setText("");
      TablaBuscador.eliminarFilasConWhile(this.modeloTabla);
      this.txt_nombre.requestFocusInWindow();
   }

   public DepositosForm() {
      this.setTitle("Registro de Depositos");
      this.setBounds(100, 100, 476, 518);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("Buscador de Registro");
      PanelPadre panel_configuracion = new PanelPadre("");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblEncargado = new LabelPrincipal("Encargado", "label");
      LabelPrincipal lblTelefono = new LabelPrincipal("Telefono", "label");
      LabelPrincipal lblDireccion = new LabelPrincipal("Direccion", "label");
      LabelPrincipal lblSucursal = new LabelPrincipal("Sucursal", "label");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreSucursal = new LabelPrincipal(0);
      this.txt_cod_sucursal = new LimiteTextFieldConSQL(999999, this.lblNombreSucursal, "Sucursales", this);
      this.txt_nombre = new LimiteTextField(25);
      this.txt_encargado = new LimiteTextField(25);
      this.txt_telefono = new LimiteTextField(25);
      this.txt_buscador = new LimiteTextField(25);
      this.txt_direccion = new LimiteTextArea(70);
      this.chckActivo = new CheckPadre("Activo");
      this.chckPOS = new CheckPadre("POS");
      this.chckStockNegativo = new CheckPadre("Stock Negativo");
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "Telefono", "Direccion", "Encargado", "CodSucursal", "Pos", "Negativo", "NomSucursal"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Depositos", this);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel_buscador, Alignment.LEADING, 0, 0, 32767)
                        .addComponent(panel_datos, Alignment.LEADING, -1, 452, 32767)
                  )
                  .addContainerGap(7, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, 258, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -2, 211, 32767)
                  .addGap(3)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_buscador.createSequentialGroup().addGap(66).addComponent(this.txt_buscador, -2, 224, -2).addGap(6))
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblLineasRecuperadas, -2, 120, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                  .addContainerGap(268, 32767)
            )
            .addGroup(gl_panel_buscador.createSequentialGroup().addComponent(scrollPane, -1, 439, 32767).addContainerGap())
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(scrollPane, -1, 141, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblEncargado, -1, 59, 32767)
                                    .addComponent(lblCodigo, -1, -1, 32767)
                                    .addComponent(lblNombre, -1, -1, 32767)
                                    .addComponent(lblTelefono, -1, -1, 32767)
                                    .addComponent(lblDireccion, -1, -1, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(this.txt_codigo, -2, 67, -2)
                                                .addComponent(this.txt_nombre, -1, 224, 32767)
                                                .addComponent(this.txt_encargado)
                                                .addComponent(this.txt_telefono)
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(panel_configuracion, -1, -1, 32767)
                                    )
                                    .addComponent(this.txt_direccion, -2, 372, -2)
                              )
                        )
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(lblSucursal, -2, 59, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_sucursal, -2, 67, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreSucursal, -1, -1, 32767)
                        )
                  )
                  .addContainerGap(15, 32767)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblCodigo).addComponent(this.txt_codigo, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblNombre).addComponent(this.txt_nombre, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblEncargado).addComponent(this.txt_encargado, -2, 25, -2)
                              )
                        )
                        .addComponent(panel_configuracion, -2, 102, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblTelefono).addComponent(this.txt_telefono, -2, 25, -2))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblDireccion).addComponent(this.txt_direccion, -2, 61, -2))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(this.lblNombreSucursal, Alignment.LEADING, -2, 25, -2)
                        .addComponent(this.txt_cod_sucursal, Alignment.LEADING, -2, 25, -2)
                        .addComponent(lblSucursal)
                  )
                  .addGap(21)
            )
      );
      GroupLayout gl_panel_configuracion = new GroupLayout(panel_configuracion);
      gl_panel_configuracion.setHorizontalGroup(
         gl_panel_configuracion.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_configuracion.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_configuracion.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.chckStockNegativo, -2, 106, 32767)
                        .addComponent(this.chckActivo, -2, 93, -2)
                        .addComponent(this.chckPOS, -2, 93, -2)
                  )
                  .addContainerGap()
            )
      );
      gl_panel_configuracion.setVerticalGroup(
         gl_panel_configuracion.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_configuracion.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.chckActivo, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.chckPOS, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.chckStockNegativo, -2, 16, -2)
                  .addContainerGap(11, 32767)
            )
      );
      panel_configuracion.setLayout(gl_panel_configuracion);
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Depositos", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = DepositosForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  DepositosForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            DepositosForm frame = new DepositosForm();

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
