package com.ceiba.adn.serviciobano.comun;

import java.util.Properties;

public final class FabricaPropiedades {

	private static final String ARCHIVO_CONFIGURACION_DEFAULT = "/dominio/configuracion/configuracionDefault.properties";
	private static final String ARCHIVO_MENSAJES_DEFAULT = "/dominio/mensajes/mensajesDefault.properties";

	private FabricaPropiedades() {
	}

	public static Propiedades propiedadConfiguracionPorDefecto() {
		return getPropiedades(ARCHIVO_CONFIGURACION_DEFAULT);
	}

	public static Propiedades propiedadMensajesPorDefecto() {
		return getPropiedades(ARCHIVO_MENSAJES_DEFAULT);
	}

	public static Propiedades cargarArchivoPropiedad(String archivoPropiedad) {
		return getPropiedades(archivoPropiedad);
	}

	private static Propiedades getPropiedades(String archivoPropiedad) {
		return new ArchivoPropiedades(
				new CargarArchivoPropiedades(archivoPropiedad, new Properties()).getPropiedades());
	}

}
