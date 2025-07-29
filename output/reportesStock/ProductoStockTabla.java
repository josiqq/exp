package reportesStock;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadreReporte;

public class ProductoStockTabla extends JTable {
   private static final long serialVersionUID = 1L;
   private DefaultTableModel modelo;

   public ProductoStockTabla(DefaultTableModel modelo, String nombre, JinternalPadreReporte frame) {
      this.setName(nombre);
      this.modelo = modelo;
      this.crearTabla(frame);
      this.setName(nombre);
      this.setSelectionMode(0);
      this.getTableHeader().setReorderingAllowed(false);
      ProductoStockTabla.ColorRenderer colorRenderer = new ProductoStockTabla.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
   }

   private void crearTabla(JinternalPadreReporte frame) {
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
         if (this.getName().equals("Productos")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(70);
            this.getColumn("Codigo").setMaxWidth(70);
            this.getColumn("Codigo").setPreferredWidth(70);
            this.getColumn("Nombre").setResizable(false);
            this.getColumn("Nombre").setMinWidth(310);
            this.getColumn("Nombre").setMaxWidth(310);
            this.getColumn("Nombre").setPreferredWidth(310);
            this.getColumn("Tipo Fiscal").setResizable(false);
            this.getColumn("Tipo Fiscal").setMinWidth(70);
            this.getColumn("Tipo Fiscal").setMaxWidth(70);
            this.getColumn("IVA").setResizable(false);
            this.getColumn("IVA").setMinWidth(30);
            this.getColumn("IVA").setMaxWidth(30);
            this.getColumn("Marca").setResizable(false);
            this.getColumn("Marca").setMinWidth(100);
            this.getColumn("Marca").setMaxWidth(100);
            this.getColumn("Unidad Medida").setResizable(false);
            this.getColumn("Unidad Medida").setMinWidth(100);
            this.getColumn("Unidad Medida").setMaxWidth(100);
            this.getColumn("Proveedor").setResizable(false);
            this.getColumn("Proveedor").setMinWidth(250);
            this.getColumn("Proveedor").setMaxWidth(250);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Tipo Fiscal").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("IVA").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Marca").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Unidad Medida").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Proveedor").setHeaderRenderer(headerRendererIzquierda);
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
