package com.ems.eventmanagement.service;

import com.ems.eventmanagement.entity.Event;
import com.ems.eventmanagement.repository.EventRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        event.setAvailableSeats(event.getTotalSeats());
        return eventRepository.save(event);
    }

    public Optional<Event> updateEvent(Long id, Event eventDetails) {

        return eventRepository.findById(id).map(event -> {

            event.setEventName(eventDetails.getEventName());
            event.setDescription(eventDetails.getDescription());
            event.setEventDate(eventDetails.getEventDate());
            event.setEventTime(eventDetails.getEventTime());
            event.setLocation(eventDetails.getLocation());
            event.setTotalSeats(eventDetails.getTotalSeats());
            event.setAvailableSeats(eventDetails.getAvailableSeats());
            event.setStatus(eventDetails.getStatus());

            return eventRepository.save(event);
        });
    }

    public boolean deleteEvent(Long id) {

        if (!eventRepository.existsById(id)) {
            return false;
        }

        eventRepository.deleteById(id);
        return true;
    }

    public List<Event> searchEvents(String name) {
        return eventRepository.findByEventNameContainingIgnoreCase(name);
    }

    public List<Event> filterByStatus(String status) {
        return eventRepository.findByStatusIgnoreCase(status);
    }

    public List<Event> filterByDate(LocalDate date) {
        return eventRepository.findByEventDate(date);
    }
}