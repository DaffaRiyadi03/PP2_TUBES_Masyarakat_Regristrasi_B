package tubes.ewaste.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private DashboardPanel dashboardPanel;
    private OTPPanel otpPanel; // Tambahkan variabel OTPPanel
    private String emailForVerification;
    private HalamanUtamaPanel halamanUtamaPanel;

    public MainFrame() {
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize panels
        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        dashboardPanel = new DashboardPanel(this);
        otpPanel = new OTPPanel(this); // Inisialisasi OTPPanel
        halamanUtamaPanel = new HalamanUtamaPanel(this);

        // Add panels to card layout
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(dashboardPanel, "DASHBOARD");
        mainPanel.add(otpPanel, "OTP"); // Tambahkan OTPPanel ke mainPanel
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
    
}
