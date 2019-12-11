package com.ceiba.adn.serviciobano.testdatabuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoCuenta;

public class ComandoCuentaTestDataBuilder {

	private Long id;

	private Integer sobres;

	private Long idBano;

	private String estado;

	private BigDecimal totalCobro;

	private LocalDateTime fechaIngreso;

	public ComandoCuentaTestDataBuilder() {
		this.id = 1L;
		this.idBano = 2L;
		this.sobres = 1;
		this.estado = EstadoCuenta.ABIERTA.getEstado();
		this.totalCobro = new BigDecimal(0);
		this.fechaIngreso = LocalDateTime.of(2019, 12, 8, 6, 0);
	}

	public ComandoCuentaTestDataBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public ComandoCuentaTestDataBuilder withSobres(Integer sobres) {
		this.sobres = sobres;
		return this;
	}

	public ComandoCuentaTestDataBuilder withIdBano(Long idBano) {
		this.idBano = idBano;
		return this;
	}

	public ComandoCuentaTestDataBuilder withEstado(String estado) {
		this.estado = estado;
		return this;
	}

	public ComandoCuentaTestDataBuilder withTotalCobro(BigDecimal totalCobro) {
		this.totalCobro = totalCobro;
		return this;
	}

	public ComandoCuentaTestDataBuilder withFechaIngreso(LocalDateTime fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
		return this;
	}

	public ComandoCuenta build() {
		return new ComandoCuenta(id, idBano, sobres, estado, totalCobro, fechaIngreso);
	}

}
