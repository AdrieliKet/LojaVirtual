package com.dev.loja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.loja.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	@Query(value = "select a from Produto a where a.marca.descricao like %?1%")
	Page<Produto> findByProduto(String descricao, Pageable page);
	
	Page<Produto> findAll(Pageable pageable);
}
