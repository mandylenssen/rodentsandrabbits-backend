package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.UserDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.filter.JwtRequestFilter;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.CustomUserDetailsService;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;



    @Test
    void shouldCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("user@test.com");
        userDto.setPassword("test12345");
        userDto.setFirstName("James");
        userDto.setLastName("Smith");
        userDto.setEmail("user@test.com");
        userDto.setPhoneNumber("0612345678");
        userDto.setEnabled(true);

        Mockito.when(userService.createUser(Mockito.any(UserDto.class))).thenReturn(userDto.getUsername());

        String expectedLocation = "http://localhost/users/" + userDto.getUsername();

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", expectedLocation));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void shouldGetUserByUsername() throws Exception {
        String username = "user@test.com";
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setEmail("user@test.com");

        Mockito.when(userService.getUser(username)).thenReturn(userDto);

        mockMvc.perform(get("/users/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(userDto)));
    }
}