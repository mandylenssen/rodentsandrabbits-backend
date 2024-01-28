package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;

import jakarta.validation.Valid;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.PetDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class PetController {

    private final PetService petService;

    public PetController(PetService petService){
        this.petService = petService;
    }


    @PostMapping("/pets")
    public ResponseEntity<Object> addPet(@Valid @RequestBody PetDto petDto) {

        PetDto dto = petService.addPet(petDto);

        return ResponseEntity.created(null).body(dto);

    }

}
