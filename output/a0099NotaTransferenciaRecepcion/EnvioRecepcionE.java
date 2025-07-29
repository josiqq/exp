package a0099NotaTransferenciaRecepcion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class EnvioRecepcionE {
   private int nro_registro;
   private int cod_deposito_origen;
   private int cod_deposito_destino;
   private int transferencia;
   private int recibido;
   private String fecha;
   private String hora_envio;
   private String observacion;
   private String usuario_alta;
   private String nombre_deposito_origen;
   private String nombre_deposito_destino;

   public static EnvioRecepcionE buscarRecepcionTransferencia(
      int registro, JinternalPadre frame, TablaDetalleEnvioRecepcion tabla, int par_decimal_cantidad, int par_decimal_importe
   ) {
      ResultSet rs = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalCantidad = new FormatoDecimalOperacionSistema(par_decimal_cantidad);
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      ModeloTablaEnvioRecepcion modelo = (ModeloTablaEnvioRecepcion)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\nenvios_detalle.cod_producto,productos.nombre_producto,unidades_medidas.sigla,\r\nenvios_detalle.cantidad_enviado,envios_detalle.costo,envios_detalle.tipo_fiscal,\r\nenvios_detalle.iva,envios_detalle.porcentaje_gravado,envios_detalle.id\r\nfrom envios_detalle, productos, unidades_medidas\r\nwhere\r\nenvios_detalle.cod_producto = productos.cod_producto\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida and envios_detalle.nro_registro =?"
         );
         preparedStatement2.setInt(1, registro);
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            modelo.addRow(
               new Object[]{
                  rs2.getString("cod_producto"),
                  rs2.getString("nombre_producto"),
                  rs2.getString("sigla"),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad_enviado")),
                  formatoDecimalPrecio.format(rs2.getDouble("costo")),
                  rs2.getInt("tipo_fiscal"),
                  formatoDecimalCantidad.format(rs2.getDouble("iva")),
                  formatoDecimalCantidad.format(rs2.getDouble("porcentaje_gravado")),
                  rs2.getInt("id"),
                  0
               }
            );
         }

         modelo.addDefaultRow();
         preparedStatement = conexion.prepareStatement(
            "select\r\nenvios.nro_registro, envios.fecha,envios.hora_envio,\r\nenvios.cod_deposito_origen,envios.cod_deposito_destino,\r\nenvios.observacion,envios.usuario_alta,envios.transferencia,envios.recibido,\r\n(select depositos.nombre_deposito from depositos where depositos.cod_deposito = envios.cod_deposito_origen)as nombre_deposito_origen,\r\n(select depositos.nombre_deposito from depositos where depositos.cod_deposito = envios.cod_deposito_destino)as nombre_deposito_destino\r\n from envios where envios.nro_registro =?"
         );
         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (rs.next()) {
            return new EnvioRecepcionE(
               rs.getInt("nro_registro"),
               rs.getString("fecha"),
               rs.getString("hora_envio"),
               rs.getInt("cod_deposito_origen"),
               rs.getInt("cod_deposito_destino"),
               rs.getString("observacion"),
               rs.getString("usuario_alta"),
               rs.getInt("transferencia"),
               rs.getInt("recibido"),
               rs.getString("nombre_deposito_origen"),
               rs.getString("nombre_deposito_destino")
            );
         }
      } catch (Exception var19) {
         LogErrores.errores(var19, "Error al seleccionar Transferencia..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatement2, rs, rs2, frame, "Error al seleccionar Transferencia..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static int actualizarRecepcionTransferencia(int nro_registro, JinternalPadre frame, JTable tablaInsertar) {
      int codigoColumn = tablaInsertar.getColumn("Código").getModelIndex();
      int descripcioncolumn = tablaInsertar.getColumn("Descripción").getModelIndex();
      int cantidadColumn = tablaInsertar.getColumn("Cantidad").getModelIndex();
      int primarioColumn = tablaInsertar.getColumn("Primario").getModelIndex();
      int SWColumn = tablaInsertar.getColumn("SW").getModelIndex();
      double cantidad = 0.0;
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatementUpdate = null;
      Connection conexion = null;
      if (tablaInsertar.getRowCount() - 1 <= 0) {
         DialogResultadoOperacion resultado = new DialogResultadoOperacion("No se puede grabar registro sin detalles! ", "Si");
         resultado.setLocationRelativeTo(frame);
         resultado.setVisible(true);
         tablaInsertar.requestFocusInWindow();
         tablaInsertar.changeSelection(0, 0, false, false);
         tablaInsertar.editCellAt(0, 0);
         return 0;
      } else {
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementUpdate = conexion.prepareStatement("UPDATE envios_detalle set cantidad_recibido=? where id =?");
            preparedStatement1 = conexion.prepareStatement("Update envios set recibido =? where nro_registro = ?");
            preparedStatement1.setInt(1, 1);
            preparedStatement1.setInt(2, nro_registro);
            preparedStatement1.executeUpdate();

            for (int fila = 0; fila < tablaInsertar.getRowCount(); fila++) {
               if (!String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tablaInsertar.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tablaInsertar.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tablaInsertar.getValueAt(fila, descripcioncolumn) != null) {
                  cantidad = Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."));
                  if (cantidad == 0.0) {
                     DialogResultadoOperacion resultado = new DialogResultadoOperacion("No se puede grabar registro con cantidad 0! ", "error");
                     resultado.setLocationRelativeTo(frame);
                     resultado.setVisible(true);
                     tablaInsertar.requestFocusInWindow();
                     tablaInsertar.changeSelection(0, fila, false, false);
                     tablaInsertar.editCellAt(0, fila);
                     return 0;
                  }

                  if (!String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))
                     .trim()
                     .equals(String.valueOf(tablaInsertar.getValueAt(fila, SWColumn)).trim())) {
                     preparedStatementUpdate.setDouble(1, cantidad);
                     preparedStatementUpdate.setInt(2, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))));
                     preparedStatementUpdate.addBatch();
                  }
               }
            }

            if (preparedStatementUpdate != null) {
               preparedStatementUpdate.executeBatch();
            }

            conexion.commit();
            return 1;
         } catch (Exception var21) {
            try {
               conexion.rollback();
            } catch (SQLException var20) {
               LogErrores.errores(var20, "Error al insertar Envios", frame);
            }

            LogErrores.errores(var21, "Error al insertar Envios..", frame);
         } finally {
            new CerrarRecursos(preparedStatementUpdate, preparedStatement1, frame, "Error al insertar Envios..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      }
   }

   public static int eliminarRecepcionDeposito(EnvioRecepcionE entidad, JinternalPadre frame) {
      int registro = entidad.getNro_registro();
      PreparedStatement preparedStatement2 = null;
      PreparedStatement preparedStatement1 = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement("delete from envios_detalle where nro_registro =? ");
         preparedStatement2.setInt(1, registro);
         preparedStatement2.executeUpdate();
         preparedStatement1 = conexion.prepareStatement("delete from envios where nro_registro =?");
         preparedStatement1.setInt(1, registro);
         preparedStatement1.executeUpdate();
         conexion.commit();
         return 1;
      } catch (Exception var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            LogErrores.errores(var12, "Error al eliminar Nota de Envio.", frame);
         }

         LogErrores.errores(var13, "Error al eliminar Nota de Envio..", frame);
      } finally {
         new CerrarRecursos(preparedStatement1, preparedStatement2, frame, "Error al eliminar Nota de Envio..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int ultimoRegistro(JinternalPadre frame) {
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      int var7;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("Select max(nro_registro) as mayor from envios where transferencia =1");
         rs = preparedStatement.executeQuery();
         if (!rs.next()) {
            return 0;
         }

         int siguienteRegistro = rs.getInt("mayor");
         var7 = siguienteRegistro;
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Envio..", frame);
         return 0;
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar Envio..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var7;
   }

   public EnvioRecepcionE(
      int nro_registro,
      String fecha,
      String hora_envio,
      int cod_deposito_origen,
      int cod_deposito_destino,
      String observacion,
      String usuario_alta,
      int transferencia,
      int recibido,
      String nombre_deposito_origen,
      String nombre_deposito_destino
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.hora_envio = hora_envio;
      this.cod_deposito_origen = cod_deposito_origen;
      this.cod_deposito_destino = cod_deposito_destino;
      this.observacion = observacion;
      this.usuario_alta = usuario_alta;
      this.transferencia = transferencia;
      this.recibido = recibido;
      this.nombre_deposito_origen = nombre_deposito_origen;
      this.nombre_deposito_destino = nombre_deposito_destino;
   }

   public EnvioRecepcionE(int nro_registro, int recibido) {
      this.nro_registro = nro_registro;
      this.recibido = recibido;
   }

   public int getNro_registro() {
      return this.nro_registro;
   }

   public void setNro_registro(int nro_registro) {
      this.nro_registro = nro_registro;
   }

   public int getCod_deposito_origen() {
      return this.cod_deposito_origen;
   }

   public void setCod_deposito_origen(int cod_deposito_origen) {
      this.cod_deposito_origen = cod_deposito_origen;
   }

   public int getCod_deposito_destino() {
      return this.cod_deposito_destino;
   }

   public void setCod_deposito_destino(int cod_deposito_destino) {
      this.cod_deposito_destino = cod_deposito_destino;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
   }

   public String getHora_envio() {
      return this.hora_envio;
   }

   public void setHora_envio(String hora_envio) {
      this.hora_envio = hora_envio;
   }

   public String getObservacion() {
      return this.observacion;
   }

   public void setObservacion(String observacion) {
      this.observacion = observacion;
   }

   public String getUsuario_alta() {
      return this.usuario_alta;
   }

   public void setUsuario_alta(String usuario_alta) {
      this.usuario_alta = usuario_alta;
   }

   public String getNombre_deposito_origen() {
      return this.nombre_deposito_origen;
   }

   public void setNombre_deposito_origen(String nombre_deposito_origen) {
      this.nombre_deposito_origen = nombre_deposito_origen;
   }

   public String getNombre_deposito_destino() {
      return this.nombre_deposito_destino;
   }

   public void setNombre_deposito_destino(String nombre_deposito_destino) {
      this.nombre_deposito_destino = nombre_deposito_destino;
   }

   public int getTransferencia() {
      return this.transferencia;
   }

   public void setTransferencia(int transferencia) {
      this.transferencia = transferencia;
   }

   public int getRecibido() {
      return this.recibido;
   }

   public void setRecibido(int recibido) {
      this.recibido = recibido;
   }
}
