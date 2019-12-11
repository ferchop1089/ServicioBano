package com.ceiba.adn.serviciobano.dominio.modelo;

public enum EstadoBano {

	DISPONIBLE("DISPONIBLE"), OCUPADO("OCUPADO"), FUERA_DE_SERVICIO("FUERA DE SERVICIO");

	private String estado;

	private EstadoBano(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}

}
