package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.ImageData;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.User;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.ImageDataRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.PetRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.UserRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.utils.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageDataService {
//    private final ImageDataRepository imageDataRepository;
//    private final UserRepository userRepository;
//    private final PetRepository petRepository;
//
//    public ImageDataService(ImageDataRepository imageDataRepository, UserRepository userRepository, PetRepository petRepository) {
//        this.imageDataRepository = imageDataRepository;
//        this.userRepository = userRepository;
//        this.petRepository = petRepository;
//    }
//
//    public String uploadImage(MultipartFile multipartFile, String username, Long petId) throws IOException {
//        Optional<User> user = userRepository.findById(username);
//        if (!user.isPresent()) {
//            throw new IllegalStateException("User not found with username: " + username);
//        }
//        User user1 = user.get();
//
//        Optional<Pet> pet = petRepository.findById(petId);
//        if (!pet.isPresent()) {
//            throw new IllegalStateException("Pet not found with id: " + petId);
//        }
//        Pet pet1 = pet.get();
//
//        ImageData imgData = new ImageData();
//        imgData.setName(multipartFile.getName());
//        imgData.setType(multipartFile.getContentType());
//        imgData.setImageData(ImageUtil.compressImage(multipartFile.getBytes()));
//        imgData.setPet(pet1);
//        imgData.setUser(user1);
//
//        pet1.setImageData(imgData);
//
//        ImageData savedImage = imageDataRepository.save(imgData);
////        user1.setImage(savedImage);
//        userRepository.save(user1);
//        petRepository.save(pet1);
//        return savedImage.getName();
//    }
//
//    public byte[] downloadImage(String username, Long petId) throws IOException {
//        Optional<User> userOptional = userRepository.findById(username);
//        if (!userOptional.isPresent()) {
//            throw new IllegalStateException("User not found with username: " + username);
//        }
//
//        Optional<Pet> petOptional = petRepository.findById(petId);
//        if (!petOptional.isPresent()) {
//            throw new IllegalStateException("Pet not found with id: " + petId);
//        }
//        Pet pet = petOptional.get();
//        ImageData imageData = pet.getImageData();
//        return ImageUtil.decompressImage(imageData.getImageData());
//    }

}