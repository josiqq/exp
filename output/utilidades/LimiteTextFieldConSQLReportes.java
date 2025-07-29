package utilidades;

import A8Marcas.MarcasE;
import a009AjusteStockMotivo.AjusteMotivosE;
import a00Clientes.ClientesE;
import a00Productos.ProductosE;
import a6Depositos.DepositosE;
import a9Proveedores.ProveedoresE;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFormattedTextField;
import utilidadesTabla.BuscadorDefecto1;
import utilidadesVentanas.JinternalPadreReporte;

public class LimiteTextFieldConSQLReportes extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private boolean focusEnabled = true;

   private void buscarRegistro(LabelPrincipal lblNombreCampo, JinternalPadreReporte frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         if (!this.getText().trim().equals("0")) {
            if (this.getName().trim().equals("Proveedores")) {
               ProveedoresE b = ProveedoresE.buscarProveedores2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_proveedor());
                  lblNombreCampo.setText(b.getNombre_proveedor());
               }
            } else if (this.getName().trim().equals("Productos")) {
               ProductosE b = ProductosE.buscarProductos2(this.getText(), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setText(b.getCod_producto());
                  lblNombreCampo.setText(b.getNombre_producto());
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
            } else if (this.getName().trim().equals("Depositos")) {
               System.out.println("POLLO1");
               DepositosE b = DepositosE.buscarDeposito2(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_deposito());
                  lblNombreCampo.setText(b.getNombre_deposito());
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
            } else if (this.getName().trim().equals("Clientes")) {
               ClientesE b = ClientesE.buscarCliente2(Integer.parseInt(this.getText().trim()), null);
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
      }
   }

   public LimiteTextFieldConSQLReportes(int limite, final LabelPrincipal lblNombreCampo, String nombre, final JinternalPadreReporte frame) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setFont(new Font("Roboto", 0, 12));
      this.setBorder(new MinimalShadowBorder());
      this.setName(nombre);
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQLReportes.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQLReportes.this.focusEnabled) {
               LimiteTextFieldConSQLReportes.this.buscarRegistro(lblNombreCampo, frame);
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
               LimiteTextFieldConSQLReportes.this.focusEnabled = false;
               if (LimiteTextFieldConSQLReportes.this.getName().trim().equals("Proveedores")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Proveedores");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLReportes.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQLReportes.this.getName().trim().equals("Productos")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Productos");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLReportes.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQLReportes.this.getName().trim().equals("Marcas")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Marcas");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLReportes.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQLReportes.this.getName().trim().equals("Depositos")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Depositos");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLReportes.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQLReportes.this.getName().trim().equals("MotivosAjuste")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("MotivosAjuste");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLReportes.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQLReportes.this.getName().trim().equals("Clientes")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Clientes");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLReportes.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               }
            }
         }
      });
   }
}
