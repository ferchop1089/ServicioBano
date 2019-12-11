package com.ceiba.adn.serviciobano.dominio.servicio.bano;

import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarObligatorio;

import java.util.Objects;

import com.ceiba.adn.serviciobano.comun.puerto.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;

public class ServicioCrearBano {

	private static final String MSG_BANO_OBLIGATORIO = "msg.err.servicio.crear.bano.obligatorio";
	private static final String MSG_BANO_POR_ID_DUPLICADO = "msg.err.servicio.crear.bano.id.duplicado";
	private static final String MSG_BANO_POR_IDENTIFICADOR_DUPLICADO = "msg.err.servicio.crear.bano.identificador.duplicado";

	private RepositorioBano repositorio;
	private Propiedades propMsg;

	public ServicioCrearBano(RepositorioBano repositorio, Propiedades propMsg) {
		this.repositorio = repositorio;
		this.propMsg = propMsg;
	}

	public Long crearBano(Bano bano) {
		validarObligatorio(bano, propMsg.getPropiedad(MSG_BANO_OBLIGATORIO));

		if (Objects.nonNull(bano.getId()) && repositorio.existePorId(bano.getId())) {
			throw new ExcepcionDuplicidad(propMsg.getPropiedad(MSG_BANO_POR_ID_DUPLICADO));
		}
		if (repositorio.existePorIdentificador(bano.getIdentificador())) {
			throw new ExcepcionDuplicidad(propMsg.getPropiedad(MSG_BANO_POR_IDENTIFICADOR_DUPLICADO));
		}

		return repositorio.crear(bano);
	}

}
