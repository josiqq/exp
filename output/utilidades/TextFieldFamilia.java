package utilidades;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.NumberFormatter;
import utilidadesTabla.BuscadorFamilia;

public class TextFieldFamilia extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private long limite;

   public TextFieldFamilia(
      long limite,
      final LabelPrincipal nombre_seccion,
      final LimiteTextField txt_cod_sub_seccion,
      final LabelPrincipal nombre_sub_seccion,
      final LimiteTextField txt_cod_grupo,
      final LabelPrincipal nombre_grupo
   ) {
      super((AbstractFormatter)createNumberFormatter(limite));
      this.setLimite(limite);
      this.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            TextFieldFamilia.this.processDigitDeletion();
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 114) {
               BuscadorFamilia buscador = new BuscadorFamilia();
               buscador.setLocationRelativeTo(null);
               buscador.setModal(true);
               buscador.setVisible(true);
               if (buscador.isSeleccionado()) {
                  TextFieldFamilia.this.setValue(buscador.getCod_seccion());
                  nombre_seccion.setText(buscador.getNombre_seccion());
                  txt_cod_sub_seccion.setValue(buscador.getCod_sub_seccion());
                  nombre_sub_seccion.setText(buscador.getNombre_sub_seccion());
                  txt_cod_grupo.setValue(buscador.getCod_grupo());
                  nombre_grupo.setText(buscador.getNombre_grupo());
               } else {
                  TextFieldFamilia.this.setValue(0);
                  nombre_seccion.setText("");
                  txt_cod_sub_seccion.setValue(0);
                  nombre_sub_seccion.setText("");
                  txt_cod_grupo.setValue(0);
                  nombre_grupo.setText("");
               }
            }
         }
      });
   }

   private static NumberFormatter createNumberFormatter(long limite) {
      NumberFormat format = DecimalFormat.getInstance();
      NumberFormatter formatter = new NumberFormatter(format);
      formatter.setValueClass(Long.class);
      formatter.setMinimum(0L);
      formatter.setMaximum(limite);
      formatter.setAllowsInvalid(false);
      formatter.setCommitsOnValidEdit(true);
      return formatter;
   }

   private void processDigitDeletion() {
      if (this.getText().isEmpty()) {
         this.setValue(0L);
      }
   }

   public long getLimite() {
      return this.limite;
   }

   public void setLimite(long limite) {
      this.limite = limite;
   }
}
