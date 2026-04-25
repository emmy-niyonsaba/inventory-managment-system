package com.airtel.ims.controller;

import com.airtel.ims.authentication.SessionManager;
import com.airtel.ims.model.*;
import com.airtel.ims.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    
    @Autowired
    private EquipmentRepository equipmentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private SessionManager sessionManager;
    
    // ========== Equipment Management ==========
    
    public Equipment addEquipment(Equipment equipment) {
        equipment.setLastUpdated(LocalDateTime.now());
        if (equipment.getStatus() == null) {
            equipment.setStatus(EquipmentStatus.AVAILABLE);
        }
        return equipmentRepository.save(equipment);
    }
    
    public Equipment updateEquipment(Equipment equipment) {
        equipment.setLastUpdated(LocalDateTime.now());
        return equipmentRepository.save(equipment);
    }
    
    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }
    
    public Optional<Equipment> getEquipmentById(Long id) {
        return equipmentRepository.findById(id);
    }
    
    public Optional<Equipment> getEquipmentByAssetTag(String assetTag) {
        return equipmentRepository.findByAssetTag(assetTag);
    }
    
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }
    
    public List<Equipment> searchEquipment(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllEquipment();
        }
        return equipmentRepository.searchEquipment(searchTerm);
    }
    
    public List<Equipment> getEquipmentByStatus(EquipmentStatus status) {
        return equipmentRepository.findByStatus(status);
    }
    
    public List<Equipment> getEquipmentByDeviceType(String deviceType) {
        return equipmentRepository.findByDeviceType(deviceType);
    }
    
    // ========== User Management ==========
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    // ========== Assignment Management ==========
    
    public Assignment assignEquipment(Equipment equipment, User user, String purpose, LocalDateTime expectedReturnDate) {
        // Check if equipment is available
        if (equipment.getStatus() != EquipmentStatus.AVAILABLE) {
            throw new IllegalStateException("Equipment is not available for assignment");
        }
        
        Assignment assignment = new Assignment();
        assignment.setEquipment(equipment);
        assignment.setAssignedUser(user);
        assignment.setAssignedBy(sessionManager.getCurrentUser());
        assignment.setAssignedToDepartment(user.getDepartment());
        assignment.setPurpose(purpose);
        assignment.setExpectedReturnDate(expectedReturnDate);
        assignment.setConditionAtAssignment(equipment.getEquipmentCondition());  // FIXED: getEquipmentCondition()
        assignment.setStatus("ACTIVE");
        
        // Update equipment status
        equipment.setStatus(EquipmentStatus.ASSIGNED);
        equipment.setCurrentOwner(user.getFullName());
        equipment.setCurrentDepartment(user.getDepartment());
        equipmentRepository.save(equipment);
        
        return assignmentRepository.save(assignment);
    }
    
    public Assignment returnEquipment(Assignment assignment, String conditionAtReturn, String notes) {
        assignment.setActualReturnDate(LocalDateTime.now());
        assignment.setConditionAtReturn(conditionAtReturn);
        assignment.setStatus("RETURNED");
        assignment.setNotes(notes);
        
        // Update equipment status back to available
        Equipment equipment = assignment.getEquipment();
        equipment.setStatus(EquipmentStatus.AVAILABLE);
        equipment.setEquipmentCondition(conditionAtReturn);  // FIXED: setEquipmentCondition()
        equipment.setCurrentOwner(null);
        equipment.setCurrentDepartment(null);
        equipment.setLastUpdated(LocalDateTime.now());
        equipmentRepository.save(equipment);
        
        return assignmentRepository.save(assignment);
    }
    
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
    
    public List<Assignment> getActiveAssignments() {
        return assignmentRepository.findByStatus("ACTIVE");
    }
    
    public List<Assignment> getOverdueAssignments() {
        return assignmentRepository.findOverdueAssignments(LocalDateTime.now());
    }
    
    public List<Assignment> getAssignmentsByUser(User user) {
        return assignmentRepository.findByAssignedUser(user);
    }
    
    public List<Assignment> getAssignmentsByEquipment(Equipment equipment) {
        return assignmentRepository.findByEquipment(equipment);
    }
    
    public boolean isEquipmentAssigned(Equipment equipment) {
        return assignmentRepository.existsByEquipmentAndStatus(equipment, "ACTIVE");
    }
    
    // ========== Dashboard Statistics ==========
    
    public long getTotalEquipmentCount() {
        return equipmentRepository.count();
    }
    
    public long getAvailableEquipmentCount() {
        return equipmentRepository.countByStatus(EquipmentStatus.AVAILABLE);
    }
    
    public long getAssignedEquipmentCount() {
        return equipmentRepository.countByStatus(EquipmentStatus.ASSIGNED);
    }
    
    public long getUnderMaintenanceCount() {
        return equipmentRepository.countByStatus(EquipmentStatus.UNDER_MAINTENANCE);
    }
    
    public long getDamagedEquipmentCount() {
        return equipmentRepository.countByStatus(EquipmentStatus.DAMAGED);
    }
    
    public long getActiveAssignmentsCount() {
        return assignmentRepository.countActiveAssignments();
    }
    
    public long getTotalUsersCount() {
        return userRepository.count();
    }
    
    public long getAdminCount() {
        return userRepository.countByRole("ADMIN");
    }
    
    // ========== Reports Data ==========
    
    public List<Object[]> getEquipmentCountByDeviceType() {
        return equipmentRepository.countByDeviceTypeGrouped();
    }
    
    public List<Object[]> getEquipmentCountByStatus() {
        return equipmentRepository.countByStatusGrouped();
    }
    
    public List<Object[]> getAssignmentsByDepartment() {
        return assignmentRepository.countAssignmentsByDepartment();
    }
}