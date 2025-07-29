package a0099Cobradores;

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
import utilidades.PanelPadre;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadorTablaCargar;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class CobradoresForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(25);
   private static final long serialVersionUID = 1L;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private LimiteTextArea txt_direccion;
   private LimiteTextField txt_telefono;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_buscador;
   private CheckPadre chckActivo;
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
         CobradoresE b = CobradoresE.buscarCobrador(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarCobradores(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         CobradoresE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = CobradoresE.insertarCobradores(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = CobradoresE.actualizarCobrador(entidad, this);
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
            CobradoresE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = CobradoresE.eliminarCobradores(ent, this);
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

   public void cargarCobradores(CobradoresE e) {
      this.txt_codigo.setValue(e.getCod_cobrador());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_cobrador()));
      this.txt_nombre.setValue(e.getNombre_cobrador());
      this.txt_direccion.setText(e.getDireccion());
      this.txt_telefono.setValue(e.getTelefono());
      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public CobradoresE CargarEntidades() {
      int estado = 0;
      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new CobradoresE(
            Integer.parseInt(this.txt_codigo.getText()), this.txt_nombre.getText(), estado, this.txt_direccion.getText(), this.txt_telefono.getText()
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
         this.txt_direccion.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Direccion").getModelIndex()).toString());
         this.txt_telefono.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Telefono").getModelIndex()).toString());
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
      this.txt_buscador.setValue("");
      this.txt_codigo.setValue(0);
      this.txt_nombre.setValue("");
      this.txt_direccion.setText("");
      this.txt_telefono.setValue("");
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public CobradoresForm() {
      this.setTitle("Registro de Cobradores");
      this.setBounds(100, 100, 362, 461);
      PanelPadre panel_datos = new PanelPadre("Datos");
      PanelPadre panel_buscador = new PanelPadre("Buscador");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblDireccion = new LabelPrincipal("Direccion", "label");
      LabelPrincipal lblTelefono = new LabelPrincipal("Telefono", "label");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.txt_buscador = new LimiteTextField(25);
      this.txt_nombre = new LimiteTextField(25);
      this.txt_telefono = new LimiteTextField(25);
      this.txt_direccion = new LimiteTextArea(70);
      this.chckActivo = new CheckPadre("Activo");
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "Direccion", "Telefono"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Cobradores", this);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panel_buscador, Alignment.LEADING, 0, 0, 32767)
                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup().addGap(4).addComponent(panel_datos, -2, 347, -2))
                  )
                  .addContainerGap(24, 32767)
            )
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addComponent(lblLineasRecuperadas, -2, 101, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                  .addContainerGap()
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addComponent(panel_datos, -2, 172, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -2, 227, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
                  .addContainerGap(50, 32767)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_buscador.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 338, 32767))
            .addGroup(gl_panel_buscador.createSequentialGroup().addGap(40).addComponent(this.txt_buscador, -2, 255, -2).addContainerGap(53, 32767))
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPane, -1, 173, 32767)
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
                        .addComponent(lblDireccion, -1, 61, 32767)
                        .addComponent(lblTelefono, -1, -1, 32767)
                        .addComponent(lblNombre, -1, -1, 32767)
                        .addComponent(lblCodigo, -1, -1, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(this.txt_codigo, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.chckActivo, -2, 93, -2)
                              .addPreferredGap(ComponentPlacement.RELATED, 161, 32767)
                        )
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.TRAILING, false)
                              .addComponent(this.txt_telefono, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.txt_nombre, Alignment.LEADING, -1, -1, 32767)
                              .addComponent(this.txt_direccion, Alignment.LEADING, -1, 263, 32767)
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
                        .addComponent(this.chckActivo, -2, 16, -2)
                  )
                  .addGap(12)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblNombre, -2, 25, -2).addComponent(this.txt_nombre, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING).addComponent(lblDireccion, -2, 25, -2).addComponent(this.txt_direccion, -2, 41, -2)
                  )
                  .addGap(10)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblTelefono, -2, 25, -2).addComponent(this.txt_telefono, -2, 25, -2)
                  )
                  .addContainerGap(88, 32767)
            )
      );
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Cobradores", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = CobradoresForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  CobradoresForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CobradoresForm frame = new CobradoresForm();

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
