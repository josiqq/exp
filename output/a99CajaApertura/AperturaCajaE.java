package a99CajaApertura;

import a0099ListaPrecios.ModeloTablaListaPrecio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class AperturaCajaE {
   private int nro_planilla;
   private int cod_caja;
   private int cod_cajero;
   private int abierto;
   private String fecha;
   private String hora_cierre;
   private String nombre_caja;
   private String nombre_cajero;
   private double total_rendicion;
   private double total_sistema;

   public static int insertarCajaApertura(AperturaCajaE entidad, JTable tabla, JinternalPadre frame) {
      int codCondicionColumn = tabla.getColumn("Condicion").getModelIndex();
      int codMonedaColumn = tabla.getColumn("Moneda").getModelIndex();
      int importeColumn = tabla.getColumn("Importe").getModelIndex();
      Connection conexion = null;
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatement2 = null;
      PreparedStatement preparedStatementMayor = null;
      ResultSet rsMayor = null;
      int registroMayor = 0;
      if (!String.valueOf(tabla.getValueAt(0, codCondicionColumn)).trim().equals("")
         && tabla.getValueAt(0, codCondicionColumn) != null
         && !String.valueOf(tabla.getValueAt(0, codMonedaColumn)).trim().equals("")
         && tabla.getValueAt(0, codMonedaColumn) != null) {
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementMayor = conexion.prepareStatement("select ifnull(max(nro_planilla),0)+1 as mayor from caja_planilla");
            rsMayor = preparedStatementMayor.executeQuery();
            if (rsMayor.next()) {
               registroMayor = rsMayor.getInt("mayor");
            }

            preparedStatement1 = conexion.prepareStatement(
               "INSERT INTO caja_planilla (nro_planilla,fecha,hora_cierre,cod_caja,cod_cajero,total_rendicion,total_sistema,abierto,fecha_alta,usuario_alta) VALUES ( ?,?,curtime(),?,?,0,0,1,NOW(), SUBSTRING_INDEX(USER(), '@', 1))"
            );
            preparedStatement1.setInt(1, registroMayor);
            preparedStatement1.setString(2, entidad.getFecha());
            preparedStatement1.setInt(3, entidad.getCod_caja());
            preparedStatement1.setInt(4, entidad.getCod_cajero());
            preparedStatement1.executeUpdate();
            preparedStatement2 = conexion.prepareStatement(
               "INSERT INTO caja_planilla_apertura (nro_planilla,cod_condicion,cod_moneda,importe) VALUES (?,?,?,?)"
            );

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
               if (!String.valueOf(tabla.getValueAt(fila, codCondicionColumn)).trim().equals("")
                  && tabla.getValueAt(fila, codCondicionColumn) != null
                  && !String.valueOf(tabla.getValueAt(fila, codMonedaColumn)).trim().equals("")
                  && tabla.getValueAt(fila, codMonedaColumn) != null) {
                  preparedStatement2.setInt(1, registroMayor);
                  preparedStatement2.setInt(2, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, codCondicionColumn))));
                  preparedStatement2.setInt(3, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, codMonedaColumn))));
                  preparedStatement2.setDouble(4, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, importeColumn)).replace(".", "").replace(",", ".")));
                  preparedStatement2.addBatch();
               }
            }

            preparedStatement2.executeBatch();
            conexion.commit();
            return 1;
         } catch (Exception var19) {
            try {
               conexion.rollback();
            } catch (SQLException var18) {
               LogErrores.errores(var18, "Error al insertar Apertura de Caja.", frame);
            }

            LogErrores.errores(var19, "Error al insertar Apertura de Caja..", frame);
         } finally {
            new CerrarRecursos(preparedStatement1, preparedStatement2, preparedStatementMayor, frame, "Error al insertar Apertura de Caja..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No se puede grabar registro sin detalles! ", "correcto");
         rs.setLocationRelativeTo(frame);
         rs.setVisible(true);
         tabla.requestFocusInWindow();
         tabla.changeSelection(0, 0, false, false);
         tabla.editCellAt(0, 0);
         return 0;
      }
   }

   public static int actualizarAperturaCaja(AperturaCajaE entidad, JTable tablaInsertar, JinternalPadre frame, List<Integer> codigosAEliminar) {
      int codCondicionColumn = tablaInsertar.getColumn("Condicion").getModelIndex();
      int codMonedaColumn = tablaInsertar.getColumn("Moneda").getModelIndex();
      int importeColumn = tablaInsertar.getColumn("Importe").getModelIndex();
      int primarioColumn = tablaInsertar.getColumn("Primario").getModelIndex();
      int SWColumn = tablaInsertar.getColumn("SW").getModelIndex();
      PreparedStatement preparedStatementInsert = null;
      PreparedStatement preparedStatementDelete = null;
      PreparedStatement preparedStatementUpdate = null;
      Connection conexion = null;
      if (String.valueOf(tablaInsertar.getValueAt(0, codCondicionColumn)).trim().equals("")
         || tablaInsertar.getValueAt(0, codCondicionColumn) == null
         || String.valueOf(tablaInsertar.getValueAt(0, codMonedaColumn)).trim().equals("")
         || tablaInsertar.getValueAt(0, codMonedaColumn) == null) {
         LogErrores.errores(null, "No se puede grabar registro sin detalle", frame);
      }

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementDelete = conexion.prepareStatement("delete from caja_planilla_apertura where id = ?");
         preparedStatementInsert = conexion.prepareStatement(
            "INSERT INTO caja_planilla_apertura (nro_planilla,cod_condicion,cod_moneda,importe) VALUES (?,?,?,?)"
         );
         preparedStatementUpdate = conexion.prepareStatement("UPDATE caja_planilla_apertura set  cod_condicion=?,cod_moneda=?,importe=? where id =?");

         for (int id : codigosAEliminar) {
            preparedStatementDelete.setInt(1, id);
            preparedStatementDelete.addBatch();
         }

         for (int fila = 0; fila < tablaInsertar.getRowCount(); fila++) {
            if (!String.valueOf(tablaInsertar.getValueAt(fila, codCondicionColumn)).trim().equals("")
               && tablaInsertar.getValueAt(fila, codCondicionColumn) != null
               && !String.valueOf(tablaInsertar.getValueAt(fila, codMonedaColumn)).trim().equals("")
               && tablaInsertar.getValueAt(fila, codMonedaColumn) != null) {
               if (String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn)).trim().equals("0")) {
                  preparedStatementInsert.setInt(1, entidad.getNro_planilla());
                  preparedStatementInsert.setInt(2, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, codCondicionColumn))));
                  preparedStatementInsert.setInt(3, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, codMonedaColumn))));
                  preparedStatementInsert.setDouble(
                     4, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, importeColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementInsert.addBatch();
               } else if (!String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))
                  .trim()
                  .equals(String.valueOf(tablaInsertar.getValueAt(fila, SWColumn)).trim())) {
                  preparedStatementInsert.setInt(1, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, codCondicionColumn))));
                  preparedStatementInsert.setInt(2, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, codMonedaColumn))));
                  preparedStatementInsert.setDouble(
                     3, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, importeColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementUpdate.setInt(4, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))));
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
      } catch (Exception var21) {
         try {
            conexion.rollback();
         } catch (SQLException var20) {
            LogErrores.errores(var20, "Error al actualizar Apertura de Caja.", frame);
         }

         LogErrores.errores(var21, "Error al actualizar Apertura de Caja..", frame);
      } finally {
         new CerrarRecursos(preparedStatementInsert, preparedStatementDelete, preparedStatementDelete, frame, "Error al actualizar Apertura de Caja..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int eliminarAperturaCaja(AperturaCajaE entidad, JinternalPadre frame) {
      int registro = entidad.getNro_planilla();
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatement2 = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement("delete from caja_planilla_apertura where nro_planilla =? ");
         preparedStatement2.setInt(1, registro);
         preparedStatement2.executeUpdate();
         preparedStatement1 = conexion.prepareStatement("delete from caja_planilla where nro_planilla =?");
         preparedStatement1.setInt(1, registro);
         preparedStatement1.executeUpdate();
         conexion.commit();
         return 1;
      } catch (Exception var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            LogErrores.errores(var12, "Error al eliminar Apertura de Caja.", frame);
         }

         LogErrores.errores(var13, "Error al eliminar Apertura de Caja..", frame);
      } finally {
         new CerrarRecursos(preparedStatement1, preparedStatement2, frame, "Error al eliminar Apertura de Caja..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static AperturaCajaE buscarAperturaCaja(
      int registro, TablaDetalleAperturaCaja tabla, int par_decimal_cantidad, int par_decimal_importe, JinternalPadre frame
   ) {
      ResultSet rs = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      ModeloTablaListaPrecio modelo = (ModeloTablaListaPrecio)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\ncaja_planilla_apertura.cod_condicion,caja_planilla_apertura.cod_moneda,caja_planilla_apertura.importe,caja_planilla_apertura.id,\r\ncondiciones_pagos.nombre_condicion,monedas.nombre_moneda\r\n from caja_planilla_apertura,condiciones_pagos,monedas\r\nwhere\r\ncaja_planilla_apertura.cod_condicion = condiciones_pagos.cod_condicion\r\nand caja_planilla_apertura.cod_moneda = monedas.cod_moneda and caja_planilla_apertura.nro_planilla =? "
         );
         preparedStatement2.setInt(1, registro);
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            modelo.addRow(
               new Object[]{
                  rs2.getInt("cod_condicion"),
                  rs2.getString("nombre_condicion"),
                  rs2.getInt("cod_moneda"),
                  rs2.getString("nombre_moneda"),
                  formatoDecimalPrecio.format(rs2.getDouble("importe")),
                  rs2.getInt("id"),
                  rs2.getInt("id")
               }
            );
         }

         modelo.addDefaultRow();
         preparedStatement = conexion.prepareStatement(
            "select\r\ncaja_planilla.nro_planilla,caja_planilla.fecha,caja_planilla.hora_cierre,\r\ncaja_planilla.cod_caja,caja_planilla.cod_cajero,caja_planilla.total_rendicion,caja_planilla.total_sistema,caja_planilla.abierto,\r\ncuentas.nombre_cuenta,cajeros.nombre_cajero\r\n from caja_planilla,cuentas,cajeros\r\nwhere\r\ncaja_planilla.cod_caja = cuentas.cod_cuenta\r\nand caja_planilla.cod_cajero = cajeros.cod_cajero and caja_planilla.nro_planilla=?"
         );
         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (rs.next()) {
            return new AperturaCajaE(
               registro,
               rs.getString("fecha"),
               rs.getString("hora_cierre"),
               rs.getInt("cod_caja"),
               rs.getInt("cod_cajero"),
               rs.getDouble("total_rendicion"),
               rs.getDouble("total_sistema"),
               rs.getInt("abierto"),
               rs.getString("nombre_caja"),
               rs.getString("nombre_cajero")
            );
         }
      } catch (Exception var18) {
         LogErrores.errores(var18, "Error al seleccionar Apertura de Caja..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatement2, rs, rs2, frame, "Error al seleccionar Apertura de Caja..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static int buscarPlanillaAbierta(int nro_caja, TablaDetalleAperturaCaja tabla, int par_decimal_importe, JinternalPadre frame) {
      Connection conexion = null;
      PreparedStatement preparedStatementMayor = null;
      PreparedStatement preparedStatement2 = null;
      ResultSet rsMayor = null;
      ResultSet rs2 = null;
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      ModeloTablaAperturaCaja modelo = (ModeloTablaAperturaCaja)tabla.getModel();
      modelo.deleteRow(tabla);

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementMayor = conexion.prepareStatement("select ifnull(max(nro_planilla),0) as mayor from caja_planilla where cod_caja = ? and abierto =1");
         preparedStatementMayor.setInt(1, nro_caja);
         rsMayor = preparedStatementMayor.executeQuery();
         if (rsMayor.next()) {
            if (rsMayor.getInt("mayor") != 0) {
               preparedStatement2 = conexion.prepareStatement(
                  "select\r\ncaja_planilla_apertura.cod_condicion,caja_planilla_apertura.cod_moneda,caja_planilla_apertura.importe,caja_planilla_apertura.id,\r\ncondiciones_pagos.nombre_condicion,monedas.nombre_moneda\r\n from caja_planilla_apertura,condiciones_pagos,monedas\r\nwhere\r\ncaja_planilla_apertura.cod_condicion = condiciones_pagos.cod_condicion\r\nand caja_planilla_apertura.cod_moneda = monedas.cod_moneda and caja_planilla_apertura.nro_planilla =? "
               );
               preparedStatement2.setInt(1, rsMayor.getInt("mayor"));
               rs2 = preparedStatement2.executeQuery();

               while (rs2.next()) {
                  modelo.addRow(
                     new Object[]{
                        rs2.getInt("cod_condicion"),
                        rs2.getString("nombre_condicion"),
                        rs2.getInt("cod_moneda"),
                        rs2.getString("nombre_moneda"),
                        formatoDecimalPrecio.format(rs2.getDouble("importe")),
                        rs2.getInt("id"),
                        rs2.getInt("id")
                     }
                  );
               }

               modelo.addDefaultRow();
            }

            return rsMayor.getInt("mayor");
         }
      } catch (Exception var19) {
         try {
            conexion.rollback();
         } catch (SQLException var18) {
            LogErrores.errores(var18, "Error al seleccionar Planilla.", frame);
         }

         LogErrores.errores(var19, "Error al seleccionar Planilla..", frame);
         return 0;
      } finally {
         new CerrarRecursos(preparedStatementMayor, preparedStatement2, rsMayor, rs2, frame, "Error al seleccionar Planilla..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public AperturaCajaE(
      int nro_planilla,
      String fecha,
      String hora_cierre,
      int cod_caja,
      int cod_cajero,
      double total_rendicion,
      double total_sistema,
      int abierto,
      String nombre_caja,
      String nombre_cajero
   ) {
      this.nro_planilla = nro_planilla;
      this.fecha = fecha;
      this.hora_cierre = hora_cierre;
      this.cod_caja = cod_caja;
      this.cod_cajero = cod_cajero;
      this.total_rendicion = total_rendicion;
      this.total_sistema = total_sistema;
      this.abierto = abierto;
      this.nombre_caja = nombre_caja;
      this.nombre_cajero = nombre_cajero;
   }

   public AperturaCajaE(
      int nro_planilla, String fecha, String hora_cierre, int cod_caja, int cod_cajero, double total_rendicion, double total_sistema, int abierto
   ) {
      this.nro_planilla = nro_planilla;
      this.fecha = fecha;
      this.hora_cierre = hora_cierre;
      this.cod_caja = cod_caja;
      this.cod_cajero = cod_cajero;
      this.total_rendicion = total_rendicion;
      this.total_sistema = total_sistema;
      this.abierto = abierto;
   }

   public AperturaCajaE(int nro_planilla, String fecha, int cod_caja, int cod_cajero) {
      this.nro_planilla = nro_planilla;
      this.fecha = fecha;
      this.cod_caja = cod_caja;
      this.cod_cajero = cod_cajero;
   }

   public int getNro_planilla() {
      return this.nro_planilla;
   }

   public void setNro_planilla(int nro_planilla) {
      this.nro_planilla = nro_planilla;
   }

   public int getCod_caja() {
      return this.cod_caja;
   }

   public void setCod_caja(int cod_caja) {
      this.cod_caja = cod_caja;
   }

   public int getCod_cajero() {
      return this.cod_cajero;
   }

   public void setCod_cajero(int cod_cajero) {
      this.cod_cajero = cod_cajero;
   }

   public int getAbierto() {
      return this.abierto;
   }

   public void setAbierto(int abierto) {
      this.abierto = abierto;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
   }

   public String getHora_cierre() {
      return this.hora_cierre;
   }

   public void setHora_cierre(String hora_cierre) {
      this.hora_cierre = hora_cierre;
   }

   public String getNombre_caja() {
      return this.nombre_caja;
   }

   public void setNombre_caja(String nombre_caja) {
      this.nombre_caja = nombre_caja;
   }

   public String getNombre_cajero() {
      return this.nombre_cajero;
   }

   public void setNombre_cajero(String nombre_cajero) {
      this.nombre_cajero = nombre_cajero;
   }

   public double getTotal_rendicion() {
      return this.total_rendicion;
   }

   public void setTotal_rendicion(double total_rendicion) {
      this.total_rendicion = total_rendicion;
   }

   public double getTotal_sistema() {
      return this.total_sistema;
   }

   public void setTotal_sistema(double total_sistema) {
      this.total_sistema = total_sistema;
   }
}
