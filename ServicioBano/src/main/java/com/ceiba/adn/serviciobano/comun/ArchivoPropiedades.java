package com.ceiba.adn.serviciobano.comun;

import java.util.Objects;
import java.util.Properties;

import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;

public class ArchivoPropiedades implements Propiedades {

	private static final String MSG_VALOR_INVALIDO = "La propiedad '%s' no fue encontrada";

	private Properties properties;

	public ArchivoPropiedades(Properties properties) {
		this.properties = Properties.class.cast(properties.clone());
	}

	@Override
	public String getPropiedad(String propiedad) {
		String valor = properties.getProperty(propiedad);
		if (Objects.isNull(valor)) {
			throw new ExcepcionValorInvalido(String.format(MSG_VALOR_INVALIDO, propiedad));
		}
		return valor;
	}
}
