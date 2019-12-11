package com.ceiba.adn.serviciobano.aplicacion.manejador;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioCobrarCuenta;

@Component
public class ManejadorCobrarCuenta implements ManejadorComandoRespuesta<Long, ComandoRespuesta<BigDecimal>> {

	private ServicioCobrarCuenta servicio;

	public ManejadorCobrarCuenta(ServicioCobrarCuenta servicio) {
		this.servicio = servicio;
	}

	@Override
	public ComandoRespuesta<BigDecimal> ejecutar(Long id) {
		return new ComandoRespuesta<>(servicio.cobrar(id));
	}

}
