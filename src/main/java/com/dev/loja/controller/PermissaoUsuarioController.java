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

import com.dev.loja.domain.PermissaoUsuario;
import com.dev.loja.exception.BadResourceException;
import com.dev.loja.exception.ResourceAlreadyExistsException;
import com.dev.loja.exception.ResourceNotFoundException;
import com.dev.loja.service.PermissaoUsuarioService;

 
@RestController
@RequestMapping("/api/permissaoUsuario")
public class PermissaoUsuarioController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PermissaoUsuarioService permissaoUsuarioService;
    
    
    @GetMapping(value="/permissaoUsuario")
	public ResponseEntity<Page<PermissaoUsuario>> findAll(Pageable pageable){
		return ResponseEntity.ok(permissaoUsuarioService.findAll(pageable));
	}

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PermissaoUsuario> findById(@PathVariable long id) throws ResourceNotFoundException {    	
        	PermissaoUsuario permissaoUsuario = permissaoUsuarioService.findById(id);
            return ResponseEntity.ok(permissaoUsuario);        
    }
    
    @PostMapping(value="/permissaoUsuario")
	public ResponseEntity<PermissaoUsuario> addProduto(@RequestBody PermissaoUsuario permissaoUsuario) throws URISyntaxException{
		try {
			PermissaoUsuario p = permissaoUsuarioService.save(permissaoUsuario);
			return ResponseEntity.created(new URI("/api/produto/"+p.getId())).body(permissaoUsuario);
		}catch(ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}catch(BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
    
    @PutMapping(value="/permissaoUsuario/{id}")
	public ResponseEntity<PermissaoUsuario> updateProduto(@Valid @RequestBody PermissaoUsuario permissaoUsuario, @PathVariable long id) throws BadResourceException{
		try {
			permissaoUsuario.setId(id);
			permissaoUsuarioService.update(permissaoUsuario);
			return ResponseEntity.ok().build();
		}catch(ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}catch(BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
    @DeleteMapping(path="/permissaoUsuario/{id}")
	public ResponseEntity<PermissaoUsuario> deleteProdutoById(@PathVariable long id){
		try {
			permissaoUsuarioService.deleteById(id);
			return ResponseEntity.ok().build();
		}catch(ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
}