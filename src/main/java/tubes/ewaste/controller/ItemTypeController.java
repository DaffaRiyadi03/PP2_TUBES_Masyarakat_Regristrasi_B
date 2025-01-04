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

    public List<ItemType> getAllItemTypes() {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            return mapper.getAll();
        }
    }

    public ItemType getItemTypesById(int id) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            return mapper.getById(id); 
        }
    }

    public List<ItemType> getItemTypesByCategory(int categoryId) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            return mapper.getByCategoryId(categoryId); 
        }
    }

    public void addItemType(ItemType itemType) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            
            ItemType existingItemType = mapper.getByNameAndCategory(itemType.getName(), itemType.getCategoryId());
            
            if (existingItemType == null) {
                mapper.insert(itemType);
                session.commit();
                System.out.println("ItemType added and transaction committed.");
            } else {
                System.out.println("ItemType with the same name already exists in the selected category.");
            }
        }
    
        getAllItemTypes(); 
    }
    
    public void updateItemType(ItemType itemType) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            mapper.update(itemType);
            session.commit();
        }
    }

    public void deleteItemType(int itemTypeId) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            mapper.delete(itemTypeId);
            session.commit();
        }
    }

    public List<ItemType> getByCategoryId(int categoryId) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            return mapper.getByCategoryId(categoryId); // Ambil item type berdasarkan kategori
        }
    }
}
