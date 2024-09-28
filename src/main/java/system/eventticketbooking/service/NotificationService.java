package system.eventticketbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    private final String senderEmail = "";  // Replace with your sender email

    public void sendBookingConfirmation(String eventName, String eventDate, String eventLocation, String userName,
                                        String ticketType, int numberOfTickets, double paymentAmount, String userEmail) {
        String subject = "Booking Confirmation for " + eventName;
        String body = String.format(
                "Dear %s,\n\nYour booking has been confirmed:\n" +
                        "Event: %s\n" +
                        "Date: %s\n" +
                        "Location: %s\n" +
                        "Ticket Type: %s\n" +
                        "Number of Tickets: %d\n" +
                        "Payment Amount: %.2f\n\n" +
                        "Thank you for booking with us!",
                userName, eventName, eventDate, eventLocation, ticketType, numberOfTickets, paymentAmount
        );

        sendEmail(userEmail, subject, body);
    }

    public void sendCancellationNotification(String eventName, String eventDate, String eventLocation, String userName,
                                             String ticketType, int numberOfTickets, String userEmail) {
        String subject = "Booking Cancellation for " + eventName;
        String body = String.format(
                "Dear %s,\n\nYour booking has been canceled:\n" +
                        "Event: %s\n" +
                        "Date: %s\n" +
                        "Location: %s\n" +
                        "Ticket Type: %s\n" +
                        "Number of Tickets: %d\n\n" +
                        "We're sorry to see you go. If you have any questions, feel free to contact us.",
                userName, eventName, eventDate, eventLocation, ticketType, numberOfTickets
        );

        sendEmail(userEmail, subject, body);
    }

    public void sendPaymentFailedNotification(String eventName, String eventDate, String eventLocation, String userName,
                                              double paymentAmount, String userEmail) {
        String subject = "Payment Failed for " + eventName;
        String body = String.format(
                "Dear %s,\n\nYour payment has failed for the booking:\n" +
                        "Event: %s\n" +
                        "Date: %s\n" +
                        "Location: %s\n" +
                        "Payment Amount: %.2f\n\n" +
                        "Please try again or contact support.",
                userName, eventName, eventDate, eventLocation, paymentAmount
        );

        sendEmail(userEmail, subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
