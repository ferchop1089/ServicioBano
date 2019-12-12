package com.ceiba.adn.serviciobano.aplicacion.manejador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoBano;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioConsultasBano;

@Component
public class ManejadorConsultasBano {

	private final ServicioConsultasBano consultasBano;
	private Mapeador<ComandoBano, Bano> mapper;

	public ManejadorConsultasBano(ServicioConsultasBano consultasBano,
			@Qualifier("mapearBano") Mapeador<ComandoBano, Bano> mapper) {
		this.consultasBano = consultasBano;
		this.mapper = mapper;
	}

	public ComandoRespuesta<List<ComandoBano>> ejecutar() {
		return new ComandoRespuesta<>(
				this.consultasBano.listarBanos().stream().map(mapper::mapearDesde).collect(Collectors.toList()));
	}
}
