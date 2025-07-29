package a00CondicionesVentas;

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
import utilidades.NumerosTextField;
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

public class CondicionesVentasForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(10);
   private CheckPadre chckActivo;
   private static final long serialVersionUID = 1L;
   private LimiteTextField txt_buscador;
   private LimiteTextField txt_nombre;
   private NumerosTextField txt_cuotas;
   private LimiteTextFieldConSQL txt_cod_lista_precio;
   private RadioBoton rdbtnCredito;
   private RadioBoton rdbtnContado;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private LabelPrincipal lblListaPrecioNombre;
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
         CondicionesVentasE b = CondicionesVentasE.buscarCondicionVta(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarCondicionesVta(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         CondicionesVentasE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = CondicionesVentasE.insertarCondicionVta(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = CondicionesVentasE.actualizarCondicionVta(entidad, this);
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
            CondicionesVentasE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = CondicionesVentasE.eliminarCondicionesVentas(ent, this);
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

   public void cargarCondicionesVta(CondicionesVentasE e) {
      this.txt_codigo.setValue(e.getCod_condicion());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_condicion()));
      this.txt_nombre.setValue(e.getNombre_condicion());
      this.txt_cod_lista_precio.setValue(e.getCod_lista());
      this.lblListaPrecioNombre.setText(e.getNombre_lista());
      this.txt_cuotas.setValue(e.getCuotas());
      if (e.getTipo() == 0) {
         this.rdbtnContado.setSelected(true);
         this.rdbtnCredito.setSelected(false);
      } else if (e.getTipo() == 1) {
         this.rdbtnContado.setSelected(false);
         this.rdbtnCredito.setSelected(true);
      }

      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public CondicionesVentasE CargarEntidades() {
      int estado = 0;
      int v_tipo = 0;
      if (this.rdbtnContado.isSelected()) {
         v_tipo = 0;
      } else if (this.rdbtnCredito.isSelected()) {
         v_tipo = 1;
      }

      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new CondicionesVentasE(
            Integer.parseInt(this.txt_codigo.getText().trim().replace(".", "")),
            this.txt_nombre.getText(),
            estado,
            v_tipo,
            Integer.parseInt(this.txt_cuotas.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_lista_precio.getText().trim().replace(".", ""))
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
         this.txt_cuotas.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Cuotas").getModelIndex()).toString());
         this.txt_cod_lista_precio.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodLista").getModelIndex()).toString());
         this.lblListaPrecioNombre.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreLista").getModelIndex()).toString());
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Estado").getModelIndex()).toString().equals("true")) {
            this.chckActivo.setSelected(true);
         } else {
            this.chckActivo.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tipo").getModelIndex()).toString().equals("0")) {
            this.rdbtnContado.setSelected(true);
            this.rdbtnCredito.setSelected(false);
         } else {
            this.rdbtnContado.setSelected(false);
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
      this.txt_cod_lista_precio.setValue(0);
      this.lblListaPrecioNombre.setText("");
      this.rdbtnContado.setSelected(false);
      this.rdbtnCredito.setSelected(false);
      this.txt_cuotas.setValue(0);
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public CondicionesVentasForm() {
      this.setTitle("Registro de Condiciones de Ventas");
      this.setBounds(100, 100, 392, 443);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("");
      PanelPadre panel_tipo = new PanelPadre("Tipo");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblListaPrecio = new LabelPrincipal("Lista de Precio", "label");
      LabelPrincipal lblCuotas = new LabelPrincipal("Cuotas", "label");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblListaPrecioNombre = new LabelPrincipal(0);
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.txt_nombre = new LimiteTextField(25);
      this.txt_buscador = new LimiteTextField(25);
      this.txt_cuotas = new NumerosTextField(25L);
      this.txt_cod_lista_precio = new LimiteTextFieldConSQL(999999, this.lblListaPrecioNombre, "ListaPrecios", this);
      this.chckActivo = new CheckPadre("Activo");
      this.rdbtnContado = new RadioBoton("Contado");
      this.rdbtnCredito = new RadioBoton("Credito");
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "Tipo", "Cuotas", "CodLista", "NombreLista"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "CondicionesVta", this);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addContainerGap()
                              .addComponent(lblLineasRecuperadas, -2, 116, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 78, -2)
                        )
                        .addComponent(panel_buscador, -1, 379, 32767)
                        .addComponent(panel_datos, 0, 0, 32767)
                  )
                  .addContainerGap(44, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addComponent(panel_datos, -2, 219, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -2, 160, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblLineasRecuperadas, -2, 21, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, 21, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_buscador.createSequentialGroup().addGap(85).addComponent(this.txt_buscador, -2, 255, -2))
                        .addGroup(gl_panel_buscador.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 363, 32767))
                  )
                  .addGap(3)
            )
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPane, -1, 116, 32767)
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
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_tipo, -2, 276, -2)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblCodigo, -1, -1, 32767)
                                    .addComponent(lblNombre, -1, -1, 32767)
                                    .addComponent(lblCuotas, -1, -1, 32767)
                                    .addComponent(lblListaPrecio, -1, -1, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.txt_nombre, -2, 255, -2)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(this.chckActivo, -2, 93, -2)
                                    )
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_cod_lista_precio, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblListaPrecioNombre, -2, 200, -2)
                                    )
                                    .addComponent(this.txt_cuotas, -2, 55, -2)
                              )
                        )
                  )
                  .addContainerGap(54, 32767)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblCodigo, -2, 25, -2).addComponent(this.txt_codigo, -2, 25, -2)
                        )
                        .addComponent(this.chckActivo, -2, 16, -2)
                  )
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_datos.createSequentialGroup().addGap(16).addComponent(lblNombre, -2, 25, -2))
                        .addGroup(gl_panel_datos.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(this.txt_nombre, -2, 25, -2))
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblListaPrecio, -2, 25, -2)
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.LEADING)
                              .addComponent(this.lblListaPrecioNombre, -2, 25, -2)
                              .addComponent(this.txt_cod_lista_precio, -2, 25, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING).addComponent(lblCuotas, -2, 25, -2).addComponent(this.txt_cuotas, -2, 25, -2)
                  )
                  .addGap(16)
                  .addComponent(panel_tipo, -2, 52, -2)
                  .addContainerGap()
            )
      );
      GroupLayout gl_panel_tipo = new GroupLayout(panel_tipo);
      gl_panel_tipo.setHorizontalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.rdbtnContado, -2, 85, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.rdbtnCredito, -2, 141, -2)
                  .addContainerGap(41, 32767)
            )
      );
      gl_panel_tipo.setVerticalGroup(
         gl_panel_tipo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo.createSequentialGroup()
                  .addGroup(
                     gl_panel_tipo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.rdbtnContado, -2, 16, -2)
                        .addComponent(this.rdbtnCredito, -2, 16, -2)
                  )
                  .addContainerGap(33, 32767)
            )
      );
      panel_tipo.setLayout(gl_panel_tipo);
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "CondicionesVta", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = CondicionesVentasForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  CondicionesVentasForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CondicionesVentasForm frame = new CondicionesVentasForm();

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
