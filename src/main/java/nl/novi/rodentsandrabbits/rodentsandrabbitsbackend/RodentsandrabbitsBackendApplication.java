package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class} //disabled login met wachtwrd
)
public class RodentsandrabbitsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RodentsandrabbitsBackendApplication.class, args);
	}

}
