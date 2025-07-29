package a0099ListaPrecios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JTable;
import utilidades.LabelPrincipal;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadreReporte;

public class ListaPreciosE {
   private int cod_lista;
   private int estado;
   private int cod_sucursal;
   private int cod_moneda;
   private int iva;
   private String nombre_lista;
   private String nombre_sucursal;
   private String nombre_moneda;
   private String observacion;

   public static void buscarListaPreciosTablaReporte(
      int tipo_precio,
      String cod_producto,
      int cod_proveedor,
      int cod_marca,
      int cod_seccion,
      int cod_sub_seccion,
      int cod_grupo,
      JTable tabla,
      int par_decimal_importe,
      JinternalPadreReporte frame
   ) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      ModeloTablaDefecto modelo = (ModeloTablaDefecto)tabla.getModel();
      modelo.deleteRow(tabla);
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         if (tipo_precio == 0) {
            preparedStatement2 = conexion.prepareStatement(
               "select\r\nproductos.cod_producto,productos.nombre_producto,monedas.sigla,\r\nlista_precios_det.precio\r\nfrom productos,marcas,proveedores,lista_precios_det,monedas\r\nwhere\r\nproductos.cod_marca =marcas.cod_marca\r\nand lista_precios_det.cod_moneda = monedas.cod_moneda and lista_precios_det.precio =0\r\nand productos.cod_producto = lista_precios_det.cod_producto\r\nand productos.cod_proveedor = proveedores.cod_proveedor and ((productos.cod_producto = ?) or (? =0))\r\nand ((productos.cod_marca = ?) or (? =0))\r\nand ((productos.cod_proveedor = ?) or (? =0))\r\nand ((productos.cod_seccion = ?) or (? =0))\r\nand ((productos.cod_sub_seccion = ?) or (? =0))\r\nand ((productos.cod_grupo = ?) or (? =0))"
            );
         } else {
            preparedStatement2 = conexion.prepareStatement(
               "select\r\nproductos.cod_producto,productos.nombre_producto,monedas.sigla,\r\nlista_precios_det.precio\r\nfrom productos,marcas,proveedores,lista_precios_det,monedas\r\nwhere\r\nproductos.cod_marca =marcas.cod_marca\r\nand lista_precios_det.cod_moneda = monedas.cod_moneda\r\nand productos.cod_producto = lista_precios_det.cod_producto\r\nand productos.cod_proveedor = proveedores.cod_proveedor and ((productos.cod_producto = ?) or (? =0))\r\nand ((productos.cod_marca = ?) or (? =0))\r\nand ((productos.cod_proveedor = ?) or (? =0))\r\nand ((productos.cod_seccion = ?) or (? =0))\r\nand ((productos.cod_sub_seccion = ?) or (? =0))\r\nand ((productos.cod_grupo = ?) or (? =0))"
            );
         }

         preparedStatement2.setString(1, cod_producto);
         preparedStatement2.setString(2, cod_producto);
         preparedStatement2.setInt(3, cod_marca);
         preparedStatement2.setInt(4, cod_marca);
         preparedStatement2.setInt(5, cod_proveedor);
         preparedStatement2.setInt(6, cod_proveedor);
         preparedStatement2.setInt(7, cod_seccion);
         preparedStatement2.setInt(8, cod_seccion);
         preparedStatement2.setInt(9, cod_sub_seccion);
         preparedStatement2.setInt(10, cod_sub_seccion);
         preparedStatement2.setInt(11, cod_grupo);
         preparedStatement2.setInt(12, cod_grupo);
         rs2 = preparedStatement2.executeQuery();
         int fila = 0;
         if (tabla.getRowCount() - 1 == 0) {
            modelo.removeRow(0);
         }

         while (rs2.next()) {
            modelo.addRow(new Object[tabla.getColumnCount()]);
            fila = tabla.getRowCount() - 1;
            tabla.setValueAt(rs2.getString("cod_producto"), fila, tabla.getColumn("Codigo").getModelIndex());
            tabla.setValueAt(rs2.getString("nombre_producto"), fila, tabla.getColumn("Nombre").getModelIndex());
            tabla.setValueAt(formatoDecimalPrecio.format(rs2.getDouble("precio")), fila, tabla.getColumn("Precio").getModelIndex());
            tabla.setValueAt(rs2.getString("sigla"), fila, tabla.getColumn("Moneda").getModelIndex());
         }

         if (String.valueOf(tabla.getValueAt(fila, tabla.getColumn("Nombre").getModelIndex())).trim().equals("")
            || tabla.getValueAt(fila, tabla.getColumn("Nombre").getModelIndex()) == null) {
            modelo.removeRow(fila);
         }
      } catch (Exception var20) {
         LogErrores.errores(var20, "Error al Cargar Producto..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al Cargar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static int ultimoRegistro(JinternalPadre frame) {
      ResultSet rsMayor = null;
      Connection conexion = null;
      PreparedStatement preparedStatementMayor = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementMayor = conexion.prepareStatement("Select max(cod_lista) as mayor from lista_precios_cab");
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

   public static int insertarPrecios(ListaPreciosE entidad, JTable tabla, JinternalPadre frame) {
      int codigoColumn = tabla.getColumn("Código").getModelIndex();
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      Connection conexion = null;
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatement2 = null;
      PreparedStatement preparedStatementMayor = null;
      ResultSet rsMayor = null;
      int registroMayor = 0;
      if (!String.valueOf(tabla.getValueAt(0, codigoColumn)).trim().equals("")
         && tabla.getValueAt(0, codigoColumn) != null
         && !String.valueOf(tabla.getValueAt(0, descripcioncolumn)).trim().equals("")
         && tabla.getValueAt(0, descripcioncolumn) != null) {
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementMayor = conexion.prepareStatement("Select max(cod_lista) as mayor from lista_precios_cab");
            rsMayor = preparedStatementMayor.executeQuery();
            if (rsMayor.next()) {
               registroMayor = rsMayor.getInt("mayor") + 1;
            }

            preparedStatement1 = conexion.prepareStatement(
               "INSERT INTO lista_precios_cab (cod_lista,nombre_lista,estado,cod_sucursal,cod_moneda,observacion,iva,fecha_alta,usuario_alta) VALUES ( ?,?,?,?,?,?,?,NOW(), SUBSTRING_INDEX(USER(), '@', 1))"
            );
            preparedStatement1.setInt(1, registroMayor);
            preparedStatement1.setString(2, entidad.getNombre_lista());
            preparedStatement1.setInt(3, entidad.getEstado());
            preparedStatement1.setInt(4, entidad.getCod_sucursal());
            preparedStatement1.setInt(5, entidad.getCod_moneda());
            preparedStatement1.setString(6, entidad.getObservacion());
            preparedStatement1.setInt(7, entidad.getIva());
            preparedStatement1.executeUpdate();
            preparedStatement2 = conexion.prepareStatement(
               "INSERT INTO lista_precios_det (cod_lista,cod_producto,precio,precio_minimo,cod_moneda,iva) VALUES (?,?,0,0,?,?)"
            );

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
               if (!String.valueOf(tabla.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tabla.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tabla.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tabla.getValueAt(fila, descripcioncolumn) != null) {
                  preparedStatement2.setInt(1, registroMayor);
                  preparedStatement2.setString(2, String.valueOf(tabla.getValueAt(fila, codigoColumn)));
                  preparedStatement2.setInt(3, entidad.getCod_moneda());
                  preparedStatement2.setInt(4, entidad.getIva());
                  preparedStatement2.addBatch();
               }
            }

            preparedStatement2.executeBatch();
            conexion.commit();
            return 1;
         } catch (Exception var18) {
            try {
               conexion.rollback();
            } catch (SQLException var17) {
               LogErrores.errores(var17, "Error al insertar Lista de Precio.", frame);
            }

            LogErrores.errores(var18, "Error al insertar Lista de Precio..", frame);
         } finally {
            new CerrarRecursos(preparedStatement1, preparedStatement2, preparedStatementMayor, frame, "Error al insertar Lista de Precio..");
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

   public static int actualizarListaPrecios(ListaPreciosE entidad, JTable tablaInsertar, JinternalPadre frame, List<Integer> codigosAEliminar) {
      int codigoColumn = tablaInsertar.getColumn("Código").getModelIndex();
      int descripcioncolumn = tablaInsertar.getColumn("Descripción").getModelIndex();
      int primarioColumn = tablaInsertar.getColumn("Primario").getModelIndex();
      int SWColumn = tablaInsertar.getColumn("SW").getModelIndex();
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatementInsert = null;
      PreparedStatement preparedStatementDelete = null;
      PreparedStatement preparedStatementUpdate = null;
      Connection conexion = null;
      if (String.valueOf(tablaInsertar.getValueAt(0, codigoColumn)).trim().equals("")
         || tablaInsertar.getValueAt(0, codigoColumn) == null
         || String.valueOf(tablaInsertar.getValueAt(0, descripcioncolumn)).trim().equals("")
         || tablaInsertar.getValueAt(0, descripcioncolumn) == null) {
         LogErrores.errores(null, "No se puede grabar registro sin detalle", frame);
      }

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementDelete = conexion.prepareStatement("delete from lista_precios_det where id = ?");
         preparedStatementInsert = conexion.prepareStatement(
            "INSERT INTO lista_precios_det (cod_lista,cod_producto,precio,precio_minimo,cod_moneda,iva) VALUES (?,?,0,0,?,?)"
         );
         preparedStatementUpdate = conexion.prepareStatement("UPDATE lista_precios_det set  cod_producto=?,cod_moneda=?,iva=? where id =?");
         preparedStatement1 = conexion.prepareStatement(
            "Update lista_precios_cab set nombre_lista=?,estado=?,cod_sucursal=?,cod_moneda=?,observacion=?,iva=? where cod_lista = ?"
         );
         preparedStatement1.setString(1, entidad.getNombre_lista());
         preparedStatement1.setInt(2, entidad.getEstado());
         preparedStatement1.setInt(3, entidad.getCod_sucursal());
         preparedStatement1.setInt(4, entidad.getCod_moneda());
         preparedStatement1.setString(5, entidad.getObservacion());
         preparedStatement1.setInt(6, entidad.getIva());
         preparedStatement1.setInt(7, entidad.getCod_lista());
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
               if (String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn)).trim().equals("0")) {
                  preparedStatementInsert.setInt(1, entidad.getCod_lista());
                  preparedStatementInsert.setString(2, String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)));
                  preparedStatementInsert.setInt(3, entidad.getCod_moneda());
                  preparedStatementInsert.setInt(4, entidad.getIva());
                  preparedStatementInsert.addBatch();
               } else if (!String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))
                  .trim()
                  .equals(String.valueOf(tablaInsertar.getValueAt(fila, SWColumn)).trim())) {
                  preparedStatementUpdate.setString(1, String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)));
                  preparedStatementUpdate.setInt(2, entidad.getCod_moneda());
                  preparedStatementUpdate.setInt(3, entidad.getIva());
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
            LogErrores.errores(var20, "Error al actualizar Lista de Precio.", frame);
         }

         LogErrores.errores(var21, "Error al actualizar Lista de Precio..", frame);
      } finally {
         new CerrarRecursos(
            preparedStatement1, preparedStatementInsert, preparedStatementDelete, preparedStatementDelete, frame, "Error al insertar Lista de Precio.."
         );
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int eliminarListaPrecios(ListaPreciosE entidad, JinternalPadre frame) {
      int registro = entidad.getCod_lista();
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatement2 = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement("delete from lista_precios_det where cod_lista =? ");
         preparedStatement2.setInt(1, registro);
         preparedStatement2.executeUpdate();
         preparedStatement1 = conexion.prepareStatement("delete from lista_precios_cab where cod_lista =?");
         preparedStatement1.setInt(1, registro);
         preparedStatement1.executeUpdate();
         conexion.commit();
         return 1;
      } catch (Exception var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            LogErrores.errores(var12, "Error al eliminar Lista de Precio.", frame);
         }

         LogErrores.errores(var13, "Error al eliminar Lista de Precio..", frame);
      } finally {
         new CerrarRecursos(preparedStatement1, preparedStatement2, frame, "Error al eliminar Lista de Precio..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static ListaPreciosE buscarPListaPrecio(
      int registro, TablaDetalleListaPrecios tabla, int par_decimal_cantidad, int par_decimal_importe, JinternalPadre frame
   ) {
      ResultSet rs = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      FormatoDecimalOperacionSistema formatoDecimal2 = new FormatoDecimalOperacionSistema(2);
      ModeloTablaListaPrecio modelo = (ModeloTablaListaPrecio)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\nlista_precios_det.cod_lista,lista_precios_det.cod_producto,productos.nombre_producto,lista_precios_det.id,\r\nlista_precios_det.precio,lista_precios_det.precio_minimo,lista_precios_det.cod_moneda,lista_precios_det.iva,unidades_medidas.sigla\r\n from lista_precios_det,unidades_medidas,productos\r\nwhere\r\nlista_precios_det.cod_producto = productos.cod_producto\r\nand unidades_medidas.cod_unidad_medida = productos.cod_unidad_medida and lista_precios_det.cod_lista =? "
         );
         preparedStatement2.setInt(1, registro);
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            modelo.addRow(
               new Object[]{
                  rs2.getString("cod_producto"),
                  rs2.getString("nombre_producto"),
                  rs2.getString("sigla"),
                  formatoDecimalPrecio.format(rs2.getDouble("precio")),
                  rs2.getBoolean("iva"),
                  formatoDecimal2.format(rs2.getDouble("precio_minimo")),
                  rs2.getInt("cod_moneda"),
                  rs2.getInt("id")
               }
            );
         }

         modelo.addDefaultRow();
         preparedStatement = conexion.prepareStatement(
            "select\r\nlista_precios_cab.cod_lista,lista_precios_cab.nombre_lista,lista_precios_cab.estado,\r\nlista_precios_cab.cod_sucursal,lista_precios_cab.cod_moneda,lista_precios_cab.observacion,lista_precios_cab.iva,sucursales.nombre_sucursal,\r\nmonedas.nombre_moneda\r\nfrom\r\nlista_precios_cab,sucursales,monedas\r\nwhere\r\nlista_precios_cab.cod_sucursal = sucursales.cod_sucursal\r\nand lista_precios_cab.cod_moneda = monedas.cod_moneda and lista_precios_cab.cod_lista =?"
         );
         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (rs.next()) {
            return new ListaPreciosE(
               registro,
               rs.getString("nombre_lista"),
               rs.getInt("estado"),
               rs.getInt("cod_sucursal"),
               rs.getInt("cod_moneda"),
               rs.getString("observacion"),
               rs.getInt("iva"),
               rs.getString("nombre_sucursal"),
               rs.getString("nombre_moneda")
            );
         }
      } catch (Exception var19) {
         LogErrores.errores(var19, "Error al seleccionar Lista de Precio..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatement2, rs, rs2, frame, "Error al seleccionar Lista de Precio..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static ListaPreciosE buscarListaPrecio3(int codigo, JinternalPadre frame) {
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      ListaPreciosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("select cod_lista,nombre_lista,cod_moneda from lista_precios_cab where cod_lista =1");
         preparedStatement.setInt(1, codigo);
         rs = preparedStatement.executeQuery();
         if (!rs.next()) {
            DialogResultadoOperacion ventana = new DialogResultadoOperacion("No existe Lista de Precio con codigo: " + codigo, "error");
            ventana.setLocationRelativeTo(frame);
            ventana.setVisible(true);
            return null;
         }

         ListaPreciosE entidad = new ListaPreciosE(rs.getInt("cod_lista"), rs.getString("nombre_lista"), rs.getInt("cod_moneda"));
         var8 = entidad;
      } catch (Exception var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            LogErrores.errores(var12, "Error al seleccionar Lista de Precio.", frame);
         }

         LogErrores.errores(var13, "Error al seleccionar Lista de Precio..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, frame, "Error al seleccionar Lista de Precio..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static ListaPreciosE buscarListaPrecio2(int codigo, JinternalPadre frame) {
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      ListaPreciosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("select cod_lista,nombre_lista from lista_precios_cab where estado =1 and cod_lista=?");
         preparedStatement.setInt(1, codigo);
         rs = preparedStatement.executeQuery();
         if (!rs.next()) {
            DialogResultadoOperacion ventana = new DialogResultadoOperacion("No existe Lista de Precio con codigo: " + codigo, "error");
            ventana.setLocationRelativeTo(frame);
            ventana.setVisible(true);
            return null;
         }

         ListaPreciosE entidad = new ListaPreciosE(rs.getInt("cod_lista"), rs.getString("nombre_lista"));
         var8 = entidad;
      } catch (Exception var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            LogErrores.errores(var12, "Error al seleccionar Lista de Precio.", frame);
         }

         LogErrores.errores(var13, "Error al seleccionar Lista de Precio..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, frame, "Error al seleccionar Lista de Precio..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void cargarVendedoresTabla3(String sql, ModeloTablaDefecto modelo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt(1), resultado.getString(2), resultado.getInt(1)});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Lista de Precios..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Lista de Precios..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarVendedoresTabla2(String sql, ModeloTablaDefecto modelo, JDialog frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt(1), resultado.getString(2)});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Lista de Precios..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Lista de Precios..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void buscarListaPrecioTabla(
      boolean iva,
      int cod_moneda,
      int cod_seccion,
      int cod_sub_seccion,
      int cod_grupo,
      TablaDetalleListaPrecios tabla,
      int par_decimal_importe,
      LabelPrincipal lblTextoTotalLinea,
      JinternalPadre frame
   ) {
      ResultSet rs2 = null;
      PreparedStatement preparedStatement2 = null;
      ModeloTablaListaPrecio modelo = (ModeloTablaListaPrecio)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\n      productos.cod_producto,productos.nombre_producto,unidades_medidas.sigla \r\nfrom productos,unidades_medidas\r\nwhere\r\nproductos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand ((productos.cod_seccion = ?) or (? =0))\r\nand ((productos.cod_sub_seccion = ?) or (? =0))\r\nand ((productos.cod_grupo = ?) or (? =0))"
         );
         preparedStatement2.setInt(1, cod_seccion);
         preparedStatement2.setInt(2, cod_seccion);
         preparedStatement2.setInt(3, cod_sub_seccion);
         preparedStatement2.setInt(4, cod_sub_seccion);
         preparedStatement2.setInt(5, cod_grupo);
         preparedStatement2.setInt(6, cod_grupo);
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
            tabla.setValueAt(0, fila, tabla.getColumn("Precio").getModelIndex());
            tabla.setValueAt(iva, fila, tabla.getColumn("IVA").getModelIndex());
            tabla.setValueAt(0, fila, tabla.getColumn("Precio Minimo").getModelIndex());
            tabla.setValueAt(cod_moneda, fila, tabla.getColumn("Moneda").getModelIndex());
            tabla.setValueAt(0, fila, tabla.getColumn("SW").getModelIndex());
            tabla.setValueAt(0, fila, tabla.getColumn("Primario").getModelIndex());
         }

         if (String.valueOf(tabla.getValueAt(fila, tabla.getColumn("Descripción").getModelIndex())).trim().equals("")
            || tabla.getValueAt(fila, tabla.getColumn("Descripción").getModelIndex()) == null) {
            modelo.removeRow(fila);
         }

         FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
         lblTextoTotalLinea.setText(String.valueOf(formatoLinea.format(tabla.getRowCount() + 1)));
      } catch (Exception var19) {
         LogErrores.errores(var19, "Error al Cargar Producto..", frame);
      } finally {
         new CerrarRecursos(preparedStatement2, rs2, frame, "Error al Cargar Producto..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public ListaPreciosE(int cod_lista, String nombre_lista, int cod_moneda) {
      this.cod_lista = cod_lista;
      this.nombre_lista = nombre_lista;
      this.cod_moneda = cod_moneda;
   }

   public ListaPreciosE(int cod_lista, String nombre_lista) {
      this.cod_lista = cod_lista;
      this.nombre_lista = nombre_lista;
   }

   public ListaPreciosE(int cod_lista, String nombre_lista, int cod_moneda, String nombre_moneda) {
      this.cod_lista = cod_lista;
      this.nombre_lista = nombre_lista;
      this.cod_moneda = cod_moneda;
      this.nombre_moneda = nombre_moneda;
   }

   public ListaPreciosE(
      int cod_lista,
      String nombre_lista,
      int estado,
      int cod_sucursal,
      int cod_moneda,
      String observacion,
      int iva,
      String nombre_sucursal,
      String nombre_moneda
   ) {
      this.cod_lista = cod_lista;
      this.nombre_lista = nombre_lista;
      this.estado = estado;
      this.cod_sucursal = cod_sucursal;
      this.cod_moneda = cod_moneda;
      this.observacion = observacion;
      this.iva = iva;
      this.nombre_sucursal = nombre_sucursal;
      this.nombre_moneda = nombre_moneda;
   }

   public ListaPreciosE(int cod_lista, String nombre_lista, int estado, int cod_sucursal, int cod_moneda, String observacion, int iva) {
      this.cod_lista = cod_lista;
      this.nombre_lista = nombre_lista;
      this.estado = estado;
      this.cod_sucursal = cod_sucursal;
      this.cod_moneda = cod_moneda;
      this.observacion = observacion;
      this.iva = iva;
   }

   public int getCod_lista() {
      return this.cod_lista;
   }

   public void setCod_lista(int cod_lista) {
      this.cod_lista = cod_lista;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public int getCod_sucursal() {
      return this.cod_sucursal;
   }

   public void setCod_sucursal(int cod_sucursal) {
      this.cod_sucursal = cod_sucursal;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public void setCod_moneda(int cod_moneda) {
      this.cod_moneda = cod_moneda;
   }

   public int getIva() {
      return this.iva;
   }

   public void setIva(int iva) {
      this.iva = iva;
   }

   public String getNombre_lista() {
      return this.nombre_lista;
   }

   public void setNombre_lista(String nombre_lista) {
      this.nombre_lista = nombre_lista;
   }

   public String getNombre_sucursal() {
      return this.nombre_sucursal;
   }

   public void setNombre_sucursal(String nombre_sucursal) {
      this.nombre_sucursal = nombre_sucursal;
   }

   public String getNombre_moneda() {
      return this.nombre_moneda;
   }

   public void setNombre_moneda(String nombre_moneda) {
      this.nombre_moneda = nombre_moneda;
   }

   public String getObservacion() {
      return this.observacion;
   }

   public void setObservacion(String observacion) {
      this.observacion = observacion;
   }
}
