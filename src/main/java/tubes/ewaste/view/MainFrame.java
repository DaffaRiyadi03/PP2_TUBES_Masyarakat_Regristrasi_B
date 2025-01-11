package tubes.ewaste.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private HalamanLogin loginPanel;
    private HalamanRegistrasi registerPanel;
    private DashboardAdmin dashboardPanel;
    private HalamanOtp otpPanel;
     private HalamanResetPassword resetPasswordPanel;
    private HalamanUtama halamanUtamaPanel;
    private HalamanProfil profilePanel;
    private String emailForVerification;
    private Integer currentUserId; // Untuk menyimpan user ID yang login
    private String currentEmail; // untuk menyimpan email sementara
    private HalamanLupaPassword lupaPasswordPanel;


    public MainFrame() {
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        resetPasswordPanel = new HalamanResetPassword(this, "");

        // Initialize panels
        loginPanel = new HalamanLogin(this);
        registerPanel = new HalamanRegistrasi(this);
        dashboardPanel = new DashboardAdmin(this);
        lupaPasswordPanel = new HalamanLupaPassword(this);
        otpPanel = new HalamanOtp(this);
         resetPasswordPanel = new HalamanResetPassword(this, ""); // Initialize without email
       halamanUtamaPanel = new HalamanUtama(this);

        // Add panels to card layout
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(dashboardPanel, "DASHBOARD");
        mainPanel.add(lupaPasswordPanel, "FORGOT_PASSWORD");
        mainPanel.add(otpPanel, "OTP_RESET");
        mainPanel.add(resetPasswordPanel, "RESET_PASSWORD");
         mainPanel.add(halamanUtamaPanel, "HALAMAN_UTAMA");

        // Add main panel to frame
        add(mainPanel);

        // Show login by default
        showLogin();
    }

    private void setupFrame() {
        setTitle("E-WastePas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void showLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void showRegister() {
        cardLayout.show(mainPanel, "REGISTER");
    }

    public void showDashboard() {
        cardLayout.show(mainPanel, "DASHBOARD");
    }
    
    public void setEmailForVerification(String email) {
        this.emailForVerification = email;
    }

    public String getEmailForVerification() {
        return emailForVerification;
    }

    public void showHalamanUtama() {
        cardLayout.show(mainPanel, "HALAMAN_UTAMA");
    }

    public void setCurrentUserId(Integer userId) {
        this.currentUserId = userId;
    }

    public Integer getCurrentUserId() {
        return currentUserId;
    }

    public void showProfile() {
        if (currentUserId != null) {
            // Inisialisasi ulang HalamanProfile dengan currentUserId
            profilePanel = new HalamanProfil(this, currentUserId);

            // Periksa apakah panel sudah ada di mainPanel
            mainPanel.add(profilePanel, "PROFILE");

            // Tampilkan HalamanProfile
            cardLayout.show(mainPanel, "PROFILE");
        } else {
            JOptionPane.showMessageDialog(this, "User ID tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

         public void showResetPassword(String email) {
        resetPasswordPanel = new HalamanResetPassword(this, email);
        mainPanel.add(resetPasswordPanel, "RESET_PASSWORD");
        cardLayout.show(mainPanel, "RESET_PASSWORD");
    }

    public boolean sendOtpToEmail(String email) {
        // Validasi email
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            return false; // Email tidak valid
        }
    
        try {
            // Logika untuk mengirim OTP, misalnya menggunakan API atau library email
            System.out.println("Mengirim OTP ke: " + email);
            return true; // Return true jika OTP berhasil dikirim
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false jika gagal
        }
    }

    public void showOtpReset() {
        cardLayout.show(mainPanel, "OTP_RESET");
    }

    public void showForgotPassword() {
        cardLayout.show(mainPanel, "FORGOT_PASSWORD");
    }


    public void showUbahPassword() {
         if (currentUserId != null) {
           HalamanUbahPassword ubahPasswordPanel = new HalamanUbahPassword(this);
           mainPanel.add(ubahPasswordPanel, "UBAH_PASSWORD");
           cardLayout.show(mainPanel, "UBAH_PASSWORD");
        }else{
             JOptionPane.showMessageDialog(this, "User ID tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showResetPassword() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showResetPassword'");
    }

    public void showOTP() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showOTP'");
    }
}