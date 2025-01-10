package tubes.ewaste.view;

import tubes.ewaste.controller.ControllerKategori;
import tubes.ewaste.controller.ControllerJenisSampah;
import tubes.ewaste.model.Kategori;
import tubes.ewaste.model.JenisSampah;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class HalamanUtama extends JPanel {

    private final MainFrame mainFrame;
    private final ControllerKategori categoryController;
    private final ControllerJenisSampah itemTypeController;

    private JPanel navbar;
    private JPanel categoryPanel;
    private JTable itemTypeTable;
    private DefaultTableModel itemTypeTableModel;
    private JLabel imageLabel;

    public HalamanUtama(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.categoryController = new ControllerKategori();
        this.itemTypeController = new ControllerJenisSampah();

        initComponents();
        setupLayout();
        loadCategories();
    }

    private void initComponents() {
        navbar = createNavbar();
        categoryPanel = createCategoryPanel();
        itemTypeTableModel = new DefaultTableModel(new String[]{"Nama", "Deskripsi", "Gambar"}, 0);
        itemTypeTable = new JTable(itemTypeTableModel);
        itemTypeTable.getColumnModel().getColumn(2).setCellRenderer(new ImageRenderer()); //set cell renderer untuk gambar
        imageLabel = new JLabel();
    }
    
    // Memisahkan pembuatan navbar
    private JPanel createNavbar() {
        JPanel navbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        JButton profileButton = new JButton("Profil");

        // Tambahkan ActionListener untuk tombol Profil
        profileButton.addActionListener(e -> mainFrame.showProfile());

        // Logout Button ActionListener
        logoutButton.addActionListener(e -> mainFrame.showLogin());
    
        navbar.add(profileButton);
        navbar.add(logoutButton);
    
        return navbar;
    }

    // Memisahkan pembuatan panel kategori
    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        return categoryPanel;
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(navbar, BorderLayout.NORTH);

        // Membagi panel utama jadi 2
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(categoryPanel), createItemPanel());

        // Atur ukuran split pane
        splitPane.setResizeWeight(0.2); // Proporsi lebar untuk kategori panel
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createItemPanel() {
        JPanel itemPanel = new JPanel(new BorderLayout());

        // Wrap tabel dan gambar dalam panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(itemTypeTable), BorderLayout.CENTER);
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        
        // letakan panel gambar dan tabel ke splitpane
        JSplitPane itemSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, imagePanel);
        itemSplitPane.setResizeWeight(0.7); // Proporsi tinggi untuk tabel
        itemPanel.add(itemSplitPane, BorderLayout.CENTER);
        
        return itemPanel;
    }


    private void loadCategories() {
        List<Kategori> categories = categoryController.getAllCategories();
        for (Kategori category : categories) {
            JButton categoryButton = new JButton(category.getName());
            categoryButton.addActionListener(e -> loadItemTypes(category.getId()));
            categoryPanel.add(categoryButton);
        }
    }

     //  loadItemTypes
     private void loadItemTypes(int categoryId) {
        List<JenisSampah> itemTypes = itemTypeController.getByCategoryId(categoryId);
        itemTypeTableModel.setRowCount(0); // Clear table
        if (itemTypes == null || itemTypes.isEmpty()) {
            imageLabel.setIcon(null);
            imageLabel.setText("No Item in This Category");
        return;
        }
        for (JenisSampah itemType : itemTypes) {
            String imageUrl = itemType.getImageUrl();
            ImageIcon imageIcon = null;
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    URL url = new URL(imageUrl);
                    BufferedImage image = ImageIO.read(url);
                if (image != null) {
                    // Scale the image to fit within a specific size while maintaining aspect ratio
                    int maxWidth = 100;  // Maximum width of the image
                    int maxHeight = 100; // Maximum height of the image

                    int originalWidth = image.getWidth();
                    int originalHeight = image.getHeight();
                    
                    double scaleRatio = Math.min((double) maxWidth / originalWidth, (double) maxHeight / originalHeight);
                    
                    int scaledWidth = (int) (originalWidth * scaleRatio);
                    int scaledHeight = (int) (originalHeight * scaleRatio);
                    
                     Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                    imageIcon = new ImageIcon(scaledImage);
                }
            } catch (IOException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
         }
          itemTypeTableModel.addRow(new Object[]{itemType.getName(), itemType.getDescription(), imageIcon});
    }
    if (itemTypes.isEmpty()){
         imageLabel.setIcon(null);
         imageLabel.setText("No Image Selected");
    }
}

    // custom cell renderer
    private static class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            if (value instanceof ImageIcon) {
                label.setIcon((ImageIcon) value);
            }
            else if(value instanceof String){
                label.setText((String) value);
            } else {
                label.setText("");
            }
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        }
    }
}