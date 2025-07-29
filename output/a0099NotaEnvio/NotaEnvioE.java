package a0099NotaEnvio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTablaDetalle.ValidacionesTabla;
import utilidadesVentanas.JinternalPadre;

public class NotaEnvioE {
   private int nro_registro;
   private int cod_deposito_origen;
   private int cod_deposito_destino;
   private int transferencia;
   private String fecha;
   private String hora_envio;
   private String observacion;
   private String usuario_alta;
   private String nombre_deposito_origen;
   private String nombre_deposito_destino;

   public static NotaEnvioE buscarNotaEnvio(int registro, JinternalPadre frame, TablaDetalleNotaEnvio tabla, int par_decimal_cantidad, int par_decimal_importe) {
      ResultSet rs = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalCantidad = new FormatoDecimalOperacionSistema(par_decimal_cantidad);
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      ModeloTablaNotaEnvio modelo = (ModeloTablaNotaEnvio)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\nenvios_detalle.cod_producto,productos.nombre_producto,unidades_medidas.sigla,\r\nenvios_detalle.cantidad_recibido,envios_detalle.costo,envios_detalle.tipo_fiscal,\r\nenvios_detalle.iva,envios_detalle.porcentaje_gravado,envios_detalle.id\r\nfrom envios_detalle, productos, unidades_medidas\r\nwhere\r\nenvios_detalle.cod_producto = productos.cod_producto\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida and envios_detalle.nro_registro =?"
         );
         preparedStatement2.setInt(1, registro);
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            modelo.addRow(
               new Object[]{
                  rs2.getString("cod_producto"),
                  rs2.getString("nombre_producto"),
                  rs2.getString("sigla"),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad_recibido")),
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
            "select\r\nenvios.nro_registro, envios.fecha,envios.hora_envio,\r\nenvios.cod_deposito_origen,envios.cod_deposito_destino,\r\nenvios.observacion,envios.usuario_alta,envios.transferencia,\r\n(select depositos.nombre_deposito from depositos where depositos.cod_deposito = envios.cod_deposito_origen)as nombre_deposito_origen,\r\n(select depositos.nombre_deposito from depositos where depositos.cod_deposito = envios.cod_deposito_destino)as nombre_deposito_destino\r\n from envios where envios.nro_registro =?"
         );
         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (rs.next()) {
            return new NotaEnvioE(
               rs.getInt("nro_registro"),
               rs.getString("fecha"),
               rs.getString("hora_envio"),
               rs.getInt("cod_deposito_origen"),
               rs.getInt("cod_deposito_destino"),
               rs.getString("observacion"),
               rs.getString("usuario_alta"),
               rs.getInt("transferencia"),
               rs.getString("nombre_deposito_origen"),
               rs.getString("nombre_deposito_destino")
            );
         }
      } catch (Exception var19) {
         LogErrores.errores(var19, "Error al seleccionar Ajuste de Stock..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatement2, rs, rs2, frame, "Error al seleccionar Ajuste de Stock..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static int insertarEnvios(NotaEnvioE entidad, JTable tabla, JinternalPadre frame) {
      int codigoColumn = tabla.getColumn("Código").getModelIndex();
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int costoColumn = tabla.getColumn("Costo").getModelIndex();
      int tipoFiscalColumn = tabla.getColumn("Tipo Fiscal").getModelIndex();
      int ivaColumn = tabla.getColumn("IVA").getModelIndex();
      int porcentaje_gravado_Column = tabla.getColumn("PorcGravado").getModelIndex();
      if (ValidacionesTabla.validarSinDetalles(tabla, frame)) {
         int lastInsertId = 0;
         PreparedStatement preparedStatement1 = null;
         PreparedStatement preparedStatement2 = null;
         ResultSet generatedKeys = null;
         Connection conexion = null;

         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatement1 = conexion.prepareStatement(
               "INSERT INTO envios (fecha,hora_envio,cod_deposito_origen,cod_deposito_destino,observacion,transferencia,recibido,fecha_alta,usuario_alta) VALUES (?,curtime(),?,?,?,0,0,now(), SUBSTRING_INDEX(USER(), '@', 1))",
               1
            );
            preparedStatement1.setString(1, entidad.getFecha());
            preparedStatement1.setInt(2, entidad.getCod_deposito_origen());
            preparedStatement1.setInt(3, entidad.getCod_deposito_destino());
            preparedStatement1.setString(4, entidad.getObservacion());
            preparedStatement1.executeUpdate();
            generatedKeys = preparedStatement1.getGeneratedKeys();
            if (generatedKeys.next()) {
               lastInsertId = generatedKeys.getInt(1);
            }

            preparedStatement2 = conexion.prepareStatement(
               "INSERT INTO envios_detalle (nro_registro,cod_producto,cantidad_recibido,cantidad_enviado,costo,tipo_fiscal,iva,porcentaje_gravado) VALUES (?,?,?,?,?,?,?,?)"
            );

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
               if (!String.valueOf(tabla.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tabla.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tabla.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tabla.getValueAt(fila, descripcioncolumn) != null) {
                  double cantidad = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."));
                  if (!ValidacionesTabla.validarCeros(tabla, fila, cantidad, "No se puede grabar registro con cantidad 0! ", frame)) {
                     return 0;
                  }

                  preparedStatement2.setInt(1, lastInsertId);
                  preparedStatement2.setString(2, String.valueOf(tabla.getValueAt(fila, codigoColumn)));
                  preparedStatement2.setDouble(3, cantidad);
                  preparedStatement2.setDouble(4, 0.0);
                  preparedStatement2.setDouble(5, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, costoColumn)).replace(".", "").replace(",", ".")));
                  preparedStatement2.setDouble(6, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn)).replace(",", ".")));
                  preparedStatement2.setDouble(7, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, ivaColumn)).replace(",", ".")));
                  preparedStatement2.setDouble(8, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, porcentaje_gravado_Column)).replace(",", ".")));
                  preparedStatement2.addBatch();
               }
            }

            preparedStatement2.executeBatch();
            conexion.commit();
            return 1;
         } catch (Exception var24) {
            try {
               conexion.rollback();
            } catch (SQLException var23) {
               LogErrores.errores(var23, "Error al insertar Nota de Envio.", frame);
            }

            LogErrores.errores(var24, "Error al insertar Nota de Envio..", frame);
         } finally {
            new CerrarRecursos(preparedStatement1, preparedStatement2, generatedKeys, frame, "Error al insertar Nota de Envio..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static int actualizarNotaEnvio(NotaEnvioE entidad, JinternalPadre frame, JTable tablaInsertar, List<Integer> codigosAEliminar) {
      int codigoColumn = tablaInsertar.getColumn("Código").getModelIndex();
      int descripcioncolumn = tablaInsertar.getColumn("Descripción").getModelIndex();
      int cantidadColumn = tablaInsertar.getColumn("Cantidad").getModelIndex();
      int costoColumn = tablaInsertar.getColumn("Costo").getModelIndex();
      int tipoFiscalColumn = tablaInsertar.getColumn("Tipo Fiscal").getModelIndex();
      int ivaColumn = tablaInsertar.getColumn("IVA").getModelIndex();
      int porcentaje_gravado_Column = tablaInsertar.getColumn("PorcGravado").getModelIndex();
      int primarioColumn = tablaInsertar.getColumn("Primario").getModelIndex();
      int SWColumn = tablaInsertar.getColumn("SW").getModelIndex();
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatementInsert = null;
      PreparedStatement preparedStatementDelete = null;
      PreparedStatement preparedStatementUpdate = null;
      Connection conexion = null;
      int registro = entidad.getNro_registro();
      if (ValidacionesTabla.validarSinDetalles(tablaInsertar, frame)) {
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementDelete = conexion.prepareStatement("delete from envios_detalle where id = ?");
            preparedStatementInsert = conexion.prepareStatement(
               "INSERT INTO envios_detalle (nro_registro,cod_producto,cantidad_recibido,cantidad_enviado,costo,tipo_fiscal,iva,porcentaje_gravado) VALUES (?,?,?,?,?,?,?,?)"
            );
            preparedStatementUpdate = conexion.prepareStatement(
               "UPDATE envios_detalle set cod_producto=?,cantidad_recibido=?,costo=?,tipo_fiscal=?,iva=?,porcentaje_gravado=? where id =?"
            );
            preparedStatement1 = conexion.prepareStatement(
               "Update envios set fecha= ?,cod_deposito_origen= ?,cod_deposito_destino= ?,observacion=? where nro_registro = ?"
            );
            preparedStatement1.setString(1, entidad.getFecha());
            preparedStatement1.setInt(2, entidad.getCod_deposito_origen());
            preparedStatement1.setInt(3, entidad.getCod_deposito_destino());
            preparedStatement1.setString(4, entidad.getObservacion());
            preparedStatement1.setInt(5, registro);
            preparedStatement1.executeUpdate();

            for (int id : codigosAEliminar) {
               preparedStatementDelete.setInt(1, id);
               preparedStatementDelete.addBatch();
            }

            for (int fila = 0; fila < tablaInsertar.getRowCount(); fila++) {
               if (!String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tablaInsertar.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tablaInsertar.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tablaInsertar.getValueAt(fila, descripcioncolumn) != null) {
                  double cantidad = Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."));
                  if (!ValidacionesTabla.validarCeros(tablaInsertar, fila, cantidad, "No se puede grabar registro con cantidad 0! ", frame)) {
                     return 0;
                  }

                  if (String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn)).trim().equals("0")) {
                     preparedStatementInsert.setInt(1, registro);
                     preparedStatementInsert.setString(2, String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)));
                     preparedStatementInsert.setDouble(3, cantidad);
                     preparedStatementInsert.setDouble(4, 0.0);
                     preparedStatementInsert.setDouble(
                        5, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, costoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.setDouble(
                        6, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, tipoFiscalColumn)).replace(",", "."))
                     );
                     preparedStatementInsert.setDouble(7, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, ivaColumn)).replace(",", ".")));
                     preparedStatementInsert.setDouble(
                        8, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, porcentaje_gravado_Column)).replace(",", "."))
                     );
                     preparedStatementInsert.addBatch();
                  } else if (!String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))
                     .trim()
                     .equals(String.valueOf(tablaInsertar.getValueAt(fila, SWColumn)).trim())) {
                     preparedStatementUpdate.setString(1, String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)));
                     preparedStatementUpdate.setDouble(2, cantidad);
                     preparedStatementUpdate.setDouble(
                        3, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, costoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setDouble(
                        4, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, tipoFiscalColumn)).replace(",", "."))
                     );
                     preparedStatementUpdate.setDouble(5, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, ivaColumn)).replace(",", ".")));
                     preparedStatementUpdate.setDouble(
                        6, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, porcentaje_gravado_Column)).replace(",", "."))
                     );
                     preparedStatementUpdate.setInt(7, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))));
                     preparedStatementUpdate.addBatch();
                  }
               }
            }

            if (preparedStatementInsert != null) {
               preparedStatementInsert.executeBatch();
            }

            if (preparedStatementUpdate != null) {
               preparedStatementUpdate.executeBatch();
            }

            if (preparedStatementDelete != null) {
               preparedStatementDelete.executeBatch();
            }

            conexion.commit();
            return 1;
         } catch (Exception var28) {
            try {
               conexion.rollback();
            } catch (SQLException var27) {
               LogErrores.errores(var27, "Error al insertar Envios", frame);
            }

            LogErrores.errores(var28, "Error al insertar Envios..", frame);
         } finally {
            new CerrarRecursos(
               preparedStatementUpdate, preparedStatement1, preparedStatementInsert, preparedStatementDelete, frame, "Error al insertar Envios.."
            );
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static int eliminarNotaEnvio(NotaEnvioE entidad, JinternalPadre frame) {
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
         preparedStatement = conexion.prepareStatement("Select max(nro_registro) as mayor from envios where transferencia =0");
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

   public NotaEnvioE(
      int nro_registro,
      String fecha,
      String hora_envio,
      int cod_deposito_origen,
      int cod_deposito_destino,
      String observacion,
      String usuario_alta,
      int transferencia,
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
      this.nombre_deposito_origen = nombre_deposito_origen;
      this.nombre_deposito_destino = nombre_deposito_destino;
   }

   public NotaEnvioE(int nro_registro, String fecha, int cod_deposito_origen, int cod_deposito_destino, String observacion) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_deposito_origen = cod_deposito_origen;
      this.cod_deposito_destino = cod_deposito_destino;
      this.observacion = observacion;
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
}
