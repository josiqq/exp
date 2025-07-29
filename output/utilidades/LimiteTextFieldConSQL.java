package utilidades;

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
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import utilidadesTabla.BuscadorDefecto1;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadreString;
import vendedores.VendedoresE;

public class LimiteTextFieldConSQL extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private boolean focusEnabled = true;

   private void buscarRegistro(LabelPrincipal lblNombreCampo, JinternalPadreString frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         if (!this.getText().trim().equals("0")) {
            if (this.getName().trim().equals("UnidadMedida")) {
               UnidadesMedidasE b = UnidadesMedidasE.buscarUnidadesMedida2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_unidad_medida());
                  lblNombreCampo.setText(b.getNombre_unidad_medida());
               }
            } else if (this.getName().trim().equals("Marcas")) {
               MarcasE b = MarcasE.buscarMarca2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_marca());
                  lblNombreCampo.setText(b.getNombre_marca());
               }
            } else if (this.getName().trim().equals("Proveedores")) {
               ProveedoresE b = ProveedoresE.buscarProveedores2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_proveedor());
                  lblNombreCampo.setText(b.getNombre_proveedor());
               }
            }
         }

         this.focusEnabled = true;
      }
   }

   private void buscarRegistro(LabelPrincipal lblNombreCampo, JinternalPadre frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         if (!this.getText().trim().equals("0")) {
            if (this.getName().trim().equals("Vendedores")) {
               VendedoresE b = VendedoresE.buscarVendedores2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_vendedor());
                  lblNombreCampo.setText(b.getNombre_vendedor());
               }
            } else if (this.getName().trim().equals("Cajeros")) {
               CajerosE b = CajerosE.buscarCajeros2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_cajero());
                  lblNombreCampo.setText(b.getNombre_cajero());
               }
            } else if (this.getName().trim().equals("Compradores")) {
               CompradoresE b = CompradoresE.buscarCompradores2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_comprador());
                  lblNombreCampo.setText(b.getNombre_comprador());
               }
            } else if (this.getName().trim().equals("Monedas")) {
               MonedasE b = MonedasE.buscarMonedas2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_moneda());
                  lblNombreCampo.setText(b.getNombre_moneda());
               }
            } else if (this.getName().trim().equals("Sucursales")) {
               SucursalesE b = SucursalesE.buscarSucursales2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_sucursal());
                  lblNombreCampo.setText(b.getNombre_sucursal());
               }
            } else if (this.getName().trim().equals("Paises")) {
               PaisesE b = PaisesE.buscarPaises2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_pais());
                  lblNombreCampo.setText(b.getNombre_pais());
               }
            } else if (this.getName().trim().equals("Rubros")) {
               RubrosE b = RubrosE.buscarRubros2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_rubro());
                  lblNombreCampo.setText(b.getNombre_rubro());
               }
            } else if (this.getName().trim().equals("Ciudades")) {
               CiudadesE b = CiudadesE.buscarCiudades2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_ciudad());
                  lblNombreCampo.setText(b.getNombre_ciudad());
               }
            } else if (this.getName().trim().equals("LugarVenta")) {
               VentasLugaresE b = VentasLugaresE.buscarLugarVta2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_lugar());
                  lblNombreCampo.setText(b.getNombre_lugar());
               }
            } else if (this.getName().trim().equals("Bancos")) {
               BancosE b = BancosE.buscarBancos2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_banco());
                  lblNombreCampo.setText(b.getNombre_banco());
               }
            } else if (this.getName().trim().equals("CategoriasCli")) {
               CategoriaClienteE b = CategoriaClienteE.buscarCategoriaCliente2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_categoria());
                  lblNombreCampo.setText(b.getNombre_categoria());
               }
            } else if (this.getName().trim().equals("UnidadMedida")) {
               UnidadesMedidasE b = UnidadesMedidasE.buscarUnidadesMedida2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_unidad_medida());
                  lblNombreCampo.setText(b.getNombre_unidad_medida());
               }
            } else if (this.getName().trim().equals("Marcas")) {
               MarcasE b = MarcasE.buscarMarca2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_marca());
                  lblNombreCampo.setText(b.getNombre_marca());
               }
            } else if (this.getName().trim().equals("Proveedores")) {
               ProveedoresE b = ProveedoresE.buscarProveedores2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_proveedor());
                  lblNombreCampo.setText(b.getNombre_proveedor());
               }
            } else if (this.getName().trim().equals("TipoDocumentos")) {
               TipoDocumentosE b = TipoDocumentosE.buscarTipodocumentos2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_tipo_documento());
                  lblNombreCampo.setText(b.getNombre_tipo_documento());
               }
            } else if (this.getName().trim().equals("Plazos")) {
               PlazosE b = PlazosE.buscarPlazos2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_plazo());
                  lblNombreCampo.setText(b.getNombre_plazo());
               }
            } else if (this.getName().trim().equals("Depositos")) {
               DepositosE b = DepositosE.buscarDeposito2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_deposito());
                  lblNombreCampo.setText(b.getNombre_deposito());
               }
            } else if (this.getName().trim().equals("ListaPrecios")) {
               ListaPreciosE b = ListaPreciosE.buscarListaPrecio2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_lista());
                  lblNombreCampo.setText(b.getNombre_lista());
               }
            } else if (this.getName().trim().equals("MotivosAjuste")) {
               AjusteMotivosE b = AjusteMotivosE.buscarMotivosAjuste2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_motivo());
                  lblNombreCampo.setText(b.getNombre_motivo());
               }
            } else if (this.getName().trim().equals("CondicionVtas")) {
               CondicionesVentasE b = CondicionesVentasE.buscarCondicionVta2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_condicion());
                  lblNombreCampo.setText(b.getNombre_condicion());
               }
            } else if (this.getName().trim().equals("MotivoCuenta")) {
               MotivosE b = MotivosE.buscarMotivo2(
                  Integer.parseInt(this.getText().trim()), "Select cod_motivo,nombre_motivo from motivos where cod_motivo =? and tesoreria =1", frame
               );
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_motivo());
                  lblNombreCampo.setText(b.getNombre_motivo());
               }
            } else if (this.getName().trim().equals("CuentaTesoreria")) {
               CuentasE b = CuentasE.buscarCuentas2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_cuenta());
                  lblNombreCampo.setText(b.getNombre_cuenta());
               }
            } else if (this.getName().trim().equals("MotivoGasto")) {
               MotivosE b = MotivosE.buscarMotivo2(
                  Integer.parseInt(this.getText().trim()), "Select cod_motivo,nombre_motivo from motivos where cod_motivo =? and gastos =1", frame
               );
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_motivo());
                  lblNombreCampo.setText(b.getNombre_motivo());
               }
            } else if (this.getName().trim().equals("Productos")) {
               ProductosE b = ProductosE.buscarProductos2(this.getText(), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_producto());
                  lblNombreCampo.setText(b.getNombre_producto());
               }
            } else if (this.getName().trim().equals("MotivosNC")) {
               MotivosNCE b = MotivosNCE.buscarMotivoNC2(Integer.parseInt(this.getText()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_motivo());
                  lblNombreCampo.setText(b.getNombre_motivo());
               }
            }
         }
      }
   }

   public LimiteTextFieldConSQL(int limite, final LabelPrincipal lblNombreCampo, String nombre, final JinternalPadreString frame) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setBorder(new MinimalShadowBorder());
      this.setName(nombre);
      this.setFont(new Font("Roboto", 0, 12));
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQL.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQL.this.focusEnabled) {
               LimiteTextFieldConSQL.this.buscarRegistro(lblNombreCampo, frame);
            }
         }
      });
      this.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyReleased(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 114) {
               LimiteTextFieldConSQL.this.focusEnabled = false;
               if (LimiteTextFieldConSQL.this.getName().trim().equals("UnidadMedida")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("UnidadMedida");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Marcas")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Marcas");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Proveedores")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Proveedores");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               }

               LimiteTextFieldConSQL.this.focusEnabled = true;
            }
         }
      });
   }

   public LimiteTextFieldConSQL(int limite, final LabelPrincipal lblNombreCampo, String nombre, final JinternalPadre frame) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setBorder(new MinimalShadowBorder());
      this.setName(nombre);
      this.setFont(new Font("Roboto", 0, 12));
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQL.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQL.this.focusEnabled) {
               LimiteTextFieldConSQL.this.buscarRegistro(lblNombreCampo, frame);
            }
         }
      });
      this.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyReleased(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 114) {
               LimiteTextFieldConSQL.this.focusEnabled = false;
               if (LimiteTextFieldConSQL.this.getName().trim().equals("Vendedores")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Vendedores");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Cajeros")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Cajeros");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Compradores")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Compradores");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Monedas")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Monedas");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Sucursales")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Sucursales");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Paises")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Paises");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Rubros")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Rubros");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Ciudades")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Ciudades");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("LugarVenta")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("LugarVenta");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Bancos")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Bancos");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("CategoriasCli")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("CategoriasCli");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("UnidadMedida")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("UnidadMedida");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Marcas")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Marcas");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Proveedores")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Proveedores");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("TipoDocumentos")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("TipoDocumentos");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Plazos")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Proveedores");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Depositos")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Depositos");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("ListaPrecios")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("ListaPrecios");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("MotivosAjuste")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("MotivosAjuste");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("CondicionVtas")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("CondicionVtas");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("MotivoCuenta")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("MotivoCuenta");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("CuentaTesoreria")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("CuentaTesoreria");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("MotivoGasto")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("MotivoGasto");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Productos")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Productos");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("MotivosNC")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("MotivosNC");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               }

               LimiteTextFieldConSQL.this.focusEnabled = true;
            }
         }
      });
   }

   public LimiteTextFieldConSQL(int limite, final LabelPrincipal lblNombreCampo, String nombre, final JDialog frame) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setBorder(new MinimalShadowBorder());
      this.setName(nombre);
      this.setFont(new Font("Roboto", 0, 12));
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQL.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQL.this.focusEnabled) {
               LimiteTextFieldConSQL.this.buscarRegistro(lblNombreCampo, frame);
            }
         }
      });
      this.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyReleased(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 114) {
               LimiteTextFieldConSQL.this.focusEnabled = false;
               if (LimiteTextFieldConSQL.this.getName().trim().equals("Monedas")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Monedas");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQL.this.getName().trim().equals("Clientes")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Clientes");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQL.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               }

               LimiteTextFieldConSQL.this.focusEnabled = true;
            }
         }
      });
   }

   private void buscarRegistro(LabelPrincipal lblNombreCampo, JDialog frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         if (!this.getText().trim().equals("0")) {
            if (this.getName().trim().equals("Monedas")) {
               MonedasE b = MonedasE.buscarMonedas2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_moneda());
                  lblNombreCampo.setText(b.getNombre_moneda());
               }
            } else if (this.getName().trim().equals("Clientes")) {
               ClientesE b = ClientesE.buscarCliente2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_cliente());
                  lblNombreCampo.setText(b.getNombre_cliente());
               }
            }
         }

         this.focusEnabled = true;
      }
   }
}
