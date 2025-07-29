package a99CajaCobro;

import java.math.BigDecimal;

public class LineaTarjeta {
   private String moneda;
   private BigDecimal cotizacion;
   private BigDecimal importeCobrado;
   private String tipoTarjeta;
   private String baucher;

   public String getMoneda() {
      return this.moneda;
   }

   public void setMoneda(String moneda) {
      this.moneda = moneda;
   }

   public BigDecimal getCotizacion() {
      return this.cotizacion;
   }

   public void setCotizacion(BigDecimal cotizacion) {
      this.cotizacion = cotizacion;
   }

   public BigDecimal getImporteCobrado() {
      return this.importeCobrado;
   }

   public void setImporteCobrado(BigDecimal importeCobrado) {
      this.importeCobrado = importeCobrado;
   }

   public String getTipoTarjeta() {
      return this.tipoTarjeta;
   }

   public void setTipoTarjeta(String tipoTarjeta) {
      this.tipoTarjeta = tipoTarjeta;
   }

   public String getBaucher() {
      return this.baucher;
   }

   public void setBaucher(String baucher) {
      this.baucher = baucher;
   }
}
