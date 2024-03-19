package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.configurations.dataloader;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.LogbookService;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.PetService;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.utils.ImageUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataLoader implements InitializingBean {

    private final PetService petService;
    private final LogbookService logbookService;

    public DataLoader(PetService petService, LogbookService logbookService) {
        this.petService = petService;
        this.logbookService = logbookService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initializeLogbookFileData();

        initializePetFileData();
    }

    private void initializeLogbookFileData() {
        try {
            byte[] logphoto1 = Files.readAllBytes(Path.of("src/main/resources/images/Squeek_rat.png"));

            logbookService.addImageToLog(1L, logphoto1, "Squeek_rat.png", "image/png");

            byte[] logphoto2 = Files.readAllBytes(Path.of("src/main/resources/images/Whiskers_rat.png"));

            logbookService.addImageToLog(2L, logphoto2, "Whiskers_rat.png", "image/png");

            byte[] logphoto3 = Files.readAllBytes(Path.of("src/main/resources/images/Nimbus_rat.png"));

            logbookService.addImageToLog(3L, logphoto3, "Nimbus_rat.png", "image/png");

            byte[] logphoto4 = Files.readAllBytes(Path.of("src/main/resources/images/Nibbles_mouse.png"));

            logbookService.addImageToLog(4L, logphoto4, "Nibbles_mouse.png", "image/png");

            byte[] logphoto5 = Files.readAllBytes(Path.of("src/main/resources/images/Peanut_mouse.png"));

            logbookService.addImageToLog(5L, logphoto5, "Peanut_mouse.png", "image/png");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializePetFileData() {
        try {
            byte[] squeekProfileImageBytea = Files.readAllBytes(Path.of("src/main/resources/images/Squeek_rat.png"));

            petService.addProfileImage(1L, squeekProfileImageBytea, "Squeek_rat.png", "image/png", "user@test.com");

            byte[] whiskersProfileImageBytea = Files.readAllBytes(Path.of("src/main/resources/images/Whiskers_rat.png"));

            petService.addProfileImage(2L, whiskersProfileImageBytea, "Whiskers_rat.png", "image/png", "user@test.com");

            byte[] nimbusProfileImageBytea = Files.readAllBytes(Path.of("src/main/resources/images/Nimbus_rat.png"));

            petService.addProfileImage(3L, nimbusProfileImageBytea, "Nimbus_rat.png", "image/png", "user@test.com");

            byte[] nibblesProfileImageBytea = Files.readAllBytes(Path.of("src/main/resources/images/Nibbles_mouse.png"));

            petService.addProfileImage(4L, nibblesProfileImageBytea, "Nibbles_mouse.png", "image/png", "user2@test.com");

            byte[] peanutProfileImageBytea = Files.readAllBytes(Path.of("src/main/resources/images/Peanut_mouse.png"));

            petService.addProfileImage(5L, peanutProfileImageBytea, "Peanut_mouse.png", "image/png", "user2@test.com");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}