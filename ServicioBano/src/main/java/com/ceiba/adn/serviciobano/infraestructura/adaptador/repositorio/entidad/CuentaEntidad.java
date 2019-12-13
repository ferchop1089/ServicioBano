package com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad;

import static com.ceiba.adn.serviciobano.dominio.modelo.validacion.ValidarCamposCuenta.validarCampos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "CuentaEntidad")
@Table(name = "CUENTA")
public class CuentaEntidad implements Serializable {

	private static final long serialVersionUID = 6045856970103249278L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "sobres")
	private Integer sobres;

	@Column(name = "idBano")
	private Long idBano;

	@Column(name = "estado")
	private String estado;

	@Column(name = "totalCobro")
	private BigDecimal totalCobro;

	@Column(name = "fechaIngreso")
	private LocalDateTime fechaIngreso;

	public CuentaEntidad() {
	}

	public CuentaEntidad(Long id, Long idBano, Integer sobres, String estado, BigDecimal totalCobro,
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

	public Integer getSobres() {
		return sobres;
	}

	public void setSobres(Integer sobres) {
		this.sobres = sobres;
	}

	public Long getIdBano() {
		return idBano;
	}

	public void setIdBano(Long idBano0) {
		this.idBano = idBano0;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado0) {
		this.estado = estado0;
	}

	public BigDecimal getTotalCobro() {
		return totalCobro;
	}

	public void setTotalCobro(BigDecimal totalCobro0) {
		this.totalCobro = totalCobro0;
	}

	public LocalDateTime getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDateTime fechaIngreso0) {
		this.fechaIngreso = fechaIngreso0;
	}

}
