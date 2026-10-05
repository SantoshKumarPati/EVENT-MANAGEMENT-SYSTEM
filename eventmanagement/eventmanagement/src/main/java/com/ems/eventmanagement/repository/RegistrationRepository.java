package com.ems.eventmanagement.repository;

import com.ems.eventmanagement.entity.Registration;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByEmailAndEventId(String email, Long eventId);

    long countByStatusIgnoreCase(String status);
}