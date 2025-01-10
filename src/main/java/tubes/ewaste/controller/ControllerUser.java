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
            otp.setExpiresAt(LocalDateTime.now().plusMinutes(60));  // OTP valid for 60 minutes
            otp.setStatus("ACTIVE");
            otpMapper.insert(otp);

            session.commit();
    
            mailService.sendOtpEmail(user.getEmail(), otpCode);  // Send OTP to user's email
        }
    }

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);  // Generate a 6-digit OTP
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
                // If OTP expired, update its status to expired
                otpMapper.updateStatus(otp.getId(), "EXPIRED");
                session.commit();  // Commit the expired OTP status
                System.out.println("OTP has expired for email: " + email);
                return false;
            }
    
            // Verify OTP code
            if (otp.getOtpCode().equals(otpCode)) {
                // Update OTP status to 'USED'
                otpMapper.updateStatus(otp.getId(), "USED");
    
                // Update user verification status
                userMapper.updateVerificationStatus(email, "YES");
    
                // Commit changes to both OTP status and user verification status
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

    public void saveOtpToDatabase(String email, String otpCode) {
        try (SqlSession session = factory.openSession()) {
            MapperOtp otpMapper = session.getMapper(MapperOtp.class);
            
            Otp otp = new Otp();
            otp.setEmail(email);
            otp.setOtpCode(otpCode);
            otp.setExpiresAt(LocalDateTime.now().plusMinutes(60)); // OTP valid selama 60 menit
            otp.setStatus("ACTIVE");
            
            otpMapper.insert(otp);
             session.commit();
        } catch (Exception e) {
            e.printStackTrace();
             throw new RuntimeException("Gagal menyimpan OTP ke database. Silakan coba lagi.");
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

    // Updated method to handle user update
    public boolean updateUser(User user) {
        try (SqlSession session = factory.openSession()) {
            MapperUser userMapper = session.getMapper(MapperUser.class);
            int rowsAffected = userMapper.updateUser(user);
            
            if (rowsAffected > 0) {
                session.commit(); // Commit changes only if update is successful
                return true;
            }
            return false; // Return false if no rows were affected
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if an error occurs
        }
    }

    public boolean resetPassword(String email, String newPassword) {
        try (SqlSession session = factory.openSession()) {
            MapperUser userMapper = session.getMapper(MapperUser.class);
            User user = userMapper.getByEmail(email);
            
            if (user != null) {
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                userMapper.updatePassword(user.getId(), hashedPassword);
                session.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updatePasswordById(Integer userId, String newPassword) {
        try (SqlSession session = factory.openSession()) {
          MapperUser userMapper = session.getMapper(MapperUser.class);
             String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
           userMapper.updatePassword(userId, hashedPassword);
           session.commit();
       } catch (Exception e) {
           e.printStackTrace();
            throw new RuntimeException("Gagal mengubah password. Silakan coba lagi.");
       }
   }

    public String getPasswordById(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPasswordById'");
    }

    public boolean checkEmailExists(String email) {
        try (SqlSession session = factory.openSession()) {
            MapperUser userMapper = session.getMapper(MapperUser.class);
            return userMapper.checkEmailExists(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal memeriksa email. Silakan coba lagi.");
        }
    }

    public boolean checkPassword(String plaintextPassword, String hashedPassword) {
        return BCrypt.checkpw(plaintextPassword, hashedPassword);
    }
    
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}

