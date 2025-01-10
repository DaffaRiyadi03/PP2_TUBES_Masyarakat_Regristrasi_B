package tubes.ewaste.view;

import tubes.ewaste.controller.ControllerUser;
import tubes.ewaste.model.User;
import javax.swing.*;
import java.awt.*;

public class HalamanUbahPassword extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser userController;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton changePasswordButton;
    private JButton backButton;

    public HalamanUbahPassword(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        currentPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        changePasswordButton = new JButton("Ubah Password");
        backButton = new JButton("Kembali");

        Dimension fieldSize = new Dimension(250, 35);
        currentPasswordField.setPreferredSize(fieldSize);
        newPasswordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);

        Dimension buttonSize = new Dimension(250, 40);
        changePasswordButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridwidth = GridBagConstraints.REMAINDER;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 5, 5, 5);

        formPanel.add(new JLabel("Password Sekarang:"), formGbc);
        formPanel.add(currentPasswordField, formGbc);
        formPanel.add(new JLabel("Password Baru:"), formGbc);
        formPanel.add(newPasswordField, formGbc);
        formPanel.add(new JLabel("Konfirmasi Password Baru:"), formGbc);
        formPanel.add(confirmPasswordField, formGbc);
        formPanel.add(Box.createVerticalStrut(20), formGbc);
        formPanel.add(changePasswordButton, formGbc);
        formPanel.add(Box.createVerticalStrut(10), formGbc);
        formPanel.add(backButton, formGbc);

        add(formPanel, gbc);
    }

    private void setupListeners() {
        changePasswordButton.addActionListener(e -> {
            String currentPassword = new String(currentPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Harap isi semua field",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this,
                        "Password baru dan konfirmasi password tidak sesuai",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Integer userId = mainFrame.getCurrentUserId();
                User user = userController.getUserById(userId);

                if (user == null) {
                    JOptionPane.showMessageDialog(this,
                            "User tidak ditemukan",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verifikasi password sekarang
                if (!userController.checkPassword(currentPassword, user.getPassword())) {
                    JOptionPane.showMessageDialog(this,
                            "Password sekarang tidak sesuai",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update password
                String hashedNewPassword = userController.hashPassword(newPassword);
                user.setPassword(hashedNewPassword);

                boolean updated = userController.updateUser(user);
                if (updated) {
                    JOptionPane.showMessageDialog(this,
                            "Password berhasil diperbarui!",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.showLogin(); // Logout pengguna setelah berhasil mengganti password
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Gagal memperbarui password.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> mainFrame.showProfile());
    }
}
