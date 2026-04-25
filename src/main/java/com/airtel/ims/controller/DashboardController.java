package com.airtel.ims.controller;

import com.airtel.ims.authentication.SessionManager;
import com.airtel.ims.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    @Autowired
    private SessionManager sessionManager;
    
    @Autowired
    private MainController mainController;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        System.out.println("=== DashboardController accessed ===");
        
        if (!sessionManager.isLoggedIn()) {
            System.out.println("User not logged in, redirecting to login");
            return "redirect:/login";
        }
        
        User currentUser = sessionManager.getCurrentUser();
        System.out.println("Current user: " + currentUser.getUsername());
        
        model.addAttribute("currentUser", currentUser);
        
        try {
            // Statistics for dashboard
            long totalEq = mainController.getTotalEquipmentCount();
            long availableEq = mainController.getAvailableEquipmentCount();
            long assignedEq = mainController.getAssignedEquipmentCount();
            long underMaint = mainController.getUnderMaintenanceCount();
            long damagedEq = mainController.getDamagedEquipmentCount();
            long activeAssign = mainController.getActiveAssignmentsCount();
            long totalUsers = mainController.getTotalUsersCount();
            
            System.out.println("Stats loaded - Total: " + totalEq + ", Available: " + availableEq);
            
            model.addAttribute("totalEquipment", totalEq);
            model.addAttribute("availableEquipment", availableEq);
            model.addAttribute("assignedEquipment", assignedEq);
            model.addAttribute("underMaintenance", underMaint);
            model.addAttribute("damagedEquipment", damagedEq);
            model.addAttribute("activeAssignments", activeAssign);
            model.addAttribute("totalUsers", totalUsers);
        } catch (Exception e) {
            System.out.println("Error loading stats: " + e.getMessage());
            e.printStackTrace();
            // Set default values to avoid errors
            model.addAttribute("totalEquipment", 0);
            model.addAttribute("availableEquipment", 0);
            model.addAttribute("assignedEquipment", 0);
            model.addAttribute("underMaintenance", 0);
            model.addAttribute("damagedEquipment", 0);
            model.addAttribute("activeAssignments", 0);
            model.addAttribute("totalUsers", 0);
        }
        
        System.out.println("Returning dashboard view");
        return "dashboard";
    }
}