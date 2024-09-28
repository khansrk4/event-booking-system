package system.eventticketbooking.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import system.eventticketbooking.entity.Booking;
import system.eventticketbooking.entity.Payment;
import system.eventticketbooking.service.BookingService;
import system.eventticketbooking.service.PaymentService;

import static org.mockito.ArgumentMatchers.eq; // Import eq for strict matching
import static org.mockito.Mockito.when;

public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @Mock
    private BookingService bookingService;

    private Booking booking;
    private Payment payment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        booking = new Booking();
        booking.setId(1L);
        // Set other properties as needed

        payment = new Payment();
        payment.setId(1L);
        payment.setAmount(100.0);
        // Set other properties as needed
    }

    @Test
    public void testProcessPayment() {
        when(bookingService.getBookingById(1L)).thenReturn(booking);
        when(paymentService.processPayment(eq(booking), eq(100.0))).thenReturn(payment);

        ResponseEntity<Payment> response = paymentController.processPayment(1L, 100.0);

        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody().equals(payment);
    }
}
