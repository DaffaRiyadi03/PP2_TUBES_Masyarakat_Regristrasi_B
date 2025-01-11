package tubes.ewaste.view;

import tubes.ewaste.controller.ControllerUser;
import tubes.ewaste.model.User;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class HalamanRegistrasi extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser userController;

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextArea addressArea;
    private JTextField birthDateField;
    private JButton registerButton;
    private JButton backButton;

    public HalamanRegistrasi(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        birthDateField = new JTextField(20);
        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");

        // Set preferred sizes
        Dimension fieldSize = new Dimension(250, 35);
        nameField.setPreferredSize(fieldSize);
        emailField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);
        birthDateField.setPreferredSize(fieldSize);

        Dimension buttonSize = new Dimension(250, 40);
        registerButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("E-Wastepas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        // Title Panel
        JPanel titlePanel1 = new JPanel();
        JLabel titleLabel1 = new JLabel("Registrasi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridwidth = GridBagConstraints.REMAINDER;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 5, 5, 5);

        formPanel.add(new JLabel("Nama:"), formGbc);
        formPanel.add(nameField, formGbc);
        formPanel.add(new JLabel("Email:"), formGbc);
        formPanel.add(emailField, formGbc);
        formPanel.add(new JLabel("Password:"), formGbc);
        formPanel.add(passwordField, formGbc);
        formPanel.add(new JLabel("Konfirmasi Password:"), formGbc);
        formPanel.add(confirmPasswordField, formGbc);
        formPanel.add(new JLabel("Alamat:"), formGbc);
        formPanel.add(new JScrollPane(addressArea), formGbc);
        formPanel.add(new JLabel("Tanggal Lahir (YYYY-MM-DD):"), formGbc);
        formPanel.add(birthDateField, formGbc);
        formPanel.add(Box.createVerticalStrut(20), formGbc);
        formPanel.add(registerButton, formGbc);
        formPanel.add(Box.createVerticalStrut(10), formGbc);
        formPanel.add(backButton, formGbc);

        // Add to main panel
        add(titlePanel, gbc);
        add(Box.createVerticalStrut(20), gbc);
        add(formPanel, gbc);
    }

    private void setupListeners() {
        registerButton.addActionListener(e -> {
            if (validateInput()) {
                registerUser();
            }
        });

        backButton.addActionListener(e -> {
            clearFields();
            mainFrame.showLogin();
        });
    }

    private boolean validateInput() {
        // Validasi nama
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        if (!nameField.getText().matches("^[A-Za-z\\s'.,]+$")) {
            JOptionPane.showMessageDialog(this, "Nama hanya boleh mengandung huruf, spasi, tanda baca sederhana", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        // Validasi email
        if (!emailField.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Format email tidak valid", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        // Validasi password
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password harus minimal 8 karakter", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Password dan konfirmasi password tidak cocok", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            JOptionPane.showMessageDialog(this, "Password harus memiliki huruf besar, huruf kecil, angka, dan simbol", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        // Validasi tanggal lahir
        try {
            LocalDate birthDate = LocalDate.parse(birthDateField.getText());
            if (birthDate.isAfter(LocalDate.now().minusYears(13))) {
                JOptionPane.showMessageDialog(this, "Pengguna harus berusia minimal 13 tahun", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Format tanggal tidak valid. Gunakan format YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        // Validasi alamat
        if (addressArea.getText().trim().isEmpty() || addressArea.getText().trim().length() < 10) {
            JOptionPane.showMessageDialog(this, "Alamat tidak boleh kosong dan harus minimal 10 karakter", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        return true;
    }

    private void registerUser() {
        try {
            User user = new User();
            user.setName(nameField.getText());
            user.setEmail(emailField.getText());
            user.setPassword(new String(passwordField.getPassword()));
            user.setAddress(addressArea.getText());
            user.setBirthDate(LocalDate.parse(birthDateField.getText()));
    
            userController.register(user);
            
            // Simpan email pengguna untuk digunakan di OTPPanel
            mainFrame.setEmailForVerification(user.getEmail());
    
            // Redirect ke OTPPanel
            mainFrame.showOTP();
    
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Registrasi gagal : " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        addressArea.setText("");
        birthDateField.setText("");
    }
}