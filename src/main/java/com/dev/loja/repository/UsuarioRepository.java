package com.dev.loja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.loja.domain.Usuario;

 
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query(value = "select a from Usuario a where a.nome like %?1%")
	Page<Usuario> findByNome(String nome, Pageable page);
	
	@Query(value = "select a from Usuario a where a.cpf like %?1%")
	Page<Usuario> findByCpf(String cpf, Pageable page);
	
	@Query(value = "select a from Usuario a where a.email like %?1%")
	Page<Usuario> findByEmail(String email, Pageable page);
	
}