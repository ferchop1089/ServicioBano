package com.ceiba.adn.serviciobano.aplicacion.manejador;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoBano;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioActualizarBano;

@Component
public class ManejadorActualizarBano implements ManejadorComando<ComandoBano> {

	private ServicioActualizarBano servicio;

	private Mapeador<ComandoBano, Bano> mapper;

	public ManejadorActualizarBano(ServicioActualizarBano servicio,
			@Qualifier("mapearBano") Mapeador<ComandoBano, Bano> mapper) {
		this.servicio = servicio;
		this.mapper = mapper;
	}

	@Override
	public void ejecutar(ComandoBano comando) {
		Bano bano = mapper.mapearA(comando);
		servicio.actualizarBano(bano);
	}

}
