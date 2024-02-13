package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Logbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogbookRepository extends JpaRepository<Logbook, Long> {
}
