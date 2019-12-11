package com.ceiba.adn.serviciobano.aplicacion.manejador;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ManejadorComandoRespuesta<C, R> {

	@Transactional(propagation = Propagation.REQUIRED)
	public R ejecutar(C comando);

}
