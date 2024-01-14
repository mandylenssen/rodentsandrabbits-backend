package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.UserContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContactDetailsRepository extends JpaRepository<UserContactDetails, Long> {
}
