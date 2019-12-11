package com.ceiba.adn.serviciobano.comun;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import com.ceiba.adn.serviciobano.comun.excepcion.ExcepcionLecturaArchivo;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;

public class CargarArchivoPropiedades {

	private static final String MSG_PATH_OBLIGATORIO = "El path para cargar el archivo de propiedades no debe estar vacío";
	private static final String MSG_VALOR_INVALIDO = "No fue posible cargar el archivo de propiedades indicado en la ruta %s";
	private static final String MSG_LECTURA_FALLIDA = "Se presento un problema al tratar de leer el archivo de propiedades indicado en la ruta %s";

	private Properties properties;

	public CargarArchivoPropiedades(String pathArchivoPropiedades, Properties properties) {
		this.properties = Properties.class.cast(properties.clone());
		cargarArchivoPropiedades(pathArchivoPropiedades);
	}

	private void cargarArchivoPropiedades(String pathArchivoPropiedades) {
		if (Objects.isNull(pathArchivoPropiedades) || pathArchivoPropiedades.trim().isEmpty()) {
			throw new ExcepcionValorObligatorio(MSG_PATH_OBLIGATORIO);
		}

		InputStream input = getClass().getResourceAsStream(pathArchivoPropiedades);
		if (Objects.isNull(input)) {
			throw new ExcepcionValorInvalido(String.format(MSG_VALOR_INVALIDO, pathArchivoPropiedades));
		}

		try {
			properties.load(input);
		} catch (IOException e) {
			throw new ExcepcionLecturaArchivo(String.format(MSG_LECTURA_FALLIDA, pathArchivoPropiedades));
		}
	}

	public Properties getPropiedades() {
		return Properties.class.cast(properties.clone());
	}

}
