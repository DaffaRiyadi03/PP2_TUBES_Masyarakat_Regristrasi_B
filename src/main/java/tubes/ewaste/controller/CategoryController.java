package tubes.ewaste.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.CategoryMapper;
import tubes.ewaste.model.Category;

import java.util.List;

public class CategoryController {
    private final SqlSessionFactory factory;

    public CategoryController() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
    }

    public List<Category> getAllCategories() {
        try (SqlSession session = factory.openSession()) {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            return mapper.getAll();
        }
    }

    public Category getCategoryById(int id) {
        try (SqlSession session = factory.openSession()) {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            return mapper.getCategoryById(id); // Call the mapper method
        }
    }
    
    public void addCategory(Category category) {
        try (SqlSession session = factory.openSession()) {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            mapper.insert(category);
            session.commit();
        }
    }

    public void updateCategory(Category category) {
        try (SqlSession session = factory.openSession()) {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            mapper.update(category);
            session.commit();
        }
    }

    public void deleteCategory(int categoryId) {
        try (SqlSession session = factory.openSession()) {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            mapper.delete(categoryId);
            session.commit();
        }
    }
    public Category getCategoryByName(String categoryName) {
        try (SqlSession session = factory.openSession()) {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            List<Category> allCategories = mapper.getAll(); // Ambil semua kategori dari database

            // Loop untuk mencari kategori berdasarkan nama
            for (Category category : allCategories) {
                if (category.getName().equals(categoryName)) {
                    return category;  // Kategori ditemukan
                }
            }
            return null;  // Kategori tidak ditemukan
        }
    }

    
}
