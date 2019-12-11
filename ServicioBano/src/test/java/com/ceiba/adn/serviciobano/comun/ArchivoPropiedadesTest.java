package com.ceiba.adn.serviciobano.comun;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ceiba.adn.serviciobano.comun.ArchivoPropiedades;
import com.ceiba.adn.serviciobano.comun.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;

public class ArchivoPropiedadesTest {

	private static final String MSG_VALOR_INVALIDO = "La propiedad '%s' no fue encontrada";

	private Propiedades propiedad;

	@Mock
	private Properties prop;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		doReturn(prop).when(prop).clone();
		propiedad = new ArchivoPropiedades(prop);
	}

	@Test
	public void cuandoBuscarValorPropiedadEsNuloEntoncesLanzarExcepcion() {
		// arrange
		String propString = "propiedad.a.buscar";
		doReturn(null).when(prop).getProperty(propString);

		try {
			// act
			propiedad.getPropiedad(propString);
			fail();
		} catch (ExcepcionValorInvalido e) {
			// assert
			assertThat(e.getMessage(), equalTo(String.format(MSG_VALOR_INVALIDO, propString)));
			verify(prop, times(1)).getProperty(anyString());
		}

	}

	@Test
	public void cuandoBuscarValorPropiedadExisteEntoncesRetornarValor() {
		// arrange
		String propString = "propiedad.a.buscar";
		String valor = "Valor retornado a partir de la propiedad";
		doReturn(valor).when(prop).getProperty(propString);

		// act
		String valorRetornado = propiedad.getPropiedad(propString);

		// assert
		assertThat(valorRetornado, equalTo(valor));
		verify(prop, times(1)).getProperty(anyString());
	}

}
