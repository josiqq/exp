package a00TipoDocumentos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadre;

public class TipoDocumentosE {
   private int cod_tipo_documento;
   private int estado;
   private int tipo;
   private int nc;
   private int ventas;
   private int compras;
   private int preventa;
   private int numeracion;
   private int timbrado;
   private String nombre_tipo_documento;
   private String archivo_impresion;
   private String expedicion;
   private String fecha_inicio_vigencia;
   private String fecha_vencimiento;

   public static int eliminarTipoDocumentos(TipoDocumentosE entidad, JinternalPadre frame) {
      PreparedStatement psEliminarTipoDoc = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psEliminarTipoDoc = conexion.prepareStatement("delete from tipo_documentos where cod_tipo_documento =?");
         psEliminarTipoDoc.setInt(1, entidad.getCod_tipo_documento());
         psEliminarTipoDoc.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al eliminar Tipo Documento.", frame);
         }

         LogErrores.errores(var11, "Error al eliminar Tipo Documento..", frame);
      } finally {
         new CerrarRecursos(psEliminarTipoDoc, frame, "Error al eliminar Tipo Documento..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int actualizarTipoDoc(TipoDocumentosE entidad, JinternalPadre frame) {
      PreparedStatement psActualizarTipodoc = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         psActualizarTipodoc = conexion.prepareStatement(
            "update tipo_documentos set nombre_tipo_documento=?,estado=?,tipo=?,nc=?,ventas=?,compras=?,preventa=?,archivo_impresion=?,numeracion =?,  timbrado=?,fecha_inicio=?,fecha_vencimiento=?,expedicion=? where cod_tipo_documento =?"
         );
         psActualizarTipodoc.setString(1, entidad.getNombre_tipo_documento());
         psActualizarTipodoc.setInt(2, entidad.getEstado());
         psActualizarTipodoc.setInt(3, entidad.getTipo());
         psActualizarTipodoc.setInt(4, entidad.getNc());
         psActualizarTipodoc.setInt(5, entidad.getVentas());
         psActualizarTipodoc.setInt(6, entidad.getCompras());
         psActualizarTipodoc.setInt(7, entidad.getPreventa());
         psActualizarTipodoc.setString(8, entidad.getArchivo_impresion());
         psActualizarTipodoc.setInt(9, entidad.getNumeracion());
         psActualizarTipodoc.setInt(10, entidad.getTimbrado());
         psActualizarTipodoc.setString(11, entidad.getFecha_inicio_vigencia());
         psActualizarTipodoc.setString(12, entidad.getFecha_vencimiento());
         psActualizarTipodoc.setString(13, entidad.getExpedicion());
         psActualizarTipodoc.setInt(14, entidad.getCod_tipo_documento());
         psActualizarTipodoc.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var11) {
         try {
            conexion.rollback();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "Error al actualizar Tipo Documento.", frame);
         }

         LogErrores.errores(var11, "Error al actualizar Tipo Documento..", frame);
      } finally {
         new CerrarRecursos(psActualizarTipodoc, frame, "Error al actualizar Tipo Documento..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static int insertarTipoDocumentos(TipoDocumentosE entidad, JinternalPadre frame) {
      PreparedStatement preparedStatementRegistromayor = null;
      ResultSet resultadoMayor = null;
      int siguienteRegistro = 0;
      PreparedStatement psInsertarTipoDoc = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementRegistromayor = conexion.prepareStatement("Select max(cod_tipo_documento) as mayor from tipo_documentos");
         resultadoMayor = preparedStatementRegistromayor.executeQuery();
         if (resultadoMayor.next()) {
            siguienteRegistro = resultadoMayor.getInt("mayor") + 1;
         }

         psInsertarTipoDoc = conexion.prepareStatement(
            "insert into tipo_documentos (cod_tipo_documento,nombre_tipo_documento,estado,tipo,nc,ventas,compras,preventa,archivo_impresion,numeracion,timbrado,fecha_inicio,fecha_vencimiento,expedicion) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
         );
         psInsertarTipoDoc.setInt(1, siguienteRegistro);
         psInsertarTipoDoc.setString(2, entidad.getNombre_tipo_documento());
         psInsertarTipoDoc.setInt(3, entidad.getEstado());
         psInsertarTipoDoc.setInt(4, entidad.getTipo());
         psInsertarTipoDoc.setInt(5, entidad.getNc());
         psInsertarTipoDoc.setInt(6, entidad.getVentas());
         psInsertarTipoDoc.setInt(7, entidad.getCompras());
         psInsertarTipoDoc.setInt(8, entidad.getPreventa());
         psInsertarTipoDoc.setString(9, entidad.getArchivo_impresion());
         psInsertarTipoDoc.setInt(10, entidad.getNumeracion());
         psInsertarTipoDoc.setInt(11, entidad.getTimbrado());
         psInsertarTipoDoc.setString(12, entidad.getFecha_inicio_vigencia());
         psInsertarTipoDoc.setString(13, entidad.getFecha_vencimiento());
         psInsertarTipoDoc.setString(14, entidad.getExpedicion());
         psInsertarTipoDoc.executeUpdate();
         conexion.commit();
         return 1;
      } catch (SQLException var14) {
         try {
            conexion.rollback();
         } catch (SQLException var13) {
            LogErrores.errores(var13, "Error al insertar Tipo de Documento.", frame);
         }

         LogErrores.errores(var14, "Error al insertar Tipo de Documento..", frame);
      } finally {
         new CerrarRecursos(psInsertarTipoDoc, preparedStatementRegistromayor, resultadoMayor, frame, "Error al insertar Tipo de Documento..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return 0;
   }

   public static TipoDocumentosE buscarTipodocumentos2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      TipoDocumentosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_tipo_documento,nombre_tipo_documento from tipo_documentos where cod_tipo_documento =? and estado =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Tipo Documento inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         TipoDocumentosE tipoDoc = new TipoDocumentosE(resultado.getInt("cod_tipo_documento"), resultado.getString("nombre_tipo_documento"));
         var8 = tipoDoc;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Tipo Documentos..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Tipo Documentos....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static TipoDocumentosE buscarTipodocumentosVta2(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      TipoDocumentosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_tipo_documento,nombre_tipo_documento from tipo_documentos where cod_tipo_documento =? and estado =1 and ventas =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Tipo Documento inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         TipoDocumentosE tipoDoc = new TipoDocumentosE(resultado.getInt("cod_tipo_documento"), resultado.getString("nombre_tipo_documento"));
         var8 = tipoDoc;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Tipo Documentos..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Tipo Documentos....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static void cargarTipoDocumentosTabla(String sql, ModeloTablaDefecto modelo, JDialog frame) {
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
         LogErrores.errores(var10, "Error al seleccionar Tipo Documentos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Tipo Documentos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static void cargarTipoDocumentosTabla4(String sql, ModeloTablaDefecto modelo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(sql);
         resultado = preparedStatementSelect.executeQuery();

         while (resultado.next()) {
            modelo.addRow(new Object[]{resultado.getInt(1), resultado.getString(2), resultado.getInt(3), resultado.getInt(4)});
         }
      } catch (Exception var10) {
         LogErrores.errores(var10, "Error al seleccionar Tipo Documentos..", frame);
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Tipo Documentos..");
         DatabaseConnection.getInstance().cerrarConexion();
      }
   }

   public static TipoDocumentosE buscarTipoDocumento(int codigo, JinternalPadre frame) {
      PreparedStatement preparedStatementSelect = null;
      ResultSet resultado = null;
      Connection conexion = null;

      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "Select cod_tipo_documento,nombre_tipo_documento,estado,\r\n\t\t\ttipo,nc,ventas,compras,preventa,archivo_impresion,\r\n\t\t\tnumeracion,timbrado,fecha_inicio,fecha_vencimiento,expedicion from tipo_documentos where cod_tipo_documento=?"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (resultado.next()) {
            return new TipoDocumentosE(
               resultado.getInt("cod_tipo_documento"),
               resultado.getString("nombre_tipo_documento"),
               resultado.getInt("estado"),
               resultado.getInt("tipo"),
               resultado.getInt("nc"),
               resultado.getInt("ventas"),
               resultado.getInt("compras"),
               resultado.getInt("preventa"),
               resultado.getString("archivo_impresion"),
               resultado.getInt("numeracion"),
               resultado.getInt("timbrado"),
               resultado.getString("fecha_inicio"),
               resultado.getString("fecha_vencimiento"),
               resultado.getString("expedicion")
            );
         }
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Tipo de Documento..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Tipo de Documento..");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return null;
   }

   public static TipoDocumentosE buscarTipodocumentos3(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      TipoDocumentosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_tipo_documento,nombre_tipo_documento,timbrado from tipo_documentos where cod_tipo_documento =? and estado =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Tipo Documento inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         TipoDocumentosE tipoDoc = new TipoDocumentosE(
            resultado.getInt("cod_tipo_documento"), resultado.getString("nombre_tipo_documento"), resultado.getInt("timbrado")
         );
         var8 = tipoDoc;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Tipo Documentos..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Tipo Documentos....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public static TipoDocumentosE buscarTipodocumentos4(int codigo, JinternalPadre frame) {
      ResultSet resultado = null;
      PreparedStatement preparedStatementSelect = null;

      TipoDocumentosE var8;
      try {
         DatabaseConnection db = DatabaseConnection.getInstance();
         Connection conexion = db.abrirConexion();
         preparedStatementSelect = conexion.prepareStatement(
            "select cod_tipo_documento,nombre_tipo_documento,timbrado,numeracion from tipo_documentos where cod_tipo_documento =? and estado =1"
         );
         preparedStatementSelect.setInt(1, codigo);
         resultado = preparedStatementSelect.executeQuery();
         if (!resultado.next()) {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Tipo Documento inactivo o inexistente", "error");
            rs.setLocationRelativeTo(frame);
            rs.setVisible(true);
            return null;
         }

         TipoDocumentosE tipoDoc = new TipoDocumentosE(
            resultado.getInt("cod_tipo_documento"), resultado.getString("nombre_tipo_documento"), resultado.getInt("timbrado"), resultado.getInt("numeracion")
         );
         var8 = tipoDoc;
      } catch (Exception var11) {
         LogErrores.errores(var11, "Error al seleccionar Tipo Documentos..", frame);
         return null;
      } finally {
         new CerrarRecursos(preparedStatementSelect, resultado, frame, "Error al seleccionar Tipo Documentos....");
         DatabaseConnection.getInstance().cerrarConexion();
      }

      return var8;
   }

   public TipoDocumentosE(int cod_tipo_documento, String nombre_tipo_documento, int timbrado, int numeracion) {
      this.cod_tipo_documento = cod_tipo_documento;
      this.nombre_tipo_documento = nombre_tipo_documento;
      this.timbrado = timbrado;
      this.numeracion = numeracion;
   }

   public TipoDocumentosE(int cod_tipo_documento, String nombre_tipo_documento, int timbrado) {
      this.cod_tipo_documento = cod_tipo_documento;
      this.nombre_tipo_documento = nombre_tipo_documento;
      this.timbrado = timbrado;
   }

   public TipoDocumentosE(
      int cod_tipo_documento,
      String nombre_tipo_documento,
      int estado,
      int tipo,
      int nc,
      int ventas,
      int compras,
      int preventa,
      String archivo_impresion,
      int numeracion,
      int timbrado,
      String fecha_inicio_vigencia,
      String fecha_vencimiento,
      String expedicion
   ) {
      this.cod_tipo_documento = cod_tipo_documento;
      this.nombre_tipo_documento = nombre_tipo_documento;
      this.estado = estado;
      this.tipo = tipo;
      this.nc = nc;
      this.ventas = ventas;
      this.compras = compras;
      this.preventa = preventa;
      this.archivo_impresion = archivo_impresion;
      this.numeracion = numeracion;
      this.timbrado = timbrado;
      this.expedicion = expedicion;
      this.fecha_inicio_vigencia = fecha_inicio_vigencia;
      this.fecha_vencimiento = fecha_vencimiento;
   }

   public TipoDocumentosE(int cod_tipo_documento, String nombre_tipo_documento) {
      this.cod_tipo_documento = cod_tipo_documento;
      this.nombre_tipo_documento = nombre_tipo_documento;
   }

   public int getCod_tipo_documento() {
      return this.cod_tipo_documento;
   }

   public void setCod_tipo_documento(int cod_tipo_documento) {
      this.cod_tipo_documento = cod_tipo_documento;
   }

   public int getEstado() {
      return this.estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public int getTipo() {
      return this.tipo;
   }

   public void setTipo(int tipo) {
      this.tipo = tipo;
   }

   public int getNc() {
      return this.nc;
   }

   public void setNc(int nc) {
      this.nc = nc;
   }

   public int getVentas() {
      return this.ventas;
   }

   public void setVentas(int ventas) {
      this.ventas = ventas;
   }

   public int getCompras() {
      return this.compras;
   }

   public void setCompras(int compras) {
      this.compras = compras;
   }

   public int getPreventa() {
      return this.preventa;
   }

   public void setPreventa(int preventa) {
      this.preventa = preventa;
   }

   public String getNombre_tipo_documento() {
      return this.nombre_tipo_documento;
   }

   public void setNombre_tipo_documento(String nombre_tipo_documento) {
      this.nombre_tipo_documento = nombre_tipo_documento;
   }

   public String getArchivo_impresion() {
      return this.archivo_impresion;
   }

   public void setArchivo_impresion(String archivo_impresion) {
      this.archivo_impresion = archivo_impresion;
   }

   public int getNumeracion() {
      return this.numeracion;
   }

   public void setNumeracion(int numeracion) {
      this.numeracion = numeracion;
   }

   public int getTimbrado() {
      return this.timbrado;
   }

   public void setTimbrado(int timbrado) {
      this.timbrado = timbrado;
   }

   public String getExpedicion() {
      return this.expedicion;
   }

   public void setExpedicion(String expedicion) {
      this.expedicion = expedicion;
   }

   public String getFecha_inicio_vigencia() {
      return this.fecha_inicio_vigencia;
   }

   public void setFecha_inicio_vigencia(String fecha_inicio_vigencia) {
      this.fecha_inicio_vigencia = fecha_inicio_vigencia;
   }

   public String getFecha_vencimiento() {
      return this.fecha_vencimiento;
   }

   public void setFecha_vencimiento(String fecha_vencimiento) {
      this.fecha_vencimiento = fecha_vencimiento;
   }
}
