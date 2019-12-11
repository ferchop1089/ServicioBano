package com.ceiba.adn.serviciobano.dominio.puerto.repositorio;

import java.util.Optional;

import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;

public interface RepositorioCuenta {

	public Long crear(Cuenta cuenta);

	public void actualizar(Cuenta cuenta);

	public void eliminar(Long id);

	public Optional<Cuenta> buscarPorId(Long id);

	public Optional<Cuenta> buscarPorIdBano(Long idBano);

	public String estadoCuenta(Long id);

	public boolean existePorId(Long id);

}
