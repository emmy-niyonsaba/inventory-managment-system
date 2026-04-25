package com.airtel.ims.authentication;

import com.airtel.ims.model.User;
import com.airtel.ims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoginService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordUtils passwordUtils;
    
    @Autowired
    private SessionManager sessionManager;
    
    public boolean authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsernameAndIsActiveTrue(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Check both hashed and plain text passwords
            String storedPassword = user.getPasswordHash();
            
            // If it's a BCrypt hash, use BCrypt check
            if (storedPassword != null && storedPassword.startsWith("$2a$")) {
                if (passwordUtils.verifyPassword(password, storedPassword)) {
                    user.setLastLogin(LocalDateTime.now());
                    userRepository.save(user);
                    sessionManager.login(user);
                    return true;
                }
            } 
            // If plain text password (temporary check)
            else if (password.equals(storedPassword)) {
                // Convert to hash for next time
                user.setPasswordHash(passwordUtils.hashPassword(password));
                user.setLastLogin(LocalDateTime.now());
                userRepository.save(user);
                sessionManager.login(user);
                return true;
            }
        }
        return false;
    }
    
    public boolean createDefaultAdmin() {
        final String defaultUsername = "24RP04021";
        final String defaultPassword = "24RP01331";

        Optional<User> existingUser = userRepository.findByUsername(defaultUsername);
        User defaultUser = existingUser.orElseGet(User::new);

        defaultUser.setUsername(defaultUsername);
        defaultUser.setPasswordHash(passwordUtils.hashPassword(defaultPassword));
        defaultUser.setFullName("System Administrator");
        defaultUser.setRole("ADMIN");
        defaultUser.setDepartment("IT");
        defaultUser.setEmail("admin@airtel.com");
        defaultUser.setActive(true);

        userRepository.save(defaultUser);

        if (existingUser.isPresent()) {
            System.out.println("Default admin updated - Username: 24RP04021, Password: 24RP01331");
            return false;
        }

        System.out.println("Default admin created - Username: 24RP04021, Password: 24RP01331");
        return true;
    }
    
    public boolean createUser(String username, String password, String fullName, String department, String email, String role) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordUtils.hashPassword(password));
        user.setFullName(fullName);
        user.setDepartment(department);
        user.setEmail(email);
        user.setRole(role);
        user.setActive(true);
        
        userRepository.save(user);
        return true;
    }
    
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordUtils.verifyPassword(oldPassword, user.getPasswordHash())) {
                if (passwordUtils.isPasswordStrong(newPassword)) {
                    user.setPasswordHash(passwordUtils.hashPassword(newPassword));
                    userRepository.save(user);
                    return true;
                }
            }
        }
        return false;
    }
    
    public void logout() {
        sessionManager.logout();
    }
}