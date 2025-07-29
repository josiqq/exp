package a2MenuPrincipal;

import a00Productos.ProductosForm;
import a3Permisos.PermisosUsuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.Beans;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import utilidades.BarraJMenu;
import utilidades.BarraJMenuItem;
import utilidades.BarraMenu;
import utilidades.BotonPadre;
import utilidades.BoundedDesktopManager;
import utilidades.LabelPrincipal;
import utilidades.PanelPadre;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorProducto;
import utilidadesVentanas.JFramePrincipal;
import utilidadesVentanas.JInternalPadreReporteJasper;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadreReporte;
import utilidadesVentanas.JinternalPadreString;

public class Principal extends JFramePrincipal {
   private static final long serialVersionUID = 1L;
   private PanelPadre contentPane;
   static JDesktopPane desktopPane;
   private BotonPadre btnBuscar;
   private BotonPadre btnNuevo;
   private BotonPadre btnEliminar;
   private BotonPadre btnGuardar;
   public static Principal instancia;

   public static void visualizarVentana(ProductosForm producto, String nombre) {
      for (int i = 0; i < desktopPane.getComponentCount(); i++) {
         Component c = desktopPane.getComponent(i);
         if (producto.getClass().isInstance(c)) {
            JInternalFrame existente = null;

            try {
               existente = (JInternalFrame)c;
               existente.setIcon(false);
               existente.setSelected(true);
               existente.moveToFront();
            } catch (Exception var7) {
               LogErrores.errores(var7, "Error al iniciar el Formulario", existente);
               var7.printStackTrace();
            }

            return;
         }
      }

      producto.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

      try {
         producto.setVisible(true);
         desktopPane.add(producto);
         producto.setName(nombre);
         producto.setSelected(true);
      } catch (Exception var6) {
         LogErrores.errores(var6, "Error al iniciar el Formulario", null);
         var6.printStackTrace();
      }
   }

   public Principal() {
      instancia = this;
      this.setExtendedState(6);
      this.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
      desktopPane = new JDesktopPane();
      desktopPane.setDesktopManager(new BoundedDesktopManager());
      PanelPadre panel_desktop = new PanelPadre("");
      panel_desktop.setLayout(new BorderLayout());
      panel_desktop.add(desktopPane, "Center");
      BarraMenu menuBar = new BarraMenu();
      this.setJMenuBar(menuBar);
      PermisosUsuario permisos = PermisosUsuario.getInstancia();
      if (permisos.tienePermiso("MConfiguraciones", "ver")) {
         BarraJMenu mnConfiguraciones = new BarraJMenu("Configuraciones");
         menuBar.add(mnConfiguraciones);
         if (permisos.tienePermiso("Parametros", "ver")) {
            BarraJMenuItem mntmParametros = new BarraJMenuItem("Parametros", "a2MenuPrincipal.ParametrosForm", desktopPane);
            mnConfiguraciones.add(mntmParametros);
         }

         if (permisos.tienePermiso("GrupoUsuario", "ver")) {
            BarraJMenuItem mntmGrupoUsuarios = new BarraJMenuItem("Grupos y Usuarios", "a3Permisos.GruposForm", "", desktopPane);
            mnConfiguraciones.add(mntmGrupoUsuarios);
         }

         if (permisos.tienePermiso("Sucursales", "ver")) {
            BarraJMenuItem mntmSucursales = new BarraJMenuItem("Sucursales", "a5Sucursales.SucursalesForm", desktopPane);
            mnConfiguraciones.add(mntmSucursales);
         }

         if (permisos.tienePermiso("Depositos", "ver")) {
            BarraJMenuItem mntmDepositos = new BarraJMenuItem("Depositos", "a6Depositos.DepositosForm", desktopPane);
            mnConfiguraciones.add(mntmDepositos);
         }

         if (permisos.tienePermiso("TipoDocumentos", "ver")) {
            BarraJMenuItem mntTipoDocumentos = new BarraJMenuItem("Tipo de Documentos", "a00TipoDocumentos.TipoDocumentosForm", desktopPane);
            mnConfiguraciones.add(mntTipoDocumentos);
         }

         if (permisos.tienePermiso("ActualizarPassword", "ver")) {
            BarraJMenuItem mntmActualizarPassword = new BarraJMenuItem("Actualizar Password", "a3Permisos.ActualizarPasswordForm", desktopPane);
            mnConfiguraciones.add(mntmActualizarPassword);
         }

         if (permisos.tienePermiso("ConfigurarImpresora", "ver")) {
            BarraJMenuItem mntmConfigurarImpresora = new BarraJMenuItem("Configurar Impresoras", "aa9Impresoras.ImpresorasForm", desktopPane);
            mnConfiguraciones.add(mntmConfigurarImpresora);
         }

         if (permisos.tienePermiso("Motivos", "ver")) {
            BarraJMenuItem mntmMotivos = new BarraJMenuItem("Motivos", "a0099Motivos.MotivosForm", desktopPane);
            mnConfiguraciones.add(mntmMotivos);
         }

         if (permisos.tienePermiso("Paises", "ver")) {
            BarraJMenuItem mntmPaises = new BarraJMenuItem("Paises", "a99Paises.PaisesForm", desktopPane);
            mnConfiguraciones.add(mntmPaises);
         }

         if (permisos.tienePermiso("Ciudades", "ver")) {
            BarraJMenuItem mntmCiudades = new BarraJMenuItem("Ciudades", "a99Paises.CiudadesForm", desktopPane);
            mnConfiguraciones.add(mntmCiudades);
         }
      }

      if (permisos.tienePermiso("MConsultas", "ver")) {
         BarraJMenu mnConsultas = new BarraJMenu("Consultas Rapidas");
         menuBar.add(mnConsultas);
         if (permisos.tienePermiso("ConsultaProductos", "ver")) {
            BuscadorProducto buscador = new BuscadorProducto();
            BarraJMenuItem mntmProductosConsulta = new BarraJMenuItem("Consulta Productos", buscador, desktopPane);
            mnConsultas.add(mntmProductosConsulta);
         }

         if (permisos.tienePermiso("ConsultaReservasProducto", "ver")) {
            BarraJMenuItem bloqueoReservaProducto = new BarraJMenuItem("Productos Reservados", "a0099ConsultasRapidas.BloqueoReservasForm", false, desktopPane);
            mnConsultas.add(bloqueoReservaProducto);
         }
      }

      if (permisos.tienePermiso("MStock", "ver")) {
         BarraJMenu mnStock = new BarraJMenu("Stock");
         menuBar.add(mnStock);
         if (permisos.tienePermiso("StockFichas", "ver")) {
            BarraJMenu mnFichasStock = new BarraJMenu("Fichas");
            mnStock.add(mnFichasStock);
            if (permisos.tienePermiso("Marcas", "ver")) {
               BarraJMenuItem mntmMarcas = new BarraJMenuItem("Marcas", "A8Marcas.MarcasForm", desktopPane);
               mnFichasStock.add(mntmMarcas);
            }

            if (permisos.tienePermiso("UnidadesMedidas", "ver")) {
               BarraJMenuItem mntmUnidadesMedidas = new BarraJMenuItem("Unidades Medidas", "a88UnidadesMedidas.UnidadesMedidasForm", desktopPane);
               mnFichasStock.add(mntmUnidadesMedidas);
            }

            if (permisos.tienePermiso("FamiliaProductos", "ver")) {
               BarraJMenuItem mntmFamiliasProductos = new BarraJMenuItem("Familia de Productos", "a4Familias.FamiliasForm", desktopPane);
               mnFichasStock.add(mntmFamiliasProductos);
            }

            if (permisos.tienePermiso("Productos", "ver")) {
               BarraJMenuItem mntmProductos = new BarraJMenuItem("Productos", "a00Productos.ProductosForm", 0, desktopPane);
               mnFichasStock.add(mntmProductos);
            }

            if (permisos.tienePermiso("MotivosAjuste", "ver")) {
               BarraJMenuItem mntmMotivosAjuste = new BarraJMenuItem("Motivos Ajustes", "a009AjusteStockMotivo.AjusteMotivosForm", desktopPane);
               mnFichasStock.add(mntmMotivosAjuste);
            }
         }

         if (permisos.tienePermiso("AjusteStock", "ver")) {
            BarraJMenuItem mntmAjusteStock = new BarraJMenuItem("Ajuste Stock", "a009AjusteStock.AjusteStockForm", desktopPane);
            mnStock.add(mntmAjusteStock);
         }

         if (permisos.tienePermiso("NotaEnvio", "ver")) {
            BarraJMenuItem mntmNotaEnvio = new BarraJMenuItem("Nota de Envio", "a0099NotaEnvio.NotaEnvioForm", desktopPane);
            mnStock.add(mntmNotaEnvio);
         }

         if (permisos.tienePermiso("EnvioDeposito", "ver")) {
            BarraJMenuItem mntmEnviosDepositos = new BarraJMenuItem("Envio de Deposito", "a0099NotaTransferencia.EnvioDepositoForm", desktopPane);
            mnStock.add(mntmEnviosDepositos);
         }

         if (permisos.tienePermiso("EnvioRecepcion", "ver")) {
            BarraJMenuItem mntmRecepcionEnvio = new BarraJMenuItem("Recepcion de Envio", "a0099NotaTransferenciaRecepcion.EnvioRecepcionForm", desktopPane);
            mnStock.add(mntmRecepcionEnvio);
         }

         if (permisos.tienePermiso("ReporteStock", "ver")) {
            BarraJMenu mnStockReportes = new BarraJMenu("Reportes");
            mnStock.add(mnStockReportes);
            if (permisos.tienePermiso("ReporteStockProductos", "ver")) {
               BarraJMenuItem mntmReportesProducto = new BarraJMenuItem("Stock de Productos", "reportesStock.ProductoStockForm", false, desktopPane);
               mnStockReportes.add(mntmReportesProducto);
            }

            if (permisos.tienePermiso("ListadoProductos", "ver")) {
               BarraJMenuItem mntmReportesProducto = new BarraJMenuItem("Listado de Productos", "reportesStock.ProductosListadoForm", false, desktopPane);
               mnStockReportes.add(mntmReportesProducto);
            }

            if (permisos.tienePermiso("ListadoAjusteStock", "ver")) {
               BarraJMenuItem mntmReportesProducto = new BarraJMenuItem("Ajuste de Stock", "reportesStock.AjusteStockForm", desktopPane, 0);
               mnStockReportes.add(mntmReportesProducto);
            }

            if (permisos.tienePermiso("ReporteNotaEnvio", "ver")) {
               BarraJMenuItem mntmReporteNotaEnvio = new BarraJMenuItem("Nota de Envio", "reportesStock.NotaEnvioForm", desktopPane, 0);
               mnStockReportes.add(mntmReporteNotaEnvio);
            }

            if (permisos.tienePermiso("ReporteEnvioResumen", "ver")) {
               BarraJMenuItem mntmReportesEnvioDepositoResumen = new BarraJMenuItem(
                  "Envio de Depositos Resumen", "reportesStock.EnviosDepositosResumenForm", false, desktopPane
               );
               mnStockReportes.add(mntmReportesEnvioDepositoResumen);
            }

            if (permisos.tienePermiso("ReporteEnvioDeposito", "ver")) {
               BarraJMenuItem mntmReporteEnvioDeposito = new BarraJMenuItem("Envio de Depositos", "reportesStock.EnviosDepositosForm", desktopPane, 0);
               mnStockReportes.add(mntmReporteEnvioDeposito);
            }

            if (permisos.tienePermiso("ReporteStExtractoProducto", "ver")) {
               BarraJMenuItem mntmExtractoProducto = new BarraJMenuItem("Extracto de Productos", "reportesStock.ExtractoProductosForm", false, desktopPane);
               mnStockReportes.add(mntmExtractoProducto);
            }
         }
      }

      if (permisos.tienePermiso("MCompras", "ver")) {
         BarraJMenu mnCompras = new BarraJMenu("Compras");
         menuBar.add(mnCompras);
         if (permisos.tienePermiso("ComprasFichas", "ver")) {
            BarraJMenu mnFichasCompras = new BarraJMenu("Fichas");
            mnCompras.add(mnFichasCompras);
            if (permisos.tienePermiso("Proveedores", "ver")) {
               BarraJMenuItem mntmProveedores = new BarraJMenuItem("Proveedores", "a9Proveedores.ProveedoresForm", desktopPane);
               mnFichasCompras.add(mntmProveedores);
            }

            if (permisos.tienePermiso("Rubros", "ver")) {
               BarraJMenuItem mntmRubros = new BarraJMenuItem("Rubro", "a99Rubros.RubrosForm", desktopPane);
               mnFichasCompras.add(mntmRubros);
            }

            if (permisos.tienePermiso("Compradores", "ver")) {
               BarraJMenuItem mntmCompradores = new BarraJMenuItem("Compradores", "compradores.CompradoresForm", desktopPane);
               mnFichasCompras.add(mntmCompradores);
            }

            if (permisos.tienePermiso("Plazos", "ver")) {
               BarraJMenuItem mntmPlazos = new BarraJMenuItem("Plazos", "a00Plazos.PlazosForm", desktopPane);
               mnFichasCompras.add(mntmPlazos);
            }
         }

         if (permisos.tienePermiso("PedidosProveedores", "ver")) {
            BarraJMenuItem mntmPedidosProveedores = new BarraJMenuItem("Pedidos a Proveedores", "a00pedidosProveedores.PedidosProveedorForm", desktopPane);
            mnCompras.add(mntmPedidosProveedores);
         }

         if (permisos.tienePermiso("Compras", "ver")) {
            BarraJMenuItem mntmCompras = new BarraJMenuItem("Compras", "a00Compras.ComprasForm", desktopPane);
            mnCompras.add(mntmCompras);
         }

         if (permisos.tienePermiso("GastosProveedores", "ver")) {
            BarraJMenuItem mntmGastosProveedores = new BarraJMenuItem("Gastos Proveedores", "a00ComprasGastos.GastosForm", desktopPane);
            mnCompras.add(mntmGastosProveedores);
         }

         if (permisos.tienePermiso("DevolucionProveedor", "ver")) {
            BarraJMenuItem mntmDevolucionCompra = new BarraJMenuItem("Devolucion a Proveedores", "a0098DevolucionCompras.DevolucionComprasForm", desktopPane);
            mnCompras.add(mntmDevolucionCompra);
         }

         if (permisos.tienePermiso("NotaCreditoCompra", "ver")) {
            BarraJMenuItem mntmNotaCreditoCompra = new BarraJMenuItem("Nota de Credito", "a0098NotaCreditoCompra.NotaCreditoCompraForm", desktopPane);
            mnCompras.add(mntmNotaCreditoCompra);
         }

         if (permisos.tienePermiso("NotaDebitoCompra", "ver")) {
            BarraJMenuItem mntmNotaDebitoCompra = new BarraJMenuItem("Nota de Debito", "a0098NotaDebitoCompras.NotaDebitoCompraForm", desktopPane);
            mnCompras.add(mntmNotaDebitoCompra);
         }

         if (permisos.tienePermiso("OrdenCompra", "ver")) {
            BarraJMenuItem mntmOrdenCompra = new BarraJMenuItem("Orden de Compra", "a0098OrdenCompra.OrdenCompraForm", desktopPane);
            mnCompras.add(mntmOrdenCompra);
         }

         if (permisos.tienePermiso("ReporteCompra", "ver")) {
            BarraJMenu mnCompraReportes = new BarraJMenu("Reportes");
            mnCompras.add(mnCompraReportes);
            if (permisos.tienePermiso("ReporteComprasProducto", "ver")) {
               BarraJMenuItem mntmReportesProductocompra = new BarraJMenuItem(
                  "Compras por Productos", "reportesCompra.ReporteComprasProductoForm", false, desktopPane
               );
               mnCompraReportes.add(mntmReportesProductocompra);
            }

            if (permisos.tienePermiso("ReporteListadoPedidosCompra", "ver")) {
               BarraJMenuItem mntmListadoPedidoCompra = new BarraJMenuItem("Listado de Pedidos", "reportesCompra.ListadoPedidosCompraForm", desktopPane, 0);
               mnCompraReportes.add(mntmListadoPedidoCompra);
            }

            if (permisos.tienePermiso("ReporteListadoCompras", "ver")) {
               BarraJMenuItem mntmListadoPedidoCompra = new BarraJMenuItem("Listado de Compras", "reportesCompra.ListadoComprasForm", desktopPane, 0);
               mnCompraReportes.add(mntmListadoPedidoCompra);
            }

            if (permisos.tienePermiso("ReporteComprasporFactura", "ver")) {
               BarraJMenuItem mntmReporteCompraPorFactura = new BarraJMenuItem(
                  "Listado de Compras por Factura", "reportesCompra.ReporteComprasporFactura", desktopPane, 0
               );
               mnCompraReportes.add(mntmReporteCompraPorFactura);
            }

            if (permisos.tienePermiso("ReporteComprasDevolucion", "ver")) {
               BarraJMenuItem mntmReporteCompraDevolucion = new BarraJMenuItem(
                  "Listado de Devoluciones a Proveedores", "reportesCompra.ListadoComprasDevolucionForm", desktopPane, 0
               );
               mnCompraReportes.add(mntmReporteCompraDevolucion);
            }

            if (permisos.tienePermiso("ReporteComprasGastos", "ver")) {
               BarraJMenuItem mntmReportesCompraGastos = new BarraJMenuItem(
                  "Compras por Productos", "reportesCompra.ReporteComprasGastosForm", false, desktopPane
               );
               mnCompraReportes.add(mntmReportesCompraGastos);
            }

            if (permisos.tienePermiso("ReporteComprasNC", "ver")) {
               BarraJMenuItem mntmReporteCompraNC = new BarraJMenuItem("Listado de Nota de Credito", "reportesCompra.ListadoComprasNCForm", desktopPane, 0);
               mnCompraReportes.add(mntmReporteCompraNC);
            }

            if (permisos.tienePermiso("ReporteComprasND", "ver")) {
               BarraJMenuItem mntmReporteCompraND = new BarraJMenuItem("Listado de Nota de Debito", "reportesCompra.ListadoComprasNDForm", desktopPane, 0);
               mnCompraReportes.add(mntmReporteCompraND);
            }

            if (permisos.tienePermiso("ReporteCompraOC", "ver")) {
               BarraJMenuItem mntmReportesCompraOC = new BarraJMenuItem("Compras por Productos", "reportesCompra.ReporteComprasOCForm", false, desktopPane);
               mnCompraReportes.add(mntmReportesCompraOC);
            }
         }
      }

      if (permisos.tienePermiso("MVentas", "ver")) {
         BarraJMenu mnVentas = new BarraJMenu("Ventas");
         menuBar.add(mnVentas);
         if (permisos.tienePermiso("Ventas", "ver")) {
            BarraJMenu mnFichasVentas = new BarraJMenu("Fichas");
            mnVentas.add(mnFichasVentas);
            if (permisos.tienePermiso("Clientes", "ver")) {
               BarraJMenuItem mntmClientes = new BarraJMenuItem("Clientes", "a00Clientes.ClientesForm", desktopPane);
               mnFichasVentas.add(mntmClientes);
            }

            if (permisos.tienePermiso("Vendedores", "ver")) {
               BarraJMenuItem mntmVendedores = new BarraJMenuItem("Vendedores", "vendedores.VendedoresForm", desktopPane);
               mnFichasVentas.add(mntmVendedores);
            }

            if (permisos.tienePermiso("LugaresVentas", "ver")) {
               BarraJMenuItem mntmLugarVenta = new BarraJMenuItem("Lugares de Ventas", "a0099VentasLugares.VentasLugaresForm", desktopPane);
               mnFichasVentas.add(mntmLugarVenta);
            }

            if (permisos.tienePermiso("CategoriaClientes", "ver")) {
               BarraJMenuItem mntmCategoriaCliente = new BarraJMenuItem("Categoria de Cliente", "a99CategoriaCliente.CategoriaClienteForm", desktopPane);
               mnFichasVentas.add(mntmCategoriaCliente);
            }

            if (permisos.tienePermiso("CondicionVenta", "ver")) {
               BarraJMenuItem mntmCondicionVenta = new BarraJMenuItem("Condicion de Venta", "a00CondicionesVentas.CondicionesVentasForm", desktopPane);
               mnFichasVentas.add(mntmCondicionVenta);
            }

            if (permisos.tienePermiso("MotivosNC", "ver")) {
               BarraJMenuItem mntmMotivosNC = new BarraJMenuItem("Motivos NC", "a0098MotivosNC.MotivosNCForm", desktopPane);
               mnFichasVentas.add(mntmMotivosNC);
            }
         }

         if (permisos.tienePermiso("PedidosClientes", "ver")) {
            BarraJMenuItem mntmPedidoClientes = new BarraJMenuItem("Pedido de Cliente", "a00PedidosClientes.PedidosClientesForm", desktopPane);
            mnVentas.add(mntmPedidoClientes);
         }

         if (permisos.tienePermiso("Ventas", "ver")) {
            BarraJMenuItem mntmVentas = new BarraJMenuItem("Ventas", "a00Ventas.VentasForm", desktopPane);
            mnVentas.add(mntmVentas);
         }

         if (permisos.tienePermiso("AjustePrecios", "ver")) {
            BarraJMenuItem mntmAjustePrecios = new BarraJMenuItem("Ajuste de Precios", "a009AjustePrecios.AjustePreciosForm", desktopPane);
            mnVentas.add(mntmAjustePrecios);
         }

         if (permisos.tienePermiso("ListaPrecios", "ver")) {
            BarraJMenuItem mntmListaPrecios = new BarraJMenuItem("Lista de Precios", "a0099ListaPrecios.ListaPreciosForm", desktopPane);
            mnVentas.add(mntmListaPrecios);
         }

         if (permisos.tienePermiso("DevolucionClientes", "ver")) {
            BarraJMenuItem mntmDevolucionesVenta = new BarraJMenuItem("Devolucion de Clientes", "a0098DevolucionVentas.DevolucionVentasForm", desktopPane);
            mnVentas.add(mntmDevolucionesVenta);
         }

         if (permisos.tienePermiso("NotaCreditoVenta", "ver")) {
            BarraJMenuItem mntmNotaCreditoVenta = new BarraJMenuItem("Nota de Credito", "a0098NotaCreditoVenta.NotaCreditoVentaForm", desktopPane);
            mnVentas.add(mntmNotaCreditoVenta);
         }

         if (permisos.tienePermiso("NotaDebitoVenta", "ver")) {
            BarraJMenuItem mntmNotaDebitoVenta = new BarraJMenuItem("Nota de Debito", "a0098NotaDebitoVenta.NotaDebitoVentaForm", desktopPane);
            mnVentas.add(mntmNotaDebitoVenta);
         }

         if (permisos.tienePermiso("ReporteVentas", "ver")) {
            BarraJMenu mnVentasReportes = new BarraJMenu("Reportes");
            mnVentas.add(mnVentasReportes);
            if (permisos.tienePermiso("ListadoPrecio", "ver")) {
               BarraJMenuItem mntmListadoPrecio = new BarraJMenuItem("Listado de Precios", "reportesVentas.ListadoPrecioForm", false, desktopPane);
               mnVentasReportes.add(mntmListadoPrecio);
            }

            if (permisos.tienePermiso("ReporteVentasListado", "ver")) {
               BarraJMenuItem mntmListadoPrecio = new BarraJMenuItem("Listado de Ventas", "reportesVentas.ListadoVentasForm", desktopPane, 0);
               mnVentasReportes.add(mntmListadoPrecio);
            }

            if (permisos.tienePermiso("ReporteVentasResumen", "ver")) {
               BarraJMenuItem mntmListadoPrecio = new BarraJMenuItem("Listado de Ventas Resumen", "reportesVentas.ListadoVentasResumenForm", false, desktopPane);
               mnVentasReportes.add(mntmListadoPrecio);
            }

            if (permisos.tienePermiso("ReporteVentaRankingProducto", "ver")) {
               BarraJMenuItem mntmReporteStockProducto = new BarraJMenuItem(
                  "Ventas por Ranking Productos", "reportesVentas.ListadoRankingProductosForm", false, desktopPane
               );
               mnVentasReportes.add(mntmReporteStockProducto);
            }

            if (permisos.tienePermiso("ReporteVentaRankingClientes", "ver")) {
               BarraJMenuItem mntmReporteStockProducto = new BarraJMenuItem(
                  "Ventas por Ranking Clientes", "reportesVentas.ListadoRankingClientesForm", false, desktopPane
               );
               mnVentasReportes.add(mntmReporteStockProducto);
            }

            if (permisos.tienePermiso("ReporteVentaxMarcas", "ver")) {
               BarraJMenuItem mntmReporteStockProducto = new BarraJMenuItem("Ventas por Marcas", "reportesVentas.ListadoVentasXMarcas", false, desktopPane);
               mnVentasReportes.add(mntmReporteStockProducto);
            }

            if (permisos.tienePermiso("ReporteVentaxProveedor", "ver")) {
               BarraJMenuItem mntmReporteStockProducto = new BarraJMenuItem(
                  "Ventas por Proveedores", "reportesVentas.ListadoVentasXProveedor", false, desktopPane
               );
               mnVentasReportes.add(mntmReporteStockProducto);
            }

            if (permisos.tienePermiso("ReporteListadoPedidosVta", "ver")) {
               BarraJMenuItem mntmListadoPrecio = new BarraJMenuItem("Listado de Pedidos", "reportesVentas.ListadoPedidosVtaForm", desktopPane, 0);
               mnVentasReportes.add(mntmListadoPrecio);
            }
         }
      }

      if (permisos.tienePermiso("MFinanciero", "ver")) {
         BarraJMenu mnFinanciero = new BarraJMenu("Financiero");
         menuBar.add(mnFinanciero);
         if (permisos.tienePermiso("FinancieroFicha", "ver")) {
            BarraJMenu mnFichasFinanciero = new BarraJMenu("Financiero");
            mnFinanciero.add(mnFichasFinanciero);
            if (permisos.tienePermiso("Cajeros", "ver")) {
               BarraJMenuItem mntmCajeros = new BarraJMenuItem("Cajeros", "cajeros.CajerosForm", desktopPane);
               mnFichasFinanciero.add(mntmCajeros);
            }

            if (permisos.tienePermiso("Monedas", "ver")) {
               BarraJMenuItem mntmMonedas = new BarraJMenuItem("Monedas", "a11Monedas.MonedasForm", desktopPane);
               mnFichasFinanciero.add(mntmMonedas);
            }

            if (permisos.tienePermiso("Bancos", "ver")) {
               BarraJMenuItem mntmBancos = new BarraJMenuItem("Bancos", "a00Bancos.BancosForm", desktopPane);
               mnFichasFinanciero.add(mntmBancos);
            }

            if (permisos.tienePermiso("Cuentas", "ver")) {
               BarraJMenuItem mntmCuentas = new BarraJMenuItem("Cuentas", "a00Cuentas.CuentasForm", desktopPane);
               mnFichasFinanciero.add(mntmCuentas);
            }

            if (permisos.tienePermiso("Cotizaciones", "ver")) {
               BarraJMenuItem mntmCotizaciones = new BarraJMenuItem("Cotizaciones", "a00Cotizaciones.CotizacionesForm", desktopPane);
               mnFichasFinanciero.add(mntmCotizaciones);
            }

            if (permisos.tienePermiso("CondicionPago", "ver")) {
               BarraJMenuItem mntmCondicionPago = new BarraJMenuItem("Condicion de Pago", "a099CajaCondicionPago.CondicionPagoForm", desktopPane);
               mnFichasFinanciero.add(mntmCondicionPago);
            }

            if (permisos.tienePermiso("Cobradores", "ver")) {
               BarraJMenuItem mntmCobradores = new BarraJMenuItem("Cobradores", "a0099Cobradores.CobradoresForm", desktopPane);
               mnFichasFinanciero.add(mntmCobradores);
            }

            if (permisos.tienePermiso("Tarjetas", "ver")) {
               BarraJMenuItem mntmTarjetas = new BarraJMenuItem("Tarjetas", "a0099Tarjetas.TarjetasForm", desktopPane);
               mnFichasFinanciero.add(mntmTarjetas);
            }
         }

         if (permisos.tienePermiso("AperturaPlanilla", "ver")) {
            BarraJMenuItem mntmAperturaCaja = new BarraJMenuItem("Apertura de Planilla", "a99CajaApertura.AperturaCajaForm", desktopPane);
            mnFinanciero.add(mntmAperturaCaja);
         }

         if (permisos.tienePermiso("CobrosCaja", "ver")) {
            BarraJMenuItem mntmCobroCaja = new BarraJMenuItem("Cobros Caja", "a99CajaCobro.CajaCobroForm", desktopPane);
            mnFinanciero.add(mntmCobroCaja);
         }

         if (permisos.tienePermiso("CierrePlanilla", "ver")) {
            BarraJMenuItem mntmCierreCaja = new BarraJMenuItem("Cierre de Planilla", "a99CajaCierre.CajaCierreForm", desktopPane);
            mnFinanciero.add(mntmCierreCaja);
         }

         if (permisos.tienePermiso("MovimientoCuenta", "ver")) {
            BarraJMenuItem mntmMovimientoCuenta = new BarraJMenuItem("Movimiento de Cuentas", "a009MovimientosCuentas.MovimientoCuentasForm", desktopPane);
            mnFinanciero.add(mntmMovimientoCuenta);
         }

         if (permisos.tienePermiso("CajaChica", "ver")) {
            BarraJMenuItem mntmCajaChica = new BarraJMenuItem("Caja Chica", "a99CajaChica.CajaChicaForm", desktopPane);
            mnFinanciero.add(mntmCajaChica);
         }
      }

      if (permisos.tienePermiso("MUtilidades", "ver")) {
         BarraJMenu mnUtilidades = new BarraJMenu("Utilidades");
         menuBar.add(mnUtilidades);
         if (permisos.tienePermiso("RecalculoStock", "ver")) {
            BarraJMenuItem mntmRecalculoStock = new BarraJMenuItem("Recalculo Stock", "utilidadesRecalculos.StockForm", desktopPane);
            mnUtilidades.add(mntmRecalculoStock);
         }

         if (permisos.tienePermiso("RecalculoCosto", "ver")) {
            BarraJMenuItem mntmRecalculoStock = new BarraJMenuItem("Recalculo Costo", "utilidadesRecalculos.CostoForm", desktopPane);
            mnUtilidades.add(mntmRecalculoStock);
         }

         if (permisos.tienePermiso("RecalculoLotes", "ver")) {
            BarraJMenuItem mntmRecalculoStock = new BarraJMenuItem("Recalculo Lotes", "utilidadesRecalculos.LotesForm", desktopPane);
            mnUtilidades.add(mntmRecalculoStock);
         }
      }

      this.setBounds(100, 100, 860, 550);
      this.contentPane = new PanelPadre("");
      this.setContentPane(this.contentPane);
      PanelPadre panel_botones = new PanelPadre("");
      PanelPadre panel_datos_sistema = new PanelPadre("");
      LabelPrincipal labelDatosSistema = new LabelPrincipal("", "label");
      if (!Beans.isDesignTime()) {
         ParametrosE parametro = ParametrosE.buscarParametros2(null);
         DatabaseConnection db = DatabaseConnection.getInstance();
         labelDatosSistema.setText(
            "<html><b>Nro. Version:&nbsp;&nbsp;&nbsp;</b>  "
               + parametro.getNro_version()
               + " <b>&nbsp;&nbsp;&nbsp;&nbsp;Usuario:&nbsp;&nbsp;&nbsp;</b>"
               + db.getUsuario()
               + " <b>&nbsp;&nbsp;&nbsp;&nbsp;Nombre Empresa:&nbsp;&nbsp;&nbsp;</b>"
               + parametro.getNombre_empresa()
               + "</html>"
         );
      }

      this.btnGuardar = new BotonPadre("Guardar", "guardar.png");
      this.btnGuardar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JInternalFrame frameActivo = Principal.desktopPane.getSelectedFrame();
            if (frameActivo instanceof JinternalPadre) {
               ((JinternalPadre)frameActivo).guardar();
            } else if (frameActivo instanceof JinternalPadreString) {
               ((JinternalPadreString)frameActivo).guardar();
            } else {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe una ventana activa ", "error");
               rs.setLocationRelativeTo(Principal.this);
               rs.setVisible(true);
            }
         }
      });
      this.btnEliminar = new BotonPadre("Eliminar", "eliminar.png");
      this.btnEliminar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JInternalFrame frameActivo = Principal.desktopPane.getSelectedFrame();
            if (frameActivo instanceof JinternalPadre) {
               ((JinternalPadre)frameActivo).eliminar();
            } else {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe una ventana activa ", "error");
               rs.setLocationRelativeTo(Principal.this);
               rs.setVisible(true);
            }
         }
      });
      this.btnNuevo = new BotonPadre("Nuevo", "nuevo.png");
      this.btnNuevo.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JInternalFrame frameActivo = Principal.desktopPane.getSelectedFrame();
            if (frameActivo instanceof JinternalPadre) {
               ((JinternalPadre)frameActivo).nuevo();
            } else {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe una ventana activa ", "error");
               rs.setLocationRelativeTo(Principal.this);
               rs.setVisible(true);
            }
         }
      });
      this.btnBuscar = new BotonPadre("Buscar", "buscar.png");
      this.btnBuscar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JInternalFrame frameActivo = Principal.desktopPane.getSelectedFrame();
            if (frameActivo instanceof JinternalPadreReporte) {
               ((JinternalPadreReporte)frameActivo).recuperar();
            } else if (frameActivo instanceof JInternalPadreReporteJasper) {
               ((JInternalPadreReporteJasper)frameActivo).recuperar();
            } else {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe una ventana activa ", "error");
               rs.setLocationRelativeTo(Principal.this);
               rs.setVisible(true);
            }
         }
      });
      BotonPadre btnImprimir = new BotonPadre("Imprimir", "printer.png");
      btnImprimir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JInternalFrame frameActivo = Principal.desktopPane.getSelectedFrame();
            if (frameActivo instanceof JinternalPadreReporte) {
               ((JinternalPadreReporte)frameActivo).imprimir();
            } else if (frameActivo instanceof JInternalPadreReporteJasper) {
               ((JInternalPadreReporteJasper)frameActivo).imprimirPDF();
            } else {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe una ventana activa ", "error");
               rs.setLocationRelativeTo(Principal.this);
               rs.setVisible(true);
            }
         }
      });
      BotonPadre btnExportarExcel = new BotonPadre("Exportar Excel", "excel.png");
      btnExportarExcel.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JInternalFrame frameActivo = Principal.desktopPane.getSelectedFrame();
            System.out.println("Clase activa: " + frameActivo.getClass().getName());
            if (frameActivo instanceof JinternalPadreReporte) {
               ((JinternalPadreReporte)frameActivo).exportarExcel();
            } else if (frameActivo instanceof JInternalPadreReporteJasper) {
               ((JInternalPadreReporteJasper)frameActivo).exportarExcel();
            } else {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe una ventana activa ", "error");
               rs.setLocationRelativeTo(Principal.this);
               rs.setVisible(true);
            }
         }
      });
      BotonPadre btnPDF = new BotonPadre("Exportar PDF", "pdf.png");
      btnPDF.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JInternalFrame frameActivo = Principal.desktopPane.getSelectedFrame();
            if (frameActivo instanceof JInternalPadreReporteJasper) {
               ((JInternalPadreReporteJasper)frameActivo).exportarPDF();
            } else {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe una ventana activa ", "error");
               rs.setLocationRelativeTo(Principal.this);
               rs.setVisible(true);
            }
         }
      });
      btnPDF.setFocusable(false);
      GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup().addComponent(panel_botones, -1, 1482, 32767).addContainerGap())
                        .addGroup(gl_contentPane.createSequentialGroup().addComponent(panel_desktop, -1, 1492, 32767).addGap(0))
                  )
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_botones, -2, 42, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_desktop, -1, 688, 32767)
                  .addGap(0)
            )
      );
      GroupLayout gl_panel_botones = new GroupLayout(panel_botones);
      gl_panel_botones.setHorizontalGroup(
         gl_panel_botones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_botones.createSequentialGroup()
                  .addComponent(this.btnNuevo, -2, 90, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnGuardar, -2, 97, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnEliminar, -2, 99, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnBuscar, -2, 99, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(btnImprimir, -2, 99, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(btnExportarExcel, -2, 129, -2)
                  .addGap(4)
                  .addComponent(btnPDF, -2, 129, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_datos_sistema, -1, 687, 32767)
                  .addContainerGap()
            )
      );
      gl_panel_botones.setVerticalGroup(
         gl_panel_botones.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_panel_botones.createSequentialGroup()
                  .addGroup(
                     gl_panel_botones.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_botones.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.btnNuevo, -2, -1, -2)
                              .addComponent(this.btnGuardar, -1, -1, 32767)
                              .addComponent(this.btnEliminar, -1, -1, 32767)
                              .addComponent(this.btnBuscar, -1, -1, 32767)
                              .addComponent(btnImprimir, -2, 35, -2)
                        )
                        .addComponent(btnExportarExcel, -2, 35, -2)
                        .addComponent(btnPDF, -2, 35, -2)
                        .addComponent(panel_datos_sistema, -2, 33, -2)
                  )
                  .addContainerGap()
            )
      );
      GroupLayout gl_panel_datos_sistema = new GroupLayout(panel_datos_sistema);
      gl_panel_datos_sistema.setHorizontalGroup(
         gl_panel_datos_sistema.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_datos_sistema.createSequentialGroup().addComponent(labelDatosSistema, -1, 418, 32767).addContainerGap())
      );
      gl_panel_datos_sistema.setVerticalGroup(
         gl_panel_datos_sistema.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_datos_sistema.createSequentialGroup().addContainerGap().addComponent(labelDatosSistema, -2, -1, -2).addContainerGap(-1, 32767))
      );
      panel_datos_sistema.setLayout(gl_panel_datos_sistema);
      panel_botones.setLayout(gl_panel_botones);
      this.contentPane.setLayout(gl_contentPane);
      this.setDefaultCloseOperation(0);
      this.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            boolean hayPadresAbiertos = false;
            System.out.println("POLLO PAPA GEIIII");

            JInternalFrame[] var6;
            for (JInternalFrame frame : var6 = Principal.desktopPane.getAllFrames()) {
               if (frame instanceof JinternalPadre padre && !padre.isClosed()) {
                  hayPadresAbiertos = true;
                  break;
               }
            }

            if (hayPadresAbiertos) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("Existe ventana abierta aun ! ", "error");
               rs.setLocationRelativeTo(Principal.this);
               rs.setVisible(true);
            } else {
               DialogoMessagebox d = new DialogoMessagebox("Desea salir del Sistema ?");
               d.setLocationRelativeTo(Principal.this);
               d.setVisible(true);
               if (d.isResultadoEncontrado()) {
                  System.exit(0);
               }
            }
         }
      });
      btnExportarExcel.setFocusable(false);
      btnImprimir.setFocusable(false);
      this.btnBuscar.setFocusable(false);
      this.btnEliminar.setFocusable(false);
      this.btnGuardar.setFocusable(false);
      this.btnNuevo.setFocusable(false);
   }

   public static Principal getInstancia() {
      if (instancia == null) {
         instancia = new Principal();
      }

      return instancia;
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            Principal frame = new Principal();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               var3.printStackTrace();
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
            }
         }
      });
   }
}
