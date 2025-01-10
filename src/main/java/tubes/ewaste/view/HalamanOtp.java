package tubes.ewaste.view;

import tubes.ewaste.controller.ControllerUser;
import javax.swing.*;
import java.awt.*;
import tubes.ewaste.controller.ControllerUser;

public class HalamanOtp extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser controllerUser;
    private JTextField otpField;
    private JButton verifyButton;
    private JButton backButton;

    public HalamanOtp(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controllerUser = new ControllerUser();
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        otpField = new JTextField(20);
        verifyButton = new JButton("Verifikasi OTP");
        backButton = new JButton("Kembali");

        Dimension fieldSize = new Dimension(250, 35);
        otpField.setPreferredSize(fieldSize);

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

        JPanel mainPanel = new JPanel(new GridBagLayout());
        
        JLabel emailLabel = new JLabel("Email: " + mainFrame.getEmailForVerification());
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(emailLabel, gbc);
        
        mainPanel.add(Box.createVerticalStrut(20), gbc);
        mainPanel.add(new JLabel("Masukkan Kode OTP:"), gbc);
        mainPanel.add(otpField, gbc);
        mainPanel.add(Box.createVerticalStrut(20), gbc);
        mainPanel.add(verifyButton, gbc);
        mainPanel.add(Box.createVerticalStrut(10), gbc);
        mainPanel.add(backButton, gbc);

        add(mainPanel);
    }

    private void setupListeners() {
        verifyButton.addActionListener(e -> {
            String otp = otpField.getText().trim();
            String email = mainFrame.getEmailForVerification();

            if (otp.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Masukkan kode OTP",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (controllerUser.verifyOtp(email, otp)) {
                    mainFrame.showResetPassword();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Kode OTP tidak valid atau sudah kadaluarsa",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> mainFrame.showLogin());
    }
}