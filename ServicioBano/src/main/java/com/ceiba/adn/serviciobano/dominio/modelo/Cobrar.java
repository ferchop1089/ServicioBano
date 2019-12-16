package com.ceiba.adn.serviciobano.dominio.modelo;

import java.math.BigDecimal;

public class Cobrar {

	private Cuenta cuenta0;

	private BigDecimal valorSobreAdicional0;

	private BigDecimal valorMinutoAdicional0;

	private Long minutosPermitidos0;

	private Long minutosTranscurridos0;

	private Long minutosAdicionales;

	public Cobrar(Cuenta cuenta, BigDecimal valorSobreAdicional, BigDecimal valorMinutoAdicional,
			Long minutosPermitidos, Long minutosTranscurridos, Long minutosAdicionales) {
		this.cuenta0 = cuenta;
		this.valorSobreAdicional0 = valorSobreAdicional;
		this.valorMinutoAdicional0 = valorMinutoAdicional;
		this.minutosPermitidos0 = minutosPermitidos;
		this.minutosTranscurridos0 = minutosTranscurridos;
		this.minutosAdicionales = minutosAdicionales;
	}

	public Cuenta getCuenta() {
		return cuenta0;
	}

	public BigDecimal getValorSobreAdicional() {
		return valorSobreAdicional0;
	}

	public BigDecimal getValorMinutoAdicional() {
		return valorMinutoAdicional0;
	}

	public Long getMinutosPermitidos() {
		return minutosPermitidos0;
	}

	public Long getMinutosTranscurridos() {
		return minutosTranscurridos0;
	}

	public Long getMinutosAdicionales() {
		return minutosAdicionales;
	}

}
