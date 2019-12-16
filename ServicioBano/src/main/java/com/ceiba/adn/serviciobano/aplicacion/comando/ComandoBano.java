package com.ceiba.adn.serviciobano.aplicacion.comando;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ComandoBano {

	private Long id;

	private String identificador;

	private String estado;

	@JsonCreator
	public ComandoBano(@JsonProperty("id") Long id, @JsonProperty("identificador") String identificador,
			@JsonProperty("estado") String estado) {
		this.id = id;
		this.identificador = identificador;
		this.estado = estado;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getEstado() {
		return estado;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

}
