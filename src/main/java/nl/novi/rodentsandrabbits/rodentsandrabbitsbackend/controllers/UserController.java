package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;

import jakarta.validation.Valid;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.UserDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto dto) {
        String newUsername = userService.createUser(dto);
        //userService.addAuthority(newUsername, "ROLE_USER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {

        UserDto optionalUser = userService.getUser(username);


        return ResponseEntity.ok().body(optionalUser);

    }


    @GetMapping(value = "")
    public ResponseEntity<String> getUsers() {


        return ResponseEntity.ok().body("Hello World..");
    }



}
