package com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio;

import java.util.Objects;

public final class RepositorioHelper {

	private RepositorioHelper() {
	}

	public static <E> Long getNuevoId(RepositorioBase<E, Long> repositorio) {
		Long id = repositorio.findMaxId();
		if (Objects.isNull(id)) {
			id = 1L;
		} else {
			id++;
		}
		return id;
	}

}
