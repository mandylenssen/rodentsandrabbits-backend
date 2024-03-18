package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;
    private String token;

    @BeforeEach
    public void setup() {
        token = generateTestTokenWithAuthorities();
    }

    private String generateTestTokenWithAuthorities() {
        UserDetails testUserDetails = new User("user@test.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        return jwtUtil.generateToken(testUserDetails);
    }

    @Test
    void shouldCreateBooking() throws Exception {
        String requestBody = """
                {
                    "startDate": "2024-05-21T10:00:00",
                    "endDate": "2024-05-24T10:00:00",
                    "additionalInfo": "Some additional info about the booking",
                    "petIds": [1,2]
                }
                """;

        this.mockMvc.perform(post("/bookings")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetCurrentlyPresentPets() throws Exception {
        String adminToken = generateAdminToken();
        this.mockMvc.perform(get("/bookings/currently-present")
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String generateAdminToken() {
        UserDetails adminUserDetails = new User("admin@rodentsandrabbits.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        return jwtUtil.generateToken(adminUserDetails);
    }


}
