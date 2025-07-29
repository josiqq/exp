package a00Cotizaciones;

import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.Beans;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import utilidades.CuadroTexto2Decimales;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQL;
import utilidades.PanelPadre;
import utilidades.RadioBoton;
import utilidadesConexion.FechaActualE;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class CotizacionesForm extends JinternalPadre {
   private static final long serialVersionUID = 1L;
   private JDateChooser txt_fecha;
   private LimiteTextFieldConSQL txt_cod_moneda_origen;
   private LimiteTextFieldConSQL txt_cod_moneda_destino;
   private RadioBoton rdbtnDivide;
   private RadioBoton rdbtnMultiplicar;
   private LabelPrincipal lblMonedaOrigenTexto;
   public int SW;
   private LabelPrincipal lblMonedaDestinoTexto;
   private CuadroTexto2Decimales txt_cotizacion;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   public void buscarCotizacion() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha_cotizacion = "";

      try {
         fecha_cotizacion = dateFormat.format(this.txt_fecha.getDate());
      } catch (Exception var4) {
         LogErrores.errores(var4, "Debe de cargar Fecha de Cotizacion...", this);
         this.txt_fecha.requestFocusInWindow();
      }

      CotizacionesE b = CotizacionesE.buscarCotizacion2(
         fecha_cotizacion,
         Integer.parseInt(this.txt_cod_moneda_origen.getText().trim().replace(".", "")),
         Integer.parseInt(this.txt_cod_moneda_destino.getText().trim().replace(".", "")),
         this
      );
      if (b != null) {
         this.cargarCotizaciones(b);
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         CotizacionesE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = CotizacionesE.insertarCotizaciones(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == 1) {
            int codigo = CotizacionesE.actualizarCotizaciones(entidad, this);
            if (codigo != 0) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro actualizado correctamente...", "correcto");
               rs.setLocationRelativeTo(this);
               rs.setVisible(true);
               this.inicializarObjetos();
            }
         }
      }
   }

   @Override
   public void eliminar() {
      if (this.SW == 1) {
         DialogoMessagebox d = new DialogoMessagebox("Desea eliminar el registro ?");
         d.setLocationRelativeTo(this);
         d.setVisible(true);
         if (d.isResultadoEncontrado()) {
            CotizacionesE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = CotizacionesE.eliminarCotizaciones(ent, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro eliminado correctamente... ", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         }
      }
   }

   public void cargarCotizaciones(CotizacionesE e) {
      this.SW = 1;
      if (e.getOperacion() == 0) {
         this.rdbtnMultiplicar.setSelected(true);
      } else if (e.getOperacion() == 1) {
         this.rdbtnDivide.setSelected(true);
      }

      this.txt_cotizacion.setValue(e.getCotizacion());
   }

   public CotizacionesE CargarEntidades() {
      int operacion = 0;
      if (this.rdbtnDivide.isSelected()) {
         operacion = 1;
      } else if (this.rdbtnMultiplicar.isSelected()) {
         operacion = 0;
      }

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha_cotizacion = "";

      try {
         fecha_cotizacion = dateFormat.format(this.txt_fecha.getDate());
      } catch (Exception var7) {
         LogErrores.errores(var7, "Debe de cargar Fecha de Cotizacion...", this);
         this.txt_fecha.requestFocusInWindow();
         return null;
      }

      try {
         return new CotizacionesE(
            fecha_cotizacion,
            Integer.parseInt(this.txt_cod_moneda_origen.getText().trim().replace(".", "")),
            Integer.parseInt(this.txt_cod_moneda_destino.getText().trim().replace(".", "")),
            Double.parseDouble(this.txt_cotizacion.getText().replace(".", "").replace(",", ".")),
            operacion
         );
      } catch (NumberFormatException var5) {
         LogErrores.errores(var5, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var6) {
         LogErrores.errores(var6, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   private void inicializarObjetos() {
      this.SW = 0;
      FechaActualE fechaActual = FechaActualE.buscarFechaactual(this);
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      String fechaActualStr = fechaActual.getFechaActual();

      try {
         Date fechaDate = dateFormat.parse(fechaActualStr);
         this.txt_fecha.setDate(fechaDate);
      } catch (ParseException var5) {
         var5.printStackTrace();
      }

      this.txt_cod_moneda_destino.setValue(0);
      this.lblMonedaDestinoTexto.setText("");
      this.txt_cod_moneda_origen.setValue(0);
      this.lblMonedaOrigenTexto.setText("");
      this.txt_cotizacion.setValue(0);
      this.rdbtnDivide.setSelected(false);
      this.rdbtnMultiplicar.setSelected(false);
   }

   public CotizacionesForm() {
      this.setTitle("Registro de Cotizaciones");
      this.setBounds(100, 100, 424, 244);
      PanelPadre panel_datos = new PanelPadre("");
      PanelPadre panel_tipo_operacion = new PanelPadre("Tipo Operacion");
      LabelPrincipal lblFecha = new LabelPrincipal("Fecha", "label");
      LabelPrincipal lblMonedaOrigen = new LabelPrincipal("Moneda Origen", "label");
      LabelPrincipal lblMonedaDestino = new LabelPrincipal("Moneda Destino", "label");
      LabelPrincipal lblCotizacion = new LabelPrincipal("Cotizacion", "label");
      this.lblMonedaOrigenTexto = new LabelPrincipal(0);
      this.lblMonedaDestinoTexto = new LabelPrincipal(0);
      this.txt_cod_moneda_origen = new LimiteTextFieldConSQL(999999, this.lblMonedaOrigenTexto, "Monedas", this);
      this.txt_cod_moneda_destino = new LimiteTextFieldConSQL(999999, this.lblMonedaDestinoTexto, "Monedas", this);
      this.txt_fecha = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.rdbtnMultiplicar = new RadioBoton("Multiplica");
      this.rdbtnDivide = new RadioBoton("Divide");
      ButtonGroup grupoTipo = new ButtonGroup();
      grupoTipo.add(this.rdbtnDivide);
      grupoTipo.add(this.rdbtnMultiplicar);
      this.txt_cod_moneda_destino.addFocusListener(new FocusListener() {
         @Override
         public void focusLost(FocusEvent e) {
            CotizacionesForm.this.buscarCotizacion();
         }

         @Override
         public void focusGained(FocusEvent e) {
         }
      });
      this.txt_cotizacion = new CuadroTexto2Decimales(2);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel_datos, -2, 396, -2).addContainerGap(32, 32767))
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel_datos, -2, 194, -2).addContainerGap(61, 32767))
      );
      GroupLayout gl_panel_datos = new GroupLayout(panel_datos);
      gl_panel_datos.setHorizontalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_tipo_operacion, -2, 348, -2)
                        .addGroup(
                           gl_panel_datos.createSequentialGroup()
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblFecha, -2, 123, -2)
                                    .addComponent(lblMonedaOrigen, -2, 123, -2)
                                    .addComponent(lblMonedaDestino, -2, 123, -2)
                                    .addComponent(lblCotizacion, -2, 123, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_datos.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_cod_moneda_destino, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblMonedaDestinoTexto, -2, 200, -2)
                                    )
                                    .addComponent(this.txt_cotizacion, -2, 61, -2)
                                    .addGroup(
                                       gl_panel_datos.createSequentialGroup()
                                          .addComponent(this.txt_cod_moneda_origen, -2, 41, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.lblMonedaOrigenTexto, -2, 200, -2)
                                    )
                                    .addComponent(this.txt_fecha, -2, -1, -2)
                              )
                        )
                  )
                  .addContainerGap(11, 32767)
            )
      );
      gl_panel_datos.setVerticalGroup(
         gl_panel_datos.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_datos.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(gl_panel_datos.createParallelGroup(Alignment.TRAILING).addComponent(lblFecha, -2, 25, -2).addComponent(this.txt_fecha, -2, 25, -2))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblMonedaOrigen, -2, 25, -2)
                        .addComponent(this.txt_cod_moneda_origen, -2, 25, -2)
                        .addComponent(this.lblMonedaOrigenTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblMonedaDestino, -2, 25, -2)
                        .addComponent(this.txt_cod_moneda_destino, -2, 25, -2)
                        .addComponent(this.lblMonedaDestinoTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_datos.createParallelGroup(Alignment.TRAILING)
                        .addComponent(this.txt_cotizacion, -2, 25, -2)
                        .addComponent(lblCotizacion, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(panel_tipo_operacion, -2, -1, -2)
                  .addContainerGap(21, 32767)
            )
      );
      GroupLayout gl_panel_tipo_operacion = new GroupLayout(panel_tipo_operacion);
      gl_panel_tipo_operacion.setHorizontalGroup(
         gl_panel_tipo_operacion.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo_operacion.createSequentialGroup()
                  .addGap(38)
                  .addComponent(this.rdbtnMultiplicar, -2, 103, -2)
                  .addGap(18)
                  .addComponent(this.rdbtnDivide, -2, 103, -2)
                  .addContainerGap(134, 32767)
            )
      );
      gl_panel_tipo_operacion.setVerticalGroup(
         gl_panel_tipo_operacion.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo_operacion.createSequentialGroup()
                  .addGroup(
                     gl_panel_tipo_operacion.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.rdbtnMultiplicar, -2, 16, -2)
                        .addComponent(this.rdbtnDivide, -2, 16, -2)
                  )
                  .addContainerGap(13, 32767)
            )
      );
      panel_tipo_operacion.setLayout(gl_panel_tipo_operacion);
      panel_datos.setLayout(gl_panel_datos);
      this.getContentPane().setLayout(groupLayout);
      if (!Beans.isDesignTime()) {
         this.inicializarObjetos();
      }
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            CotizacionesForm frame = new CotizacionesForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
               var3.printStackTrace();
            }
         }
      });
   }
}
