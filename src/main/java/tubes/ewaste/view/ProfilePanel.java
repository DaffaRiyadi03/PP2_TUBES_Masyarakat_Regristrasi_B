package tubes.ewaste.view;

import tubes.ewaste.controller.UserController;
import tubes.ewaste.model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;

public class ProfilePanel extends JPanel {
    private final MainFrame mainFrame;
    private final UserController userController;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField birthDateField; // Format: yyyy-MM-dd
    private JLabel profilePictureLabel;
    private JButton saveButton;
    private JButton uploadPhotoButton;
    private JButton backButton;

    private User user;

    public ProfilePanel(MainFrame mainFrame, Integer userId) {
        this.mainFrame = mainFrame;
        this.userController = new UserController();
        this.user = userController.getUserById(userId); // Ambil data user berdasarkan ID
  
        initComponents();
        populateFields();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        emailField.setEditable(false); // Email tidak bisa diubah
        addressField = new JTextField(20);
        birthDateField = new JTextField(20);

        profilePictureLabel = new JLabel("");
        profilePictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePictureLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        profilePictureLabel.setPreferredSize(new Dimension(150, 150));

        saveButton = new JButton("Simpan");
        uploadPhotoButton = new JButton("Unggah Foto");
        backButton = new JButton("Kembali");

        Dimension fieldSize = new Dimension(250, 35);
        nameField.setPreferredSize(fieldSize);
        emailField.setPreferredSize(fieldSize);
        addressField.setPreferredSize(fieldSize);
        birthDateField.setPreferredSize(fieldSize);

        Dimension buttonSize = new Dimension(150, 40);
        saveButton.setPreferredSize(buttonSize);
        uploadPhotoButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
    }

    private void populateFields() {
        
        if (user != null) {
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
            addressField.setText(user.getAddress() != null ? user.getAddress() : "");
            birthDateField.setText(user.getBirthDate() != null ? user.getBirthDate().toString() : "");
            
            if (user.getPhotoPath() != null) {
                File imgFile = new File(user.getPhotoPath());
                if (imgFile.exists() && imgFile.isFile()) {
                    ImageIcon icon = new ImageIcon(user.getPhotoPath());
                    Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    profilePictureLabel.setIcon(new ImageIcon(image));
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
            Image defaultImage = defaultIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            profilePictureLabel.setIcon(new ImageIcon(defaultImage));
        } catch (Exception e) {
            profilePictureLabel.setText("Foto tidak ditemukan");
        }
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.anchor = GridBagConstraints.WEST;
        add(backButton, gbc);
        
        gbc.anchor = GridBagConstraints.NORTHWEST;

        add(new JLabel("Nama:"), gbc);
        add(nameField, gbc);

        add(new JLabel("Email:"), gbc);
        add(emailField, gbc);

        add(new JLabel("Alamat:"), gbc);
        add(addressField, gbc);

        add(new JLabel("Tanggal Lahir (YYYY-MM-DD):"), gbc);
        add(birthDateField, gbc);

        add(new JLabel("Foto Profil:"), gbc);
        add(profilePictureLabel, gbc);
        add(uploadPhotoButton, gbc);

        add(Box.createVerticalStrut(20), gbc);
        add(saveButton, gbc);
    }
    
    private void setupListeners() {
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String address = addressField.getText();
            String birthDateStr = birthDateField.getText();

            if (name.isEmpty() || birthDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nama atau tanggal lahir harus diisi!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate birthDate = LocalDate.parse(birthDateStr);
                user.setName(name);
                user.setAddress(address);
                user.setBirthDate(birthDate);

                boolean updated = userController.updateUser(user);
                if (updated) {
                    JOptionPane.showMessageDialog(this,
                            "Profil berhasil diperbarui!",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Gagal memperbarui profil.",
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

        uploadPhotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String photoPath = fileChooser.getSelectedFile().getAbsolutePath();
                user.setPhotoPath(photoPath);
                
                ImageIcon imageIcon = new ImageIcon(photoPath);
                Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                profilePictureLabel.setIcon(new ImageIcon(image));
            }
        });

        backButton.addActionListener(e -> {
            mainFrame.showHalamanUtama(); // Kembali ke halaman utama setelah tombol back ditekan
        });
    
    }
}
