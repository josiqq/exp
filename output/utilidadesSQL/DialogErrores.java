package utilidadesSQL;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import utilidades.BotonMensajes;

public class DialogErrores extends JDialog {
   private static final long serialVersionUID = 1L;

   public DialogErrores(String errorMessage) {
      this.setTitle("Mensaje de error...");
      this.setModalityType(ModalityType.APPLICATION_MODAL);
      this.setDefaultCloseOperation(2);
      this.setModal(true);
      ImageIcon iconoVentana = new ImageIcon(DialogErrores.class.getResource("/imagenes/error.png"));
      Image iconImage = iconoVentana.getImage();
      Image scaledIcon = iconImage.getScaledInstance(24, 24, 4);
      ImageIcon scaledIconoVentana = new ImageIcon(scaledIcon);
      this.setIconImage(scaledIcon);
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JPanel messagePanel = new JPanel(new BorderLayout());
      JLabel iconLabel = new JLabel(scaledIconoVentana);
      JLabel errorMessageLabel = new JLabel(errorMessage);
      messagePanel.add(iconLabel, "West");
      messagePanel.add(errorMessageLabel, "Center");
      panel.add(messagePanel, "North");
      JPanel buttonPanel = new JPanel(new FlowLayout(2));
      BotonMensajes viewDetailsButton = new BotonMensajes("Ver detalles");
      viewDetailsButton.setPreferredSize(new Dimension(110, 35));
      BotonMensajes closeButton = new BotonMensajes("Cerrar");
      buttonPanel.add(viewDetailsButton);
      buttonPanel.add(closeButton);
      panel.add(buttonPanel, "South");
      this.getContentPane().add(panel);
      this.pack();
      closeButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            DialogErrores.this.dispose();
         }
      });
      this.setFocusTraversal(closeButton, viewDetailsButton);
      this.setFocusTraversal(viewDetailsButton, closeButton);
   }

   public DialogErrores(String errorMessage, String errorDetails) {
      this.setTitle("Mensaje de error...");
      this.setModalityType(ModalityType.APPLICATION_MODAL);
      this.setDefaultCloseOperation(2);
      this.setModal(true);
      ImageIcon iconoVentana = new ImageIcon(DialogErrores.class.getResource("/imagenes/error.png"));
      Image iconImage = iconoVentana.getImage();
      Image scaledIcon = iconImage.getScaledInstance(24, 24, 4);
      ImageIcon scaledIconoVentana = new ImageIcon(scaledIcon);
      this.setIconImage(scaledIcon);
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JPanel messagePanel = new JPanel(new BorderLayout());
      JLabel iconLabel = new JLabel(scaledIconoVentana);
      JLabel errorMessageLabel = new JLabel(errorMessage);
      messagePanel.add(iconLabel, "West");
      messagePanel.add(errorMessageLabel, "Center");
      panel.add(messagePanel, "North");
      final JTextArea detailsArea = new JTextArea(errorDetails);
      detailsArea.setEditable(false);
      detailsArea.setVisible(false);
      detailsArea.setLineWrap(true);
      detailsArea.setWrapStyleWord(true);
      final JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
      detailsScrollPane.setVisible(false);
      panel.add(detailsScrollPane, "Center");
      JPanel buttonPanel = new JPanel(new FlowLayout(2));
      final BotonMensajes viewDetailsButton = new BotonMensajes("Ver detalles");
      viewDetailsButton.setPreferredSize(new Dimension(110, 35));
      BotonMensajes closeButton = new BotonMensajes("Cerrar");
      buttonPanel.add(viewDetailsButton);
      buttonPanel.add(closeButton);
      panel.add(buttonPanel, "South");
      this.getContentPane().add(panel);
      this.pack();
      viewDetailsButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            boolean isVisible = detailsArea.isVisible();
            detailsArea.setVisible(!isVisible);
            detailsScrollPane.setVisible(!isVisible);
            if (!isVisible) {
               detailsArea.setPreferredSize(new Dimension(400, 200));
            } else {
               detailsArea.setPreferredSize(null);
            }

            DialogErrores.this.pack();
            viewDetailsButton.setText(isVisible ? "Ver detalles" : "Ocultar detalles");
         }
      });
      closeButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            DialogErrores.this.dispose();
         }
      });
      this.setFocusTraversal(closeButton, viewDetailsButton);
      this.setFocusTraversal(viewDetailsButton, closeButton);
   }

   public DialogErrores(JDialog frame, String errorMessage, String errorDetails) {
      this.setTitle("Mensaje de error...");
      this.setModalityType(ModalityType.APPLICATION_MODAL);
      this.setDefaultCloseOperation(2);
      this.setModal(true);
      this.setLocationRelativeTo(frame);
      ImageIcon iconoVentana = new ImageIcon(DialogErrores.class.getResource("/imagenes/error.png"));
      Image iconImage = iconoVentana.getImage();
      Image scaledIcon = iconImage.getScaledInstance(24, 24, 4);
      ImageIcon scaledIconoVentana = new ImageIcon(scaledIcon);
      this.setIconImage(scaledIcon);
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JPanel messagePanel = new JPanel(new BorderLayout());
      JLabel iconLabel = new JLabel(scaledIconoVentana);
      JLabel errorMessageLabel = new JLabel(errorMessage);
      messagePanel.add(iconLabel, "West");
      messagePanel.add(errorMessageLabel, "Center");
      panel.add(messagePanel, "North");
      final JTextArea detailsArea = new JTextArea(errorDetails);
      detailsArea.setEditable(false);
      detailsArea.setVisible(false);
      detailsArea.setLineWrap(true);
      detailsArea.setWrapStyleWord(true);
      final JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
      detailsScrollPane.setVisible(false);
      panel.add(detailsScrollPane, "Center");
      JPanel buttonPanel = new JPanel(new FlowLayout(2));
      final BotonMensajes viewDetailsButton = new BotonMensajes("Ver detalles");
      viewDetailsButton.setPreferredSize(new Dimension(110, 35));
      BotonMensajes closeButton = new BotonMensajes("Cerrar");
      buttonPanel.add(viewDetailsButton);
      buttonPanel.add(closeButton);
      panel.add(buttonPanel, "South");
      this.getContentPane().add(panel);
      this.pack();
      this.setLocationRelativeTo(frame);
      viewDetailsButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            boolean isVisible = detailsArea.isVisible();
            detailsArea.setVisible(!isVisible);
            detailsScrollPane.setVisible(!isVisible);
            if (!isVisible) {
               detailsArea.setPreferredSize(new Dimension(400, 200));
            } else {
               detailsArea.setPreferredSize(null);
            }

            DialogErrores.this.pack();
            viewDetailsButton.setText(isVisible ? "Ver detalles" : "Ocultar detalles");
         }
      });
      closeButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            DialogErrores.this.dispose();
         }
      });
      this.setFocusTraversal(closeButton, viewDetailsButton);
      this.setFocusTraversal(viewDetailsButton, closeButton);
   }

   public DialogErrores(JInternalFrame frame, String errorMessage, String errorDetails) {
      this.setTitle("Mensaje de error...");
      this.setModalityType(ModalityType.APPLICATION_MODAL);
      this.setDefaultCloseOperation(2);
      this.setModal(true);
      this.setLocationRelativeTo(frame);
      ImageIcon iconoVentana = new ImageIcon(DialogErrores.class.getResource("/imagenes/error.png"));
      Image iconImage = iconoVentana.getImage();
      Image scaledIcon = iconImage.getScaledInstance(24, 24, 4);
      ImageIcon scaledIconoVentana = new ImageIcon(scaledIcon);
      this.setIconImage(scaledIcon);
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JPanel messagePanel = new JPanel(new BorderLayout());
      JLabel iconLabel = new JLabel(scaledIconoVentana);
      JLabel errorMessageLabel = new JLabel(errorMessage);
      messagePanel.add(iconLabel, "West");
      messagePanel.add(errorMessageLabel, "Center");
      panel.add(messagePanel, "North");
      final JTextArea detailsArea = new JTextArea(errorDetails);
      detailsArea.setEditable(false);
      detailsArea.setVisible(false);
      detailsArea.setLineWrap(true);
      detailsArea.setWrapStyleWord(true);
      final JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
      detailsScrollPane.setVisible(false);
      panel.add(detailsScrollPane, "Center");
      JPanel buttonPanel = new JPanel(new FlowLayout(2));
      final BotonMensajes viewDetailsButton = new BotonMensajes("Ver detalles");
      viewDetailsButton.setPreferredSize(new Dimension(110, 35));
      BotonMensajes closeButton = new BotonMensajes("Cerrar");
      buttonPanel.add(viewDetailsButton);
      buttonPanel.add(closeButton);
      panel.add(buttonPanel, "South");
      this.getContentPane().add(panel);
      this.pack();
      this.setLocationRelativeTo(frame);
      viewDetailsButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            boolean isVisible = detailsArea.isVisible();
            detailsArea.setVisible(!isVisible);
            detailsScrollPane.setVisible(!isVisible);
            if (!isVisible) {
               detailsArea.setPreferredSize(new Dimension(400, 200));
            } else {
               detailsArea.setPreferredSize(null);
            }

            DialogErrores.this.pack();
            viewDetailsButton.setText(isVisible ? "Ver detalles" : "Ocultar detalles");
         }
      });
      closeButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            DialogErrores.this.dispose();
         }
      });
      this.setFocusTraversal(closeButton, viewDetailsButton);
      this.setFocusTraversal(viewDetailsButton, closeButton);
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

   public DialogErrores(JFrame frame, String errorMessage, String errorDetails) {
      this.setTitle("Mensaje de error...");
      this.setModalityType(ModalityType.APPLICATION_MODAL);
      this.setDefaultCloseOperation(2);
      this.setModal(true);
      this.setLocationRelativeTo(frame);
      ImageIcon iconoVentana = new ImageIcon(DialogErrores.class.getResource("/imagenes/error.png"));
      Image iconImage = iconoVentana.getImage();
      Image scaledIcon = iconImage.getScaledInstance(24, 24, 4);
      ImageIcon scaledIconoVentana = new ImageIcon(scaledIcon);
      this.setIconImage(scaledIcon);
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JPanel messagePanel = new JPanel(new BorderLayout());
      JLabel iconLabel = new JLabel(scaledIconoVentana);
      JLabel errorMessageLabel = new JLabel(errorMessage);
      messagePanel.add(iconLabel, "West");
      messagePanel.add(errorMessageLabel, "Center");
      panel.add(messagePanel, "North");
      final JTextArea detailsArea = new JTextArea(errorDetails);
      detailsArea.setEditable(false);
      detailsArea.setVisible(false);
      detailsArea.setLineWrap(true);
      detailsArea.setWrapStyleWord(true);
      final JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
      detailsScrollPane.setVisible(false);
      panel.add(detailsScrollPane, "Center");
      JPanel buttonPanel = new JPanel(new FlowLayout(2));
      final BotonMensajes viewDetailsButton = new BotonMensajes("Ver detalles");
      viewDetailsButton.setPreferredSize(new Dimension(110, 35));
      BotonMensajes closeButton = new BotonMensajes("Cerrar");
      buttonPanel.add(viewDetailsButton);
      buttonPanel.add(closeButton);
      panel.add(buttonPanel, "South");
      this.getContentPane().add(panel);
      this.pack();
      this.setLocationRelativeTo(frame);
      viewDetailsButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            boolean isVisible = detailsArea.isVisible();
            detailsArea.setVisible(!isVisible);
            detailsScrollPane.setVisible(!isVisible);
            if (!isVisible) {
               detailsArea.setPreferredSize(new Dimension(400, 200));
            } else {
               detailsArea.setPreferredSize(null);
            }

            DialogErrores.this.pack();
            viewDetailsButton.setText(isVisible ? "Ver detalles" : "Ocultar detalles");
         }
      });
      closeButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            DialogErrores.this.dispose();
         }
      });
      this.setFocusTraversal(closeButton, viewDetailsButton);
      this.setFocusTraversal(viewDetailsButton, closeButton);
   }
}
