package a0099Tarjetas;

import java.awt.EventQueue;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
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
import utilidades.RadioBoton;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadorTablaCargar;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class TarjetasForm extends JinternalPadre {
   LimiteTextField txt_codigo = new LimiteTextField(25);
   private static final long serialVersionUID = 1L;
   private CheckPadre chckActivo;
   private RadioBoton rdbtnCredito;
   private RadioBoton rdbtnDebito;
   private LimiteTextField txt_buscador;
   private LimiteTextField txt_nombre;
   private LimiteTextFieldConSQL txt_cod_proveedor;
   private LabelPrincipal lblNombreProveedor;
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
         TarjetasE b = TarjetasE.buscarTarjeta(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarTarjetas(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         TarjetasE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = TarjetasE.insertarTarjetas(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = TarjetasE.actualizarTarjeta(entidad, this);
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
            TarjetasE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = TarjetasE.eliminarTarjeta(ent, this);
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

   public void cargarTarjetas(TarjetasE e) {
      this.txt_codigo.setValue(e.getCod_tarjeta());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_tarjeta()));
      this.txt_nombre.setValue(e.getNombre_tarjeta());
      this.txt_cod_proveedor.setValue(e.getCod_proveedor());
      this.lblNombreProveedor.setText(e.getNombre_proveedor());
      if (e.getTipo() == 0) {
         this.rdbtnDebito.setSelected(true);
      } else if (e.getTipo() == 1) {
         this.rdbtnCredito.setSelected(true);
      }

      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public TarjetasE CargarEntidades() {
      int estado = 0;
      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      int tipo = 0;
      if (this.rdbtnDebito.isSelected()) {
         tipo = 0;
      } else if (this.rdbtnCredito.isSelected()) {
         tipo = 1;
      }

      try {
         return new TarjetasE(
            Integer.parseInt(this.txt_codigo.getText()), this.txt_nombre.getText(), estado, tipo, Integer.parseInt(this.txt_cod_proveedor.getText())
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
         this.txt_cod_proveedor.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodProveedor").getModelIndex()).toString());
         this.txt_cod_proveedor.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreProveedor").getModelIndex()).toString());
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Estado").getModelIndex()).toString().equals("true")) {
            this.chckActivo.setSelected(true);
         } else {
            this.chckActivo.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().equals("0")) {
            this.rdbtnDebito.setSelected(true);
         } else if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().equals("1")) {
            this.rdbtnCredito.setSelected(true);
         }

         this.tabla.requestFocusInWindow();
      }
   }

   private void inicializarObjetos() {
      this.SW = 0;
      this.chckActivo.setSelected(true);
      TablaBuscador.eliminarFilasConWhile(this.modeloTabla);
      this.txt_buscador.setValue("");
      this.txt_codigo.setValue(0);
      this.txt_nombre.setValue("");
      this.txt_cod_proveedor.setValue(0);
      this.lblNombreProveedor.setText("");
      this.rdbtnDebito.setSelected(true);
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public TarjetasForm() {
      this.setTitle("Registro de Tarjetas");
      this.setBounds(100, 100, 383, 446);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("Buscador");
      PanelPadre panel_tipo = new PanelPadre("Tipo");
      JLabel lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas", "lineas");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblProveedor = new LabelPrincipal("Proveedor", "label");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreProveedor = new LabelPrincipal(0);
      this.txt_buscador = new LimiteTextField(25);
      this.txt_nombre = new LimiteTextField(25);
      this.txt_cod_proveedor = new LimiteTextFieldConSQL(999999, this.lblNombreProveedor, "Proveedores", this);
      this.chckActivo = new CheckPadre("Activo");
      this.rdbtnDebito = new RadioBoton("Debito");
      this.rdbtnCredito = new RadioBoton("Credito");
      ButtonGroup grupo = new ButtonGroup();
      grupo.add(this.rdbtnCredito);
      grupo.add(this.rdbtnDebito);
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "Tipo", "CodProveedor", "NombreProveedor"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Tarjetas", this);
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
                        .addComponent(panel_buscador, -2, 368, -2)
                        .addComponent(panel_datos, -2, 368, -2)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addContainerGap(1, 32767)
                        )
                  )
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, 169, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -2, 203, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_buscador.createSequentialGroup().addGap(29).addComponent(this.txt_buscador, -2, 255, -2))
                        .addComponent(scrollPane, -1, 355, 32767)
                  )
                  .addContainerGap()
            )
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(scrollPane, -1, 117, 32767)
                  .addContainerGap()
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(panel_tipo, -2, 344, -2)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblProveedor, -1, -1, 32767)
                                    .addComponent(lblNombre, -1, -1, 32767)
                                    .addComponent(lblCodigo, -1, 65, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(this.chckActivo, -2, 93, -2)
                                    )
                                    .addComponent(this.txt_nombre, -2, 255, -2)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_cod_proveedor, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblNombreProveedor, -2, 200, -2)
                                    )
                              )
                        )
                  )
                  .addContainerGap()
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblCodigo, -2, 25, -2)
                        .addComponent(this.txt_codigo, -2, 25, -2)
                        .addComponent(this.chckActivo, -2, 25, -2)
                  )
                  .addGap(8)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING).addComponent(this.txt_nombre, -2, 25, -2).addComponent(lblNombre, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblProveedor, -2, 25, -2)
                        .addComponent(this.txt_cod_proveedor, -2, 25, -2)
                        .addComponent(this.lblNombreProveedor, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_tipo, -2, 46, -2)
                  .addContainerGap(43, 32767)
            )
      );
      GroupLayout gl_panel_tipo = new GroupLayout(panel_tipo);
      gl_panel_tipo.setHorizontalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo.createSequentialGroup()
                  .addGap(17)
                  .addComponent(this.rdbtnDebito, -2, 103, -2)
                  .addGap(27)
                  .addComponent(this.rdbtnCredito, -2, 103, -2)
                  .addContainerGap(243, 32767)
            )
      );
      gl_panel_tipo.setVerticalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo.createSequentialGroup()
                  .addGap(16)
                  .addGroup(
                     gl_panel_tipo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.rdbtnDebito, -2, 16, -2)
                        .addComponent(this.rdbtnCredito, -2, 16, -2)
                  )
                  .addContainerGap(22, 32767)
            )
      );
      panel_tipo.setLayout(gl_panel_tipo);
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Tarjetas", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = TarjetasForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  TarjetasForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            TarjetasForm frame = new TarjetasForm();

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
