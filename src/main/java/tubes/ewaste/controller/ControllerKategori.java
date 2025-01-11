package tubes.ewaste.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.MapperKategori;
import tubes.ewaste.model.Kategori;

import java.util.List;

public class ControllerKategori {
    private final SqlSessionFactory factory;

    public ControllerKategori() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
    }

    public List<Kategori> getAllCategories() {
        try (SqlSession session = factory.openSession()) {
            MapperKategori mapper = session.getMapper(MapperKategori.class);
            return mapper.getAll();
        }
    }

    public Kategori getCategoryById(int id) {
        try (SqlSession session = factory.openSession()) {
            MapperKategori mapper = session.getMapper(MapperKategori.class);
            return mapper.getCategoryById(id); // Call the mapper method
        }
    }
    
    public void addCategory(Kategori category) {
        try (SqlSession session = factory.openSession()) {
            MapperKategori mapper = session.getMapper(MapperKategori.class);
            mapper.insert(category);
            session.commit();
        }
    }

    public void updateCategory(Kategori category) {
        try (SqlSession session = factory.openSession()) {
            MapperKategori mapper = session.getMapper(MapperKategori.class);
            mapper.update(category);
            session.commit();
        }
    }

    public void deleteCategory(int categoryId) {
        try (SqlSession session = factory.openSession()) {
            MapperKategori mapper = session.getMapper(MapperKategori.class);
            mapper.delete(categoryId);
            session.commit();
        }
    }
    public Kategori getCategoryByName(String categoryName) {
        try (SqlSession session = factory.openSession()) {
            MapperKategori mapper = session.getMapper(MapperKategori.class);
            List<Kategori> allCategories = mapper.getAll(); // Ambil semua kategori dari database

            // Loop untuk mencari kategori berdasarkan nama
            for (Kategori category : allCategories) {
                if (category.getName().equals(categoryName)) {
                    return category;  // Kategori ditemukan
                }
            }
            return null;  // Kategori tidak ditemukan
        }
    }

    
}
