package com.ceiba.adn.serviciobano.dominio.modelo;

import static com.ceiba.adn.serviciobano.dominio.modelo.validacion.ValidarCamposCuenta.validarCampos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Cuenta {

	private Long id;

	private Integer sobres;

	private Long idBano;

	private String estado;

	private BigDecimal totalCobro;

	private LocalDateTime fechaIngreso;

	public Cuenta(Long id, Long idBano, Integer sobres, String estado, BigDecimal totalCobro,
			LocalDateTime fechaIngreso) {
		validarCampos(estado, fechaIngreso);

		this.id = id;
		this.idBano = idBano;
		this.sobres = sobres;
		this.estado = estado;
		this.totalCobro = totalCobro;
		this.fechaIngreso = fechaIngreso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id0) {
		this.id = id0;
	}

	public Long getIdBano() {
		return idBano;
	}

	public void setIdBano(Long idBano0) {
		this.idBano = idBano0;
	}

	public Integer getSobres() {
		return sobres;
	}

	public void setSobres(Integer sobres0) {
		this.sobres = sobres0;
	}

	public String getEstado() {
		return estado;
	}

	public LocalDateTime getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDateTime fechaIngreso0) {
		this.fechaIngreso = fechaIngreso0;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getTotalCobro() {
		return totalCobro;
	}

	public void setTotalCobro(BigDecimal totalCobro0) {
		this.totalCobro = totalCobro0;
	}

}
