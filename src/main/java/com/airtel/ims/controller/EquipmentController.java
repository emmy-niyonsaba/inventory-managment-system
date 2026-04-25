package com.airtel.ims.controller;

import com.airtel.ims.authentication.SessionManager;
import com.airtel.ims.model.Equipment;
import com.airtel.ims.model.EquipmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {
    
    @Autowired
    private MainController mainController;
    
    @Autowired
    private SessionManager sessionManager;
    
    @GetMapping
    public String listEquipment(@RequestParam(required = false) String search, Model model) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        List<Equipment> equipmentList;
        if (search != null && !search.isEmpty()) {
            equipmentList = mainController.searchEquipment(search);
            model.addAttribute("searchTerm", search);
        } else {
            equipmentList = mainController.getAllEquipment();
        }
        
        model.addAttribute("equipmentList", equipmentList);
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        model.addAttribute("statuses", EquipmentStatus.values());
        
        return "equipment-list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        model.addAttribute("equipment", new Equipment());
        model.addAttribute("statuses", EquipmentStatus.values());
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        return "equipment-form";
    }
    
    @PostMapping("/add")
    public String addEquipment(@RequestParam String assetTag,
                               @RequestParam String deviceType,
                               @RequestParam String brand,
                               @RequestParam String model,
                               @RequestParam(required = false) String serialNumber,
                               @RequestParam(required = false) String specifications,
                               @RequestParam(required = false, defaultValue = "AVAILABLE") String status,
                               @RequestParam(required = false) String equipmentCondition,
                               @RequestParam(required = false) String purchaseDate,
                               @RequestParam(required = false) String notes,
                               RedirectAttributes redirectAttributes) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        try {
            Equipment equipment = new Equipment();
            equipment.setAssetTag(assetTag);
            equipment.setDeviceType(deviceType);
            equipment.setBrand(brand);
            equipment.setModel(model);
            equipment.setSerialNumber(serialNumber);
            equipment.setSpecifications(specifications);
            equipment.setEquipmentCondition(equipmentCondition);
            equipment.setNotes(notes);
            
            // Set status
            try {
                equipment.setStatus(EquipmentStatus.valueOf(status));
            } catch (IllegalArgumentException e) {
                equipment.setStatus(EquipmentStatus.AVAILABLE);
            }
            
            // Set purchase date
            if (purchaseDate != null && !purchaseDate.isEmpty()) {
                equipment.setPurchaseDate(LocalDateTime.parse(purchaseDate + "T00:00:00"));
            }
            
            equipment.setLastUpdated(LocalDateTime.now());
            
            mainController.addEquipment(equipment);
            redirectAttributes.addFlashAttribute("message", "Equipment added successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Error adding equipment: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        return "redirect:/equipment";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        Equipment equipment = mainController.getEquipmentById(id).orElse(null);
        if (equipment == null) {
            return "redirect:/equipment";
        }
        
        model.addAttribute("equipment", equipment);
        model.addAttribute("statuses", EquipmentStatus.values());
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        return "equipment-form";
    }
    
    @PostMapping("/edit/{id}")
    public String updateEquipment(@PathVariable Long id,
                                  @RequestParam String assetTag,
                                  @RequestParam String deviceType,
                                  @RequestParam String brand,
                                  @RequestParam String model,
                                  @RequestParam(required = false) String serialNumber,
                                  @RequestParam(required = false) String specifications,
                                  @RequestParam(required = false, defaultValue = "AVAILABLE") String status,
                                  @RequestParam(required = false) String equipmentCondition,
                                  @RequestParam(required = false) String purchaseDate,
                                  @RequestParam(required = false) String notes,
                                  RedirectAttributes redirectAttributes) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        try {
            Equipment equipment = mainController.getEquipmentById(id).orElse(null);
            if (equipment == null) {
                throw new RuntimeException("Equipment not found");
            }
            
            equipment.setAssetTag(assetTag);
            equipment.setDeviceType(deviceType);
            equipment.setBrand(brand);
            equipment.setModel(model);
            equipment.setSerialNumber(serialNumber);
            equipment.setSpecifications(specifications);
            equipment.setEquipmentCondition(equipmentCondition);
            equipment.setNotes(notes);
            
            // Set status
            try {
                equipment.setStatus(EquipmentStatus.valueOf(status));
            } catch (IllegalArgumentException e) {
                equipment.setStatus(EquipmentStatus.AVAILABLE);
            }
            
            // Set purchase date
            if (purchaseDate != null && !purchaseDate.isEmpty()) {
                equipment.setPurchaseDate(LocalDateTime.parse(purchaseDate + "T00:00:00"));
            }
            
            equipment.setLastUpdated(LocalDateTime.now());
            
            mainController.updateEquipment(equipment);
            redirectAttributes.addFlashAttribute("message", "Equipment updated successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Error updating equipment: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        return "redirect:/equipment";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteEquipment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        Equipment equipment = mainController.getEquipmentById(id).orElse(null);
        if (equipment != null && equipment.getStatus() == EquipmentStatus.ASSIGNED) {
            redirectAttributes.addFlashAttribute("message", "Cannot delete equipment that is currently assigned!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        } else {
            mainController.deleteEquipment(id);
            redirectAttributes.addFlashAttribute("message", "Equipment deleted successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        }
        
        return "redirect:/equipment";
    }
    
    @GetMapping("/view/{id}")
    public String viewEquipment(@PathVariable Long id, Model model) {
        if (!sessionManager.isLoggedIn()) {
            return "redirect:/login";
        }
        
        Equipment equipment = mainController.getEquipmentById(id).orElse(null);
        model.addAttribute("equipment", equipment);
        model.addAttribute("currentUser", sessionManager.getCurrentUser());
        return "equipment-view";
    }
}