package a99CajaCobro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JDialog;
import utilidades.LimiteTextField;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;

public class VentaTicketE {
   private int cod_moneda;
   private int cod_cliente;
   private double importe;

   public static void cargarVentaETabla(ModeloTablaDefecto modelo, int par_decimal_importe, int COD_MONEDA, int COD_CLIENTE, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\nventas_ticket.nro_registro,ventas_ticket.tipo_doc,\r\nconcat(ventas_ticket.tipo_doc,' - ',tipo_documentos.nombre_tipo_documento) as documento, 0 as timbrado,ventas_ticket.nro_registro as nro_documento,\r\nventas_ticket.cuotas,\r\nventas_ticket.importe,monedas.sigla,ventas_ticket.cod_moneda,ventas_ticket.cod_cliente\r\nfrom\r\nventas_ticket,tipo_documentos,monedas\r\nwhere\r\nventas_ticket.tipo_doc = tipo_documentos.cod_tipo_documento\r\nand ventas_ticket.cod_moneda = monedas.cod_moneda and ventas_ticket.cod_cliente =?"
         );
         preparedStatementSelect.setInt(1, COD_CLIENTE);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            if ((COD_MONEDA == resultado.getInt("cod_moneda") || COD_MONEDA == 0) && (COD_CLIENTE == resultado.getInt("cod_cliente") || COD_CLIENTE == 0)) {
               modelo.addRow(
                  new Object[]{
                     resultado.getInt("tipo_doc"),
                     resultado.getString("documento"),
                     0,
                     resultado.getInt("nro_registro"),
                     resultado.getInt("cuotas"),
                     resultado.getInt("cuotas"),
                     formatoDecimalPrecio.format(resultado.getDouble("importe")),
                     resultado.getInt("cod_moneda"),
                     resultado.getString("sigla"),
                     resultado.getInt("cod_cliente"),
                     0,
                     0
                  }
               );
            } else {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("Moneda o Cliente no pueden ser diferentes", "error");
               rs.setLocationRelativeTo(frame);
               rs.setVisible(true);
            }
         }
      } catch (Exception var14) {
         LogErrores.errores("Error al seleccionar registro", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Registros..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static VentaTicketE cargarVentaETabla(
      LimiteTextField txt_registro, ModeloTablaDocumentosCobrarCaja modelo, int par_decimal_importe, int COD_MONEDA, int COD_CLIENTE, JinternalPadre frame
   ) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);

      VentaTicketE var13;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select\r\nventas_ticket.nro_registro,ventas_ticket.tipo_doc,\r\nconcat(ventas_ticket.tipo_doc,' - ',tipo_documentos.nombre_tipo_documento) as documento, 0 as timbrado,ventas_ticket.nro_registro as nro_documento,\r\nventas_ticket.cuotas,\r\nventas_ticket.importe,monedas.sigla,ventas_ticket.cod_moneda,ventas_ticket.cod_cliente\r\nfrom\r\nventas_ticket,tipo_documentos,monedas\r\nwhere\r\nventas_ticket.tipo_doc = tipo_documentos.cod_tipo_documento\r\nand ventas_ticket.cod_moneda = monedas.cod_moneda and ventas_ticket.nro_registro =?"
         );
         preparedStatementSelect.setInt(1, Integer.parseInt(txt_registro.getText()));
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro: " + Integer.parseInt(txt_registro.getText()), "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         if (COD_MONEDA != resultado.getInt("cod_moneda") && COD_MONEDA != 0 || COD_CLIENTE != resultado.getInt("cod_cliente") && COD_CLIENTE != 0) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Moneda o Cliente no pueden ser diferentes", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         modelo.addRow(
            new Object[]{
               resultado.getInt("nro_registro"),
               resultado.getInt("tipo_doc"),
               resultado.getString("documento"),
               0,
               resultado.getInt("nro_registro"),
               resultado.getInt("cuotas"),
               resultado.getInt("cuotas"),
               formatoDecimalPrecio.format(resultado.getDouble("importe")),
               resultado.getInt("cod_moneda"),
               resultado.getString("sigla"),
               resultado.getInt("cod_cliente"),
               0
            }
         );
         txt_registro.setValue("");
         VentaTicketE venta = new VentaTicketE(resultado.getInt("cod_moneda"), resultado.getInt("cod_cliente"), resultado.getDouble("importe"));
         var13 = venta;
      } catch (Exception var16) {
         LogErrores.errores("Error al seleccionar registro", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Registro..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var13;
   }

   public VentaTicketE(int cod_moneda, int cod_cliente, double importe) {
      this.cod_cliente = cod_cliente;
      this.cod_moneda = cod_moneda;
      this.importe = importe;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public void setCod_moneda(int cod_moneda) {
      this.cod_moneda = cod_moneda;
   }

   public int getCod_cliente() {
      return this.cod_cliente;
   }

   public void setCod_cliente(int cod_cliente) {
      this.cod_cliente = cod_cliente;
   }

   public double getImporte() {
      return this.importe;
   }

   public void setImporte(double importe) {
      this.importe = importe;
   }
}
