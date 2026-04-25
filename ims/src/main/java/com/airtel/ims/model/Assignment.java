package com.airtel.ims.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;
    
    @ManyToOne
    @JoinColumn(name = "assigned_to_user_id", nullable = false)
    private User assignedUser;
    
    @ManyToOne
    @JoinColumn(name = "assigned_by_user_id")
    private User assignedBy; // Admin who assigned
    
    @Column(length = 100)
    private String assignedToDepartment;
    
    private LocalDateTime assignedDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnDate;
    
    @Column(columnDefinition = "TEXT")
    private String purpose;
    
    @Column(columnDefinition = "TEXT")
    private String conditionAtAssignment;
    
    @Column(columnDefinition = "TEXT")
    private String conditionAtReturn;
    
    @Column(length = 20)
    private String status; // ACTIVE, RETURNED, OVERDUE
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    // Constructor
    public Assignment() {
        this.assignedDate = LocalDateTime.now();
        this.status = "ACTIVE";
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Equipment getEquipment() {
        return equipment;
    }
    
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    
    public User getAssignedUser() {
        return assignedUser;
    }
    
    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }
    
    public User getAssignedBy() {
        return assignedBy;
    }
    
    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }
    
    public String getAssignedToDepartment() {
        return assignedToDepartment;
    }
    
    public void setAssignedToDepartment(String assignedToDepartment) {
        this.assignedToDepartment = assignedToDepartment;
    }
    
    public LocalDateTime getAssignedDate() {
        return assignedDate;
    }
    
    public void setAssignedDate(LocalDateTime assignedDate) {
        this.assignedDate = assignedDate;
    }
    
    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }
    
    public void setExpectedReturnDate(LocalDateTime expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }
    
    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }
    
    public void setActualReturnDate(LocalDateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public String getConditionAtAssignment() {
        return conditionAtAssignment;
    }
    
    public void setConditionAtAssignment(String conditionAtAssignment) {
        this.conditionAtAssignment = conditionAtAssignment;
    }
    
    public String getConditionAtReturn() {
        return conditionAtReturn;
    }
    
    public void setConditionAtReturn(String conditionAtReturn) {
        this.conditionAtReturn = conditionAtReturn;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}