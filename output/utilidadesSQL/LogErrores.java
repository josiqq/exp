package utilidadesSQL;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

public class LogErrores {
   private static final Logger logger = Logger.getLogger(LogErrores.class.getName());

   public static void errores(String mensaje) {
      FileHandler fhandler = null;

      try {
         Date fechaHora = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss a");
         String fecha = sdf.format(fechaHora);
         String carpetaErrores = "errores";
         String nombreArchivo = "errores_" + fecha + ".log";
         String directorioProyecto = System.getProperty("user.dir");
         File directorio = new File(directorioProyecto + File.separator + carpetaErrores);
         if (!directorio.exists()) {
            directorio.mkdirs();
         }

         String rutaCompleta = directorioProyecto + File.separator + carpetaErrores + File.separator + nombreArchivo;
         fhandler = new FileHandler(rutaCompleta, true);
         fhandler.setFormatter(new SimpleFormatter());
         logger.addHandler(fhandler);
         DialogErrores dialog1 = new DialogErrores(mensaje);
         dialog1.setLocationRelativeTo(null);
         dialog1.setVisible(true);
      } catch (IOException | SecurityException var11) {
         logger.log(Level.SEVERE, "Error al manejar excepción en el log", (Throwable)var11);
      }
   }

   public static void errores(Exception e, String mensaje) {
      FileHandler fhandler = null;

      try {
         Date fechaHora = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss a");
         String fecha = sdf.format(fechaHora);
         String carpetaErrores = "errores";
         String nombreArchivo = "errores_" + fecha + ".log";
         String directorioProyecto = System.getProperty("user.dir");
         File directorio = new File(directorioProyecto + File.separator + carpetaErrores);
         if (!directorio.exists()) {
            directorio.mkdirs();
         }

         String rutaCompleta = directorioProyecto + File.separator + carpetaErrores + File.separator + nombreArchivo;
         fhandler = new FileHandler(rutaCompleta, true);
         fhandler.setFormatter(new SimpleFormatter());
         logger.addHandler(fhandler);
         String mensajeError = e.getMessage() + "\r\n    \n";
         StackTraceElement[] elements = e.getStackTrace();

         for (StackTraceElement element : elements) {
            mensajeError = mensajeError + element + "\r\n\t";
         }

         LogRecord log = new LogRecord(Level.WARNING, mensajeError);
         fhandler.publish(log);
         fhandler.close();
      } catch (IOException | SecurityException var17) {
         logger.log(Level.SEVERE, "Error al manejar excepción en el log", (Throwable)var17);
      }

      StackTraceElement[] stackTrace = e.getStackTrace();
      int var10000 = stackTrace.length;
      DialogErrores dialog = new DialogErrores(mensaje, e.getMessage());
      dialog.setVisible(true);
   }

   public static void errores(String mensaje, JDialog frame) {
      FileHandler fhandler = null;

      try {
         Date fechaHora = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss a");
         String fecha = sdf.format(fechaHora);
         String carpetaErrores = "errores";
         String nombreArchivo = "errores_" + fecha + ".log";
         String directorioProyecto = System.getProperty("user.dir");
         File directorio = new File(directorioProyecto + File.separator + carpetaErrores);
         if (!directorio.exists()) {
            directorio.mkdirs();
         }

         String rutaCompleta = directorioProyecto + File.separator + carpetaErrores + File.separator + nombreArchivo;
         fhandler = new FileHandler(rutaCompleta, true);
         fhandler.setFormatter(new SimpleFormatter());
         logger.addHandler(fhandler);
      } catch (IOException | SecurityException var11) {
         logger.log(Level.SEVERE, "Error al manejar excepción en el log", (Throwable)var11);
      }

      DialogErrorPermisos dialog = new DialogErrorPermisos(frame, mensaje, "");
      dialog.setVisible(true);
   }

   public static void errores(String mensaje, JInternalFrame frame) {
      FileHandler fhandler = null;

      try {
         Date fechaHora = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss a");
         String fecha = sdf.format(fechaHora);
         String carpetaErrores = "errores";
         String nombreArchivo = "errores_" + fecha + ".log";
         String directorioProyecto = System.getProperty("user.dir");
         File directorio = new File(directorioProyecto + File.separator + carpetaErrores);
         if (!directorio.exists()) {
            directorio.mkdirs();
         }

         String rutaCompleta = directorioProyecto + File.separator + carpetaErrores + File.separator + nombreArchivo;
         fhandler = new FileHandler(rutaCompleta, true);
         fhandler.setFormatter(new SimpleFormatter());
         logger.addHandler(fhandler);
      } catch (IOException | SecurityException var11) {
         logger.log(Level.SEVERE, "Error al manejar excepción en el log", (Throwable)var11);
      }

      DialogErrorPermisos dialog = new DialogErrorPermisos(frame, mensaje, "");
      dialog.setLocationRelativeTo(frame);
      dialog.setVisible(true);
   }

   public static void errores(Exception e, String mensaje, JDialog frame) {
      FileHandler fhandler = null;

      try {
         Date fechaHora = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss a");
         String fecha = sdf.format(fechaHora);
         String carpetaErrores = "errores";
         String nombreArchivo = "errores_" + fecha + ".log";
         String directorioProyecto = System.getProperty("user.dir");
         File directorio = new File(directorioProyecto + File.separator + carpetaErrores);
         if (!directorio.exists()) {
            directorio.mkdirs();
         }

         String rutaCompleta = directorioProyecto + File.separator + carpetaErrores + File.separator + nombreArchivo;
         fhandler = new FileHandler(rutaCompleta, true);
         fhandler.setFormatter(new SimpleFormatter());
         logger.addHandler(fhandler);
         String mensajeError = e.getMessage() + "\r\n    \n";
         StackTraceElement[] elements = e.getStackTrace();

         for (StackTraceElement element : elements) {
            mensajeError = mensajeError + element + "\r\n\t";
         }

         LogRecord log = new LogRecord(Level.WARNING, mensajeError);
         fhandler.publish(log);
         fhandler.close();
      } catch (IOException | SecurityException var18) {
         logger.log(Level.SEVERE, "Error al manejar excepción en el log", (Throwable)var18);
      }

      StackTraceElement[] stackTrace = e.getStackTrace();
      int var10000 = stackTrace.length;
      DialogErrores dialog = new DialogErrores(frame, mensaje, e.getMessage());
      dialog.setVisible(true);
   }

   public static void errores(Exception e, String mensaje, JInternalFrame frame) {
      FileHandler fhandler = null;

      try {
         Date fechaHora = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss a");
         String fecha = sdf.format(fechaHora);
         String carpetaErrores = "errores";
         String nombreArchivo = "errores_" + fecha + ".log";
         String directorioProyecto = System.getProperty("user.dir");
         File directorio = new File(directorioProyecto + File.separator + carpetaErrores);
         if (!directorio.exists()) {
            directorio.mkdirs();
         }

         String rutaCompleta = directorioProyecto + File.separator + carpetaErrores + File.separator + nombreArchivo;
         fhandler = new FileHandler(rutaCompleta, true);
         fhandler.setFormatter(new SimpleFormatter());
         logger.addHandler(fhandler);
         String mensajeError = e.getMessage() + "\r\n    \n";
         StackTraceElement[] elements = e.getStackTrace();

         for (StackTraceElement element : elements) {
            mensajeError = mensajeError + element + "\r\n\t";
         }

         LogRecord log = new LogRecord(Level.WARNING, mensajeError);
         fhandler.publish(log);
         fhandler.close();
      } catch (IOException | SecurityException var18) {
         logger.log(Level.SEVERE, "Error al manejar excepción en el log", (Throwable)var18);
      }

      StackTraceElement[] stackTrace = e.getStackTrace();
      int var10000 = stackTrace.length;
      DialogErrores dialog = new DialogErrores(frame, mensaje, e.getMessage());
      dialog.setVisible(true);
   }

   public static void errores(Exception e, String mensaje, JFrame frame) {
      FileHandler fhandler = null;

      try {
         Date fechaHora = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss a");
         String fecha = sdf.format(fechaHora);
         String carpetaErrores = "errores";
         String nombreArchivo = "errores_" + fecha + ".log";
         String directorioProyecto = System.getProperty("user.dir");
         File directorio = new File(directorioProyecto + File.separator + carpetaErrores);
         if (!directorio.exists()) {
            directorio.mkdirs();
         }

         String rutaCompleta = directorioProyecto + File.separator + carpetaErrores + File.separator + nombreArchivo;
         fhandler = new FileHandler(rutaCompleta, true);
         fhandler.setFormatter(new SimpleFormatter());
         logger.addHandler(fhandler);
         String mensajeError = e.getMessage() + "\r\n    \n";
         StackTraceElement[] elements = e.getStackTrace();

         for (StackTraceElement element : elements) {
            mensajeError = mensajeError + element + "\r\n\t";
         }

         LogRecord log = new LogRecord(Level.WARNING, mensajeError);
         fhandler.publish(log);
         fhandler.close();
      } catch (IOException | SecurityException var18) {
         logger.log(Level.SEVERE, "Error al manejar excepción en el log", (Throwable)var18);
      }

      StackTraceElement[] stackTrace = e.getStackTrace();
      int var10000 = stackTrace.length;
      DialogErrores dialog = new DialogErrores(frame, mensaje, e.getMessage());
      dialog.setVisible(true);
   }
}
