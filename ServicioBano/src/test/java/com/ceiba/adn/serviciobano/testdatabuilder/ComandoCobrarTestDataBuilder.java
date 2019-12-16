package com.ceiba.adn.serviciobano.testdatabuilder;

import java.math.BigDecimal;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCobrar;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;

public class ComandoCobrarTestDataBuilder {

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

	public ComandoCobrarTestDataBuilder() {
		this.cuenta = new ComandoCuentaTestDataBuilder().build();
		this.tarifaSobreAdicional = new BigDecimal(200);
		this.tarifaMinutoAdicional = new BigDecimal(100);
		this.minutosPermitidos = 15L;
		this.minutosTranscurridos = 8L;
		this.minutosAdicionales = 0L;
		this.tarifaMinutosPermitidos = new BigDecimal(1000);
		this.subtotalMinutosAdicionales = new BigDecimal(0L);
		this.subtotalSobresAdicionales = new BigDecimal(0L);
		this.sobresAdicionales = 0L;
	}

	public ComandoCobrarTestDataBuilder withCuenta(ComandoCuenta cuenta) {
		this.cuenta = cuenta;
		return this;
	}

	public ComandoCobrarTestDataBuilder withTarifaSobreAdicional(BigDecimal tarifaSobreAdicional) {
		this.tarifaSobreAdicional = tarifaSobreAdicional;
		return this;
	}

	public ComandoCobrarTestDataBuilder withTarifaMinutoAdicional(BigDecimal tarifaMinutoAdicional) {
		this.tarifaMinutoAdicional = tarifaMinutoAdicional;
		return this;
	}

	public ComandoCobrarTestDataBuilder withMinutosPermitidos(Long minutosPermitidos) {
		this.minutosPermitidos = minutosPermitidos;
		return this;
	}

	public ComandoCobrarTestDataBuilder withMinutosTranscurridos(Long minutosTranscurridos) {
		this.minutosTranscurridos = minutosTranscurridos;
		return this;
	}

	public ComandoCobrarTestDataBuilder withMinutosAdicionales(Long minutosAdicionales) {
		this.minutosAdicionales = minutosAdicionales;
		return this;
	}

	public ComandoCobrarTestDataBuilder withTarifaMinutosPermitidos(BigDecimal tarifaMinutosPermitidos) {
		this.tarifaMinutosPermitidos = tarifaMinutosPermitidos;
		return this;
	}

	public ComandoCobrarTestDataBuilder withSubtotalMinutosAdicionales(BigDecimal subtotalMinutosAdicionales) {
		this.subtotalMinutosAdicionales = subtotalMinutosAdicionales;
		return this;
	}

	public ComandoCobrarTestDataBuilder withSubtotalSobresAdicionales(BigDecimal subtotalSobresAdicionales) {
		this.subtotalSobresAdicionales = subtotalSobresAdicionales;
		return this;
	}

	public ComandoCobrarTestDataBuilder withSobresAdicionales(Long sobresAdicionales) {
		this.sobresAdicionales = sobresAdicionales;
		return this;
	}

	public ComandoCobrar build() {
		return new ComandoCobrar(cuenta, tarifaSobreAdicional, tarifaMinutoAdicional, minutosPermitidos,
				minutosTranscurridos, minutosAdicionales, tarifaMinutosPermitidos, subtotalMinutosAdicionales,
				subtotalSobresAdicionales, sobresAdicionales);
	}

}
