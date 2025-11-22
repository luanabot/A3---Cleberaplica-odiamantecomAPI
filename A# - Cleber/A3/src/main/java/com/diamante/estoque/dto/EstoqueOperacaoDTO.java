package com.diamante.estoque.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueOperacaoDTO {

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantidade;

    @Size(max = 100, message = "Responsável deve ter no máximo 100 caracteres")
    private String responsavel = "Sistema";
}