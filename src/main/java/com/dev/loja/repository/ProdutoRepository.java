package com.dev.loja.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.loja.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	@Query(value = "select a from Produto a where a.descricao like %?1%")
	Page<Produto> findByDescricao(String descricao, Pageable page);
	
	Page<Produto> findAll(Pageable pageable);
	
	@Query("select p from Produto p where p.categoria.id=?1")
	public List<Produto> buscarProdutosCategoria(Long idCategoria);	
	
	@Query("select p from Produto p where p.categoria.descricao like %?1%")
	public List<Produto> buscarProdutosNomeCategoria(Long nomeCategoria);	
	
	@Query("select p from Produto p where p.marca.id=?1")
	public List<Produto> buscarProdutosMarca(Long idMarca);	
	
	@Query("select p from Produto p where p.marca.descricao like %?1%")
	public List<Produto> buscarProdutosNomeMarca(Long nomeMarca);	
}
