package com.ceiba.adn.serviciobano.dominio.modelo.validacion;

import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarMenor;
import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarObligatorio;
import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarRegex;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import com.ceiba.adn.serviciobano.comun.FabricaPropiedades;
import com.ceiba.adn.serviciobano.comun.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoCuenta;

public final class ValidarCamposCuenta {

	/** The Constant MSG_ESTADO_ES_INCORRECTO. */
	private static final String MSG_ESTADO_ES_INCORRECTO = "msg.err.modelo.cuenta.estado.incorrecto";

	/** The Constant MSG_ESTADO_OBLIGATORIO. */
	private static final String MSG_ESTADO_OBLIGATORIO = "msg.err.modelo.cuenta.estado.obligatorio";

	/** The Constant MSG_FECHA_INGRESO_MAYOR_QUE_FECHA_ACTUAL. */
	private static final String MSG_FECHA_INGRESO_MAYOR_QUE_FECHA_ACTUAL = "msg.err.modelo.cuenta.fecha.ingreso.furuta";

	/** The Constant VALIDACION_ESTADOS_REGEX. */
	private static final String VALIDACION_ESTADOS_REGEX = String.format("^(%1$s|%2$s)$",
			EstadoCuenta.ABIERTA.getEstado(), EstadoCuenta.CERRADA.getEstado());

	/** The Constant PROP_MSG. */
	private static final Propiedades PROP_MSG = FabricaPropiedades.propiedadMensajesPorDefecto();

	/**
	 * Instantiates a new validar campos cuenta.
	 */
	private ValidarCamposCuenta() {
	}

	/**
	 * Validar campos.
	 *
	 * @param estado       the estado a validar
	 * @param fechaIngreso the fechaIngreso a validar
	 * 
	 * @see ValidarCamposCuenta#validarCampoIdentificador(String)
	 * @see ValidarCamposCuenta#validarCampoEstado(String)
	 * @see ValidarCamposCuenta#validarCampoFechaIngreso(LocalDateTime)
	 */
	public static void validarCampos(String estado, LocalDateTime fechaIngreso) {
		validarCampoEstado(estado);
		validarCampoFechaIngreso(fechaIngreso);
	}

	/**
	 * Validar campo estado. Realiza las siguientes validaciones: <br>
	 * - Valida que el Estado NO sea nulo <br>
	 * - Valida que el Estado NO sea diferente de {@link EstadoCuenta.ABIERTA} O
	 * {@link EstadoCuenta.CERRADA}
	 *
	 * @param estado the estado a validar
	 * 
	 * @exception ExcepcionValorObligatorio cuando el estado es nulo
	 * @exception ExcepcionValorInvalido    cuando el estado es diferente de
	 *                                      {@link EstadoCuenta.ABIERTA} O
	 *                                      {@link EstadoCuenta.CERRADA}
	 */
	public static void validarCampoEstado(String estado) {
		validarObligatorio(estado, PROP_MSG.getPropiedad(MSG_ESTADO_OBLIGATORIO));
		String msg = MessageFormat.format(PROP_MSG.getPropiedad(MSG_ESTADO_ES_INCORRECTO),
				EstadoCuenta.ABIERTA.getEstado(), EstadoCuenta.CERRADA.getEstado());
		validarRegex(estado, VALIDACION_ESTADOS_REGEX, msg);
	}

	/**
	 * Validar campo fecha ingreso. Valida que la fecha no sea mayor a la actual
	 *
	 * @param id the id a validar
	 * 
	 * @exception ExcepcionValorInvalido cuando la fecha es mayor a la actual
	 */
	public static void validarCampoFechaIngreso(LocalDateTime fechaIngreso) {
		validarMenor(fechaIngreso, LocalDateTime.now(),
				PROP_MSG.getPropiedad(MSG_FECHA_INGRESO_MAYOR_QUE_FECHA_ACTUAL));
	}

}
