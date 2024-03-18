package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.UserDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Authority;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void loadUserByUsername() {
    String username = "user@test.com";
    String password = "password";

    Set<Authority> authorities = new HashSet<>();
    Authority authority = new Authority();
    authority.setAuthority("ROLE_USER");
    authorities.add(authority);

    UserDto userDto = new UserDto();
    userDto.setUsername(username);
    userDto.setPassword(password);
    userDto.setAuthorities(authorities);

    when(userService.getUser(username)).thenReturn(userDto);

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
    }

}