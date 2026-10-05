package com.ems.eventmanagement.service;

import com.ems.eventmanagement.entity.Event;
import com.ems.eventmanagement.entity.Registration;
import com.ems.eventmanagement.repository.EventRepository;
import com.ems.eventmanagement.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    public RegistrationService(
            RegistrationRepository registrationRepository,
            EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Optional<Registration> getRegistrationById(Long id) {
        return registrationRepository.findById(id);
    }

    public Registration createRegistration(Registration registration) {

        Long eventId = registration.getEvent().getId();

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (registrationRepository.existsByEmailAndEventId(
                registration.getEmail(), eventId)) {
            throw new RuntimeException("Already registered for this event");
        }

        if (event.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);

        registration.setEvent(event);
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus("Registered");

        return registrationRepository.save(registration);
    }

    public Optional<Registration> updateRegistration(
            Long id,
            Registration registrationDetails) {

        return registrationRepository.findById(id).map(registration -> {

            registration.setName(registrationDetails.getName());
            registration.setEmail(registrationDetails.getEmail());
            registration.setPhone(registrationDetails.getPhone());
            registration.setStatus(registrationDetails.getStatus());

            return registrationRepository.save(registration);
        });
    }

    public boolean cancelRegistration(Long id) {

        Optional<Registration> registrationOptional =
                registrationRepository.findById(id);

        if (registrationOptional.isEmpty()) {
            return false;
        }

        Registration registration = registrationOptional.get();

        if (!"Cancelled".equals(registration.getStatus())) {

            Event event = registration.getEvent();

            event.setAvailableSeats(event.getAvailableSeats() + 1);
            eventRepository.save(event);

            registration.setStatus("Cancelled");
            registrationRepository.save(registration);
        }

        return true;
    }

    public boolean deleteRegistration(Long id) {

        if (!registrationRepository.existsById(id)) {
            return false;
        }

        registrationRepository.deleteById(id);
        return true;
    }
}