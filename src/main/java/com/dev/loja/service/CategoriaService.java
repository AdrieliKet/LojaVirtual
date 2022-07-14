package com.dev.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dev.loja.domain.Categoria;
import com.dev.loja.exception.BadResourceException;
import com.dev.loja.exception.ResourceAlreadyExistsException;
import com.dev.loja.exception.ResourceNotFoundException;
import com.dev.loja.repository.CategoriaRepository;




@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	private boolean existsById(Long id) {
		return categoriaRepository.existsById(id);
	}
	
	
	public Categoria findById(Long id) throws ResourceNotFoundException{
		Categoria categoria = categoriaRepository.findById(id).orElse(null);
		if(categoria==null) {
			throw new ResourceNotFoundException("Categoria não encontrado com o id: " + id);
		}
		else return categoria;
	}
	
	 public Page<Categoria> findAll(Pageable pageable){
		 return categoriaRepository.findAll(pageable);
	 }
	 
	 public Page<Categoria> findAllByNome(String descricao, Pageable page){
		 Page<Categoria> categorias = categoriaRepository.findByDescricao(descricao, page);
		 return categorias;
	 }
	 
	 public Categoria save(Categoria categoria) throws BadResourceException, ResourceAlreadyExistsException{
			if(!StringUtils.isEmpty(categoria.getDescricao())) {
				if(categoria.getId() != null && existsById(categoria.getId())) {
					throw new ResourceAlreadyExistsException("Categoria com o id: "+categoria.getId()+"\n já existe");
				}
				return categoriaRepository.save(categoria);
			}else {
				BadResourceException exc = new BadResourceException("Erro ao salvar categoria");
				exc.addErrorMessages("Categoria está vazio ou é nulo");
				throw exc;
			}
		}
	 
	 public void update(Categoria categoria) throws BadResourceException, ResourceNotFoundException{
			if(!StringUtils.isEmpty(categoria.getDescricao())) {
				if(!existsById(categoria.getId())) {
					throw new ResourceNotFoundException("Categoria não encontrado com o id: "+categoria.getId());
				}
				categoriaRepository.save(categoria);
			}
		}
		 
		 public void deleteById(Long id) throws ResourceNotFoundException{
			 if(!existsById(id)) {
				 throw new ResourceNotFoundException("Categoria não encontrado com o id: "+id);
			 } else {
				 categoriaRepository.deleteById(id);
			 }
		 }
		 
		 public Long count() {
			 return categoriaRepository.count();
		 }
		 
		 
}
