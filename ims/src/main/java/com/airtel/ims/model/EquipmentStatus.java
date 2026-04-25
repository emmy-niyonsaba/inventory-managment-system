package com.airtel.ims.model;

public enum EquipmentStatus {
    AVAILABLE("Available"),
    ASSIGNED("Assigned"),
    UNDER_MAINTENANCE("Under Maintenance"),
    DAMAGED("Damaged"),
    DISPOSED("Disposed"),
    LOST("Lost");
    
    private final String displayName;
    
    EquipmentStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    // Get enum from display name
    public static EquipmentStatus fromDisplayName(String displayName) {
        for (EquipmentStatus status : values()) {
            if (status.displayName.equals(displayName)) {
                return status;
            }
        }
        return AVAILABLE;
    }
}