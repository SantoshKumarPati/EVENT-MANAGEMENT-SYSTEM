package com.ems.eventmanagement.controller;

import com.ems.eventmanagement.entity.Event;
import com.ems.eventmanagement.service.EventService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String date) {

        if (name != null && !name.isBlank()) {
            return eventService.searchEvents(name);
        }

        if (status != null && !status.isBlank()) {
            return eventService.filterByStatus(status);
        }

        if (date != null && !date.isBlank()) {
            return eventService.filterByDate(LocalDate.parse(date));
        }

        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {

        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {

        return ResponseEntity.ok(
                eventService.createEvent(event)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @RequestBody Event event) {

        return eventService.updateEvent(id, event)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {

        boolean deleted = eventService.deleteEvent(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Event deleted successfully");
    }
}