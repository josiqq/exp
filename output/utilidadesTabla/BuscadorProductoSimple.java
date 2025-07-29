package utilidadesTabla;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utilidades.BotonPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidades.PanelPadre;
import utilidades.TablaScrollPane;
import utilidadesVentanas.JDialogBuscadores;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadreReporte;

public class BuscadorProductoSimple extends JDialogBuscadores {
   private static final long serialVersionUID = 1L;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private ModeloTablaDefecto modelo_producto;
   private TablaBuscador tabla_producto;
   private String nombre;
   private String codigo;
   private boolean seleccionado = false;
   private JinternalPadre frame;
   private LimiteTextField txt_buscador;
   private BotonPadre btnSalir;
   private BotonPadre btnSeleccionar;

   public BuscadorProductoSimple(JinternalPadreReporte frame) {
      this.setBounds(100, 100, 600, 560);
      PanelPadre panel_buscador = new PanelPadre("Buscador de Productos");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      this.txt_buscador = new LimiteTextField(25);
      this.txt_buscador.setName("txt_buscador");
      String[] cabecera_producto = new String[]{"Codigo", "Nombre"};
      this.modelo_producto = new ModeloTablaDefecto(cabecera_producto);
      this.tabla_producto = new TablaBuscador(this.modelo_producto, "CodigoNom2");
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla_producto);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addComponent(lblLineasRecuperadas, -2, 119, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnSeleccionar, -2, 151, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnSalir, -2, 102, -2)
                  .addGap(146)
            )
            .addComponent(panel_buscador, -1, 579, 32767)
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addComponent(panel_buscador, -2, 483, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                        )
                        .addGroup(
                           groupLayout.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.btnSeleccionar, -2, 33, -2)
                              .addComponent(this.btnSalir, -2, 33, -2)
                        )
                  )
                  .addGap(53)
            )
      );
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_buscador.createSequentialGroup().addGap(23).addComponent(this.txt_buscador, -2, 404, -2))
                        .addComponent(scrollPane, -1, 455, 32767)
                  )
                  .addContainerGap()
            )
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.txt_buscador, -2, 34, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(scrollPane, -1, 366, 32767)
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      this.getContentPane().setLayout(groupLayout);
      ListSelectionModel modeloSeleccion = this.tabla_producto.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int var2 = BuscadorProductoSimple.this.tabla_producto.getSelectedRow();
            }
         }
      });
      this.addEscapeKey();
      this.txt_buscador.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyReleased(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadorProductoSimple.this.cargarTabla();
            }
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscadorProductoSimple.this.tabla_producto.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscadorProductoSimple.this.seleccionLinea(filaSeleccionada);
            }
         }
      });
      this.btnSalir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorProductoSimple.this.seleccionado = false;
            BuscadorProductoSimple.this.dispose();
         }
      });
      this.tabla_producto.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyReleased(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               int filaSeleccionada = BuscadorProductoSimple.this.tabla_producto.getSelectedRow();
               BuscadorProductoSimple.this.seleccionLinea(filaSeleccionada);
            }
         }
      });
   }

   private void cargarTabla() {
      if (this.modelo_producto.getRowCount() - 1 >= 0) {
         while (this.modelo_producto.getRowCount() > 0) {
            this.modelo_producto.removeRow(0);
         }
      }

      BuscadorProductoE.buscarBuscadorBasicoSimple(this.txt_buscador.getText(), this.tabla_producto, this.modelo_producto, null);
      if (this.tabla_producto.getRowCount() > 0) {
         this.tabla_producto.setRowSelectionInterval(0, 0);
         this.tabla_producto.requestFocusInWindow();
      }

      if (this.modelo_producto.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo_producto.getRowCount())));
      }
   }

   private void seleccionLinea(int filaSeleccionada) {
      this.codigo = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Codigo").getModelIndex()).toString();
      this.nombre = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Nombre").getModelIndex()).toString();
      this.seleccionado = true;
      this.dispose();
   }

   private void addEscapeKey() {
      KeyStroke escape = KeyStroke.getKeyStroke(27, 0, false);
      Action escapeAction = new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorProductoSimple.this.seleccionado = false;
            BuscadorProductoSimple.this.dispose();
         }
      };
      this.getRootPane().getInputMap(2).put(escape, "ESCAPE");
      this.getRootPane().getActionMap().put("ESCAPE", escapeAction);
   }

   public String getCodigo() {
      return this.codigo;
   }

   public String getNombre() {
      return this.nombre;
   }

   public boolean isSeleccionado() {
      return this.seleccionado;
   }
}
