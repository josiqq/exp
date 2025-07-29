package utilidadesTabla;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JinternalPadrePermisos;

public class TablaConParametro extends JTable {
   private static final long serialVersionUID = 1L;
   private ModeloTablaDefecto modelo;

   public static void eliminarFilasConWhile(ModeloTablaDefecto model) {
      int rowCount = model.getRowCount();

      for (int i = rowCount - 1; i >= 0; i--) {
         model.removeRow(i);
      }
   }

   public TablaConParametro(ModeloTablaDefecto modelo, String nombre, JinternalPadrePermisos frame) {
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      this.setName(nombre);
      this.modelo = modelo;
      this.crearTabla(frame);
      this.setName(nombre);
      this.setSelectionMode(0);
      this.getTableHeader().setReorderingAllowed(false);
      if (nombre.trim().equals("Permisos")) {
         TablaConParametro.ColorRendererPermiso colorRenderer = new TablaConParametro.ColorRendererPermiso();
         this.setDefaultRenderer(Object.class, colorRenderer);
      } else {
         TablaConParametro.ColorRenderer colorRenderer = new TablaConParametro.ColorRenderer();
         this.setDefaultRenderer(Object.class, colorRenderer);
      }
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
         this.setRowHeight(25);
         if (this.getName().equals("GrupoUsuario")) {
            this.getColumn("Nombre Grupo").setResizable(false);
            this.getColumn("Nombre Grupo").setMinWidth(175);
            this.getColumn("Nombre Grupo").setMaxWidth(175);
            this.getColumn("Nombre Grupo").setPreferredWidth(175);
            this.getColumn("Estado").setResizable(false);
            this.getColumn("Estado").setMinWidth(50);
            this.getColumn("Estado").setMaxWidth(50);
            this.getColumn("Estado").setPreferredWidth(50);
            this.getColumn("Nombre Grupo").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Estado").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("Permisos")) {
            this.getColumn("Menu").setResizable(false);
            this.getColumn("Menu").setMinWidth(300);
            this.getColumn("Menu").setMaxWidth(300);
            this.getColumn("Menu").setPreferredWidth(0);
            this.getColumn("Ver").setResizable(false);
            this.getColumn("Ver").setMinWidth(60);
            this.getColumn("Ver").setMaxWidth(60);
            this.getColumn("Ver").setPreferredWidth(60);
            this.getColumn("Insertar").setResizable(false);
            this.getColumn("Insertar").setMinWidth(60);
            this.getColumn("Insertar").setMaxWidth(60);
            this.getColumn("Insertar").setPreferredWidth(60);
            this.getColumn("Modificar").setResizable(false);
            this.getColumn("Modificar").setMinWidth(60);
            this.getColumn("Modificar").setMaxWidth(60);
            this.getColumn("Modificar").setPreferredWidth(60);
            this.getColumn("Eliminar").setResizable(false);
            this.getColumn("Eliminar").setMinWidth(60);
            this.getColumn("Eliminar").setMaxWidth(60);
            this.getColumn("Eliminar").setPreferredWidth(60);
            this.getColumn("Tipo").setResizable(false);
            this.getColumn("Tipo").setMinWidth(0);
            this.getColumn("Tipo").setMaxWidth(0);
            this.getColumn("Tipo").setPreferredWidth(0);
            this.getColumn("Menu").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Ver").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Insertar").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Modificar").setHeaderRenderer(headerRendererIzquierda);
            this.getColumn("Eliminar").setHeaderRenderer(headerRendererIzquierda);
         } else if (this.getName().equals("Usuario")) {
            this.getColumn("Nombre Usuario").setResizable(false);
            this.getColumn("Nombre Usuario").setMinWidth(230);
            this.getColumn("Nombre Usuario").setMaxWidth(230);
            this.getColumn("Nombre Usuario").setPreferredWidth(230);
            this.getColumn("Estado").setResizable(false);
            this.getColumn("Estado").setMinWidth(50);
            this.getColumn("Estado").setMaxWidth(50);
            this.getColumn("Estado").setPreferredWidth(50);
            this.getColumn("Nombre Usuario").setHeaderRenderer(headerRendererIzquierda);
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

   class ColorRendererPermiso extends DefaultTableCellRenderer {
      private static final long serialVersionUID = 1L;

      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         int tipo = Integer.parseInt(String.valueOf(table.getValueAt(row, TablaConParametro.this.getColumn("Tipo").getModelIndex())));
         if (tipo == 1) {
            c.setBackground(new Color(200, 220, 200));
         } else if (tipo == 2) {
            c.setBackground(new Color(200, 220, 240));
         } else if (!isSelected) {
            c.setBackground(new Color(245, 245, 245));
         }

         return c;
      }
   }
}
