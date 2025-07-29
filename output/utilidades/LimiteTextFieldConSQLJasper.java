package utilidades;

import a009AjusteStockMotivo.AjusteMotivosE;
import a00Clientes.ClientesE;
import a6Depositos.DepositosE;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFormattedTextField;
import utilidadesTabla.BuscadorDefecto1;
import utilidadesVentanas.JInternalPadreReporteJasper;

public class LimiteTextFieldConSQLJasper extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private boolean focusEnabled = true;

   private void buscarRegistro(LabelPrincipal lblNombreCampo, JInternalPadreReporteJasper frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         if (!this.getText().trim().equals("0")) {
            if (this.getName().trim().equals("Depositos")) {
               DepositosE b = DepositosE.buscarDeposito2(Integer.parseInt(this.getText().trim()), null);
               if (b == null) {
                  lblNombreCampo.setText("");
                  this.setValue(0);
                  this.requestFocusInWindow();
               } else {
                  this.setValue(b.getCod_deposito());
                  lblNombreCampo.setText(b.getNombre_deposito());
               }
            } else if (this.getName().trim().equals("MotivosAjuste")) {
               AjusteMotivosE b = AjusteMotivosE.buscarMotivosAjuste2(Integer.parseInt(this.getText().trim()), null);
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

         this.focusEnabled = true;
      }
   }

   public LimiteTextFieldConSQLJasper(int limite, final LabelPrincipal lblNombreCampo, String nombre, final JInternalPadreReporteJasper frame) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setBorder(new MinimalShadowBorder());
      this.setName(nombre);
      this.setFont(new Font("Roboto", 0, 12));
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQLJasper.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQLJasper.this.focusEnabled) {
               LimiteTextFieldConSQLJasper.this.buscarRegistro(lblNombreCampo, frame);
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
               LimiteTextFieldConSQLJasper.this.focusEnabled = false;
               if (LimiteTextFieldConSQLJasper.this.getName().trim().equals("MotivosAjuste")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("MotivosAjuste");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLJasper.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQLJasper.this.getName().trim().equals("Depositos")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Depositos");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLJasper.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               } else if (LimiteTextFieldConSQLJasper.this.getName().trim().equals("Clientes")) {
                  BuscadorDefecto1 buscador = new BuscadorDefecto1("Clientes");
                  buscador.setLocationRelativeTo(frame);
                  buscador.setModal(true);
                  buscador.setVisible(true);
                  if (buscador.isSeleccionado()) {
                     LimiteTextFieldConSQLJasper.this.setValue(buscador.getCodigo());
                     lblNombreCampo.setText(buscador.getNombre());
                  }
               }
            }
         }
      });
   }
}
