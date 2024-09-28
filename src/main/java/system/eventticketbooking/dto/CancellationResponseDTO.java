package system.eventticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancellationResponseDTO {
    private String username;
    private Long bookingId;
    private String eventName;
    private String message;
}
