package tubes.ewaste.view;

import tubes.ewaste.controller.UserController;
import tubes.ewaste.controller.CategoryController;
import tubes.ewaste.controller.ItemController;
import tubes.ewaste.controller.ItemTypeController; // Tambahkan ItemTypeController
import tubes.ewaste.model.User;
import tubes.ewaste.model.Category;
import tubes.ewaste.model.Item;
import tubes.ewaste.model.ItemType; // Tambahkan model ItemType

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DashboardPanel extends JPanel {
    private final MainFrame mainFrame;
    private final UserController userController;
    private final CategoryController categoryController;
    private final ItemController itemController;
    private final ItemTypeController itemTypeController; // Tambahkan ItemTypeController

    private JTabbedPane tabbedPane;

    // Components for Users Tab
    private JTable usersTable;
    private DefaultTableModel userTableModel;
    private JButton refreshUserButton;
    private JButton updateUserButton;
    private JButton deleteUserButton;

    // Components for Categories Tab
    private JTable categoriesTable;
    private DefaultTableModel categoryTableModel;
    private JButton refreshCategoryButton;
    private JButton addCategoryButton;
    private JButton deleteCategoryButton;
    private JButton updateCategoryButton;

    // Components for Item Types Tab
    private JTable itemTypeTable;
    private DefaultTableModel itemTypeTableModel;
    private JButton refreshItemTypeButton;
    private JButton addItemTypeButton;
    private JButton deleteItemTypeButton;
    private JButton updateItemTypeButton;
    private JComboBox<Integer> categoryComboBox;

    // Components for Items Tab
    private JTable itemsTable;
    private DefaultTableModel itemsTableModel;
    private JButton refreshItemsButton;
    private JButton addItemsButton;
    private JButton deleteItemsButton;
    private JButton updateItemsButton;

    // Logout Button
    private JButton logoutButton;

    public DashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new UserController();
        this.categoryController = new CategoryController();
        this.itemController = new ItemController();
        this.itemTypeController = new ItemTypeController(); // Inisialisasi ItemTypeController

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Users Tab
        userTableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Email", "Alamat", "Tanggal Lahir", "Foto"}, 0);
        usersTable = new JTable(userTableModel);
        refreshUserButton = new JButton("Refresh Akun");
        updateUserButton = new JButton("Rubah Akun");
        deleteUserButton = new JButton("Hapus Akun");

        // Categories Tab
        categoryTableModel = new DefaultTableModel(new String[]{"ID", "Nama Kategori", "Deskripsi"}, 0);
        categoriesTable = new JTable(categoryTableModel);
        refreshCategoryButton = new JButton("Refresh Kategori");
        addCategoryButton = new JButton("Tambah Kategori");
        deleteCategoryButton = new JButton("Hapus Kategori");
        updateCategoryButton = new JButton("Rubah Kategori");

        // Item Types Tab
        itemTypeTableModel = new DefaultTableModel(new String[]{"ID", "Nama Jenis Item", "Kategori", "Deskripsi"}, 0);
        itemTypeTable = new JTable(itemTypeTableModel);
        refreshItemTypeButton = new JButton("Refresh Jenis Item");
        addItemTypeButton = new JButton("Tambah Jenis Item");
        updateItemTypeButton = new JButton("Rubah Jenis Item");
        deleteItemTypeButton = new JButton("Hapus Jenis Item");

        // Item Tab
        itemsTableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Deskripsi", "Jenis Item"}, 0);
        itemsTable = new JTable(itemsTableModel);
        refreshItemsButton = new JButton("Refresh Item");
        addItemsButton = new JButton("Tambah Item");
        updateItemsButton = new JButton("Rubah Item");
        deleteItemsButton = new JButton("Hapus Item");


        // Logout Button
        logoutButton = new JButton("Logout");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Users Tab Layout
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.add(new JScrollPane(usersTable), BorderLayout.CENTER);
        userPanel.add(createButtonPanel(refreshUserButton, updateUserButton, deleteUserButton), BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Akun", userPanel);

        // Categories Tab Layout
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.add(new JScrollPane(categoriesTable), BorderLayout.CENTER);
        categoryPanel.add(createButtonPanel(refreshCategoryButton, addCategoryButton, updateCategoryButton ,deleteCategoryButton), BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Kategori", categoryPanel);

        // Item Types Tab Layout
        JPanel itemTypePanel = new JPanel(new BorderLayout());
        itemTypePanel.add(new JScrollPane(itemTypeTable), BorderLayout.CENTER);
        itemTypePanel.add(createButtonPanel(refreshItemTypeButton, addItemTypeButton, updateItemTypeButton, deleteItemTypeButton), BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Jenis Item", itemTypePanel);

        // Items Tab Layout
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);
        itemPanel.add(createButtonPanel(refreshItemsButton, addItemsButton, updateItemsButton, deleteItemsButton), BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Item", itemPanel);

        // Add TabbedPane and Logout Button
        add(tabbedPane, BorderLayout.CENTER);
        add(logoutButton, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }
    private void loadCategories() {
        List<Category> categories = categoryController.getAllCategories();
        categoryTableModel.setRowCount(0); // Bersihkan data tabel sebelumnya
    
        for (Category category : categories) {
            categoryTableModel.addRow(new Object[]{
                category.getId(),
                category.getName(),
                category.getDescription()
            });
        }
    }
    
    private void loadItemTypes() {
        // Fetch the latest item types from the database
        List<ItemType> itemTypes = itemTypeController.getAllItemTypes();
        
        // Clear existing rows in the table model before adding new data
        itemTypeTableModel.setRowCount(0);  // This will clear all rows in the table model
        
        // List to hold added item types, checking uniqueness based on ID and Name
        Set<String> addedItemTypes = new HashSet<>();
        
        // Add new rows based on the fetched data, while avoiding duplicates
        for (ItemType itemType : itemTypes) {
            String uniqueKey = itemType.getId() + "_" + itemType.getName();  // Create a unique key based on ID and Name
            
            // Check if this unique key already exists in the added set
            if (!addedItemTypes.contains(uniqueKey)) {
                addedItemTypes.add(uniqueKey);  // Mark this item type as added
                
                // Add new row to the table model
                itemTypeTableModel.addRow(new Object[]{
                    itemType.getId(),
                    itemType.getName(),
                    itemType.getDescription(),
                    itemType.getCategory() != null ? itemType.getCategory().getName() : "Unknown Category"
                });
            }
        }
    }
    private void loadItems() {
        itemsTableModel.setRowCount(0); // Clear existing data
        List<Item> items = itemController.getAllItems();
        Set<String> addedItem = new HashSet<>();

        for(Item item : items){
            String uniqueKey = item.getId() + "_" + item.getName();
            if(!addedItem.contains(uniqueKey)){
                addedItem.add(uniqueKey);
                itemsTableModel.addRow(new Object[]{
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getItemType() != null ? item.getItemType().getName() : "Unknown Type" // Safely display Type name
                });
            }
        }
    
    }

    private void setupListeners() {
        // User Tab Listeners
        refreshUserButton.addActionListener(e -> loadUsers());
        updateUserButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Rubah Akun Belum Diimplementasikan"));
        deleteUserButton.addActionListener(e -> deleteUser());

        // Category Tab Listeners
        addCategoryButton.addActionListener(e -> {
            CategoryFormDialog dialog = new CategoryFormDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Tambah Kategori");
        
            dialog.addSaveButtonListener(event -> {
                String name = dialog.getCategoryName();
                String description = dialog.getCategoryDescription();
        
                if (!name.isEmpty() && !description.isEmpty()) {
                    Category newCategory = new Category();
                    newCategory.setName(name);
                    newCategory.setDescription(description);
        
                    categoryController.addCategory(newCategory); // Gunakan metode controller
                    loadCategories(); // Muat ulang data di tabel
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                }
            });
        
            dialog.setVisible(true);
        });
        

        updateCategoryButton.addActionListener(e -> {
            int selectedRow = categoriesTable.getSelectedRow();
            if (selectedRow != -1) {
                int categoryId = (int) categoryTableModel.getValueAt(selectedRow, 0);
                String currentName = (String) categoryTableModel.getValueAt(selectedRow, 1);
                String currentDescription = (String) categoryTableModel.getValueAt(selectedRow, 2);
        
                CategoryFormDialog dialog = new CategoryFormDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Ubah Kategori");
                dialog.setCategoryName(currentName);
                dialog.setCategoryDescription(currentDescription);
        
                dialog.addSaveButtonListener(event -> {
                    String newName = dialog.getCategoryName();
                    String newDescription = dialog.getCategoryDescription();
        
                    if (!newName.isEmpty() && !newDescription.isEmpty()) {
                        Category updatedCategory = new Category();
                        updatedCategory.setId(categoryId); // Gunakan ID untuk update
                        updatedCategory.setName(newName);
                        updatedCategory.setDescription(newDescription);
        
                        categoryController.updateCategory(updatedCategory); // Gunakan metode controller
                        loadCategories(); // Muat ulang data di tabel
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                    }
                });
        
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan pilih kategori yang ingin diubah!");
            }
        });
        
        refreshCategoryButton.addActionListener(e -> loadCategories());            
        deleteCategoryButton.addActionListener(e -> deleteCategory());
    

        // Item Type Tab Listeners
        // Tombol "Add" untuk menambahkan Item Type
        addItemTypeButton.addActionListener(e -> {
            ItemTypeFormDialog dialog = new ItemTypeFormDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                itemTypeController,
                categoryController,
                "Tambah Jenis Item"
            );

            dialog.addSaveButtonListener(event -> {
                String name = dialog.getItemTypeName();
                String description = dialog.getItemTypeDescription();
                Category selectedCategory = dialog.getSelectedCategory();

                if (!name.isEmpty() && !description.isEmpty() && selectedCategory != null) {
                    ItemType newItemType = new ItemType();
                    newItemType.setName(name);
                    newItemType.setDescription(description);
                    newItemType.setCategoryId(selectedCategory.getId());

                    itemTypeController.addItemType(newItemType);  // Gunakan metode controller
                    loadItemTypes();  // Muat ulang data di tabel
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                }
            });

            dialog.setVisible(true);
        });

        // Tombol "Update" untuk memperbarui Item Type yang dipilih
        updateItemTypeButton.addActionListener(e -> {
            int selectedRow = itemTypeTable.getSelectedRow();
            if (selectedRow != -1) {
                int itemTypeId = (int) itemTypeTableModel.getValueAt(selectedRow, 0);
                String currentName = (String) itemTypeTableModel.getValueAt(selectedRow, 1);
                String currentDescription = (String) itemTypeTableModel.getValueAt(selectedRow, 2);
                Category currentCategory = (Category) itemTypeTableModel.getValueAt(selectedRow, 3);

                ItemTypeFormDialog dialog = new ItemTypeFormDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    itemTypeController,
                    categoryController,
                    "Ubah Jenis Item"
                );

                dialog.setItemTypeName(currentName);
                dialog.setItemTypeDescription(currentDescription);
                dialog.setCategoryComboBoxSelectedItem(currentCategory);

                dialog.addSaveButtonListener(event -> {
                    String newName = dialog.getItemTypeName();
                    String newDescription = dialog.getItemTypeDescription();
                    Category selectedCategory = dialog.getSelectedCategory();

                    if (!newName.isEmpty() && !newDescription.isEmpty() && selectedCategory != null) {
                        ItemType updatedItemType = new ItemType();
                        updatedItemType.setId(itemTypeId);  // Gunakan ID untuk update
                        updatedItemType.setName(newName);
                        updatedItemType.setDescription(newDescription);
                        updatedItemType.setCategoryId(selectedCategory.getId());

                        itemTypeController.updateItemType(updatedItemType);  // Gunakan metode controller
                        loadItemTypes();  // Muat ulang data di tabel
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                    }
                });

                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan pilih jenis item yang ingin diubah!");
            }
        });

        refreshItemTypeButton.addActionListener(e -> loadItemTypes());
        deleteItemTypeButton.addActionListener(e -> deleteItemType());

        // Item Tab Listeners
        // Tombol "Add" untuk menambahkan Item
        addItemsButton.addActionListener(e -> {
            ItemFormDialog dialog = new ItemFormDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                itemController,
                itemTypeController,
                "Tambah Item"
            );

            dialog.addSaveButtonListener(event -> {
                String name = dialog.getItemName();
                String description = dialog.getItemDescription();
                ItemType selectedItemType = dialog.getSelectedItemType();

                if (!name.isEmpty() && !description.isEmpty() && selectedItemType != null) {
                    Item newItem = new Item();
                    newItem.setName(name);
                    newItem.setDescription(description);
                    newItem.setItemTypeId(selectedItemType.getId());

                    itemController.addItem(newItem); // Gunakan metode controller
                    loadItems(); // Muat ulang data di tabel
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                }
            });

            dialog.setVisible(true);
        });

        // Tombol "Update" untuk memperbarui Item yang dipilih
        updateItemsButton.addActionListener(e -> {
            int selectedRow = itemsTable.getSelectedRow();
            if (selectedRow != -1) {
                int itemId = (int) itemsTableModel.getValueAt(selectedRow, 0);
                String currentName = (String) itemsTableModel.getValueAt(selectedRow, 1);
                String currentDescription = (String) itemsTableModel.getValueAt(selectedRow, 3);
                ItemType currentItemType = (ItemType) itemsTableModel.getValueAt(selectedRow, 2);

                ItemFormDialog dialog = new ItemFormDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    itemController,
                    itemTypeController,
                    "Ubah Item"
                );

                dialog.setItemName(currentName);
                dialog.setItemDescription(currentDescription);
                dialog.setItemTypeComboBoxSelectedItem(currentItemType);

                dialog.addSaveButtonListener(event -> {
                    String newName = dialog.getItemName();
                    String newDescription = dialog.getItemDescription();
                    ItemType selectedItemType = dialog.getSelectedItemType();

                    if (!newName.isEmpty() && !newDescription.isEmpty() && selectedItemType != null) {
                        Item updatedItem = new Item();
                        updatedItem.setId(itemId); // Gunakan ID untuk update
                        updatedItem.setName(newName);
                        updatedItem.setDescription(newDescription);
                        updatedItem.setItemTypeId(selectedItemType.getId());

                        itemController.updateItem(updatedItem); // Gunakan metode controller
                        loadItems(); // Muat ulang data di tabel
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                    }
                });

                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan pilih item yang ingin diubah!");
            }
        });

        // Tombol "Refresh" untuk memuat ulang data
        refreshItemsButton.addActionListener(e -> loadItems());

        // Tombol "Delete" untuk menghapus Item yang dipilih
        deleteItemsButton.addActionListener(e -> {
            int selectedRow = itemsTable.getSelectedRow();
            if (selectedRow != -1) {
                int itemId = (int) itemsTableModel.getValueAt(selectedRow, 0);

                int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus item ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    itemController.deleteItem(itemId); // Hapus item dari database
                    loadItems(); // Muat ulang data di tabel
                }
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan pilih item yang ingin dihapus!");
            }
        });

        
        

        // Logout Listener
        logoutButton.addActionListener(e -> mainFrame.showLogin());
    }

    public void loadUsers() {
        userTableModel.setRowCount(0); // Clear existing data
        List<User> users = userController.getAllUsers();
        for (User user : users) {
            userTableModel.addRow(new Object[]{
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getBirthDate(),
                    user.getPhotoPath()
            });
        }
    }
    
    

    private void deleteUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) userTableModel.getValueAt(selectedRow, 0);
            userController.deleteUser(userId);
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih akun yang ingin dihapus!");
        }
    }

    private void deleteCategory() {
        int selectedRow = categoriesTable.getSelectedRow();
        if (selectedRow != -1) {
            int categoryId = (int) categoryTableModel.getValueAt(selectedRow, 0);
            categoryController.deleteCategory(categoryId);
            loadCategories();
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih kategori yang ingin dihapus!");
        }
    }

    private void deleteItemType() {
        int selectedRow = itemTypeTable.getSelectedRow();
        if (selectedRow != -1) {
            int itemTypeId = (int) itemTypeTableModel.getValueAt(selectedRow, 0);
            itemTypeController.deleteItemType(itemTypeId);
            loadItemTypes();
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih jenis item yang ingin dihapus!");
        }
    }

    private void deleteItem() {
        int selectedRow = itemsTable.getSelectedRow();
        if (selectedRow != -1) {
            int itemId = (int) itemsTableModel.getValueAt(selectedRow, 0);
            itemController.deleteItem(itemId);
            loadItems();
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih item yang ingin dihapus!");
        }
    }
}
