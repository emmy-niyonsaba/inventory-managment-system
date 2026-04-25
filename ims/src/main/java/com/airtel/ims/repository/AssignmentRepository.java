package com.airtel.ims.repository;

import com.airtel.ims.model.Assignment;
import com.airtel.ims.model.Equipment;
import com.airtel.ims.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    List<Assignment> findByAssignedUser(User user);
    
    List<Assignment> findByAssignedUserOrderByAssignedDateDesc(User user);
    
    List<Assignment> findByEquipment(Equipment equipment);
    
    List<Assignment> findByStatus(String status);
    
    List<Assignment> findByStatusOrderByAssignedDateDesc(String status);
    
    List<Assignment> findByAssignedDateBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT a FROM Assignment a WHERE a.status = 'ACTIVE' AND a.expectedReturnDate < :currentDate")
    List<Assignment> findOverdueAssignments(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.status = 'ACTIVE'")
    long countActiveAssignments();
    
    @Query("SELECT a.assignedToDepartment, COUNT(a) FROM Assignment a WHERE a.status = 'ACTIVE' GROUP BY a.assignedToDepartment")
    List<Object[]> countAssignmentsByDepartment();
    
    @Query("SELECT a.assignedUser, COUNT(a) FROM Assignment a WHERE a.status = 'ACTIVE' GROUP BY a.assignedUser ORDER BY COUNT(a) DESC")
    List<Object[]> findUsersWithMostActiveAssignments();
    
    boolean existsByEquipmentAndStatus(Equipment equipment, String status);
    
    Optional<Assignment> findTopByEquipmentAndStatusOrderByAssignedDateDesc(Equipment equipment, String status);
}