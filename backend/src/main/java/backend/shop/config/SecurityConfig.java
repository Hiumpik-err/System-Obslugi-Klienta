package backend.shop.config;

import backend.shop.model.UserProfiler;
import backend.shop.model.Users;
import backend.shop.service.UserProfilerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserProfilerService userProfilerService;

    @Bean
    public SecurityFilterChain userDetails(HttpSecurity http){
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        r ->
                                r.requestMatchers("/api/register", "/api/login", "/api/forgot-password").permitAll()
                                .anyRequest().authenticated())
                .formLogin(f ->
                        f.loginProcessingUrl("/api/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .successHandler(((request, response, authentication) -> {
                                    /*UserProfiler userProfiler = (UserProfiler) authentication.getPrincipal();
                                    Users user = (Users) userProfiler.getUser();

                                    response.setStatus(HttpServletResponse.SC_OK);
                                    response.setContentType("application/json");
                                    response.setCharacterEncoding("UTF-8");

                                    var responseBody = Map.of(
                                                "userId", user.getUserId(),
                                                "firstName", user.getFirstName() != null ? user.getFirstName() : "",
                                                "lastName", user.getLastName() != null ? user.getLastName() : "",
                                                "email", user.getEmail(),
                                                "password", user.getPassword(),
                                                "role", user.getRole() != null ? user.getRole() : java.util.Collections.emptySet(),
                                                "accountCreationDate", user.getAccountCreationDate() != null ? user.getAccountCreationDate().toString() : "",
                                                "deliveryDetails", user.getDeliveryDetails() != null ? user.getDeliveryDetails() : ""
                                    );
                                    ObjectMapper om = new ObjectMapper();
                                    om.writeValue(response.getWriter(), responseBody);*/
                                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                                }))
                                .failureHandler(((request, response, exception) -> {
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                })
                                )
                )
                .exceptionHandling(e -> e
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized - Brak dostepu.");
                })
                )
                .logout(l ->
                        l.logoutUrl("/api/logout")
                                .logoutSuccessHandler(((request, response, authentication) -> {
                                    response.setStatus(HttpServletResponse.SC_OK);
                                }))
                )
                .build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(BCryptPasswordEncoder passwordEncoder){
        var provider = new DaoAuthenticationProvider(userProfilerService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    @Bean
    public BCryptPasswordEncoder getBcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
