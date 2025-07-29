package a0098OrdenCompra;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.BotonPadre;
import utilidades.LabelPrincipal;
import utilidades.PanelPadre;
import utilidades.TablaScrollPane;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JDialogBuscadores;
import utilidadesVentanas.JinternalPadre;

public class BuscarPedidoOCForm extends JDialogBuscadores {
   private static final long serialVersionUID = 1L;
   private BotonPadre btnSeleccionar;
   private BotonPadre btnSalir;
   private boolean seleccionado = false;
   private int nro_pedido;
   private int nro_oc;
   private ModeloTablaDefecto modelo;
   private TablaBuscador tabla;
   private JinternalPadre frame;
   private LabelPrincipal lblLineasRecuperadasTexto;

   public BuscarPedidoOCForm(int cod_proveedor, int oc, JinternalPadre frame) {
      PanelPadre contentPanel = new PanelPadre("");
      PanelPadre panel = new PanelPadre("");
      this.frame = frame;
      this.setTitle("Buscador de Pedidos de Clientes");
      this.setBounds(100, 100, 699, 459);
      this.getContentPane().setLayout(new BorderLayout());
      contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(contentPanel, "South");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblLineasRecuperadasTexto.setHorizontalAlignment(2);
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      String[] cabecera = new String[]{
         "Nro. OC", "Nro. Pedido", "Fecha", "CodComprador", "Nombre Comprador", "CodProveedor", "Nombre Proveedor", "Total", "Moneda"
      };
      this.modelo = new ModeloTablaDefecto(cabecera);
      this.tabla = new TablaBuscador(this.modelo, "PedidosProveedoresOC");
      this.tabla.setName("tabla_buscador");
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla);
      GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
      gl_contentPanel.setHorizontalGroup(
         gl_contentPanel.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_contentPanel.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addGap(56)
                              .addComponent(this.btnSeleccionar, -2, 138, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                        )
                        .addComponent(panel, Alignment.TRAILING, -1, 665, 32767)
                  )
                  .addContainerGap()
            )
      );
      gl_contentPanel.setVerticalGroup(
         gl_contentPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel, -2, 353, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.btnSalir, -2, -1, -2)
                        .addComponent(this.btnSeleccionar, -2, -1, -2)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
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
            BuscarPedidoOCForm.this.seleccionado = false;
            BuscarPedidoOCForm.this.dispose();
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscarPedidoOCForm.this.tabla.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscarPedidoOCForm.this.seleccionLinea(filaSeleccionada);
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
               int filaSeleccionada = BuscarPedidoOCForm.this.tabla.getSelectedRow();
               BuscarPedidoOCForm.this.seleccionLinea(filaSeleccionada);
            }
         }
      });
      BuscarPedidoOCE.buscarOC(cod_proveedor, frame, this.tabla);
      if (this.modelo.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo.getRowCount())));
      }
   }

   public BuscarPedidoOCForm(int cod_proveedor, JinternalPadre frame) {
      PanelPadre contentPanel = new PanelPadre("");
      PanelPadre panel = new PanelPadre("");
      this.frame = frame;
      this.setTitle("Buscador de Pedidos de Clientes");
      this.setBounds(100, 100, 699, 459);
      this.getContentPane().setLayout(new BorderLayout());
      contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(contentPanel, "South");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblLineasRecuperadasTexto.setHorizontalAlignment(2);
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      String[] cabecera = new String[]{"Nro. Pedido", "Fecha", "CodComprador", "Nombre Comprador", "CodProveedor", "Nombre Proveedor", "Total", "Moneda"};
      this.modelo = new ModeloTablaDefecto(cabecera);
      this.tabla = new TablaBuscador(this.modelo, "PedidosProveedores");
      this.tabla.setName("tabla_buscador");
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla);
      GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
      gl_contentPanel.setHorizontalGroup(
         gl_contentPanel.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_contentPanel.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addGap(56)
                              .addComponent(this.btnSeleccionar, -2, 138, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                        )
                        .addComponent(panel, Alignment.TRAILING, -1, 665, 32767)
                  )
                  .addContainerGap()
            )
      );
      gl_contentPanel.setVerticalGroup(
         gl_contentPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel, -2, 353, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.btnSalir, -2, -1, -2)
                        .addComponent(this.btnSeleccionar, -2, -1, -2)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
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
            BuscarPedidoOCForm.this.seleccionado = false;
            BuscarPedidoOCForm.this.dispose();
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscarPedidoOCForm.this.tabla.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscarPedidoOCForm.this.seleccionLinea(filaSeleccionada);
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
               int filaSeleccionada = BuscarPedidoOCForm.this.tabla.getSelectedRow();
               BuscarPedidoOCForm.this.seleccionLinea(filaSeleccionada);
            }
         }
      });
      BuscarPedidoOCE.buscarPedido(cod_proveedor, frame, this.tabla);
      if (this.modelo.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo.getRowCount())));
      }
   }

   public BuscarPedidoOCForm(JinternalPadre frame, int cod_proveedor, int cod_moneda, int cod_sucursal) {
      PanelPadre contentPanel = new PanelPadre("");
      PanelPadre panel = new PanelPadre("");
      this.frame = frame;
      this.setTitle("Buscador de Pedidos de Clientes");
      this.setBounds(100, 100, 699, 459);
      this.getContentPane().setLayout(new BorderLayout());
      contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(contentPanel, "South");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblLineasRecuperadasTexto.setHorizontalAlignment(2);
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      String[] cabecera = new String[]{"Nro. Pedido", "Fecha", "CodComprador", "Nombre Comprador", "CodProveedor", "Nombre Proveedor", "Total", "Moneda"};
      this.modelo = new ModeloTablaDefecto(cabecera);
      this.tabla = new TablaBuscador(this.modelo, "PedidosProveedores");
      this.tabla.setName("tabla_buscador");
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla);
      GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
      gl_contentPanel.setHorizontalGroup(
         gl_contentPanel.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_contentPanel.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addGap(56)
                              .addComponent(this.btnSeleccionar, -2, 138, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                        )
                        .addComponent(panel, Alignment.TRAILING, -1, 665, 32767)
                  )
                  .addContainerGap()
            )
      );
      gl_contentPanel.setVerticalGroup(
         gl_contentPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel, -2, 353, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.btnSalir, -2, -1, -2)
                        .addComponent(this.btnSeleccionar, -2, -1, -2)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
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
            BuscarPedidoOCForm.this.seleccionado = false;
            BuscarPedidoOCForm.this.dispose();
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscarPedidoOCForm.this.tabla.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscarPedidoOCForm.this.seleccionLinea(filaSeleccionada);
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
               int filaSeleccionada = BuscarPedidoOCForm.this.tabla.getSelectedRow();
               BuscarPedidoOCForm.this.seleccionLinea(filaSeleccionada);
            }
         }
      });
      BuscarPedidoOCE.buscarPedido(cod_proveedor, cod_moneda, cod_sucursal, frame, this.tabla);
      if (this.modelo.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo.getRowCount())));
      }
   }

   private void seleccionLinea(int filaSeleccionada) {
      this.nro_pedido = Integer.parseInt(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nro. Pedido").getModelIndex()).toString());
      this.nro_oc = Integer.parseInt(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nro. OC").getModelIndex()).toString());
      this.seleccionado = true;
      this.dispose();
   }

   private void seleccionLinea(int filaSeleccionada, int oc) {
      this.nro_pedido = Integer.parseInt(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nro. Pedido").getModelIndex()).toString());
      this.seleccionado = true;
      this.dispose();
   }

   public static void main(String[] args) {
      BuscarPedidoOCForm dialog = new BuscarPedidoOCForm(null, 0, 0, 0);

      try {
         dialog.setVisible(true);
      } catch (Exception var3) {
         LogErrores.errores(var3, "Error al iniciar el Formulario", dialog);
         var3.printStackTrace();
      }
   }

   public int getNro_pedido() {
      return this.nro_pedido;
   }

   public boolean isSeleccionado() {
      return this.seleccionado;
   }

   public int getNro_oc() {
      return this.nro_oc;
   }
}
