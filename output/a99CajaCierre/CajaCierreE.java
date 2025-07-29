package a99CajaCierre;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;

public class CajaCierreE extends JInternalFrame {
   private static final long serialVersionUID = 1L;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               CajaCierreE frame = new CajaCierreE();
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
   }

   public CajaCierreE() {
      this.setBounds(100, 100, 450, 300);
   }
}
