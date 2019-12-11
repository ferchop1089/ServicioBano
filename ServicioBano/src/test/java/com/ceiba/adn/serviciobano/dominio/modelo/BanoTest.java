package com.ceiba.adn.serviciobano.dominio.modelo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionLongitudValor;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.testdatabuilder.BanoTestDataBuilder;

public class BanoTest {

	@Test
	public void validarGetters() {
		// arrange
		Bano bano;

		// act
		bano = new BanoTestDataBuilder().build();

		// assert
		assertThat(bano.getId(), equalTo(1L));
		assertThat(bano.getIdentificador(), equalTo("Baño 1"));
		assertThat(bano.getEstado(), equalTo(EstadoBano.DISPONIBLE.getEstado()));
	}

	@Test
	public void validarSetters() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();

		// act
		bano.setId(2L);
		bano.setIdentificador("Baño 2");
		bano.setEstado(EstadoBano.FUERA_DE_SERVICIO.getEstado());

		// assert
		assertThat(bano.getId(), equalTo(2L));
		assertThat(bano.getIdentificador(), equalTo("Baño 2"));
		assertThat(bano.getEstado(), equalTo(EstadoBano.FUERA_DE_SERVICIO.getEstado()));
	}

	@Test
	public void validarConstructor1() {
		// arrange
		Bano bano;

		// act
		bano = new Bano(1L, "Baño 1", EstadoBano.DISPONIBLE.getEstado());

		// assert
		assertThat(bano.getId(), equalTo(1L));
		assertThat(bano.getIdentificador(), equalTo("Baño 1"));
		assertThat(bano.getEstado(), equalTo(EstadoBano.DISPONIBLE.getEstado()));
	}

	@Test
	public void cuandoCamposBanoCorrectosEntoncesValidacionExitosa() {
		// arrange
		Bano bano;

		// act
		bano = new BanoTestDataBuilder().build();

		// assert
		assertThat(bano.getId(), equalTo(1L));
		assertThat(bano.getIdentificador(), equalTo("Baño 1"));
		assertThat(bano.getEstado(), equalTo(EstadoBano.DISPONIBLE.getEstado()));
	}

	@Test
	public void cuandoLongitudCampoIdentificadorEsMayorA10EntoncesValidacionFalla() {
		// arrange
		BanoTestDataBuilder builder = new BanoTestDataBuilder();
		builder.withIdentificador("12345678901");

		try {
			// act
			builder.build();
			fail();
		} catch (ExcepcionLongitudValor e) {
			// assert
			assertThat(e.getMessage(), is(notNullValue()));
		}
	}

	@Test
	public void cuandoLongitudCampoIdentificadorEsCeroEntoncesValidacionFalla() {
		// arrange
		BanoTestDataBuilder builder = new BanoTestDataBuilder();
		builder.withIdentificador("");

		try {
			// act
			builder.build();
			fail();
		} catch (ExcepcionLongitudValor e) {
			// assert
			assertThat(e.getMessage(), is(notNullValue()));
		}
	}

	@Test
	public void cuandoCampoIdentificadorTieneSoloEspaciosEntoncesValidacionFalla() {
		// arrange
		BanoTestDataBuilder builder = new BanoTestDataBuilder();
		builder.withIdentificador("    ");

		try {
			// act
			builder.build();
			fail();
		} catch (ExcepcionValorInvalido e) {
			// assert
			assertThat(e.getMessage(), is(notNullValue()));
		}
	}

	@Test
	public void cuandoValorCampoEstadoIncorrectoEntoncesValidacionFalla() {
		// arrange
		BanoTestDataBuilder builder = new BanoTestDataBuilder();
		builder.withEstado("ESTADO_DIFERENTE");

		try {
			// act
			builder.build();
			fail();
		} catch (ExcepcionValorInvalido e) {
			// assert
			assertThat(e.getMessage(), is(notNullValue()));
		}
	}

}
