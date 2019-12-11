package com.ceiba.adn.serviciobano.aplicacion.mapeador;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoBano;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;

@Component
public class MapearBano implements Mapeador<ComandoBano, Bano> {

	@Override
	public Bano mapearA(ComandoBano comandoBano) {
		if (Objects.isNull(comandoBano)) {
			return null;
		}
		return new Bano(comandoBano.getId(), comandoBano.getIdentificador(), comandoBano.getEstado());
	}

	@Override
	public ComandoBano mapearDesde(Bano bano) {
		if (Objects.isNull(bano)) {
			return null;
		}
		return new ComandoBano(bano.getId(), bano.getIdentificador(), bano.getEstado());
	}

}
