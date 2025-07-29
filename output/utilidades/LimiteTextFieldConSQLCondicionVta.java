package utilidades;

import a00CondicionesVentas.CondicionesVentasE;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFormattedTextField;
import utilidadesTabla.BuscadorPlazo;
import utilidadesVentanas.JinternalPadre;

public class LimiteTextFieldConSQLCondicionVta extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private boolean focusEnabled = true;

   private void buscarRegistro(LabelPrincipal lblNombreCampo, NumerosTextField dias, JinternalPadre frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         CondicionesVentasE b = CondicionesVentasE.buscarCondicionVta3(Integer.parseInt(this.getText().trim()), frame);
         if (b == null) {
            lblNombreCampo.setText("");
            this.setValue(0);
            this.requestFocusInWindow();
         } else {
            this.setValue(b.getCod_condicion());
            lblNombreCampo.setText(b.getNombre_condicion());
            dias.setText(String.valueOf(b.getCuotas()));
         }

         this.focusEnabled = true;
      }
   }

   public LimiteTextFieldConSQLCondicionVta(int limite, final LabelPrincipal lblNombreCampo, final NumerosTextField dias, final JinternalPadre frame) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setBorder(new MinimalShadowBorder());
      this.setFont(new Font("Roboto", 0, 12));
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQLCondicionVta.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQLCondicionVta.this.focusEnabled) {
               LimiteTextFieldConSQLCondicionVta.this.buscarRegistro(lblNombreCampo, dias, frame);
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
               LimiteTextFieldConSQLCondicionVta.this.focusEnabled = false;
               BuscadorPlazo buscador = new BuscadorPlazo(frame);
               buscador.setModal(true);
               buscador.setLocationRelativeTo(frame);
               buscador.setVisible(true);
               if (buscador.isSeleccionado()) {
                  LimiteTextFieldConSQLCondicionVta.this.setValue(buscador.getCodigo());
                  lblNombreCampo.setText(buscador.getNombre());
                  dias.setText(String.valueOf(buscador.getDias()));
               }

               LimiteTextFieldConSQLCondicionVta.this.focusEnabled = true;
            }
         }
      });
   }
}
