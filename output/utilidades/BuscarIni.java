package utilidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import utilidadesSQL.LogErrores;

public class BuscarIni {
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static String buscar(String linea) {
      String valor = "";
      Properties propiedades = new Properties();
      String nombreArchivo = "configuraciones/configuracion.properties";

      try {
         Throwable ex = null;
         Object var5 = null;

         try {
            InputStream input = new FileInputStream(nombreArchivo);

            try {
               propiedades.load(input);
               valor = propiedades.getProperty(linea);
            } finally {
               if (input != null) {
                  input.close();
               }
            }

            return valor;
         } catch (Throwable var16) {
            if (ex == null) {
               ex = var16;
            } else if (ex != var16) {
               ex.addSuppressed(var16);
            }

            throw ex;
         }
      } catch (FileNotFoundException var17) {
         var17.printStackTrace();
         LogErrores.errores(var17, "Error al iniciar Sesion:", null);
         throw new RuntimeException("No se encontró el archivo de configuración externo", var17);
      } catch (NumberFormatException | IOException var18) {
         var18.printStackTrace();
         LogErrores.errores(var18, "Error al iniciar Sesion ", null);
         throw new RuntimeException("Error al cargar la configuración", var18);
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void guardar(String clave, String valor) {
      Properties propiedades = new Properties();
      String nombreArchivo = "configuraciones/configuracion.properties";

      try {
         Throwable e = null;
         Object var5 = null;

         try {
            InputStream input = new FileInputStream(nombreArchivo);

            try {
               propiedades.load(input);
            } finally {
               if (input != null) {
                  input.close();
               }
            }
         } catch (Throwable var35) {
            if (e == null) {
               e = var35;
            } else if (e != var35) {
               e.addSuppressed(var35);
            }

            throw e;
         }
      } catch (IOException var36) {
         var36.printStackTrace();
         System.out.println("Archivo no encontrado o no se pudo leer. Se creará uno nuevo si es necesario.");
      }

      propiedades.setProperty(clave, valor);

      try {
         Throwable var37 = null;
         Object var38 = null;

         try {
            OutputStream output = new FileOutputStream(nombreArchivo);

            try {
               propiedades.store(output, "Configuración actualizada");
            } finally {
               if (output != null) {
                  output.close();
               }
            }
         } catch (Throwable var32) {
            if (var37 == null) {
               var37 = var32;
            } else if (var37 != var32) {
               var37.addSuppressed(var32);
            }

            throw var37;
         }
      } catch (IOException var33) {
         var33.printStackTrace();
         throw new RuntimeException("Error al guardar la configuración", var33);
      }
   }
}
