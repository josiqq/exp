package a99CajaCobro;

import java.math.BigDecimal;

public class LineaEfectivo {
   private String moneda;
   private BigDecimal cotizacion = BigDecimal.ONE;
   private BigDecimal importeACobrar = BigDecimal.ZERO;
   private BigDecimal importeCobrado = BigDecimal.ZERO;
   private String monedaVuelto;
   private BigDecimal importeVuelto = BigDecimal.ZERO;
   private BigDecimal cotizacionVuelto = BigDecimal.ONE;

   public String getMoneda() {
      return this.moneda;
   }

   public void setMoneda(String m) {
      this.moneda = m;
   }

   public BigDecimal getCotizacion() {
      return this.cotizacion;
   }

   public void setCotizacion(BigDecimal v) {
      this.cotizacion = v;
   }

   public BigDecimal getImporteACobrar() {
      return this.importeACobrar;
   }

   public void setImporteACobrar(BigDecimal v) {
      this.importeACobrar = v;
   }

   public BigDecimal getImporteCobrado() {
      return this.importeCobrado;
   }

   public void setImporteCobrado(BigDecimal v) {
      this.importeCobrado = v;
   }

   public String getMonedaVuelto() {
      return this.monedaVuelto;
   }

   public void setMonedaVuelto(String m) {
      this.monedaVuelto = m;
   }

   public BigDecimal getImporteVuelto() {
      return this.importeVuelto;
   }

   public void setImporteVuelto(BigDecimal v) {
      this.importeVuelto = v;
   }

   public BigDecimal getCotizacionVuelto() {
      return this.cotizacionVuelto;
   }

   public void setCotizacionVuelto(BigDecimal v) {
      this.cotizacionVuelto = v;
   }
}
