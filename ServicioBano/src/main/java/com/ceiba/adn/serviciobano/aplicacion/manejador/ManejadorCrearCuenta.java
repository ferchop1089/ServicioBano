package com.ceiba.adn.serviciobano.aplicacion.manejador;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioActualizarBano;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioConsultasBano;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioCrearCuenta;

@Component
public class ManejadorCrearCuenta implements ManejadorComandoRespuesta<ComandoCuenta, ComandoRespuesta<Long>> {

	private ServicioCrearCuenta servicio;

	private ServicioConsultasBano servicioConsultasBano;

	private ServicioActualizarBano servicioActualizarBano;

	private Mapeador<ComandoCuenta, Cuenta> mapper;

	public ManejadorCrearCuenta(ServicioCrearCuenta servicio, ServicioConsultasBano servicioConsultasBano,
			ServicioActualizarBano servicioActualizarBano,
			@Qualifier("mapearCuenta") Mapeador<ComandoCuenta, Cuenta> mapper) {
		this.servicio = servicio;
		this.servicioConsultasBano = servicioConsultasBano;
		this.servicioActualizarBano = servicioActualizarBano;
		this.mapper = mapper;
	}

	@Override
	public ComandoRespuesta<Long> ejecutar(ComandoCuenta comando) {
		if(Objects.isNull(comando.getFechaIngreso())) {
			comando.setFechaIngreso(LocalDateTime.now());
		}
		Cuenta cuenta = mapper.mapearA(comando);
		
		Long id = servicio.crearCuenta(cuenta);

		Optional<Bano> banoOpt = servicioConsultasBano.buscarBano(cuenta.getIdBano());
		banoOpt.ifPresent(t -> {
			t.setEstado(EstadoBano.OCUPADO.getEstado());
			servicioActualizarBano.actualizarBano(t);
		});

		return new ComandoRespuesta<>(id);
	}

}
