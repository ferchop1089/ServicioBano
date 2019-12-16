package com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "BanoEntidad")
@Table(name = "BANO")
public class BanoEntidad implements Serializable {

	private static final long serialVersionUID = -4355079371381703359L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "identificador")
	private String identificador;

	@Column(name = "estado")
	private String estado;
	
	public BanoEntidad() {
	}

	public BanoEntidad(Long id, String identificador, String estado) {
		this.id = id;
		this.identificador = identificador;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id0) {
		this.id = id0;
	}

	public String getIdentificador() {
		return identificador;
	}

	public String getEstado() {
		return estado;
	}

}
