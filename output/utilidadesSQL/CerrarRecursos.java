package utilidadesSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JDialog;
import javax.swing.JFrame;
import utilidadesVentanas.JDialogPrincipal;
import utilidadesVentanas.JinternalPadre;
import utilidadesVentanas.JinternalPadrePermisos;
import utilidadesVentanas.JinternalPadreReporte;
import utilidadesVentanas.JinternalPadreString;

public class CerrarRecursos {
   public CerrarRecursos(
      PreparedStatement preparedStatementInsert,
      PreparedStatement preparedStatementRegistromayor,
      PreparedStatement preparedStatementInsert2,
      PreparedStatement preparedStatementInsert3,
      JinternalPadre frame,
      String mensaje
   ) {
      if (preparedStatementInsert3 != null) {
         try {
            preparedStatementInsert3.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (preparedStatementInsert2 != null) {
         try {
            preparedStatementInsert2.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }

      if (preparedStatementInsert != null) {
         try {
            preparedStatementInsert.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }

      if (preparedStatementRegistromayor != null) {
         try {
            preparedStatementRegistromayor.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatementInsert,
      PreparedStatement preparedStatementRegistromayor,
      PreparedStatement preparedStatementInsert2,
      JinternalPadre frame,
      String mensaje
   ) {
      if (preparedStatementInsert2 != null) {
         try {
            preparedStatementInsert2.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }

      if (preparedStatementInsert != null) {
         try {
            preparedStatementInsert.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, mensaje + "  :", frame);
         }
      }

      if (preparedStatementRegistromayor != null) {
         try {
            preparedStatementRegistromayor.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      ResultSet resultado,
      ResultSet resultado2,
      JinternalPadre frame,
      String mensaje
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }

      if (resultado2 != null) {
         try {
            resultado2.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement, ResultSet resultado, String mensaje) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :");
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var5) {
            LogErrores.errores(var5, mensaje + "  :");
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement, ResultSet resultado, JDialog frame, String mensaje) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement1,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      PreparedStatement preparedStatement4,
      PreparedStatement preparedStatement5,
      PreparedStatement preparedStatement6,
      ResultSet resultado,
      String mensaje,
      JinternalPadre frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var19) {
            LogErrores.errores(var19, mensaje + "  :", frame);
         }
      }

      if (preparedStatement1 != null) {
         try {
            preparedStatement1.close();
         } catch (SQLException var18) {
            LogErrores.errores(var18, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var17) {
            LogErrores.errores(var17, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var16) {
            LogErrores.errores(var16, mensaje + "  :", frame);
         }
      }

      if (preparedStatement4 != null) {
         try {
            preparedStatement4.close();
         } catch (SQLException var15) {
            LogErrores.errores(var15, mensaje + "  :", frame);
         }
      }

      if (preparedStatement5 != null) {
         try {
            preparedStatement5.close();
         } catch (SQLException var14) {
            LogErrores.errores(var14, mensaje + "  :", frame);
         }
      }

      if (preparedStatement6 != null) {
         try {
            preparedStatement6.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      PreparedStatement preparedStatement4,
      PreparedStatement preparedStatement5,
      PreparedStatement preparedStatement6,
      String mensaje,
      JinternalPadre frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var15) {
            LogErrores.errores(var15, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var14) {
            LogErrores.errores(var14, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (preparedStatement4 != null) {
         try {
            preparedStatement4.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }

      if (preparedStatement5 != null) {
         try {
            preparedStatement5.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (preparedStatement6 != null) {
         try {
            preparedStatement6.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      PreparedStatement preparedStatement4,
      PreparedStatement preparedStatement5,
      PreparedStatement preparedStatement6,
      PreparedStatement preparedStatement7,
      String mensaje,
      JinternalPadre frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var17) {
            LogErrores.errores(var17, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var16) {
            LogErrores.errores(var16, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var15) {
            LogErrores.errores(var15, mensaje + "  :", frame);
         }
      }

      if (preparedStatement4 != null) {
         try {
            preparedStatement4.close();
         } catch (SQLException var14) {
            LogErrores.errores(var14, mensaje + "  :", frame);
         }
      }

      if (preparedStatement5 != null) {
         try {
            preparedStatement5.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (preparedStatement6 != null) {
         try {
            preparedStatement6.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }

      if (preparedStatement7 != null) {
         try {
            preparedStatement7.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      PreparedStatement preparedStatement4,
      PreparedStatement preparedStatement5,
      ResultSet rs,
      String mensaje,
      JinternalPadre frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var15) {
            LogErrores.errores(var15, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var14) {
            LogErrores.errores(var14, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (preparedStatement4 != null) {
         try {
            preparedStatement4.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }

      if (preparedStatement5 != null) {
         try {
            preparedStatement5.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      PreparedStatement preparedStatement4,
      PreparedStatement preparedStatement5,
      String mensaje,
      JinternalPadre frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (preparedStatement4 != null) {
         try {
            preparedStatement4.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }

      if (preparedStatement5 != null) {
         try {
            preparedStatement5.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      ResultSet rs,
      String mensaje,
      JinternalPadre frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }

      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement, PreparedStatement preparedStatement2, ResultSet rs, ResultSet rs2, String mensaje, JinternalPadre frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }

      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }

      if (rs2 != null) {
         try {
            rs2.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      ResultSet rs,
      ResultSet rs2,
      String mensaje,
      JinternalPadre frame
   ) {
      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (rs2 != null) {
         try {
            rs2.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }

      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement, PreparedStatement preparedStatement2, String mensaje, JinternalPadre frame) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      PreparedStatement preparedStatement4,
      String mensaje,
      JinternalPadre frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }

      if (preparedStatement4 != null) {
         try {
            preparedStatement4.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      PreparedStatement preparedStatement4,
      ResultSet rs,
      String mensaje,
      JinternalPadre frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (preparedStatement4 != null) {
         try {
            preparedStatement4.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }

      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      ResultSet rs,
      ResultSet rs2,
      ResultSet rs3,
      String mensaje,
      JinternalPadre frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var15) {
            LogErrores.errores(var15, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var14) {
            LogErrores.errores(var14, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }

      if (rs2 != null) {
         try {
            rs2.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (rs3 != null) {
         try {
            rs3.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement, PreparedStatement preparedStatement2, PreparedStatement preparedStatement3, String mensaje, JinternalPadre frame
   ) {
   }

   public CerrarRecursos(Statement statement, ResultSet rs, String mensaje, JinternalPadre frame) {
      if (statement != null) {
         try {
            statement.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      ResultSet rs,
      ResultSet rs2,
      ResultSet rs3,
      ResultSet rs4,
      Statement statement,
      String mensaje,
      JinternalPadre frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var19) {
            LogErrores.errores(var19, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var18) {
            LogErrores.errores(var18, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var17) {
            LogErrores.errores(var17, mensaje + "  :", frame);
         }
      }

      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException var16) {
            LogErrores.errores(var16, mensaje + "  :", frame);
         }
      }

      if (rs2 != null) {
         try {
            rs2.close();
         } catch (SQLException var15) {
            LogErrores.errores(var15, mensaje + "  :", frame);
         }
      }

      if (rs3 != null) {
         try {
            rs3.close();
         } catch (SQLException var14) {
            LogErrores.errores(var14, mensaje + "  :", frame);
         }
      }

      if (rs4 != null) {
         try {
            rs4.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (statement != null) {
         try {
            statement.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement, ResultSet resultado, JinternalPadre frame, String mensaje) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement, JinternalPadre frame, String mensaje) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var5) {
            LogErrores.errores(var5, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatementInsert,
      PreparedStatement preparedStatementRegistromayor,
      ResultSet resultadoMayor,
      JinternalPadre frame,
      String mensaje
   ) {
      if (preparedStatementInsert != null) {
         try {
            preparedStatementInsert.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }

      if (preparedStatementRegistromayor != null) {
         try {
            preparedStatementRegistromayor.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, mensaje + "  :", frame);
         }
      }

      if (resultadoMayor != null) {
         try {
            resultadoMayor.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatementInsert, PreparedStatement preparedStatementRegistromayor, JinternalPadre frame, String mensaje) {
      if (preparedStatementInsert != null) {
         try {
            preparedStatementInsert.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (preparedStatementRegistromayor != null) {
         try {
            preparedStatementRegistromayor.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatementInsert, PreparedStatement preparedStatementRegistromayor, JDialog frame, String mensaje) {
      if (preparedStatementInsert != null) {
         try {
            preparedStatementInsert.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (preparedStatementRegistromayor != null) {
         try {
            preparedStatementRegistromayor.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatementInsert, JDialog frame, String mensaje) {
      if (preparedStatementInsert != null) {
         try {
            preparedStatementInsert.close();
         } catch (SQLException var5) {
            LogErrores.errores(var5, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement, PreparedStatement preparedStatement2, JinternalPadrePermisos frame, String mensaje) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement, JinternalPadrePermisos frame, String mensaje) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var5) {
            LogErrores.errores(var5, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement, ResultSet resultado, JinternalPadrePermisos frame, String mensaje) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      JinternalPadrePermisos frame,
      String mensaje
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement, ResultSet resultado, String mensaje, JFrame frame) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatementSelect, ResultSet resultado, String mensaje, JinternalPadreString frame) {
      if (preparedStatementSelect != null) {
         try {
            preparedStatementSelect.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement psInsertarProducto,
      PreparedStatement preparedStatementRegistromayor,
      ResultSet resultadoMayor,
      JinternalPadreString frame,
      String mensaje
   ) {
      if (resultadoMayor != null) {
         try {
            resultadoMayor.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }

      if (psInsertarProducto != null) {
         try {
            psInsertarProducto.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, mensaje + "  :", frame);
         }
      }

      if (preparedStatementRegistromayor != null) {
         try {
            preparedStatementRegistromayor.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement psActualizarProductos, JinternalPadreString frame, String mensaje) {
      if (psActualizarProductos != null) {
         try {
            psActualizarProductos.close();
         } catch (SQLException var5) {
            LogErrores.errores(var5, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatementSelect, ResultSet resultado, JinternalPadreString frame, String mensaje) {
      if (preparedStatementSelect != null) {
         try {
            preparedStatementSelect.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement, PreparedStatement preparedStatement2, JinternalPadreString frame, String mensaje) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      ResultSet resultado,
      ResultSet resultado2,
      JinternalPadre frame,
      String mensaje
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (resultado2 != null) {
         try {
            resultado2.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatementSelect, ResultSet resultado, JinternalPadreReporte frame, String string) {
      if (preparedStatementSelect != null) {
         try {
            preparedStatementSelect.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatementSelect,
      PreparedStatement preparedStatementSelect2,
      ResultSet resultado2,
      ResultSet resultado,
      JinternalPadrePermisos frame,
      String mensaje
   ) {
      if (preparedStatementSelect != null) {
         try {
            preparedStatementSelect.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, "  :", frame);
         }
      }

      if (preparedStatementSelect2 != null) {
         try {
            preparedStatementSelect2.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, "  :", frame);
         }
      }

      if (resultado2 != null) {
         try {
            resultado2.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatement,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      ResultSet resultado,
      ResultSet resultado1,
      ResultSet resultado2,
      String mensaje,
      JFrame frame
   ) {
      if (preparedStatement != null) {
         try {
            preparedStatement.close();
         } catch (SQLException var15) {
            LogErrores.errores(var15, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var14) {
            LogErrores.errores(var14, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }

      if (resultado2 != null) {
         try {
            resultado2.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (resultado1 != null) {
         try {
            resultado1.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatementSelect,
      PreparedStatement preparedStatementSelect2,
      PreparedStatement preparedStatementSelect3,
      ResultSet resultado2,
      ResultSet resultado,
      ResultSet resultado3,
      JinternalPadrePermisos frame,
      String mensaje
   ) {
      if (preparedStatementSelect != null) {
         try {
            preparedStatementSelect.close();
         } catch (SQLException var15) {
            LogErrores.errores(var15, mensaje + "  :", frame);
         }
      }

      if (preparedStatementSelect2 != null) {
         try {
            preparedStatementSelect2.close();
         } catch (SQLException var14) {
            LogErrores.errores(var14, mensaje + "  :", frame);
         }
      }

      if (preparedStatementSelect3 != null) {
         try {
            preparedStatementSelect3.close();
         } catch (SQLException var13) {
            LogErrores.errores(var13, mensaje + "  :", frame);
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var12) {
            LogErrores.errores(var12, mensaje + "  :", frame);
         }
      }

      if (resultado2 != null) {
         try {
            resultado2.close();
         } catch (SQLException var11) {
            LogErrores.errores(var11, mensaje + "  :", frame);
         }
      }

      if (resultado3 != null) {
         try {
            resultado3.close();
         } catch (SQLException var10) {
            LogErrores.errores(var10, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      PreparedStatement preparedStatementInsert,
      PreparedStatement preparedStatementUpdate,
      PreparedStatement preparedStatementDelete,
      JDialogPrincipal frame,
      String mensaje
   ) {
      if (preparedStatementInsert != null) {
         try {
            preparedStatementInsert.close();
         } catch (SQLException var9) {
            LogErrores.errores(var9, mensaje + "  :", frame);
         }
      }

      if (preparedStatementUpdate != null) {
         try {
            preparedStatementUpdate.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, mensaje + "  :", frame);
         }
      }

      if (preparedStatementDelete != null) {
         try {
            preparedStatementDelete.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(
      ResultSet resultado1,
      ResultSet resultado2,
      ResultSet resultado3,
      ResultSet resultado4,
      ResultSet resultado5,
      PreparedStatement preparedStatement1,
      PreparedStatement preparedStatement2,
      PreparedStatement preparedStatement3,
      PreparedStatement preparedStatement4,
      PreparedStatement preparedStatement5,
      JinternalPadre frame,
      String mensaje
   ) {
      if (resultado1 != null) {
         try {
            resultado1.close();
         } catch (SQLException var23) {
            LogErrores.errores(var23, mensaje + "  :", frame);
         }
      }

      if (resultado2 != null) {
         try {
            resultado2.close();
         } catch (SQLException var22) {
            LogErrores.errores(var22, mensaje + "  :", frame);
         }
      }

      if (resultado3 != null) {
         try {
            resultado3.close();
         } catch (SQLException var21) {
            LogErrores.errores(var21, mensaje + "  :", frame);
         }
      }

      if (resultado4 != null) {
         try {
            resultado4.close();
         } catch (SQLException var20) {
            LogErrores.errores(var20, mensaje + "  :", frame);
         }
      }

      if (resultado5 != null) {
         try {
            resultado5.close();
         } catch (SQLException var19) {
            LogErrores.errores(var19, mensaje + "  :", frame);
         }
      }

      if (preparedStatement1 != null) {
         try {
            preparedStatement1.close();
         } catch (SQLException var18) {
            LogErrores.errores(var18, mensaje + "  :", frame);
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var17) {
            LogErrores.errores(var17, mensaje + "  :", frame);
         }
      }

      if (preparedStatement3 != null) {
         try {
            preparedStatement3.close();
         } catch (SQLException var16) {
            LogErrores.errores(var16, mensaje + "  :", frame);
         }
      }

      if (preparedStatement4 != null) {
         try {
            preparedStatement4.close();
         } catch (SQLException var15) {
            LogErrores.errores(var15, mensaje + "  :", frame);
         }
      }

      if (preparedStatement5 != null) {
         try {
            preparedStatement5.close();
         } catch (SQLException var14) {
            LogErrores.errores(var14, mensaje + "  :", frame);
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement1, String mensaje) {
      if (preparedStatement1 != null) {
         try {
            preparedStatement1.close();
         } catch (SQLException var4) {
            LogErrores.errores(var4, mensaje + "  :");
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement1, PreparedStatement preparedStatement2, ResultSet resultado, String mensaje) {
      if (preparedStatement1 != null) {
         try {
            preparedStatement1.close();
         } catch (SQLException var8) {
            LogErrores.errores(var8, mensaje + "  :");
         }
      }

      if (preparedStatement2 != null) {
         try {
            preparedStatement2.close();
         } catch (SQLException var7) {
            LogErrores.errores(var7, mensaje + "  :");
         }
      }

      if (resultado != null) {
         try {
            resultado.close();
         } catch (SQLException var6) {
            LogErrores.errores(var6, mensaje + "  :");
         }
      }
   }

   public CerrarRecursos(PreparedStatement preparedStatement1, JinternalPadreReporte frame, String mensaje) {
      if (preparedStatement1 != null) {
         try {
            preparedStatement1.close();
         } catch (SQLException var5) {
            LogErrores.errores(var5, mensaje + "  :");
         }
      }
   }
}
