package com.example.pago.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Asegúrate de que esta ruta coincida con donde guardaste tu JwtFilter
import com.example.pago.security.JwtFilter; 
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor // CAMBIO 1: Agregamos esto para poder inyectar
public class SecurityConfig {

    private final JwtFilter jwtFilter; // CAMBIO 2: Llamamos a tu guardia (JwtFilter)

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // ❌ CSRF off (API REST)
            .csrf(csrf -> csrf.disable())

            // 🔐 SIN sesiones (clave para JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 🔓 Endpoints públicos
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
            )
            
            // CAMBIO 3: Ponemos al guardia en la puerta antes de que Spring revise las contraseñas
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}