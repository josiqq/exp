package a99CajaCobro;

public class BancosE {
   private int cod_banco;
   private String nombre_banco;

   public BancosE(int cod_banco, String nombre_banco) {
      this.cod_banco = cod_banco;
      this.nombre_banco = nombre_banco;
   }

   public int getCod_banco() {
      return this.cod_banco;
   }

   public String getNombre_banco() {
      return this.nombre_banco;
   }

   @Override
   public String toString() {
      return this.nombre_banco;
   }
}
