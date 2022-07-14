package com.dev.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.loja.domain.Endereco;
import com.dev.loja.service.EnderecoService;

@RestController
@RequestMapping("/api")
public class EnderecoController {
	@Autowired
	private EnderecoService brasilApiService;
	
	@GetMapping(value="/cep/{cep}")
	public Endereco encontrarEnderecoPorCEP(@PathVariable String cep) {
		Endereco endereco = brasilApiService.encontrarEnderecoPorCEP(cep);
		return endereco;
	}
}
