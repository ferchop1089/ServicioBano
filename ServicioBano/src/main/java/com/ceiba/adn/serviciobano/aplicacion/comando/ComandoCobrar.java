package com.ceiba.adn.serviciobano.aplicacion.comando;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ComandoCobrar {

	private ComandoCuenta cuenta;

	private BigDecimal valorSobreAdicional;

	private BigDecimal valorMinutoAdicional;

	private Long minutosPermitidos;

	private Long minutosTranscurridos;

	private Long minutosAdicionales;

	@JsonCreator
	public ComandoCobrar(ComandoCuenta cuenta, BigDecimal valorSobreAdicional, BigDecimal valorMinutoAdicional,
			Long minutosPermitidos, Long minutosTranscurridos, Long minutosAdicionales) {
		this.cuenta = cuenta;
		this.valorSobreAdicional = valorSobreAdicional;
		this.valorMinutoAdicional = valorMinutoAdicional;
		this.minutosPermitidos = minutosPermitidos;
		this.minutosTranscurridos = minutosTranscurridos;
		this.minutosAdicionales = minutosAdicionales;
	}

	public ComandoCuenta getCuenta() {
		return cuenta;
	}

	public BigDecimal getValorSobreAdicional() {
		return valorSobreAdicional;
	}

	public BigDecimal getValorMinutoAdicional() {
		return valorMinutoAdicional;
	}

	public Long getMinutosPermitidos() {
		return minutosPermitidos;
	}

	public Long getMinutosTranscurridos() {
		return minutosTranscurridos;
	}

	public Long getMinutosAdicionales() {
		return minutosAdicionales;
	}

}
