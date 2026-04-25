package com.airtel.ims.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipment")
public class Equipment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String assetTag;
    
    @Column(nullable = false, length = 50)
    private String deviceType;
    
    @Column(nullable = false, length = 100)
    private String brand;
    
    @Column(nullable = false, length = 100)
    private String model;
    
    @Column(unique = true, length = 100)
    private String serialNumber;
    
    @Column(columnDefinition = "TEXT")
    private String specifications;
    
    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;
    
    @Column(length = 100)
    private String currentOwner;
    
    @Column(length = 100)
    private String currentDepartment;
    
    @Column(columnDefinition = "TEXT")
    private String equipmentCondition;  // Changed from "condition" to avoid SQL reserved keyword
    
    private LocalDateTime purchaseDate;
    private LocalDateTime lastUpdated;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    public Equipment() {
        this.status = EquipmentStatus.AVAILABLE;
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getAssetTag() { return assetTag; }
    public void setAssetTag(String assetTag) { this.assetTag = assetTag; }
    
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    
    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }
    
    public EquipmentStatus getStatus() { return status; }
    public void setStatus(EquipmentStatus status) { this.status = status; }
    
    public String getCurrentOwner() { return currentOwner; }
    public void setCurrentOwner(String currentOwner) { this.currentOwner = currentOwner; }
    
    public String getCurrentDepartment() { return currentDepartment; }
    public void setCurrentDepartment(String currentDepartment) { this.currentDepartment = currentDepartment; }
    
    public String getEquipmentCondition() { return equipmentCondition; }
    public void setEquipmentCondition(String equipmentCondition) { this.equipmentCondition = equipmentCondition; }
    
    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}