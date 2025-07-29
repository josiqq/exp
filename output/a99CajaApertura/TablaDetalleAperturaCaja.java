package a99CajaApertura;

import a099CajaCondicionPago.CondicionPagoE;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;
import utilidades.LabelPrincipal;
import utilidadesOperaciones.FormatoDecimalOperacionSistema;

public class TablaDetalleAperturaCaja extends JTable {
   private static final long serialVersionUID = 1L;

   public TablaDetalleAperturaCaja(final LabelPrincipal lblTextoTotalLinea, final List<Integer> codigosAEliminar, int DECIMAL_CANTIDAD, int DECIMAL_IMPORTE) {
      this.setRowHeight(25);
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      this.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
      String[] columnNames = new String[]{"Condicion", "Nombre Condicion", "Moneda", "Nombre Moneda", "Importe", "Primario", "SW"};
      final ModeloTablaAperturaCaja model = new ModeloTablaAperturaCaja(null, columnNames);
      TablaDetalleAperturaCaja.ColorRenderer colorRenderer = new TablaDetalleAperturaCaja.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
      this.setModel(model);
      model.formato(this, DECIMAL_CANTIDAD, DECIMAL_IMPORTE);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(37, 0), "left");
      this.getActionMap().put("left", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaDetalleAperturaCaja.this.getSelectedRow();
            int selectedColumn = TablaDetalleAperturaCaja.this.getSelectedColumn();

            for (int i = selectedColumn - 1; i >= 0; i--) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleAperturaCaja.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleAperturaCaja.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleAperturaCaja.this.getEditorComponent();
                  if (editorComponent instanceof JTextComponent) {
                     ((JTextComponent)editorComponent).selectAll();
                  }
                  break;
               }
            }
         }
      });
      this.getInputMap(1).put(KeyStroke.getKeyStroke(39, 0), "right");
      this.getActionMap().put("right", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaDetalleAperturaCaja.this.getSelectedRow();
            int selectedColumn = TablaDetalleAperturaCaja.this.getSelectedColumn();

            for (int i = selectedColumn + 1; i < TablaDetalleAperturaCaja.this.getColumnCount(); i++) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleAperturaCaja.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleAperturaCaja.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleAperturaCaja.this.getEditorComponent();
                  if (editorComponent instanceof JTextComponent) {
                     ((JTextComponent)editorComponent).selectAll();
                  }
                  break;
               }
            }
         }
      });
      this.getInputMap(1).put(KeyStroke.getKeyStroke(38, 0), "up");
      this.getActionMap().put("up", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            if (TablaDetalleAperturaCaja.this.getRowCount() - 1 > 0) {
               int selectedRow = TablaDetalleAperturaCaja.this.getSelectedRow() - 1;
               TablaDetalleAperturaCaja.this.changeSelection(selectedRow, TablaDetalleAperturaCaja.this.getSelectedColumn(), false, false);
               TablaDetalleAperturaCaja.this.editCellAt(selectedRow, TablaDetalleAperturaCaja.this.getSelectedColumn());
               Component editorComponent = TablaDetalleAperturaCaja.this.getEditorComponent();
               if (editorComponent instanceof JTextComponent) {
                  ((JTextComponent)editorComponent).selectAll();
               }
            }
         }
      });
      this.getInputMap(1).put(KeyStroke.getKeyStroke(40, 0), "down");
      this.getActionMap().put("down", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            if (TablaDetalleAperturaCaja.this.getRowCount() - 1 > TablaDetalleAperturaCaja.this.getSelectedRow()) {
               int selectedRow = TablaDetalleAperturaCaja.this.getSelectedRow() + 1;
               TablaDetalleAperturaCaja.this.changeSelection(selectedRow, TablaDetalleAperturaCaja.this.getSelectedColumn(), false, false);
               TablaDetalleAperturaCaja.this.editCellAt(selectedRow, TablaDetalleAperturaCaja.this.getSelectedColumn());
               Component editorComponent = TablaDetalleAperturaCaja.this.getEditorComponent();
               if (editorComponent instanceof JTextComponent) {
                  ((JTextComponent)editorComponent).selectAll();
               }
            }
         }
      });
      this.getInputMap(1).put(KeyStroke.getKeyStroke(10, 0), "enter");
      this.getActionMap()
         .put(
            "enter",
            new AbstractAction() {
               private static final long serialVersionUID = 1L;

               @Override
               public void actionPerformed(ActionEvent e) {
                  int selectedRow = TablaDetalleAperturaCaja.this.getSelectedRow();
                  int selectedColumn = TablaDetalleAperturaCaja.this.getSelectedColumn();
                  String columnName = TablaDetalleAperturaCaja.this.getColumnName(selectedColumn);
                  if (columnName.equals("Importe") && TablaDetalleAperturaCaja.this.getRowCount() - 1 == TablaDetalleAperturaCaja.this.getSelectedRow()) {
                     if (TablaDetalleAperturaCaja.this.getValueAt(TablaDetalleAperturaCaja.this.getSelectedRow(), 1) != null
                        && !String.valueOf(TablaDetalleAperturaCaja.this.getValueAt(TablaDetalleAperturaCaja.this.getSelectedRow(), 1)).equals("")) {
                        FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
                        lblTextoTotalLinea.setText(String.valueOf(formatoLinea.format(TablaDetalleAperturaCaja.this.getRowCount() + 1)));
                        model.addDefaultRow();
                        TablaDetalleAperturaCaja.this.editCellAt(TablaDetalleAperturaCaja.this.getSelectedRow() + 1, 0);
                        TablaDetalleAperturaCaja.this.changeSelection(TablaDetalleAperturaCaja.this.getSelectedRow() + 1, 0, false, false);
                        Component editorComponent = TablaDetalleAperturaCaja.this.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     } else {
                        SwingUtilities.invokeLater(
                           () -> {
                              TablaDetalleAperturaCaja.this.changeSelection(
                                 TablaDetalleAperturaCaja.this.getSelectedRow(),
                                 TablaDetalleAperturaCaja.this.getColumn("Condicion").getModelIndex(),
                                 false,
                                 false
                              );
                              TablaDetalleAperturaCaja.this.editCellAt(
                                 TablaDetalleAperturaCaja.this.getSelectedRow(), TablaDetalleAperturaCaja.this.getColumn("Condicion").getModelIndex()
                              );
                              Component editorComponentx = TablaDetalleAperturaCaja.this.getEditorComponent();
                              if (editorComponentx instanceof JTextComponent) {
                                 ((JTextComponent)editorComponentx).selectAll();
                              }

                              editorComponentx.requestFocusInWindow();
                           }
                        );
                     }
                  } else if (columnName.equals("Importe") && TablaDetalleAperturaCaja.this.getSelectedRow() < TablaDetalleAperturaCaja.this.getRowCount() - 1) {
                     TablaDetalleAperturaCaja.this.editCellAt(TablaDetalleAperturaCaja.this.getSelectedRow() + 1, 0);
                     TablaDetalleAperturaCaja.this.changeSelection(TablaDetalleAperturaCaja.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaDetalleAperturaCaja.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     for (int i = selectedColumn + 1; i < TablaDetalleAperturaCaja.this.getColumnCount(); i++) {
                        if (model.isCellEditable(selectedRow, i)) {
                           TablaDetalleAperturaCaja.this.editCellAt(selectedRow, i);
                           TablaDetalleAperturaCaja.this.changeSelection(selectedRow, i, false, false);
                           Component editorComponent = TablaDetalleAperturaCaja.this.getEditorComponent();
                           if (editorComponent instanceof JTextComponent) {
                              ((JTextComponent)editorComponent).selectAll();
                           }
                           break;
                        }
                     }
                  }
               }
            }
         );
      this.getInputMap(1).put(KeyStroke.getKeyStroke(119, 0), "deleteRow");
      this.getActionMap()
         .put(
            "deleteRow",
            new AbstractAction() {
               private static final long serialVersionUID = 1L;

               @Override
               public void actionPerformed(ActionEvent e) {
                  int editingRow = TablaDetalleAperturaCaja.this.getEditingRow();
                  int editingColumn = TablaDetalleAperturaCaja.this.getEditingColumn();
                  if (editingRow != -1 && editingColumn != -1) {
                     TableCellEditor cellEditor = TablaDetalleAperturaCaja.this.getCellEditor();
                     cellEditor.stopCellEditing();
                  }

                  int option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta fila?", "Confirmar eliminación", 0);
                  if (option == 0) {
                     int selectedRow = TablaDetalleAperturaCaja.this.getSelectedRow();
                     int primarioColumn = TablaDetalleAperturaCaja.this.getColumn("Primario").getModelIndex();
                     if (!String.valueOf(TablaDetalleAperturaCaja.this.getValueAt(TablaDetalleAperturaCaja.this.getSelectedRow(), primarioColumn))
                        .trim()
                        .equals("0")) {
                        codigosAEliminar.add(
                           Integer.parseInt(
                              String.valueOf(TablaDetalleAperturaCaja.this.getValueAt(TablaDetalleAperturaCaja.this.getSelectedRow(), primarioColumn)).trim()
                           )
                        );
                     }

                     if (selectedRow == 0 && TablaDetalleAperturaCaja.this.getRowCount() - 1 == 0) {
                        FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
                        lblTextoTotalLinea.setText(String.valueOf(formatoLinea.format(TablaDetalleAperturaCaja.this.getRowCount() + 1)));
                        model.addDefaultRow();
                     }

                     if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel)TablaDetalleAperturaCaja.this.getModel();
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        if (model.getRowCount() > 0) {
                           int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                           TablaDetalleAperturaCaja.this.changeSelection(newSelectedRow, 0, false, false);
                           TablaDetalleAperturaCaja.this.editCellAt(newSelectedRow, 0);
                        }
                     }
                  } else {
                     TablaDetalleAperturaCaja.this.changeSelection(editingRow, 0, false, false);
                     TablaDetalleAperturaCaja.this.editCellAt(editingRow, 0);
                  }
               }
            }
         );
   }

   @Override
   public void editingStopped(ChangeEvent e) {
      if (this.getRowCount() - 1 >= 0) {
         int column = this.getEditingColumn();
         int row = this.getEditingRow();
         String columnName = this.getColumnName(column);
         if (columnName.equals("Condicion")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)
                  && !valorActual.trim().equals("")
                  && (String.valueOf(this.getValueAt(row, 1)).trim().equals("") || this.getValueAt(row, 1) == null)
               || !valorActual.trim().equals(valorAnterior)) {
               this.buscarBDCondicion(row);
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         if (columnName.equals("Moneda")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)
                  && !valorActual.trim().equals("")
                  && (String.valueOf(this.getValueAt(row, 1)).trim().equals("") || this.getValueAt(row, 1) == null)
               || !valorActual.trim().equals(valorAnterior)) {
               this.buscarBDMoneda(row);
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         super.editingStopped(e);
      }
   }

   private void buscarBDCondicion(int row) {
      String codigo = (String)this.getValueAt(row, this.getColumn("Condicion").getModelIndex());
      if (!codigo.trim().equals("")) {
         CondicionPagoE producto = CondicionPagoE.buscarCondicionPago2(Integer.parseInt(codigo), null);
         if (producto != null) {
            this.setValueAt(producto.getNombre_condicion(), row, this.getColumn("Nombre Condicion").getModelIndex());
         } else {
            this.setValueAt("", row, this.getColumn("Nombre Condicion").getModelIndex());
            SwingUtilities.invokeLater(() -> {
               this.changeSelection(row, this.getColumn("Condicion").getModelIndex(), false, false);
               this.editCellAt(row, this.getColumn("Condicion").getModelIndex());
               Component editorComponent = this.getEditorComponent();
               if (editorComponent instanceof JTextComponent) {
                  ((JTextComponent)editorComponent).selectAll();
               }

               editorComponent.requestFocusInWindow();
            });
         }
      }
   }

   private void buscarBDMoneda(int var1) {
      throw new Error("Unresolved compilation problem: \n\tThe method buscarMonedas2(int, JinternalPadre) is ambiguous for the type MonedasE\n");
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
