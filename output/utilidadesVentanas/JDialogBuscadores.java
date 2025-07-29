package utilidadesVentanas;

import a1Login.LoginForm;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class JDialogBuscadores extends JDialog {
   private static final long serialVersionUID = 1L;

   public JDialogBuscadores() {
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setIconImage(iconoVentana.getImage());
   }
}
