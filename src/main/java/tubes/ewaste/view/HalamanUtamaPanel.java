package tubes.ewaste.view;

import tubes.ewaste.controller.CategoryController;
import tubes.ewaste.controller.ItemTypeController;
import tubes.ewaste.model.Category;
import tubes.ewaste.model.ItemType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HalamanUtamaPanel extends JPanel {
    private final MainFrame mainFrame; // Tambahkan variabel instance untuk mainFrame
    private final CategoryController categoryController;
    private final ItemTypeController itemTypeController;

    private JPanel navbar;
    private JPanel categoryPanel;
    private JTable itemTypeTable;
    private DefaultTableModel itemTypeTableModel;

    public HalamanUtamaPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Simpan mainFrame yang diteruskan melalui konstruktor
        this.categoryController = new CategoryController();
        this.itemTypeController = new ItemTypeController();

        initComponents();
        setupLayout();
        loadCategories();
    }

    private void initComponents() {
        navbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        JButton profileButton = new JButton("Profil");
    
        // Tambahkan ActionListener untuk tombol Profil
        profileButton.addActionListener(e -> mainFrame.showProfile());
    
        navbar.add(profileButton);
        navbar.add(logoutButton);
    
        categoryPanel = new JPanel(new GridLayout(0, 1, 5, 5));
    
        itemTypeTableModel = new DefaultTableModel(new String[]{"Nama", "Deskripsi"}, 0);
        itemTypeTable = new JTable(itemTypeTableModel);
    
        // Logout Button ActionListener
        logoutButton.addActionListener(e -> mainFrame.showLogin());
    }
    

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(navbar, BorderLayout.NORTH);
        add(new JScrollPane(categoryPanel), BorderLayout.WEST);
        add(new JScrollPane(itemTypeTable), BorderLayout.CENTER);
    }

    private void loadCategories() {
        List<Category> categories = categoryController.getAllCategories();
        for (Category category : categories) {
            JButton categoryButton = new JButton(category.getName());
            categoryButton.addActionListener(e -> loadItemTypes(category.getId()));
            categoryPanel.add(categoryButton);
        }
    }

    private void loadItemTypes(int categoryId) {
        List<ItemType> itemTypes = itemTypeController.getByCategoryId(categoryId);
        itemTypeTableModel.setRowCount(0); // Clear table
        for (ItemType itemType : itemTypes) {
            itemTypeTableModel.addRow(new Object[]{itemType.getName(), itemType.getDescription()});
        }
    }
}
