package com.ceiba.adn.serviciobano.dominio.comun;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionLongitudValor;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;

public final class ValidadorArgumento {

	private ValidadorArgumento() {
	}

	public static void validarObligatorio(Object valor, String mensaje) {
		if (valor == null) {
			throw new ExcepcionValorObligatorio(mensaje);
		}
	}

	public static void validarSinEspacios(String valor, String mensaje) {
		if (valor.trim().isEmpty()) {
			throw new ExcepcionValorInvalido(mensaje);
		}
	}

	public static void validarTamano(Object valor, int longitudMinima, int longitudMaxima, String mensaje) {
		validarLongitudMinima(valor, longitudMinima, mensaje);
		validarLongitudMaxima(valor, longitudMaxima, mensaje);
	}

	public static void validarLongitudMinima(Object valor, int longitudMinima, String mensaje) {
		if (valor.toString().length() < longitudMinima) {
			throw new ExcepcionLongitudValor(mensaje);
		}
	}

	public static void validarLongitudMaxima(Object valor, int longitudMaxima, String mensaje) {
		if (valor.toString().length() > longitudMaxima) {
			throw new ExcepcionLongitudValor(mensaje);
		}
	}

	public static void validarRegex(String correoElectronico, String regex, String mensaje) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(correoElectronico);

		if (!matcher.matches()) {
			throw new ExcepcionValorInvalido(mensaje);
		}
	}

	public static void validarMenor(LocalDateTime fechaInicial, LocalDateTime fechaFinal, String mensaje) {
		if (fechaInicial.isAfter(fechaFinal)) {
			throw new ExcepcionValorInvalido(mensaje);
		}
	}

}
