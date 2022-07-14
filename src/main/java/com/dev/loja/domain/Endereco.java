package com.dev.loja.domain;

import lombok.Data;

@Data
public class Endereco {
	private String cep;
	private String state;
	private String city;
	private String street;
	private String neighborhood;
	private Localizacao location;
}
