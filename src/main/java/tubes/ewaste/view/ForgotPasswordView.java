package tubes.ewaste.view;

import javax.swing.*;
import tubes.ewaste.controller.UserController;

import java.awt.*;

public class ForgotPasswordView extends JPanel {
    private JTextField emailField;
    private JPasswordField newPasswordField, confirmPasswordField;
    private final MainFrame mainFrame;
    private final UserController userController;

    public ForgotPasswordView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new UserController(); // Create an instance of UserController
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JPanel buttonPanel = new JPanel();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        panel.add(emailLabel);
        panel.add(emailField);

        JLabel newPasswordLabel = new JLabel("Password Baru:");
        newPasswordField = new JPasswordField();
        panel.add(newPasswordLabel);
        panel.add(newPasswordField);

        JLabel confirmPasswordLabel = new JLabel("Konfirmasi Password:");
        confirmPasswordField = new JPasswordField();
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);

        JButton submitButton = new JButton("Ubah Password");
        JButton backButton = new JButton("Kembali");
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listener untuk tombol "Kembali"
        backButton.addActionListener(e -> mainFrame.showLogin());
        submitButton.addActionListener(e -> handlePasswordReset());
    }

    private void handlePasswordReset() {
        String email = emailField.getText().trim();
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validasi input
        if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Password tidak sesuai.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email tidak valid.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Call the controller method to reset the password
        boolean success = userController.resetPassword(email, newPassword);
        if (success) {
            JOptionPane.showMessageDialog(this, "Password berhasil diubah.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.showLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Email tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Simple email validation using regular expression
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
