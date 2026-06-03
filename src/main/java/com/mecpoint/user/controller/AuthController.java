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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de login, cadastro e usuário autenticado")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    @Operation(summary = "Realiza login e retorna token JWT")
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
    @Operation(summary = "Cadastra um novo usuário")
    public ResponseEntity<UserOutDTO> register(@RequestBody UserInDTO dto) {
        return ResponseEntity.ok(userService.criar(dto));
    }

    @GetMapping("/me")
    @Operation(summary = "Retorna os dados do usuário autenticado")
    public ResponseEntity<UserOutDTO> me(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado"));

        return ResponseEntity.ok(userMapper.toOutDTO(user));
    }
}