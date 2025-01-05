package tubes.ewaste.controller;

import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.OtpMapper;
import tubes.ewaste.mapper.UserMapper;
import tubes.ewaste.model.Otp;
import tubes.ewaste.model.User;
import tubes.ewaste.service.MailService;
import org.mindrot.jbcrypt.BCrypt;

public class UserController {
    private final SqlSessionFactory factory;
    private final MailService mailService;

    public UserController() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
        this.mailService = new MailService(); 
    }

    public boolean login(String email, String password) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.validateLogin(email, password) > 0;
        }
    }

    public void register(User user) throws Exception {
        try (SqlSession session = factory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            OtpMapper otpMapper = session.getMapper(OtpMapper.class);
    
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
            OtpMapper otpMapper = session.getMapper(OtpMapper.class);
            UserMapper userMapper = session.getMapper(UserMapper.class);
    
            Otp otp = otpMapper.findActiveOtpByEmail(email);
            if (otp == null || otp.getExpiresAt().isBefore(LocalDateTime.now())) {
                return false; 
            }
    
            if (otp.getOtpCode().equals(otpCode)) {
                otpMapper.updateStatus(otp.getId(), "USED");
                session.commit();
    
                userMapper.updateVerificationStatus(email, "YES");
                session.commit();
    
                return true;
            }
    
            return false;
        }
    }

    public User findUserByEmail(String email) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.getByEmail(email);
        }
    }

    public void updateProfile(User user) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.update(user);
            session.commit();
        }
    }

    public List<User> getAllUsers() {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.getAll();
        }
    }

    public void deleteUser(int userId) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.delete(userId);
            session.commit();
        }
    }

    public User getUserById(Integer userId) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.getUserById(userId);
        }
    }

    
    public boolean updateUser(User user) {
        try (SqlSession session = factory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            int rowsAffected = userMapper.updateUser(user); // Pastikan metode update di UserMapper benar
            session.commit(); // Commit perubahan jika update berhasil
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Mengembalikan false jika terjadi error
        }
    }
      

}
