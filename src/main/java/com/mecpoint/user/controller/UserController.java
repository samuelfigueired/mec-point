package com.mecpoint.user.controller;

import com.mecpoint.user.dto.UserInDTO;
import com.mecpoint.user.dto.UserOutDTO;
import com.mecpoint.user.dto.UserRoleDTO;
import com.mecpoint.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<UserOutDTO> criar(@RequestBody UserInDTO dto) {
        UserOutDTO created = service.criar(dto);

        return ResponseEntity
                .created(URI.create("/users/" + created.getId()))
                .body(created);
    }

    @GetMapping
    public ResponseEntity<List<UserOutDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserOutDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserOutDTO> atualizar(
            @PathVariable Long id,
            @RequestBody UserInDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserOutDTO> atualizarRole(
            @PathVariable Long id,
            @RequestBody UserRoleDTO dto
    ) {
        return ResponseEntity.ok(service.atualizarRole(id, dto.getRole()));
    }


}
