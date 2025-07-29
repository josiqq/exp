package utilidadesVentanas;

import a1Login.LoginForm;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;

public class JinternalPadreReporte extends JInternalFrame {
   private static final long serialVersionUID = 1L;

   public JinternalPadreReporte() {
      this.setIconifiable(true);
      this.setMaximizable(false);
      this.setClosable(true);
      this.getContentPane().setBackground(new Color(210, 210, 210));
      ImageIcon iconoVentana = new ImageIcon(LoginForm.class.getResource("/imagenes/logo_sistema.png"));
      this.setFrameIcon(iconoVentana);
      this.addEscapeKey();
      this.addInternalFrameListener(new InternalFrameAdapter() {
         @Override
         public void internalFrameClosing(InternalFrameEvent e) {
            JinternalPadreReporte.this.confirmarSalida();
         }
      });
   }

   public void copiar() {
      DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe evento para esta ventana", "error");
      rs.setLocationRelativeTo(this);
      rs.setVisible(true);
   }

   public void exportarExcel() {
      DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe evento para esta ventana", "error");
      rs.setLocationRelativeTo(this);
      rs.setVisible(true);
   }

   public void imprimir() {
      DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe evento para esta ventana", "error");
      rs.setLocationRelativeTo(this);
      rs.setVisible(true);
   }

   public void recuperar() {
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
            JinternalPadreReporte.this.confirmarSalida();
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
