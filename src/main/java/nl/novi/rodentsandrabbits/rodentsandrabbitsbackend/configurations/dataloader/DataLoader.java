package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.configurations.dataloader;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.PetService;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.utils.ImageUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataLoader implements InitializingBean {

    private final PetService petService;

    public DataLoader(PetService petService) {
        this.petService = petService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initializePetFileData();
    }

    private void initializePetFileData() {
        try {
            byte[] squeekProfileImageBytea = Files.readAllBytes(Path.of("src/main/resources/images/Squeek_rat.png"));

            petService.addProfileImage(1L, squeekProfileImageBytea, "Squeek_rat.png", "image/png", "system");

            byte[] whiskersProfileImageBytea = Files.readAllBytes(Path.of("src/main/resources/images/Whiskers_rat.png"));

            petService.addProfileImage(2L, whiskersProfileImageBytea, "Whiskers_rat.png", "image/png", "system");

            byte[] nimbusProfileImageBytea = Files.readAllBytes(Path.of("src/main/resources/images/Nimbus_rat.png"));

            petService.addProfileImage(3L, nimbusProfileImageBytea, "Nimbus_rat.png", "image/png", "system");

            byte[] nibblesProfileImageBytea = Files.readAllBytes(Path.of("src/main/resources/images/Nibbles_mouse.png"));

            petService.addProfileImage(4L, nibblesProfileImageBytea, "Nibbles_mouse.png", "image/png", "system");

            byte[] peanutProfileImageBytea = Files.readAllBytes(Path.of("src/main/resources/images/Peanut_mouse.png"));

            petService.addProfileImage(5L, peanutProfileImageBytea, "Peanut_mouse.png", "image/png", "system");


        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}