package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.ImageData;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.User;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.ImageDataRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.UserRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.ImageDataService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ImageDataController {

    private final ImageDataService imageDataService;
    private final UserRepository userRepository;
    private final ImageDataRepository imageDataRepository;

    public ImageDataController(ImageDataService imageDataService, UserRepository userRepository, ImageDataRepository imageDataRepository) {
        this.imageDataService = imageDataService;
        this.userRepository = userRepository;
        this.imageDataRepository = imageDataRepository;
    }


    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile multipartFile, @RequestParam String username) throws IOException {
        String image = imageDataService.uploadImage(multipartFile, username);
        return ResponseEntity.ok().body("file has been uploaded, " + image);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> downloadImage(@PathVariable String username) throws IOException {
        byte[] image = imageDataService.downloadImage(username);
        Optional<User> user = userRepository.findById(username);
        Optional<ImageData> dbImageData = imageDataRepository.findById(user.get().getImageData().getId());
        MediaType mediaType = MediaType.valueOf(dbImageData.get().getType());
        return ResponseEntity.ok().contentType(mediaType).body(image);

    }
}