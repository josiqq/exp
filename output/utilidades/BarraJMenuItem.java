package utilidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorProducto;
import utilidadesVentanas.JDialogBuscadores;
import utilidadesVentanas.JInternalPadreReporteJasper;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadrePermisos;
import utilidadesVentanas.JinternalPadreReporte;
import utilidadesVentanas.JinternalPadreString;

public class BarraJMenuItem extends JMenuItem {
   private static final long serialVersionUID = 1L;

   public static void visualizarVentana(String nombre, JDesktopPane escritorio, JInternalPadreReporteJasper frame) {
      for (int i = 0; i < escritorio.getComponentCount(); i++) {
         Component c = escritorio.getComponent(i);
         if (frame.getClass().isInstance(c)) {
            JInternalFrame existente = null;

            try {
               existente = (JInternalFrame)c;
               existente.setIcon(false);
               existente.setSelected(true);
               existente.moveToFront();
            } catch (Exception var8) {
               LogErrores.errores(var8, "Error al abrir formulario:", null);
               var8.printStackTrace();
            }

            return;
         }
      }

      frame.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

      try {
         frame.setVisible(true);
         escritorio.add(frame);
         frame.setName(nombre);
         frame.setSelected(true);
      } catch (Exception var7) {
         var7.printStackTrace();
      }
   }

   public BarraJMenuItem(final String texto, final String ventana, final JDesktopPane escritorio, int x) {
      super(texto);
      this.setEstilo();
      this.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JInternalPadreReporteJasper frame = null;

            try {
               Class<?> clazz = Class.forName(ventana);
               frame = (JInternalPadreReporteJasper)clazz.getDeclaredConstructor().newInstance();
               BarraJMenuItem.visualizarVentana(texto, escritorio, frame);
            } catch (Exception var4) {
               LogErrores.errores(var4, "Error al abrir formulario:", null);
            }
         }
      });
   }

   public BarraJMenuItem(final String texto, final String ventana, final JDesktopPane escritorio) {
      super(texto);
      this.setEstilo();
      this.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JinternalPadre frame = null;

            try {
               Class<?> clazz = Class.forName(ventana);
               frame = (JinternalPadre)clazz.getDeclaredConstructor().newInstance();
               BarraJMenuItem.visualizarVentana(texto, escritorio, frame);
            } catch (Exception var4) {
               LogErrores.errores(var4, "Error al abrir formulario:", null);
               var4.printStackTrace();
            }
         }
      });
   }

   public BarraJMenuItem(final String texto, final String ventana, int x, final JDesktopPane escritorio) {
      super(texto);
      this.setEstilo();
      this.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JinternalPadreString frame = null;

            try {
               Class<?> clazz = Class.forName(ventana);
               frame = (JinternalPadreString)clazz.getDeclaredConstructor().newInstance();
               BarraJMenuItem.this.visualizarVentana(texto, escritorio, frame);
            } catch (Exception var4) {
               LogErrores.errores(var4, "Error al abrir formulario:", null);
               var4.printStackTrace();
            }
         }
      });
   }

   public BarraJMenuItem(final String texto, final String ventana, String x, final JDesktopPane escritorio) {
      super(texto);
      this.setEstilo();
      this.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JinternalPadrePermisos frame = null;

            try {
               Class<?> clazz = Class.forName(ventana);
               frame = (JinternalPadrePermisos)clazz.getDeclaredConstructor().newInstance();
               BarraJMenuItem.this.visualizarVentana(texto, escritorio, frame);
            } catch (Exception var4) {
               LogErrores.errores(var4, "Error al abrir formulario:", null);
               var4.printStackTrace();
            }
         }
      });
   }

   private void visualizarVentana(String nombre, JDesktopPane escritorio, JinternalPadreReporte frame) {
      boolean mostrar = true;

      for (int a = 0; a < escritorio.getComponentCount(); a++) {
         if (frame.getClass().isInstance(escritorio.getComponent(a))) {
            mostrar = false;
         }
      }

      if (mostrar) {
         try {
            frame.setVisible(true);
            escritorio.add(frame);
            frame.setName(nombre);
            frame.setSelected(true);
         } catch (Exception var6) {
            LogErrores.errores(var6, "Error al visualizar formulario:", frame);
            var6.printStackTrace();
         }
      }
   }

   private void visualizarVentana(String nombre, JDesktopPane escritorio, JinternalPadrePermisos frame) {
      boolean mostrar = true;

      for (int a = 0; a < escritorio.getComponentCount(); a++) {
         if (frame.getClass().isInstance(escritorio.getComponent(a))) {
            mostrar = false;
         }
      }

      if (mostrar) {
         try {
            frame.setVisible(true);
            escritorio.add(frame);
            frame.setName(nombre);
            frame.setSelected(true);
         } catch (Exception var6) {
            LogErrores.errores(var6, "Error al visualizar formulario:", frame);
            var6.printStackTrace();
         }
      }
   }

   public BarraJMenuItem(String texto, JDialogBuscadores frame, final JDesktopPane escritorio) {
      super(texto);
      this.setEstilo();
      this.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorProducto buscador = new BuscadorProducto();
            buscador.setLocationRelativeTo(escritorio);
            buscador.setModal(true);
            buscador.setVisible(true);
         }
      });
   }

   public BarraJMenuItem(final String texto, final String frame, boolean x, final JDesktopPane escritorio) {
      super(texto);
      this.setEstilo();
      this.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               Class<?> clazz = Class.forName(frame);
               JinternalPadreReporte framex = (JinternalPadreReporte)clazz.getDeclaredConstructor().newInstance();
               BarraJMenuItem.this.visualizarVentana(texto, escritorio, framex);
            } catch (Exception var4) {
               LogErrores.errores(var4, "Error al visualizar formulario:", null);
               var4.printStackTrace();
            }
         }
      });
   }

   private void setEstilo() {
      this.setFont(new Font("Roboto", 0, 13));
      this.setForeground(new Color(33, 37, 41));
   }

   public static void visualizarVentana(JDesktopPane escritorio, JinternalPadreReporte frame, String nombre) {
      for (int i = 0; i < escritorio.getComponentCount(); i++) {
         Component c = escritorio.getComponent(i);
         if (frame.getClass().isInstance(c)) {
            try {
               JInternalFrame existente = (JInternalFrame)c;
               existente.setIcon(false);
               existente.setSelected(true);
               existente.moveToFront();
            } catch (Exception var7) {
               LogErrores.errores(var7, "Error al visualizar formulario:", null);
               var7.printStackTrace();
            }

            return;
         }
      }

      frame.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

      try {
         frame.setVisible(true);
         escritorio.add(frame);
         frame.setName(nombre);
         frame.setSelected(true);
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }

   public static void visualizarVentana(String nombre, JDesktopPane escritorio, JinternalPadre frame) {
      for (int i = 0; i < escritorio.getComponentCount(); i++) {
         Component c = escritorio.getComponent(i);
         if (frame.getClass().isInstance(c)) {
            try {
               JInternalFrame existente = (JInternalFrame)c;
               existente.setIcon(false);
               existente.setSelected(true);
               existente.moveToFront();
            } catch (Exception var7) {
               LogErrores.errores(var7, "Error al visualizar formulario:", null);
               var7.printStackTrace();
            }

            return;
         }
      }

      frame.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

      try {
         frame.setVisible(true);
         escritorio.add(frame);
         frame.setName(nombre);
         frame.setSelected(true);
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }

   private void visualizarVentana(String nombre, JDesktopPane escritorio, JinternalPadreString frame) {
      boolean mostrar = true;

      for (int a = 0; a < escritorio.getComponentCount(); a++) {
         if (frame.getClass().isInstance(escritorio.getComponent(a))) {
            mostrar = false;
         }
      }

      if (mostrar) {
         try {
            frame.setVisible(true);
            escritorio.add(frame);
            frame.setName(nombre);
            frame.setSelected(true);
         } catch (Exception var6) {
            LogErrores.errores(var6, "Error al visualizar formulario:", null);
            var6.printStackTrace();
         }
      }
   }
}
