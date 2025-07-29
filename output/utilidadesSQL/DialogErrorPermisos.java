package utilidadesSQL;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DialogErrorPermisos extends JDialog {
   private static final long serialVersionUID = 1L;

   public DialogErrorPermisos(JInternalFrame frame, String errorMessage, String errorDetails) {
      this.setTitle("Mensaje de error...");
      this.setModalityType(ModalityType.APPLICATION_MODAL);
      this.setDefaultCloseOperation(2);
      this.setLocation(3, 128);
      this.setModal(true);
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JLabel errorMessageLabel = new JLabel(errorMessage);
      panel.add(errorMessageLabel, "North");
      JTextArea detailsArea = new JTextArea(errorDetails);
      detailsArea.setEditable(false);
      detailsArea.setVisible(false);
      JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
      detailsScrollPane.setVisible(false);
      panel.add(detailsScrollPane, "Center");
      JPanel buttonPanel = new JPanel(new FlowLayout(2));
      JButton btnAceptar = new JButton("Aceptar");
      buttonPanel.add(btnAceptar);
      panel.add(buttonPanel, "South");
      this.getContentPane().add(panel);
      this.pack();
      this.setLocationRelativeTo(frame);
      btnAceptar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            DialogErrorPermisos.this.dispose();
         }
      });
   }

   public DialogErrorPermisos(JDialog frame, String errorMessage, String errorDetails) {
      this.setTitle("Mensaje de error...");
      this.setModalityType(ModalityType.APPLICATION_MODAL);
      this.setDefaultCloseOperation(2);
      this.setLocation(3, 128);
      this.setModal(true);
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JLabel errorMessageLabel = new JLabel(errorMessage);
      panel.add(errorMessageLabel, "North");
      JTextArea detailsArea = new JTextArea(errorDetails);
      detailsArea.setEditable(false);
      detailsArea.setVisible(false);
      JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
      detailsScrollPane.setVisible(false);
      panel.add(detailsScrollPane, "Center");
      JPanel buttonPanel = new JPanel(new FlowLayout(2));
      JButton btnAceptar = new JButton("Aceptar");
      buttonPanel.add(btnAceptar);
      panel.add(buttonPanel, "South");
      this.getContentPane().add(panel);
      this.pack();
      this.setLocationRelativeTo(frame);
      btnAceptar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            DialogErrorPermisos.this.dispose();
         }
      });
   }
}
