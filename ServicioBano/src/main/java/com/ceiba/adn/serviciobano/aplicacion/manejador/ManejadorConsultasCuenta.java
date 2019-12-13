package com.ceiba.adn.serviciobano.aplicacion.manejador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioConsultasCuenta;

@Component
public class ManejadorConsultasCuenta implements ManejadorComandoRespuesta<Long, ComandoRespuesta<ComandoCuenta>> {

	private final ServicioConsultasCuenta consultasCuenta;
	private Mapeador<ComandoCuenta, Cuenta> mapper;

	public ManejadorConsultasCuenta(ServicioConsultasCuenta consultasCuenta,
			@Qualifier("mapearCuenta") Mapeador<ComandoCuenta, Cuenta> mapper) {
		this.consultasCuenta = consultasCuenta;
		this.mapper = mapper;
	}

	public ComandoRespuesta<ComandoCuenta> ejecutar(Long id) {
		Optional<Cuenta> banoOpt = this.consultasCuenta.buscarCuenta(id);
		return new ComandoRespuesta<>(mapper.mapearDesde(banoOpt.orElse(null)));
	}
	
}
