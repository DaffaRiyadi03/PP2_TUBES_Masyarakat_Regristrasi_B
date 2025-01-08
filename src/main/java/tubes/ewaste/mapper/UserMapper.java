package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.User;
import java.util.List;

public interface UserMapper {

    @Select("SELECT * FROM users")
    List<User> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User getById(Integer id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "email", column = "email"),
        @Result(property = "password", column = "password"),
        @Result(property = "address", column = "address"),
        @Result(property = "birthDate", column = "birth_date"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "roleId", column = "role_id"),
        @Result(property = "otp", column = "otp"),
        @Result(property = "otpExpiry", column = "otp_expiry"),
        @Result(property = "isUsed", column = "is_used"),
        @Result(property = "verified", column = "is_verified"),
        @Result(property = "photoPath", column = "photo_path")
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

    @Select("SELECT COUNT(*) FROM users WHERE email = #{email} AND password = #{password}")
    int validateLogin(@Param("email") String email, @Param("password") String password);

    // Update the verification status of the user
    @Update("UPDATE users SET is_verified = #{status} WHERE email = #{email}")
    void updateVerificationStatus(@Param("email") String email, @Param("status") String status);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUserById(Integer id);

    @Update("UPDATE users SET name = #{name}, address = #{address}, birth_date = #{birthDate}, photo_path = #{photoPath} WHERE id = #{id}")
    int updateUser(User user);

}
