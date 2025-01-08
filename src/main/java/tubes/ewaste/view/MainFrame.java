package tubes.ewaste.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private HalamanLogin loginPanel;
    private HalamanRegister registerPanel;
    private DashboardAdmin dashboardPanel;
    private HalamanOtp otpPanel; // Tambahkan variabel OTPPanel
    private String emailForVerification;
    private HalamanUtamaPanel halamanUtamaPanel;
    private HalamanProfile profilePanel;
    private Integer currentUserId; // Untuk menyimpan user ID yang login


    public MainFrame() {
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
    
        // Initialize panels
        loginPanel = new HalamanLogin(this);
        registerPanel = new HalamanRegister(this);
        dashboardPanel = new DashboardAdmin(this);
        otpPanel = new HalamanOtp(this); 
        halamanUtamaPanel = new HalamanUtamaPanel(this);
        profilePanel = new HalamanProfile(this, currentUserId); // Inisialisasi ProfilePanel
    
        // Add panels to card layout
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(dashboardPanel, "DASHBOARD");
        mainPanel.add(otpPanel, "OTP");
        mainPanel.add(halamanUtamaPanel, "HALAMAN_UTAMA");
        mainPanel.add(profilePanel, "PROFILE"); // Tambahkan ProfilePanel ke mainPanel
    
        // Add main panel to frame
        add(mainPanel);
    
        // Show login by default
        showLogin();
    }
    
    // Tambahkan metode untuk menampilkan ProfilePanel
    public void showProfile() {
        // Pastikan userId diambil dari user yang login
        if (currentUserId != null) {
            profilePanel = new HalamanProfile(this, currentUserId); // Berikan userId saat inisialisasi
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
        dashboardPanel.loadUsers(); // Load users data when showing dashboard
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    public void showOTP() {
        cardLayout.show(mainPanel, "OTP"); // Menampilkan OTPPanel
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
    
}
