package com.ceiba.adn.serviciobano.comun.mapeador;

public interface Mapeador<S, D> {

	public D mapearA(S source);

	public S mapearDesde(D source);

}
