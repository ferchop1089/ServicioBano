package com.ceiba.adn.serviciobano.testdatabuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad.CuentaEntidad;

public class CuentaEntidadTestDataBuilder {

	private Long id;

	private Integer sobres;

	private Long idBano;

	private String estado;

	private BigDecimal totalCobro;

	private LocalDateTime fechaIngreso;

	public CuentaEntidadTestDataBuilder() {
		this.id = 1L;
		this.idBano = 1L;
		this.sobres = 1;
		this.estado = "ABIERTA";
		this.totalCobro = new BigDecimal(0);
		this.fechaIngreso = LocalDateTime.of(2019, 12, 8, 6, 0);
	}

	public CuentaEntidadTestDataBuilder withId(Long id0) {
		this.id = id0;
		return this;
	}

	public CuentaEntidadTestDataBuilder withSobres(Integer sobres) {
		this.sobres = sobres;
		return this;
	}

	public CuentaEntidadTestDataBuilder withIdBano(Long idBano0) {
		this.idBano = idBano0;
		return this;
	}

	public CuentaEntidadTestDataBuilder withEstado(String estado0) {
		this.estado = estado0;
		return this;
	}

	public CuentaEntidadTestDataBuilder withTotalCobro(BigDecimal totalCobro0) {
		this.totalCobro = totalCobro0;
		return this;
	}

	public CuentaEntidadTestDataBuilder withFechaIngreso(LocalDateTime fechaIngreso0) {
		this.fechaIngreso = fechaIngreso0;
		return this;
	}

	public CuentaEntidad build() {
		return new CuentaEntidad(id, idBano, sobres, estado, totalCobro, fechaIngreso);
	}

}
