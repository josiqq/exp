package a99CajaCobro;

public class CondicionesE {
   private int cod_condicion;
   private String nombre_condicion;
   private int tipo;

   public CondicionesE(int cod, String nombre, int tipo) {
      this.cod_condicion = cod;
      this.nombre_condicion = nombre;
      this.tipo = tipo;
   }

   public int getCod_condicion() {
      return this.cod_condicion;
   }

   public String getNombre_condicion() {
      return this.nombre_condicion;
   }

   public int getTipo() {
      return this.tipo;
   }

   @Override
   public String toString() {
      return this.nombre_condicion;
   }
}
