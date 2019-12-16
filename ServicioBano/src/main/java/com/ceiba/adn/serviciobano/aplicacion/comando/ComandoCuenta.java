package com.ceiba.adn.serviciobano.aplicacion.comando;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ComandoCuenta {

	private Long id;

	private Integer sobres;

	private Long idBano;

	private String estado;

	private BigDecimal totalCobro;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaIngreso;

	@JsonCreator
	public ComandoCuenta(@JsonProperty("id") Long id, @JsonProperty("idBano") Long idBano,
			@JsonProperty("sobres") Integer sobres, @JsonProperty("estado") String estado,
			@JsonProperty("totalCobro") BigDecimal totalCobro,
			@JsonProperty("fechaIngreso") LocalDateTime fechaIngreso) {
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

	public Long getIdBano() {
		return idBano;
	}

	public Integer getSobres() {
		return sobres;
	}

	public String getEstado() {
		return estado;
	}

	public LocalDateTime getFechaIngreso() {
		return fechaIngreso;
	}
	
	public void setFechaIngreso(LocalDateTime fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	
	public BigDecimal getTotalCobro() {
		return totalCobro;
	}

}
