package system.eventticketbooking.dto;

import lombok.Data;

@Data
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long eventId;
    private String username;
    private String email;
    private String eventName;
    private String eventLocation;
    private String ticketType; // New field
    private Integer numberOfTickets;
    private Double totalPrice;
}
