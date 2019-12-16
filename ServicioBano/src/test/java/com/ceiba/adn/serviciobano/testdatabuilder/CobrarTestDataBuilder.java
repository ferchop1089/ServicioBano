package com.ceiba.adn.serviciobano.testdatabuilder;

import java.math.BigDecimal;

import com.ceiba.adn.serviciobano.dominio.modelo.Cobrar;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;

public class CobrarTestDataBuilder {

	private Cuenta cuenta;

	private BigDecimal valorSobreAdicional;

	private BigDecimal valorMinutoAdicional;

	private Long minutosPermitidos;

	private Long minutosTranscurridos;

	private Long minutosAdicionales;

	public CobrarTestDataBuilder() {
		this.cuenta = new CuentaTestDataBuilder().build();
		this.valorSobreAdicional = new BigDecimal(200);
		this.valorMinutoAdicional = new BigDecimal(200);
		this.minutosPermitidos = 15L;
		this.minutosTranscurridos = 30L;
		this.minutosAdicionales = 15L;
	}

	public CobrarTestDataBuilder withCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
		return this;
	}

	public CobrarTestDataBuilder withValorSobreAdicional(BigDecimal valorSobreAdicional) {
		this.valorSobreAdicional = valorSobreAdicional;
		return this;
	}

	public CobrarTestDataBuilder withValorMinutoAdicional(BigDecimal valorMinutoAdicional) {
		this.valorMinutoAdicional = valorMinutoAdicional;
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

	public Cobrar build() {
		return new Cobrar(cuenta, valorSobreAdicional, valorMinutoAdicional, minutosPermitidos, minutosTranscurridos,
				minutosAdicionales);
	}

}
