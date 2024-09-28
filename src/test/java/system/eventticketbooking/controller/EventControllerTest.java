package system.eventticketbooking.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import system.eventticketbooking.entity.Event;
import system.eventticketbooking.service.EventService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    private Event event;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        event = new Event();
        event.setId(1L);
        event.setName("Sample Event");
        event.setAvailableTickets(100);
    }

    @Test
    public void testGetAllEvents() {
        List<Event> events = Arrays.asList(event);
        when(eventService.getAllEvents()).thenReturn(events);

        ResponseEntity<List<Event>> response = eventController.getAllEvents();

        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody().equals(events);
    }

    @Test
    public void testCreateEvent() {
        when(eventService.createEvent(any(Event.class))).thenReturn(event);

        ResponseEntity<Event> response = eventController.createEvent(event);

        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody().equals(event);
    }
}
