package com.diamante.estoque.controller;

import com.diamante.estoque.dto.*;
import com.diamante.estoque.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Gerenciamento de produtos do estoque")
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar produto por código")
    public ResponseEntity<ProdutoResponseDTO> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(produtoService.buscarPorCodigo(codigo));
    }

    @GetMapping("/estoque-baixo")
    @Operation(summary = "Listar produtos com estoque baixo")
    public ResponseEntity<List<ProdutoResponseDTO>> listarEstoqueBaixo() {
        return ResponseEntity.ok(produtoService.listarEstoqueBaixo());
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo produto")
    public ResponseEntity<ProdutoResponseDTO> cadastrar(@Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO response = produtoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.ok(produtoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/baixa")
    @Operation(summary = "Dar baixa no estoque (venda)")
    public ResponseEntity<ProdutoResponseDTO> darBaixa(
            @PathVariable Long id,
            @Valid @RequestBody EstoqueOperacaoDTO dto) {
        return ResponseEntity.ok(produtoService.darBaixa(id, dto));
    }

    @PatchMapping("/{id}/repor")
    @Operation(summary = "Repor estoque (entrada)")
    public ResponseEntity<ProdutoResponseDTO> reporEstoque(
            @PathVariable Long id,
            @Valid @RequestBody EstoqueOperacaoDTO dto) {
        return ResponseEntity.ok(produtoService.reporEstoque(id, dto));
    }
}