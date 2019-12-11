package com.ceiba.adn.serviciobano.dominio.modelo.validacion;

import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarObligatorio;
import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarRegex;
import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarSinEspacios;
import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarTamano;

import java.text.MessageFormat;

import com.ceiba.adn.serviciobano.comun.puerto.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionLongitudValor;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.propiedades.FabricaPropiedades;

/**
 * The Class ValidarCamposBano.
 */
public final class ValidarCamposBano {

	/** The Constant MSG_IDENTIFICADOR_TIENE_LONGITUD_INCORRECTA. */
	private static final String MSG_IDENTIFICADOR_TIENE_LONGITUD_INCORRECTA = "msg.err.modelo.bano.identificador.longitud.incorrecta";

	/** The Constant MSG_IDENTIFICADOR_TIENE_SOLO_ESPACIOS. */
	private static final String MSG_IDENTIFICADOR_TIENE_SOLO_ESPACIOS = "msg.err.modelo.bano.identificador.espacios.incorrecto";

	/** The Constant MSG_IDENTIFICADOR_OBLIGATORIO. */
	private static final String MSG_IDENTIFICADOR_OBLIGATORIO = "msg.err.modelo.bano.identificador.obligatorio";

	/** The Constant MSG_ESTADO_ES_INCORRECTO. */
	private static final String MSG_ESTADO_ES_INCORRECTO = "msg.err.modelo.bano.estado.incorrecto";

	/** The Constant MSG_ESTADO_OBLIGATORIO. */
	private static final String MSG_ESTADO_OBLIGATORIO = "msg.err.modelo.bano.estado.obligatorio";

	/** The Constant CONFIG_IDENTIFICADOR_LONGITUD_MIN. */
	private static final String CONFIG_IDENTIFICADOR_LONGITUD_MIN = "prop.modelo.bano.identificador.longitud.min";

	/** The Constant CONFIG_IDENTIFICADOR_LONGITUD_MAX. */
	private static final String CONFIG_IDENTIFICADOR_LONGITUD_MAX = "prop.modelo.bano.identificador.longitud.max";

	/** The Constant VALIDACION_ESTADOS_REGEX. */
	private static final String VALIDACION_ESTADOS_REGEX = String.format("^(%1$s|%2$s|%3$s)$",
			EstadoBano.DISPONIBLE.getEstado(), EstadoBano.OCUPADO.getEstado(),
			EstadoBano.FUERA_DE_SERVICIO.getEstado());

	/** The Constant PROP_MSG. */
	private static final Propiedades PROP_MSG = FabricaPropiedades.propiedadMensajesPorDefecto();

	/** The Constant PROP_CONFIG. */
	private static final Propiedades PROP_CONFIG = FabricaPropiedades.propiedadConfiguracionPorDefecto();

	/**
	 * Instantiates a new validar campos bano.
	 */
	private ValidarCamposBano() {
	}

	/**
	 * Validar campos.
	 *
	 * @param identificador the identificador a validar
	 * @param estado        the estado a validar
	 * 
	 * @see ValidarCamposBano#validarCampoIdentificador(String)
	 * @see ValidarCamposBano#validarCampoEstado(String)
	 */
	public static void validarCampos(String identificador, String estado) {
		validarCampoIdentificador(identificador);
		validarCampoEstado(estado);
	}

	/**
	 * Validar campo identificador. Realiza las siguientes validaciones: <br>
	 * - Que el valor NO sea nulo <br>
	 * - Que la longitud del campo este entre el min y max configurado <br>
	 * - Que el valor NO sea sólo espacios p.e (' ')
	 *
	 * @param identificador the identificador a validar
	 * 
	 * @exception ExcepcionValorObligatorio cuando el Identificador es nulo
	 * @exception ExcepcionValorInvalido    cuando el Identificador es sólo espacios
	 * @exception ExcepcionLongitudValor    cuando la longitud del Identificador se
	 *                                      encuentra por fuera del min y max
	 *                                      configurado
	 */
	public static void validarCampoIdentificador(String identificador) {
		validarObligatorio(identificador, PROP_MSG.getPropiedad(MSG_IDENTIFICADOR_OBLIGATORIO));

		String min = PROP_CONFIG.getPropiedad(CONFIG_IDENTIFICADOR_LONGITUD_MIN);
		String max = PROP_CONFIG.getPropiedad(CONFIG_IDENTIFICADOR_LONGITUD_MAX);
		String msg = MessageFormat.format(PROP_MSG.getPropiedad(MSG_IDENTIFICADOR_TIENE_LONGITUD_INCORRECTA), min, max);
		validarTamano(identificador, Integer.valueOf(min), Integer.valueOf(max), msg);

		validarSinEspacios(identificador, PROP_MSG.getPropiedad(MSG_IDENTIFICADOR_TIENE_SOLO_ESPACIOS));
	}

	/**
	 * Validar campo estado. Realiza las siguientes validaciones: <br>
	 * - Valida que el Estado NO sea nulo <br>
	 * - Valida que el Estado NO sea diferente de EstadoBano.DISPONIBLE,
	 * EstadoBano.OCUPADO o EstadoBano.FUERA_DE_SERVICIO
	 *
	 * @param estado the estado a validar
	 * 
	 * @exception ExcepcionValorObligatorio cuando el estado es nulo
	 * @exception ExcepcionValorInvalido    cuando el estado es diferente de
	 *                                      EstadoBano.DISPONIBLE,
	 *                                      EstadoBano.OCUPADO o
	 *                                      EstadoBano.FUERA_DE_SERVICIO
	 */
	public static void validarCampoEstado(String estado) {
		validarObligatorio(estado, PROP_MSG.getPropiedad(MSG_ESTADO_OBLIGATORIO));
		String msg = MessageFormat.format(PROP_MSG.getPropiedad(MSG_ESTADO_ES_INCORRECTO),
				EstadoBano.DISPONIBLE.getEstado(), EstadoBano.OCUPADO.getEstado(),
				EstadoBano.FUERA_DE_SERVICIO.getEstado());
		validarRegex(estado, VALIDACION_ESTADOS_REGEX, msg);
	}

}
