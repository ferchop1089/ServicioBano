package com.ceiba.adn.serviciobano.dominio.servicio.cuenta;

import java.util.Optional;

import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioCuenta;

public class ServicioConsultasCuenta {

	private RepositorioCuenta repositorio;

	public ServicioConsultasCuenta(RepositorioCuenta repositorio) {
		this.repositorio = repositorio;
	}

	public Optional<Cuenta> buscarCuentaPorId(Long id) {
		return repositorio.buscarPorId(id);
	}
	
	public Optional<Cuenta> buscarCuentaPorIdBano(Long idBano) {
		return repositorio.buscarPorIdBano(idBano);
	}
	
}
