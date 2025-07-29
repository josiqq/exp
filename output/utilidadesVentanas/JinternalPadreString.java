package utilidadesVentanas;

import a1Login.LoginForm;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import utilidades.LimiteTextFieldString;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;

public class JinternalPadreString extends JInternalFrame {
   protected LimiteTextFieldString txt_codigo;
   private static final long serialVersionUID = 1L;

   public JinternalPadreString() {
      this.setIconifiable(true);
      this.setMaximizable(false);
      this.setClosable(true);
      this.getContentPane().setBackground(new Color(210, 210, 210));
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setFrameIcon(iconoVentana);
      this.addEscapeKey();
      InputMap inputMap = this.getRootPane().getInputMap(2);
      ActionMap actionMap = this.getRootPane().getActionMap();
      inputMap.put(KeyStroke.getKeyStroke("F9"), "F9_ACTION");
      actionMap.put("F9_ACTION", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
            JinternalPadreString.this.guardar();
         }
      });
      this.addInternalFrameListener(new InternalFrameAdapter() {
         @Override
         public void internalFrameClosing(InternalFrameEvent e) {
            JinternalPadreString.this.confirmarSalida();
         }
      });
      this.initializeCodigoTextField();
   }

   private void initializeCodigoTextField() {
      this.txt_codigo = new LimiteTextFieldString(25);
      this.txt_codigo.addFocusListener(new FocusAdapter() {
         @Override
         public void focusGained(FocusEvent e) {
            JinternalPadreString.this.txt_codigo.setBackground(Color.ORANGE);
         }

         @Override
         public void focusLost(FocusEvent e) {
            JinternalPadreString.this.buscar();
            JinternalPadreString.this.txt_codigo.setBackground(Color.WHITE);
         }
      });
   }

   public void nuevo() {
      DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe evento para esta ventana", "error");
      rs.setLocationRelativeTo(this);
      rs.setVisible(true);
   }

   public void buscar() {
   }

   public void guardar() {
      DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe evento para esta ventana", "error");
      rs.setLocationRelativeTo(this);
      rs.setVisible(true);
   }

   public void eliminar() {
      DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe evento para esta ventana", "error");
      rs.setLocationRelativeTo(this);
      rs.setVisible(true);
   }

   private void addEscapeKey() {
      KeyStroke escape = KeyStroke.getKeyStroke(27, 0, false);
      Action escapeAction = new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            JinternalPadreString.this.confirmarSalida();
         }
      };
      this.getRootPane().getInputMap(2).put(escape, "ESCAPE");
      this.getRootPane().getActionMap().put("ESCAPE", escapeAction);
   }

   private void confirmarSalida() {
      DialogoMessagebox d = new DialogoMessagebox("Desea cerrar la ventana ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         this.dispose();
      }
   }
}
