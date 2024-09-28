package system.eventticketbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.eventticketbooking.entity.Booking;
import system.eventticketbooking.entity.Payment;
import system.eventticketbooking.service.BookingService;
import system.eventticketbooking.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{bookingId}")
    public ResponseEntity<Payment> processPayment(@PathVariable Long bookingId, @RequestBody Double amount) {
        Booking booking = bookingService.getBookingById(bookingId);
        Payment payment = paymentService.processPayment(booking, amount);
        return ResponseEntity.ok(payment);
    }
}
