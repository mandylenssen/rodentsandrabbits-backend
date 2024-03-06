package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;

import jakarta.validation.Valid;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.PetDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.ImageData;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.PetService;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.utils.ImageUtil;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
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

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/pets/{petId}/profileImage")
                .build().toUri();

        pet.setProfileImageLocation(location.toString());
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
    public ResponseEntity<Object> deletePet(@PathVariable Long petId, Principal principal, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        petService.deletePet(petId, principal.getName(), isAdmin);

        return ResponseEntity.noContent().build();
    }



    @PostMapping("/{petId}/profileImage")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long petId) throws IOException {
        petService.addProfileImage(petId, multipartFile.getBytes(), multipartFile.getOriginalFilename(), multipartFile.getContentType());
        return ResponseEntity.ok().body("The profile picture file has been uploaded");
    }

    @GetMapping("/{petId}/profileImage")
    public ResponseEntity<Object> downloadProfileImage(@PathVariable Long petId) throws IOException {
        ImageData imageData = petService.getProfileImage(petId);
        if (imageData == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] image = ImageUtil.decompressImage(imageData.getImageData());
        MediaType mediaType = MediaType.valueOf(imageData.getType());

        return ResponseEntity.ok().contentType(mediaType).body(image);
    }


    @PutMapping("/{petId}/profileImage")
    public ResponseEntity<String> updateProfileImage(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long petId) throws IOException {
        petService.updateProfileImage(petId, multipartFile);
        return ResponseEntity.ok().body("The profile picture file has been updated");
    }

    @GetMapping("/{petId}/owner")
    public ResponseEntity<String> getOwner(@PathVariable Long petId) {
        String owner = petService.getOwner(petId);
        return ResponseEntity.ok().body(owner);
    }
}




