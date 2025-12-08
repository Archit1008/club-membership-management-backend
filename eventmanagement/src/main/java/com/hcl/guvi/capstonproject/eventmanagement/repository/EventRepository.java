
package com.hcl.guvi.capstonproject.eventmanagement.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hcl.guvi.capstonproject.eventmanagement.entity.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findByEventName(String eventName);

    // Parameter name should be ownerName, not eventName
    List<EventEntity> findAllByOwnerName(String ownerName);

    // Option A: Use MONTH/YEAR — works on MySQL/PostgreSQL with Hibernate
    @Query("SELECT COUNT(e) FROM EventEntity e WHERE e.ownerName = :ownerName AND MONTH(e.eventDate) = :month AND YEAR(e.eventDate) = :year")
    int countEventsByOwnerAndMonth(@Param("ownerName") String ownerName,
                                   @Param("month") int month,      // 1..12
                                   @Param("year") int year);       // e.g., 2025

    // Option B: Recommended — range-based (index-friendly)
    @Query("SELECT COUNT(e) FROM EventEntity e WHERE e.ownerName = :ownerName AND e.eventDate >= :startOfMonth AND e.eventDate < :startOfNextMonth")
    int countEventsByOwnerAndMonthRange(@Param("ownerName") String ownerName,
                                        @Param("startOfMonth") LocalDateTime startOfMonth,
                                        @Param("startOfNextMonth") LocalDateTime startOfNextMonth);
}