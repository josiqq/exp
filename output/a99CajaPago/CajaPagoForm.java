package a99CajaPago;

import java.awt.EventQueue;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class CajaPagoForm extends JinternalPadre {
   private static final long serialVersionUID = 1L;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CajaPagoForm frame = new CajaPagoForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
               var3.printStackTrace();
            }
         }
      });
   }

   public CajaPagoForm() {
      this.setTitle("Registro de Pago a Proveedores");
      this.setBounds(100, 100, 450, 300);
   }
}
