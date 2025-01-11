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
    
    // Mencari OTP yang aktif berdasarkan email, diurutkan berdasarkan created_at terbaru
    @Select("SELECT * FROM otp WHERE email = #{email} AND status = 'ACTIVE' ORDER BY created_at DESC LIMIT 1")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "email", column = "email"),
        @Result(property = "otpCode", column = "otp_code"),
        @Result(property = "expiresAt", column = "expires_at"),
        @Result(property = "status", column = "status"),
        @Result(property = "createdAt", column = "created_at")
    })
    Otp findActiveOtpByEmail(@Param("email") String email);
     // Mencari OTP yang aktif berdasarkan email, diurutkan berdasarkan created_at terbaru
    @Select("SELECT * FROM otp WHERE email = #{email} AND status = 'ACTIVE' ORDER BY created_at DESC LIMIT 1")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "email", column = "email"),
        @Result(property = "otpCode", column = "otp_code"),
        @Result(property = "expiresAt", column = "expires_at"),
        @Result(property = "status", column = "status"),
        @Result(property = "createdAt", column = "created_at")
    })
    Otp findActiveOtpByEmailResetPassword(@Param("email") String email);

    
    // Mengupdate status OTP setelah digunakan
    @Update("UPDATE otp SET status = #{status} WHERE id = #{id}")
    void updateStatus(@Param("id") Integer id, @Param("status") String status);
    
    // Validasi OTP berdasarkan email dan kode OTP
    @Select("SELECT * FROM otp WHERE email = #{email} AND otp_code = #{otpCode} AND status = 'ACTIVE'")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "email", column = "email"),
        @Result(property = "otpCode", column = "otp_code"),
        @Result(property = "expiresAt", column = "expires_at"),
        @Result(property = "status", column = "status"),
        @Result(property = "createdAt", column = "created_at")
    })
    Otp validateOtp(@Param("email") String email, @Param("otpCode") String otpCode);
}