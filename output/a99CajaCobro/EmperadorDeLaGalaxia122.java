package a99CajaCobro;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class EmperadorDeLaGalaxia122 extends JFrame {
   private final JPanel panelLineas = new JPanel();
   private final List<EmperadorDeLaGalaxia122.LineaPago> lineas = new ArrayList<>();

   public EmperadorDeLaGalaxia122() {
      super("Sistema Galáctico de Cobranzas");
      this.setDefaultCloseOperation(3);
      this.setLayout(new BorderLayout());
      this.panelLineas.setLayout(new BoxLayout(this.panelLineas, 1));
      JScrollPane scroll = new JScrollPane(this.panelLineas);
      this.add(scroll, "Center");
      JButton btnAgregar = new JButton("Agregar Línea");
      btnAgregar.addActionListener(e -> this.agregarLinea());
      JButton btnMostrar = new JButton("Mostrar Datos");
      btnMostrar.addActionListener(e -> this.mostrarDatos());
      JPanel barra = new JPanel(new FlowLayout(0, 10, 0));
      barra.add(btnAgregar);
      barra.add(btnMostrar);
      this.add(barra, "South");
      this.agregarLinea();
      this.agregarLinea();
      this.setSize(1100, 350);
      this.setLocationRelativeTo(null);
   }

   private void agregarLinea() {
      EmperadorDeLaGalaxia122.LineaPago linea = new EmperadorDeLaGalaxia122.LineaPago();
      EmperadorDeLaGalaxia122.PanelLineaPago[] ui = new EmperadorDeLaGalaxia122.PanelLineaPago[1];
      ui[0] = new EmperadorDeLaGalaxia122.PanelLineaPago(linea, () -> this.eliminarLinea(linea, ui[0]));
      this.lineas.add(linea);
      this.panelLineas.add(ui[0]);
      this.panelLineas.revalidate();
      this.panelLineas.repaint();
   }

   private void eliminarLinea(EmperadorDeLaGalaxia122.LineaPago linea, EmperadorDeLaGalaxia122.PanelLineaPago ui) {
      this.lineas.remove(linea);
      this.panelLineas.remove(ui);
      this.panelLineas.revalidate();
      this.panelLineas.repaint();
   }

   private void mostrarDatos() {
      System.out.println("---- DATOS CAPTURADOS ----");
      int idx = 1;

      for (EmperadorDeLaGalaxia122.LineaPago l : this.lineas) {
         System.out.printf("[%d] Tipo: %s | %s%n", idx++, l.tipoPago, l);
      }
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new EmperadorDeLaGalaxia122().setVisible(true));
   }

   static class LineaPago {
      String tipoPago = "Efectivo";
      String monedaEfec = "USD";
      double cotizacionEfec = 1.0;
      double importeACobrarEfec = 0.0;
      double importeCobradoEfec = 0.0;
      String monedaVuelto = "USD";
      double importeVuelto = 0.0;
      double cotizacionVuelto = 1.0;
      String monedaTarjeta = "USD";
      double cotizacionTarjeta = 1.0;
      double importeCobradoTarjeta = 0.0;
      String tarjeta = "Visa";
      String baucher = "";
      String monedaCheque = "USD";
      double cotizacionCheque = 1.0;
      double importeCheque = 0.0;
      String banco = "";
      String nroCuenta = "";
      String nroCheque = "";
      String fechaEmision = "";
      String fechaVencimiento = "";
      String librador = "";

      @Override
      public String toString() {
         String var1 = this.tipoPago;
         switch (this.tipoPago.hashCode()) {
            case -982542963:
               if (var1.equals("Efectivo")) {
                  return String.format(
                     "Moneda=%s, Cot=%.2f, A Cobrar=%.2f, Cobrado=%.2f, Vuelto %s %.2f cot %.2f",
                     this.monedaEfec,
                     this.cotizacionEfec,
                     this.importeACobrarEfec,
                     this.importeCobradoEfec,
                     this.monedaVuelto,
                     this.importeVuelto,
                     this.cotizacionVuelto
                  );
               }
               break;
            case 126465517:
               if (var1.equals("Tarjeta")) {
                  return String.format(
                     "Moneda=%s, Cot=%.2f, Importe=%.2f, Tarjeta=%s, Baucher=%s",
                     this.monedaTarjeta,
                     this.cotizacionTarjeta,
                     this.importeCobradoTarjeta,
                     this.tarjeta,
                     this.baucher
                  );
               }
               break;
            case 2017320513:
               if (var1.equals("Cheque")) {
                  return String.format(
                     "Moneda=%s, Cot=%.2f, Importe=%.2f, Banco=%s, NroCuenta=%s, NroCheque=%s, FechaEm=%s, FechaVen=%s, Librador=%s",
                     this.monedaCheque,
                     this.cotizacionCheque,
                     this.importeCheque,
                     this.banco,
                     this.nroCuenta,
                     this.nroCheque,
                     this.fechaEmision,
                     this.fechaVencimiento,
                     this.librador
                  );
               }
         }

         return "Tipo no reconocido";
      }
   }

   class PanelLineaPago extends JPanel {
      private static final long serialVersionUID = 1L;
      private final EmperadorDeLaGalaxia122.LineaPago linea;
      private final Runnable onEliminar;
      private final CardLayout cardLayout = new CardLayout();
      private final JPanel panelCards = new JPanel(this.cardLayout);
      private final JComboBox<String> comboTipoPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Cheque"});
      private final JButton btnEliminar = new JButton("Eliminar");
      private final JComboBox<String> comboMonedas_efectivo = new JComboBox<>(new String[]{"USD", "EUR", "ARS"});
      private final JTextField txtCotizacion_efectivo = new JTextField(5);
      private final JTextField txtImporteACobrar_efectivo = new JTextField(6);
      private final JTextField txtImporteCobrado_efectivo = new JTextField(6);
      private final JComboBox<String> comboMonedaVuelto = new JComboBox<>(new String[]{"USD", "EUR", "ARS"});
      private final JTextField txtImporteVuelto = new JTextField(6);
      private final JTextField txtCotizacionVuelto = new JTextField(5);
      private final JComboBox<String> comboMonedas_tarjeta = new JComboBox<>(new String[]{"USD", "EUR", "ARS"});
      private final JTextField txtCotizacion_tarjeta = new JTextField(5);
      private final JTextField txtImporteCobrado_tarjeta = new JTextField(6);
      private final JComboBox<String> comboTarjetas = new JComboBox<>(new String[]{"Visa", "Mastercard", "Amex"});
      private final JTextField txtBaucher = new JTextField(10);
      private final JComboBox<String> comboMonedas_cheque = new JComboBox<>(new String[]{"USD", "EUR", "ARS"});
      private final JTextField txtCotizacion_cheque = new JTextField(5);
      private final JTextField txtImporte_cheque = new JTextField(6);
      private final JComboBox<String> comboBancos = new JComboBox<>(new String[]{"Banco Nación", "Banco Galicia", "Banco Macro"});
      private final JTextField txtNroCuenta = new JTextField(10);
      private final JTextField txtNroCheque = new JTextField(10);
      private final JTextField txtFechaEmision = new JTextField(10);
      private final JTextField txtFechaVencimiento = new JTextField(10);
      private final JTextField txtLibrador = new JTextField(15);

      public PanelLineaPago(EmperadorDeLaGalaxia122.LineaPago linea, Runnable onEliminar) {
         this.linea = linea;
         this.onEliminar = onEliminar;
         this.setLayout(new BorderLayout());
         JPanel topPanel = new JPanel(new FlowLayout(0, 10, 5));
         topPanel.add(new JLabel("Tipo:"));
         topPanel.add(this.comboTipoPago);
         topPanel.add(this.btnEliminar);
         this.add(topPanel, "North");
         this.panelCards.add(this.crearPanelEfectivo(), "Efectivo");
         this.panelCards.add(this.crearPanelTarjeta(), "Tarjeta");
         this.panelCards.add(this.crearPanelCheque(), "Cheque");
         this.add(this.panelCards, "Center");
         this.comboTipoPago.setSelectedItem(linea.tipoPago);
         this.cardLayout.show(this.panelCards, linea.tipoPago);
         this.comboTipoPago.addActionListener(e -> {
            String tipo = (String)this.comboTipoPago.getSelectedItem();
            linea.tipoPago = tipo;
            this.cardLayout.show(this.panelCards, tipo);
            this.cargarDatosEnUI();
         });
         this.btnEliminar.addActionListener(e -> onEliminar.run());
         this.agregarListenersDeActualizacion();
         this.cargarDatosEnUI();
      }

      private void agregarListenersDeActualizacion() {
         this.comboMonedas_efectivo.addActionListener(e -> this.linea.monedaEfec = (String)this.comboMonedas_efectivo.getSelectedItem());
         this.txtCotizacion_efectivo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.cotizacionEfec = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtCotizacion_efectivo.getText());
            }
         });
         this.txtImporteACobrar_efectivo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.importeACobrarEfec = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtImporteACobrar_efectivo.getText());
            }
         });
         this.txtImporteCobrado_efectivo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.importeCobradoEfec = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtImporteCobrado_efectivo.getText());
            }
         });
         this.comboMonedaVuelto.addActionListener(e -> this.linea.monedaVuelto = (String)this.comboMonedaVuelto.getSelectedItem());
         this.txtImporteVuelto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.importeVuelto = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtImporteVuelto.getText());
            }
         });
         this.txtCotizacionVuelto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.cotizacionVuelto = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtCotizacionVuelto.getText());
            }
         });
         this.comboMonedas_tarjeta.addActionListener(e -> this.linea.monedaTarjeta = (String)this.comboMonedas_tarjeta.getSelectedItem());
         this.txtCotizacion_tarjeta.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.cotizacionTarjeta = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtCotizacion_tarjeta.getText());
            }
         });
         this.txtImporteCobrado_tarjeta.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.importeCobradoTarjeta = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtImporteCobrado_tarjeta.getText());
            }
         });
         this.comboTarjetas.addActionListener(e -> this.linea.tarjeta = (String)this.comboTarjetas.getSelectedItem());
         this.txtBaucher.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.baucher = PanelLineaPago.this.txtBaucher.getText();
            }
         });
         this.comboMonedas_cheque.addActionListener(e -> this.linea.monedaCheque = (String)this.comboMonedas_cheque.getSelectedItem());
         this.txtCotizacion_cheque.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.cotizacionCheque = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtCotizacion_cheque.getText());
            }
         });
         this.txtImporte_cheque.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.importeCheque = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtImporte_cheque.getText());
            }
         });
         this.comboBancos.addActionListener(e -> this.linea.banco = (String)this.comboBancos.getSelectedItem());
         this.txtNroCuenta.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.nroCuenta = PanelLineaPago.this.txtNroCuenta.getText();
            }
         });
         this.txtNroCheque.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.nroCheque = PanelLineaPago.this.txtNroCheque.getText();
            }
         });
         this.txtFechaEmision.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.fechaEmision = PanelLineaPago.this.txtFechaEmision.getText();
            }
         });
         this.txtFechaVencimiento.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.fechaVencimiento = PanelLineaPago.this.txtFechaVencimiento.getText();
            }
         });
         this.txtLibrador.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               PanelLineaPago.this.linea.librador = PanelLineaPago.this.txtLibrador.getText();
            }
         });
      }

      private JPanel crearPanelEfectivo() {
         JPanel p = new JPanel(new FlowLayout(0, 5, 5));
         p.add(new JLabel("Moneda:"));
         p.add(this.comboMonedas_efectivo);
         p.add(new JLabel("Cotización:"));
         p.add(this.txtCotizacion_efectivo);
         p.add(new JLabel("Importe a cobrar:"));
         p.add(this.txtImporteACobrar_efectivo);
         p.add(new JLabel("Importe cobrado:"));
         p.add(this.txtImporteCobrado_efectivo);
         p.add(new JLabel("Moneda Vuelto:"));
         p.add(this.comboMonedaVuelto);
         p.add(new JLabel("Importe Vuelto:"));
         p.add(this.txtImporteVuelto);
         p.add(new JLabel("Cot. Vuelto:"));
         p.add(this.txtCotizacionVuelto);
         return p;
      }

      private JPanel crearPanelTarjeta() {
         JPanel p = new JPanel(new FlowLayout(0, 5, 5));
         p.add(new JLabel("Moneda:"));
         p.add(this.comboMonedas_tarjeta);
         p.add(new JLabel("Cotización:"));
         p.add(this.txtCotizacion_tarjeta);
         p.add(new JLabel("Importe cobrado:"));
         p.add(this.txtImporteCobrado_tarjeta);
         p.add(new JLabel("Tarjeta:"));
         p.add(this.comboTarjetas);
         p.add(new JLabel("Baucher:"));
         p.add(this.txtBaucher);
         return p;
      }

      private JPanel crearPanelCheque() {
         JPanel p = new JPanel(new FlowLayout(0, 5, 5));
         p.add(new JLabel("Moneda:"));
         p.add(this.comboMonedas_cheque);
         p.add(new JLabel("Cotización:"));
         p.add(this.txtCotizacion_cheque);
         p.add(new JLabel("Importe:"));
         p.add(this.txtImporte_cheque);
         p.add(new JLabel("Banco:"));
         p.add(this.comboBancos);
         p.add(new JLabel("Nro Cuenta:"));
         p.add(this.txtNroCuenta);
         p.add(new JLabel("Nro Cheque:"));
         p.add(this.txtNroCheque);
         p.add(new JLabel("Fecha Emisión:"));
         p.add(this.txtFechaEmision);
         p.add(new JLabel("Fecha Vencimiento:"));
         p.add(this.txtFechaVencimiento);
         p.add(new JLabel("Librador:"));
         p.add(this.txtLibrador);
         return p;
      }

      private void cargarDatosEnUI() {
         String var1 = this.linea.tipoPago;
         switch (this.linea.tipoPago.hashCode()) {
            case -982542963:
               if (var1.equals("Efectivo")) {
                  this.comboMonedas_efectivo.setSelectedItem(this.linea.monedaEfec);
                  this.txtCotizacion_efectivo.setText(String.valueOf(this.linea.cotizacionEfec));
                  this.txtImporteACobrar_efectivo.setText(String.valueOf(this.linea.importeACobrarEfec));
                  this.txtImporteCobrado_efectivo.setText(String.valueOf(this.linea.importeCobradoEfec));
                  this.comboMonedaVuelto.setSelectedItem(this.linea.monedaVuelto);
                  this.txtImporteVuelto.setText(String.valueOf(this.linea.importeVuelto));
                  this.txtCotizacionVuelto.setText(String.valueOf(this.linea.cotizacionVuelto));
               }
               break;
            case 126465517:
               if (var1.equals("Tarjeta")) {
                  this.comboMonedas_tarjeta.setSelectedItem(this.linea.monedaTarjeta);
                  this.txtCotizacion_tarjeta.setText(String.valueOf(this.linea.cotizacionTarjeta));
                  this.txtImporteCobrado_tarjeta.setText(String.valueOf(this.linea.importeCobradoTarjeta));
                  this.comboTarjetas.setSelectedItem(this.linea.tarjeta);
                  this.txtBaucher.setText(this.linea.baucher);
               }
               break;
            case 2017320513:
               if (var1.equals("Cheque")) {
                  this.comboMonedas_cheque.setSelectedItem(this.linea.monedaCheque);
                  this.txtCotizacion_cheque.setText(String.valueOf(this.linea.cotizacionCheque));
                  this.txtImporte_cheque.setText(String.valueOf(this.linea.importeCheque));
                  this.comboBancos.setSelectedItem(this.linea.banco);
                  this.txtNroCuenta.setText(this.linea.nroCuenta);
                  this.txtNroCheque.setText(this.linea.nroCheque);
                  this.txtFechaEmision.setText(this.linea.fechaEmision);
                  this.txtFechaVencimiento.setText(this.linea.fechaVencimiento);
                  this.txtLibrador.setText(this.linea.librador);
               }
         }
      }

      private double parseDouble(String s) {
         try {
            return Double.parseDouble(s);
         } catch (Exception var3) {
            return 0.0;
         }
      }
   }
}
