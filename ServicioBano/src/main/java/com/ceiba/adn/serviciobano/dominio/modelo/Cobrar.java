package com.ceiba.adn.serviciobano.dominio.modelo;

import java.math.BigDecimal;

public class Cobrar {

	private Cuenta cuenta0;

	private BigDecimal tarifaSobreAdicional0;

	private BigDecimal tarifaMinutoAdicional0;

	private Long minutosPermitidos0;

	private Long minutosTranscurridos0;

	private Long minutosAdicionales0;

	private Long sobresAdicionales0;

	private BigDecimal tarifaMinutosPermitidos0;

	private BigDecimal subtotalMinutosAdicionales0;

	private BigDecimal subtotalSobresAdicionales0;

	public Cobrar(Cuenta cuenta, BigDecimal tarifaSobreAdicional, BigDecimal tarifaMinutoAdicional,
			Long minutosPermitidos, Long minutosTranscurridos, Long minutosAdicionales,
			BigDecimal tarifaMinutosPermitidos, BigDecimal subtotalMinutosAdicionales,
			BigDecimal subtotalSobresAdicionales, Long sobresAdicionales) {
		this.cuenta0 = cuenta;
		this.tarifaSobreAdicional0 = tarifaSobreAdicional;
		this.tarifaMinutoAdicional0 = tarifaMinutoAdicional;
		this.minutosPermitidos0 = minutosPermitidos;
		this.minutosTranscurridos0 = minutosTranscurridos;
		this.minutosAdicionales0 = minutosAdicionales;
		this.tarifaMinutosPermitidos0 = tarifaMinutosPermitidos;
		this.subtotalMinutosAdicionales0 = subtotalMinutosAdicionales;
		this.subtotalSobresAdicionales0 = subtotalSobresAdicionales;
		this.sobresAdicionales0 = sobresAdicionales;
	}

	public Cuenta getCuenta() {
		return cuenta0;
	}

	public BigDecimal getTarifaSobreAdicional() {
		return tarifaSobreAdicional0;
	}

	public BigDecimal getTarifaMinutoAdicional() {
		return tarifaMinutoAdicional0;
	}

	public Long getMinutosPermitidos() {
		return minutosPermitidos0;
	}

	public Long getMinutosTranscurridos() {
		return minutosTranscurridos0;
	}

	public Long getMinutosAdicionales() {
		return minutosAdicionales0;
	}

	public BigDecimal getTarifaMinutosPermitidos() {
		return tarifaMinutosPermitidos0;
	}

	public BigDecimal getSubtotalMinutosAdicionales() {
		return subtotalMinutosAdicionales0;
	}

	public BigDecimal getSubtotalSobresAdicionales() {
		return subtotalSobresAdicionales0;
	}
	
	public Long getSobresAdicionales() {
		return sobresAdicionales0;
	}

}
