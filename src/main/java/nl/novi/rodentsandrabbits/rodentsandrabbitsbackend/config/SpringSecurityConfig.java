package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.config;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.filter.JwtRequestFilter;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }

  @Bean
  protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

      http
              .csrf(csrf -> csrf.disable())
              .httpBasic(basic -> basic.disable())
              .cors(Customizer.withDefaults())
              .authorizeHttpRequests(auth ->
                              auth

//                .requestMatchers("/**").permitAll()

                                      .requestMatchers(HttpMethod.GET,"/users/{username}").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.POST, "/users").permitAll()

                                      .requestMatchers("/authenticated").authenticated()
                                      .requestMatchers("/authenticate").permitAll()

                                      .requestMatchers(HttpMethod.POST,"/pets/{petId}/profileImage").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.POST, "/pets").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.GET, "/pets").hasRole("ADMIN")
                                      .requestMatchers(HttpMethod.GET, "/pets/{petId}").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.PUT,"/pets/{petId}").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.GET, "/pets/user").hasRole("USER")
                                      .requestMatchers(HttpMethod.DELETE, "/pets/{petId}").hasAnyRole("ADMIN", "USER")

                                      .requestMatchers(HttpMethod.GET,"/pets/{petId}/profileImage").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.PUT,"/pets/{petId}/profileImage").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.GET,"/pets/{petId}/owner").hasRole("ADMIN")



                                      .requestMatchers(HttpMethod.POST, "/bookings").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.GET, "/bookings").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.GET, "/bookings/user/{username}").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.GET,"/bookings/currently-present").hasRole("ADMIN")
                                      .requestMatchers(HttpMethod.PUT,"/bookings").hasRole("ADMIN")



                                      .requestMatchers(HttpMethod.GET,"/logbooks/user/{username}/id").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.GET,"/logbooks/pets/{petId}/owner").hasRole("ADMIN")
                                      .requestMatchers(HttpMethod.POST,"/logbooks/{logbookId}/logs").hasAnyRole("ADMIN")
                                      .requestMatchers(HttpMethod.GET,"/logbooks/user/{username}").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.GET,"/logbooks/{logbookId}").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers(HttpMethod.DELETE,"/logbooks/{logbookId}/logs/{logId}").hasRole("ADMIN")
                                      .requestMatchers(HttpMethod.POST,"/logbooks/{logbookId}/logs/{logId}/images").hasRole("ADMIN")
                                      .requestMatchers(HttpMethod.GET,"/logbooks/{logbookId}/logs/{logId}/images").hasAnyRole("ADMIN", "USER")






                                      .requestMatchers(HttpMethod.GET, "/bookings/availability").permitAll()
                                      .requestMatchers(HttpMethod.GET, "/bookings/unavailable-dates").permitAll()
                                      .requestMatchers(HttpMethod.GET, "/bookings/user").permitAll()
                                      .anyRequest().denyAll()
              )
              .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
      http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
      return http.build();
  }


}
