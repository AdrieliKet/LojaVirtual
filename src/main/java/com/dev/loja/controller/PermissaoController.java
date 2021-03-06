package com.dev.loja.controller;


import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.dev.loja.domain.Permissao;
import com.dev.loja.exception.BadResourceException;
import com.dev.loja.exception.ResourceAlreadyExistsException;
import com.dev.loja.exception.ResourceNotFoundException;
import com.dev.loja.service.PermissaoService;

 
@RestController
@RequestMapping("/api/permissao")
public class PermissaoController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PermissaoService permissaoService;
    
    
    @GetMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Permissao>> findAll(Pageable pageable) {           	
            return ResponseEntity.ok(permissaoService.findAll(pageable));        
    }
 

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Permissao> findById(@PathVariable long id) throws ResourceNotFoundException {    	
        	Permissao permissao = permissaoService.findById(id);
            return ResponseEntity.ok(permissao);        
    }
    
    @PostMapping(value="/permissao")
	public ResponseEntity<Permissao> addProduto(@RequestBody Permissao permissao) throws URISyntaxException{
		try {
			Permissao p = permissaoService.save(permissao);
			return ResponseEntity.created(new URI("/api/marca/"+p.getId())).body(permissao);
		}catch(ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}catch(BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
    
    @PutMapping(value="/permissao/{id}")
	public ResponseEntity<Permissao> updateProduto(@Valid @RequestBody Permissao permissao, @PathVariable long id) throws BadResourceException{
		try {
			permissao.setId(id);
			permissaoService.update(permissao);
			return ResponseEntity.ok().build();
		}catch(ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}catch(BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	} 
  
    @DeleteMapping(path="/permissao/{id}")
	public ResponseEntity<Permissao> deleteProdutoById(@PathVariable long id){
		try {
			permissaoService.deleteById(id);
			return ResponseEntity.ok().build();
		}catch(ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
}