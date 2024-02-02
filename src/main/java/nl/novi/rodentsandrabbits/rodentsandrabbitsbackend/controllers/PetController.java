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
    public ResponseEntity<List<PetDto>> getAllPets() {

        List<PetDto> dtos;

        dtos = petService.getAllPets();
        return ResponseEntity.ok().body(dtos);
    }



    @PutMapping("/pets/{petId}")
    public ResponseEntity<PetDto> updatePet(@PathVariable Long petId, @RequestBody PetDto petDto, Principal principal) {

        String username = principal.getName();

        PetDto updatedPetDto = petService.updatePet(petId, petDto, username);

        return ResponseEntity.ok().body(updatedPetDto);
    }

@GetMapping("/pets/user")
    public ResponseEntity<List<PetDto>> getPetsByUsername(Principal principal) {
        List<PetDto> dtos = petService.getAllPetsByUsername(principal.getName());
        return ResponseEntity.ok().body(dtos);
    }


}
