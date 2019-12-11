package com.ceiba.adn.serviciobano.dominio.modelo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;

import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.testdatabuilder.CuentaTestDataBuilder;

public class CuentaTest {

	@Test
	public void validarGetters() {
		// arrange
		Cuenta cuenta;

		// act
		cuenta = new CuentaTestDataBuilder().build();

		// assert
		assertThat(cuenta.getId(), equalTo(1L));
		assertThat(cuenta.getIdBano(), equalTo(1L));
		assertThat(cuenta.getSobres(), equalTo(1));
		assertThat(cuenta.getEstado(), equalTo(EstadoCuenta.ABIERTA.getEstado()));
		assertThat(cuenta.getTotalCobro(), equalTo(new BigDecimal(0)));
		assertThat(cuenta.getFechaIngreso(), equalTo(LocalDateTime.of(2019, 12, 8, 6, 0)));
	}

	@Test
	public void validarSetters() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().build();

		// act
		cuenta.setId(2L);
		cuenta.setIdBano(3L);
		cuenta.setSobres(3);
		cuenta.setEstado(EstadoCuenta.CERRADA.getEstado());
		cuenta.setTotalCobro(new BigDecimal(2500));
		cuenta.setFechaIngreso(LocalDateTime.of(2019, 12, 7, 3, 0));

		// assert
		assertThat(cuenta.getId(), equalTo(2L));
		assertThat(cuenta.getIdBano(), equalTo(3L));
		assertThat(cuenta.getSobres(), equalTo(3));
		assertThat(cuenta.getEstado(), equalTo(EstadoCuenta.CERRADA.getEstado()));
		assertThat(cuenta.getTotalCobro(), equalTo(new BigDecimal(2500)));
		assertThat(cuenta.getFechaIngreso(), equalTo(LocalDateTime.of(2019, 12, 7, 3, 0)));
	}

	@Test
	public void validarConstructor1() {
		// arrange
		Cuenta cuenta;
		BigDecimal totalCobro = new BigDecimal(1000);
		LocalDateTime fechaIngreso = LocalDateTime.of(2019, 12, 7, 3, 0);

		// act
		cuenta = new Cuenta(1L, 1L, 0, EstadoCuenta.ABIERTA.getEstado(), totalCobro, fechaIngreso);

		// assert
		assertThat(cuenta.getId(), equalTo(1L));
		assertThat(cuenta.getIdBano(), equalTo(1L));
		assertThat(cuenta.getSobres(), equalTo(0));
		assertThat(cuenta.getEstado(), equalTo(EstadoCuenta.ABIERTA.getEstado()));
		assertThat(cuenta.getTotalCobro(), equalTo(new BigDecimal(1000)));
		assertThat(cuenta.getFechaIngreso(), equalTo(LocalDateTime.of(2019, 12, 7, 3, 0)));
	}

	@Test
	public void cuandoCamposCuentaCorrectosEntoncesValidacionExitosa() {
		// arrange
		Cuenta cuenta;

		// act
		cuenta = new CuentaTestDataBuilder().build();

		// assert
		assertThat(cuenta.getId(), equalTo(1L));
		assertThat(cuenta.getIdBano(), equalTo(1L));
		assertThat(cuenta.getSobres(), equalTo(1));
		assertThat(cuenta.getEstado(), equalTo(EstadoCuenta.ABIERTA.getEstado()));
		assertThat(cuenta.getTotalCobro(), equalTo(new BigDecimal(0)));
		assertThat(cuenta.getFechaIngreso(), equalTo(LocalDateTime.of(2019, 12, 8, 6, 0)));
	}

	@Test
	public void cuandoValorCampoEstadoIncorrectoEntoncesValidacionFalla() {
		// arrange
		CuentaTestDataBuilder builder = new CuentaTestDataBuilder();
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

	@Test
	public void cuandoValorCampoFechaIngresoMayorAActualEntoncesValidacionFalla() {
		// arrange
		CuentaTestDataBuilder builder = new CuentaTestDataBuilder();
		builder.withFechaIngreso(LocalDateTime.now().plusMinutes(10));

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
