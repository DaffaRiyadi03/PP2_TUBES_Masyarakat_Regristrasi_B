package tubes.ewaste.controller;

import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.MapperOtp;
import tubes.ewaste.mapper.MapperUser;
import tubes.ewaste.model.Otp;
import tubes.ewaste.model.User;
import tubes.ewaste.service.MailService;
import org.mindrot.jbcrypt.BCrypt;

public class ControllerUser {
    private final SqlSessionFactory factory;
    private final MailService mailService;

    public ControllerUser() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
        this.mailService = new MailService(); 
    }

    public boolean login(String email, String password) {
        try (SqlSession session = factory.openSession()) {
            MapperUser userMapper = session.getMapper(MapperUser.class);
    
            // Fetch the hashed password from the database
            String hashedPassword = userMapper.getPasswordByEmail(email);
    
            // If no user is found, return false
            if (hashedPassword == null) {
                return false;
            }
    
            // Use BCrypt to compare the plaintext password with the hashed password
            return BCrypt.checkpw(password, hashedPassword);
        }
    }
    

    public void register(User user) throws Exception {
        try (SqlSession session = factory.openSession()) {
            MapperUser userMapper = session.getMapper(MapperUser.class);
            MapperOtp otpMapper = session.getMapper(MapperOtp.class);
    
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
            user.setCreatedAt(LocalDateTime.now());
    
            userMapper.insert(user);
    
            String otpCode = generateOtp();
            Otp otp = new Otp();
            otp.setEmail(user.getEmail());
            otp.setOtpCode(otpCode);
            otp.setExpiresAt(LocalDateTime.now().plusMinutes(60)); 
            otp.setStatus("ACTIVE");
            otpMapper.insert(otp);

            session.commit();
    
            mailService.sendOtpEmail(user.getEmail(), otpCode);
        }
    }
    
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); 
        return String.valueOf(otp);
    }
    

    public boolean verifyOtp(String email, String otpCode) {
        try (SqlSession session = factory.openSession()) {
            MapperOtp otpMapper = session.getMapper(MapperOtp.class);
            MapperUser userMapper = session.getMapper(MapperUser.class);
    
            // Fetch active OTP
            Otp otp = otpMapper.findActiveOtpByEmail(email);
    
            if (otp == null) {
                System.out.println("No active OTP found for email: " + email);
                return false;
            }
    
            // Check expiration
            if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
                System.out.println("OTP has expired for email: " + email);
                return false;
            }
    
            // Verify OTP code
            if (otp.getOtpCode().equals(otpCode)) {
                // Update OTP status
                otpMapper.updateStatus(otp.getId(), "USED");
    
                // Update user verification status
                userMapper.updateVerificationStatus(email, "YES");
    
                // Commit all changes
                session.commit();
    
                System.out.println("OTP verified successfully for email: " + email);
                return true;
            }
    
            System.out.println("Invalid OTP for email: " + email);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to verify OTP. Please try again.");
        }
    }
    
    

    public User findUserByEmail(String email) {
        try (SqlSession session = factory.openSession()) {
            MapperUser mapper = session.getMapper(MapperUser.class);
            return mapper.getByEmail(email);
        }
    }

    public void updateProfile(User user) {
        try (SqlSession session = factory.openSession()) {
            MapperUser mapper = session.getMapper(MapperUser.class);
            mapper.update(user);
            session.commit();
        }
    }

    public List<User> getAllUsers() {
        try (SqlSession session = factory.openSession()) {
            MapperUser mapper = session.getMapper(MapperUser.class);
            return mapper.getAll();
        }
    }

    public void deleteUser(int userId) {
        try (SqlSession session = factory.openSession()) {
            MapperUser mapper = session.getMapper(MapperUser.class);
            mapper.delete(userId);
            session.commit();
        }
    }

    public User getUserById(Integer userId) {
        try (SqlSession session = factory.openSession()) {
            MapperUser mapper = session.getMapper(MapperUser.class);
            return mapper.getUserById(userId);
        }
    }

    
    public boolean updateUser(User user) {
        try (SqlSession session = factory.openSession()) {
            MapperUser userMapper = session.getMapper(MapperUser.class);
            int rowsAffected = userMapper.updateUser(user); // Pastikan metode update di UserMapper benar
            session.commit(); // Commit perubahan jika update berhasil
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Mengembalikan false jika terjadi error
        }
    }
      

}
