package utilidadesVentanas;

import a1Login.LoginForm;
import java.awt.AWTKeyStroke;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

public class JDialogBuscador extends JDialog {
   private static final long serialVersionUID = 1L;

   public JDialogBuscador() {
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setIconImage(iconoVentana.getImage());
      this.addEscapeKey();
      this.configurarEnterComoTab();
   }

   private void addEscapeKey() {
      KeyStroke escape = KeyStroke.getKeyStroke(27, 0, false);
      Action escapeAction = new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            JDialogBuscador.this.dispose();
         }
      };
      this.getRootPane().getInputMap(2).put(escape, "ESCAPE");
      this.getRootPane().getActionMap().put("ESCAPE", escapeAction);
   }

   private void configurarEnterComoTab() {
      Set<AWTKeyStroke> forwardKeys = new HashSet<>(this.getFocusTraversalKeys(0));
      forwardKeys.add(KeyStroke.getKeyStroke(10, 0));
      this.setFocusTraversalKeys(0, forwardKeys);
   }
}
