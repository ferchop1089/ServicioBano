package com.ceiba.adn.serviciobano.testdatabuilder;

import com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad.BanoEntidad;

public class BanoEntidadTestDataBuilder {

	private Long id;

	private String identificador;

	private String estado;

	public BanoEntidadTestDataBuilder() {
		this.id = 1L;
		this.identificador = "Bano 1";
		this.estado = "DISPONIBLE";
	}

	public BanoEntidadTestDataBuilder withId(Long id0) {
		this.id = id0;
		return this;
	}

	public BanoEntidadTestDataBuilder withIdentificador(String identificador0) {
		this.identificador = identificador0;
		return this;
	}

	public BanoEntidadTestDataBuilder withEstado(String estado0) {
		this.estado = estado0;
		return this;
	}

	public BanoEntidad build() {
		return new BanoEntidad(id, identificador, estado);
	}
}