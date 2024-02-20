package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Logbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogbookRepository extends JpaRepository<Logbook, Long> {
    Optional<Logbook> findByUserName(String username);
}
