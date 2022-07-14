package com.dev.loja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.loja.domain.PermissaoUsuario;

public interface PermissaoUsuarioRepository extends JpaRepository<PermissaoUsuario, Long> {
	
	@Query("select p from PermissaoUsuario p where p.usuario.id=?1")
	public List<PermissaoUsuario> buscarPermissaoUsuario(Long idUsuario);	
	
}