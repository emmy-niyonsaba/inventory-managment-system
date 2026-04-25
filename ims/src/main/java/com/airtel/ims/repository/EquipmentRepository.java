package com.airtel.ims.repository;

import com.airtel.ims.model.Equipment;
import com.airtel.ims.model.EquipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    
    Optional<Equipment> findByAssetTag(String assetTag);
    
    Optional<Equipment> findBySerialNumber(String serialNumber);
    
    List<Equipment> findByStatus(EquipmentStatus status);
    
    List<Equipment> findByDeviceType(String deviceType);
    
    List<Equipment> findByCurrentOwnerContaining(String owner);
    
    List<Equipment> findByCurrentDepartment(String department);
    
    List<Equipment> findByBrand(String brand);
    
    @Query("SELECT e FROM Equipment e WHERE " +
           "LOWER(e.assetTag) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.brand) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.model) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.serialNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.deviceType) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Equipment> searchEquipment(@Param("search") String search);
    
    long countByStatus(EquipmentStatus status);
    
    long countByDeviceType(String deviceType);
    
    @Query("SELECT e.deviceType, COUNT(e) FROM Equipment e GROUP BY e.deviceType")
    List<Object[]> countByDeviceTypeGrouped();
    
    @Query("SELECT e.status, COUNT(e) FROM Equipment e GROUP BY e.status")
    List<Object[]> countByStatusGrouped();
}