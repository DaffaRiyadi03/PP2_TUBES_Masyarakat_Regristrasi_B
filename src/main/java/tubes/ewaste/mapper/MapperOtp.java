package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.Otp;

public interface MapperOtp {
    
    // Menyimpan OTP ke dalam database
    @Insert("INSERT INTO otp (email, otp_code, expires_at, status) VALUES (#{email}, #{otpCode}, #{expiresAt}, #{status})")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "email", column = "email"),
        @Result(property = "otpCode", column = "otp_code"),
        @Result(property = "expiresAt", column = "expires_at"),
        @Result(property = "status", column = "status"),
        @Result(property = "createdAt", column = "created_at")
    })
    void insert(Otp otp);
    

    // Mencari OTP yang aktif berdasarkan email
    @Select("SELECT * FROM otp WHERE email = #{email} AND status = 'ACTIVE'")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "email", column = "email"),
        @Result(property = "otpCode", column = "otp_code"),
        @Result(property = "expiresAt", column = "expires_at"),
        @Result(property = "status", column = "status"),
        @Result(property = "createdAt", column = "created_at")
    })
    Otp findActiveOtpByEmail(@Param("email") String email);
    

    // Mengupdate status OTP setelah digunakan
    @Update("UPDATE otp SET status = #{status} WHERE id = #{id}")
    void updateStatus(@Param("id") Integer id, @Param("status") String status);
}
