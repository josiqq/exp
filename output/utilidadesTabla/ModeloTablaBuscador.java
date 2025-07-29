package utilidadesTabla;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class ModeloTablaBuscador extends JTable {
   private static final long serialVersionUID = 1L;
   private ModeloTablaDefecto modelo;

   public ModeloTablaBuscador(ModeloTablaDefecto modelo, String nombre) {
      this.setName(nombre);
      this.modelo = modelo;
      this.crearTabla();
      this.setName(nombre);
      this.setSelectionMode(0);
      this.getTableHeader().setReorderingAllowed(false);
      ModeloTablaBuscador.ColorRenderer colorRenderer = new ModeloTablaBuscador.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
   }

   private void crearTabla() {
      try {
         this.setModel(this.modelo);
         DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
         modelocentrar.setHorizontalAlignment(2);
         this.setRowHeight(25);
         TableColumnModel columnModel = this.getColumnModel();
         if (this.getName().equals("Vendedores")) {
            columnModel.getColumn(0).setResizable(false);
            columnModel.getColumn(0).setMaxWidth(70);
            columnModel.getColumn(0).setMinWidth(70);
            columnModel.getColumn(0).setPreferredWidth(70);
            columnModel.getColumn(1).setResizable(false);
            columnModel.getColumn(1).setMaxWidth(180);
            columnModel.getColumn(1).setMinWidth(180);
            columnModel.getColumn(1).setPreferredWidth(180);
         } else if (this.getName().equals("Cajeros")) {
            columnModel.getColumn(0).setResizable(false);
            columnModel.getColumn(0).setMaxWidth(70);
            columnModel.getColumn(0).setMinWidth(70);
            columnModel.getColumn(0).setPreferredWidth(70);
            columnModel.getColumn(1).setResizable(false);
            columnModel.getColumn(1).setMaxWidth(180);
            columnModel.getColumn(1).setMinWidth(180);
            columnModel.getColumn(1).setPreferredWidth(180);
         } else if (this.getName().equals("Compradores")) {
            columnModel.getColumn(0).setResizable(false);
            columnModel.getColumn(0).setMaxWidth(70);
            columnModel.getColumn(0).setMinWidth(70);
            columnModel.getColumn(0).setPreferredWidth(70);
            columnModel.getColumn(1).setResizable(false);
            columnModel.getColumn(1).setMaxWidth(180);
            columnModel.getColumn(1).setMinWidth(180);
            columnModel.getColumn(1).setPreferredWidth(180);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   class ColorRenderer extends DefaultTableCellRenderer {
      private static final long serialVersionUID = 1L;
      private Color colorFilaPar = new Color(240, 240, 240);
      private Color colorFilaImpar = new Color(255, 255, 255);

      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         if (!isSelected) {
            c.setBackground(row % 2 == 0 ? this.colorFilaPar : this.colorFilaImpar);
         }

         return c;
      }
   }
}
