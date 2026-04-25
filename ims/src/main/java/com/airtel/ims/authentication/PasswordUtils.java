package com.airtel.ims.authentication;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {
    
    // Hash a password using BCrypt
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }
    
    // Verify a plain password against a hashed password
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    
    // Check if password meets security requirements
    public boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        // At least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        // At least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        return true;
    }
}