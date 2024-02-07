package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByOwnerUsername(String username);

    List<Pet> findAllPetsByName(String name);
    List<Pet> findByIdIn(List<Long> petIds);

}
