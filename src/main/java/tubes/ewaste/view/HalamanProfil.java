package tubes.ewaste.view;

import tubes.ewaste.controller.ControllerUser;
import tubes.ewaste.model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.awt.geom.Ellipse2D;
import javax.swing.border.EmptyBorder;

public class HalamanProfil extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser userController;
    private User user;

    private JTextField nameField;
    private JTextField addressField;
    private JTextField birthDateField;
    private JLabel profilePictureLabel;
    private JButton saveButton;
    private JButton changePasswordButton;
    private JButton uploadPhotoButton;
    private JButton deleteAccountButton;
    private JButton backButton;

    public HalamanProfil(MainFrame mainFrame, Integer userId) {
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();
        this.user = userController.getUserById(userId);

        initComponents();
        populateFields();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        nameField = new JTextField(20);
        addressField = new JTextField(20);
        birthDateField = new JTextField(20);

        profilePictureLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (getIcon() != null) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int diameter = Math.min(getWidth(), getHeight());
                    int x = (getWidth() - diameter) / 2;
                    int y = (getHeight() - diameter) / 2;

                    g2.setClip(new Ellipse2D.Float(x, y, diameter, diameter));
                    super.paintComponent(g2);
                    g2.dispose();
                } else {
                    super.paintComponent(g);
                }
            }
        };
        profilePictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePictureLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        profilePictureLabel.setPreferredSize(new Dimension(150, 150));

        saveButton = createButton("Simpan", new Color(98, 0, 238), Color.WHITE);
        changePasswordButton = createButton("Ubah Password", Color.DARK_GRAY, Color.WHITE);
        deleteAccountButton = createButton("Hapus Akun", Color.RED, Color.WHITE);
        backButton = createButton("Kembali", null, null);
        uploadPhotoButton = createButton("Unggah Foto", Color.LIGHT_GRAY, null);

        Dimension fieldSize = new Dimension(250, 35);
        nameField.setPreferredSize(fieldSize);
        addressField.setPreferredSize(fieldSize);
        birthDateField.setPreferredSize(fieldSize);

        Dimension buttonSize = new Dimension(200, 40);
        saveButton.setPreferredSize(buttonSize);
        changePasswordButton.setPreferredSize(buttonSize);
        deleteAccountButton.setPreferredSize(buttonSize);
    }

    private JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        if (background != null) button.setBackground(background);
        if (foreground != null) button.setForeground(foreground);
        return button;
    }

    private void populateFields() {
        if (user != null) {
            nameField.setText(user.getName());
            addressField.setText(user.getAddress() != null ? user.getAddress() : "");
            birthDateField.setText(user.getBirthDate() != null ? user.getBirthDate().toString() : "");

            if (user.getPhotoPath() != null) {
                File imgFile = new File(user.getPhotoPath());
                if (imgFile.exists() && imgFile.isFile()) {
                    ImageIcon originalIcon = new ImageIcon(user.getPhotoPath());
                    Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    profilePictureLabel.setIcon(new ImageIcon(scaledImage));
                } else {
                    setDefaultImage();
                }
            } else {
                setDefaultImage();
            }
        }
    }

    private void setDefaultImage() {
        try {
            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/avatar.png"));
            Image scaledImage = defaultIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            profilePictureLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            profilePictureLabel.setText("Foto tidak ditemukan");
        }
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(backButton, BorderLayout.WEST);

        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.add(profilePictureLabel);
        profilePanel.add(uploadPhotoButton);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addField(formPanel, gbc, "Nama Lengkap:", nameField, 0);
        addField(formPanel, gbc, "Alamat:", addressField, 1);
        addField(formPanel, gbc, "Tanggal Lahir:", birthDateField, 2);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(saveButton);
        buttonPanel.add(changePasswordButton);
        buttonPanel.add(deleteAccountButton);

        add(headerPanel);
        add(profilePanel);
        add(new JLabel("Nama:"));
        add(nameField);
        add(new JLabel("Alamat:"));
        add(addressField);
        add(new JLabel("Tanggal Lahir:"));
        add(birthDateField);
        add(saveButton);
        add(changePasswordButton);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void setupListeners() {
        saveButton.addActionListener(e -> saveUserProfile());
        uploadPhotoButton.addActionListener(e -> uploadPhoto());
        changePasswordButton.addActionListener(e -> mainFrame.showUbahPassword());
        deleteAccountButton.addActionListener(e -> deleteAccount());
        backButton.addActionListener(e -> mainFrame.showHalamanUtama());
    }

    private void saveUserProfile() {
        String name = nameField.getText();
        String address = addressField.getText();
        String birthDateStr = birthDateField.getText();

        if (name.isEmpty() || birthDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama atau tanggal lahir harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            LocalDate birthDate = LocalDate.parse(birthDateStr);
            user.setName(name);
            user.setAddress(address);
            user.setBirthDate(birthDate);

            boolean updated = userController.updateUser(user);
            String message = updated ? "Profil berhasil diperbarui!" : "Gagal memperbarui profil.";
            JOptionPane.showMessageDialog(this, message, updated ? "Sukses" : "Error", updated ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void uploadPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String photoPath = fileChooser.getSelectedFile().getAbsolutePath();
            user.setPhotoPath(photoPath);

            ImageIcon imageIcon = new ImageIcon(photoPath);
            Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            profilePictureLabel.setIcon(new ImageIcon(image));
        }
    }

    private void deleteAccount() {
        int confirmed = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus akun ini? Tindakan ini tidak dapat dibatalkan.", "Konfirmasi Hapus Akun", JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            try {
                Integer userId = mainFrame.getCurrentUserId();
                if (userId != null) {
                    userController.deleteUser(userId);
                    JOptionPane.showMessageDialog(this, "Akun berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.showLogin();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus akun. User ID tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}