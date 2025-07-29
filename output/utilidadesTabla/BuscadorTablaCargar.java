package utilidadesTabla;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadreString;

public class BuscadorTablaCargar {
   private String nom_tabla;
   private LabelPrincipal lblLineasRecupera;
   private ModeloTablaDefecto modelo;
   private LimiteTextField txt_buscador;

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void cargarTabla(String condicion, JinternalPadreString frame) {
      this.modelo.setRowCount(0);
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;
      boolean var10 = false /* VF: Semaphore variable */;

      label63: {
         label62: {
            try {
               var10 = true;
               DatabaseConnection nf = DatabaseConnection.getInstance();
               conexion = nf.abrirConexion();
               if (!this.nom_tabla.trim().equals("Productos")) {
                  var10 = false;
               } else {
                  preparedStatement = conexion.prepareStatement(
                     "select\r\nproductos.cod_producto,productos.nombre_producto,productos.estado,productos.cod_unidad_medida,productos.cod_marca,\r\nproductos.cod_proveedor,productos.costo_producto,productos.cod_moneda_costo,productos.activo_venta,productos.activo_compra,\r\nproductos.controla_stock,productos.perecedero,productos.produccion,productos.calculo_costo,productos.tipo_fiscal,\r\nproductos.iva_producto,productos.porcentaje_gravado,productos.cod_seccion,productos.cod_sub_seccion,productos.cod_grupo,\r\nfamilia_seccion.nombre_seccion,familia_sub_seccion.nombre_sub_seccion,familia_grupo.nombre_grupo,\r\nunidades_medidas.nombre_unidad_medida,marcas.nombre_marca,proveedores.nombre_proveedor,monedas.nombre_moneda,productos.porc_regimen\r\n from productos,familia_seccion,familia_sub_seccion,familia_grupo,unidades_medidas,marcas,proveedores,monedas\r\nwhere\r\nproductos.cod_seccion = familia_seccion.cod_seccion\r\nand productos.cod_sub_seccion = familia_sub_seccion.cod_sub_seccion\r\nand productos.cod_grupo = familia_grupo.cod_grupo\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand productos.cod_marca = marcas.cod_marca\r\nand productos.cod_proveedor = proveedores.cod_proveedor\r\nand productos.cod_moneda_costo = monedas.cod_moneda "
                        + condicion
                  );
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getString("cod_producto"),
                              rs.getString("nombre_producto"),
                              rs.getBoolean("estado"),
                              rs.getInt("cod_unidad_medida"),
                              rs.getInt("cod_marca"),
                              rs.getInt("cod_proveedor"),
                              rs.getDouble("costo_producto"),
                              rs.getInt("cod_moneda_costo"),
                              rs.getInt("activo_venta"),
                              rs.getInt("activo_compra"),
                              rs.getInt("controla_stock"),
                              rs.getInt("perecedero"),
                              rs.getInt("produccion"),
                              rs.getInt("calculo_costo"),
                              rs.getInt("tipo_fiscal"),
                              rs.getDouble("iva_producto"),
                              rs.getDouble("porcentaje_gravado"),
                              rs.getInt("cod_seccion"),
                              rs.getInt("cod_sub_seccion"),
                              rs.getInt("cod_grupo"),
                              rs.getString("nombre_seccion"),
                              rs.getString("nombre_sub_seccion"),
                              rs.getString("nombre_grupo"),
                              rs.getString("nombre_unidad_medida"),
                              rs.getString("nombre_marca"),
                              rs.getString("nombre_proveedor"),
                              rs.getString("nombre_moneda"),
                              rs.getDouble("porc_regimen")
                           }
                        );
                  }

                  var10 = false;
               }
               break label62;
            } catch (Exception var11) {
               LogErrores.errores(var11, "Error al seleccionar " + this.nom_tabla, frame);
               var10 = false;
            } finally {
               if (var10) {
                  new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar " + this.nom_tabla);
                  DatabaseConnection.getInstance().cerrarConexion();
               }
            }

            new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar " + this.nom_tabla);
            DatabaseConnection.getInstance().cerrarConexion();
            break label63;
         }

         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar " + this.nom_tabla);
         DatabaseConnection.getInstance().cerrarConexion();
      }

      if (!this.nom_tabla.trim().equals("ParametrosDet") && this.modelo.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecupera.setText(String.valueOf(nf.format((long)this.modelo.getRowCount())));
      }
   }

   public BuscadorTablaCargar(
      final LimiteTextField txt_buscador,
      LabelPrincipal lblLineasRecupera,
      final ModeloTablaDefecto modelo,
      final String nom_tabla,
      final JinternalPadreString frame
   ) {
      this.txt_buscador = txt_buscador;
      this.txt_buscador.setName("txt_buscador");
      this.modelo = modelo;
      this.lblLineasRecupera = lblLineasRecupera;
      this.nom_tabla = nom_tabla;
      this.txt_buscador.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyReleased(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (modelo.getRowCount() >= 0 && e.getKeyCode() == 10) {
               if (txt_buscador.getText().trim().equals("")) {
                  BuscadorTablaCargar.this.cargarTabla("", frame);
               } else if (nom_tabla.trim().equals("Productos")) {
                  BuscadorTablaCargar.this.cargarTabla(" and nombre_producto like '%" + txt_buscador.getText().trim() + "%'", frame);
               }
            }
         }
      });
   }

   public BuscadorTablaCargar(
      final LimiteTextField txt_buscador, LabelPrincipal lblLineasRecupera, final ModeloTablaDefecto modelo, final String nom_tabla, final JinternalPadre frame
   ) {
      this.txt_buscador = txt_buscador;
      this.txt_buscador.setName("txt_buscador");
      this.modelo = modelo;
      this.lblLineasRecupera = lblLineasRecupera;
      this.nom_tabla = nom_tabla;
      this.txt_buscador.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyReleased(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (modelo.getRowCount() >= 0 && e.getKeyCode() == 10) {
               if (txt_buscador.getText().trim().equals("")) {
                  BuscadorTablaCargar.this.cargarTabla("", frame);
               } else if (nom_tabla.trim().equals("Sucursales")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_sucursal like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Depositos")) {
                  BuscadorTablaCargar.this.cargarTabla(" and nombre_deposito like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("UnidadesMedida")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_unidad_medida like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Marcas")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_marca like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Paises")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_pais like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Ciudades")) {
                  BuscadorTablaCargar.this.cargarTabla(" and nombre_ciudad like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Rubros")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_rubro like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Proveedores")) {
                  BuscadorTablaCargar.this.cargarTabla(" and nombre_proveedor like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Monedas")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_moneda like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Cajeros")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_cajero like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Compradores")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_comprador like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Vendedores")) {
                  BuscadorTablaCargar.this.cargarTabla(" and nombre_vendedor like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("ClientesCategoria")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_categoria like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("LugaresVentas")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_lugar like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("TipoDocumentos")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_tipo_documento like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Cuentas")) {
                  BuscadorTablaCargar.this.cargarTabla(" and nombre_cuenta like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Clientes")) {
                  BuscadorTablaCargar.this.cargarTabla(" and nombre_cliente like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Bancos")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_banco like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Productos")) {
                  BuscadorTablaCargar.this.cargarTabla(" and nombre_producto like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Plazos")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_plazo like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("CondicionesVta")) {
                  BuscadorTablaCargar.this.cargarTabla(" and nombre_condicion like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("MotivosAjuste")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_motivo like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("CondicionesPagos")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_condicion like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Cobradores")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_cobrador like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Tarjetas")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_tarjeta like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("Motivos")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_motivo like '%" + txt_buscador.getText().trim() + "%'", frame);
               } else if (nom_tabla.trim().equals("MotivosNC")) {
                  BuscadorTablaCargar.this.cargarTabla(" where nombre_motivo like '%" + txt_buscador.getText().trim() + "%'", frame);
               }
            }
         }
      });
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void cargarTabla(String condicion, JinternalPadre frame) {
      this.modelo.setRowCount(0);
      ResultSet rs = null;
      PreparedStatement preparedStatement = null;
      Connection conexion = null;
      boolean var10 = false /* VF: Semaphore variable */;

      label557: {
         label556: {
            try {
               var10 = true;
               DatabaseConnection nf = DatabaseConnection.getInstance();
               conexion = nf.abrirConexion();
               if (this.nom_tabla.equals("Sucursales")) {
                  preparedStatement = conexion.prepareStatement("Select cod_sucursal,nombre_sucursal,estado,telefono,direccion from sucursales " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_sucursal"),
                              rs.getString("nombre_sucursal"),
                              rs.getBoolean("estado"),
                              rs.getString("telefono"),
                              rs.getString("direccion")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("Depositos")) {
                  preparedStatement = conexion.prepareStatement(
                     "select depositos.cod_deposito,depositos.nombre_deposito,depositos.estado,depositos.telefono,depositos.direccion,depositos.encargado,\r\ndepositos.cod_sucursal,depositos.pos,depositos.stock_negativo,sucursales.nombre_sucursal  from depositos,sucursales\r\nwhere\r\ndepositos.cod_sucursal = sucursales.cod_sucursal "
                        + condicion
                  );
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_deposito"),
                              rs.getString("nombre_deposito"),
                              rs.getBoolean("estado"),
                              rs.getString("telefono"),
                              rs.getString("direccion"),
                              rs.getString("encargado"),
                              rs.getInt("cod_sucursal"),
                              rs.getBoolean("pos"),
                              rs.getBoolean("stock_negativo"),
                              rs.getString("nombre_sucursal")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("UnidadesMedida")) {
                  preparedStatement = conexion.prepareStatement("Select cod_unidad_medida,nombre_unidad_medida,estado,sigla from unidades_medidas " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{rs.getInt("cod_unidad_medida"), rs.getString("nombre_unidad_medida"), rs.getBoolean("estado"), rs.getString("sigla")}
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("Marcas")) {
                  preparedStatement = conexion.prepareStatement("Select cod_marca,nombre_marca,estado from marcas " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo.addRow(new Object[]{rs.getInt("cod_marca"), rs.getString("nombre_marca"), rs.getBoolean("estado")});
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("Paises")) {
                  preparedStatement = conexion.prepareStatement("Select cod_pais,nombre_pais,estado,sigla from paises " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo.addRow(new Object[]{rs.getInt("cod_pais"), rs.getString("nombre_pais"), rs.getBoolean("estado"), rs.getString("sigla")});
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("Ciudades")) {
                  preparedStatement = conexion.prepareStatement(
                     "Select ciudades.cod_ciudad,ciudades.nombre_ciudad,ciudades.estado,ciudades.cod_pais,paises.nombre_pais from ciudades,paises where ciudades.cod_pais = paises.cod_pais "
                        + condicion
                  );
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_ciudad"),
                              rs.getString("nombre_ciudad"),
                              rs.getBoolean("estado"),
                              rs.getInt("cod_pais"),
                              rs.getString("nombre_pais")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("Rubros")) {
                  preparedStatement = conexion.prepareStatement("Select cod_rubro,nombre_rubro,estado from rubros " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo.addRow(new Object[]{rs.getInt("cod_rubro"), rs.getString("nombre_rubro"), rs.getBoolean("estado")});
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("Proveedores")) {
                  preparedStatement = conexion.prepareStatement(
                     "Select cod_proveedor,nombre_proveedor,proveedores.estado,ruc,telefono,direccion,proveedores.cod_rubro,proveedores.cod_pais,proveedores.cod_ciudad, rubros.nombre_rubro,paises.nombre_pais,ciudades.nombre_ciudad  from proveedores,rubros,paises,ciudades where  proveedores.cod_rubro = rubros.cod_rubro and proveedores.cod_pais = paises.cod_pais and proveedores.cod_ciudad = ciudades.cod_ciudad "
                        + condicion
                  );
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_proveedor"),
                              rs.getString("nombre_proveedor"),
                              rs.getBoolean("estado"),
                              rs.getString("ruc"),
                              rs.getString("telefono"),
                              rs.getString("direccion"),
                              rs.getInt("cod_rubro"),
                              rs.getInt("cod_pais"),
                              rs.getInt("cod_ciudad"),
                              rs.getString("nombre_rubro"),
                              rs.getString("nombre_pais"),
                              rs.getString("nombre_ciudad")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("Monedas")) {
                  preparedStatement = conexion.prepareStatement("Select cod_moneda,nombre_moneda,estado,sigla from monedas " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo.addRow(new Object[]{rs.getInt("cod_moneda"), rs.getString("nombre_moneda"), rs.getBoolean("estado"), rs.getString("sigla")});
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("Cajeros")) {
                  preparedStatement = conexion.prepareStatement("Select cod_cajero,nombre_cajero,estado,tesorero from cajeros " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(new Object[]{rs.getInt("cod_cajero"), rs.getString("nombre_cajero"), rs.getBoolean("estado"), rs.getBoolean("tesorero")});
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("Compradores")) {
                  preparedStatement = conexion.prepareStatement("Select cod_comprador,nombre_comprador,estado from compradores " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo.addRow(new Object[]{rs.getInt("cod_comprador"), rs.getString("nombre_comprador"), rs.getBoolean("estado")});
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("Vendedores")) {
                  preparedStatement = conexion.prepareStatement(
                     "Select vendedores.cod_vendedor,vendedores.nombre_vendedor,vendedores.estado,vendedores.clave,\r\nvendedores.cod_lugar_venta,vendedores.supervisor,vendedores.realiza_descuento,vendedores.descuento_maximo,ventas_lugares.nombre_lugar from vendedores,ventas_lugares where\r\nvendedores.cod_lugar_venta = ventas_lugares.cod_lugar "
                        + condicion
                  );
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_vendedor"),
                              rs.getString("nombre_vendedor"),
                              rs.getBoolean("estado"),
                              rs.getBoolean("supervisor"),
                              rs.getBoolean("realiza_descuento"),
                              rs.getDouble("descuento_maximo"),
                              rs.getString("clave"),
                              rs.getInt("cod_lugar_venta"),
                              rs.getString("nombre_lugar")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("ClientesCategoria")) {
                  preparedStatement = conexion.prepareStatement("Select cod_categoria,nombre_categoria,estado from clientes_categoria " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo.addRow(new Object[]{rs.getInt("cod_categoria"), rs.getString("nombre_categoria"), rs.getBoolean("estado")});
                  }

                  var10 = false;
               } else if (this.nom_tabla.equals("LugaresVentas")) {
                  preparedStatement = conexion.prepareStatement("Select cod_lugar,nombre_lugar,estado from ventas_lugares " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo.addRow(new Object[]{rs.getInt("cod_lugar"), rs.getString("nombre_lugar"), rs.getBoolean("estado")});
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("TipoDocumentos")) {
                  preparedStatement = conexion.prepareStatement(
                     "Select cod_tipo_documento,nombre_tipo_documento,estado,\r\n\t\t\ttipo,nc,ventas,compras,preventa,archivo_impresion,\r\n\t\t\tnumeracion,timbrado,fecha_inicio,fecha_vencimiento,expedicion from tipo_documentos "
                        + condicion
                  );
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_tipo_documento"),
                              rs.getString("nombre_tipo_documento"),
                              rs.getBoolean("estado"),
                              rs.getInt("tipo"),
                              rs.getInt("nc"),
                              rs.getInt("ventas"),
                              rs.getInt("compras"),
                              rs.getInt("preventa"),
                              rs.getString("archivo_impresion"),
                              rs.getInt("numeracion"),
                              rs.getInt("timbrado"),
                              rs.getString("fecha_inicio"),
                              rs.getString("fecha_vencimiento"),
                              rs.getString("expedicion")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("Cuentas")) {
                  preparedStatement = conexion.prepareStatement(
                     "select\r\ncuentas.cod_cuenta,cuentas.nombre_cuenta,cuentas.estado,cuentas.cod_banco,cuentas.tipo,cuentas.cod_sucursal,sucursales.nombre_sucursal,bancos.nombre_banco,cuentas.cod_moneda,monedas.nombre_moneda\r\n from cuentas,sucursales,bancos,monedas\r\nwhere\r\ncuentas.cod_sucursal = sucursales.cod_sucursal and cuentas.cod_moneda = monedas.cod_moneda and bancos.cod_banco = cuentas.cod_banco "
                        + condicion
                  );
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_cuenta"),
                              rs.getString("nombre_cuenta"),
                              rs.getBoolean("estado"),
                              rs.getInt("cod_banco"),
                              rs.getInt("tipo"),
                              rs.getInt("cod_sucursal"),
                              rs.getString("nombre_banco"),
                              rs.getString("nombre_sucursal"),
                              rs.getInt("cod_moneda"),
                              rs.getString("nombre_moneda")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("Clientes")) {
                  preparedStatement = conexion.prepareStatement(
                     "select\r\nclientes.cod_cliente,clientes.nombre_cliente,clientes.ruc,clientes.estado,\r\nclientes.tipo_fiscal,clientes.telefono,clientes.direccion,\r\nclientes.email,clientes.cod_categoria,clientes.cod_pais,clientes.cod_ciudad,\r\npaises.nombre_pais,ciudades.nombre_ciudad,clientes_categoria.nombre_categoria,cod_condicion_vta,cod_lista,tipo_precio,(select lista_precios_cab.nombre_lista from lista_precios_cab where lista_precios_cab.cod_lista = clientes.cod_lista) as nombre_lista,(select condiciones_ventas.nombre_condicion from condiciones_ventas where condiciones_ventas.cod_condicion = clientes.cod_condicion_vta) as nombre_condicion_vta\r\nfrom clientes,paises,ciudades,clientes_categoria where\r\nclientes.cod_ciudad = ciudades.cod_ciudad and clientes.cod_categoria = clientes_categoria.cod_categoria and clientes.cod_pais = paises.cod_pais "
                        + condicion
                  );
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_cliente"),
                              rs.getString("nombre_cliente"),
                              rs.getString("ruc"),
                              rs.getBoolean("estado"),
                              rs.getInt("tipo_fiscal"),
                              rs.getString("telefono"),
                              rs.getString("direccion"),
                              rs.getString("email"),
                              rs.getInt("cod_categoria"),
                              rs.getInt("cod_pais"),
                              rs.getInt("cod_ciudad"),
                              rs.getString("nombre_categoria"),
                              rs.getString("nombre_pais"),
                              rs.getString("nombre_ciudad"),
                              rs.getInt("cod_lista"),
                              rs.getString("nombre_lista"),
                              rs.getInt("cod_condicion_vta"),
                              rs.getString("nombre_condicion_vta"),
                              rs.getInt("tipo_precio")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("Bancos")) {
                  preparedStatement = conexion.prepareStatement("Select cod_banco,nombre_banco,estado,direccion,telefono from bancos " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_banco"),
                              rs.getString("nombre_banco"),
                              rs.getBoolean("estado"),
                              rs.getString("direccion"),
                              rs.getString("telefono")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("Productos")) {
                  preparedStatement = conexion.prepareStatement(
                     "select\r\nproductos.cod_producto,productos.nombre_producto,productos.estado,productos.cod_unidad_medida,productos.cod_marca,\r\nproductos.cod_proveedor,productos.costo_producto,productos.cod_moneda_costo,productos.activo_venta,productos.activo_compra,\r\nproductos.controla_stock,productos.perecedero,productos.produccion,productos.calculo_costo,productos.tipo_fiscal,\r\nproductos.iva_producto,productos.porcentaje_gravado,productos.cod_seccion,productos.cod_sub_seccion,productos.cod_grupo,\r\nfamilia_seccion.nombre_seccion,familia_sub_seccion.nombre_sub_seccion,familia_grupo.nombre_grupo,productos.stock_negativo,\r\nunidades_medidas.nombre_unidad_medida,marcas.nombre_marca,proveedores.nombre_proveedor,monedas.nombre_moneda,productos.porc_regimen\r\n from productos,familia_seccion,familia_sub_seccion,familia_grupo,unidades_medidas,marcas,proveedores,monedas\r\nwhere\r\nproductos.cod_seccion = familia_seccion.cod_seccion\r\nand productos.cod_sub_seccion = familia_sub_seccion.cod_sub_seccion\r\nand productos.cod_grupo = familia_grupo.cod_grupo\r\nand productos.cod_unidad_medida = unidades_medidas.cod_unidad_medida\r\nand productos.cod_marca = marcas.cod_marca\r\nand productos.cod_proveedor = proveedores.cod_proveedor\r\nand productos.cod_moneda_costo = monedas.cod_moneda "
                        + condicion
                  );
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getString("cod_producto"),
                              rs.getString("nombre_producto"),
                              rs.getBoolean("estado"),
                              rs.getInt("cod_unidad_medida"),
                              rs.getInt("cod_marca"),
                              rs.getInt("cod_proveedor"),
                              rs.getDouble("costo_producto"),
                              rs.getInt("cod_moneda_costo"),
                              rs.getInt("activo_venta"),
                              rs.getInt("activo_compra"),
                              rs.getInt("controla_stock"),
                              rs.getInt("perecedero"),
                              rs.getInt("produccion"),
                              rs.getInt("calculo_costo"),
                              rs.getInt("tipo_fiscal"),
                              rs.getDouble("iva_producto"),
                              rs.getDouble("porcentaje_gravado"),
                              rs.getInt("cod_seccion"),
                              rs.getInt("cod_sub_seccion"),
                              rs.getInt("cod_grupo"),
                              rs.getString("nombre_seccion"),
                              rs.getString("nombre_sub_seccion"),
                              rs.getString("nombre_grupo"),
                              rs.getString("nombre_unidad_medida"),
                              rs.getString("nombre_marca"),
                              rs.getString("nombre_proveedor"),
                              rs.getString("nombre_moneda"),
                              rs.getDouble("porc_regimen"),
                              rs.getInt("stock_negativo")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("Plazos")) {
                  preparedStatement = conexion.prepareStatement("Select cod_plazo,nombre_plazo,estado,dias from plazos " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo.addRow(new Object[]{rs.getInt("cod_plazo"), rs.getString("nombre_plazo"), rs.getBoolean("estado"), rs.getInt("dias")});
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("CondicionesVta")) {
                  preparedStatement = conexion.prepareStatement(
                     "Select condiciones_ventas.cod_condicion,condiciones_ventas.nombre_condicion,condiciones_ventas.estado,\r\ncondiciones_ventas.tipo,condiciones_ventas.cuotas,condiciones_ventas.cod_lista,lista_precios_cab.nombre_lista  from condiciones_ventas,lista_precios_cab\r\nwhere\r\ncondiciones_ventas.cod_lista =lista_precios_cab.cod_lista "
                        + condicion
                  );
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_condicion"),
                              rs.getString("nombre_condicion"),
                              rs.getBoolean("estado"),
                              rs.getInt("tipo"),
                              rs.getInt("cuotas"),
                              rs.getInt("cod_lista"),
                              rs.getString("nombre_lista")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("MotivosAjuste")) {
                  preparedStatement = conexion.prepareStatement("Select cod_motivo,nombre_motivo,estado,tipo from ajuste_stock_motivos " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo.addRow(new Object[]{rs.getInt("cod_motivo"), rs.getString("nombre_motivo"), rs.getBoolean("estado"), rs.getInt("tipo")});
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("CondicionesPagos")) {
                  preparedStatement = conexion.prepareStatement("Select cod_condicion,nombre_condicion,estado,tipo from condiciones_pagos " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo.addRow(new Object[]{rs.getInt("cod_condicion"), rs.getString("nombre_condicion"), rs.getBoolean("estado"), rs.getInt("tipo")});
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("Cobradores")) {
                  preparedStatement = conexion.prepareStatement("Select cod_cobrador,nombre_cobrador,estado,direccion,telefono from cobradores " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_cobrador"),
                              rs.getString("nombre_cobrador"),
                              rs.getBoolean("estado"),
                              rs.getString("direccion"),
                              rs.getString("telefono")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("Tarjetas")) {
                  preparedStatement = conexion.prepareStatement(
                     "select\r\ntarjetas.cod_tarjeta,tarjetas.nombre_tarjeta,tarjetas.estado,tarjetas.tipo,tarjetas.cod_proveedor,\r\n(select proveedores.nombre_proveedor from proveedores where proveedores.cod_proveedor = tarjetas.cod_proveedor) as nombre_proveedor\r\n from tarjetas "
                        + condicion
                  );
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_tarjeta"),
                              rs.getString("nombre_tarjeta"),
                              rs.getBoolean("estado"),
                              rs.getInt("tipo"),
                              rs.getInt("cod_proveedor"),
                              rs.getString("nombre_proveedor")
                           }
                        );
                  }

                  var10 = false;
               } else if (this.nom_tabla.trim().equals("Motivos")) {
                  preparedStatement = conexion.prepareStatement("select cod_motivo,nombre_motivo,estado,tesoreria,gastos,cajachica from motivos " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo
                        .addRow(
                           new Object[]{
                              rs.getInt("cod_motivo"),
                              rs.getString("nombre_motivo"),
                              rs.getBoolean("estado"),
                              rs.getInt("tesoreria"),
                              rs.getInt("gastos"),
                              rs.getInt("cajachica")
                           }
                        );
                  }

                  var10 = false;
               } else if (!this.nom_tabla.trim().equals("MotivosNC")) {
                  var10 = false;
               } else {
                  preparedStatement = conexion.prepareStatement("select cod_motivo,nombre_motivo,estado,tipo from motivos_nc " + condicion);
                  rs = preparedStatement.executeQuery();

                  while (rs.next()) {
                     this.modelo.addRow(new Object[]{rs.getInt("cod_motivo"), rs.getString("nombre_motivo"), rs.getBoolean("estado"), rs.getInt("tipo")});
                  }

                  var10 = false;
               }
               break label556;
            } catch (Exception var11) {
               LogErrores.errores(var11, "Error al seleccionar " + this.nom_tabla, frame);
               var10 = false;
            } finally {
               if (var10) {
                  new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar " + this.nom_tabla);
                  DatabaseConnection.getInstance().cerrarConexion();
               }
            }

            new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar " + this.nom_tabla);
            DatabaseConnection.getInstance().cerrarConexion();
            break label557;
         }

         new CerrarRecursos(preparedStatement, rs, frame, "Error al seleccionar " + this.nom_tabla);
         DatabaseConnection.getInstance().cerrarConexion();
      }

      if (!this.nom_tabla.trim().equals("ParametrosDet") && this.modelo.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecupera.setText(String.valueOf(nf.format((long)this.modelo.getRowCount())));
      }
   }
}
