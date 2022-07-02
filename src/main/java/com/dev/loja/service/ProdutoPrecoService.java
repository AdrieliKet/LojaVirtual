package com.dev.loja.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.dev.loja.domain.ProdutoPreco;
import com.dev.loja.repository.ProdutoPrecoRepository;

public class ProdutoPrecoService {
	@Autowired
	private ProdutoPrecoRepository produtoPrecoRepository;
	
	 public ProdutoPreco save(ProdutoPreco produtoPreco) {
		 return produtoPrecoRepository.save(produtoPreco);
	 }

}
