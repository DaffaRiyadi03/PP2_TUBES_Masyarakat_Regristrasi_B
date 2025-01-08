package tubes.ewaste.view;

import tubes.ewaste.controller.UserController;
import tubes.ewaste.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private final MainFrame mainFrame;
    private final UserController userController;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new UserController();

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        Dimension fieldSize = new Dimension(250, 35);
        emailField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);

        Dimension buttonSize = new Dimension(250, 40);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel headerPanel = new JPanel();
        JLabel titleLabel = new JLabel("E-WastePas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridwidth = GridBagConstraints.REMAINDER;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 5, 5, 5);

        formPanel.add(new JLabel("Email:"), formGbc);
        formPanel.add(emailField, formGbc);
        formPanel.add(new JLabel("Password:"), formGbc);
        formPanel.add(passwordField, formGbc);
        formPanel.add(Box.createVerticalStrut(20), formGbc);
        formPanel.add(loginButton, formGbc);
        formPanel.add(Box.createVerticalStrut(10), formGbc);
        formPanel.add(registerButton, formGbc);

        add(headerPanel, gbc);
        add(Box.createVerticalStrut(30), gbc);
        add(formPanel, gbc);
    }

    private void setupListeners() {
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
    
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Tolong isi semua kolom",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            try {
                if (userController.login(email, password)) {
                    User user = userController.findUserByEmail(email);
                    mainFrame.setCurrentUserId(user.getId()); // Simpan userId ke MainFrame
                    
                    if (user.getRoleId() == 2) { // Arahkan ke halaman utama untuk pengguna dengan roleId 2
                        mainFrame.showHalamanUtama(); // Navigasi ke halaman utama
                    } else if (user.getRoleId() == 1) {
                        mainFrame.showDashboard(); // Navigasi ke dashboard untuk akun admin
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Login berhasil!",
                                "Sukses",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Email atau password salah",
                            "Login Gagal",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error saat login: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    
        registerButton.addActionListener(e -> mainFrame.showRegister());
    }    
}
