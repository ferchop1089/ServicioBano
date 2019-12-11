package com.ceiba.adn.serviciobano.aplicacion.comando;

public class ComandoRespuesta<R> {

	private R respuesta;

	public ComandoRespuesta(R respuesta) {
		this.respuesta = respuesta;
	}

	public R getRespuesta() {
		return respuesta;
	}

}
