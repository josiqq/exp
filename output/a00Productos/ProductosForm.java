package a00Productos;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Beans;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import utilidades.BotonPadre;
import utilidades.CheckPadre;
import utilidades.Combo;
import utilidades.CuadroTexto2Decimales;
import utilidades.GrupoRadioButton;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextArea;
import utilidades.LimiteTextField;
import utilidades.LimiteTextFieldConSQL;
import utilidades.PanelPadre;
import utilidades.TextFieldFamilia;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTabla;
import utilidadesTabla.BuscadorTablaCargar;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesTabla.TablaBuscador;
import utilidadesVentanas.JinternalPadreString;

public class ProductosForm extends JinternalPadreString implements ActionListener {
   List<String> codigosAEliminar = new ArrayList<>();
   private static final long serialVersionUID = 1L;
   private CheckPadre chckProduccion;
   private CheckPadre chckPerecedero;
   private CheckPadre chckManejaStock;
   private CheckPadre chckUltimaCompra;
   private CheckPadre chckCostoPromedio;
   private CheckPadre chckActivoCompra;
   private CheckPadre chckActivoVta;
   private CheckPadre chckActivo;
   private LabelPrincipal lblNombreSeccionTexto;
   private LabelPrincipal lblNombreGrupoTexto;
   private LabelPrincipal lblNombreSubSeccionTexto;
   private LabelPrincipal lblNombreProveedorTexto;
   private LabelPrincipal lblNombreMarcaTexto;
   private LabelPrincipal lblNombreUnidadMedidaTexto;
   private LabelPrincipal lblNombreMonedaTexto;
   private LabelPrincipal lblCostoTexto;
   private ModeloTablaDefecto modeloTabla;
   private BuscadorTabla tabla;
   public int SW;
   private LimiteTextArea txt_descripcion;
   private TablaCodigoBarras tabla_codigo_barras;
   private LimiteTextFieldConSQL txt_cod_proveedor;
   private LimiteTextFieldConSQL txt_cod_unidad_medida;
   private LimiteTextFieldConSQL txt_cod_marca;
   private Combo combo_tipo_fiscal;
   private CuadroTexto2Decimales txt_porcentaje_gravado;
   private CuadroTexto2Decimales txt_iva;
   private LimiteTextField txt_cod_grupo;
   private LimiteTextField txt_cod_sub_seccion;
   private LimiteTextField txt_buscador;
   private TextFieldFamilia txt_cod_seccion;
   private BotonPadre btnGuardarCodigoBarra;
   private CuadroTexto2Decimales txt_porcentaje_regimen;
   private LabelPrincipal lblLineasRecuperadasTexto;
   private CheckPadre chckStockNegativo;

   @Override
   public void nuevo() {
      this.inicializarObjetos();
   }

   public void buscar(String codigo) {
      ProductosE b = ProductosE.buscarProducto(codigo, this);
      if (b == null) {
         DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
         rs.setLocationRelativeTo(this);
         rs.setVisible(true);
         this.inicializarObjetos();
      } else {
         this.cargarProductos(b);
         this.txt_descripcion.requestFocusInWindow();
      }
   }

   @Override
   public void buscar() {
      if (!this.txt_codigo.getText().trim().equals("0") && Integer.parseInt(this.txt_codigo.getText()) != this.SW) {
         ProductosE b = ProductosE.buscarProducto(this.txt_codigo.getText().trim(), this);
         if (b == null) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe registro con codigo: " + this.txt_codigo.getText(), "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            this.inicializarObjetos();
         } else {
            this.cargarProductos(b);
         }
      }
   }

   @Override
   public void guardar() {
      DialogoMessagebox d = new DialogoMessagebox("Desea Grabar la Operacion ?");
      d.setLocationRelativeTo(this);
      d.setVisible(true);
      if (d.isResultadoEncontrado()) {
         ProductosE entidad = this.CargarEntidades();
         if (this.SW == 0) {
            if (entidad != null) {
               int codigo = ProductosE.insertarProductos(entidad, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         } else if (this.SW == Integer.parseInt(this.txt_codigo.getText())) {
            int codigo = ProductosE.actualizarProductos(entidad, this);
            if (codigo != 0) {
               DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro actualizado correctamente...", "correcto");
               rs.setLocationRelativeTo(this);
               rs.setVisible(true);
               this.inicializarObjetos();
            }
         }
      }
   }

   @Override
   public void eliminar() {
      if (Integer.parseInt(this.txt_codigo.getText()) == this.SW && this.SW != 0) {
         DialogoMessagebox d = new DialogoMessagebox("Desea eliminar el registro ?");
         d.setLocationRelativeTo(this);
         d.setVisible(true);
         if (d.isResultadoEncontrado()) {
            ProductosE ent = this.CargarEntidades();
            if (ent != null) {
               int codigo = ProductosE.eliminarProductos(ent, this);
               if (codigo != 0) {
                  DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro eliminado correctamente... ", "correcto");
                  rs.setLocationRelativeTo(this);
                  rs.setVisible(true);
                  this.inicializarObjetos();
               }
            }
         }
      }
   }

   public void cargarProductos(ProductosE e) {
      this.txt_codigo.setText(e.getCod_producto());
      this.SW = Integer.parseInt(String.valueOf(e.getCod_producto()));
      this.txt_descripcion.setText(e.getNombre_producto());
      this.txt_cod_marca.setValue(e.getCod_marca());
      this.lblNombreMarcaTexto.setText(e.getNombreMarca());
      this.txt_cod_proveedor.setValue(e.getCod_proveedor());
      this.lblNombreProveedorTexto.setText(e.getNombreProveedor());
      this.txt_cod_seccion.setValue(e.getCod_seccion());
      this.lblNombreSeccionTexto.setText(e.getNombreSeccion());
      this.txt_cod_sub_seccion.setValue(e.getCod_sub_seccion());
      this.lblNombreSubSeccionTexto.setText(e.getNombreSubSeccion());
      this.txt_cod_grupo.setValue(e.getCod_grupo());
      this.lblNombreGrupoTexto.setText(e.getNombreGrupo());
      this.txt_cod_unidad_medida.setValue(e.getCod_unidad_medida());
      this.lblNombreUnidadMedidaTexto.setText(e.getNombreUnidadMedida());
      this.txt_porcentaje_gravado.setValue(e.getPorcentaje_gravado());
      this.txt_iva.setValue(e.getIva_producto());
      if (e.getActivo_compra() == 1) {
         this.chckActivoCompra.setSelected(true);
      } else {
         this.chckActivoCompra.setSelected(false);
      }

      if (e.getActivo_venta() == 1) {
         this.chckActivoVta.setSelected(true);
      } else {
         this.chckActivoVta.setSelected(false);
      }

      if (e.getCalculo_costo() == 0) {
         this.chckCostoPromedio.setSelected(true);
         this.chckUltimaCompra.setSelected(false);
      } else if (e.getCalculo_costo() == 1) {
         this.chckCostoPromedio.setSelected(false);
         this.chckUltimaCompra.setSelected(true);
      }

      if (e.getProduccion() == 1) {
         this.chckProduccion.setSelected(true);
      } else {
         this.chckProduccion.setSelected(false);
      }

      if (e.getControla_stock() == 1) {
         this.chckManejaStock.setSelected(true);
      } else {
         this.chckManejaStock.setSelected(false);
      }

      if (e.getPerecedero() == 1) {
         this.chckPerecedero.setEnabled(true);
      } else {
         this.chckPerecedero.setSelected(false);
      }

      if (e.getStock_negativo() == 0) {
         this.chckStockNegativo.setSelected(false);
      } else {
         this.chckStockNegativo.setSelected(true);
      }

      if (e.getEstado() == 1) {
         this.chckActivo.setSelected(true);
      } else {
         this.chckActivo.setSelected(false);
      }

      ProductosBarrasE.cargarCodigoBarra(this.txt_codigo.getText(), this, this.tabla_codigo_barras);
      this.txt_descripcion.requestFocusInWindow();
   }

   public ProductosE CargarEntidades() {
      int produccion = 0;
      if (this.chckProduccion.isSelected()) {
         produccion = 1;
      }

      int perecedero = 0;
      if (this.chckPerecedero.isSelected()) {
         perecedero = 1;
      }

      int controla_stock = 0;
      if (this.chckManejaStock.isSelected()) {
         controla_stock = 1;
      }

      int activo_compra = 0;
      if (this.chckActivoCompra.isSelected()) {
         activo_compra = 1;
      }

      int activo_vta = 0;
      if (this.chckActivoVta.isSelected()) {
         activo_vta = 1;
      }

      int estado = 0;
      if (this.chckActivo.isSelected()) {
         estado = 1;
      }

      int calculo_costo = 0;
      if (this.chckCostoPromedio.isSelected()) {
         calculo_costo = 0;
      } else if (this.chckUltimaCompra.isSelected()) {
         calculo_costo = 1;
      }

      int tipo_fiscal = this.combo_tipo_fiscal.getSelectedIndex();
      int v_stock_negativo = 0;
      if (this.chckStockNegativo.isSelected()) {
         v_stock_negativo = 1;
      }

      double v_porcentaje_regimen = Double.parseDouble(this.txt_porcentaje_regimen.getText().replace(".", "").replace(",", "."));
      double v_iva = Double.parseDouble(this.txt_iva.getText().replace(".", "").replace(",", "."));
      double v_porcentaje_gravado = Double.parseDouble(this.txt_porcentaje_gravado.getText().replace(".", "").replace(",", "."));

      try {
         return new ProductosE(
            this.txt_codigo.getText(),
            this.txt_descripcion.getText(),
            estado,
            Integer.parseInt(this.txt_cod_unidad_medida.getText()),
            Integer.parseInt(this.txt_cod_marca.getText()),
            Integer.parseInt(this.txt_cod_proveedor.getText()),
            activo_vta,
            activo_compra,
            controla_stock,
            perecedero,
            produccion,
            calculo_costo,
            tipo_fiscal,
            v_iva,
            v_porcentaje_gravado,
            v_porcentaje_regimen,
            Integer.parseInt(this.txt_cod_seccion.getText()),
            Integer.parseInt(this.txt_cod_sub_seccion.getText()),
            Integer.parseInt(this.txt_cod_grupo.getText()),
            v_stock_negativo
         );
      } catch (NumberFormatException var17) {
         LogErrores.errores(var17, "Error al Cargar Entidad! Excepcion Numerica...", this);
         return null;
      } catch (Exception var18) {
         LogErrores.errores(var18, "Error al Cargar Entidad.", this);
         return null;
      }
   }

   public void buscarTabla() {
      int filaSeleccionada = this.tabla.getSelectedRow();
      if (filaSeleccionada != -1) {
         this.txt_descripcion.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nombre").getModelIndex()).toString());
         DecimalFormat df = new DecimalFormat("#,##0.00");
         this.lblCostoTexto
            .setText(
               df.format(
                  Double.parseDouble(String.valueOf(this.tabla.getValueAt(this.tabla.getSelectedRow(), this.tabla.getColumn("CostoProducto").getModelIndex())))
               )
            );
         this.txt_iva
            .setText(
               df.format(Double.parseDouble(String.valueOf(this.tabla.getValueAt(this.tabla.getSelectedRow(), this.tabla.getColumn("IVA").getModelIndex()))))
            );
         this.txt_porcentaje_regimen
            .setText(
               df.format(
                  Double.parseDouble(String.valueOf(this.tabla.getValueAt(this.tabla.getSelectedRow(), this.tabla.getColumn("PorcRegimen").getModelIndex())))
               )
            );
         this.txt_porcentaje_gravado
            .setText(
               df.format(
                  Double.parseDouble(String.valueOf(this.tabla.getValueAt(this.tabla.getSelectedRow(), this.tabla.getColumn("PorcGravado").getModelIndex())))
               )
            );
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("ActivoVta").getModelIndex()).toString().equals("1")) {
            this.chckActivoVta.setSelected(true);
         } else {
            this.chckActivoVta.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("SNegativo").getModelIndex()).toString().equals("1")) {
            this.chckStockNegativo.setSelected(true);
         } else {
            this.chckStockNegativo.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("ActivoCompra").getModelIndex()).toString().equals("1")) {
            this.chckActivoCompra.setSelected(true);
         } else {
            this.chckActivoCompra.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("ControlaStock").getModelIndex()).toString().equals("1")) {
            this.chckManejaStock.setSelected(true);
         } else {
            this.chckManejaStock.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Perecedero").getModelIndex()).toString().equals("1")) {
            this.chckPerecedero.setSelected(true);
         } else {
            this.chckPerecedero.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Produccion").getModelIndex()).toString().equals("1")) {
            this.chckProduccion.setSelected(true);
         } else {
            this.chckProduccion.setSelected(false);
         }

         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CalculoCosto").getModelIndex()).toString().equals("0")) {
            this.chckCostoPromedio.setSelected(true);
            this.chckUltimaCompra.setSelected(false);
         } else {
            this.chckCostoPromedio.setSelected(false);
            this.chckUltimaCompra.setSelected(true);
         }

         this.txt_codigo.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString());
         this.SW = Integer.parseInt(String.valueOf(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString()));
         this.txt_cod_unidad_medida.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodUnidadMedida").getModelIndex()).toString());
         this.lblNombreUnidadMedidaTexto
            .setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreUnidadMedida").getModelIndex()).toString());
         this.txt_cod_marca.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodMarca").getModelIndex()).toString());
         this.lblNombreMarcaTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreMarca").getModelIndex()).toString());
         this.txt_cod_proveedor.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodProveedor").getModelIndex()).toString());
         this.lblNombreProveedorTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreProveedor").getModelIndex()).toString());
         this.txt_cod_seccion.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodSeccion").getModelIndex()).toString());
         this.lblNombreSeccionTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreSeccion").getModelIndex()).toString());
         this.txt_cod_sub_seccion.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodSubSeccion").getModelIndex()).toString());
         this.lblNombreSubSeccionTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreSubSeccion").getModelIndex()).toString());
         this.txt_cod_grupo.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("CodGrupo").getModelIndex()).toString());
         this.lblNombreGrupoTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreGrupo").getModelIndex()).toString());
         this.lblNombreMonedaTexto.setText(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("NombreMoneda").getModelIndex()).toString());
         if (this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Estado").getModelIndex()).toString().equals("true")) {
            this.chckActivo.setSelected(true);
         } else {
            this.chckActivo.setSelected(false);
         }

         ProductosBarrasE.cargarCodigoBarra(this.txt_codigo.getText(), this, this.tabla_codigo_barras);
         this.tabla.requestFocusInWindow();
      }
   }

   private void inicializarObjetos() {
      this.SW = 0;
      this.chckActivo.setSelected(true);
      TablaBuscador.eliminarFilasConWhile(this.modeloTabla);
      ModeloTablaCodigoBarra modeloBarra = (ModeloTablaCodigoBarra)this.tabla_codigo_barras.getModel();
      modeloBarra.deleteRow(this.tabla_codigo_barras);
      modeloBarra.addDefaultRow();
      this.txt_buscador.setValue("");
      this.txt_codigo.setText("0");
      this.txt_cod_grupo.setValue(0);
      this.lblNombreGrupoTexto.setText("");
      this.txt_cod_marca.setValue(0);
      this.lblNombreMarcaTexto.setText("");
      this.txt_cod_proveedor.setValue(0);
      this.lblNombreProveedorTexto.setText("");
      this.txt_cod_seccion.setValue(0);
      this.lblNombreSeccionTexto.setText("");
      this.txt_cod_sub_seccion.setValue(0);
      this.lblNombreSubSeccionTexto.setText("");
      this.txt_cod_unidad_medida.setValue(0);
      this.lblNombreUnidadMedidaTexto.setText("");
      this.txt_iva.setValue(10);
      this.txt_porcentaje_gravado.setValue(100);
      this.txt_porcentaje_regimen.setValue(0);
      this.txt_descripcion.setText("");
      this.txt_descripcion.requestFocusInWindow();
      this.chckActivoCompra.setSelected(false);
      this.chckActivoVta.setSelected(false);
      this.chckCostoPromedio.setSelected(false);
      this.chckManejaStock.setSelected(false);
      this.chckPerecedero.setSelected(false);
      this.chckProduccion.setSelected(false);
      this.chckUltimaCompra.setSelected(false);
      this.combo_tipo_fiscal.setSelectedIndex(0);
      this.codigosAEliminar.clear();
   }

   public ProductosForm() {
      this.setTitle("Registro de Productos");
      this.setBounds(100, 100, 710, 609);
      PanelPadre panel_basico = new PanelPadre("");
      PanelPadre panel_buscador = new PanelPadre("Buscador de Registro");
      PanelPadre panel_tipo_fiscal = new PanelPadre("Impuesto");
      PanelPadre panel_activaciones = new PanelPadre("Activaciones");
      PanelPadre panel_calculo_costo = new PanelPadre("Calculo Costo");
      PanelPadre panel_definiciones = new PanelPadre("");
      PanelPadre panel_familia = new PanelPadre("Familia");
      PanelPadre panel_codigo_barras = new PanelPadre("");
      LabelPrincipal lblCodigo = new LabelPrincipal("Codigo", "label");
      LabelPrincipal lblDescripcion = new LabelPrincipal("Descripcion", "label");
      LabelPrincipal lblUnidadMedida = new LabelPrincipal("Unidad Medida", "label");
      LabelPrincipal lblMarca = new LabelPrincipal("Marca", "label");
      LabelPrincipal lblProveedor = new LabelPrincipal("Proveedor", "label");
      LabelPrincipal lblCosto = new LabelPrincipal("Costo Actual", "label");
      LabelPrincipal lblSeccion = new LabelPrincipal("Seccion", "label");
      LabelPrincipal lblSubSeccion = new LabelPrincipal("Sub Seccion", "label");
      LabelPrincipal lblTipoFiscal = new LabelPrincipal("Tipo Fiscal", "label");
      LabelPrincipal lblGrupo = new LabelPrincipal("Grupo", "label");
      LabelPrincipal lblIVA = new LabelPrincipal("Iva", "label");
      LabelPrincipal lblPorcentajeGravado = new LabelPrincipal("% Gravado", "label");
      LabelPrincipal lblPorcentajeRegimen = new LabelPrincipal("% Regimen", "label");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblNombreSeccionTexto = new LabelPrincipal(0);
      this.lblNombreSubSeccionTexto = new LabelPrincipal(0);
      this.lblNombreGrupoTexto = new LabelPrincipal(0);
      this.lblNombreProveedorTexto = new LabelPrincipal(0);
      this.lblNombreMarcaTexto = new LabelPrincipal(0);
      this.lblNombreUnidadMedidaTexto = new LabelPrincipal(0);
      this.lblCostoTexto = new LabelPrincipal(0);
      this.lblNombreMonedaTexto = new LabelPrincipal(0);
      this.txt_iva = new CuadroTexto2Decimales(2);
      this.txt_porcentaje_gravado = new CuadroTexto2Decimales(2);
      this.txt_porcentaje_regimen = new CuadroTexto2Decimales(2);
      this.txt_buscador = new LimiteTextField(25);
      this.txt_buscador.setName("txt_buscador");
      this.txt_cod_unidad_medida = new LimiteTextFieldConSQL(999999, this.lblNombreUnidadMedidaTexto, "UnidadMedida", this);
      this.txt_cod_marca = new LimiteTextFieldConSQL(999999, this.lblNombreMarcaTexto, "Marcas", this);
      this.txt_cod_proveedor = new LimiteTextFieldConSQL(999999, this.lblNombreProveedorTexto, "Proveedores", this);
      this.txt_cod_sub_seccion = new LimiteTextField(25);
      this.txt_cod_sub_seccion.setEnabled(false);
      this.txt_cod_grupo = new LimiteTextField(25);
      this.txt_cod_grupo.setEnabled(false);
      this.txt_cod_seccion = new TextFieldFamilia(
         99L, this.lblNombreSeccionTexto, this.txt_cod_sub_seccion, this.lblNombreSubSeccionTexto, this.txt_cod_grupo, this.lblNombreGrupoTexto
      );
      this.chckCostoPromedio = new CheckPadre("Promedio");
      this.chckUltimaCompra = new CheckPadre("Ultima Compra");
      ButtonGroup grupoCosto = new GrupoRadioButton();
      grupoCosto.add(this.chckCostoPromedio);
      grupoCosto.add(this.chckUltimaCompra);
      this.chckManejaStock = new CheckPadre("Controla Stock");
      this.chckPerecedero = new CheckPadre("Perecedero");
      this.chckProduccion = new CheckPadre("Produccion");
      this.chckActivo = new CheckPadre("Activo");
      this.chckActivoVta = new CheckPadre("Activo para Venta");
      this.chckActivoCompra = new CheckPadre("Activo para Compra");
      this.chckStockNegativo = new CheckPadre("Stock Negativo");
      this.combo_tipo_fiscal = new Combo(1);
      this.txt_descripcion = new LimiteTextArea(70);
      this.btnGuardarCodigoBarra = new BotonPadre("Guardar");
      String[] cabecera = new String[]{
         "Codigo",
         "Nombre",
         "Estado",
         "CodUnidadMedida",
         "CodMarca",
         "CodProveedor",
         "CostoProducto",
         "CodMonedaCosto",
         "ActivoVta",
         "ActivoCompra",
         "ControlaStock",
         "Perecedero",
         "Produccion",
         "CalculoCosto",
         "TF",
         "IVA",
         "PorcGravado",
         "CodSeccion",
         "CodSubSeccion",
         "CodGrupo",
         "NombreSeccion",
         "NombreSubSeccion",
         "NombreGrupo",
         "NombreUnidadMedida",
         "NombreMarca",
         "NombreProveedor",
         "NombreMoneda",
         "PorcRegimen",
         "SNegativo"
      };
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      this.tabla = new BuscadorTabla(this.modeloTabla, "Productos", this);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(this.tabla);
      this.tabla_codigo_barras = new TablaCodigoBarras(this.codigosAEliminar);
      JScrollPane scrollPane_codigoBarras = new JScrollPane();
      scrollPane_codigoBarras.setViewportView(this.tabla_codigo_barras);
      GroupLayout gl_panel_calculo_costo = new GroupLayout(panel_calculo_costo);
      gl_panel_calculo_costo.setHorizontalGroup(
         gl_panel_calculo_costo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_calculo_costo.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_calculo_costo.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.chckUltimaCompra, -2, 118, -2)
                        .addComponent(this.chckCostoPromedio, -2, -1, -2)
                  )
                  .addContainerGap(93, 32767)
            )
      );
      gl_panel_calculo_costo.setVerticalGroup(
         gl_panel_calculo_costo.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_calculo_costo.createSequentialGroup()
                  .addComponent(this.chckCostoPromedio, -2, 16, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.chckUltimaCompra, -2, 16, -2)
                  .addContainerGap(22, 32767)
            )
      );
      panel_calculo_costo.setLayout(gl_panel_calculo_costo);
      GroupLayout gl_panel_definiciones = new GroupLayout(panel_definiciones);
      gl_panel_definiciones.setHorizontalGroup(
         gl_panel_definiciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_definiciones.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_definiciones.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(this.chckProduccion, Alignment.LEADING, -1, -1, 32767)
                        .addComponent(this.chckPerecedero, Alignment.LEADING, -1, -1, 32767)
                        .addComponent(this.chckManejaStock, Alignment.LEADING, -1, -1, 32767)
                  )
                  .addContainerGap(43, 32767)
            )
      );
      gl_panel_definiciones.setVerticalGroup(
         gl_panel_definiciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_definiciones.createSequentialGroup()
                  .addComponent(this.chckManejaStock, -2, 18, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.chckPerecedero, -2, 18, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.chckProduccion, -2, 18, -2)
                  .addContainerGap(13, 32767)
            )
      );
      panel_definiciones.setLayout(gl_panel_definiciones);
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING,
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panel_buscador, Alignment.LEADING, -2, 688, -2)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(panel_familia, 0, 0, 32767)
                                    .addComponent(panel_basico, -2, 368, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING)
                                    .addComponent(panel_codigo_barras, 0, 0, 32767)
                                    .addComponent(panel_tipo_fiscal, 0, 0, 32767)
                                    .addGroup(
                                       groupLayout.createSequentialGroup()
                                          .addComponent(panel_definiciones, -2, 116, -2)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addGroup(
                                             groupLayout.createParallelGroup(Alignment.LEADING)
                                                .addComponent(this.chckStockNegativo, -2, 136, -2)
                                                .addComponent(panel_calculo_costo, 0, 0, 32767)
                                          )
                                    )
                                    .addComponent(panel_activaciones, -2, 314, -2)
                              )
                        )
                  )
                  .addGap(222)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_activaciones, -2, 32, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING)
                                    .addComponent(panel_definiciones, -2, 75, -2)
                                    .addGroup(
                                       groupLayout.createSequentialGroup()
                                          .addComponent(panel_calculo_costo, -2, 42, -2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(this.chckStockNegativo, -2, 16, -2)
                                    )
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_tipo_fiscal, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_codigo_barras, -2, 132, -2)
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addComponent(panel_basico, -2, 225, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(panel_familia, -2, 95, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_buscador, -2, 235, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      GroupLayout gl_panel_codigo_barras = new GroupLayout(panel_codigo_barras);
      gl_panel_codigo_barras.setHorizontalGroup(
         gl_panel_codigo_barras.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_codigo_barras.createSequentialGroup()
                  .addGroup(
                     gl_panel_codigo_barras.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.btnGuardarCodigoBarra, -2, 114, -2)
                        .addComponent(scrollPane_codigoBarras, -1, 301, 32767)
                  )
                  .addContainerGap()
            )
      );
      gl_panel_codigo_barras.setVerticalGroup(
         gl_panel_codigo_barras.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_codigo_barras.createSequentialGroup()
                  .addComponent(scrollPane_codigoBarras, -2, 85, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.btnGuardarCodigoBarra, -2, 33, -2)
                  .addContainerGap(-1, 32767)
            )
      );
      panel_codigo_barras.setLayout(gl_panel_codigo_barras);
      GroupLayout gl_panel_familia = new GroupLayout(panel_familia);
      gl_panel_familia.setHorizontalGroup(
         gl_panel_familia.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_familia.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_familia.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblSeccion, -2, 70, -2)
                        .addComponent(lblSubSeccion, -2, 70, -2)
                        .addComponent(lblGrupo, -2, 70, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_familia.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_familia.createSequentialGroup()
                              .addComponent(this.txt_cod_grupo, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreGrupoTexto, -2, 224, -2)
                        )
                        .addGroup(
                           gl_panel_familia.createSequentialGroup()
                              .addComponent(this.txt_cod_sub_seccion, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreSubSeccionTexto, -2, 225, -2)
                        )
                        .addGroup(
                           gl_panel_familia.createSequentialGroup()
                              .addComponent(this.txt_cod_seccion, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreSeccionTexto, -2, 225, -2)
                        )
                  )
                  .addContainerGap(31, 32767)
            )
      );
      gl_panel_familia.setVerticalGroup(
         gl_panel_familia.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_familia.createSequentialGroup()
                  .addGroup(
                     gl_panel_familia.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_familia.createSequentialGroup()
                              .addGroup(
                                 gl_panel_familia.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       gl_panel_familia.createParallelGroup(Alignment.BASELINE)
                                          .addComponent(this.txt_cod_seccion, -2, 25, -2)
                                          .addComponent(lblSeccion, -2, 25, -2)
                                    )
                                    .addComponent(this.lblNombreSeccionTexto, -2, 25, -2)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreSubSeccionTexto, -2, 25, -2)
                        )
                        .addGroup(
                           gl_panel_familia.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_sub_seccion, -2, 25, -2)
                              .addComponent(lblSubSeccion, -2, 25, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_familia.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_familia.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_grupo, -2, 25, -2)
                              .addComponent(lblGrupo, -2, 25, -2)
                        )
                        .addComponent(this.lblNombreGrupoTexto, -2, 25, -2)
                  )
                  .addContainerGap(16, 32767)
            )
      );
      panel_familia.setLayout(gl_panel_familia);
      GroupLayout gl_panel_activaciones = new GroupLayout(panel_activaciones);
      gl_panel_activaciones.setHorizontalGroup(
         gl_panel_activaciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_activaciones.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.chckActivo)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.chckActivoVta)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.chckActivoCompra)
                  .addContainerGap(30, 32767)
            )
      );
      gl_panel_activaciones.setVerticalGroup(
         gl_panel_activaciones.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_activaciones.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_activaciones.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.chckActivo, -2, 25, -2)
                        .addComponent(this.chckActivoVta, -2, 25, -2)
                        .addComponent(this.chckActivoCompra, -2, 25, -2)
                  )
                  .addContainerGap(187, 32767)
            )
      );
      panel_activaciones.setLayout(gl_panel_activaciones);
      GroupLayout gl_panel_tipo_fiscal = new GroupLayout(panel_tipo_fiscal);
      gl_panel_tipo_fiscal.setHorizontalGroup(
         gl_panel_tipo_fiscal.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo_fiscal.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_tipo_fiscal.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           gl_panel_tipo_fiscal.createSequentialGroup()
                              .addComponent(lblTipoFiscal, -2, 59, -2)
                              .addGap(10)
                              .addComponent(this.combo_tipo_fiscal, -2, 90, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(lblIVA, -2, 60, -2)
                        )
                        .addGroup(
                           gl_panel_tipo_fiscal.createSequentialGroup()
                              .addComponent(lblPorcentajeGravado, -2, 64, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.txt_porcentaje_gravado, -2, 52, -2)
                              .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                              .addComponent(lblPorcentajeRegimen, -2, 64, -2)
                        )
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_tipo_fiscal.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.txt_porcentaje_regimen, -2, 45, -2)
                        .addComponent(this.txt_iva, -2, 45, -2)
                  )
                  .addGap(61)
            )
      );
      gl_panel_tipo_fiscal.setVerticalGroup(
         gl_panel_tipo_fiscal.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_tipo_fiscal.createSequentialGroup()
                  .addGroup(
                     gl_panel_tipo_fiscal.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.combo_tipo_fiscal, -2, 25, -2)
                        .addComponent(lblTipoFiscal, -2, 25, -2)
                        .addComponent(lblIVA, -2, 25, -2)
                        .addComponent(this.txt_iva, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_tipo_fiscal.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.txt_porcentaje_gravado, -2, 25, -2)
                        .addComponent(this.txt_porcentaje_regimen, -2, 25, -2)
                        .addComponent(lblPorcentajeRegimen, -2, 25, -2)
                        .addComponent(lblPorcentajeGravado, -2, 25, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_tipo_fiscal.setLayout(gl_panel_tipo_fiscal);
      GroupLayout gl_panel_buscador = new GroupLayout(panel_buscador);
      gl_panel_buscador.setHorizontalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_buscador.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 741, 32767).addGap(6))
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGap(68)
                  .addComponent(this.txt_buscador, -2, 415, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblLineasRecuperadas)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                  .addContainerGap(124, 32767)
            )
      );
      gl_panel_buscador.setVerticalGroup(
         gl_panel_buscador.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_buscador.createSequentialGroup()
                  .addGroup(
                     gl_panel_buscador.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.txt_buscador, -2, 25, -2)
                        .addComponent(lblLineasRecuperadas)
                        .addComponent(this.lblLineasRecuperadasTexto)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(scrollPane, -1, 104, 32767)
                  .addContainerGap()
            )
      );
      panel_buscador.setLayout(gl_panel_buscador);
      GroupLayout gl_panel_basico = new GroupLayout(panel_basico);
      gl_panel_basico.setHorizontalGroup(
         gl_panel_basico.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_basico.createSequentialGroup()
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(lblCosto, Alignment.LEADING, -1, -1, 32767)
                        .addComponent(lblProveedor, Alignment.LEADING, -1, -1, 32767)
                        .addComponent(lblCodigo, Alignment.LEADING, -2, 45, -2)
                        .addComponent(lblDescripcion, Alignment.LEADING, -1, 72, 32767)
                        .addComponent(lblUnidadMedida, Alignment.LEADING, -1, -1, 32767)
                        .addComponent(lblMarca, Alignment.LEADING, -2, 45, -2)
                  )
                  .addGap(18)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_basico.createSequentialGroup()
                              .addComponent(this.txt_cod_marca, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreMarcaTexto, -2, 200, -2)
                        )
                        .addComponent(this.txt_descripcion, -2, 264, -2)
                        .addComponent(this.txt_codigo, -2, 153, -2)
                        .addGroup(
                           gl_panel_basico.createSequentialGroup()
                              .addComponent(this.txt_cod_unidad_medida, -2, 41, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblNombreUnidadMedidaTexto, -2, 200, -2)
                        )
                        .addGroup(
                           gl_panel_basico.createParallelGroup(Alignment.TRAILING, false)
                              .addGroup(
                                 Alignment.LEADING,
                                 gl_panel_basico.createSequentialGroup()
                                    .addComponent(this.lblCostoTexto, -2, 109, -2)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(this.lblNombreMonedaTexto, -1, -1, 32767)
                              )
                              .addGroup(
                                 Alignment.LEADING,
                                 gl_panel_basico.createSequentialGroup()
                                    .addComponent(this.txt_cod_proveedor, -2, 41, -2)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(this.lblNombreProveedorTexto, -2, 200, -2)
                              )
                        )
                  )
                  .addContainerGap(21, 32767)
            )
      );
      gl_panel_basico.setVerticalGroup(
         gl_panel_basico.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_basico.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(gl_panel_basico.createParallelGroup(Alignment.BASELINE).addComponent(this.txt_codigo, -2, 25, -2).addComponent(lblCodigo))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(gl_panel_basico.createParallelGroup(Alignment.BASELINE).addComponent(this.txt_descripcion, -2, 54, -2).addComponent(lblDescripcion))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                           gl_panel_basico.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.txt_cod_unidad_medida, -2, 25, -2)
                              .addComponent(lblUnidadMedida)
                        )
                        .addComponent(this.lblNombreUnidadMedidaTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_basico.createParallelGroup(Alignment.BASELINE).addComponent(this.txt_cod_marca, -2, 25, -2).addComponent(lblMarca))
                        .addComponent(this.lblNombreMarcaTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           gl_panel_basico.createParallelGroup(Alignment.BASELINE).addComponent(this.txt_cod_proveedor, -2, 25, -2).addComponent(lblProveedor)
                        )
                        .addComponent(this.lblNombreProveedorTexto, -2, 25, -2)
                  )
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(
                     gl_panel_basico.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblCosto)
                        .addComponent(this.lblCostoTexto, -2, 25, -2)
                        .addComponent(this.lblNombreMonedaTexto, -2, 25, -2)
                  )
                  .addContainerGap(45, 32767)
            )
      );
      panel_basico.setLayout(gl_panel_basico);
      this.getContentPane().setLayout(groupLayout);
      if (!Beans.isDesignTime()) {
         this.inicializarObjetos();
      }

      ListSelectionModel modeloSeleccion = this.tabla.getSelectionModel();
      modeloSeleccion.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int filaSeleccionada = ProductosForm.this.tabla.getSelectedRow();
               if (filaSeleccionada != -1) {
                  ProductosForm.this.buscarTabla();
               }
            }
         }
      });
      new BuscadorTablaCargar(this.txt_buscador, this.lblLineasRecuperadasTexto, this.modeloTabla, "Productos", this);
      this.btnGuardarCodigoBarra.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.btnGuardarCodigoBarra) {
         if (this.tabla_codigo_barras.isEditing()) {
            TableCellEditor editor = this.tabla_codigo_barras.getCellEditor();
            if (editor != null) {
               editor.addCellEditorListener(new CellEditorListener() {
                  @Override
                  public void editingStopped(ChangeEvent e) {
                     System.out.println("Edición detenida correctamente.");
                  }

                  @Override
                  public void editingCanceled(ChangeEvent e) {
                  }
               });
               editor.stopCellEditing();
            }
         }

         this.tabla_codigo_barras.clearSelection();
         int codigo = ProductosBarrasE.insertarCodigoBarras(this.txt_codigo.getText(), this.tabla_codigo_barras, this.codigosAEliminar, this);
         if (codigo != 0) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Registro insertado correctamente", "correcto");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
         }
      }
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ProductosForm frame = new ProductosForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
               var3.printStackTrace();
            }
         }
      });
   }

   public void setTxt_codigo(String par_codigo) {
      this.txt_codigo.setText(par_codigo);
   }
}
