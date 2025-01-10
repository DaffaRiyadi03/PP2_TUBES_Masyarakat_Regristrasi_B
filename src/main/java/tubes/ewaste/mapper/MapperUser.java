package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;

import tubes.ewaste.model.User;
import java.util.List;

public interface MapperUser {

    @Select("SELECT * FROM users")
    List<User> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User getById(Integer id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results({
        @Result(property = "email", column = "email"),
        @Result(property = "password", column = "password"),
        @Result(property = "roleId", column = "role_id"),
    })
    User getByEmail(String email);

    @Insert("INSERT INTO users(name, email, password, address, birth_date, created_at, role_id) " +
            "VALUES(#{name}, #{email}, #{password}, #{address}, #{birthDate}, #{createdAt}, COALESCE(#{roleId}, 2))")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE users SET name=#{name}, email=#{email}, " +
            "address=#{address}, birth_date=#{birthDate}, created_at=#{createdAt}, role_id=#{roleId} WHERE id=#{id}")
    void update(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(Integer id);

    @Select("SELECT password FROM users WHERE email = #{email}")
    String getPasswordByEmail(@Param("email") String email);
    

    // Update the verification status of the user
    @Update("UPDATE users SET is_verified = #{status} WHERE email = #{email}")
    void updateVerificationStatus(@Param("email") String email, @Param("status") String status);

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
        @Result(property = "email", column = "email"),
        @Result(property = "roleId", column = "role_id"),
        @Result(property = "photoPath", column = "photo_path"),
        @Result(property = "birthDate", column = "birth_date"),
    })
    User getUserById(Integer id);

    @Update("UPDATE users SET name = #{name}, email = #{email}, password = #{password}, address = #{address}, birth_date = #{birthDate}, photo_path = #{photoPath}, role_id = #{roleId} WHERE id = #{id}")
    int updateUser(User user);

      @Update("UPDATE users SET password = #{hashedPassword} WHERE id = #{userId}")
    void updatePassword(@Param("userId") Integer userId, @Param("hashedPassword") String hashedPassword);

    @Select("SELECT password FROM users WHERE id = #{userId}")
    String getPasswordById(@Param("userId") Integer userId);

    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email}")
    boolean checkEmailExists(@Param("email") String email);

   


}
