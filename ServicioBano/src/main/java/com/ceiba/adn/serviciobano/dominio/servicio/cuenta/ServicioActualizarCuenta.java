package com.ceiba.adn.serviciobano.dominio.servicio.cuenta;

import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarObligatorio;

import java.util.Objects;

import com.ceiba.adn.serviciobano.comun.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionSinDatos;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioCuenta;

public class ServicioActualizarCuenta {

	private static final String MSG_CUENTA_OBLIGATORIO = "msg.err.servicio.actualizar.cuenta.obligatorio";
	private static final String MSG_ID_OBLIGATORIO = "msg.err.servicio.actualizar.cuenta.id.obligatorio";
	private static final String MSG_POR_ID_NO_EXISTE = "msg.err.servicio.actualizar.cuenta.no.encontrada";
	private static final String MSG_CUENTA_SOBRES_NEGATIVO = "msg.err.servicio.actualizar.cuenta.sobres.en.negativo";
	private static final String MSG_CUENTA_ID_BANO_NO_EXISTE = "msg.err.servicio.actualizar.cuenta.idbano.no.existe";
	private static final String MSG_CUENTA_TOTAL_COBRO_NEGATIVO = "msg.err.servicio.actualizar.cuenta.total.cobro.en.negativo";

	private RepositorioCuenta repositorioCuenta;
	private RepositorioBano repositorioBano;
	private Propiedades propMsg;

	public ServicioActualizarCuenta(RepositorioCuenta repositorioCuenta, RepositorioBano repositorioBano,
			Propiedades propMsg) {
		this.repositorioCuenta = repositorioCuenta;
		this.repositorioBano = repositorioBano;
		this.propMsg = propMsg;
	}

	public void actualizarCuenta(Cuenta cuenta) {
		validarObligatorio(cuenta, propMsg.getPropiedad(MSG_CUENTA_OBLIGATORIO));
		validarObligatorio(cuenta.getId(), propMsg.getPropiedad(MSG_ID_OBLIGATORIO));

		if (!repositorioCuenta.existePorId(cuenta.getId())) {
			throw new ExcepcionSinDatos(propMsg.getPropiedad(MSG_POR_ID_NO_EXISTE));
		}
		if (Objects.nonNull(cuenta.getIdBano()) && !repositorioBano.existePorId(cuenta.getIdBano())) {
			throw new ExcepcionValorInvalido(propMsg.getPropiedad(MSG_CUENTA_ID_BANO_NO_EXISTE));
		}
		if (Objects.nonNull(cuenta.getSobres()) && cuenta.getSobres() < 0) {
			throw new ExcepcionValorInvalido(propMsg.getPropiedad(MSG_CUENTA_SOBRES_NEGATIVO));
		}
		if (Objects.nonNull(cuenta.getTotalCobro()) && cuenta.getTotalCobro().signum() == -1) {
			throw new ExcepcionValorInvalido(propMsg.getPropiedad(MSG_CUENTA_TOTAL_COBRO_NEGATIVO));
		}

		repositorioCuenta.actualizar(cuenta);
	}

}
