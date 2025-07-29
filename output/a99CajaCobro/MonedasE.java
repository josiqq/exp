package a99CajaCobro;

public class MonedasE {
   private int cod_moneda;
   private String nombre_moneda;

   public MonedasE(int cod_moneda, String nombre_moneda) {
      this.cod_moneda = cod_moneda;
      this.nombre_moneda = nombre_moneda;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public String getNombre_moneda() {
      return this.nombre_moneda;
   }

   @Override
   public String toString() {
      return this.nombre_moneda;
   }
}
