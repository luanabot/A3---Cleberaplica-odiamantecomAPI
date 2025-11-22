package com.diamante.estoque.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;
import java.util.List;
import com.diamante.estoque.dto.MovimentacaoDTO;
import com.diamante.estoque.service.MovimentacaoService;

@RestController
@RequestMapping("/api/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @GetMapping
    public List<MovimentacaoDTO> listar() {
        return movimentacaoService.listarTodas();
    }
}

