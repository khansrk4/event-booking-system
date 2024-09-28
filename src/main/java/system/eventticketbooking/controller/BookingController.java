package system.eventticketbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.eventticketbooking.dto.BookingDTO;
import system.eventticketbooking.dto.CancellationResponseDTO;
import system.eventticketbooking.entity.Booking;
import system.eventticketbooking.entity.Event;
import system.eventticketbooking.entity.TicketType;
import system.eventticketbooking.entity.User;
import system.eventticketbooking.exception.ResourceNotFoundException;
import system.eventticketbooking.service.BookingService;
import system.eventticketbooking.repository.UserRepository;
import system.eventticketbooking.repository.EventRepository; // Import EventRepository
import system.eventticketbooking.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository; // Inject UserRepository

    @Autowired
    private EventRepository eventRepository; // Inject EventRepository

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setNumberOfTickets(bookingDTO.getNumberOfTickets());
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setTicketType(TicketType.valueOf(bookingDTO.getTicketType()));

        // Lookup user by userId from the DTO
        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        booking.setUser(user);

        // Lookup event by eventId
        Event event = eventRepository.findById(bookingDTO.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        booking.setEvent(event);

        // Save booking
        Booking createdBooking = bookingService.createBooking(booking, bookingDTO.getTotalPrice());

        // Map to DTO to return with detailed user and event info
        BookingDTO responseDTO = new BookingDTO();
        responseDTO.setId(createdBooking.getId());
        responseDTO.setUserId(createdBooking.getUser().getId());
        responseDTO.setUsername(createdBooking.getUser().getUsername()); // Add username
        responseDTO.setEmail(createdBooking.getUser().getEmail()); // Add email
        responseDTO.setEventId(createdBooking.getEvent().getId());
        responseDTO.setEventName(createdBooking.getEvent().getName()); // Add event name
        responseDTO.setEventLocation(createdBooking.getEvent().getLocation()); // Add event location
        responseDTO.setNumberOfTickets(createdBooking.getNumberOfTickets());
        responseDTO.setTotalPrice(createdBooking.getTotalPrice());
        responseDTO.setTicketType(createdBooking.getTicketType().name());

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings().stream()
                .map(booking -> {
                    BookingDTO dto = new BookingDTO();
                    dto.setId(booking.getId());
                    dto.setUserId(booking.getUser().getId());
                    dto.setUsername(booking.getUser().getUsername()); // Add username
                    dto.setEmail(booking.getUser().getEmail()); // Add email
                    dto.setEventId(booking.getEvent().getId());
                    dto.setEventName(booking.getEvent().getName()); // Add event name
                    dto.setEventLocation(booking.getEvent().getLocation()); // Add event location
                    dto.setNumberOfTickets(booking.getNumberOfTickets());
                    dto.setTotalPrice(booking.getTotalPrice());
                    dto.setTicketType(booking.getTicketType().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Method to get a booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);

        // Map the booking to BookingDTO
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setUserId(booking.getUser().getId());
        bookingDTO.setUsername(booking.getUser().getUsername());
        bookingDTO.setEmail(booking.getUser().getEmail());
        bookingDTO.setEventId(booking.getEvent().getId());
        bookingDTO.setEventName(booking.getEvent().getName());
        bookingDTO.setEventLocation(booking.getEvent().getLocation());
        bookingDTO.setNumberOfTickets(booking.getNumberOfTickets());
        bookingDTO.setTotalPrice(booking.getTotalPrice());
        bookingDTO.setTicketType(booking.getTicketType().name());

        return ResponseEntity.ok(bookingDTO);
    }


    @GetMapping("/checkAvailability/{eventId}")
    public ResponseEntity<Integer> checkTicketAvailability(@PathVariable Long eventId) {
        int availableTickets = eventService.checkTicketAvailability(eventId);
        return ResponseEntity.ok(availableTickets); // Return number of available tickets
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CancellationResponseDTO> cancelBooking(@PathVariable Long id) {
        CancellationResponseDTO cancellationResponse = bookingService.cancelBooking(id);

        return ResponseEntity.ok(cancellationResponse);
    }
}

