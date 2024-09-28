package system.eventticketbooking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bookings")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private Integer numberOfTickets;

    @Column(nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING) // Store enum as String in the database
    @Column(nullable = false)
    private TicketType ticketType;
}
