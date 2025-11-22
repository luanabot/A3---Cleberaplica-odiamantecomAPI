package com.diamante.estoque.repository;

import com.diamante.estoque.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByProdutoIdOrderByDataMovimentacaoDesc(Long produtoId);

    List<Movimentacao> findAllByOrderByDataMovimentacaoDesc();
}