package com.ceiba.adn.serviciobano.dominio.modelo;

import static com.ceiba.adn.serviciobano.dominio.modelo.validacion.ValidarCamposBano.validarCampos;

public class Bano {

	private Long id;

	private String identificador;

	private String estado;

	public Bano(Long id, String identificador, String estado) {
		validarCampos(identificador, estado);

		this.id = id;
		this.identificador = identificador.trim();
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

}
