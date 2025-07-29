package a0098NotaCreditoVenta;

import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import utilidadesVentanas.JinternalPadre;

public class NotaCreditoVentaForm extends JinternalPadre {
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               NotaCreditoVentaForm frame = new NotaCreditoVentaForm();
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
   }

   public NotaCreditoVentaForm() {
      this.setBounds(100, 100, 450, 300);
      this.contentPane = new JPanel();
      this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(this.contentPane);
   }
}
