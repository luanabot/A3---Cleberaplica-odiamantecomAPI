package com.diamante.estoque.service;

import com.diamante.estoque.dto.MovimentacaoDTO;
import com.diamante.estoque.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;

    public List<MovimentacaoDTO> listarTodas() {
        return movimentacaoRepository.findAllByOrderByDataMovimentacaoDesc()
                .stream()
                .map(MovimentacaoDTO::fromEntity)
                .toList();
    }

    public List<MovimentacaoDTO> listarPorProduto(Long produtoId) {
        return movimentacaoRepository.findByProdutoIdOrderByDataMovimentacaoDesc(produtoId)
                .stream()
                .map(MovimentacaoDTO::fromEntity)
                .toList();
    }
}