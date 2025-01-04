package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.Category;

import java.util.List;

public interface CategoryMapper {
    @Select("SELECT * FROM categories")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description"),
    })
    List<Category> getAll();

    @Select("SELECT * FROM categories WHERE name = #{name}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description"),
    })
    Category getCategoryByName(String name);    
    

    @Select("SELECT * FROM categories WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description"),
    })
    Category getCategoryById(int id);

    @Insert("INSERT INTO categories(name, description) VALUES(#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Category category);

    @Update("UPDATE categories SET name=#{name}, description=#{description} WHERE id=#{id}")
    void update(Category category);

    @Delete("DELETE FROM categories WHERE id = #{id}")
    void delete(int id);
}
