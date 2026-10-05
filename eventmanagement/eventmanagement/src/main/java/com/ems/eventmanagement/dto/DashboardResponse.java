package com.ems.eventmanagement.dto;

public class DashboardResponse {

    private long totalEvents;

    private long upcomingEvents;

    private long totalRegistrations;

    private long availableSeats;

    private long cancelledRegistrations;

    public DashboardResponse() {
    }

    public DashboardResponse(
            long totalEvents,
            long upcomingEvents,
            long totalRegistrations,
            long availableSeats,
            long cancelledRegistrations) {

        this.totalEvents = totalEvents;
        this.upcomingEvents = upcomingEvents;
        this.totalRegistrations = totalRegistrations;
        this.availableSeats = availableSeats;
        this.cancelledRegistrations = cancelledRegistrations;
    }

    public long getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(long totalEvents) {
        this.totalEvents = totalEvents;
    }

    public long getUpcomingEvents() {
        return upcomingEvents;
    }

    public void setUpcomingEvents(long upcomingEvents) {
        this.upcomingEvents = upcomingEvents;
    }

    public long getTotalRegistrations() {
        return totalRegistrations;
    }

    public void setTotalRegistrations(long totalRegistrations) {
        this.totalRegistrations = totalRegistrations;
    }

    public long getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(long availableSeats) {
        this.availableSeats = availableSeats;
    }

    public long getCancelledRegistrations() {
        return cancelledRegistrations;
    }

    public void setCancelledRegistrations(long cancelledRegistrations) {
        this.cancelledRegistrations = cancelledRegistrations;
    }
}