package com.example.user.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user.dto.LoginRequest;
import com.example.user.dto.RegisterRequest;
import com.example.user.dto.UserResponse;
import com.example.user.model.user;
import com.example.user.repository.UsuarioRepository;
import com.example.user.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class userService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    // 🔹 REGISTRAR
    public UserResponse registrar(RegisterRequest req) {
        user usuario = new user();
        usuario.setusername(req.getusername());
        usuario.setpassword(encoder.encode(req.getpassword()));
        usuario.setrole("USER");

        usuarioRepo.save(usuario);

        // Usamos los nombres en español que pusimos en tu JwtUtil
        String access = jwtUtil.generarToken(usuario.getUsername(), usuario.getRole());
        String refresh = jwtUtil.generarRefreshToken(usuario.getUsername());

        return new UserResponse(access, refresh);
    }

    // 🔹 LOGIN
    public UserResponse login(LoginRequest req) {
        // Esto verifica que el usuario exista y la clave sea correcta
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getusername(), req.getpassword())
        );

        // Si llegó aquí, los datos están bien. Buscamos al usuario para sacar su rol.
        user usuario = usuarioRepo.findByUsername(req.getusername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String access = jwtUtil.generarToken(usuario.getUsername(), usuario.getRole());
        String refresh = jwtUtil.generarRefreshToken(usuario.getUsername());

        return new UserResponse(access, refresh);
    }
}