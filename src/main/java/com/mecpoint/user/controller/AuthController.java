package com.mecpoint.user.controller;

import com.mecpoint.core.config.security.service.JwtService;
import com.mecpoint.user.dto.LoginDTO;
import com.mecpoint.user.dto.TokenDTO;
import com.mecpoint.user.dto.UserInDTO;
import com.mecpoint.user.dto.UserOutDTO;
import com.mecpoint.user.entities.User;
import com.mecpoint.user.mapper.UserMapper;
import com.mecpoint.user.repositories.UserRepository;
import com.mecpoint.user.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de login e cadastro de usuários")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO dto) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        String token = jwtService.gerarToken(
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserOutDTO> register(@RequestBody UserInDTO dto) {
        return ResponseEntity.ok(userService.criar(dto));
    }
}
