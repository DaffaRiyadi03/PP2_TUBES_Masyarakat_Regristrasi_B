package tubes.ewaste.view;

import tubes.ewaste.controller.ControllerUser;

import javax.swing.*;
import java.awt.*;

public class HalamanResetPassword extends JPanel {
     private final MainFrame mainFrame;
    private final ControllerUser userController;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton resetButton;
     private JButton backButton;


    public HalamanResetPassword(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();
         initComponents();
        setupLayout();
        setupListeners();
    }
     private void initComponents() {
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        resetButton = new JButton("Reset Password");
        backButton = new JButton("Back to Login");

         Dimension fieldSize = new Dimension(250, 35);
        newPasswordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);


        Dimension buttonSize = new Dimension(250, 40);
        resetButton.setPreferredSize(buttonSize);
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

        formPanel.add(new JLabel("Password Baru:"), formGbc);
        formPanel.add(newPasswordField, formGbc);
        formPanel.add(new JLabel("Konfirmasi Password Baru:"), formGbc);
        formPanel.add(confirmPasswordField, formGbc);
        formPanel.add(Box.createVerticalStrut(20), formGbc);
        formPanel.add(resetButton, formGbc);
        formPanel.add(Box.createVerticalStrut(10), formGbc);
        formPanel.add(backButton, formGbc);

        add(formPanel, gbc);
    }

    private void setupListeners() {
        resetButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
             if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                         "Please fill all the fields",
                        "Error",
                         JOptionPane.ERROR_MESSAGE);
                 return;
            }

            if (!newPassword.equals(confirmPassword)) {
               JOptionPane.showMessageDialog(this,
                       "New password and confirm password do not match",
                        "Error",
                         JOptionPane.ERROR_MESSAGE);
               return;
            }

            try {
                String email = mainFrame.getEmailForVerification();

                if (email == null || email.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Error: Email for verification is missing.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean reseted = userController.resetPassword(email, newPassword);

                 if(reseted){
                     JOptionPane.showMessageDialog(this,
                            "Password has been reset successfully. Please login with your new password",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.showLogin();
                 }else{
                    JOptionPane.showMessageDialog(this,
                            "Failed to reset password",
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