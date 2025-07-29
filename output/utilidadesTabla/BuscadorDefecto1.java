package utilidadesTabla;

import A8Marcas.MarcasE;
import a0098MotivosNC.MotivosNCE;
import a0099ListaPrecios.ListaPreciosE;
import a0099Motivos.MotivosE;
import a0099VentasLugares.VentasLugaresE;
import a009AjusteStockMotivo.AjusteMotivosE;
import a00Bancos.BancosE;
import a00Clientes.ClientesE;
import a00CondicionesVentas.CondicionesVentasE;
import a00Cuentas.CuentasE;
import a00Plazos.PlazosE;
import a00Productos.ProductosE;
import a00TipoDocumentos.TipoDocumentosE;
import a11Monedas.MonedasE;
import a5Sucursales.SucursalesE;
import a6Depositos.DepositosE;
import a88UnidadesMedidas.UnidadesMedidasE;
import a99CategoriaCliente.CategoriaClienteE;
import a99Paises.CiudadesE;
import a99Paises.PaisesE;
import a99Rubros.RubrosE;
import a9Proveedores.ProveedoresE;
import cajeros.CajerosE;
import compradores.CompradoresE;
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
import vendedores.VendedoresE;

public class BuscadorDefecto1 extends JDialogBuscadores implements KeyListener, ActionListener {
   private static final long serialVersionUID = 1L;
   private final PanelPadre contentPanel = new PanelPadre("");
   private JFormattedTextField txt_buscador;
   private BotonPadre btnSeleccionar;
   private BotonPadre btnSalir;
   private int codigo;
   private String nombre;
   private boolean seleccionado = false;
   private ModeloTablaDefecto modelo;
   private TablaBuscador tabla;
   private LabelPrincipal lblLineasRecuperadasTexto;

   public BuscadorDefecto1(String titulo) {
      this.setName(titulo);
      this.setTitle("Buscador de " + titulo);
      this.setBounds(100, 100, 542, 453);
      this.getContentPane().setLayout(new BorderLayout());
      this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(this.contentPanel, "Center");
      PanelPadre panel = new PanelPadre("");
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
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla);
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
                              .addGap(38)
                              .addComponent(this.btnSeleccionar, -2, 156, -2)
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
      this.contentPanel.setLayout(gl_contentPanel);
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

         VendedoresE.cargarVendedoresTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Cajeros")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_cajero,nombre_cajero from cajeros where estado =1";
         } else {
            sql = "select cod_cajero,nombre_cajero from cajeros where estado = 1 and nombre_cajero like '%" + this.txt_buscador.getText() + "%'";
         }

         CajerosE.cargarCajerosTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Compradores")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_comprador,nombre_comprador from compradores where estado =1";
         } else {
            sql = "select cod_comprador,nombre_comprador from compradores where etado = 1 and nombre_ciomprador like '%" + this.txt_buscador.getText() + "%'";
         }

         CompradoresE.cargarCompradoresTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Monedas")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_moneda,nombre_moneda from monedas where estado =1";
         } else {
            sql = "select cod_moneda,nombre_moneda from monedas where estado = 1 and nombre_moneda like '%" + this.txt_buscador.getText() + "%'";
         }

         MonedasE.cargarMonedasTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Sucursales")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_sucursal,nombre_sucursal from sucursales where estado =1";
         } else {
            sql = "select cod_sucursal,nombre_sucursal from sucursales where estado = 1 and nombre_sucursal like '%" + this.txt_buscador.getText() + "%'";
         }

         SucursalesE.cargarSucursalesTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Paises")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_pais,nombre_pais from paises where estado =1";
         } else {
            sql = "select cod_pais,nombre_pais from paises where estado = 1 and nombre_pais like '%" + this.txt_buscador.getText() + "%'";
         }

         PaisesE.cargarPaisesTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Rubros")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_rubro,nombre_rubro from rubros where estado =1";
         } else {
            sql = "select cod_rubro,nombre_rubro from rubros where estado = 1 and nombre_rubro like '%" + this.txt_buscador.getText() + "%'";
         }

         RubrosE.cargarRubrosTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Ciudades")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_ciudad,nombre_ciudad from ciudades where estado =1";
         } else {
            sql = "select cod_ciudad,nombre_ciudad from ciudades where estado = 1 and nombre_ciudad like '%" + this.txt_buscador.getText() + "%'";
         }

         CiudadesE.cargarCiudadesTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("LugarVenta")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_lugar,nombre_lugar from lugares_ventas where estado =1";
         } else {
            sql = "select cod_lugar,nombre_lugar from lugares_ventas where estado = 1 and nombre_lugar like '%" + this.txt_buscador.getText() + "%'";
         }

         VentasLugaresE.cargarLugaresVtasTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Bancos")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_banco,nombre_banco from bancos where estado =1";
         } else {
            sql = "select cod_banco,nombre_banco from bancos where estado =1 and nombre_banco like '%" + this.txt_buscador.getText() + "%'";
         }

         BancosE.cargarBancosTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("CategoriasCli")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_categoria,nombre_categoria from clientes_categoria where estado =1";
         } else {
            sql = "select cod_categoria,nombre_categoria from clientes_categoria where estado =1 and nombre_categoria like '%"
               + this.txt_buscador.getText()
               + "%'";
         }

         CategoriaClienteE.cargarCategoriaClienteTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("UnidadMedida")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_unidad_medida,nombre_unidad_medida from unidades_medidas where estado =1";
         } else {
            sql = "select cod_unidad_medida,nombre_unidad_medida from unidades_medidas where estado =1 and nombre_unidad_medida like '%"
               + this.txt_buscador.getText()
               + "%'";
         }

         UnidadesMedidasE.cargarUnidadesMedidasTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Marcas")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_marca,nombre_marca from marcas where estado =1";
         } else {
            sql = "select cod_marca,nombre_marca from marcas where estado =1 and nombre_marca like '%" + this.txt_buscador.getText() + "%'";
         }

         MarcasE.cargarMarcasTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Proveedores")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_proveedor,nombre_proveedor from proveedores where estado =1";
         } else {
            sql = "select cod_proveedor,nombre_proveedor from proveedores where estado =1 and nombre_proveedor like '%" + this.txt_buscador.getText() + "%'";
         }

         ProveedoresE.cargarProveedoresTabla2(sql, this.modelo, this);
      } else if (this.getName().trim().equals("TipoDocumentos")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_tipo_documento,nombre_tipo_documento from tipo_documentos where estado =1";
         } else {
            sql = "select cod_tipo_documento,nombre_tipo_documento from tipo_documentos where estado =1 and nombre_tipo_documento like '%"
               + this.txt_buscador.getText()
               + "%'";
         }

         TipoDocumentosE.cargarTipoDocumentosTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Plazos")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_plazo,nombre_plazo from plazos where estado =1";
         } else {
            sql = "select cod_plazo,nombre_plazo from plazos where estado =1 and nombre_plazo like '%" + this.txt_buscador.getText() + "%'";
         }

         PlazosE.cargarPlazoTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Depositos")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_deposito,nombre_deposito from depositos where estado =1";
         } else {
            sql = "select cod_deposito,nombre_deposito from depositos where estado =1 and nombre_deposito like '%" + this.txt_buscador.getText() + "%'";
         }

         DepositosE.cargarDepositoTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("ListaPrecios")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_lista,nombre_lista from lista_precios_cab where estado =1";
         } else {
            sql = "select cod_lista,nombre_lista from lista_precios_cab where estado =1 and nombre_lista like '%" + this.txt_buscador.getText() + "%'";
         }

         ListaPreciosE.cargarVendedoresTabla2(sql, this.modelo, this);
      } else if (this.getName().trim().equals("MotivosAjuste")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_motivo,nombre_motivo from ajuste_stock_motivos where estado =1";
         } else {
            sql = "select cod_motivo,nombre_motivo from ajuste_stock_motivos where estado =1 and nombre_motivo like '%" + this.txt_buscador.getText() + "%'";
         }

         AjusteMotivosE.cargarMotivosAjusteTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("CondicionVtas")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_condicion,nombre_condicion from condiciones_ventas where estado =1";
         } else {
            sql = "select cod_condicion,nombre_condicion from condiciones_ventas where estado =1 and nombre_condicion like '%"
               + this.txt_buscador.getText()
               + "%'";
         }

         CondicionesVentasE.cargarCondicionesVentasTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("MotivoCuenta")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_motivo,nombre_motivo from motivos where tesoreria =1 and estado =1";
         } else {
            sql = "select cod_motivo,nombre_motivo from motivos where tesoreria =1 and estado =1 and nombre_motivo like '%"
               + this.txt_buscador.getText()
               + "%'";
         }

         MotivosE.cargarMotivosTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("CuentaTesoreria")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_cuenta,nombre_cuenta from cuentas where estado =1";
         } else {
            sql = "select cod_cuenta,nombre_cuenta from cuentas where estado =1 and nombre_cuenta like '%" + this.txt_buscador.getText() + "%'";
         }

         CuentasE.cargarCuentasTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("MotivoGasto")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_motivo,nombre_motivo from motivos where gastos =1 and estado =1";
         } else {
            sql = "select cod_motivo,nombre_motivo from motivos where gastos =1 and estado =1 and nombre_motivo like '%" + this.txt_buscador.getText() + "%'";
         }

         MotivosE.cargarMotivosTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Productos")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_producto,nombre_producto from productos where estado =1";
         } else {
            sql = "select cod_producto,nombre_producto from productos where estado =1 and productos.nombre_producto like '%"
               + this.txt_buscador.getText()
               + "%'";
         }

         ProductosE.cargarProductosTabla2(sql, this.modelo, this);
      } else if (this.getName().trim().equals("MotivosNC")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_motivo,nombre_motivo from motivos_nc where estado =1";
         } else {
            sql = "select cod_motivo,nombre_motivo from motivos_nc where estado =1 and motivos_nc.nombre_motivo like '%" + this.txt_buscador.getText() + "%'";
         }

         MotivosNCE.cargarMotivosNCTabla(sql, this.modelo, this);
      } else if (this.getName().trim().equals("Clientes")) {
         String sql = "";
         if (this.txt_buscador.getText().equals("")) {
            sql = "select cod_cliente,nombre_cliente from clientes where estado =1";
         } else {
            sql = "select cod_cliente,nombre_cliente from clientes where estado =1 and clientes.nombre_cliente like '%" + this.txt_buscador.getText() + "%'";
         }

         ClientesE.cargarClientesTabla2(sql, this.modelo, null);
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
