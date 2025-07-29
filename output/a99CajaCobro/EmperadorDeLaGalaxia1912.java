package a99CajaCobro;

import java.awt.CardLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EmperadorDeLaGalaxia1912 extends JFrame {
   private final JPanel panelLineas;
   private final List<EmperadorDeLaGalaxia1912.LineaPago> lineas;

   public EmperadorDeLaGalaxia1912() {
      throw new Error("Unresolved compilation problem: \n\tEmperadorDeLaGalaxia2 cannot be resolved to a type\n");
   }

   private void agregarLinea() {
      throw new Error("Unresolved compilation problem: \n");
   }

   private void eliminarLinea(EmperadorDeLaGalaxia1912.LineaPago var1, EmperadorDeLaGalaxia1912.PanelLineaPago var2) {
      throw new Error("Unresolved compilation problem: \n");
   }

   private void mostrarDatos() {
      throw new Error("Unresolved compilation problem: \n");
   }

   public static void main(String[] var0) {
      throw new Error("Unresolved compilation problem: \n\tEmperadorDeLaGalaxia2 cannot be resolved to a type\n");
   }

   static class LineaPago {
      String tipoPago;
      String monedaEfec;
      double cotizacionEfec;
      double importeACobrarEfec;
      double importeCobradoEfec;
      String monedaVuelto;
      double importeVuelto;
      double cotizacionVuelto;
      String monedaTarjeta;
      double cotizacionTarjeta;
      double importeCobradoTarjeta;
      String tarjeta;
      String baucher;
      String monedaCheque;
      double cotizacionCheque;
      double importeCheque;
      String banco;
      String nroCuenta;
      String nroCheque;
      String fechaEmision;
      String fechaVencimiento;
      String librador;

      LineaPago() {
         throw new Error("Unresolved compilation problem: \n\tEmperadorDeLaGalaxia2 cannot be resolved to a type\n");
      }

      @Override
      public String toString() {
         throw new Error("Unresolved compilation problem: \n");
      }
   }

   class PanelLineaPago extends JPanel {
      private static final long serialVersionUID = 1L;
      private final EmperadorDeLaGalaxia1912.LineaPago linea;
      private final Runnable onEliminar;
      private final CardLayout cardLayout;
      private final JPanel panelCards;
      private final JComboBox<String> comboTipoPago;
      private final JButton btnEliminar;
      private final JComboBox<String> comboMonedas_efectivo;
      private final JTextField txtCotizacion_efectivo;
      private final JTextField txtImporteACobrar_efectivo;
      private final JTextField txtImporteCobrado_efectivo;
      private final JComboBox<String> comboMonedaVuelto;
      private final JTextField txtImporteVuelto;
      private final JTextField txtCotizacionVuelto;
      private final JComboBox<String> comboMonedas_tarjeta;
      private final JTextField txtCotizacion_tarjeta;
      private final JTextField txtImporteCobrado_tarjeta;
      private final JComboBox<String> comboTarjetas;
      private final JTextField txtBaucher;
      private final JComboBox<String> comboMonedas_cheque;
      private final JTextField txtCotizacion_cheque;
      private final JTextField txtImporte_cheque;
      private final JComboBox<String> comboBancos;
      private final JTextField txtNroCuenta;
      private final JTextField txtNroCheque;
      private final JTextField txtFechaEmision;
      private final JTextField txtFechaVencimiento;
      private final JTextField txtLibrador;

      public PanelLineaPago(EmperadorDeLaGalaxia1912.LineaPago var2, Runnable var3) {
         throw new Error("Unresolved compilation problem: \n\tEmperadorDeLaGalaxia2 cannot be resolved to a type\n");
      }

      private void agregarListenersDeActualizacion() {
         throw new Error("Unresolved compilation problem: \n");
      }

      private JPanel crearPanelEfectivo() {
         throw new Error("Unresolved compilation problem: \n");
      }

      private JPanel crearPanelTarjeta() {
         throw new Error("Unresolved compilation problem: \n");
      }

      private JPanel crearPanelCheque() {
         throw new Error("Unresolved compilation problem: \n");
      }

      private void cargarDatosEnUI() {
         throw new Error("Unresolved compilation problem: \n");
      }

      private double parseDouble(String var1) {
         throw new Error("Unresolved compilation problem: \n");
      }
   }
}
