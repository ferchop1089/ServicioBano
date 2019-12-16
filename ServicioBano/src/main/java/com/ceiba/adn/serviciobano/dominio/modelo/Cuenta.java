package com.ceiba.adn.serviciobano.dominio.modelo;

import static com.ceiba.adn.serviciobano.dominio.modelo.validacion.ValidarCamposCuenta.validarCampos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Cuenta {

	private Long id0;

	private Integer sobres0;

	private Long idBano0;

	private String estado0;

	private BigDecimal totalCobro0;

	private LocalDateTime fechaIngreso0;

	public Cuenta(Long id, Long idBano, Integer sobres, String estado, BigDecimal totalCobro,
			LocalDateTime fechaIngreso) {
		validarCampos(estado, fechaIngreso);

		this.id0 = id;
		this.idBano0 = idBano;
		this.sobres0 = sobres;
		this.estado0 = estado;
		this.totalCobro0 = totalCobro;
		this.fechaIngreso0 = fechaIngreso;
	}

	public Long getId() {
		return id0;
	}

	public void setId(Long id0) {
		this.id0 = id0;
	}

	public Long getIdBano() {
		return idBano0;
	}

	public void setIdBano(Long idBano0) {
		this.idBano0 = idBano0;
	}

	public Integer getSobres() {
		return sobres0;
	}

	public void setSobres(Integer sobres0) {
		this.sobres0 = sobres0;
	}

	public String getEstado() {
		return estado0;
	}

	public LocalDateTime getFechaIngreso() {
		return fechaIngreso0;
	}

	public void setFechaIngreso(LocalDateTime fechaIngreso0) {
		this.fechaIngreso0 = fechaIngreso0;
	}

	public void setEstado(String estado) {
		this.estado0 = estado;
	}

	public BigDecimal getTotalCobro() {
		return totalCobro0;
	}

	public void setTotalCobro(BigDecimal totalCobro0) {
		this.totalCobro0 = totalCobro0;
	}

}
