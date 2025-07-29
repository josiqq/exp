package a0099ListaPrecios;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.JTextComponent;
import utilidades.CheckPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextField;
import utilidades.LimiteTextFieldConSQL;
import utilidades.PanelPadre;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorFamilia;
import utilidadesTabla.BuscadorProducto;
import utilidadesVentanas.JinternalPadre;

public class ListaPreciosForm extends JinternalPadre {
   List<Integer> codigosAEliminar = new ArrayList<>();
   public int SW;
   private static final long serialVersionUID = 1L;
   private LabelPrincipal lblTotalLineasTexto;
   private TablaDetalleListaPrecios tabla;
   private LimiteTextField txt_nombre_lista;
   private LimiteTextFieldConSQL txt_cod_moneda;
   private LimiteTextFieldConSQL txt_cod_sucursal;
   private LabelPrincipal lblNombreMoneda;
   private LabelPrincipal lblNombreSucursal;
   private LimiteTextArea txt_observacion;
   private CheckPadre chckActivo;
   private CheckPadre chckIva;
   private LabelPrincipal lblUltimoRegistroTexto;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         ListaPreciosE b = ListaPreciosE.buscarPListaPrecio(Integer.parseInt(this.txt_codigo.getText().trim()), this.tabla, 2, 2, this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarListaPrecios(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         ListaPreciosE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = ListaPreciosE.insertarPrecios(entidad, this.tabla, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = ListaPreciosE.actualizarListaPrecios(entidad, this.tabla, this, this.codigosAEliminar);
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
            ListaPreciosE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = ListaPreciosE.eliminarListaPrecios(ent, this);
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

   public void cargarListaPrecios(ListaPreciosE e) {
      this.txt_codigo.setValue(e.getCod_lista());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_lista()));
      this.txt_nombre_lista.setValue(e.getNombre_lista());
      this.txt_cod_moneda.setValue(e.getCod_moneda());
      this.lblNombreMoneda.setText(e.getNombre_moneda());
      this.txt_cod_sucursal.setValue(e.getCod_sucursal());
      this.lblNombreSucursal.setText(e.getNombre_sucursal());
      this.txt_observacion.setText(e.getObservacion());
      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      if (e.getIva() == 1) {
         this.chckIva.setSelected(true);
      } else {
         this.chckIva.setSelected(false);
      }

      this.txt_nombre_lista.requestFocusInWindow();
      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblTotalLineasTexto.setText(String.valueOf(formatoLinea.format(this.tabla.getRowCount())));
   }

   public ListaPreciosE CargarEntidades() {
      int estado = 0;
      int iva = 0;
      if (this.chckIva.isSelected()) {
         iva = 1;
      }

      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new ListaPreciosE(
            Integer.parseInt(this.txt_codigo.getText()),
            this.txt_nombre_lista.getText(),
            estado,
            Integer.parseInt(this.txt_cod_sucursal.getText()),
            Integer.parseInt(this.txt_cod_moneda.getText()),
            this.txt_observacion.getText(),
            iva
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
      this.lblUltimoRegistroTexto.setText(String.valueOf(formatoLinea.format(ListaPreciosE.ultimoRegistro(this))));
      this.SW = 0;
      this.txt_codigo.setValue(0);
      this.chckActivo.setSelected(true);
      this.chckIva.setSelected(false);
      ModeloTablaListaPrecio modelo = (ModeloTablaListaPrecio)this.tabla.getModel();
      modelo.deleteRow(this.tabla);
      modelo.addDefaultRow();
      this.txt_cod_moneda.setValue(0);
      this.lblNombreMoneda.setText("");
      this.txt_cod_sucursal.setValue(0);
      this.lblNombreSucursal.setText("");
      this.txt_observacion.setText("");
      this.txt_nombre_lista.setValue("");
      this.lblTotalLineasTexto.setText("0");
      this.txt_nombre_lista.requestFocusInWindow();
      this.codigosAEliminar.clear();
   }

   public ListaPreciosForm() {
      this.setTitle("Registro de Lista de Precios");
      this.setBounds(100, 100, 885, 602);
      PanelPadre panel_dato = new PanelPadre("");
      PanelPadre panel_detalle = new PanelPadre("Detalles de Lista de Precios");
      LabelPrincipal lblTotalLineas = new LabelPrincipal("Total Lineas:", "lineas");
      LabelPrincipal lblNroLista = new LabelPrincipal("Nro. Lista", "label");
      LabelPrincipal lblNombreLista = new LabelPrincipal("Nombre Lista", "label");
      LabelPrincipal lblSucursal = new LabelPrincipal("Sucursal", "label");
      LabelPrincipal lblMoneda = new LabelPrincipal("Moneda", "label");
      LabelPrincipal lblObservacion = new LabelPrincipal("Observacion", "label");
      LabelPrincipal lblUltimoRegistro = new LabelPrincipal("Ult. Registro:", "lineas");
      this.lblUltimoRegistroTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreSucursal = new LabelPrincipal(0);
      this.lblNombreMoneda = new LabelPrincipal(0);
      this.lblTotalLineasTexto = new LabelPrincipal("0", "lineas");
      this.txt_nombre_lista = new LimiteTextField(25);
      this.txt_cod_sucursal = new LimiteTextFieldConSQL(999999, this.lblNombreSucursal, "Sucursales", this);
      this.txt_cod_moneda = new LimiteTextFieldConSQL(999999, this.lblNombreMoneda, "Monedas", this);
      this.chckActivo = new CheckPadre("Activo");
      this.chckIva = new CheckPadre("Con Iva");
      this.txt_observacion = new LimiteTextArea(70);
      this.tabla = new TablaDetalleListaPrecios(this.lblTotalLineasTexto, this.codigosAEliminar, 2, 2);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      JButton btnInsertarFamilia = new JButton("Insertar Familias");
      btnInsertarFamilia.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if (Integer.parseInt(ListaPreciosForm.this.txt_cod_moneda.getText()) == 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Se tiene que cargar una moneda", "error");
                  rs.setLocationRelativeTo(ListaPreciosForm.this);
                  rs.setVisible(true);
                  ListaPreciosForm.this.txt_cod_moneda.requestFocusInWindow();
               } else {
                  BuscadorFamilia buscador = new BuscadorFamilia("");
                  buscador.setLocationRelativeTo(null);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     ListaPreciosForm.this.txt_cod_moneda.setEnabled(false);
                     boolean iva;
                     if (ListaPreciosForm.this.chckIva.isSelected()) {
                        iva = true;
                     } else {
                        iva = false;
                     }

                     ListaPreciosE.buscarListaPrecioTabla(
                        iva,
                        Integer.parseInt(ListaPreciosForm.this.txt_cod_moneda.getText()),
                        buscador.getCod_seccion(),
                        buscador.getCod_sub_seccion(),
                        buscador.getCod_grupo(),
                        ListaPreciosForm.this.tabla,
                        2,
                        ListaPreciosForm.this.lblTotalLineasTexto,
                        ListaPreciosForm.this
                     );
                  }
               }
            }
         }
      );
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_detalle, -1, 951, 32767)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblTotalLineas, -2, 84, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblTotalLineasTexto, -2, 84, -2)
                        )
                        .addComponent(panel_dato, -2, 857, -2)
                  )
                  .addContainerGap()
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_dato, -2, 162, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(panel_detalle, -1, 359, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblTotalLineas, -2, 15, -2)
                        .addComponent(this.lblTotalLineasTexto, -2, 15, -2)
                  )
                  .addContainerGap()
            )
      );
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 829, 32767).addContainerGap())
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 339, 32767).addContainerGap())
      );
      panel_detalle.setLayout(gl_panel_detalle);
      GroupLayout gl_panel_dato = new GroupLayout(panel_dato);
      gl_panel_dato.setHorizontalGroup(
         gl_panel_dato.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_dato.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_dato.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_dato.createParallelGroup(Alignment.TRAILING, false)
                              .addComponent(lblNroLista, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(lblNombreLista, Alignment.LEADING, -1, 84, 32767)
                        )
                        .addComponent(lblObservacion, -2, 84, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_dato.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_dato.createSequentialGroup()
                              .addGroup(
                                 gl_panel_dato.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.txt_nombre_lista, -2, 201, -2)
                                    .addGroup(
                                       gl_panel_dato.createSequentialGroup()
                                          .addComponent(this.txt_codigo, -2, 55, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(lblUltimoRegistro, -2, -1, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, 84, -2)
                                    )
                              )
                              .addGap(24)
                              .addGroup(
                                 gl_panel_dato.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(lblMoneda, -1, -1, 32767)
                                    .addComponent(lblSucursal, -1, -1, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_dato.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(this.txt_cod_moneda, -1, -1, 32767)
                                    .addComponent(this.txt_cod_sucursal, -1, 55, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_dato.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.lblNombreMoneda, -2, 200, -2)
                                    .addComponent(this.lblNombreSucursal, -2, 200, -2)
                              )
                        )
                        .addComponent(this.txt_observacion, -1, -1, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_dato.createParallelGroup(Alignment.LEADING)
                        .addComponent(btnInsertarFamilia)
                        .addComponent(this.chckIva, -2, 93, -2)
                        .addComponent(this.chckActivo, -2, 93, -2)
                  )
                  .addGap(186)
            )
      );
      gl_panel_dato.setVerticalGroup(
         gl_panel_dato.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_dato.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_dato.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_dato.createSequentialGroup()
                              .addComponent(this.chckIva, -2, 25, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.chckActivo, -2, 25, -2)
                              .addPreferredGap(ComponentPlacement.RELATED, 42, 32767)
                              .addComponent(btnInsertarFamilia)
                              .addGap(29)
                        )
                        .addGroup(
                           gl_panel_dato.createSequentialGroup()
                              .addGroup(
                                 gl_panel_dato.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_dato.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblNroLista, -2, 25, -2)
                                          .addComponent(this.txt_codigo, -2, 25, -2)
                                          .addComponent(lblSucursal, -2, 25, -2)
                                          .addComponent(this.txt_cod_sucursal, -2, 25, -2)
                                          .addComponent(lblUltimoRegistro, -2, 25, -2)
                                          .addComponent(this.lblUltimoRegistroTexto, -2, 25, -2)
                                    )
                                    .addComponent(this.lblNombreSucursal, -2, 25, -2)
                              )
                              .addGap(14)
                              .addGroup(
                                 gl_panel_dato.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_dato.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblNombreLista, -2, 25, -2)
                                          .addComponent(this.txt_nombre_lista, -2, 25, -2)
                                          .addComponent(lblMoneda, -2, 25, -2)
                                          .addComponent(this.txt_cod_moneda, -2, 25, -2)
                                    )
                                    .addComponent(this.lblNombreMoneda, -2, 25, -2)
                              )
                              .addGroup(
                                 gl_panel_dato.createParallelGroup(Alignment.LEADING)
                                    .addGroup(gl_panel_dato.createSequentialGroup().addGap(25).addComponent(lblObservacion, -2, 25, -2))
                                    .addGroup(
                                       gl_panel_dato.createSequentialGroup()
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.txt_observacion, -2, 49, -2)
                                    )
                              )
                              .addContainerGap(17, 32767)
                        )
                  )
            )
      );
      panel_dato.setLayout(gl_panel_dato);
      this.getContentPane().setLayout(groupLayout);
      if (!Beans.isDesignTime()) {
         this.inicializarObjetos();
      }

      this.tabla.getInputMap(1).put(KeyStroke.getKeyStroke(112, 0), "F1");
      this.tabla
         .getActionMap()
         .put(
            "F1",
            new AbstractAction() {
               private static final long serialVersionUID = 1L;

               @Override
               public void actionPerformed(ActionEvent e) {
                  if (ListaPreciosForm.this.tabla.getSelectedColumn() == 0) {
                     BuscadorProducto buscador = new BuscadorProducto();
                     buscador.setLocationRelativeTo(ListaPreciosForm.this);
                     buscador.setModal(true);
                     buscador.setVisible(true);
                     if (buscador.getCodigo() != null) {
                        int filaSeleccionada = ListaPreciosForm.this.tabla.getSelectedRow();
                        ListaPreciosForm.this.tabla.clearSelection();
                        ListaPreciosForm.this.tabla.requestFocusInWindow();
                        ListaPreciosForm.this.tabla
                           .changeSelection(filaSeleccionada, ListaPreciosForm.this.tabla.getColumn("Precio").getModelIndex(), false, false);
                        ListaPreciosForm.this.tabla.editCellAt(filaSeleccionada, ListaPreciosForm.this.tabla.getColumn("Precio").getModelIndex());
                        Component editorComponent = ListaPreciosForm.this.tabla.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }

                        ListaPreciosForm.this.tabla
                           .setValueAt(
                              buscador.getCodigo(),
                              ListaPreciosForm.this.tabla.getSelectedRow(),
                              ListaPreciosForm.this.tabla.getColumn("Código").getModelIndex()
                           );
                        ListaPreciosForm.this.tabla
                           .setValueAt(
                              buscador.getNombre(),
                              ListaPreciosForm.this.tabla.getSelectedRow(),
                              ListaPreciosForm.this.tabla.getColumn("Descripción").getModelIndex()
                           );
                        ListaPreciosForm.this.tabla
                           .setValueAt(
                              buscador.getV_unidad_medida(),
                              ListaPreciosForm.this.tabla.getSelectedRow(),
                              ListaPreciosForm.this.tabla.getColumn("UM").getModelIndex()
                           );
                     }
                  }
               }
            }
         );
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ListaPreciosForm frame = new ListaPreciosForm();

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
