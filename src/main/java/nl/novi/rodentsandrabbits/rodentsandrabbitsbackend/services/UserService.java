package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.UserDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Authority;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.User;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.UserRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.utils.RandomStringGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            dto = fromUser(user.get());
        }else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }


    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        userDto.setUsername(userDto.email);
        userDto.setEnabled(true);

        if (userExists(userDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User newUser = userRepository.save(toUser(userDto));
        return newUser.getUsername();
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }



    private User toUser(UserDto userDto) {
        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());

        return user;
    }



    public static UserDto fromUser(User user) {
        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.firstName = user.getFirstName();
        dto.lastName = user.getLastName();
        dto.phoneNumber = user.getPhoneNumber();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();

        return dto;
    }

        public void addAuthority(String username, String authority) {

            if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
            User user = userRepository.findById(username).get();
            user.addAuthority(new Authority(username, authority));
            userRepository.save(user);
        }

        }
