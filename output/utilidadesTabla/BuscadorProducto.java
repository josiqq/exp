package utilidadesTabla;

import a00Productos.ProductosForm;
import a2MenuPrincipal.Principal;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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
import utilidades.LimiteTextFieldConSQL;
import utilidades.PanelPadre;
import utilidades.TablaScrollPane;
import utilidadesVentanas.JDialogBuscadores;
import utilidadesVentanas.JinternalPadre;

public class BuscadorProducto extends JDialogBuscadores {
   private LimiteTextFieldConSQL txt_cod_deposito;
   private static final long serialVersionUID = 1L;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private ModeloTablaDefecto modelo_producto;
   private ModeloTablaDefecto modelo_precio;
   private ModeloTablaDefecto modelo_stock;
   private TablaBuscador tabla_producto;
   private TablaBuscador tabla_precio;
   private TablaBuscador tabla_stock;
   private String sigla;
   private String nombre_moneda;
   private String nombre;
   private String v_descripcion;
   private String v_unidad_medida;
   private String codigo;
   private String v_tipoFiscal;
   private double costo_producto;
   private double precio;
   private double precio_minimo;
   private double v_iva;
   private double v_regimen;
   private double v_porcentaje_gravado;
   private double stock;
   private int cod_moneda;
   private boolean seleccionado = false;
   private JinternalPadre frame;
   private LimiteTextField txt_buscador;
   private BotonPadre btnSalir;
   private BotonPadre btnSeleccionar;

   public BuscadorProducto(final int cod_lista) {
      this.setPreferredSize(new Dimension(1010, 590));
      this.pack();
      PanelPadre panel_buscador = new PanelPadre("Buscador de Productos");
      PanelPadre panel_precio = new PanelPadre("Lista de Precios");
      PanelPadre panel_stock = new PanelPadre("Stock Depositos1");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      this.txt_buscador = new LimiteTextField(25);
      this.txt_buscador.setName("txt_buscador");
      String[] cabecera_producto = new String[]{"Codigo", "Nombre", "Sigla", "CodMoneda", "NombreMoneda", "Costo", "Precio", "Precio Minimo"};
      String[] cabecera_precio = new String[]{"Lista de Precio", "Moneda", "Precio", "Precio Minimo"};
      String[] cabecera_stock = new String[]{"Deposito", "Cantidad", "Reserva", "Transito"};
      this.modelo_producto = new ModeloTablaDefecto(cabecera_producto);
      this.tabla_producto = new TablaBuscador(this.modelo_producto, "BuscadorProductoPrecio");
      this.modelo_precio = new ModeloTablaDefecto(cabecera_precio);
      this.tabla_precio = new TablaBuscador(this.modelo_precio, "ListaPrecioProducto");
      this.modelo_stock = new ModeloTablaDefecto(cabecera_stock);
      this.tabla_stock = new TablaBuscador(this.modelo_stock, "StockProducto");
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla_producto);
      TablaScrollPane scrollPaneListaPrecio = new TablaScrollPane(this.tabla_precio);
      TablaScrollPane scrollPaneDepositos = new TablaScrollPane(this.tabla_stock);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_buscador, -2, 532, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(panel_stock, -1, -1, 32767)
                                    .addComponent(panel_precio, -1, 454, 32767)
                              )
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, 119, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addGap(41)
                              .addComponent(this.btnSeleccionar, -2, 151, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                        )
                  )
                  .addGap(38)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(panel_buscador, -2, 483, -2)
                        .addGroup(groupLayout.createSequentialGroup().addComponent(panel_precio, -2, 210, -2).addGap(3).addComponent(panel_stock, -2, 206, -2))
                  )
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
      GroupLayout gl_panel_stock = new GroupLayout(panel_stock);
      gl_panel_stock.setHorizontalGroup(gl_panel_stock.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneDepositos, -1, 348, 32767));
      gl_panel_stock.setVerticalGroup(
         gl_panel_stock.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_stock.createSequentialGroup().addComponent(scrollPaneDepositos, -2, 179, -2).addContainerGap(-1, 32767))
      );
      panel_stock.setLayout(gl_panel_stock);
      GroupLayout gl_panel_precio = new GroupLayout(panel_precio);
      gl_panel_precio.setHorizontalGroup(
         gl_panel_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_precio.createSequentialGroup().addComponent(scrollPaneListaPrecio, -2, 431, -2).addContainerGap(45, 32767))
      );
      gl_panel_precio.setVerticalGroup(
         gl_panel_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_precio.createSequentialGroup().addComponent(scrollPaneListaPrecio, -2, 184, -2).addContainerGap(-1, 32767))
      );
      panel_precio.setLayout(gl_panel_precio);
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
      modeloSeleccion.addListSelectionListener(
         new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
               if (!e.getValueIsAdjusting()) {
                  int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
                  if (filaSeleccionada != -1) {
                     BuscadorProductoE.buscarListaPrecios(
                        String.valueOf(
                           BuscadorProducto.this.tabla_producto
                              .getValueAt(filaSeleccionada, BuscadorProducto.this.tabla_producto.getColumn("Codigo").getModelIndex())
                              .toString()
                        ),
                        BuscadorProducto.this.tabla_precio,
                        BuscadorProducto.this.modelo_precio,
                        BuscadorProducto.this
                     );
                     BuscadorProductoE.buscarStockProducto(
                        String.valueOf(
                           BuscadorProducto.this.tabla_producto
                              .getValueAt(filaSeleccionada, BuscadorProducto.this.tabla_producto.getColumn("Codigo").getModelIndex())
                              .toString()
                        ),
                        BuscadorProducto.this.tabla_stock,
                        BuscadorProducto.this.modelo_stock,
                        BuscadorProducto.this
                     );
                  }
               }
            }
         }
      );
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
               BuscadorProducto.this.cargarTabla(cod_lista);
            }
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscadorProducto.this.seleccionLinea(filaSeleccionada, cod_lista);
            }
         }
      });
      this.btnSalir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorProducto.this.seleccionado = false;
            BuscadorProducto.this.dispose();
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
               int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
               BuscadorProducto.this.seleccionLinea(filaSeleccionada, cod_lista);
            }
         }
      });
   }

   private void cargarTabla(int cod_lista) {
      if (this.modelo_producto.getRowCount() - 1 >= 0) {
         while (this.modelo_producto.getRowCount() > 0) {
            this.modelo_producto.removeRow(0);
         }
      }

      BuscadorProductoE.buscarBuscadorAjustePrecio(cod_lista, this.txt_buscador.getText(), this.tabla_producto, this.modelo_producto, this);
      if (this.tabla_producto.getRowCount() > 0) {
         this.tabla_producto.setRowSelectionInterval(0, 0);
         this.tabla_producto.requestFocusInWindow();
      }

      if (this.modelo_producto.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo_producto.getRowCount())));
      }
   }

   private void seleccionLinea(int filaSeleccionada, int cod_lista) {
      this.codigo = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Codigo").getModelIndex()).toString();
      this.nombre = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Nombre").getModelIndex()).toString();
      this.sigla = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Sigla").getModelIndex()).toString();
      this.cod_moneda = Integer.parseInt(
         this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("CodMoneda").getModelIndex()).toString()
      );
      this.nombre_moneda = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("NombreMoneda").getModelIndex()).toString();
      this.costo_producto = Double.parseDouble(
         this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Costo").getModelIndex()).toString()
      );
      this.precio = Double.parseDouble(this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Precio").getModelIndex()).toString());
      this.precio_minimo = Double.parseDouble(
         this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Precio Minimo").getModelIndex()).toString()
      );
      this.seleccionado = true;
      this.dispose();
   }

   public BuscadorProducto(final double tipo) {
      this.setPreferredSize(new Dimension(1010, 590));
      this.pack();
      PanelPadre panel_buscador = new PanelPadre("Buscador de Productos");
      PanelPadre panel_precio = new PanelPadre("Lista de Precios");
      PanelPadre panel_stock = new PanelPadre("Stock Depositos");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      this.txt_buscador = new LimiteTextField(25);
      this.txt_buscador.setName("txt_buscador");
      String[] cabecera_producto = new String[]{"Codigo", "Nombre", "Sigla", "Costo", "Stock"};
      String[] cabecera_precio = new String[]{"Lista de Precio", "Moneda", "Precio", "Precio Minimo"};
      String[] cabecera_stock = new String[]{"Deposito", "Cantidad", "Reserva", "Transito"};
      this.modelo_producto = new ModeloTablaDefecto(cabecera_producto);
      this.tabla_producto = new TablaBuscador(this.modelo_producto, "AjusteStock");
      this.modelo_precio = new ModeloTablaDefecto(cabecera_precio);
      this.tabla_precio = new TablaBuscador(this.modelo_precio, "ListaPrecioProducto");
      this.modelo_stock = new ModeloTablaDefecto(cabecera_stock);
      this.tabla_stock = new TablaBuscador(this.modelo_stock, "StockProducto");
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla_producto);
      TablaScrollPane scrollPaneListaPrecio = new TablaScrollPane(this.tabla_precio);
      TablaScrollPane scrollPaneDepositos = new TablaScrollPane(this.tabla_stock);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_buscador, -2, 532, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(panel_stock, -1, -1, 32767)
                                    .addComponent(panel_precio, -1, 454, 32767)
                              )
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, 119, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addGap(41)
                              .addComponent(this.btnSeleccionar, -2, 151, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                        )
                  )
                  .addGap(38)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(panel_buscador, -2, 483, -2)
                        .addGroup(groupLayout.createSequentialGroup().addComponent(panel_precio, -2, 210, -2).addGap(3).addComponent(panel_stock, -2, 206, -2))
                  )
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
      GroupLayout gl_panel_stock = new GroupLayout(panel_stock);
      gl_panel_stock.setHorizontalGroup(gl_panel_stock.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneDepositos, -1, 348, 32767));
      gl_panel_stock.setVerticalGroup(
         gl_panel_stock.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_stock.createSequentialGroup().addComponent(scrollPaneDepositos, -2, 179, -2).addContainerGap(-1, 32767))
      );
      panel_stock.setLayout(gl_panel_stock);
      GroupLayout gl_panel_precio = new GroupLayout(panel_precio);
      gl_panel_precio.setHorizontalGroup(
         gl_panel_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_precio.createSequentialGroup().addComponent(scrollPaneListaPrecio, -2, 431, -2).addContainerGap(45, 32767))
      );
      gl_panel_precio.setVerticalGroup(
         gl_panel_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_precio.createSequentialGroup().addComponent(scrollPaneListaPrecio, -2, 184, -2).addContainerGap(-1, 32767))
      );
      panel_precio.setLayout(gl_panel_precio);
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
      this.addEscapeKey();
      ListSelectionModel modeloSeleccion = this.tabla_producto.getSelectionModel();
      modeloSeleccion.addListSelectionListener(
         new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
               if (!e.getValueIsAdjusting()) {
                  int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
                  if (filaSeleccionada != -1) {
                     BuscadorProductoE.buscarListaPrecios(
                        String.valueOf(
                           BuscadorProducto.this.tabla_producto
                              .getValueAt(filaSeleccionada, BuscadorProducto.this.tabla_producto.getColumn("Codigo").getModelIndex())
                              .toString()
                        ),
                        BuscadorProducto.this.tabla_precio,
                        BuscadorProducto.this.modelo_precio,
                        BuscadorProducto.this
                     );
                     BuscadorProductoE.buscarStockProducto(
                        String.valueOf(
                           BuscadorProducto.this.tabla_producto
                              .getValueAt(filaSeleccionada, BuscadorProducto.this.tabla_producto.getColumn("Codigo").getModelIndex())
                              .toString()
                        ),
                        BuscadorProducto.this.tabla_stock,
                        BuscadorProducto.this.modelo_stock,
                        BuscadorProducto.this
                     );
                  }
               }
            }
         }
      );
      this.txt_buscador.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadorProducto.this.cargarTabla(tipo);
            }
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscadorProducto.this.seleccionLinea(filaSeleccionada, tipo);
            }
         }
      });
      this.btnSalir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorProducto.this.seleccionado = false;
            BuscadorProducto.this.dispose();
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
               int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
               BuscadorProducto.this.seleccionLinea(filaSeleccionada, tipo);
            }
         }
      });
   }

   private void seleccionLinea(int filaSeleccionada, double tipo) {
      this.codigo = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Codigo").getModelIndex()).toString();
      this.nombre = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Nombre").getModelIndex()).toString();
      this.sigla = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Sigla").getModelIndex()).toString();
      this.seleccionado = true;
      this.dispose();
   }

   private void cargarTabla(double tipo) {
      if (this.modelo_producto.getRowCount() - 1 >= 0) {
         while (this.modelo_producto.getRowCount() > 0) {
            this.modelo_producto.removeRow(0);
         }
      }

      BuscadorProductoE.buscarBuscadorBasicosinCosto(this.txt_buscador.getText(), this.tabla_producto, this.modelo_producto, this.frame);
      if (this.tabla_producto.getRowCount() > 0) {
         this.tabla_producto.setRowSelectionInterval(0, 0);
         this.tabla_producto.requestFocusInWindow();
      }

      if (this.modelo_producto.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo_producto.getRowCount())));
      }
   }

   public BuscadorProducto(final String tipo, LimiteTextFieldConSQL txt_cod_deposito) {
      this.txt_cod_deposito = txt_cod_deposito;
      this.setPreferredSize(new Dimension(1010, 590));
      this.pack();
      PanelPadre panel_buscador = new PanelPadre("Buscador de Productos");
      PanelPadre panel_precio = new PanelPadre("Lista de Precios");
      PanelPadre panel_stock = new PanelPadre("Stock Depositos");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      this.txt_buscador = new LimiteTextField(25);
      this.txt_buscador.setName("txt_buscador");
      String[] cabecera_producto = new String[]{"Codigo", "Nombre", "Sigla", "Costo", "Stock"};
      String[] cabecera_precio = new String[]{"Lista de Precio", "Moneda", "Precio", "Precio Minimo"};
      String[] cabecera_stock = new String[]{"Deposito", "Cantidad", "Reserva", "Transito"};
      this.modelo_producto = new ModeloTablaDefecto(cabecera_producto);
      this.tabla_producto = new TablaBuscador(this.modelo_producto, "AjusteStock");
      this.modelo_precio = new ModeloTablaDefecto(cabecera_precio);
      this.tabla_precio = new TablaBuscador(this.modelo_precio, "ListaPrecioProducto");
      this.modelo_stock = new ModeloTablaDefecto(cabecera_stock);
      this.tabla_stock = new TablaBuscador(this.modelo_stock, "StockProducto");
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla_producto);
      TablaScrollPane scrollPaneListaPrecio = new TablaScrollPane(this.tabla_precio);
      TablaScrollPane scrollPaneDepositos = new TablaScrollPane(this.tabla_stock);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_buscador, -2, 532, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(panel_stock, -1, -1, 32767)
                                    .addComponent(panel_precio, -1, 454, 32767)
                              )
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, 119, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addGap(41)
                              .addComponent(this.btnSeleccionar, -2, 151, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                        )
                  )
                  .addGap(38)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(panel_buscador, -2, 483, -2)
                        .addGroup(groupLayout.createSequentialGroup().addComponent(panel_precio, -2, 210, -2).addGap(3).addComponent(panel_stock, -2, 206, -2))
                  )
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
      GroupLayout gl_panel_stock = new GroupLayout(panel_stock);
      gl_panel_stock.setHorizontalGroup(gl_panel_stock.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneDepositos, -1, 348, 32767));
      gl_panel_stock.setVerticalGroup(
         gl_panel_stock.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_stock.createSequentialGroup().addComponent(scrollPaneDepositos, -2, 179, -2).addContainerGap(-1, 32767))
      );
      panel_stock.setLayout(gl_panel_stock);
      GroupLayout gl_panel_precio = new GroupLayout(panel_precio);
      gl_panel_precio.setHorizontalGroup(
         gl_panel_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_precio.createSequentialGroup().addComponent(scrollPaneListaPrecio, -2, 431, -2).addContainerGap(45, 32767))
      );
      gl_panel_precio.setVerticalGroup(
         gl_panel_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_precio.createSequentialGroup().addComponent(scrollPaneListaPrecio, -2, 184, -2).addContainerGap(-1, 32767))
      );
      panel_precio.setLayout(gl_panel_precio);
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
      this.addEscapeKey();
      ListSelectionModel modeloSeleccion = this.tabla_producto.getSelectionModel();
      modeloSeleccion.addListSelectionListener(
         new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
               if (!e.getValueIsAdjusting()) {
                  int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
                  if (filaSeleccionada != -1) {
                     BuscadorProductoE.buscarListaPrecios(
                        String.valueOf(
                           BuscadorProducto.this.tabla_producto
                              .getValueAt(filaSeleccionada, BuscadorProducto.this.tabla_producto.getColumn("Codigo").getModelIndex())
                              .toString()
                        ),
                        BuscadorProducto.this.tabla_precio,
                        BuscadorProducto.this.modelo_precio,
                        BuscadorProducto.this
                     );
                     BuscadorProductoE.buscarStockProducto(
                        String.valueOf(
                           BuscadorProducto.this.tabla_producto
                              .getValueAt(filaSeleccionada, BuscadorProducto.this.tabla_producto.getColumn("Codigo").getModelIndex())
                              .toString()
                        ),
                        BuscadorProducto.this.tabla_stock,
                        BuscadorProducto.this.modelo_stock,
                        BuscadorProducto.this
                     );
                  }
               }
            }
         }
      );
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
               BuscadorProducto.this.cargarTabla(tipo);
            }
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscadorProducto.this.seleccionLinea(filaSeleccionada, tipo);
            }
         }
      });
      this.btnSalir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorProducto.this.seleccionado = false;
            BuscadorProducto.this.dispose();
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
               int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
               BuscadorProducto.this.seleccionLinea(filaSeleccionada, tipo);
            }
         }
      });
   }

   private void seleccionLinea(int filaSeleccionada, String tipo) {
      this.codigo = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Codigo").getModelIndex()).toString();
      this.nombre = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Nombre").getModelIndex()).toString();
      this.sigla = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Sigla").getModelIndex()).toString();
      this.costo_producto = Double.parseDouble(
         this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Costo").getModelIndex()).toString()
      );
      this.stock = Double.parseDouble(this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Stock").getModelIndex()).toString());
      this.seleccionado = true;
      this.dispose();
   }

   private void cargarTabla(String tipo) {
      if (this.modelo_producto.getRowCount() - 1 >= 0) {
         while (this.modelo_producto.getRowCount() > 0) {
            this.modelo_producto.removeRow(0);
         }
      }

      BuscadorProductoE.buscarBuscadorAjusteStock(
         Integer.parseInt(this.txt_cod_deposito.getText()), this.txt_buscador.getText(), this.tabla_producto, this.modelo_producto, this
      );
      if (this.tabla_producto.getRowCount() > 0) {
         this.tabla_producto.setRowSelectionInterval(0, 0);
         this.tabla_producto.requestFocusInWindow();
      }

      if (this.modelo_producto.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo_producto.getRowCount())));
      }
   }

   public BuscadorProducto() {
      this.setPreferredSize(new Dimension(1010, 590));
      this.pack();
      PanelPadre panel_buscador = new PanelPadre("Buscador de Productos");
      PanelPadre panel_precio = new PanelPadre("Lista de Precios");
      PanelPadre panel_stock = new PanelPadre("Stock Depositos");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      this.txt_buscador = new LimiteTextField(25);
      this.txt_buscador.setName("txt_buscador");
      String[] cabecera_producto = new String[]{"Codigo", "Nombre", "Sigla", "Costo", "Stock"};
      String[] cabecera_precio = new String[]{"Lista de Precio", "Moneda", "Precio", "Precio Minimo"};
      String[] cabecera_stock = new String[]{"Deposito", "Cantidad", "Reserva", "Transito"};
      this.modelo_producto = new ModeloTablaDefecto(cabecera_producto);
      this.tabla_producto = new TablaBuscador(this.modelo_producto, "AjusteStock");
      this.modelo_precio = new ModeloTablaDefecto(cabecera_precio);
      this.tabla_precio = new TablaBuscador(this.modelo_precio, "ListaPrecioProducto");
      this.modelo_stock = new ModeloTablaDefecto(cabecera_stock);
      this.tabla_stock = new TablaBuscador(this.modelo_stock, "StockProducto");
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla_producto);
      TablaScrollPane scrollPaneListaPrecio = new TablaScrollPane(this.tabla_precio);
      TablaScrollPane scrollPaneDepositos = new TablaScrollPane(this.tabla_stock);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_buscador, -2, 532, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(panel_stock, -1, -1, 32767)
                                    .addComponent(panel_precio, -1, 454, 32767)
                              )
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, 119, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addGap(41)
                              .addComponent(this.btnSeleccionar, -2, 151, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                        )
                  )
                  .addGap(38)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(panel_buscador, -2, 483, -2)
                        .addGroup(groupLayout.createSequentialGroup().addComponent(panel_precio, -2, 210, -2).addGap(3).addComponent(panel_stock, -2, 206, -2))
                  )
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
      GroupLayout gl_panel_stock = new GroupLayout(panel_stock);
      gl_panel_stock.setHorizontalGroup(gl_panel_stock.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneDepositos, -1, 348, 32767));
      gl_panel_stock.setVerticalGroup(
         gl_panel_stock.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_stock.createSequentialGroup().addComponent(scrollPaneDepositos, -2, 179, -2).addContainerGap(-1, 32767))
      );
      panel_stock.setLayout(gl_panel_stock);
      GroupLayout gl_panel_precio = new GroupLayout(panel_precio);
      gl_panel_precio.setHorizontalGroup(
         gl_panel_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_precio.createSequentialGroup().addComponent(scrollPaneListaPrecio, -2, 431, -2).addContainerGap(45, 32767))
      );
      gl_panel_precio.setVerticalGroup(
         gl_panel_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_precio.createSequentialGroup().addComponent(scrollPaneListaPrecio, -2, 184, -2).addContainerGap(-1, 32767))
      );
      panel_precio.setLayout(gl_panel_precio);
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
      this.addEscapeKey();
      ListSelectionModel modeloSeleccion = this.tabla_producto.getSelectionModel();
      modeloSeleccion.addListSelectionListener(
         new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
               if (!e.getValueIsAdjusting()) {
                  int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
                  if (filaSeleccionada != -1) {
                     BuscadorProductoE.buscarListaPrecios(
                        String.valueOf(
                           BuscadorProducto.this.tabla_producto
                              .getValueAt(filaSeleccionada, BuscadorProducto.this.tabla_producto.getColumn("Codigo").getModelIndex())
                              .toString()
                        ),
                        BuscadorProducto.this.tabla_precio,
                        BuscadorProducto.this.modelo_precio,
                        BuscadorProducto.this
                     );
                     BuscadorProductoE.buscarStockProducto(
                        String.valueOf(
                           BuscadorProducto.this.tabla_producto
                              .getValueAt(filaSeleccionada, BuscadorProducto.this.tabla_producto.getColumn("Codigo").getModelIndex())
                              .toString()
                        ),
                        BuscadorProducto.this.tabla_stock,
                        BuscadorProducto.this.modelo_stock,
                        BuscadorProducto.this
                     );
                  }
               }
            }
         }
      );
      this.txt_buscador.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadorProducto.this.cargarTabla(true);
            }
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscadorProducto.this.seleccionLinea(filaSeleccionada, true);
            }
         }
      });
      this.btnSalir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorProducto.this.seleccionado = false;
            BuscadorProducto.this.dispose();
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
               BuscadorProducto.this.abrirFichaProducto();
            }
         }
      });
   }

   private void seleccionLinea(int filaSeleccionada, boolean tipo) {
      this.codigo = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Codigo").getModelIndex()).toString();
      this.nombre = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Nombre").getModelIndex()).toString();
      this.sigla = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("Sigla").getModelIndex()).toString();
      this.seleccionado = true;
      this.dispose();
   }

   private void cargarTabla(boolean tipo) {
      if (this.modelo_producto.getRowCount() - 1 >= 0) {
         while (this.modelo_producto.getRowCount() > 0) {
            this.modelo_producto.removeRow(0);
         }
      }

      BuscadorProductoE.buscarBuscadorBasicosinCosto(this.txt_buscador.getText(), this.tabla_producto, this.modelo_producto, this.frame);
      if (this.tabla_producto.getRowCount() > 0) {
         this.tabla_producto.setRowSelectionInterval(0, 0);
         this.tabla_producto.requestFocusInWindow();
      }

      if (this.modelo_producto.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo_producto.getRowCount())));
      }
   }

   public BuscadorProducto(boolean tipo) {
      this.setPreferredSize(new Dimension(1010, 590));
      this.pack();
      PanelPadre panel_buscador = new PanelPadre("Buscador de Productos");
      PanelPadre panel_precio = new PanelPadre("Lista de Precios");
      PanelPadre panel_stock = new PanelPadre("Stock Depositos1");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      this.txt_buscador = new LimiteTextField(25);
      this.txt_buscador.setName("txt_buscador");
      String[] cabecera_producto = new String[]{"Codigo", "Nombre", "TF", "IVA", "REGIMEN", "GRAVADO", "UM"};
      String[] cabecera_precio = new String[]{"Lista de Precio", "Moneda", "Precio", "Precio Minimo"};
      String[] cabecera_stock = new String[]{"Deposito", "Cantidad", "Reserva", "Transito"};
      this.modelo_producto = new ModeloTablaDefecto(cabecera_producto);
      this.tabla_producto = new TablaBuscador(this.modelo_producto, "BuscadorProducto");
      this.modelo_precio = new ModeloTablaDefecto(cabecera_precio);
      this.tabla_precio = new TablaBuscador(this.modelo_precio, "ListaPrecioProducto");
      this.modelo_stock = new ModeloTablaDefecto(cabecera_stock);
      this.tabla_stock = new TablaBuscador(this.modelo_stock, "StockProducto");
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla_producto);
      TablaScrollPane scrollPaneListaPrecio = new TablaScrollPane(this.tabla_precio);
      TablaScrollPane scrollPaneDepositos = new TablaScrollPane(this.tabla_stock);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_buscador, -2, 532, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(panel_stock, -2, 454, -2)
                                    .addComponent(panel_precio, -2, 454, -2)
                              )
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, 119, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addGap(41)
                              .addComponent(this.btnSeleccionar, -2, 151, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                        )
                  )
                  .addGap(38)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(panel_buscador, -2, 483, -2)
                        .addGroup(groupLayout.createSequentialGroup().addComponent(panel_precio, -2, 210, -2).addGap(3).addComponent(panel_stock, -2, 270, -2))
                  )
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
      GroupLayout gl_panel_stock = new GroupLayout(panel_stock);
      gl_panel_stock.setHorizontalGroup(gl_panel_stock.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneDepositos, -1, 348, 32767));
      gl_panel_stock.setVerticalGroup(
         gl_panel_stock.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_stock.createSequentialGroup().addComponent(scrollPaneDepositos, -2, 179, -2).addContainerGap(-1, 32767))
      );
      panel_stock.setLayout(gl_panel_stock);
      GroupLayout gl_panel_precio = new GroupLayout(panel_precio);
      gl_panel_precio.setHorizontalGroup(
         gl_panel_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_precio.createSequentialGroup().addComponent(scrollPaneListaPrecio, -2, 431, -2).addContainerGap(45, 32767))
      );
      gl_panel_precio.setVerticalGroup(
         gl_panel_precio.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_precio.createSequentialGroup().addComponent(scrollPaneListaPrecio, -2, 184, -2).addContainerGap(-1, 32767))
      );
      panel_precio.setLayout(gl_panel_precio);
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
      modeloSeleccion.addListSelectionListener(
         new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
               if (!e.getValueIsAdjusting()) {
                  int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
                  if (filaSeleccionada != -1) {
                     BuscadorProductoE.buscarListaPrecios(
                        String.valueOf(
                           BuscadorProducto.this.tabla_producto
                              .getValueAt(filaSeleccionada, BuscadorProducto.this.tabla_producto.getColumn("Codigo").getModelIndex())
                              .toString()
                        ),
                        BuscadorProducto.this.tabla_precio,
                        BuscadorProducto.this.modelo_precio,
                        BuscadorProducto.this
                     );
                     BuscadorProductoE.buscarStockProducto(
                        String.valueOf(
                           BuscadorProducto.this.tabla_producto
                              .getValueAt(filaSeleccionada, BuscadorProducto.this.tabla_producto.getColumn("Codigo").getModelIndex())
                              .toString()
                        ),
                        BuscadorProducto.this.tabla_stock,
                        BuscadorProducto.this.modelo_stock,
                        BuscadorProducto.this
                     );
                  }
               }
            }
         }
      );
      this.addEscapeKey();
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
               BuscadorProducto.this.abrirFichaProducto();
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
               BuscadorProducto.this.cargarTabla();
            }
         }
      });
      this.btnSeleccionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
            if (filaSeleccionada != -1) {
               BuscadorProducto.this.seleccionLinea(filaSeleccionada);
            }
         }
      });
      this.btnSalir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorProducto.this.seleccionado = false;
            BuscadorProducto.this.dispose();
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
               int filaSeleccionada = BuscadorProducto.this.tabla_producto.getSelectedRow();
               BuscadorProducto.this.seleccionLinea(filaSeleccionada);
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

      String sql = "";
      this.txt_buscador.getText().equals("");
      System.out.println("POLLO1: " + sql);
      BuscadorProductoE.cargarProductosTablaFiscal(this.txt_buscador.getText(), this.tabla_producto, this.modelo_producto, this.frame);
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
      this.v_tipoFiscal = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("TF").getModelIndex()).toString();
      this.v_iva = Double.parseDouble(this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("IVA").getModelIndex()).toString());
      this.v_regimen = Double.parseDouble(this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("REGIMEN").getModelIndex()).toString());
      this.v_porcentaje_gravado = Double.parseDouble(
         this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("GRAVADO").getModelIndex()).toString()
      );
      this.v_unidad_medida = this.tabla_producto.getValueAt(filaSeleccionada, this.tabla_producto.getColumn("UM").getModelIndex()).toString();
      this.seleccionado = true;
      this.dispose();
   }

   private void abrirFichaProducto() {
      this.dispose();
      ProductosForm producto = new ProductosForm();
      producto.buscar(this.tabla_producto.getValueAt(this.tabla_producto.getSelectedRow(), this.tabla_producto.getColumn("Codigo").getModelIndex()).toString());
      Principal.visualizarVentana(producto, "producto");
   }

   private void addEscapeKey() {
      KeyStroke escape = KeyStroke.getKeyStroke(27, 0, false);
      Action escapeAction = new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorProducto.this.seleccionado = false;
            BuscadorProducto.this.dispose();
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

   public String getV_tipoFiscal() {
      return this.v_tipoFiscal;
   }

   public String getV_descripcion() {
      return this.v_descripcion;
   }

   public String getV_unidad_medida() {
      return this.v_unidad_medida;
   }

   public double getV_iva() {
      return this.v_iva;
   }

   public double getV_regimen() {
      return this.v_regimen;
   }

   public double getCosto_producto() {
      return this.costo_producto;
   }

   public double getStock() {
      return this.stock;
   }

   public double getPrecio() {
      return this.precio;
   }

   public String getSigla() {
      return this.sigla;
   }

   public String getNombre_moneda() {
      return this.nombre_moneda;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public double getPrecio_minimo() {
      return this.precio_minimo;
   }

   public double getV_porcentaje_gravado() {
      return this.v_porcentaje_gravado;
   }
}
