package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;

import jakarta.validation.Valid;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.PetDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class PetController {

    private final PetService petService;

    public PetController(PetService petService){
        this.petService = petService;
    }


    @PostMapping("/pets")
    public ResponseEntity<Object> addPet(@Valid @RequestBody PetDto petDto) {
        PetDto createdPetDto = petService.addPet(petDto);
        return ResponseEntity.created(null).body(createdPetDto);

    }

    @GetMapping("/pets")
    public ResponseEntity<List<PetDto>> getAllPets(@RequestParam(value = "name", required = false) Optional<String> name) {

        List<PetDto> dtos;

        if (name.isEmpty()) {
            dtos = petService.getAllPets();
        } else {
            dtos = petService.getAllPetsByName(name.get());
        }
        return ResponseEntity.ok().body(dtos);
    }


@GetMapping("/pets/{username}")
    public ResponseEntity<List<PetDto>> getPetsByUsername(@PathVariable("username") String username) {
        List<PetDto> dtos = petService.getAllPetsByUsername(username);
        return ResponseEntity.ok().body(dtos);
    }


}
