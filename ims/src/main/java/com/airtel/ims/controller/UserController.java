package com.airtel.ims.controller;

import com.airtel.ims.authentication.LoginService;
import com.airtel.ims.authentication.SessionManager;
import com.airtel.ims.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private MainController mainController;
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private SessionManager sessionManager;
    
    @GetMapping
    public String listUsers(Model model) {
        if (!sessionManager.isLoggedIn() || !sessionManager.isAdmin()) {
            return "redirect:/dashboard";
        }
        
        List<User> users = mainController.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        return "users-list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        if (!sessionManager.isLoggedIn() || !sessionManager.isAdmin()) {
            return "redirect:/dashboard";
        }
        
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        return "user-form";
    }
    
    @PostMapping("/add")
    public String addUser(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String fullName,
                          @RequestParam(required = false) String department,
                          @RequestParam(required = false) String email,
                          @RequestParam(defaultValue = "USER") String role,
                          RedirectAttributes redirectAttributes) {
        if (!sessionManager.isLoggedIn() || !sessionManager.isAdmin()) {
            return "redirect:/dashboard";
        }
        
        boolean created = loginService.createUser(username, password, fullName, department, email, role);
        
        if (created) {
            redirectAttributes.addFlashAttribute("message", "User created successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Username already exists!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        return "redirect:/users";
    }
}