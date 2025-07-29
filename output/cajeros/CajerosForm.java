package cajeros;

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
import utilidades.PanelPadre;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadorTablaCargar;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class CajerosForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(10);
   private static final long serialVersionUID = 1L;
   private CheckPadre chckActivo;
   private CheckPadre chckTesorero;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_buscador;
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
         CajerosE b = CajerosE.buscarCajero(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarCajeros(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         CajerosE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = CajerosE.insertarCajeros(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = CajerosE.actualizarCajeros(entidad, this);
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
            CajerosE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = CajerosE.eliminarCajeros(ent, this);
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

   public void cargarCajeros(CajerosE e) {
      this.txt_codigo.setValue(e.getCod_cajero());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_cajero()));
      this.txt_nombre.setValue(e.getNombre_cajero());
      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      if (e.getTesorero() == 1) {
         this.chckTesorero.setSelected(true);
      } else {
         this.chckTesorero.setSelected(false);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public CajerosE CargarEntidades() {
      int estado = 0;
      int tesorero = 0;
      if (this.chckTesorero.isSelected()) {
         tesorero = 1;
      }

      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new CajerosE(Integer.parseInt(this.txt_codigo.getText()), this.txt_nombre.getText(), estado, tesorero);
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
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Estado").getModelIndex()).toString().equals("true")) {
            this.chckActivo.setSelected(true);
         } else {
            this.chckActivo.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Tesorero").getModelIndex()).toString().equals("true")) {
            this.chckTesorero.setSelected(true);
         } else {
            this.chckTesorero.setSelected(false);
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
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public CajerosForm() {
      this.setTitle("Registro de Cajeros");
      this.setBounds(100, 100, 424, 402);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("Buscador de Registro");
      PanelPadre panel_definiciones = new PanelPadre("");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.txt_nombre = new LimiteTextField(25);
      this.txt_buscador = new LimiteTextField(25);
      this.chckActivo = new CheckPadre("Activo");
      this.chckTesorero = new CheckPadre("Tesorero");
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "Tesorero"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Cajeros", this);
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
                              .addComponent(lblLineasRecuperadas, -2, 117, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                        )
                        .addGroup(
                           groupLayout.createParallelGroup(Alignment.LEADING, false)
                              .addComponent(panel_datos, -1, 399, 32767)
                              .addComponent(panel_buscador, 0, 0, 32767)
                        )
                  )
                  .addContainerGap(226, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, 108, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -2, 220, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
                  .addContainerGap(120, 32767)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_buscador.createSequentialGroup().addComponent(scrollPane, -1, 369, 32767).addGap(20))
            .addGroup(gl_panel_buscador.createSequentialGroup().addGap(51).addComponent(this.txt_buscador, -2, 224, -2).addContainerGap())
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addGap(10)
                  .addComponent(scrollPane, -1, 153, 32767)
                  .addGap(0)
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(lblCodigo, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_codigo, -2, 41, -2)
                        )
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(lblNombre, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_nombre, -2, 226, -2)
                        )
                  )
                  .addGap(10)
                  .addComponent(panel_definiciones, -2, 96, -2)
                  .addContainerGap(136, 32767)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_definiciones, -2, 70, -2)
                        .addComponent(this.txt_codigo, -2, 25, -2)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(lblCodigo)
                              .addGap(32)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblNombre, -2, 25, -2)
                                    .addComponent(this.txt_nombre, -2, 25, -2)
                              )
                        )
                  )
                  .addContainerGap(121, 32767)
            )
      );
      GroupLayout gl_panel_definiciones = new GroupLayout(panel_definiciones);
      gl_panel_definiciones.setHorizontalGroup(
         gl_panel_definiciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_definiciones.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_definiciones.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.chckActivo, -2, 93, -2)
                        .addComponent(this.chckTesorero, -2, 93, -2)
                  )
                  .addContainerGap(96, 32767)
            )
      );
      gl_panel_definiciones.setVerticalGroup(
         gl_panel_definiciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_definiciones.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.chckActivo, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.chckTesorero, -2, 16, -2)
                  .addContainerGap(82, 32767)
            )
      );
      panel_definiciones.setLayout(gl_panel_definiciones);
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Cajeros", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = CajerosForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  CajerosForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CajerosForm frame = new CajerosForm();

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
