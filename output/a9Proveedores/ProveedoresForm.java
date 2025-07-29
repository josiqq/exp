package a9Proveedores;

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

public class ProveedoresForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(25);
   private static final long serialVersionUID = 1L;
   private CheckPadre chckActivo;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_ruc;
   private LimiteTextField txt_telefono;
   private LimiteTextField txt_buscador;
   private LimiteTextArea txt_direccion;
   private LimiteTextFieldConSQL txt_cod_rubro;
   private LimiteTextFieldConSQL txt_cod_pais;
   private LimiteTextFieldConSQL txt_cod_ciudad;
   private LabelPrincipal lblNombreRubroTexto;
   private LabelPrincipal lblNombrePaisTexto;
   private LabelPrincipal lblNombreCiudadTexto;
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
         ProveedoresE b = ProveedoresE.buscarProveedor(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarProveedores(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         ProveedoresE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = ProveedoresE.insertarProveedores(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = ProveedoresE.actualizarProveedores(entidad, this);
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
            ProveedoresE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = ProveedoresE.eliminarProveedores(ent, this);
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

   public void cargarProveedores(ProveedoresE e) {
      this.txt_codigo.setValue(e.getCod_rubro());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_rubro()));
      this.txt_nombre.setValue(e.getNombre_rubro());
      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_cod_ciudad.setValue(e.getCod_ciudad());
      this.lblNombreCiudadTexto.setText(e.getNombre_ciudad());
      this.txt_cod_pais.setValue(e.getCod_pais());
      this.lblNombrePaisTexto.setText(e.getNombre_pais());
      this.txt_cod_rubro.setValue(e.getCod_rubro());
      this.lblNombreRubroTexto.setText(e.getNombre_rubro());
      this.txt_direccion.setText(e.getDireccion());
      this.txt_ruc.setValue(e.getRuc());
      this.txt_telefono.setValue(e.getTelefono());
      this.txt_nombre.requestFocusInWindow();
   }

   public ProveedoresE CargarEntidades() {
      int estado = 0;
      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new ProveedoresE(
            Integer.parseInt(this.txt_codigo.getText()),
            this.txt_nombre.getText(),
            estado,
            this.txt_ruc.getText(),
            this.txt_telefono.getText(),
            this.txt_direccion.getText(),
            Integer.parseInt(this.txt_cod_rubro.getText()),
            Integer.parseInt(this.txt_cod_pais.getText()),
            Integer.parseInt(this.txt_cod_ciudad.getText())
         );
      } catch (NumberFormatException var3) {
         LogErrores.errores(var3, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var4) {
         LogErrores.errores(var4, "Error al Cargar Entidad.", this);
         return null;
      }
   }

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

         this.txt_ruc.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Ruc").getModelIndex()).toString());
         this.txt_telefono.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Telefono").getModelIndex()).toString());
         this.txt_direccion.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Direccion").getModelIndex()).toString());
         this.txt_cod_rubro.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodRubro").getModelIndex()).toString());
         this.txt_cod_pais.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodPais").getModelIndex()).toString());
         this.txt_cod_ciudad.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodCiudad").getModelIndex()).toString());
         this.lblNombreRubroTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreRubro").getModelIndex()).toString());
         this.lblNombrePaisTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombrePais").getModelIndex()).toString());
         this.lblNombreCiudadTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreCiudad").getModelIndex()).toString());
         this.tabla.requestFocusInWindow();
      }
   }

   private void inicializarObjetos() {
      this.SW = 0;
      this.chckActivo.setSelected(true);
      TablaBuscador.eliminarFilasConWhile(this.modeloTabla);
      this.txt_buscador.setValue("");
      this.txt_cod_ciudad.setValue(0);
      this.txt_cod_pais.setValue(0);
      this.txt_cod_rubro.setValue(0);
      this.txt_codigo.setValue(0);
      this.txt_direccion.setText("");
      this.txt_nombre.setValue("");
      this.txt_ruc.setValue("");
      this.txt_telefono.setValue("");
      this.lblNombreCiudadTexto.setText("");
      this.lblNombrePaisTexto.setText("");
      this.lblNombreRubroTexto.setText("");
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public ProveedoresForm() {
      this.setTitle("Registro de Proveedores");
      this.setBounds(100, 100, 602, 555);
      PanelPadre panel_basico = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("Buscador de Registro");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblRuc = new LabelPrincipal("RUC", "label");
      LabelPrincipal lblTelefono = new LabelPrincipal("Telefono", "label");
      LabelPrincipal lblDireccion = new LabelPrincipal("Direccion", "label");
      LabelPrincipal lblRubro = new LabelPrincipal("Rubro", "label");
      LabelPrincipal lblCiudad = new LabelPrincipal("Ciudad", "label");
      LabelPrincipal lblPais = new LabelPrincipal("Pais", "label");
      this.lblNombreRubroTexto = new LabelPrincipal(0);
      this.lblNombrePaisTexto = new LabelPrincipal(0);
      this.lblNombreCiudadTexto = new LabelPrincipal(0);
      this.txt_buscador = new LimiteTextField(25);
      this.txt_nombre = new LimiteTextField(70);
      this.txt_ruc = new LimiteTextField(25);
      this.txt_telefono = new LimiteTextField(25);
      this.txt_cod_rubro = new LimiteTextFieldConSQL(999999, this.lblNombreRubroTexto, "Rubros", this);
      this.txt_cod_pais = new LimiteTextFieldConSQL(999999, this.lblNombrePaisTexto, "Paises", this);
      this.txt_cod_ciudad = new LimiteTextFieldConSQL(999999, this.lblNombreCiudadTexto, "Ciudades", this);
      this.txt_direccion = new LimiteTextArea(70);
      this.chckActivo = new CheckPadre("Activo");
      String[] cabecera = new String[]{
         "Codigo", "Nombre", "Estado", "Ruc", "Telefono", "Direccion", "CodRubro", "CodPais", "CodCiudad", "NombreRubro", "NombrePais", "NombreCiudad"
      };
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Proveedores", this);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Proveedores", this);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createParallelGroup(Alignment.LEADING)
                              .addComponent(panel_basico, -2, 585, -2)
                              .addComponent(panel_buscador, Alignment.TRAILING, -2, 585, -2)
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addComponent(panel_basico, -2, -1, -2)
                  .addGap(8)
                  .addComponent(panel_buscador, -2, 261, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
                  .addContainerGap(12, 32767)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addComponent(scrollPane, -1, 539, 32767)
            .addGroup(gl_panel_buscador.createSequentialGroup().addGap(70).addComponent(this.txt_buscador, -2, 374, -2).addContainerGap())
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPane, -1, 154, 32767)
                  .addContainerGap()
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      GroupLayout gl_panel_basico = new GroupLayout(panel_basico);
      gl_panel_basico.setHorizontalGroup(
         gl_panel_basico.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_basico.createSequentialGroup()
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_basico.createSequentialGroup()
                              .addComponent(lblCodigo, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.txt_codigo, -2, 76, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.chckActivo, -2, 93, -2)
                        )
                        .addGroup(
                           gl_panel_basico.createSequentialGroup()
                              .addGroup(
                                 gl_panel_basico.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblRuc, -2, 45, -2)
                                    .addComponent(lblDireccion, -2, 53, -2)
                                    .addComponent(lblRubro, -2, 45, -2)
                                    .addComponent(lblCiudad, -2, 45, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_basico.createParallelGroup(Alignment.LEADING, false)
                                    .addGroup(
                                       gl_panel_basico.createSequentialGroup()
                                          .addComponent(this.txt_ruc, -2, 200, -2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(lblTelefono, -2, 45, -2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(this.txt_telefono, -1, -1, 32767)
                                    )
                                    .addGroup(
                                       gl_panel_basico.createSequentialGroup()
                                          .addGroup(
                                             gl_panel_basico.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(this.txt_cod_ciudad, -2, 41, -2)
                                                .addComponent(this.txt_cod_rubro, -2, 41, -2)
                                          )
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addGroup(
                                             gl_panel_basico.createParallelGroup(Alignment.LEADING)
                                                .addGroup(
                                                   gl_panel_basico.createSequentialGroup()
                                                      .addComponent(this.lblNombreRubroTexto, -2, 165, -2)
                                                      .addPreferredGap(ComponentPlacement.UNRELATED)
                                                      .addComponent(lblPais, -2, 31, -2)
                                                      .addPreferredGap(ComponentPlacement.RELATED)
                                                      .addComponent(this.txt_cod_pais, -2, 41, -2)
                                                      .addPreferredGap(ComponentPlacement.RELATED)
                                                      .addComponent(this.lblNombrePaisTexto, -2, 193, -2)
                                                )
                                                .addComponent(this.lblNombreCiudadTexto, -1, -1, 32767)
                                          )
                                    )
                                    .addComponent(this.txt_direccion, -2, 511, -2)
                              )
                        )
                        .addGroup(
                           gl_panel_basico.createSequentialGroup()
                              .addComponent(lblNombre, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.txt_nombre, -1, -1, 32767)
                        )
                  )
                  .addContainerGap(14, 32767)
            )
      );
      gl_panel_basico.setVerticalGroup(
         gl_panel_basico.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_basico.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblCodigo, -2, 25, -2)
                        .addComponent(this.txt_codigo, -2, 25, -2)
                        .addComponent(this.chckActivo, -2, 16, -2)
                  )
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.LEADING).addComponent(lblNombre, -2, 25, -2).addComponent(this.txt_nombre, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblRuc, -2, 25, -2)
                        .addGroup(
                           gl_panel_basico.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_ruc, -2, 25, -2)
                              .addComponent(lblTelefono, -2, 25, -2)
                              .addComponent(this.txt_telefono, -2, 25, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblDireccion, -2, -1, -2)
                        .addComponent(this.txt_direccion, -2, 46, -2)
                  )
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_basico.createSequentialGroup()
                              .addGroup(
                                 gl_panel_basico.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblRubro, -2, 25, -2)
                                    .addComponent(this.txt_cod_rubro, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_basico.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(this.txt_cod_ciudad, -2, 25, -2)
                                    .addComponent(lblCiudad, -2, 25, -2)
                              )
                        )
                        .addGroup(
                           gl_panel_basico.createSequentialGroup()
                              .addGroup(
                                 gl_panel_basico.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(this.lblNombrePaisTexto, -2, 25, -2)
                                    .addGroup(
                                       gl_panel_basico.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(lblPais, -1, 25, -2)
                                          .addComponent(this.txt_cod_pais, -1, 25, -2)
                                    )
                                    .addComponent(this.lblNombreRubroTexto, Alignment.LEADING, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreCiudadTexto, -2, 25, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      panel_basico.setLayout(gl_panel_basico);
      this.getContentPane().setLayout(groupLayout);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = ProveedoresForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  ProveedoresForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ProveedoresForm frame = new ProveedoresForm();

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
