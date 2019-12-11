package com.ceiba.adn.serviciobano.aplicacion.mapeador;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;

@Component
public class MapearCuenta implements Mapeador<ComandoCuenta, Cuenta> {

	@Override
	public Cuenta mapearA(ComandoCuenta source) {
		if (Objects.isNull(source)) {
			return null;
		}
		return new Cuenta(source.getId(), source.getIdBano(), source.getSobres(), source.getEstado(),
				source.getTotalCobro(), source.getFechaIngreso());
	}

	@Override
	public ComandoCuenta mapearDesde(Cuenta source) {
		if (Objects.isNull(source)) {
			return null;
		}
		return new ComandoCuenta(source.getId(), source.getIdBano(), source.getSobres(), source.getEstado(),
				source.getTotalCobro(), source.getFechaIngreso());
	}

}
