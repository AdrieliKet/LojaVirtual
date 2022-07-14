package com.dev.loja.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import com.dev.loja.domain.Categoria;
import com.dev.loja.domain.Marca;
import com.dev.loja.domain.Produto;

import lombok.Data;

@Data
public class ProdutoDTO {
	private Long id;
	private String descricao;
	private Double valorVenda;
	private Double valorCusto;
	private Double quantidadeEstoque;
	private Marca marca;
	private Categoria categoria;
    private Date dataCadastro;
    
    public ProdutoDTO converter(Produto produto) {
		ProdutoDTO produtoDTO = new ProdutoDTO();
		BeanUtils.copyProperties(produto, produtoDTO);		
		return produtoDTO;
	}
	
	public Page<ProdutoDTO> converterListaProdutoDTO(Page<Produto> pageProduto){
		Page<ProdutoDTO> listaDTO = pageProduto.map(this::converter);
		return listaDTO;
	}
}

