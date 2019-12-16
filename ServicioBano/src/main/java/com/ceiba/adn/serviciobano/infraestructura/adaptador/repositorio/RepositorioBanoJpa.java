package com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad.BanoEntidad;

public interface RepositorioBanoJpa extends RepositorioBase<BanoEntidad, Long> {

	public Optional<BanoEntidad> findByIdentificador(@Param("identificador") String identificador);

	@Query("SELECT b.estado FROM BanoEntidad b WHERE b.id = ?1")
	public String findEstadoBanoEntidadById(@Param("id") Long id);

	public boolean existsByIdentificador(@Param("identificador") String identificador);
	
	@Query("SELECT max(b.id) FROM BanoEntidad b")
	public Long findMaxId();

}
