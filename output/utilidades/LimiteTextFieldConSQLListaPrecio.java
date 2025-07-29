package utilidades;

import a0099ListaPrecios.ListaPreciosE;
import a009AjustePrecios.BuscadorListaPrecio;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFormattedTextField;
import utilidadesVentanas.JinternalPadre;

public class LimiteTextFieldConSQLListaPrecio extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private NumerosTextField txt_cod_moneda;
   private boolean focusEnabled = true;

   public void buscarRegistro(LabelPrincipal lblNombreCampo, NumerosTextField txt_cod_moneda, JinternalPadre frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         ListaPreciosE b = ListaPreciosE.buscarListaPrecio3(Integer.parseInt(this.getText().trim()), frame);
         if (b == null) {
            lblNombreCampo.setText("");
            this.setValue(0);
            this.requestFocusInWindow();
         } else {
            this.setValue(b.getCod_lista());
            lblNombreCampo.setText(b.getNombre_lista());
            txt_cod_moneda.setValue(b.getCod_moneda());
         }

         this.focusEnabled = true;
      }
   }

   public LimiteTextFieldConSQLListaPrecio(int limite, final LabelPrincipal lblNombreCampo, NumerosTextField tx_cod_moneda, final JinternalPadre frame) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setFont(new Font("Roboto", 0, 12));
      this.setBorder(new MinimalShadowBorder());
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQLListaPrecio.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            LimiteTextFieldConSQLListaPrecio.this.buscarRegistro(lblNombreCampo, LimiteTextFieldConSQLListaPrecio.this.txt_cod_moneda, frame);
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
               BuscadorListaPrecio buscador = new BuscadorListaPrecio(frame);
               buscador.setModal(true);
               buscador.setVisible(true);
               if (buscador.isSeleccionado()) {
                  LimiteTextFieldConSQLListaPrecio.this.setValue(buscador.getCodigo());
                  lblNombreCampo.setText(buscador.getNombre());
                  LimiteTextFieldConSQLListaPrecio.this.txt_cod_moneda.setValue(buscador.getCod_moneda());
               }

               LimiteTextFieldConSQLListaPrecio.this.focusEnabled = true;
            }
         }
      });
   }
}
