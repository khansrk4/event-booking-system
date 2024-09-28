package system.eventticketbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.eventticketbooking.entity.Event;
import system.eventticketbooking.repository.EventRepository;
import system.eventticketbooking.service.EventService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    public int checkTicketAvailability(Long eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            return event.getAvailableTickets(); // Return the actual number of available tickets
        }
        return 0; // Return 0 if no tickets are available or the event doesn't exist
    }
}
