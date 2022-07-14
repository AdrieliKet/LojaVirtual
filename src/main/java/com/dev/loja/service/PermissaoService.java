package com.dev.loja.service;


import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dev.loja.domain.Permissao;
import com.dev.loja.exception.BadResourceException;
import com.dev.loja.exception.ResourceAlreadyExistsException;
import com.dev.loja.exception.ResourceNotFoundException;
import com.dev.loja.repository.PermissaoRepository;

 
@Service
public class PermissaoService {
    
    @Autowired
    private PermissaoRepository permissaoRepository;
    
    private boolean existsById(Long id) {
        return permissaoRepository.existsById(id);
    }
    
    public Permissao findById(Long id) throws ResourceNotFoundException {
		Permissao permissao = permissaoRepository.findById(id).orElse(null);
		if(permissao == null) {
			throw new ResourceNotFoundException("Permissão não foi encontrado com o id: "+id);
		}else {
			return permissao;
		}
	}
	
	public Page<Permissao> findAll(Pageable pageable){
		return permissaoRepository.findAll(pageable);
	}
	
	public Permissao save(Permissao permissao) throws BadResourceException, ResourceAlreadyExistsException{
		if(!StringUtils.isEmpty(permissao.getDescricao())) {
			if(permissao.getId() != null && existsById(permissao.getId())) {
				throw new ResourceAlreadyExistsException("Permissão com o id: "+permissao.getId()+"\n já existe");
			}
			return permissaoRepository.save(permissao);
		}else {
			BadResourceException exc = new BadResourceException("Erro ao salvar Permissão");
			exc.addErrorMessages("Permissão está vazio ou é nulo");
			throw exc;
		}
	}
	
	public void update(Permissao permissao) throws BadResourceException, ResourceNotFoundException{
		if(!StringUtils.isEmpty(permissao.getDescricao())) {
			if(!existsById(permissao.getId())) {
				throw new ResourceNotFoundException("Permissão não encontrado com o id: "+permissao.getId());
			}
			permissaoRepository.save(permissao);
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException{
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Permissão não encontrado com o id: "+id);
		}else {
			permissaoRepository.deleteById(id);
		}
	}
    
    public Long count() {
        return permissaoRepository.count();
    }
}