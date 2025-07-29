package a99CajaCobro;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utilidades.CuadroTexto2Decimales;
import utilidades.LimiteTextField;

public class PanelLineaPago extends JPanel {
   private static final long serialVersionUID = 1L;
   private final LineaPago linea;
   private final Runnable onEliminar;
   private final CardLayout cardLayout = new CardLayout();
   private final JPanel panelCards = new JPanel(this.cardLayout);
   private final JButton btnEliminar = new JButton("Eliminar");
   private final CuadroTexto2Decimales txtCotizacion_efectivo = new CuadroTexto2Decimales(2);
   private final CuadroTexto2Decimales txtImporteACobrar_efectivo = new CuadroTexto2Decimales(2);
   private final CuadroTexto2Decimales txtImporteCobrado_efectivo = new CuadroTexto2Decimales(2);
   private final CuadroTexto2Decimales txtImporteVuelto = new CuadroTexto2Decimales(2);
   private final CuadroTexto2Decimales txtCotizacionVuelto = new CuadroTexto2Decimales(2);
   private JComboBox<MonedasE> comboMonedas_tarjeta;
   private final CuadroTexto2Decimales txtCotizacion_tarjeta = new CuadroTexto2Decimales(2);
   private final CuadroTexto2Decimales txtImporteCobrado_tarjeta = new CuadroTexto2Decimales(2);
   private JComboBox<TarjetasE> comboTarjetas;
   private final LimiteTextField txtBaucher = new LimiteTextField(2);
   private JComboBox<MonedasE> comboMonedas_cheque;
   private final CuadroTexto2Decimales txtCotizacion_cheque = new CuadroTexto2Decimales(2);
   private final CuadroTexto2Decimales txtImporte_cheque = new CuadroTexto2Decimales(2);
   private JComboBox<BancosE> comboBancos;
   private final LimiteTextField txtNroCuenta = new LimiteTextField(18);
   private final JTextField txtNroCheque = new JTextField(10);
   private final JTextField txtFechaEmision = new JTextField(10);
   private final JTextField txtFechaVencimiento = new JTextField(10);
   private final LimiteTextField txtLibrador = new LimiteTextField(45);
   private JComboBox<MonedasE> comboMonedas_efectivo;
   private JComboBox<MonedasE> comboMonedaVuelto;

   public PanelLineaPago(
      LineaPago linea,
      Runnable onEliminar,
      JComboBox<CondicionesE> comboTipoPago,
      JComboBox<MonedasE> comboMonedas_efectivo,
      JComboBox<MonedasE> comboMonedaVuelto,
      JComboBox<MonedasE> comboMonedas_tarjeta,
      JComboBox<TarjetasE> comboTarjetas,
      JComboBox<MonedasE> comboMonedas_cheque,
      JComboBox<BancosE> comboBancos
   ) {
      this.linea = linea;
      this.onEliminar = onEliminar;
      this.comboMonedas_efectivo = comboMonedas_efectivo;
      this.comboMonedaVuelto = comboMonedaVuelto;
      this.comboMonedas_tarjeta = comboMonedas_tarjeta;
      this.comboTarjetas = comboTarjetas;
      this.comboMonedas_cheque = comboMonedas_cheque;
      this.comboBancos = comboBancos;
      this.setLayout(new BorderLayout());
      JPanel topPanel = new JPanel(new FlowLayout(0, 10, 5));
      topPanel.add(new JLabel("Tipo:"));
      topPanel.add(comboTipoPago);
      topPanel.add(this.btnEliminar);
      this.add(topPanel, "North");
      this.panelCards.add(this.crearPanelEfectivo(), "0");
      this.panelCards.add(this.crearPanelTarjeta(), "1");
      this.panelCards.add(this.crearPanelCheque(), "2");
      this.add(this.panelCards, "Center");
      comboTipoPago.setSelectedItem(comboTipoPago.getSelectedItem());
      this.cardLayout.show(this.panelCards, "0");
      comboTipoPago.addActionListener(e -> {
         CondicionesE int_tipo = (CondicionesE)comboTipoPago.getSelectedItem();
         Integer card = int_tipo.getTipo();
         this.cardLayout.show(this.panelCards, String.valueOf(card));
         this.cargarDatosEnUI();
      });
      this.btnEliminar.addActionListener(e -> onEliminar.run());
      this.agregarListenersDeActualizacion();
      this.cargarDatosEnUI();
   }

   private void agregarListenersDeActualizacion() {
      this.comboMonedas_efectivo.addActionListener(e -> {
         MonedasE moneda = (MonedasE)this.comboMonedas_efectivo.getSelectedItem();
         this.linea.cod_moneda_cobro = moneda.getCod_moneda();
      });
      this.txtCotizacion_efectivo.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.cotizacion = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtCotizacion_efectivo.getText());
         }
      });
      this.txtImporteACobrar_efectivo.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.importeACobrar = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtImporteACobrar_efectivo.getText());
         }
      });
      this.txtImporteCobrado_efectivo.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.importeCobrado = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtImporteCobrado_efectivo.getText());
         }
      });
      this.comboMonedaVuelto.addActionListener(e -> {
         MonedasE moneda = (MonedasE)this.comboMonedaVuelto.getSelectedItem();
         this.linea.cod_monedaVuelto = moneda.getCod_moneda();
      });
      this.txtImporteVuelto.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.importeVuelto = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtImporteVuelto.getText());
         }
      });
      this.txtImporteVuelto.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
         }
      });
      this.txtCotizacionVuelto.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.cotizacionVuelto = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtCotizacionVuelto.getText());
         }
      });
      this.comboMonedas_tarjeta.addActionListener(e -> {
         MonedasE moneda = (MonedasE)this.comboMonedas_tarjeta.getSelectedItem();
         this.linea.cod_moneda_cobro = moneda.getCod_moneda();
      });
      this.txtCotizacion_tarjeta.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.cotizacion = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtCotizacion_tarjeta.getText());
         }
      });
      this.txtImporteCobrado_tarjeta.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.importeCobrado = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtImporteCobrado_tarjeta.getText());
         }
      });
      this.comboTarjetas.addActionListener(e -> {
         TarjetasE tarjeta = (TarjetasE)this.comboTarjetas.getSelectedItem();
         this.linea.cod_tarjeta = tarjeta.getCod_tarjeta();
      });
      this.txtBaucher.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.baucher = PanelLineaPago.this.txtBaucher.getText();
         }
      });
      this.comboMonedas_cheque.addActionListener(e -> {
         MonedasE moneda = (MonedasE)this.comboMonedas_cheque.getSelectedItem();
         this.linea.cod_moneda_cobro = moneda.getCod_moneda();
      });
      this.txtCotizacion_cheque.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.cotizacion = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtCotizacion_cheque.getText());
         }
      });
      this.txtImporte_cheque.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.importeCobrado = PanelLineaPago.this.parseDouble(PanelLineaPago.this.txtImporte_cheque.getText());
         }
      });
      this.comboBancos.addActionListener(e -> {
         BancosE banco = (BancosE)this.comboBancos.getSelectedItem();
         this.linea.cod_banco = banco.getCod_banco();
      });
      this.txtNroCuenta.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.nroCuenta = PanelLineaPago.this.txtNroCuenta.getText();
         }
      });
      this.txtNroCheque.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            PanelLineaPago.this.linea.nroCheque = Integer.parseInt(PanelLineaPago.this.txtNroCheque.getText());
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
      switch (this.linea.cod_tipo_pago) {
         case 0:
            this.comboMonedas_efectivo.setSelectedItem(this.comboMonedas_efectivo.getSelectedItem());
            this.txtCotizacion_efectivo.setText(String.valueOf(this.linea.cotizacion));
            this.txtImporteACobrar_efectivo.setText(String.valueOf(this.linea.importeACobrar));
            this.txtImporteCobrado_efectivo.setText(String.valueOf(this.linea.importeCobrado));
            this.comboMonedaVuelto.setSelectedItem(this.comboMonedaVuelto.getSelectedItem());
            this.txtImporteVuelto.setText(String.valueOf(this.linea.importeVuelto));
            this.txtCotizacionVuelto.setText(String.valueOf(this.linea.cotizacionVuelto));
            break;
         case 1:
            this.comboMonedas_tarjeta.setSelectedItem(this.comboMonedas_tarjeta.getSelectedItem());
            this.txtCotizacion_tarjeta.setText(String.valueOf(this.linea.cotizacion));
            this.txtImporteCobrado_tarjeta.setText(String.valueOf(this.linea.importeCobrado));
            this.comboTarjetas.setSelectedItem(this.comboTarjetas.getSelectedItem());
            this.txtBaucher.setText(this.linea.baucher);
            break;
         case 2:
            this.comboMonedas_cheque.setSelectedItem(this.comboMonedas_cheque.getSelectedItem());
            this.txtCotizacion_cheque.setText(String.valueOf(this.linea.cotizacion));
            this.txtImporte_cheque.setText(String.valueOf(this.linea.importeCobrado));
            this.comboBancos.setSelectedItem(this.comboBancos.getSelectedItem());
            this.txtNroCuenta.setText(this.linea.nroCuenta);
            this.txtNroCheque.setText(String.valueOf(this.linea.nroCheque));
            this.txtFechaEmision.setText(this.linea.fechaEmision);
            this.txtFechaVencimiento.setText(this.linea.fechaVencimiento);
            this.txtLibrador.setText(this.linea.librador);
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
