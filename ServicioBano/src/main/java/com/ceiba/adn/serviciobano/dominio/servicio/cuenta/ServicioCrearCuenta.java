package com.ceiba.adn.serviciobano.dominio.servicio.cuenta;

import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarObligatorio;

import java.util.Objects;

import com.ceiba.adn.serviciobano.comun.puerto.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionRestriccion;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioCuenta;

public class ServicioCrearCuenta {

	private static final String MSG_CUENTA_OBLIGATORIO = "msg.err.servicio.crear.cuenta.obligatorio";
	private static final String MSG_CUENTA_BANO_ID_OBLIGATORIO = "msg.err.servicio.crear.cuenta.idbano.obligatorio";
	private static final String MSG_CUENTA_POR_ID_DUPLICADO = "msg.err.servicio.crear.cuenta.id.duplicado";
	private static final String MSG_CUENTA_ID_BANO_NO_EXISTE = "msg.err.servicio.crear.cuenta.idbano.no.existe";
	private static final String MSG_CUENTA_BANO_NO_DISPONIBLE = "msg.err.servicio.crear.cuenta.bano.no.disponible";

	private RepositorioCuenta repositorioCuenta;
	private RepositorioBano repositorioBano;
	private Propiedades propMsg;

	public ServicioCrearCuenta(RepositorioCuenta repositorio, RepositorioBano repositorioBano, Propiedades propMsg) {
		this.repositorioCuenta = repositorio;
		this.repositorioBano = repositorioBano;
		this.propMsg = propMsg;
	}

	public Long crearCuenta(Cuenta cuenta) {
		validarObligatorio(cuenta, propMsg.getPropiedad(MSG_CUENTA_OBLIGATORIO));
		validarObligatorio(cuenta.getIdBano(), propMsg.getPropiedad(MSG_CUENTA_BANO_ID_OBLIGATORIO));

		if (Objects.nonNull(cuenta.getId()) && repositorioCuenta.existePorId(cuenta.getId())) {
			throw new ExcepcionDuplicidad(propMsg.getPropiedad(MSG_CUENTA_POR_ID_DUPLICADO));
		}
		if (!repositorioBano.existePorId(cuenta.getIdBano())) {
			throw new ExcepcionValorInvalido(propMsg.getPropiedad(MSG_CUENTA_ID_BANO_NO_EXISTE));
		}
		if (!EstadoBano.DISPONIBLE.getEstado().equals(repositorioBano.estadoBano(cuenta.getIdBano()))) {
			throw new ExcepcionRestriccion(propMsg.getPropiedad(MSG_CUENTA_BANO_NO_DISPONIBLE));
		}

		return repositorioCuenta.crear(cuenta);
	}

}
