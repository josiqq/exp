package a0099ConsultasRapidas;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import utilidadesSQL.LogErrores;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadreReporte;

public class BuscadorTablaConsultas extends JTable {
   private static final long serialVersionUID = 1L;
   private ModeloTablaDefecto modelo;

   public BuscadorTablaConsultas(ModeloTablaDefecto modelo, String nombre, JinternalPadreReporte frame) {
      this.setName(nombre);
      this.modelo = modelo;
      this.crearTabla(frame);
      this.setName(nombre);
      this.setSelectionMode(0);
      this.getTableHeader().setReorderingAllowed(false);
      BuscadorTablaConsultas.ColorRenderer colorRenderer = new BuscadorTablaConsultas.ColorRenderer();
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
         if (this.getName().equals("ProductosReservados")) {
            this.getColumn("Codigo").setResizable(false);
            this.getColumn("Codigo").setMinWidth(70);
            this.getColumn("Codigo").setMaxWidth(70);
            this.getColumn("Codigo").setPreferredWidth(70);
            this.getColumn("Descripcion Producto").setResizable(false);
            this.getColumn("Descripcion Producto").setMinWidth(340);
            this.getColumn("Descripcion Producto").setMaxWidth(340);
            this.getColumn("Descripcion Producto").setPreferredWidth(340);
            this.getColumn("CodDeposito").setResizable(false);
            this.getColumn("CodDeposito").setMinWidth(0);
            this.getColumn("CodDeposito").setMaxWidth(0);
            this.getColumn("Nombre Deposito").setResizable(false);
            this.getColumn("Nombre Deposito").setMinWidth(210);
            this.getColumn("Nombre Deposito").setMaxWidth(210);
            this.getColumn("Cantidad").setResizable(false);
            this.getColumn("Cantidad").setMinWidth(70);
            this.getColumn("Cantidad").setMaxWidth(70);
            this.getColumn("Tipo").setResizable(false);
            this.getColumn("Tipo").setMinWidth(100);
            this.getColumn("Tipo").setMaxWidth(100);
            this.getColumn("Sesion").setResizable(false);
            this.getColumn("Sesion").setMinWidth(90);
            this.getColumn("Sesion").setMaxWidth(90);
            this.getColumn("Anular").setResizable(false);
            this.getColumn("Anular").setMinWidth(48);
            this.getColumn("Anular").setMaxWidth(48);
            this.getColumn("Codigo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Descripcion Producto").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Nombre Deposito").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Cantidad").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Tipo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Sesion").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Anular").setHeaderRenderer(headerRendererIzquierda);
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
