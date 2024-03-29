package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.PetDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.ImageData;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.PetRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.UserRepository;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.*;

@Service
public class PetService {

    private final PetRepository petRepository;

    private final UserRepository userRepository;

    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }


    public PetDto addPet(PetDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (!dto.getOwnerUsername().equals(currentUsername) && !isAdmin) {
            throw new AuthorizationServiceException("User is not authorized to add pets for this owner");
        }

        Pet pet = transferToPet(dto);
        petRepository.save(pet);
        return transferToDto(pet);
    }


    public PetDto updatePet(Long petId, PetDto dto, String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        Pet pet = petRepository.findByIdAndEnabledTrue(petId).orElseThrow(() -> new NoSuchElementException("Pet not found"));

        if (!username.equals(pet.getOwner().getUsername()) && !isAdmin) {
            throw new AuthorizationServiceException("User is not authorized to update this pet");
        }

        pet.setName(dto.getName());
        pet.setBirthday(dto.getBirthday());
        pet.setSpecies(dto.getSpecies());
        pet.setGender(dto.getGender());
        pet.setDetails(dto.getDetails());
        pet.setMedication(dto.getMedication());
        pet.setDiet(dto.getDiet());

        petRepository.save(pet);
        return transferToDto(pet);
    }

    public PetDto getPet(long id) {
        Pet pet = petRepository.findByIdAndEnabledTrue(id).orElseThrow();

        return transferToDto(pet);
    }

    public List<PetDto> getAllPets() {
        List<Pet> pets = petRepository.findAllByEnabledTrue();
        return transferPetListToDtoList(pets);

    }

    public List<PetDto> transferPetListToDtoList(List<Pet> pets) {
        List<PetDto> petDtoList = new ArrayList<>();
        for (Pet pet : pets) {
            PetDto dto = transferToDto(pet);
            petDtoList.add(dto);
        }
        return petDtoList;
    }


public PetDto getPetById(long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();
    boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

    Pet pet = petRepository.findByIdAndEnabledTrue(id).orElseThrow(() -> new NoSuchElementException("Pet not found with ID " + id));

    if (!pet.getOwner().getUsername().equals(currentUsername) && !isAdmin) {
        throw new AuthorizationServiceException("User is not authorized to view this pet");
    }

    return transferToDto(pet);
}

    private Pet transferToPet(PetDto dto) {
        var pet = new Pet();

        pet.setName(dto.getName());
        pet.setBirthday(dto.getBirthday());
        pet.setSpecies(dto.getSpecies());
        pet.setGender(dto.getGender());
        pet.setDetails(dto.getDetails());
        pet.setMedication(dto.getMedication());
        pet.setDiet(dto.getDiet());
        pet.setOwner(userRepository.findByUsername(dto.getOwnerUsername()).orElseThrow());

        return pet;
    }

    private PetDto transferToDto(Pet pet) {
        PetDto dto = new PetDto();

        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthday(pet.getBirthday());
        dto.setSpecies(pet.getSpecies());
        dto.setGender(pet.getGender());
        dto.setDetails(pet.getDetails());
        dto.setMedication(pet.getMedication());
        dto.setDiet(pet.getDiet());
        dto.setOwnerUsername(pet.getOwner().getUsername());
        return dto;
    }

    public List<PetDto> getAllPetsByUsername(String username) {
    List<Pet> pets = petRepository.findAllByOwnerUsernameAndEnabledTrue(username);
    return transferPetListToDtoList(pets);
    }



    public void deletePet(Long petId, String name, boolean isAdmin) {
        Pet pet = petRepository.findByIdAndEnabledTrue(petId).orElseThrow();
        if (!pet.getOwner().getUsername().equals(name) && !isAdmin) {
            throw new AuthorizationServiceException("Pet does not belong to user or user is not admin");
        }

        pet.setEnabled(false);
        petRepository.save(pet);
    }


    public void addProfileImage(Long petId, byte[] bytea, String fileName, String fileType, String username) throws IOException {
        Pet pet = petRepository.findByIdAndEnabledTrue(petId).orElseThrow(() -> new NoSuchElementException("Pet not found with ID " + petId));

        if (!pet.getOwner().getUsername().equals(username) && !userHasRole("ROLE_ADMIN", username)) {
            throw new AuthorizationServiceException("Not authorized to add profile image to this pet");
        }

        ImageData imageData = new ImageData(bytea, fileName, fileType);
        imageData.setPet(pet);

        pet.setProfileImageData(imageData);
        petRepository.save(pet);
    }

    private boolean userHasRole(String role, String username) {
        if ("system".equals(username)) {
            return true;
        }
        return userRepository.findByUsername(username)
                .map(user -> user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(role)))
                .orElse(false);
    }



    public ImageData getProfileImage(Long petId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        Pet pet = petRepository.findByIdAndEnabledTrue(petId)
                .orElseThrow(() -> new IllegalStateException("Pet not found with id: " + petId));

        if (!pet.getOwner().getUsername().equals(currentUsername) && !isAdmin) {
            throw new AuthorizationServiceException("User is not authorized to view this pet's profile image");
        }

        return pet.getProfileImageData();
    }


    public void updateProfileImage(Long petId, MultipartFile multipartFile, String username) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        Pet pet = petRepository.findByIdAndEnabledTrue(petId)
                .orElseThrow(() -> new NoSuchElementException("Pet not found with ID " + petId));

        if (!pet.getOwner().getUsername().equals(username) && !isAdmin) {
            throw new AuthorizationServiceException("Not authorized to update profile image for this pet");
        }

        ImageData imageData = pet.getProfileImageData() == null ? new ImageData() : pet.getProfileImageData();
        imageData.updateImageData(multipartFile.getBytes(), multipartFile.getOriginalFilename(), multipartFile.getContentType());
        imageData.setPet(pet);

        pet.setProfileImageData(imageData);
        petRepository.save(pet);
    }

    public String getOwner(Long petId) {
        Pet pet = petRepository.findByIdAndEnabledTrue(petId).orElseThrow();
        return pet.getOwner().getUsername();
    }
}


