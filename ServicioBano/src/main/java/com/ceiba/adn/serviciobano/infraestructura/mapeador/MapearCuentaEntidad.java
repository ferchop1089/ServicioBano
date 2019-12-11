package com.ceiba.adn.serviciobano.infraestructura.mapeador;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad.CuentaEntidad;

@Component
public class MapearCuentaEntidad implements Mapeador<CuentaEntidad, Cuenta> {

	@Override
	public Cuenta mapearA(CuentaEntidad cuentaEntidad) {
		if (Objects.isNull(cuentaEntidad)) {
			return null;
		}
		return new Cuenta(cuentaEntidad.getId(), cuentaEntidad.getIdBano(), cuentaEntidad.getSobres(),
				cuentaEntidad.getEstado(), cuentaEntidad.getTotalCobro(), cuentaEntidad.getFechaIngreso());
	}

	@Override
	public CuentaEntidad mapearDesde(Cuenta cuenta) {
		if (Objects.isNull(cuenta)) {
			return null;
		}
		return new CuentaEntidad(cuenta.getId(), cuenta.getIdBano(), cuenta.getSobres(), cuenta.getEstado(),
				cuenta.getTotalCobro(), cuenta.getFechaIngreso());
	}

}
