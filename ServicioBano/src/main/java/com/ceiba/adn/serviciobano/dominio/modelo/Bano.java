package com.ceiba.adn.serviciobano.dominio.modelo;

import static com.ceiba.adn.serviciobano.dominio.modelo.validacion.ValidarCamposBano.validarCampos;

public class Bano {

	private Long id0;

	private String identificador0;

	private String estado0;

	public Bano(Long id, String identificador, String estado) {
		validarCampos(identificador, estado);

		this.id0 = id;
		this.identificador0 = identificador.trim();
		this.estado0 = estado;
	}

	public Long getId() {
		return id0;
	}

	public void setId(Long id) {
		this.id0 = id;
	}

	public String getEstado() {
		return estado0;
	}

	public void setEstado(String estado) {
		this.estado0 = estado;
	}

	public String getIdentificador() {
		return identificador0;
	}

	public void setIdentificador(String identificador) {
		this.identificador0 = identificador;
	}

}
