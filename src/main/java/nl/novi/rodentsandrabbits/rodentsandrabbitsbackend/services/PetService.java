package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.PetDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.PetRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class PetService {

    private final PetRepository petRepository;

    private final UserRepository userRepository;

    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public PetDto addPet(PetDto dto) {
        Pet pet = transferToPet(dto);
        petRepository.save(pet);
        return transferToDto(pet);
    }


    public PetDto updatePet(Long petId, PetDto dto, String username) {
        Pet pet = petRepository.findById(petId).orElseThrow();

        if (!username.equals(pet.getOwner().getUsername())) {
            throw new AuthorizationServiceException("Pet does not belong to user");
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
        Pet pet = petRepository.findById(id).orElseThrow();

        return transferToDto(pet);
    }

    public List<PetDto> getAllPets() {
        List<Pet> pets = petRepository.findAll();
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
        Pet pet = petRepository.findById(id).orElseThrow();

        return transferToDto(pet);
    }

//
//    public void deletePet(long id) {
//        petRepository.deleteById(id);
//    }

    public List<PetDto> getPetByOwnerId(String username) {
        List<Pet> pets = petRepository.findAllByOwnerUsername(username);
        return transferPetListToDtoList(pets);
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


    public List<PetDto> getAllPetsByName(String name) {
        List<Pet> pets = petRepository.findAllPetsByName(name);
        return transferPetListToDtoList(pets);
    }

    public List<PetDto> getAllPetsByUsername(String username) {
    List<Pet> pets = petRepository.findAllByOwnerUsername(username);
    return transferPetListToDtoList(pets);
    }
}


