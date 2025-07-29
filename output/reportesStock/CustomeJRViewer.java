package reportesStock;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import utilidades.BotonPadreJasper;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQLJasper;
import utilidadesVentanas.JInternalPadreReporteJasper;

public class CustomeJRViewer extends JRViewer {
   private static final long serialVersionUID = 1L;

   public static void imprimirComponentes(Container contenedor, int nivel) {
      Component[] componentes = contenedor.getComponents();
      String indent = "  ".repeat(nivel);

      for (Component comp : componentes) {
         String nombre = comp.getName() != null ? comp.getName() : "(sin nombre)";
         System.out.println(indent + comp.getClass().getSimpleName() + " - Nombre: " + nombre);
         if (comp instanceof Container) {
            imprimirComponentes((Container)comp, nivel + 1);
         }
      }
   }

   public CustomeJRViewer(JasperPrint jasperPrint, final JInternalPadreReporteJasper reporte, int totalPaginas) {
      super(jasperPrint);

      try {
         JPanel jPanel2 = (JPanel)this.getComponent(1);
         imprimirComponentes(jPanel2, 0);
         JPanel jPanel = (JPanel)this.getComponent(0);
         jPanel.setLayout(new BoxLayout(jPanel, 0));
         jPanel.setBackground(new Color(187, 222, 251));
         jPanel.add(Box.createHorizontalStrut(1));
         LabelPrincipal label1 = new LabelPrincipal("Fecha Inicio", "label");
         label1.setPreferredSize(new Dimension(72, 25));
         label1.setMaximumSize(new Dimension(72, 25));
         jPanel.add(label1);
         JDateChooser txt_fecha_ini = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
         txt_fecha_ini.setPreferredSize(new Dimension(100, 25));
         txt_fecha_ini.setMaximumSize(new Dimension(100, 25));
         jPanel.add(txt_fecha_ini);
         jPanel.add(Box.createHorizontalStrut(10));
         LabelPrincipal label2 = new LabelPrincipal("Fecha Fin", "label");
         label2.setPreferredSize(new Dimension(72, 25));
         label2.setMaximumSize(new Dimension(72, 25));
         jPanel.add(label2);
         JDateChooser txt_fecha_fin = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
         txt_fecha_fin.setPreferredSize(new Dimension(100, 25));
         txt_fecha_fin.setMaximumSize(new Dimension(100, 25));
         jPanel.add(txt_fecha_fin);
         jPanel.add(Box.createHorizontalStrut(10));
         LabelPrincipal label3 = new LabelPrincipal("Motivo", "label");
         label3.setPreferredSize(new Dimension(45, 25));
         label3.setMaximumSize(new Dimension(45, 25));
         jPanel.add(label3);
         LabelPrincipal lblNombreMotivo = new LabelPrincipal(0);
         lblNombreMotivo.setPreferredSize(new Dimension(200, 25));
         lblNombreMotivo.setMaximumSize(new Dimension(200, 25));
         LimiteTextFieldConSQLJasper txt_cod_motivo = new LimiteTextFieldConSQLJasper(999999, lblNombreMotivo, "MotivosAjuste", null);
         txt_cod_motivo.setPreferredSize(new Dimension(30, 25));
         txt_cod_motivo.setMaximumSize(new Dimension(30, 25));
         jPanel.add(txt_cod_motivo);
         jPanel.add(lblNombreMotivo);
         jPanel.add(Box.createHorizontalStrut(10));
         LabelPrincipal label4 = new LabelPrincipal("Deposito", "label");
         label4.setPreferredSize(new Dimension(52, 25));
         label4.setMaximumSize(new Dimension(52, 25));
         LabelPrincipal lblNombreDeposito = new LabelPrincipal(0);
         lblNombreDeposito.setPreferredSize(new Dimension(200, 25));
         lblNombreDeposito.setMaximumSize(new Dimension(200, 25));
         LimiteTextFieldConSQLJasper txt_cod_deposito = new LimiteTextFieldConSQLJasper(999999, lblNombreDeposito, "Depositos", null);
         txt_cod_deposito.setPreferredSize(new Dimension(30, 25));
         txt_cod_deposito.setMaximumSize(new Dimension(30, 25));
         jPanel.add(label4);
         jPanel.add(txt_cod_deposito);
         jPanel.add(lblNombreDeposito);
         jPanel.add(Box.createHorizontalStrut(10));
         LabelPrincipal label5 = new LabelPrincipal("Producto", "label");
         label5.setPreferredSize(new Dimension(57, 25));
         label5.setMaximumSize(new Dimension(57, 25));
         LabelPrincipal lblNombreProducto = new LabelPrincipal(0);
         lblNombreProducto.setPreferredSize(new Dimension(250, 25));
         lblNombreProducto.setMaximumSize(new Dimension(250, 25));
         LimiteTextFieldConSQLJasper txt_cod_producto = new LimiteTextFieldConSQLJasper(999999, lblNombreProducto, "Depositos", null);
         txt_cod_producto.setPreferredSize(new Dimension(70, 25));
         txt_cod_producto.setMaximumSize(new Dimension(70, 25));
         jPanel.add(label5);
         jPanel.add(txt_cod_producto);
         jPanel.add(lblNombreProducto);
         jPanel.add(Box.createHorizontalGlue());
         new Color(212, 175, 55);
         jPanel.add(Box.createHorizontalStrut(1));
         Color colorOriginal = new Color(212, 175, 55);
         BotonPadreJasper minimizar = new BotonPadreJasper("-", "minimizar.png", colorOriginal);
         minimizar.setPreferredSize(new Dimension(25, 16));
         minimizar.setMaximumSize(new Dimension(25, 16));
         jPanel.add(minimizar);
         minimizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                  reporte.setIcon(true);
               } catch (PropertyVetoException var3) {
                  var3.printStackTrace();
               }
            }
         });
         colorOriginal = new Color(192, 57, 43);
         jPanel.add(Box.createHorizontalStrut(5));
         BotonPadreJasper cerrar = new BotonPadreJasper("x", "close.png", colorOriginal);
         cerrar.setPreferredSize(new Dimension(25, 16));
         cerrar.setMaximumSize(new Dimension(25, 16));
         jPanel.add(cerrar);
         cerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               reporte.dispose();
            }
         });
         this.setZoomRatio(0.75F);
         Component btnSave = jPanel.getComponent(0);
         Component btnPrint = jPanel.getComponent(1);
         Component btnRefresh = jPanel.getComponent(2);
         Component btnSave2 = jPanel.getComponent(3);
         Component btnPrimero = jPanel.getComponent(4);
         Component btnUltimo = jPanel.getComponent(7);
         Component txt_pagina = jPanel.getComponent(8);
         Component btnSeparador = jPanel.getComponent(9);
         Component btnNormalizar = jPanel.getComponent(10);
         Component btnMinimizar = jPanel.getComponent(11);
         Component btnMaximizar = jPanel.getComponent(12);
         Component btnSeparador2 = jPanel.getComponent(13);
         jPanel.remove(btnSave);
         jPanel.remove(btnPrint);
         jPanel.remove(btnRefresh);
         jPanel.remove(btnSave2);
         jPanel.remove(btnPrimero);
         jPanel.remove(btnUltimo);
         jPanel.remove(txt_pagina);
         jPanel.remove(btnSeparador);
         jPanel.remove(btnNormalizar);
         jPanel.remove(btnMinimizar);
         jPanel.remove(btnMaximizar);
         jPanel.remove(btnSeparador2);
      } catch (Exception var34) {
         var34.printStackTrace();
      }
   }

   public void setZoom(float zoomRatio) {
      this.setZoomRatio(zoomRatio);
   }
}
