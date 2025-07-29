package a99CajaCobroCliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;

public class CtasCobrarE {
   public static void cargarCtasCobrarTabla(
      String sql, int cod_cliente, int cod_moneda, int par_decimal_importe, ModeloTablaDefecto modelo, JinternalPadre frame
   ) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         System.out.println("SQL: " + sql);
         preparedStatementSelect = conexion.prepareStatement(sql);
         preparedStatementSelect.setInt(1, cod_cliente);
         if (cod_moneda > 0) {
            preparedStatementSelect.setInt(2, cod_moneda);
         }

         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(
               new Object[]{
                  resultado.getInt("cod_tipo_doc"),
                  resultado.getString("nombre_tipo_documento"),
                  resultado.getInt("timbrado"),
                  resultado.getInt("nro_factura"),
                  resultado.getInt("cuotas"),
                  resultado.getInt("nro_cuota"),
                  formatoDecimalPrecio.format(resultado.getDouble("saldo")),
                  resultado.getInt("cod_moneda"),
                  resultado.getString("sigla_moneda"),
                  resultado.getInt("cod_cliente"),
                  resultado.getInt("id"),
                  resultado.getInt("id")
               }
            );
         }
      } catch (Exception var14) {
         LogErrores.errores(var14, "Error al seleccionar Ctas Cobrar..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Ctas. Cobrar..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }
}
