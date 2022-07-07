package com.dev.loja.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import com.dev.loja.exception.ResourceAlreadyExistsException;
import com.dev.loja.domain.Produto;
import com.dev.loja.exception.BadResourceException;
import com.dev.loja.exception.ResourceNotFoundException;
import com.dev.loja.repository.ProdutoRepository;
import com.dev.loja.service.ProdutoService;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api")
@Tag(name = "loja", description = "API para loja")
@AllArgsConstructor
public class ProdutoController {
	
private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping(value = "/produtos",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Produto>> findAll(@RequestBody(required=false) String descricao, Pageable pageable){
		if (StringUtils.isEmpty(descricao)) {
			return ResponseEntity.ok(produtoService.findAll(pageable));
		}
		else {
			return ResponseEntity.ok(produtoService.findAllByNome(descricao, pageable));
		}
	}
	
	@Operation(summary = "Busca ID",description = "Buscar um produto por ID", tags = {"produto"})
	@ApiResponses(value= {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content= @Content(schema = @Schema(implementation = Produto.class))),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado")
	})
	@GetMapping(value = "/produto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Produto> findProdutoById(@PathVariable long id){
		try {
			Produto produto = produtoService.findById(id);
			return ResponseEntity.ok(produto);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		} 
		
	}
	
	@PostMapping(value = "/produto")
	public ResponseEntity<Produto> addProduto(@RequestBody Produto produto) throws URISyntaxException{
			try {
				Produto novoProduto = produtoService.save(produto);
				return ResponseEntity.created(new URI("/api/produto/" + novoProduto.getId())).body(produto);
			} catch (ResourceAlreadyExistsException ex) {
				logger.error(ex.getMessage());
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			} catch (BadResourceException ex) {
				logger.error(ex.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
	}
	
	@PutMapping(value = "/produto/{id}")
	public ResponseEntity<Produto> updateProduto(@Valid @RequestBody Produto produto, @PathVariable long id){
		try {
			produto.setId(id);
			produtoService.update(produto);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@DeleteMapping(path="/Produto/{id}")
	public ResponseEntity<Void> deleteProdutoById(@PathVariable long id){
		try {
			produtoService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch(ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
	
	//localhost:8080/api/produto/atualizarValorCategoria?percentual=5&idCategoria=1&tipoOperacao=%2B  acrescentar
	//localhost:8080/api/produto/atualizarValorCategoria?percentual=5&idCategoria=1&tipoOperacao=%B  diminuir (ver no github do professor)
	@GetMapping(path = "/atualizarValorCategoria")
	public ResponseEntity<Void> atualizarValorProdutoCategoria(@RequestParam Double percentual, @RequestParam Long idCategoria, @RequestParam String tipoOperacao){
		produtoService.atualizarValorProdutoCategoria(idCategoria, percentual, tipoOperacao);
		return ResponseEntity.ok().build();
	}
}
