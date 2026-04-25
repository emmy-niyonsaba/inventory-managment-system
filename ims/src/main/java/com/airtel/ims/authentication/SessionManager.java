package com.airtel.ims.authentication;

import com.airtel.ims.model.User;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    
    private User currentUser;
    private static SessionManager instance;
    
    public SessionManager() {
        instance = this;
    }
    
    public static SessionManager getInstance() {
        return instance;
    }
    
    public void login(User user) {
        this.currentUser = user;
        System.out.println("User logged in: " + user.getUsername() + " (" + user.getRole() + ")");
    }
    
    public void logout() {
        if (currentUser != null) {
            System.out.println("User logged out: " + currentUser.getUsername());
        }
        this.currentUser = null;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean hasRole(String role) {
        return currentUser != null && currentUser.getRole() != null && currentUser.getRole().equals(role);
    }
    
    public boolean isAdmin() {
        return hasRole("ADMIN");
    }
    
    public boolean isManager() {
        return hasRole("MANAGER") || isAdmin();
    }
    
    public String getCurrentUsername() {
        return currentUser != null ? currentUser.getUsername() : null;
    }
    
    public String getCurrentUserFullName() {
        return currentUser != null ? currentUser.getFullName() : null;
    }
}