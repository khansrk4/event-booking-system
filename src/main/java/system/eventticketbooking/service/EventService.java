package system.eventticketbooking.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import system.eventticketbooking.entity.Event;
import system.eventticketbooking.exception.ResourceNotFoundException;
import system.eventticketbooking.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public int checkTicketAvailability(Long eventId) {
        return eventRepository.findById(eventId)
                .map(Event::getAvailableTickets) // Assuming `Event` has a `getAvailableTickets()` method
                .orElse(0); // Return 0 if the event doesn't exist or has no available tickets
    }

    @Transactional
    public void updateAvailableTickets(Long eventId, int ticketAdjustment) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        int updatedAvailableTickets = event.getAvailableTickets() + ticketAdjustment;
        event.setAvailableTickets(updatedAvailableTickets);

        eventRepository.save(event);
    }
}
