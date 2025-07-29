package utilidades;

import a5Sucursales.SucursalesE;
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
import vendedores.VendedoresE;

public class LimiteTextFieldConSQLDialog extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private boolean focusEnabled = true;

   private void buscarRegistro(LabelPrincipal lblNombreCampo, JDialog frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         if (!this.getText().trim().equals("0")) {
            if (this.getName().trim().equals("Vendedores")) {
               VendedoresE b = VendedoresE.buscarVendedores2Dialog(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_vendedor());
                  lblNombreCampo.setText(b.getNombre_vendedor());
               }
            } else if (this.getName().trim().equals("Compradores")) {
               CompradoresE b = CompradoresE.buscarCompradores2Dialog(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_comprador());
                  lblNombreCampo.setText(b.getNombre_comprador());
               }
            } else if (this.getName().trim().equals("Cajeros")) {
               CajerosE b = CajerosE.buscarCajeros2Dialog(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_cajero());
                  lblNombreCampo.setText(b.getNombre_cajero());
               }
            } else if (this.getName().trim().equals("Sucursales")) {
               SucursalesE b = SucursalesE.buscarSucursales2Dialog(Integer.parseInt(this.getText().trim()), frame);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_sucursal());
                  lblNombreCampo.setText(b.getNombre_sucursal());
               }
            }
         }

         this.focusEnabled = true;
      }
   }

   public LimiteTextFieldConSQLDialog(int limite, final LabelPrincipal lblNombreCampo, String nombre, final JDialog frame) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setFont(new Font("Roboto", 0, 12));
      this.setBorder(new MinimalShadowBorder());
      this.setName(nombre);
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQLDialog.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            LimiteTextFieldConSQLDialog.this.buscarRegistro(lblNombreCampo, frame);
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
               LimiteTextFieldConSQLDialog.this.focusEnabled = false;
               if (LimiteTextFieldConSQLDialog.this.getName().trim().equals("Vendedores")) {
                  BuscadorDefectoDialog buscador = new BuscadorDefectoDialog(frame, LimiteTextFieldConSQLDialog.this.getName().trim());
                  buscador.setModal(true);
                  buscador.setLocationRelativeTo(frame);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLDialog.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQLDialog.this.getName().trim().equals("Compradores")) {
                  BuscadorDefectoDialog buscador = new BuscadorDefectoDialog(frame, LimiteTextFieldConSQLDialog.this.getName().trim());
                  buscador.setModal(true);
                  buscador.setLocationRelativeTo(frame);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLDialog.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQLDialog.this.getName().trim().equals("Cajeros")) {
                  BuscadorDefectoDialog buscador = new BuscadorDefectoDialog(frame, LimiteTextFieldConSQLDialog.this.getName().trim());
                  buscador.setModal(true);
                  buscador.setLocationRelativeTo(frame);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLDialog.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQLDialog.this.getName().trim().equals("Sucursales")) {
                  BuscadorDefectoDialog buscador = new BuscadorDefectoDialog(frame, LimiteTextFieldConSQLDialog.this.getName().trim());
                  buscador.setModal(true);
                  buscador.setLocationRelativeTo(frame);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLDialog.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               }

               LimiteTextFieldConSQLDialog.this.focusEnabled = true;
            }
         }
      });
   }
}
