package a009AjustePrecios;

import a0099ListaPrecios.ListaPreciosE;
import a1Login.LoginForm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.BotonPadre;
import utilidades.LabelPrincipal;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class BuscadorListaPrecio extends JDialog implements KeyListener, ActionListener {
   private static final long serialVersionUID = 1L;
   private final JPanel contentPanel = new JPanel();
   private JFormattedTextField txt_buscador;
   private BotonPadre btnSeleccionar;
   private BotonPadre btnSalir;
   private int codigo;
   private String nombre;
   private boolean seleccionado = false;
   private ModeloTablaDefecto modelo;
   private TablaBuscador tabla;
   private JinternalPadre frame;
   private JLabel lblLineasRecuperadasTexto;
   private int cod_moneda;

   public static void main(String[] args) {
      try {
         BuscadorListaPrecio dialog = new BuscadorListaPrecio(null);
         dialog.setDefaultCloseOperation(2);
         dialog.setVisible(true);
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }

   private void addEscapeKey() {
      KeyStroke escape = KeyStroke.getKeyStroke(27, 0, false);
      Action escapeAction = new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorListaPrecio.this.seleccionado = false;
            BuscadorListaPrecio.this.dispose();
         }
      };
      this.getRootPane().getInputMap(2).put(escape, "ESCAPE");
      this.getRootPane().getActionMap().put("ESCAPE", escapeAction);
   }

   public BuscadorListaPrecio(JinternalPadre frame) {
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setIconImage(iconoVentana.getImage());
      this.frame = frame;
      this.setBounds(100, 100, 542, 453);
      this.getContentPane().setLayout(new BorderLayout());
      this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(this.contentPanel, "Center");
      JPanel panel = new JPanel();
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblLineasRecuperadasTexto.setHorizontalAlignment(2);
      this.txt_buscador = new JFormattedTextField();
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      String[] cabecera = new String[]{"Codigo", "Nombre", "CodMoneda"};
      this.modelo = new ModeloTablaDefecto(cabecera);
      this.tabla = new TablaBuscador(this.modelo, "ListaPrecios");
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout gl_contentPanel = new GroupLayout(this.contentPanel);
      gl_contentPanel.setHorizontalGroup(
         gl_contentPanel.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup().addGap(68).addComponent(this.txt_buscador, -2, 265, -2))
                        .addGroup(
                           gl_contentPanel.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addGap(74)
                              .addComponent(this.btnSeleccionar, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                        )
                        .addComponent(panel, Alignment.TRAILING, -1, 535, 32767)
                  )
                  .addContainerGap()
            )
      );
      gl_contentPanel.setVerticalGroup(
         gl_contentPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.txt_buscador, -2, 33, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel, -2, 314, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.btnSalir, -2, -1, -2)
                        .addComponent(this.btnSeleccionar, -2, -1, -2)
                        .addComponent(lblLineasRecuperadas)
                        .addComponent(this.lblLineasRecuperadasTexto)
                  )
                  .addContainerGap()
            )
      );
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 525, 32767))
      );
      gl_panel.setVerticalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 304, 32767))
      );
      panel.setLayout(gl_panel);
      this.contentPanel.setLayout(gl_contentPanel);
      this.addEscapeKey();
      this.txt_buscador.addKeyListener(this);
      this.btnSeleccionar.addActionListener(this);
      this.btnSalir.addActionListener(this);
      this.tabla.addKeyListener(this);
   }

   private void cargarTabla() {
      if (this.modelo.getRowCount() - 1 >= 0) {
         while (this.modelo.getRowCount() > 0) {
            this.modelo.removeRow(0);
         }
      }

      String sql = "";
      if (this.txt_buscador.getText().equals("")) {
         sql = "select cod_lista,nombre_lista,cod_moneda from lista_precios_cab where estado =1";
      } else {
         sql = "select cod_lista,nombre_lista,cod_moneda from lista_precios_cab where estado = 1 and nombre_lista like '%" + this.txt_buscador.getText() + "%'";
      }

      ListaPreciosE.cargarVendedoresTabla3(sql, this.modelo, this.frame);
      if (this.tabla.getRowCount() > 0) {
         this.tabla.setRowSelectionInterval(0, 0);
         this.tabla.requestFocusInWindow();
      }

      if (this.modelo.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo.getRowCount())));
      }
   }

   @Override
   public void keyTyped(KeyEvent e) {
   }

   @Override
   public void keyPressed(KeyEvent e) {
      if (e.getSource() == this.tabla && e.getKeyCode() == 10) {
         int filaSeleccionada = this.tabla.getSelectedRow();
         this.seleccionLinea(filaSeleccionada);
      }

      if (e.getSource() == this.txt_buscador && e.getKeyCode() == 10) {
         this.cargarTabla();
      }
   }

   private void seleccionLinea(int filaSeleccionada) {
      this.codigo = Integer.parseInt(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString());
      this.nombre = this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nombre").getModelIndex()).toString();
      this.cod_moneda = Integer.parseInt(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodMoneda").getModelIndex()).toString());
      this.seleccionado = true;
      this.dispose();
   }

   @Override
   public void keyReleased(KeyEvent e) {
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.btnSeleccionar) {
         int filaSeleccionada = this.tabla.getSelectedRow();
         if (filaSeleccionada != -1) {
            this.seleccionLinea(filaSeleccionada);
         }
      } else if (e.getSource() == this.btnSalir) {
         this.seleccionado = false;
         this.dispose();
      }
   }

   public int getCodigo() {
      return this.codigo;
   }

   public String getNombre() {
      return this.nombre;
   }

   public boolean isSeleccionado() {
      return this.seleccionado;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }
}
