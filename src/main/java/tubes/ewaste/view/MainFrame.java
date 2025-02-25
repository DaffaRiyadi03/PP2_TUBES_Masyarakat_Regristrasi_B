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
     private HalamanOtpReset otpResetPanel;
    private String emailForVerification;
    private HalamanUtama halamanUtamaPanel;
    private HalamanProfil profilePanel;
    private Integer currentUserId;
    private HalamanUbahPassword halamanUbahPassword;
    private HalamanLupaPassword lupaPasswordPanel;
    private HalamanResetPassword resetPasswordPanel;
   

    public MainFrame() {
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
    
        // Initialize panels
        loginPanel = new HalamanLogin(this);
        registerPanel = new HalamanRegistrasi(this);
        dashboardPanel = new DashboardAdmin(this);
        otpPanel = new HalamanOtp(this);
         otpResetPanel = new HalamanOtpReset(this);
        halamanUtamaPanel = new HalamanUtama(this);
        profilePanel = new HalamanProfil(this, currentUserId);
        halamanUbahPassword = new HalamanUbahPassword(this);
         lupaPasswordPanel = new HalamanLupaPassword(this);
        resetPasswordPanel = new HalamanResetPassword(this);
        
        // Add panels to card layout
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(dashboardPanel, "DASHBOARD");
        mainPanel.add(otpPanel, "OTP");
         mainPanel.add(otpResetPanel, "OTP_RESET");
        mainPanel.add(halamanUtamaPanel, "HALAMAN_UTAMA");
        mainPanel.add(profilePanel, "PROFILE");
        mainPanel.add(halamanUbahPassword, "UBAH_PASSWORD");
         mainPanel.add(lupaPasswordPanel, "LUPA_PASSWORD");
          mainPanel.add(resetPasswordPanel, "RESET_PASSWORD");
        // Add main panel to frame
        add(mainPanel);
    
        // Show login by default
        showLogin();
    }
    
    // Tambahkan metode untuk menampilkan ProfilePanel
    public void showProfile() {
        // Pastikan userId diambil dari user yang login
        if (currentUserId != null) {
            profilePanel = new HalamanProfil(this, currentUserId); // Berikan userId saat inisialisasi
            mainPanel.add(profilePanel, "PROFILE"); // Tambahkan panel ke mainPanel
            cardLayout.show(mainPanel, "PROFILE"); // Tampilkan ProfilePanel
        } else {
            JOptionPane.showMessageDialog(this, "User ID tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    public void showOTP() {
        cardLayout.show(mainPanel, "OTP"); // Menampilkan OTPPanel
    }
    
      public void showOTPReset() {
        cardLayout.show(mainPanel, "OTP_RESET"); // Menampilkan OTP Reset Panel
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
    public void showUbahPassword() { 
        cardLayout.show(mainPanel, "UBAH_PASSWORD"); }
        
     public void showLupaPassword() {
        cardLayout.show(mainPanel, "LUPA_PASSWORD");
     }
     public void showResetPassword(){
        cardLayout.show(mainPanel, "RESET_PASSWORD");
     }
}