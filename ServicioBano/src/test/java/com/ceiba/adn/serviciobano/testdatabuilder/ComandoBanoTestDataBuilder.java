package com.ceiba.adn.serviciobano.testdatabuilder;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoBano;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;

public class ComandoBanoTestDataBuilder {

	private Long id;

	private String identificador;

	private String estado;

	public ComandoBanoTestDataBuilder() {
		this.id = 1L;
		this.identificador = "Baño 1";
		this.estado = EstadoBano.DISPONIBLE.getEstado();
	}

	public ComandoBanoTestDataBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public ComandoBanoTestDataBuilder withIdentificador(String identificador) {
		this.identificador = identificador;
		return this;
	}

	public ComandoBanoTestDataBuilder withEstado(String estado) {
		this.estado = estado;
		return this;
	}

	public ComandoBano build() {
		return new ComandoBano(id, identificador, estado);
	}

}
