package a0099NotaTransferencia;

import a00Productos.ProductosE;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
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
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.DialogoMessagebox;
import utilidadesTabla.BuscadordeCodigos;

public class TablaDetalleEnvioTransferencia extends JTable {
   private static final long serialVersionUID = 1L;

   public TablaDetalleEnvioTransferencia(
      final LabelPrincipal lblTextoTotalLinea, final List<Integer> codigosAEliminar, int DECIMAL_CANTIDAD, int DECIMAL_IMPORTE
   ) {
      this.setRowHeight(25);
      this.setFont(new Font("Roboto", 0, 13));
      this.getTableHeader().setFont(new Font("Roboto", 1, 14));
      this.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
      String[] columnNames = new String[]{"Código", "Descripción", "UM", "Cantidad", "Costo", "Tipo Fiscal", "IVA", "PorcGravado", "Primario", "SW"};
      final ModeloTablaEnvioTransferencia model = new ModeloTablaEnvioTransferencia(null, columnNames);
      TablaDetalleEnvioTransferencia.ColorRenderer colorRenderer = new TablaDetalleEnvioTransferencia.ColorRenderer();
      this.setDefaultRenderer(Object.class, colorRenderer);
      this.setModel(model);
      model.formato(this, DECIMAL_CANTIDAD, DECIMAL_IMPORTE);
      this.getInputMap(1).put(KeyStroke.getKeyStroke(37, 0), "left");
      this.getActionMap().put("left", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            int selectedRow = TablaDetalleEnvioTransferencia.this.getSelectedRow();
            int selectedColumn = TablaDetalleEnvioTransferencia.this.getSelectedColumn();

            for (int i = selectedColumn - 1; i >= 0; i--) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleEnvioTransferencia.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleEnvioTransferencia.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleEnvioTransferencia.this.getEditorComponent();
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
            int selectedRow = TablaDetalleEnvioTransferencia.this.getSelectedRow();
            int selectedColumn = TablaDetalleEnvioTransferencia.this.getSelectedColumn();

            for (int i = selectedColumn + 1; i < TablaDetalleEnvioTransferencia.this.getColumnCount(); i++) {
               if (model.isCellEditable(selectedRow, i)) {
                  TablaDetalleEnvioTransferencia.this.changeSelection(selectedRow, i, false, false);
                  TablaDetalleEnvioTransferencia.this.editCellAt(selectedRow, i);
                  Component editorComponent = TablaDetalleEnvioTransferencia.this.getEditorComponent();
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
            if (TablaDetalleEnvioTransferencia.this.getRowCount() - 1 > 0) {
               int selectedRow = TablaDetalleEnvioTransferencia.this.getSelectedRow() - 1;
               TablaDetalleEnvioTransferencia.this.changeSelection(selectedRow, TablaDetalleEnvioTransferencia.this.getSelectedColumn(), false, false);
               TablaDetalleEnvioTransferencia.this.editCellAt(selectedRow, TablaDetalleEnvioTransferencia.this.getSelectedColumn());
               Component editorComponent = TablaDetalleEnvioTransferencia.this.getEditorComponent();
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
            if (TablaDetalleEnvioTransferencia.this.getRowCount() - 1 > TablaDetalleEnvioTransferencia.this.getSelectedRow()) {
               int selectedRow = TablaDetalleEnvioTransferencia.this.getSelectedRow() + 1;
               TablaDetalleEnvioTransferencia.this.changeSelection(selectedRow, TablaDetalleEnvioTransferencia.this.getSelectedColumn(), false, false);
               TablaDetalleEnvioTransferencia.this.editCellAt(selectedRow, TablaDetalleEnvioTransferencia.this.getSelectedColumn());
               Component editorComponent = TablaDetalleEnvioTransferencia.this.getEditorComponent();
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
                  int selectedRow = TablaDetalleEnvioTransferencia.this.getSelectedRow();
                  int selectedColumn = TablaDetalleEnvioTransferencia.this.getSelectedColumn();
                  String columnName = TablaDetalleEnvioTransferencia.this.getColumnName(selectedColumn);
                  if (columnName.equals("Cantidad")
                     && TablaDetalleEnvioTransferencia.this.getRowCount() - 1 == TablaDetalleEnvioTransferencia.this.getSelectedRow()) {
                     if (TablaDetalleEnvioTransferencia.this.getValueAt(TablaDetalleEnvioTransferencia.this.getSelectedRow(), 1) != null
                        && !String.valueOf(TablaDetalleEnvioTransferencia.this.getValueAt(TablaDetalleEnvioTransferencia.this.getSelectedRow(), 1)).equals("")) {
                        FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
                        lblTextoTotalLinea.setText(String.valueOf(formatoLinea.format(TablaDetalleEnvioTransferencia.this.getRowCount() + 1)));
                        model.addDefaultRow();
                        TablaDetalleEnvioTransferencia.this.editCellAt(TablaDetalleEnvioTransferencia.this.getSelectedRow() + 1, 0);
                        TablaDetalleEnvioTransferencia.this.changeSelection(TablaDetalleEnvioTransferencia.this.getSelectedRow() + 1, 0, false, false);
                        Component editorComponent = TablaDetalleEnvioTransferencia.this.getEditorComponent();
                        if (editorComponent instanceof JTextComponent) {
                           ((JTextComponent)editorComponent).selectAll();
                        }
                     } else {
                        SwingUtilities.invokeLater(
                           () -> {
                              TablaDetalleEnvioTransferencia.this.changeSelection(
                                 TablaDetalleEnvioTransferencia.this.getSelectedRow(),
                                 TablaDetalleEnvioTransferencia.this.getColumn("Código").getModelIndex(),
                                 false,
                                 false
                              );
                              TablaDetalleEnvioTransferencia.this.editCellAt(
                                 TablaDetalleEnvioTransferencia.this.getSelectedRow(), TablaDetalleEnvioTransferencia.this.getColumn("Código").getModelIndex()
                              );
                              Component editorComponentx = TablaDetalleEnvioTransferencia.this.getEditorComponent();
                              if (editorComponentx instanceof JTextComponent) {
                                 ((JTextComponent)editorComponentx).selectAll();
                              }

                              editorComponentx.requestFocusInWindow();
                           }
                        );
                     }
                  } else if (columnName.equals("Cantidad")
                     && TablaDetalleEnvioTransferencia.this.getSelectedRow() < TablaDetalleEnvioTransferencia.this.getRowCount() - 1) {
                     TablaDetalleEnvioTransferencia.this.editCellAt(TablaDetalleEnvioTransferencia.this.getSelectedRow() + 1, 0);
                     TablaDetalleEnvioTransferencia.this.changeSelection(TablaDetalleEnvioTransferencia.this.getSelectedRow() + 1, 0, false, false);
                     Component editorComponent = TablaDetalleEnvioTransferencia.this.getEditorComponent();
                     if (editorComponent instanceof JTextComponent) {
                        ((JTextComponent)editorComponent).selectAll();
                     }
                  } else {
                     for (int i = selectedColumn + 1; i < TablaDetalleEnvioTransferencia.this.getColumnCount(); i++) {
                        if (model.isCellEditable(selectedRow, i)) {
                           TablaDetalleEnvioTransferencia.this.editCellAt(selectedRow, i);
                           TablaDetalleEnvioTransferencia.this.changeSelection(selectedRow, i, false, false);
                           Component editorComponent = TablaDetalleEnvioTransferencia.this.getEditorComponent();
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
                  int editingRow = TablaDetalleEnvioTransferencia.this.getEditingRow();
                  int editingColumn = TablaDetalleEnvioTransferencia.this.getEditingColumn();
                  if (editingRow != -1 && editingColumn != -1) {
                     TableCellEditor cellEditor = TablaDetalleEnvioTransferencia.this.getCellEditor();
                     cellEditor.stopCellEditing();
                  }

                  DialogoMessagebox d = new DialogoMessagebox("¿Estás seguro de eliminar esta fila?");
                  d.setLocationRelativeTo(TablaDetalleEnvioTransferencia.this);
                  d.setVisible(true);
                  if (d.isResultadoEncontrado()) {
                     int selectedRow = TablaDetalleEnvioTransferencia.this.getSelectedRow();
                     int primarioColumn = TablaDetalleEnvioTransferencia.this.getColumn("Primario").getModelIndex();
                     if (!String.valueOf(TablaDetalleEnvioTransferencia.this.getValueAt(TablaDetalleEnvioTransferencia.this.getSelectedRow(), primarioColumn))
                        .trim()
                        .equals("0")) {
                        codigosAEliminar.add(
                           Integer.parseInt(
                              String.valueOf(
                                    TablaDetalleEnvioTransferencia.this.getValueAt(TablaDetalleEnvioTransferencia.this.getSelectedRow(), primarioColumn)
                                 )
                                 .trim()
                           )
                        );
                     }

                     if (selectedRow == 0 && TablaDetalleEnvioTransferencia.this.getRowCount() - 1 == 0) {
                        FormatoDecimalOperacionSistema formatoLinea = new FormatoDecimalOperacionSistema(0);
                        lblTextoTotalLinea.setText(String.valueOf(formatoLinea.format(TablaDetalleEnvioTransferencia.this.getRowCount() + 1)));
                        model.addDefaultRow();
                     }

                     if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel)TablaDetalleEnvioTransferencia.this.getModel();
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        if (model.getRowCount() > 0) {
                           int newSelectedRow = Math.min(selectedRow, model.getRowCount() - 1);
                           TablaDetalleEnvioTransferencia.this.changeSelection(newSelectedRow, 0, false, false);
                           TablaDetalleEnvioTransferencia.this.editCellAt(newSelectedRow, 0);
                        }
                     }
                  } else {
                     TablaDetalleEnvioTransferencia.this.changeSelection(editingRow, 0, false, false);
                     TablaDetalleEnvioTransferencia.this.editCellAt(editingRow, 0);
                  }
               }
            }
         );
      this.getInputMap(1).put(KeyStroke.getKeyStroke(115, 0), "search");
      this.getActionMap().put("search", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadordeCodigos buscador = new BuscadordeCodigos();
            buscador.setModal(true);
            buscador.setVisible(true);
            if (buscador.getCodigo() != null) {
               for (int row = 0; row < TablaDetalleEnvioTransferencia.this.getRowCount(); row++) {
                  String codigo = String.valueOf(TablaDetalleEnvioTransferencia.this.getValueAt(row, 0));
                  if (codigo.equals(buscador.getCodigo())) {
                     TablaDetalleEnvioTransferencia.this.changeSelection(row, 0, false, false);
                     TablaDetalleEnvioTransferencia.this.editCellAt(row, 0);
                     return;
                  }
               }

               DialogResultadoOperacion rs = new DialogResultadoOperacion("No existe producto con codigo: " + buscador.getCodigo(), "error");
               rs.setLocationRelativeTo(buscador);
               rs.setVisible(true);
            }
         }
      });
   }

   private boolean buscarCodigoNoRepetido(String par_codigo) {
      for (int row = 0; row < this.getRowCount(); row++) {
         String codigo = String.valueOf(this.getValueAt(row, 0));
         if (codigo.trim().equals(par_codigo.trim()) && this.getRowCount() > 1 && row != this.getSelectedRow()) {
            this.clearSelection();
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Ya existe el codigo: " + codigo, "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
            int fila = row;
            SwingUtilities.invokeLater(() -> {
               this.changeSelection(fila, this.getColumn("Código").getModelIndex(), false, false);
               this.editCellAt(fila, this.getColumn("Código").getModelIndex());
               Component editorComponent = this.getEditorComponent();
               if (editorComponent instanceof JTextComponent) {
                  ((JTextComponent)editorComponent).selectAll();
               }

               editorComponent.requestFocusInWindow();
            });
            return false;
         }
      }

      return true;
   }

   private void buscarParametros(int row) {
      String codigo = (String)this.getValueAt(row, this.getColumn("Código").getModelIndex());
      if (this.buscarCodigoNoRepetido(codigo)) {
         this.buscarBD(codigo, row);
      }
   }

   private void buscarBD(String codigo, int row) {
      String[] var10000 = new String[]{"Código", "Descripción", "UM", "Cantidad", "Costo", "Tipo Fiscal", "IVA", "PorcGravado", "Primario", "SW"};
      if (!codigo.trim().equals("")) {
         ProductosE producto = ProductosE.buscarProductosImpuestos(codigo, null);
         if (producto != null) {
            this.setValueAt(producto.getCod_producto(), row, this.getColumn("Código").getModelIndex());
            this.setValueAt(producto.getNombre_producto(), row, this.getColumn("Descripción").getModelIndex());
            this.setValueAt(producto.getNombreUnidadMedida(), row, this.getColumn("UM").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Cantidad").getModelIndex());
            this.setValueAt(producto.getCosto_producto(), row, this.getColumn("Costo").getModelIndex());
            this.setValueAt(producto.getTipo_fiscal(), row, this.getColumn("Tipo Fiscal").getModelIndex());
            this.setValueAt(producto.getIva_producto(), row, this.getColumn("IVA").getModelIndex());
            this.setValueAt(producto.getPorcentaje_gravado(), row, this.getColumn("PorcGravado").getModelIndex());
         } else {
            this.setValueAt("", row, this.getColumn("Código").getModelIndex());
            this.setValueAt("", row, this.getColumn("Descripción").getModelIndex());
            this.setValueAt("", row, this.getColumn("UM").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Cantidad").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Costo").getModelIndex());
            this.setValueAt(0, row, this.getColumn("Tipo Fiscal").getModelIndex());
            this.setValueAt(0, row, this.getColumn("IVA").getModelIndex());
            this.setValueAt(0, row, this.getColumn("PorcGravado").getModelIndex());
            SwingUtilities.invokeLater(() -> {
               this.changeSelection(row, this.getColumn("Código").getModelIndex(), false, false);
               this.editCellAt(row, this.getColumn("Código").getModelIndex());
               Component editorComponent = this.getEditorComponent();
               if (editorComponent instanceof JTextComponent) {
                  ((JTextComponent)editorComponent).selectAll();
               }

               editorComponent.requestFocusInWindow();
            });
         }
      }
   }

   @Override
   public void editingStopped(ChangeEvent e) {
      if (this.getRowCount() - 1 >= 0) {
         int column = this.getEditingColumn();
         int row = this.getEditingRow();
         String columnName = this.getColumnName(column);
         if (columnName.equals("Código")) {
            String valorAnterior = String.valueOf(this.getValueAt(row, column));
            super.editingStopped(e);
            String valorActual = String.valueOf(this.getValueAt(row, column));
            if (!valorActual.trim().equals(valorAnterior)
                  && !valorActual.trim().equals("")
                  && (String.valueOf(this.getValueAt(row, 1)).trim().equals("") || this.getValueAt(row, 1) == null)
               || !valorActual.trim().equals(valorAnterior)) {
               this.buscarParametros(row);
               this.setValueAt(0, row, this.getColumn("SW").getModelIndex());
            }
         }

         super.editingStopped(e);
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
