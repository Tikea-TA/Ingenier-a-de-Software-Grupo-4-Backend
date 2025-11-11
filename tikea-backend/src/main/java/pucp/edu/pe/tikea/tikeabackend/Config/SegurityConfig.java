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
                        // Rutas públicas - sin autenticación requerida
                        .requestMatchers(
                                "/api/clientes/registro",
                                "/api/clientes/login",
                                "/api/productores/registro",
                                "/api/productores",
                                "/api/productores/**",
                                "/api/gestores",
                                "/api/gestores/**",
                                "/api/establecimientos",
                                "/api/establecimientos/**"
                        ).permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable());

        return http.build();
    }
}
