package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.JenisSampah;

import java.util.List;

@Mapper
public interface MapperJenisSampah {
    @Select("SELECT it.*, c.name AS category_name, c.description AS category_description " +
            "FROM jenis_sampah it " +
            "LEFT JOIN kategori_sampah c ON it.category_id = c.id")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description"),
        @Result(property = "category", column = "category_id", 
                one = @One(select = "tubes.ewaste.mapper.MapperKategori.getCategoryById"))
    })
    List<JenisSampah> getAll();


@Select("SELECT it.*, c.name AS category_name, c.description AS category_description " +
        "FROM jenis_sampah it " +
        "LEFT JOIN kategori_sampah c ON it.category_id = c.id WHERE it.id = #{id}")
@Results({
    @Result(property = "id", column = "id"),
    @Result(property = "name", column = "name"),
    @Result(property = "description", column = "description"),
    @Result(property = "category", column = "category_id", 
            one = @One(select = "tubes.ewaste.mapper.MapperKategori.getCategoryById"))
})
JenisSampah getById(@Param("id") int id);

@Select("SELECT * FROM jenis_sampah WHERE name = #{name}")
@Results({
    @Result(property = "category", column = "category_id", 
            one = @One(select = "tubes.ewaste.mapper.MapperKategori.getCategoryById")),
    @Result(property = "id", column = "id"),
    @Result(property = "name", column = "name"),
    @Result(property = "description", column = "description"),
})
JenisSampah getByName(@Param("name") String name);

@Select("SELECT * FROM jenis_sampah WHERE name = #{name} AND category_id = #{categoryId}")
JenisSampah getByNameAndCategory(@Param("name") String name, @Param("categoryId") int categoryId);

    @Select("SELECT * FROM jenis_sampah WHERE category_id = #{categoryId}")
    List<JenisSampah> getByCategoryId(int categoryId);

    @Insert("INSERT INTO jenis_sampah(name, description, category_id) VALUES(#{name}, #{description}, #{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(JenisSampah itemType);    

    @Update("UPDATE jenis_sampah SET name=#{name}, description=#{description}, category_id=#{categoryId} WHERE id=#{id}")
    void update(JenisSampah itemType);

    @Delete("DELETE FROM jenis_sampah WHERE id = #{id}")
    void delete(int id);
}
