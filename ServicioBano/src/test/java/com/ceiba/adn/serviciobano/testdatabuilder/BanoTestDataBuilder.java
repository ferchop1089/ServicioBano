package com.ceiba.adn.serviciobano.testdatabuilder;

import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;

public class BanoTestDataBuilder {

	private Long id;
	private String identificador;
	private String estado;

	public BanoTestDataBuilder() {
		id = 1L;
		identificador = "Baño 1";
		estado = EstadoBano.DISPONIBLE.getEstado();
	}

	public BanoTestDataBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public BanoTestDataBuilder withIdentificador(String identificador) {
		this.identificador = identificador;
		return this;
	}

	public BanoTestDataBuilder withEstado(String estado) {
		this.estado = estado;
		return this;
	}

	public Bano build() {
		return new Bano(id, identificador, estado);
	}

}
