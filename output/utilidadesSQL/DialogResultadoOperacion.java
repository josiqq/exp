package utilidadesSQL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utilidades.BotonMensajes;

public class DialogResultadoOperacion extends JDialog {
   private static final long serialVersionUID = 1L;

   public DialogResultadoOperacion(String errorMessage, String nombreImagen) {
      this.setTitle("Mensaje de Sistema...");
      this.setModalityType(ModalityType.APPLICATION_MODAL);
      this.setDefaultCloseOperation(2);
      this.setModal(true);
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      ImageIcon iconoVentana = new ImageIcon(DialogErrores.class.getResource("/imagenes/" + nombreImagen + ".png"));
      Image iconImage = iconoVentana.getImage();
      Image scaledIcon = iconImage.getScaledInstance(30, 30, 4);
      ImageIcon scaledIconoVentana = new ImageIcon(scaledIcon);
      this.setIconImage(scaledIcon);
      JLabel iconLabel = new JLabel(scaledIconoVentana);
      iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
      JLabel errorMessageLabel = new JLabel(errorMessage);
      errorMessageLabel.setFont(new Font("Tahoma", 1, 12));
      errorMessageLabel.setForeground(new Color(0, 128, 0));
      errorMessageLabel.setHorizontalAlignment(0);
      JPanel messageAndButtonsPanel = new JPanel(new BorderLayout());
      messageAndButtonsPanel.add(errorMessageLabel, "Center");
      JPanel buttonPanel = new JPanel(new GridBagLayout());
      buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
      BotonMensajes okButton = new BotonMensajes("OK");
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.anchor = 10;
      buttonPanel.add(okButton, gbc);
      messageAndButtonsPanel.add(buttonPanel, "South");
      JPanel topPanel = new JPanel(new BorderLayout());
      topPanel.add(iconLabel, "West");
      topPanel.add(messageAndButtonsPanel, "Center");
      panel.add(topPanel, "North");
      this.getContentPane().add(panel);
      this.pack();
      okButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            DialogResultadoOperacion.this.dispose();
         }
      });
      okButton.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               DialogResultadoOperacion.this.dispose();
            }
         }
      });
      this.addFocusListener(okButton);
   }

   private void addFocusListener(JButton button) {
   }
}
