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
                                        .requestMatchers(HttpMethod.POST, "/users").hasAnyRole("ADMIN", "USER")
                                        .requestMatchers(HttpMethod.GET,"/users").hasAnyRole("ADMIN")
                                        .requestMatchers(HttpMethod.GET,"/users/**").hasRole("ADMIN")
                                      .requestMatchers(HttpMethod.POST, "/pets").hasAnyRole("ADMIN", "USER")

                                      .requestMatchers( "/reservations", "/diary", "/diarylogs").hasAnyRole("ADMIN", "USER")
                                      .requestMatchers("/authenticated").authenticated()
                                      .requestMatchers("/authenticate").permitAll()
                                      .anyRequest().denyAll()
              )
              .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
      http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
      return http.build();
  }


}
