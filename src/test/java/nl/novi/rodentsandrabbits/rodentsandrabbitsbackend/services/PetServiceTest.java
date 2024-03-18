package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.PetDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.ImageData;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.User;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.ImageDataRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.PetRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetService petService;

    @Mock
    UserRepository userRepository;

    @Mock
    ImageDataRepository imageDataRepository;


    @BeforeEach
    public void setUp() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        lenient().when(authentication.getName()).thenReturn("user@test.com");

    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Get all pets from the database.")
    public void getAllPetsTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse("2019-01-01");
        User owner = new User();
        Pet pet1 = new Pet(1L, "Fluffy", birthday, "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);
        Pet pet2 = new Pet(2L, "Squeaky", birthday, "rat", "female", "Details about Squeaky", "", "Pet2 diet", owner);

        when(petRepository.findAll()).thenReturn(List.of(pet1, pet2));

        List<PetDto> pets = petService.getAllPets();

        assertEquals(2, pets.size());
    }

    @Test
    @DisplayName("Get all pets by username from the database.")
    public void getAllPetsByUsernameTest() {
        String username = "user@test.com";
        User owner = new User();
        owner.setUsername(username);
        Pet pet1 = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);
        Pet pet2 = new Pet(2L, "Nibbles", new Date(), "hamster", "female", "Details about Nibbles", "", "Pet2 diet", owner);

        when(petRepository.findAllByOwnerUsername(username)).thenReturn(List.of(pet1, pet2));

        List<PetDto> resultDtos = petService.getAllPetsByUsername(username);

        assertEquals(2, resultDtos.size());
        assertEquals("Fluffy", resultDtos.get(0).getName());
        assertEquals("Nibbles", resultDtos.get(1).getName());
    }

    @Test
    @DisplayName("Get a pet by id from the database.")
    public void getPetTest() {
        Long id = 1L;
        User owner = new User();
        Pet pet = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);

        when(petRepository.findById(id)).thenReturn(java.util.Optional.of(pet));

        PetDto resultDto = petService.getPet(id);

        assertEquals("Fluffy", resultDto.getName());
    }

    @Test
    @DisplayName("Add pet to the database.")
    public void addPetTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse("2019-01-01");
        User owner = new User();
        owner.setUsername("user@test.com");
        Pet pet = new Pet(1L, "Nibbles", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", owner);
        PetDto petDto = new PetDto(1L, "Nibbles", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", "user@test.com");

        when(petRepository.save(any(Pet.class))).thenReturn(pet);
        when(userRepository.findByUsername(owner.getUsername())).thenReturn(Optional.of(owner));

        PetDto resultDto = petService.addPet(petDto);

        assertEquals("Nibbles", resultDto.getName());
    }

    @Test
    @DisplayName("Throw exception when unauthorized to add pet to the database.")
    public void addPetTest_WhenUnauthorized_ThrowsAuthorizationServiceException() throws ParseException {
        String unAuthorizedUsername = "user2@test.com";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse("2019-01-01");
        User owner = new User();
        owner.setUsername(unAuthorizedUsername);
        Pet pet = new Pet(1L, "Nibbles", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", owner);
        PetDto petDto = new PetDto(1L, "Nibbles", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", "user2@test.com");

        assertThrows(AuthorizationServiceException.class, () -> {
            petService.addPet(petDto);
        });
    }

    @Test
    @DisplayName("Get owner of pet")
    public void getOwnerTest() throws ParseException {
        User owner = new User();
        owner.setUsername("user@test.com");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse("2019-01-01");
        Pet pet = new Pet(1L, "Nibbles", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", owner);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        String ownerUserName = petService.getOwner(1L);

        assertEquals(ownerUserName, pet.getOwner().getUsername());
    }

    @Test
    @DisplayName("Update pet in the database.")
    public void updatePetTest() throws ParseException {
        User owner = new User();
        owner.setUsername("user@test.com");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse("2019-01-01");
        Pet pet = new Pet(1L, "Nibbles", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", owner);
        Pet petUpdate = new Pet(1L, "Sjaak", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", owner);

        PetDto petDtoInput = new PetDto(1L, "Sjaak", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", "user@test.com");

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(petRepository.save(any(Pet.class))).thenReturn(petUpdate);

        PetDto result = petService.updatePet(1L, petDtoInput, owner.getUsername());

        assertEquals(result.getName(), petUpdate.getName());
    }

    @Test
    @DisplayName("Throw exception when unauthorized to update pet in the database.")
    public void updatePetTest_WhenUnauthorized_ThrowsAuthorizationServiceException() throws ParseException {
        String unAuthorizedUsername = "user2@test.com";

        User owner = new User();
        owner.setUsername("user@test.com");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse("2019-01-01");
        Pet pet = new Pet(1L, "Nibbles", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", owner);

        PetDto petDtoInput = new PetDto(1L, "Sjaak", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", unAuthorizedUsername);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        assertThrows(AuthorizationServiceException.class, () -> {
            petService.updatePet(1L, petDtoInput, petDtoInput.getOwnerUsername());
        });
    }


    @Test
    @DisplayName("Get a pet by id from the database.")
    public void getPetByIdTest() throws ParseException {
        User owner = new User();
        owner.setUsername("user@test.com");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse("2019-01-01");
        Pet pet = new Pet(1L, "Nibbles", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", owner);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        PetDto result = petService.getPetById(1L);

        assertEquals(pet.getName(), result.getName());
    }

    @Test
    @DisplayName("Throw exception when unauthorized to get pet by id from the database.")
    public void getPetByIdTest_WhenUnauthorized_ThrowsAuthorizationServiceException() throws ParseException {
        String unAuthorizedUsername = "user2@test.com";

        User owner = new User();
        owner.setUsername(unAuthorizedUsername);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse("2019-01-01");
        Pet pet = new Pet(1L, "Nibbles", birthday, "rat", "female", "Details about Nibbles", "", "Pet2 diet", owner);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        assertThrows(AuthorizationServiceException.class, () -> {
            petService.getPetById(1L);
        });
    }

    @Test
    @DisplayName("Throw exception when unauthorized to get pet by id from the database.")
    public void getPetByIdTest_WhenNotExists_ThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> {
            petService.getPetById(1L);
        });
    }
    @Test
    @DisplayName("Delete pet from the database.")
    public void deletePetTest() {
        String username = "user@test.com";
        User owner = new User();
        owner.setUsername(username);
        Pet pet = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        doNothing().when(petRepository).deleteById(1L);

        petService.deletePet(1L, username, false);

        verify(petRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Throw exception when unauthorized to delete pet from the database.")
    public void deletePetTest_WhenUnauthorized_ThrowsAuthorizationServiceException() {
        String unAuthorizedUsername = "user2@test.com";

        String username = "user@test.com";
        User owner = new User();
        owner.setUsername(username);
        Pet pet = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        assertThrows(AuthorizationServiceException.class, () -> {
            petService.deletePet(1L, unAuthorizedUsername, false);
        });
    }

    @Test
    @DisplayName("Get profile image of pet from the database.")
    public void getPetProfileImageTest() {
        String username = "user@test.com";
        User owner = new User();
        owner.setUsername(username);
        Pet pet = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);

        ImageData imageData = new ImageData();
        imageData.setName("Fluffy.jpg");
        imageData.setType("image/jpeg");
        pet.setProfileImageData(imageData);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        ImageData result = petService.getProfileImage(1L);

        assertEquals(imageData.getName(), result.getName());
        assertEquals(imageData.getType(), result.getType());
    }

    @Test
    @DisplayName("Get profile image of pet from the database.")
    public void getPetProfileImageTest_WhenUnauthorized_ThrowsAuthorizationServiceException() {
        String unAuthorizedUsername = "user2@test.com";

        String username = unAuthorizedUsername;
        User owner = new User();
        owner.setUsername(username);
        Pet pet = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);

        ImageData imageData = new ImageData();
        imageData.setName("Fluffy.jpg");
        imageData.setType("image/jpeg");
        pet.setProfileImageData(imageData);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        assertThrows(AuthorizationServiceException.class, () -> {
            petService.getProfileImage(1L);
        });
    }

    @Test
    @DisplayName("Add profile image of pet to the database.")
    public void updatePetProfileImageTest() throws IOException {
        String username = "user@test.com";
        User owner = new User();
        owner.setUsername(username);
        Pet pet = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);

        ImageData imageData = new ImageData();
        imageData.setName("Fluffy.jpg");
        imageData.setType("image/jpeg");
        imageData.setImageData("Banaan".getBytes());
        pet.setProfileImageData(imageData);

        Pet petUpdate = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);
        ImageData imageDataUpdate = new ImageData();
        imageDataUpdate.setName("Fluffy2.jpg");
        imageDataUpdate.setType("image/jpeg");
        petUpdate.setProfileImageData(imageDataUpdate);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        MockMultipartFile mockFile = new MockMultipartFile(
                "Fluffy2.jpg",
                "Fluffy2.jpg",
                "image/jpeg",
                "Banaan".getBytes()
        );

        petService.updateProfileImage(1L, mockFile, username);
        when(petRepository.findById(1L)).thenReturn(Optional.of(petUpdate));

        ImageData result = petService.getProfileImage(1L);

        assertEquals(petUpdate.getProfileImageData().getName(), result.getName());
    }

    @Test
    @DisplayName("Throw exception when unauthorized to update profile image of pet in the database.")
    public void updatePetProfileImageTest_WhenUnauthorized_ThrowsAuthorizationServiceException() throws IOException {
        String UnauthorizedUsername = "user2@test.com";

        String username = "user@test.com";
        User owner = new User();
        owner.setUsername(username);
        Pet pet = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);

        ImageData imageData = new ImageData();
        imageData.setName("Fluffy.jpg");
        imageData.setType("image/jpeg");
        imageData.setImageData("Banaan".getBytes());
        pet.setProfileImageData(imageData);

        Pet petUpdate = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);
        ImageData imageDataUpdate = new ImageData();
        imageDataUpdate.setName("Fluffy2.jpg");
        imageDataUpdate.setType("image/jpeg");
        petUpdate.setProfileImageData(imageDataUpdate);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        MockMultipartFile mockFile = new MockMultipartFile(
                "Fluffy2.jpg",
                "Fluffy2.jpg",
                "image/jpeg",
                "Banaan".getBytes()
        );

        when(petRepository.findById(1L)).thenReturn(Optional.of(petUpdate));

        assertThrows(AuthorizationServiceException.class, () -> {
            petService.updateProfileImage(1L, mockFile, UnauthorizedUsername);
        });
    }

    @Test
    @DisplayName("Throw exception when unauthorized to add profile image of pet in the database.")
    public void addProfileImageTest_WhenUnauthorized_ThrowsAuthorizationServiceException() throws IOException {
        String UnauthorizedUsername = "user2@test.com";

        String username = "user@test.com";
        User owner = new User();
        owner.setUsername(username);
        Pet pet = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);

        ImageData imageData = new ImageData();
        imageData.setName("Fluffy.jpg");
        imageData.setType("image/jpeg");
        imageData.setImageData("Banaan".getBytes());
        pet.setProfileImageData(imageData);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        assertThrows(AuthorizationServiceException.class, () -> {
            petService.addProfileImage(1L, "Banaan".getBytes(), "Fluffy2.jpg", "image/jpeg", UnauthorizedUsername);
        });
    }

    @Test
    @DisplayName("Throw exception when unauthorized to add profile image of pet in the database.")
    public void addProfileImageTest_WhenSystem_AddsProfileImage() throws IOException {
        String systemUsername = "system";

        String username = "user@test.com";
        User owner = new User();
        owner.setUsername(username);
        Pet pet = new Pet(1L, "Fluffy", new Date(), "rat", "male", "Details about Fluffy", "", "Pet1 diet", owner);

        ImageData imageData = new ImageData();
        imageData.setName("Fluffy.jpg");
        imageData.setType("image/jpeg");
        imageData.setImageData("Banaan".getBytes());
        pet.setProfileImageData(imageData);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        assertDoesNotThrow(() -> {
            petService.addProfileImage(1L, "Banaan".getBytes(), "Fluffy2.jpg", "image/jpeg", systemUsername);
        });

    }
}