package com.ceiba.adn.serviciobano.aplicacion.manejador;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoBano;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioCrearBano;

@Component
public class ManejadorCrearBano implements ManejadorComandoRespuesta<ComandoBano, ComandoRespuesta<Long>> {

	private ServicioCrearBano servicio;

	private Mapeador<ComandoBano, Bano> mapper;

	public ManejadorCrearBano(ServicioCrearBano servicio, @Qualifier("mapearBano") Mapeador<ComandoBano, Bano> mapper) {
		this.servicio = servicio;
		this.mapper = mapper;
	}

	@Override
	public ComandoRespuesta<Long> ejecutar(ComandoBano comando) {
		Bano bano = mapper.mapearA(comando);
		return new ComandoRespuesta<>(servicio.crearBano(bano));
	}

}
