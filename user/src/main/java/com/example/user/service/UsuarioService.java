package com.example.user.service;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.user.repository.RefreshTokenRepository;
import com.example.user.repository.UsuarioRepository;
import com.example.user.security.jwtUtil; // 
import com.example.user.dto.UsuarioResponse;
import com.example.user.dto.RegisterRequest;
import com.example.user.dto.LoginRequest;
import com.example.user.model.RefreshToken;
import com.example.user.model.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RefreshTokenRepository refreshTokenRepo;
    private final PasswordEncoder encoder;          
    private final AuthenticationManager authManager;
    private final jwtUtil jwtUtil;                  

    public UsuarioResponse register(RegisterRequest req) {

        Usuario user = new Usuario();
        user.setUsername(req.getUsername());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole("ROLE_USER");

        usuarioRepo.save(user);

        String access = jwtUtil.generarToken(user.getUsername(), user.getRole());
        String refresh = generarRefreshToken(user.getUsername());

        return new UsuarioResponse(access, refresh);
    }

    // 🔹 LOGIN (Corregido para tu UsuarioResponse)
    public UsuarioResponse login(LoginRequest req) {

        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        // Usamos orElseThrow porque es más seguro que .get()
        Usuario user = usuarioRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String access = jwtUtil.generarToken(user.getUsername(), user.getRole());
        String refresh = generarRefreshToken(user.getUsername());

        return new UsuarioResponse(access, refresh);
    }

    // 🔹 REFRESH (Corregido para tu UsuarioResponse)
    public UsuarioResponse refresh(String refreshToken) {

        // Asegúrate de que tu repositorio se llame refreshTokenRepo o refreshRepo
        RefreshToken token = refreshTokenRepo.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh inválido"));

        if (!jwtUtil.esValido(refreshToken) || !jwtUtil.esRefreshToken(refreshToken)) {
            throw new RuntimeException("Refresh token inválido");
        }

        Usuario user = usuarioRepo.findByUsername(token.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String newAccess = jwtUtil.generarToken(user.getUsername(), user.getRole());

        return new UsuarioResponse(newAccess, refreshToken);
    }

    // 🔹 GENERAR REFRESH TOKEN
    private String generarRefreshToken(String username) {

        String token = UUID.randomUUID().toString();

        RefreshToken rt = new RefreshToken();
        rt.setToken(token);
        rt.setUsername(username);
        // Expira en 24 horas
       rt.setExpiryDate(new java.sql.Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));

        refreshTokenRepo.save(rt);

        return token;
    }
}