package com.upiiz.discounts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Configuración de la seguridad personalizada
        return httpSecurity
                .csrf(csrf -> csrf
                                .ignoringRequestMatchers("/api/v2/discounts/**") // Ignorar CSRF en rutas
                        // específicas
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http
                            .requestMatchers("/v3/api-docs/**").permitAll()
                            .requestMatchers("/swagger-ui/**").permitAll()
                            .requestMatchers("/swagger-resources/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v2/discounts/**").hasAnyAuthority("READ")
                            .requestMatchers(HttpMethod.POST, "/api/v2/discounts/**").hasAnyAuthority("CREATE")
                            .requestMatchers(HttpMethod.PUT, "/api/v2/discounts/**").hasAnyAuthority("UPDATE")
                            .requestMatchers(HttpMethod.DELETE, "/api/v2/discounts/**").hasAnyAuthority("DELETE")
                            .anyRequest().denyAll();
                })
                .build();
    }

    //authentication manager - Lo obtenemos de una instancia que ya existe
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // authentication provider - DAO - Va a proporcionar la autenticacion
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    // Password encoder
    public PasswordEncoder passwordEncoder() {
        //return  new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    // UserDetailsService - base de datos o usuarios en memoria
    @Bean
    public UserDetailsService userDetailsService() {
        // Definir usuario en memoria por el momento
        UserDetails usuarioAdmin =
                User.withUsername("Admin")
                .password(passwordEncoder()
                .encode("admin1234"))
                .roles("ADMIN").authorities(getAuthorities("ADMIN"))
                .build();
        UserDetails usuarioUser =
                User.withUsername("User")
                .password(passwordEncoder().encode("user1234"))
                .roles("USER").authorities(getAuthorities("USER"))
                .build();
        UserDetails usuarioModerator =
                User.withUsername("Moderator")
                .password(passwordEncoder()
                .encode("moderator1234"))
                .roles("MODERATOR")
                .authorities(getAuthorities("MODERATOR"))
                .build();
        UserDetails usuarioEditor =
                User.withUsername("Editor")
                .password(passwordEncoder()
                .encode("editor1234"))
                .roles("EDITOR").authorities(getAuthorities("EDITOR"))
                .build();
        UserDetails usuarioDeveloper =
                User.withUsername("Developer")
                .password(passwordEncoder()
                .encode("developer1234"))
                .roles("DEVELOPER").authorities(getAuthorities("DEVELOPER"))
                .build();
        UserDetails usuarioAnalyst =
                User.withUsername("Analyst")
                .password(passwordEncoder()
                .encode("analyst1234"))
                .roles("ANALYST").authorities(getAuthorities("ANALYST"))
                .build();

        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(usuarioAdmin);
        userDetailsList.add(usuarioUser);
        userDetailsList.add(usuarioModerator);
        userDetailsList.add(usuarioEditor);
        userDetailsList.add(usuarioDeveloper);
        userDetailsList.add(usuarioAnalyst);
        return new InMemoryUserDetailsManager(userDetailsList);
    }

    private String[] getAuthorities(String role) {
        switch (role) {
            case "ADMIN":
                return new String[]{"READ", "CREATE", "UPDATE", "DELETE"};
            case "USER":
                return new String[]{"READ"};
            case "MODERATOR":
                return new String[]{"READ", "UPDATE"};
            case "EDITOR":
                return new String[]{"UPDATE"};
            case "DEVELOPER":
                return new String[]{"READ", "CREATE", "UPDATE", "DELETE", "CREATE-USER"};
            case "ANALYST":
                return new String[]{"READ", "DELETE"};
            default:
                return new String[]{};
        }
    }
}