package a009AjustePrecios;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesTablaDetalle.ValidacionesTabla;
import utilidadesVentanas.JinternalPadre;

public class AjustePreciosE {
   private int nro_registro;
   private int cod_lista_precio;
   private double margen_costo;
   private double margen_precio;
   private String fecha;
   private String observacion;
   private String nombre_lista;
   private String usuario_alta;
   private String fecha_alta;

   public static int ultimoRegistro(JinternalPadre frame) {
      ResultSet rsMayor = null;
      Connection conexion = null;
      PreparedStatement preparedStatementMayor = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementMayor = conexion.prepareStatement("Select max(nro_registro) as mayor from ajustes_precios");
         rsMayor = preparedStatementMayor.executeQuery();
         if (rsMayor.next()) {
            return rsMayor.getInt("mayor");
         }
      } catch (Exception var9) {
         LogErrores.errores(var9, "Error al seleccionar ultimo registro.", frame);
         return 0;
      } finally {
         new CerrarRecursos(preparedStatementMayor, rsMayor, frame, "Error al recuperar ultimo registro..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int ajustePrecios(AjustePreciosE entidad, JTable tabla, JinternalPadre frame) {
      int nroListaColumn = tabla.getColumn("Nro Lista").getModelIndex();
      int codigoColumn = tabla.getColumn("Código").getModelIndex();
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      int monedaColumn = tabla.getColumn("CodMon").getModelIndex();
      int precioAnteriorColumn = tabla.getColumn("Precio Anterior").getModelIndex();
      int precioNuevoColumn = tabla.getColumn("Precio Nuevo").getModelIndex();
      int precioCostoColumn = tabla.getColumn("Costo").getModelIndex();
      int margenPrecioColumn = tabla.getColumn("Margen Precio").getModelIndex();
      int margenCostoColumn = tabla.getColumn("Margen Costo").getModelIndex();
      int precioMinimoColumn = tabla.getColumn("Precio Minimo").getModelIndex();
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatement2 = null;
      PreparedStatement preparedStatementMayor = null;
      ResultSet rsMayor = null;
      int registro = 0;
      Connection conexion = null;
      if (ValidacionesTabla.validarSinDetalles(tabla, frame)) {
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementMayor = conexion.prepareStatement("Select max(nro_registro) as mayor from ajustes_precios");
            rsMayor = preparedStatementMayor.executeQuery();
            if (rsMayor.next()) {
               registro = rsMayor.getInt("mayor") + 1;
            }

            preparedStatement1 = conexion.prepareStatement(
               "INSERT INTO ajustes_precios (nro_registro,fecha,cod_lista_precio,margen_costo,margen_precio,observacion, fecha_alta, usuario_alta) VALUES (?,curdate(),?,?,?,?, NOW(), SUBSTRING_INDEX(USER(), '@', 1))"
            );
            preparedStatement1.setInt(1, registro);
            preparedStatement1.setInt(2, entidad.getCod_lista_precio());
            preparedStatement1.setDouble(3, entidad.getMargen_costo());
            preparedStatement1.setDouble(4, entidad.getMargen_precio());
            preparedStatement1.setString(5, entidad.getObservacion());
            preparedStatement1.executeUpdate();
            preparedStatement2 = conexion.prepareStatement(
               "INSERT INTO ajustes_precios_detalle (nro_registro,cod_lista,cod_moneda,cod_producto,precio_costo,nuevo_precio,precio_anterior,margen_precio,margen_costo,precio_minimo) VALUES (?,?,?,?,?,?,?,?,?,?)"
            );
            double v_precio_minimo = 0.0;
            double v_precio = 0.0;

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
               if (!String.valueOf(tabla.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tabla.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tabla.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tabla.getValueAt(fila, descripcioncolumn) != null) {
                  v_precio = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, precioNuevoColumn)).replace(".", "").replace(",", "."));
                  v_precio_minimo = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, precioMinimoColumn)).replace(".", "").replace(",", "."));
                  if (v_precio_minimo > v_precio) {
                     DialogResultadoOperacion rs = new DialogResultadoOperacion("Precio Minimo no puede ser mayor a Precio de Venta", "error");
                     rs.setLocationRelativeTo(frame);
                     rs.setVisible(true);
                     tabla.changeSelection(fila, tabla.getColumn("Código").getModelIndex(), false, false);
                     tabla.editCellAt(fila, tabla.getColumn("Código").getModelIndex());
                     return 0;
                  }

                  double precio = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, precioNuevoColumn)).replace(".", "").replace(",", "."));
                  if (ValidacionesTabla.validarCeros(tabla, fila, precio, "No se puede grabar registro con Precio 0! ", frame)) {
                     preparedStatement2.setInt(1, registro);
                     preparedStatement2.setInt(2, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, nroListaColumn))));
                     preparedStatement2.setInt(3, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, monedaColumn))));
                     preparedStatement2.setString(4, String.valueOf(tabla.getValueAt(fila, codigoColumn)));
                     preparedStatement2.setDouble(
                        5, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, precioCostoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatement2.setDouble(6, v_precio);
                     preparedStatement2.setDouble(
                        7, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, precioAnteriorColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatement2.setDouble(
                        8, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, margenPrecioColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatement2.setDouble(
                        9, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, margenCostoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatement2.setDouble(10, v_precio_minimo);
                     preparedStatement2.addBatch();
                  }
               }
            }

            preparedStatement2.executeBatch();
            conexion.commit();
            return 1;
         } catch (Exception var32) {
            try {
               conexion.rollback();
            } catch (SQLException var31) {
               LogErrores.errores(var31, "Error al insertar Ajuste de Precio.", frame);
            }

            LogErrores.errores(var32, "Error al insertar Ajuste de Precio..", frame);
         } finally {
            new CerrarRecursos(preparedStatement1, preparedStatement2, preparedStatementMayor, frame, "Error al insertar Ajuste de Precio..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static int actualizarAjusteprecio(AjustePreciosE entidad, JTable tablaInsertar, JinternalPadre frame) {
      int nroListaColumn = tablaInsertar.getColumn("Nro Lista").getModelIndex();
      int codigoColumn = tablaInsertar.getColumn("Código").getModelIndex();
      int descripcioncolumn = tablaInsertar.getColumn("Descripción").getModelIndex();
      int monedaColumn = tablaInsertar.getColumn("CodMon").getModelIndex();
      int precioCostoColumn = tablaInsertar.getColumn("Costo").getModelIndex();
      int precioAnteriorColumn = tablaInsertar.getColumn("Precio Anterior").getModelIndex();
      int precioNuevoColumn = tablaInsertar.getColumn("Precio Nuevo").getModelIndex();
      int margenPrecioColumn = tablaInsertar.getColumn("Margen Precio").getModelIndex();
      int margenCostoColumn = tablaInsertar.getColumn("Margen Costo").getModelIndex();
      int precioMinimoColumn = tablaInsertar.getColumn("Precio Minimo").getModelIndex();
      int primarioColumn = tablaInsertar.getColumn("Primario").getModelIndex();
      int SWColumn = tablaInsertar.getColumn("SW").getModelIndex();
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatementInsert = null;
      PreparedStatement preparedStatementDelete = null;
      PreparedStatement preparedStatementUpdate = null;
      int registro = entidad.getNro_registro();
      Connection conexion = null;
      if (ValidacionesTabla.validarSinDetalles(tablaInsertar, frame)) {
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementDelete = conexion.prepareStatement("delete from ajustes_precios_detalle where id = ?");
            preparedStatementInsert = conexion.prepareStatement(
               "INSERT INTO ajustes_precios_detalle (nro_registro,cod_lista,cod_moneda,cod_producto,precio_costo,nuevo_precio,precio_anterior,margen_precio,margen_costo,precio_minimo)values (?,?,?,?,?,?,?,?,?,?) "
            );
            preparedStatementUpdate = conexion.prepareStatement(
               "UPDATE ajustes_precios_detalle set cod_lista=?,cod_moneda=?,cod_producto=?,precio_costo=?,nuevo_precio=?,precio_anterior=?,margen_precio=?,margen_costo=?,precio_minimo=? where id =?"
            );
            preparedStatement1 = conexion.prepareStatement(
               "UPDATE ajustes_precios set cod_lista_precio=?,margen_costo=?,margen_precio=?,observacion=? where nro_registro =?"
            );
            preparedStatement1.setInt(1, entidad.getCod_lista_precio());
            preparedStatement1.setDouble(2, entidad.getMargen_costo());
            preparedStatement1.setDouble(3, entidad.getMargen_precio());
            preparedStatement1.setString(4, entidad.getObservacion());
            preparedStatement1.setInt(5, registro);
            preparedStatement1.executeUpdate();

            for (int fila = 0; fila < tablaInsertar.getRowCount(); fila++) {
               if (!String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tablaInsertar.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tablaInsertar.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tablaInsertar.getValueAt(fila, descripcioncolumn) != null) {
                  double v_precio = Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioNuevoColumn)).replace(".", "").replace(",", "."));
                  double v_precio_minimo = Double.parseDouble(
                     String.valueOf(tablaInsertar.getValueAt(fila, precioMinimoColumn)).replace(".", "").replace(",", ".")
                  );
                  if (v_precio_minimo > v_precio) {
                     DialogResultadoOperacion rs = new DialogResultadoOperacion("Precio Minimo no puede ser mayor a Precio de Venta", "error");
                     rs.setLocationRelativeTo(frame);
                     rs.setVisible(true);
                     tablaInsertar.changeSelection(fila, tablaInsertar.getColumn("Código").getModelIndex(), false, false);
                     tablaInsertar.editCellAt(fila, tablaInsertar.getColumn("Código").getModelIndex());
                     return 0;
                  }

                  double precio = Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioNuevoColumn)).replace(".", "").replace(",", "."));
                  if (!ValidacionesTabla.validarCeros(tablaInsertar, fila, precio, "No se puede grabar registro con Precio 0! ", frame)) {
                     return 0;
                  }

                  if (String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn)).trim().equals("0")) {
                     preparedStatementInsert.setInt(1, registro);
                     preparedStatementInsert.setInt(2, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, nroListaColumn))));
                     preparedStatementInsert.setInt(3, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, monedaColumn))));
                     preparedStatementInsert.setString(4, String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)));
                     preparedStatementInsert.setDouble(
                        5, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioCostoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.setDouble(
                        6, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioNuevoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.setDouble(
                        7, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioAnteriorColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.setDouble(
                        8, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, margenPrecioColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.setDouble(
                        9, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, margenCostoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.setDouble(
                        10, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioMinimoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.addBatch();
                  } else if (!String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))
                     .trim()
                     .equals(String.valueOf(tablaInsertar.getValueAt(fila, SWColumn)).trim())) {
                     preparedStatementUpdate.setInt(1, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, nroListaColumn))));
                     preparedStatementUpdate.setInt(2, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, monedaColumn))));
                     preparedStatementUpdate.setString(3, String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)));
                     preparedStatementInsert.setDouble(
                        4, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioCostoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setDouble(
                        5, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioNuevoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setDouble(
                        6, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioAnteriorColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setDouble(
                        7, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, margenPrecioColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setDouble(
                        8, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, margenCostoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setDouble(
                        9, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioMinimoColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setInt(10, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))));
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
         } catch (Exception var34) {
            try {
               conexion.rollback();
            } catch (SQLException var33) {
               LogErrores.errores(var33, "Error al actualizar Ajuste de Precio.", frame);
            }

            LogErrores.errores(var34, "Error al actualizar Ajuste de Precio..", frame);
            return 0;
         } finally {
            new CerrarRecursos(
               preparedStatement1, preparedStatementInsert, preparedStatementDelete, preparedStatementUpdate, frame, "Error al actualizar Ajuste de Precio.."
            );
            DatabaseConnection.getInstance().cerrarConexion();
         }
      } else {
         return 0;
      }
   }

   public static int eliminarAjustePrecio(AjustePreciosE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatement2 = null;
      Connection conexion = null;
      int registro = entidad.getNro_registro();

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement("delete from ajustes_precios_detalle where nro_registro =? ");
         preparedStatement2.setInt(1, registro);
         preparedStatement2.executeUpdate();
         preparedStatement1 = conexion.prepareStatement("delete from ajustes_precios where nro_registro =?");
         preparedStatement1.setInt(1, registro);
         preparedStatement1.executeUpdate();
         conexion.commit();
         return 1;
      } catch (Exception var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            LogErrores.errores(var12, "Error al eliminar Ajuste de Precio.", frame);
         }

         LogErrores.errores(var13, "Error al eliminar Ajuste de Precio..", frame);
      } finally {
         new CerrarRecursos(preparedStatement1, preparedStatement2, frame, "Error al eliminar Ajuste de Precio..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static AjustePreciosE buscarAjustePrecio(int registro, TablaAjustePreciosDetalles tabla, int par_decimal_importe, JinternalPadre frame) {
      ResultSet rs = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      FormatoDecimalOperacionSistema formatoDecimal2 = new FormatoDecimalOperacionSistema(2);
      ModeloTablaAjustePrecios modelo = (ModeloTablaAjustePrecios)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\najustes_precios_detalle.cod_producto,productos.nombre_producto,unidades_medidas.sigla,\r\najustes_precios_detalle.cod_moneda,monedas.nombre_moneda,ajustes_precios_detalle.precio_costo,\r\najustes_precios_detalle.precio_anterior,ajustes_precios_detalle.margen_precio,\r\najustes_precios_detalle.margen_costo,ajustes_precios_detalle.precio_minimo,\r\najustes_precios_detalle.nuevo_precio,ajustes_precios_detalle.id,\r\najustes_precios_detalle.cod_lista,\r\najustes_precios_detalle.id\r\n from ajustes_precios_detalle,productos,unidades_medidas,monedas\r\nwhere\r\najustes_precios_detalle.cod_producto = productos.cod_producto\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand ajustes_precios_detalle.cod_moneda = monedas.cod_moneda and ajustes_precios_detalle.nro_registro =?"
         );
         preparedStatement2.setInt(1, registro);
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            modelo.addRow(
               new Object[]{
                  rs2.getString("cod_producto"),
                  rs2.getString("nombre_producto"),
                  rs2.getString("sigla"),
                  rs2.getInt("cod_moneda"),
                  rs2.getString("nombre_moneda"),
                  formatoDecimalPrecio.format(rs2.getDouble("precio_costo")),
                  formatoDecimalPrecio.format(rs2.getDouble("precio_anterior")),
                  formatoDecimal2.format(rs2.getDouble("margen_precio")),
                  formatoDecimal2.format(rs2.getDouble("margen_costo")),
                  formatoDecimalPrecio.format(rs2.getDouble("precio_minimo")),
                  formatoDecimalPrecio.format(rs2.getDouble("nuevo_precio")),
                  rs2.getInt("id"),
                  rs2.getInt("cod_lista"),
                  rs2.getInt("id")
               }
            );
         }

         modelo.addDefaultRow();
         preparedStatement = conexion.prepareStatement(
            "select\r\najustes_precios.nro_registro,ajustes_precios.fecha,ajustes_precios.cod_lista_precio,\r\najustes_precios.margen_costo,ajustes_precios.margen_precio,\r\najustes_precios.observacion,lista_precios_cab.nombre_lista,ajustes_precios.fecha_alta,ajustes_precios.usuario_alta\r\n from ajustes_precios,lista_precios_cab\r\nwhere\r\najustes_precios.cod_lista_precio =lista_precios_cab.cod_lista and ajustes_precios.nro_registro =?"
         );
         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (rs.next()) {
            return new AjustePreciosE(
               registro,
               rs.getString("fecha"),
               rs.getInt("cod_lista_precio"),
               rs.getDouble("margen_costo"),
               rs.getDouble("margen_precio"),
               rs.getString("observacion"),
               rs.getString("nombre_lista"),
               rs.getString("fecha_alta"),
               rs.getString("usuario_alta")
            );
         }
      } catch (Exception var18) {
         LogErrores.errores(var18, "Error al seleccionar Ajuste de Precio..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatement2, rs, rs2, frame, "Error al seleccionar Ajuste de Precio..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static void buscarAjustePrecioTabla(String cod_producto, TablaAjustePreciosDetalles tabla, int par_decimal_importe, JinternalPadre frame) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      ModeloTablaAjustePrecios modelo = (ModeloTablaAjustePrecios)tabla.getModel();
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            " select\r\n  lista_precios_det.cod_producto,productos.nombre_producto,unidades_medidas.sigla,lista_precios_cab.nombre_lista,\r\n  lista_precios_cab.cod_moneda,monedas.nombre_moneda,productos.costo_producto,lista_precios_det.precio,lista_precios_det.precio_minimo,lista_precios_det.cod_lista\r\n  from lista_precios_det,productos,unidades_medidas,lista_precios_cab,monedas\r\nwhere\r\nlista_precios_det.cod_producto = productos.cod_producto\r\nand lista_precios_cab.cod_moneda = monedas.cod_moneda\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand lista_precios_det.cod_lista = lista_precios_cab.cod_lista\r\nand lista_precios_cab.estado =1\r\nand productos.estado = 1\r\nand lista_precios_det.cod_producto = ?\r\norder by lista_precios_det.cod_lista"
         );
         preparedStatement2.setString(1, cod_producto);
         rs2 = preparedStatement2.executeQuery();
         int fila = 0;
         if (tabla.getRowCount() - 1 == 0) {
            modelo.removeRow(0);
         }

         while (rs2.next()) {
            modelo.addRow(new Object[tabla.getColumnCount()]);
            fila = tabla.getRowCount() - 1;
            tabla.setValueAt(rs2.getString("cod_producto"), fila, tabla.getColumn("Código").getModelIndex());
            tabla.setValueAt(rs2.getString("nombre_producto"), fila, tabla.getColumn("Descripción").getModelIndex());
            tabla.setValueAt(rs2.getString("sigla"), fila, tabla.getColumn("UM").getModelIndex());
            tabla.setValueAt(rs2.getString("nombre_lista"), fila, tabla.getColumn("Lista de Precio").getModelIndex());
            tabla.setValueAt(rs2.getInt("cod_moneda"), fila, tabla.getColumn("CodMon").getModelIndex());
            tabla.setValueAt(rs2.getString("nombre_moneda"), fila, tabla.getColumn("Moneda").getModelIndex());
            tabla.setValueAt(formatoDecimalPrecio.format(rs2.getDouble("costo_producto")), fila, tabla.getColumn("Costo").getModelIndex());
            tabla.setValueAt(formatoDecimalPrecio.format(rs2.getDouble("precio")), fila, tabla.getColumn("Precio Anterior").getModelIndex());
            tabla.setValueAt(formatoDecimalPrecio.format(rs2.getDouble("precio")), fila, tabla.getColumn("Precio Nuevo").getModelIndex());
            tabla.setValueAt(formatoDecimalPrecio.format(rs2.getDouble("precio_minimo")), fila, tabla.getColumn("Precio Minimo").getModelIndex());
            tabla.setValueAt(rs2.getInt("cod_lista"), fila, tabla.getColumn("Nro Lista").getModelIndex());
            tabla.setValueAt(0, fila, tabla.getColumn("SW").getModelIndex());
            tabla.setValueAt(0, fila, tabla.getColumn("Primario").getModelIndex());
            tabla.setValueAt(0, fila, tabla.getColumn("Margen Precio").getModelIndex());
            tabla.setValueAt(0, fila, tabla.getColumn("Margen Costo").getModelIndex());
         }

         if (String.valueOf(tabla.getValueAt(fila, tabla.getColumn("Descripción").getModelIndex())).trim().equals("")
            || tabla.getValueAt(fila, tabla.getColumn("Descripción").getModelIndex()) == null) {
            modelo.removeRow(fila);
         }

         SwingUtilities.invokeLater(() -> {
            tabla.changeSelection(tabla.getRowCount() - 1, tabla.getColumn("Código").getModelIndex(), false, false);
            tabla.editCellAt(tabla.getRowCount() - 1, tabla.getColumn("Código").getModelIndex());
            Component editorComponent = tabla.getEditorComponent();
            if (editorComponent instanceof JTextComponent) {
               ((JTextComponent)editorComponent).selectAll();
            }

            editorComponent.requestFocusInWindow();
         });
      } catch (Exception var14) {
         LogErrores.errores(var14, "Error al Cargar Producto..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al Cargar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public AjustePreciosE(int nro_registro, int cod_lista_precio, double margen_costo, double margen_precio, String observacion) {
      this.nro_registro = nro_registro;
      this.cod_lista_precio = cod_lista_precio;
      this.margen_costo = margen_costo;
      this.margen_precio = margen_precio;
      this.observacion = observacion;
   }

   public AjustePreciosE(
      int nro_registro,
      String fecha,
      int cod_lista_precio,
      double margen_costo,
      double margen_precio,
      String observacion,
      String nombre_lista,
      String fecha_alta,
      String usuario_alta
   ) {
      this.nro_registro = nro_registro;
      this.fecha = fecha;
      this.cod_lista_precio = cod_lista_precio;
      this.margen_costo = margen_costo;
      this.margen_precio = margen_precio;
      this.observacion = observacion;
      this.nombre_lista = nombre_lista;
      this.fecha_alta = fecha_alta;
      this.usuario_alta = usuario_alta;
   }

   public int getNro_registro() {
      return this.nro_registro;
   }

   public void setNro_registro(int nro_registro) {
      this.nro_registro = nro_registro;
   }

   public int getCod_lista_precio() {
      return this.cod_lista_precio;
   }

   public void setCod_lista_precio(int cod_lista_precio) {
      this.cod_lista_precio = cod_lista_precio;
   }

   public double getMargen_costo() {
      return this.margen_costo;
   }

   public void setMargen_costo(double margen_costo) {
      this.margen_costo = margen_costo;
   }

   public double getMargen_precio() {
      return this.margen_precio;
   }

   public void setMargen_precio(double margen_precio) {
      this.margen_precio = margen_precio;
   }

   public String getNombre_lista() {
      return this.nombre_lista;
   }

   public void setNombre_lista(String nombre_lista) {
      this.nombre_lista = nombre_lista;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
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

   public String getFecha_alta() {
      return this.fecha_alta;
   }

   public void setFecha_alta(String fecha_alta) {
      this.fecha_alta = fecha_alta;
   }
}
