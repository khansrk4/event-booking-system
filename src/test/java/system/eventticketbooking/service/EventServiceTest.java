package system.eventticketbooking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import system.eventticketbooking.entity.Event;
import system.eventticketbooking.exception.ResourceNotFoundException;
import system.eventticketbooking.repository.EventRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        event = new Event();
        event.setId(1L);
        event.setName("Concert");
        event.setAvailableTickets(100);
    }

    @Test
    void testGetAllEvents() {
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event));

        var events = eventService.getAllEvents();

        assertEquals(1, events.size());
    }

    @Test
    void testCreateEvent() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event createdEvent = eventService.createEvent(event);

        assertNotNull(createdEvent);
        assertEquals("Concert", createdEvent.getName());
    }

    @Test
    void testCheckTicketAvailability() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

        int availableTickets = eventService.checkTicketAvailability(1L);

        assertEquals(100, availableTickets);
    }

    @Test
    void testCheckTicketAvailabilityNotFound() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        int availableTickets = eventService.checkTicketAvailability(1L);

        assertEquals(0, availableTickets);
    }

    @Test
    void testUpdateAvailableTickets() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

        eventService.updateAvailableTickets(1L, -10);

        assertEquals(90, event.getAvailableTickets());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void testUpdateAvailableTicketsNotFound() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventService.updateAvailableTickets(1L, -10));
    }
}
