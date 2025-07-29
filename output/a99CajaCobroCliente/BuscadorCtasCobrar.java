package a99CajaCobroCliente;

import a1Login.LoginForm;
import a99CajaCobro.VentaTicketE;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.BotonPadre;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQL;
import utilidades.PanelPadre;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadre;

public class BuscadorCtasCobrar extends JDialog {
   private static final long serialVersionUID = 1L;
   private BotonPadre btnSeleccionar;
   private BotonPadre btnSalir;
   private List<Integer> cod_tipo_documentos = new ArrayList<>();
   private List<String> nombre_tipo_documentos = new ArrayList<>();
   private List<Integer> timbrados = new ArrayList<>();
   private List<Integer> nro_documentos = new ArrayList<>();
   private List<Integer> cuotas = new ArrayList<>();
   private List<Integer> nro_cuotas = new ArrayList<>();
   private List<Double> importes = new ArrayList<>();
   private List<Integer> cod_monedas = new ArrayList<>();
   private List<String> moneda_siglas = new ArrayList<>();
   private List<Integer> cod_clientes = new ArrayList<>();
   private List<Integer> primarios = new ArrayList<>();
   private boolean seleccionado = false;
   private ModeloTablaDefecto modelo;
   private TablaBuscador tabla;
   private JinternalPadre frame;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private LimiteTextFieldConSQL txt_cod_cliente;
   private LimiteTextFieldConSQL txt_cod_moneda;

   public BuscadorCtasCobrar(int COD_CLIENTE, int COD_MONEDA, String tipo, JinternalPadre frame) {
      PanelPadre contentPanel = new PanelPadre("");
      PanelPadre panel = new PanelPadre("");
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setIconImage(iconoVentana.getImage());
      this.frame = frame;
      this.setTitle("Buscador de Documentos Pendientes a Cobrar");
      this.setBounds(100, 100, 699, 459);
      this.getContentPane().setLayout(new BorderLayout());
      contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(contentPanel, "Center");
      LabelPrincipal lblCliente = new LabelPrincipal("Cliente", "label");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblLineasRecuperadasTexto.setHorizontalAlignment(2);
      LabelPrincipal lblNombreCliente = new LabelPrincipal(0);
      lblNombreCliente.setText("");
      this.txt_cod_moneda = new LimiteTextFieldConSQL(999999, lblNombreCliente, "Monedas", this);
      this.txt_cod_cliente = new LimiteTextFieldConSQL(999999, lblNombreCliente, "Clientes", this);
      this.txt_cod_cliente.setName("txt_buscador");
      if (COD_CLIENTE != 0) {
         this.txt_cod_cliente.setValue(COD_CLIENTE);
         this.txt_cod_cliente.setEnabled(false);
         this.txt_cod_moneda.setValue(COD_MONEDA);
      } else {
         this.txt_cod_cliente.setEnabled(true);
         lblNombreCliente.setText("");
         this.txt_cod_moneda.setValue(0);
      }

      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      String[] cabecera = new String[]{
         "Cod Tipo Doc",
         "Tipo Documento",
         "Timbrado",
         "Nro. Documento",
         "Cuotas",
         "Nro Cuota",
         "Importe",
         "CodMoneda",
         "Moneda",
         "CodCliente",
         "Primario",
         "SW"
      };
      this.modelo = new ModeloTablaDefecto(cabecera);
      this.tabla = new TablaBuscador(this.modelo, "CtasCobrar");
      this.tabla.setName("tabla_buscador");
      this.tabla.setSelectionMode(2);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
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
                        .addGroup(
                           gl_contentPanel.createSequentialGroup()
                              .addComponent(lblCliente, -2, 49, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_cliente, -2, 66, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblNombreCliente, -2, 360, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      gl_contentPanel.setVerticalGroup(
         gl_contentPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblCliente, -2, 25, -2)
                              .addComponent(this.txt_cod_cliente, -2, 31, -2)
                        )
                        .addComponent(lblNombreCliente, -2, 28, -2)
                  )
                  .addGap(14)
                  .addComponent(panel, -2, 314, -2)
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
      this.addEscapeKey();
      if (this.txt_cod_cliente.isEnabled()) {
         this.txt_cod_cliente.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
               if (e.getKeyCode() == 10) {
                  BuscadorCtasCobrar.this.cargarTabla("");
               }
            }
         });
      } else {
         this.cargarTabla("");
      }

      this.btnSalir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorCtasCobrar.this.dispose();
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscadorCtasCobrar.this.tabla.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscadorCtasCobrar.this.seleccionLinea(filaSeleccionada);
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
               int filaSeleccionada = BuscadorCtasCobrar.this.tabla.getSelectedRow();
               BuscadorCtasCobrar.this.seleccionLinea(filaSeleccionada);
            }
         }
      });
   }

   private void cargarTabla(String tipo) {
      if (this.modelo.getRowCount() - 1 >= 0) {
         while (this.modelo.getRowCount() > 0) {
            this.modelo.removeRow(0);
         }
      }

      this.txt_cod_cliente.isEnabled();
      VentaTicketE.cargarVentaETabla(
         this.modelo,
         2,
         Integer.parseInt(this.txt_cod_moneda.getText().trim().replace(".", "")),
         Integer.parseInt(this.txt_cod_cliente.getText().trim().replace(".", "")),
         this
      );
      if (this.tabla.getRowCount() > 0) {
         this.tabla.setRowSelectionInterval(0, 0);
         this.tabla.requestFocusInWindow();
      }

      if (this.modelo.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo.getRowCount())));
      }
   }

   public BuscadorCtasCobrar(int COD_CLIENTE, int COD_MONEDA, JinternalPadre frame) {
      PanelPadre contentPanel = new PanelPadre("");
      PanelPadre panel = new PanelPadre("");
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setIconImage(iconoVentana.getImage());
      this.frame = frame;
      this.setTitle("Buscador de Documentos Pendientes a Cobrar");
      this.setBounds(100, 100, 699, 459);
      this.getContentPane().setLayout(new BorderLayout());
      contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(contentPanel, "Center");
      LabelPrincipal lblCliente = new LabelPrincipal("Cliente", "label");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblLineasRecuperadasTexto.setHorizontalAlignment(2);
      LabelPrincipal lblNombreCliente = new LabelPrincipal(0);
      lblNombreCliente.setText("");
      this.txt_cod_moneda = new LimiteTextFieldConSQL(999999, lblNombreCliente, "Monedas", this);
      this.txt_cod_cliente = new LimiteTextFieldConSQL(999999, lblNombreCliente, "Clientes", this);
      this.txt_cod_cliente.setName("txt_buscador");
      if (COD_CLIENTE != 0) {
         this.txt_cod_cliente.setValue(COD_CLIENTE);
         this.txt_cod_cliente.setEnabled(false);
         this.txt_cod_moneda.setValue(COD_MONEDA);
      } else {
         this.txt_cod_cliente.setEnabled(true);
         lblNombreCliente.setText("");
         this.txt_cod_moneda.setValue(0);
      }

      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      String[] cabecera = new String[]{
         "Cod Tipo Doc",
         "Tipo Documento",
         "Timbrado",
         "Nro. Documento",
         "Cuotas",
         "Nro Cuota",
         "Importe",
         "CodMoneda",
         "Moneda",
         "CodCliente",
         "Primario",
         "SW"
      };
      this.modelo = new ModeloTablaDefecto(cabecera);
      this.tabla = new TablaBuscador(this.modelo, "CtasCobrar");
      this.tabla.setName("tabla_buscador");
      this.tabla.setSelectionMode(2);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
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
                        .addGroup(
                           gl_contentPanel.createSequentialGroup()
                              .addComponent(lblCliente, -2, 49, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_cod_cliente, -2, 66, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblNombreCliente, -2, 360, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      gl_contentPanel.setVerticalGroup(
         gl_contentPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                              .addComponent(lblCliente, -2, 25, -2)
                              .addComponent(this.txt_cod_cliente, -2, 31, -2)
                        )
                        .addComponent(lblNombreCliente, -2, 28, -2)
                  )
                  .addGap(14)
                  .addComponent(panel, -2, 314, -2)
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
      this.addEscapeKey();
      if (this.txt_cod_cliente.isEnabled()) {
         this.txt_cod_cliente.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
               if (e.getKeyCode() == 10) {
                  BuscadorCtasCobrar.this.cargarTabla();
               }
            }
         });
      } else {
         this.cargarTabla();
      }

      this.btnSalir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorCtasCobrar.this.dispose();
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscadorCtasCobrar.this.tabla.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscadorCtasCobrar.this.seleccionLinea(filaSeleccionada);
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
               int filaSeleccionada = BuscadorCtasCobrar.this.tabla.getSelectedRow();
               BuscadorCtasCobrar.this.seleccionLinea(filaSeleccionada);
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
      if (this.txt_cod_cliente.isEnabled()) {
         sql = "select\r\nctas_cobrar.cod_tipo_doc,tipo_documentos.nombre_tipo_documento,ctas_cobrar.timbrado,ctas_cobrar.nro_factura,ctas_cobrar.cuotas,ctas_cobrar.nro_cuota,\r\n((ctas_cobrar.total)-(ctas_cobrar.total_cobrado)) as saldo,ctas_cobrar.cod_moneda,monedas.sigla as sigla_moneda,ctas_cobrar.cod_cliente,ctas_cobrar.id\r\n from ctas_cobrar,tipo_documentos,monedas\r\nwhere\r\nctas_cobrar.cod_tipo_doc = tipo_documentos.cod_tipo_documento\r\nand ctas_cobrar.cod_moneda = monedas.cod_moneda and ctas_cobrar.cod_cliente =?";
      } else {
         sql = "select\r\nctas_cobrar.cod_tipo_doc,tipo_documentos.nombre_tipo_documento,ctas_cobrar.timbrado,ctas_cobrar.nro_factura,ctas_cobrar.cuotas,ctas_cobrar.nro_cuota,\r\n((ctas_cobrar.total)-(ctas_cobrar.total_cobrado)) as saldo,ctas_cobrar.cod_moneda,monedas.sigla as sigla_moneda,ctas_cobrar.cod_cliente,ctas_cobrar.id\r\n from ctas_cobrar,tipo_documentos,monedas\r\nwhere\r\nctas_cobrar.cod_tipo_doc = tipo_documentos.cod_tipo_documento\r\nand ctas_cobrar.cod_moneda = monedas.cod_moneda and ctas_cobrar.cod_cliente =? and ctas_cobrar.cod_moneda =?";
      }

      CtasCobrarE.cargarCtasCobrarTabla(
         sql,
         Integer.parseInt(this.txt_cod_cliente.getText().trim().replace(".", "")),
         Integer.parseInt(this.txt_cod_moneda.getText().trim().replace(".", "")),
         2,
         this.modelo,
         this.frame
      );
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
      this.cod_tipo_documentos.clear();
      this.nombre_tipo_documentos.clear();
      this.timbrados.clear();
      this.nro_documentos.clear();
      this.nro_cuotas.clear();
      this.cuotas.clear();
      this.importes.clear();
      this.cod_monedas.clear();
      this.moneda_siglas.clear();
      this.cod_clientes.clear();
      this.primarios.clear();
      int[] filasSeleccionadas = this.tabla.getSelectedRows();

      for (int fila : filasSeleccionadas) {
         this.cod_tipo_documentos.add(Integer.parseInt(this.tabla.getValueAt(fila, this.tabla.getColumn("Cod Tipo Doc").getModelIndex()).toString()));
         this.nombre_tipo_documentos.add(this.tabla.getValueAt(fila, this.tabla.getColumn("Tipo Documento").getModelIndex()).toString());
         this.timbrados.add(Integer.parseInt(this.tabla.getValueAt(fila, this.tabla.getColumn("Timbrado").getModelIndex()).toString()));
         this.nro_documentos.add(Integer.parseInt(this.tabla.getValueAt(fila, this.tabla.getColumn("Nro. Documento").getModelIndex()).toString()));
         this.cuotas.add(Integer.parseInt(this.tabla.getValueAt(fila, this.tabla.getColumn("Cuotas").getModelIndex()).toString()));
         this.nro_cuotas.add(Integer.parseInt(this.tabla.getValueAt(fila, this.tabla.getColumn("Nro Cuota").getModelIndex()).toString()));
         this.importes
            .add(Double.parseDouble(this.tabla.getValueAt(fila, this.tabla.getColumn("Importe").getModelIndex()).toString().replace(".", "").replace(",", ".")));
         this.cod_monedas.add(Integer.parseInt(this.tabla.getValueAt(fila, this.tabla.getColumn("CodMoneda").getModelIndex()).toString()));
         this.moneda_siglas.add(this.tabla.getValueAt(fila, this.tabla.getColumn("Moneda").getModelIndex()).toString());
         this.cod_clientes.add(Integer.parseInt(this.tabla.getValueAt(fila, this.tabla.getColumn("CodCliente").getModelIndex()).toString()));
         this.primarios.add(Integer.parseInt(this.tabla.getValueAt(fila, this.tabla.getColumn("Primario").getModelIndex()).toString()));
      }

      this.seleccionado = true;
      this.dispose();
   }

   public List<Integer> getCod_tipo_documentos() {
      return this.cod_tipo_documentos;
   }

   public List<String> getNombre_tipo_documentos() {
      return this.nombre_tipo_documentos;
   }

   public List<Integer> getTimbrados() {
      return this.timbrados;
   }

   public List<Integer> getNro_documentos() {
      return this.nro_documentos;
   }

   public List<Integer> getCuotas() {
      return this.cuotas;
   }

   public List<Double> getImportes() {
      return this.importes;
   }

   public List<String> getMoneda_siglas() {
      return this.moneda_siglas;
   }

   public List<Integer> getPrimarios() {
      return this.primarios;
   }

   public List<Integer> getNro_cuotas() {
      return this.nro_cuotas;
   }

   public List<Integer> getCod_monedas() {
      return this.cod_monedas;
   }

   public List<Integer> getCod_clientes() {
      return this.cod_clientes;
   }

   private void addEscapeKey() {
      KeyStroke escape = KeyStroke.getKeyStroke(27, 0, false);
      Action escapeAction = new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorCtasCobrar.this.seleccionado = false;
            BuscadorCtasCobrar.this.dispose();
         }
      };
      this.getRootPane().getInputMap(2).put(escape, "ESCAPE");
      this.getRootPane().getActionMap().put("ESCAPE", escapeAction);
   }

   public static void main(String[] args) {
      BuscadorCtasCobrar dialog = new BuscadorCtasCobrar(0, 0, null);

      try {
         dialog.setDefaultCloseOperation(2);
         dialog.setVisible(true);
      } catch (Exception var3) {
         LogErrores.errores(var3, "Error al iniciar el Formulario", dialog);
         var3.printStackTrace();
      }
   }
}
