package utilidadesVentanas;

import a1Login.LoginForm;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class JFramePrincipal extends JFrame {
   private static final long serialVersionUID = 1L;

   public JFramePrincipal() {
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setIconImage(iconoVentana.getImage());
      this.setDefaultCloseOperation(0);
   }
}
