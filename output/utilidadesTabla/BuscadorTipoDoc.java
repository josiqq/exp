package utilidadesTabla;

import a00TipoDocumentos.TipoDocumentosE;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.BotonPadre;
import utilidades.LabelPrincipal;
import utilidades.PanelPadre;
import utilidades.TablaScrollPane;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JDialogPrincipal;
import utilidadesVentanas.JinternalPadre;

public class BuscadorTipoDoc extends JDialogPrincipal implements KeyListener, ActionListener {
   private static final long serialVersionUID = 1L;
   private final PanelPadre contentPanel = new PanelPadre("");
   private JFormattedTextField txt_buscador;
   private BotonPadre btnSeleccionar;
   private BotonPadre btnSalir;
   private int codigo;
   private int timbrado;
   private int numeracion;
   private String nombre;
   private boolean seleccionado = false;
   private ModeloTablaDefecto modelo;
   private TablaBuscador tabla;
   private JinternalPadre frame;
   private LabelPrincipal lblLineasRecuperadasTexto;

   private void addEscapeKey() {
      KeyStroke escape = KeyStroke.getKeyStroke(27, 0, false);
      Action escapeAction = new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadorTipoDoc.this.seleccionado = false;
            BuscadorTipoDoc.this.dispose();
         }
      };
      this.getRootPane().getInputMap(2).put(escape, "ESCAPE");
      this.getRootPane().getActionMap().put("ESCAPE", escapeAction);
   }

   public BuscadorTipoDoc(JinternalPadre frame) {
      this.frame = frame;
      this.setTitle("Buscador de Plazos");
      this.setBounds(100, 100, 542, 453);
      this.getContentPane().setLayout(new BorderLayout());
      this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.getContentPane().add(this.contentPanel, "Center");
      PanelPadre panel = new PanelPadre("");
      LabelPrincipal lblLineasRecuperadas = new LabelPrincipal("Lineas Recuperadas:", "lineas");
      this.lblLineasRecuperadasTexto = new LabelPrincipal("0", "lineas");
      this.lblLineasRecuperadasTexto.setHorizontalAlignment(2);
      this.txt_buscador = new JFormattedTextField();
      this.txt_buscador.setName("txt_buscador");
      this.btnSeleccionar = new BotonPadre("Seleccionar", "acceder.png");
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      String[] cabecera = new String[]{"Codigo", "Nombre", "Timbrado", "Numeracion"};
      this.modelo = new ModeloTablaDefecto(cabecera);
      this.tabla = new TablaBuscador(this.modelo, "TipoDoc4");
      this.tabla.setName("tabla_buscador");
      TablaScrollPane scrollPane = new TablaScrollPane(this.tabla);
      GroupLayout gl_contentPanel = new GroupLayout(this.contentPanel);
      gl_contentPanel.setHorizontalGroup(
         gl_contentPanel.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup().addGap(68).addComponent(this.txt_buscador, -2, 265, -2))
                        .addGroup(
                           gl_contentPanel.createSequentialGroup()
                              .addComponent(lblLineasRecuperadas, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.lblLineasRecuperadasTexto, -2, 45, -2)
                              .addGap(46)
                              .addComponent(this.btnSeleccionar, -2, 148, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.btnSalir, -2, 102, -2)
                        )
                        .addComponent(panel, Alignment.TRAILING, -1, 535, 32767)
                  )
                  .addContainerGap()
            )
      );
      gl_contentPanel.setVerticalGroup(
         gl_contentPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPanel.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.txt_buscador, -2, 33, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel, -2, 314, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.btnSalir, -2, -1, -2)
                        .addComponent(this.btnSeleccionar, -2, -1, -2)
                        .addComponent(lblLineasRecuperadas, -2, -1, -2)
                        .addComponent(this.lblLineasRecuperadasTexto, -2, -1, -2)
                  )
                  .addContainerGap()
            )
      );
      GroupLayout gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 525, 32767))
      );
      gl_panel.setVerticalGroup(
         gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane, -1, 304, 32767))
      );
      panel.setLayout(gl_panel);
      this.contentPanel.setLayout(gl_contentPanel);
      this.addEscapeKey();
      this.txt_buscador.addKeyListener(this);
      this.btnSeleccionar.addActionListener(this);
      this.btnSalir.addActionListener(this);
      this.tabla.addKeyListener(this);
   }

   private void cargarTabla() {
      if (this.modelo.getRowCount() - 1 >= 0) {
         while (this.modelo.getRowCount() > 0) {
            this.modelo.removeRow(0);
         }
      }

      String sql = "";
      if (this.txt_buscador.getText().equals("")) {
         sql = "select cod_tipo_documento,nombre_tipo_documento,timbrado,numeracion from tipo_documentos where estado =1";
      } else {
         sql = "select cod_tipo_documento,nombre_tipo_documento,timbrado,numeracion from tipo_documentos where estado = 1 and nombre_tipo_documento like '%"
            + this.txt_buscador.getText()
            + "%'";
      }

      TipoDocumentosE.cargarTipoDocumentosTabla4(sql, this.modelo, this.frame);
      if (this.tabla.getRowCount() > 0) {
         this.tabla.setRowSelectionInterval(0, 0);
         this.tabla.requestFocusInWindow();
      }

      if (this.modelo.getRowCount() - 1 >= 0) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         this.lblLineasRecuperadasTexto.setText(String.valueOf(nf.format((long)this.modelo.getRowCount())));
      }
   }

   @Override
   public void keyTyped(KeyEvent e) {
   }

   @Override
   public void keyPressed(KeyEvent e) {
      if (e.getSource() == this.tabla && e.getKeyCode() == 10) {
         int filaSeleccionada = this.tabla.getSelectedRow();
         this.seleccionLinea(filaSeleccionada);
      }

      if (e.getSource() == this.txt_buscador && e.getKeyCode() == 10) {
         this.cargarTabla();
      }
   }

   private void seleccionLinea(int filaSeleccionada) {
      this.codigo = Integer.parseInt(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Codigo").getModelIndex()).toString());
      this.nombre = this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Nombre").getModelIndex()).toString();
      this.timbrado = Integer.parseInt(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Timbrado").getModelIndex()).toString());
      this.numeracion = Integer.parseInt(this.tabla.getValueAt(filaSeleccionada, this.tabla.getColumn("Numeracion").getModelIndex()).toString());
      this.seleccionado = true;
      this.dispose();
   }

   @Override
   public void keyReleased(KeyEvent e) {
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.btnSeleccionar) {
         int filaSeleccionada = this.tabla.getSelectedRow();
         if (filaSeleccionada != -1) {
            this.seleccionLinea(filaSeleccionada);
         }
      } else if (e.getSource() == this.btnSalir) {
         this.seleccionado = false;
         this.dispose();
      }
   }

   public int getTimbrado() {
      return this.timbrado;
   }

   public int getCodigo() {
      return this.codigo;
   }

   public String getNombre() {
      return this.nombre;
   }

   public boolean isSeleccionado() {
      return this.seleccionado;
   }

   public int getNumeracion() {
      return this.numeracion;
   }

   public static void main(String[] args) {
      BuscadorDefecto1 dialog = new BuscadorDefecto1("Vendedores");

      try {
         dialog.setVisible(true);
      } catch (Exception var3) {
         LogErrores.errores(var3, "Error al iniciar el Formulario", dialog);
         var3.printStackTrace();
      }
   }
}
