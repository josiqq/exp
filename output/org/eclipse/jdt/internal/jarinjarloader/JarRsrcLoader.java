package org.eclipse.jdt.internal.jarinjarloader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class JarRsrcLoader {
   public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
      JarRsrcLoader.ManifestInfo mi = getManifestInfo();
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      URL.setURLStreamHandlerFactory(new RsrcURLStreamHandlerFactory(cl));
      URL[] rsrcUrls = new URL[mi.rsrcClassPath.length];

      for (int i = 0; i < mi.rsrcClassPath.length; i++) {
         String rsrcPath = mi.rsrcClassPath[i];
         if (rsrcPath.endsWith("/")) {
            rsrcUrls[i] = new URL("rsrc:" + rsrcPath);
         } else {
            rsrcUrls[i] = new URL("jar:rsrc:" + rsrcPath + "!/");
         }
      }

      ClassLoader jceClassLoader = new URLClassLoader(rsrcUrls, getParentClassLoader());
      Thread.currentThread().setContextClassLoader(jceClassLoader);
      Class<?> c = Class.forName(mi.rsrcMainClass, true, jceClassLoader);
      Method main = c.getMethod("main", args.getClass());
      main.invoke(null, args);
   }

   private static ClassLoader getParentClassLoader() throws InvocationTargetException, IllegalAccessException {
      try {
         Method platformClassLoader = ClassLoader.class.getMethod("getPlatformClassLoader", null);
         return (ClassLoader)platformClassLoader.invoke(null, null);
      } catch (NoSuchMethodException var1) {
         return null;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static JarRsrcLoader.ManifestInfo getManifestInfo() throws IOException {
      Enumeration<URL> resEnum = Thread.currentThread().getContextClassLoader().getResources("META-INF/MANIFEST.MF");

      while (resEnum.hasMoreElements()) {
         try {
            Throwable var1 = null;
            Object var2 = null;

            try {
               InputStream is = resEnum.nextElement().openStream();

               JarRsrcLoader.ManifestInfo var10000;
               try {
                  if (is == null) {
                     continue;
                  }

                  JarRsrcLoader.ManifestInfo result = new JarRsrcLoader.ManifestInfo(null);
                  Manifest manifest = new Manifest(is);
                  Attributes mainAttribs = manifest.getMainAttributes();
                  result.rsrcMainClass = mainAttribs.getValue("Rsrc-Main-Class");
                  String rsrcCP = mainAttribs.getValue("Rsrc-Class-Path");
                  if (rsrcCP == null) {
                     rsrcCP = "";
                  }

                  result.rsrcClassPath = splitSpaces(rsrcCP);
                  if (result.rsrcMainClass == null || result.rsrcMainClass.trim().isEmpty()) {
                     continue;
                  }

                  var10000 = result;
               } finally {
                  if (is != null) {
                     is.close();
                  }
               }

               return var10000;
            } catch (Throwable var15) {
               if (var1 == null) {
                  var1 = var15;
               } else if (var1 != var15) {
                  var1.addSuppressed(var15);
               }

               throw var1;
            }
         } catch (Exception var16) {
         }
      }

      System.err.println("Missing attributes for JarRsrcLoader in Manifest (Rsrc-Main-Class, Rsrc-Class-Path)");
      return null;
   }

   private static String[] splitSpaces(String line) {
      if (line == null) {
         return null;
      } else {
         List<String> result = new ArrayList<>();
         int firstPos = 0;

         while (firstPos < line.length()) {
            int lastPos = line.indexOf(32, firstPos);
            if (lastPos == -1) {
               lastPos = line.length();
            }

            if (lastPos > firstPos) {
               result.add(line.substring(firstPos, lastPos));
            }

            firstPos = lastPos + 1;
         }

         return result.toArray(new String[result.size()]);
      }
   }

   private static class ManifestInfo {
      String rsrcMainClass;
      String[] rsrcClassPath;

      private ManifestInfo() {
      }
   }
}
