package utilidades;

import a1Login.LoginForm;
import a5Sucursales.SucursalesE;
import cajeros.CajerosE;
import compradores.CompradoresE;
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
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import vendedores.VendedoresE;

public class BuscadorDefectoDialog extends JDialog implements KeyListener, ActionListener {
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
   private JDialog frame;
   private JLabel lblLineasRecuperadasTexto;

   public static void main(String[] args) {
      try {
         BuscadorDefectoDialog dialog = new BuscadorDefectoDialog(null, "Vendedores");
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
            BuscadorDefectoDialog.this.seleccionado = false;
            BuscadorDefectoDialog.this.dispose();
         }
      };
      this.getRootPane().getInputMap(2).put(escape, "ESCAPE");
      this.getRootPane().getActionMap().put("ESCAPE", escapeAction);
   }

   public BuscadorDefectoDialog(JDialog frame, String titulo) {
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setIconImage(iconoVentana.getImage());
      this.setName(titulo);
      this.frame = frame;
      this.setTitle("Buscador de " + titulo);
      this.setLocationRelativeTo(frame);
      this.setSize(512, 459);
      this.getContentPane().setLayout(new BorderLayout());
      this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(this.contentPanel, "Center");
      JPanel panel = new JPanel();
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblLineasRecuperadasTexto.setHorizontalAlignment(2);
      this.txt_buscador = new JFormattedTextField();
      this.txt_buscador.setName("txt_buscador");
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      String[] cabecera = new String[]{"Codigo", "Nombre"};
      this.modelo = new ModeloTablaDefecto(cabecera);
      this.tabla = new TablaBuscador(this.modelo, "CodigoNom2");
      this.tabla.setName("tabla_buscador");
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
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addGap(33)
                              .addComponent(this.btnSeleccionar, -2, 180, -2)
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
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                        .addComponent(this.btnSalir, -2, -1, -2)
                        .addComponent(this.btnSeleccionar, -2, -1, -2)
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

      if (this.getName().trim().equals("Vendedores")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_vendedor,nombre_vendedor from vendedores where estado =1";
         } else {
            sql = "select cod_vendedor,nombre_vendedor from vendedores where estado = 1 and nombre_vendedor like '%" + this.txt_buscador.getText() + "%'";
         }

         VendedoresE.cargarVendedoresTablaDialog(sql, this.modelo, this.frame);
         System.out.println(1916);
      } else if (this.getName().trim().equals("Compradores")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_comprador,nombre_comprador from compradores where estado =1";
         } else {
            sql = "select cod_comprador,nombre_comprador from compradores where estado = 1 and nombre_comprador like '%" + this.txt_buscador.getText() + "%'";
         }

         CompradoresE.cargarCompradoresTablaDialog(sql, this.modelo, this.frame);
      } else if (this.getName().trim().equals("Cajeros")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_cajero,nombre_cajero from cajeros where estado =1";
         } else {
            sql = "select cod_cajero,nombre_cajero from cajeros where estado = 1 and nombre_cajero like '%" + this.txt_buscador.getText() + "%'";
         }

         CajerosE.cargarCajerosTablaDialog(sql, this.modelo, this.frame);
      } else if (this.getName().trim().equals("Sucursales")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_sucursal,nombre_sucursal from sucursales where estado =1";
         } else {
            sql = "select cod_sucursal,nombre_sucursal from sucursales where estado = 1 and nombre_sucursal like '%" + this.txt_buscador.getText() + "%'";
         }

         SucursalesE.cargarSucursalesTablaDialog(sql, this.modelo, this.frame);
      }

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
}
