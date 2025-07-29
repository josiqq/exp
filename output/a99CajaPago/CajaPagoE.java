package a99CajaPago;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;

public class CajaPagoE extends JInternalFrame {
   private static final long serialVersionUID = 1L;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               CajaPagoE frame = new CajaPagoE();
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
   }

   public CajaPagoE() {
      this.setBounds(100, 100, 450, 300);
   }
}
