package a99CajaCobro;

public class TarjetasE {
   private int cod_tarjeta;
   private String nombre_tarjeta;

   public TarjetasE(int cod_tarjeta, String nombre_tarjeta) {
      this.cod_tarjeta = cod_tarjeta;
      this.nombre_tarjeta = nombre_tarjeta;
   }

   public int getCod_tarjeta() {
      return this.cod_tarjeta;
   }

   public String getNombre_tarjeta() {
      return this.nombre_tarjeta;
   }

   @Override
   public String toString() {
      return this.nombre_tarjeta;
   }
}
