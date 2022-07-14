package com.dev.loja.domain;

import lombok.Data;

@Data
public class Localizacao {
	private String type;
	private Coordenadas coordinates;
}
