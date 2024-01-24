package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.PetDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PetService {

    private final PetRepository petRepository;


    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    public PetDto addPet(PetDto dto) {
        Pet pet = transferToPet(dto);
        petRepository.save(pet);
        return transferToDto(pet);
    }

    public Pet transferToPet(PetDto dto) {
        var pet = new Pet();

        pet.setName(dto.getName());
        pet.setBirthday(dto.getBirthday());
        pet.setSpecies(dto.getSpecies());
        pet.setGender(dto.getGender());
        pet.setDetails(dto.getDetails());
        pet.setMedication(dto.getMedication());
        pet.setDiet(dto.getDiet());

        return pet;
    }



    public PetDto transferToDto(Pet pet){
        PetDto dto = new PetDto();

        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthday(pet.getBirthday());
        dto.setSpecies(pet.getSpecies());
        dto.setGender(pet.getGender());
        dto.setDetails(pet.getDetails());
        dto.setMedication(pet.getMedication());
        dto.setDiet(pet.getDiet());
        return dto;
    }

}
