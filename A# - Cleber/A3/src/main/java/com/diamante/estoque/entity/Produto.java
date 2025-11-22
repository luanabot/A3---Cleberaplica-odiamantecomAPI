package com.diamante.estoque.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(length = 255)
    private String caracteristicas;

    @Column(nullable = false)
    private Integer quantidadeEstoque = 0;

    @Column(nullable = false)
    private Integer estoqueMinimo = 3;

    @Column(updatable = false)
    private LocalDateTime dataCadastro;

    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public boolean isEstoqueBaixo() {
        return quantidadeEstoque <= estoqueMinimo;
    }
}