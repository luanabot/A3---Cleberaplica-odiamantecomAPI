package com.diamante.estoque.service;

import com.diamante.estoque.dto.*;
import com.diamante.estoque.entity.*;
import com.diamante.estoque.entity.Movimentacao.TipoMovimentacao;
import com.diamante.estoque.exception.BusinessException;
import com.diamante.estoque.exception.ResourceNotFoundException;
import com.diamante.estoque.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::fromEntity)
                .toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = findProdutoOrThrow(id);
        return ProdutoResponseDTO.fromEntity(produto);
    }

    public ProdutoResponseDTO buscarPorCodigo(String codigo) {
        Produto produto = produtoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com código: " + codigo));
        return ProdutoResponseDTO.fromEntity(produto);
    }

    @Transactional
    public ProdutoResponseDTO cadastrar(ProdutoRequestDTO dto) {
        if (produtoRepository.existsByCodigo(dto.getCodigo())) {
            throw new BusinessException("Já existe produto com o código: " + dto.getCodigo());
        }

        Produto produto = Produto.builder()
                .codigo(dto.getCodigo())
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .caracteristicas(dto.getCaracteristicas())
                .quantidadeEstoque(dto.getQuantidadeEstoque())
                .estoqueMinimo(dto.getEstoqueMinimo() != null ? dto.getEstoqueMinimo() : 3)
                .build();

        produto = produtoRepository.save(produto);

        // Registra movimentação inicial
        if (dto.getQuantidadeEstoque() > 0) {
            registrarMovimentacao(produto, TipoMovimentacao.ENTRADA,
                    dto.getQuantidadeEstoque(), "Sistema - Cadastro Inicial");
        }

        return ProdutoResponseDTO.fromEntity(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = findProdutoOrThrow(id);

        // Verifica código duplicado (se mudou)
        if (!produto.getCodigo().equals(dto.getCodigo())
                && produtoRepository.existsByCodigo(dto.getCodigo())) {
            throw new BusinessException("Já existe produto com o código: " + dto.getCodigo());
        }

        produto.setCodigo(dto.getCodigo());
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setCaracteristicas(dto.getCaracteristicas());
        produto.setEstoqueMinimo(dto.getEstoqueMinimo());

        return ProdutoResponseDTO.fromEntity(produtoRepository.save(produto));
    }

    @Transactional
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    @Transactional
    public ProdutoResponseDTO darBaixa(Long id, EstoqueOperacaoDTO dto) {
        Produto produto = findProdutoOrThrow(id);

        if (dto.getQuantidade() > produto.getQuantidadeEstoque()) {
            throw new BusinessException(
                    String.format("Estoque insuficiente. Disponível: %d, Solicitado: %d",
                            produto.getQuantidadeEstoque(), dto.getQuantidade()));
        }

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - dto.getQuantidade());
        produto = produtoRepository.save(produto);

        registrarMovimentacao(produto, TipoMovimentacao.SAIDA,
                dto.getQuantidade(), dto.getResponsavel());

        return ProdutoResponseDTO.fromEntity(produto);
    }

    @Transactional
    public ProdutoResponseDTO reporEstoque(Long id, EstoqueOperacaoDTO dto) {
        Produto produto = findProdutoOrThrow(id);

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + dto.getQuantidade());
        produto = produtoRepository.save(produto);

        registrarMovimentacao(produto, TipoMovimentacao.ENTRADA,
                dto.getQuantidade(), dto.getResponsavel());

        return ProdutoResponseDTO.fromEntity(produto);
    }

    public List<ProdutoResponseDTO> listarEstoqueBaixo() {
        return produtoRepository.findProdutosComEstoqueBaixo()
                .stream()
                .map(ProdutoResponseDTO::fromEntity)
                .toList();
    }

    private Produto findProdutoOrThrow(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
    }

    private void registrarMovimentacao(Produto produto, TipoMovimentacao tipo,
                                       Integer quantidade, String responsavel) {
        Movimentacao mov = Movimentacao.builder()
                .produto(produto)
                .tipo(tipo)
                .quantidade(quantidade)
                .responsavel(responsavel)
                .build();
        movimentacaoRepository.save(mov);
    }
}