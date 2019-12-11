package com.ceiba.adn.serviciobano.comun.excepcion;

public class ExcepcionLecturaArchivo extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExcepcionLecturaArchivo(String mensaje) {
		super(mensaje);
	}
}
