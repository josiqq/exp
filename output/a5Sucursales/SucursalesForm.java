package a5Sucursales;

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

public class SucursalesForm extends JinternalPadre {
   private LimiteTextField txt_codigo = new LimiteTextField(99);
   public int SW;
   private static final long serialVersionUID = 1L;
   private CheckPadre chckActivo;
   private LimiteTextField txt_buscador;
   private LimiteTextField txt_nombre;
   private LimiteTextField txt_telefono;
   private LimiteTextArea txt_direccion;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private ModeloTablaDefecto modeloTabla;
   private BuscadorTabla tabla;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         SucursalesE b = SucursalesE.buscarSucursal(Integer.parseInt(this.txt_codigo.getText().trim()), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarSucursales(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         SucursalesE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = SucursalesE.insertarSucursales(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = SucursalesE.actualizarSucursales(entidad, this);
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
            SucursalesE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = SucursalesE.eliminarSucursales(ent, this);
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

   public void cargarSucursales(SucursalesE e) {
      this.txt_codigo.setValue(e.getCod_sucursal());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_sucursal()));
      this.txt_nombre.setValue(e.getNombre_sucursal());
      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      this.txt_direccion.setText(e.getDireccion());
      this.txt_telefono.setText(e.getTelefono());
      this.txt_nombre.requestFocusInWindow();
   }

   public SucursalesE CargarEntidades() {
      int estado = 0;
      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      try {
         return new SucursalesE(
            Integer.parseInt(this.txt_codigo.getText()), this.txt_nombre.getText(), estado, this.txt_telefono.getText(), this.txt_direccion.getText()
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

         this.txt_direccion.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Direccion").getModelIndex()).toString());
         this.txt_telefono.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Telefono").getModelIndex()).toString());
         this.tabla.requestFocusInWindow();
      }
   }

   private void inicializarObjetos() {
      this.chckActivo.setSelected(true);
      this.SW = 0;
      this.txt_buscador.setValue("");
      this.txt_codigo.setValue(0);
      this.txt_direccion.setText("");
      this.txt_nombre.setValue("");
      this.txt_telefono.setValue("");
      TablaBuscador.eliminarFilasConWhile(this.modeloTabla);
      this.lblLineasRecuperadasTexto.setText("0");
      this.txt_nombre.requestFocusInWindow();
   }

   public SucursalesForm() {
      this.setTitle("Registro de Sucursales");
      this.setBounds(100, 100, 433, 482);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("Buscador de Registro");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblNombre = new LabelPrincipal("Nombre", "label");
      LabelPrincipal lblTelefono = new LabelPrincipal("Telefono", "label");
      LabelPrincipal lblDireccion = new LabelPrincipal("Direccion", "label");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.chckActivo = new CheckPadre("Activo");
      this.txt_nombre = new LimiteTextField(25);
      this.txt_telefono = new LimiteTextField(25);
      this.txt_buscador = new LimiteTextField(25);
      this.txt_direccion = new LimiteTextArea(70);
      String[] cabecera = new String[]{"Codigo", "Nombre", "Estado", "Telefono", "Direccion"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Sucursales", this);
      JScrollPane scrollPaneBuscador = new JScrollPane();
      scrollPaneBuscador.setViewportView(this.tabla);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel_buscador, Alignment.LEADING, 0, 0, 32767)
                        .addComponent(panel_datos, Alignment.LEADING, -1, 412, 32767)
                  )
                  .addContainerGap(47, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_datos, -2, 191, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -1, 237, 32767)
                  .addGap(8)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_buscador.createSequentialGroup().addGap(63).addComponent(this.txt_buscador, -2, 207, -2))
                        .addGroup(
                           gl_panel_buscador.createSequentialGroup()
                              .addContainerGap()
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                        )
                        .addComponent(scrollPaneBuscador, -1, 399, 32767)
                  )
                  .addContainerGap()
            )
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addComponent(this.txt_buscador, -2, 28, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPaneBuscador, -1, 178, 32767)
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
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(lblNombre, -1, 45, 32767)
                        .addComponent(lblCodigo, -1, 45, 32767)
                        .addComponent(lblTelefono, -1, 45, 32767)
                        .addComponent(lblDireccion, -1, -1, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.txt_telefono, -2, 207, -2)
                        .addComponent(this.txt_nombre, -2, 207, -2)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addComponent(this.txt_codigo, -2, 67, -2)
                              .addGap(18)
                              .addComponent(this.chckActivo, -2, 93, -2)
                        )
                        .addComponent(this.txt_direccion, -2, 331, -2)
                  )
                  .addContainerGap(60, 32767)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblCodigo)
                        .addComponent(this.txt_codigo, -2, 28, -2)
                        .addComponent(this.chckActivo, -2, -1, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(this.txt_nombre, -2, 28, -2).addComponent(lblNombre))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(gl_panel_datos.createParallelGroup(Alignment.BASELINE).addComponent(this.txt_telefono, -2, 28, -2).addComponent(lblTelefono))
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_datos.createSequentialGroup().addGap(22).addComponent(lblDireccion))
                        .addGroup(
                           gl_panel_datos.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(this.txt_direccion, -2, 60, -2)
                        )
                  )
                  .addContainerGap(17, 32767)
            )
      );
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Sucursales", this);
      this.inicializarObjetos();
      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = SucursalesForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  SucursalesForm.this.buscarTabla();
               }
            }
         }
      });
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            SucursalesForm frame = new SucursalesForm();

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
