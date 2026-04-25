package com.airtel.ims.controller;

import com.airtel.ims.authentication.LoginService;
import com.airtel.ims.authentication.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private SessionManager sessionManager;
    
    @GetMapping("/")
    public String home() {
        if (sessionManager.isLoggedIn()) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        if (sessionManager.isLoggedIn()) {
            return "redirect:/dashboard";
        }
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                        @RequestParam String password,
                        Model model) {
        if (loginService.authenticate(username, password)) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("message", "Invalid username or password");
            model.addAttribute("messageType", "error");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout() {
        loginService.logout();
        return "redirect:/login";
    }
}