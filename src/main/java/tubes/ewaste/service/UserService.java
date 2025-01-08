package tubes.ewaste.service;

import tubes.ewaste.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, User> userDatabase = new HashMap<>();

    public void saveUser(User user) {
        userDatabase.put(user.getEmail(), user);
    }

    public User findUserByEmail(String email) {
        return userDatabase.get(email);
    }

    public String changePassword(String email, String currentPassword, String newPassword, String confirmPassword) {
        User user = findUserByEmail(email);

        if (user == null) {
            return "Pengguna tidak ditemukan.";
        }

        if (!user.getPassword().equals(currentPassword)) {
            return "Password saat ini salah.";
        }

        if (!newPassword.equals(confirmPassword)) {
            return "Password baru dan konfirmasi tidak cocok.";
        }

        user.setPassword(newPassword);
        saveUser(user);
        return "Password berhasil diubah.";
    }
}
