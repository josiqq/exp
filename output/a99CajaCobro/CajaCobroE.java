package a99CajaCobro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class CajaCobroE {
   private int nro_registro;
   private int cod_cliente;
   private int cod_cajero;
   private int cod_cobrador;
   private int cod_moneda;
   private String recibo;
   private String observacion;
   private String fecha;
   private String hora_cobro;
   private String nombre_cliente;
   private String nombre_cajero;
   private String nombre_cobrador;
   private String nombre_moneda;
   private double total_cabecera;
   private double total_detalle;
   private double importe_moneda1;
   private double importe_moneda2;
   private double importe_moneda3;
   private double importe_moneda4;

   public static int insertarCobros(CajaCobroE entidad, JTable tabla_documentos, JinternalPadre frame) {
      int tipoDocColumn = tabla_documentos.getColumn("CodTipoDoc").getModelIndex();
      int timbradoColumn = tabla_documentos.getColumn("Timbrado").getModelIndex();
      int nroDocumentoColumn = tabla_documentos.getColumn("Nro Documento").getModelIndex();
      int nroCuotaColumn = tabla_documentos.getColumn("Nro Cuota").getModelIndex();
      int importeColumn = tabla_documentos.getColumn("Importe").getModelIndex();
      PreparedStatement preparedStatementInsertarCobro = null;
      PreparedStatement preparedStatementInsertarDoc = null;
      PreparedStatement preparedStatementInsertarDetalle = null;
      Connection conexion = null;
      ResultSet rsID = null;
      int id_generado = 0;
      if (tabla_documentos.getRowCount() - 1 < 0) {
         DialogResultadoOperacion resultado = new DialogResultadoOperacion("No se puede grabar registro sin detalles! ", "Si");
         resultado.setLocationRelativeTo(frame);
         resultado.setVisible(true);
         tabla_documentos.requestFocusInWindow();
         tabla_documentos.changeSelection(0, 0, false, false);
         tabla_documentos.editCellAt(0, 0);
         return 0;
      } else {
         try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            conexion = db.abrirConexion();
            preparedStatementInsertarCobro = conexion.prepareStatement(
               "insert into cobros\t(fecha,hora_cobro,cod_cliente,cod_cajero,cod_cobrador,cod_moneda,recibo,observacion,total_cabecera,total_detalle) values(?,curtime(),?,?,?,?,?,?,?,?,?)",
               1
            );
            preparedStatementInsertarCobro.setString(1, entidad.getFecha());
            preparedStatementInsertarCobro.setInt(2, entidad.getCod_cliente());
            preparedStatementInsertarCobro.setInt(3, entidad.getCod_cajero());
            preparedStatementInsertarCobro.setInt(4, entidad.getCod_cobrador());
            preparedStatementInsertarCobro.setInt(5, entidad.getCod_moneda());
            preparedStatementInsertarCobro.setString(6, entidad.getRecibo());
            preparedStatementInsertarCobro.setString(7, entidad.getObservacion());
            preparedStatementInsertarCobro.setDouble(8, entidad.getTotal_cabecera());
            preparedStatementInsertarCobro.setDouble(9, entidad.getTotal_detalle());
            preparedStatementInsertarCobro.executeUpdate();
            rsID = preparedStatementInsertarCobro.getGeneratedKeys();
            if (rsID.next()) {
               id_generado = rsID.getInt(1);
            }

            preparedStatementInsertarDoc = conexion.prepareStatement(
               "INSERT INTO cobros_documentos (nro_registro,cod_tipo_documento,timbrado,nro_factura,nro_cuota,valor_cobrado) VALUES (?,?,?,?,?,?)"
            );

            for (int fila = 0; fila < tabla_documentos.getRowCount(); fila++) {
               if (!String.valueOf(tabla_documentos.getValueAt(fila, tipoDocColumn)).trim().equals("")
                  && tabla_documentos.getValueAt(fila, tipoDocColumn) != null
                  && !String.valueOf(tabla_documentos.getValueAt(fila, importeColumn)).trim().equals("0,00")
                  && !String.valueOf(tabla_documentos.getValueAt(fila, importeColumn)).trim().equals("0")) {
                  preparedStatementInsertarDoc.setInt(1, id_generado);
                  preparedStatementInsertarDoc.setString(2, String.valueOf(tabla_documentos.getValueAt(fila, tipoDocColumn)));
                  preparedStatementInsertarDoc.setInt(3, Integer.parseInt(String.valueOf(tabla_documentos.getValueAt(fila, timbradoColumn))));
                  preparedStatementInsertarDoc.setDouble(4, Double.parseDouble(String.valueOf(tabla_documentos.getValueAt(fila, nroDocumentoColumn))));
                  preparedStatementInsertarDoc.setDouble(5, Double.parseDouble(String.valueOf(tabla_documentos.getValueAt(fila, nroCuotaColumn))));
                  preparedStatementInsertarDoc.setDouble(
                     6, Double.parseDouble(String.valueOf(tabla_documentos.getValueAt(fila, importeColumn)).replace(".", "").replace(",", "."))
                  );
                  preparedStatementInsertarDoc.addBatch();
               }
            }

            preparedStatementInsertarDoc.executeBatch();
            conexion.commit();
            return 1;
         } catch (SQLException var21) {
            try {
               conexion.rollback();
            } catch (SQLException var20) {
               LogErrores.errores(var20, "Error al insertar Cobro.", frame);
            }

            LogErrores.errores(var21, "Error al insertar Cobro..", frame);
         } finally {
            new CerrarRecursos(preparedStatementInsertarCobro, frame, "Error al insertar Cobro..");
            DatabaseConnection.getInstance().cerrarConexion();
         }

         return 0;
      }
   }

   public CajaCobroE(double importe_moneda1, double importe_moneda2, double importe_moneda3, double importe_moneda4) {
      this.importe_moneda1 = importe_moneda1;
      this.importe_moneda2 = importe_moneda2;
      this.importe_moneda3 = importe_moneda3;
      this.importe_moneda4 = importe_moneda4;
   }

   public double getImporte_moneda1() {
      return this.importe_moneda1;
   }

   public void setImporte_moneda1(double importe_moneda1) {
      this.importe_moneda1 = importe_moneda1;
   }

   public double getImporte_moneda2() {
      return this.importe_moneda2;
   }

   public void setImporte_moneda2(double importe_moneda2) {
      this.importe_moneda2 = importe_moneda2;
   }

   public double getImporte_moneda3() {
      return this.importe_moneda3;
   }

   public void setImporte_moneda3(double importe_moneda3) {
      this.importe_moneda3 = importe_moneda3;
   }

   public double getImporte_moneda4() {
      return this.importe_moneda4;
   }

   public void setImporte_moneda4(double importe_moneda4) {
      this.importe_moneda4 = importe_moneda4;
   }

   public int getNro_registro() {
      return this.nro_registro;
   }

   public void setNro_registro(int nro_registro) {
      this.nro_registro = nro_registro;
   }

   public int getCod_cliente() {
      return this.cod_cliente;
   }

   public void setCod_cliente(int cod_cliente) {
      this.cod_cliente = cod_cliente;
   }

   public int getCod_cajero() {
      return this.cod_cajero;
   }

   public void setCod_cajero(int cod_cajero) {
      this.cod_cajero = cod_cajero;
   }

   public int getCod_cobrador() {
      return this.cod_cobrador;
   }

   public void setCod_cobrador(int cod_cobrador) {
      this.cod_cobrador = cod_cobrador;
   }

   public int getCod_moneda() {
      return this.cod_moneda;
   }

   public void setCod_moneda(int cod_moneda) {
      this.cod_moneda = cod_moneda;
   }

   public String getRecibo() {
      return this.recibo;
   }

   public void setRecibo(String recibo) {
      this.recibo = recibo;
   }

   public String getObservacion() {
      return this.observacion;
   }

   public void setObservacion(String observacion) {
      this.observacion = observacion;
   }

   public String getFecha() {
      return this.fecha;
   }

   public void setFecha(String fecha) {
      this.fecha = fecha;
   }

   public String getHora_cobro() {
      return this.hora_cobro;
   }

   public void setHora_cobro(String hora_cobro) {
      this.hora_cobro = hora_cobro;
   }

   public String getNombre_cliente() {
      return this.nombre_cliente;
   }

   public void setNombre_cliente(String nombre_cliente) {
      this.nombre_cliente = nombre_cliente;
   }

   public String getNombre_cajero() {
      return this.nombre_cajero;
   }

   public void setNombre_cajero(String nombre_cajero) {
      this.nombre_cajero = nombre_cajero;
   }

   public String getNombre_cobrador() {
      return this.nombre_cobrador;
   }

   public void setNombre_cobrador(String nombre_cobrador) {
      this.nombre_cobrador = nombre_cobrador;
   }

   public String getNombre_moneda() {
      return this.nombre_moneda;
   }

   public void setNombre_moneda(String nombre_moneda) {
      this.nombre_moneda = nombre_moneda;
   }

   public double getTotal_cabecera() {
      return this.total_cabecera;
   }

   public void setTotal_cabecera(double total_cabecera) {
      this.total_cabecera = total_cabecera;
   }

   public double getTotal_detalle() {
      return this.total_detalle;
   }

   public void setTotal_detalle(double total_detalle) {
      this.total_detalle = total_detalle;
   }
}
