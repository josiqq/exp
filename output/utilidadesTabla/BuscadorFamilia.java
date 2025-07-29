package utilidadesTabla;

import a1Login.LoginForm;
import a4Familias.GrupoE;
import a4Familias.SeccionE;
import a4Familias.SubSeccionE;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utilidades.BotonPadre;
import utilidades.PanelPadre;
import utilidadesSQL.LogErrores;

public class BuscadorFamilia extends JDialog implements ActionListener {
   private boolean seleccionado = false;
   private static final long serialVersionUID = 1L;
   private BotonPadre btnSeleccionar;
   private BotonPadre btnSalir;
   private int cod_seccion;
   private int cod_sub_seccion;
   private int cod_grupo;
   private String nombre_seccion;
   private String nombre_grupo;
   private String nombre_sub_seccion;
   private ModeloTablaDefecto modeloSeccion;
   private ModeloTablaDefecto modeloSubSeccion;
   private ModeloTablaDefecto modeloGrupo;
   private BuscadorTabla tabla_seccion;
   private BuscadorTabla tabla_sub_seccion;
   private BuscadorTabla tabla_grupo;

   public BuscadorFamilia() {
      this.getContentPane().setBackground(new Color(210, 210, 210));
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setIconImage(iconoVentana.getImage());
      this.setTitle("Buscador de Familia de Productos");
      this.setBounds(100, 100, 954, 509);
      PanelPadre panel_seccion = new PanelPadre("Seccion");
      PanelPadre panel_sub_seccion = new PanelPadre("Sub Seccion");
      PanelPadre panel_grupo = new PanelPadre("Grupo");
      String[] cabecera = new String[]{"Codigo", "Nombre"};
      this.modeloSeccion = new ModeloTablaDefecto(cabecera);
      this.modeloSubSeccion = new ModeloTablaDefecto(cabecera);
      this.modeloGrupo = new ModeloTablaDefecto(cabecera);
      this.tabla_seccion = new BuscadorTabla(this.modeloSeccion, "Familias", this);
      this.tabla_sub_seccion = new BuscadorTabla(this.modeloSubSeccion, "Familias", this);
      this.tabla_grupo = new BuscadorTabla(this.modeloGrupo, "Familias", this);
      JScrollPane scrollPaneGrupo = new JScrollPane();
      scrollPaneGrupo.setViewportView(this.tabla_grupo);
      JScrollPane scrollPaneSubSeccion = new JScrollPane();
      scrollPaneSubSeccion.setViewportView(this.tabla_sub_seccion);
      JScrollPane scrollPaneSeccion = new JScrollPane();
      scrollPaneSeccion.setViewportView(this.tabla_seccion);
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               Alignment.LEADING,
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_seccion, -2, 303, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addGap(10)
                              .addComponent(this.btnSeleccionar, -2, 156, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                              .addContainerGap(347, 32767)
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_sub_seccion, -2, 302, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_grupo, -2, 303, -2)
                              .addContainerGap(-1, 32767)
                        )
                  )
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGap(33)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel_grupo, Alignment.LEADING, 0, 0, 32767)
                        .addComponent(panel_sub_seccion, Alignment.LEADING, 0, 0, 32767)
                        .addComponent(panel_seccion, Alignment.LEADING, -1, 394, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING).addComponent(this.btnSalir, -2, 33, -2).addComponent(this.btnSeleccionar, -2, 33, -2)
                  )
                  .addContainerGap(99, 32767)
            )
      );
      GroupLayout gl_panel_grupo = new GroupLayout(panel_grupo);
      gl_panel_grupo.setHorizontalGroup(
         gl_panel_grupo.createParallelGroup(Alignment.TRAILING)
            .addGroup(gl_panel_grupo.createSequentialGroup().addContainerGap().addComponent(scrollPaneGrupo, -1, 274, 32767).addContainerGap())
      );
      gl_panel_grupo.setVerticalGroup(
         gl_panel_grupo.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_grupo.createSequentialGroup().addComponent(scrollPaneGrupo, -1, 406, 32767).addGap(10))
      );
      panel_grupo.setLayout(gl_panel_grupo);
      GroupLayout gl_panel_sub_seccion = new GroupLayout(panel_sub_seccion);
      gl_panel_sub_seccion.setHorizontalGroup(gl_panel_sub_seccion.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneSubSeccion, -1, 302, 32767));
      gl_panel_sub_seccion.setVerticalGroup(
         gl_panel_sub_seccion.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_sub_seccion.createSequentialGroup().addComponent(scrollPaneSubSeccion, -1, 379, 32767).addContainerGap())
      );
      panel_sub_seccion.setLayout(gl_panel_sub_seccion);
      GroupLayout gl_panel_seccion = new GroupLayout(panel_seccion);
      gl_panel_seccion.setHorizontalGroup(gl_panel_seccion.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneSeccion, -1, 303, 32767));
      gl_panel_seccion.setVerticalGroup(
         gl_panel_seccion.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_seccion.createSequentialGroup().addComponent(scrollPaneSeccion, -1, 379, 32767).addContainerGap())
      );
      panel_seccion.setLayout(gl_panel_seccion);
      this.getContentPane().setLayout(groupLayout);
      ListSelectionModel modeloSeleccion = this.tabla_seccion.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = BuscadorFamilia.this.tabla_seccion.getSelectedRow();
               if (filaSeleccionada != -1) {
                  BuscadorFamilia.this.buscarSubSeccion();
               }
            }
         }
      });
      ListSelectionModel modeloSeleccion2 = this.tabla_sub_seccion.getSelectionModel();
      modeloSeleccion2.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = BuscadorFamilia.this.tabla_sub_seccion.getSelectedRow();
               if (filaSeleccionada != -1) {
                  BuscadorFamilia.this.buscarGrupo();
               }
            }
         }
      });
      SeccionE.cargarSeccion(this, this.modeloSeccion);
      this.btnSalir.addActionListener(this);
      this.btnSeleccionar.addActionListener(this);
   }

   private void buscarGrupo() {
      int filaSeleccionada = this.tabla_sub_seccion.getSelectedRow();
      int v_codigo = Integer.parseInt(
         String.valueOf(this.tabla_sub_seccion.getValueAt(filaSeleccionada, this.tabla_sub_seccion.getColumn("Codigo").getModelIndex()))
      );
      TablaBuscador.eliminarFilasConWhile(this.modeloGrupo);
      GrupoE.cargarGrupo(v_codigo, this.modeloGrupo, this);
   }

   private void buscarSubSeccion() {
      int filaSeleccionada = this.tabla_seccion.getSelectedRow();
      int v_codigo = Integer.parseInt(String.valueOf(this.tabla_seccion.getValueAt(filaSeleccionada, this.tabla_seccion.getColumn("Codigo").getModelIndex())));
      TablaBuscador.eliminarFilasConWhile(this.modeloSubSeccion);
      TablaBuscador.eliminarFilasConWhile(this.modeloGrupo);
      SubSeccionE.cargarSubSeccion(v_codigo, this.modeloSubSeccion, this);
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         BuscadorFamilia frame = new BuscadorFamilia();

         @Override
         public void run() {
            try {
               this.frame.setVisible(true);
            } catch (Exception var2) {
               LogErrores.errores(var2, "Error al iniciar el Formulario", this.frame);
               var2.printStackTrace();
            }
         }
      });
   }

   private void seleccionLinea() {
      int filaSeleccionadaSeccion = this.tabla_seccion.getSelectedRow();
      int filaSeleccionadaSubSeccion = this.tabla_sub_seccion.getSelectedRow();
      int filaSeleccionadagrupo = this.tabla_grupo.getSelectedRow();
      this.cod_seccion = Integer.parseInt(
         this.tabla_seccion.getValueAt(filaSeleccionadaSeccion, this.tabla_seccion.getColumn("Codigo").getModelIndex()).toString()
      );
      this.nombre_seccion = this.tabla_seccion.getValueAt(filaSeleccionadaSeccion, this.tabla_seccion.getColumn("Nombre").getModelIndex()).toString();
      this.cod_sub_seccion = Integer.parseInt(
         this.tabla_sub_seccion.getValueAt(filaSeleccionadaSubSeccion, this.tabla_sub_seccion.getColumn("Codigo").getModelIndex()).toString()
      );
      this.nombre_sub_seccion = this.tabla_sub_seccion
         .getValueAt(filaSeleccionadaSubSeccion, this.tabla_sub_seccion.getColumn("Nombre").getModelIndex())
         .toString();
      this.cod_grupo = Integer.parseInt(this.tabla_grupo.getValueAt(filaSeleccionadagrupo, this.tabla_grupo.getColumn("Codigo").getModelIndex()).toString());
      this.nombre_grupo = this.tabla_grupo.getValueAt(filaSeleccionadagrupo, this.tabla_grupo.getColumn("Nombre").getModelIndex()).toString();
      this.seleccionado = true;
      this.dispose();
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.btnSalir) {
         this.dispose();
      }

      if (e.getSource() == this.btnSeleccionar) {
         this.seleccionLinea();
      }
   }

   public boolean isSeleccionado() {
      return this.seleccionado;
   }

   public void setSeleccionado(boolean seleccionado) {
      this.seleccionado = seleccionado;
   }

   public int getCod_seccion() {
      return this.cod_seccion;
   }

   public void setCod_seccion(int cod_seccion) {
      this.cod_seccion = cod_seccion;
   }

   public int getCod_sub_seccion() {
      return this.cod_sub_seccion;
   }

   public void setCod_sub_seccion(int cod_sub_seccion) {
      this.cod_sub_seccion = cod_sub_seccion;
   }

   public int getCod_grupo() {
      return this.cod_grupo;
   }

   public void setCod_grupo(int cod_grupo) {
      this.cod_grupo = cod_grupo;
   }

   public String getNombre_seccion() {
      return this.nombre_seccion;
   }

   public void setNombre_seccion(String nombre_seccion) {
      this.nombre_seccion = nombre_seccion;
   }

   public String getNombre_grupo() {
      return this.nombre_grupo;
   }

   public void setNombre_grupo(String nombre_grupo) {
      this.nombre_grupo = nombre_grupo;
   }

   public String getNombre_sub_seccion() {
      return this.nombre_sub_seccion;
   }

   public void setNombre_sub_seccion(String nombre_sub_seccion) {
      this.nombre_sub_seccion = nombre_sub_seccion;
   }

   public BuscadorFamilia(String tipo) {
      this.getContentPane().setBackground(new Color(210, 210, 210));
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setIconImage(iconoVentana.getImage());
      this.setTitle("Buscador de Familia de Productos");
      this.setBounds(100, 100, 954, 509);
      PanelPadre panel_seccion = new PanelPadre("Seccion");
      PanelPadre panel_sub_seccion = new PanelPadre("Sub Seccion");
      PanelPadre panel_grupo = new PanelPadre("Grupo");
      String[] cabecera = new String[]{"Codigo", "Nombre"};
      this.modeloSeccion = new ModeloTablaDefecto(cabecera);
      this.modeloSubSeccion = new ModeloTablaDefecto(cabecera);
      this.modeloGrupo = new ModeloTablaDefecto(cabecera);
      this.tabla_seccion = new BuscadorTabla(this.modeloSeccion, "Familias", this);
      this.tabla_sub_seccion = new BuscadorTabla(this.modeloSubSeccion, "Familias", this);
      this.tabla_grupo = new BuscadorTabla(this.modeloGrupo, "Familias", this);
      JScrollPane scrollPaneGrupo = new JScrollPane();
      scrollPaneGrupo.setViewportView(this.tabla_grupo);
      JScrollPane scrollPaneSubSeccion = new JScrollPane();
      scrollPaneSubSeccion.setViewportView(this.tabla_sub_seccion);
      JScrollPane scrollPaneSeccion = new JScrollPane();
      scrollPaneSeccion.setViewportView(this.tabla_seccion);
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               Alignment.LEADING,
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_seccion, -2, 303, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addGap(10)
                              .addComponent(this.btnSeleccionar, -2, 156, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                              .addContainerGap(347, 32767)
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_sub_seccion, -2, 302, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_grupo, -2, 303, -2)
                              .addContainerGap(-1, 32767)
                        )
                  )
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGap(33)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel_grupo, Alignment.LEADING, 0, 0, 32767)
                        .addComponent(panel_sub_seccion, Alignment.LEADING, 0, 0, 32767)
                        .addComponent(panel_seccion, Alignment.LEADING, -1, 394, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING).addComponent(this.btnSalir, -2, 33, -2).addComponent(this.btnSeleccionar, -2, 33, -2)
                  )
                  .addContainerGap(99, 32767)
            )
      );
      GroupLayout gl_panel_grupo = new GroupLayout(panel_grupo);
      gl_panel_grupo.setHorizontalGroup(
         gl_panel_grupo.createParallelGroup(Alignment.TRAILING)
            .addGroup(gl_panel_grupo.createSequentialGroup().addContainerGap().addComponent(scrollPaneGrupo, -1, 274, 32767).addContainerGap())
      );
      gl_panel_grupo.setVerticalGroup(
         gl_panel_grupo.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_grupo.createSequentialGroup().addComponent(scrollPaneGrupo, -1, 406, 32767).addGap(10))
      );
      panel_grupo.setLayout(gl_panel_grupo);
      GroupLayout gl_panel_sub_seccion = new GroupLayout(panel_sub_seccion);
      gl_panel_sub_seccion.setHorizontalGroup(gl_panel_sub_seccion.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneSubSeccion, -1, 302, 32767));
      gl_panel_sub_seccion.setVerticalGroup(
         gl_panel_sub_seccion.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_sub_seccion.createSequentialGroup().addComponent(scrollPaneSubSeccion, -1, 379, 32767).addContainerGap())
      );
      panel_sub_seccion.setLayout(gl_panel_sub_seccion);
      GroupLayout gl_panel_seccion = new GroupLayout(panel_seccion);
      gl_panel_seccion.setHorizontalGroup(gl_panel_seccion.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneSeccion, -1, 303, 32767));
      gl_panel_seccion.setVerticalGroup(
         gl_panel_seccion.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_seccion.createSequentialGroup().addComponent(scrollPaneSeccion, -1, 379, 32767).addContainerGap())
      );
      panel_seccion.setLayout(gl_panel_seccion);
      this.getContentPane().setLayout(groupLayout);
      ListSelectionModel modeloSeleccion = this.tabla_seccion.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = BuscadorFamilia.this.tabla_seccion.getSelectedRow();
               if (filaSeleccionada != -1) {
                  BuscadorFamilia.this.buscarSubSeccion();
               }
            }
         }
      });
      ListSelectionModel modeloSeleccion2 = this.tabla_sub_seccion.getSelectionModel();
      modeloSeleccion2.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = BuscadorFamilia.this.tabla_sub_seccion.getSelectedRow();
               if (filaSeleccionada != -1) {
                  BuscadorFamilia.this.buscarGrupo();
               }
            }
         }
      });
      SeccionE.cargarSeccion(this, this.modeloSeccion);
      this.btnSalir.addActionListener(this);
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorFamilia.this.seleccionLinea(0);
         }
      });
   }

   private void seleccionLinea(int tipo) {
      int filaSeleccionadaSeccion = this.tabla_seccion.getSelectedRow();
      this.cod_seccion = Integer.parseInt(
         this.tabla_seccion.getValueAt(filaSeleccionadaSeccion, this.tabla_seccion.getColumn("Codigo").getModelIndex()).toString()
      );
      this.nombre_seccion = this.tabla_seccion.getValueAt(filaSeleccionadaSeccion, this.tabla_seccion.getColumn("Nombre").getModelIndex()).toString();
      if (this.tabla_sub_seccion.getSelectedRow() != -1) {
         int filaSeleccionadaSubSeccion = this.tabla_sub_seccion.getSelectedRow();
         this.cod_sub_seccion = Integer.parseInt(
            this.tabla_sub_seccion.getValueAt(filaSeleccionadaSubSeccion, this.tabla_sub_seccion.getColumn("Codigo").getModelIndex()).toString()
         );
         this.nombre_sub_seccion = this.tabla_sub_seccion
            .getValueAt(filaSeleccionadaSubSeccion, this.tabla_sub_seccion.getColumn("Nombre").getModelIndex())
            .toString();
      } else {
         this.cod_sub_seccion = 0;
         this.nombre_sub_seccion = "";
      }

      if (this.tabla_grupo.getSelectedRow() != -1) {
         int filaSeleccionadagrupo = this.tabla_grupo.getSelectedRow();
         this.cod_grupo = Integer.parseInt(this.tabla_grupo.getValueAt(filaSeleccionadagrupo, this.tabla_grupo.getColumn("Codigo").getModelIndex()).toString());
         this.nombre_grupo = this.tabla_grupo.getValueAt(filaSeleccionadagrupo, this.tabla_grupo.getColumn("Nombre").getModelIndex()).toString();
      } else {
         this.cod_grupo = 0;
         this.nombre_grupo = "";
      }

      this.seleccionado = true;
      this.dispose();
   }
}
