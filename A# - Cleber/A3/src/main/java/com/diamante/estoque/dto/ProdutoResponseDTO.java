package com.diamante.estoque.dto;

import com.diamante.estoque.entity.Produto;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoResponseDTO {

    private Long id;
    private String codigo;
    private String nome;
    private String descricao;
    private String caracteristicas;
    private Integer quantidadeEstoque;
    private Integer estoqueMinimo;
    private boolean estoqueBaixo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

    public static ProdutoResponseDTO fromEntity(Produto p) {
        return ProdutoResponseDTO.builder()
                .id(p.getId())
                .codigo(p.getCodigo())
                .nome(p.getNome())
                .descricao(p.getDescricao())
                .caracteristicas(p.getCaracteristicas())
                .quantidadeEstoque(p.getQuantidadeEstoque())
                .estoqueMinimo(p.getEstoqueMinimo())
                .estoqueBaixo(p.isEstoqueBaixo())
                .dataCadastro(p.getDataCadastro())
                .dataAtualizacao(p.getDataAtualizacao())
                .build();
    }
}