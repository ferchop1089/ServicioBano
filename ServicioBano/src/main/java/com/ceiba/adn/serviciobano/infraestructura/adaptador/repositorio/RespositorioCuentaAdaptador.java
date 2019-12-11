package com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioCuenta;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad.CuentaEntidad;

@Component
public class RespositorioCuentaAdaptador implements RepositorioCuenta {

	private RepositorioCuentaJpa jpa;

	private Mapeador<CuentaEntidad, Cuenta> mapper;

	public RespositorioCuentaAdaptador(RepositorioCuentaJpa jpa, Mapeador<CuentaEntidad, Cuenta> mapper) {
		this.jpa = jpa;
		this.mapper = mapper;
	}

	@Override
	public Long crear(Cuenta cuenta) {
		return guardar(cuenta);
	}

	@Override
	public void actualizar(Cuenta cuenta) {
		guardar(cuenta);
	}

	private Long guardar(Cuenta cuenta) {
		CuentaEntidad entidad = mapper.mapearDesde(cuenta);
		return mapper.mapearA(jpa.saveAndFlush(entidad)).getId();
	}

	@Override
	public void eliminar(Long id) {
		jpa.deleteById(id);
	}

	@Override
	public Optional<Cuenta> buscarPorId(Long id) {
		return jpa.findById(id).map(mapper::mapearA);
	}

	@Override
	public Optional<Cuenta> buscarPorIdBano(Long idBano) {
		return jpa.findCuentaEntidadByIdBano(idBano).map(mapper::mapearA);
	}

	@Override
	public String estadoCuenta(Long id) {
		return jpa.findEstadoCuentaEntidadById(id);
	}

	@Override
	public boolean existePorId(Long id) {
		return jpa.existsById(id);
	}

}
