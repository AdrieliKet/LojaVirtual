package com.dev.loja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dev.loja.domain.Produto;
import com.dev.loja.domain.ProdutoPreco;
import com.dev.loja.exception.BadResourceException;
import com.dev.loja.exception.ResourceAlreadyExistsException;
import com.dev.loja.exception.ResourceNotFoundException;
import com.dev.loja.repository.ProdutoPrecoRepository;
import com.dev.loja.repository.ProdutoRepository;



@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired 
	private ProdutoPrecoRepository produtoPrecoRepository;
	
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
		 Page<Produto> produtos = produtoRepository.findByDescricao(descricao, page);
		 return produtos;
	 }
	 
	 public Produto save(Produto produto) throws BadResourceException, ResourceAlreadyExistsException{
		 if(!StringUtils.isEmpty(produto.getDescricao())) {
			 if(produto.getId() != null && existsById(produto.getId())) {
				throw new ResourceAlreadyExistsException("Produto com id: "+produto.getId()+" já existe"); 
			 }
			 
			 Produto produtoNovo = produtoRepository.save(produto);
			 
			 ProdutoPreco produtoPreco = new ProdutoPreco();
			 produtoPreco.setProduto(produtoNovo);
			 produtoPreco.setValorCusto(produtoNovo.getValorCusto());
			 produtoPreco.setValorVenda(produtoNovo.getValorVenda());
			 
			 return produtoNovo;	 
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
			 ProdutoPreco produtoPreco = new ProdutoPreco();
			 produtoPreco.setProduto(produto);
			 produtoPreco.setValorCusto(produto.getValorCusto());
			 produtoPreco.setValorVenda(produto.getValorVenda());
			 produtoRepository.save(produto);
		 } else {
			 BadResourceException exc = new BadResourceException("Falha ao salvar o produto");
			 exc.addErrorMessages("Produto está nulo ou em branco");
			 throw exc;
		 }
	 }
	 
	 public void atualizarValorProdutoCategoria(Long idCategoria, Double percentual, String tipoOperacao) {
		 List<Produto> produtos = produtoRepository.buscarProdutosCategoria(idCategoria);
		 for(Produto produto:produtos) {
			 try {
				 if(tipoOperacao == "+") {
					produto.setValorVenda(produto.getValorVenda()*(1+(percentual/100)));
					 
				 }else {
					produto.setValorVenda(produto.getValorVenda()*(1-(percentual/100)));
				 }
				update(produto);
			} catch (BadResourceException | ResourceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
