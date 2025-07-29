package utilidadesConexion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import utilidadesSQL.CerrarRecursos;
import utilidadesSQL.LogErrores;

public class DatabaseConnection {
   private static DatabaseConnection instancia;
   private static final String HOST;
   private static final int PUERTO;
   private static final int TIPO_SERVER;
   private static final String BD;
   private String usuario;
   private String password;
   private int id_conexion;
   private Connection conexion;

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      Properties propiedades = new Properties();
      String nombreArchivo = "configuraciones/conexion.properties";

      try {
         Throwable ex = null;
         Object var3 = null;

         try {
            InputStream input = new FileInputStream(nombreArchivo);

            try {
               propiedades.load(input);
               HOST = propiedades.getProperty("host.server");
               PUERTO = Integer.parseInt(propiedades.getProperty("puerto.server"));
               TIPO_SERVER = Integer.parseInt(propiedades.getProperty("tipo.server"));
               BD = propiedades.getProperty("database.server");
            } finally {
               if (input != null) {
                  input.close();
               }
            }
         } catch (Throwable var14) {
            if (ex == null) {
               ex = var14;
            } else if (ex != var14) {
               ex.addSuppressed(var14);
            }

            throw ex;
         }
      } catch (FileNotFoundException var15) {
         LogErrores.errores(var15, "Error al iniciar Sesion:", null);
         throw new RuntimeException("No se encontró el archivo de configuración externo", var15);
      } catch (NumberFormatException | IOException var16) {
         LogErrores.errores(var16, "Error al iniciar Sesion ", null);
         throw new RuntimeException("Error al cargar la configuración", var16);
      }
   }

   private DatabaseConnection() {
   }

   public static DatabaseConnection getInstance() {
      if (instancia == null) {
         instancia = new DatabaseConnection();
      }

      return instancia;
   }

   public void setCredenciales(String usuario, String password) {
      this.usuario = usuario;
      this.password = password;
   }

   public Connection abrirConexion() throws SQLException {
      if (TIPO_SERVER == 1) {
         try {
            this.conexion = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PUERTO + "/" + BD, this.usuario, this.password);
            this.conexion.setAutoCommit(false);
         } catch (SQLException var2) {
            LogErrores.errores(var2, "Error al iniciar sesion ", null);
            throw new RuntimeException("Error al iniciar sesion", var2);
         }
      }

      return this.conexion;
   }

   public void cerrarConexion() {
      if (this.conexion != null) {
         try {
            this.conexion.close();
         } catch (SQLException var2) {
            LogErrores.errores(var2, "Error al iniciar sesion ", null);
            var2.printStackTrace();
         }
      }
   }

   public String getUsuario() {
      return this.usuario;
   }

   public void generarIDConexion() {
      PreparedStatement psInsertarConexion = null;

      try {
         this.abrirConexion();
         psInsertarConexion = this.conexion.prepareStatement("insert into sys_usuarios_conexiones (usuario, inicio_conexion) values (?,now())", 1);
         psInsertarConexion.setString(1, this.usuario);
         psInsertarConexion.executeUpdate();
         ResultSet rs = psInsertarConexion.getGeneratedKeys();
         int idGenerado = 0;
         if (rs.next()) {
            idGenerado = rs.getInt(1);
         }

         rs.close();
         psInsertarConexion.close();
         this.conexion.commit();
         this.id_conexion = idGenerado;
      } catch (SQLException var9) {
         try {
            this.conexion.rollback();
         } catch (SQLException var8) {
            LogErrores.errores(var8, "Error al insertar Conexion.");
         }

         LogErrores.errores(var9, "Error al insertar Conexion..");
      } finally {
         new CerrarRecursos(psInsertarConexion, "Error al insertar Conexion..");
         getInstance().cerrarConexion();
      }
   }

   public int getId_conexion() {
      return this.id_conexion;
   }

   public static boolean probarConexion(String usuario, String password) {
      Connection conexion = null;

      try {
         String url = "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + BD + "?useSSL=false&serverTimezone=UTC";
         conexion = DriverManager.getConnection(url, usuario, password);
         return true;
      } catch (SQLException var12) {
         LogErrores.errores(var12, "Error al iniciar sesion ", null);
      } finally {
         if (conexion != null) {
            try {
               conexion.close();
            } catch (SQLException var11) {
               LogErrores.errores(var11, "Error al iniciar sesion ", null);
               return false;
            }
         }
      }

      return false;
   }
}
