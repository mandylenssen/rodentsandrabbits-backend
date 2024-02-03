package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.ImageData;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.User;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.ImageDataRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.UserRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.utils.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageDataService {
    private final ImageDataRepository imageDataRepository;
    private final UserRepository userRepository;

    public ImageDataService(ImageDataRepository imageDataRepository, UserRepository userRepository) {
        this.imageDataRepository = imageDataRepository;
        this.userRepository = userRepository;

    }

    public String uploadImage(MultipartFile multipartFile, String username) throws IOException {
        Optional<User> user = userRepository.findById(username);
        User user1 = user.get();

        ImageData imgData = new ImageData();
        imgData.setName(multipartFile.getName());
        imgData.setType(multipartFile.getContentType());
        imgData.setImageData(ImageUtil.compressImage(multipartFile.getBytes()));
        imgData.setUser(user1);

        ImageData savedImage = imageDataRepository.save(imgData);
        user1.setImage(savedImage);
        userRepository.save(user1);
        return savedImage.getName();
    }

   public byte[] downloadImage(String username) throws IOException {
     Optional<User> user = userRepository.findById(username);
        User user1 = user.get();
        ImageData imageData = user1.getImageData();
        return ImageUtil.decompressImage(imageData.getImageData());
   }
}