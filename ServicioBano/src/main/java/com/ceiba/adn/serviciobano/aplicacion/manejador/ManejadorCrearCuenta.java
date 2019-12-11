package com.ceiba.adn.serviciobano.aplicacion.manejador;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioCrearCuenta;

@Component
public class ManejadorCrearCuenta implements ManejadorComandoRespuesta<ComandoCuenta, ComandoRespuesta<Long>> {

	private ServicioCrearCuenta servicio;

	private Mapeador<ComandoCuenta, Cuenta> mapper;

	public ManejadorCrearCuenta(ServicioCrearCuenta servicio,
			@Qualifier("mapearCuenta") Mapeador<ComandoCuenta, Cuenta> mapper) {
		this.servicio = servicio;
		this.mapper = mapper;
	}

	@Override
	public ComandoRespuesta<Long> ejecutar(ComandoCuenta comando) {
		Cuenta cuenta = mapper.mapearA(comando);
		return new ComandoRespuesta<>(servicio.crearCuenta(cuenta));
	}

}
