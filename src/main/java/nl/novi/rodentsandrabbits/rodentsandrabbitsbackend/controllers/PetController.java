package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;

import jakarta.validation.Valid;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.PetDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService){
        this.petService = petService;
    }


    @PostMapping
    public ResponseEntity<Object> addPet(@Valid @RequestBody PetDto petDto) {
        PetDto createdPetDto = petService.addPet(petDto);
        return ResponseEntity.created(null).body(createdPetDto);

    }

    @GetMapping
    public ResponseEntity<List<PetDto>> getAllPets() {

        List<PetDto> dtos;

        dtos = petService.getAllPets();
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDto> GetPet(@PathVariable Long petId) {

        PetDto pet = petService.getPetById(petId);
        return ResponseEntity.ok().body(pet);
    }



    @PutMapping("/{petId}")
    public ResponseEntity<PetDto> updatePet(@PathVariable Long petId, @RequestBody PetDto petDto, Principal principal) {
        String username = principal.getName();
        PetDto updatedPetDto = petService.updatePet(petId, petDto, username);
        return ResponseEntity.ok().body(updatedPetDto);
    }


@GetMapping("/user")
    public ResponseEntity<List<PetDto>> getPetsByUsername(Principal principal) {
        List<PetDto> dtos = petService.getAllPetsByUsername(principal.getName());
        return ResponseEntity.ok().body(dtos);
    }


    @DeleteMapping("/{petId}")
    public ResponseEntity<Object> deletePet(@PathVariable Long petId, Principal principal) {
        petService.deletePet(petId, principal.getName());
        return ResponseEntity.noContent().build();
    }


}

