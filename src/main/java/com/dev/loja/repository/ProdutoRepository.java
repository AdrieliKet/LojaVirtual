package com.dev.loja.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.loja.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	@Query(value = "select a from Produto a where a.marca.descricao like %?1%")
	Page<Produto> findByProduto(String descricao, Pageable page);
	
	Page<Produto> findAll(Pageable pageable);
	
	@Query(nativeQuery = true, value="select p from Produto p where p.categoria.id=?1 ")
	public List<Produto> buscarProdutosCategoria(Long idCategoria);
}
