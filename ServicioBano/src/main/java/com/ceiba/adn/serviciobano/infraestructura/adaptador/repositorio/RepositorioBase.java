package com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RepositorioBase<E, I> extends JpaRepository<E, I> {

	public I findMaxId();
	
}
