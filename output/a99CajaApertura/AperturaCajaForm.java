package a99CajaApertura;

import a00Cuentas.CuentasE;
import cajeros.CajerosE;
import com.toedter.calendar.JDateChooser;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import utilidades.CheckPadre;
import utilidades.LabelPrincipal;
import utilidades.NumerosTextField;
import utilidades.PanelPadre;
import utilidadesConexion.DatabaseConnection;
import utilidadesConexion.FechaActualE;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class AperturaCajaForm extends JinternalPadre {
   private static final long serialVersionUID = 1L;
   private NumerosTextField txt_cod_caja;
   private NumerosTextField txt_cod_cajero;
   private NumerosTextField txt_nro_planilla;
   private JDateChooser txt_fecha_planilla;
   private LabelPrincipal lblNombreCajaTexto;
   private LabelPrincipal lblNombreCajeroTexto;
   private LabelPrincipal lblTotalLineasTexto;
   private TablaDetalleAperturaCaja tabla;
   List<Integer> codigosAEliminar = new ArrayList<>();
   private CheckPadre chkCerrado;

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void cargarConfiguracion() {
      Properties properties = new Properties();

      try {
         Throwable ex = null;
         Object var17 = null;

         try {
            FileInputStream fis = new FileInputStream("src/configuraciones/configuracion.properties");

            try {
               properties.load(fis);
               String caja = properties.getProperty("caja.cod_caja", "");
               this.txt_cod_caja.setValue(Integer.parseInt(caja));
               this.txt_nro_planilla.setValue(AperturaCajaE.buscarPlanillaAbierta(Integer.parseInt(caja), this.tabla, 2, this));
               CuentasE cuenta = CuentasE.buscarCuentas1(Integer.parseInt(caja), this);
               this.lblNombreCajaTexto.setText(cuenta.getNombre_cuenta());
               CajerosE cajero = CajerosE.buscarCajeros2(DatabaseConnection.getInstance().getUsuario(), this);
               this.txt_cod_cajero.setValue(cajero.getCod_cajero());
               this.lblNombreCajeroTexto.setText(cajero.getNombre_cajero());
            } finally {
               if (fis != null) {
                  fis.close();
               }
            }
         } catch (Throwable var15) {
            if (ex == null) {
               ex = var15;
            } else if (ex != var15) {
               ex.addSuppressed(var15);
            }

            throw ex;
         }
      } catch (IOException var16) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("Error al cargar la configuracion " + var16.getMessage(), "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
      }
   }

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != Integer.parseInt(this.txt_nro_planilla.getText())) {
         AperturaCajaE b = AperturaCajaE.buscarAperturaCaja(Integer.parseInt(this.txt_codigo.getText().trim()), this.tabla, 2, 2, this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarAperturaCaja(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         AperturaCajaE entidad = this.CargarEntidades();
         if (Integer.parseInt(this.txt_nro_planilla.getText()) == 0) {
            if (entidad != null) {
               int codigo = AperturaCajaE.insertarCajaApertura(entidad, this.tabla, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (Integer.parseInt(this.txt_nro_planilla.getText()) == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = AperturaCajaE.actualizarAperturaCaja(entidad, this.tabla, this, this.codigosAEliminar);
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
      if (Integer.parseInt(this.txt_codigo.getText()) == Integer.parseInt(this.txt_nro_planilla.getText())
         && Integer.parseInt(this.txt_nro_planilla.getText()) != 0) {
         DialogoMessagebox d = new DialogoMessagebox("Desea eliminar el registro ?");
         d.setLocationRelativeTo(this);
         d.setVisible(true);
         if (d.isResultadoEncontrado()) {
            AperturaCajaE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = AperturaCajaE.eliminarAperturaCaja(ent, this);
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

   public void cargarAperturaCaja(AperturaCajaE e) {
      this.txt_codigo.setValue(e.getNro_planilla());
      this.txt_cod_caja.setValue(e.getCod_caja());
      this.lblNombreCajaTexto.setText(e.getNombre_caja());
      this.txt_cod_cajero.setValue(e.getCod_cajero());
      this.lblNombreCajeroTexto.setText(e.getNombre_cajero());
      if (e.getAbierto() == 0) {
         this.chkCerrado.setSelected(false);
      } else if (e.getAbierto() == 1) {
         this.chkCerrado.setSelected(true);
      }

      Date v_fecha = null;

      try {
         v_fecha = new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha());
         this.txt_fecha_planilla.setDate(v_fecha);
      } catch (ParseException var4) {
         LogErrores.errores(var4, "Error al Convertir Fecha...", this);
      }

      FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
      this.lblTotalLineasTexto.setText(String.valueOf(formatoLinea.format(this.tabla.getRowCount())));
   }

   public AperturaCajaE CargarEntidades() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fecha = "";

      try {
         fecha = dateFormat.format(this.txt_fecha_planilla.getDate());
      } catch (Exception var6) {
         LogErrores.errores(var6, "Debe de cargar Fecha...", this);
         this.txt_fecha_planilla.requestFocusInWindow();
         return null;
      }

      try {
         return new AperturaCajaE(
            Integer.parseInt(this.txt_codigo.getText()), fecha, Integer.parseInt(this.txt_cod_caja.getText()), Integer.parseInt(this.txt_cod_cajero.getText())
         );
      } catch (NumberFormatException var4) {
         LogErrores.errores(var4, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var5) {
         LogErrores.errores(var5, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   private void inicializarObjetos() {
      this.txt_codigo.setValue(0);
      this.chkCerrado.setSelected(false);
      ModeloTablaAperturaCaja modelo = (ModeloTablaAperturaCaja)this.tabla.getModel();
      modelo.deleteRow(this.tabla);
      modelo.addDefaultRow();
      this.txt_cod_caja.setValue(0);
      this.txt_cod_cajero.setValue(0);
      this.txt_codigo.setValue(0);
      FechaActualE fechaActual = FechaActualE.buscarFechaactual(this);
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      String fechaActualStr = fechaActual.getFechaActual();

      try {
         Date fechaDate = dateFormat.parse(fechaActualStr);
         this.txt_fecha_planilla.setDate(fechaDate);
      } catch (ParseException var6) {
         var6.printStackTrace();
      }

      this.lblNombreCajaTexto.setText("");
      this.lblNombreCajeroTexto.setText("");
      this.lblTotalLineasTexto.setText("0");
      this.cargarConfiguracion();
      this.codigosAEliminar.clear();
   }

   public AperturaCajaForm() {
      this.setTitle("Registro de Apertura de Caja");
      this.setBounds(100, 100, 516, 490);
      PanelPadre panel_basico = new PanelPadre("Datos Basicos");
      PanelPadre panel_detalle = new PanelPadre("Detalles de Apertura");
      LabelPrincipal lblNroPlanilla = new LabelPrincipal("Nro. Planilla", "label");
      LabelPrincipal lblNombreCaja = new LabelPrincipal("Caja", "label");
      LabelPrincipal lblFechaPlanilla = new LabelPrincipal("Fecha de Planilla", "label");
      LabelPrincipal lblCajero = new LabelPrincipal("Cajero", "label");
      LabelPrincipal lblTotalLineas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblTotalLineasTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreCajeroTexto = new LabelPrincipal(0);
      this.lblNombreCajaTexto = new LabelPrincipal(0);
      this.txt_nro_planilla = new NumerosTextField(9999L);
      this.txt_nro_planilla.setEditable(false);
      this.txt_fecha_planilla = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_planilla.setEnabled(false);
      this.txt_cod_caja = new NumerosTextField(999999L);
      this.txt_cod_caja.setEditable(false);
      this.txt_cod_cajero = new NumerosTextField(999999L);
      this.txt_cod_cajero.setEditable(false);
      this.chkCerrado = new CheckPadre("Cerrado");
      this.tabla = new TablaDetalleAperturaCaja(this.lblTotalLineasTexto, this.codigosAEliminar, 2, 2);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGap(18)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(lblTotalLineas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblTotalLineasTexto, -2, 65, -2)
                              .addGap(346)
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(panel_detalle, Alignment.LEADING, -1, 485, 32767)
                                    .addComponent(panel_basico, Alignment.LEADING, -2, 484, -2)
                              )
                              .addContainerGap(-1, 32767)
                        )
                  )
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panel_basico, -2, 190, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -2, 229, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblTotalLineas, -2, -1, -2)
                        .addComponent(this.lblTotalLineasTexto, -2, -1, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.TRAILING)
            .addGroup(Alignment.LEADING, gl_panel_detalle.createSequentialGroup().addComponent(scrollPane, -1, 464, 32767).addContainerGap())
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 186, 32767).addContainerGap())
      );
      panel_detalle.setLayout(gl_panel_detalle);
      GroupLayout gl_panel_basico = new GroupLayout(panel_basico);
      gl_panel_basico.setHorizontalGroup(
         gl_panel_basico.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_basico.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(lblCajero, -1, -1, 32767)
                        .addComponent(lblFechaPlanilla, -1, -1, 32767)
                        .addComponent(lblNroPlanilla, -1, 90, 32767)
                        .addComponent(lblNombreCaja, -1, -1, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_basico.createSequentialGroup()
                              .addComponent(this.txt_cod_cajero, -2, 69, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreCajeroTexto, -2, 200, -2)
                        )
                        .addComponent(this.txt_fecha_planilla, -2, 101, -2)
                        .addGroup(
                           gl_panel_basico.createSequentialGroup()
                              .addGroup(
                                 gl_panel_basico.createParallelGroup(Alignment.TRAILING, false)
                                    .addComponent(this.txt_nro_planilla, -1, -1, 32767)
                                    .addComponent(this.txt_cod_caja, -1, 61, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addGroup(
                                 gl_panel_basico.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.lblNombreCajaTexto, -2, 200, -2)
                                    .addComponent(this.chkCerrado, -2, 93, -2)
                              )
                        )
                  )
                  .addGap(107)
            )
      );
      gl_panel_basico.setVerticalGroup(
         gl_panel_basico.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_basico.createSequentialGroup()
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.txt_nro_planilla, -2, 33, -2)
                        .addComponent(lblNroPlanilla, -2, 25, -2)
                        .addComponent(this.chkCerrado, -2, -1, -2)
                  )
                  .addGap(11)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_basico.createSequentialGroup()
                              .addGroup(
                                 gl_panel_basico.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblNombreCaja, -2, 25, -2)
                                    .addComponent(this.txt_cod_caja, -2, 33, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 gl_panel_basico.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblFechaPlanilla, -2, 25, -2)
                                    .addComponent(this.txt_fecha_planilla, -2, 33, -2)
                              )
                        )
                        .addComponent(this.lblNombreCajaTexto, -2, 28, -2)
                  )
                  .addGap(13)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblCajero, -2, 25, -2)
                        .addComponent(this.txt_cod_cajero, -2, 33, -2)
                        .addComponent(this.lblNombreCajeroTexto, -2, 28, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_basico.setLayout(gl_panel_basico);
      this.getContentPane().setLayout(groupLayout);
      this.inicializarObjetos();
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            AperturaCajaForm frame = new AperturaCajaForm();

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
