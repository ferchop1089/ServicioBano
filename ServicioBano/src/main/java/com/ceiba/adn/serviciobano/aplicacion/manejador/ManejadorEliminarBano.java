package com.ceiba.adn.serviciobano.aplicacion.manejador;

import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioEliminarBano;

@Component
public class ManejadorEliminarBano implements ManejadorComando<Long> {

	private ServicioEliminarBano servicio;

	public ManejadorEliminarBano(ServicioEliminarBano servicio) {
		this.servicio = servicio;
	}

	@Override
	public void ejecutar(Long id) {
		servicio.eliminarBano(id);
	}

}
