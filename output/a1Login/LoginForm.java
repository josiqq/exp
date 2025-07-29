package a1Login;

import a00PedidosClientes.TablaDetallePedidosCliente;
import a2MenuPrincipal.Principal;
import a3Permisos.PermisosUsuario;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import utilidades.BotonPadre;
import utilidades.BuscarIni;
import utilidades.CheckPadre;
import utilidades.LabelImagenes;
import utilidades.LabelPrincipal;
import utilidadesConexion.DatabaseConnection;
import utilidadesSQL.DialogResultadoOperacion;
import utilidadesSQL.LogErrores;
import utilidadesVentanas.JFramePrincipal;

public class LoginForm extends JFramePrincipal implements ActionListener {
   public static LoginForm instancia;
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private JFormattedTextField txt_usuario;
   private JPasswordField txt_password;
   private BotonPadre btnSalir;
   private BotonPadre btnIngresar;
   private CheckPadre chckbxRecordarUsuario;

   public static LoginForm getInstancia() {
      if (instancia == null) {
         instancia = new LoginForm();
      }

      return instancia;
   }

   private void addEscapeKey() {
      KeyStroke escape = KeyStroke.getKeyStroke(27, 0, false);
      Action escapeAction = new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            LoginForm.this.confirmarSalida();
         }
      };
      this.getRootPane().getInputMap(2).put(escape, "ESCAPE");
      this.getRootPane().getActionMap().put("ESCAPE", escapeAction);
   }

   private void confirmarSalida() {
      this.dispose();
   }

   private void configurarEnterComoTabGlobal() {
      KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
         @Override
         public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == 401 && e.getKeyCode() == 10) {
               Component comp = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
               if (comp instanceof JTextArea) {
                  return false;
               } else if (comp instanceof JTable) {
                  return false;
               } else {
                  String nombre = comp.getName();
                  if ("txt_codigo".trim().equals(nombre) || "txt_buscador".trim().equals(nombre) || "tabla_buscador".trim().equals(nombre)) {
                     return false;
                  } else if (comp instanceof TablaDetallePedidosCliente) {
                     return false;
                  } else {
                     if (comp instanceof AbstractButton) {
                        ((AbstractButton)comp).doClick();
                     }

                     KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                     return true;
                  }
               }
            } else {
               return false;
            }
         }
      });
   }

   public static void main(String[] args) {
      try {
         UIManager.setLookAndFeel(new FlatLightLaf());
      } catch (Exception var2) {
         LogErrores.errores(var2, "Error al cargar Tema");
         var2.printStackTrace();
      }

      UIManager.put("Desktop.background", new Color(230, 230, 230));
      UIManager.put("InternalFrame.background", new Color(245, 245, 245));
      UIManager.put("InternalFrame.borderColor", new Color(220, 220, 220));
      UIManager.put("InternalFrame.activeBackground", new Color(0, 69, 134));
      UIManager.put("InternalFrame.inactiveBackground", new Color(240, 240, 240));
      UIManager.put("InternalFrame.titleForeground", new Color(50, 50, 50));
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            LoginForm frame = new LoginForm();

            try {
               frame.setVisible(true);
            } catch (Exception var3) {
            }
         }
      });
   }

   public LoginForm() {
      this.setResizable(false);
      this.setUndecorated(true);
      this.setDefaultCloseOperation(3);
      this.setBounds(100, 100, 441, 402);
      this.setLocationRelativeTo(null);
      this.setVisible(true);
      this.contentPane = new JPanel();
      this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.contentPane.setBackground(new Color(0, 0, 0, 0));
      this.contentPane.setOpaque(false);
      this.setContentPane(this.contentPane);
      this.contentPane.setLayout(null);
      this.contentPane.setSize(100, 100);
      JPanel panel_login = new JPanel();
      panel_login.setBackground(Color.LIGHT_GRAY);
      panel_login.setBounds(125, 96, 432, 311);
      this.contentPane.add(panel_login);
      panel_login.setLayout(null);
      int panelX = (this.getWidth() - panel_login.getWidth() - this.getInsets().left - this.getInsets().right) / 2;
      int panelY = (this.getHeight() - panel_login.getHeight() - this.getInsets().top - this.getInsets().bottom) / 2;
      panel_login.setLocation(panelX, panelY);
      LabelPrincipal lblInicioSesion = new LabelPrincipal("Inicio de Sesion al Sistema", "label");
      lblInicioSesion.setBounds(4, 10, 432, 25);
      this.contentPane.add(lblInicioSesion);
      lblInicioSesion.setHorizontalAlignment(0);
      lblInicioSesion.setFont(new Font("Tahoma", 1, 18));
      LabelImagenes lblUsuario = new LabelImagenes("usuario");
      lblUsuario.setFont(new Font("Tahoma", 1, 14));
      lblUsuario.setBounds(52, 50, 86, 46);
      panel_login.add(lblUsuario);
      LabelImagenes lblPassword = new LabelImagenes("password");
      lblPassword.setFont(new Font("Tahoma", 1, 14));
      lblPassword.setBounds(52, 106, 86, 42);
      panel_login.add(lblPassword);
      this.txt_usuario = new JFormattedTextField();
      this.txt_usuario.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            if (LoginForm.this.txt_usuario.getText().equals("Nombre de usuario")) {
               LoginForm.this.txt_usuario.setText("");
               LoginForm.this.txt_usuario.setForeground(Color.BLACK);
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (LoginForm.this.txt_usuario.getText().isEmpty()) {
               LoginForm.this.txt_usuario.setText("Nombre de usuario");
               LoginForm.this.txt_usuario.setForeground(Color.GRAY);
            }
         }
      });
      this.txt_usuario.setBounds(148, 63, 215, 33);
      panel_login.add(this.txt_usuario);
      this.txt_password = new JPasswordField();
      this.txt_password.setBounds(148, 115, 215, 33);
      panel_login.add(this.txt_password);
      this.btnIngresar = new BotonPadre("Acceder", "acceder.png");
      this.btnIngresar.setBounds(81, 214, 117, 42);
      panel_login.add(this.btnIngresar);
      this.btnSalir = new BotonPadre("Salir", "salir.png");
      this.btnSalir.setBounds(230, 214, 117, 42);
      panel_login.add(this.btnSalir);
      this.chckbxRecordarUsuario = new CheckPadre("Recordar Usuario");
      this.chckbxRecordarUsuario.setHorizontalAlignment(4);
      this.chckbxRecordarUsuario.setBounds(220, 155, 140, 21);
      panel_login.add(this.chckbxRecordarUsuario);
      this.btnIngresar.addActionListener(this);
      this.btnSalir.addActionListener(this);
      this.addEscapeKey();
      this.configurarEnterComoTabGlobal();
      this.txt_usuario.setValue(BuscarIni.buscar("usuario"));
      if (this.txt_usuario.getText().isEmpty()) {
         this.txt_usuario.requestFocusInWindow();
      } else {
         this.txt_password.requestFocusInWindow();
      }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.btnSalir) {
         System.exit(0);
      }

      if (e.getSource() == this.btnIngresar) {
         String usuarioIngresado = this.txt_usuario.getText();
         String passwordIngresado = new String(this.txt_password.getPassword());
         DatabaseConnection db = DatabaseConnection.getInstance();
         db.setCredenciales(usuarioIngresado, passwordIngresado);
         LoginEntidad entidad = LoginEntidad.buscarUsuario();
         if (entidad.getEstado() == 1) {
            db.generarIDConexion();
            PermisosUsuario permisos = PermisosUsuario.getInstancia();
            permisos.cargarPermisosDesdeBD(usuarioIngresado, this);
            if (this.chckbxRecordarUsuario.isSelected()) {
               BuscarIni.guardar("usuario", this.txt_usuario.getText());
            }

            Principal principal = Principal.getInstancia();
            principal.setVisible(true);
            this.dispose();
         } else {
            DialogResultadoOperacion rs = new DialogResultadoOperacion("Usuario inactivo o inexistente", "error");
            rs.setLocationRelativeTo(this);
            rs.setVisible(true);
         }
      }
   }
}
