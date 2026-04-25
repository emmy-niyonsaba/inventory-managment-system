package com.airtel.ims.controller;

import com.airtel.ims.authentication.SessionManager;
import com.airtel.ims.model.Assignment;
import com.airtel.ims.model.Equipment;
import com.airtel.ims.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/assignments")
public class AssignmentController {
    
    @Autowired
    private MainController mainController;
    
    @Autowired
    private SessionManager sessionManager;
    
    @GetMapping
    public String listAssignments(Model model) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        List<Assignment> activeAssignments = mainController.getActiveAssignments();
        List<Assignment> overdueAssignments = mainController.getOverdueAssignments();
        List<Assignment> allAssignments = mainController.getAllAssignments();
        
        model.addAttribute("activeAssignments", activeAssignments);
        model.addAttribute("overdueAssignments", overdueAssignments);
        model.addAttribute("allAssignments", allAssignments);
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        
        return "assignments-list";
    }
    
    @GetMapping("/assign-equipment/{equipmentId}")
    public String showAssignForm(@PathVariable Long equipmentId, Model model) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        Equipment equipment = mainController.getEquipmentById(equipmentId).orElse(null);
        if (equipment == null) {
            return "redirect:/equipment";
        }
        
        List<User> users = mainController.getAllUsers();
        
        model.addAttribute("equipment", equipment);
        model.addAttribute("users", users);
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        model.addAttribute("assignment", new Assignment());
        
        return "assignment-form";
    }
    
    @PostMapping("/assign")
    public String assignEquipment(@RequestParam Long equipmentId,
                                  @RequestParam Long userId,
                                  @RequestParam String purpose,
                                  @RequestParam String expectedReturnDate,
                                  RedirectAttributes redirectAttributes) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        try {
            Equipment equipment = mainController.getEquipmentById(equipmentId).orElse(null);
            User user = mainController.getUserById(userId).orElse(null);
            
            if (equipment == null || user == null) {
                throw new IllegalArgumentException("Equipment or User not found");
            }
            
            LocalDateTime expectedDate = LocalDateTime.parse(expectedReturnDate + "T23:59:59");
            
            mainController.assignEquipment(equipment, user, purpose, expectedDate);
            
            redirectAttributes.addFlashAttribute("message", "Equipment assigned successfully to " + user.getFullName());
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error assigning equipment: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        return "redirect:/assignments";
    }
    
    @GetMapping("/return/{assignmentId}")
    public String showReturnForm(@PathVariable Long assignmentId, Model model) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        Assignment assignment = mainController.getAssignmentsByUser(null).stream()
                .filter(a -> a.getId().equals(assignmentId))
                .findFirst()
                .orElse(null);
        
        // Get assignment directly
        for (Assignment a : mainController.getAllAssignments()) {
            if (a.getId().equals(assignmentId)) {
                assignment = a;
                break;
            }
        }
        
        if (assignment == null) {
            return "redirect:/assignments";
        }
        
        model.addAttribute("assignment", assignment);
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        
        return "assignment-return";
    }
    
    @PostMapping("/return")
    public String returnEquipment(@RequestParam Long assignmentId,
                                  @RequestParam String conditionAtReturn,
                                  @RequestParam(required = false) String notes,
                                  RedirectAttributes redirectAttributes) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        try {
            Assignment assignment = null;
            for (Assignment a : mainController.getAllAssignments()) {
                if (a.getId().equals(assignmentId)) {
                    assignment = a;
                    break;
                }
            }
            
            if (assignment == null) {
                throw new IllegalArgumentException("Assignment not found");
            }
            
            mainController.returnEquipment(assignment, conditionAtReturn, notes);
            
            redirectAttributes.addFlashAttribute("message", "Equipment returned successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error returning equipment: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        return "redirect:/assignments";
    }
    
    @GetMapping("/history")
    public String viewHistory(Model model) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        List<Assignment> allAssignments = mainController.getAllAssignments();
        model.addAttribute("allAssignments", allAssignments);
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        
        return "assignment-history";
    }
}