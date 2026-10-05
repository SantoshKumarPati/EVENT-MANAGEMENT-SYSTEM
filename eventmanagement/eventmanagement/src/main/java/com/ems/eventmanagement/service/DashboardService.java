package com.ems.eventmanagement.service;

import com.ems.eventmanagement.dto.DashboardResponse;
import com.ems.eventmanagement.repository.EventRepository;
import com.ems.eventmanagement.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;

    public DashboardService(
            EventRepository eventRepository,
            RegistrationRepository registrationRepository) {

        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    public DashboardResponse getDashboard() {

        long totalEvents = eventRepository.count();

        long upcomingEvents =
                eventRepository.countByStatusIgnoreCase("Upcoming");

        long totalRegistrations =
                registrationRepository.count();

        long availableSeats =
                eventRepository.getTotalAvailableSeats();

        long cancelledRegistrations =
                registrationRepository.countByStatusIgnoreCase("Cancelled");

        return new DashboardResponse(
                totalEvents,
                upcomingEvents,
                totalRegistrations,
                availableSeats,
                cancelledRegistrations
        );
    }
}