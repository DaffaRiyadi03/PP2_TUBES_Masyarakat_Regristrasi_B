package tubes.ewaste.view;

import javax.swing.*;
import java.awt.*;
import tubes.ewaste.controller.ControllerUser;
import tubes.ewaste.model.User;

public class HalamanResetPassword extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser controllerUser;
    private final String email;

    private JTextField otpField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton verifyButton;
    private JButton backButton;

     public HalamanResetPassword(MainFrame mainFrame, String email) {
        this.mainFrame = mainFrame;
        this.controllerUser = new ControllerUser();
        this.email = email;

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        otpField = new JTextField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        verifyButton = new JButton("Reset Password");
        backButton = new JButton("Kembali");

        // Set ukuran komponen
        Dimension fieldSize = new Dimension(250, 35);
        otpField.setPreferredSize(fieldSize);
        newPasswordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);

        Dimension buttonSize = new Dimension(250, 40);
        verifyButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Panel utama
        JPanel mainPanel = new JPanel(new GridBagLayout());

        // Label instruksi
        JLabel instructionLabel = new JLabel("Masukkan kode OTP yang telah dikirim ke email: " + email);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(instructionLabel, gbc);

        mainPanel.add(Box.createVerticalStrut(20), gbc);

        // OTP field
        mainPanel.add(new JLabel("Kode OTP:"), gbc);
        mainPanel.add(otpField, gbc);

        mainPanel.add(Box.createVerticalStrut(10), gbc);

        // Password fields
        mainPanel.add(new JLabel("Password Baru:"), gbc);
        mainPanel.add(newPasswordField, gbc);

        mainPanel.add(new JLabel("Konfirmasi Password Baru:"), gbc);
        mainPanel.add(confirmPasswordField, gbc);

        mainPanel.add(Box.createVerticalStrut(20), gbc);

        // Buttons
        mainPanel.add(verifyButton, gbc);
        mainPanel.add(Box.createVerticalStrut(10), gbc);
        mainPanel.add(backButton, gbc);

        add(mainPanel);
    }

    private void setupListeners() {
        verifyButton.addActionListener(e -> {
            String otp = otpField.getText().trim();
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Validasi input
            if (otp.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                showMessage("Semua field harus diisi", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validasi password match
            if (!newPassword.equals(confirmPassword)) {
                showMessage("Password baru dan konfirmasi password tidak cocok", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validasi password length and strength
            if (!isValidPassword(newPassword)) {
                showMessage("Password harus memiliki minimal 8 karakter dan mengandung angka, huruf besar, dan karakter khusus", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Verifikasi OTP
                if (controllerUser.verifyOtp(email, otp)) {
                    // Update password
                    User user = controllerUser.findUserByEmail(email);
                    if (user != null) {
                        controllerUser.updatePasswordById(user.getId(), newPassword);
                        showMessage("Password berhasil direset. Silakan login dengan password baru.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.showLogin();
                    }
                } else {
                    showMessage("Kode OTP tidak valid atau sudah kadaluarsa", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                showMessage("Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> mainFrame.showLogin());
    }

    // Function to display messages in a dialog box
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    // Function to validate password strength
    private boolean isValidPassword(String password) {
        // Check for minimum length, at least one uppercase letter, one digit, and one special character
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") && // at least one uppercase letter
               password.matches(".*\\d.*") && // at least one digit
               password.matches(".*[!@#$%^&*(),.?\":{}|<>].*"); // at least one special character
    }
}