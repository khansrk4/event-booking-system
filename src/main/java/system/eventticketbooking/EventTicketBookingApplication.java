package system.eventticketbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "system.eventticketbooking")
public class EventTicketBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventTicketBookingApplication.class, args);
	}

}
