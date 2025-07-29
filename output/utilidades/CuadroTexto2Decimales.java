package utilidades;

import com.formdev.flatlaf.json.ParseException;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class CuadroTexto2Decimales extends JFormattedTextField {
   private static final long serialVersionUID = 1L;
   private static DecimalFormat df;
   private static int DECIMAL;

   public CuadroTexto2Decimales(int decimal) {
      DECIMAL = decimal;
      this.setHorizontalAlignment(4);
      DecimalFormatSymbols symbols = new DecimalFormatSymbols();
      symbols.setDecimalSeparator(',');
      symbols.setGroupingSeparator('.');
      NumberFormatter cantidadFormatter = null;
      if (DECIMAL == 0) {
         df = new DecimalFormat("#,##0", symbols);
      } else if (DECIMAL == 2) {
         df = new DecimalFormat("#,##0.00", symbols);
      } else if (DECIMAL == 3) {
         df = new DecimalFormat("#,##0.000", symbols);
      } else {
         df = new DecimalFormat("#,##0.###", symbols);
      }

      cantidadFormatter = new NumberFormatter(df);
      cantidadFormatter.setValueClass(Double.class);
      cantidadFormatter.setAllowsInvalid(false);
      this.setFormatterFactory(new DefaultFormatterFactory(cantidadFormatter));
      this.addKeyListener(new KeyAdapter() {
         @Override
         public void keyTyped(KeyEvent evt) {
            char c = evt.getKeyChar();
            if (!Character.isDigit(c) && c != ',' && c != '\b') {
               evt.consume();
            }
         }
      });
      this.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            CuadroTexto2Decimales.this.setCaretPosition(0);
            CuadroTexto2Decimales.this.selectAll();
         }

         @Override
         public void focusLost(FocusEvent e) {
            String text = CuadroTexto2Decimales.this.getText();
            if (!text.isEmpty()) {
               try {
                  Number parsed = CuadroTexto2Decimales.df.parse(text);
                  double value = parsed.doubleValue();
                  CuadroTexto2Decimales.this.setText(CuadroTexto2Decimales.formatAndRoundDecimalValue(value));
               } catch (ParseException var6) {
                  var6.printStackTrace();
               } catch (java.text.ParseException var7) {
                  var7.printStackTrace();
               }
            }
         }
      });
   }

   public BigDecimal getValorComoBigDecimal() {
      try {
         Number parsed = df.parse(this.getText());
         return BigDecimal.valueOf(parsed.doubleValue());
      } catch (ParseException var2) {
         return BigDecimal.ZERO;
      } catch (java.text.ParseException var3) {
         var3.printStackTrace();
         return null;
      }
   }

   private static String formatAndRoundDecimalValue(double value) {
      BigDecimal bd = BigDecimal.valueOf(value);
      bd = bd.setScale(DECIMAL, RoundingMode.HALF_EVEN);
      df.setDecimalSeparatorAlwaysShown(true);
      return df.format(bd.doubleValue());
   }

   public CuadroTexto2Decimales(int decimal, int letraTamaño) {
      DECIMAL = decimal;
      this.setHorizontalAlignment(4);
      this.setFont(new Font("Roboto", 0, letraTamaño));
      this.setHorizontalAlignment(4);
      NumberFormatter cantidadFormatter = null;
      if (DECIMAL == 0) {
         cantidadFormatter = new NumberFormatter(new DecimalFormat("#,##0"));
         df = new DecimalFormat("#,##0");
      } else if (DECIMAL == 2) {
         cantidadFormatter = new NumberFormatter(new DecimalFormat("#,##0.00"));
         df = new DecimalFormat("#,##0.00");
      } else if (DECIMAL == 3) {
         cantidadFormatter = new NumberFormatter(new DecimalFormat("#,##0.000"));
         df = new DecimalFormat("#,##0.000");
      }

      cantidadFormatter.setValueClass(Double.class);
      cantidadFormatter.setAllowsInvalid(false);
      this.setFormatterFactory(new DefaultFormatterFactory(cantidadFormatter));
      this.addKeyListener(new KeyAdapter() {
         @Override
         public void keyTyped(KeyEvent evt) {
            char c = evt.getKeyChar();
            if (!Character.isDigit(c) && c != ',' && c != '\b') {
               evt.consume();
            }
         }
      });
      this.addFocusListener(new FocusAdapter() {
         @Override
         public void focusGained(FocusEvent e) {
            CuadroTexto2Decimales.this.setCaretPosition(0);
            CuadroTexto2Decimales.this.selectAll();
         }

         @Override
         public void focusLost(FocusEvent e) {
            String text = CuadroTexto2Decimales.this.getText();
            if (!text.isEmpty()) {
               try {
                  double value = Double.parseDouble(text.replace(",", "."));
                  CuadroTexto2Decimales.this.setText(CuadroTexto2Decimales.formatAndRoundDecimalValue(value));
               } catch (NumberFormatException var5) {
               }
            }
         }
      });
   }
}
