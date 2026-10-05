package com.ems.eventmanagement.repository;

import com.ems.eventmanagement.entity.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventNameContainingIgnoreCase(String eventName);

    List<Event> findByStatusIgnoreCase(String status);

    List<Event> findByEventDate(LocalDate eventDate);

    long countByStatusIgnoreCase(String status);

    @Query("SELECT COALESCE(SUM(e.availableSeats), 0) FROM Event e")
    long getTotalAvailableSeats();
}