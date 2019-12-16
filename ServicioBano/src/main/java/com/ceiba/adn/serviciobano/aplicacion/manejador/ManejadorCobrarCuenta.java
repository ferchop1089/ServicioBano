package com.ceiba.adn.serviciobano.aplicacion.manejador;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCobrar;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Cobrar;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioCobrarCuenta;

@Component
public class ManejadorCobrarCuenta implements ManejadorComandoRespuesta<Long, ComandoRespuesta<ComandoCobrar>> {

	private ServicioCobrarCuenta servicio;

	private Mapeador<ComandoCobrar, Cobrar> mapper;

	public ManejadorCobrarCuenta(ServicioCobrarCuenta servicio,
			@Qualifier("mapearCobrar") Mapeador<ComandoCobrar, Cobrar> mapper) {
		this.servicio = servicio;
		this.mapper = mapper;
	}

	@Override
	public ComandoRespuesta<ComandoCobrar> ejecutar(Long id) {
		Cobrar cobrar = servicio.cobrar(id);
		return new ComandoRespuesta<>(mapper.mapearDesde(cobrar));
	}

}
