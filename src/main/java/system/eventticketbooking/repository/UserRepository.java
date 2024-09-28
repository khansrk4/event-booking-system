package system.eventticketbooking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.eventticketbooking.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}