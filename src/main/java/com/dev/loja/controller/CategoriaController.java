package com.dev.loja.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dev.loja.domain.Categoria;
import com.dev.loja.exception.BadResourceException;
import com.dev.loja.exception.ResourceAlreadyExistsException;
import com.dev.loja.exception.ResourceNotFoundException;
import com.dev.loja.service.CategoriaService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name="categoria", description="Api De Categoria")
public class CategoriaController {
private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping(value="/categoria")
	public ResponseEntity<Page<Categoria>> findAll(Pageable pageable){
		return ResponseEntity.ok(categoriaService.findAll(pageable));
	}
	
	@PostMapping(value="/categoria")
	public ResponseEntity<Categoria> addProduto(@RequestBody Categoria categoria) throws URISyntaxException{
		try {
			Categoria c = categoriaService.save(categoria);
			return ResponseEntity.created(new URI("/api/categoria/"+c.getId())).body(categoria);
		}catch(ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}catch(BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PutMapping(value="/categoria/{id}")
	public ResponseEntity<Categoria> updateProduto(@Valid @RequestBody Categoria categoria, @PathVariable long id) throws BadResourceException{
		try {
			categoria.setId(id);
			categoriaService.update(categoria);
			return ResponseEntity.ok().build();
		}catch(ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}catch(BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@DeleteMapping(path="/categoria/{id}")
	public ResponseEntity<Categoria> deleteProdutoById(@PathVariable long id){
		try {
			categoriaService.deleteById(id);
			return ResponseEntity.ok().build();
		}catch(ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
}
