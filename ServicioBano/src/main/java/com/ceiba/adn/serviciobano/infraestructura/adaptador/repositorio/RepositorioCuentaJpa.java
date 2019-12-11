package com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad.CuentaEntidad;

public interface RepositorioCuentaJpa extends JpaRepository<CuentaEntidad, Long> {

	@Query("SELECT b FROM CuentaEntidad b WHERE b.idBano = ?1")
	public Optional<CuentaEntidad> findCuentaEntidadByIdBano(@Param("idBano") Long idBano);

	@Query("SELECT b.estado FROM CuentaEntidad b WHERE b.id = ?1")
	public String findEstadoCuentaEntidadById(@Param("id") Long id);

}
