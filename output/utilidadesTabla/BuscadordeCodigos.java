package utilidadesTabla;

import java.awt.AWTKeyStroke;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import utilidades.BotonMensajes;
import utilidades.LabelPrincipal;
import utilidades.LimiteTextField;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JDialogBuscador;

public class BuscadordeCodigos extends JDialogBuscador {
   private static final long serialVersionUID = 1L;
   private String codigo;

   public BuscadordeCodigos(int numero) {
      this.setTitle("Numeracion de Factura");
      this.setBounds(100, 100, 321, 155);
      this.setLocationRelativeTo(null);
      LabelPrincipal lblBuscador = new LabelPrincipal("Confirme el numero de Factura", "label");
      final LimiteTextField txt_buscador_valor = new LimiteTextField(25);
      txt_buscador_valor.setName("txt_buscador");
      txt_buscador_valor.setValue(numero);
      BotonMensajes btnAceptar = new BotonMensajes("Aceptar", "acceder.png");
      BotonMensajes btnCancelar = new BotonMensajes("Cancelar", "salir.png");
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING)
                                    .addComponent(txt_buscador_valor, -2, 276, -2)
                                    .addComponent(lblBuscador, -2, 242, -2)
                              )
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addGap(63)
                              .addComponent(btnAceptar, -2, 90, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(btnCancelar, -2, 90, -2)
                        )
                  )
                  .addContainerGap(148, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblBuscador, -2, 22, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(txt_buscador_valor, -2, 30, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnAceptar, -2, 30, -2).addComponent(btnCancelar, -2, 30, -2))
                  .addContainerGap(175, 32767)
            )
      );
      this.getContentPane().setLayout(groupLayout);
      txt_buscador_valor.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadordeCodigos.this.codigo = txt_buscador_valor.getText();
               BuscadordeCodigos.this.dispose();
            }
         }
      });
      btnCancelar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadordeCodigos.this.dispose();
         }
      });
      btnCancelar.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadordeCodigos.this.dispose();
            }
         }
      });
      btnAceptar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadordeCodigos.this.codigo = txt_buscador_valor.getText();
            BuscadordeCodigos.this.dispose();
         }
      });
      btnAceptar.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadordeCodigos.this.codigo = txt_buscador_valor.getText();
               BuscadordeCodigos.this.dispose();
            }
         }
      });
      this.setFocusTraversal(btnAceptar, btnCancelar);
      this.setFocusTraversal(btnCancelar, btnAceptar);
   }

   public BuscadordeCodigos(String titulo) {
      this.setTitle("Crear nuevo " + titulo);
      this.setBounds(100, 100, 321, 155);
      this.setLocationRelativeTo(null);
      LabelPrincipal lblBuscador = new LabelPrincipal("Ingrese nombre del nuevo " + titulo, "label");
      final LimiteTextField txt_buscador_valor = new LimiteTextField(25);
      txt_buscador_valor.setName("txt_buscador");
      BotonMensajes btnAceptar = new BotonMensajes("Aceptar", "acceder.png");
      BotonMensajes btnCancelar = new BotonMensajes("Cancelar", "salir.png");
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING)
                                    .addComponent(txt_buscador_valor, -2, 276, -2)
                                    .addComponent(lblBuscador, -2, 242, -2)
                              )
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addGap(63)
                              .addComponent(btnAceptar, -2, 90, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(btnCancelar, -2, 90, -2)
                        )
                  )
                  .addContainerGap(148, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblBuscador, -2, 22, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(txt_buscador_valor, -2, 30, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnAceptar, -2, 30, -2).addComponent(btnCancelar, -2, 30, -2))
                  .addContainerGap(175, 32767)
            )
      );
      this.getContentPane().setLayout(groupLayout);
      txt_buscador_valor.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadordeCodigos.this.codigo = txt_buscador_valor.getText();
               BuscadordeCodigos.this.dispose();
            }
         }
      });
      btnCancelar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadordeCodigos.this.dispose();
         }
      });
      btnCancelar.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadordeCodigos.this.dispose();
            }
         }
      });
      btnAceptar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadordeCodigos.this.codigo = txt_buscador_valor.getText();
            BuscadordeCodigos.this.dispose();
         }
      });
      btnAceptar.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadordeCodigos.this.codigo = txt_buscador_valor.getText();
               BuscadordeCodigos.this.dispose();
            }
         }
      });
      this.setFocusTraversal(btnAceptar, btnCancelar);
      this.setFocusTraversal(btnCancelar, btnAceptar);
   }

   public BuscadordeCodigos() {
      this.setTitle("Buscador de Detalles");
      this.setBounds(100, 100, 311, 150);
      this.setLocationRelativeTo(null);
      LabelPrincipal lblBuscador = new LabelPrincipal("Digite el valor a Buscar", "lineas");
      final LimiteTextField txt_buscador_valor = new LimiteTextField(25);
      txt_buscador_valor.setName("txt_buscador");
      BotonMensajes btnBuscar = new BotonMensajes("Buscar");
      BotonMensajes btnCancelar = new BotonMensajes("Cancelar");
      GroupLayout groupLayout = new GroupLayout(this.getContentPane());
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addGroup(
                     groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addContainerGap()
                              .addGroup(
                                 groupLayout.createParallelGroup(Alignment.LEADING)
                                    .addComponent(txt_buscador_valor, -2, 276, -2)
                                    .addComponent(lblBuscador, -2, 242, -2)
                              )
                        )
                        .addGroup(
                           groupLayout.createSequentialGroup()
                              .addGap(63)
                              .addComponent(btnBuscar, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(btnCancelar, -2, 70, -2)
                        )
                  )
                  .addContainerGap(148, 32767)
            )
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               groupLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblBuscador, -2, 22, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(txt_buscador_valor, -2, 30, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnBuscar, -2, -1, -2).addComponent(btnCancelar, -2, -1, -2))
                  .addContainerGap(175, 32767)
            )
      );
      this.getContentPane().setLayout(groupLayout);
      txt_buscador_valor.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadordeCodigos.this.codigo = txt_buscador_valor.getText();
               BuscadordeCodigos.this.dispose();
            }
         }
      });
      btnCancelar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadordeCodigos.this.dispose();
         }
      });
      btnCancelar.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadordeCodigos.this.dispose();
            }
         }
      });
      btnBuscar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BuscadordeCodigos.this.codigo = txt_buscador_valor.getText();
            BuscadordeCodigos.this.dispose();
         }
      });
      btnBuscar.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               BuscadordeCodigos.this.codigo = txt_buscador_valor.getText();
               BuscadordeCodigos.this.dispose();
            }
         }
      });
      this.setFocusTraversal(btnBuscar, btnCancelar);
      this.setFocusTraversal(btnCancelar, btnBuscar);
   }

   private void setFocusTraversal(final BotonMensajes button, final BotonMensajes prevButton) {
      Set<AWTKeyStroke> forwardKeys = new HashSet<>(button.getFocusTraversalKeys(0));
      forwardKeys.add(KeyStroke.getKeyStroke(39, 0));
      button.setFocusTraversalKeys(0, forwardKeys);
      Set<AWTKeyStroke> backwardKeys = new HashSet<>(button.getFocusTraversalKeys(1));
      backwardKeys.add(KeyStroke.getKeyStroke(37, 0));
      button.setFocusTraversalKeys(1, backwardKeys);
      button.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 39) {
               button.requestFocus();
            } else if (e.getKeyCode() == 37) {
               prevButton.requestFocus();
            }
         }
      });
   }

   public String getCodigo() {
      return this.codigo;
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            BuscadordeCodigos frame = new BuscadordeCodigos();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
               LogErrores.errores(var3, "Error al iniciar el Formulario", frame);
               var3.printStackTrace();
            }
         }
      });
   }
}
