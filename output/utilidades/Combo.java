package utilidades;

import a0099Tarjetas.TarjetasE;
import a00Bancos.BancosE;
import a00Cuentas.CuentasE;
import a099CajaCondicionPago.CondicionPagoE;
import a11Monedas.MonedasE;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import utilidadesVentanas.JinternalPadre;

public class Combo extends JComboBox<Object> {
   private static final long serialVersionUID = 1L;

   public Combo(int tipo) {
      if (tipo == 1) {
         this.addItem("Gravado");
         this.addItem("Exento");
         this.addItem("Regimen");
      }

      this.setFont(new Font("Roboto", 0, 10));
      this.setBackground(Color.WHITE);
      this.setForeground(new Color(33, 37, 41));
      this.setBorder(new CompoundBorder(new LineBorder(new Color(180, 180, 180), 1, true), new EmptyBorder(5, 10, 5, 10)));
      this.setFocusable(false);
      this.setMaximumRowCount(5);
      this.setCursor(new Cursor(12));
   }

   public Combo(String tabla, JinternalPadre frame) {
      if (tabla.trim().equals("CondicionesPago")) {
         for (ComboEntidad c : CondicionPagoE.obtenerCondiciones(frame)) {
            this.addItem(c);
         }
      } else if (tabla.trim().equals("Monedas")) {
         for (ComboEntidad c : MonedasE.obtenerMonedas(frame)) {
            this.addItem(c);
         }
      } else if (tabla.trim().equals("Tarjetas")) {
         for (ComboEntidad c : TarjetasE.obtenerTarjetas(frame)) {
            this.addItem(c);
         }
      } else if (tabla.trim().equals("Bancos")) {
         for (ComboEntidad c : BancosE.obtenerBancos(frame)) {
            this.addItem(c);
         }
      } else if (tabla.trim().equals("Cuentas")) {
         for (ComboEntidad c : CuentasE.obtenerCuentas(frame)) {
            this.addItem(c);
         }
      }

      this.setFont(new Font("Roboto", 0, 12));
      this.setBackground(Color.WHITE);
      this.setForeground(new Color(33, 37, 41));
      this.setBorder(new CompoundBorder(new LineBorder(new Color(180, 180, 180), 1, true), new EmptyBorder(5, 10, 5, 10)));
      this.setFocusable(false);
      this.setMaximumRowCount(5);
      this.setCursor(new Cursor(12));
   }
}
