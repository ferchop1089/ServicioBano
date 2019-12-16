package com.ceiba.adn.serviciobano.testdatabuilder;

import java.math.BigDecimal;

import com.ceiba.adn.serviciobano.dominio.modelo.Cobrar;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;

public class CobrarTestDataBuilder {

	private Cuenta cuenta;

	private BigDecimal tarifaSobreAdicional;

	private BigDecimal tarifaMinutoAdicional;

	private Long minutosPermitidos;

	private Long minutosTranscurridos;

	private Long minutosAdicionales;

	private Long sobresAdicionales;

	private BigDecimal tarifaMinutosPermitidos;

	private BigDecimal subtotalMinutosAdicionales;

	private BigDecimal subtotalSobresAdicionales;

	public CobrarTestDataBuilder() {
		this.cuenta = new CuentaTestDataBuilder().build();
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

	public CobrarTestDataBuilder withCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
		return this;
	}

	public CobrarTestDataBuilder withTarifaSobreAdicional(BigDecimal tarifaSobreAdicional) {
		this.tarifaSobreAdicional = tarifaSobreAdicional;
		return this;
	}

	public CobrarTestDataBuilder withTarifaMinutoAdicional(BigDecimal tarifaMinutoAdicional) {
		this.tarifaMinutoAdicional = tarifaMinutoAdicional;
		return this;
	}

	public CobrarTestDataBuilder withMinutosPermitidos(Long minutosPermitidos) {
		this.minutosPermitidos = minutosPermitidos;
		return this;
	}

	public CobrarTestDataBuilder withMinutosTranscurridos(Long minutosTranscurridos) {
		this.minutosTranscurridos = minutosTranscurridos;
		return this;
	}

	public CobrarTestDataBuilder withMinutosAdicionales(Long minutosAdicionales) {
		this.minutosAdicionales = minutosAdicionales;
		return this;
	}

	public CobrarTestDataBuilder withTarifaMinutosPermitidos(BigDecimal tarifaMinutosPermitidos) {
		this.tarifaMinutosPermitidos = tarifaMinutosPermitidos;
		return this;
	}

	public CobrarTestDataBuilder withSubtotalMinutosAdicionales(BigDecimal subtotalMinutosAdicionales) {
		this.subtotalMinutosAdicionales = subtotalMinutosAdicionales;
		return this;
	}

	public CobrarTestDataBuilder withSubtotalSobresAdicionales(BigDecimal subtotalSobresAdicionales) {
		this.subtotalSobresAdicionales = subtotalSobresAdicionales;
		return this;
	}

	public CobrarTestDataBuilder withSobresAdicionales(Long sobresAdicionales) {
		this.sobresAdicionales = sobresAdicionales;
		return this;
	}

	public Cobrar build() {
		return new Cobrar(cuenta, tarifaSobreAdicional, tarifaMinutoAdicional, minutosPermitidos, minutosTranscurridos,
				minutosAdicionales, tarifaMinutosPermitidos, subtotalMinutosAdicionales, subtotalSobresAdicionales,
				sobresAdicionales);
	}

}
