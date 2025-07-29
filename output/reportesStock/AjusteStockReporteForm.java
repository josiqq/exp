package reportesStock;

import a009AjusteStock.AjusteStockE;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListCellRenderer;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextFieldConSQLReportes;
import utilidades.PanelPadre;
import utilidadesSQL.LogErrores;
import utilidadesTabla.BuscadorTablaReportes;
import utilidadesTabla.ModeloTablaDefecto;
import utilidadesVentanas.JinternalPadreReporte;

public class AjusteStockReporteForm extends JinternalPadreReporte {
   private JList<Map<String, Object>> listaCabecera;
   private DefaultListModel<Map<String, Object>> modeloLista;
   private static final long serialVersionUID = 1L;
   private JDateChooser txt_fecha_inicial;
   private JDateChooser txt_fecha_final;
   private LimiteTextFieldConSQLReportes txt_cod_motivo;
   private LimiteTextFieldConSQLReportes txt_cod_deposito;
   private LabelPrincipal lblNombreMotivo;
   private LabelPrincipal lblNombreDeposito;
   private LabelPrincipal lblFechaInicialFiltroTexto;
   private LabelPrincipal lblFechaFinalFiltroTexto;
   private LabelPrincipal lblDepositoFiltroTexto;
   private LabelPrincipal lblMotivoFiltroTexto;
   private ModeloTablaDefecto modeloTabla;

   private void inicializarObjetos() {
      this.txt_cod_deposito.setValue(0);
      this.lblNombreDeposito.setText("");
      this.txt_cod_motivo.setValue(0);
      this.lblNombreMotivo.setText("");
      this.txt_fecha_final.setDate(null);
      this.txt_fecha_inicial.setDate(null);
      this.lblDepositoFiltroTexto.setText("");
      this.lblFechaFinalFiltroTexto.setText("");
      this.lblFechaInicialFiltroTexto.setText("");
      this.lblNombreDeposito.setText("");
      this.lblNombreMotivo.setText("");
   }

   public AjusteStockReporteForm() {
      this.setTitle("Reporte de Ajuste de Stock");
      this.setBounds(100, 100, 990, 613);
      PanelPadre contentPane = new PanelPadre("");
      PanelPadre panel_filtro = new PanelPadre("Filtro");
      PanelPadre panel_detalle = new PanelPadre("");
      PanelPadre panel_filtro_establecido = new PanelPadre("Filtros Utilizados");
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(contentPane);
      LabelPrincipal lblDeposito = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblMotivo = new LabelPrincipal("Motivo", "label");
      LabelPrincipal lblFechaInicial = new LabelPrincipal("Fecha Inicial", "label");
      LabelPrincipal lblFechaFinal = new LabelPrincipal("Fecha Final", "label");
      LabelPrincipal lblFechaIniFiltro = new LabelPrincipal("Fecha Inicial", "label");
      LabelPrincipal lblFechaFinalFiltro = new LabelPrincipal("Fecha Inicial", "label");
      LabelPrincipal lblDepositoFiltro = new LabelPrincipal("Deposito", "label");
      LabelPrincipal lblMotivoFiltro = new LabelPrincipal("Motivo", "label");
      this.lblFechaInicialFiltroTexto = new LabelPrincipal("Fecha Inicial", "label");
      this.lblFechaFinalFiltroTexto = new LabelPrincipal("Fecha Inicial", "label");
      this.lblDepositoFiltroTexto = new LabelPrincipal(0);
      this.lblMotivoFiltroTexto = new LabelPrincipal(0);
      this.lblNombreMotivo = new LabelPrincipal(0);
      this.lblNombreDeposito = new LabelPrincipal(0);
      this.txt_fecha_inicial = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_fecha_final = new JDateChooser("dd-MM-yyyy", "##-##-####", ' ');
      this.txt_cod_deposito = new LimiteTextFieldConSQLReportes(999999, this.lblNombreDeposito, "Depositos", this);
      this.txt_cod_motivo = new LimiteTextFieldConSQLReportes(999999, this.lblNombreMotivo, "MotivosAjuste", this);
      String[] cabecera = new String[]{"Codigo", "Nombre", "Cantidad"};
      this.modeloTabla = new ModeloTablaDefecto(cabecera);
      BuscadorTablaReportes tabla = new BuscadorTablaReportes(this.modeloTabla, "AjusteStock", this);
      JScrollPane scrollTabla = new JScrollPane(tabla);
      this.modeloLista = new DefaultListModel<>();
      this.listaCabecera = new JList<>(this.modeloLista);
      this.listaCabecera.setCellRenderer(new AjusteStockReporteForm.AjusteRenderer());
      JScrollPane scrollLista = new JScrollPane(this.listaCabecera);
      JSplitPane splitPane = new JSplitPane(1, scrollLista, scrollTabla);
      splitPane.setResizeWeight(0.6);
      GroupLayout gl_panel_filtro_establecido = new GroupLayout(panel_filtro_establecido);
      gl_panel_filtro_establecido.setHorizontalGroup(
         gl_panel_filtro_establecido.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro_establecido.createSequentialGroup()
                  .addComponent(lblFechaIniFiltro, -2, 73, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblFechaInicialFiltroTexto, -2, 85, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblFechaFinalFiltro, -2, 73, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblFechaFinalFiltroTexto, -2, 73, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblDepositoFiltro, -2, 57, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblDepositoFiltroTexto, -2, 200, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblMotivoFiltro, -2, 57, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblMotivoFiltroTexto, -2, 200, -2)
                  .addContainerGap(103, 32767)
            )
      );
      gl_panel_filtro_establecido.setVerticalGroup(
         gl_panel_filtro_establecido.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro_establecido.createSequentialGroup()
                  .addGroup(
                     gl_panel_filtro_establecido.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblFechaIniFiltro, -2, 25, -2)
                        .addComponent(this.lblFechaInicialFiltroTexto, -2, 25, -2)
                        .addComponent(lblFechaFinalFiltro, -2, 25, -2)
                        .addComponent(this.lblFechaFinalFiltroTexto, -2, 25, -2)
                        .addComponent(lblDepositoFiltro, -2, 25, -2)
                        .addComponent(this.lblDepositoFiltroTexto, -2, 28, -2)
                        .addComponent(lblMotivoFiltro, -2, 25, -2)
                        .addComponent(this.lblMotivoFiltroTexto, -2, 28, -2)
                  )
                  .addContainerGap(-1, 32767)
            )
      );
      panel_filtro_establecido.setLayout(gl_panel_filtro_establecido);
      GroupLayout gl_contentPane = new GroupLayout(contentPane);
      gl_contentPane.setHorizontalGroup(
         gl_contentPane.createParallelGroup(Alignment.TRAILING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addGroup(
                     gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(panel_detalle, -2, 965, 32767)
                        .addComponent(panel_filtro_establecido, -2, 962, -2)
                        .addComponent(panel_filtro, 0, 0, 32767)
                  )
                  .addContainerGap(81, 32767)
            )
      );
      gl_contentPane.setVerticalGroup(
         gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_contentPane.createSequentialGroup()
                  .addComponent(panel_filtro, -2, 100, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_detalle, -2, 400, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(panel_filtro_establecido, -2, 59, -2)
                  .addContainerGap(84, 32767)
            )
      );
      GroupLayout gl_panel_filtro = new GroupLayout(panel_filtro);
      gl_panel_filtro.setHorizontalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addComponent(lblFechaInicial, -2, 73, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_fecha_inicial, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblFechaFinal, -2, 65, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_fecha_final, -2, 96, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(lblDeposito, -2, 59, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cod_deposito, -2, 55, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreDeposito, -2, 200, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblMotivo, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.txt_cod_motivo, -2, 45, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.lblNombreMotivo, -2, 190, -2)
                  .addContainerGap(12, 32767)
            )
      );
      gl_panel_filtro.setVerticalGroup(
         gl_panel_filtro.createParallelGroup(Alignment.LEADING)
            .addGroup(
               gl_panel_filtro.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.txt_fecha_final, -2, 33, -2)
                        .addComponent(this.txt_fecha_inicial, -2, 33, -2)
                        .addComponent(lblFechaFinal, -2, 25, -2)
                        .addComponent(lblFechaInicial, -2, 25, -2)
                        .addGroup(
                           gl_panel_filtro.createParallelGroup(Alignment.TRAILING)
                              .addGroup(
                                 gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                                    .addComponent(lblDeposito, -2, 25, -2)
                                    .addComponent(this.txt_cod_deposito, -2, 32, -2)
                              )
                              .addGroup(
                                 gl_panel_filtro.createParallelGroup(Alignment.LEADING)
                                    .addComponent(this.lblNombreDeposito, -2, 28, -2)
                                    .addGroup(
                                       gl_panel_filtro.createParallelGroup(Alignment.TRAILING)
                                          .addComponent(this.lblNombreMotivo, -2, 28, -2)
                                          .addGroup(
                                             gl_panel_filtro.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(lblMotivo, -2, 25, -2)
                                                .addComponent(this.txt_cod_motivo, -2, 32, -2)
                                          )
                                    )
                              )
                        )
                  )
                  .addGap(45)
            )
      );
      panel_filtro.setLayout(gl_panel_filtro);
      GroupLayout gl_panel_detalle = new GroupLayout(panel_detalle);
      gl_panel_detalle.setHorizontalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.TRAILING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addComponent(splitPane, -2, 963, -2).addContainerGap(-1, 32767))
      );
      gl_panel_detalle.setVerticalGroup(
         gl_panel_detalle.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_detalle.createSequentialGroup().addContainerGap().addComponent(splitPane, -2, 380, -2).addContainerGap(85, 32767))
      );
      panel_detalle.setLayout(gl_panel_detalle);
      contentPane.setLayout(gl_contentPane);
      this.inicializarObjetos();
      this.listaCabecera.addListSelectionListener(e -> {
         if (!e.getValueIsAdjusting()) {
            Map<String, Object> seleccionado = this.listaCabecera.getSelectedValue();
            if (seleccionado != null) {
               int nroAjuste = (Integer)seleccionado.get("nro_registro");
               this.modeloTabla.deleteRow(tabla);
               AjusteStockE.buscarAjusteReporteDetalle(nroAjuste, this.modeloTabla, this);
            }
         }
      });
   }

   @Override
   public void recuperar() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
      String fecha_ini = null;
      String fecha_ini2 = null;
      String fecha_final = null;
      String fecha_final2 = null;
      if (this.txt_fecha_inicial.getDate() != null) {
         fecha_ini = dateFormat.format(this.txt_fecha_inicial.getDate());
         fecha_ini2 = dateFormat2.format(this.txt_fecha_inicial.getDate());
      }

      if (this.txt_fecha_final.getDate() != null) {
         fecha_final = dateFormat.format(this.txt_fecha_final.getDate());
         fecha_final2 = dateFormat2.format(this.txt_fecha_final.getDate());
      }

      this.lblDepositoFiltroTexto.setText(this.lblNombreDeposito.getText());
      this.lblMotivoFiltroTexto.setText(this.lblNombreMotivo.getText());
      this.lblFechaInicialFiltroTexto.setText(fecha_ini2);
      this.lblFechaFinalFiltroTexto.setText(fecha_final2);
      AjusteStockE.buscarAjusteReporte(
         this.modeloLista,
         Integer.parseInt(this.txt_cod_deposito.getText().trim().replace(".", "")),
         Integer.parseInt(this.txt_cod_motivo.getText().trim().replace(".", "")),
         fecha_ini,
         fecha_final,
         this
      );
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            AjusteStockReporteForm frame = new AjusteStockReporteForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
               var3.printStackTrace();
            }
         }
      });
   }

   class AjusteRenderer extends JPanel implements ListCellRenderer<Map<String, Object>> {
      private static final long serialVersionUID = 1L;
      private JLabel lblTitulo = new JLabel();
      private JLabel lblFecha = new JLabel();
      private JLabel lblHora = new JLabel();
      private JLabel lblUsuario = new JLabel();
      private JLabel lblDeposito = new JLabel();
      private JLabel lblMotivo = new JLabel();

      public AjusteRenderer() {
         this.setLayout(new BorderLayout(10, 5));
         this.lblTitulo.setFont(this.lblTitulo.getFont().deriveFont(1, 14.0F));
         this.lblFecha.setFont(new Font("SansSerif", 0, 12));
         this.lblHora.setFont(new Font("SansSerif", 0, 12));
         this.lblUsuario.setFont(new Font("SansSerif", 0, 12));
         this.lblDeposito.setFont(new Font("SansSerif", 0, 12));
         this.lblMotivo.setFont(new Font("SansSerif", 0, 12));
         JPanel panelIzquierdo = new JPanel(new GridLayout(3, 1));
         panelIzquierdo.add(this.lblTitulo);
         panelIzquierdo.add(this.lblFecha);
         panelIzquierdo.add(this.lblHora);
         JPanel panelDerecho = new JPanel(new GridLayout(3, 1));
         panelDerecho.add(this.lblUsuario);
         panelDerecho.add(this.lblDeposito);
         panelDerecho.add(this.lblMotivo);
         JPanel panelCentro = new JPanel(new BorderLayout(10, 0));
         panelCentro.add(panelIzquierdo, "West");
         panelCentro.add(panelDerecho, "Center");
         this.add(panelCentro, "Center");
         this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      }

      public Component getListCellRendererComponent(
         JList<? extends Map<String, Object>> list, Map<String, Object> value, int index, boolean isSelected, boolean cellHasFocus
      ) {
         this.lblTitulo.setText("\ud83e\uddfe Ajuste N° " + value.get("nro_registro"));
         this.lblFecha.setText("\ud83d\udcc5 Fecha: " + value.get("fecha"));
         this.lblHora.setText("\ud83d\udd52 Hora: " + value.get("hora"));
         this.lblUsuario.setText("\ud83d\udc64 Usuario: " + value.get("usuario"));
         this.lblDeposito.setText("\ud83c\udfec Depósito: " + value.get("deposito"));
         this.lblMotivo.setText("\ud83d\udccc Motivo: " + value.get("motivo"));
         this.setBackground(isSelected ? new Color(200, 230, 255) : Color.WHITE);
         return this;
      }
   }
}
