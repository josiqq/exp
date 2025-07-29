package a3Permisos;

public class Permiso {
   private boolean ver;
   private boolean agregar;
   private boolean modificar;
   private boolean eliminar;

   public Permiso(boolean ver, boolean agregar, boolean modificar, boolean eliminar) {
      this.ver = ver;
      this.agregar = agregar;
      this.modificar = modificar;
      this.eliminar = eliminar;
   }

   public boolean isVer() {
      return this.ver;
   }

   public boolean isAgregar() {
      return this.agregar;
   }

   public boolean isModificar() {
      return this.modificar;
   }

   public boolean isEliminar() {
      return this.eliminar;
   }
}
