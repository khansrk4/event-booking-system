package system.eventticketbooking.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.eventticketbooking.entity.Payment;
import system.eventticketbooking.entity.Booking;
import system.eventticketbooking.entity.PaymentStatus;

import system.eventticketbooking.exception.PaymentException;
import system.eventticketbooking.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional // Ensure database integrity
    public Payment processPayment(Booking booking, Double amount) {
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PENDING); // Initially, set status as pending
        payment.setBooking(booking);

        try {
            // Simulate payment processing (Replace with actual payment gateway integration)
            boolean paymentSuccess = simulatePaymentProcessing(booking, amount); // This method should return true or false

            if (paymentSuccess) {
                payment.setStatus(PaymentStatus.COMPLETED); // Set to completed upon success

                // Send confirmation notification
                notificationService.sendBookingConfirmation(
                        booking.getEvent().getName(),
                        booking.getEvent().getDate().toString(),
                        booking.getEvent().getLocation(),
                        booking.getUser().getUsername(),
                        booking.getTicketType().name(), // Use enum name for ticket type
                        booking.getNumberOfTickets(),
                        amount,
                        booking.getUser().getEmail() // User's email for notification
                );
            } else {
                payment.setStatus(PaymentStatus.FAILED); // Handle payment failure

                // Send failure notification
                notificationService.sendPaymentFailedNotification(
                        booking.getEvent().getName(),
                        booking.getEvent().getDate().toString(),
                        booking.getEvent().getLocation(),
                        booking.getUser().getUsername(),
                        amount,
                        booking.getUser().getEmail() // User's email for notification
                );
                throw new PaymentException("Payment failed for booking ID: " + booking.getId());
            }
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED); // Handle exceptions gracefully

            // Log the error message for debugging purposes
            System.err.println("Payment processing error: " + e.getMessage());

            // Send failure notification
            notificationService.sendPaymentFailedNotification(
                    booking.getEvent().getName(),
                    booking.getEvent().getDate().toString(),
                    booking.getEvent().getLocation(),
                    booking.getUser().getUsername(),
                    amount,
                    booking.getUser().getEmail() // User's email for notification
            );

            throw new PaymentException("Payment processing error: " + e.getMessage());
        }

        return paymentRepository.save(payment); // Save payment record
    }

    private boolean simulatePaymentProcessing(Booking booking, Double amount) {
        // Simulate payment logic here; return true for success or false for failure
        return true; // Change this to your actual logic
    }
}
