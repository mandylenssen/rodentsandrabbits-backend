package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.LogbookLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogbookLogRepository extends JpaRepository<LogbookLog, Long> {
    List<LogbookLog> findByLogbookId(Long logbookId);
}
