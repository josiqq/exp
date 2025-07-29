package utilidadesTabla;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadre;

public class BuscadorTablaParametros extends JTable {
   private static final long serialVersionUID = 1L;
   private ModeloTablaDefecto modelo;

   public BuscadorTablaParametros(ModeloTablaDefecto modelo, String nombre, JinternalPadre frame) {
      this.setName(nombre);
      this.modelo = modelo;
      this.crearTabla(frame);
      this.setName(nombre);
      this.setSelectionMode(0);
      this.getTableHeader().setReorderingAllowed(false);
      BuscadorTablaParametros.ColorRenderer colorRenderer = new BuscadorTablaParametros.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
   }

   private void crearTabla(JInternalFrame frame) {
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

      try {
         this.setModel(this.modelo);
         DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
         modelocentrar.setHorizontalAlignment(2);
         this.setRowHeight(25);
         if (this.getName().equals("ParametrosDetalle")) {
            this.getColumn("ID").setResizable(false);
            this.getColumn("ID").setMinWidth(30);
            this.getColumn("ID").setMaxWidth(30);
            this.getColumn("ID").setPreferredWidth(30);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(300);
            this.getColumn("Nombre").setMaxWidth(300);
            this.getColumn("Nombre").setPreferredWidth(300);
            this.getColumn("Modulo").setResizable(false);
            this.getColumn("Modulo").setMinWidth(90);
            this.getColumn("Modulo").setMaxWidth(90);
            this.getColumn("Estado").setResizable(false);
            this.getColumn("Estado").setMinWidth(40);
            this.getColumn("Estado").setMaxWidth(40);
            this.getColumn("ID").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Modulo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Estado").setHeaderRenderer(headerRendererIzquierda);
         }
      } catch (Exception var4) {
         LogErrores.errores(var4, "Error al iniciar tabla", frame);
      }
   }

   class ColorRenderer extends DefaultTableCellRenderer {
      private static final long serialVersionUID = 1L;
      private Color colorFilaPar = new Color(224, 224, 224);
      private Color colorFilaImpar = new Color(245, 245, 245);

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
