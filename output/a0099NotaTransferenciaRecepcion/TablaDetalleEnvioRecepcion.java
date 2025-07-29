package a0099NotaTransferenciaRecepcion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import utilidades.LabelPrincipal;

public class TablaDetalleEnvioRecepcion extends JTable {
   private static final long serialVersionUID = 1L;

   public TablaDetalleEnvioRecepcion(LabelPrincipal lblTextoTotalLinea, int DECIMAL_CANTIDAD, int DECIMAL_IMPORTE) {
      this.setRowHeight(25);
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      this.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
      String[] columnNames = new String[]{"Código", "Descripción", "UM", "Cantidad", "Costo", "Tipo Fiscal", "IVA", "PorcGravado", "Primario", "SW"};
      ModeloTablaEnvioRecepcion model = new ModeloTablaEnvioRecepcion(null, columnNames);
      TablaDetalleEnvioRecepcion.ColorRenderer colorRenderer = new TablaDetalleEnvioRecepcion.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
      this.setModel(model);
      model.formato(this, DECIMAL_CANTIDAD, DECIMAL_IMPORTE);
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
