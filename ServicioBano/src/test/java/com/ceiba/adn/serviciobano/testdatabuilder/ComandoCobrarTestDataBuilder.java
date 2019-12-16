package com.ceiba.adn.serviciobano.testdatabuilder;

import java.math.BigDecimal;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCobrar;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;

public class ComandoCobrarTestDataBuilder {

	private ComandoCuenta cuenta;

	private BigDecimal valorSobreAdicional;

	private BigDecimal valorMinutoAdicional;

	private Long minutosPermitidos;

	private Long minutosTranscurridos;

	private Long minutosAdicionales;

	public ComandoCobrarTestDataBuilder() {
		this.cuenta = new ComandoCuentaTestDataBuilder().build();
		this.valorSobreAdicional = new BigDecimal(200);
		this.valorMinutoAdicional = new BigDecimal(200);
		this.minutosPermitidos = 15L;
		this.minutosTranscurridos = 30L;
		this.minutosAdicionales = 15L;
	}

	public ComandoCobrarTestDataBuilder withCuenta(ComandoCuenta cuenta) {
		this.cuenta = cuenta;
		return this;
	}

	public ComandoCobrarTestDataBuilder withValorSobreAdicional(BigDecimal valorSobreAdicional) {
		this.valorSobreAdicional = valorSobreAdicional;
		return this;
	}

	public ComandoCobrarTestDataBuilder withValorMinutoAdicional(BigDecimal valorMinutoAdicional) {
		this.valorMinutoAdicional = valorMinutoAdicional;
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

	public ComandoCobrar build() {
		return new ComandoCobrar(cuenta, valorSobreAdicional, valorMinutoAdicional, minutosPermitidos,
				minutosTranscurridos, minutosAdicionales);
	}

}
