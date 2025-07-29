package utilidadesTabla;

import java.io.Serializable;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class ModeloTablaDefecto extends DefaultTableModel implements Serializable {
   private static final long serialVersionUID = 77L;

   public ModeloTablaDefecto(String[] cabecera) {
      super(cabecera, 0);
   }

   @Override
   public Class<?> getColumnClass(int columnIndex) {
      label166: {
         label127: {
            label126: {
               Class clazz = String.class;
               String var3;
               switch ((var3 = this.getColumnName(columnIndex)).hashCode()) {
                  case -1956037655:
                     if (var3.equals("Nombre")) {
                        clazz = String.class;
                     }

                     return clazz;
                  case -1690516349:
                     if (!var3.equals("RealizaDesc")) {
                        return clazz;
                     }
                     break;
                  case -1295855852:
                     if (var3.equals("Telefono")) {
                        clazz = String.class;
                     }

                     return clazz;
                  case -1085868579:
                     if (!var3.equals("Tesorero")) {
                        return clazz;
                     }
                     break label127;
                  case -1036428910:
                     if (var3.equals("Multiplica")) {
                        clazz = String.class;
                     }

                     return clazz;
                  case -740602741:
                     if (!var3.equals("Recibido")) {
                        return clazz;
                     }

                     clazz = Boolean.class;
                     break;
                  case -502807441:
                     if (var3.equals("Contacto")) {
                        clazz = String.class;
                     }

                     return clazz;
                  case 72884:
                     if (var3.equals("IVA")) {
                        clazz = Double.class;
                     }

                     return clazz;
                  case 80436:
                     if (!var3.equals("Pos")) {
                        return clazz;
                     }
                     break label166;
                  case 85471:
                     if (var3.equals("UxB")) {
                        clazz = Boolean.class;
                     }

                     return clazz;
                  case 85891:
                     if (var3.equals("Ver")) {
                        clazz = Boolean.class;
                     }

                     return clazz;
                  case 2606932:
                     if (var3.equals("Tipo")) {
                        clazz = String.class;
                     }

                     return clazz;
                  case 102919882:
                     if (var3.equals("Modificar")) {
                        clazz = Boolean.class;
                     }

                     return clazz;
                  case 170940097:
                     if (var3.equals("Eliminar")) {
                        clazz = Boolean.class;
                     }

                     return clazz;
                  case 606431434:
                     if (var3.equals("Insertar")) {
                        clazz = Boolean.class;
                     }

                     return clazz;
                  case 985755743:
                     if (!var3.equals("Negativo")) {
                        return clazz;
                     }

                     return Boolean.class;
                  case 1040870672:
                     if (var3.equals("Direccion")) {
                        clazz = String.class;
                     }

                     return clazz;
                  case 1966074581:
                     if (var3.equals("Anular")) {
                        clazz = Boolean.class;
                     }

                     return clazz;
                  case 2023747257:
                     if (var3.equals("Codigo")) {
                        clazz = String.class;
                     }

                     return clazz;
                  case 2085168518:
                     if (!var3.equals("Estado")) {
                        return clazz;
                     }
                     break label126;
                  default:
                     return clazz;
               }

               clazz = Boolean.class;
            }

            Class var6 = Boolean.class;
         }

         Class var7 = Boolean.class;
      }

      Class var8 = Boolean.class;
      return Boolean.class;
   }

   @Override
   public boolean isCellEditable(int row, int column) {
      return this.getColumnName(column).equals("Barra")
         || this.getColumnName(column).equals("Ver")
         || this.getColumnName(column).equals("Estado")
         || this.getColumnName(column).equals("Agregar")
         || this.getColumnName(column).equals("Insertar")
         || this.getColumnName(column).equals("Modificar")
         || this.getColumnName(column).equals("Eliminar")
         || this.getColumnName(column).equals("Anular")
         || this.getColumnName(column).equals("Valor")
         || this.getColumnName(column).equals("Multiplicar");
   }

   public static void eliminarFilas(JTable tabla) {
      DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
      tabla.clearSelection();
      if (modelo.getRowCount() - 1 >= 0) {
         while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
         }
      }
   }

   public void deleteRow(JTable tabla) {
      if (tabla.isEditing()) {
         TableCellEditor editor = tabla.getCellEditor();
         if (editor != null) {
            editor.stopCellEditing();
         }
      }

      if (this.getRowCount() - 1 >= 0) {
         while (this.getRowCount() > 0) {
            this.removeRow(0);
         }
      }
   }
}
