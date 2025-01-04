package tubes.ewaste.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.ItemTypeMapper;
import tubes.ewaste.model.ItemType;

import java.util.List;

public class ItemTypeController {
    private final SqlSessionFactory factory;

    public ItemTypeController() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
    }

    // Mendapatkan semua item types, termasuk kategori yang berelasi
    public List<ItemType> getAllItemTypes() {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            return mapper.getAll(); // Ambil semua item type
        }
    }

    // Mendapatkan item types berdasatkan Id
    public ItemType getItemTypesById(int id) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            return mapper.getById(id); // Pastikan `getById` ada di `ItemTypeMapper`
        }
    }

    // Mendapatkan item types berdasarkan kategori ID
    public List<ItemType> getItemTypesByCategory(int categoryId) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            return mapper.getByCategoryId(categoryId); // Ambil item type berdasarkan kategori
        }
    }

    // Menambahkan item type baru
    public void addItemType(ItemType itemType) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            
            // Check if an ItemType with the same name exists in the same category
            ItemType existingItemType = mapper.getByNameAndCategory(itemType.getName(), itemType.getCategoryId());
            
            if (existingItemType == null) {
                // Proceed with insert
                mapper.insert(itemType);
                session.commit();
                System.out.println("ItemType added and transaction committed.");
            } else {
                // Item with the same name already exists in the same category, handle it
                System.out.println("ItemType with the same name already exists in the selected category.");
            }
        }
    
        getAllItemTypes();  // Reload data to update the table
    }
    
    // Memperbarui item type yang sudah ada
    public void updateItemType(ItemType itemType) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            mapper.update(itemType);
            session.commit();
        }
    }

    // Menghapus item type berdasarkan ID
    public void deleteItemType(int itemTypeId) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            mapper.delete(itemTypeId);
            session.commit();
        }
    }
}
