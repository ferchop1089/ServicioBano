package com.ceiba.adn.serviciobano.aplicacion.manejador;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.modelo.Cobrar;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoCuenta;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioActualizarBano;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioConsultasBano;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioActualizarCuenta;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioCobrarCuenta;

@Component
public class ManejadorPagarCuenta implements ManejadorComando<Long> {

	private ServicioActualizarCuenta servicioCuenta;
	private ServicioActualizarBano servicioBano;
	private ServicioConsultasBano servicioConsultasBano;
	private ServicioCobrarCuenta servicioCobrar;

	public ManejadorPagarCuenta(ServicioActualizarCuenta servicioCuenta, ServicioActualizarBano servicioActualizarBano,
			ServicioConsultasBano servicioConsultasBano, ServicioCobrarCuenta servicioCobrar) {
		this.servicioCuenta = servicioCuenta;
		this.servicioBano = servicioActualizarBano;
		this.servicioConsultasBano = servicioConsultasBano;
		this.servicioCobrar = servicioCobrar;
	}

	@Override
	public void ejecutar(Long id) {
		Cobrar cobrar = servicioCobrar.cobrar(id);
		Cuenta cuenta = cobrar.getCuenta();
		Long idBano = cuenta.getIdBano();
		cuenta.setEstado(EstadoCuenta.CERRADA.getEstado());
		cuenta.setIdBano(null);

		servicioCuenta.actualizarCuenta(cuenta);

		Optional<Bano> banoOpt = servicioConsultasBano.buscarBano(idBano);
		banoOpt.ifPresent(t -> {
			t.setEstado(EstadoBano.DISPONIBLE.getEstado());
			servicioBano.actualizarBano(t);
		});
	}

}
