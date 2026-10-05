package com.ems.eventmanagement.controller;

import com.ems.eventmanagement.entity.Registration;
import com.ems.eventmanagement.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public List<Registration> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistrationById(
            @PathVariable Long id) {

        return registrationService.getRegistrationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createRegistration(
            @RequestBody Registration registration) {

        try {
            Registration savedRegistration =
                    registrationService.createRegistration(registration);

            return ResponseEntity.ok(savedRegistration);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registration> updateRegistration(
            @PathVariable Long id,
            @RequestBody Registration registration) {

        return registrationService
                .updateRegistration(id, registration)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelRegistration(
            @PathVariable Long id) {

        boolean cancelled =
                registrationService.cancelRegistration(id);

        if (!cancelled) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                "Registration cancelled successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistration(
            @PathVariable Long id) {

        boolean deleted =
                registrationService.deleteRegistration(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                "Registration deleted successfully");
    }
}