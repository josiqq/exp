package a00pedidosProveedores;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.F_OperacionesSistema;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTablaDetalle.TablaDetallePedidos;
import utilidadesTablaDetalle.ValidacionesTabla;
import utilidadesVentanas.JinternalPadre;

public class PedidosE {
   private int nro_registro;
   private int cod_sucursal;
   private int cod_moneda;
   private int cod_proveedor;
   private int cod_tipo_doc;
   private int cod_plazo;
   private int dias;
   private int cod_comprador;
   private String fecha;
   private String usuario_alta;
   private String fecha_alta;
   private String nombreSucursal;
   private String nombreMoneda;
   private String nombreProveedor;
   private String nombreTipoDocumento;
   private String nombrePlazo;
   private String nombreComprador;
   private double total;

   public static int insertarPedidos(PedidosE entidad, JTable tabla, JinternalPadre frame) {
      int codigoColumn = tabla.getColumn("Código").getModelIndex();
      int descripcioncolumn = tabla.getColumn("Descripción").getModelIndex();
      int tipoFiscalColumn = tabla.getColumn("TF").getModelIndex();
      int ivaColumn = tabla.getColumn("IVA").getModelIndex();
      int regimenColumn = tabla.getColumn("REG").getModelIndex();
      int cantidadColumn = tabla.getColumn("Cantidad").getModelIndex();
      int bonificacionColum = tabla.getColumn("Cant. Bonif").getModelIndex();
      int precioColumn = tabla.getColumn("Precio").getModelIndex();
      if (ValidacionesTabla.validarSinDetalles(tabla, frame)) {
         int lastInsertId = 0;
         PreparedStatement preparedStatement1 = null;
         PreparedStatement preparedStatementInsert = null;
         ResultSet generatedKeys = null;
         Connection conexion = null;

         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatement1 = conexion.prepareStatement(
               "INSERT INTO pedidos_proveedores (cod_sucursal,cod_moneda,cod_proveedor,cod_tipo_doc,cod_plazo,dias,fecha,cod_comprador,total,fecha_alta,usuario_alta) VALUES ( ?,?,?,?,?,?,?,?,?, NOW(), SUBSTRING_INDEX(USER(), '@', 1))",
               1
            );
            preparedStatementInsert = conexion.prepareStatement(
               "INSERT INTO pedidos_proveedores_det (nro_registro,cod_producto,tipo_fiscal,iva_producto,porc_gravado,cantidad,cantidad_bonif,precio) VALUES (?,?,?,?,?,?,?,?)"
            );
            preparedStatement1.setInt(1, entidad.getCod_sucursal());
            preparedStatement1.setInt(2, entidad.getCod_moneda());
            preparedStatement1.setInt(3, entidad.getCod_proveedor());
            preparedStatement1.setInt(4, entidad.getCod_tipo_doc());
            preparedStatement1.setInt(5, entidad.getCod_plazo());
            preparedStatement1.setInt(6, entidad.getDias());
            preparedStatement1.setString(7, entidad.getFecha());
            preparedStatement1.setInt(8, entidad.getCod_comprador());
            preparedStatement1.setDouble(9, entidad.getTotal());
            preparedStatement1.executeUpdate();
            generatedKeys = preparedStatement1.getGeneratedKeys();
            if (generatedKeys.next()) {
               lastInsertId = generatedKeys.getInt(1);
            }

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
               if (!String.valueOf(tabla.getValueAt(fila, codigoColumn)).trim().equals("")
                  && tabla.getValueAt(fila, codigoColumn) != null
                  && !String.valueOf(tabla.getValueAt(fila, descripcioncolumn)).trim().equals("")
                  && tabla.getValueAt(fila, descripcioncolumn) != null) {
                  double cantidad = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."));
                  double precio = Double.parseDouble(String.valueOf(tabla.getValueAt(fila, precioColumn)).replace(".", "").replace(",", "."));
                  if (!ValidacionesTabla.validarCeros(tabla, fila, cantidad, "No se puede grabar registro con cantidad 0! ", frame)) {
                     return 0;
                  }

                  if (!ValidacionesTabla.validarCeros(tabla, fila, precio, "No se puede grabar registro con precio 0! ", frame)) {
                     return 0;
                  }

                  preparedStatementInsert.setInt(1, lastInsertId);
                  preparedStatementInsert.setString(2, String.valueOf(tabla.getValueAt(fila, codigoColumn)));
                  preparedStatementInsert.setInt(3, Integer.parseInt(String.valueOf(tabla.getValueAt(fila, tipoFiscalColumn))));
                  preparedStatementInsert.setDouble(4, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, ivaColumn)).replace(",", ".")));
                  preparedStatementInsert.setDouble(5, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, regimenColumn)).replace(",", ".")));
                  preparedStatementInsert.setDouble(6, cantidad);
                  preparedStatementInsert.setDouble(
                     7, Double.parseDouble(String.valueOf(tabla.getValueAt(fila, bonificacionColum)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementInsert.setDouble(8, precio);
                  preparedStatementInsert.addBatch();
               }
            }

            if (preparedStatementInsert != null) {
               preparedStatementInsert.executeBatch();
            }

            conexion.commit();
            return 1;
         } catch (Exception var27) {
            try {
               conexion.rollback();
            } catch (SQLException var26) {
               LogErrores.errores(var26, "Error al insertar Pedido.", frame);
            }

            LogErrores.errores(var27, "Error al insertar Pedido..", frame);
            return 0;
         } finally {
            new CerrarRecursos(preparedStatement1, preparedStatementInsert, generatedKeys, frame, "Error al insertar Pedido..");
            DatabaseConnection.getInstance().cerrarConexion();
         }
      } else {
         return 0;
      }
   }

   public static int actualizarPedidos(PedidosE entidad, JinternalPadre frame, JTable tablaInsertar, List<Integer> codigosAEliminar) {
      int codigoColumn = tablaInsertar.getColumn("Código").getModelIndex();
      int descripcioncolumn = tablaInsertar.getColumn("Descripción").getModelIndex();
      int tipoFiscalColumn = tablaInsertar.getColumn("TF").getModelIndex();
      int ivaColumn = tablaInsertar.getColumn("IVA").getModelIndex();
      int regimenColumn = tablaInsertar.getColumn("REG").getModelIndex();
      int cantidadColumn = tablaInsertar.getColumn("Cantidad").getModelIndex();
      int bonificacionColum = tablaInsertar.getColumn("Cant. Bonif").getModelIndex();
      int precioColumn = tablaInsertar.getColumn("Precio").getModelIndex();
      int subTotalColumn = tablaInsertar.getColumn("SubTotal").getModelIndex();
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
            preparedStatementDelete = conexion.prepareStatement("delete from pedidos_proveedores_det where id = ?");
            preparedStatementInsert = conexion.prepareStatement(
               "INSERT INTO pedidos_proveedores_det (nro_registro,cod_producto,tipo_fiscal,iva_producto,porc_gravado,cantidad,cantidad_bonif,precio) VALUES (?,?,?,?,?,?,?,?)"
            );
            preparedStatementUpdate = conexion.prepareStatement(
               "UPDATE pedidos_proveedores_det set cod_producto=?,tipo_fiscal=?,iva_producto=?,porc_gravado=?,cantidad=?,cantidad_bonif=?,precio=? where id =?"
            );
            preparedStatement1 = conexion.prepareStatement(
               "Update pedidos_proveedores set cod_sucursal= ?,cod_moneda= ?,cod_proveedor= ?,cod_tipo_doc= ?,cod_plazo= ?,dias= ?,fecha= ?,cod_comprador= ?,total= ? where nro_registro = ?"
            );
            preparedStatement1.setInt(1, entidad.getCod_sucursal());
            preparedStatement1.setInt(2, entidad.getCod_moneda());
            preparedStatement1.setInt(3, entidad.getCod_proveedor());
            preparedStatement1.setInt(4, entidad.getCod_tipo_doc());
            preparedStatement1.setInt(5, entidad.getCod_plazo());
            preparedStatement1.setInt(6, entidad.getDias());
            preparedStatement1.setString(7, entidad.getFecha());
            preparedStatement1.setInt(8, entidad.getCod_comprador());
            preparedStatement1.setDouble(9, entidad.getTotal());
            preparedStatement1.setInt(10, registro);
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
                  double precio = Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioColumn)).replace(".", "").replace(",", "."));
                  if (String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn)).trim().equals("0")) {
                     if (!ValidacionesTabla.validarCeros(tablaInsertar, fila, cantidad, "No se puede grabar registro con cantidad 0! ", frame)) {
                        return 0;
                     }

                     if (!ValidacionesTabla.validarCeros(tablaInsertar, fila, precio, "No se puede grabar registro con precio 0! ", frame)) {
                        return 0;
                     }

                     preparedStatementInsert.setInt(1, registro);
                     preparedStatementInsert.setString(2, String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)));
                     preparedStatementInsert.setInt(3, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, tipoFiscalColumn))));
                     preparedStatementInsert.setDouble(4, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, ivaColumn)).replace(",", ".")));
                     preparedStatementInsert.setDouble(5, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, regimenColumn)).replace(",", ".")));
                     preparedStatementInsert.setDouble(
                        6, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.setDouble(
                        7, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, bonificacionColum)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.setDouble(
                        8, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementInsert.addBatch();
                  } else if (!String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))
                     .trim()
                     .equals(String.valueOf(tablaInsertar.getValueAt(fila, SWColumn)).trim())) {
                     if (!ValidacionesTabla.validarCeros(tablaInsertar, fila, cantidad, "No se puede grabar registro con cantidad 0! ", frame)) {
                        return 0;
                     }

                     if (!ValidacionesTabla.validarCeros(tablaInsertar, fila, precio, "No se puede grabar registro con precio 0! ", frame)) {
                        return 0;
                     }

                     preparedStatementUpdate.setString(1, String.valueOf(tablaInsertar.getValueAt(fila, codigoColumn)));
                     preparedStatementUpdate.setInt(2, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, tipoFiscalColumn))));
                     preparedStatementUpdate.setDouble(3, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, ivaColumn)).replace(",", ".")));
                     preparedStatementUpdate.setDouble(4, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, regimenColumn)).replace(",", ".")));
                     preparedStatementUpdate.setDouble(
                        5, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, cantidadColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setDouble(
                        6, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, bonificacionColum)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setDouble(
                        7, Double.parseDouble(String.valueOf(tablaInsertar.getValueAt(fila, precioColumn)).replace(".", "").replace(",", "."))
                     );
                     preparedStatementUpdate.setInt(8, Integer.parseInt(String.valueOf(tablaInsertar.getValueAt(fila, primarioColumn))));
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
         } catch (Exception var32) {
            try {
               conexion.rollback();
            } catch (SQLException var31) {
               LogErrores.errores(var31, "Error al insertar Pedido.", frame);
            }

            LogErrores.errores(var32, "Error al insertar Pedido..", frame);
            return 0;
         } finally {
            new CerrarRecursos(
               preparedStatementUpdate, preparedStatement1, preparedStatementInsert, preparedStatementDelete, frame, "Error al insertar Pedido.."
            );
            DatabaseConnection.getInstance().cerrarConexion();
         }
      } else {
         return 0;
      }
   }

   public static int eliminarPedidos(PedidosE entidad, JinternalPadre frame) {
      int registro = entidad.getNro_registro();
      PreparedStatement preparedStatement2 = null;
      PreparedStatement preparedStatement1 = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement("delete from pedidos_proveedores_det where nro_registro =? ");
         preparedStatement2.setInt(1, registro);
         preparedStatement2.executeUpdate();
         preparedStatement1 = conexion.prepareStatement("delete from pedidos_proveedores where nro_registro =?");
         preparedStatement1.setInt(1, registro);
         preparedStatement1.executeUpdate();
         conexion.commit();
         return 1;
      } catch (Exception var13) {
         try {
            conexion.rollback();
         } catch (SQLException var12) {
            LogErrores.errores(var12, "Error al eliminar Pedido.", frame);
         }

         LogErrores.errores(var13, "Error al eliminar Pedido..", frame);
      } finally {
         new CerrarRecursos(preparedStatement1, preparedStatement2, frame, "Error al eliminar Pedido..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static PedidosE buscarPedido(int registro, JinternalPadre frame, TablaDetallePedidos tabla, int par_decimal_cantidad, int par_decimal_importe) {
      ResultSet rs = null;
      ResultSet rs2 = null;
      PreparedStatement preparedStatement = null;
      PreparedStatement preparedStatement2 = null;
      FormatoDecimalOperacionSistema formatoDecimalCantidad = new FormatoDecimalOperacionSistema(par_decimal_cantidad);
      FormatoDecimalOperacionSistema formatoDecimalPrecio = new FormatoDecimalOperacionSistema(par_decimal_importe);
      FormatoDecimalOperacionSistema formatoDecimal2 = new FormatoDecimalOperacionSistema(2);
      ModeloTablaProveedores modelo = (ModeloTablaProveedores)tabla.getModel();
      modelo.deleteRow(tabla);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement2 = conexion.prepareStatement(
            "select\r\npedidos_proveedores_det.cod_producto,productos.nombre_producto,pedidos_proveedores_det.tipo_fiscal,\r\npedidos_proveedores_det.iva_producto,pedidos_proveedores_det.porc_gravado,unidades_medidas.sigla,\r\npedidos_proveedores_det.cantidad,pedidos_proveedores_det.cantidad_bonif,pedidos_proveedores_det.precio,\r\npedidos_proveedores_det.id\r\n from pedidos_proveedores_det,productos,unidades_medidas\r\nwhere\r\npedidos_proveedores_det.cod_producto = productos.cod_producto\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida and pedidos_proveedores_det.nro_registro =?"
         );
         preparedStatement2.setInt(1, registro);
         rs2 = preparedStatement2.executeQuery();

         while (rs2.next()) {
            BigDecimal v_precio = new BigDecimal(rs2.getDouble("precio"));
            BigDecimal v_cantidad = new BigDecimal(rs2.getDouble("cantidad"));
            modelo.addRow(
               new Object[]{
                  rs2.getString("cod_producto"),
                  rs2.getString("nombre_producto"),
                  rs2.getInt("tipo_fiscal"),
                  formatoDecimal2.format(rs2.getDouble("iva_producto")),
                  formatoDecimal2.format(rs2.getDouble("porc_gravado")),
                  rs2.getString("sigla"),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad")),
                  formatoDecimalCantidad.format(rs2.getDouble("cantidad_bonif")),
                  formatoDecimalPrecio.format(rs2.getDouble("precio")),
                  formatoDecimalPrecio.format(F_OperacionesSistema.multiplicar(v_cantidad, v_precio)),
                  rs2.getInt("id")
               }
            );
         }

         modelo.addDefaultRow();
         preparedStatement = conexion.prepareStatement(
            "select\r\n      pedidos_proveedores.nro_registro,pedidos_proveedores.cod_sucursal,pedidos_proveedores.cod_moneda,pedidos_proveedores.cod_proveedor,\r\n      pedidos_proveedores.cod_tipo_doc,pedidos_proveedores.cod_plazo,pedidos_proveedores.dias,pedidos_proveedores.fecha,\r\n      pedidos_proveedores.cod_comprador,pedidos_proveedores.total,pedidos_proveedores.usuario_alta,\r\n      pedidos_proveedores.fecha_alta,sucursales.nombre_sucursal,monedas.nombre_moneda,proveedores.nombre_proveedor,\r\n      tipo_documentos.nombre_tipo_documento,plazos.nombre_plazo,compradores.nombre_comprador\r\n       from pedidos_proveedores,sucursales,monedas,proveedores,tipo_documentos,plazos,compradores\r\nwhere\r\npedidos_proveedores.cod_sucursal = sucursales.cod_sucursal\r\nand pedidos_proveedores.cod_moneda = monedas.cod_moneda\r\nand pedidos_proveedores.cod_proveedor = proveedores.cod_proveedor\r\nand pedidos_proveedores.cod_tipo_doc = tipo_documentos.cod_tipo_documento\r\nand pedidos_proveedores.cod_plazo = plazos.cod_plazo\r\nand pedidos_proveedores.cod_comprador = compradores.cod_comprador and pedidos_proveedores.nro_registro =?"
         );
         preparedStatement.setInt(1, registro);
         rs = preparedStatement.executeQuery();
         if (rs.next()) {
            return new PedidosE(
               registro,
               rs.getInt("cod_sucursal"),
               rs.getInt("cod_moneda"),
               rs.getInt("cod_proveedor"),
               rs.getInt("cod_tipo_doc"),
               rs.getInt("cod_plazo"),
               rs.getInt("dias"),
               rs.getString("fecha"),
               rs.getInt("cod_comprador"),
               rs.getDouble("total"),
               rs.getString("usuario_alta"),
               rs.getString("fecha_alta"),
               rs.getString("nombre_sucursal"),
               rs.getString("nombre_moneda"),
               rs.getString("nombre_proveedor"),
               rs.getString("nombre_tipo_documento"),
               rs.getString("nombre_plazo"),
               rs.getString("nombre_comprador")
            );
         }
      } catch (Exception var22) {
         LogErrores.errores(var22, "Error al seleccionar Pedido a Proveedores..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatement, preparedStatement2, rs, rs2, frame, "Error al seleccionar Pedido..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static int ultimoRegistro(JinternalPadre frame) {
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;

      int var7;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatement = conexion.prepareStatement("Select max(nro_registro) as mayor from pedidos_proveedores");
         rs = preparedStatement.executeQuery();
         if (!rs.next()) {
            return 0;
         }

         int siguienteRegistro = rs.getInt("mayor");
         var7 = siguienteRegistro;
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Pedido a Proveedores..", frame);
         return 0;
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar Pedido..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var7;
   }

   public PedidosE(
      int nro_registro,
      int cod_sucursal,
      int cod_moneda,
      int cod_proveedor,
      int cod_tipo_doc,
      int cod_plazo,
      int dias,
      String fecha,
      int cod_comprador,
      double total,
      String usuario_alta,
      String fecha_alta,
      String nombreSucursal,
      String nombreMoneda,
      String nombreProveedor,
      String nombreTipoDocumento,
      String nombrePlazo,
      String nombreComprador
   ) {
      this.nro_registro = nro_registro;
      this.cod_sucursal = cod_sucursal;
      this.cod_moneda = cod_moneda;
      this.cod_proveedor = cod_proveedor;
      this.cod_tipo_doc = cod_tipo_doc;
      this.cod_plazo = cod_plazo;
      this.dias = dias;
      this.fecha = fecha;
      this.cod_comprador = cod_comprador;
      this.total = total;
      this.usuario_alta = usuario_alta;
      this.fecha_alta = fecha;
      this.nombreComprador = nombreComprador;
      this.nombreMoneda = nombreMoneda;
      this.nombrePlazo = nombrePlazo;
      this.nombreProveedor = nombreProveedor;
      this.nombreSucursal = nombreSucursal;
      this.nombreTipoDocumento = nombreTipoDocumento;
   }

   public PedidosE(
      int nro_registro,
      int cod_sucursal,
      int cod_moneda,
      int cod_proveedor,
      int cod_tipo_doc,
      int cod_plazo,
      int dias,
      String fecha,
      int cod_comprador,
      double total,
      String usuario_alta,
      String fecha_alta
   ) {
      this.nro_registro = nro_registro;
      this.cod_sucursal = cod_sucursal;
      this.cod_moneda = cod_moneda;
      this.cod_proveedor = cod_proveedor;
      this.cod_tipo_doc = cod_tipo_doc;
      this.cod_plazo = cod_plazo;
      this.dias = dias;
      this.fecha = fecha;
      this.cod_comprador = cod_comprador;
      this.total = total;
      this.usuario_alta = usuario_alta;
      this.fecha_alta = fecha;
   }

   public PedidosE(
      int nro_registro,
      int cod_sucursal,
      int cod_moneda,
      int cod_proveedor,
      int cod_tipo_doc,
      int cod_plazo,
      int dias,
      String fecha,
      int cod_comprador,
      double total
   ) {
      this.nro_registro = nro_registro;
      this.cod_sucursal = cod_sucursal;
      this.cod_moneda = cod_moneda;
      this.cod_proveedor = cod_proveedor;
      this.cod_tipo_doc = cod_tipo_doc;
      this.cod_plazo = cod_plazo;
      this.dias = dias;
      this.fecha = fecha;
      this.cod_comprador = cod_comprador;
      this.total = total;
   }

   public int getNro_registro() {
      return this.nro_registro;
   }

   public void setNro_registro(int nro_registro) {
      this.nro_registro = nro_registro;
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

   public int getCod_proveedor() {
      return this.cod_proveedor;
   }

   public void setCod_proveedor(int cod_proveedor) {
      this.cod_proveedor = cod_proveedor;
   }

   public int getCod_tipo_doc() {
      return this.cod_tipo_doc;
   }

   public void setCod_tipo_doc(int cod_tipo_doc) {
      this.cod_tipo_doc = cod_tipo_doc;
   }

   public int getCod_plazo() {
      return this.cod_plazo;
   }

   public void setCod_plazo(int cod_plazo) {
      this.cod_plazo = cod_plazo;
   }

   public int getDias() {
      return this.dias;
   }

   public void setDias(int dias) {
      this.dias = dias;
   }

   public int getCod_comprador() {
      return this.cod_comprador;
   }

   public void setCod_comprador(int cod_comprador) {
      this.cod_comprador = cod_comprador;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
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

   public String getNombreSucursal() {
      return this.nombreSucursal;
   }

   public void setNombreSucursal(String nombreSucursal) {
      this.nombreSucursal = nombreSucursal;
   }

   public String getNombreMoneda() {
      return this.nombreMoneda;
   }

   public void setNombreMoneda(String nombreMoneda) {
      this.nombreMoneda = nombreMoneda;
   }

   public String getNombreProveedor() {
      return this.nombreProveedor;
   }

   public void setNombreProveedor(String nombreProveedor) {
      this.nombreProveedor = nombreProveedor;
   }

   public String getNombreTipoDocumento() {
      return this.nombreTipoDocumento;
   }

   public void setNombreTipoDocumento(String nombreTipoDocumento) {
      this.nombreTipoDocumento = nombreTipoDocumento;
   }

   public String getNombrePlazo() {
      return this.nombrePlazo;
   }

   public void setNombrePlazo(String nombrePlazo) {
      this.nombrePlazo = nombrePlazo;
   }

   public String getNombreComprador() {
      return this.nombreComprador;
   }

   public void setNombreComprador(String nombreComprador) {
      this.nombreComprador = nombreComprador;
   }

   public double getTotal() {
      return this.total;
   }

   public void setTotal(double total) {
      this.total = total;
   }
}
