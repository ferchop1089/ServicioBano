package com.ceiba.adn.serviciobano.infraestructura.adaptador.propiedades;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.ceiba.adn.serviciobano.comun.puerto.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.propiedades.FabricaPropiedades;

public class FabricaPropiedadesTest {

	private static final String PATH_ARCHIVO_CORRECTO = "/dominio/configuracion/configuracionDefault.properties";
	private static final String PATH_ARCHIVO_INCORRECTO = "/dominio/configuracion/configuracionIncorrecto.properties";

	private static final String MSG_VALOR_INVALIDO = "No fue posible cargar el archivo de propiedades indicado en la ruta %s";

	@Test
	public void cuandoEnviaPathCorrectoEntoncesCargarArchivoPropieades() {
		// arrange
		Propiedades prop;

		// act
		prop = FabricaPropiedades.cargarArchivoPropiedad(PATH_ARCHIVO_CORRECTO);

		// assert
		assertThat(prop, is(notNullValue()));
	}

	@Test
	public void cuandoEnviaPathIncorrectoEntoncesLanzarExcepcion() {
		try {
			// arrange - act
			FabricaPropiedades.cargarArchivoPropiedad(PATH_ARCHIVO_INCORRECTO);
			fail();
		} catch (ExcepcionValorInvalido e) {
			// assert
			assertThat(e.getMessage(), equalTo(String.format(MSG_VALOR_INVALIDO, PATH_ARCHIVO_INCORRECTO)));
		}
	}

}
