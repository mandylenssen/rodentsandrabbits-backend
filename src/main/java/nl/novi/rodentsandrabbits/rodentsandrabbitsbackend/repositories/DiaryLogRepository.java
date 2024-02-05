package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.DiaryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryLogRepository extends JpaRepository<DiaryLog, Long> {
}
