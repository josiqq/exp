package utilidadesTabla;

import a00Clientes.ClientesE;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.BotonPadre;
import utilidades.LabelPrincipal;
import utilidades.PanelPadre;
import utilidades.TablaScrollPane;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JDialogBuscadores;
import utilidadesVentanas.JinternalPadre;

public class BuscadorProveedor extends JDialogBuscadores {
   private static final long serialVersionUID = 1L;
   private JFormattedTextField txt_buscador;
   private BotonPadre btnSeleccionar;
   private BotonPadre btnSalir;
   private int codigo;
   private String nombre;
   private String ruc;
   private String telefono;
   private String direccion;
   private boolean seleccionado = false;
   private ModeloTablaDefecto modelo;
   private TablaBuscador tabla;
   private JinternalPadre frame;
   private LabelPrincipal lblLineasRecuperadasTexto;

   public BuscadorProveedor(JinternalPadre frame) {
      PanelPadre contentPanel = new PanelPadre("");
      this.frame = frame;
      this.setTitle("Buscador de Proveedores");
      this.setBounds(100, 100, 699, 459);
      this.getContentPane().setLayout(new BorderLayout());
      contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(contentPanel, "Center");
      PanelPadre panel = new PanelPadre("");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblLineasRecuperadasTexto.setHorizontalAlignment(2);
      this.txt_buscador = new JFormattedTextField();
      this.txt_buscador.setName("txt_buscador");
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      String[] cabecera = new String[]{"Codigo", "Nombre", "RUC", "Telefono", "Direccion"};
      this.modelo = new ModeloTablaDefecto(cabecera);
      this.tabla = new TablaBuscador(this.modelo, "CodigoNom5Proveedor");
      this.tabla.setName("tabla_buscador");
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla);
      GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
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
      contentPanel.setLayout(gl_contentPanel);
      this.btnSalir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorProveedor.this.dispose();
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscadorProveedor.this.tabla.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscadorProveedor.this.seleccionLinea(filaSeleccionada);
            }
         }
      });
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
               BuscadorProveedor.this.cargarTabla();
            }
         }
      });
      this.tabla.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyReleased(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               int filaSeleccionada = BuscadorProveedor.this.tabla.getSelectedRow();
               BuscadorProveedor.this.seleccionLinea(filaSeleccionada);
            }
         }
      });
   }

   private void cargarTabla() {
      if (this.modelo.getRowCount() - 1 >= 0) {
         while (this.modelo.getRowCount() > 0) {
            this.modelo.removeRow(0);
         }
      }

      String sql = "";
      if (this.txt_buscador.getText().equals("")) {
         sql = "select cod_proveedor,nombre_proveedor,ruc,telefono,direccion from proveedores where estado =1";
      } else {
         sql = "select cod_proveedor,nombre_proveedor,ruc,telefono,direccion from proveedores where estado =1 and nombre_proveedor like '%"
            + this.txt_buscador.getText()
            + "%'";
      }

      ClientesE.cargarClientesTabla(sql, this.modelo, this.frame);
      if (this.tabla.getRowCount() > 0) {
         this.tabla.setRowSelectionInterval(0, 0);
         this.tabla.requestFocusInWindow();
      }

      if (this.modelo.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo.getRowCount())));
      }
   }

   private void seleccionLinea(int filaSeleccionada) {
      this.codigo = Integer.parseInt(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString());
      this.nombre = this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nombre").getModelIndex()).toString();
      this.ruc = this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("RUC").getModelIndex()).toString();
      this.telefono = this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Telefono").getModelIndex()).toString();
      this.direccion = this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Direccion").getModelIndex()).toString();
      this.seleccionado = true;
      this.dispose();
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

   public String getRuc() {
      return this.ruc;
   }

   public String getTelefono() {
      return this.telefono;
   }

   public String getDireccion() {
      return this.direccion;
   }

   public static void main(String[] args) {
      BuscadorDefecto1 dialog = new BuscadorDefecto1("Vendedores");

      try {
         dialog.setVisible(true);
      } catch (Exception var3) {
         LogErrores.errores(var3, "Error al iniciar el Formulario", dialog);
         var3.printStackTrace();
      }
   }
}
