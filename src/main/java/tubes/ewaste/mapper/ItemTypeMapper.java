package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.ItemType;

import java.util.List;

public interface ItemTypeMapper {

@Select("SELECT it.*, c.name AS category_name, c.description AS category_description " +
        "FROM item_types it " +
        "LEFT JOIN categories c ON it.category_id = c.id")
@Results({
    @Result(property = "id", column = "id"),
    @Result(property = "name", column = "name"),
    @Result(property = "description", column = "description"),
    @Result(property = "category", column = "category_id", 
            one = @One(select = "tubes.ewaste.mapper.CategoryMapper.getCategoryById"))
})
List<ItemType> getAll();


@Select("SELECT it.*, c.name AS category_name, c.description AS category_description " +
        "FROM item_types it " +
        "LEFT JOIN categories c ON it.category_id = c.id WHERE it.id = #{id}")
@Results({
    @Result(property = "id", column = "id"),
    @Result(property = "name", column = "name"),
    @Result(property = "description", column = "description"),
    @Result(property = "category", column = "category_id", 
            one = @One(select = "tubes.ewaste.mapper.CategoryMapper.getCategoryById"))
})
ItemType getById(@Param("id") int id);

@Select("SELECT * FROM item_types WHERE name = #{name}")
@Results({
    @Result(property = "category", column = "category_id", 
            one = @One(select = "tubes.ewaste.mapper.CategoryMapper.getCategoryById")),
    @Result(property = "id", column = "id"),
    @Result(property = "name", column = "name"),
    @Result(property = "description", column = "description"),
})
ItemType getByName(@Param("name") String name);

@Select("SELECT * FROM item_types WHERE name = #{name} AND category_id = #{categoryId}")
ItemType getByNameAndCategory(@Param("name") String name, @Param("categoryId") int categoryId);

    @Select("SELECT * FROM item_types WHERE category_id = #{categoryId}")
    List<ItemType> getByCategoryId(int categoryId);

    @Insert("INSERT INTO item_types(name, description, category_id) VALUES(#{name}, #{description}, #{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ItemType itemType);    

    @Update("UPDATE item_types SET name=#{name}, description=#{description}, category_id=#{categoryId} WHERE id=#{id}")
    void update(ItemType itemType);

    @Delete("DELETE FROM item_types WHERE id = #{id}")
    void delete(int id);
}
