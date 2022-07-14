package com.dev.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dev.loja.domain.Marca;
import com.dev.loja.exception.BadResourceException;
import com.dev.loja.exception.ResourceAlreadyExistsException;
import com.dev.loja.exception.ResourceNotFoundException;
import com.dev.loja.repository.MarcaRepository;



@Service
public class MarcaService {
	@Autowired
	private MarcaRepository marcaRepository;
	
	private boolean existsById(Long id) {
		return marcaRepository.existsById(id);
	}
	
	
	public Marca findById(Long id) throws ResourceNotFoundException{
		Marca marca = marcaRepository.findById(id).orElse(null);
		if(marca==null) {
			throw new ResourceNotFoundException("Marca não encontrado com o id: " + id);
		}
		else return marca;
	}
	
	 public Page<Marca> findAll(Pageable pageable){
		 return marcaRepository.findAll(pageable);
	 }
	 
	 public Page<Marca> findAllByNome(String descricao, Pageable page){
		 Page<Marca> marcas = marcaRepository.findByDescricao(descricao, page);
		 return marcas;
	 }
	 
	 public Marca save(Marca marca) throws BadResourceException, ResourceAlreadyExistsException{
			if(!StringUtils.isEmpty(marca.getDescricao())) {
				if(marca.getId() != null && existsById(marca.getId())) {
					throw new ResourceAlreadyExistsException("Marca com o id: "+marca.getId()+"\n já existe");
				}
				return marcaRepository.save(marca);
			}else {
				BadResourceException exc = new BadResourceException("Erro ao salvar marca");
				exc.addErrorMessages("Marca está vazio ou é nulo");
				throw exc;
			}
		}
	 
	 public void update(Marca marca) throws BadResourceException, ResourceNotFoundException{
			if(!StringUtils.isEmpty(marca.getDescricao())) {
				if(!existsById(marca.getId())) {
					throw new ResourceNotFoundException("Marca não encontrado com o id: "+marca.getId());
				}
				marcaRepository.save(marca);
			}
		}
		 
		 public void deleteById(Long id) throws ResourceNotFoundException{
			 if(!existsById(id)) {
				 throw new ResourceNotFoundException("Marca não encontrado com o id: "+id);
			 } else {
				 marcaRepository.deleteById(id);
			 }
		 }
		 
		 public Long count() {
			 return marcaRepository.count();
		 }
		 
		 
}
