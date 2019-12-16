package com.ceiba.adn.serviciobano.aplicacion.comando;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ComandoCobrar {

	private ComandoCuenta cuenta;

	private BigDecimal tarifaSobreAdicional;

	private BigDecimal tarifaMinutoAdicional;

	private Long minutosPermitidos;

	private Long minutosTranscurridos;

	private Long minutosAdicionales;

	private Long sobresAdicionales;

	private BigDecimal tarifaMinutosPermitidos;

	private BigDecimal subtotalMinutosAdicionales;

	private BigDecimal subtotalSobresAdicionales;

	@JsonCreator
	public ComandoCobrar(@JsonProperty("cuenta") ComandoCuenta cuenta,
			@JsonProperty("tarifaSobreAdicional") BigDecimal tarifaSobreAdicional,
			@JsonProperty("tarifaMinutoAdicional") BigDecimal tarifaMinutoAdicional,
			@JsonProperty("minutosPermitidos") Long minutosPermitidos,
			@JsonProperty("minutosTranscurridos") Long minutosTranscurridos,
			@JsonProperty("minutosAdicionales") Long minutosAdicionales,
			@JsonProperty("tarifaMinutosPermitidos") BigDecimal tarifaMinutosPermitidos,
			@JsonProperty("subtotalMinutosAdicionales") BigDecimal subtotalMinutosAdicionales,
			@JsonProperty("subtotalSobresAdicionales") BigDecimal subtotalSobresAdicionales,
			@JsonProperty("sobresAdicionales") Long sobresAdicionales) {
		this.cuenta = cuenta;
		this.tarifaSobreAdicional = tarifaSobreAdicional;
		this.tarifaMinutoAdicional = tarifaMinutoAdicional;
		this.minutosPermitidos = minutosPermitidos;
		this.minutosTranscurridos = minutosTranscurridos;
		this.minutosAdicionales = minutosAdicionales;
		this.tarifaMinutosPermitidos = tarifaMinutosPermitidos;
		this.subtotalMinutosAdicionales = subtotalMinutosAdicionales;
		this.subtotalSobresAdicionales = subtotalSobresAdicionales;
		this.sobresAdicionales = sobresAdicionales;
	}

	public ComandoCuenta getCuenta() {
		return cuenta;
	}

	public BigDecimal getTarifaSobreAdicional() {
		return tarifaSobreAdicional;
	}

	public BigDecimal getTarifaMinutoAdicional() {
		return tarifaMinutoAdicional;
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

	public BigDecimal getTarifaMinutosPermitidos() {
		return tarifaMinutosPermitidos;
	}

	public BigDecimal getSubtotalMinutosAdicionales() {
		return subtotalMinutosAdicionales;
	}

	public BigDecimal getSubtotalSobresAdicionales() {
		return subtotalSobresAdicionales;
	}

	public Long getSobresAdicionales() {
		return sobresAdicionales;
	}

}
