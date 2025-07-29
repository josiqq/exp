package a0098OrdenCompra;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ModeloTablaOrdenCompra extends JFrame {
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               ModeloTablaOrdenCompra frame = new ModeloTablaOrdenCompra();
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
   }

   public ModeloTablaOrdenCompra() {
      this.setDefaultCloseOperation(3);
      this.setBounds(100, 100, 450, 300);
      this.contentPane = new JPanel();
      this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(this.contentPane);
   }
}
