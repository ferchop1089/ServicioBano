package com.ceiba.adn.serviciobano.dominio.servicio.bano;

import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarObligatorio;

import java.util.Optional;

import com.ceiba.adn.serviciobano.comun.puerto.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionSinDatos;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;

public class ServicioActualizarBano {

	private static final String MSG_BANO_OBLIGATORIO = "msg.err.servicio.actualizar.bano.obligatorio";
	private static final String MSG_ID_OBLIGATORIO = "msg.err.servicio.actualizar.bano.id.obligatorio";
	private static final String MSG_BANO_POR_ID_NO_EXISTE = "msg.err.servicio.actualizar.bano.no.encontrado";
	private static final String MSG_BANO_POR_IDENTIFICADOR_DUPLICADO = "msg.err.servicio.actualizar.bano.identificador.duplicado";

	private RepositorioBano repositorio;
	private Propiedades propMsg;

	public ServicioActualizarBano(RepositorioBano repositorio, Propiedades propMsg) {
		this.repositorio = repositorio;
		this.propMsg = propMsg;
	}

	public void actualizarBano(Bano bano) {
		validarObligatorio(bano, propMsg.getPropiedad(MSG_BANO_OBLIGATORIO));
		validarObligatorio(bano.getId(), propMsg.getPropiedad(MSG_ID_OBLIGATORIO));

		if (!repositorio.existePorId(bano.getId())) {
			throw new ExcepcionSinDatos(propMsg.getPropiedad(MSG_BANO_POR_ID_NO_EXISTE));
		}

		Optional<Bano> banoOpt = repositorio.buscarPorIdentificador(bano.getIdentificador());
		if (banoOpt.isPresent() && !banoOpt.filter(t -> t.getId().equals(bano.getId())).isPresent()) {
			throw new ExcepcionDuplicidad(propMsg.getPropiedad(MSG_BANO_POR_IDENTIFICADOR_DUPLICADO));
		}

		repositorio.actualizar(bano);
	}

}
