package tubes.ewaste.view;

import tubes.ewaste.controller.ControllerJenisSampah;
import tubes.ewaste.controller.ControllerKategori;
import tubes.ewaste.model.JenisSampah;
import tubes.ewaste.model.Kategori;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormJenisSampah extends JDialog {
    private final ControllerJenisSampah itemTypeController;
    private final ControllerKategori categoryController;

    private JTextField nameField;
    private JTextArea descriptionField;
    private JComboBox<Kategori> categoryComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    private JenisSampah itemType; 

    public FormJenisSampah(JFrame parent, ControllerJenisSampah itemTypeController, ControllerKategori categoryController, String title) {
        super(parent, title, true);
        this.itemTypeController = itemTypeController;
        this.categoryController = categoryController;

        setTitle(title);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initComponents();
        loadCategories();
    }

    private void initComponents() {
        nameField = new JTextField(20);
        descriptionField = new JTextArea(5, 20);
        categoryComboBox = new JComboBox<>();
        saveButton = new JButton("Simpan");
        cancelButton = new JButton("Batal");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Nama Jenis Item:"));
        panel.add(nameField);
        panel.add(new JLabel("Deskripsi:"));
        panel.add(new JScrollPane(descriptionField));
        panel.add(new JLabel("Kategori:"));
        panel.add(categoryComboBox);
        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveItemType();
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    private void loadCategories() {
        List<Kategori> categories = categoryController.getAllCategories(); 
        categoryComboBox.removeAllItems();
        
        for (Kategori category : categories) {
            categoryComboBox.addItem(category);
        }
    }    
    

    private void saveItemType() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        Kategori selectedCategory = (Kategori) categoryComboBox.getSelectedItem();
        
        if (name.isEmpty() || description.isEmpty() || selectedCategory == null) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!");
            return;
        }
        
        System.out.println("Selected Category: " + selectedCategory);
        
        if (itemType == null) {
            JenisSampah newItemType = new JenisSampah();
            newItemType.setName(name);
            newItemType.setDescription(description);
            newItemType.setCategoryId(selectedCategory.getId());
            itemTypeController.addItemType(newItemType);
        } else {
            itemType.setName(name);
            itemType.setDescription(description);
            itemType.setCategoryId(selectedCategory.getId());
            itemTypeController.updateItemType(itemType);
        }
        
        dispose();
    }
    
    public void setItemType(JenisSampah itemType) {
        this.itemType = itemType;
        if (itemType != null) {
            nameField.setText(itemType.getName());
            descriptionField.setText(itemType.getDescription());
            categoryComboBox.setSelectedItem(itemType.getCategory()); 
        }
    }

    public String getItemTypeName() {
        return nameField.getText();
    }

    public String getItemTypeDescription() {
        return descriptionField.getText();
    }

    public Kategori getSelectedCategory() {
        return (Kategori) categoryComboBox.getSelectedItem();
    }
    
    public void setItemTypeName(String name) {
        nameField.setText(name);
    }

    public void setItemTypeDescription(String description) {
        descriptionField.setText(description);
    }

    public void setCategoryComboBoxSelectedItem(Kategori category) {
        categoryComboBox.setSelectedItem(category);
    }

    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
}
