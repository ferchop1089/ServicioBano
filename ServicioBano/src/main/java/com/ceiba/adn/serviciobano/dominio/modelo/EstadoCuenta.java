package com.ceiba.adn.serviciobano.dominio.modelo;

public enum EstadoCuenta {

	ABIERTA("ABIERTA"), CERRADA("CERRADA");

	private String estado;

	private EstadoCuenta(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}

}
