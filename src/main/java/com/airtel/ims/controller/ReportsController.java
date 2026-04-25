package com.airtel.ims.controller;

import com.airtel.ims.authentication.SessionManager;
import com.airtel.ims.model.Equipment;
import com.airtel.ims.model.EquipmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportsController {
    
    @Autowired
    private MainController mainController;
    
    @Autowired
    private SessionManager sessionManager;
    
    @GetMapping
    public String showReports(Model model) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        // Equipment statistics
        model.addAttribute("totalEquipment", mainController.getTotalEquipmentCount());
        model.addAttribute("availableEquipment", mainController.getAvailableEquipmentCount());
        model.addAttribute("assignedEquipment", mainController.getAssignedEquipmentCount());
        model.addAttribute("underMaintenance", mainController.getUnderMaintenanceCount());
        model.addAttribute("damagedEquipment", mainController.getDamagedEquipmentCount());
        
        // Device type distribution
        List<Object[]> deviceTypeStats = mainController.getEquipmentCountByDeviceType();
        model.addAttribute("deviceTypeStats", deviceTypeStats);
        
        // Status distribution
        List<Object[]> statusStats = mainController.getEquipmentCountByStatus();
        model.addAttribute("statusStats", statusStats);
        
        // Assignment by department
        List<Object[]> deptAssignments = mainController.getAssignmentsByDepartment();
        model.addAttribute("deptAssignments", deptAssignments);
        
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        
        return "reports";
    }
    
    @GetMapping("/export")
    public String exportReport(@RequestParam String type, Model model) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        if ("equipment".equals(type)) {
            List<Equipment> equipmentList = mainController.getAllEquipment();
            model.addAttribute("equipmentList", equipmentList);
            model.addAttribute("reportTitle", "Equipment Inventory Report");
        } else if ("assignments".equals(type)) {
            model.addAttribute("assignments", mainController.getAllAssignments());
            model.addAttribute("reportTitle", "Assignments Report");
        }
        
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        return "report-export";
    }
}