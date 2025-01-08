package tubes.ewaste.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.MapperJenisSampah;
import tubes.ewaste.model.JenisSampah;

import java.util.List;

public class ControllerJenisSampah {
    private final SqlSessionFactory factory;

    public ControllerJenisSampah() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
    }

    public List<JenisSampah> getAllItemTypes() {
        try (SqlSession session = factory.openSession()) {
            MapperJenisSampah mapper = session.getMapper(MapperJenisSampah.class);
            return mapper.getAll();
        }
    }

    public JenisSampah getItemTypesById(int id) {
        try (SqlSession session = factory.openSession()) {
            MapperJenisSampah mapper = session.getMapper(MapperJenisSampah.class);
            return mapper.getById(id); 
        }
    }

    public List<JenisSampah> getItemTypesByCategory(int categoryId) {
        try (SqlSession session = factory.openSession()) {
            MapperJenisSampah mapper = session.getMapper(MapperJenisSampah.class);
            return mapper.getByCategoryId(categoryId); 
        }
    }

    public void addItemType(JenisSampah itemType) {
        try (SqlSession session = factory.openSession()) {
            MapperJenisSampah mapper = session.getMapper(MapperJenisSampah.class);
            
            JenisSampah existingItemType = mapper.getByNameAndCategory(itemType.getName(), itemType.getCategoryId());
            
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
    
    public void updateItemType(JenisSampah itemType) {
        try (SqlSession session = factory.openSession()) {
            MapperJenisSampah mapper = session.getMapper(MapperJenisSampah.class);
            mapper.update(itemType);
            session.commit();
        }
    }

    public void deleteItemType(int itemTypeId) {
        try (SqlSession session = factory.openSession()) {
            MapperJenisSampah mapper = session.getMapper(MapperJenisSampah.class);
            mapper.delete(itemTypeId);
            session.commit();
        }
    }

    public List<JenisSampah> getByCategoryId(int categoryId) {
        try (SqlSession session = factory.openSession()) {
            MapperJenisSampah mapper = session.getMapper(MapperJenisSampah.class);
            return mapper.getByCategoryId(categoryId); // Ambil item type berdasarkan kategori
        }
    }
}
