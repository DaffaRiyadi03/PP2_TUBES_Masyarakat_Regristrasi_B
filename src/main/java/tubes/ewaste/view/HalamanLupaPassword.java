package tubes.ewaste.view;

import tubes.ewaste.controller.ControllerUser;
import javax.swing.*;
import java.awt.*;

public class HalamanLupaPassword extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser userController;
    private JTextField emailField;
    private JButton sendOtpButton;
     private JButton backButton;


    public HalamanLupaPassword(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        emailField = new JTextField(20);
        sendOtpButton = new JButton("Kirim OTP");
        backButton = new JButton("Back to Login");

        Dimension fieldSize = new Dimension(250, 35);
        emailField.setPreferredSize(fieldSize);

         Dimension buttonSize = new Dimension(250, 40);
        sendOtpButton.setPreferredSize(buttonSize);
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

         formPanel.add(new JLabel("Email:"), formGbc);
         formPanel.add(emailField, formGbc);
        formPanel.add(Box.createVerticalStrut(20), formGbc);
         formPanel.add(sendOtpButton, formGbc);
          formPanel.add(Box.createVerticalStrut(10), formGbc);
        formPanel.add(backButton, formGbc);
        add(formPanel, gbc);
    }

    private void setupListeners() {
        sendOtpButton.addActionListener(e -> {
            String email = emailField.getText().trim();

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter your email address",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
               try {
                   String otp = userController.generatePasswordResetOtp(email);
                  if (otp != null) {
                       mainFrame.setEmailForVerification(email);
                        JOptionPane.showMessageDialog(this,
                            "OTP has been sent to your email.",
                             "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                         mainFrame.showOTPReset();

                  }else{
                     JOptionPane.showMessageDialog(this,
                            "Failed to send OTP.",
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
           backButton.addActionListener(e -> mainFrame.showLogin());
    }
}