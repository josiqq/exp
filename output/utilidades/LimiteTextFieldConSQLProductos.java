package utilidades;

import a00Productos.ProductosE;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;
import utilidadesTabla.BuscadorProductoSimple;
import utilidadesVentanas.JinternalPadreReporte;

public class LimiteTextFieldConSQLProductos extends JTextField {
   private static final long serialVersionUID = 1L;
   private boolean focusEnabled = true;

   private void buscarRegistro(LabelPrincipal lblNombreCampo, JinternalPadreReporte frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         if (!this.getText().trim().equals("0")) {
            ProductosE b = ProductosE.buscarProducto2(this.getText(), frame);
            if (b == null) {
               lblNombreCampo.setText("");
               this.setText("0");
               this.requestFocusInWindow();
            } else {
               lblNombreCampo.setText(b.getNombre_producto());
            }
         } else {
            lblNombreCampo.setText("");
         }
      }
   }

   public LimiteTextFieldConSQLProductos(final LabelPrincipal lblNombreCampo, final JinternalPadreReporte frame) {
      this.setFont(new Font("Roboto", 0, 12));
      this.setBorder(new MinimalShadowBorder());
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQLProductos.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQLProductos.this.focusEnabled) {
               LimiteTextFieldConSQLProductos.this.buscarRegistro(lblNombreCampo, frame);
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
               LimiteTextFieldConSQLProductos.this.focusEnabled = false;
               BuscadorProductoSimple buscador = new BuscadorProductoSimple(frame);
               buscador.setModal(true);
               buscador.setLocationRelativeTo(frame);
               buscador.setVisible(true);
               if (buscador.isSeleccionado()) {
                  LimiteTextFieldConSQLProductos.this.setText(buscador.getCodigo());
                  lblNombreCampo.setText(buscador.getNombre());
               }

               LimiteTextFieldConSQLProductos.this.focusEnabled = true;
            }
         }
      });
   }
}
