package com.diamante.estoque.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    @Column(nullable = false)
    private Integer quantidade;

    private String responsavel;

    private LocalDateTime dataMovimentacao;

    @PrePersist
    protected void onCreate() {
        dataMovimentacao = LocalDateTime.now();
    }

    // A ENUM QUE ESTÁ FALTANDO ⬇⬇⬇
    public enum TipoMovimentacao {
        ENTRADA,
        SAIDA
    }
}
