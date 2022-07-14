package com.dev.loja.service;


import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.dev.loja.domain.PermissaoUsuario;
import com.dev.loja.exception.BadResourceException;
import com.dev.loja.exception.ResourceAlreadyExistsException;
import com.dev.loja.exception.ResourceNotFoundException;
import com.dev.loja.repository.PermissaoUsuarioRepository;


 
@Service
public class PermissaoUsuarioService {
    
    @Autowired
    private PermissaoUsuarioRepository permissaoUsuarioRepository;
    
    private boolean existsById(Long id) {
        return permissaoUsuarioRepository.existsById(id);
    }
    
    
    public PermissaoUsuario findById(Long id) throws ResourceNotFoundException {
		PermissaoUsuario permissaoUsuario = permissaoUsuarioRepository.findById(id).orElse(null);
		if(permissaoUsuario == null) {
			throw new ResourceNotFoundException("Produto não foi encontrado com o id: "+id);
		}else {
			return permissaoUsuario;
		}
	}
	
	public Page<PermissaoUsuario> findAll(Pageable pageable){
		return permissaoUsuarioRepository.findAll(pageable);
	}
	
	public PermissaoUsuario save(PermissaoUsuario permissaoUsuario) throws BadResourceException, ResourceAlreadyExistsException{
		if(permissaoUsuario.getId() != null) {
			if(existsById(permissaoUsuario.getId())) {
				throw new ResourceAlreadyExistsException("Permissao Usuario com o id: "+permissaoUsuario.getId()+"\n já existe");
			}
			return permissaoUsuarioRepository.save(permissaoUsuario);			
		}else {
			BadResourceException exc = new BadResourceException("Erro ao salvar Permissao Usuario");
			exc.addErrorMessages("Permissao Usuario está vazio ou é nulo");
			throw exc;
		}
	}
	
	public void update(PermissaoUsuario permissaoUsuario) throws BadResourceException, ResourceNotFoundException{
		if(permissaoUsuario.getId() != null) {
			if(!existsById(permissaoUsuario.getId())) {
				throw new ResourceNotFoundException("Produto não encontrado com o id: "+permissaoUsuario.getId());
			}
			permissaoUsuarioRepository.save(permissaoUsuario);
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException{
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Produto não encontrado com o id: "+id);
		}else {
			permissaoUsuarioRepository.deleteById(id);
		}
	}
    
    public Long count() {
        return permissaoUsuarioRepository.count();
    }
}