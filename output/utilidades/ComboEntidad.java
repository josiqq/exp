package utilidades;

public class ComboEntidad {
   private int id;
   private int tipo;
   private String nombre;

   public ComboEntidad(int id, String nombre) {
      this.id = id;
      this.nombre = nombre;
   }

   public ComboEntidad(int id, String nombre, int tipo) {
      this.id = id;
      this.nombre = nombre;
      this.tipo = tipo;
   }

   public int getId() {
      return this.id;
   }

   public String getNombre() {
      return this.nombre;
   }

   public int getTipo() {
      return this.tipo;
   }

   @Override
   public String toString() {
      return this.nombre;
   }
}
