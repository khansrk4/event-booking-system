package system.eventticketbooking.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import system.eventticketbooking.dto.BookingDTO;
import system.eventticketbooking.dto.CancellationResponseDTO;
import system.eventticketbooking.entity.Booking;
import system.eventticketbooking.entity.Event;
import system.eventticketbooking.entity.TicketType;
import system.eventticketbooking.entity.User;
import system.eventticketbooking.repository.EventRepository;
import system.eventticketbooking.repository.UserRepository;
import system.eventticketbooking.service.BookingService;
import system.eventticketbooking.service.EventService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookingControllerTest {

    @InjectMocks
    private BookingController bookingController;

    @Mock
    private BookingService bookingService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventService eventService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void testCreateBooking() throws Exception {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setNumberOfTickets(2);
        bookingDTO.setTotalPrice(100.0);
        bookingDTO.setUserId(1L);
        bookingDTO.setEventId(1L);
        bookingDTO.setTicketType("REGULAR");

        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        Event event = new Event();
        event.setId(1L);
        event.setName("Test Event");
        event.setLocation("Test Location");

        Booking createdBooking = new Booking();
        createdBooking.setId(1L);
        createdBooking.setUser(user);
        createdBooking.setEvent(event);
        createdBooking.setNumberOfTickets(2);
        createdBooking.setTotalPrice(100.0);
        createdBooking.setTicketType(TicketType.REGULAR);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(bookingService.createBooking(any(Booking.class), any(Double.class))).thenReturn(createdBooking);

        ResponseEntity<BookingDTO> response = bookingController.createBooking(bookingDTO);

        // Verify the response
        assert response.getStatusCodeValue() == 201; // 201 Created
        assert response.getBody().getId() == 1L;
        assert response.getBody().getUsername().equals("testUser");
        assert response.getBody().getEventName().equals("Test Event");

        // Verify that the appropriate methods were called
        verify(userRepository).findById(1L);
        verify(eventRepository).findById(1L);
        verify(bookingService).createBooking(any(Booking.class), any(Double.class));
    }

    @Test
    void testGetAllBookings() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        Event event = new Event();
        event.setId(1L);
        event.setName("Test Event");
        event.setLocation("Test Location");

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setUser(user);
        booking1.setEvent(event);
        booking1.setNumberOfTickets(2);
        booking1.setTotalPrice(100.0);
        booking1.setTicketType(TicketType.REGULAR);

        Booking booking2 = new Booking();
        booking2.setId(2L);
        booking2.setUser(user);
        booking2.setEvent(event);
        booking2.setNumberOfTickets(3);
        booking2.setTotalPrice(150.0);
        booking2.setTicketType(TicketType.REGULAR);

        when(bookingService.getAllBookings()).thenReturn(Arrays.asList(booking1, booking2));

        List<BookingDTO> bookings = bookingController.getAllBookings();

        // Verify the response
        assert bookings.size() == 2;
        assert bookings.get(0).getId() == 1L;
        assert bookings.get(1).getId() == 2L;

        // Verify that the booking service method was called
        verify(bookingService).getAllBookings();
    }

    @Test
    void testGetBookingById() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        Event event = new Event();
        event.setId(1L);
        event.setName("Test Event");
        event.setLocation("Test Location");

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUser(user);
        booking.setEvent(event);
        booking.setNumberOfTickets(2);
        booking.setTotalPrice(100.0);
        booking.setTicketType(TicketType.REGULAR);

        when(bookingService.getBookingById(1L)).thenReturn(booking);

        ResponseEntity<BookingDTO> response = bookingController.getBookingById(1L);

        // Verify the response
        assert response.getStatusCodeValue() == 200; // 200 OK
        assert response.getBody().getId() == 1L;
        assert response.getBody().getUsername().equals("testUser");

        // Verify that the booking service method was called
        verify(bookingService).getBookingById(1L);
    }

    @Test
    void testCheckTicketAvailability() throws Exception {
        long eventId = 1L;
        int availableTickets = 100;

        when(eventService.checkTicketAvailability(eventId)).thenReturn(availableTickets);

        ResponseEntity<Integer> response = bookingController.checkTicketAvailability(eventId);

        // Verify the response
        assert response.getStatusCodeValue() == 200; // 200 OK
        assert response.getBody() == availableTickets;

        // Verify that the event service method was called
        verify(eventService).checkTicketAvailability(eventId);
    }

    @Test
    void testCancelBooking() throws Exception {
        long bookingId = 1L;
        CancellationResponseDTO cancellationResponse = new CancellationResponseDTO();
        cancellationResponse.setMessage("Booking cancelled successfully");

        when(bookingService.cancelBooking(bookingId)).thenReturn(cancellationResponse);

        ResponseEntity<CancellationResponseDTO> response = bookingController.cancelBooking(bookingId);

        // Verify the response
        assert response.getStatusCodeValue() == 200; // 200 OK
        assert response.getBody().getMessage().equals("Booking cancelled successfully");

        // Verify that the booking service method was called
        verify(bookingService).cancelBooking(bookingId);
    }
}
