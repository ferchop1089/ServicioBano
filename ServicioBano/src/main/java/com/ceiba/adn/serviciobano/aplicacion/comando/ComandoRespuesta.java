package com.ceiba.adn.serviciobano.aplicacion.comando;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ComandoRespuesta<R> {

	private R respuesta;

	@JsonCreator
	public ComandoRespuesta(@JsonProperty("respuesta") R respuesta) {
		this.respuesta = respuesta;
	}

	public R getRespuesta() {
		return respuesta;
	}

}
