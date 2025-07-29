package a99Paises;

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

public class CiudadesForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(25);
   private static final long serialVersionUID = 1L;
   private LimiteTextFieldConSQL txt_cod_pais;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_buscador;
   private CheckPadre chckActivo;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private LabelPrincipal lblNombrePaisTexto;
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
         CiudadesE b = CiudadesE.buscarCiudad(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarCiudades(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         CiudadesE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = CiudadesE.insertarCiudad(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = CiudadesE.actualizarCiudades(entidad, this);
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
            CiudadesE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = CiudadesE.eliminarCiudades(ent, this);
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

   public void cargarCiudades(CiudadesE e) {
      this.txt_codigo.setValue(e.getCod_ciudad());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_ciudad()));
      this.txt_nombre.setValue(e.getNombre_pais());
      this.txt_cod_pais.setValue(e.getCod_pais());
      this.lblNombrePaisTexto.setText(e.getNombre_pais());
      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_nombre.requestFocusInWindow();
   }

   public CiudadesE CargarEntidades() {
      int estado = 0;
      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new CiudadesE(Integer.parseInt(this.txt_codigo.getText()), this.txt_nombre.getText(), estado, Integer.parseInt(this.txt_cod_pais.getText()));
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
         this.txt_cod_pais.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodPais").getModelIndex()).toString());
         this.lblNombrePaisTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombrePais").getModelIndex()).toString());
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
      this.txt_cod_pais.setValue(0);
      this.lblNombrePaisTexto.setText("");
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public CiudadesForm() {
      this.setTitle("Registro de Ciudades");
      this.setBounds(100, 100, 361, 360);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("Buscador de Registro");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblPais = new LabelPrincipal("Pais", "label");
      LabelPrincipal lblLineaRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblNombrePaisTexto = new LabelPrincipal(0);
      this.txt_buscador = new LimiteTextField(25);
      this.txt_nombre = new LimiteTextField(25);
      this.txt_cod_pais = new LimiteTextFieldConSQL(999999, this.lblNombrePaisTexto, "Paises", this);
      this.chckActivo = new CheckPadre("Activo");
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "CodPais", "NombrePais"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Ciudades", this);
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
                           groupLayout.createParallelGroup(Alignment.TRAILING, false)
                              .addComponent(panel_buscador, Alignment.LEADING, 0, 0, 32767)
                              .addComponent(panel_datos, Alignment.LEADING, -1, 341, 32767)
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineaRecuperadas, -2, 115, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                        )
                  )
                  .addContainerGap(47, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, 121, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(panel_buscador, -2, 164, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLineaRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
                  .addContainerGap(23, 32767)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_buscador.createSequentialGroup().addGap(43).addComponent(this.txt_buscador, -2, 278, -2).addContainerGap(-1, 32767))
            .addGroup(gl_panel_buscador.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 350, 32767).addGap(3))
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addComponent(this.txt_buscador, -2, 25, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPane, -1, 130, 32767)
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
                        .addComponent(lblCodigo, -2, 45, -2)
                        .addComponent(lblNombre, -2, 45, -2)
                        .addComponent(lblPais, -2, 45, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(this.txt_codigo, -2, 57, -2)
                              .addGap(39)
                              .addComponent(this.chckActivo, -2, 93, -2)
                        )
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(this.txt_cod_pais, -2, 55, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombrePaisTexto, -1, -1, 32767)
                        )
                        .addComponent(this.txt_nombre, -2, 268, -2)
                  )
                  .addContainerGap(44, 32767)
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
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblNombre, -2, 25, -2).addComponent(this.txt_nombre, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(lblPais, -2, 25, -2).addComponent(this.txt_cod_pais, -2, 25, -2)
                        )
                        .addComponent(this.lblNombrePaisTexto, -2, 25, -2)
                  )
                  .addContainerGap(41, 32767)
            )
      );
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Ciudades", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = CiudadesForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  CiudadesForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CiudadesForm frame = new CiudadesForm();

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
