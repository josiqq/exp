package a99Paises;

import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
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
import utilidades.PanelPadre;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadorTablaCargar;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class PaisesForm extends JinternalPadre {
   private JFormattedTextField txt_codigo = new JFormattedTextField();
   private static final long serialVersionUID = 1L;
   private CheckPadre chckActivo;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_sigla;
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
         PaisesE b = PaisesE.buscarPais(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarPaises(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         PaisesE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = PaisesE.insertarPais(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = PaisesE.actualizarPaises(entidad, this);
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
            PaisesE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = PaisesE.eliminarPaises(ent, this);
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

   public void cargarPaises(PaisesE e) {
      this.txt_codigo.setValue(e.getCod_pais());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_pais()));
      this.txt_nombre.setValue(e.getNombre_pais());
      this.txt_sigla.setValue(e.getSigla());
      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public PaisesE CargarEntidades() {
      int estado = 0;
      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new PaisesE(Integer.parseInt(this.txt_codigo.getText()), this.txt_nombre.getText(), estado, this.txt_sigla.getText());
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
         this.txt_sigla.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Sigla").getModelIndex()).toString());
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
      this.txt_sigla.setValue("");
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public PaisesForm() {
      this.setTitle("Registro de Paises");
      this.setBounds(100, 100, 312, 353);
      PanelPadre panel_pais = new PanelPadre("");
      PanelPadre panel = new PanelPadre("Buscador de Registro");
      JLabel lblCodigo = new JLabel("Codigo");
      JLabel lblNombre = new JLabel("Nombre");
      JLabel lblSigla = new JLabel("Sigla");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.txt_nombre = new LimiteTextField(25);
      this.txt_sigla = new LimiteTextField(25);
      this.txt_buscador = new LimiteTextField(25);
      this.chckActivo = new CheckPadre("Activo");
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "Sigla"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Paises", this);
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
                        .addComponent(panel, -2, 291, -2)
                        .addComponent(panel_pais, -2, 291, -2)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
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
                  .addComponent(panel_pais, -2, 115, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel, -1, 170, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
            )
      );
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup().addComponent(this.txt_buscador, -2, 202, -2).addGap(25))
                        .addGroup(gl_panel.createSequentialGroup().addComponent(scrollPane, -1, 268, 32767).addContainerGap())
                  )
            )
      );
      gl_panel.setVerticalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel.createSequentialGroup()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(scrollPane, -1, 122, 32767)
                  .addContainerGap()
            )
      );
      panel.setLayout(gl_panel);
      GroupLayout gl_panel_pais = new GroupLayout(panel_pais);
      gl_panel_pais.setHorizontalGroup(
         gl_panel_pais.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_pais.createSequentialGroup()
                  .addGroup(
                     gl_panel_pais.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_pais.createSequentialGroup()
                              .addComponent(lblCodigo, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_codigo, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.chckActivo, -2, 93, -2)
                        )
                        .addGroup(
                           gl_panel_pais.createSequentialGroup()
                              .addComponent(lblNombre, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_nombre, -2, 225, -2)
                        )
                        .addGroup(
                           gl_panel_pais.createSequentialGroup()
                              .addComponent(lblSigla, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_sigla, -2, 68, -2)
                        )
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      gl_panel_pais.setVerticalGroup(
         gl_panel_pais.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_pais.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_pais.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblCodigo, -2, 25, -2)
                        .addComponent(this.txt_codigo, -2, 25, -2)
                        .addComponent(this.chckActivo, -2, 16, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(gl_panel_pais.createParallelGroup(Alignment.BASELINE).addComponent(lblNombre, -2, 25, -2).addComponent(this.txt_nombre, -2, 25, -2))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(gl_panel_pais.createParallelGroup(Alignment.BASELINE).addComponent(lblSigla, -2, 25, -2).addComponent(this.txt_sigla, -2, 25, -2))
                  .addContainerGap(51, 32767)
            )
      );
      panel_pais.setLayout(gl_panel_pais);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Paises", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = PaisesForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  PaisesForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            PaisesForm frame = new PaisesForm();

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
