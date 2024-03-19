package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

}
