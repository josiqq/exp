package utilidades;

import a00Clientes.ClientesE;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFormattedTextField;
import utilidadesTabla.BuscadorCliente;
import utilidadesVentanas.JinternalPadre;

public class LimiteTextFieldConSQLClientes extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private boolean focusEnabled = true;

   private void buscarRegistro(
      LabelPrincipal lblNombreCampo, LabelPrincipal ruc, String direccion, String telefono, NumerosTextField txt_tipo_fiscal_cliente, JinternalPadre frame
   ) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         ClientesE b = ClientesE.buscarCliente5(Integer.parseInt(this.getText().trim()), frame);
         if (b == null) {
            lblNombreCampo.setText("");
            this.setValue(0);
            txt_tipo_fiscal_cliente.setValue(9);
            this.requestFocusInWindow();
         } else {
            this.setValue(b.getCod_cliente());
            lblNombreCampo.setText(b.getNombre_cliente());
            telefono = b.getTelefono();
            direccion = b.getDireccion();
            txt_tipo_fiscal_cliente.setValue(b.getTipo_fiscal());
         }

         this.focusEnabled = true;
      }
   }

   public LimiteTextFieldConSQLClientes(
      int limite,
      final LabelPrincipal lblNombreCampo,
      final LabelPrincipal ruc,
      final String telefono,
      final String direccion,
      final NumerosTextField txt_tipo_fiscal_cliente,
      final JinternalPadre frame
   ) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setFont(new Font("Roboto", 0, 12));
      this.setBorder(new MinimalShadowBorder());
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQLClientes.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQLClientes.this.focusEnabled) {
               LimiteTextFieldConSQLClientes.this.buscarRegistro(lblNombreCampo, ruc, telefono, direccion, txt_tipo_fiscal_cliente, frame);
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
               LimiteTextFieldConSQLClientes.this.focusEnabled = false;
               BuscadorCliente buscador = new BuscadorCliente(frame, 1);
               buscador.setModal(true);
               buscador.setVisible(true);
               if (buscador.isSeleccionado()) {
                  LimiteTextFieldConSQLClientes.this.setValue(buscador.getCodigo());
                  lblNombreCampo.setText(buscador.getNombre());
                  ruc.setText(buscador.getRuc());
                  txt_tipo_fiscal_cliente.setValue(buscador.getTipo_fiscal());
               }
            }
         }
      });
   }

   public LimiteTextFieldConSQLClientes(int limite, final LabelPrincipal lblNombreCampo, final LabelPrincipal ruc, final JinternalPadre frame) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setFont(new Font("Roboto", 0, 12));
      this.setBorder(new MinimalShadowBorder());
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQLClientes.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQLClientes.this.focusEnabled) {
               LimiteTextFieldConSQLClientes.this.buscarRegistro(lblNombreCampo, ruc, frame);
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
               LimiteTextFieldConSQLClientes.this.focusEnabled = false;
               BuscadorCliente buscador = new BuscadorCliente(frame, 1);
               buscador.setModal(true);
               buscador.setVisible(true);
               if (buscador.isSeleccionado()) {
                  LimiteTextFieldConSQLClientes.this.setValue(buscador.getCodigo());
                  lblNombreCampo.setText(buscador.getNombre());
                  ruc.setText(buscador.getRuc());
               }
            }
         }
      });
   }

   private void buscarRegistro(LabelPrincipal lblNombreCampo, LabelPrincipal ruc, JinternalPadre frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         ClientesE b = ClientesE.buscarCliente4(Integer.parseInt(this.getText().trim()), frame);
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

   private void buscarRegistro(LabelPrincipal lblNombreCampo, LabelPrincipal ruc, String direccion, String telefono, JinternalPadre frame) {
      this.setBackground(Color.WHITE);
      if (!this.focusEnabled) {
         this.focusEnabled = true;
      } else {
         ClientesE b = ClientesE.buscarCliente4(Integer.parseInt(this.getText().trim()), frame);
         if (b == null) {
            lblNombreCampo.setText("");
            this.setValue(0);
            this.requestFocusInWindow();
         } else {
            this.setValue(b.getCod_cliente());
            lblNombreCampo.setText(b.getNombre_cliente());
            telefono = b.getTelefono();
            direccion = b.getDireccion();
         }
      }
   }

   public LimiteTextFieldConSQLClientes(
      int limite, final LabelPrincipal lblNombreCampo, final LabelPrincipal ruc, final String telefono, final String direccion, final JinternalPadre frame
   ) {
      super(AbstractFormatoNumero.createFormatter(limite));
      this.setFont(new Font("Roboto", 0, 12));
      this.setBorder(new MinimalShadowBorder());
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            LimiteTextFieldConSQLClientes.this.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LimiteTextFieldConSQLClientes.this.focusEnabled) {
               LimiteTextFieldConSQLClientes.this.buscarRegistro(lblNombreCampo, ruc, telefono, direccion, frame);
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
               LimiteTextFieldConSQLClientes.this.focusEnabled = false;
               BuscadorCliente buscador = new BuscadorCliente(frame);
               buscador.setModal(true);
               buscador.setVisible(true);
               if (buscador.isSeleccionado()) {
                  LimiteTextFieldConSQLClientes.this.setValue(buscador.getCodigo());
                  lblNombreCampo.setText(buscador.getNombre());
                  ruc.setText(buscador.getRuc());
               }
            }
         }
      });
   }
}
