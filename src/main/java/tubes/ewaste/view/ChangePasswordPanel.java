package tubes.ewaste.view;

import tubes.ewaste.controller.UserController;
import tubes.ewaste.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChangePasswordPanel extends JPanel {
    private final MainFrame mainFrame;
    private final UserController userController;
    private final User user; // Assuming user is passed to the panel

    public ChangePasswordPanel(MainFrame mainFrame, UserController userController, User user) {
        this.mainFrame = mainFrame;
        this.userController = userController;
        this.user = user;

        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel lblCurrentPassword = new JLabel("Password Saat Ini:");
        JLabel lblNewPassword = new JLabel("Password Baru:");
        JLabel lblConfirmPassword = new JLabel("Konfirmasi Password Baru:");

        JPasswordField txtCurrentPassword = new JPasswordField();
        JPasswordField txtNewPassword = new JPasswordField();
        JPasswordField txtConfirmPassword = new JPasswordField();

        JButton btnChangePassword = new JButton("Ubah Password");
        JButton btnBack = new JButton("Kembali");
        JLabel lblMessage = new JLabel();
        lblMessage.setForeground(Color.RED);

        add(lblCurrentPassword);
        add(txtCurrentPassword);
        add(lblNewPassword);
        add(txtNewPassword);
        add(lblConfirmPassword);
        add(txtConfirmPassword);
        add(new JLabel()); // Spacer
        add(btnChangePassword);
        add(btnBack); // Tombol kembali
        add(lblMessage);

        // Aksi tombol "Ubah Password"
        btnChangePassword.addActionListener((ActionEvent e) -> {
            String currentPassword = new String(txtCurrentPassword.getPassword());
            String newPassword = new String(txtNewPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());

            // Verifikasi dan ubah password
            if (!newPassword.equals(confirmPassword)) {
                lblMessage.setForeground(Color.RED);
                lblMessage.setText("Password baru dan konfirmasi tidak cocok.");
            } else {
                // Verifikasi password lama
                boolean isPasswordValid = userController.checkPassword(user.getId(), currentPassword);

                if (!isPasswordValid) {
                    lblMessage.setForeground(Color.RED);
                    lblMessage.setText("Password lama salah.");
                } else {
                    try {
                        userController.changePassword(user.getId(), currentPassword, newPassword);
                        lblMessage.setForeground(Color.GREEN);
                        lblMessage.setText("Password berhasil diubah.");
                    } catch (Exception ex) {
                        lblMessage.setForeground(Color.RED);
                        lblMessage.setText("Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Aksi tombol "Kembali"
        btnBack.addActionListener(e -> mainFrame.showDashboard());
    }
}
