package com.diamante.estoque.dto;

import com.diamante.estoque.entity.Movimentacao;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoDTO {

    private Long id;
    private Long produtoId;
    private String produtoCodigo;
    private String produtoNome;
    private String tipo;
    private Integer quantidade;
    private String responsavel;
    private LocalDateTime dataMovimentacao;

    public static MovimentacaoDTO fromEntity(Movimentacao m) {
        return MovimentacaoDTO.builder()
                .id(m.getId())
                .produtoId(m.getProduto().getId())
                .produtoCodigo(m.getProduto().getCodigo())
                .produtoNome(m.getProduto().getNome())
                .tipo(m.getTipo().name())
                .quantidade(m.getQuantidade())
                .responsavel(m.getResponsavel())
                .dataMovimentacao(m.getDataMovimentacao())
                .build();
    }
}