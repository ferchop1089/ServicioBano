package com.ceiba.adn.serviciobano.testdatabuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoCuenta;

public class CuentaTestDataBuilder {

	private Long id;

	private Integer sobres;

	private Long idBano;

	private String estado;

	private BigDecimal totalCobro;

	private LocalDateTime fechaIngreso;

	public CuentaTestDataBuilder() {
		this.id = 1L;
		this.idBano = 1L;
		this.sobres = 1;
		this.estado = EstadoCuenta.ABIERTA.getEstado();
		this.totalCobro = new BigDecimal(0);
		this.fechaIngreso = LocalDateTime.of(2019, 12, 8, 6, 0);
	}

	public CuentaTestDataBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public CuentaTestDataBuilder withSobres(Integer sobres) {
		this.sobres = sobres;
		return this;
	}

	public CuentaTestDataBuilder withIdBano(Long idBano) {
		this.idBano = idBano;
		return this;
	}

	public CuentaTestDataBuilder withEstado(String estado) {
		this.estado = estado;
		return this;
	}

	public CuentaTestDataBuilder withTotalCobro(BigDecimal totalCobro) {
		this.totalCobro = totalCobro;
		return this;
	}

	public CuentaTestDataBuilder withFechaIngreso(LocalDateTime fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
		return this;
	}

	public Cuenta build() {
		return new Cuenta(id, idBano, sobres, estado, totalCobro, fechaIngreso);
	}

}
