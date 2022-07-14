package com.dev.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.domain.ProdutoPreco;

 
public interface ProdutoPrecoRepository extends JpaRepository<ProdutoPreco, Long> {
	
	
}