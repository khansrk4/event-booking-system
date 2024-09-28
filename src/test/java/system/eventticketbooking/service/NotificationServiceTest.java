package system.eventticketbooking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

class NotificationServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendBookingConfirmation() {
        notificationService.sendBookingConfirmation("Concert", "2024-09-29", "Stadium", "John Doe", "VIP", 2, 200.0, "john@example.com");
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendCancellationNotification() {
        notificationService.sendCancellationNotification("Concert", "2024-09-29", "Stadium", "John Doe", "VIP", 2, "john@example.com");
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendPaymentFailedNotification() {
        notificationService.sendPaymentFailedNotification("Concert", "2024-09-29", "Stadium", "John Doe", 200.0, "john@example.com");
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
