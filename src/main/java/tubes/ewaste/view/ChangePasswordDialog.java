package tubes.ewaste.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import tubes.ewaste.controller.UserController;
import tubes.ewaste.model.User;

public class ChangePasswordDialog extends JDialog {
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private UserController userController;
    private User user;

    public ChangePasswordDialog(JFrame parent, UserController userController, User user) {
        super(parent, "Change Password", true);
        
        this.userController = userController;
        this.user = user;
        
        // Panel untuk form input
        JPanel panel = new JPanel(new GridLayout(4, 2));  // Add an extra row for padding or any additional info
        
        // Label dan field untuk password lama
        panel.add(new JLabel("Old Password:"));
        oldPasswordField = new JPasswordField(20);
        panel.add(oldPasswordField);
        
        // Label dan field untuk password baru
        panel.add(new JLabel("New Password:"));
        newPasswordField = new JPasswordField(20);
        panel.add(newPasswordField);
        
        // Label dan field untuk konfirmasi password baru
        panel.add(new JLabel("Confirm New Password:"));
        confirmPasswordField = new JPasswordField(20);
        panel.add(confirmPasswordField);
        
        // Tombol simpan
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> handleSave());
        
        // Panel untuk tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        
        // Menambahkan panel ke dialog
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        // Mengatur ukuran dan posisi dialog
        setSize(300, 250); // Increased the height slightly for better visibility
        setLocationRelativeTo(parent);
    }
    
    // Method untuk mendapatkan password lama
    public String getOldPassword() {
        return new String(oldPasswordField.getPassword());
    }
    
    // Method untuk mendapatkan password baru
    public String getNewPassword() {
        return new String(newPasswordField.getPassword());
    }
    
    // Method untuk mendapatkan konfirmasi password baru
    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }
    
    // Menangani aksi tombol simpan
    private void handleSave() {
        String oldPassword = getOldPassword();
        String newPassword = getNewPassword();
        String confirmPassword = getConfirmPassword();
        
        // Validasi jika password baru dan konfirmasi password tidak sama
        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "New password and confirmation do not match.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Verifikasi password lama
            boolean isPasswordValid = userController.checkPassword(user.getId(), oldPassword);
            
            if (!isPasswordValid) {
                JOptionPane.showMessageDialog(this, "Old password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Proses perubahan password
                try {
                    userController.changePassword(user.getId(), oldPassword, newPassword);
                    JOptionPane.showMessageDialog(this, "Password changed successfully.");
                    dispose(); // Menutup dialog setelah berhasil
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error changing password: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
