package com.ceiba.adn.serviciobano.aplicacion.manejador;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioActualizarCuenta;

@Component
public class ManejadorActualizarCuenta implements ManejadorComando<ComandoCuenta> {

	private ServicioActualizarCuenta servicio;

	private Mapeador<ComandoCuenta, Cuenta> mapper;

	public ManejadorActualizarCuenta(ServicioActualizarCuenta servicio,
			@Qualifier("mapearCuenta") Mapeador<ComandoCuenta, Cuenta> mapper) {
		this.servicio = servicio;
		this.mapper = mapper;
	}

	@Override
	public void ejecutar(ComandoCuenta comando) {
		Cuenta cuenta = mapper.mapearA(comando);
		servicio.actualizarCuenta(cuenta);
	}

}
