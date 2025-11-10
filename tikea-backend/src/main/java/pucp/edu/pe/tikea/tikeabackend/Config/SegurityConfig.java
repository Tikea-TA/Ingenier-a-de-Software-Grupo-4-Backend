package pucp.edu.pe.tikea.tikeabackend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SegurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/clientes/registro", "/api/clientes/login").permitAll()
                        .anyRequest().permitAll() // ← deja toda tu API libre mientras desarrollas
                )
                .formLogin(form -> form.disable()); // ← evita redirección a /login

        return http.build();
    }
}
