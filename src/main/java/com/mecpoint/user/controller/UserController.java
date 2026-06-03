package com.mecpoint.user.controller;

import com.mecpoint.user.dto.UserInDTO;
import com.mecpoint.user.dto.UserOutDTO;
import com.mecpoint.user.dto.UserRoleDTO;
import com.mecpoint.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints de gerenciamento de usuários")
public class UserController {

    private final UserService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cria um novo usuário")
    public ResponseEntity<UserOutDTO> criar(@RequestBody @Valid UserInDTO dto) {
        UserOutDTO created = service.criar(dto);

        return ResponseEntity
                .created(URI.create("/users/" + created.getId()))
                .body(created);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista todos os usuários")
    public ResponseEntity<List<UserOutDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/mecanicos")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO')")
    @Operation(summary = "Lista usuários com perfil de mecânico")
    public ResponseEntity<List<UserOutDTO>> listarMecanicos() {
        return ResponseEntity.ok(service.listarMecanicos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO')")
    @Operation(summary = "Busca um usuário por ID")
    public ResponseEntity<UserOutDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza um usuário existente")
    public ResponseEntity<UserOutDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UserInDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove um usuário")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza a permissão de um usuário")
    public ResponseEntity<UserOutDTO> atualizarRole(
            @PathVariable Long id,
            @RequestBody @Valid UserRoleDTO dto
    ) {
        return ResponseEntity.ok(service.atualizarRole(id, dto.getRole()));
    }
}