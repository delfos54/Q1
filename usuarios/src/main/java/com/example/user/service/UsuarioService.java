package com.example.user.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// IMPORTS CLAVE
import com.example.user.dto.LoginRequest;
import com.example.user.dto.RegisterRequest;
import com.example.user.dto.UsuarioResponse;
import com.example.user.model.Usuario;
import com.example.user.model.RefreshToken;
import com.example.user.repository.UsuarioRepository;
import com.example.user.repository.RefreshTokenRepository;
import com.example.user.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RefreshTokenRepository refreshRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    // 🔹 REGISTRAR
    public UsuarioResponse register(RegisterRequest req) {
        Usuario user = new Usuario();
        user.setUsername(req.getUsername());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole("USER");

        usuarioRepo.save(user);

        String access = jwtUtil.generarToken(user.getUsername(), user.getRole());
        String refresh = generarRefreshToken(user.getUsername());

        return new UsuarioResponse(access, refresh);
    }

    // 🔹 LOGIN
    public UsuarioResponse login(LoginRequest req) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        Usuario user = usuarioRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String access = jwtUtil.generarToken(user.getUsername(), user.getRole());
        String refresh = generarRefreshToken(user.getUsername());

        return new UsuarioResponse(access, refresh);
    }

    // 🔹 GENERAR REFRESH TOKEN (Método del profesor)
    private String generarRefreshToken(String username) {
        String tokenString = UUID.randomUUID().toString();

        RefreshToken rt = new RefreshToken();
        rt.setToken(tokenString);
        rt.setUsername(username);
        rt.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));

        refreshRepo.save(rt);

        return tokenString;
    }
}