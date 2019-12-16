package com.ceiba.adn.serviciobano.dominio.modelo;

import java.math.BigDecimal;

public class Cobrar {

	private Cuenta cuenta;

	private BigDecimal valorSobreAdicional;

	private BigDecimal valorMinutoAdicional;

	private Long minutosPermitidos;

	private Long minutosTranscurridos;

	private Long minutosAdicionales;

	public Cobrar(Cuenta cuenta, BigDecimal valorSobreAdicional, BigDecimal valorMinutoAdicional,
			Long minutosPermitidos, Long minutosTranscurridos, Long minutosAdicionales) {
		this.cuenta = cuenta;
		this.valorSobreAdicional = valorSobreAdicional;
		this.valorMinutoAdicional = valorMinutoAdicional;
		this.minutosPermitidos = minutosPermitidos;
		this.minutosTranscurridos = minutosTranscurridos;
		this.minutosAdicionales = minutosAdicionales;
	}

	public Cuenta getCuenta() {
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
