package tubes.ewaste.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;

public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String address;
    private LocalDate birthDate;  // Menggunakan LocalDate untuk birthDate
    private LocalDateTime createdAt;  // Menggunakan LocalDateTime untuk createdAt
    private Integer roleId;  
    private String otp;
    private LocalDateTime otpExpiry;
    private Boolean isUsed;
    private Boolean verified;
    private String photoPath;

    public User() {
        // Default constructor
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getter dan Setter untuk semua field
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(LocalDateTime otpExpiry) {
        this.otpExpiry = otpExpiry;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    // Validate password (using BCrypt for secure password comparison)
    public boolean validatePassword(String inputPassword) {
        if (this.password == null || inputPassword == null) {
            return false; // Invalid input
        }
        return BCrypt.checkpw(inputPassword, this.password);  // BCrypt to verify password
    }

    // Update password (hash new password using BCrypt)
    public void updatePassword(String newPassword) {
        if (newPassword != null && !newPassword.isEmpty()) {
            this.password = BCrypt.hashpw(newPassword, BCrypt.gensalt());  // Hash the new password
        }
    }

    // Generate OTP
    public void generateOtp(String otp, int expiryMinutes) {
        this.otp = otp;
        this.otpExpiry = LocalDateTime.now().plusMinutes(expiryMinutes);
        this.isUsed = false; // Mark OTP as unused
    }

    // Validate OTP
    public boolean validateOtp(String inputOtp) {
        if (this.otp == null || inputOtp == null || this.isUsed == null || this.isUsed) {
            return false;
        }
        return inputOtp.equals(this.otp) && LocalDateTime.now().isBefore(this.otpExpiry);
    }

    // Mark OTP as used
    public void markOtpAsUsed() {
        this.isUsed = true;
    }

    // Check if the account is verified
    public boolean isAccountVerified() {
        return Boolean.TRUE.equals(this.verified);
    }
}
