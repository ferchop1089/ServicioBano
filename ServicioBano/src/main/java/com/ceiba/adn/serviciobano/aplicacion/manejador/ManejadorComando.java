package com.ceiba.adn.serviciobano.aplicacion.manejador;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ManejadorComando<C> {

	@Transactional(propagation = Propagation.REQUIRED)
	public void ejecutar(C comando);

}
