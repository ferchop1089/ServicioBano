package com.ceiba.adn.serviciobano.infraestructura.mapeador;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad.BanoEntidad;

@Component
public class MapearBanoEntidad implements Mapeador<BanoEntidad, Bano> {

	@Override
	public Bano mapearA(BanoEntidad banoEntidad) {
		if (Objects.isNull(banoEntidad)) {
			return null;
		}
		return new Bano(banoEntidad.getId(), banoEntidad.getIdentificador(), banoEntidad.getEstado());
	}

	@Override
	public BanoEntidad mapearDesde(Bano bano) {
		if (Objects.isNull(bano)) {
			return null;
		}
		return new BanoEntidad(bano.getId(), bano.getIdentificador(), bano.getEstado());
	}

}
