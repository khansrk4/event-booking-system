package system.eventticketbooking.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.eventticketbooking.dto.CancellationResponseDTO;
import system.eventticketbooking.entity.Booking;
import system.eventticketbooking.entity.Event;
import system.eventticketbooking.entity.User;
import system.eventticketbooking.exception.ResourceNotFoundException;
import system.eventticketbooking.repository.BookingRepository;
import system.eventticketbooking.repository.EventRepository;
import system.eventticketbooking.repository.UserRepository;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    EventService eventService;

    @Autowired
    PaymentService paymentService;

    public Booking createBooking(Booking booking,Double paymentAmount) {
        Booking savedBooking = bookingRepository.save(booking);
        eventService.updateAvailableTickets(booking.getEvent().getId(), -booking.getNumberOfTickets());  // Subtract tickets

//       paymentService.processPayment(booking, paymentAmount);


        return savedBooking;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Method to get a booking by ID
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Transactional
    public CancellationResponseDTO cancelBooking(Long bookingId) {
        // Find the booking by ID
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // Logic to cancel the booking
        bookingRepository.delete(booking);

        // Send cancellation notification
//        notificationService.sendCancellationNotification(
//                booking.getEvent().getName(),
//                booking.getEvent().getDate().toString(),
//                booking.getEvent().getLocation(),
//                booking.getUser().getUsername(),
//                booking.getTicketType().name(),
//                booking.getNumberOfTickets(),
//                booking.getUser().getEmail()
//        );

        // Create and return the CancellationResponseDTO
        return new CancellationResponseDTO(
                booking.getUser().getUsername(),
                booking.getId(),
                booking.getEvent().getName(),
                "Booking successfully cancelled for " + booking.getUser().getUsername()
        );
    }

}

