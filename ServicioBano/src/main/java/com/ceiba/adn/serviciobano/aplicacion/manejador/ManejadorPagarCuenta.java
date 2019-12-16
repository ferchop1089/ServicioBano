package com.ceiba.adn.serviciobano.aplicacion.manejador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoCuenta;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioActualizarBano;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioConsultasBano;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioActualizarCuenta;

@Component
public class ManejadorPagarCuenta implements ManejadorComando<ComandoCuenta> {

	private ServicioActualizarCuenta servicioCuenta;
	private ServicioActualizarBano servicioBano;
	private ServicioConsultasBano servicioConsultasBano;

	private Mapeador<ComandoCuenta, Cuenta> mapper;

	public ManejadorPagarCuenta(ServicioActualizarCuenta servicioCuenta, ServicioActualizarBano servicioActualizarBano,
			ServicioConsultasBano servicioConsultasBano,
			@Qualifier("mapearCuenta") Mapeador<ComandoCuenta, Cuenta> mapper) {
		this.servicioCuenta = servicioCuenta;
		this.servicioBano = servicioActualizarBano;
		this.servicioConsultasBano = servicioConsultasBano;
		this.mapper = mapper;
	}

	@Override
	public void ejecutar(ComandoCuenta comando) {
		Cuenta cuenta = mapper.mapearA(comando);
		cuenta.setEstado(EstadoCuenta.CERRADA.getEstado());
		cuenta.setIdBano(null);
		servicioCuenta.actualizarCuenta(cuenta);

		Optional<Bano> banoOpt = servicioConsultasBano.buscarBano(comando.getIdBano());
		banoOpt.ifPresent(t -> {
			t.setEstado(EstadoBano.DISPONIBLE.getEstado());
			servicioBano.actualizarBano(t);
		});

	}

}
