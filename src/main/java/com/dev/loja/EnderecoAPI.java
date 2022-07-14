package com.dev.loja;

import org.springframework.web.client.RestTemplate;

import com.dev.loja.domain.Endereco;

public class EnderecoAPI {
	public static void main(String[] args) {
		String cep = "87711748";
		String url = "https://brasilapi.com.br/api/cep/v2/"+cep;
		RestTemplate restTemplate = new RestTemplate();
		Endereco ob = restTemplate.getForObject(url, Endereco.class);
	}
}
