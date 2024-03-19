package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByOwnerUsername(String username);
    Optional<Pet> findByIdAndEnabledTrue(Long id);
    List<Pet> findAllByOwnerUsernameAndEnabledTrue(String username);
    List<Pet> findAllByEnabledTrue();
    List<Pet> findAllPetsByName(String name);
}
