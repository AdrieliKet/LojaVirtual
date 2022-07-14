package com.dev.loja.dto.request;


import org.springframework.beans.BeanUtils;


import com.dev.loja.domain.Categoria;
import com.dev.loja.domain.Marca;
import com.dev.loja.domain.Produto;

import lombok.Data;

@Data
public class ProdutoDTORequest {
	
	private String descricao;
	private Double valorVenda;
	private Double valorCusto;
	private Double quantidadeEstoque;
	private Marca marca;
	private Categoria categoria;
	
	public Produto converter(ProdutoDTORequest produtoDTO) {
		Produto produto = new Produto();
		BeanUtils.copyProperties(produtoDTO, produto);
		return produto;
	}
}
