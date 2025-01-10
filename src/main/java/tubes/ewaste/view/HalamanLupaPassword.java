package tubes.ewaste.view;

import javax.swing.*;
import java.awt.*;
import tubes.ewaste.controller.ControllerUser;
import tubes.ewaste.service.MailService;

public class HalamanLupaPassword extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser controllerUser;
    private final MailService mailService;
    private JTextField emailField;
    private JButton sendOtpButton; // Ganti nama dari submitButton menjadi sendOtpButton
     private JButton backButton;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel emailPanel;
    private JPanel otpPanel;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JTextField otpField;
    private JButton verifyButton;

    public HalamanLupaPassword(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controllerUser = new ControllerUser();
        this.mailService = new MailService();
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        emailField = new JTextField(20);
        sendOtpButton = new JButton("Kirim OTP"); // Ganti nama tombol
        backButton = new JButton("Kembali");
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        otpField = new JTextField(20);
        verifyButton = new JButton("Verifikasi & Reset Password");

        Dimension fieldSize = new Dimension(250, 35);
        emailField.setPreferredSize(fieldSize);
        newPasswordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);
        otpField.setPreferredSize(fieldSize);

        Dimension buttonSize = new Dimension(250, 40);
        sendOtpButton.setPreferredSize(buttonSize);
        verifyButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

         cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        createEmailPanel();
        createOtpPanel();
    }

     private void createEmailPanel() {
        emailPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        emailPanel.add(new JLabel("Masukkan Email Anda:"), gbc);
        emailPanel.add(emailField, gbc);
        emailPanel.add(Box.createVerticalStrut(20), gbc);
        emailPanel.add(sendOtpButton, gbc);
        emailPanel.add(Box.createVerticalStrut(10), gbc);
        emailPanel.add(backButton, gbc);
    }

    private void createOtpPanel() {
        otpPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        otpPanel.add(new JLabel("Masukkan Kode OTP:"), gbc);
        otpPanel.add(otpField, gbc);
        otpPanel.add(Box.createVerticalStrut(10), gbc);

        otpPanel.add(new JLabel("Password Baru:"), gbc);
        otpPanel.add(newPasswordField, gbc);
        otpPanel.add(Box.createVerticalStrut(10), gbc);

        otpPanel.add(new JLabel("Konfirmasi Password Baru:"), gbc);
        otpPanel.add(confirmPasswordField, gbc);
        otpPanel.add(Box.createVerticalStrut(20), gbc);

        otpPanel.add(verifyButton, gbc);
        otpPanel.add(Box.createVerticalStrut(10), gbc);

        JButton backToEmailButton = new JButton("Kembali");
        backToEmailButton.setPreferredSize(new Dimension(250, 40));
        backToEmailButton.addActionListener(e -> cardLayout.show(cardPanel, "email"));
        otpPanel.add(backToEmailButton, gbc);
    }


    private void setupLayout() {
         setLayout(new BorderLayout());

        cardPanel.add(emailPanel, "email");
        cardPanel.add(otpPanel, "otp");

        add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "email");
    }

    private void setupListeners() {
        sendOtpButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            if (!validateEmail(email)) {
                return;
            }

            try {
                if (!controllerUser.checkEmailExists(email)) {
                    JOptionPane.showMessageDialog(this,
                        "Email tidak terdaftar",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String otp = controllerUser.generateOtp();
                controllerUser.saveOtpToDatabase(email, otp);
                mailService.sendOtpEmail(email, otp);

                JOptionPane.showMessageDialog(this,
                    "OTP telah dikirim ke email Anda",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

               cardLayout.show(cardPanel, "otp");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

         verifyButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String otp = otpField.getText().trim();
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!validateResetPassword(otp, newPassword, confirmPassword)) {
                return;
            }

            try {
                // Verify OTP
                if (controllerUser.verifyOtp(email, otp)) {
                    // Update password in database
                    tubes.ewaste.model.User user = controllerUser.findUserByEmail(email);
                    if (user != null) {
                        controllerUser.updatePasswordById(user.getId(), newPassword);
                        JOptionPane.showMessageDialog(this,
                            "Password berhasil direset. Silakan login dengan password baru.",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.showLogin(); // Navigate to login page
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Kode OTP tidak valid atau sudah kadaluarsa",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });


        backButton.addActionListener(e -> mainFrame.showLogin());
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Email tidak boleh kosong",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this,
                "Format email tidak valid",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean validateResetPassword(String otp, String newPassword, String confirmPassword) {
         if (otp.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Semua field harus diisi",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                "Password baru dan konfirmasi password tidak cocok",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (newPassword.length() < 8) {
            JOptionPane.showMessageDialog(this,
                "Password harus minimal 8 karakter",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}