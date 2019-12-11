package com.ceiba.adn.serviciobano.dominio.servicio.bano;

import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarObligatorio;

import com.ceiba.adn.serviciobano.comun.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionRestriccion;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionSinDatos;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;

public class ServicioEliminarBano {

	private static final String MSG_ID_OBLIGATORIO = "msg.err.servicio.eliminar.bano.id.obligatorio";
	private static final String MSG_BANO_POR_ID_NO_EXISTE = "msg.err.servicio.eliminar.bano.no.encontrado";
	private static final String MSG_BANO_OCUPADO_NO_ELIMINAR = "msg.err.servicio.eliminar.bano.estado.ocupado";

	private RepositorioBano repositorio;
	private Propiedades propMsg;

	public ServicioEliminarBano(RepositorioBano repositorio, Propiedades propMsg) {
		this.repositorio = repositorio;
		this.propMsg = propMsg;
	}

	public void eliminarBano(Long id) {
		validarObligatorio(id, propMsg.getPropiedad(MSG_ID_OBLIGATORIO));

		if (!repositorio.existePorId(id)) {
			throw new ExcepcionSinDatos(propMsg.getPropiedad(MSG_BANO_POR_ID_NO_EXISTE));
		}

		String estado = repositorio.estadoBano(id);
		if (EstadoBano.OCUPADO.getEstado().equals(estado)) {
			throw new ExcepcionRestriccion(propMsg.getPropiedad(MSG_BANO_OCUPADO_NO_ELIMINAR));
		}

		repositorio.eliminar(id);
	}

}
