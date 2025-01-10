package tubes.ewaste.view;

import tubes.ewaste.controller.ControllerUser;
import javax.swing.*;
import java.awt.*;

public class HalamanOTP extends JPanel {
    private final MainFrame mainFrame;
    private JTextField otpField;
    private JButton submitButton;
    private JButton backButton;

    public HalamanOTP(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        otpField = new JTextField(20);
        submitButton = new JButton("Submit");
        backButton = new JButton("Back to Register");

        Dimension fieldSize = new Dimension(250, 35);
        otpField.setPreferredSize(fieldSize);

        Dimension buttonSize = new Dimension(250, 40);
        submitButton.setPreferredSize(buttonSize);
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

        formPanel.add(new JLabel("Enter OTP:"), formGbc);
        formPanel.add(otpField, formGbc);
        formPanel.add(Box.createVerticalStrut(20), formGbc);
        formPanel.add(submitButton, formGbc);
        formPanel.add(Box.createVerticalStrut(10), formGbc);
        formPanel.add(backButton, formGbc);

        add(formPanel, gbc);
    }

    private void setupListeners() {
        submitButton.addActionListener(e -> {
            String otp = otpField.getText().trim();

            if (otp.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter the OTP",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                ControllerUser userController = new ControllerUser();
                String email = mainFrame.getEmailForVerification();

                if (email == null || email.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Error: Email for verification is missing.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean verified = userController.verifyOtp(email, otp);

                if (verified) {
                    JOptionPane.showMessageDialog(this,
                            "OTP Verified Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.showLogin();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid or Expired OTP",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error during OTP verification: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> mainFrame.showRegister());
    }
}
