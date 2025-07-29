package utilidades;

import a9Proveedores.ProveedoresE;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFormattedTextField;
import utilidadesTabla.BuscadorProveedor;
import utilidadesVentanas.JinternalPadre;

public class LimiteTextFieldConSQLProveedores extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private boolean focusEnabled = true;

   private void buscarRegistro(
      LimiteTextField txt_nombre_proveedor, LimiteTextField txt_ruc, LimiteTextField txt_telefono, LimiteTextField txt_direccion, JinternalPadre frame
   ) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         ProveedoresE b = ProveedoresE.buscarProveedores5(Integer.parseInt(this.getText().trim()), frame);
         if (b == null) {
            txt_nombre_proveedor.setValue("");
            this.setValue(0);
            txt_direccion.setValue("");
            txt_ruc.setValue("");
            txt_telefono.setValue("");
            this.requestFocusInWindow();
         } else {
            this.setValue(b.getCod_proveedor());
            txt_nombre_proveedor.setText(b.getNombre_proveedor());
            txt_direccion.setValue(b.getDireccion());
            txt_ruc.setValue(b.getRuc());
            txt_telefono.setValue(b.getTelefono());
         }

         this.focusEnabled = true;
      }
   }

   public LimiteTextFieldConSQLProveedores(
      int limite,
      final LimiteTextField txt_nombre_proveedor,
      final LimiteTextField txt_ruc,
      final LimiteTextField txt_telefono,
      final LimiteTextField txt_direccion,
      final JinternalPadre frame
   ) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setFont(new Font("Roboto", 0, 12));
      this.setBorder(new MinimalShadowBorder());
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQLProveedores.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQLProveedores.this.focusEnabled) {
               LimiteTextFieldConSQLProveedores.this.buscarRegistro(txt_nombre_proveedor, txt_ruc, txt_telefono, txt_direccion, frame);
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
               LimiteTextFieldConSQLProveedores.this.focusEnabled = false;
               BuscadorProveedor buscador = new BuscadorProveedor(frame);
               buscador.setModal(true);
               buscador.setVisible(true);
               if (buscador.isSeleccionado()) {
                  LimiteTextFieldConSQLProveedores.this.setValue(buscador.getCodigo());
                  txt_nombre_proveedor.setValue(buscador.getNombre());
                  txt_ruc.setValue(buscador.getRuc());
                  txt_telefono.setValue(buscador.getTelefono());
                  txt_direccion.setValue(buscador.getDireccion());
               }

               LimiteTextFieldConSQLProveedores.this.focusEnabled = true;
            }
         }
      });
   }

   private void buscarRegistro(LabelPrincipal lbl_nombre_proveedor, LabelPrincipal lblRuc, JinternalPadre frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         ProveedoresE b = ProveedoresE.buscarProveedores3(Integer.parseInt(this.getText().trim()), frame);
         if (b == null) {
            lbl_nombre_proveedor.setText("");
            this.setValue(0);
            lblRuc.setText("");
            this.requestFocusInWindow();
         } else {
            this.setValue(b.getCod_proveedor());
            lbl_nombre_proveedor.setText(b.getNombre_proveedor());
            lblRuc.setText(b.getDireccion());
         }

         this.focusEnabled = true;
      }
   }

   public LimiteTextFieldConSQLProveedores(int limite, final LabelPrincipal lbl_nombre_proveedor, final LabelPrincipal lblRuc, final JinternalPadre frame) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setFont(new Font("Roboto", 0, 12));
      this.setBorder(new MinimalShadowBorder());
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQLProveedores.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQLProveedores.this.focusEnabled) {
               LimiteTextFieldConSQLProveedores.this.buscarRegistro(lbl_nombre_proveedor, lblRuc, frame);
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
               LimiteTextFieldConSQLProveedores.this.focusEnabled = false;
               BuscadorProveedor buscador = new BuscadorProveedor(frame);
               buscador.setModal(true);
               buscador.setVisible(true);
               if (buscador.isSeleccionado()) {
                  LimiteTextFieldConSQLProveedores.this.setValue(buscador.getCodigo());
                  lbl_nombre_proveedor.setText(buscador.getNombre());
                  lblRuc.setText(buscador.getRuc());
               }

               LimiteTextFieldConSQLProveedores.this.focusEnabled = true;
            }
         }
      });
   }
}
