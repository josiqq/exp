package reportesStock;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import utilidadesConexion.DatabaseConnection;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ColorRenderer;
import utilidadesVentanas.JinternalPadreReporte;

public class ProductoStockE {
   public static void buscarProductoStock(
      int tipo_stock,
      int tipo_costo,
      String cod_producto,
      int cod_deposito,
      int cod_marca,
      int cod_seccion,
      int cod_sub_seccion,
      int cod_grupo,
      JTable tabla,
      int par_decimal_cantidad,
      JinternalPadreReporte frame
   ) {
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      FormatoDecimalOperacionSistema formatoDecimalCantidad = new FormatoDecimalOperacionSistema(par_decimal_cantidad);
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         if (tipo_stock == 0) {
            if (tipo_costo == 0) {
               preparedStatement = conexion.prepareStatement(
                  "select\r\ndepositos.nombre_deposito,\r\nstock_depositos.cod_producto,\r\nproductos.nombre_producto,0 as costo_producto,\r\ncantidad\r\nfrom stock_depositos,depositos,productos,marcas,proveedores\r\nwhere\r\nproductos.cod_marca = marcas.cod_marca\r\nand productos.cod_proveedor = proveedores.cod_proveedor\r\nand stock_depositos.cod_deposito = depositos.cod_deposito\r\nand stock_depositos.cod_producto = productos.cod_producto and ((productos.cod_producto = ?) or (? =0))\r\nand ((productos.cod_marca = ?) or (? =0))\r\nand ((stock_depositos.cod_deposito = ?) or (? =0))\r\nand ((productos.cod_seccion = ?) or (? =0))\r\nand ((productos.cod_sub_seccion = ?) or (? =0))\r\nand ((productos.cod_grupo = ?) or (? =0))"
               );
            } else {
               preparedStatement = conexion.prepareStatement(
                  "select\r\ndepositos.nombre_deposito,\r\nstock_depositos.cod_producto,\r\nproductos.nombre_producto,productos.costo_producto,\r\ncantidad\r\nfrom stock_depositos,depositos,productos,marcas,proveedores\r\nwhere\r\nproductos.cod_marca = marcas.cod_marca\r\nand productos.cod_proveedor = proveedores.cod_proveedor\r\nand stock_depositos.cod_deposito = depositos.cod_deposito\r\nand stock_depositos.cod_producto = productos.cod_producto and ((productos.cod_producto = ?) or (? =0))\r\nand ((productos.cod_marca = ?) or (? =0))\r\nand ((stock_depositos.cod_deposito = ?) or (? =0))\r\nand ((productos.cod_seccion = ?) or (? =0))\r\nand ((productos.cod_sub_seccion = ?) or (? =0))\r\nand ((productos.cod_grupo = ?) or (? =0))"
               );
            }
         } else {
            preparedStatement = conexion.prepareStatement(
               "select\r\ndepositos.nombre_deposito,\r\nstock_depositos.cod_producto,\r\nproductos.nombre_producto,\r\ncantidad\r\nfrom stock_depositos,depositos,productos,marcas,proveedores\r\nwhere\r\nproductos.cod_marca = marcas.cod_marca\r\nand productos.cod_proveedor = proveedores.cod_proveedor\r\nand stock_depositos.cod_deposito = depositos.cod_deposito and stock_depositos.cantidad =0 and stock_depositos.cod_producto = productos.cod_producto and ((productos.cod_producto = ?) or (? =0))\r\nand ((productos.cod_marca = ?) or (? =0))\r\nand ((stock_depositos.cod_deposito = ?) or (? =0))\r\nand ((productos.cod_seccion = ?) or (? =0))\r\nand ((productos.cod_sub_seccion = ?) or (? =0))\r\nand ((productos.cod_grupo = ?) or (? =0))"
            );
         }

         preparedStatement.setString(1, cod_producto);
         preparedStatement.setString(2, cod_producto);
         preparedStatement.setInt(3, cod_marca);
         preparedStatement.setInt(4, cod_marca);
         preparedStatement.setInt(5, cod_deposito);
         preparedStatement.setInt(6, cod_deposito);
         preparedStatement.setInt(7, cod_seccion);
         preparedStatement.setInt(8, cod_seccion);
         preparedStatement.setInt(9, cod_sub_seccion);
         preparedStatement.setInt(10, cod_sub_seccion);
         preparedStatement.setInt(11, cod_grupo);
         preparedStatement.setInt(12, cod_grupo);
         rs = preparedStatement.executeQuery();
         Map<String, ProductoStockE.ProductoInfo> productosMap = new LinkedHashMap<>();
         Set<String> nombreDepositos = new TreeSet<>();

         while (rs.next()) {
            String nombreDeposito = rs.getString("nombre_deposito");
            String codProducto = rs.getString("cod_producto");
            String nombreProducto = rs.getString("nombre_producto");
            String costoProducto = rs.getString("costo_producto");
            String cantidad = formatoDecimalCantidad.format(rs.getDouble("cantidad"));
            nombreDepositos.add(nombreDeposito);
            productosMap.putIfAbsent(codProducto, new ProductoStockE.ProductoInfo(nombreProducto));
            productosMap.get(codProducto).cantidades.put(nombreDeposito, cantidad);
         }

         List<String> columnas = new ArrayList<>();
         columnas.add("Codigo");
         columnas.add("Descripcion Producto");
         columnas.add("Costo");
         columnas.addAll(nombreDepositos);
         columnas.add("Total");
         DefaultTableModel modelo = new DefaultTableModel(columnas.toArray(), 0);

         for (Entry<String, ProductoStockE.ProductoInfo> entry : productosMap.entrySet()) {
            String codProducto = entry.getKey();
            ProductoStockE.ProductoInfo info = entry.getValue();
            Object[] fila = new Object[columnas.size()];
            fila[0] = codProducto;
            fila[1] = info.nombreProducto;
            int total = 0;

            for (int i = 2; i < columnas.size() - 1; i++) {
               String deposito = columnas.get(i);
               double cantidad = Double.parseDouble(info.cantidades.getOrDefault(deposito, "0").replace(".", "").replace(",", "."));
               fila[i] = cantidad;
               total = (int)(total + cantidad);
            }

            fila[columnas.size() - 1] = total;
            modelo.addRow(fila);
         }

         tabla.setModel(modelo);
         ajustarAnchoColumnas(tabla);
      } catch (Exception var33) {
         LogErrores.errores(var33, "Error al seleccionar Stock de Productos..", frame);
      } finally {
         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar Stock de Productos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   private static void ajustarAnchoColumnas(JTable tabla) {
      tabla.setSelectionMode(0);
      tabla.getTableHeader().setReorderingAllowed(false);
      ColorRenderer colorRenderer = new ColorRenderer();
      tabla.setDefaultRenderer(Object.class, colorRenderer);
      DefaultTableCellRenderer headerRendererIzquierda = new DefaultTableCellRenderer() {
         private static final long serialVersionUID = 1L;

         @Override
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            this.setHorizontalAlignment(2);
            this.setFont(this.getFont().deriveFont(1));
            this.setBackground(new Color(240, 240, 240));
            this.setForeground(Color.BLACK);
            this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            this.setOpaque(true);
            return this;
         }
      };
      JTableHeader headerTabla = tabla.getTableHeader();

      for (int i = 0; i < tabla.getColumnCount(); i++) {
         TableColumn col = tabla.getColumnModel().getColumn(i);
         String nombreColumna = col.getHeaderValue().toString();
         if (nombreColumna.equals("Codigo")) {
            col.setPreferredWidth(120);
            col.setMinWidth(120);
            col.setMaxWidth(120);
            tabla.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
         } else if (nombreColumna.equals("Descripcion Producto")) {
            col.setPreferredWidth(450);
            col.setMinWidth(450);
            col.setMaxWidth(450);
            tabla.getColumn("Descripcion Producto").setHeaderRenderer(headerRendererIzquierda);
         } else if (nombreColumna.equals("Costo")) {
            col.setPreferredWidth(120);
            col.setMinWidth(120);
            col.setMaxWidth(120);
            tabla.getColumn("Costo").setHeaderRenderer(headerRendererIzquierda);
         } else {
            TableCellRenderer renderer = headerTabla.getDefaultRenderer();
            Component comp = renderer.getTableCellRendererComponent(tabla, nombreColumna, false, false, -1, i);
            int ancho = comp.getPreferredSize().width + 12;
            col.setPreferredWidth(ancho);
            tabla.getColumn(nombreColumna).setHeaderRenderer(headerRendererIzquierda);
         }
      }
   }

   static class ProductoInfo {
      String nombreProducto;
      Map<String, String> cantidades = new HashMap<>();

      ProductoInfo(String nombreProducto) {
         this.nombreProducto = nombreProducto;
      }
   }
}
