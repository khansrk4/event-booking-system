package system.eventticketbooking.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import system.eventticketbooking.dto.CancellationResponseDTO;
import system.eventticketbooking.entity.Booking;
import system.eventticketbooking.entity.Event;
import system.eventticketbooking.entity.User;
import system.eventticketbooking.exception.ResourceNotFoundException;
import system.eventticketbooking.repository.BookingRepository;
import system.eventticketbooking.repository.EventRepository;
import system.eventticketbooking.repository.UserRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventService eventService;

    @Mock
    private NotificationService notificationService;

    private Booking booking;
    private User user;
    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("JohnDoe");

        event = new Event();
        event.setId(1L);
        event.setName("Concert");

        booking = new Booking();
        booking.setId(1L);
        booking.setUser(user);
        booking.setEvent(event);
        booking.setNumberOfTickets(2);
    }

    @Test
    void testCreateBooking() {
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking createdBooking = bookingService.createBooking(booking, 100.0);

        assertNotNull(createdBooking);
        assertEquals(1L, createdBooking.getId());
        verify(eventService, times(1)).updateAvailableTickets(anyLong(), anyInt());
    }

    @Test
    void testGetAllBookings() {
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking));

        var bookings = bookingService.getAllBookings();

        assertEquals(1, bookings.size());
    }

    @Test
    void testGetBookingById() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        Booking foundBooking = bookingService.getBookingById(1L);

        assertNotNull(foundBooking);
        assertEquals(1L, foundBooking.getId());
    }

    @Test
    void testGetBookingByIdNotFound() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookingService.getBookingById(1L));
    }

    @Test
    void testCancelBooking() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        CancellationResponseDTO response = bookingService.cancelBooking(1L);

        assertNotNull(response);
        assertEquals("Booking successfully cancelled for JohnDoe", response.getMessage());
        verify(bookingRepository, times(1)).delete(any(Booking.class));
    }

    @Test
    void testCancelBookingNotFound() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookingService.cancelBooking(1L));
    }
}
