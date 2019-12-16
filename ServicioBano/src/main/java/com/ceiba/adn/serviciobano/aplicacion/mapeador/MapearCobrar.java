package com.ceiba.adn.serviciobano.aplicacion.mapeador;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCobrar;
import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Cobrar;

@Component
public class MapearCobrar implements Mapeador<ComandoCobrar, Cobrar> {

	private MapearCuenta mapearCuenta;

	public MapearCobrar(MapearCuenta mapearCuenta) {
		this.mapearCuenta = mapearCuenta;
	}

	@Override
	public Cobrar mapearA(ComandoCobrar source) {
		if (Objects.isNull(source)) {
			return null;
		}
		return new Cobrar(mapearCuenta.mapearA(source.getCuenta()), source.getTarifaSobreAdicional(),
				source.getTarifaMinutoAdicional(), source.getMinutosPermitidos(), source.getMinutosTranscurridos(),
				source.getMinutosAdicionales(), source.getTarifaMinutosPermitidos(),
				source.getSubtotalMinutosAdicionales(), source.getSubtotalSobresAdicionales(),
				source.getSobresAdicionales());
	}

	@Override
	public ComandoCobrar mapearDesde(Cobrar source) {
		if (Objects.isNull(source)) {
			return null;
		}
		return new ComandoCobrar(mapearCuenta.mapearDesde(source.getCuenta()), source.getTarifaSobreAdicional(),
				source.getTarifaMinutoAdicional(), source.getMinutosPermitidos(), source.getMinutosTranscurridos(),
				source.getMinutosAdicionales(), source.getTarifaMinutosPermitidos(),
				source.getSubtotalMinutosAdicionales(), source.getSubtotalSobresAdicionales(),
				source.getSobresAdicionales());
	}

}