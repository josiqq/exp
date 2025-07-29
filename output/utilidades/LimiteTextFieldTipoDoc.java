package utilidades;

import a00TipoDocumentos.TipoDocumentosE;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFormattedTextField;
import utilidadesTabla.BuscadorTipoDoc;
import utilidadesVentanas.JinternalPadre;

public class LimiteTextFieldTipoDoc extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private boolean focusEnabled = true;

   private void buscarRegistro(LabelPrincipal lblNombreCampo, LabelPrincipal timbrado, LabelPrincipal numeracion, JinternalPadre frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         TipoDocumentosE b = TipoDocumentosE.buscarTipodocumentos4(Integer.parseInt(this.getText().trim()), frame);
         if (b == null) {
            lblNombreCampo.setText("");
            this.setValue(0);
            this.requestFocusInWindow();
         } else {
            this.setValue(b.getCod_tipo_documento());
            lblNombreCampo.setText(b.getNombre_tipo_documento());
            timbrado.setText(String.valueOf(b.getTimbrado()));
            numeracion.setText(String.valueOf(b.getNumeracion()));
         }

         this.focusEnabled = true;
      }
   }

   public LimiteTextFieldTipoDoc(
      int limite, final LabelPrincipal lblNombreCampo, final LabelPrincipal timbrado, final LabelPrincipal numeracion, final JinternalPadre frame
   ) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setFont(new Font("Roboto", 0, 12));
      this.setBorder(new MinimalShadowBorder());
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldTipoDoc.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldTipoDoc.this.focusEnabled) {
               LimiteTextFieldTipoDoc.this.buscarRegistro(lblNombreCampo, timbrado, numeracion, frame);
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
               LimiteTextFieldTipoDoc.this.focusEnabled = false;
               BuscadorTipoDoc buscador = new BuscadorTipoDoc(frame);
               buscador.setModal(true);
               buscador.setLocationRelativeTo(frame);
               buscador.setVisible(true);
               if (buscador.isSeleccionado()) {
                  LimiteTextFieldTipoDoc.this.setValue(buscador.getCodigo());
                  lblNombreCampo.setText(buscador.getNombre());
                  timbrado.setText(String.valueOf(buscador.getTimbrado()));
                  numeracion.setText(String.valueOf(buscador.getNumeracion()));
               }

               LimiteTextFieldTipoDoc.this.focusEnabled = true;
            }
         }
      });
   }
}
