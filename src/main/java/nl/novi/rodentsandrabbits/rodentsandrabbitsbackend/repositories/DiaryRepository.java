package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
