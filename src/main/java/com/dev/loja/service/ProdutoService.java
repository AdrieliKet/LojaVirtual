package com.dev.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dev.loja.domain.Produto;
import com.dev.loja.exception.BadResourceException;
import com.dev.loja.exception.ResourceAlreadyExistsException;
import com.dev.loja.exception.ResourceNotFoundException;
import com.dev.loja.repository.ProdutoRepository;


@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	
	private boolean existsById(Long id) {
		return produtoRepository.existsById(id);
	}
	
	public Produto findById(Long id) throws ResourceNotFoundException{
		Produto produto = produtoRepository.findById(id).orElse(null);
		if(produto==null) {
			throw new ResourceNotFoundException("Produto não encontrado com o id: " + id);
		}
		else return produto;
	}
	
	 public Page<Produto> findAll(Pageable pageable){
		 return produtoRepository.findAll(pageable);
	 }
	 
	 public Page<Produto> findAllByNome(String descricao, Pageable page){
		 Page<Produto> produtos = produtoRepository.findByProduto(descricao, page);
		 return produtos;
	 }
	 
	 public Produto save(Produto produto) throws BadResourceException, ResourceAlreadyExistsException{
		 if(!StringUtils.isEmpty(produto.getDescricao())) {
			 if(produto.getId() != null && existsById(produto.getId())) {
				throw new ResourceAlreadyExistsException("Produto com id: "+produto.getId()+" já existe"); 
			 }
		return produtoRepository.save(produto);	 
		 } else {
			 BadResourceException exc = new BadResourceException("Erro ao salvar produto");
			 exc.addErrorMessages("Produto está vazio ou é nulo");
			 throw exc;
		 }
	 }
	 
	 public void update(Produto produto) throws BadResourceException, ResourceNotFoundException {
		 if(!StringUtils.isEmpty(produto.getDescricao())) {
			 if(!existsById(produto.getId())) {
				 throw new ResourceNotFoundException("Produto não encontrado com o id: "+produto.getId());
			 }
			 produtoRepository.save(produto);
		 } else {
			 BadResourceException exc = new BadResourceException("Falha ao salvar o produto");
			 exc.addErrorMessages("Produto está nulo ou em branco");
			 throw exc;
		 }
	 }
		 
		 public void deleteById(Long id) throws ResourceNotFoundException{
			 if(!existsById(id)) {
				 throw new ResourceNotFoundException("Produto não encontrado com o id: "+id);
			 } else {
				 produtoRepository.deleteById(id);
			 }
		 }
		 
		 public Long count() {
			 return produtoRepository.count();
		 }
}
