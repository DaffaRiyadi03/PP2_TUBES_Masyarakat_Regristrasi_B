package tubes.ewaste.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FormDialogKategori extends JDialog {
    private JTextField nameField;
    private JTextArea descriptionField;
    private JButton saveButton;
    private JButton cancelButton;

    public FormDialogKategori(JFrame parent, String title) {
        super(parent, title, true);

        nameField = new JTextField();
        descriptionField = new JTextArea(5, 20);
        saveButton = new JButton("Simpan");
        cancelButton = new JButton("Batal");

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Nama Kategori:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Deskripsi:"));
        inputPanel.add(new JScrollPane(descriptionField));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);

        cancelButton.addActionListener(e -> dispose());
    }

    public String getCategoryName() {
        return nameField.getText().trim();
    }

    public String getCategoryDescription() {
        return descriptionField.getText().trim();
    }

    public void setCategoryName(String name) {
        nameField.setText(name);
    }

    public void setCategoryDescription(String description) {
        descriptionField.setText(description);
    }

    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(e -> {
            String name = getCategoryName();
            String description = getCategoryDescription();

            if (name.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Nama kategori dan deskripsi tidak boleh kosong!",
                    "Validasi Gagal",
                    JOptionPane.WARNING_MESSAGE
                );
            } else if (!isValidString(name) || !isValidString(description)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Nama kategori dan deskripsi harus berupa teks yang valid!",
                    "Validasi Gagal",
                    JOptionPane.WARNING_MESSAGE
                );
            } else {
                listener.actionPerformed(e);
            }
        });
    }

    private boolean isValidString(String input) {
        return input.matches("^[\\p{L}\\p{M}\\p{Zs}\\p{P}]+$"); // Hanya huruf, spasi, tanda baca
    }

}
