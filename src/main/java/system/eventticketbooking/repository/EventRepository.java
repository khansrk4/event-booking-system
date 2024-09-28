package system.eventticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.eventticketbooking.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
