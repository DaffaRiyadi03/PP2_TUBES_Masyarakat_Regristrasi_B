package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.Kategori;

import java.util.List;

@Mapper
public interface MapperKategori {
    @Select("SELECT * FROM kategori_sampah")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description"),
    })
    List<Kategori> getAll();

    @Select("SELECT * FROM kategori_sampah WHERE name = #{name}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description"),
    })
    Kategori getCategoryByName(String name);    
    

    @Select("SELECT * FROM kategori_sampah WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description"),
    })
    Kategori getCategoryById(int id);

    @Insert("INSERT INTO kategori_sampah(name, description) VALUES(#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Kategori category);

    @Update("UPDATE kategori_sampah SET name=#{name}, description=#{description} WHERE id=#{id}")
    void update(Kategori category);

    @Delete("DELETE FROM kategori_sampah WHERE id = #{id}")
    void delete(int id);
}
