package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.ImageData;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.User;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.ImageDataRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.PetRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.UserRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.ImageDataService;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.utils.ImageUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ImageDataController {

//    private final ImageDataService imageDataService;
//    private final UserRepository userRepository;
//    private final ImageDataRepository imageDataRepository;
//    private final PetRepository petRepository;
//
//    public ImageDataController(ImageDataService imageDataService, UserRepository userRepository, ImageDataRepository imageDataRepository, PetRepository petRepository) {
//        this.imageDataService = imageDataService;
//        this.userRepository = userRepository;
//        this.imageDataRepository = imageDataRepository;
//        this.petRepository = petRepository;
//    }


//    @PostMapping
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile multipartFile, @RequestParam String username, @RequestParam Long petId) throws IOException {
//        String image = imageDataService.uploadImage(multipartFile, username, petId);
//        return ResponseEntity.ok().body("file has been uploaded, " + image);
//    }

//    @GetMapping("/{username}/{petId}")
//    public ResponseEntity<Object> downloadImage(@PathVariable String username, @PathVariable Long petId) throws IOException {
//        Optional<Pet> petOptional = petRepository.findById(petId);
//        if(!petOptional.isPresent() || !petOptional.get().getOwner().getUsername().equals(username)) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Pet pet = petOptional.get();
//
//        ImageData imageData = pet.getImageData();
//        if(imageData == null) {
//            return ResponseEntity.notFound().build();
//        }
//        byte[] image = ImageUtil.decompressImage(imageData.getImageData());
//        MediaType mediaType = MediaType.valueOf(imageData.getType());
//
//        return ResponseEntity.ok().contentType(mediaType).body(image);
//    }
}