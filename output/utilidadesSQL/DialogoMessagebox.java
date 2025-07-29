package utilidadesSQL;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import utilidades.BotonMensajes;

public class DialogoMessagebox extends JDialog {
   private static final long serialVersionUID = 1L;
   private boolean resultadoEncontrado = false;

   public DialogoMessagebox(String errorMessage) {
      this.setTitle("Mensaje de Sistema...");
      this.setModalityType(ModalityType.APPLICATION_MODAL);
      this.setDefaultCloseOperation(2);
      this.setModal(true);
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setBorder(
         BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), BorderFactory.createEmptyBorder(15, 15, 15, 15))
      );
      ImageIcon iconoVentana = new ImageIcon(DialogErrores.class.getResource("/imagenes/pregunta.png"));
      Image iconImage = iconoVentana.getImage();
      Image scaledIcon = iconImage.getScaledInstance(30, 30, 4);
      ImageIcon scaledIconoVentana = new ImageIcon(scaledIcon);
      this.setIconImage(scaledIcon);
      JLabel iconLabel = new JLabel(scaledIconoVentana);
      iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
      JLabel errorMessageLabel = new JLabel(errorMessage);
      errorMessageLabel.setFont(new Font("Tahoma", 1, 12));
      errorMessageLabel.setForeground(Color.RED);
      errorMessageLabel.setHorizontalAlignment(0);
      JPanel messageAndButtonsPanel = new JPanel(new BorderLayout());
      messageAndButtonsPanel.add(errorMessageLabel, "Center");
      JPanel buttonPanel = new JPanel(new FlowLayout(2));
      buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
      BotonMensajes noButton = new BotonMensajes("No");
      BotonMensajes yesButton = new BotonMensajes("Si");
      buttonPanel.add(yesButton);
      buttonPanel.add(noButton);
      messageAndButtonsPanel.add(buttonPanel, "South");
      JPanel topPanel = new JPanel(new BorderLayout());
      topPanel.add(iconLabel, "West");
      topPanel.add(messageAndButtonsPanel, "Center");
      panel.add(topPanel, "North");
      this.getContentPane().add(panel);
      this.pack();
      noButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            System.out.println("nooooooooooo");
            DialogoMessagebox.this.resultadoEncontrado = false;
            DialogoMessagebox.this.dispose();
         }
      });
      noButton.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               DialogoMessagebox.this.resultadoEncontrado = false;
               DialogoMessagebox.this.dispose();
            }
         }
      });
      yesButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            DialogoMessagebox.this.resultadoEncontrado = true;
            DialogoMessagebox.this.dispose();
         }
      });
      yesButton.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               DialogoMessagebox.this.resultadoEncontrado = true;
               DialogoMessagebox.this.dispose();
            }
         }
      });
      this.setFocusTraversal(noButton, yesButton);
      this.setFocusTraversal(yesButton, noButton);
      this.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            DialogoMessagebox.this.resultadoEncontrado = false;
            DialogoMessagebox.this.dispose();
         }
      });
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

   public boolean isResultadoEncontrado() {
      return this.resultadoEncontrado;
   }
}
