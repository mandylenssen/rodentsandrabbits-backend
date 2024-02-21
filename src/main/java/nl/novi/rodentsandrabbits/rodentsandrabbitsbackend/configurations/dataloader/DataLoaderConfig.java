package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.configurations.dataloader;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoaderConfig {

    private final PetService petService;

    @Autowired
    public DataLoaderConfig(PetService petService) {
        this.petService = petService;
    }

    @Bean
    @DependsOnDatabaseInitialization
    public DataLoader dataLoader() {
        return new DataLoader(petService);
    }
}
