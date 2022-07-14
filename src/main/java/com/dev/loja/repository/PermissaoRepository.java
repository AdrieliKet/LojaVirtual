package com.dev.loja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.loja.domain.Permissao;

 
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
	
	@Query(value = "from Permissao p where p.descricao=?1")
	public List<Permissao> buscarPermissaoNome(String descricao);
	
}