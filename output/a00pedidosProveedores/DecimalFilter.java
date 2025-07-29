package a00pedidosProveedores;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import utilidades.ShadowBorder;

public class DecimalFilter extends JFormattedTextField {
   private static final long serialVersionUID = 1L;

   public DecimalFilter() {
      this.setHorizontalAlignment(4);
      DecimalFormat internalFormat = new DecimalFormat();
      internalFormat.setMaximumFractionDigits(16);
      internalFormat.setMinimumFractionDigits(16);
      DecimalFormat visualFormat = new DecimalFormat("#,##0.00");
      visualFormat.setDecimalSeparatorAlwaysShown(true);
      NumberFormatter cantidadFormatter = new NumberFormatter(internalFormat);
      cantidadFormatter.setValueClass(BigDecimal.class);
      cantidadFormatter.setAllowsInvalid(false);
      cantidadFormatter.setFormat(visualFormat);
      this.setFormatterFactory(new DefaultFormatterFactory(cantidadFormatter));
   }

   public DecimalFilter(int decimal) {
      this.setHorizontalAlignment(4);
      DecimalFormat internalFormat = new DecimalFormat();
      internalFormat.setMaximumFractionDigits(16);
      internalFormat.setMinimumFractionDigits(16);
      DecimalFormat visualFormat = null;
      if (decimal == 0) {
         visualFormat = new DecimalFormat("#,##0");
      } else if (decimal == 2) {
         visualFormat = new DecimalFormat("#,##0.00");
         visualFormat.setDecimalSeparatorAlwaysShown(true);
      }

      NumberFormatter cantidadFormatter = new NumberFormatter(internalFormat);
      cantidadFormatter.setValueClass(BigDecimal.class);
      cantidadFormatter.setAllowsInvalid(false);
      cantidadFormatter.setFormat(visualFormat);
      this.setFormatterFactory(new DefaultFormatterFactory(cantidadFormatter));
      this.setBorder(new ShadowBorder());
      this.setOpaque(true);
      this.setBackground(new Color(200, 220, 200));
   }

   public DecimalFilter(int decimal, int fuente) {
      this.setFont(new Font("Tahoma", 0, fuente));
      this.setHorizontalAlignment(4);
      DecimalFormat internalFormat = new DecimalFormat();
      internalFormat.setMaximumFractionDigits(16);
      internalFormat.setMinimumFractionDigits(16);
      DecimalFormat visualFormat = null;
      if (decimal == 0) {
         visualFormat = new DecimalFormat("#,##0");
      } else if (decimal == 2) {
         visualFormat = new DecimalFormat("#,##0.00");
         visualFormat.setDecimalSeparatorAlwaysShown(true);
      }

      NumberFormatter cantidadFormatter = new NumberFormatter(internalFormat);
      cantidadFormatter.setValueClass(BigDecimal.class);
      cantidadFormatter.setAllowsInvalid(false);
      cantidadFormatter.setFormat(visualFormat);
      this.setFormatterFactory(new DefaultFormatterFactory(cantidadFormatter));
   }

   @Override
   public String getText() {
      Object value = this.getValue();
      return value instanceof BigDecimal ? ((BigDecimal)value).toPlainString() : super.getText();
   }

   @Override
   public Object getValue() {
      Object value = super.getValue();
      return value instanceof BigDecimal ? ((BigDecimal)value).setScale(16, RoundingMode.DOWN) : value;
   }

   @Override
   public void setValue(Object value) {
      if (value instanceof BigDecimal) {
         BigDecimal bd = ((BigDecimal)value).setScale(16, RoundingMode.DOWN);
         super.setValue(bd);
      } else {
         super.setValue(value);
      }
   }
}
